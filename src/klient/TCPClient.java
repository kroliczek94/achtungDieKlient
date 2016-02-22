/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.awt.Color;
import java.awt.HeadlessException;
import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Set;
import javax.swing.JOptionPane;
import org.json.simple.JSONArray;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

/**
 *
 * @author lukasz
 */
public class TCPClient extends Thread {

    private static final int BUFFER_SIZE = 255;
    private static final long CHANNEL_WRITE_SLEEP = 10L;
    private static final int PORT = 12345;

    private ByteBuffer writeBuffer;
    private ByteBuffer readBuffer;
    private boolean running;
    private SocketChannel channel;
    private String host;
    private Selector readSelector;
    private CharsetDecoder asciiDecoder;
    private InputThread it;
    private MenuLogowania ml;
    private Grafika graf;

//    public static void main(String args[]) {
//	String host = args[0];
//	TCPClient cc = new TCPClient(host);
//	cc.start();
//    }
    public TCPClient(String host, MenuLogowania ml, Grafika graf) {
        this.host = host;
        writeBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        readBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
        asciiDecoder = Charset.forName("US-ASCII").newDecoder();
        this.ml = ml;
        this.graf = graf;
    }

    public void run() {
        connect(host);
        it = new InputThread(this);
        it.start();

    }

    private void connect(String hostname) {
        try {
            readSelector = Selector.open();
            InetAddress addr = InetAddress.getByName(hostname);
            channel = SocketChannel.open(new InetSocketAddress(addr, PORT));
            channel.configureBlocking(false);
            channel.register(readSelector, SelectionKey.OP_READ, new StringBuffer());
        } catch (UnknownHostException uhe) {
        } catch (ConnectException ce) {
        } catch (Exception e) {
        }
    }

    public void readIncomingMessages() {
        // check for incoming mesgs
        try {
            // non-blocking select, returns immediately regardless of how many keys are ready
            readSelector.selectNow();

            // fetch the keys
            Set readyKeys = readSelector.selectedKeys();

            // run through the keys and process
            Iterator i = readyKeys.iterator();
            while (i.hasNext()) {
                SelectionKey key = (SelectionKey) i.next();
                i.remove();
                SocketChannel channel = (SocketChannel) key.channel();
                readBuffer.clear();

                // read from the channel into our buffer
                long nbytes = channel.read(readBuffer);

                // check for end-of-stream
                if (nbytes == -1) {
                    System.out.println("disconnected from server: end-of-stream");
                    channel.close();
                    shutdown();
                    it.shutdown();
                } else {
                    // grab the StringBuffer we stored as the attachment
                    StringBuffer sb = (StringBuffer) key.attachment();

                    readBuffer.flip();
                    String str = asciiDecoder.decode(readBuffer).toString();
                    sb.append(str);
                    readBuffer.clear();

                    String line = sb.toString();
                    sb.delete(0, sb.length());

                    JSONParser parser = new JSONParser();
                    Object obj = parser.parse(line);
                    JSONObject jsonObj = (JSONObject) obj;

                    String stan = (String) jsonObj.get("action");
                    System.out.println(line);
                    if ("register".equals(stan)) {
                        Boolean ok = (Boolean) jsonObj.get("ok");
                        Long id = (Long) jsonObj.get("id");

                        if (!ok) {
                            JOptionPane.showMessageDialog(null, "Wybrane miejsce jest już zajęte!");
                        }
                        //                       ml.zmienLabela(id, Color.yellow);
                        System.out.println("Zarejestrowano gracza " + id.intValue());
                    } else if ("statebefore".equals(stan)) {
                        JSONArray tabStan = new JSONArray();
                        JSONArray tabImie = new JSONArray();
                        JSONArray tabReady = new JSONArray();

                        //Long ttl = (Long) jsonObj.get("ttl");
                        ml.resetLabeli();
                        tabStan = (JSONArray) jsonObj.get("reservedplaces");
                        tabImie = (JSONArray) jsonObj.get("peoplenames");
                        tabReady = (JSONArray) jsonObj.get("readypeople");

                        //ml.zmienTTL(ttl);
                        for (int j = 0; j < 6; j++) {
                            ml.zmienLabela(Long.valueOf(j), (String) tabImie.get(j));
                        }

                        for (int j = 0; j < 6; j++) {
                            if ((boolean) tabStan.get(j)) {
                                ml.zmienLabela(Long.valueOf(j), Color.yellow);
                            }
                        }

                        for (int j = 0; j < 6; j++) {
                            if ((boolean) tabReady.get(j)) {
                                ml.zmienLabela(Long.valueOf(j), Color.green);
                            }
                        }
                    } else if ("letstart".equals(stan)) {
                        boolean x = (boolean) jsonObj.get("success");
                        if (!x) {
                            JOptionPane.showMessageDialog(null, "Niepowodzenie przy uruchomieniu gry\nSprawdź liczbę uczestników");
                        } else {
                            Klient.setStart(true);
                        }
                    } else if (("stateafter").equals(stan)) {
                        Klient.setStart(true);
                        JSONArray nazwy = new JSONArray();
                        JSONArray punkty = new JSONArray();
                        JSONArray idPlayers = new JSONArray();
                        JSONArray xPos = new JSONArray();
                        JSONArray yPos = new JSONArray();

                        idPlayers = (JSONArray) jsonObj.get("idplayers");
                        nazwy = (JSONArray) jsonObj.get("peoplenames");
                        punkty = (JSONArray) jsonObj.get("points");
                        xPos = (JSONArray) jsonObj.get("xpos");
                        yPos = (JSONArray) jsonObj.get("ypos");

                        for (int i1 = 0; i1 < idPlayers.size(); i1++) {
                            Long id = (Long) idPlayers.get(i1);
                            Long l = (Long) punkty.get(i1);
                            String name = (String) nazwy.get(i1);
                            Klient.getGracze().get(id.intValue()).setActivePlayer(true);
                            Player.getDane().add(new PlayerToTab(name, id.intValue(), l.intValue()));
                        }

                        Collections.sort(Player.getDane(), new Comparator<PlayerToTab>() {
                            @Override
                            public int compare(PlayerToTab o1, PlayerToTab o2) {
                                return -1*o1.getPoints().compareTo(o2.getPoints());
                            }
                        });

                        for (int k = 0; k < Player.getDane().size(); k++) {

                            PlayerToTab ptt = Player.getDane().get(k);
                            graf.setImiona(k, ptt.getName(), ptt.getPoints(), ptt.getC());
                            Player.getDane().remove(ptt);

                        }

                        for (int i1 = 0; i1 < 6; i1++) {
                            if (!idPlayers.contains(Long.valueOf(i1))) {
                                continue;
                            }

                            Long x = (Long) xPos.get(0);
                            Long y = (Long) yPos.get(0);
                            Player p = Klient.getGracze().get(i1);

                            p.setOldx(p.getX());
                            p.setOldy(p.getY());

                            p.setX(x.intValue());
                            p.setY(y.intValue());

                            xPos.remove(0);
                            yPos.remove(0);

                        }

                    }

                }
            }
        } catch (IOException ioe) {
        } catch (ParseException | HeadlessException e) {

            System.out.println("nic " + e.getMessage());
        }
    }

    public void sendMessage(String mesg) {
        prepWriteBuffer(mesg);
        channelWrite(channel, writeBuffer);
    }

    private void prepWriteBuffer(String mesg) {
        // fills the buffer from the given string
        // and prepares it for a channel write
        writeBuffer.clear();
        writeBuffer.put(mesg.getBytes());
        writeBuffer.putChar('\n');
        writeBuffer.flip();
    }

    private void channelWrite(SocketChannel channel, ByteBuffer writeBuffer) {
        long nbytes = 0;
        long toWrite = writeBuffer.remaining();

        // loop on the channel.write() call since it will not necessarily
        // write all bytes in one shot
        try {
            while (nbytes != toWrite) {
                nbytes += channel.write(writeBuffer);

                try {
                    Thread.sleep(CHANNEL_WRITE_SLEEP);
                    //Thread.sleep(30);
                } catch (InterruptedException e) {
                }
            }
        } catch (ClosedChannelException cce) {
        } catch (Exception e) {
        }

        // get ready for another write if needed
        writeBuffer.rewind();
    }

    public void shutdown() {
        running = false;
        interrupt();
    }

    /**
     * InputThread reads user input from STDIN
     */
}

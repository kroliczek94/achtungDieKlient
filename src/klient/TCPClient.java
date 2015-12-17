/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.io.*;
import java.net.*;
import java.nio.ByteBuffer;
import java.nio.channels.ClosedChannelException;
import java.nio.channels.SelectionKey;
import java.nio.channels.Selector;
import java.nio.channels.SocketChannel;
import java.nio.charset.Charset;
import java.nio.charset.CharsetDecoder;
import java.nio.charset.StandardCharsets;
import java.util.Iterator;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author lukasz
 */
public class TCPClient extends Thread {
    private static final int BUFFER_SIZE = 255;
    private static final long CHANNEL_WRITE_SLEEP = 10L;
    private static final int PORT = 8888;
  
    private ByteBuffer writeBuffer;
    private ByteBuffer readBuffer;
    private boolean running;
    private SocketChannel channel;
    private String host;
    private Selector readSelector;
    private CharsetDecoder asciiDecoder;
    private InputThread it;
    
//    public static void main(String args[]) {
//	String host = args[0];
//	TCPClient cc = new TCPClient(host);
//	cc.start();
//    }

    public TCPClient(String host) {
	this.host = host;
	writeBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
	readBuffer = ByteBuffer.allocateDirect(BUFFER_SIZE);
	asciiDecoder = Charset.forName( "US-ASCII").newDecoder();;
    }

    public void run() {
	connect(host);
	it = new InputThread(this);
	it.start();
	
	/*running = true;
	while (running) {
	    readIncomingMessages();

	    // nap for a bit
	    try {
		Thread.sleep(50);
	    }
	    catch (InterruptedException ie) {
	    }
	}*/
    }

    private void connect(String hostname) {
	try {
	    readSelector = Selector.open();
	    InetAddress addr = InetAddress.getByName(hostname);
	    channel = SocketChannel.open(new InetSocketAddress(addr, PORT));
	    channel.configureBlocking(false);
	    channel.register(readSelector, SelectionKey.OP_READ, new StringBuffer());
	}
	catch (UnknownHostException uhe) {
	}
	catch (ConnectException ce) {
	}
	catch (Exception e) {
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
		}
		else {
		    // grab the StringBuffer we stored as the attachment
		    StringBuffer sb = (StringBuffer)key.attachment();

		    // use a CharsetDecoder to turn those bytes into a string
		    // and append to our StringBuffer
		    readBuffer.flip( );
		    String str = asciiDecoder.decode( readBuffer).toString( );
		    sb.append( str );
		    readBuffer.clear( );

		    // check for a full line and write to STDOUT
		    String line = sb.toString();
                    //System.out.println(line);
                    
		    if ((line.indexOf("\n") != -1) || (line.indexOf("\r") != -1)) {
			sb.delete(0,sb.length());
			System.out.print(line);
			//System.out.print("> ");
		    }
		}
	    }		
	}
	catch (IOException ioe) {
	}
	catch (Exception e) {
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
		}
		catch (InterruptedException e) {}
	    }
	}
	catch (ClosedChannelException cce) {
	}
	catch (Exception e) {
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

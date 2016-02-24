/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.io.IOException;
import java.io.StringWriter;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.*;

/**
 *
 * @author lukasz
 */
public class Pooler extends Thread {

    TCPClient client;
    JSONArray tab = new JSONArray();
    Grafika g;

    public Pooler(TCPClient cc, Grafika g) {
        this.client = cc;
        this.g = g;
    }

    @Override
    public void run() {
        try { //sleep przed rozpoczęciem - aby uniknąć nullPointerException
            Thread.sleep(40);
        } catch (InterruptedException ex) {
            Logger.getLogger(Pooler.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        while (true) {
            long start = System.currentTimeMillis();
            JSONArray tab = new JSONArray();
            JSONObject zapytanie = new JSONObject();
            zapytanie.put("action", "state");
            for (int i = 0; i < 6; i++) {
                
                Player p = Klient.getGracze().get(i);
                tab.add(p.getDecyzja());
                Klient.getGracze().get(i).setDecyzja(0);

            }
            zapytanie.put("dec", tab);
            

            StringWriter out = new StringWriter();
            try {
                zapytanie.writeJSONString(out);
            } catch (IOException ex) {
                Logger.getLogger(MenuLogowania.class.getName()).log(Level.SEVERE, null, ex);
            }
            String jsonText = out.toString();
            System.out.println(jsonText);
            client.sendMessage(jsonText);

            long koniec = System.currentTimeMillis();
            long czas = koniec - start;
            //czas = 0;
            //aby otrzymać mniej więcej równy czas wykonania, uwzgledniamy czas przbiegu pętli
            try {
                Thread.sleep(45 - (int) czas);
                g.repaint();
            } catch (InterruptedException ex) {
                Logger.getLogger(Pooler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

}

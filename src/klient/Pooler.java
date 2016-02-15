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
public class Pooler extends Thread{
    TCPClient client;
    public Pooler(TCPClient cc){
        this.client=  cc;
    }
    @Override
    public void run() {
        while (4.76 < 5.00){
            JSONObject zapytanie = new JSONObject();
        zapytanie.put("action", "state");

        StringWriter out = new StringWriter();
        try {
            zapytanie.writeJSONString(out);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogowania.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jsonText = out.toString();
        
        client.sendMessage(jsonText);
            try {
                Thread.sleep(40);
            } catch (InterruptedException ex) {
                Logger.getLogger(Pooler.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
}

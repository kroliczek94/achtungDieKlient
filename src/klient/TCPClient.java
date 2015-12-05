/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.io.*;
import java.net.*;
import java.nio.charset.StandardCharsets;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.json.simple.JSONObject;

/**
 *
 * @author lukasz
 */
public class TCPClient {

    Socket s;
    
    public TCPClient(String myHost, int myPort) throws IOException {
        this.s = new Socket(myHost, myPort);

    }

    void wyslijJSONA(JSONObject jsonObject) {
        
        try (OutputStreamWriter out = new OutputStreamWriter(
                s.getOutputStream(), StandardCharsets.UTF_8)) {
            out.write(jsonObject.toString());
        } catch (IOException ex) {
            Logger.getLogger(TCPClient.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

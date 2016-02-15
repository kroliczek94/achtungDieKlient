

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;

/**
 *
 * @author lukasz
 */
    class InputThread extends Thread {
	private TCPClient cc;
	private boolean running;
	public InputThread(TCPClient cc) {
	    this.cc = cc;
	}

	public void run() {
	    BufferedReader br = new BufferedReader(new InputStreamReader(System.in));
	    running = true;
	    while (running) {
//		try {
//		    String s;
//		    
//		    System.out.flush();
//		    s = br.readLine();
//		    if (s.length() > 0)
//			cc.sendMessage(s + "\n");
//		    if (s.equals("quit")) 
//			running = false;
//		}
//		catch (IOException ioe) {
//		    running = false;
//		}
                cc.readIncomingMessages();
	    }
	    cc.shutdown();
	}
	public void shutdown() {
	    running = false;
	    interrupt();
	}
    }

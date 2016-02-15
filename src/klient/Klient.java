/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.awt.EventQueue;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JFrame;
import java.io.*;
import java.net.Socket;

/** 
 *
 * @author Łukasz Królik
 */
public class Klient {

    private static ArrayList<Player> gracze = new ArrayList();

    /**
     * @param args the command line arguments
     */
    public static void main(String[] args) throws InterruptedException {
        Grafika g = new Grafika(gracze, null);
        MenuLogowania menu = new MenuLogowania(null);
        
        
        String host = args[0];
        TCPClient cc = new TCPClient(host, menu, g);
        g.setCc(cc);
        menu.setCc(cc);
        Pooler pool  = new Pooler(cc);
        pool.start();
        cc.start();

        for (int i = 0; i < 6; i++) {
//            if (i > 0) {
//                continue;
//            }
            Player p = new Player();
            getGracze().add(p);
        }

        
        JFrame window = new JFrame("Achtung Die Kurve");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        

        window.add(menu);
        window.setVisible(true);
        window.setSize(400, 330);
        
        boolean start = false;
        while (!start){
           Thread.sleep(1000);
           
        }
        
        window.add(g);
        window.setSize(700, 500);
        window.setVisible(true);

        while (true) {
            try {
                for (Player p : Klient.getGracze()) {
                    p.setModulo(false);
                    p.setDecyzja(0);
                    Thread.sleep(50);
                    p.setKat(p.getKat() + p.getDecyzja());
                    System.out.println("Kat " + p.getKat() + "Decyzja " + p.getDecyzja());
                }

                g.repaint();
            } catch (InterruptedException ex) {
                Logger.getLogger(Klient.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    /**
     * @return the gracze
     */
    public static ArrayList<Player> getGracze() {
        return gracze;
    }

    /**
     * @param aGracze the gracze to set
     */
    public static void setGracze(ArrayList<Player> aGracze) {
        gracze = aGracze;
    }

}

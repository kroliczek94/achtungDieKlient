/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.util.ArrayList;
import javax.swing.JFrame;

/** 
 *
 * @author Łukasz Królik
 */
public class Klient {

    private static ArrayList<Player> gracze = new ArrayList();
    private static boolean start = false;
    private static int maxX = 1000;
    private static int maxY = 600;
    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        Grafika g = new Grafika(getGracze());
        MenuLogowania menu = new MenuLogowania(null);
        
        
        String host = args[0];
        TCPClient cc = new TCPClient(host, menu, g);
            
        menu.setCc(cc);
        Pooler pool  = new Pooler(cc, g);
        pool.start();
        cc.start();

        for (int i = 0; i < 6; i++) {

            Player p = new Player(i);
            getGracze().add(p);
        }

        
        JFrame window = new JFrame("Achtung Die Kurve");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        
        window.add(menu);
        window.setVisible(true);
        window.setSize(400, 330);
        
        while (!isStart()) {Thread.sleep(1000);}
        menu.setVisible(false);
        window.add(g);
        g.setFocusable(true);
        g.requestFocus();
        
        window.setSize(1366, 720);
        window.setVisible(true);

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

    /**
     * @return the start
     */
    public static boolean isStart() {
        return start;
    }

    /**
     * @param aStart the start to set
     */
    public static void setStart(boolean aStart) {
        start = aStart;
    }

    /**
     * @return the maxX
     */
    public static int getMaxX() {
        return maxX;
    }

    /**
     * @param aMaxX the maxX to set
     */
    public static void setMaxX(int aMaxX) {
        maxX = aMaxX;
    }

    /**
     * @return the maxY
     */
    public static int getMaxY() {
        return maxY;
    }

    /**
     * @param aMaxY the maxY to set
     */
    public static void setMaxY(int aMaxY) {
        maxY = aMaxY;
    }

}

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
    private static boolean restart = false;
    private static boolean exit = false;

    /**
     * @param args the command line arguments
     * @throws java.lang.InterruptedException
     */
    public static void main(String[] args) throws InterruptedException {
        //Klasa grafika odpowiadająca za wyswietlanie planszy i menu, odpowiedzialne za logowanie gracza
        Grafika g = new Grafika(getGracze());
        MenuLogowania menu = new MenuLogowania(null);

        String host = args[0];
        TCPClient cc = new TCPClient(host, menu, g);
        
        Pooler pool = new Pooler(cc, g);
        g.setCc(cc);
        //wątki słuchające i wysyłające
        pool.start();
        cc.start();

        for (int i = 0; i < 6; i++) {

            Player p = new Player(i);
            getGracze().add(p);
        }

        JFrame window = new JFrame("Achtung Die Kurve");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        while (!exit) {
            MenuLogowania menu1 = new MenuLogowania(null);
            menu1.setCc(cc);
            cc.setMl(menu1);
            
            restart = false;
            menu1.setVisible(true);
            window.add(menu1);
            window.setVisible(true);
            window.setSize(400, 330);

            while (!isStart()) {
                Thread.sleep(1000);
            }
            menu1.setVisible(false);
            window.add(g);
            g.setFocusable(true);
            g.requestFocus();

            window.setSize(1366, 720);
            window.setVisible(true);
            while (!isRestart()) {
                Thread.sleep(1000);
            }
            Klient.setStart(false);       
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

    /**
     * @return the restart
     */
    public static boolean isRestart() {
        return restart;
    }

    /**
     * @param aRestart the restart to set
     */
    public static void setRestart(boolean aRestart) {
        restart = aRestart;
    }

    /**
     * @return the exit
     */
    public static boolean isExit() {
        return exit;
    }

    /**
     * @param aExit the exit to set
     */
    public static void setExit(boolean aExit) {
        exit = aExit;
    }

}

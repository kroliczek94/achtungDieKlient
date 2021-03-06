/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.awt.BasicStroke;
import java.awt.Color;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.awt.geom.Line2D;
import java.io.IOException;
import java.io.StringWriter;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import org.json.simple.JSONObject;

/**
 *
 * @author Łukasz Królik
 */
public class Grafika extends javax.swing.JPanel implements KeyListener {

    public static void end() {

        for (Player p: Klient.getGracze()){
            p.setActivePlayer(false);
            p.setMyPlayer(false);
        }
        String message = "Zwycięzcą został " + getWinner()+ "\nKoniec gry, chcesz zagrać jeszcze raz?" ;
        String title = "KONIEC";
        
        int reply = JOptionPane.showConfirmDialog(null, message, title, JOptionPane.YES_NO_OPTION);
        if (reply == JOptionPane.NO_OPTION) {
            Klient.setExit(true);
            Klient.setRestart(true);
        }else{
            Klient.setRestart(true);
        }
    }

    /**
     * @return the winner
     */
    public static String getWinner() {
        return winner;
    }

    /**
     * @param aWinner the winner to set
     */
    public static void setWinner(String aWinner) {
        winner = aWinner;
    }

    /**
     * @return the end
     */
    public static boolean isEnd() {
        return end;
    }

    /**
     * @param aEnd the end to set
     */
    public static void setEnd(boolean aEnd) {
        end = aEnd;
    }

    private ArrayList<Player> p;
    private TCPClient cc;

    private Color[] colors;
    private static boolean restart = false;
    private static boolean pause = true;
    private static String winner = "";
    private static boolean end = false;

    /**
     * Creates new form Grafika
     */
    public Grafika(ArrayList<Player> p) {
        this.colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};
        initComponents();

        PlacesLabel1.setVisible(false);
        PlacesLabel2.setVisible(false);
        PlacesLabel3.setVisible(false);
        PlacesLabel4.setVisible(false);
        PlacesLabel5.setVisible(false);
        PlacesLabel6.setVisible(false);

        addKeyListener(this);
        setFocusable(true);

        this.p = p;

    }

    public Grafika(ArrayList<Player> p, TCPClient cc) {
        this.colors = new Color[]{Color.RED, Color.GREEN, Color.BLUE, Color.YELLOW, Color.MAGENTA, Color.CYAN};
        this.p = p;
        this.cc = cc;
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jPanel1 = new javax.swing.JPanel();
        PlacesLabel1 = new javax.swing.JLabel();
        PlacesLabel2 = new javax.swing.JLabel();
        PlacesLabel3 = new javax.swing.JLabel();
        PlacesLabel4 = new javax.swing.JLabel();
        PlacesLabel5 = new javax.swing.JLabel();
        PlacesLabel6 = new javax.swing.JLabel();
        SpaceLabel = new javax.swing.JLabel();

        setPreferredSize(new java.awt.Dimension(1366, 720));
        addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                formKeyPressed(evt);
            }
        });

        jPanel1.setBackground(new java.awt.Color(237, 150, 237));

        PlacesLabel1.setText("jLabel1");

        PlacesLabel2.setText("jLabel2");

        PlacesLabel3.setText("jLabel3");

        PlacesLabel4.setText("jLabel4");

        PlacesLabel5.setText("jLabel5");

        PlacesLabel6.setText("jLabel6");

        javax.swing.GroupLayout jPanel1Layout = new javax.swing.GroupLayout(jPanel1);
        jPanel1.setLayout(jPanel1Layout);
        jPanel1Layout.setHorizontalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(PlacesLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PlacesLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                        .addGap(95, 95, 95))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PlacesLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(94, 94, 94))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PlacesLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(94, 94, 94))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PlacesLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(95, 95, 95))
                    .addGroup(jPanel1Layout.createSequentialGroup()
                        .addComponent(PlacesLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                        .addGap(95, 95, 95)))
                .addGap(33, 33, 33))
        );
        jPanel1Layout.setVerticalGroup(
            jPanel1Layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(jPanel1Layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(PlacesLabel1, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlacesLabel2, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlacesLabel3, javax.swing.GroupLayout.DEFAULT_SIZE, 80, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlacesLabel4, javax.swing.GroupLayout.DEFAULT_SIZE, 85, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlacesLabel5, javax.swing.GroupLayout.DEFAULT_SIZE, 81, Short.MAX_VALUE)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(PlacesLabel6, javax.swing.GroupLayout.DEFAULT_SIZE, 83, Short.MAX_VALUE)
                .addGap(80, 80, 80))
        );

        SpaceLabel.setText("Naciśnij Space aby rozpocząć turę");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(javax.swing.GroupLayout.Alignment.TRAILING, layout.createSequentialGroup()
                .addGap(458, 458, 458)
                .addComponent(SpaceLabel)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, 457, Short.MAX_VALUE)
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap())
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(jPanel1, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(67, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addComponent(SpaceLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 106, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addGap(0, 0, Short.MAX_VALUE))
        );
    }// </editor-fold>//GEN-END:initComponents

    private void formKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_formKeyPressed
 
    }//GEN-LAST:event_formKeyPressed

    @Override
    public void paintComponent(Graphics g) {
        if (restart) {
            SpaceLabel.setVisible(true);
            super.paintComponent(g);
            setRestart(false);
        }
        for (Player pl : Klient.getGracze()) {

            if (pl.isActivePlayer()) {

                g.drawRect(30, 30, 1000, 600);
                    g.setColor(Color.BLUE);
                    malujDoPunktu(pl, g);
                
            }
        }
    }

    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JLabel PlacesLabel1;
    private javax.swing.JLabel PlacesLabel2;
    private javax.swing.JLabel PlacesLabel3;
    private javax.swing.JLabel PlacesLabel4;
    private javax.swing.JLabel PlacesLabel5;
    private javax.swing.JLabel PlacesLabel6;
    private javax.swing.JLabel SpaceLabel;
    private javax.swing.JPanel jPanel1;
    // End of variables declaration//GEN-END:variables
    @Override
    public void keyPressed(KeyEvent e) {
        if (KeyEvent.VK_SPACE == e.getKeyCode()) {
            JSONObject zapytanie = new JSONObject();
            zapytanie.put("action", "unpause");

            StringWriter out = new StringWriter();
            try {
                zapytanie.writeJSONString(out);
            } catch (IOException ex) {
                Logger.getLogger(MenuLogowania.class.getName()).log(Level.SEVERE, null, ex);
            }
            
            String jsonText = out.toString();
            SpaceLabel.setVisible(false);
            pause = false;
            cc.sendMessage(jsonText);

        }

        for (Player p : Klient.getGracze()) {
            if (e.getKeyCode() == p.getLewy()) {
                p.setDecyzja(15);
                System.out.println("Decyzja L !");
            }

            if (e.getKeyCode() == p.getPrawy()) {
                p.setDecyzja(-15);
                System.out.println("Decyzja P !");
            }

        }

        //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void keyReleased(KeyEvent e) {
        System.out.println("Dziala to");
        //throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public void malujDoPunktu(Player p, Graphics g) {
        Graphics2D g2 = (Graphics2D) g;
        g2.setStroke(new BasicStroke(5));
        switch (p.getId()) {
            case 0:
                g.setColor(Color.red);
                break;
            case 1:
                g.setColor(Color.green);
                break;
            case 2:
                g.setColor(Color.blue);
                break;
            case 3:
                g.setColor(Color.yellow);
                break;
            case 4:
                g.setColor(Color.magenta);
                break;
            case 5:
                g.setColor(Color.cyan);
                break;
        }
        System.out.println(" -> " + p.getOldx() + " " + p.getOldx() + ":" + p.getX() + " " + p.getY());
        g2.draw(new Line2D.Double(p.getOldx() + 30, p.getOldy() + 30, p.getX() + 30, p.getY() + 30));
        g.setColor(Color.black);

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
        pause = true;

    }

    public void rstrt() {
        SpaceLabel.setVisible(true);
    }

    public void setImiona(int nkol, String i, Integer points, Integer c) {

        switch (nkol) {
            case 0:
                PlacesLabel1.setVisible(true);
                PlacesLabel1.setText(i + " : " + String.valueOf(points));
                PlacesLabel1.setForeground(colors[c]);
                break;
            case 1:
                PlacesLabel2.setVisible(true);
                PlacesLabel2.setText(i + " : " + String.valueOf(points));
                PlacesLabel2.setForeground(colors[c]);
                break;
            case 2:
                PlacesLabel3.setVisible(true);
                PlacesLabel3.setText(i + " : " + String.valueOf(points));
                PlacesLabel3.setForeground(colors[c]);
                break;
            case 3:
                PlacesLabel4.setVisible(true);
                PlacesLabel4.setText(i + " : " + String.valueOf(points));
                PlacesLabel4.setForeground(colors[c]);
                break;
            case 4:
                PlacesLabel5.setVisible(true);
                PlacesLabel5.setText(i + " : " + String.valueOf(points));
                PlacesLabel5.setForeground(colors[c]);
                break;
            case 5:
                PlacesLabel6.setVisible(true);
                PlacesLabel6.setText(i + " : " + String.valueOf(points));
                PlacesLabel6.setForeground(colors[c]);
                break;

        }
    }

    @Override
    public void keyTyped(KeyEvent e) {

    }

    /**
     * @return the pause
     */
    public static boolean isPause() {
        return pause;
    }

    /**
     * @param aPause the pause to set
     */
    public static void setPause(boolean aPause) {
        pause = aPause;
    }

    /**
     * @return the cc
     */
    public TCPClient getCc() {
        return cc;
    }

    /**
     * @param cc the cc to set
     */
    public void setCc(TCPClient cc) {
        this.cc = cc;
    }

}

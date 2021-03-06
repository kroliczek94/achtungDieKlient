        /*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.awt.Color;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.io.IOException;
import java.io.StringWriter;
import java.util.concurrent.ConcurrentLinkedDeque;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

import org.json.simple.*;

/**
 *
 * @author Łukasz Królik
 */
public class MenuLogowania extends javax.swing.JPanel implements KeyListener {

    int activePlayer = -1;
    int dostepnyKlawisz = 0;
    private TCPClient cc;
    private ConcurrentLinkedDeque<Integer> stos = new ConcurrentLinkedDeque<>();

    /**
     * Creates new form MenuLogowania
     */
    public MenuLogowania(TCPClient cc) {
        initComponents();
        addKeyListener(this);
        setFocusable(true);
        zmienionoImieLabel.setVisible(false);
        this.cc = cc;
        stos.add(-1);
    }

    /**
     * This method is called from within the constructor to initialize the form.
     * WARNING: Do NOT modify this code. The content of this method is always
     * regenerated by the Form Editor.
     */
    @SuppressWarnings("unchecked")
    // <editor-fold defaultstate="collapsed" desc="Generated Code">//GEN-BEGIN:initComponents
    private void initComponents() {

        jTextField1 = new javax.swing.JTextField();
        g1Button = new javax.swing.JButton();
        g2Button = new javax.swing.JButton();
        g3Button = new javax.swing.JButton();
        g4Button = new javax.swing.JButton();
        g5Button = new javax.swing.JButton();
        g6Button = new javax.swing.JButton();
        g1Label = new javax.swing.JLabel();
        g2Label = new javax.swing.JLabel();
        g3Label = new javax.swing.JLabel();
        g4Label = new javax.swing.JLabel();
        g5Label = new javax.swing.JLabel();
        g6Label = new javax.swing.JLabel();
        leftLabel = new javax.swing.JLabel();
        rightLabel = new javax.swing.JLabel();
        changeButton = new javax.swing.JButton();
        StartButton = new javax.swing.JButton();
        imieField = new javax.swing.JTextField();
        imieButton = new javax.swing.JButton();
        AddButton = new javax.swing.JButton();
        zmienionoImieLabel = new javax.swing.JLabel();
        AnulujButton = new javax.swing.JButton();
        ttlLabel = new javax.swing.JLabel();

        jTextField1.setText("jTextField1");

        setPreferredSize(new java.awt.Dimension(400, 300));
        addFocusListener(new java.awt.event.FocusAdapter() {
            public void focusGained(java.awt.event.FocusEvent evt) {
                formFocusGained(evt);
            }
        });

        g1Button.setBackground(new java.awt.Color(255, 0, 0));
        g1Button.setText("Dołącz");
        g1Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g1ButtonActionPerformed(evt);
            }
        });

        g2Button.setBackground(new java.awt.Color(0, 255, 0));
        g2Button.setText("Dołącz");
        g2Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g2ButtonActionPerformed(evt);
            }
        });

        g3Button.setBackground(new java.awt.Color(0, 0, 255));
        g3Button.setText("Dołącz");
        g3Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g3ButtonActionPerformed(evt);
            }
        });

        g4Button.setBackground(new java.awt.Color(255, 255, 0));
        g4Button.setText("Dołącz");
        g4Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g4ButtonActionPerformed(evt);
            }
        });

        g5Button.setBackground(new java.awt.Color(255, 0, 255));
        g5Button.setText("Dołącz");
        g5Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g5ButtonActionPerformed(evt);
            }
        });

        g6Button.setBackground(new java.awt.Color(0, 255, 255));
        g6Button.setText("Dołącz");
        g6Button.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                g6ButtonActionPerformed(evt);
            }
        });

        g1Label.setForeground(new java.awt.Color(255, 0, 0));
        g1Label.setText("Gracz 1");

        g2Label.setForeground(new java.awt.Color(255, 0, 0));
        g2Label.setText("Gracz 2");

        g3Label.setForeground(new java.awt.Color(255, 0, 0));
        g3Label.setText("Gracz 3");

        g4Label.setForeground(new java.awt.Color(255, 0, 0));
        g4Label.setText("Gracz 4 ");

        g5Label.setForeground(new java.awt.Color(255, 0, 0));
        g5Label.setText("Gracz 5");

        g6Label.setForeground(new java.awt.Color(255, 0, 0));
        g6Label.setText("Gracz 6");

        leftLabel.setText("LEFT");

        rightLabel.setText("RIGHT");

        changeButton.setText("Zmień klawisze");
        changeButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                changeButtonActionPerformed(evt);
            }
        });
        changeButton.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                changeButtonKeyPressed(evt);
            }
        });

        StartButton.setText("Rozpocznij");
        StartButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                StartButtonActionPerformed(evt);
            }
        });

        imieField.addKeyListener(new java.awt.event.KeyAdapter() {
            public void keyPressed(java.awt.event.KeyEvent evt) {
                imieFieldKeyPressed(evt);
            }
        });

        imieButton.setText("Zmień nick");
        imieButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                imieButtonActionPerformed(evt);
            }
        });

        AddButton.setText("Dodaj gracza");
        AddButton.addActionListener(new java.awt.event.ActionListener() {
            public void actionPerformed(java.awt.event.ActionEvent evt) {
                AddButtonActionPerformed(evt);
            }
        });

        zmienionoImieLabel.setText("Zmieniono imię");

        AnulujButton.setText("Anuluj");

        javax.swing.GroupLayout layout = new javax.swing.GroupLayout(this);
        this.setLayout(layout);
        layout.setHorizontalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(10, 10, 10)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(g3Label)
                            .addComponent(g6Label)
                            .addComponent(g1Label)
                            .addComponent(g5Label)
                            .addComponent(g4Label)
                            .addComponent(g2Label))
                        .addGap(41, 41, 41)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.TRAILING)
                            .addComponent(g1Button)
                            .addComponent(g2Button)
                            .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                                .addComponent(g6Button)
                                .addComponent(g5Button)
                                .addComponent(g4Button)
                                .addComponent(g3Button))))
                    .addGroup(layout.createSequentialGroup()
                        .addContainerGap()
                        .addComponent(AnulujButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)))
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addGroup(layout.createSequentialGroup()
                        .addGap(50, 50, 50)
                        .addComponent(zmienionoImieLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 133, javax.swing.GroupLayout.PREFERRED_SIZE)
                        .addContainerGap(javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                    .addGroup(layout.createSequentialGroup()
                        .addGap(18, 18, 18)
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                            .addComponent(changeButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(imieField)
                            .addGroup(layout.createSequentialGroup()
                                .addComponent(leftLabel)
                                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                                .addComponent(rightLabel))
                            .addComponent(imieButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                            .addComponent(AddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                        .addGap(44, 44, 44))))
            .addGroup(layout.createSequentialGroup()
                .addGap(46, 46, 46)
                .addComponent(ttlLabel, javax.swing.GroupLayout.PREFERRED_SIZE, 249, javax.swing.GroupLayout.PREFERRED_SIZE)
                .addContainerGap(105, Short.MAX_VALUE))
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addComponent(StartButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addGap(44, 44, 44))
        );
        layout.setVerticalGroup(
            layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
            .addGroup(layout.createSequentialGroup()
                .addContainerGap()
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g1Button)
                    .addComponent(g1Label)
                    .addComponent(leftLabel)
                    .addComponent(rightLabel))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g2Button)
                    .addComponent(g2Label)
                    .addComponent(changeButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING)
                    .addComponent(zmienionoImieLabel, javax.swing.GroupLayout.Alignment.TRAILING)
                    .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                        .addComponent(g3Button)
                        .addComponent(g3Label)))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g4Button)
                    .addComponent(g4Label)
                    .addComponent(imieField, javax.swing.GroupLayout.PREFERRED_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.PREFERRED_SIZE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                    .addComponent(g5Button)
                    .addComponent(g5Label)
                    .addComponent(imieButton))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.LEADING, false)
                    .addGroup(layout.createSequentialGroup()
                        .addGroup(layout.createParallelGroup(javax.swing.GroupLayout.Alignment.BASELINE)
                            .addComponent(g6Button)
                            .addComponent(g6Label))
                        .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                        .addComponent(AnulujButton))
                    .addComponent(AddButton, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE))
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.RELATED)
                .addComponent(StartButton)
                .addPreferredGap(javax.swing.LayoutStyle.ComponentPlacement.UNRELATED)
                .addComponent(ttlLabel, javax.swing.GroupLayout.DEFAULT_SIZE, javax.swing.GroupLayout.DEFAULT_SIZE, Short.MAX_VALUE)
                .addContainerGap())
        );
    }// </editor-fold>//GEN-END:initComponents

    private void g1ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g1ButtonActionPerformed
       
        imieField.setText(g1Label.getText());
        activePlayer = 0;
        int former = stos.getLast();
        zmienionoImieLabel.setVisible(false);
        stos.add(activePlayer);
        przestawKlawisze();
        wyslijZapytanie(0, former);
// TODO add your handling code here:
    }//GEN-LAST:event_g1ButtonActionPerformed

    private void changeButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_changeButtonActionPerformed

        dostepnyKlawisz = 2;
        addKeyListener(this);
        this.setFocusable(true);

// TODO add your handling code here:
    }//GEN-LAST:event_changeButtonActionPerformed

    private void changeButtonKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_changeButtonKeyPressed
        try {

            System.out.println(evt.getKeyText(evt.getKeyCode()));
            if (dostepnyKlawisz > 0) {
                if (dostepnyKlawisz == 2) {
                    Klient.getGracze().get(activePlayer).setLewy(evt.getKeyCode());
                    leftLabel.setText(evt.getKeyText(Klient.getGracze().get(activePlayer).getLewy()));
                }
                if (dostepnyKlawisz == 1) {
                    Klient.getGracze().get(activePlayer).setPrawy(evt.getKeyCode());
                    rightLabel.setText(evt.getKeyText(Klient.getGracze().get(activePlayer).getPrawy()));
                }
                dostepnyKlawisz--;
            }

        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(changeButton, "Nie wybrałeś żadnego gracza!");
            dostepnyKlawisz = 0;
        }
        // TODO add your handling code here:
    }//GEN-LAST:event_changeButtonKeyPressed

    private void g2ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g2ButtonActionPerformed
        
        imieField.setText(g2Label.getText());
        activePlayer = 1;
        zmienionoImieLabel.setVisible(false);
        int former = stos.getLast();
        stos.add(activePlayer);
        przestawKlawisze();
        wyslijZapytanie(1, former);

// TODO add your handling code here:
    }//GEN-LAST:event_g2ButtonActionPerformed

    private void g3ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g3ButtonActionPerformed
        
        imieField.setText(g3Label.getText());
        activePlayer = 2;// TODO add your handling code here:
        zmienionoImieLabel.setVisible(false);
        int former = stos.getLast();
        stos.add(activePlayer);
        przestawKlawisze();
        wyslijZapytanie(2, former);
    }//GEN-LAST:event_g3ButtonActionPerformed

    private void g4ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g4ButtonActionPerformed
        //g4Label.setForeground(Color.yellow);
        imieField.setText(g4Label.getText());
        activePlayer = 3;// TODO add your handling code here:
        zmienionoImieLabel.setVisible(false);
        int former = stos.getLast();
        stos.add(activePlayer);
        przestawKlawisze();
        wyslijZapytanie(3, former);
    }//GEN-LAST:event_g4ButtonActionPerformed

    private void g5ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g5ButtonActionPerformed
        //g5Label.setForeground(Color.yellow);
        imieField.setText(g5Label.getText());
        activePlayer = 4;// TODO add your handling code here:
        zmienionoImieLabel.setVisible(false);
        int former = stos.getLast();
        stos.add(activePlayer);
        przestawKlawisze();
        wyslijZapytanie(4, former);
    }//GEN-LAST:event_g5ButtonActionPerformed

    private void g6ButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_g6ButtonActionPerformed
        // g6Label.setForeground(Color.yellow);
        imieField.setText(g6Label.getText());
        activePlayer = 5;// TODO add your handling code here:
        zmienionoImieLabel.setVisible(false);
        int former = stos.getLast();
        stos.add(activePlayer);
        przestawKlawisze();
        wyslijZapytanie(5, former);
    }//GEN-LAST:event_g6ButtonActionPerformed

    private void imieButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_imieButtonActionPerformed
        try {
            Klient.getGracze().get(activePlayer).setName(imieField.getText());

            zmianaImienia(imieField.getText(), activePlayer);
            imieField.setText("");
            zmienionoImieLabel.setVisible(true);
        } catch (IndexOutOfBoundsException ex) {
            JOptionPane.showMessageDialog(imieButton, "Nie wybrałeś żadnego gracza!");
            System.out.println(Klient.getGracze().size());
        }

    }//GEN-LAST:event_imieButtonActionPerformed

    private void imieFieldKeyPressed(java.awt.event.KeyEvent evt) {//GEN-FIRST:event_imieFieldKeyPressed
        if (evt.getKeyCode() == KeyEvent.VK_ENTER) {
            imieButton.doClick();
        }// TODO add your handling code here: 
    }//GEN-LAST:event_imieFieldKeyPressed

    private void AddButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_AddButtonActionPerformed
        Klient.getGracze().get(activePlayer).setMyPlayer(true);
        stos.add(-1);
        dolaczDoGry();        // TODO add your handling code here:
    }//GEN-LAST:event_AddButtonActionPerformed

    private void StartButtonActionPerformed(java.awt.event.ActionEvent evt) {//GEN-FIRST:event_StartButtonActionPerformed
        letsStart();
    }//GEN-LAST:event_StartButtonActionPerformed

    private void formFocusGained(java.awt.event.FocusEvent evt) {//GEN-FIRST:event_formFocusGained
    
    }//GEN-LAST:event_formFocusGained

    private void przestawKlawisze() {
        leftLabel.setText(KeyEvent.getKeyText(Klient.getGracze().get(activePlayer).getLewy()));
        rightLabel.setText(KeyEvent.getKeyText(Klient.getGracze().get(activePlayer).getPrawy()));
    }

    public void resetLabeli() {
        Color c = Color.RED;
        g1Label.setForeground(c);
        g2Label.setForeground(c);
        g3Label.setForeground(c);
        g4Label.setForeground(c);
        g5Label.setForeground(c);
        g6Label.setForeground(c);
    }

    public void zmienLabela(Long id, Color c) {
        switch (id.intValue()) {
            case 0:
                g1Label.setForeground(c);
                break;
            case 1:
                g2Label.setForeground(c);
                break;
            case 2:
                g3Label.setForeground(c);
                break;
            case 3:
                g4Label.setForeground(c);
                break;
            case 4:
                g5Label.setForeground(c);
                break;
            case 5:
                g6Label.setForeground(c);
                break;
        }
    }

    public void zmienLabela(Long id, String s) {

        switch (id.intValue()) {
            case 0:
                g1Label.setText(s);
                break;
            case 1:
                g2Label.setText(s);
                break;
            case 2:
                g3Label.setText(s);
                break;
            case 3:
                g4Label.setText(s);
                break;
            case 4:
                g5Label.setText(s);
                break;
            case 5:
                g6Label.setText(s);
                break;
        }
    }

    public void zmienTTL(Long time) {
        if (time.intValue() == 10) {
            ttlLabel.setVisible(false);
        } else {
            ttlLabel.setVisible(true);
            ttlLabel.setText("Pozostało czasu do rozpoczęcia: " + String.valueOf(time) + " sekund");
        }
    }

    private void letsStart(){
        JSONObject zapytanie = new JSONObject();
        zapytanie.put("action", "letstart");
        
        StringWriter out = new StringWriter();
        try {
            zapytanie.writeJSONString(out);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogowania.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jsonText = out.toString();

        cc.sendMessage(jsonText);
    }
    
    private void zmianaImienia(String s, int id) {
        JSONObject zapytanie = new JSONObject();
        zapytanie.put("action", "changename");
        zapytanie.put("id", id);
        zapytanie.put("name", s);

        StringWriter out = new StringWriter();
        try {
            zapytanie.writeJSONString(out);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogowania.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jsonText = out.toString();

        cc.sendMessage(jsonText);

    }

    private JSONObject wyslijZapytanie(int position, int formerPosition) {
        JSONObject zapytanie = new JSONObject();
        zapytanie.put("action", "register");
        zapytanie.put("id", position);
        zapytanie.put("formerid", formerPosition);

        StringWriter out = new StringWriter();
        try {
            zapytanie.writeJSONString(out);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogowania.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jsonText = out.toString();

        cc.sendMessage(jsonText);

        return zapytanie;
    }

    private void dolaczDoGry() {
        JSONObject zapytanie = new JSONObject();
        zapytanie.put("action", "readytoplay");
        zapytanie.put("id", activePlayer);

        StringWriter out = new StringWriter();
        try {
            zapytanie.writeJSONString(out);
        } catch (IOException ex) {
            Logger.getLogger(MenuLogowania.class.getName()).log(Level.SEVERE, null, ex);
        }
        String jsonText = out.toString();

        cc.sendMessage(jsonText);
   
    }
    // Variables declaration - do not modify//GEN-BEGIN:variables
    private javax.swing.JButton AddButton;
    private javax.swing.JButton AnulujButton;
    private javax.swing.JButton StartButton;
    private javax.swing.JButton changeButton;
    private javax.swing.JButton g1Button;
    private javax.swing.JLabel g1Label;
    private javax.swing.JButton g2Button;
    private javax.swing.JLabel g2Label;
    private javax.swing.JButton g3Button;
    private javax.swing.JLabel g3Label;
    private javax.swing.JButton g4Button;
    private javax.swing.JLabel g4Label;
    private javax.swing.JButton g5Button;
    private javax.swing.JLabel g5Label;
    private javax.swing.JButton g6Button;
    private javax.swing.JLabel g6Label;
    private javax.swing.JButton imieButton;
    private javax.swing.JTextField imieField;
    private javax.swing.JTextField jTextField1;
    private javax.swing.JLabel leftLabel;
    private javax.swing.JLabel rightLabel;
    private javax.swing.JLabel ttlLabel;
    private javax.swing.JLabel zmienionoImieLabel;
    // End of variables declaration//GEN-END:variables

    @Override
    public void keyTyped(KeyEvent e) {

    }
 
    @Override
    public void keyPressed(KeyEvent e) {
    }

    @Override
    public void keyReleased(KeyEvent e) {

    }

    /**
     * @param cc the cc to set
     */
    public void setCc(TCPClient cc) {
        this.cc = cc;
    }
}

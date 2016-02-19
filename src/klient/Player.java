/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.util.ArrayList;
import java.util.concurrent.ConcurrentSkipListMap;


/**
 *
 * @author Łukasz Królik
 */
public class Player {

    /**
     * @return the dane
     */
    public static ArrayList<PlayerToTab> getDane() {
        return dane;
    }

    /**
     * @param aDane the dane to set
     */
    public static void setDane(ArrayList<PlayerToTab> aDane) {
        dane = aDane;
    }
    private int id;
    private int x = 200;
    private int y = 200;
    private int oldx;
    private int oldy;
    private int kat = 55;
    private double krok = 4.0;
    private int decyzja = 0;
    private int lewy  = 37;
    private int prawy = 39;
    private String name;
    private Boolean modulo = false;
    private int idPoprzedniegoPola = -1;
    private boolean myPlayer = false;
    private static ConcurrentSkipListMap<Integer, PlayerToTab> punkty= new ConcurrentSkipListMap<>();
    private static ArrayList<PlayerToTab> dane = new ArrayList<>();
    //private Socket sock;

    public Player(int id) {
        this.id = id;
    }
    

    public int getX() {
        return x;
    }

    public int getY() {
        return y;
    }

    public void setX(int x, int maxX) {
        int newx = x % maxX;
        
        if (x >= maxX){
            this.x = newx;
            setModulo((Boolean) true);
        }
            
        else if ((x < maxX) && (x > 0))
            this.x = x;
        else{
            this.x = (maxX - newx);
            setModulo((Boolean) true);
        } 
            
    }

    public void setY(int y, int maxY) {
         int newy = y % maxY;
        
        if (y >= maxY){
            this.y = newy;
            setModulo((Boolean) true);
        }
            
        else if ((y < maxY) && (y > 0))
            this.y = y;
        else{
            this.y = (maxY - newy);
            setModulo((Boolean) true);
        } 
    }

    
    /**
     * @return the oldx
     */
    public int getOldx() {
        return oldx;
    }

    /**
     * @param oldx the oldx to set
     */
    public void setOldx(int oldx) {
        this.oldx = oldx;
    }

    /**
     * @return the oldy
     */
    public int getOldy() {
        return oldy;
    }

    /**
     * @param oldy the oldy to set
     */
    public void setOldy(int oldy) {
        this.oldy = oldy;
    }

    /**
     * @return the kat
     */
    public int getKat() {
        return kat;
    }

    /**
     * @param kat the kat to set
     */
    public void setKat(int kat) {
        int alfa = kat % 360;
        if (alfa < 0){
            this.kat = (360 + alfa);
        }
        else{
            this.kat = alfa;
        }
    }
    
        public void newPosition(int x, int y, int alfa){
            
        double alfaRadian = Math.toRadians((double) alfa);
        double sinAlfa = Math.sin(alfaRadian);
        double cosAlfa = Math.cos(alfaRadian);
        
            setOldx(x);
            setOldy(y);
        
        setX((int) (getKrok() * sinAlfa)+x, Klient.getMaxX());
        setY((int) (getKrok() * cosAlfa)+y, Klient.getMaxY());
        
    }

    /**
     * @return the krok
     */
    public double getKrok() {
        return krok;
    }

    /**
     * @param krok the krok to set
     */
    public void setKrok(double krok) {
        this.krok = krok;
    }

    /**
     * @return the decyzja
     */
    public int getDecyzja() {
        return decyzja;
    }

    /**
     * @param decyzja the decyzja to set
     */
    public void setDecyzja(int decyzja) {
        this.decyzja = decyzja;
    }

    /**
     * @return the modulo
     */
    public Boolean getModulo() {
        return modulo;
    }

    /**
     * @param modulo the modulo to set
     */
    public void setModulo(Boolean modulo) {
        this.modulo = modulo;
    }

    /**
     * @return the lewy
     */
    public int getLewy() {
        return lewy;
    }

    /**
     * @param lewy the lewy to set
     */
    public void setLewy(int lewy) {
        
        this.lewy = lewy;
    }

    /**
     * @return the prawy
     */
    public int getPrawy() {
        return prawy;
    }

    /**
     * @param prawy the prawy to set
     */
    public void setPrawy(int prawy) {
        
        this.prawy = prawy;
    }

    /**
     * @return the name
     */
    public String getName() {
        return name;
    }

    /**
     * @param name the name to set
     */
    public void setName(String name) {
        this.name = name;
    }

    /**
     * @return the idPoprzedniegoPola
     */
    public int getIdPoprzedniegoPola() {
        return idPoprzedniegoPola;
    }

    /**
     * @param idPoprzedniegoPola the idPoprzedniegoPola to set
     */
    public void setIdPoprzedniegoPola(int idPoprzedniegoPola) {
        this.idPoprzedniegoPola = idPoprzedniegoPola;
    }

    /**
     * @return the myPlayer
     */
    public boolean isMyPlayer() {
        return myPlayer;
    }

    /**
     * @param myPlayer the myPlayer to set
     */
    public void setMyPlayer(boolean myPlayer) {
        this.myPlayer = myPlayer;
    }

    /**
     * @return the id
     */
    public int getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(int id) {
        this.id = id;
    }

    /**
     * @return the punkty
     */
    public static ConcurrentSkipListMap<Integer, PlayerToTab> getPunkty() {
        return punkty;
    }

    /**
     * @param punkty the punkty to set
     */
    public static void setPunkty(ConcurrentSkipListMap<Integer, PlayerToTab> punkty) {
        Player.punkty = punkty;
    }

  
}

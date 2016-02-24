/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.util.ArrayList;

/**
 *
 * @author Łukasz Królik
 */
public class Player {
//Tylko gettery i settery
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
    private int x = 0;
    private int y = 0;
    private int oldx = 0;
    private int oldy = 0;
    private int decyzja = 0;
    private int lewy = 37;
    private int prawy = 39;
    private String name;
    private int idPoprzedniegoPola = -1;
    private boolean myPlayer = false;
    private boolean activePlayer = false;
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

    public void setX(int x) {
        this.x = x;
    }

    public void setY(int y) {
        this.y = y;
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
     * @return the activePlayer
     */
    public boolean isActivePlayer() {
        return activePlayer;
    }

    /**
     * @param activePlayer the activePlayer to set
     */
    public void setActivePlayer(boolean activePlayer) {
        this.activePlayer = activePlayer;
    }

}

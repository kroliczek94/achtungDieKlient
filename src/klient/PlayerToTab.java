/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package klient;

import java.util.ArrayList;

/**
 *
 * @author lukasz
 */
public class PlayerToTab {

    private String name;
    private Integer c;
    private Integer points;


    public PlayerToTab(String n, Integer col, Integer p) {
        this.name = n;
        this.c = col;
        this.points = p;
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
     * @return the c
     */
    public Integer getC() {
        return c;
    }

    /**
     * @param c the c to set
     */
    public void setC(Integer c) {
        this.c = c;
    }

    /**
     * @return the points
     */
    public Integer getPoints() {
        return points;
    }

    /**
     * @param points the points to set
     */
    public void setPoints(Integer points) {
        this.points = points;
    }

}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.gis.domain;

/**
 *
 * @author riccardo.iovenitti
 */
public class Accesso {
    private int quando;
    private int accessi;

    public Accesso() {
    }

    public Accesso(int quando, int accessi) {
        this.quando = quando;
        this.accessi = accessi;
    }

    public int getQuando() {
        return quando;
    }

    public void setQuando(int quando) {
        this.quando = quando;
    }

    public int getAccessi() {
        return accessi;
    }

    public void setAccessi(int accessi) {
        this.accessi = accessi;
    }        
}

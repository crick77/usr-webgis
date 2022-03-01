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
public class Centroide {
    private int id;
    private int codiceComune;
    private String comune;
    private Geometry[] centro;
    private int numElementi;

    public Centroide() {
    }

    public Centroide(int id, int codiceComune, String comune, Geometry[] centro, int numElementi) {
        this.id = id;
        this.codiceComune = codiceComune;
        this.comune = comune;
        this.centro = centro;
        this.numElementi = numElementi;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getCodiceComune() {
        return codiceComune;
    }

    public void setCodiceComune(int codiceComune) {
        this.codiceComune = codiceComune;
    }

    public String getComune() {
        return comune;
    }

    public void setComune(String comune) {
        this.comune = comune;
    }

    public Geometry[] getCentro() {
        return centro;
    }

    public void setCentro(Geometry[] centro) {
        this.centro = centro;
    }

    public int getNumElementi() {
        return numElementi;
    }

    public void setNumElementi(int numElementi) {
        this.numElementi = numElementi;
    }        
}

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
public class Poligono {
    private int id;
    private int idPratica;
    private int avanzamento;
    private Geometry[] geom;

    public Poligono(int id, int idPratica, int avanzamento, Geometry[] geom) {
        this.id = id;
        this.idPratica = idPratica;
        this.avanzamento = avanzamento;
        this.geom = geom;
    }

    public Poligono() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(int idPratica) {
        this.idPratica = idPratica;
    }

    public int getAvanzamento() {
        return avanzamento;
    }

    public void setAvanzamento(int avanzamento) {
        this.avanzamento = avanzamento;
    }

    public Geometry[] getGeom() {
        return geom;
    }

    public void setGeom(Geometry[] geom) {
        this.geom = geom;
    }        
}

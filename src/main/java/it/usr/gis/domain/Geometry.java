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
public class Geometry {
    private Coordinate[] coords;

    public Geometry() {
    }

    public Geometry(Coordinate[] coords) {
        this.coords = coords;
    }

    public Coordinate[] getCoords() {
        return coords;
    }

    public void setCoords(Coordinate[] coords) {
        this.coords = coords;
    }   
}

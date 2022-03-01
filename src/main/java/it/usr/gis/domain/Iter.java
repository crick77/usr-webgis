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
public class Iter {
    private String tipoAtto;
    private String provvedimento;
    private String dataEvento;
    private double importo;

    public Iter() {
    }

    public Iter(String tipoAtto, String provvedimento, String dataEvento, double importo) {
        this.tipoAtto = tipoAtto;
        this.provvedimento = provvedimento;
        this.dataEvento = dataEvento;
        this.importo = importo;
    }

    public String getTipoAtto() {
        return tipoAtto;
    }

    public void setTipoAtto(String tipoAtto) {
        this.tipoAtto = tipoAtto;
    }

    public String getProvvedimento() {
        return provvedimento;
    }

    public void setProvvedimento(String provvedimento) {
        this.provvedimento = provvedimento;
    }

    public String getDataEvento() {
        return dataEvento;
    }

    public void setDataEvento(String dataEvento) {
        this.dataEvento = dataEvento;
    }

    public double getImporto() {
        return importo;
    }

    public void setImporto(double importo) {
        this.importo = importo;
    }        
}

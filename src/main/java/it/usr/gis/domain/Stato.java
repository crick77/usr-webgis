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
public class Stato {
    private String servizi;
    private String iterCorrente;
    private String dataIterCorrente;

    public Stato() {
    }

    public Stato(String servizi, String iterCorrente, String dataIterCorrente) {
        this.servizi = servizi;
        this.iterCorrente = iterCorrente;
        this.dataIterCorrente = dataIterCorrente;
    }

    public String getServizi() {
        return servizi;
    }

    public void setServizi(String servizi) {
        this.servizi = servizi;
    }

    public String getIterCorrente() {
        return iterCorrente;
    }

    public void setIterCorrente(String iterCorrente) {
        this.iterCorrente = iterCorrente;
    }

    public String getDataIterCorrente() {
        return dataIterCorrente;
    }

    public void setDataIterCorrente(String dataIterCorrente) {
        this.dataIterCorrente = dataIterCorrente;
    }        
}

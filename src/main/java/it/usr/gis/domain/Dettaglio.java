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
public class Dettaglio {
    private int idPratica;
    private String nominativo;
    private String tecnico;
    private String provvedimento;
    private boolean ord100;
    private double importoRichiesto;
    private double importoLiquidato;
    private int numLiquidazioni;
    private double importoConcesso;
    private String ordinanza;
    private String usoPrevalente;

    public Dettaglio() {
    }
           
    public int getIdPratica() {
        return idPratica;
    }

    public void setIdPratica(int idPratica) {
        this.idPratica = idPratica;
    }

    public String getProvvedimento() {
        return provvedimento;
    }

    public void setProvvedimento(String provvedimento) {
        this.provvedimento = provvedimento;
    }
       
    public String getNominativo() {
        return nominativo;
    }

    public void setNominativo(String nominativo) {
        this.nominativo = nominativo;
    }

    public String getTecnico() {
        return tecnico;
    }

    public void setTecnico(String tecnico) {
        this.tecnico = tecnico;
    }        
      
    public boolean isOrd100() {
        return ord100;
    }

    public void setOrd100(boolean ord100) {
        this.ord100 = ord100;
    }        

    public double getImportoLiquidato() {
        return importoLiquidato;
    }

    public void setImportoLiquidato(double importoLiquidato) {
        this.importoLiquidato = importoLiquidato;
    }

    public int getNumLiquidazioni() {
        return numLiquidazioni;
    }

    public void setNumLiquidazioni(int numLiquidazioni) {
        this.numLiquidazioni = numLiquidazioni;
    }

    public double getImportoConcesso() {
        return importoConcesso;
    }

    public void setImportoConcesso(double importoConcesso) {
        this.importoConcesso = importoConcesso;
    }      

    public double getImportoRichiesto() {
        return importoRichiesto;
    }

    public void setImportoRichiesto(double importoRichiesto) {
        this.importoRichiesto = importoRichiesto;
    }        

    public String getOrdinanza() {
        return ordinanza;
    }

    public void setOrdinanza(String ordinanza) {
        this.ordinanza = ordinanza;
    }       

    public String getUsoPrevalente() {
        return usoPrevalente;
    }

    public void setUsoPrevalente(String usoPrevalente) {
        this.usoPrevalente = usoPrevalente;
    }        
}

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
public class ManifDettaglio {
    private int id;
    private int idPoligon;
    private String codiceManifestazione;
    private String dataRichiesta;

    public ManifDettaglio() {
    }

    public ManifDettaglio(int id, int idPoligon, String codiceManifestazione, String dataRichiesta) {
        this.id = id;
        this.idPoligon = idPoligon;
        this.codiceManifestazione = codiceManifestazione;
        this.dataRichiesta = dataRichiesta;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getIdPoligon() {
        return idPoligon;
    }

    public void setIdPoligon(int idPoligon) {
        this.idPoligon = idPoligon;
    }

    public String getCodiceManifestazione() {
        return codiceManifestazione;
    }

    public void setCodiceManifestazione(String codiceManifestazione) {
        this.codiceManifestazione = codiceManifestazione;
    }

    public String getDataRichiesta() {
        return dataRichiesta;
    }

    public void setDataRichiesta(String dataRichiesta) {
        this.dataRichiesta = dataRichiesta;
    }        
}

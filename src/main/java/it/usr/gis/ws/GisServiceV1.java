package it.usr.gis.ws;

import it.usr.gis.domain.Centroide;
import it.usr.gis.domain.Dettaglio;
import it.usr.gis.domain.Poligono;
import it.usr.gis.domain.Iter;
import it.usr.gis.domain.Stato;
import java.sql.Connection;
import java.util.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.StringJoiner;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

/**
 *
 * @author 
 */
@Path("v1")
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GisServiceV1 extends CommonService {
    @Resource(lookup = "jdbc/usrdecreti") 
    private DataSource dsDecreti;

    @GET
    @Path("polys/{upper}/{lower}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPoligoni(@PathParam("upper") String upper, @PathParam("lower") String lower) {        
        try {
            final String sql = "SELECT gp.id, gp.id_pratica, IF(gd.stato is null, -100, gd.stato) as avanzamento, ST_AsText(gp.geometria) as geometria FROM gis_privata AS gp LEFT JOIN gis_dettaglio gd on gp.id_pratica = gd.id_pratica WHERE MBRContains(GeomFromText(?), gp.geometria) AND gp.id_layer = 1 AND gp.abilitato = 1";
            List<Poligono> poligoni = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                String bBox = "LINESTRING("+upper.replace(",", " ")+", "+lower.replace(",", " ")+")";
                ps.setString(1, bBox);
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        int idPratica = rs.getInt(2);
                        int avanzamento = rs.getInt(3);
                        String geom = rs.getString(4);
                        
                        Poligono p = new Poligono(id, idPratica, avanzamento, geomToDomain(geom));
                        poligoni.add(p);
                    }
                }
            }
                                    
            return Response.ok(poligoni).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }
    }
    
    @GET
    @Path("manif/polys/{upper}/{lower}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getManifestazioniPoligoni(@PathParam("upper") String upper, @PathParam("lower") String lower) {        
        try {
            final String sql = "SELECT gp.id, gp.id_pratica, IF(gd.stato is null, -100, gd.stato) as avanzamento, ST_AsText(gp.geometria) as geometria FROM gis_privata AS gp LEFT JOIN gis_dettaglio gd on gp.id_pratica = gd.id_pratica WHERE MBRContains(GeomFromText(?), gp.geometria) AND gp.id_layer = 3 AND gp.abilitato = 1";
            List<Poligono> poligoni = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                String bBox = "LINESTRING("+upper.replace(",", " ")+", "+lower.replace(",", " ")+")";
                ps.setString(1, bBox);
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        int idPratica = rs.getInt(2);
                        int avanzamento = rs.getInt(3);
                        String geom = rs.getString(4);
                        
                        Poligono p = new Poligono(id, idPratica, avanzamento, geomToDomain(geom));
                        poligoni.add(p);
                    }
                }
            }
                                    
            return Response.ok(poligoni).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }
    }
    
    @GET
    @Path("polys2/{upper}/{lower}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPoligoni2(@PathParam("upper") String upper, @PathParam("lower") String lower) {        
        try {
            final String sql = "SELECT gp.id, gp.id_pratica, IF(gd.stato is null, -100, gd.stato) as avanzamento, ST_AsText(gp.geometria) as geometria FROM gis_privata AS gp LEFT JOIN gis_dettaglio gd on gp.id_pratica = gd.id_pratica WHERE ST_Contains(GeomFromText(?), gp.geometria) AND gp.id_layer = 1 AND gp.abilitato = 1";
            List<Poligono> poligoni = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                StringJoiner bBox = new StringJoiner(",", "POLYGON((", "))");
                String[] _upper = upper.split(",");
                String[] _lower = lower.split(",");
                bBox.add(_upper[0]+" "+_upper[1]).add(_lower[0]+" "+_upper[1]).add(_lower[0]+" "+_lower[1]).add(_upper[0]+" "+_lower[1]).add(_upper[0]+" "+_upper[1]);
                ps.setString(1, bBox.toString());
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        int idPratica = rs.getInt(2);
                        int avanzamento = rs.getInt(3);
                        String geom = rs.getString(4);
                        
                        Poligono p = new Poligono(id, idPratica, avanzamento, geomToDomain(geom));
                        poligoni.add(p);
                    }
                }
            }
                                    
            return Response.ok(poligoni).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }
    }
    
    @GET
    @Path("polys/{idPratica: [0-9]*}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getPoligoniByIdPratica(@PathParam("idPratica") int idPratica) {
        try {
            final String sql = "SELECT gp.id, IF(gd.stato is null, -100, gd.stato) as avanzamento, ST_AsText(gp.geometria) as geometria FROM gis_privata AS gp LEFT JOIN gis_dettaglio gd on gp.id_pratica = gd.id_pratica WHERE gp.id_pratica = ? AND gp.id_layer = 1";
            List<Poligono> poligoni = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPratica);
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        int avanzamento = rs.getInt(2);
                        String geom = rs.getString(3);
                        
                        Poligono p = new Poligono(id, idPratica, avanzamento, geomToDomain(geom));
                        poligoni.add(p);
                    }
                }
            }
                                    
            return Response.ok(poligoni).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }
    }
    
    @GET
    @Path("centroids/{upper}/{lower}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCentroidi(@PathParam("upper") String upper, @PathParam("lower") String lower) {        
        try {
            final String sql = "SELECT gce.id, gce.codice_com, gce.comune, ST_AsText(gce.centro) AS centro, gce.num_elementi FROM gis_centroidi_elementi gce WHERE MBRCONTAINS(GeomFromText(?), gce.centro) AND (gce.num_elementi > 0)";
            List<Centroide> centroidi = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                double ux = Double.parseDouble(upper.split(",")[0]);
                double uy = Double.parseDouble(upper.split(",")[1]);
                double lx = Double.parseDouble(lower.split(",")[0]);
                double ly = Double.parseDouble(lower.split(",")[1]);
                String bBox = "POLYGON(("+ux+" "+uy+", "+lx+" "+uy+", "+lx+" "+ly+", "+ux+" "+ly+", "+ux+" "+uy+"))";                      
                ps.setString(1, bBox);
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        int codiceCom = rs.getInt(2);
                        String comune = rs.getString(3);                        
                        String centro = rs.getString(4);
                        int numElementi = rs.getInt(5); 
                        
                        Centroide c = new Centroide(id, codiceCom, comune, geomToDomain(centro), numElementi);
                        centroidi.add(c);
                    }
                }
            }
                                    
            return Response.ok(centroidi).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();            
        }
    }
    
    @GET
    @Path("centroids2/{upper}/{lower}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getCentroidi2(@PathParam("upper") String upper, @PathParam("lower") String lower) {        
        try {
            final String sql = "SELECT gce.id, gce.codice_com, gce.comune, ST_AsText(gce.centro) AS centro, gce.num_elementi FROM gis_centroidi_elementi gce WHERE ST_Contains(GeomFromText(?), gce.centro) AND (gce.num_elementi > 0)";
            List<Centroide> centroidi = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                double ux = Double.parseDouble(upper.split(",")[0]);
                double uy = Double.parseDouble(upper.split(",")[1]);
                double lx = Double.parseDouble(lower.split(",")[0]);
                double ly = Double.parseDouble(lower.split(",")[1]);
                String bBox = "POLYGON(("+ux+" "+uy+", "+lx+" "+uy+", "+lx+" "+ly+", "+ux+" "+ly+", "+ux+" "+uy+"))";                      
                ps.setString(1, bBox);
                try (ResultSet rs = ps.executeQuery()) {
                    while(rs.next()) {
                        int id = rs.getInt(1);
                        int codiceCom = rs.getInt(2);
                        String comune = rs.getString(3);                        
                        String centro = rs.getString(4);
                        int numElementi = rs.getInt(5); 
                        
                        Centroide c = new Centroide(id, codiceCom, comune, geomToDomain(centro), numElementi);
                        centroidi.add(c);
                    }
                }
            }
                                    
            return Response.ok(centroidi).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();            
        }
    }
    
    @GET
    @Path("detail/{idPratica: [0-9]*}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getDetail(@PathParam("idPratica") int idPratica) {
        String sql = "SELECT gd.destinatario, gd.contributo_richiesto, gd.contributo_concesso, gd.numero_liquidazioni, gd.contributo_liquidato, gd.ord100, gd.provvedimento_concessione, gd.ordinanza_riferimento, ltp.tipo_pratica_esteso FROM gis_dettaglio gd " +
                     "INNER JOIN l_tipo_pratica ltp ON gd.id_tipo_pratica = ltp.id_tipo_pratica WHERE id_pratica = ?";
        try (Connection con = dsDecreti.getConnection()) {
            Dettaglio d = null;
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPratica);
                try (ResultSet rs = ps.executeQuery()) {                
                    if(rs.next()) { 
                        int fNum = 1;
                        String destinatario = rs.getString(fNum++);
                        double contributoRichiesto = rs.getDouble(fNum++);
                        double contributoConcesso = rs.getDouble(fNum++);
                        int numLiquidazioni = rs.getInt(fNum++);
                        double contributoLiquidato = rs.getDouble(fNum++);
                        boolean ord100 = strToBool(rs.getString(fNum++));
                        String provvedimento = rs.getString(fNum++);
                        String ordinanza = rs.getString(fNum++);
                        String usoPrevalente = rs.getString(fNum++);

                        d = new Dettaglio();
                        d.setIdPratica(idPratica);
                        d.setProvvedimento(provvedimento);
                        d.setOrdinanza(ordinanza);
                        d.setImportoConcesso(contributoConcesso);
                        d.setImportoRichiesto(contributoRichiesto);
                        d.setImportoLiquidato(contributoLiquidato);
                        d.setNumLiquidazioni(numLiquidazioni);
                        d.setOrd100(ord100);                    
                        d.setNominativo(destinatario);
                        d.setUsoPrevalente(usoPrevalente);
                        d.setTecnico("-");                                                
                    }
                }
            }
                                    
            return (d!=null) ? Response.ok(d).build() : Response.status(Response.Status.NOT_FOUND).build();  
        }
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }                              
    }
    
    @GET
    @Path("iter/{idPratica: [0-9]*}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getIter(@PathParam("idPratica") int idPratica) {
        String sql = "SELECT valore FROM config WHERE chiave = ?";                
        try (Connection con = dsDecreti.getConnection()) {            
            String currentIter = null;
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, "iter_corrente");
                try (ResultSet rs = ps.executeQuery()) {                
                    if(rs.next()) { // unico??
                        currentIter = rs.getString("valore");
                    }
                }
            }
            
            if(currentIter==null) throw new SQLException("Errore iter_corrente non trovato!");
            
            sql = "select t.tipo_atto, t.provvedimento, DATE_FORMAT(t.data_evento, '%d-%m-%Y') as data_evento, t.importo, t.src from ((SELECT " +
                  "                    	l_tipo_passo.testo_passo AS tipo_atto, " +
                  "                    	CONCAT(IF(codice_passo=50, 'n. ', 'comunicazione n. '), it.protocollo) AS provvedimento, " +
                  "                    	it.data_evento, " +
                  "                    	-1 AS importo, " +
                  "                        'iter' as src " +
                  "                    FROM iter_"+currentIter+" AS it " +
                  "                    LEFT JOIN l_tipo_passo " +
                  "                    ON l_tipo_passo.codice_passo=it.codice_evento " +
                  "                    WHERE it.id_pratica = ? " +
                  "                   ) " +
                  "                     " +
                  "                    UNION " +
                  "                     " +
                  "                    (SELECT " +
                  "                    	l_tipo_decreto.tipo_decreto AS tipo_atto, " +
                  "                    	IF(decreti.numero_provvedimento>0, CONCAT(l_tipo_provvedimento.tipo_provvedimento, ' n. ', decreti.numero_provvedimento), l_tipo_provvedimento.tipo_provvedimento) AS provvedimento, " +
                  "                    	decreti.data_ora_provvedimento AS data_evento, " +
                  "                    	decreti.importo_contributo AS importo, " +
                  "                        'dec' as src " +
                  "                    FROM decreti " +
                  "                    LEFT JOIN l_tipo_provvedimento " +
                  "                    ON l_tipo_provvedimento.id_tipo_provvedimento=decreti.id_tipo_provvedimento " +
                  "                    LEFT JOIN l_tipo_decreto " +
                  "                    ON l_tipo_decreto.id_tipo_decreto=decreti.id_tipo_decreto " +
                  "                    WHERE decreti.da_rendicontare = 1 AND decreti.numero_provvedimento is not NULL  " +
                  "                    	AND decreti.id_tipo_provvedimento>0  " +
                  "                    	AND decreti.id_tipo_decreto IN (1, 2, 3, 5, 11, 15, 18, 19, 21)  " +
                  "                    	AND decreti.id_tipo_pratica IN (1,2,11) " +
                  "                    	AND decreti.ID_Pratica = ? " +
                  "                    )) as t " +
                  "ORDER BY t.data_evento ASC";
            List<Iter> lIter = new ArrayList<>();
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPratica);
                ps.setInt(2, idPratica);
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) { 
                        int fNum = 1;
                        String tipoAtto = rs.getString(fNum++);                        
                        String provvedimento = rs.getString(fNum++);
                        String dataEvento = rs.getString(fNum++);
                        double importo = rs.getDouble(fNum++);

                        Iter i = new Iter();
                        i.setTipoAtto(tipoAtto);
                        i.setProvvedimento(provvedimento);
                        i.setDataEvento(dataEvento);
                        i.setImporto(importo);
                        
                        lIter.add(i);
                    }
                }
            }
            return Response.ok(lIter).build();  
        }
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }                              
    }
    
    @GET    
    @Path("search/{idPratica: [0-9]*}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response searchPratica(@PathParam("idPratica") Integer idPratica) {
        try {                        
            final String sql = "SELECT DISTINCT gpi.id_pratica_poligono FROM gis_privata_interfaccia AS gpi WHERE gpi.id_pratica = ?";
            List<Integer> results = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPratica);
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) {
                        int idP = rs.getInt(1);

                        results.add(idP);
                    }
                }
            }

            return Response.ok(results).build();            
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }
    }
            
    @GET
    @Path("lastIter")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getLastIter() {
        try {                        
            final String sql = "SELECT valore FROM config WHERE chiave = 'iter_data'";            
            Date lastUpdate = null;
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {                
                    if(rs.next()) {
                        String _lastUpdate = rs.getString("valore");
                        _lastUpdate = _lastUpdate.split(" ")[0];
                        lastUpdate = stringToDate(_lastUpdate);
                    }
                }
            }
            
            return Response.ok(dateToString(lastUpdate)).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }
    }
            
    @GET
    @Path("status")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response status() {
        try {                        
            final String sql = "SELECT chiave, valore FROM config WHERE chiave in ('iter_corrente', 'iter_data')";
            Stato s = new Stato();
            s.setServizi("ok");
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) {
                        String k = rs.getString("chiave");
                        String v = rs.getString("valore");

                        switch(k) {
                            case "iter_corrente": {
                                s.setIterCorrente(v);
                                break;
                            }
                            case "iter_data": {
                                s.setDataIterCorrente(v);
                                break;
                            }
                        }                        
                    }
                }
            }
            
            return Response.ok(s).build();
        }   
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }
    }        
}

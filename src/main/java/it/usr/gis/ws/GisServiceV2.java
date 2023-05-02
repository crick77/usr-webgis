/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.gis.ws;

import it.usr.gis.domain.Centroide;
import it.usr.gis.domain.Dettaglio;
import it.usr.gis.domain.ManifDettaglio;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
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
 * @author riccardo.iovenitti
 */
@Path("v2")
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class GisServiceV2 extends CommonService {
    @Resource(lookup = "jdbc/usrdecreti") 
    private DataSource dsDecreti;

    @GET
    @Path("search/pratica/{idPratica: [0-9]*}")    
    @Produces({ MediaType.APPLICATION_JSON })
    public Response searchPratica(@PathParam("idPratica") Integer idPratica) {
        try {                        
            final String sql = "SELECT DISTINCT gp.id_pratica FROM gis_privata AS gp INNER JOIN gis_privata_interfaccia gpi on gp.id_pratica = gpi.id_pratica_poligono WHERE gpi.id_pratica = ? AND gp.id_layer = 1";
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
    @Path("search/comune/{comune}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response searchComune(@PathParam("comune") String srcComune) {
        try {   
            // meno di 3 caratteri la request non è valida
            if(srcComune.length()<3) {
                return Response.status(Response.Status.BAD_REQUEST).build();
            }
            
            srcComune = "%"+srcComune.toUpperCase()+"%";
            final String sql = "SELECT gc.comune, ST_AsText(gc.centro) AS centro FROM gis_centroidi gc WHERE upper(gc.comune) LIKE ? ORDER BY gc.comune";
            List<Centroide> results = new ArrayList<>();
            try (Connection con = dsDecreti.getConnection(); PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setString(1, srcComune);
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) {
                        String comune = rs.getString(1);
                        String centro = rs.getString(2);
                        
                        Centroide c = new Centroide();
                        c.setComune(comune);
                        c.setCentro(geomToDomain(centro));

                        results.add(c);
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
    @Path("detail/{idPratica: [0-9]*}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getDetail(@PathParam("idPratica") int idPratica) {
        String sql = "SELECT gpi.id_pratica, gd.destinatario, gd.contributo_richiesto, gd.contributo_concesso, gd.numero_liquidazioni, gd.contributo_liquidato, gd.ord100, gd.provvedimento_concessione, gd.ordinanza_riferimento, ltp.tipo_pratica_esteso, if(gpi.id_pratica-gpi.id_pratica_poligono=0, 0, 1) as ordine FROM gis_dettaglio gd " +
                     "INNER JOIN l_tipo_pratica ltp ON gd.id_tipo_pratica = ltp.id_tipo_pratica INNER JOIN gis_privata_interfaccia gpi ON gd.id_pratica = gpi.id_pratica WHERE gpi.id_pratica_poligono = ? ORDER BY ordine ASC";
        try (Connection con = dsDecreti.getConnection()) {
            Dettaglio d;
            List<Dettaglio> ld = new ArrayList<>();
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPratica);
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) { 
                        int fNum = 1;
                        int _idPratica = rs.getInt(fNum++);
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
                        d.setIdPratica(_idPratica);
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
                        
                        ld.add(d);
                    }
                }
            }
                                    
            return (ld.size()>0) ? Response.ok(ld).build() : Response.status(Response.Status.NOT_FOUND).build();  
        }
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }                              
    }
    
    @GET
    @Path("manif/detail/{idPoligon: [0-9]*}")
    @Produces({ MediaType.APPLICATION_JSON })
    public Response getManifestazioniDetail(@PathParam("idPoligon") int idPoligon) {
        String sql = "SELECT id, codice_manifestazione, data_manifestazione FROM gis_manifestaz_dettaglio WHERE id_poligon = ?";
        try (Connection con = dsDecreti.getConnection()) {            
            List<ManifDettaglio> lmd = new ArrayList<>();
            try(PreparedStatement ps = con.prepareStatement(sql)) {
                ps.setInt(1, idPoligon);
                ManifDettaglio md;
                try (ResultSet rs = ps.executeQuery()) {                
                    while(rs.next()) { 
                        md = new ManifDettaglio();
                        
                        md.setId(rs.getInt(1));
                        md.setIdPoligon(idPoligon);
                        md.setCodiceManifestazione(rs.getString(2));
                        md.setDataRichiesta(dateToString(rs.getDate(3)));
                        
                        lmd.add(md);
                    }
                }
            }
                                    
            return (lmd.size()>0) ? Response.ok(lmd).build() : Response.status(Response.Status.NOT_FOUND).build();  
        }
        catch(SQLException sqle) {
            return Response.serverError().entity(new Error(sqle)).build();
        }                              
    }
}

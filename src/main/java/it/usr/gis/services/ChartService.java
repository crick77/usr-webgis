/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.gis.services;

import it.usr.gis.domain.Accesso;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.annotation.Resource;
import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.sql.DataSource;

/**
 *
 * @author riccardo.iovenitti
 */
@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class ChartService {
    @Resource(lookup = "jdbc/usrdecreti") 
    private DataSource dsDecreti;
    
    public List<Integer> getAnni() {
        List<Integer> lAnni = new ArrayList<>();
        try(Connection c = dsDecreti.getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT DISTINCT YEAR(data_accesso) AS anno FROM gis_accessi ORDER BY anno DESC")) {
                try(ResultSet rs = ps.executeQuery()) {                    
                    while(rs.next()) {
                        lAnni.add(rs.getInt(1));
                    }
                }                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return lAnni;
    }
    
    public List<Integer> getMesiAnno(int anno) {
        List<Integer> lMesi = new ArrayList<>();
        try(Connection c = dsDecreti.getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT DISTINCT MONTH(data_accesso) AS mese FROM gis_accessi WHERE YEAR(data_accesso) = ? ORDER BY mese DESC")) {
                ps.setInt(1, anno);
                try(ResultSet rs = ps.executeQuery()) {                    
                    while(rs.next()) {
                        lMesi.add(rs.getInt(1));
                    }
                }                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return lMesi;
    }
    
    public List<Accesso> getAccessiAnno(int anno) {
        List<Accesso> lAccessi = new ArrayList<>();
        try(Connection c = dsDecreti.getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT MONTH(data_accesso) AS mese, SUM(numero_accessi) accessi_totali FROM gis_accessi WHERE YEAR(data_accesso) = ? GROUP BY MONTH(data_accesso) ORDER BY mese")) {
                ps.setInt(1, anno);
                try(ResultSet rs = ps.executeQuery()) {                    
                    while(rs.next()) {
                        Accesso a = new Accesso(rs.getInt(1), rs.getInt(2));
                        lAccessi.add(a);
                    }
                }                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return lAccessi;
    }
    
    public List<Accesso> getAccessiMeseAnno(int mese, int anno) {
        List<Accesso> lAccessi = new ArrayList<>();
        try(Connection c = dsDecreti.getConnection()) {
            try(PreparedStatement ps = c.prepareStatement("SELECT day(data_accesso) AS giorno, numero_accessi FROM gis_accessi WHERE MONTH(data_accesso) = ? AND YEAR(data_accesso) = ? ORDER BY giorno")) {
                ps.setInt(1, mese);
                ps.setInt(2, anno);
                try(ResultSet rs = ps.executeQuery()) {                    
                    while(rs.next()) {
                        Accesso a = new Accesso(rs.getInt(1), rs.getInt(2));
                        lAccessi.add(a);
                    }
                }                
            }
        } catch (SQLException ex) {
            Logger.getLogger(ChartService.class.getName()).log(Level.SEVERE, null, ex);
            return null;
        }
        
        return lAccessi;
    }    
}

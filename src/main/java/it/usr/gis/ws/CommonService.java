/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.gis.ws;

import it.usr.gis.domain.Coordinate;
import it.usr.gis.domain.Geometry;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author riccardo.iovenitti
 */
public abstract class CommonService {
    /**********************************************************************
     *
     *                        INTERNAL USE
     *
     **********************************************************************/
    
    protected Geometry[] geomToDomain(String geom) {
        final Geometry[] geomType = new Geometry[]{};
        final Coordinate[] coordType = new Coordinate[]{};
        
        if(geom.contains("POLYGON")) {
            geom = geom.replace("POLYGON(", "").replace("))", ")");
        }
        else {
            geom = geom.replace("POINT(", "").replace("))", ")");
        }
        List<Geometry> lGeom = new ArrayList<>();
         
        String[] pieces = geom.split("\\),");        
        for(String p : pieces) {       
            p = p.replace("(", "").replace(")", "");
            String[] coords = p.split(",");
            
            List<Coordinate> lCoord = new ArrayList<>();
            for(String c : coords) {
                String[] latLon = c.split(" ");
                Coordinate _c = new Coordinate(Double.parseDouble(latLon[0]), Double.parseDouble(latLon[1]));
                
                lCoord.add(_c);
            }
            
            Geometry g = new Geometry(lCoord.toArray(coordType));
            lGeom.add(g);
        }
        
        return lGeom.toArray(geomType);
    }
    
    protected boolean strToBool(String s) {
        return !"0".equals(s);
    }
    
    protected Date stringToDate(String s) {
        try {
            SimpleDateFormat inputFormat = new SimpleDateFormat("yyyy-MM-dd", Locale.ITALY);
            inputFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
            return inputFormat.parse(s);
        }
        catch(ParseException pe) {
            Logger.getLogger(GisServiceV1.class.getName()).log(Level.SEVERE, null, pe);
            return null;
        }
    }
    
    protected String dateToString(Date d) {
        return new SimpleDateFormat("dd-MM-yyyy").format(d);
    }
}

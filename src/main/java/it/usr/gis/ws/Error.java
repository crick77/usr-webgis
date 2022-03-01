/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package it.usr.gis.ws;

import java.io.PrintWriter;
import java.io.StringWriter;

/**
 *
 * @author riccardo.iovenitti
 */
public class Error {
    private String type;
    private String message;
    private String trace;
    private String customMessage;

    public Error() {
    }

    public Error(Throwable t) {
        if(t!=null) {
            type = t.getClass().getName();
            message = t.getMessage();
            StringWriter sw = new StringWriter();
            t.printStackTrace(new PrintWriter(sw));
            trace = sw.toString();
            customMessage = null;
        }
    }
    
    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getTrace() {
        return trace;
    }

    public void setTrace(String trace) {
        this.trace = trace;
    }

    public String getCustomMessage() {
        return customMessage;
    }

    public void setCustomMessage(String customMessage) {
        this.customMessage = customMessage;
    }       
}

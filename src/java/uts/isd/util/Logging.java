/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.util;

import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class has logging related utility functions.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-19
 */
public class Logging {
    
    private static Logger logger;
    
    private static Logger getLogger()
    {
        if (Logging.logger != null)
            return Logging.logger;
        //Figured out how to get caller from the stack
        //here:
        Throwable t = new Throwable();
        StackTraceElement methodCaller = t.getStackTrace()[1];
        Logger logger = Logger.getLogger(methodCaller.getClassName());
        logger.setLevel(Level.SEVERE);
        Logging.logger = logger;
        return Logging.logger;
    }
    
    public static void logMessage(String message, Exception e)
    {
        Logger log = Logging.getLogger();
        Date dt = new Date();
        String msg = dt.toString()+": "+message+"\n";
        msg += e.getMessage();
        log.log(Level.SEVERE, msg, e);
    }
    
    public static void logMessage(String message)
    {
        Logger log = Logging.getLogger();
        Date dt = new Date();
        String msg = dt.toString()+": "+message+"\n";
        log.log(Level.SEVERE, msg);
    }
    
}

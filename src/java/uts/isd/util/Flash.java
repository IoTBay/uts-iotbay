/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.util;   

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpSession;

/**
 *
 * @author rhys
 */
public class Flash {
    
    private static Flash instance;
    private final static String FLASH_SESSION_KEY = "_flashMessages";
    
    private HttpSession session;
    private List<FlashMessage> messages;
    
    public enum MessageType {
        Success,
        Warning,
        Error
    }
    
    public static Flash getInstance(HttpSession session)
    {
        //Check to make sure session is valid to avoid session already invalidated error.
        if (Flash.instance != null && Flash.instance.sessionMatches(session))
            return Flash.instance;
        
        Flash.instance = new Flash(session);
        return Flash.instance;
    }
    
    public Flash(HttpSession session)
    {
        this.session = session;
        if (this.session.getAttribute(Flash.FLASH_SESSION_KEY) == null)
        {
            this.messages = new ArrayList<FlashMessage>();
        }
        else
        {
            this.messages = (ArrayList<FlashMessage>)this.session.getAttribute(Flash.FLASH_SESSION_KEY);
        }
    }
    
    public boolean sessionMatches(HttpSession session)
    {
        return (this.session != null && this.session.equals(session));
    }
    
    public void add(MessageType type, String message)
    {
        FlashMessage m = new FlashMessage(type, message);
        this.messages.add(m);
        //Update session
        this.session.setAttribute(Flash.FLASH_SESSION_KEY, this.messages);
    }
    
    public String displayMessages()
    {
        String messages = "";
        List<Integer> deleteIndexes = new ArrayList<Integer>();
        
        for (int i = 0; i < this.messages.size(); i++)
        {
            FlashMessage message = this.messages.get(i);
            messages += message.outputMessage();
            deleteIndexes.add(i);
        }
        
        //Remove messages that have been displayed.
        for (int index: deleteIndexes)
        {
            this.messages.remove(index);
        }
        
        //Update session
        this.session.setAttribute(Flash.FLASH_SESSION_KEY, this.messages);
        
        return messages;
    }
    
    private class FlashMessage implements Serializable {
        
        private MessageType type;
        private String value;
        
        public FlashMessage(MessageType type, String value)
        {
            this.type = type;
            this.value = value;
        }
        
        public String outputMessage()
        {
            String out = "";
            switch (this.type)
            {
                case Success:
                    out += "<div class=\"alert alert-success\" role=\"alert\">\n";
                    break;
                    
                case Warning:
                    out += "<div class=\"alert alert-warning\" role=\"alert\">\n";
                    break;
                    
                case Error:
                    out += "<div class=\"alert alert-danger\" role=\"alert\">\n";
                    break;
            }
            out += "  "+ this.value +"\n";
            out += "</div>\n\n";
            
            return out;
        }
    }
}

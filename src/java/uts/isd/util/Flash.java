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
        if (Flash.instance != null)
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
            
            if (message.IsDisplayed())
            {
                deleteIndexes.add(i);
                continue;
            }
            
            messages += message.outputMessage();
            message.setDisplayed();
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
        private boolean isDisplayed;
        
        public FlashMessage(MessageType type, String value)
        {
            this.type = type;
            this.value = value;
            this.isDisplayed = false;
        }
        
        public boolean IsDisplayed()
        {
            return this.isDisplayed;
        }
        
        public void setDisplayed()
        {
            this.isDisplayed = true;
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
            
            this.isDisplayed = true;
            return out;
        }
    }
}

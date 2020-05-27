/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import javax.servlet.http.HttpServletRequest;

/**
 *
 * @author rhys
 */
public abstract class ValidationMethod {
    
    protected String name;
    protected String field;
    protected Object value;
        
    public abstract boolean validate(HttpServletRequest request);
    public abstract String getError();

    public String getName()
    {
        return this.name;
    }
    
    public String getField()
    {
        return this.field;
    }
    
    public Object getValue()
    {
        return this.value;
    }            
}

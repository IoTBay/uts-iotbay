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
    
    protected String value;
        
    public abstract boolean validate(String field, String value, HttpServletRequest request);
    public abstract String getError();
    
    public Object getValue()
    {
        return this.value;
    }            
}

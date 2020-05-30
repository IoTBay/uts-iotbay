/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import javax.servlet.http.HttpServletRequest;

/**
 * This class is used as the base class to allow implementation of 
 * different validation rule types.
 * 
 * The main logic is defined in the validate method. You may need to accept a parameter in the
 * class constructor in some circumstances.
 * 
 * You should also implement any new method in the ValidatorFieldRules parseRulesString.
 * 
 * @author Rhys Hanrahan 11000801
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
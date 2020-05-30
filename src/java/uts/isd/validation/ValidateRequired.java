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
public class ValidateRequired extends ValidationMethod {
    
    private int length;
    
    public ValidateRequired() { }
    
    @Override
    public boolean validate(String field, String value, HttpServletRequest request) 
    {
        return (value != null && !value.isEmpty());
    }

    @Override
    public String getError() {
        return "is required";
    }
    
}

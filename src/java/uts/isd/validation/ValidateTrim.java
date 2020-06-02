/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import javax.servlet.http.HttpServletRequest;

/**
 * Does not perform any validation, but trims whitespace from the field's value.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-29
 */
public class ValidateTrim extends ValidationMethod {
        
    public ValidateTrim()
    {
    }
    
    @Override
    public boolean validate(String field, String value, HttpServletRequest request) 
    {
        if (request == null || request.getParameter(field) == null)
            return true;
        
        String s = request.getParameter(field).trim();
        request.setAttribute(field, s);
        return true;
    }

    @Override
    public String getError() {
        return "";
    }
    
}

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
public class ValidateTrim extends ValidationMethod {
        
    public ValidateTrim()
    {
    }
    
    @Override
    public boolean validate(String field, String value, HttpServletRequest request) 
    {
        String s = request.getParameter(field).trim();
        request.setAttribute(field, s);
        return true;
    }

    @Override
    public String getError() {
        return "";
    }
    
}

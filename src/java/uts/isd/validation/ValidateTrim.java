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
        
    public ValidateTrim(String name, String field)
    {
        this.name = name;
        this.field = field;
    }
    
    @Override
    public boolean validate(HttpServletRequest request) 
    {
        this.value = request.getParameter(this.field);
        String s = (String)this.value;
        request.setAttribute(this.field, s.trim());
        return true;
    }

    @Override
    public String getError() {
        return "";
    }
    
}

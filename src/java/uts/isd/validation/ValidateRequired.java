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
    
    public ValidateRequired(String name, String field)
    {
        this.name = name;
        this.field = field;
    }
    
    @Override
    public boolean validate(HttpServletRequest request) 
    {
        this.value = request.getParameter(this.field);
        String s = (String)this.value;
        return (s != null && !s.isEmpty());
    }

    @Override
    public String getError() {
        return this.name+" is required";
    }
    
}

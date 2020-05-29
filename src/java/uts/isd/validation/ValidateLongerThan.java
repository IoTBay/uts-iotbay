/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class ValidateLongerThan extends ValidationMethod {
    
    private int length;
    private String name;
    private String field;
    private String value;
    
    public ValidateLongerThan(int length, String name, String field)
    {
        this.length = length;
        this.name = name;
        this.field = field;
    }
    
    @Override
    public boolean validate(HttpServletRequest request) 
    {
        this.value = request.getParameter(this.field);
        return (((String)this.value).length() > this.length);
    }

    @Override
    public String getError() {
        return this.name+" length must be grater than "+this.length;
    }
    
}
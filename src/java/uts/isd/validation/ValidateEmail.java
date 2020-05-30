/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

/**
 * Validates that a string is in the format of an email.
 * 
 * @author rhys
 */
public class ValidateEmail extends ValidationMethod {
    
    private final String pattern = "^(.+)@(.+)\\.(.+)$";

    public ValidateEmail() { }
    
    @Override
    public boolean validate(String field, String value, HttpServletRequest request) 
    {
        this.value = value;
        
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher((String)this.value);
        return m.matches();
    }

    @Override
    public String getError() {
        return "does not contain a valid email";
    }
    
}
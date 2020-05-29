/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;

public class ValidateEmail extends ValidationMethod {
    
    private final String pattern = "^(.+)@(.+)\\.(.+)$";

    public ValidateEmail(String name, String field)
    {
        this.name = name;
        this.field = field;
    }
    
    @Override
    public boolean validate(HttpServletRequest request) 
    {
        this.value = request.getParameter(this.field);
        
        Pattern r = Pattern.compile(pattern);
        Matcher m = r.matcher((String)this.value);
        return m.matches();
    }

    @Override
    public String getError() {
        return this.name+" does not contain a valid email";
    }
    
}
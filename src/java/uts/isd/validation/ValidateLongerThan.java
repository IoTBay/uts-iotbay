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
public class ValidateLongerThan extends ValidationMethod {
    
    private int length;
    
    public ValidateLongerThan(int length)
    {
        this.length = length;
    }
    
    @Override
    public boolean validate(String field, String value, HttpServletRequest request) 
    {
        return (value.length() > this.length);
    }

    @Override
    public String getError() {
        return "length must be grater than "+this.length;
    }
    
}
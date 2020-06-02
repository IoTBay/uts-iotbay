/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import javax.servlet.http.HttpServletRequest;

/**
 * Validates that a string is of a length longer than a certain length.
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-05-29
 */
public class ValidateShorterThan extends ValidationMethod {
    
    private int length;
    
    public ValidateShorterThan(int length)
    {
        this.length = length;
    }
    
    @Override
    public boolean validate(String field, String value, HttpServletRequest request) 
    {
        return (value.length() < this.length);
    }

    @Override
    public String getError() {
        return "length must be less than "+this.length;
    }
    
}
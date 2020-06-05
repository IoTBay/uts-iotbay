/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.text.DateFormat;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;

/**
 * Ensures the string input can be converted to integer
 * 
 * @author Rhys Hanrahan 11000801
 * @since 2020-06-03
 */
public class ValidateDate extends ValidationMethod {
        
    public ValidateDate()
    {
    }
    
    @Override
    public boolean validate(String field, String value, HttpServletRequest request) 
    {
        if (request == null || request.getParameter(field) == null)
            return false;
        
        try
        {
            Date testVal = DateFormat.getInstance().parse(request.getParameter(field));
        }
        catch (Exception e)
        {
            //If an exception is thrown then this wasn't a valid double
            return false;
        }
        return true;
    }

    @Override
    public String getError() {
        return "is not a valid date.";
    }
    
}

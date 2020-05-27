/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import uts.isd.util.Flash;

/**
 *
 * @author rhys
 */
public class Validator {
    
    private List<ValidationMethod> validationRules;
    private List<ValidationError> errors;
    
    public static final String SESSION_KEY = "_errors";
    
    public Validator() 
    {
        this.validationRules = new ArrayList<ValidationMethod>();
        this.errors = new ArrayList<ValidationError>();
    }
    
    /**
     * 
     * @param rules A list of validation rules to add to this validator.
     */
    public Validator(ValidationMethod[] rules) 
    {
        this.validationRules = new ArrayList<ValidationMethod>();
        this.errors = new ArrayList<ValidationError>();
        
        for (ValidationMethod rule : rules)
            this.validationRules.add(rule);
    }
    
    public void loadErrors(HttpSession session)
    {
        this.errors = (List<Validator.ValidationError>)session.getAttribute(Validator.SESSION_KEY);
        
        if (this.errors == null)
            this.errors = new ArrayList<ValidationError>();
    }
    
    public boolean validate(HttpServletRequest request)
    {
        Flash f = Flash.getInstance(request.getSession());
        boolean valid = true;
        for (ValidationMethod rule : this.validationRules)
        {
            if (!rule.validate(request))
            {
                valid &= false;
                ValidationError error = new ValidationError(rule);
                this.errors.add(error);
                //Add to flash?
                f.add(Flash.MessageType.Error, error.getError());
            }
            else
            {
                valid &= true;
            }
        }
        
        if (!valid)
        {
            request.getSession().setAttribute(Validator.SESSION_KEY, this.errors);
        }
        else
        {
            request.getSession().removeAttribute(Validator.SESSION_KEY);
        }
        
        return valid;
    }
    
    public String repopulate(String fieldName)
    {
        for (ValidationMethod rule : this.validationRules)
        {
            if (rule.getField().equals(fieldName))
                return rule.getValue().toString();
        }
        return "";
    }
        
    private class ValidationError implements Serializable
    {
        private String field;
        private Object value;
        private String error;
        
        public ValidationError(ValidationMethod rule)
        {
            this.field = rule.getField();
            this.value = rule.getValue();
            this.error = rule.getError();
        }
        
        public String getError()
        {
            return this.error;
        }
        
        public String getField()
        {
            return this.field;
        }
        
        public Object getValue()
        {
            return this.value;
        }
    }
}

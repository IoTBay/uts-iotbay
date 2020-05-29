/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import javax.servlet.http.HttpServletRequest;

/**
 * This class stores a collection of rules belonging to a particular field
 * 
 * @author Rhys Hanrahan - 11000801
 * @since 2020-05-29
 */
public class ValidatorFieldRules implements Serializable {
    
    private String field;
    private String value;
    private boolean valid;
    private ValidationError error;
    private List<ValidationMethod> rules;
    
    
    public ValidatorFieldRules(String field, String value)
    {
        this.field = field;
        this.value = value;
        this.valid = true;
        this.rules = new ArrayList<ValidationMethod>();
    }
    
    public ValidatorFieldRules(String field, String value, ValidationMethod[] rules)
    {
        this.field = field;
        this.value = value;
        this.valid = true;
        this.rules = new ArrayList<ValidationMethod>();
        
        for (ValidationMethod rule : rules)
            this.rules.add(rule);
    }
    
    public void addRule(ValidationMethod rule)
    {
        this.rules.add(rule);
    }
    
    public String getField()
    {
        return this.field;
    }
    
    public String getValue()
    {
        return this.value;
    }
    
    public ValidationError getError()
    {
        return this.error;
    }
    
    public boolean isValid()
    {
        return this.valid;
    }
    
    public boolean validate(HttpServletRequest request)
    {
        this.value = (String)request.getAttribute(this.field);
        
        for (ValidationMethod rule : this.rules)
        {
            if (!rule.validate(this.field, this.value, request))
            {
                this.valid = false;
                this.error = new ValidationError(rule, this);
                return false;
            }
        }
        this.valid = true;
        return true;
    }
}

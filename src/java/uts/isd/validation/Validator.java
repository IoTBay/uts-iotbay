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
    
    private List<ValidatorFieldRules> validatorFields;
    
    public static final String SESSION_KEY = "_validator_fields";
    
    public Validator() 
    {
        this.validatorFields = new ArrayList<ValidatorFieldRules>();
    }
    
    
    
    /**
     * 
     * @param rules A list of validation rules to add to this validator.
     */
    public Validator(ValidatorFieldRules[] fields) 
    {
        this.validatorFields = new ArrayList<ValidatorFieldRules>();
        
        for (ValidatorFieldRules field : fields)
            this.validatorFields.add(field);
    }
    
    public boolean validate(HttpServletRequest request)
    {
        Flash f = Flash.getInstance(request.getSession());
        boolean valid = true;
        for (ValidatorFieldRules field : this.validatorFields)
        {
            if (!field.validate(request))
            {
                valid &= false;
                //Add to flash?
                f.add(Flash.MessageType.Error, field.getError().getError());
            }
            else
            {
                valid &= true;
            }
        }
        
        if (!valid)
        {
            request.getSession().setAttribute(Validator.SESSION_KEY, this.validatorFields);
        }
        else
        {
            request.getSession().removeAttribute(Validator.SESSION_KEY);
        }
        
        return valid;
    }
    
    public String repopulate(String fieldName)
    {
        for (ValidatorFieldRules field : this.validatorFields)
        {
            if (field.getField().equals(fieldName))
                return field.getValue().toString();
        }
        return "";
    }
}

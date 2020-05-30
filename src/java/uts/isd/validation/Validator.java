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
    private HttpSession session;
    
    public static final String SESSION_KEY = "_validator_fields";
    
    public Validator(HttpSession session) 
    {
        this.session = session;
        this.loadFields();
    }
    
    /**
     * 
     * @param rules A list of validation rules to add to this validator.
     */
    public Validator(ValidatorFieldRules[] fields) 
    {
        this.session = session;
        this.validatorFields = new ArrayList<ValidatorFieldRules>();
        
        for (ValidatorFieldRules field : fields)
            this.validatorFields.add(field);
    }
    
    /**
     * Load the validation fields values
     * into array from the previous page request.
     * 
     * This allows form fields to be re-populated from the failed validation.
     */
    private void loadFields()
    {
        if (this.session == null)
            return;
        
        if (this.session.getAttribute(SESSION_KEY) == null)
            return;
        
        this.validatorFields = (List<ValidatorFieldRules>)this.session.getAttribute(SESSION_KEY);
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
        if (this.validatorFields == null)
            return "";
        
        for (ValidatorFieldRules field : this.validatorFields)
        {
            if (field.getField().equals(fieldName))
                return field.getValue();
        }
        return "";
    }
}

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

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
    
    /**
     * This function performs the validation of the form submission request, against all of the fields
     * and their corresponding validation rules. Use the output of this function to determine whether form submission
     * should proceed.
     * 
     * If it fails, do: response.sendRedirect(request.getHeader("referer"))
     * 
     * @param request The current HTTP Request used to add error messages to Flash, and save field values for repopulation.
     * @return TRUE if Validation was successful. Or FALSE if it fails.
     */
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
    
    /**
     * This function uses the validation fields that are currently loaded in the class, and can be used to
     * reload form field values from the previous failed validation attempt. 
     * 
     * For each form field you should add a HTML value attribute with the output of this function:
     * <input name="field" value="<%= v.repopulate("field") %>">
     * 
     * The field values are saved in the session from the previous "validate" call.
     * This function should be used in conjunction with the Validator(session) constructor
     * to load the saved form field values from the session.
     * 
     * @param fieldName The name of the HTML input field that we watch to fetch a value for. 
     * Matches the name provided for this field during the validation attempt.
     * 
     * @return String that contains the saved value from the last validation attempt.
     */
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

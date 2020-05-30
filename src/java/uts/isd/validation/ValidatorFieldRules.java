/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.io.Serializable;
import java.util.List;
import java.util.ArrayList;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.servlet.http.HttpServletRequest;
import uts.isd.util.Logging;

/**
 * This class stores a collection of rules belonging to a particular field
 * 
 * @author Rhys Hanrahan - 11000801
 * @since 2020-05-29
 */
public class ValidatorFieldRules implements Serializable {
    
    private String name;
    private String field;
    private String value;
    private boolean valid;
    private ValidationError error;
    private List<ValidationMethod> rules;
    
    
    public ValidatorFieldRules(String name, String field)
    {
        this.name = name;
        this.field = field;
        this.valid = true;
        this.rules = new ArrayList<ValidationMethod>();
    }
    
    public ValidatorFieldRules(String name, String field, ValidationMethod[] rules)
    {
        this.name = name;
        this.field = field;
        this.valid = true;
        this.rules = new ArrayList<ValidationMethod>();
        
        for (ValidationMethod rule : rules)
            this.rules.add(rule);
    }
    
    public ValidatorFieldRules(String name, String field, String rules)
    {
        this.name = name;
        this.field = field;
        this.valid = true;
        
        this.rules = parseRulesString(rules);
    }
    
    private List<ValidationMethod> parseRulesString(String ruleStr)
    {
        /**
         * Rules string examples:
         * email|required|greater[8]|lessthan[8]
         * email|required
         * required
         * greater[8]
         */
        List<ValidationMethod> rulesList = new ArrayList<ValidationMethod>();
        String[] rules = ruleStr.split("\\|");
        /*
        if (ruleStr.contains("|"))
            rules = ruleStr.split("\\|");
        else
            rules = new String[] { ruleStr };
        */
        //Use this to parse the individual rules 
        final String rulePattern = "^(.+?)(?:\\[(\\d+)\\])?";
        Pattern r = Pattern.compile(rulePattern);
        
        for (String rule : rules)
        {
            Matcher matches = r.matcher(rule);
            if (!matches.matches())
            {
                Logging.logMessage("Could not find match for rule string: "+rule);
                continue;
            }
            //First match is the rule name
            Logging.logMessage("Parsed new validation rule: "+matches.group(1));
            
            switch (matches.group(1))
            {
                case "required":
                    rulesList.add(new ValidateRequired());
                    break;
                    
                case "trim":
                    rulesList.add(new ValidateTrim());
                    break;
                    
                case "email":
                    rulesList.add(new ValidateEmail());
                    break;
                    
                case "longerthan":
                    Logging.logMessage("Matched rule 'longer' and extracted length: "+matches.group(2));
                    if (matches.groupCount() < 2)
                    {
                        Logging.logMessage("Could not extract length for longer than rule: "+rule);
                        continue;
                    }
                    int length = Integer.parseInt(matches.group(2));
                    rulesList.add(new ValidateLongerThan(length));
                    break;
            }
        }
        return rulesList;
    }
    
    public void addRule(ValidationMethod rule)
    {
        this.rules.add(rule);
    }
    
    public String getName()
    {
        return this.name;
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
        this.value = (String)request.getParameter(this.field);
        
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

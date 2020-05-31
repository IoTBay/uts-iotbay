/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package uts.isd.validation;

import java.io.Serializable;

/**
 * Each instance of this class contains all the info required related to a particular validation error.
 * 
 * @author Rhys Hanrahan - 11000801
 * @since 2020-05-29
 */
public class ValidationError implements Serializable
{
    private String field;
    private Object value;
    private String error;

    public ValidationError(ValidationMethod rule, ValidatorFieldRules fieldRules)
    {
        this.field = fieldRules.getField();
        this.value = fieldRules.getValue();
        this.error = fieldRules.getName() +" "+rule.getError();
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

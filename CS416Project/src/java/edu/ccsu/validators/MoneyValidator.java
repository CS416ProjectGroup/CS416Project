/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.validators;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author KK
 */
@FacesValidator(value="MoneyValidator")
public class MoneyValidator implements Validator{

    @Override
    public void validate(FacesContext context, UIComponent component, Object value) throws ValidatorException {
        //String bet = (String)value;
        double Dbet = (double)value;
        String bet = Double.toString(Dbet);
        //double Dbet = Double.parseDouble(bet);
        HtmlInputText htmlInputText = (HtmlInputText)component;
        if (Dbet<0){
            FacesMessage facesMessage =  new FacesMessage("You can't bet negative money!");
            throw new ValidatorException(facesMessage);
        }
        if (bet.matches("0-9")){
            FacesMessage facesMessage =  new FacesMessage("Bet must be a numeric value!");
            throw new ValidatorException(facesMessage);    
        }
    }
}

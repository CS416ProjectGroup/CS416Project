/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package edu.ccsu.validators;
import java.util.HashSet;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.component.UIComponent;
import javax.faces.component.html.HtmlInputText;
import javax.faces.context.FacesContext;
import javax.faces.validator.ValidatorException;


@ManagedBean
public class ValidationUtils {
    
    public void validateUserName(FacesContext context, UIComponent component, Object value) throws ValidatorException {

    }
}

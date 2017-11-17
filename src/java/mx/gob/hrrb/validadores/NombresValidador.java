/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

/**
 *
 * @author pedro
 */
@FacesValidator("mx.gob.hrrb.validadores.NombresValidador")
public class NombresValidador implements Validator{
  
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                    throws ValidatorException {
        
        String nombre = (String)arg2;
        boolean ban=false;
        FacesMessage msj  = new FacesMessage();
        String aux="";
        for(int i=0;i<nombre.length();i++){
            if(nombre.charAt(i)=='0' || nombre.charAt(i)=='1' || nombre.charAt(i)=='2' || nombre.charAt(i)=='3' || nombre.charAt(i)=='4'
                    || nombre.charAt(i)=='5' || nombre.charAt(i)=='6' || nombre.charAt(i)=='7' || nombre.charAt(i)=='8' || nombre.charAt(i)=='9'
                    || nombre.charAt(i)=='!'|| nombre.charAt(i)=='"'|| nombre.charAt(i)=='#'|| nombre.charAt(i)=='$'|| nombre.charAt(i)=='%'
                    || nombre.charAt(i)=='/'|| nombre.charAt(i)==')' || nombre.charAt(i)=='('|| nombre.charAt(i)=='='|| nombre.charAt(i)=='?'
                    || nombre.charAt(i)=='¿'|| nombre.charAt(i)=='*'|| nombre.charAt(i)=='+' || nombre.charAt(i)=='-' || nombre.charAt(i)==','
                    || nombre.charAt(i)==';'|| nombre.charAt(i)==':'|| nombre.charAt(i)=='_'|| nombre.charAt(i)=='}'|| nombre.charAt(i)=='{'
                    || nombre.charAt(i)=='|'|| nombre.charAt(i)=='@'|| nombre.charAt(i)=='>'|| nombre.charAt(i)=='<'|| (aux+nombre.charAt(i)).equals("'")){
                ban=true;
                break;
            }
    }
        if (ban){		       	
                    msj.setDetail("Se ingresó un caracter inválido");
                    msj.setSummary("Nombre, Apellido Paterno, Apellido Materno");
                    msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(msj);
            }	
}
}

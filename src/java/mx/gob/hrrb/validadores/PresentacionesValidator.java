/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.validadores;

import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.convert.Converter;
import javax.faces.convert.ConverterException;
import javax.faces.convert.FacesConverter;
import javax.faces.validator.ValidatorException;
import mx.gob.hrrb.modelo.core.Medicamento;


/**
 *
 * @author sam
 */
@FacesConverter("mx.gob.hrrb.validadores.PresentacionesValidator")
public class PresentacionesValidator implements Converter {        
    @Override
public Medicamento getAsObject(FacesContext arg0, UIComponent arg1, String arg2) {

    if (arg2 == null || arg2.isEmpty()) {return null;}
    try {    
        Medicamento m=new Medicamento(arg2);
         return m;  // here's where should be retreived the desired selected instance
    } catch (NumberFormatException e) {
        throw new ConverterException(new FacesMessage(("Presentacion NO valida")), e);
    }         
}

    @Override
    public String getAsString(FacesContext fc, UIComponent uic, Object o)throws ConverterException {
        if (o == null) {return null;}
    else{       
         return o.toString();  // here's where should be retreived the desired selected instance
    }
}
}
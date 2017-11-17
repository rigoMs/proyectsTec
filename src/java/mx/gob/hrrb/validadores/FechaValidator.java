/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.validadores;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("mx.gob.hrrb.validadores.FechaValidator")
public class FechaValidator implements Validator{
    boolean error=false;
//Valida que la fecha Final sea menor a la Fecha Inicial
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                    throws ValidatorException {
        
        if (arg2 == null) {
            return;
        }
        Object startDateValue = arg1.getAttributes().get("finicial");

        if (startDateValue==null) {
            return;
        }
        SimpleDateFormat df = new SimpleDateFormat("yyyy/MM/dd");
        Date startDate = (Date)startDateValue;
        Date endDate = (Date)arg2;
        String start=df.format(startDate);
        String end=df.format(endDate);
        String v1[]=start.split("/");

        if(v1[1].length()==1)
            v1[1]="0"+v1[1];
        if(v1[2].length()==1)
            v1[2]="0"+v1[2];
        start=v1[0]+"/"+v1[1]+"/"+v1[2];
        v1=end.split("/");
        end=v1[0]+"/"+v1[1]+"/"+v1[2];
        
        if (end.compareTo(start)<0) {
             FacesMessage message = new FacesMessage("La fecha Final no puede ser anterior a la fecha Inicial.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
   }
}
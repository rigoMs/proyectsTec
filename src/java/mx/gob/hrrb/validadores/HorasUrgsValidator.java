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

@FacesValidator("mx.gob.hrrb.validadores.HorasUrgsValidator")
public class HorasUrgsValidator implements Validator{
    private boolean error=false;

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                    throws ValidatorException {
            String sHora = (String)arg2;
            
            FacesMessage msj  = new FacesMessage();
            int h1, h2, h3, h4;

                h1=Integer.parseInt(sHora.substring(0, 2));
                h2=Integer.parseInt(sHora.substring(3, 5));
            
                if (sHora.length()>=15){
                    h3=Integer.parseInt(sHora.substring(10, 12));
                    h4=Integer.parseInt(sHora.substring(13, 15));
                }else{
                    h3=23;
                    h4=59;
                }
            
            if (h1>23){error=true;}
            if (h2>59){error=true;}
            if (h3>23){error=true;}
            if (h4>59){error=true;}
            if (h1>h3){error=true;}
            if (h1==h3 && h2>h4){error=true;}
            if (h1==0 && h2==0){error=true;}
            if (h3==0 && h4==0){error=true;}

            if (error==true){		       	
                    msj.setDetail("La hora no tiene el formato correcto");
                    msj.setSummary("Horas Incorrectas");
                    msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(msj);
            }		
    }
}
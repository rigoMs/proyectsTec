/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.validadores;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("mx.gob.hrrb.validadores.FechasNacValidator")
public class FechasNacValidator implements Validator{
    boolean error=false;
//Valida que la fecha sea mayor o igual al día actual, y que la fecha sea congruente
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                    throws ValidatorException {
            String sFecha = (String)arg2;
            String v[]=sFecha.split("/");
            FacesMessage msj  = new FacesMessage();
            int dia, mes, año;
            int diaAct, mesAct, añoAct;
            Calendar fecha = new GregorianCalendar();
            boolean bisiesto=false;
            
            dia=Integer.parseInt(v[0]);
            mes=Integer.parseInt(v[1]);
            año=Integer.parseInt(v[2]);
            diaAct=fecha.get(Calendar.DAY_OF_MONTH);
            mesAct=fecha.get(Calendar.MONTH);
            añoAct=fecha.get(Calendar.YEAR);

            if ((año % 4 == 0) && ((año % 100 != 0) || (año % 400 == 0)))
                bisiesto=true;

            if(bisiesto==true){
                if (mes>12){error=true;}
                switch(mes){
                    case 1: if(dia>31){error=true;} break;
                    case 2: if(dia>29){error=true;} break;
                    case 3: if(dia>31){error=true;} break;
                    case 4: if(dia>30){error=true;} break;
                    case 5: if(dia>31){error=true;} break;
                    case 6: if(dia>30){error=true;} break;
                    case 7: if(dia>31){error=true;} break;
                    case 8: if(dia>31){error=true;} break;
                    case 9: if(dia>30){error=true;} break;
                    case 10: if(dia>31){error=true;} break;
                    case 11: if(dia>30){error=true;} break;
                    case 12: if(dia>31){error=true;} break;
                }
            }else{
                    if (mes>12){error=true;}
                    switch(mes){
                    case 1: if(dia>31){error=true;} break;
                    case 2: if(dia>28){error=true;} break;
                    case 3: if(dia>31){error=true;} break;
                    case 4: if(dia>30){error=true;} break;
                    case 5: if(dia>31){error=true;} break;
                    case 6: if(dia>30){error=true;} break;
                    case 7: if(dia>31){error=true;} break;
                    case 8: if(dia>31){error=true;} break;
                    case 9: if(dia>30){error=true;} break;
                    case 10: if(dia>31){error=true;} break;
                    case 11: if(dia>30){error=true;} break;
                    case 12: if(dia>31){error=true;} break;
                }
            }
            if (año>añoAct){
                error=true;
            }
            if (año==añoAct){
                if((mes-1)>mesAct)
                    error=true;
                if ((mes-1)==mesAct){
                    if (dia>diaAct)
                        error=true;
                }
            }
            if (dia==0 || mes==0 || año==0)
                error=true;
            
            if (error==true){		       	
                    msj.setDetail("La fecha no tiene el formato correcto");
                    msj.setDetail("La fecha no tiene el formato correcto o es mayor a la fecha actual");
                    msj.setSummary("Fecha Incorrecta");
                    msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(msj);
            }		
    }
}
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

@FacesValidator("mx.gob.hrrb.validadores.vigenciaSegPopValidator")
public class vigenciaSegPopValidator implements Validator{
    boolean error=false;
//Valida que la vigencia sea mayor o igual al día actual
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                    throws ValidatorException {
            String sFecha = (String)arg2;
            FacesMessage msj  = new FacesMessage();
            int dia, mes, año;
            int diaAct, mesAct, añoAct;
            Calendar fecha = new GregorianCalendar();
            boolean bisiesto=false;
            
            if(sFecha.compareTo("00/00/0000")!=0){
            String v[]=sFecha.split("/");
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
            if (año<añoAct){
                error=true;
            }
            if (año==añoAct){
                if((mes-1)<mesAct)
                    error=true;
                if ((mes-1)==mesAct){
                    if (dia<diaAct)
                        error=true;
                }
            }
            }
            if (error==true){		       	
                    msj.setDetail("La Vigencia está vencida o la fecha es incorrecta");
                    msj.setSummary("Fecha Incorrecta");
                    msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(msj);
            }		
    }
}
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.validadores;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("mx.gob.hrrb.validadores.FechaNacValidator")
public class FechaNacValidator implements Validator{
    boolean error=false;
    private int año;
    private int mes;
    private int dia;
    
//Valida que la fecha Final sea menor a la Fecha Inicial
    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                    throws ValidatorException {
        
       
    Calendar fecha = new GregorianCalendar();
        año = fecha.get(Calendar.YEAR);
        mes = fecha.get(Calendar.MONTH);
        dia = fecha.get(Calendar.DAY_OF_MONTH);
        //System.out.println("Fecha Actual Validador: "+ dia + "/" + (mes+1) + "/" + año);    
        String fechaNaci=(String)arg2;
        SimpleDateFormat formatter = new SimpleDateFormat("dd-MM-yyyy");
	String dateInString = Integer.parseInt(fechaNaci.substring(0, 2))+"-"+Integer.parseInt(fechaNaci.substring(3, 5))+"-"+Integer.parseInt(fechaNaci.substring(6, 10));
        String fechaAct=dia+"-"+(mes+1)+"-"+año;
	Date date=null;
        Date date2=null;
        try {
 
		date = formatter.parse(dateInString);
		//System.out.println(date);
		//System.out.println(formatter.format(date));
                
                date2 = formatter.parse(fechaAct);
		//System.out.println(date2);
		//System.out.println(formatter.format(date2));
 
	} catch (ParseException e) {
		e.printStackTrace();
        }
        if(date.after(date2))
            error=true;
        if(date2.after(date)){
            error=false;
            System.out.println("en if 2");
        }
       if((Integer.parseInt(fechaNaci.substring(0, 2))==dia) &&(Integer.parseInt(fechaNaci.substring(3, 5))==(mes+1)) && (Integer.parseInt(fechaNaci.substring(6, 10))==año)){
           error=false; 
           //System.out.println("Fechas iguales");
       } 
        /*String fechaNaci=(String)arg2;
        
        System.out.println("Dia val: "+Integer.parseInt(fechaNaci.substring(0, 2)));
        System.out.println("Mes val: "+Integer.parseInt(fechaNaci.substring(3, 5)));
        System.out.println("Año val: "+Integer.parseInt(fechaNaci.substring(6, 10)));
        
        
        System.out.println("Fecha Nac: AÑO"+Integer.parseInt(fechaNaci.substring(6, 10))+" MES"+Integer.parseInt(fechaNaci.substring(3, 5))+" DIA"+Integer.parseInt(fechaNaci.substring(0, 2)));
        System.out.println("Fecha Actual: AÑO "+año+" MES "+(mes)+" DIA "+dia);
       
        if(Integer.parseInt(fechaNaci.substring(6, 10))>año)
            error=true;
        else
       if( Integer.parseInt(fechaNaci.substring(3, 5))>(mes+1))
            error=true;
       if(Integer.parseInt(fechaNaci.substring(0, 2))>dia)
           error=true; 
       else
       if((Integer.parseInt(fechaNaci.substring(0, 2))==dia) &&(Integer.parseInt(fechaNaci.substring(3, 5))==(mes+1)) && (Integer.parseInt(fechaNaci.substring(6, 10))==año)){
           error=false; 
           System.out.println("Fechas iguales");
       }
       */
        
        
        System.out.println("Bandera de validador: "+error);
        if (error==true) {
             FacesMessage message = new FacesMessage("Fecha de Nacimiento superior a la fecha actual.");
            message.setSeverity(FacesMessage.SEVERITY_ERROR);
            throw new ValidatorException(message);
        }
   }
}
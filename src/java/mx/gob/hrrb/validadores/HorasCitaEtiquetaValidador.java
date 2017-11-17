package mx.gob.hrrb.validadores;
import javax.faces.application.FacesMessage;
import javax.faces.component.UIComponent;
import javax.faces.context.FacesContext;
import javax.faces.validator.FacesValidator;
import javax.faces.validator.Validator;
import javax.faces.validator.ValidatorException;

@FacesValidator("mx.gob.hrrb.validadores.HorasCitaEtiquetaValidador")
public class HorasCitaEtiquetaValidador implements Validator{
   private boolean error=false;

    @Override
    public void validate(FacesContext arg0, UIComponent arg1, Object arg2)
                    throws ValidatorException {
            String sHora = (String)arg2;
            
            FacesMessage msj  = new FacesMessage();
            int h1;
            String min;

                h1=Integer.parseInt(sHora.substring(0, 2));
                min=sHora.substring(3, 5);
            
                if(h1>6 && h1<20){
                   if(min.equals("00") || min.equals("03") || min.equals("06") || min.equals("09")|| min.equals("12")|| min.equals("15")|| min.equals("18")|| min.equals("21")
                            || min.equals("24")|| min.equals("27")|| min.equals("30")|| min.equals("33")|| min.equals("36")|| min.equals("39")|| min.equals("42")|| min.equals("45")
                            || min.equals("48")|| min.equals("51")|| min.equals("54")|| min.equals("57"))
                       error=false;
                    else error=true;
                }
                else error=true;
               
        System.out.println(error);
            if (error==true){		       	
                    msj.setDetail("Los datos no son validos para el campo hora");
                    msj.setSummary("Error al introducir la hora");
                    msj.setSeverity(FacesMessage.SEVERITY_ERROR);
                  throw new ValidatorException(msj);
            }		
    }
}
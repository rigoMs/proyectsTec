package mx.gob.hrrb.jbs.core;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Programa;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Danny
 */
@ManagedBean(name = "oGenPacExp")
@ViewScoped
public class GenerarPacienteConExpJB {
    private Paciente oPaciente;
    private boolean visibleSeg1;
    private boolean visibleSeg2;
    private boolean visibleSeg3;
    private boolean visibleSeg4;
    private boolean visibleSeg5;
    private boolean visibleSeg6;
    private boolean visibleSeg7;
    private boolean visibleSeg8;
    private boolean visibleSegG;
    private boolean visibleSegP;
    private boolean vigencia1;
    private boolean vigencia2;
    private boolean vigencia3;
    private boolean vigencia4;
    private boolean vigencia5;
    private boolean vigencia6;
    private boolean vigencia7;
    private boolean vigencia8;
    private boolean vigenciaG;
    private boolean vigenciaP;
    /**
     * Creates a new instance of GenerarPacienteConExpJB
     */
    public GenerarPacienteConExpJB() {
     oPaciente=new Paciente();
     oPaciente.getExpediente().setInserta(true);
     visibleSeg1= visibleSeg2= visibleSeg3= visibleSeg4= visibleSeg5= visibleSeg6= visibleSeg7=visibleSeg8= visibleSegG=visibleSegP=true;
     vigencia1= vigencia2= vigencia3= vigencia4= vigencia5= vigencia6= vigencia7=vigencia8= vigenciaG=vigenciaP=false;
    }
    
    public String registrarPacienteConExpediente(){
        String pag="/faces/sesiones/Inicio.xhtml";
        String encontrado="", encontradoAux="";
        int afec=0;
        System.out.println("tiposeguro"+getPaciente().getSeg().getUnaDer().charAt(1));
        FacesMessage message=null;
        try{
            switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                case '0':encontrado="[]";
                        getPaciente().getSeg().setNumero("");
                        getPaciente().getSeg().setVigenciaTexto("");break;
                case '9':encontrado="[]";
                        getPaciente().getSeg().setNumero("");
                        getPaciente().getSeg().setVigenciaTexto("");break;
                case '1':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente()); 
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero()); 
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto());break;
                case '2':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero2(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente());
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero2());
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto2());break;
                case '3':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero3(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente()); 
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero3());
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto3());break;
                case '4':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero4(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente()); 
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero4()); 
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto4());break;
                case '5':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero5(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente());
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero5());
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto5());break;
                case '6':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero6(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente()); 
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero6()); 
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto6());break;
                case '7':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero7(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente()); 
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero7()); 
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto7());break;
                case '8':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumero8(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente()); 
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumero8()); 
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTexto8());break;
                case 'P':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumeroP(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente());
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumeroP()); 
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTextoP());break;
                case 'G':encontrado=getPaciente().getSeg().existeSeguro(getPaciente().getSeg().getNumeroG(),getPaciente().getSeg().getUnaDer(), getPaciente().getFolioPaciente()); 
                            getPaciente().getSeg().setNumero(getPaciente().getSeg().getNumeroG()); 
                            getPaciente().getSeg().setVigenciaTexto(getPaciente().getSeg().getVigenciaTextoG());break;
            }
            encontradoAux=encontrado.substring(1, (encontrado.length()-1));
            System.out.println("Existe Seguro: "+encontradoAux);
            if(getPaciente().getExpediente().getNumero()!=0){
                if (encontradoAux.compareTo("")==0){
                    afec=getPaciente().insertarPacienteConExpediente();
                    if(afec==0){
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente con Expediente", "Registro fallido!!!");
                        pag="GenerarPacienteConExpediente.xhtml";
                    }else{
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente con Expediente", "Registro exitoso!!!");
                    }
                }else{
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente con Expediente", "El número de seguro popular ya existe para el paciente: "+encontradoAux);
                    pag="GenerarPacienteConExpediente.xhtml";
                }
            }else{
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Paciente con Expediente", "Debe agregar un número de expediente adecuado");
                pag="GenerarPacienteConExpediente.xhtml";
            }
            RequestContext.getCurrentInstance().showMessageInDialog(message);
        }catch(Exception e){
            Logger.getLogger(GenerarPacienteConExpJB.class.getName()).log(Level.SEVERE, null, e);
        }
        return pag;
    }
    
    public List<Programa> getListaProgramas(){
        List<Programa> lLista = null;
       try {
           lLista = new ArrayList<Programa>(Arrays.asList(
                   (new Programa()).buscaListProgram()));
       } catch (Exception ex) {
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaEdoCivil(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion()).buscarEdosCiviles()));
       } catch (Exception ex) {
           Logger.getLogger(GenerarPacienteConExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaDerechobiente(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarDerechohabiencia()));
       } catch (Exception ex) {
           Logger.getLogger(GenerarPacienteConExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public boolean habilitaDir(){
        boolean p=true;
        if(getPaciente().getPais().compareToIgnoreCase("MÉXICO")==0 || getPaciente().getPais().compareToIgnoreCase("MEXICO")==0 )
            p=true;
        else{
            p=false;
        }
        return p;
    }
    
    public boolean habilitaPais(){
        boolean p=false;
        if (getPaciente().getPais().compareToIgnoreCase("MÉXICO")==0 || 
            getPaciente().getPais().compareToIgnoreCase("MEXICO")==0 )
            p=false;
        else{
            p=true;
        }
        return p;
    }
    
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(GenerarPacienteConExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList(
                   (new Municipio()).buscarMunicipio(getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(GenerarPacienteConExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Ciudad> getListaCiudades(){
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList(
                   (new Ciudad()).buscarCiudad(getPaciente().getEstado().getClaveEdo(), getPaciente().getMunicipio().getClaveMun())));
       } catch (Exception ex) {
           Logger.getLogger(GenerarPacienteConExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(getPaciente().getCiudad().getClaveCiu(),getPaciente().getMunicipio().getClaveMun(),getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(GenerarPacienteConExpJB.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lListaCP;
    }
    
    public void habilitaNumSeg(){
        String v="";
        visibleSeg1= visibleSeg2= visibleSeg3= visibleSeg4= visibleSeg5=visibleSeg6= visibleSeg7= visibleSeg8= visibleSegG= visibleSegP=true;
        vigencia1= vigencia2= vigencia3= vigencia4= vigencia5= vigencia6= vigencia7=vigencia8= vigenciaG=vigenciaP=false;
        v=getPaciente().getSeg().getUnaDer();
        switch (v){
            case "01 ":  visibleSeg1=false;vigencia1=true; break;
            case "02 ":  visibleSeg2=false;vigencia2=true; break;
            case "03 ":  visibleSeg3=false;vigencia3=true; break;
            case "04 ":  visibleSeg4=false;vigencia4=true; break;
            case "05 ":  visibleSeg5=false;vigencia5=true; break;
            case "06 ":  visibleSeg6=false;vigencia6=true; break;
            case "07 ":  visibleSeg7=false;vigencia7=true; break;
            case "08 ":  visibleSeg8=false;vigencia8=true; break;
            case "0G ":  visibleSegG=false;vigenciaG=true; break;
            case "0P ":  visibleSegP=false;vigenciaP=true; break;
            default:break;
        }
    }
    
    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public boolean getVisibleSeg1() {
        return visibleSeg1;
    }

    public boolean getVisibleSeg2() {
        return visibleSeg2;
    }

    public boolean getVisibleSeg3() {
        return visibleSeg3;
    }

    public boolean getVisibleSeg4() {
        return visibleSeg4;
    }

    public boolean getVisibleSeg5() {
        return visibleSeg5;
    }

    public boolean getVisibleSeg6() {
        return visibleSeg6;
    }

    public boolean getVisibleSeg7() {
        return visibleSeg7;
    }

    public boolean getVisibleSeg8() {
        return visibleSeg8;
    }

    public boolean getVisibleSegG() {
        return visibleSegG;
    }

    public boolean getVisibleSegP() {
        return visibleSegP;
    }

    public boolean getVigencia1() {
        return vigencia1;
    }

    public boolean getVigencia2() {
        return vigencia2;
    }

    public boolean getVigencia3() {
        return vigencia3;
    }

    public boolean getVigencia4() {
        return vigencia4;
    }

    public boolean getVigencia5() {
        return vigencia5;
    }

    public boolean getVigencia6() {
        return vigencia6;
    }

    public boolean getVigencia7() {
        return vigencia7;
    }

    public boolean getVigencia8() {
        return vigencia8;
    }

    public boolean getVigenciaG() {
        return vigenciaG;
    }

    public boolean getVigenciaP() {
        return vigenciaP;
    }
    
}

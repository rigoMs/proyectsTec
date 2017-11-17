package mx.gob.hrrb.jbs.consultaexterna;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.ActividadMedico;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Especialidad;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Puesto;
import mx.gob.hrrb.modelo.core.TipoMedico;
import mx.gob.hrrb.modelo.core.Turno;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Javi
 */
@ManagedBean(name = "oAddMed")
@SessionScoped
public class opcionesMed {
private Medico oMed;
private String sTipoMedico;
private String sActividad;
private Turno oTurno;
private Puesto oPuesto;
List<String> arrRet;
private FacesMessage message;
    /**
     * Creates a new instance of opcionesMed
     */
    public opcionesMed() {
        oMed=new Medico();
        sTipoMedico="";
        sActividad="";
        message=null;
        oTurno=new Turno();
        oPuesto=new Puesto();
    }
    
    public List<Turno> getListaTurno(){
        List<Turno> lLista = null;
       try {
           lLista = new ArrayList<Turno>(Arrays.asList((new Turno().buscarTurnosCE())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Puesto> getListaPuesto(){
        List<Puesto> lLista = null;
       try {
           lLista = new ArrayList<Puesto>(Arrays.asList((new Puesto().buscarPuestoCE())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Especialidad> getListaEspecialidad(){
        List<Especialidad> lLista = null;
       try {
           lLista = new ArrayList<Especialidad>(Arrays.asList(
                   (new Especialidad().buscarTodos())));
           lLista.remove(0);
           lLista.remove(14);
           lLista.remove(41);
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaTipoPersonal(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Medico().getTipoPersonal().buscarTipoPersonal())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaTipoContrato(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Medico().getTipoContrato().buscarTipoContrato())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Parametrizacion> getListaEdoCiviles(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Medico().getEstadoCivil().buscarEdosCiviles())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<AreaServicioHRRB> getListaServicios(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList((new Medico().getArea().buscarAreasCE())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    //*******************************************************************      
        public String sp_ascii(String input) {
    // Cadena de caracteres original a sustituir.
    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    // Cadena de caracteres ASCII que reemplazarán los originales.
    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    String output = input;
    for (int i=0; i<original.length(); i++) {
        // Reemplazamos los caracteres especiales.
        output = output.replace(original.charAt(i), ascii.charAt(i));
    }//for i
    return output;
}
   //*******************************************************************    

    public String getTipoMedico() {
        return sTipoMedico;
    }

    public void setTipoMedico(String sTipoMedico) {
        this.sTipoMedico = sTipoMedico;
    }
    
    public List<String> completar(String sTxt){
    arrRet = new ArrayList<String>();
    List<TipoMedico> lis= getListaTipoMedico(sTxt);
    for (TipoMedico li : lis) {
        if (sp_ascii(li.getDescripcion()).contains(sTxt)) {
            arrRet.add(li.getDescripcion());
        }
    }
    return arrRet;
    }
    
    public List<String> completar2(String sTxt){
    arrRet = new ArrayList<String>();
    List<ActividadMedico> lis= getListaActividad(sTxt);
    for (ActividadMedico li : lis) {
        if (sp_ascii(li.getDescripcion()).contains(sTxt)) {
            arrRet.add(li.getDescripcion());
        }
    }
    return arrRet;
    }
    
    public List<TipoMedico> getListaTipoMedico(String txt){
        List<TipoMedico> lLista = null;
       try {
           lLista = new ArrayList<TipoMedico>(Arrays.asList(
                   (new TipoMedico().buscarTipoMed(txt))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }

    public String getActividad() {
        return sActividad;
    }

    public void setActividad(String sActividad) {
        this.sActividad = sActividad;
    }
    
    public List<ActividadMedico> getListaActividad(String txt){
        List<ActividadMedico> lLista = null;
       try {
           lLista = new ArrayList<ActividadMedico>(Arrays.asList(
                   (new ActividadMedico().buscarActividad(txt))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public String ingresaMedico() throws Exception{
        String pag="AgregarMedico";
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Medico", "Registro Fallido :(");
        int ins=oMed.insertarMedico(getActividad(), getTipoMedico(), getTurno().getClave(), getPuesto().getClave());
        
        if (ins==1){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Medico", "Registro exitoso! :)");
            oMed.setNoTarjeta(0);
            oMed.setCurp("");
            oMed.setApPaterno("");
            oMed.setApMaterno("");
            oMed.setNombres("");
            oMed.setSexoP("M");
            oMed.setFechaNacTexto("00/00/0000");
            oMed.setTelefono("");
            oMed.setEspecialidad(new Especialidad());
            oMed.getTipoPersonal().setTipoParametro("T3401");
            oMed.getTipoContrato().setTipoParametro("T3300");
            oMed.getEstadoCivil().setTipoParametro("T3501");
            oMed.setTipoMedico(new TipoMedico());
            oMed.setArea(new AreaServicioHRRB());
            oMed.setActividadMedico(new ActividadMedico());
            oMed.setActividad("");
            oMed.setCedProf("");
            oMed.setCedEsp("");
            oMed.setCedIMSS("");
            setTipoMedico("");
            setActividad("");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }
    
    public Medico getMed() {
        return oMed;
    }

    public void setMed(Medico oMed) {
        this.oMed = oMed;
    }
    
    public Turno getTurno(){
        return oTurno;
    }
    
    public void setTurno(Turno oTurno){
        this.oTurno=oTurno;
    }
    
    public Puesto getPuesto(){
        return oPuesto;
    }
    
    public void setPuesto(Puesto oPuesto){
        this.oPuesto=oPuesto;
    }
}

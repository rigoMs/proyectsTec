/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.hospitalizacion;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import org.primefaces.context.RequestContext;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oEstancia")
@SessionScoped
public class DatosEstancia {
    private boolean bLeido;
    private DiagnosticoCIE10 oCIE10;
    private ProcedimientoCIE9 oCIE9;
    List<String> arrRet;
    List<DiagnosticoCIE10>lListaDiagcve;
    List<ProcedimientoCIE9>lListaProcecve;
    ArrayList <DiagnosticoCIE10>oDiagnostico;
    private EpisodioMedico oEpiMed;
    private Hospitalizacion oHosp;    
    private String sOpe="";
    private long nFolioPac=0;
    private long nEpisodio=0;
    private long nNumHospitalizacion=0;
    private int nNumeroExpediente=0;
    private boolean bBuscado = false;
    private boolean bBloqueado;
    private boolean bActivar;
    /**
     * Creates a new instance of DatosEstancia
     */
    public DatosEstancia() {
        arrRet = null;
        lListaDiagcve = null;
        lListaProcecve = null;
        oCIE10 = new DiagnosticoCIE10();
        oCIE9 = new ProcedimientoCIE9();
        oEpiMed = new EpisodioMedico();
        oHosp = new Hospitalizacion();
    }

    public boolean habilita(int num){
        boolean habilitado=true;
        if(num==0)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag().equals("") && num==1)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag().equals("") && num==2)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag().equals("") && num==3)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag().equals("") && num==4)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag().equals("") && num==5)
             habilitado=false;
        else if(oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag()!=null && !oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag().equals("") && num==6)
             habilitado=false; 
        else if(num==7 && bLeido==false)
             habilitado=false;
        else if((oHosp.getEpisodioMedico().getProceRe1()==null || 
                oHosp.getEpisodioMedico().getProceRe1().getCIE9()==null || 
                oHosp.getEpisodioMedico().getProceRe1().getCIE9().getDescripcion()==null) && num==8 && bLeido==false)
             habilitado=false;
        else if((oHosp.getEpisodioMedico().getProceRe2()==null || 
                oHosp.getEpisodioMedico().getProceRe2().getCIE9()==null || 
                oHosp.getEpisodioMedico().getProceRe2().getCIE9().getDescripcion()==null) && num==9 && bLeido==false)
             habilitado=false;
        else if((oHosp.getEpisodioMedico().getProceRe3()==null || 
                oHosp.getEpisodioMedico().getProceRe3().getCIE9()==null || 
                oHosp.getEpisodioMedico().getProceRe3().getCIE9().getDescripcion()==null) && num==10 && bLeido==false)
             habilitado=false;
        else if((oHosp.getEpisodioMedico().getProceRe4()==null || 
                oHosp.getEpisodioMedico().getProceRe4().getCIE9()==null || 
                oHosp.getEpisodioMedico().getProceRe4().getCIE9().getDescripcion()==null) && num==11 && bLeido==false)
             habilitado=false;
        else if((oHosp.getEpisodioMedico().getProceRe5()==null || 
                oHosp.getEpisodioMedico().getProceRe5().getCIE9()==null || 
                oHosp.getEpisodioMedico().getProceRe5().getCIE9().getDescripcion()==null) && num==12 && bLeido==false)
             habilitado=false;
        else if((oHosp.getEpisodioMedico().getProceRe6()==null || 
                oHosp.getEpisodioMedico().getProceRe6().getCIE9()==null || 
                oHosp.getEpisodioMedico().getProceRe6().getCIE9().getDescripcion()==null) && num==13 && bLeido==false)
             habilitado=false;
        else if((oHosp.getEpisodioMedico().getProceRe7()==null || 
                oHosp.getEpisodioMedico().getProceRe7().getCIE9()==null || 
                oHosp.getEpisodioMedico().getProceRe7().getCIE9().getDescripcion()==null) && num==14 && bLeido==false)
             habilitado=false;
 
        else if(oHosp.getEpisodioMedico().getMotivoEgresoP()!=null && (oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3804") || oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3803")) && num==19){
            //System.out.println("motivo egreso pase otro hospital");
            habilitado=false;
        }else if(oHosp.getProcedenciaP()!=null && oHosp.getProcedenciaP().equals("T0403") && num==20 && bBloqueado!=true)
             habilitado=false;
        else if(oHosp.getTipoEstancia()!=' ' && oHosp.getTipoEstancia()=='1' && num==15)
             habilitado=false;
        else if(oHosp.getTipoEstancia()!=' ' && oHosp.getTipoEstancia()=='2' && num==18)
             habilitado=false;
        else if(oHosp.getTipoEstancia()=='1' && oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion()!=null && !oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion().equals("") && num==16)
             habilitado=false;
        else if(oHosp.getTipoEstancia()=='1' && oHosp.getServicioSegundo().getAreaServicioSAEH().getDescripcion()!=null && !oHosp.getServicioSegundo().getAreaServicioSAEH().getDescripcion().equals("") && num==17)
             habilitado=false;
        else if(oHosp.getTipoEstancia()=='1' && num==18)
             habilitado=false;

            return habilitado;
  
    }        
    
    public boolean requerido(int num){
        boolean requerido=false;
        if(oHosp.getProcedenciaP()!=null && oHosp.getProcedenciaP().equals("T0403") && num==20)
            requerido=true;
        else if(oHosp.getEpisodioMedico().getMotivoEgresoP()!=null && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3804") && num==19)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe1()==null || 
                oHosp.getEpisodioMedico().getProceRe1().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe1().getCIE9().getDescripcion()==null)
                && num==1)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe2()==null || 
                oHosp.getEpisodioMedico().getProceRe2().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe2().getCIE9().getDescripcion()==null)
                && num==2)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe3()==null || 
                oHosp.getEpisodioMedico().getProceRe3().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe3().getCIE9().getDescripcion()==null)
                && num==3)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe4()==null || 
                oHosp.getEpisodioMedico().getProceRe4().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe4().getCIE9().getDescripcion()==null)
                && num==4)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe5()==null || 
                oHosp.getEpisodioMedico().getProceRe5().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe5().getCIE9().getDescripcion()==null)
                && num==5)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe6()==null || 
                oHosp.getEpisodioMedico().getProceRe6().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe6().getCIE9().getDescripcion()==null)
                 && num==6)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe7()==null || 
                oHosp.getEpisodioMedico().getProceRe7().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe7().getCIE9().getDescripcion()==null)
                 && num==7)
            requerido=true;
        else if((oHosp.getEpisodioMedico().getProceRe8()==null || 
                oHosp.getEpisodioMedico().getProceRe8().getCIE9()==null ||
                oHosp.getEpisodioMedico().getProceRe8().getCIE9().getDescripcion()==null)
                && num==8)
            requerido=true;
        return requerido;
    }
    
    public boolean botonCerrarCODE(){
        if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null &&
                (oHosp.getTipoEstancia()!='1' || oHosp.getTipoEstancia()!='2') && oHosp.getProcedenciaP()!=null &&
                oHosp.getEpisodioMedico().getMotivoEgresoP()!=null && oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag()!=null
                && (oHosp.getEpisodioMedico().getTipoAfePrinc()!='1' || oHosp.getEpisodioMedico().getTipoAfePrinc()!='2')&&
                (oHosp.getEpisodioMedico().getInfeccionIntrahospitalaria()!='S' || oHosp.getEpisodioMedico().getInfeccionIntrahospitalaria()!='N')){
            if(oHosp.getTipoEstancia()=='1' && oHosp.getServicioIngreso()!=null && oHosp.getServicioEgreso()!=null)
                return false;
            else if(oHosp.getTipoEstancia()=='2' && oHosp.getServicioEgreso()!=null)
                return false;
            else 
                return true;
        }
        else
            return true;
    }
    
    public boolean altaHosp(){
        boolean bloqueado=false;
        if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null)
            bloqueado=true;
        return bloqueado;
    }
    
    public boolean habilitaBoton(){
        System.out.println("Motivo egreso"+oHosp.getEpisodioMedico().getMotivoEgresoP()+"Hola");
        if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null||oHosp.getSalaLabor().getTiempoUso()!=0
                ||oHosp.getSalaExpulsion().getTiempoUso()!=0||oHosp.getSalaRecuperacion().getTiempoUso()!=0||
                oHosp.getTerapiaIntensiva().getTiempoUso()!=0||oHosp.getTerapiaIntermedia().getTiempoUso()!=0||
                oHosp.getEpisodioMedico().getMotivoEgresoP().compareTo("     ")!=0)
            return false;
        else 
            return true;
    }
    
    public boolean habilitaDef(){
        if(oHosp.getEpisodioMedico().getMotivoEgresoP()!=null && oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3805") && bLeido==false){
            return false;           
        }
        else{
            return true;
        }
    }       

    /*public boolean bloquearTE(){
        boolean deshabilitado=bBloqueado;
        DateFormat fecha=new SimpleDateFormat("yyyy-MM-dd");
        String fechaI="";
        String fechaA="";
        if(deshabilitado==false){
            if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null){
                fechaI=fecha.format(oHosp.getFechaIngresoHos());
                fechaA=fecha.format(oHosp.getEpisodioMedico().getAltaHospitalaria());
                if(fechaI.equals(fechaA)){
                    oHosp.setTipoEstancia('2');
                    deshabilitado=true;                   
                }else if(oHosp.getFechaIngresoHos().before(oHosp.getEpisodioMedico().getAltaHospitalaria())){
                    oHosp.setTipoEstancia('1');
                    deshabilitado=true;                    
                }
            }else if(oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion()!=null && !oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion().equals("")){
                oHosp.setTipoEstancia('1');
                deshabilitado=true;
            }            
        }else
            deshabilitado=true;
        return deshabilitado;
    }*/
    
    public Date minDateAlta(){
        //if(oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion()!=null && !oHosp.getServicioIngreso().getAreaServicioSAEH().getDescripcion().equals("")){
          if(oHosp.getTipoEstancia()=='1'){
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(oHosp.getFechaIngresoHos()); // Configuramos la fecha que se recibe
            calendar.add(Calendar.DAY_OF_YEAR, 1);  // numero de días a añadir, o restar en caso de días<0
            System.out.println("FECHA1: "+calendar.getTime());
            return calendar.getTime(); 
        }else{
            System.out.println("FECHA2: "+oHosp.getFechaIngresoHos());
            return oHosp.getFechaIngresoHos();            
        }
    }
    
    public void guardar(){
        System.out.println("**********************Guardando Datos Estancia****************************");
    FacesMessage message=null;
    int nAfec = 0;
        try{
            switch (this.sOpe) {
                case "a":
                    nAfec = this.oHosp.insertar();
                    break;
                case "m":
                    nAfec = this.oHosp.modificarInsertarEstancia();
                    setBuscado(false);
                    break;
            }
        }catch(Exception e){
            e.printStackTrace();
        }
        System.out.println("Update"+ nAfec);
            if (nAfec>=1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estancia", "Datos Guardados Correctamente");
            else
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Estancia", "Datos no Guardados :(");
            RequestContext.getCurrentInstance().showMessageInDialog(message);       
        //sOpe="";
    }     
    
        public Hospitalizacion getCode(){        
        if (!getBuscado()){
            setBuscado(true);
            if (this.getOpe().equals("a")){
                oHosp = new Hospitalizacion();

            }
            else{
                try{        
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(nFolioPac);
                    oHosp.getEpisodioMedico().setClaveEpisodio(nEpisodio); 
                    oHosp.getPaciente().getExpediente().setNumero(nNumeroExpediente);
                    oHosp.setNumIngresoHos(nNumHospitalizacion);
                    oHosp.buscarCodeEstancia();
                    oHosp.buscarCodeAreasApoyo();
                    oHosp.buscarCodeAreasServicio();
                    oHosp.buscarCodeAfecciones();
                    oHosp.buscarCodeProcedimientos();                   
                }catch(Exception e){
                    e.printStackTrace();
                    setBuscado(false);
                }
            }
        }
        return oHosp;
    }  
        
    /**
     * @return the oCIE10
     */
    public DiagnosticoCIE10 getCIE10() {
        return oCIE10;
    }

    /**
     * @param oCIE10 the oCIE10 to set
     */
    public void setCIE10(DiagnosticoCIE10 oCIE10) {
        this.oCIE10 = oCIE10;
    }

    /**
     * @return the oCIE9
     */
    public ProcedimientoCIE9 getCIE9() {
        return oCIE9;
    }

    /**
     * @param oCIE9 the oCIE9 to set
     */
    public void setCIE9(ProcedimientoCIE9 oCIE9) {
        this.oCIE9 = oCIE9;
    }

    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    /**
     * @return the oEpiMed
     */
    public EpisodioMedico getEpiMed() {
        return oEpiMed;
    }

    /**
     * @param oEpiMed the oEpiMed to set
     */
    public void setEpiMed(EpisodioMedico oEpiMed) {
        this.oEpiMed = oEpiMed;
    }

    /**
     * @return the sOpe
     */
    public String getOpe() {
        return sOpe;
    }

    /**
     * @param sOpe the sOpe to set
     */
    public void setOpe(String sOpe) {
        this.sOpe = sOpe;
    }

    /**
     * @return the nFolioPac
     */
    public long getFolioPac() {
        return nFolioPac;
    }

    /**
     * @param nFolioPac the nFolioPac to set
     */
    public void setFolioPac(long nFolioPac) {
        this.nFolioPac = nFolioPac;
    }

    /**
     * @return the nEpisodio
     */
    public long getEpisodio() {
        return nEpisodio;
    }

    /**
     * @param nEpisodio the nEpisodio to set
     */
    public void setEpisodio(long nEpisodio) {
        this.nEpisodio = nEpisodio;
    }

    /**
     * @return the bBuscado
     */
    public boolean getBuscado() {
        return bBuscado;
    }

    /**
     * @param bBuscado the bBuscado to set
     */
    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
    }

    /**
     * @return the nNumHospitalizacion
     */
    public long getNumHospitalizacion() {
        return nNumHospitalizacion;
    }

    /**
     * @param nNumHospitalizacion the nNumHospitalizacion to set
     */
    public void setNumHospitalizacion(long nNumHospitalizacion) {
        this.nNumHospitalizacion = nNumHospitalizacion;
    }

    /**
     * @return the nNumeroExpediente
     */
    public int getNumeroExpediente() {
        return nNumeroExpediente;
    }

    /**
     * @param nNumeroExpediente the nNumeroExpediente to set
     */
    public void setNumeroExpediente(int nNumeroExpediente) {
        this.nNumeroExpediente = nNumeroExpediente;
    }

    /**
     * @return the bBloqueado
     */
    public boolean getBloqueado() {
        return bBloqueado;
    }

    /**
     * @param bBloqueado the bBloqueado to set
     */
    public void setBloqueado(boolean bBloqueado) {
        this.bBloqueado = bBloqueado;
    }

    /**
     * @return the bLeido
     */
    public boolean getLeido() {
        return bLeido;
    }

    /**
     * @param bLeido the bLeido to set
     */
    public void setLeido(boolean bLeido) {
        this.bLeido = bLeido;
    }

    /**
     * @return the bActivar
     */
    public boolean getActivar() {
        return bActivar;
    }

    /**
     * @param bActivar the bActivar to set
     */
    public void setActivar(boolean bActivar) {
        if(bActivar ==true){
            guardar();
            bActivar=false;
        }
        this.bActivar = bActivar;
    }
        
}

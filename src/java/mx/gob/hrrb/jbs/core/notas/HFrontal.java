package mx.gob.hrrb.jbs.core.notas;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.notas.HojaFrontal;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.NotaEmbarazo;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;
import org.primefaces.context.RequestContext;
/**
 *
 * @author Alberto
 */
@ManagedBean(name="frontal")
@ViewScoped
public class HFrontal implements Serializable {
    private EpisodioMedico ep;
    private HojaFrontal hFrontal;
    private HojaFrontal[] arrFrontal;
    private DiagnosticoCIE10 oCie10;
    private DiagnosticoCIE10 oCie101;    
    private ArrayList<DiagnosticoCIE10> arrDiagCie10;
    private ArrayList<DiagnosticoCIE10> arrDiagCie101;
    private DiagnosticoCIE10 arrRet[];
    private Medico oMedFirm;    
    private boolean bShow;    
    private PersonalHospitalario oPersonal;    
    
    
    public HFrontal() throws Exception{
        HttpServletRequest req;
        oMedFirm = new Medico();
        oMedFirm.setUsuar(new Usuario());
        req = (HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm") != null){
            oMedFirm.getUsuar().setIdUsuario(((Firmado) req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
            oMedFirm.buscaUsuarioFirmado();
        }
        hFrontal = new HojaFrontal();
        arrDiagCie10 = new ArrayList<DiagnosticoCIE10>();
        oCie10 = new DiagnosticoCIE10();
        arrDiagCie101 = new ArrayList<DiagnosticoCIE10>();
        oCie101 = new DiagnosticoCIE10();
        bShow = false;
        oPersonal = new PersonalHospitalario();
    }
    public Medico getMedFirm(){
        return oMedFirm;
    }
    public void setMedFirm(Medico oMedFirm){
        this.oMedFirm = oMedFirm;
    }
    /*public void registroDiagnostico(){
        if(getCie10().getClave().isEmpty()){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","NO PUEDE AGREGAR VACÍO"));
        }else{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(nTam >= 6 || arrDiagCie10.size() >= 6 || nTam + arrDiagCie10.size() >= 6){
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", 
                        "IMPOSIBLE AGREGAR MAS DIAGNOSTICOS MAXIMO 6 DIAGNOSTICOS POR PACIENTE"));
                try{
                    getCie10().setDescripcionDiag("");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                if(arrDiagCie10.isEmpty() && buscaRepetidoDxAnterior(oCie10.getClave()) != true){
                    hFrontal.setClaveDiagnostico(oCie10.getClave());
                    arrDiagCie10.add(oCie10);                
                    oCie10 = new DiagnosticoCIE10();
                    try{
                        getCie10().setDescripcionDiag("");
                    }catch(Exception e){
                        e.printStackTrace();
                    }
                }else{
                    if(buscaRepetido(oCie10.getClave())){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                        try{
                        getCie10().setDescripcionDiag("");
                        }catch(Exception e){
                            e.printStackTrace();
                        }
                    }else{
                        if(buscaRepetidoDxAnterior(oCie10.getClave())){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                            try{
                                getCie10().setDescripcionDiag("");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }else{
                            arrDiagCie10.add(oCie10);
                            oCie10 = new DiagnosticoCIE10();
                            try{
                                getCie10().setDescripcionDiag("");
                            }catch(Exception e){
                                e.printStackTrace();
                            }
                        }                        
                    }
                }
            }
        }
    }*/
    public boolean buscaRepetido(String clave){
        boolean bandera = false;
        for(DiagnosticoCIE10 i: arrDiagCie10){
            if(i.getClave().equals(clave)){
                bandera = true;
                break;
            }
        }
        return bandera;
    }
    public boolean buscaRepetidoDxAnterior(String clave){
        if(arrRet == null)
            return false;
        for (DiagnosticoCIE10 arrRet1 : arrRet) {
            if (arrRet1.getClave().compareTo(clave) == 0) {
                return true;
            }
        }
        return false;
    }
    public HojaFrontal getHFrontal(){
        return hFrontal;
    }
    public void setHFrontal(HojaFrontal hFrontal){
        this.hFrontal=hFrontal;
    }    
    public EpisodioMedico getEpisodioMedico(){       
        return ep;
    }
    public void cargaDatosPacienteFrontal(long fp){
        try{
            ep = hFrontal.buscaPaciente(fp);
            oPersonal = hFrontal.informacionMedico();
            this.hFrontal.getEpisodioMedico().getPaciente().setFolioPaciente(this.ep.getPaciente().getFolioPaciente());
            this.hFrontal.getEpisodioMedico().getPaciente().setClaveEpisodio(this.ep.getPaciente().getClaveEpisodio());
            this.hFrontal.buscaDatosEgreso();
            this.hFrontal.buscaDatosHojaFrontal();
            bShow = ep != null;
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cargarPaciente(long fp){
        try{            
            ep = hFrontal.buscaPaciente(fp);
            if(ep == null){
                bShow = false;
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN,"ADVERTENCIA","EL PACIENTE NO TIENE UN NUMERO DE EPISODIO VIGENTE"));
            }else{
                oPersonal = hFrontal.informacionMedico();
                this.hFrontal.getEpisodioMedico().getPaciente().setFolioPaciente(this.ep.getPaciente().getFolioPaciente());
                this.hFrontal.getEpisodioMedico().getPaciente().setClaveEpisodio(this.ep.getPaciente().getClaveEpisodio());
                this.hFrontal.buscaDatosHojaFrontal();
                hFrontal.buscaDatosEgreso();
                bShow = true;
            }
        }catch(Exception e){
            Logger.getLogger(EpisodioMedico.class.getName()).log(Level.SEVERE,null,e);
        }
    }
    public DiagnosticoCIE10[] listaDiagnosticos(){
        try{
            NotaEmbarazo oConsulta = new NotaEmbarazo();
            return arrRet = oConsulta.datosPaciente(this.ep.getPaciente().getFolioPaciente(), this.ep.getPaciente().getClaveEpisodio());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }    
    public void guardaInformacion(){
        try{
            if(this.hFrontal.insertActualizaHojaFrontal(this.ep)){               
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
                FacesContext.getCurrentInstance().getExternalContext().redirect("frmHojaFrontal.xhtml");
            }
            else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }   
        }catch(Exception e){
            e.printStackTrace();
        }    
    }
    public void buscaHojaFrontal(short opcion){
        try{
            this.hFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().setOpcionUrg(opcion);
             arrFrontal = hFrontal.buscaHojaFrontal();            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public HojaFrontal[] getArrHojaFrontal(){
        return arrFrontal;
    }
    public boolean getConfirmaBandera(){
            return bShow;
    }
    public void setConfirmaBandera(boolean bShow){
        this.bShow = bShow;
    }
    public void setEpisodioMedico(EpisodioMedico ep){
        this.ep=ep;
    }
    public DiagnosticoCIE10 getCie10(){
        return oCie10;
    }
    public void setCie10(DiagnosticoCIE10 oCie10){
        this.oCie10=oCie10;
    }
    public DiagnosticoCIE10 getCie101(){
        return oCie101;
    }
    public void setCie101(DiagnosticoCIE10 oCie101){
        this.oCie101 = oCie101;
    }      
    public ArrayList<DiagnosticoCIE10> getDiagCie10(){
        return arrDiagCie10;
    }
     public ArrayList<DiagnosticoCIE10> getDiagCie101(){
        return arrDiagCie101;
    }
    public void setDiagCie10(ArrayList<DiagnosticoCIE10> arrDiagCie10){
        this.arrDiagCie10 = arrDiagCie10;
    }
    public void setDiagCie101(ArrayList<DiagnosticoCIE10> arrDiagCie101){
        this.arrDiagCie101 = arrDiagCie101;
    }
    public void borrarElementoCie10(DiagnosticoCIE10 obj){
        if(arrDiagCie10 != null || arrDiagCie10.isEmpty()){
            if(arrDiagCie10.get(0).getClave().compareTo(obj.getClave()) == 0){
                hFrontal.setClaveDiagnostico("");
                arrDiagCie10.remove(obj);
            }else{
                arrDiagCie10.remove(obj);
            }
        }
    }
    public void borrarElementoCie101(DiagnosticoCIE10 obj){
        arrDiagCie101.remove(obj);
    }
    public PersonalHospitalario getPersonalHospitalario(){
        return oPersonal;
    }
    public void setPersonalHospitalario(PersonalHospitalario oPersonal){
        this.oPersonal = oPersonal;
    }
    public DiagnosticoCIE10[] getArregloDiagnosticos(){
        return arrRet;
    }
    public void setArrgloDiagnosticos(DiagnosticoCIE10[] arrRet){
        this.arrRet = arrRet;
    }
    public boolean getBandera(){
        return !false;
    }
}
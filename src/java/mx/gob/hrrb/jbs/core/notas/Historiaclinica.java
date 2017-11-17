package mx.gob.hrrb.jbs.core.notas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.NotaEmbarazo;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.notas.HistoriaClinica;
import org.primefaces.context.RequestContext;
import org.primefaces.event.TabChangeEvent;
/**
 *
 * @author Alberto
 */
@ManagedBean(name="historiaclinica")
@ViewScoped
public class Historiaclinica implements Serializable{
    private Paciente oPaciente;
    private Paciente[] arrPaciente;
    private HistoriaClinica oHistoriaClinica;
    private boolean bMuestraPantalla;
    private PersonalHospitalario oPersonal;
    private Medico[] arrMedico;
    private DiagnosticoCIE10 oCie10;
    private DiagnosticoCIE10 arrRet[];
    private ArrayList<DiagnosticoCIE10> arrDiagCie10;
    private HistoriaClinica[] arrHistoria;
    private boolean bTab0;
    private boolean bTab1;
    private boolean bTab2;
    private boolean bTab3;
    public Historiaclinica() {
        oPaciente = new Paciente();
        oPersonal = new PersonalHospitalario();
        oCie10 = new DiagnosticoCIE10();
        arrDiagCie10 = new ArrayList<DiagnosticoCIE10>();
        this.oHistoriaClinica = new HistoriaClinica();
        this.bTab0 = true;
    }
//**************INICIAN METODOS DE CONTROL DE DATOS****************//
    public void requerir(TabChangeEvent event){
        if(event.getTab().getId().compareTo("hc00") == 0){
            this.bTab0 = true;
            this.bTab1 = false;
            this.bTab2 = false;
            this.bTab3 = false;
        }else if(event.getTab().getId().compareTo("hc01") == 0){
            this.bTab0 = false;
            this.bTab1 = true;
            this.bTab2 = false;
            this.bTab3 = false;
        }else if(event.getTab().getId().compareTo("hc02") == 0){
            this.bTab0 = false;
            this.bTab1 = false;
            this.bTab2 = true;
            this.bTab3 = false;
        }else if(event.getTab().getId().compareTo("hc03") == 0){
            this.bTab0 = false;
            this.bTab1 = false;
            this.bTab2 = false;
            this.bTab3 = true;
        }
    }
    public void cargaDatosPaciente(short opcion){
        try{
            this.oPaciente.setOpcionUrg(opcion);
            this.arrPaciente = this.oPaciente.buscarPacientesConExpediente();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cargaDatosGenerales(long foliopaciente, long clave, int exp, Date fechaNac){
        try{
            Date fechaActual = new Date();            
            if(((fechaActual.getTime() - fechaNac.getTime())/86400000)< 180){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ADVERTENCIA", "EL PACIENTE ES MENOR A 180 DE DÍAS DE NACIDO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
                this.setMuestraPantalla(false);
            }else{                
                this.oHistoriaClinica = new HistoriaClinica();                
                this.oHistoriaClinica.getEpisodioMedico().getPaciente().setFolioPaciente(foliopaciente);
                this.oHistoriaClinica.getEpisodioMedico().getPaciente().setClaveEpisodio(clave);
                this.oHistoriaClinica.getEpisodioMedico().getPaciente().getExpediente().setNumero(exp);
                this.oHistoriaClinica.buscaDatosPacienteHistoriaClinica();
                this.oHistoriaClinica.buscaDatosAnverso01();
                this.oHistoriaClinica.buscaDatosReverso01();
                this.oHistoriaClinica.buscaDatosAnverso02();
                this.oPersonal = this.oHistoriaClinica.informacionMedico();
                this.arrMedico = this.oHistoriaClinica.buscaMedicos((short) 0);
                this.oHistoriaClinica.buscaDatosReverso02();
                this.habilitaTalla();
                this.oCie10.setDescripcionDiag(this.oHistoriaClinica.getDiagnostico().getDescripcionDiag() == null ||
                        this.oHistoriaClinica.getDiagnostico().getDescripcionDiag().isEmpty() ? "" : this.oHistoriaClinica.getDiagnostico().getDescripcionDiag());
                this.oCie10.setClave(this.oHistoriaClinica.getDiagnostico().getClave() == null ||
                        this.oHistoriaClinica.getDiagnostico().getDescripcionDiag().isEmpty() ? "" : this.oHistoriaClinica.getDiagnostico().getClave());            
                this.setMuestraPantalla(true);
            }
        }catch(Exception w){
            w.printStackTrace();
        }        
    }
    public void guardAnverso1(){
        try{
            if(this.oHistoriaClinica.insertaAnverso01()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public void guardaReverso1(){
        try{
            if(this.oHistoriaClinica.insertaReverso01()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void guardaAnverso2(){
        try{
            if(this.oHistoriaClinica.insertaAnverso02()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }            
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void asignaCedula(){
        if(this.oHistoriaClinica.getEpisodioMedico().getMedicoTratante().getNombreCompleto().isEmpty())
            this.oHistoriaClinica.getEpisodioMedico().getMedicoTratante().setCedProf("SELECCIONE UN MEDICO");
        else{
            String cedulaProfesional = buscaMedico(this.oHistoriaClinica.getEpisodioMedico().getMedicoTratante().getNombres());
            int ntarjeta = buscaMedico(this.oHistoriaClinica);
            String cedula = cedulaProfesional.compareTo("") == 0 ? "NO DISPONIBLE" : cedulaProfesional;
            this.oHistoriaClinica.getEpisodioMedico().getMedicoTratante().setCedProf(cedula);
            int tarjeta = ntarjeta != 0 ? ntarjeta : 0;
            this.oHistoriaClinica.getEpisodioMedico().getMedicoTratante().setNoTarjeta(tarjeta);
        }
        //historiaclinica.historiaClinica.episodioMedico.medicoTratante.nombres
    }
    public String buscaMedico(String medico){
        for(int i = 0 ; i < arrMedico.length ; i++)
            if(arrMedico[i].getNombreCompleto().compareTo(medico) == 0)
                return arrMedico[i].getCedProf();
        return null;
    }
    public int buscaMedico(HistoriaClinica oHistoria){
        for(int i = 0 ; i < arrMedico.length ; i++)
            if(arrMedico[i].getNombreCompleto().compareTo(oHistoria.getEpisodioMedico().getMedicoTratante().getNombres()) == 0)
                return arrMedico[i].getNoTarjeta();
        return 0;
    }
    public DiagnosticoCIE10[] listaDiagnosticos(){
        try{
            NotaEmbarazo oConsulta = new NotaEmbarazo();
            return arrRet = oConsulta.datosPaciente(this.oHistoriaClinica.getEpisodioMedico().getPaciente().getFolioPaciente(), this.oHistoriaClinica.getEpisodioMedico().getPaciente().getClaveEpisodio());
        }catch(Exception e){
            e.printStackTrace();
        }
        return null;
    }
    public void registroDiagnostico(){
        if(getCie10().getClave().isEmpty()){
           FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","NO PUEDE AGREGAR VACÍO"));
        }else{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(nTam >= 6 || arrDiagCie10.size() >= 6 || nTam + arrDiagCie10.size() >= 6){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "IMPOSIBLE AGREGAR MAS DIAGNOSTICOS MAXIMO 6 DIAGNOSTICOS POR PACIENTE"));
                try{
                    getCie10().setDescripcionDiag("");
                }catch(Exception e){
                    e.printStackTrace();
                }
            }else{
                if(arrDiagCie10.isEmpty() && buscaRepetidoDxAnterior(oCie10.getClave()) != true){
                    this.oHistoriaClinica.getDiagnostico().setClave(oCie10.getClave());
                    arrDiagCie10.add(oCie10);                
                    oCie10 = new DiagnosticoCIE10();
                }else{
                    if(buscaRepetido(oCie10.getClave())){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                        
                    }else{
                        if(buscaRepetidoDxAnterior(oCie10.getClave())){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                            
                        }else{
                            arrDiagCie10.add(oCie10);
                            oCie10 = new DiagnosticoCIE10();
                        }                        
                    }
                }
            }
        }
    }
    public boolean buscaRepetidoDxAnterior(String clave){
        if(arrRet == null)
            return false;
        for(int i = 0; i < arrRet.length; i++)
            if(arrRet[i].getClave().compareTo(clave) == 0)
                return true;
        return false;
    }
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
    public void guardaReverso2(){
        try{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(this.oHistoriaClinica.insertaDatosReverso02(arrDiagCie10, nTam)){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "INFORMACION", "GUARDADO CON EXITO"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            }else{
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA", "TUVIMOS PROBLEMAS AL GUARDAR LOS DATOS"));
                RequestContext.getCurrentInstance().update("busqueda:msgs");
            } 
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void buscaHistoriaClinicaPaciente(short opcion){
        try{            
            this.oHistoriaClinica.getEpisodioMedico().getPaciente().setOpcionUrg(opcion);
            this.arrHistoria = this.oHistoriaClinica.buscaPacienteHistoriaClinica();           
        }catch(Exception e){
            e.printStackTrace();
        }
        
    }
    public void cargaDatosPacienteHistoria(long foliopaciente, long clave, int exp){
        try{
            this.oHistoriaClinica.getEpisodioMedico().getPaciente().setFolioPaciente(foliopaciente);
            this.oHistoriaClinica.getEpisodioMedico().getPaciente().setClaveEpisodio(clave);
            this.oHistoriaClinica.getEpisodioMedico().getPaciente().getExpediente().setNumero(exp);
            this.oHistoriaClinica.buscaDatosPacienteHistoriaClinica();
            this.oHistoriaClinica.buscaDatosAnverso01();
            this.oHistoriaClinica.buscaDatosReverso01();
            this.oHistoriaClinica.buscaDatosAnverso02();
            this.oPersonal = this.oHistoriaClinica.informacionMedico();
            this.arrMedico = this.oHistoriaClinica.buscaMedicos((short) 0);
            this.oHistoriaClinica.buscaDatosReverso02();            
            this.oCie10.setDescripcionDiag(this.oHistoriaClinica.getDiagnostico().getDescripcionDiag() == null ||
                    this.oHistoriaClinica.getDiagnostico().getDescripcionDiag().isEmpty() ? "" : this.oHistoriaClinica.getDiagnostico().getDescripcionDiag());
            this.oCie10.setClave(this.oHistoriaClinica.getDiagnostico().getClave() == null ||
                    this.oHistoriaClinica.getDiagnostico().getDescripcionDiag().isEmpty() ? "" : this.oHistoriaClinica.getDiagnostico().getClave());            
            this.setMuestraPantalla(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
//**************TERMINAN METODOS DE CONTROL DE DATOS****************//
//**************INICIAN METODOS SET'S Y GETS'S****************//
    public Paciente getPaciente(){
        return oPaciente;
    }
    public void setPaciente(Paciente oPaciente){
        this.oPaciente = oPaciente;
    }
    public Paciente[] getArrPaciente(){
        return arrPaciente;
    }
    public boolean getMuestraPantalla(){
        return bMuestraPantalla;
    }
    public void setMuestraPantalla(boolean bMuestraPantalla){
        this.bMuestraPantalla = bMuestraPantalla;
    }
    public HistoriaClinica getHistoriaClinica(){
        return oHistoriaClinica;
    }
    public boolean habilitaTalla(){
        return this.oHistoriaClinica.getEpisodioMedico().getPaciente().getPeso() == 0;
    }
    public void setHistoriaClinica(HistoriaClinica oHistoriaClinica){
        this.oHistoriaClinica = oHistoriaClinica;
    }
    public PersonalHospitalario getPersonalHospitalario(){
        return oPersonal;
    }
    public void setPersonalHospitalario(PersonalHospitalario oPersonal){
        this.oPersonal = oPersonal;
    }
    public Medico[] getArrMedico(){
        return arrMedico;
    }
    public void setArrMedico(Medico[] arrMedico){
        this.arrMedico = arrMedico;
    }
    public DiagnosticoCIE10 getCie10(){
        return oCie10;
    }
    public void setCie10(DiagnosticoCIE10 oCie10){
        this.oCie10=oCie10;
    }
    public DiagnosticoCIE10[] getArregloDiagnosticos(){
        return arrRet;
    }
    public void setArrgloDiagnosticos(DiagnosticoCIE10[] arrRet){
        this.arrRet = arrRet;
    }
    public ArrayList<DiagnosticoCIE10> getDiagCie10(){
        return arrDiagCie10;
    }
    public void borrarElementoCie10(DiagnosticoCIE10 obj){
        if(arrDiagCie10 != null || arrDiagCie10.isEmpty()){
            if(arrDiagCie10.get(0).getClave().compareTo(obj.getClave()) == 0){
                this.oHistoriaClinica.getDiagnostico().setClave("");
                arrDiagCie10.remove(obj);
            }else{
                arrDiagCie10.remove(obj);
            }
        }
    }
    public HistoriaClinica[] getArrHistoriaClinica(){
        return this.arrHistoria;
    }
    public void setArrHistoriaClinica(HistoriaClinica[] arrHistoria){
        this.arrHistoria = arrHistoria;
    }
    public boolean getTab0(){
        return this.bTab0;
    }
    public void setTab0(boolean bTab0){
        this.bTab0 = bTab0;        
    }
    public boolean getTab1(){
        return this.bTab1;
    }
    public void setTab1(boolean bTab1){
        this.bTab1 = bTab1;
    }
    public boolean getTab2(){
        return this.bTab2;
    }
    public void setTab2(boolean bTab2){
        this.bTab2 = bTab2;
    }
    public boolean getTab3(){
        return bTab3;
    }
    public void setTab3(boolean bTab3){
        this.bTab3 = bTab3;
    }
//**************TERMINAN METODOS SET'S Y GETS'S****************//
}

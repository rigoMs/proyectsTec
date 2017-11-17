package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.cir.Residente;
import mx.gob.hrrb.modelo.core.DetalleRecetaHRRB;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.core.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Quintero
 */

@ManagedBean(name = "oRegistroRes")
@ViewScoped
public class RegistroResultadosQx implements Serializable {
    private ProcedimientosRealizados oProcedimientos = new ProcedimientosRealizados();
    private PersonalHospitalario oAnestesiologoFirmado = new PersonalHospitalario();
    private Medico oAnestesio = new Medico();
    private Residente oResid = new Residente();
    private String sVisibilidadTabla="hidden";
    private ArrayList<ProcedimientosRealizados> arrProcedimientos = null;
    private String Visible = "hidden";
    private boolean bBuscado = false;    
    private DetalleRecetaHRRB oDetalleRecHRRB;
    private Medicamento oMedicamento;
    //private ArrayList<DetalleRecetaHRRB> arrRec;
    private String sVisibilidadReceta = "hidden";
    private Date dFechaEnt;
    private String sHoraEnt;
    private Date dFechaSal;
    private String sHoraSal;
    private ProcedimientoCIE9 oCIE9Pro;
    private String sDuracionProcedimiento = "";
    private String sFechaHoy = "" ;
    private int nCantidadMed = 0 ;
    
    public RegistroResultadosQx(){
        oProcedimientos = new ProcedimientosRealizados();
        oCIE9Pro = new ProcedimientoCIE9();
        oDetalleRecHRRB = new DetalleRecetaHRRB();
        oMedicamento = new Medicamento();
    }
    
     public void inicializar() throws Exception{
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("oPacienteSeleccionado", new ProcedimientosRealizados());
        oProcedimientos = new ProcedimientosRealizados();
        arrProcedimientos = null;
    }
     
     public void MedicoFirmado() throws Exception{
        HttpServletRequest req;
        oAnestesiologoFirmado = new PersonalHospitalario();
        oAnestesiologoFirmado.setUsuar(new Usuario());
        req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm") != null){
            oAnestesiologoFirmado.getUsuar().setIdUsuario(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario());
        }
        oAnestesiologoFirmado.buscaMedicoAnestesioFirmado();
    }
     
     public List<Medico> getListaAnestesiologos() throws Exception{
        List<Medico> listAnes = null;
        listAnes = new ArrayList<Medico>(Arrays.asList((new Medico().buscarMedicoAnestesio())));
        return listAnes;
    }
     
     public void buscaProcedimientosAnestesiologo(){
         sVisibilidadTabla = "visible";
         try{
             arrProcedimientos = new ArrayList<ProcedimientosRealizados>(Arrays.asList(oProcedimientos.buscaProcedimientosxAnestesiologo(oAnestesio)));
         }catch(Exception e){
             e.printStackTrace();
         }
     }
     
     public String getVisible(){
         if(this.bBuscado)
             Visible = "visible";
         else
             Visible = "hidden";
         return Visible;
     }
     
     public ArrayList<ProcedimientosRealizados> getListaProcedimientos(){
         return arrProcedimientos;
     }
     
     public void setSeleccionado(ProcedimientosRealizados valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec", valor);
    }
     
     public ProcedimientosRealizados getSeleccionado(){
         return new ProcedimientosRealizados();
     }
     
     public void llenaDatosProce(){
         ProcedimientosRealizados oProce = null;
         FacesContext facesContext = FacesContext.getCurrentInstance();
         HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
         oProce = (ProcedimientosRealizados)session.getAttribute("pacienteSelec");
         if(oProce == null){
            FacesMessage msj2 = new FacesMessage("¡Alerta!","Selecciona Un Paciente");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
        }else{
            System.out.println("Estamos en la impresion de Datos "+oProce.getEpisodioMedico().getPaciente().getNombreCompleto());
            oProcedimientos = oProce;
            bBuscado = true;
            limpia();
        }
     }
    
      public void limpia(){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session = (HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec",null);
    }
      
    
    public void registraFechasdeResultado(){
        try{
            System.out.println("Fecha que trae entrada--->" + getFechaEnt());
            System.out.println("Fecha que trae salida --->" + getFechaSal());
            System.out.println("Hora de entrada ---> "+ getHoraEnt());
            System.out.println("Hora de salida ---> " + getHoraSal());
            SimpleDateFormat fecComEnt = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat fecComSal = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
            String fechaCompletaEntrada = "" + fd.format(dFechaEnt)+ " "+(sHoraEnt)+ ":00";
            String fechaCompletaSalida = "" + fd.format(dFechaSal)+ " "+(sHoraSal)+ ":00";
            Date dateComEnt = fecComEnt.parse(fechaCompletaEntrada);
            Date dateComSal = fecComSal.parse(fechaCompletaSalida);
            oProcedimientos.setFechaEntrada(dateComEnt);
            oProcedimientos.setFechaSalida(dateComSal);
            if(!oProcedimientos.registraResultadosdeProcedimientoFechas().equals("")){
                sDuracionProcedimiento = (oProcedimientos.registraResultadosdeProcedimientoFechas());
            }
                
        }catch(Exception ex){
            ex.printStackTrace();
            System.out.println("Las fechas no se agregaron correctamente");
        }
    }
    
    
    public void registraResultadoQx(){
        System.out.println("Método");
        FacesMessage message = null;
        try{
            if(oProcedimientos.registraResultadosdeProcedimientoQx() == 1)
                message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Resultado de Procedimiento","Guardado Exitosamente  :) !!");
        }catch(Exception ex){
            ex.printStackTrace();
            message = new FacesMessage(FacesMessage.SEVERITY_INFO,"Resultado de Procedimiento","Ocurrio un Error al Guardar :( !!");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
    }
     
    public void registraReceta(){
        getDetalleRecHRRB().setMedicamento(getMedicamento());
        if(getDetalleRecHRRB().getMedicamento().getClaveMedicamento()!=null &&getDetalleRecHRRB().getMedicamento().getNombre()!=null &&
                    getDetalleRecHRRB().getMedicamento().getPresentacion()!=null && getDetalleRecHRRB().getDosis()!=null && getDetalleRecHRRB().getVia().getValor()!=null && 
                    getDetalleRecHRRB().getFrecuencia()!=null && getDetalleRecHRRB().getDuracion()!=null){
            //arrRec.add(getDetalleRecHRRB());
            setMedicamento(new Medicamento());
            setDetalleRecHRRB(new DetalleRecetaHRRB());
            getDetalleRecHRRB().setMedicamento(new Medicamento());
            getDetalleRecHRRB().setVia(new Parametrizacion());
            setVisibilidadReceta("visible");
        }
        
    }
    
    public void buscarCveProce() throws Exception{
        String procedimiento = this.oProcedimientos.getEpisodioMedico().getProceRe2().getCIE9Realizado().getDescripcion();
        oCIE9Pro.buscarClavexProcedimiento(procedimiento);
    }
    
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; }
    public Residente getResid() { return oResid; }
    public void setResid(Residente oResid) { this.oResid = oResid; }
    public String getVisibilidadTabla() { return this.sVisibilidadTabla; } 
    public Medico getAnestesio() { return oAnestesio; }
    public void setAnestesio(Medico oAnestesio) { this.oAnestesio = oAnestesio; }
    public PersonalHospitalario getAnestesiologoFirmado() { return oAnestesiologoFirmado; }
    public void setAnestesiologoFirmado(PersonalHospitalario oMedicoFirm) { this.oAnestesiologoFirmado = oMedicoFirm; }
    public String getVisibilidadReceta() { return sVisibilidadReceta; }
    public void setVisibilidadReceta(String sVisibilidadReceta) { this.sVisibilidadReceta = sVisibilidadReceta; }
    public DetalleRecetaHRRB getDetalleRecHRRB() { return oDetalleRecHRRB; }
    public void setDetalleRecHRRB(DetalleRecetaHRRB oDetalleRecHRRB) { this.oDetalleRecHRRB = oDetalleRecHRRB; }
    public Medicamento getMedicamento() { return oMedicamento; }
    public void setMedicamento(Medicamento oMedicamento) { this.oMedicamento = oMedicamento; }
    public Date getFechaEnt() { return dFechaEnt; }
    public void setFechaEnt(Date dFechaEnt) { this.dFechaEnt = dFechaEnt; }
    public String getHoraEnt() { return sHoraEnt; }
    public void setHoraEnt(String sHoraEnt) { this.sHoraEnt = sHoraEnt; }
    public Date getFechaSal() { return dFechaSal; }
    public void setFechaSal(Date dFechaSal) { this.dFechaSal = dFechaSal; }
    public String getHoraSal() { return sHoraSal; }
    public void setHoraSal(String sHoraSal) { this.sHoraSal = sHoraSal; }
    public String getDuracionProcedimiento() { return sDuracionProcedimiento; }
    public void setDuracionProcedimiento(String sDuracionProcedimiento) { this.sDuracionProcedimiento = sDuracionProcedimiento; }
     public String getFechaHoy() { 
        SimpleDateFormat hAc = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        Date actual = new Date();
        sFechaHoy = hAc.format(actual);
        return sFechaHoy; 
    }
    
    public void setFechaHoy(String sFechaHoy) {
        this.sFechaHoy = sFechaHoy;
}

    /**
     * @return the nCantidadMed
     */
    public int getCantidadMed() {
        return nCantidadMed;
    }

    /**
     * @param nCantidadMed the nCantidadMed to set
     */
    public void setCantidadMed(int nCantidadMed) {
        this.nCantidadMed = nCantidadMed;
    }
    
}

package mx.gob.hrrb.jbs.cir;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.DetalleRecetaHRRB;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.core.Receta;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Quintero
 */
@ManagedBean (name="oRecetasQx")
@ViewScoped
public class RegistraRecetasQx implements Serializable{
    private ProcedimientosRealizados oProcedimientos = new ProcedimientosRealizados();
    private ArrayList<ProcedimientosRealizados> arrProcedimientos;
    private Medico oAnestesiologo = new Medico();
    private String sVisibilidad = "hidden";
    private String Visible = "hidden";
    private boolean bBuscado = false;
    private DetalleRecetaHRRB oDetalleRec = new DetalleRecetaHRRB();;
    private Receta oRecetas;
    private ArrayList<Medicamento> arrReceta;
    private ArrayList<DetalleRecetaHRRB> arrRecetaMedSol;
    private Medicamento oMedicamento = new Medicamento();;
    private String sVisibilidadRegistroMed="hidden";
    private int nCantidadMed = 0;
    
    public void RegistraRecetasQx(){
        oProcedimientos = new ProcedimientosRealizados();
        oDetalleRec = new DetalleRecetaHRRB();
        oMedicamento = new Medicamento();
        arrProcedimientos = new ArrayList<ProcedimientosRealizados>();
    }
    
     public List<Medico> getLisCirujanos() throws Exception{
        List<Medico> listaCir = null;
        listaCir = new ArrayList<Medico>(Arrays.asList((new Medico().buscarMedicoCir())));
        return listaCir;
    }
     
    public List<Medico> getListaAnestesiologos() throws Exception{
        List<Medico> listAnes = null;
        listAnes = new ArrayList<Medico>(Arrays.asList((new Medico().buscarMedicoAnestesio())));
        return listAnes;
    }
    
    public void setSeleccionado(ProcedimientosRealizados valor){
        FacesContext facesContext = FacesContext.getCurrentInstance();
        HttpSession session =(HttpSession)facesContext.getExternalContext().getSession(false);
        session.setAttribute("pacienteSelec", valor);
    }
    public ProcedimientosRealizados getSeleccionado(){ return new ProcedimientosRealizados(); }
    
    public void buscaProcedimientos(){
        sVisibilidad = "visible";
        try{
            arrProcedimientos = new ArrayList<ProcedimientosRealizados>(
                    Arrays.asList(oProcedimientos.buscaProcedimientosReceta(
                            oAnestesiologo)));
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void llenaDatosProcedimiento(){
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
        }
    }
    
       
    public void registroReceta(){
        oDetalleRec.setMedicamento(oMedicamento);
        if(getMedicamento().getNombre() == null || 
                getMedicamento().getNombre().equals("") || 
                oDetalleRec.getCantidad()==0){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "SELECCIONE UN MEDICAMENTO"));
        } else {
            if(buscaRepetidoMedic(oMedicamento.getClaveMedicamento())){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", "EL MEDICAMENTO YA FUE PREVIAMENTE AGREGADO"));
                oMedicamento = new Medicamento();
                oMedicamento.setClaveMedicamento("");
                oMedicamento.setNombre("");
                oMedicamento.setPresentacion("");
                oDetalleRec.setCantidad(0);
            } else {
                if(!getMedicamento().getNombre().equals("") && 
                        oDetalleRec.getCantidad()!=0){

                    oDetalleRec.setEpisodio(oProcedimientos.getEpisodioMedico());
                    oDetalleRec.setAutorizadoPor(oAnestesiologo);
                    oDetalleRec.setReceta(oRecetas);
                    arrReceta.add(oDetalleRec.getMedicamento());

                    oMedicamento = new Medicamento();
                    oMedicamento.setClaveMedicamento("");
                    oMedicamento.setNombre("");
                    oMedicamento.setPresentacion("");
                    oDetalleRec.setCantidad(0);
                    sVisibilidadRegistroMed="visible";
                }
            }
        }
    }
    
    public boolean buscaRepetidoMedic(String clave) {
        boolean bandera = false;
        for (Medicamento i : arrReceta) {
            if (i.getClaveMedicamento().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }
    
    public void guardarReceta(){
        try{
            if(this.oDetalleRec.insertaRecetaInd(arrRecetaMedSol) == 1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO, "Solicitud de Medicamento", "Solicitud Guardada Correctamente"));
                limpiarTabla();
            }
        }catch(Exception e){
            Logger.getLogger(RegistraRecetasQx.class.getName()).log(Level.SEVERE, null, e);
        }
    }
    
    public void borrarElemento(Medicamento obj){
        arrReceta.remove(obj);
        if(arrReceta.isEmpty()){
            sVisibilidadRegistroMed="hidden";
        }else{
            sVisibilidadRegistroMed="visible";
        }
    }
    
    public void buscaClave() throws Exception{
        oMedicamento.buscarPorNombre();
    }
    
    public void limpiarTabla(){
        arrReceta.clear();
        sVisibilidadRegistroMed="hidden";
    }
    
    public void borrarTodo(){
        this.getRecetas().setFolioReceta(0);
        oMedicamento.setClaveMedicamento("");
        oMedicamento.setNombre("");
        oMedicamento.setPresentacion("");
        oDetalleRec.setCantidad(0);
    }
    
    public ArrayList<ProcedimientosRealizados> getListaProcedimientos(){ return arrProcedimientos; }
    public ProcedimientosRealizados getProcedimientos() { return oProcedimientos; }
    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) { this.oProcedimientos = oProcedimientos; }
    public String getVisibilidad() { return sVisibilidad; }
    public String getVisible(){
        if(this.bBuscado)
            Visible = "visible";
        else
            Visible = "hidden";
        return Visible;
    }
    public Medico getAnestesiologo() { return oAnestesiologo; }
    public void setAnestesiologo(Medico oAnestesiologo) { this.oAnestesiologo = oAnestesiologo; }
     public DetalleRecetaHRRB getDetalleRec() {
        return oDetalleRec;
    }

    public void setDetalleRec(DetalleRecetaHRRB oDetalleRec) {
        this.oDetalleRec = oDetalleRec;
    }

    public Medicamento getMedicamento() {
        return oMedicamento;
    }

    public void setMedicamento(Medicamento oMedicamento) {
        this.oMedicamento = oMedicamento;
    }

    public ArrayList<Medicamento> getReceta() {
        return arrReceta;
    }

    public void setReceta(ArrayList<Medicamento> arrReceta) {
        this.arrReceta = arrReceta;
    }
    
    public Receta getRecetas() {
        return oRecetas;
    }

    public void setRecetas(Receta oRecetas) {
        this.oRecetas = oRecetas;
    }
    
    public ArrayList<DetalleRecetaHRRB> getRecetaMedSol() {
        return arrRecetaMedSol;
    }

    public void setRecetaMedSol(ArrayList<DetalleRecetaHRRB> arrRecetaMedSol) {
        this.arrRecetaMedSol = arrRecetaMedSol;
    }
    
    public String getVisibilidadRegistroMed() {
        return sVisibilidadRegistroMed;
    }

    public void setVisibilidadRegistroMed(String sVisibilidadRegistroMed) {
        this.sVisibilidadRegistroMed = sVisibilidadRegistroMed;
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

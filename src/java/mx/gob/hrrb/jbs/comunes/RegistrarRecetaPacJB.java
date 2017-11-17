package mx.gob.hrrb.jbs.comunes;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.DetalleRecetaHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Receta;
import mx.gob.hrrb.modelo.core.Usuario;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Rafael
 */
@ManagedBean(name = "oRegRecPac")
@ViewScoped
public class RegistrarRecetaPacJB implements Serializable {
private Medico oMedFirm;
private Paciente oPaciente;
private EpisodioMedico oEpisodioMedico;
private EpisodioMedico oSeleccionado;
private DetalleRecetaHRRB oDetalleRecHRRB;
private Receta oReceta;
private Medicamento oMedicamento;
private Date dFechaActual;
private Parametrizacion arrVia[];
private ArrayList<DetalleRecetaHRRB> arrRec;
private String sVisibilidadReceta = "hidden";
private String sBotonG = "hidden";
private String sVisibilidadRegRec = "hidden";
private String sBoton="hidden";
private String sVisibilidadTabla="hidden";
private int nSeleccion = 0;
private ArrayList<EpisodioMedico> arrEpiMed;
private String sArea1;
private String sArea2;
private String sArea3;
private String sSeguroDesglosado[];

private boolean bBuscado = false;

    public RegistrarRecetaPacJB() {
        HttpServletRequest req;
        oMedFirm = new Medico();
        oMedFirm.setUsuar(new Usuario());
        req = (HttpServletRequest) 
                FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
            oMedFirm.getUsuar().setIdUsuario(((Firmado) req.getSession().getAttribute(
                    "oFirm")).getUsu().getIdUsuario());
            try{
                oMedFirm.buscaUsuarioFirmado();
            }catch(Exception e){
                e.printStackTrace();
            }
        }
        
        oPaciente = new Paciente();
        oEpisodioMedico = new EpisodioMedico();
        oReceta = new Receta();
        oDetalleRecHRRB = new DetalleRecetaHRRB();
        oMedicamento = new Medicamento();
        oDetalleRecHRRB.setMedicamento(new Medicamento());
        oDetalleRecHRRB.setVia(new Parametrizacion());
        arrRec = new ArrayList<DetalleRecetaHRRB>();
        arrEpiMed = new ArrayList<EpisodioMedico>();
        dFechaActual = new Date();
        sSeguroDesglosado = new String[13];
        sSeguroDesglosado[0] = "0";
        sSeguroDesglosado[1] = "0";
        sSeguroDesglosado[2] = "0";
        sSeguroDesglosado[3] = "0";
        sSeguroDesglosado[4] = "0";
        sSeguroDesglosado[5] = "0";
        sSeguroDesglosado[6] = "0";
        sSeguroDesglosado[7] = "0";
        sSeguroDesglosado[8] = "0";
        sSeguroDesglosado[9] = "0";
        sSeguroDesglosado[10] = "0";
        sSeguroDesglosado[11] = "0";
        sSeguroDesglosado[12] = "0";
    }
    
    
////////////////////////////////////////////////////////////////////////////////
    
    
    public Parametrizacion[] getListaVia() {
        try {
            arrVia = (new Parametrizacion()).buscarViaAdministracion();
        } catch (Exception ex) {
            Logger.getLogger(RegistrarRecetaPacJB.class.getName()).log(Level.SEVERE, null, ex);
        }
        return arrVia;
    }
    
    public void buscaMedicamento() {
        try{
            oMedicamento.buscarPorNombre();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void registroReceta() {
        oDetalleRecHRRB.setMedicamento(oMedicamento);
        if (getMedicamento().getNombre() == null 
                || getMedicamento().getNombre().equals("")
                || getMedicamento().getClaveMedicamento() == null 
                || getMedicamento().getClaveMedicamento().equals("")
                || getMedicamento().getPresentacion() == null 
                || getMedicamento().getPresentacion().equals("")
                || oDetalleRecHRRB.getDosis() == null 
                || oDetalleRecHRRB.getDosis().equals("")
                || oDetalleRecHRRB.getVia().getValor() == null 
                || oDetalleRecHRRB.getVia().getValor().equals("")
                || oDetalleRecHRRB.getFrecuencia() == null 
                || oDetalleRecHRRB.getFrecuencia().equals("")
                || oDetalleRecHRRB.getDuracion() == null 
                || oDetalleRecHRRB.getDuracion().equals("")) {
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(
                    FacesMessage.SEVERITY_ERROR, "ERROR", 
                    "DEBE COMPLETAR LA RECETA"));
        } else {
            if (buscaRepetidoRec(getMedicamento().getClaveMedicamento())) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, "ERROR", 
                                "EL MEDICAMENTO YA FUE PREVIAMENTE AGREGADO"));
                oMedicamento = new Medicamento();
                oDetalleRecHRRB.setMedicamento(new Medicamento());
                oDetalleRecHRRB.setVia(new Parametrizacion());
                oDetalleRecHRRB.getMedicamento().setClaveMedicamento("");
                oDetalleRecHRRB.getMedicamento().setNombre("");
                getMedicamento().setPresentacion("");
                oDetalleRecHRRB.setDosis("");
                oDetalleRecHRRB.getVia().setValor("");
                oDetalleRecHRRB.setFrecuencia("");
                oDetalleRecHRRB.setDuracion("");
            } else {
                if (!getMedicamento().getNombre().equals("") && 
                        !getMedicamento().getClaveMedicamento().equals("") && 
                        !getMedicamento().getPresentacion().equals("") && 
                        !oDetalleRecHRRB.getDosis().equals("") && 
                        !oDetalleRecHRRB.getVia().getValor().equals("") && 
                        !oDetalleRecHRRB.getFrecuencia().equals("") && 
                        !oDetalleRecHRRB.getDuracion().equals("")) {
                    oDetalleRecHRRB.getVia().equalsViaAdm(arrVia);
                    arrRec.add(oDetalleRecHRRB);
                    oDetalleRecHRRB = new DetalleRecetaHRRB();
                    oMedicamento = new Medicamento();
                    oDetalleRecHRRB.setMedicamento(new Medicamento());
                    oDetalleRecHRRB.setVia(new Parametrizacion());
                    sVisibilidadReceta = "visible";
                    sBotonG = "visible";
                }
            }
        }
    }

    public boolean buscaRepetidoRec(String clave) {
        boolean bandera = false;
        for (DetalleRecetaHRRB i : arrRec) {
            if (i.getMedicamento().getClaveMedicamento().equals(clave)) {
                bandera = true;
                break;
            }
        }
        return bandera;
    }

    public void borrarElemento(DetalleRecetaHRRB obj) {
        arrRec.remove(obj);
        if (arrRec.isEmpty()) {
            sVisibilidadReceta = "hidden";
            sBotonG = "hidden";
        } else {
            sVisibilidadReceta = "visible";
            sBotonG = "visible";
        }
    }

    public void guardar() {
        try {
            oReceta.setEpisodioMedico(oEpisodioMedico);
            oDetalleRecHRRB.setReceta(oReceta);
            oDetalleRecHRRB.setEpisodio(oEpisodioMedico);
            oDetalleRecHRRB.setAutorizadoPor(oMedFirm);
            if (oDetalleRecHRRB.insertaRecetaHrrb(arrRec) >= 1) {
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_INFO, 
                                "Receta médica", 
                                "Receta Guardada Correctamente"));
                sBotonG = "hidden";
            }else{
                FacesContext.getCurrentInstance().addMessage(null, 
                        new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                                "Receta médica", 
                                "Error al almacenar la receta"));
            }
        } catch (Exception ex) {
            Logger.getLogger(RegistrarRecetaPacJB.class.getName()).log(
                    Level.SEVERE, null, ex);
            FacesContext.getCurrentInstance().addMessage(null, 
                    new FacesMessage(FacesMessage.SEVERITY_ERROR, 
                            "Receta médica", 
                            "Error al almacenar la receta"));
        }
    }
    
    public void borraDatos() {
        getReceta().setFolioReceta(0);
        this.getMedicamento().setClaveMedicamento("");
        this.getMedicamento().setNombre("");
        this.getMedicamento().setPresentacion("");
        this.oDetalleRecHRRB.setDosis("");
        this.oDetalleRecHRRB.getVia().setValor("");
        this.oDetalleRecHRRB.setFrecuencia("");
        this.oDetalleRecHRRB.setDuracion("");
    }

    public void validaSeleccionArea() {
    switch (getEpisodioMedico().getArea().getTipo()) {
        case "CE":
            sArea1 = "X";
            sArea2 = "";
            sArea3 = "";
            break;
        case "HOSP":
        case "HOSP-P":
        case "HOSPSUB":
            sArea1 = "";
            sArea2 = "X";
            sArea3 = "";
            break;
        case "URG":
            sArea1 = "";
            sArea2 = "";
            sArea3 = "X";
            break;
    }
    }
    
    public void desglosar(){
        String num;
        num = this.oEpisodioMedico.getPaciente().getSeg().getNumero();
        char[] arrC = num.toCharArray();
        for(int i=0; i<arrC.length; i++){
            this.sSeguroDesglosado[i] = ""+arrC[i];
        }
    }
    
////////////////////////////////////////////////////////////////////////////////    

    public void buscaPacienteAtenMed(){
        sVisibilidadTabla="visible";
        sBoton="visible";
        try{
            arrEpiMed = new ArrayList<EpisodioMedico>(Arrays.asList(
                    oEpisodioMedico.buscarDatosPacienteAtencionMedica()));
            oSeleccionado = null;
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    
    public void llenadoDatosPersonales() {
        if (oSeleccionado == null) {
            FacesMessage msj2 = new FacesMessage("¡Aviso!", "¡Seleccione un paciente!");
            RequestContext.getCurrentInstance().showMessageInDialog(msj2);
            bBuscado = false;
            oEpisodioMedico = new EpisodioMedico();
        } else {
            oEpisodioMedico = oSeleccionado;
            bBuscado = true;
            sVisibilidadRegRec = "visible";
            sVisibilidadTabla = "hidden";
            sBotonG = "hidden";
            arrRec.clear();
            arrEpiMed.clear();
            validaSeleccionArea();
            borraDatos();
            desglosar();
        }
    }

    public String getVisible() {
        if (this.bBuscado) {
            return "visible";
        } else {
            return "hidden";
        }
    }
////////////////////////////////////////////////////////////////////////////////   

    public Medico getMedFirm() {
        return oMedFirm;
    }

    public void setMedFirm(Medico oMedFirm) {
        this.oMedFirm = oMedFirm;
    }
    
    
    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public DetalleRecetaHRRB getDetalleRecHRRB() {
        return oDetalleRecHRRB;
    }

    public void setDetalleRecHRRB(DetalleRecetaHRRB oDetalleRecHRRB) {
        this.oDetalleRecHRRB = oDetalleRecHRRB;
    }

    public ArrayList<DetalleRecetaHRRB> getRec() {
        return arrRec;
    }

    public void setRec(ArrayList<DetalleRecetaHRRB> arrRec) {
        this.arrRec = arrRec;
    }

    public boolean getBuscado() {
        return this.bBuscado;
    }

    public int getSeleccion() {
        return nSeleccion;
    }

    public void setSeleccion(int nSeleccion) {
        this.nSeleccion = nSeleccion;
    }

    public Medicamento getMedicamento() {
        return oMedicamento;
    }

    public void setMedicamento(Medicamento oMedicamento) {
        this.oMedicamento = oMedicamento;
    }

    public EpisodioMedico getEpisodioMedico() {
        return oEpisodioMedico;
    }

    public void setEpisodioMedico(EpisodioMedico oEpisodioMedico) {
        this.oEpisodioMedico = oEpisodioMedico;
    }

    public String getVisibilidadReceta() {
        return sVisibilidadReceta;
    }

    public void setVisibilidadReceta(String sVisibilidadReceta) {
        this.sVisibilidadReceta = sVisibilidadReceta;
    }

    public String getBotonG() {
        return sBotonG;
    }

    public void setBotonG(String sBotonG) {
        this.sBotonG = sBotonG;
    }

    public String getVisibilidadRegRec() {
        return sVisibilidadRegRec;
    }

    public void setVisibilidadRegRec(String sVisibilidadRegRec) {
        this.sVisibilidadRegRec = sVisibilidadRegRec;
    }

    public Parametrizacion[] getVia() {
        return arrVia;
    }

    public void setVia(Parametrizacion[] arrVia) {
        this.arrVia = arrVia;
    }

    public Receta getReceta() {
        return oReceta;
    }

    public void setReceta(Receta oReceta) {
        this.oReceta = oReceta;
    }

    public Date getFechaActual() {
        return dFechaActual;
    }

    public void setFechaActual(Date dFechaActual) {
        this.dFechaActual = dFechaActual;
    }

    public String getBoton() {
        return sBoton;
}

    public void setBoton(String sBoton) {
        this.sBoton = sBoton;
    }

    public String getVisibilidadTabla() {
        return sVisibilidadTabla;
    }

    public void setVisibilidadTabla(String sVisibilidadTabla) {
        this.sVisibilidadTabla = sVisibilidadTabla;
    }

    public ArrayList<EpisodioMedico> getEpiMed() {
        return arrEpiMed;
    }

    public void setEpiMed(ArrayList<EpisodioMedico> arrEpiMed) {
        this.arrEpiMed = arrEpiMed;
    }

    public EpisodioMedico getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(EpisodioMedico oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
    }

    public String getArea1() {
        return sArea1;
    }

    public void setArea1(String sArea1) {
        this.sArea1 = sArea1;
    }

    public String getArea2() {
        return sArea2;
    }

    public void setArea2(String sArea2) {
        this.sArea2 = sArea2;
    }

    public String getArea3() {
        return sArea3;
    }

    public void setArea3(String sArea3) {
        this.sArea3 = sArea3;
    }

    public String[] getSeguroDesglosado() {
        return sSeguroDesglosado;
    }

    public void setSeguroDesglosado(String[] sSeguroDesglosado) {
        this.sSeguroDesglosado = sSeguroDesglosado;
    }

}

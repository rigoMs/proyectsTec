package mx.gob.hrrb.jbs.caja;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.RequestScoped;
import mx.gob.hrrb.modelo.caja.CorteCaja;
import mx.gob.hrrb.modelo.caja.ReciboSeguroPopular;
import mx.gob.hrrb.modelo.caja.reportes.InformeIngresosCuotasRecup;
import mx.gob.hrrb.modelo.caja.reportes.InformeServMedSegPop;
import mx.gob.hrrb.modelo.caja.reportes.RegistroServMedSinRecibo;

/**
 *
 * @author Daniel
 */
@ManagedBean(name="oRealizarCorte")
@RequestScoped
public class RealizarCorteCajaJB {
    private CorteCaja oCorte;
    private InformeIngresosCuotasRecup oInfCR;
    private InformeServMedSegPop oInfSP;
    private RegistroServMedSinRecibo oRegServSinRec=new RegistroServMedSinRecibo();
    private ReciboSeguroPopular arrRecCancelados[];
    private RegistroServMedSinRecibo arrPacientesProg[];
    /**
     * Creates a new instance of RealizarCorteCajaJB
     */
    public RealizarCorteCajaJB() throws Exception {
        arrPacientesProg=oRegServSinRec.pacientesPrograma();
    }

    public CorteCaja getCorte() {
        return oCorte;
    }

    public void setCorte(CorteCaja oCorte) {
        this.oCorte = oCorte;
    }

    public InformeIngresosCuotasRecup getInfCR() {
        return oInfCR;
    }

    public void setInfCR(InformeIngresosCuotasRecup oInfCR) {
        this.oInfCR = oInfCR;
    }

    public InformeServMedSegPop getInfSP() {
        return oInfSP;
    }

    public void setInfSP(InformeServMedSegPop oInfSP) {
        this.oInfSP = oInfSP;
    }

    public RegistroServMedSinRecibo getRegServSinRec() {
        return oRegServSinRec;
    }

    public void setRegServSinRec(RegistroServMedSinRecibo oRegServSinRec) {
        this.oRegServSinRec = oRegServSinRec;
    }

    public ReciboSeguroPopular[] getRecCancelados() {
        return arrRecCancelados;
    }

    public void setRecCancelados(ReciboSeguroPopular[] arrRecCancelados) {
        this.arrRecCancelados = arrRecCancelados;
    }

    public RegistroServMedSinRecibo[] getPacientesProg() {
        return arrPacientesProg;
    }

    public void setPacientesProg(RegistroServMedSinRecibo[] arrPacientesProg) {
        this.arrPacientesProg = arrPacientesProg;
    }
    
}

package mx.gob.hrrb.modelo.caja.reportes;

import java.math.BigDecimal;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.modelo.serv.ProductoHemoderivado;

/**
 *
 * @author Daniel
 */
public class RegistroServMedSinRecibo {
    private int nNumProg;
    private Paciente oPaciente;
    private BigDecimal nConsultaSub;
    private BigDecimal nLaboratorio;
    private BigDecimal nRadiologia;
    private BigDecimal nColposcopia;
    private ServicioCobrable arrServVarios[];
    private BigDecimal nTotal;
    
    public RegistroServMedSinRecibo[] pacientesPrograma() throws Exception{
        RegistroServMedSinRecibo arrPacientesProg[]=null;
        
        RegistroServMedSinRecibo oPac=new RegistroServMedSinRecibo();
        oPac.setPaciente(new Paciente());
        oPac.getPaciente().setExpediente(new Expediente());
        oPac.getPaciente().setNombres("Daniel");
        oPac.getPaciente().setApPaterno("López");
        oPac.getPaciente().setApMaterno("Rosas");
        oPac.setNumProg(1);
        oPac.getPaciente().getExpediente().setNumero(1344);
        oPac.setColposcopia(new BigDecimal(123.33));
        oPac.setConsultaSub(new BigDecimal(123.33));
        oPac.setLaboratorio(new BigDecimal(123.33));
        oPac.setRadiologia(new BigDecimal(123.33));
        oPac.setTotal(new BigDecimal(123.33));
        
        arrServVarios=new ServicioCobrable[3];
        ServicioCobrable oSC=new ProductoHemoderivado();
        oSC.setClave("22-222");
        oSC.setCostoHospital(new BigDecimal(222.22));
        arrServVarios[0]=oSC;
        oSC=new ProductoHemoderivado();
        oSC.setClave("11-222");
        oSC.setCostoHospital(new BigDecimal(789.22));
        arrServVarios[1]=oSC;
        oSC=new ProductoHemoderivado();
        oSC.setClave("33-222");
        oSC.setCostoHospital(new BigDecimal(111.22));
        arrServVarios[2]=oSC;
        oPac.setServVarios(arrServVarios);
        
        
        arrPacientesProg=new RegistroServMedSinRecibo[1];
        arrPacientesProg[0]=oPac;

        
        oPac=new RegistroServMedSinRecibo();
        oPac.setPaciente(new Paciente());
        oPac.getPaciente().setExpediente(new Expediente());
        oPac.getPaciente().setNombres("Daniel");
        oPac.getPaciente().setApPaterno("López");
        oPac.getPaciente().setApMaterno("Rosas");
        oPac.setNumProg(1);
        oPac.getPaciente().getExpediente().setNumero(1344);
        oPac.setColposcopia(new BigDecimal(123.33));
        oPac.setConsultaSub(new BigDecimal(123.33));
        oPac.setLaboratorio(new BigDecimal(123.33));
        oPac.setRadiologia(new BigDecimal(123.33));
        oPac.setTotal(new BigDecimal(123.33));
        
        arrServVarios=new ServicioCobrable[3];
        oSC=new ProductoHemoderivado();
        oSC.setClave("22-222");
        oSC.setCostoHospital(new BigDecimal(222.22));
        arrServVarios[0]=oSC;
        oSC=new ProductoHemoderivado();
        oSC.setClave("11-222");
        oSC.setCostoHospital(new BigDecimal(789.22));
        arrServVarios[1]=oSC;
        oSC=new ProductoHemoderivado();
        oSC.setClave("33-222");
        oSC.setCostoHospital(new BigDecimal(111.22));
        arrServVarios[2]=oSC;
        oPac.setServVarios(arrServVarios);
        
        
        arrPacientesProg=new RegistroServMedSinRecibo[2];
        arrPacientesProg[0]=oPac;
        
        oPac=new RegistroServMedSinRecibo();
        oPac.setPaciente(new Paciente());
        oPac.getPaciente().setExpediente(new Expediente());
        oPac.getPaciente().setNombres("Diego Samuel");
        oPac.getPaciente().setApPaterno("Apes");
        oPac.getPaciente().setApMaterno("Tosas");
        oPac.setNumProg(1);
        oPac.getPaciente().getExpediente().setNumero(1344);
        oPac.setColposcopia(new BigDecimal(123.33));
        oPac.setConsultaSub(new BigDecimal(123.33));
        oPac.setLaboratorio(new BigDecimal(123.33));
        oPac.setRadiologia(new BigDecimal(123.33));
        oPac.setTotal(new BigDecimal(123.33));
        
        arrServVarios=new ServicioCobrable[3];
        oSC=new ProductoHemoderivado();
        oSC.setClave("22-222");
        oSC.setCostoHospital(new BigDecimal(222.22));
        arrServVarios[0]=oSC;
        oSC=new ProductoHemoderivado();
        oSC.setClave("11-222");
        oSC.setCostoHospital(new BigDecimal(789.22));
        arrServVarios[1]=oSC;
        oSC=new ProductoHemoderivado();
        oSC.setClave("33-222");
        oSC.setCostoHospital(new BigDecimal(111.22));
        arrServVarios[2]=oSC;
        oPac.setServVarios(arrServVarios);
        arrPacientesProg[1]=oPac;
        return arrPacientesProg;

    } 
    
    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public BigDecimal getConsultaSub() {
        return nConsultaSub;
    }

    public void setConsultaSub(BigDecimal nConsultaSub) {
        this.nConsultaSub = nConsultaSub;
    }

    public BigDecimal getLaboratorio() {
        return nLaboratorio;
    }

    public void setLaboratorio(BigDecimal nLaboratorio) {
        this.nLaboratorio = nLaboratorio;
    }

    public BigDecimal getRadiologia() {
        return nRadiologia;
    }

    public void setRadiologia(BigDecimal nRadiologia) {
        this.nRadiologia = nRadiologia;
    }

    public BigDecimal getColposcopia() {
        return nColposcopia;
    }

    public void setColposcopia(BigDecimal nColposcopia) {
        this.nColposcopia = nColposcopia;
    }

    public BigDecimal getTotal() {
        return nTotal;
    }

    public void setTotal(BigDecimal nTotal) {
        this.nTotal = nTotal;
    }

    public ServicioCobrable[] getServVarios() {
        return arrServVarios;
    }

    public void setServVarios(ServicioCobrable[] arrServVarios) {
        this.arrServVarios = arrServVarios;
    }

    public int getNumProg() {
        return nNumProg;
    }

    public void setNumProg(int nNumProg) {
        this.nNumProg = nNumProg;
    }

}

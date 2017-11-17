package mx.gob.hrrb.jbs.core.notas;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.NotaEmbarazo;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.notas.AtencionNeonatalTococirugia;
import mx.gob.hrrb.modelo.core.notas.CalificacionApgar;
import mx.gob.hrrb.modelo.core.notas.CalificacionSilvermann;
import mx.gob.hrrb.modelo.core.notas.TerminacionEmbarazo;
import mx.gob.hrrb.modelo.core.notas.ValoracionEpidemiologico;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Alberto
 */
@ManagedBean(name="atencionneonatal")
@ViewScoped
public class atencionNeonatal implements Serializable{
    private AtencionNeonatalTococirugia oAtencion;
    private AtencionNeonatalTococirugia[] arrAtencion;
    private AtencionNeonatalTococirugia oDatosAtencion;
    private boolean bMuestraPantalla;
    private PersonalHospitalario oPersonal;
    private Medico arrMedico[];
    private DiagnosticoCIE10 arrRet[];
    private ArrayList<DiagnosticoCIE10> arrDiagCie10;
    private DiagnosticoCIE10 oDiagnostico;
    private CalificacionApgar oCalificacion;
    private CalificacionSilvermann oSilvermann;
    private ValoracionEpidemiologico oValoracion;
    public atencionNeonatal() {
        this.oAtencion = new AtencionNeonatalTococirugia();
        this.arrDiagCie10 = new ArrayList<DiagnosticoCIE10>();        
        
    }
    //***********INICIA LOS METODOS D CONTROL DE DATOS****************
    public void cargaDatosPaciente(short opcion){
        try{
            this.oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setOpcionUrg(opcion);
            this.arrAtencion = this.oAtencion.buscaDatosPacienteNproductos();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public void cargaDatosPacienteAtencionNeonatal(short opcion){
        try{
            this.oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setOpcionUrg(opcion);
            this.arrAtencion = this.oAtencion.buscaDatosPacienteAtencionNeonatal();
        }catch(Exception e){
            e.printStackTrace();
        }        
    }
    public AtencionNeonatalTococirugia getDatosGenerales(){
        AtencionNeonatalTococirugia oAtencion = new AtencionNeonatalTococirugia();
        if(!this.arrAtencion.equals(null) || this.arrAtencion.length > 0){
            oAtencion.getProducto().setTerminacionEmbarazo(new TerminacionEmbarazo());
            oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFechaNac(this.arrAtencion[0].getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFechaNac());
            oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setNombres(this.arrAtencion[0].getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getNombres());
            oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApPaterno(this.arrAtencion[0].getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApPaterno());
            oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setApMaterno(this.arrAtencion[0].getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getApMaterno());
        }
        return oAtencion;
    }
    public void cargaDatosGenerales(long fpaciente, long clave, int nxpediente, int consecutivonota, long npartograma, int ningresohosp, int consecutivoproducto){
        try{
            this.oDatosAtencion = new AtencionNeonatalTococirugia();
            NotaEmbarazo oConsulta = new NotaEmbarazo();
            this.arrDiagCie10 = new ArrayList<DiagnosticoCIE10>();
            this.oDiagnostico = new DiagnosticoCIE10();
            this.oCalificacion = new CalificacionApgar();
            this.oSilvermann = new CalificacionSilvermann();
            this.oValoracion = new ValoracionEpidemiologico();
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(fpaciente);
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(clave);
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().setNumero(nxpediente);
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(consecutivonota);
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(npartograma);
            this.oDatosAtencion.getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oDatosAtencion.getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oPersonal = this.oDatosAtencion.informacionMedico();
            this.oDatosAtencion.buscaDatosPacienteProductoEpecifico();
            this.oDatosAtencion.consultaAnverso();
            this.arrRet = oConsulta.datosPaciente(this.oDatosAtencion.getPacienteNeonato().getFolioPaciente(), this.oDatosAtencion.getPacienteNeonato().getClaveEpisodio());
            this.arrMedico = this.oDatosAtencion.buscaMedicos((short) 0);                        
            if(this.oDatosAtencion.getDiagnosticos().getClave() != null){
                oDiagnostico.setClave(this.oDatosAtencion.getDiagnosticos().getClave());
                oDiagnostico.setDescripcionDiag(this.oDatosAtencion.getDiagnosticos().getDescripcionDiag());
            }else{
                oDiagnostico.setClave("");
                oDiagnostico.setDescripcionDiag("");
            }
            this.oCalificacion.getAtencionNeonatalTococirugia().getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oCalificacion.getAtencionNeonatalTococirugia().getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oCalificacion.buscaDetalleApgar();
            this.oSilvermann.getAtencionNeonatalTococirugia().getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oSilvermann.getAtencionNeonatalTococirugia().getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oSilvermann.buscaDetalleSilvermann();
            this.oValoracion.getAtencionNeonatalTococirugia().getProducto().setNumeroIngresoHospitalario(ningresohosp);
            this.oValoracion.getAtencionNeonatalTococirugia().getProducto().setConsecutivoProducto(consecutivoproducto);
            this.oValoracion.buscaDetalleValoracionEpidemio();
            this.setMuestraPantalla(true);
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public long calculaEdadGestacional(){
        Calendar fechaultimaregla = new GregorianCalendar();
        Calendar fechanacimiento = new GregorianCalendar();
        fechaultimaregla.setTime(this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion());
        fechanacimiento.setTime(this.oDatosAtencion.getPacienteNeonato().getProducto().getFechaNacimiento());
        long resta = fechanacimiento.getTimeInMillis() - fechaultimaregla.getTimeInMillis();
        long semanas = resta / 604800000;// VALOR EQUIVALENTE AL NUMERO DE MILISEGUNDOS EN UNA SEMANA COMPLETA [7 DIAS]
        this.oDatosAtencion.setEdadgestacionalEGFUR((int)semanas); 
        return semanas;
    }
    public void asignaCedula(){
        if(this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombreCompleto().isEmpty())
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("SELECCIONE UN MEDICO");
        else{
            
            String cedulaProfesional = buscaMedico(this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres());
            int ntarjeta = buscaMedico(this.oDatosAtencion);
            String cedula = cedulaProfesional.compareTo("") == 0 ? "NO DISPONIBLE" : cedulaProfesional;
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf(cedula);
            int tarjeta = ntarjeta != 0 ? ntarjeta : 0;
            this.oDatosAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNoTarjeta(tarjeta);
        }
    }
    public String buscaMedico(String medico){
        for(int i = 0; i < arrMedico.length; i++)
            if(arrMedico[i].getNombreCompleto().compareTo(medico) == 0)
                return arrMedico[i].getCedProf();
        return null;
    }
    public int buscaMedico(AtencionNeonatalTococirugia oAtencion){
        for(int i = 0; i < arrMedico.length; i++)
            if(arrMedico[i].getNombreCompleto().compareTo(oAtencion.getProducto().getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNombres()) == 0)
                return arrMedico[i].getNoTarjeta();
        return 0;
    }
    public void registroDiagnostico(){
        if(this.oDiagnostico.getClave().isEmpty())
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "NO PUEDE AGREGAR VACÍO"));
        else{
            int nTam = arrRet == null ? 0 : arrRet.length;
            if(nTam >= 6 || arrDiagCie10.size() >= 6 || nTam + arrDiagCie10.size() >= 6){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "IMPOSIBLE AGREGAR MAS DIAGNOSTICOS MAXIMO 6 DIAGNOSTICOS POR PACIENTE"));
                
            }else{
                if(arrDiagCie10.isEmpty() && buscaRepetidoDxAnterior(this.oDiagnostico.getClave()) != true){
                    this.oDatosAtencion.getDiagnosticos().setClave(this.oDiagnostico.getClave());
                    this.arrDiagCie10.add(this.oDiagnostico);
                    this.oDiagnostico = new DiagnosticoCIE10();
                }else{
                    if(buscaRepetido(this.oDiagnostico.getClave())){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                        
                    }else{
                        if(buscaRepetidoDxAnterior(this.oDiagnostico.getClave())){
                            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR", "EL DIAGNOSTICO YA SE ENCUENTRA AGREGADO"));
                            
                        }else{
                            arrDiagCie10.add(this.oDiagnostico);
                            oDiagnostico = new DiagnosticoCIE10();
                            
                        }
                    }
                }
            }
        }
    }
    public boolean buscaRepetidoDxAnterior(String clave){
        if(this.arrRet == null)
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
    public void guardaAnverso(){
        try{
            int nTam = arrRet == null ? 0 : arrRet.length;            
            if(this.oDatosAtencion.guardaAnverso(this.arrDiagCie10, nTam)){
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
    public int sumaApgar1Minuto(){
        this.oCalificacion.setTotalMin(this.oCalificacion.getFcMin() + this.oCalificacion.getEsfuerzoRespiratorioMin() + this.oCalificacion.getTonoMuscularMin() + this.oCalificacion.getIrritabilidadMin() + this.oCalificacion.getColorMin());
        return this.oCalificacion.getTotalMin();
    }
    public int sumaApgar5Minutos(){
        this.oCalificacion.setTotal5Min(this.oCalificacion.getFc5Min() + this.oCalificacion.getEsfuerzoRespiratorio5Min() + this.oCalificacion.getTonoMuscular5Min() + this.oCalificacion.getIrritabilidad5Min() + this.oCalificacion.getColor5Min());
        return this.oCalificacion.getTotal5Min();
    }
    public int sumaSilvermann(){
        this.oSilvermann.setTotal(this.oSilvermann.getAleteoNasal() + this.oSilvermann.getQResp() + this.oSilvermann.getTInter() + this.oSilvermann.getRetraccion() + this.oSilvermann.getDisociacion());
        return this.oSilvermann.getTotal();
    }
    public float puntajeTotal(){
        this.oValoracion.setPuntageTotal(this.oValoracion.getValoracionPesoNacer() + this.oValoracion.getValoracionEdadMaterna() + this.oValoracion.getValoracionEdadGestacional() + this.oValoracion.getValoracionEmParto() + this.oValoracion.getValoracionApgar() + this.oValoracion.getValoracionReanimacion());
        return this.oValoracion.getPuntageTotal();
    }
    public String mensaje(){
        if(this.oValoracion.getPuntageTotal() > 30){
            this.oValoracion.setUnidadCorresp("UCIN");
            return "UCIN";
        }
        if(this.oValoracion.getPuntageTotal() >= 15 && this.oValoracion.getPuntageTotal() <= 30 ){
            this.oValoracion.setUnidadCorresp("CUIDADOS INTERMEDIOS");
            return "CUIDADOS INTERMEDIOS";
        }
        if(this.oValoracion.getPuntageTotal() < 15){
            this.oValoracion.setUnidadCorresp("ALOJAMIENTO CONJUNTO");
            return "ALOJAMIENTO CONJUNTO";
        }
        return null;
    }
    public void guardaReverso(){
        try{
            if(this.oDatosAtencion.guardaReverso(this.oCalificacion.getQueryCalficacionApgar(), this.oSilvermann.getCalificacionSilvermann(), this.oValoracion.getValoracion())){
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
    //***********TERMINAN LOS METODOS D CONTROL DE DATOS****************
    //***********INICIA LOS METODOS SET´S Y GET'S****************
    public AtencionNeonatalTococirugia getAtencionNeonatal(){
        return oAtencion;
    }
    public void setAtencionNeonatal(AtencionNeonatalTococirugia oAtencion){
        this.oAtencion = oAtencion;
    }
    public boolean getMuestraPantalla(){
        return bMuestraPantalla;
    }
    public void setMuestraPantalla(boolean bMuestraPantalla){
        this.bMuestraPantalla = bMuestraPantalla;
    }
    public AtencionNeonatalTococirugia[] getArrAtencion(){
        return arrAtencion;
    }
    public void setArrAtencion(AtencionNeonatalTococirugia[] arrAtencion){
        this.arrAtencion = arrAtencion;
    }
    public AtencionNeonatalTococirugia getDatosAtencion(){
        return oDatosAtencion;
    }
    public void setDatosAtencion(AtencionNeonatalTococirugia oDatosAtencion){
        this.oDatosAtencion = oDatosAtencion;
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
    public Date getFechaActual(){
        return new Date();
    }
    public DiagnosticoCIE10[] getArregloDiagnosticos(){
        return arrRet;
    }
    public void setArregloDiagnosticos(DiagnosticoCIE10[] arrRet){
        this.arrRet = arrRet;
    }
    public ArrayList<DiagnosticoCIE10> getDiagnosticocie10(){
        return arrDiagCie10;
    }
    public void borrarElementoCie10(DiagnosticoCIE10 obj){
        if(arrDiagCie10 != null || arrDiagCie10.isEmpty())
            if(arrDiagCie10.get(0).getClave().compareTo(obj.getClave()) == 0){
                this.oDatosAtencion.getDiagnosticos().setClave("");
                arrDiagCie10.remove(obj);
            }else{
                arrDiagCie10.remove(obj);
            }
    }
    public DiagnosticoCIE10 getDiagnostico(){
        return oDiagnostico;
    }
    public void setDiagnostico(DiagnosticoCIE10 oDiagnostico){
        this.oDiagnostico = oDiagnostico;
    }
    public CalificacionApgar getCalificacionApgar(){
        return oCalificacion;
    }
    public void setCalificacionApgar(CalificacionApgar oCalificacion){
        this.oCalificacion = oCalificacion;
    }
    public CalificacionSilvermann getCalificacionSilvermann(){
        return oSilvermann;
    }
    public void setCalificacionSilvermann(CalificacionSilvermann oSilvermann){
        this.oSilvermann = oSilvermann;
    }
    public ValoracionEpidemiologico getValoracion(){
        return oValoracion;
    }
    public void setValoracion(ValoracionEpidemiologico oValoracion){
        this.oValoracion = oValoracion;
    }
    //***********TERMINAN LOS METODOS SET´S Y GET'S****************
}

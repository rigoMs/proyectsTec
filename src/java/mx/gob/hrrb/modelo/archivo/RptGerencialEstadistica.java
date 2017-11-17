package mx.gob.hrrb.modelo.archivo;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.datos.AccesoDatos;


/**
 *
 * @author Danny
 */
public class RptGerencialEstadistica {
    private AccesoDatos oAD;
    private String sUsuFirm;
    private Date dFechaIni;
    private Date dFechaFin;
    private AreaServicioHRRB oArea;
    
    //PARA PARTO O CESAREA
    private int nEutocicos;
    private int nDistocicosVaginal;
    private int nCesarea;
    private int nTotalPartoOcesarea;
        //en adolescentes
        private int nAMenor15;
        private int nAEntre15_19;
    //NACIDOS VIVOS
        //de 22 a 36 semanas
        private int nNV22_36semMe2500Gr;
        private int nNV22_36semMa2500Gr;
        private int nNV22_36semPesoNE;
        //37 y mas semanas
        private int nNV37semMe2500Gr;
        private int nNV37semMa2500Gr;
        private int nNV37semPesoNE;
        //semanas no especificadas
        private int nNVnoEMe2500Gr;
        private int nNVnoEMa2500Gr;
        private int nNVnoPesoNE;
    //DEFUNCIONES FETALES
    private int nDF22_27sem;
    private int nDF28sem;
    private int nDFsemNE;
    //ABORTOS(MENOR A 22 SEMANAS)ATENDIDOS
    private int nAMe22semMe15a;
    private int nAMe22sem15_19a;
    private int nAMe22semMa20a;
    private int nAMen22semEdadNE;
    private int nTotalAbortosMen22sem;
    //ABORTOS (SEMANAS N.E.)ATENDIDOS
    private int nAsemNEMe15a;
    private int nAsemNE15_19a;
    private int nAsemNEMa20a;
    private int nAsemNEedadNE;
    private int nTotalAbortosSemNE; 
    //PLANIFICACION FAMILIAR
        //vasectomia tradicional
        private int nVTmen20a;
        private int nVT20_39a;
        private int nVTma40a;
        private int nVTedadNE;
        private int nTotalVT;
        //vasectomia sin bisturi
        private int nVSBmen20;
        private int nVSB20_39a;
        private int nVSBma40a;
        private int nVSBedadNE;
        private int nTotalVSB;
        //oclusion tubaria
        private int nOTmen20;
        private int nOT20_39a;
        private int nOTma40a;
        private int nOTedadNE;
        private int nTotalOT;
    //ACEPTANTES DE METODOS POSTEVENTOS OBSTETRICO
        //insercion DIU
        private int nDIUpostparto;
        private int nDIUtranscesarea;
        private int nDIUpostAborto;
        private int nDIUtotal;
        //ocluison tubaria
        private int nOTpostparto;
        private int nOTtranscesarea;
        private int nOTpostAborto;
        private int nOTtotal;
    private int nSoloHormonal;
    private int nImplanteSubdermico;
    private int nOtroMetodo;
    private int nAdolescenteAcepta;
    //INFORMACION POR SERVICIO
    private int nEgresos;
    private int nDiasEstancia; 
    private float nPromDiasEstancia;
    private int nDefunHospDentro48hrs;
    private int nDefunHospDespues48hrs;
    private int nCirugiaQuirofano;
    private int nCiruFueraQuirofano;
    //EGRESOS SEGUN MOTIVO
    private int nCuracion;
    private int nMejoria;
    private int nVoluntario;
    private int nOtroHosp;
    private int nDefuncion;
    private int nOtro;
    private int nTotalEgresos;
    //CORTA ESTANCIA = CE
    private int nDefunHospMaternasCE;
    private int nDefunHospRNacCE;
    private int nPersonasAtendidasCE;
    private int nPlanFamCE;
    private int nOtrasCiruCE;
    private int nTotalCirugiasCE;
    private int nPartosCE;
    
    public RptGerencialEstadistica(){
        HttpServletRequest r;
        //oHosp= new Hospitalizacion();
        oArea= new AreaServicioHRRB();
        r=(HttpServletRequest) FacesContext.getCurrentInstance().getExternalContext().getRequest();        
        if(r.getSession().getAttribute("oFirm")!=null)
            sUsuFirm = ((Firmado)r.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        
    }
    
    public void buscaNacimientosAtendidosPartoCesarea() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaNacimientosAtendidosPartoCesarea: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaNacimientosAtendidosPorPartoOcesarea('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setEutocicos(((Double)vRowTemp.get(0)).intValue());
                    setDistocicosVaginal(((Double)vRowTemp.get(1)).intValue());
                    setCesarea(((Double)vRowTemp.get(2)).intValue());
                    setTotalPartoOcesarea(((Double)vRowTemp.get(3)).intValue());
                    setAMenor15(((Double)vRowTemp.get(4)).intValue());
                    setAEntre15_19(((Double)vRowTemp.get(5)).intValue());
                }
            }
        }
    }
    
    public void buscaNacidosVivosPorSemanaGestacional() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaNacidosVivosPorSemanaGestacional: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaNacidosVivos('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setNV22_36semMe2500Gr(((Double)vRowTemp.get(0)).intValue());
                    setNV22_36semMa2500Gr(((Double)vRowTemp.get(1)).intValue());
                    setNV37semMe2500Gr(((Double)vRowTemp.get(2)).intValue());
                    setNV37semMa2500Gr(((Double)vRowTemp.get(3)).intValue());
                }
            }
        }
    }
    
    public void buscaDefuncionesFetales() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaDefuncionesFetales: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaDefuncionesFetales('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setDF22_27sem(((Double)vRowTemp.get(0)).intValue());
                    setDF28sem(((Double)vRowTemp.get(1)).intValue());
                    setAMe22semMe15a(((Double)vRowTemp.get(2)).intValue());
                    setAMe22sem15_19a(((Double)vRowTemp.get(3)).intValue());
                    setAMe22semMa20a(((Double)vRowTemp.get(4)).intValue());
                    setTotalAbortosMen22sem(((Double)vRowTemp.get(5)).intValue());
                }
            }
        }
    }
    
    public void buscaProcedimientosdePlanificacionFamiliar() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaProcedimientosdePlanificacionFamiliar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaProcedimientosdePlanificacionFamiliar('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setVTmen20a(((Double)vRowTemp.get(0)).intValue());
                    setVT20_39a(((Double)vRowTemp.get(1)).intValue());
                    setVTma40a(((Double)vRowTemp.get(2)).intValue());
                    setTotalVT(((Double)vRowTemp.get(3)).intValue());
                    setVSBmen20(((Double)vRowTemp.get(4)).intValue());
                    setVSB20_39a(((Double)vRowTemp.get(5)).intValue());
                    setVSBma40a(((Double)vRowTemp.get(6)).intValue());
                    setTotalVSB(((Double)vRowTemp.get(7)).intValue());
                    setOTmen20(((Double)vRowTemp.get(8)).intValue());
                    setOT20_39a(((Double)vRowTemp.get(9)).intValue());
                    setOTma40a(((Double)vRowTemp.get(10)).intValue());
                    setTotalOT(((Double)vRowTemp.get(11)).intValue());
                   
                }
            }
        }
    }
    
    public void buscaAceptantesDeMetodosAnticonceptivosPostEventoObstetrico() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaAceptantesDeMetodosAnticonceptivosPostEventoObstetrico: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaAceptantesDeMetodosAnticonceptivosPostEventoObstetrico('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setDIUpostparto(((Double)vRowTemp.get(0)).intValue());
                    setDIUtranscesarea(((Double)vRowTemp.get(1)).intValue());
                    setDIUpostAborto(((Double)vRowTemp.get(2)).intValue());
                    setDIUtotal(((Double)vRowTemp.get(3)).intValue());
                    setOTpostparto(((Double)vRowTemp.get(4)).intValue());
                    setOTtranscesarea(((Double)vRowTemp.get(5)).intValue());
                    setOTpostAborto(((Double)vRowTemp.get(6)).intValue());
                    setOTtotal(((Double)vRowTemp.get(7)).intValue());
                    setSoloHormonal(((Double)vRowTemp.get(8)).intValue());
                    setImplanteSubdermico(((Double)vRowTemp.get(9)).intValue());
                    setOtroMetodo(((Double)vRowTemp.get(10)).intValue());
                    setAdolescenteAcepta(((Double)vRowTemp.get(11)).intValue());
                }
            }
        }
    }
    
    public RptGerencialEstadistica[] buscaReporteEstadisticaCensoGerencial() throws Exception{
        RptGerencialEstadistica arrRet[] = null, oRpt=null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaReporteEstadisticaCensoGerencial: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaReporteEstadisticaCensoGerencial('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new RptGerencialEstadistica[rst.size()];
                for(i=0;i<rst.size();i++){
                    oRpt= new RptGerencialEstadistica();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oRpt.getArea().setClave(((Double)vRowTemp.get(0)).intValue());
                    oRpt.getArea().setDescripcion(((String)vRowTemp.get(1)));
                    oRpt.setEgresos(((Double)vRowTemp.get(2)).intValue());
                    oRpt.setDiasEstancia(((Double)vRowTemp.get(3)).intValue());
                    oRpt.setPromDiasEstancia(((Double)vRowTemp.get(4)).intValue());
                    oRpt.setDefunHospDentro48hrs(((Double)vRowTemp.get(5)).intValue());
                    oRpt.setDefunHospDespues48hrs(((Double)vRowTemp.get(6)).intValue());
                    oRpt.setCirugiaQuirofano(((Double)vRowTemp.get(7)).intValue());
                    oRpt.setCiruFueraQuirofano(((Double)vRowTemp.get(8)).intValue());
                    arrRet[i]=oRpt;
                }
            }
        }
        return arrRet;
    }
    
    public void buscaEgresosHospSegunMotivo() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaEgresosHospSegunMotivo: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaEgresosHospSegunMotivo('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setCuracion(((Double)vRowTemp.get(0)).intValue());
                    setMejoria(((Double)vRowTemp.get(1)).intValue());
                    setVoluntario(((Double)vRowTemp.get(2)).intValue());
                    setOtroHosp(((Double)vRowTemp.get(3)).intValue());
                    setDefuncion(((Double)vRowTemp.get(4)).intValue());
                    setOtro(((Double)vRowTemp.get(5)).intValue());
                    setTotalEgresos(((Double)vRowTemp.get(6)).intValue());
                }
            }
        }
    }
    
    public void buscaRptDefuncionesHospitalarias() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaRptDefuncionesHospitalarias: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaRptDefuncionesHospitalarias('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setDefunHospMaternasCE(((Double)vRowTemp.get(0)).intValue());
                    setDefunHospRNacCE(((Double)vRowTemp.get(1)).intValue());
                }
            }
        }
    }
    
    public void buscaRptCortaEstancia() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(dFechaIni==null && dFechaFin==null){
            throw new Exception("RptGerencialEstadistica.buscaRptCortaEstancia: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaRptCortaEstancia('"+fFech.format(dFechaIni)+"'::DATE,'"+fFech.format(dFechaFin)+"'::DATE);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    setPersonasAtendidasCE(((Double)vRowTemp.get(0)).intValue());
                    setPlanFamCE(((Double)vRowTemp.get(1)).intValue());
                    setOtrasCiruCE(((Double)vRowTemp.get(2)).intValue());
                    setTotalCirugiasCE(((Double)vRowTemp.get(3)).intValue());
                    setPartosCE(((Double)vRowTemp.get(4)).intValue());
                }
            }
        }
    }
    
    public Date getFechaIni() {
        return dFechaIni;
    }

    public void setFechaIni(Date dFechaIni) {
        this.dFechaIni = dFechaIni;
    }

    public Date getFechaFin() {
        return dFechaFin;
    }

    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    public int getEutocicos() {
        return nEutocicos;
    }

    public void setEutocicos(int nEutocicos) {
        this.nEutocicos = nEutocicos;
    }

    public int getDistocicosVaginal() {
        return nDistocicosVaginal;
    }

    public void setDistocicosVaginal(int nDistocicosVaginal) {
        this.nDistocicosVaginal = nDistocicosVaginal;
    }

    public int getCesarea() {
        return nCesarea;
    }

    public void setCesarea(int nCesarea) {
        this.nCesarea = nCesarea;
    }

    public int getAMenor15() {
        return nAMenor15;
    }

    public void setAMenor15(int nAMenor15) {
        this.nAMenor15 = nAMenor15;
    }

    public int getAEntre15_19() {
        return nAEntre15_19;
    }

    public void setAEntre15_19(int nAEntre15_19) {
        this.nAEntre15_19 = nAEntre15_19;
    }

    public int getNV22_36semMe2500Gr() {
        return nNV22_36semMe2500Gr;
    }

    public void setNV22_36semMe2500Gr(int nNV22_36semMe2500Gr) {
        this.nNV22_36semMe2500Gr = nNV22_36semMe2500Gr;
    }

    public int getNV22_36semMa2500Gr() {
        return nNV22_36semMa2500Gr;
    }

    public void setNV22_36semMa2500Gr(int nNV22_36semMa2500Gr) {
        this.nNV22_36semMa2500Gr = nNV22_36semMa2500Gr;
    }

    public int getNV22_36semPesoNE() {
        return nNV22_36semPesoNE;
    }

    public void setNV22_36semPesoNE(int nNV22_36semPesoNE) {
        this.nNV22_36semPesoNE = nNV22_36semPesoNE;
    }

    public int getNV37semMe2500Gr() {
        return nNV37semMe2500Gr;
    }

    public void setNV37semMe2500Gr(int nNV37semMe2500Gr) {
        this.nNV37semMe2500Gr = nNV37semMe2500Gr;
    }

    public int getNV37semMa2500Gr() {
        return nNV37semMa2500Gr;
    }

    public void setNV37semMa2500Gr(int nNV37semMa2500Gr) {
        this.nNV37semMa2500Gr = nNV37semMa2500Gr;
    }

    public int getNV37semPesoNE() {
        return nNV37semPesoNE;
    }

    public void setNV37semPesoNE(int nNV37semPesoNE) {
        this.nNV37semPesoNE = nNV37semPesoNE;
    }

    public int getNVnoEMe2500Gr() {
        return nNVnoEMe2500Gr;
    }

    public void setNVnoEMe2500Gr(int nNVnoEMe2500Gr) {
        this.nNVnoEMe2500Gr = nNVnoEMe2500Gr;
    }

    public int getNVnoEMa2500Gr() {
        return nNVnoEMa2500Gr;
    }

    public void setNVnoEMa2500Gr(int nNVnoEMa2500Gr) {
        this.nNVnoEMa2500Gr = nNVnoEMa2500Gr;
    }

    public int getNVnoPesoNE() {
        return nNVnoPesoNE;
    }

    public void setNVnoPesoNE(int nNVnoPesoNE) {
        this.nNVnoPesoNE = nNVnoPesoNE;
    }

    public int getDF22_27sem() {
        return nDF22_27sem;
    }

    public void setDF22_27sem(int nDF22_27sem) {
        this.nDF22_27sem = nDF22_27sem;
    }

    public int getDF28sem() {
        return nDF28sem;
    }

    public void setDF28sem(int nDF28sem) {
        this.nDF28sem = nDF28sem;
    }

    public int getDFsemNE() {
        return nDFsemNE;
    }

    public void setDFsemNE(int nDFsemNE) {
        this.nDFsemNE = nDFsemNE;
    }

    public int getAMe22semMe15a() {
        return nAMe22semMe15a;
    }

    public void setAMe22semMe15a(int nAMe22semMe15a) {
        this.nAMe22semMe15a = nAMe22semMe15a;
    }

    public int getAMe22sem15_19a() {
        return nAMe22sem15_19a;
    }

    public void setAMe22sem15_19a(int nAMe22sem15_19a) {
        this.nAMe22sem15_19a = nAMe22sem15_19a;
    }

    public int getAMe22semMa20a() {
        return nAMe22semMa20a;
    }

    public void setAMe22semMa20a(int nAMe22semMa20a) {
        this.nAMe22semMa20a = nAMe22semMa20a;
    }

    public int getAMen22semEdadNE() {
        return nAMen22semEdadNE;
    }

    public void setAMen22semEdadNE(int nAMen22semEdadNE) {
        this.nAMen22semEdadNE = nAMen22semEdadNE;
    }

    public int getTotalAbortosMen22sem() {
        return nTotalAbortosMen22sem;
    }

    public void setTotalAbortosMen22sem(int nTotalAbortosMen22sem) {
        this.nTotalAbortosMen22sem = nTotalAbortosMen22sem;
    }

    public int getAsemNEMe15a() {
        return nAsemNEMe15a;
    }

    public void setAsemNEMe15a(int nAsemNEMe15a) {
        this.nAsemNEMe15a = nAsemNEMe15a;
    }

    public int getAsemNE15_19a() {
        return nAsemNE15_19a;
    }

    public void setAsemNE15_19a(int nAsemNE15_19a) {
        this.nAsemNE15_19a = nAsemNE15_19a;
    }

    public int getAsemNEMa20a() {
        return nAsemNEMa20a;
    }

    public void setAsemNEMa20a(int nAsemNEMa20a) {
        this.nAsemNEMa20a = nAsemNEMa20a;
    }

    public int getAsemNEedadNE() {
        return nAsemNEedadNE;
    }

    public void setAsemNEedadNE(int nAsemNEedadNE) {
        this.nAsemNEedadNE = nAsemNEedadNE;
    }

    public int getTotalAbortosSemNE() {
        return nTotalAbortosSemNE;
    }

    public void setTotalAbortosSemNE(int nTotalAbortosSemNE) {
        this.nTotalAbortosSemNE = nTotalAbortosSemNE;
    }

    public int getVTmen20a() {
        return nVTmen20a;
    }

    public void setVTmen20a(int nVTmen20a) {
        this.nVTmen20a = nVTmen20a;
    }

    public int getVT20_39a() {
        return nVT20_39a;
    }

    public void setVT20_39a(int nVT20_39a) {
        this.nVT20_39a = nVT20_39a;
    }

    public int getVTma40a() {
        return nVTma40a;
    }

    public void setVTma40a(int nVTma40a) {
        this.nVTma40a = nVTma40a;
    }

    public int getVTedadNE() {
        return nVTedadNE;
    }

    public void setVTedadNE(int nVTedadNE) {
        this.nVTedadNE = nVTedadNE;
    }

    public int getTotalVT() {
        return nTotalVT;
    }

    public void setTotalVT(int nTotalVT) {
        this.nTotalVT = nTotalVT;
    }

    public int getVSBmen20() {
        return nVSBmen20;
    }

    public void setVSBmen20(int nVSBmen20) {
        this.nVSBmen20 = nVSBmen20;
    }

    public int getVSB20_39a() {
        return nVSB20_39a;
    }

    public void setVSB20_39a(int nVSB20_39a) {
        this.nVSB20_39a = nVSB20_39a;
    }

    public int getVSBma40a() {
        return nVSBma40a;
    }

    public void setVSBma40a(int nVSBma40a) {
        this.nVSBma40a = nVSBma40a;
    }

    public int getVSBedadNE() {
        return nVSBedadNE;
    }

    public void setVSBedadNE(int nVSBedadNE) {
        this.nVSBedadNE = nVSBedadNE;
    }

    public int getTotalVSB() {
        return nTotalVSB;
    }

    public void setTotalVSB(int nTotalVSB) {
        this.nTotalVSB = nTotalVSB;
    }

    public int getOTmen20() {
        return nOTmen20;
    }

    public void setOTmen20(int nOTmen20) {
        this.nOTmen20 = nOTmen20;
    }

    public int getOT20_39a() {
        return nOT20_39a;
    }

    public void setOT20_39a(int nOT20_39a) {
        this.nOT20_39a = nOT20_39a;
    }

    public int getOTma40a() {
        return nOTma40a;
    }

    public void setOTma40a(int nOTma40a) {
        this.nOTma40a = nOTma40a;
    }

    public int getOTedadNE() {
        return nOTedadNE;
    }

    public void setOTedadNE(int nOTedadNE) {
        this.nOTedadNE = nOTedadNE;
    }

    public int getTotalOT() {
        return nTotalOT;
    }

    public void setTotalOT(int nTotalOT) {
        this.nTotalOT = nTotalOT;
    }

    public int getDIUpostparto() {
        return nDIUpostparto;
    }

    public void setDIUpostparto(int nDIUpostparto) {
        this.nDIUpostparto = nDIUpostparto;
    }

    public int getDIUtranscesarea() {
        return nDIUtranscesarea;
    }

    public void setDIUtranscesarea(int nDIUtranscesarea) {
        this.nDIUtranscesarea = nDIUtranscesarea;
    }

    public int getDIUpostAborto() {
        return nDIUpostAborto;
    }

    public void setDIUpostAborto(int nDIUpostAborto) {
        this.nDIUpostAborto = nDIUpostAborto;
    }

    public int getDIUtotal() {
        return nDIUtotal;
    }

    public void setDIUtotal(int nDIUtotal) {
        this.nDIUtotal = nDIUtotal;
    }
    
    public int getOTpostparto() {
        return nOTpostparto;
    }

    public void setOTpostparto(int nOTpostparto) {
        this.nOTpostparto = nOTpostparto;
    }

    public int getOTtranscesarea() {
        return nOTtranscesarea;
    }

    public void setOTtranscesarea(int nOTtranscesarea) {
        this.nOTtranscesarea = nOTtranscesarea;
    }

    public int getOTpostAborto() {
        return nOTpostAborto;
    }

    public void setOTpostAborto(int nOTpostAborto) {
        this.nOTpostAborto = nOTpostAborto;
    }


    public int getOTtotal() {
        return nOTtotal;
    }


    public void setOTtotal(int nOTtotal) {
        this.nOTtotal = nOTtotal;
    }


    public int getSoloHormonal() {
        return nSoloHormonal;
    }

    public void setSoloHormonal(int nSoloHormonal) {
        this.nSoloHormonal = nSoloHormonal;
    }

    public int getImplanteSubdermico() {
        return nImplanteSubdermico;
    }

    public void setImplanteSubdermico(int nImplanteSubdermico) {
        this.nImplanteSubdermico = nImplanteSubdermico;
    }

    public int getAdolescenteAcepta() {
        return nAdolescenteAcepta;
    }

    public void setAdolescenteAcepta(int nAdolescenteAcepta) {
        this.nAdolescenteAcepta = nAdolescenteAcepta;
    }

    public int getEgresos() {
        return nEgresos;
    }

    public void setEgresos(int nEgresos) {
        this.nEgresos = nEgresos;
    }

    public int getDiasEstancia() {
        return nDiasEstancia;
    }

    public void setDiasEstancia(int nDiasEstancia) {
        this.nDiasEstancia = nDiasEstancia;
    }

    public float getPromDiasEstancia() {
        return nPromDiasEstancia;
    }

    public void setPromDiasEstancia(float nPromDiasEstancia) {
        this.nPromDiasEstancia = nPromDiasEstancia;
    }

    public int getDefunHospDentro48hrs() {
        return nDefunHospDentro48hrs;
    }

    public void setDefunHospDentro48hrs(int nDefunHospDentro48hrs) {
        this.nDefunHospDentro48hrs = nDefunHospDentro48hrs;
    }
 
    public int getDefunHospDespues48hrs() {
        return nDefunHospDespues48hrs;
    }

    public void setDefunHospDespues48hrs(int nDefunHospDespues48hrs) {
        this.nDefunHospDespues48hrs = nDefunHospDespues48hrs;
    }

    public int getCirugiaQuirofano() {
        return nCirugiaQuirofano;
    }

    public void setCirugiaQuirofano(int nCirugiaQuirofano) {
        this.nCirugiaQuirofano = nCirugiaQuirofano;
    }

    public int getCiruFueraQuirofano() {
        return nCiruFueraQuirofano;
    }

    public void setCiruFueraQuirofano(int nCiruFueraQuirofano) {
        this.nCiruFueraQuirofano = nCiruFueraQuirofano;
    }

    public int getCuracion() {
        return nCuracion;
    }
     
    public void setCuracion(int nCuracion) {
        this.nCuracion = nCuracion;
    }

    public int getMejoria() {
        return nMejoria;
    }
 
    public void setMejoria(int nMejoria) {
        this.nMejoria = nMejoria;
    }
 
    public int getVoluntario() {
        return nVoluntario;
    }

    public void setVoluntario(int nVoluntario) {
        this.nVoluntario = nVoluntario;
    }

    public int getOtroHosp() {
        return nOtroHosp;
    }

    public void setOtroHosp(int nOtroHosp) {
        this.nOtroHosp = nOtroHosp;
    }

    public int getDefuncion() {
        return nDefuncion;
    }

    public void setDefuncion(int nDefuncion) {
        this.nDefuncion = nDefuncion;
    }

    public int getOtro() {
        return nOtro;
    }
 
    public void setOtro(int nOtro) {
        this.nOtro = nOtro;
    }

    public int getTotalEgresos() {
        return nTotalEgresos;
    }

    public void setTotalEgresos(int nTotalEgresos) {
        this.nTotalEgresos = nTotalEgresos;
    }

    public int getDefunHospMaternasCE() {
        return nDefunHospMaternasCE;
    }

    public void setDefunHospMaternasCE(int nDefunHospMaternasCE) {
        this.nDefunHospMaternasCE = nDefunHospMaternasCE;
    }

    public int getDefunHospRNacCE() {
        return nDefunHospRNacCE;
    }

    public void setDefunHospRNacCE(int nDefunHospRNacCE) {
        this.nDefunHospRNacCE = nDefunHospRNacCE;
    }

    public int getPersonasAtendidasCE() {
        return nPersonasAtendidasCE;
    }

    public void setPersonasAtendidasCE(int nPersonasAtendidasCE) {
        this.nPersonasAtendidasCE = nPersonasAtendidasCE;
    }

    public int getPlanFamCE() {
        return nPlanFamCE;
    }

    public void setPlanFamCE(int nPlanFamCE) {
        this.nPlanFamCE = nPlanFamCE;
    }

    public int getPartosCE() {
        return nPartosCE;
    }

    public void setPartosCE(int nPartosCE) {
        this.nPartosCE = nPartosCE;
    }

    public int getTotalCirugiasCE() {
        return nTotalCirugiasCE;
    }

    public void setTotalCirugiasCE(int nTotalCirugiasCE) {
        this.nTotalCirugiasCE = nTotalCirugiasCE;
    }    

    public int getTotalPartoOcesarea() {
        return nTotalPartoOcesarea;
    }

    public void setTotalPartoOcesarea(int nTotalPartoOcesarea) {
        this.nTotalPartoOcesarea = nTotalPartoOcesarea;
    }
    
    public int getOtroMetodo() {
        return nOtroMetodo;
    }

    public void setOtroMetodo(int nOtroMetodo) {
        this.nOtroMetodo = nOtroMetodo;
    }

    public AreaServicioHRRB getArea() {
        return oArea;
    }

    public void setArea(AreaServicioHRRB oArea) {
        this.oArea = oArea;
    }

    public int getOtrasCiruCE() {
        return nOtrasCiruCE;
    }

    public void setOtrasCiruCE(int nOtrasCiruCE) {
        this.nOtrasCiruCE = nOtrasCiruCE;
    }
}
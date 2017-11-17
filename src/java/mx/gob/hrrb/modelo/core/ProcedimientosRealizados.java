package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.text.DateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.almacen.ValeMaterial;
import mx.gob.hrrb.modelo.cir.AnestesiaEspecifica;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.modelo.hospitalizacion.Quirofano;
import mx.gob.hrrb.modelo.serv.ProductoHemoderivado;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.modelo.serv.SolicitudSangre;


/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/
public class ProcedimientosRealizados extends ServicioRealizado implements Serializable {

private Date dDuracion;
private Date dFechaRealizacion;
private Parametrizacion oOrden;
private Parametrizacion oTipoAnestesia;
private Medico oCirujano;
private EpisodioMedico oEpisodioMedico;
private ProcedimientoCIE9 oCIE9;
private String sTipoAnestesiaP;
private String sQuirofano;
private String sDuracionP;      
/*Agregados para Fase II*/
private AnestesiaEspecifica oAnestEspecifica;
private Date dFechaProgramada;
private String sNota;
private String sNotaPreanestesica;
private String sNotaResultado;
private String sDuracionEsperada;
private String sTipoTurno;
private ProcedimientoCIE9 oCIE9Realizado;
private int sCantSangreQx;
private int sCantSangreRes;
private Date dFechaPropInternacion;
private Paciente oPaciente;
private Quirofano oQuirofanos;
private Medico oAnestesio;
private Medico oResidente;
private Parametrizacion oValorHorarioQx;
private String sTipoCir;
private String sEstTransProb;
private SolicitudSangre oSolSangre;
private ProductoHemoderivado oProdHem;
private int nOrdenP;
private Parametrizacion oValorCausaDifQx;
private Parametrizacion oValorTipoQx;
private String sFechaInternacion;
private ValeMaterial oValeMat;
private SolicitudSangre oSolicitudSangre;

    /*AGREGADOS FASE TRES*/
    private String sResultado;
    private Date dFechaEntrada;
    private Date dFechaSalida;
    private PersonalHospitalario oCirculante;
    private Medico oCAyudanteUno;
    private Medico oCAyudanteDos;
    private Medico oInstrumentista;
    private Date dfechavalanestesica;
        
        
    public ProcedimientosRealizados(){
        this.oAnestEspecifica = new AnestesiaEspecifica();
        this.oCirculante = new PersonalHospitalario();
        this.oCAyudanteUno = new Medico();
        this.oCAyudanteDos = new Medico();
        this.oInstrumentista = new Medico();
        this.oAnestesio = new Medico();
    }

    public void inicializar(){
        oCIE9 = new ProcedimientoCIE9();
        oCirujano = new Medico();
        oPaciente = new Paciente();
        oEpisodioMedico = new EpisodioMedico();
        this.oAnestEspecifica = new AnestesiaEspecifica();
        this.oCirculante = new PersonalHospitalario();
        this.oCAyudanteUno = new Medico();
        this.oCAyudanteDos = new Medico();
        this.oInstrumentista = new Medico();
        this.oAnestesio = new Medico();
        
        HttpServletRequest req;
            req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                    sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
            }
    }

        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveProcedimientosRealizados();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				bRet = true;
			}
		} 
		return bRet; 
	} 
	
    @Override
    public ProcedimientosRealizados[] buscarTodos() throws Exception{
    ProcedimientosRealizados arrRet[]=null, oProcedimientosRealizados=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosProcedimientosRealizados();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new ProcedimientosRealizados[rst.size()];
                    for (i = 0; i < rst.size(); i++) {

                    } 
            } 
            return arrRet; 
    } 

    @Override
    public int insertar() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";


             if( this==null){   //completar llave
                    throw new Exception("ProcedimientosRealizados.insertar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaProcedimientosRealizados();"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)rst.get(0)).intValue();

                            }
                    }
            } 
            return nRet; 
    } 

    @Override
    public int modificar() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaProcedimientosRealizados();"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)rst.get(0)).intValue();
                            }
                    }
            } 
            return nRet; 
    } 

    
    public int modificarConjunto(ArrayList<ProcedimientosRealizados> arr) throws Exception{
        System.out.println("Estamos en ModificarConjunto");
        int nRet = -1;
        ArrayList<String> arrAfectaciones = new ArrayList<String>();  
        
        if(arr == null || arr.isEmpty())
            throw new Exception("ProcedimientosRealizados.modicarConjunto: Faltan Datos");
        else{
            for(ProcedimientosRealizados oProcedimientos: arr){
                if(oProcedimientos.getValorTipoQx().getValor() != null )
                    arrAfectaciones.add(oProcedimientos.getModificarTabla());
            }
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                nRet = oAD.ejecutarConsultaComando(arrAfectaciones); 
                oAD.desconectar(); 
            }
            oAD = null;
            }
        return nRet;
    }
    
    public String getModificarTabla() throws Exception{
        String sQuery = "";        
        if(getValorTipoQx().getValor().equals("")){
            throw new Exception("ProcedimientosRealizados.getModificarTabla: error de programación, Faltan datos");
        }else{
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoReporteTipo('"+sUsuarioFirmado+"',"
                    +""+oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +""+oEpisodioMedico.getClaveEpisodio()+","
                    +"'"+oCIE9.getClave().trim()+"',"
                    + getIdentificador()+","
                    +"'"+oValorTipoQx.getValor()+"');";
        }
        return sQuery;
    }

    @Override
    public int eliminar() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("ProcedimientosRealizados.eliminar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaProcedimientosRealizados();"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)rst.get(0)).intValue();
                            }
                    }
            } 
            return nRet; 
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    //Órdenes de servicio
    public int insertaSolProcedimientoQuirurgico() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
    System.out.println("Dentro de insertaSolProcedimientoQuirurgico");
            if(this==null){   //completar llave
                throw new Exception("ProcedimientosRealizados.insertar: error de programación, faltan datos");
            }else{
                System.out.println("Dentro del Else");
                    sQuery = "SELECT * FROM insertasolicitudprocedimientoquirurgico('"+ sUsuarioFirmado +"'::character varying,"
                            + getEpisodioMedico().getPaciente().getFolioPaciente() + "::bigint,"
                            + getEpisodioMedico().getClaveEpisodio() + "::bigint,"
                            + getAutorizadoPor().getNoTarjeta() + "::integer,'"
                            + getEpisodioMedico().getProceRe1().getCIE9().getClave() + "','"
                            + getDuracionP() + "'::character varying,'"
                            + getNota() + "'::text,'"
                            + getTipoCir() + "','"
                            + getSolSangre().getTipoSangre().getClaveParametro() + "','"
                            + getSolSangre().getRH().getClaveParametro() + "','"
                            + getProdHem().getClave() + "'::character varying,"
                            + getCantSangreRes() + ");";
                    System.out.println(sQuery);
                    oAD=new AccesoDatos();
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)vRowTemp.get(0)).intValue();
                            }
                    }
            } 
            return nRet; 
    }

    public ProcedimientosRealizados[] buscarUltimoProcedimientoReal(long FolioPac, long CveEpiMed) throws Exception{
    ProcedimientosRealizados arrRet[]=null, oProcReal=null;
    ArrayList rst = null;
    ArrayList<ProcedimientosRealizados> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
             if( this==null){
                    throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
            }else{
                        sQuery = "SELECT * FROM buscarultimoprocedimientoagregado("+FolioPac+"::bigint,"+CveEpiMed+"::bigint);";
                        System.out.println(sQuery);
                    oAD=new AccesoDatos();

                    if (oAD.conectar()){
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                        vObj = new ArrayList<ProcedimientosRealizados>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oProcReal=new ProcedimientosRealizados();
                            this.oCIE9.setClave((String) vRowTemp.get(0));
                            this.oCIE9.setDescripcion((String) vRowTemp.get(1));
                            this.setOrdenP(((Double) vRowTemp.get(2)).shortValue());

                            vObj.add(oProcReal);                              
                        }
                        nTam = vObj.size();
                        arrRet = new ProcedimientosRealizados[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
    }
    
    public ProcedimientosRealizados[] buscaProcedimientosReceta(Medico noTarjeta) throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "", edad = "";
        int i = 0, nTam = 0;
        int noTar = noTarjeta.getNoTarjeta();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        if(this.getFechaProgramada() == null){
            throw new Exception(
                   "ProcedimientosRealizados.buscaProcedimientosReceta: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaProcedimientoRealizadoReceta("+noTar+",'"+fecha.format(this.getFechaProgramada())+"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oProcedimientos = new ProcedimientosRealizados();
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oEpisodioMedico.setDiagIngreso(new DiagnosticoCIE10());
                    //Empieza a mandarse los datos
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(1));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(2));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(3));
                    oProcedimientos.oEpisodioMedico.getPaciente().setCurp((String) vRowTemp.get(4));
                    oProcedimientos.oEpisodioMedico.getPaciente().setCalleNum((String) vRowTemp.get(5));
                    oProcedimientos.oEpisodioMedico.getPaciente().setColonia((String) vRowTemp.get(6));
                    oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(7));
                    edad=(String)vRowTemp.get(7);
                    if (edad.compareTo("")!=0){
                        if(edad.substring(0, 3).compareTo("000")!=0){
                            if (edad.charAt(0)=='0')
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                        }else{  
                            if (edad.substring(4, 6).compareTo("00")!=0)
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(8)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(9));
                    oProcedimientos.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(10)).longValue());
                    oProcedimientos.setIdentificador(((Double) vRowTemp.get(11)).intValue());
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(12));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setClave((String) vRowTemp.get(13));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(14));
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(15));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(16));
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(17)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(18));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(19));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(20));
                    oProcedimientos.oAnestesio.setCurp((String) vRowTemp.get(21));
                    vObj.add(oProcedimientos); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    public ProcedimientosRealizados[] buscarProcedimientosRealPac(long FolioPac, long CveEpiMed) throws Exception{
    ProcedimientosRealizados arrRet[]=null, oProcReal=null;
    ArrayList rst = null;
    ArrayList<ProcedimientosRealizados> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
        if(FolioPac==0 || CveEpiMed==0){
            throw new Exception("ProcedimientosRealizados.buscarProcedimientosRealPac: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarprocedimientosrealizadospaciente("+FolioPac+"::bigint,"+CveEpiMed+"::bigint);";
            System.out.println(sQuery);
            oAD=new AccesoDatos();

            if (oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar(); 
            }
            if (rst != null) {
                vObj = new ArrayList<ProcedimientosRealizados>();
                for (i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oProcReal=new ProcedimientosRealizados();
                    oProcReal.setCIE9(new ProcedimientoCIE9());
                    oProcReal.oCIE9.setClave((String) vRowTemp.get(0));
                    oProcReal.oCIE9.setDescripcion((String) vRowTemp.get(1));
                    System.out.println(oProcReal.oCIE9.getDescripcion());
                    oProcReal.setOrdenP(((Double) vRowTemp.get(2)).shortValue());
                    vObj.add(oProcReal); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    public ProcedimientosRealizados[] buscarUltimoProcedimientoRealMatOst(long FolioPac, long CveEpiMed) throws Exception{
	ProcedimientosRealizados arrRet[]=null, oProcReal=null;
	ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		 if( this==null){
			throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
		}else{
                            sQuery = "SELECT * FROM buscarultimoprocedimientoagregado("+FolioPac+"::bigint,"+CveEpiMed+"::bigint);";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<ProcedimientosRealizados>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oProcReal=new ProcedimientosRealizados();
                                oProcReal.setCIE9(new ProcedimientoCIE9());
                                oProcReal.oCIE9.setClave((String) vRowTemp.get(0));
                                oProcReal.oCIE9.setDescripcion((String) vRowTemp.get(1));
                                oProcReal.setOrdenP(((Double) vRowTemp.get(2)).shortValue());
                                
				vObj.add(oProcReal);                              
                            }
                            nTam = vObj.size();
                            arrRet = new ProcedimientosRealizados[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
    
    public ProcedimientosRealizados[] buscaPacientaValoracionPre(
            String nom, String app, String apm, int numEx) throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "";
        String edad = "";
        int i = 0, nTam = 0;
        oEpisodioMedico = new EpisodioMedico();
        oEpisodioMedico.getPaciente().setNombres(nom);
        oEpisodioMedico.getPaciente().setApPaterno(app);
        oEpisodioMedico.getPaciente().setApMaterno(apm);
        oEpisodioMedico.setExpediente(new Expediente());
        oEpisodioMedico.getExpediente().setNumero(numEx);
        
        if( oEpisodioMedico.getPaciente().getNombres().equals("") ||
            oEpisodioMedico.getPaciente().getApPaterno().equals("") || 
            oEpisodioMedico.getExpediente().getNumero() == 0 ){
            throw new Exception("ProcedimientosRealizados.buscaPacientaValoracionPre: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaDatosPacienteValoracionPreanestesica('"+oEpisodioMedico.getPaciente().getNombres()+"','"
                    +oEpisodioMedico.getPaciente().getApPaterno()+"','"+oEpisodioMedico.getPaciente().getApMaterno()+"',"+oEpisodioMedico.getExpediente()+");";
        
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i = 0;  i < rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oProcedimientos = new ProcedimientosRealizados();
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oPaciente = new Paciente();
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    // Inicio
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(1));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(2));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(3));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(4)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(5));
                    oProcedimientos.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(6)).longValue());
                    oProcedimientos.setIdentificador(((Double) vRowTemp.get(7)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNac((Date) vRowTemp.get(8));
                    oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                    edad = (String)vRowTemp.get(9);
                    if(edad.compareTo("") != 0){
                        if(edad.substring(0,3).compareTo("000")!=0){
                            if(edad.charAt(0) == '0')
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                            else 
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                        }else{
                            if(edad.substring(4, 6).compareTo("00")!=0)
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                            else
                                oProcedimientos.oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oProcedimientos.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(10));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setClave((String) vRowTemp.get(11));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(12));
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(13));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(14));
                    vObj.add(oProcedimientos);
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    ////////////////////////////////////////////////////////////////////////////////
    
	public Date getDuracion() { return dDuracion; }
	public void setDuracion(Date valor) { dDuracion=valor; }
	public Date getFechaRealizacion() { return dFechaRealizacion; }
	public void setFechaRealizacion(Date valor) { dFechaRealizacion=valor; }
	public Parametrizacion getOrden() { return oOrden; }
	public void setOrden(Parametrizacion valor) { oOrden=valor; }
	public Parametrizacion getTipoAnestesia() { return oTipoAnestesia; }
	public void setTipoAnestesia(Parametrizacion valor) { oTipoAnestesia=valor; }
	public Medico getCirujano() { return oCirujano; }
	public void setCirujano(Medico valor) { oCirujano=valor; }
	public EpisodioMedico getEpisodioMedico() { return oEpisodioMedico; }
	public void setEpisodioMedico(EpisodioMedico valor) { oEpisodioMedico=valor; }
    public ProcedimientoCIE9 getCIE9() { return oCIE9; }
    public void setCIE9(ProcedimientoCIE9 oCIE9) { this.oCIE9 = oCIE9; }
    public String getTipoAnestesiaP() { return sTipoAnestesiaP; }
    public void setTipoAnestesiaP(String sTipoAnestesiaP) { this.sTipoAnestesiaP = sTipoAnestesiaP; }
    public String getQuirofano() { return sQuirofano;}
    public void setQuirofano(String sQuirofano) { this.sQuirofano = sQuirofano; }
    public String getDuracionP() { return sDuracionP; }
    public void setDuracionP(String sDuracionP) { this.sDuracionP = sDuracionP; }
    public AnestesiaEspecifica getAnestEspecifica(){ return this.oAnestEspecifica; }
    public void setAnestEspecifica(AnestesiaEspecifica valor){ this.oAnestEspecifica = valor; }

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oCIE9;
    }
    
    public ProcedimientosRealizados[] buscaServicioRealizadoSolicitudxFechas(Date dFec, int Clv) throws Exception{
        ProcedimientosRealizados oProcedimiento, arrRet[] = null;
        ArrayList rst = null;
        ArrayList rstMat = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        DetalleValeMaterial oDetalle;    
        String sQuery = "", edad = "", sx = "", situacion="";
        int i = 0, nTam = 0, j=0;       
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("La fecha recibida es ---> "+ dFec);
        if(dFec == null){
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaServicioRealizadoSolicitudxFechas('"+fd.format(dFec)+"',"+Clv+");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i = 0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oProcedimiento = new ProcedimientosRealizados();
                    oProcedimiento.oEpisodioMedico = new EpisodioMedico();
                    oProcedimiento.oEpisodioMedico.setPaciente(new Paciente());
                    oProcedimiento.oCIE9 = new ProcedimientoCIE9();
                    oProcedimiento.setSituacion(new Parametrizacion());
                    oProcedimiento.oValeMat = new ValeMaterial();
                    oProcedimiento.oValeMat.setArrDetalle(new ArrayList<DetalleValeMaterial>());
                    //Inicio del los datos
                    oProcedimiento.setFechaSolicitud((Date) vRowTemp.get(0));
                    oProcedimiento.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                    //Datos del Paciente
                    oProcedimiento.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(2)).longValue());
                    oProcedimiento.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(3));
                    oProcedimiento.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(4));
                    oProcedimiento.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(5));
                    oProcedimiento.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(6));
                    oProcedimiento.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(7)).intValue());
                    oProcedimiento.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(8));
                    sx = (String)vRowTemp.get(8);
                    if(sx.compareTo("M") == 0)
                        oProcedimiento.oEpisodioMedico.getPaciente().setSexoP("MASCULINO");
                        else 
                            oProcedimiento.oEpisodioMedico.getPaciente().setSexoP("FEMENINO");
                        oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                        edad=(String)vRowTemp.get(9);
                        if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{  
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                        }
                        oProcedimiento.oEpisodioMedico.getDiagIngreso().setClave((String) vRowTemp.get(10));
                        oProcedimiento.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(11));
                        oProcedimiento.oCIE9.setClave((String) vRowTemp.get(12));
                        oProcedimiento.oCIE9.setDescripcion((String) vRowTemp.get(13));
                        oProcedimiento.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(14)).longValue());
                        oProcedimiento.oEpisodioMedico.getArea().setDescripcion((String) vRowTemp.get(15));
                        oProcedimiento.oValeMat.setFolioVale(((Double) vRowTemp.get(16)).intValue());
                        oProcedimiento.oValeMat.setFechaSurtido((Date) vRowTemp.get(17));
                        oProcedimiento.oValeMat.setSituacionVale((String) vRowTemp.get(18));
                        situacion = (String) vRowTemp.get(18);
                        if(situacion.compareTo("1") == 0){
                            oProcedimiento.oValeMat.setSituacionVale("Vale Cancelado");
                        }else{
                            if(situacion.compareTo("0") ==0)
                                oProcedimiento.oValeMat.setSituacionVale("Esperando Material");
                        }
                        vObj.add(oProcedimiento);
                }//fin del ciclo 
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            } 
        }
        return arrRet;
    }
    
    public ProcedimientosRealizados[] buscarSolicitudesFecha(Date dFec) throws Exception {
        ProcedimientosRealizados oProRealizado, arrRet[] = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "", edad = "";
        int i = 0, nTam = 0;
        System.out.println("Estamos buscando en las solicitudes "+dFec);
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        if(dFec == null){
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaSolicitudServicioRealizadoxFecha('"+fd.format(dFec)+"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i = 0; i<rst.size(); i++){
                    oProRealizado = new ProcedimientosRealizados();
                    oProRealizado.oEpisodioMedico = new EpisodioMedico();
                    oProRealizado.oEpisodioMedico.setPaciente(new Paciente());
                    oProRealizado.oCIE9 = new ProcedimientoCIE9();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oProRealizado.setFechaSolicitud((Date) vRowTemp.get(0));
                    oProRealizado.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(1)).longValue());
                    oProRealizado.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(2));
                    oProRealizado.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(3));
                    oProRealizado.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(4));
                    oProRealizado.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(5));
                    oProRealizado.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(6)).intValue());
                    oProRealizado.setIdentificador(((Double) vRowTemp.get(7)).intValue());
                    oProRealizado.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(8));
                    oProRealizado.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                    edad=(String)vRowTemp.get(9);
                    if (edad.compareTo("")!=0){
                        if(edad.substring(0, 3).compareTo("000")!=0){
                            if (edad.charAt(0)=='0')
                                oProRealizado.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                            else
                                oProRealizado.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                        }else{
                            if (edad.substring(4, 6).compareTo("00")!=0)
                                oProRealizado.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                            else
                                oProRealizado.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oProRealizado.oEpisodioMedico.getDiagIngreso().setClave((String) vRowTemp.get(10));
                    oProRealizado.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(11));
                    oProRealizado.oCIE9.setClave((String) vRowTemp.get(12));
                    oProRealizado.oCIE9.setDescripcion((String) vRowTemp.get(13));
                    vObj.add(oProRealizado); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }      
        }
        return arrRet;
    }
    
    public ProcedimientosRealizados[] buscaProcedimientosxAnestesiologo(Medico noTarjeta) throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        int noTar = noTarjeta.getNoTarjeta();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println(dFechaProgramada);
        System.out.println("Estamos imprimiendo los datos con la fecha " + this.getFechaProgramada());
        if(this == null){
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaProcedimientoRealizadoxAnestesiologo("+noTar+",'"+fecha.format(this.getFechaProgramada())+"') ;";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oProcedimientos = new ProcedimientosRealizados();
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oCirujano = new Medico();
                    oProcedimientos.oResidente = new Medico();
                    oProcedimientos.oEpisodioMedico.setDiagIngreso(new DiagnosticoCIE10());
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(1));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(2));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(3));
                    oProcedimientos.oEpisodioMedico.getPaciente().setCalleNum((String) vRowTemp.get(4));
                    oProcedimientos.oEpisodioMedico.getPaciente().setColonia((String) vRowTemp.get(5));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(6)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(7));
                    oProcedimientos.setIdentificador(((Double) vRowTemp.get(8)).intValue());
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(9));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setClave((String) vRowTemp.get(10));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(11));
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(12));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(13));
                    oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(14));
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(15)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(16));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(17));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(18));
                    oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(19)).intValue());
                    oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(20));
                    oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(21));
                    oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(22));
                    oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(23)).intValue());
                    oProcedimientos.oResidente.setNombres((String) vRowTemp.get(24));
                    oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(25));
                    oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(26));
                    vObj.add(oProcedimientos); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    } 
    
    public ProcedimientosRealizados[] buscaProgramacion() throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "", edad= "";
        int i = 0, nTam = 0;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Estamos imprimiendo los datos con la fecha " + this.getFechaProgramada());
        if(this == null){
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaProcedimientosProgramados('"+fecha.format(this.getFechaProgramada())+"') ;";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oProcedimientos = new ProcedimientosRealizados();
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestEspecifica = new AnestesiaEspecifica();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oCirujano = new Medico();
                    oProcedimientos.oResidente = new Medico();
                    //Manda los datos de los medicos
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(0)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(1));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(2));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(3));
                    oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(4)).intValue());
                    oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(5));
                    oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(6));
                    oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(7));
                    oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(8)).intValue());
                    oProcedimientos.oResidente.setNombres((String) vRowTemp.get(9));
                    oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(10));
                    oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(11));
                    //Manda Datos Procedimiento, Qx, Hora 
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(12));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(13));
                    oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(14));
                    oProcedimientos.oAnestEspecifica.setDescripcion((String) vRowTemp.get(15));
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(16));
                    //Manda Datos Paciente
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(17)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(18));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(19));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(20));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(21)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(22));
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNac((Date) vRowTemp.get(23));
                    oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(24));
                    edad=(String)vRowTemp.get(7);
                    if (edad.compareTo("")!=0){
                        if(edad.substring(0, 3).compareTo("000")!=0){
                            if (edad.charAt(0)=='0')
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                        }else{
                            if (edad.substring(4, 6).compareTo("00")!=0)
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(25)); 
                    vObj.add(oProcedimientos); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    
    public ProcedimientosRealizados[] buscaProcedimientosReprogramacion(Medico noTarjeta) throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "", edad= "";
        int i = 0, nTam = 0;
        int noTar = noTarjeta.getNoTarjeta();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Estamos imprimiendo los datos con la fecha " + this.getFechaProgramada());
        System.out.println("No tarjeta de medico " + noTar);
        if(this == null){
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaProcedimientosRealizadosPorCirujano("+noTar+",'"+fecha.format(this.getFechaProgramada())+"') ;";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oProcedimientos = new ProcedimientosRealizados();
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestEspecifica = new AnestesiaEspecifica();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oCirujano = new Medico();
                    oProcedimientos.oResidente = new Medico();
                    //Manda los datos de los medicos
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(0)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(1));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(2));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(3));
                    oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(4)).intValue());
                    oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(5));
                    oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(6));
                    oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(7));
                    oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(8)).intValue());
                    oProcedimientos.oResidente.setNombres((String) vRowTemp.get(9));
                    oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(10));
                    oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(11));
                    //Manda Datos Procedimiento, Qx, Hora 
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(12));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(13));
                    oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(14));
                    oProcedimientos.oAnestEspecifica.setDescripcion((String) vRowTemp.get(15));
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(16));
                    //Manda Datos Paciente
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(17)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(18));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(19));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(20));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(21)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(22));
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNac((Date) vRowTemp.get(23));
                    oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(24));
                    edad=(String)vRowTemp.get(7);
                    if (edad.compareTo("")!=0){
                        if(edad.substring(0, 3).compareTo("000")!=0){
                            if (edad.charAt(0)=='0')
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                        }else{
                            if (edad.substring(4, 6).compareTo("00")!=0)
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(25)); 
                    vObj.add(oProcedimientos); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    
    public ProcedimientosRealizados[] buscaProcedimientosxResidente(Medico noTarjeta, Date dFecIni, Date dFecFin) throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        int noTar = noTarjeta.getNoTarjeta();
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Estamos mandando los datos del residente "+ noTar);
        System.out.println("Estamos imprimiendo los datos con la fecha de Inicio " + dFecIni);
        System.out.println("Estamos imprimiendo los datos con la fecha  de Fin " + dFecFin);
        if(this == null){
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaProcedimientoRealizadoxResidente("+noTar+",'"+fecha.format(dFecIni)+"','"+fecha.format(dFecFin)+"') ;";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oProcedimientos = new ProcedimientosRealizados();
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oCirujano = new Medico();
                    oProcedimientos.oResidente = new Medico();
                    oProcedimientos.oEpisodioMedico.setDiagIngreso(new DiagnosticoCIE10());
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(1));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(2));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(3));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(4)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(5));
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(6));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setClave((String) vRowTemp.get(7));
                    oProcedimientos.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(8));
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(9));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(10));
                    oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(11));
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(12)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(13));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(14));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(15));
                    oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(16)).intValue());
                    oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(17));
                    oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(18));
                    oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(19));
                    oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(20)).intValue());
                    oProcedimientos.oResidente.setNombres((String) vRowTemp.get(21));
                    oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(22));
                    oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(23));
                    vObj.add(oProcedimientos); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    
    public ProcedimientosRealizados buscaProcedimientoHojaIndicaciones() throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        DateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        if(this == null){
            throw new Exception("ProcedimientosRealizados.buscaProcedimientoHojaIndicaciones: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaprocedimientohojaindicaciones("+getPaciente().getFolioPaciente()+");";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst.isEmpty())
                return oProcedimientos=null;
            else{
                oProcedimientos = new ProcedimientosRealizados();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                oProcedimientos.oEpisodioMedico.setPaciente(new Paciente()); 
                oProcedimientos.oEpisodioMedico.setArea(new AreaServicioHRRB());
                oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                oProcedimientos.oQuirofanos = new Quirofano();
                oProcedimientos.oAnestEspecifica = new AnestesiaEspecifica();
                oProcedimientos.oAnestesio =new Medico();
                oProcedimientos.oCirujano = new Medico();
                oProcedimientos.oResidente = new Medico();
                //Datos Procedimiento, Qx, fecha programada 
                oProcedimientos.oCIE9.setClave((String) vRowTemp.get(0));
                oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(1));
                oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(2));
                oProcedimientos.oAnestEspecifica.setDescripcion((String) vRowTemp.get(3));
                oProcedimientos.setFechaProgramada((Date) vRowTemp.get(4));
                //Datos Paciente
                oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).longValue());
                oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(6));
                oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(7));
                oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(8));
                oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                oProcedimientos.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(10));
                oProcedimientos.oEpisodioMedico.getPaciente().setFechaNac((Date) vRowTemp.get(11));
                String convierte=edad.format(oProcedimientos.oEpisodioMedico.getPaciente().getFechaNac());
                oProcedimientos.oEpisodioMedico.getPaciente().setFechaNacTexto(convierte);
                oProcedimientos.oEpisodioMedico.getPaciente().calculaEdad();
                oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(12)); 
                oProcedimientos.setFechaPropInternacion((Date)vRowTemp.get(13));
                oProcedimientos.setFechaValAnestesica((Date)vRowTemp.get(14));
                oProcedimientos.setTipoCir((String)vRowTemp.get(15));
                //Manda los datos de los medicos
                oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(16)).intValue());
                oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(17));
                oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(18));
                oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(19));
                oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(20)).intValue());
                oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(21));
                oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(22));
                oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(23));
                oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(24)).intValue());
                oProcedimientos.oResidente.setNombres((String) vRowTemp.get(25));
                oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(26));
                oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(27));
                oProcedimientos.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(28)).longValue());
                oProcedimientos.setOrdenP(((Double)vRowTemp.get(29)).intValue());
                oProcedimientos.oEpisodioMedico.getArea().setDescripcion((String)vRowTemp.get(30));
                //arrRet[i]=oProcedimientos;
                return oProcedimientos;
            }
        }
    }
    
    public ProcedimientosRealizados[] buscacitadosparavaloracionpreanestesica(Date dFech1, Date dFech2) throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        DateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(this == null){
            throw new Exception("ProcedimientosRealizados.buscacitadosparavaloracionpreanestesica: error de programación, faltan datos");
        }else{
            if(dFech1!=null && dFech2==null)
                sQuery = "SELECT * FROM buscacitadosparavaloracionpreanestesica('"+fFech.format(dFech1)+"'::DATE,null);";
            else
                sQuery = "SELECT * FROM buscacitadosparavaloracionpreanestesica('"+fFech.format(dFech1)+"'::DATE,'"+fFech.format(dFech2)+"'::DATE);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new ProcedimientosRealizados[rst.size()];
                for(i=0;i<rst.size();i++){
                    oProcedimientos = new ProcedimientosRealizados();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente()); 
                    oProcedimientos.oEpisodioMedico.setArea(new AreaServicioHRRB());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestEspecifica = new AnestesiaEspecifica();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oCirujano = new Medico();
                    oProcedimientos.oResidente = new Medico();
                    //Datos Procedimiento, Qx, fecha programada 
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(0));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(1));
                    oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(2));
                    oProcedimientos.oAnestEspecifica.setDescripcion((String) vRowTemp.get(3));
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(4));
                    //Datos Paciente
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(6));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(7));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(8));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(10));
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNac((Date) vRowTemp.get(11));
                    String convierte=edad.format(oProcedimientos.oEpisodioMedico.getPaciente().getFechaNac());
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNacTexto(convierte);
                    oProcedimientos.oEpisodioMedico.getPaciente().calculaEdad();
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(12)); 
                    oProcedimientos.setFechaPropInternacion((Date)vRowTemp.get(13));
                    oProcedimientos.setFechaValAnestesica((Date)vRowTemp.get(14));
                    oProcedimientos.setTipoCir((String)vRowTemp.get(15));
                    //Manda los datos de los medicos
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(16)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(17));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(18));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(19));
                    oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(20)).intValue());
                    oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(21));
                    oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(22));
                    oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(23));
                    oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(24)).intValue());
                    oProcedimientos.oResidente.setNombres((String) vRowTemp.get(25));
                    oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(26));
                    oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(27));
                    oProcedimientos.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(28)).longValue());
                    oProcedimientos.setOrdenP(((Double)vRowTemp.get(29)).intValue());
                    //oProcedimientos.oEpisodioMedico.getArea().setDescripcion((String)vRowTemp.get(30));
                    arrRet[i]=oProcedimientos;
                    //return oProcedimientos;
                }
            }
        }
        return arrRet;
    }
    
    public ProcedimientosRealizados[] buscaProcedimientosProgramadosPorFecha(Date dFech1, Date dFech2) throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        DateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        DateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(this == null){
            throw new Exception("ProcedimientosRealizados.buscaprocedimientosporcriterio: error de programación, faltan datos");
        }else{
            if(dFech1!=null && dFech2==null)
                sQuery = "SELECT * FROM buscaprocedimientosporcriterio('"+fFech.format(dFech1)+"'::DATE,null);";
            else
                sQuery = "SELECT * FROM buscaprocedimientosporcriterio('"+fFech.format(dFech1)+"'::DATE,'"+fFech.format(dFech2)+"'::DATE);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new ProcedimientosRealizados[rst.size()];
                for(i=0;i<rst.size();i++){
                    oProcedimientos = new ProcedimientosRealizados();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente()); 
                    oProcedimientos.oEpisodioMedico.setArea(new AreaServicioHRRB());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestEspecifica = new AnestesiaEspecifica();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oCirujano = new Medico();
                    oProcedimientos.oResidente = new Medico();
                    //Datos Procedimiento, Qx, fecha programada 
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(0));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(1));
                    oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(2));
                    oProcedimientos.oAnestEspecifica.setDescripcion((String) vRowTemp.get(3));
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(4));
                    //Datos Paciente
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(5)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(6));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(7));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(8));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(10));
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNac((Date) vRowTemp.get(11));
                    String convierte=edad.format(oProcedimientos.oEpisodioMedico.getPaciente().getFechaNac());
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNacTexto(convierte);
                    oProcedimientos.oEpisodioMedico.getPaciente().calculaEdad();
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(12)); 
                    oProcedimientos.setFechaPropInternacion((Date)vRowTemp.get(13));
                    oProcedimientos.setFechaValAnestesica((Date)vRowTemp.get(14));
                    oProcedimientos.setTipoCir((String)vRowTemp.get(15));
                    //Manda los datos de los medicos
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(16)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(17));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(18));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(19));
                    oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(20)).intValue());
                    oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(21));
                    oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(22));
                    oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(23));
                    oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(24)).intValue());
                    oProcedimientos.oResidente.setNombres((String) vRowTemp.get(25));
                    oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(26));
                    oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(27));
                    oProcedimientos.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(28)).longValue());
                    oProcedimientos.setOrdenP(((Double)vRowTemp.get(29)).intValue());
                    //oProcedimientos.oEpisodioMedico.getArea().setDescripcion((String)vRowTemp.get(30));
                    arrRet[i]=oProcedimientos;
                    //return oProcedimientos;
                }
            }
        }
        return arrRet;
    }
    
    
      public ProcedimientosRealizados[] buscaReporteDiario() throws Exception{
        ProcedimientosRealizados arrRet[] = null, oProcedimientos = null;
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "", edad= "";
        int i = 0, nTam = 0;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        if(this.getFechaProgramada() == null){
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaProcedimientosRealizadosReporteDiaQx('"+fecha.format(getFechaProgramada())+"');";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProcedimientosRealizados>();
                for(i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oProcedimientos = new ProcedimientosRealizados();
                    oProcedimientos.oEpisodioMedico = new EpisodioMedico();
                    oProcedimientos.oEpisodioMedico.setPaciente(new Paciente());
                    oProcedimientos.oCIE9 = new ProcedimientoCIE9();
                    oProcedimientos.oQuirofanos = new Quirofano();
                    oProcedimientos.oAnestEspecifica = new AnestesiaEspecifica();
                    oProcedimientos.oAnestesio =new Medico();
                    oProcedimientos.oCirujano = new Medico();
                    oProcedimientos.oResidente = new Medico();
                    oProcedimientos.oValorHorarioQx = new Parametrizacion();
                    oProcedimientos.oValorTipoQx = new Parametrizacion();
                    //Manda los datos de los medicos
                    oProcedimientos.oAnestesio.setNoTarjeta(((Double) vRowTemp.get(0)).intValue());
                    oProcedimientos.oAnestesio.setNombres((String) vRowTemp.get(1));
                    oProcedimientos.oAnestesio.setApPaterno((String) vRowTemp.get(2));
                    oProcedimientos.oAnestesio.setApMaterno((String) vRowTemp.get(3));
                    oProcedimientos.oCirujano.setNoTarjeta(((Double) vRowTemp.get(4)).intValue());
                    oProcedimientos.oCirujano.setNombres((String) vRowTemp.get(5));
                    oProcedimientos.oCirujano.setApPaterno((String) vRowTemp.get(6));
                    oProcedimientos.oCirujano.setApMaterno((String) vRowTemp.get(7));
                    oProcedimientos.oResidente.setNoTarjeta(((Double) vRowTemp.get(8)).intValue());
                    oProcedimientos.oResidente.setNombres((String) vRowTemp.get(9));
                    oProcedimientos.oResidente.setApPaterno((String) vRowTemp.get(10));
                    oProcedimientos.oResidente.setApMaterno((String) vRowTemp.get(11));
                    //Manda Datos Procedimiento, Qx, Fecha
                    oProcedimientos.oCIE9.setClave((String) vRowTemp.get(12));
                    oProcedimientos.oCIE9.setDescripcion((String) vRowTemp.get(13));
                    oProcedimientos.oQuirofanos.setNombre((String) vRowTemp.get(14));
                    oProcedimientos.oAnestEspecifica.setDescripcion((String) vRowTemp.get(15));
                    oProcedimientos.setFechaProgramada((Date) vRowTemp.get(16));
                    //Manda Datos Paciente
                    oProcedimientos.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(17)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(18));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(19));
                    oProcedimientos.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(20));
                    oProcedimientos.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(21)).intValue());
                    oProcedimientos.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(22)).longValue());
                    oProcedimientos.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(23));
                    oProcedimientos.oEpisodioMedico.getPaciente().setFechaNac((Date) vRowTemp.get(24));
                    oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(25));
                    edad=(String)vRowTemp.get(25);
                    if (edad.compareTo("")!=0){
                        if(edad.substring(0, 3).compareTo("000")!=0){
                            if (edad.charAt(0)=='0')
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                        }else{
                            if (edad.substring(4, 6).compareTo("00")!=0)
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                            else
                                oProcedimientos.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oProcedimientos.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(26));
                    oProcedimientos.setIdentificador(((Double) vRowTemp.get(27)).intValue());
                    // Turno y Hora del Procedimiento
                    oProcedimientos.setTipoTurno((String) vRowTemp.get(28));
                    oProcedimientos.oValorHorarioQx.setValor((String) vRowTemp.get(29));
                    oProcedimientos.oValorTipoQx.setClaveParametro((String) vRowTemp.get(30));
                    oProcedimientos.oValorTipoQx.setValor((String) vRowTemp.get(31));
                    vObj.add(oProcedimientos); 
                }
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
 
    public int registraProgramacionDeProcedimientoHospUrg() throws Exception{
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        if(this.oEpisodioMedico == null){
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else {
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoCompletaProgramacionHospitalizacion('"+sUsuarioFirmado+"',"
                    +""+oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +""+oEpisodioMedico.getClaveEpisodio()+","
                    +"'"+oCIE9.getClave().trim()+"',"
                    + getIdentificador()+","
                    + "'"+fd.format(getFechaProgramada())+"',"
                    + ""+oCirujano.getNoTarjeta()+","
                    + ""+oAnestesio.getNoTarjeta()+","
                    + ""+oResidente.getNoTarjeta()+","
                    + "'"+oQuirofanos.getClave()+"',"
                    + "'"+oAnestEspecifica.getClave()+"',"
                    + "'"+oValorTipoQx.getClaveParametro()+"',"
                    + "'"+oValorHorarioQx.getClaveParametro().trim()+"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size() == 1){
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public int registraFechaDeProcedimiento() throws Exception{
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(this.oEpisodioMedico == null){
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else {
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoCompletaPrograTHFQ('"+sUsuarioFirmado+"',"
                    +""+oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +""+oEpisodioMedico.getClaveEpisodio()+","
                    +"'"+oCIE9.getClave().trim()+"',"
                    + getIdentificador()+","
                    + "'"+fd.format(getFechaProgramada())+"',"
                    + "'"+oQuirofanos.getClave()+"',"                   
                    + "'"+getTipoTurno()+"');";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size() == 1){
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public int registraProgramacionDeProcedimiento() throws Exception{
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(this.oEpisodioMedico == null){
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else {
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoCompletaProgramacion('"+sUsuarioFirmado+"',"
                    +""+oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +""+oEpisodioMedico.getClaveEpisodio()+","
                    +"'"+oCIE9.getClave().trim()+"',"
                    + getIdentificador()+","
                    + "'"+fd.format(getFechaProgramada())+"',"
                    + ""+oCirujano.getNoTarjeta()+","
                    + ""+oAnestesio.getNoTarjeta()+","
                    + ""+oResidente.getNoTarjeta()+","
                    + "'"+oQuirofanos.getClave()+"',"
                    + "'"+oAnestEspecifica.getClave()+"',"
                    + "'"+oValorTipoQx.getClaveParametro()+"',"
                    + "'"+fd.format(getFechaPropInternacion())+"',"
                    + "'"+oValorHorarioQx.getClaveParametro().trim()+"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size() == 1){
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
            
    public ProcedimientosRealizados[] buscaSolicitudesConMaterialDeOsteosintesis(Date dFec, int Clv) throws Exception {
        ProcedimientosRealizados oProcedimiento=null, arrRet[] = null;
        DetalleValeMaterial oDetalle = null;     
        ArrayList rst = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        String sQuery = "", edad = "", sx = "", situacion = "";
        int i = 0, nTam = 0;       
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("La fecha recibida es ---> "+ dFec);
        if(dFec == null){
            System.out.println("was here");
            throw new Exception("ProcedimientosRealizados.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaServicioRealizadoconSolicitudMaterialOsteoCEYE('"+fd.format(dFec)+"',"+Clv+");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            } System.out.println("RST" + rst);
            if(rst != null && rst.size()>0){
                vObj = new ArrayList<ProcedimientosRealizados>();
                oProcedimiento = new ProcedimientosRealizados();
                oProcedimiento.oEpisodioMedico = new EpisodioMedico();
                oProcedimiento.oEpisodioMedico.setPaciente(new Paciente());
                oProcedimiento.oCIE9 = new ProcedimientoCIE9();
                oProcedimiento.setSituacion(new Parametrizacion());
                oProcedimiento.oValeMat = new ValeMaterial();
                oProcedimiento.oValeMat.setArrDetalle(new ArrayList<DetalleValeMaterial>());
                for(i = 0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);                    
                    int nLlave=((Double) vRowTemp.get(1)).intValue();
                    int nLlaveVal=((Double) vRowTemp.get(20)).intValue();
                    System.out.println("Llave de identificdor actual " +nLlave);
                    System.out.println("Llave de identificdor de Vale Material " +nLlaveVal);
                    //Si es la misma llave
                    System.out.println("Llave de identificador de lo que trae " + oProcedimiento.getIdentificador());
                    if (nLlave == oProcedimiento.getIdentificador()){
                        if(nLlaveVal == oProcedimiento.oValeMat.getFolioVale()){
                            System.out.println("Claves a comparar de Materiales  Vale guardado primero =  "+nLlaveVal+" "+"llave que trae ahora "+oProcedimiento.oValeMat.getFolioVale());
                            oDetalle = new DetalleValeMaterial();
                            oDetalle.setServicioCobrable(new Material());
                            oDetalle.setSituacion(new Parametrizacion());
                            oDetalle.setAutorizadoPor(new PersonalHospitalario());
                            //Detalles de Vale Material 
                            oDetalle.setIdentificador(((Double) vRowTemp.get(16)).intValue());
                            oDetalle.setCantSolicitada(((Double) vRowTemp.get(17)).shortValue());
                            oDetalle.setFechaAutorizacion((Date) vRowTemp.get(18));
                            oDetalle.getAutorizadoPor().setNoTarjeta(((Double) vRowTemp.get(19)).intValue());
                            oProcedimiento.oValeMat.setFolioVale(((Double) vRowTemp.get(20)).intValue());
                            oProcedimiento.oValeMat.setFechaEmision((Date) vRowTemp.get(21));
                            oProcedimiento.oValeMat.setFechaSurtido((Date) vRowTemp.get(22));
                            oProcedimiento.oValeMat.setSituacionVale((String) vRowTemp.get(23));
                            situacion = (String) vRowTemp.get(23);
                            if(situacion.compareTo("1") == 0)
                                oProcedimiento.oValeMat.setSituacionVale("Vale Cancelado");
                            else {
                                if(situacion.compareTo("0") ==0)
                                    oProcedimiento.oValeMat.setSituacionVale("Surtir");
                            }
                                
                            oDetalle.getMaterial().setClaveMaterial((String) vRowTemp.get(24));
                            oDetalle.setObservaciones((String) vRowTemp.get(25));
                            oDetalle.getMaterial().setNombre((String) vRowTemp.get(26));
                            oDetalle.getSituacion().setValor((String) vRowTemp.get(27));
                            oDetalle.getAutorizadoPor().setNombres((String) vRowTemp.get(28));
                            oDetalle.getAutorizadoPor().setApPaterno((String) vRowTemp.get(29));
                            oDetalle.getAutorizadoPor().setApMaterno((String) vRowTemp.get(30));
                            oDetalle.setValeMat(oProcedimiento.oValeMat);
                            oProcedimiento.oValeMat.getArrDetalle().add(oDetalle);
                            System.out.println("Numero de registros dentro del vale   --->   " + oProcedimiento.oValeMat.getArrDetalle().size());
                        }
                    }else{
                        if(oProcedimiento.getIdentificador() != 0){
                            vObj.add(oProcedimiento);
                        }
                        oProcedimiento = new ProcedimientosRealizados();
                        oProcedimiento.oEpisodioMedico = new EpisodioMedico();
                        oProcedimiento.oEpisodioMedico.setPaciente(new Paciente());
                        oProcedimiento.oCIE9 = new ProcedimientoCIE9();
                        oProcedimiento.setSituacion(new Parametrizacion());
                        oProcedimiento.oValeMat = new ValeMaterial();
                        oProcedimiento.oValeMat.setArrDetalle(new ArrayList<DetalleValeMaterial>());
                        //Inicio del los datos
                        oProcedimiento.setFechaSolicitud((Date) vRowTemp.get(0));
                        oProcedimiento.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                        //Datos del Paciente
                        oProcedimiento.oEpisodioMedico.getPaciente().setFolioPaciente(((Double) vRowTemp.get(2)).longValue());
                        oProcedimiento.oEpisodioMedico.getPaciente().setNombres((String) vRowTemp.get(3));
                        oProcedimiento.oEpisodioMedico.getPaciente().setApPaterno((String) vRowTemp.get(4));
                        oProcedimiento.oEpisodioMedico.getPaciente().setApMaterno((String) vRowTemp.get(5));
                        oProcedimiento.oEpisodioMedico.getPaciente().getSeg().setNumero((String) vRowTemp.get(6));
                        oProcedimiento.oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(7)).intValue());
                        oProcedimiento.oEpisodioMedico.getPaciente().setSexoP((String) vRowTemp.get(8));
                        sx = (String)vRowTemp.get(8);
                        if(sx.compareTo("M") == 0)
                            oProcedimiento.oEpisodioMedico.getPaciente().setSexoP("MASCULINO");
                        else 
                            oProcedimiento.oEpisodioMedico.getPaciente().setSexoP("FEMENINO");
                        oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero((String) vRowTemp.get(9));
                        edad=(String)vRowTemp.get(9);
                        if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{  
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oProcedimiento.oEpisodioMedico.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                        }
                        oProcedimiento.oEpisodioMedico.getDiagIngreso().setClave((String) vRowTemp.get(10));
                        oProcedimiento.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get(11));
                        oProcedimiento.oCIE9.setClave((String) vRowTemp.get(12));
                        oProcedimiento.oCIE9.setDescripcion((String) vRowTemp.get(13));
                        oProcedimiento.oEpisodioMedico.setClaveEpisodio(((Double) vRowTemp.get(14)).longValue());
                        oProcedimiento.oEpisodioMedico.getArea().setDescripcion((String) vRowTemp.get(15));
                        oProcedimiento.oValeMat.setFolioVale(((Double) vRowTemp.get(20)).intValue());
                        //Se Agrega el vale
                            oDetalle = new DetalleValeMaterial();
                            oDetalle.setServicioCobrable(new Material());
                            oDetalle.setSituacion(new Parametrizacion());
                            oDetalle.setAutorizadoPor(new PersonalHospitalario());
                            //Detalles de Vale Material 
                            oDetalle.setIdentificador(((Double) vRowTemp.get(16)).intValue());
                            oDetalle.setCantSolicitada(((Double) vRowTemp.get(17)).shortValue());
                            oDetalle.setFechaAutorizacion((Date) vRowTemp.get(18));
                            oDetalle.getAutorizadoPor().setNoTarjeta(((Double) vRowTemp.get(19)).intValue());
                            oProcedimiento.oValeMat.setFolioVale(((Double) vRowTemp.get(20)).intValue());
                            oProcedimiento.oValeMat.setFechaEmision((Date) vRowTemp.get(21));
                            oProcedimiento.oValeMat.setFechaSurtido((Date) vRowTemp.get(22));
                            oProcedimiento.oValeMat.setSituacionVale((String) vRowTemp.get(23));
                            oDetalle.getMaterial().setClaveMaterial((String) vRowTemp.get(24));
                            oDetalle.setObservaciones((String) vRowTemp.get(25));
                            oDetalle.getMaterial().setNombre((String) vRowTemp.get(26));
                            oDetalle.getSituacion().setValor((String) vRowTemp.get(27));
                            oDetalle.getAutorizadoPor().setNombres((String) vRowTemp.get(28));
                            oDetalle.getAutorizadoPor().setApPaterno((String) vRowTemp.get(29));
                            oDetalle.getAutorizadoPor().setApMaterno((String) vRowTemp.get(30));
                            oDetalle.setValeMat(oProcedimiento.oValeMat);
                            oProcedimiento.oValeMat.getArrDetalle().add(oDetalle);
                    }
                }//fin del ciclo 
                vObj.add(oProcedimiento);
                
                nTam = vObj.size();
                arrRet = new ProcedimientosRealizados[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    public int registraCancelacion(String sClave) throws Exception{
        ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if( sClave.equals("")){   //completar llave
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoCancelarSolicitudQx('"+sUsuarioFirmado+"',"
                    +oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +oEpisodioMedico.getClaveEpisodio()+","
                    + "'"+oCIE9.getClave().trim()+"',"
                    +getIdentificador()+","
                    + "'"+sClave.trim()+"');"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        } 
        return nRet; 
    }
    
    public int registraDiferirQx() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if( this == null){   //completar llave
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoDiferirQx('"+sUsuarioFirmado+"',"
                    +oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +oEpisodioMedico.getClaveEpisodio()+","
                    + "'"+oCIE9.getClave().trim()+"',"
                    +getIdentificador()+","
                    + "'"+oValorCausaDifQx.getClaveParametro().trim()+"');"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        } 
        return nRet; 
    }
    
    public String registraResultadosdeProcedimientoFechas() throws Exception{
        ArrayList rst = null;
        String sQuery = "",duracion = "";
        ProcedimientosRealizados oProcedimientos = null;
        ArrayList<ProcedimientosRealizados> vObj = null;
        int i=0;
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        
        if(this.oEpisodioMedico == null){
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else {
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoResultadoFechasQx('"+sUsuarioFirmado+"',"
                    +""+oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +""+oEpisodioMedico.getClaveEpisodio()+","
                    +"'"+oCIE9.getClave().trim()+"',"
                    + getIdentificador()+","
                    + "'"+fd.format(getFechaEntrada())+"',"
                    + "'"+fd.format(getFechaSalida())+"');";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size() == 1){
                    vObj = new ArrayList<ProcedimientosRealizados>();
                    for(i=0; i<rst.size(); i++){
                        oProcedimientos = new ProcedimientosRealizados();
                        ArrayList vRowTemp = (ArrayList) rst.get(0);
                        oProcedimientos.setDuracionP((String) vRowTemp.get(0));
                    }
                }
                duracion = oProcedimientos.getDuracionP();
            }
        }
        return duracion;
    }
    
    public int registraResultadosdeProcedimientoQx() throws Exception{
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        if(this.oEpisodioMedico == null){
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else {
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoResultadoQx('"+sUsuarioFirmado+"',"
                    +""+oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    +""+oEpisodioMedico.getClaveEpisodio()+","
                    +"'"+oCIE9.getClave().trim()+"',"
                    + getIdentificador()+","
                    +"'"+oEpisodioMedico.getProceRe2().getCIE9Realizado().getClave().trim()+"',"
                    +"'"+getNotaResultado()+"');";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size() == 1){
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public int registraValoracionPreanestesica() throws Exception{    
        ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        if(this.sNotaPreanestesica == null){
            throw new Exception("ProcedimientosRealizados.modificar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM modificaProcedimientoRealizadoValoracionPreanestesica('"+sUsuarioFirmado+"',"
                    + ""+oEpisodioMedico.getPaciente().getFolioPaciente()+","
                    + ""+oEpisodioMedico.getClaveEpisodio()+","
                    + "'"+oCIE9.getClave().trim()+"',"
                    + getIdentificador()+","
                    + "'"+getNotaPreanestesica()+"',"
                    + "'"+fd.format(getFechaValAnestesica())+"');"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet = ((Double)vRowTemp.get(0)).intValue();
                }
            }
        } 
        return nRet; 
    }
    
    public Paciente getPaciente() { return oPaciente; }
    public void setPaciente(Paciente oPaciente) { this.oPaciente = oPaciente; }
    
    
    public String fechaActual(){
        Calendar fecha = new GregorianCalendar();
        int anio = fecha.get(Calendar.YEAR);
        int mes = fecha.get(Calendar.MONTH);
        int dia = fecha.get(Calendar.DAY_OF_MONTH);
        
        String a = anio+"";
        String hoy = dia+"/"+(mes+1)+"/"+a.substring(2,4);
        System.out.println(hoy);
        return hoy;
    }
    
    public Quirofano getQuirofanos() { return oQuirofanos; }
    public void setQuirofanos(Quirofano oQuirofanos) { this.oQuirofanos = oQuirofanos; }
    public Date getFechaProgramada() { return dFechaProgramada; }
    public void setFechaProgramada(Date dFechaProgramada) { this.dFechaProgramada = dFechaProgramada; }
    public Medico getAnestesio() { return oAnestesio; }
    public void setAnestesio(Medico oAnestesio) { this.oAnestesio = oAnestesio; }
    public Medico getResidente() { return oResidente; }
    public void setResidente(Medico oResidente) { this.oResidente = oResidente; }
    
    

    public String getTipoTurno() {
        return sTipoTurno;
    }

    public void setTipoTurno(String sTipoTurno) {
        this.sTipoTurno = sTipoTurno;
    }

    public Parametrizacion getValorHorarioQx() {
        return oValorHorarioQx;
    }

    public void setValorHorarioQx(Parametrizacion oValorHorarioQx) {
        this.oValorHorarioQx = oValorHorarioQx;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable val) {
        this.oCIE9 = (ProcedimientoCIE9)val;
    }
    
    public String getNota() {
        return sNota;
    }

    public void setNota(String sNota) {
        this.sNota = sNota;
    }

    public int getCantSangreQx() {
        return sCantSangreQx;
    }

    public void setCantSangreQx(int sCantSangreQx) {
        this.sCantSangreQx = sCantSangreQx;
    }

    public int getCantSangreRes() {
        return sCantSangreRes;
    }

    public void setCantSangreRes(int sCantSangreRes) {
        this.sCantSangreRes = sCantSangreRes;
    }

    public Date getFechaPropInternacion() {
        return dFechaPropInternacion;
    }

    public void setFechaPropInternacion(Date dFechaPropInternacion) {
        this.dFechaPropInternacion = dFechaPropInternacion;
    }
    public String getEstTransProb() {
        return sEstTransProb;
    }

    public void setEstTransProb(String sEstTransProb) {
        this.sEstTransProb = sEstTransProb;
    }

    public SolicitudSangre getSolSangre() {
        return oSolSangre;
    }

    public void setSolSangre(SolicitudSangre oSolSangre) {
        this.oSolSangre = oSolSangre;
    }

    public ProductoHemoderivado getProdHem() {
        return oProdHem;
    }

    public void setProdHem(ProductoHemoderivado oProdHem) {
        this.oProdHem = oProdHem;
    }

    public String getTipoCir() {
        return sTipoCir;
    }

    public void setTipoCir(String sTipoCir) {
        this.sTipoCir = sTipoCir;
    }

    public int getOrdenP() {
        return nOrdenP;
    }

    public void setOrdenP(int nOrdenP) {
        this.nOrdenP = nOrdenP;
    }
    public String getResultado(){
        return sResultado;
    }
    public void setResultado(String sResultado){
        this.sResultado = sResultado;
    }
    public Date getFechaEntrada(){
        return dFechaEntrada;
    }
    public void setFechaEntrada(Date dFechaEntrada){
        this.dFechaEntrada = dFechaEntrada;
    }
    public Date getFechaSalida(){
        return dFechaSalida;
    }
    public void setFechaSalida(Date dFechaSalida){
        this.dFechaSalida = dFechaSalida;
    }
    public PersonalHospitalario getCirculante(){
        return oCirculante;
    }
    public void setCirculante(PersonalHospitalario oCirculante){
        this.oCirculante = oCirculante;
    }
    public Medico getCAyudanteUno(){
        return oCAyudanteUno;
    }
    public void setCAyudanteUno(Medico oCAyudanteUno){
        this.oCAyudanteUno = oCAyudanteUno;
    }
    public Medico getCAyudanteDos(){
        return oCAyudanteDos;
    }
    public void setCAyudanteDos(Medico oCAyudanteDos){
        this.oCAyudanteDos = oCAyudanteDos;
    }
    public Medico getInstrumentista(){
        return oInstrumentista;
    }
    public void setInstrumentisat(Medico oInstrumentista){
        this.oInstrumentista = oInstrumentista;
    }
	public Date getFechaValAnestesica() {
        return dfechavalanestesica;
    }
	public void setFechaValAnestesica(Date dfechavalanestesica) {
        this.dfechavalanestesica = dfechavalanestesica;
    }
        
    public Parametrizacion getValorCausaDifQx() { return oValorCausaDifQx; }
    public void setValorCausaDifQx(Parametrizacion oValorCausaDifQx) { this.oValorCausaDifQx = oValorCausaDifQx; }
    public Parametrizacion getValorTipoQx() { return oValorTipoQx; }
    public void setValorTipoQx(Parametrizacion oValorTipoQx) { this.oValorTipoQx = oValorTipoQx; }
    public String getNotaPreanestesica() { return sNotaPreanestesica; }
    public void setNotaPreanestesica(String sNotaPreanestesica) { this.sNotaPreanestesica = sNotaPreanestesica; }
    public String getNotaResultado() { return sNotaResultado; }
    public void setNotaResultado(String sNotaResultado) { this.sNotaResultado = sNotaResultado; }
    public String getDuracionEsperada() { return sDuracionEsperada; }
    public void setDuracionEsperada(String sDuracionEsperada) { this.sDuracionEsperada = sDuracionEsperada; }
    public String getFechaInternacion() { return sFechaInternacion; }
    public void setFechaInternacion(String sFechaInternacion) { this.sFechaInternacion = sFechaInternacion; }
    public ProcedimientoCIE9 getCIE9Realizado() { return oCIE9Realizado; }
    public void setCIE9Realizado(ProcedimientoCIE9 oCIE9Realizado) { this.oCIE9Realizado = oCIE9Realizado; }
    public ValeMaterial getValeMat() { return oValeMat; }
    public void setValeMat(ValeMaterial oValeMat) { this.oValeMat = oValeMat; }

    public SolicitudSangre getSolicitudSangre() {
        return oSolicitudSangre;
    }

    public void setSolicitudSangre(SolicitudSangre oSolicitudSangre) {
        this.oSolicitudSangre = oSolicitudSangre;
    }

} 


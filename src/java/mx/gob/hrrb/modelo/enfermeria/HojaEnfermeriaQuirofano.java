package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.DetalleMedicamentos;
import mx.gob.hrrb.modelo.core.MedicamentoAplicado;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.enfermeria.reporte.MustraSignosVitalesHojaQx;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class HojaEnfermeriaQuirofano implements Serializable{
    
	private AccesoDatos oAD;        
	private boolean bCompletoIns;
	private boolean bCompletoMQ;
	private boolean bCuentaCompresas;
        private boolean nCuentaGasas;	      
	private int nConteoCompresas;
	private int nConteoGasas;	
	private int nInstrumental;
	private int nMaterialQx;
	private long nIdHoja;
        private String sUsuarioFirmado;
        private String sOtrosProc;
	private String sEnvioPiezaBiopsia; 
        private Date dFechaCreacion;  
	private AreaServicioHRRB oArea;	
	private DiagnosticoCIE10 oDiagnosticoPre;
	private PersonalHospitalario oEnfermeraQxToco;
	private PersonalHospitalario oEnfRecupera;
        
        private PersonalHospitalario oCirculante;
        private PersonalHospitalario oInstrumentista;
        private PersonalHospitalario oAyudante;
        private PersonalHospitalario oAnesteciologo;
        private ProcedimientosRealizados oProcedimiento;
        private EpisodioMedico oEpisodioMedico;
	private ArrayList<SignosVitalesEnf> arrSignosVitalesEnf;        
	private ArrayList<MedicamentoAplicado> arrTerapeuticaEmpleada;
	private ArrayList<Hemoderivados> arrHemoderivados;
	private ArrayList<MedicamentoAplicado> arrSoluciones;
	private ArrayList<ControlLiquidos> arrControlLiquidos;
	private ArrayList<ActividadesRecuperacion> arrActividadesRecuperacion;
	private ArrayList<ActividadesRealizadasQx> arrActividadesRealizadasQx;        
        private ArrayList<MustraSignosVitalesHojaQx> arrMustraSignos;
	
        //atributos de ayuda
        private SimpleDateFormat df;
        
	public HojaEnfermeriaQuirofano(){
            dFechaCreacion= new Date();
            df= new SimpleDateFormat("yyyy-MM-dd"); 
            //sUsuarioFirmado="JAVIE28";           
            oEpisodioMedico = new EpisodioMedico();            
            oProcedimiento = new ProcedimientosRealizados();           
            oProcedimiento.inicializar();
            arrTerapeuticaEmpleada = new ArrayList<MedicamentoAplicado>();
            arrActividadesRecuperacion = new ArrayList<ActividadesRecuperacion> ();
            arrSoluciones = new ArrayList<MedicamentoAplicado>();
            arrHemoderivados= new ArrayList<Hemoderivados>();
            arrControlLiquidos = new ArrayList<ControlLiquidos>();
            arrActividadesRealizadasQx = new ArrayList<ActividadesRealizadasQx>();
            oCirculante = new PersonalHospitalario();
            oInstrumentista = new PersonalHospitalario();
            oAyudante= new PersonalHospitalario();
            oAnesteciologo = new PersonalHospitalario();
           
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
        
        public boolean buscarHojaQx() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
            String edad="";
            String sSexo="",sComGasas="",sComComp="",sComInst="",sComMaQx="";
            if(this.getIdHoja()==0){ 
                throw new Exception("HojaEnfermeriaQuirofano.buscarHojaQx: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaDatosHojaEnfermeriaQuirurgica("+this.getIdHoja()+"::bigint);"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery); 
                            oAD.desconectar(); 
                    }
                    if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            setIdHoja(((Double)vRowTemp.get(0)).longValue());
                            getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                            getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                            getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                            getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(4));
                            getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(5));
                            getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(6));
                            sSexo=(String)vRowTemp.get(7);
                            if(sSexo.equals("M")){
                              getEpisodioMedico().getPaciente().setSexoP("MASCULINO");  
                            }else if(sSexo.equals("F")){
                              getEpisodioMedico().getPaciente().setSexoP("FEMENINO");
                            }                               
                            edad=(String)vRowTemp.get(8);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                               if (edad.charAt(0)=='0')
                                    getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                 else
                                    getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                 if (edad.substring(4, 6).compareTo("00")!=0)
                                    getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                 else
                                    getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                             }
                            }
                            getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(9));
                            getEpisodioMedico().getArea().setDescripcion((String)vRowTemp.get(10));
                            setFechaCreacion((Date)vRowTemp.get(11));
                            setConteoGasas(((Double)vRowTemp.get(12)).intValue());
                            sComGasas=(String)vRowTemp.get(13);
                            setCuentaGasas((sComGasas.equals("1")));
                            setConteoCompresas(((Double)vRowTemp.get(14)).intValue());
                            sComComp=(String)vRowTemp.get(15);
                            setCuentaCompresas(sComComp.equals("1"));
                            setInstrumental(((Double)vRowTemp.get(16)).intValue());
                            sComInst=(String)vRowTemp.get(17);
                            setCompletoIns(sComInst.equals("1"));
                            setMaterialQx(((Double)vRowTemp.get(18)).intValue());
                            sComMaQx=(String)vRowTemp.get(19);
                            setCompletoMQ(sComMaQx.equals("1"));
                            setEnvioPiezaBiopsia((String)vRowTemp.get(20));
                            setOtrosProc((String)vRowTemp.get(21));
                            setDiagnosticoPre(new DiagnosticoCIE10() );
                            getDiagnosticoPre().setDescripcionDiag((String)vRowTemp.get(22));
                            getProcedimiento().getCIE9().setDescripcion((String)vRowTemp.get(23));
                            getProcedimiento().getCirujano().setNombres((String)vRowTemp.get(24));
                            getProcedimiento().getCirujano().setApPaterno((String)vRowTemp.get(25));
                            getProcedimiento().getCirujano().setApMaterno((String)vRowTemp.get(26));
                            setAnesteciologo(new PersonalHospitalario());
                            getAnesteciologo().setNombres((String)vRowTemp.get(27));
                            getAnesteciologo().setApPaterno((String)vRowTemp.get(28));
                            getAnesteciologo().setApMaterno((String)vRowTemp.get(29));
                            setInstrumentista(new PersonalHospitalario());
                            getInstrumentista().setNombres((String)vRowTemp.get(30));
                            getInstrumentista().setApPaterno((String)vRowTemp.get(31));
                            getInstrumentista().setApMaterno((String)vRowTemp.get(32));                            
                            getAyudante().setNombres((String)vRowTemp.get(33)); 
                            getAyudante().setApPaterno((String)vRowTemp.get(34));
                            getAyudante().setApMaterno((String)vRowTemp.get(35));                            
                            getCirculante().setNombres((String )vRowTemp.get(36));
                            getCirculante().setApPaterno((String)vRowTemp.get(37));
                            getCirculante().setApMaterno((String)vRowTemp.get(38));  
                            setEnfermeraQxToco(new PersonalHospitalario());
                            getEnfermeraQxToco().setNombres((String)vRowTemp.get(39));
                            getEnfermeraQxToco().setApPaterno((String)vRowTemp.get(40));
                            getEnfermeraQxToco().setApMaterno((String)vRowTemp.get(41));
                            setEnfRecupera(new PersonalHospitalario());
                            getEnfRecupera().setNombres((String)vRowTemp.get(42));
                            getEnfRecupera().setApPaterno((String)vRowTemp.get(43));
                            getEnfRecupera().setApMaterno((String)vRowTemp.get(44));
                            BuscaActividadesRealizadasQx();
                            BuscarSignosVitalesHojaEnfermeriaQx();
                            BuscaMedicamentoAplicadoHojaEnfermeriaQx();
                            BuscaSolucionesAplicadasHojaEnfermeriaQx();
                            BuscaHemoderivadosAplicadosHojaEnfermeriaQx();
                            BuscaActividadesRealizadasEnRecuperacion();
                            BuscaControlLiquidos();
                            bRet = true;
                    }
            } 
            return bRet; 
        } 
        
        public void BuscaActividadesRealizadasQx()throws Exception{
           setArrActividadesRealizadasQx(new ArrayList<ActividadesRealizadasQx>());
           ArrayList rst=null;
           ActividadesRealizadasQx oActQx;
           String sQuery="";
           int i=0;
           if(this.getIdHoja()==0){
               throw new Exception("HojaEnfermeriaQuirofano.BuscaActividadesRealizadasQx, error, faltan datos");
           }else{
               sQuery="SELECT * FROM buscaActividadesRealizadasQx("+this.getIdHoja()+"::bigint);";
               oAD= new AccesoDatos();
               if(oAD.conectar()){
                   rst = oAD.ejecutarConsulta(sQuery);
                   oAD.desconectar();
               }
               if(rst!=null && rst.size()>0){                   
                   for(i=0; i<rst.size();i++){
                       oActQx = new ActividadesRealizadasQx();
                       ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oActQx.setIdAct(((Double)vRowTemp.get(0)).longValue());
                        oActQx.setHojaEnfermeriaQuirofano(new HojaEnfermeriaQuirofano());
                        oActQx.getHojaEnfermeriaQuirofano().setIdHoja(((Double)vRowTemp.get(1)).longValue());
                        oActQx.setHoraActividad((Date)vRowTemp.get(2));
                        oActQx.setActividad((String)vRowTemp.get(3));
                        getArrActividadesRealizadasQx().add(oActQx);
                   }
               }
           }
           
        }
        
        public void BuscarSignosVitalesHojaEnfermeriaQx()throws Exception{
            setArrMustraSignos(new ArrayList<MustraSignosVitalesHojaQx>());
            MustraSignosVitalesHojaQx oSigM;
            ArrayList rst = null;
            String sQuery="";
            int i=0;
            if(this.getIdHoja()==0){
                throw new Exception("HojaEnfermeriaQuirofano.BuscarSignosVitalesHojaEnfermeriaQx: error, faltan datos");
            }else{
                sQuery="SELECT * FROM BuscaSignosVItalesHojaEnfermeriaQx2("+this.getIdHoja()+"::bigint);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()>0){
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oSigM= new MustraSignosVitalesHojaQx();
                        oSigM.getSignoValor().setSignoValor((String)vRowTemp.get(0));
                        oSigM.getSigHora7().setValor((String)vRowTemp.get(1));
                        oSigM.getSigHora8().setValor((String)vRowTemp.get(2));
                        oSigM.getSigHora9().setValor((String)vRowTemp.get(3));
                        oSigM.getSigHora10().setValor((String)vRowTemp.get(4));
                        oSigM.getSigHora11().setValor((String)vRowTemp.get(5));
                        oSigM.getSigHora12().setValor((String)vRowTemp.get(6));
                        oSigM.getSigHora13().setValor((String)vRowTemp.get(7));
                        oSigM.getSigHora14().setValor((String)vRowTemp.get(8));
                        oSigM.getSigHora15().setValor((String)vRowTemp.get(9));
                        oSigM.getSigHora16().setValor((String)vRowTemp.get(10));
                        oSigM.getSigHora17().setValor((String)vRowTemp.get(11));
                        oSigM.getSigHora18().setValor((String)vRowTemp.get(12));
                        oSigM.getSigHora19().setValor((String)vRowTemp.get(13));
                        oSigM.getSigHora20().setValor((String)vRowTemp.get(14));
                        oSigM.getSigHora21().setValor((String)vRowTemp.get(15));
                        oSigM.getSigHora22().setValor((String)vRowTemp.get(16));
                        oSigM.getSigHora23().setValor((String)vRowTemp.get(17));
                        oSigM.getSigHora24().setValor((String)vRowTemp.get(18));
                        oSigM.getSigHora1().setValor((String)vRowTemp.get(19));
                        oSigM.getSigHora2().setValor((String)vRowTemp.get(20));
                        oSigM.getSigHora3().setValor((String)vRowTemp.get(21));
                        oSigM.getSigHora4().setValor((String)vRowTemp.get(22));
                        oSigM.getSigHora5().setValor((String)vRowTemp.get(23));
                        oSigM.getSigHora6().setValor((String)vRowTemp.get(24));
                        getArrMustraSignos().add(oSigM);
                    }
                }
            }
            
        }
        
        public void BuscaMedicamentoAplicadoHojaEnfermeriaQx()throws Exception{
            setArrTerapeuticaEmpleada(new ArrayList<MedicamentoAplicado>());
            MedicamentoAplicado oMed;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            if(this.getIdHoja()==0 || this.getEpisodioMedico().getPaciente().getFolioPaciente()==0
                    || this.getEpisodioMedico().getPaciente().getClaveEpisodio()==0){
                throw new Exception("HojaEnfermeriaQuirofano.BuscaMedicamentoAplicadoHojaEnfermeriaQx: error, faltan datos");
                
            }else{
                sQuery="SELECT * FROM BuscaMedicamentoAplicadoHojaEnfermeriaQx("+getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                        +getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,"
                        +this.getIdHoja()+"::bigint,"+"1::integer);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()>0){
                    for(i=0; i<rst.size();i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oMed= new MedicamentoAplicado();  
                        oMed.setEpisodioMedico(new EpisodioMedico());
                        oMed.getMedicamento().setDetalle(new DetalleMedicamentos());
                        oMed.getMedicamento().setNombre((String) vRowTemp.get(0));
                        oMed.getMedicamento().setPresentacion((String) vRowTemp.get(1));
                        oMed.setDosis((String) vRowTemp.get(2));
                        oMed.getVia().setValor((String) vRowTemp.get(3));
                        oMed.getTurno().setDescripcion((String) vRowTemp.get(4));
                        oMed.getAplico().setNombres((String) vRowTemp.get(5));
                        oMed.getAplico().setApPaterno((String) vRowTemp.get(6));
                        oMed.getAplico().setApMaterno((String) vRowTemp.get(7));
                        oMed.setFechaAplicacion((Date) vRowTemp.get(8));
                        getArrTerapeuticaEmpleada().add(oMed);
                    }
                }
            }
        }
        
        public void BuscaSolucionesAplicadasHojaEnfermeriaQx()throws Exception{
            setArrSoluciones(new ArrayList<MedicamentoAplicado>());
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            MedicamentoAplicado oMed;
            if(this.getIdHoja()==0){
                throw new Exception("HojaEnfermeriaQuirofano.BuscaSolucionesAplicadasHojaEnfermeriaQx: Error, faltan datos");
            }else{
                 sQuery="SELECT * FROM BuscaMedicamentoAplicadoHojaEnfermeriaQx("+getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                        +getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,"
                        +this.getIdHoja()+"::bigint,"+"2::integer);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()>0){
                    for(i=0; i<rst.size();i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oMed= new MedicamentoAplicado();
                        oMed.setEpisodioMedico(new EpisodioMedico());
                        oMed.getMedicamento().setDetalle(new DetalleMedicamentos());
                        oMed.getMedicamento().setNombre((String) vRowTemp.get(0));
                        oMed.getMedicamento().setPresentacion((String) vRowTemp.get(1));
                        oMed.setDosis((String) vRowTemp.get(2));
                        oMed.getVia().setValor((String) vRowTemp.get(3));
                        oMed.getTurno().setDescripcion((String) vRowTemp.get(4));
                        oMed.getAplico().setNombres((String) vRowTemp.get(5));
                        oMed.getAplico().setApPaterno((String) vRowTemp.get(6));
                        oMed.getAplico().setApMaterno((String) vRowTemp.get(7));
                        oMed.setFechaAplicacion((Date) vRowTemp.get(8));
                        oMed.setCantAplicada(((Double)vRowTemp.get(9)).shortValue());
                        getArrSoluciones().add(oMed);
                    }
                }
            }
        }
        
        public void BuscaHemoderivadosAplicadosHojaEnfermeriaQx()throws Exception{
            setArrHemoderivados(new ArrayList<Hemoderivados>());
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            Hemoderivados oHe;
            if(this.getIdHoja()==0){
                 throw new Exception("HojaEnfermeriaQuirofano.BuscaHemoderivadosAplicadosHojaEnfermeriaQx: Error, faltan datos");
            }else{
                sQuery="SELECT * FROM buscaHemoderivadosAplicadosHojaEnfermeriaQx("+this.getIdHoja()+"::bigint);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()>0){
                    for(i=0; i<rst.size();i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oHe = new Hemoderivados();
                        oHe.setClaveHemod(((Double)vRowTemp.get(0)).longValue());
                        oHe.setHojaEnfermeriaQuirofano(new HojaEnfermeriaQuirofano());
                        oHe.getHojaEnfermeriaQuirofano().setIdHoja(((Double)vRowTemp.get(1)).longValue());
                        oHe.setHoraAplicacion((Date)vRowTemp.get(2));
                        oHe.setCantidad(((Double)vRowTemp.get(3)).intValue());
                        oHe.setHemoderivado((String)vRowTemp.get(4));
                        oHe.setTipoSangre(new Parametrizacion ());
                        oHe.getTipoSangre().setValor((String)vRowTemp.get(5));
                        oHe.setTipoRH(new Parametrizacion ());
                        oHe.getTipoRH().setValor((String)vRowTemp.get(6));
                        getArrHemoderivados().add(oHe);
                    }                    
                }
            }
        }
        
        public void BuscaControlLiquidos()throws Exception{
            setArrControlLiquidos(new ArrayList<ControlLiquidos>());
            ArrayList rst=null;
            ControlLiquidos oCl;
            String sQuery="";
            int i=0;
            if(this.getIdHoja()==0){
                throw new Exception("HojaEnfermeriaQuirofano.BuscaControlLiquidos: Error, faltan datos");
            }else{
                sQuery="SELECT * FROM BuscaContolLiquidosHojaEnfermeriaQx("+this.getIdHoja()+"::bigint);";
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()>0){
                    for(i=0; i<rst.size();i++){
                      ArrayList vRowTemp =(ArrayList)rst.get(i);
                      oCl= new ControlLiquidos();
                      oCl.setIdEgresos(((Double)vRowTemp.get(0)).longValue());
                      oCl.setHojaEnfermeriaQuirofano(new HojaEnfermeriaQuirofano());
                      oCl.getHojaEnfermeriaQuirofano().setIdHoja(((Double)vRowTemp.get(1)).longValue());
                      oCl.setTotalEgresos(((Double)vRowTemp.get(2)).intValue());
                      oCl.setDiuresis(((Double)vRowTemp.get(3)).intValue());
                      oCl.setOtros((String)vRowTemp.get(4));
                      oCl.getTipoControl().setValor((String)vRowTemp.get(5));
                      oCl.setSangrado(((Double)vRowTemp.get(6)).intValue());                     
                      getArrControlLiquidos().add(oCl);
                    }
                }
            }
        }
        
        
        public void BuscaActividadesRealizadasEnRecuperacion()throws Exception{
            setArrActividadesRecuperacion(new ArrayList<ActividadesRecuperacion>());
            ActividadesRecuperacion oActRe;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            if(this.getIdHoja()==0){
                throw new Exception("HojaEnfermeriaQuirofano.BuscaActividadesRealizadasEnRecuperacion: Error, faltan datos");
            }else{
                sQuery="SELECT * FROM buscaActividadesRealizadasRecuperacion("+this.getIdHoja()+"::bigint);";
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()>0){
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oActRe = new ActividadesRecuperacion();
                        oActRe.setIdRecuperacion(((Double)vRowTemp.get(0)).longValue());
                        oActRe.setHojaEnfermeriaQuirofano(new HojaEnfermeriaQuirofano());
                        oActRe.getHojaEnfermeriaQuirofano().setIdHoja(((Double)vRowTemp.get(1)).longValue());
                        oActRe.setHoraRecuperacion((Date)vRowTemp.get(2));
                        oActRe.setRecuperacion((String)vRowTemp.get(3));
                        oActRe.getTurno().setClave((String)vRowTemp.get(4));
                        getArrActividadesRecuperacion().add(oActRe);
                    }                    
                }
            }
        }
        
	public HojaEnfermeriaQuirofano[] buscarHojaEnfermeriaQxTodas() throws Exception{
            HojaEnfermeriaQuirofano arrRet[]=null, oHojaQx=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;

            if(this.getEpisodioMedico().getPaciente().getFolioPaciente()==0 ||
                    this.getEpisodioMedico().getPaciente().getClaveEpisodio()==0){
                throw new Exception("HojaEnfermeriaQuirofano.buscarHojaEnfermeriaQxTodas: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM buscaHojasEnfermeriaQuirurgica("+this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                        +this.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint"+");"; 
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                }
                if (rst != null) {
                        arrRet = new HojaEnfermeriaQuirofano[rst.size()];
                        for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oHojaQx= new HojaEnfermeriaQuirofano();
                            oHojaQx.setIdHoja(((Double)vRowTemp.get(0)).intValue());
                            oHojaQx.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                            oHojaQx.getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                            oHojaQx.getProcedimiento().getCIE9().setClave((String)vRowTemp.get(3));
                            oHojaQx.setFechaCreacion((Date)vRowTemp.get(4));
                            oHojaQx.getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(5));
                            oHojaQx.getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(6));
                            oHojaQx.getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(7));
                            arrRet[i]=oHojaQx;

                        } 
                } 
            }
                    return arrRet; 
	} 
        
        public HojaEnfermeriaQuirofano[] buscaHistorialHojasEnfermeriaQuirurgica(long folioPac) throws Exception{
        HojaEnfermeriaQuirofano arrRet[]=null, oHojaQx=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("HojaEnfermeriaQuirofano.buscahistorialhojasenfermeriaquirurgica: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialhojasenfermeriaquirurgica("+folioPac+"::bigint);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new HojaEnfermeriaQuirofano[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oHojaQx= new HojaEnfermeriaQuirofano();
                        oHojaQx.setIdHoja(((Double)vRowTemp.get(0)).intValue());
                        oHojaQx.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                        oHojaQx.getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                        oHojaQx.getProcedimiento().getCIE9().setClave((String)vRowTemp.get(3));
                        oHojaQx.setFechaCreacion((Date)vRowTemp.get(4));
                        oHojaQx.getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(5));
                        oHojaQx.getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(6));
                        oHojaQx.getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(7));
                        arrRet[i]=oHojaQx;
                    } 
            } 
        }
        return arrRet; 
    }
    
     
	public int insertarHojaEnfermeriaQx() throws Exception{
            ArrayList rst = null;
            int nRet = -1;        
            String sQuery = "";                      
            
            if( this.getEpisodioMedico().getPaciente().getFolioPaciente()==0
                    && this.getEpisodioMedico().getPaciente().getClaveEpisodio()==0){
                   throw new Exception("HojaEnfermeriaQuirofano.insertarHojaEnfermeriaQx: error, faltan datos");                   
           }else{                 
                   sQuery = this.getInsertarHojaEnfermeriaQx();
                   System.out.println(sQuery);
                   oAD=new AccesoDatos(); 
                   if (oAD.conectar()){ 
                           rst = oAD.ejecutarConsulta(sQuery);
                           oAD.desconectar(); 
                           if (rst != null && rst.size() == 1) {
                                   ArrayList vRowTemp = (ArrayList)rst.get(0);
                                   nRet = ((Double)vRowTemp.get(0)).intValue();
                                   setIdHoja(((Double)vRowTemp.get(1)).longValue());
                           }
                   }
           } 
           return nRet; 
	} 
        
        public String getInsertarHojaEnfermeriaQx() throws Exception{                
            String sQuery = "";
            String sConGasas = this.getCuentaGasas()?"1":"0";
            String sConCompreas = this.getCuentaCompresas()? "1":"0";
            String sConInt = this.getCompletoIns()?"1":"0";
            String sConMaQx= this.getCompletoMQ()?"1":"0";           
            
            if( this.getEpisodioMedico().getPaciente().getFolioPaciente()==0
                    && this.getEpisodioMedico().getPaciente().getClaveEpisodio()==0){
                   throw new Exception("HojaEnfermeriaQuirofano.insertarHojaEnfermeriaQx: error, faltan datos");                   
           }else{                 
                   sQuery = "SELECT * FROM insertaHojaEnfermeriaQuirofano("+this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                           +this.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,'"
                           +this.getProcedimiento().getCIE9().getClave()+"'::character varying,'"
                           +this.df.format(dFechaCreacion)+"'::date,"+this.getEpisodioMedico().getArea().getClave()+"::smallint,"
                           +this.getConteoGasas()+"::integer,'"+sConGasas+"'::character,"
                           +this.getConteoCompresas()+"::integer,'"+sConCompreas+"'::character,"
                           +this.getInstrumental()+"::integer,'"+sConInt+"'::character,"
                           +this.getMaterialQx()+"::integer,'"+sConMaQx+"'::character,"
                           +((this.getEnvioPiezaBiopsia()== null || this.getEnvioPiezaBiopsia().equals(""))?"null":"'"+this.getEnvioPiezaBiopsia()+"'")+"::character varying,"
                           +((this.getOtrosProc()==null || this.getOtrosProc().equals(""))?"null":"'"+this.getOtrosProc()+"'")+"::character varying,'"
                           +this.getDiagnosticoPre().getClave()+"'::character varying,"+"'"+sUsuarioFirmado+"'::character varying,"
                           +this.getAyudante().getNoTarjeta()+"::integer,"
                           +this.getInstrumentista().getNoTarjeta()+"::integer,"
                           +this.getCirculante().getNoTarjeta()+"::integer);"; 
                
           } 
           return sQuery; 
	} 
        
        public int insertarHojaEnfermeriaQxArreglos()throws Exception{
            int nRtn=0;
            ArrayList<String> arrInserta=null;
            String sQuery="";
            if(this.getIdHoja()<0){
                throw new Exception("HojaEnfermeriaQuirofano.insertarHojaEnfermeriaQxArreglos: Error, Faltan Datos");
            }else{
                arrInserta=new ArrayList<String>();
                arrInserta.add(this.getInsertarHojaEnfermeriaQx());
                
                if(!this.getInsertarActividadesQx().isEmpty()){
                    for(String oStr: this.getInsertarActividadesQx()){                        
                        arrInserta.add(oStr);
                    }
                }
                
                if(this.getInsertaSignosVitales()!=null && !this.getInsertaSignosVitales().isEmpty()){
                    for(String out : this.getInsertaSignosVitales()){
                        arrInserta.add(out);
                    }
                }
                
                if(this.getInsetarMedicamentoAplicado()!=null && !this.getInsetarMedicamentoAplicado().isEmpty()){
                    for(String ostr : this.getInsetarMedicamentoAplicado()){
                        arrInserta.add(ostr);
                    }
                }
                
                if(this.getInsertaSoluciones()!=null && !this.getInsertaSoluciones().isEmpty()){
                    for(String out : this.getInsertaSoluciones()){
                        arrInserta.add(out);
                    }
                }
                if(this.getInsertaHemoderivados()!=null && !this.getInsertaHemoderivados().isEmpty()){
                    for(String oIn: this.getInsertaHemoderivados()){
                        arrInserta.add(oIn);
                    }
                }
                if(this.getInsertarControlLiquidos()!=null && !this.getInsertarControlLiquidos().isEmpty()){
                    for(String sStr:this.getInsertarControlLiquidos()){                       
                        arrInserta.add(sStr);
                    }
                }
                if(this.getInsertarActividadesRecuperacion()!=null && !this.getInsertarActividadesRecuperacion().isEmpty()){
                    for(String str : this.getInsertarActividadesRecuperacion()){                        
                        arrInserta.add(str);
                    }
                }
                
                if(!arrInserta.isEmpty()){
                    /*for(String oRR: arrInserta){
                        System.out.println(oRR);
                    }*/
                    if(arrInserta.size()>0){
                        oAD= new AccesoDatos();
                        if(oAD.conectar()){
                           nRtn = oAD.ejecutarConsultaComando(arrInserta);
                           oAD.desconectar();
                        }
                    }
                }           
            }
        
            return nRtn;
        }
        
        public ArrayList<String> getInsertarActividadesQx() throws Exception{
            ArrayList<String> rstTemp;
            String sQuery="";
            if(this.getArrActividadesRealizadasQx().isEmpty()){
                throw new Exception("HojaEnfermeriaQuirofano.getInsertarActividadesQx: Error Fantan Datos");
            }else{
                rstTemp= new ArrayList<String>();
                for(ActividadesRealizadasQx oAct: this.getArrActividadesRealizadasQx()){
                    sQuery="SELECT * FROM insertarAcividadesHojaEnfQx("+this.getIdHoja()+"::bigint,'"
                            +this.df.format(dFechaCreacion)+" "+oAct.getHoraActividadString()+"'::timestamp without time zone,'"
                            +oAct.getActividad()+"'::character varying,'"+sUsuarioFirmado+"'::character varying);";                    
                    rstTemp.add(sQuery);
                }
            }
            
            return rstTemp;
        }

        public ArrayList<String> getInsetarMedicamentoAplicado()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(!this.getArrTerapeuticaEmpleada().isEmpty()){
                rstTemp= new ArrayList<String>();
                for(MedicamentoAplicado oAm: this.getArrTerapeuticaEmpleada()){
                    sQuery="SELECT * FROM insertaHojaEnfmeriaMedicamentoAplicadoYSoluciones('"+sUsuarioFirmado+"'::character varying,"
                            +this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                            +this.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,'"
                            +oAm.getMedicamento().getClaveMedicamento()+"'::character varying,'"
                            +oAm.getMedicamento().getPresentacion()+"'::character varying,"
                            +oAm.getMedicamento().getCodBarras()+"::bigint,'"
                            +oAm.getTurno().getClave()+"'::character varying,'"
                            +oAm.getDosis()+"'::character varying,'"
                            +oAm.getVia().getClaveParametro()+"'::character varying,"
                            +oAm.getServReal().getIdentificador()+"::bigint,'"
                            +this.df.format(dFechaCreacion)+" "+oAm.getFechaAplicacionString()+"'::timestamp without time zone,"
                            +"'01'::character varying,"+this.getIdHoja()+"::bigint,1::integer,"+"0::smallint);";
                    rstTemp.add(sQuery);
                }
            }    
            return rstTemp;
        }    
        
        public ArrayList<String> getInsertaSoluciones()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(this.getIdHoja()<0){
                throw new Exception("HojaEnfermeriaQuirofano.getInsertaSoluciones: Error Fantan Datos");
            }else{
                if(!this.getArrSoluciones().isEmpty()){
                    rstTemp = new ArrayList<String>();
                    for(MedicamentoAplicado oSo: this.getArrSoluciones()){
                        sQuery="SELECT * FROM insertaHojaEnfmeriaMedicamentoAplicadoYSoluciones('"+sUsuarioFirmado+"'::character varying,"
                            +this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                            +this.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,'"
                            +oSo.getMedicamento().getClaveMedicamento()+"'::character varying,'"
                            +oSo.getMedicamento().getPresentacion()+"'::character varying,"
                            +oSo.getMedicamento().getCodBarras()+"::bigint,"
                            +"NULL::character varying,"
                            +"NULL::character varying,"
                            +"NULL::character varying,"
                            +oSo.getServReal().getIdentificador()+"::bigint,'"
                            +this.df.format(dFechaCreacion)+" "+oSo.getFechaAplicacionString()+"'::timestamp without time zone,"
                            +"'01'::character varying,"+this.getIdHoja()+"::bigint,"+"2::INTEGER,"+oSo.getCantAplicada()+"::smallint);";
                        rstTemp.add(sQuery);
                    }
                }
            }
            return rstTemp;
        }

        public ArrayList<String> getInsertaHemoderivados()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(this.getIdHoja()<0){
                throw new Exception("HojaEnfermeriaQuirofano.getInsertaHemoderivados: Error Fantan Datos");
            }else{
                if(!this.getArrHemoderivados().isEmpty()){
                    rstTemp= new ArrayList<String>();
                    for(Hemoderivados oEm: this.getArrHemoderivados()){
                        sQuery="SELECT * FROM insertarHojaEnfQxHemoderivadosAplicados("+this.getIdHoja()+"::bigint,'"
                           +this.df.format(dFechaCreacion)+" "+oEm.getHoraAplicadaString()+"'::timestamp without time zone,"
                                +oEm.getCantidad()+"::integer,"
                                +""+oEm.getIdDetalleSol()+"::integer,'"+oEm.getClaveHemoderivado()+"'::character varying,'"+sUsuarioFirmado+"'::character varying);";
                        rstTemp.add(sQuery);  
                    }                   
                }
            }
            return rstTemp;
        }
        
        public ArrayList<String> getInsertarControlLiquidos()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(!this.getArrControlLiquidos().isEmpty()){            
                rstTemp=new ArrayList<String>();
                for(ControlLiquidos oCtr: this.getArrControlLiquidos()){
                    sQuery="SELECT * FROM insertarHojaEnfQxControlDeLiquidos("+this.getIdHoja()+"::bigint,"
                            +oCtr.getTotalEgresos()+"::integer,"
                            +oCtr.getDiuresis()+"::integer,"
                            +((oCtr.getOtros()==null || oCtr.getOtros().equals("")?"null":"'"+oCtr.getOtros()+"'"))+"::character varying,'"
                            +oCtr.getTipoControl().getClaveParametro()+"'::character varying,'"
                            +oCtr.getTipoControl().getTipoParametro()+"'::character varying,"
                            +oCtr.getSangrado()+"::integer,"
                            +this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                            +this.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,'"
                            +this.getProcedimiento().getCIE9().getClave()+"'::character varying,'"
                            +sUsuarioFirmado+"'::character varying);";
                    rstTemp.add(sQuery);
                }
            }
            
            return rstTemp;        
        }
        
        public ArrayList<String> getInsertarActividadesRecuperacion() throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(this.getIdHoja()<0){
                throw new Exception("HojaEnfermeriaQuirofano.getInsertarActividadesRecuperacion: Error Fantan Datos");
            }else{
                rstTemp= new ArrayList<String>();
                for(ActividadesRecuperacion oAcRe: this.getArrActividadesRecuperacion()){
                    sQuery="SELECT * FROM insertarAcividadesHojaEnfQxRecuperacion("+this.getIdHoja()+"::bigint,'"
                            +this.df.format(dFechaCreacion)+" "+oAcRe.getHoraRecuString()+"'::timestamp without time zone,'"
                            +oAcRe.getRecuperacion()+"'::character varying,'"
                            +this.getDeterminaTurno(oAcRe.getHoraRecuperacion())+"'::character varying,'"
                            +sUsuarioFirmado+"'::character varying"+");";
                    rstTemp.add(sQuery);                            
                }
            }
            return rstTemp;
        }
        
        
        
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HojaEnfermeriaQuirofano.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHojaEnfermeriaQuirofano('"+sUsuarioFirmado+"');"; 
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
        
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HojaEnfermeriaQuirofano.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHojaEnfermeriaQuirofano('"+sUsuarioFirmado+"');"; 
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
        
        public boolean buscaPacienteRegistrarHojaQx()throws Exception{
            boolean bRtn=false; 
            ArrayList rst=null;
            String sQuery="";
            String sSexo="",edad="";
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente()==0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio()==0){
                 throw new Exception("HojaEnfermeriaQuirofano.buscaPacienteRegistrarHojaQx: ERROR, Faltan Datos");
            }else{
                sQuery="SELECT * FROM buscaDatosPacienteHojaQx("+this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                        +this.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint);";
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    getProcedimiento().getCIE9().setClave((String)vRowTemp.get(2));
                    getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(4));
                    getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(5));
                    getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(6));
                    getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(7));
                    sSexo = (String)vRowTemp.get(8);
                    if(sSexo.equals("M")){
                       getEpisodioMedico().getPaciente().setSexoP("MASCULINO");  
                    }else if(sSexo.equals("F")){
                         getEpisodioMedico().getPaciente().setSexoP("FEMENINO");
                    }
                    edad=(String)vRowTemp.get(9);
                   if (edad.compareTo("")!=0){
                   if(edad.substring(0, 3).compareTo("000")!=0){
                      if (edad.charAt(0)=='0')
                          getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                       else
                          getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                   }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                          getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                          getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                  }
                   getEpisodioMedico().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(10));
                   getEpisodioMedico().getArea().setDescripcion((String)vRowTemp.get(11));
                   getProcedimiento().getCirujano().setNombres((String)vRowTemp.get(12));
                   getProcedimiento().getCirujano().setApPaterno((String)vRowTemp.get(13));
                   getProcedimiento().getCirujano().setApMaterno((String)vRowTemp.get(14));
                   setAnesteciologo(new PersonalHospitalario());
                   getAnesteciologo().setNombres((String)vRowTemp.get(15));
                   getAnesteciologo().setApPaterno((String)vRowTemp.get(16));
                   getAnesteciologo().setApMaterno((String)vRowTemp.get(17));
                   getProcedimiento().getCIE9().setDescripcion((String)vRowTemp.get(18));
                   setDiagnosticoPre(new DiagnosticoCIE10() );
                   getDiagnosticoPre().setDescripcionDiag((String)vRowTemp.get(19));
                   getInstrumentista().setNombres((String)vRowTemp.get(20));
                   getInstrumentista().setApPaterno((String)vRowTemp.get(21));
                   getInstrumentista().setApMaterno((String)vRowTemp.get(22));
                   getAyudante().setNombres((String)vRowTemp.get(23));
                   getAyudante().setApPaterno((String)vRowTemp.get(24));
                   getAyudante().setApMaterno((String)vRowTemp.get(25));
                   getCirculante().setNombres((String)vRowTemp.get(26));
                   getCirculante().setApPaterno((String)vRowTemp.get(27));
                   getCirculante().setApMaterno((String)vRowTemp.get(28));
                   getDiagnosticoPre().setClave((String)vRowTemp.get(29));
                   getEpisodioMedico().getArea().setClave(((Double)vRowTemp.get(30)).intValue());
                   bRtn=true;
                }
            }
            return bRtn;
        }
        
        
        public ArrayList<HojaEnfermeriaQuirofano> buscaAyudanteInstrumentistaCirculante(short n)throws Exception{
            HojaEnfermeriaQuirofano oH=null;
            ArrayList<HojaEnfermeriaQuirofano> arrRet=null;
            ArrayList rst=null;
            int i=0;
            String sQuery="";
            if(n==0){
                throw new Exception("HojaEnfermeriaQuirofano.buscaAyudanteInstrumentistaCirculante:Error, faltan datos");
            }else{
                sQuery="SELECT * FROM buscaAyudanteInstrumentistaCirculante("+n+"::smallint);";
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                
                if(rst!=null && rst.size()>0){ 
                    arrRet= new ArrayList<HojaEnfermeriaQuirofano>();
                    for(i=0; i<rst.size();i++){
                        oH= new HojaEnfermeriaQuirofano();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oH.getCirculante().setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                        oH.getCirculante().setNombres((String)vRowTemp.get(1));
                        oH.getCirculante().setApPaterno((String)vRowTemp.get(2));
                        oH.getCirculante().setApMaterno((String)vRowTemp.get(3));
                        oH.getCirculante().setCedProf((String)vRowTemp.get(4));
                        arrRet.add(oH);
                    }
                }
                
            }           
            return arrRet;
        }
        
        public int ActualizaHojaDeEnfermeriaQx() throws Exception{
            int nRet=0;
            ArrayList arrActualiza;
            String sQuery="";
            if(this.getIdHoja()<0){
                
            }else{
                arrActualiza= new ArrayList();
                sQuery="SELECT * FROM modificaHojaEnfermeriaQx('"+sUsuarioFirmado+"'::character varying,"+
                        this.getIdHoja()+"::bigint);";
                arrActualiza.add(sQuery);
                if(this.getActualizaMedicamentoAplicado()!=null && !this.getActualizaMedicamentoAplicado().isEmpty()){
                    for(String ostr : this.getActualizaMedicamentoAplicado()){
                        arrActualiza.add(ostr);
                    }
                }
                
                if(this.getActualizaSoluciones()!=null && !this.getActualizaSoluciones().isEmpty()){
                    for(String out : this.getActualizaSoluciones()){
                        arrActualiza.add(out);
                    }
                }
                if(this.getActualizaHemoderivados()!=null && !this.getActualizaHemoderivados().isEmpty()){
                    for(String oIn: this.getActualizaHemoderivados()){
                        arrActualiza.add(oIn);
                    }
                }
                if(this.getActualizaControlLiquidos()!=null && !this.getActualizaControlLiquidos().isEmpty()){
                    for(String sStr:this.getActualizaControlLiquidos()){                       
                        arrActualiza.add(sStr);
                    }
                }
                if(this.getActualizaActividadesRecuperacion()!=null && !this.getActualizaActividadesRecuperacion().isEmpty()){
                    for(String str : this.getActualizaActividadesRecuperacion()){                        
                        arrActualiza.add(str);
                    }
                }
                
                if(!arrActualiza.isEmpty()){                   
                    if(arrActualiza.size()>0){
                        oAD= new AccesoDatos();
                        if(oAD.conectar()){
                           nRet = oAD.ejecutarConsultaComando(arrActualiza);
                           oAD.desconectar();
                        }
                    }
                } 
            }
            
            return nRet;
        }
        
        public ArrayList<String> getActualizaMedicamentoAplicado()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(!this.getArrTerapeuticaEmpleada().isEmpty()){
                rstTemp= new ArrayList<String>();
                for(MedicamentoAplicado oAm: this.getArrTerapeuticaEmpleada()){
                    if(oAm.getEpisodioMedico().getPaciente().getFolioPaciente()!=0
                            && oAm.getEpisodioMedico().getPaciente().getClaveEpisodio()!=0){
                            sQuery="SELECT * FROM ActualizaHojaEnfmeriaMedicamentoAplicadoYSoluciones('"+sUsuarioFirmado+"'::character varying,"
                                +oAm.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                                +oAm.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,'"
                                +oAm.getMedicamento().getClaveMedicamento()+"'::character varying,'"
                                +oAm.getMedicamento().getPresentacion()+"'::character varying,"
                                +oAm.getMedicamento().getCodBarras()+"::bigint,'"
                                +oAm.getTurno().getClave()+"'::character varying,'"
                                +oAm.getDosis()+"'::character varying,'"
                                +oAm.getVia().getClaveParametro()+"'::character varying,"
                                +oAm.getServReal().getIdentificador()+"::bigint,'"
                                +this.df.format(dFechaCreacion)+" "+oAm.getFechaAplicacionString()+"'::timestamp without time zone,"
                                +"'01'::character varying,"+this.getIdHoja()+"::bigint,1::integer,"+"0::smallint);";
                        rstTemp.add(sQuery);
                    }
                    
                }
            }    
            return rstTemp;
        }    
        
        public ArrayList<String> getActualizaSoluciones()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(this.getIdHoja()<0){
                throw new Exception("HojaEnfermeriaQuirofano.getInsertaSoluciones: Error Fantan Datos");
            }else{
                if(!this.getArrSoluciones().isEmpty()){
                    rstTemp = new ArrayList<String>();
                    for(MedicamentoAplicado oSo: this.getArrSoluciones()){
                        if(oSo.getEpisodioMedico().getPaciente().getFolioPaciente()!=0
                                && oSo.getEpisodioMedico().getPaciente().getClaveEpisodio()!=0){
                            sQuery="SELECT * FROM ActualizaHojaEnfmeriaMedicamentoAplicadoYSoluciones('"+sUsuarioFirmado+"'::character varying,"
                                +oSo.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                                +oSo.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,'"
                                +oSo.getMedicamento().getClaveMedicamento()+"'::character varying,'"
                                +oSo.getMedicamento().getPresentacion()+"'::character varying,"
                                +oSo.getMedicamento().getCodBarras()+"::bigint,"
                                +"NULL::character varying,"
                                +"NULL::character varying,"
                                +"NULL::character varying,"
                                +oSo.getServReal().getIdentificador()+"::bigint,'"
                                +this.df.format(dFechaCreacion)+" "+oSo.getFechaAplicacionString()+"'::timestamp without time zone,"
                                +"'01'::character varying,"+this.getIdHoja()+"::bigint,"+"2::INTEGER,"+oSo.getCantAplicada()+"::smallint);";
                            rstTemp.add(sQuery);
                        }                        
                    }
                }
            }
            return rstTemp;
        }

        public ArrayList<String> getActualizaHemoderivados()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(this.getIdHoja()<0){
                throw new Exception("HojaEnfermeriaQuirofano.getInsertaHemoderivados: Error Fantan Datos");
            }else{
                if(!this.getArrHemoderivados().isEmpty()){
                    rstTemp= new ArrayList<String>();
                    for(Hemoderivados oEm: this.getArrHemoderivados()){
                        if(oEm.getClaveHemod()==0){
                            sQuery="SELECT * FROM ActualizaHojaEnfQxHemoderivadosAplicados("+oEm.getClaveHemod()+"::integer,"+this.getIdHoja()+"::bigint,'"
                                +this.df.format(dFechaCreacion)+" "+oEm.getHoraAplicadaString()+"'::timestamp without time zone,"
                                     +oEm.getCantidad()+"::integer,"
                                     +""+oEm.getIdDetalleSol()+"::integer,'"+oEm.getClaveHemoderivado()+"'::character varying,'"+sUsuarioFirmado+"'::character varying);";
                             rstTemp.add(sQuery); 
                        }                         
                    }                   
                }
            }
            return rstTemp;
        }
        
        public ArrayList<String> getActualizaControlLiquidos()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(!this.getArrControlLiquidos().isEmpty()){            
                rstTemp=new ArrayList<String>();
                for(ControlLiquidos oCtr: this.getArrControlLiquidos()){
                    if(oCtr.getIdEgresos()==0){
                        sQuery="SELECT * FROM ActualizaHojaEnfQxControlDeLiquidos("+oCtr.getIdEgresos()+"::bigint,"+this.getIdHoja()+"::bigint,"
                                +oCtr.getTotalEgresos()+"::integer,"
                                +oCtr.getDiuresis()+"::integer,"
                                +((oCtr.getOtros()==null || oCtr.getOtros().equals("")?"null":"'"+oCtr.getOtros()+"'"))+"::character varying,'"
                                +oCtr.getTipoControl().getClaveParametro()+"'::character varying,'"
                                +oCtr.getTipoControl().getTipoParametro()+"'::character varying,"
                                +oCtr.getSangrado()+"::integer,"
                                +this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,'"
                                +this.getProcedimiento().getCIE9().getClave()+"'::character varying,'"
                                +sUsuarioFirmado+"'::character varying);";
                        rstTemp.add(sQuery);
                    }                    
                }
            }
            
            return rstTemp;        
        }
        
        public ArrayList<String> getActualizaActividadesRecuperacion() throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";
            if(this.getIdHoja()<0){
                throw new Exception("HojaEnfermeriaQuirofano.getInsertarActividadesRecuperacion: Error Fantan Datos");
            }else{
                rstTemp= new ArrayList<String>();
                for(ActividadesRecuperacion oAcRe: this.getArrActividadesRecuperacion()){
                    if(oAcRe.getIdRecuperacion()==0){
                        sQuery="SELECT * FROM  ActualizaAcividadesHojaEnfQxRecuperacion("+oAcRe.getIdRecuperacion()+"::bigint,"+this.getIdHoja()+"::bigint,'"
                                +this.df.format(dFechaCreacion)+" "+oAcRe.getHoraRecuString()+"'::timestamp without time zone,'"
                                +oAcRe.getRecuperacion()+"'::character varying,'"
                                +this.getDeterminaTurno(oAcRe.getHoraRecuperacion())+"'::character varying,'"
                                +sUsuarioFirmado+"'::character varying"+");";
                        rstTemp.add(sQuery);
                    }                                                
                }
            }
            return rstTemp;
        }
        
	public boolean getCompletoIns() {
            return bCompletoIns;
	}

	public void setCompletoIns(boolean valor) {
            this.bCompletoIns=valor;
	}

	public boolean getCompletoMQ() {
            return bCompletoMQ;
	}

	public void setCompletoMQ(boolean valor) {
            this.bCompletoMQ=valor;
	}

	public boolean getCuentaCompresas() {
            return bCuentaCompresas;
	}

	public void setCuentaCompresas(boolean valor) {
            this.bCuentaCompresas=valor;
	}

	public Date getFechaCreacion() {
            return dFechaCreacion;
	}

	public void setFechaCreacion(Date valor) {
            this.dFechaCreacion=valor;
	}

	public int getConteoCompresas() {
            return nConteoCompresas;
	}

	public void setConteoCompresas(int valor) {
            this.nConteoCompresas=valor;
	}

	public int getConteoGasas() {
            return nConteoGasas;
	}

	public void setConteoGasas(int valor) {
            this.nConteoGasas=valor;
	}

	public boolean getCuentaGasas() {
            return nCuentaGasas;
	}

	public void setCuentaGasas(boolean valor) {
            this.nCuentaGasas=valor;
	}

	public int getInstrumental() {
            return nInstrumental;
	}

	public void setInstrumental(int valor) {
            this.nInstrumental=valor;
	}

	public int getMaterialQx() {
            return nMaterialQx;
	}

	public void setMaterialQx(int valor) {
            this.nMaterialQx=valor;
	}	
        
	public AreaServicioHRRB getArea() {
            return oArea;
	}

	public void setArea(AreaServicioHRRB valor) {
            this.oArea=valor;
	}

        public DiagnosticoCIE10 getDiagnosticoPre() {
            return oDiagnosticoPre;
	}

	public void setDiagnosticoPre(DiagnosticoCIE10 valor) {
            this.oDiagnosticoPre=valor;
	}

	public PersonalHospitalario getEnfermeraQxToco() {
            return oEnfermeraQxToco;
	}

	public void setEnfermeraQxToco(PersonalHospitalario valor) {
            this.oEnfermeraQxToco=valor;
	}

	public PersonalHospitalario getEnfRecupera() {
            return oEnfRecupera;
	}

	public void setEnfRecupera(PersonalHospitalario valor) {
            this.oEnfRecupera=valor;
	}
        
	public String getOtrosProc() {
            return sOtrosProc;
	}

	public void setOtrosProc(String valor) {
            this.sOtrosProc=valor;
	}

	public String getEnvioPiezaBiopsia() {
            return sEnvioPiezaBiopsia;
	}

	public void setEnvioPiezaBiopsia(String valor) {
            this.sEnvioPiezaBiopsia=valor;
	}

	public ArrayList<SignosVitalesEnf> getArrSignosVitalesEnf() {
            return arrSignosVitalesEnf;
	}

	public void setArrSignosVitalesEnf(ArrayList<SignosVitalesEnf> valor) {
            this.arrSignosVitalesEnf=valor;
	}

	public ArrayList<MedicamentoAplicado> getArrTerapeuticaEmpleada() {
            return arrTerapeuticaEmpleada;
	}

	public void setArrTerapeuticaEmpleada(ArrayList<MedicamentoAplicado> valor) {
            this.arrTerapeuticaEmpleada=valor;
	}

	public ArrayList<Hemoderivados> getArrHemoderivados() {
            return arrHemoderivados;
	}

	public void setArrHemoderivados(ArrayList<Hemoderivados> valor) {
            this.arrHemoderivados=valor;
	}

	public ArrayList<MedicamentoAplicado> getArrSoluciones() {
            return arrSoluciones;
	}

	public void setArrSoluciones(ArrayList<MedicamentoAplicado> valor) {
            this.arrSoluciones=valor;
	}

	public ArrayList<ControlLiquidos> getArrControlLiquidos() {
            return arrControlLiquidos;
	}

	public void setArrControlLiquidos(ArrayList<ControlLiquidos> valor) {
            this.arrControlLiquidos=valor;
	}

	public ArrayList<ActividadesRecuperacion> getArrActividadesRecuperacion() {
            return arrActividadesRecuperacion;
	}

	public void setArrActividadesRecuperacion(ArrayList<ActividadesRecuperacion> valor) {
            this.arrActividadesRecuperacion=valor;
	}

	public ArrayList<ActividadesRealizadasQx> getArrActividadesRealizadasQx() {
            return arrActividadesRealizadasQx;
	}

	public void setArrActividadesRealizadasQx(ArrayList<ActividadesRealizadasQx> valor) {
            this.arrActividadesRealizadasQx=valor;
	}

	public EpisodioMedico getEpisodioMedico() {
            return oEpisodioMedico;
	}

	public void setEpisodioMedico(EpisodioMedico valor) {
            this.oEpisodioMedico=valor;
	}

   
    public PersonalHospitalario getCirculante() {
        return oCirculante;
    }

    public void setCirculante(PersonalHospitalario oCirculante) {
        this.oCirculante = oCirculante;
    }

    public PersonalHospitalario getInstrumentista() {
        return oInstrumentista;
    }

    public void setInstrumentista(PersonalHospitalario oInstrumentista) {
        this.oInstrumentista = oInstrumentista;
    }

   
    public PersonalHospitalario getAyudante() {
        return oAyudante;
    }

    public void setAyudante(PersonalHospitalario oAyudante) {
        this.oAyudante = oAyudante;
    }

    public PersonalHospitalario getAnesteciologo() {
        return oAnesteciologo;
    }

    public void setAnesteciologo(PersonalHospitalario oAnesteciologo) {
        this.oAnesteciologo = oAnesteciologo;
    }

    public ProcedimientosRealizados getProcedimiento() {
        return oProcedimiento;
    }

    public void setProcedimiento(ProcedimientosRealizados oProcedimiento) {
        this.oProcedimiento = oProcedimiento;
    }

   
    public long getIdHoja() {
        return nIdHoja;
    }

    public void setIdHoja(long nIdHoja) {
        this.nIdHoja = nIdHoja;
    }
    
    public String getDeterminaTurno(Date dHora){         
        SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dHora);
         Double hora=Double.parseDouble(sT);
         sTurno= (hora<=07.00 && hora<15.00)?"MAT": (hora >= 15.00 && hora <= 21.00)?"VES":"NOC";        
         return sTurno;
    } 

    public ArrayList<MustraSignosVitalesHojaQx> getArrMustraSignos() {
        return arrMustraSignos;
    }

    public void setArrMustraSignos(ArrayList<MustraSignosVitalesHojaQx> arrMustraSignos) {
        this.arrMustraSignos = arrMustraSignos;
    }
    
    public String getCompletoGasas(){
        return (this.getCuentaGasas()?"COMPLETO":"INCOMPLETO");
    }
   
    public String getCompletoCompresas(){
        return (this.getCuentaCompresas()?"COMPLETO":"INCOMPLETO");
    }
    
    public String getCompletoInstru(){
        return (this.getCompletoIns()?"COMPLETO":"INCOMPLETO");
    }
    
    public String getCompletoMat(){
        return (this.getCompletoMQ()?"COMPLETO":"INCOMPLETO");
    }
    
    public ArrayList<String> getInsertaSignosVitales()throws Exception{
            ArrayList<String> rstTemp=null;
            String sQuery="";            
            if(!this.getArrMustraSignos().isEmpty()){
                rstTemp= new ArrayList<String>();
                if(this.getArrMustraSignos().get(0).getSigHora7().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora7().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora7().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora7().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora7().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora7().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora7().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora7().getValor().equals("")) {                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora7().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora7().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora7().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora7().getValor(); 
                        String sso2= (this.getArrMustraSignos().get(4).getSigHora7().getValor());
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"7::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora8().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora8().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora8().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora8().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora8().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora8().getValor().equals("")&&
                    this.getArrMustraSignos().get(3).getSigHora8().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora8().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora8().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora8().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora8().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora8().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora8().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"8::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora9().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora9().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora9().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora9().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora9().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora9().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora9().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora9().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora9().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora9().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora9().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora9().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora9().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"9::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora10().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora10().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora10().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora10().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora10().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora10().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora10().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora10().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora10().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora10().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora10().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora10().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora10().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"10::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora11().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora11().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora11().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora11().getValor().equals("")  && 
                    this.getArrMustraSignos().get(2).getSigHora11().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora11().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora11().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora11().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora11().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora11().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora11().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora11().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora11().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"11::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                } if(this.getArrMustraSignos().get(0).getSigHora12().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora12().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora12().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora12().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora12().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora12().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora12().getValor()!=null  && !this.getArrMustraSignos().get(3).getSigHora12().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora12().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora12().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora12().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora12().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora12().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"12::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora13().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora13().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora13().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora13().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora13().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora13().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora13().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora13().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora13().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora13().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora13().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora13().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora13().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"13::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora14().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora14().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora14().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora14().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora14().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora14().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora14().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora14().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora14().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora14().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora14().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora14().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora14().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"14::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora15().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora15().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora15().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora15().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora15().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora15().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora15().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora15().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora15().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora15().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora15().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora15().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora15().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"15::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora16().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora16().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora16().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora16().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora16().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora16().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora16().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora16().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora16().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora16().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora16().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora16().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora16().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"16::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora17().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora17().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora17().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora17().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora17().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora17().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora17().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora17().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora17().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora17().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora17().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora17().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora17().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"17::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora18().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora18().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora18().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora18().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora18().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora18().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora18().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora18().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora18().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora18().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora18().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora18().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora18().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"18::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora19().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora19().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora19().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora19().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora19().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora19().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora19().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora19().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora19().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora19().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora19().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora19().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora19().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"19::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora20().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora20().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora20().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora20().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora20().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora20().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora20().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora20().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora20().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora20().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora20().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora20().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora20().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"20::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora21().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora21().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora21().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora21().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora21().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora21().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora21().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora21().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora21().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora21().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora21().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora21().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora21().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"21::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora22().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora23().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora22().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora23().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora22().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora23().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora22().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora23().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora22().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora22().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora22().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora22().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora22().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"22::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora23().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora23().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora23().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora23().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora23().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora23().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora23().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora23().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora23().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora23().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora23().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora23().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora23().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"23::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora24().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora24().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora24().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora24().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora24().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora24().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora24().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora24().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora24().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora24().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora24().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora24().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora24().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"24::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,'"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora1().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora1().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora1().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora1().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora1().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora1().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora1().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora1().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora1().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora1().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora1().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora1().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora1().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"1::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora2().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora2().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora2().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora2().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora2().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora2().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora2().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora2().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora2().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora2().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora2().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora2().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora13().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"2::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora3().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora3().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora3().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora3().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora3().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora3().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora3().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora3().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora3().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora3().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora3().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora3().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora3().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"3::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora4().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora4().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora4().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora4().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora4().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora4().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora4().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora4().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora4().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora4().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora4().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora4().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora4().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"4::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora5().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora5().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora5().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora5().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora5().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora5().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora5().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora5().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora5().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora5().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora5().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora5().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora5().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"5::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }if(this.getArrMustraSignos().get(0).getSigHora6().getValor()!=null && !this.getArrMustraSignos().get(0).getSigHora6().getValor().equals("") &&
                    this.getArrMustraSignos().get(1).getSigHora6().getValor()!=null && !this.getArrMustraSignos().get(1).getSigHora6().getValor().equals("") && 
                    this.getArrMustraSignos().get(2).getSigHora6().getValor()!=null && !this.getArrMustraSignos().get(2).getSigHora6().getValor().equals("") &&
                    this.getArrMustraSignos().get(3).getSigHora6().getValor()!=null && !this.getArrMustraSignos().get(3).getSigHora6().getValor().equals("")){                    
                        String sta = this.getArrMustraSignos().get(0).getSigHora6().getValor();
                        String sfr= this.getArrMustraSignos().get(1).getSigHora6().getValor();
                        String spulso= this.getArrMustraSignos().get(2).getSigHora6().getValor();
                        String stemp= this.getArrMustraSignos().get(3).getSigHora6().getValor(); 
                        String sso2= this.getArrMustraSignos().get(4).getSigHora6().getValor();
                        sQuery="SELECT * FROM insertarSignosVitalesHojaEnfermeria("+this.getIdHoja()+"::bigint,"
                                +"6::smallint,'"+sta+"'::character varying,'"+sfr+"'::character varying,'"
                                +spulso+"'::character varying,'"+stemp+"'::character varying,"
                                +(sso2==null || sso2.equals("")?"null":"'"+sso2+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying"+");"; 
                    rstTemp.add(sQuery);                    
                }                
                
            }            
            return rstTemp;
        }        

} 

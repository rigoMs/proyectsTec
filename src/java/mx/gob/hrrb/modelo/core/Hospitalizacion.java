package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.hospitalizacion.HospitalPsiquiatrico;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.hospitalizacion.AreasUsadas;
import mx.gob.hrrb.modelo.hospitalizacion.OrdenUsoArea;
import org.primefaces.context.RequestContext;
import org.primefaces.model.chart.BarChartModel;
import org.primefaces.model.chart.ChartSeries;
import org.primefaces.model.chart.LineChartModel;
import org.primefaces.model.chart.LineChartSeries;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Hospitalizacion implements Serializable{
	private AccesoDatos oAD;
	private String sEstadoCode;
	private String sReingreso;
	private Date dFechaElaboracion;
	private Date dFechaIngresoHos;
	private long nNumIngresoHos;
	private float nPeso;
	private float nTalla;
	private Parametrizacion oProcedencia;
	private Parametrizacion oPronostico;
        private String sPronosticoP;
        private String sConsultaExt;
	private String sFolioCode;
	private String sManejo;
	private String sPlan;
	private String sProblemasPend;
	private String sRecomendaciones;
	private String sResumenEvolucion;
        private String sDiagnosticosIngreso;
        private String sDiagnosticosEgreso;
        private String sProcedimientosOperaciones;
	private char sTipoEstancia;
	private ArrayList arrAreasUsadas;
	private ArrayList arrOrdenUsoArea;
	private HospitalPsiquiatrico oHospitalPsiquiatrico;
	private AtencionObstetrica oAtencionObstetrica;
	private EpisodioMedico oEpisodioMedico;
        private Paciente oPaciente;
        private Estado oEstado;
        private Municipio oMunicipio;
        private Ciudad oCiudad;
        private Seguro oSeguro;
        private Expediente oExpediente;
        private Etnicidad oEtnicidad;
        private String fechaIngresoHospStr;
    	private String sProcedenciaP;
        private AreasUsadas oSalaLabor;
        private AreasUsadas oSalaExpulsion;
        private AreasUsadas oSalaRecuperacion;
        private AreasUsadas oTerapiaIntensiva;
        private AreasUsadas oTerapiaIntermedia;
        private OrdenUsoArea oServicioIngreso;
        private OrdenUsoArea oServicioSegundo;
        private OrdenUsoArea oServicioTercero;
        private OrdenUsoArea oServicioEgreso;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private FacesMessage facesMessage;     
        private Date dFechaIni;
        private Date dFechaFin;
        private List<Hospitalizacion> lHospitalizacion;
        private List<Hospitalizacion> lFiltaHospitalizacion;
        public int tamGra;
        private AreaServicioHRRB oASerHRRB;
        private int nTipo;
        private String bCodeFirmada;  
        
        public Hospitalizacion(){
            oPaciente = new Paciente();
            oEstado = new Estado();
            oMunicipio = new Municipio();
            oCiudad = new Ciudad();
            oSeguro = new Seguro();
            oExpediente = new Expediente();
            oEtnicidad = new Etnicidad();
            oEpisodioMedico=new EpisodioMedico();
            oSalaLabor = new AreasUsadas();
            oSalaExpulsion = new AreasUsadas();
            oSalaRecuperacion = new AreasUsadas();
            oTerapiaIntensiva = new AreasUsadas();
            oTerapiaIntermedia = new AreasUsadas();
            oServicioIngreso = new OrdenUsoArea();
            oServicioSegundo = new OrdenUsoArea();
            oServicioTercero = new OrdenUsoArea();
            oServicioEgreso = new OrdenUsoArea();
            oAtencionObstetrica = new AtencionObstetrica();
            oASerHRRB = new AreaServicioHRRB();
            dFechaIni = null;
            dFechaFin = null;            
            oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }          
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
        boolean reingreso = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveHospitalizacion("+oPaciente.getFolioPaciente()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setFechaIngresoHos((Date) vRowTemp.get( 4 ));
                                this.setFolioCode((String) vRowTemp.get( 5 ));
                                this.setTipoEstancia(((String) vRowTemp.get( 6 )).charAt(0));
                                this.setEstadoCode((String) vRowTemp.get( 7 ));
                                this.setFechaElaboracion((Date) vRowTemp.get( 8 ));
                                /*if(((String) vRowTemp.get( 9 )).charAt(0)=='S'){
                                    reingreso=true;
                                }*/
                                this.setReingreso((String) vRowTemp.get( 9 ));
                                this.setPeso(((Double)vRowTemp.get(10)).floatValue());
                                this.setTalla(((Double)vRowTemp.get(11)).floatValue());
                                this.setResumenEvolucion((String) vRowTemp.get( 12 ));
                                this.setManejo((String) vRowTemp.get( 13 ));
                                this.setProblemasPend((String) vRowTemp.get( 14 ));
                                this.setPlan((String) vRowTemp.get( 15 ));
                                this.setRecomendaciones((String) vRowTemp.get( 16 ));
				bRet = true;
			}
		} 
		return bRet; 
	} 
        
        public boolean buscaperfilHosp() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
                     //System.out.println("antes de la busqueda");
			sQuery = "SELECT * FROM buscaUsuHosp('"+sUsuario+"');"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            bRet = sUsuario.compareTo((String) vRowTemp.get( 0 ))==0;
			}
		} 
		return bRet; 
        }
        
        public String buscaNombreMedicoAutoriza() throws Exception{
            String NomMedico="";
            ArrayList rst = null;
            String sQuery = "";

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaMedicoAutoriza("+oPaciente.getFolioPaciente()+"::bigint, "+oEpisodioMedico.getClaveEpisodio()+"::bigint, "+ getNumIngresoHos()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            NomMedico=((String) vRowTemp.get( 0 ));
			}
		} 
		return NomMedico; 
        }
        
	public boolean buscarDatosPacienteCODE() throws Exception{
            boolean bRet = false;
            Hospitalizacion arrRet[]=null, oHospitalizacion=null;
            ArrayList rst = null;
            ArrayList<Hospitalizacion> vObj=null;
            String sQuery = "";
            String edad="";
            int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
                     //System.out.println("antes de la busqueda");
			sQuery = "SELECT * FROM buscaDatosPacienteCODE("+oPaciente.getFolioPaciente()+"::bigint, "+getNumIngresoHos()+"::bigint, "+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
                            //System.out.println("Si entra a este If");
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                                this.oPaciente.setNombres((String) vRowTemp.get( 1 ));                                
                                this.oPaciente.setApPaterno((String) vRowTemp.get( 2 ));
                                this.oPaciente.setApMaterno((String) vRowTemp.get( 3 ));  
                                edad=(String)vRowTemp.get(4);
                                //this.oPaciente.setEdadNumero((String) vRowTemp.get( 4 ));
                                this.oPaciente.setClaveEdadP((String) vRowTemp.get( 5 ));  
                                switch (oPaciente.getClaveEdadP()) {
                                case "AÑOS":
                                    {
                                        int edadN=Integer.parseInt(edad.substring(0, 3));
                                        this.oPaciente.setEdad(edadN);
                                        break;
                                    }
                                case "MESES":
                                    {
                                        int edadN=Integer.parseInt(edad.substring(4, 6));
                                        this.oPaciente.setEdad(edadN);
                                        if(edadN<=3){
                                            String nacHosp=(String)vRowTemp.get(6);
                                            if(nacHosp.equals("N"))
                                                oPaciente.setNacidoEnHospitalP("NO");
                                            else
                                                oPaciente.setNacidoEnHospitalP("SI");
                                        }   break;
                                    }
                                case "DÍAS":
                                    {
                                        int edadN=Integer.parseInt(edad.substring(7, 9));
                                        this.oPaciente.setEdad(edadN);
                                        String nacHosp=(String)vRowTemp.get(6);
                                        if(nacHosp.equals("N"))
                                            oPaciente.setNacidoEnHospitalP("NO");
                                        else
                                            oPaciente.setNacidoEnHospitalP("SI");
                                        break;
                                    }
                            }
                                String Sexo=(String)vRowTemp.get(7);
                                if(Sexo.equals("M")){
                                    this.oPaciente.setSexoP("Masculino");
                                    this.oPaciente.setSexos("true");                                    
                                }
                                else{
                                    this.oPaciente.setSexoP("Femenino");
                                    this.oPaciente.setSexos("false");
                                }
                                this.oPaciente.setCurp((String) vRowTemp.get( 8 ));
                                this.oPaciente.setPeso(((Double)vRowTemp.get( 9 )).floatValue());
                                this.oPaciente.setTalla(((Double)vRowTemp.get( 10 )).floatValue());  
                                this.oEstado.setDescripcionEdo((String) vRowTemp.get( 11 ));
                                this.oEstado.setClaveEdo((String) vRowTemp.get( 12 ));
                                this.oMunicipio.setDescripcionMun((String) vRowTemp.get( 13 ));
                                this.oMunicipio.setClaveMun((String) vRowTemp.get( 14 ));
                                this.oCiudad.setDescripcionCiu((String) vRowTemp.get( 15 ));
                                this.oCiudad.setClaveCiu((String) vRowTemp.get( 16 ));
                                this.oExpediente.setNumero(((Double)vRowTemp.get( 17 )).intValue());
                                this.oSeguro.setDerechohabienteP((String) vRowTemp.get( 18 ));
                                this.oSeguro.setNumero((String) vRowTemp.get( 19 ));
                                this.oEtnicidad.setPertenenciaGpoIndP((String) vRowTemp.get( 20 ));
                                this.oEtnicidad.setHablaLenguaIndP((String) vRowTemp.get( 21 ));
                                this.oEtnicidad.setHablaEspaniolP((String) vRowTemp.get( 22 ));
                                this.oEtnicidad.setClaveLengua((String) vRowTemp.get( 23 ));
				bRet = true;
			}
		} 
		return bRet; 
	}                

//*********************************************************************************************************************        
    public List<Hospitalizacion> buscarPacientesHospitalizados() throws Exception{

	Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
        ArrayList<Hospitalizacion> vObj=null;
	String sQuery = "";
        String edad="";
        SimpleDateFormat fecha=new SimpleDateFormat("yyyy/MM/dd");
        Date fAlta=null;
        Date fHoy=new Date();
        String FechaA="";
        String FechaH="";
	int i=0, nTam=0;
        System.out.println("TIPO: "+getTipo());
            switch(getTipo()){
                case 1:
                    sQuery = "SELECT * FROM buscaPacientesHospitalizadosCODEArea("+getASerHRRB().getClave()+"::smallint);"; 
                    break;
                case 2:
                    sQuery = "SELECT * FROM buscaPacientesHospitalizadosCODE();"; 
                    break;
                case 3:
                    sQuery = "SELECT * FROM buscaPacientesHospitalizadosArea("+getASerHRRB().getClave()+"::smallint);"; 
                    break;
                case 4:
                    sQuery = "SELECT * FROM buscaPacientesHospitalizados();"; 
                    break;      
                case 5:
                    sQuery = "SELECT * FROM buscaPacientesHospitalizadosAltaArea("+getASerHRRB().getClave()+"::smallint);";
                    break;
                case 6:
                    sQuery = "SELECT * FROM buscaPacientesHospitalizadosAlta();"; 
                    break;
                    
            }
		
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {                            
                            oHospitalizacion = new Hospitalizacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);                           
                            oHospitalizacion.getPaciente().setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oHospitalizacion.setFechaIngresoHos((Date) vRowTemp.get(1));
                            oHospitalizacion.getPaciente().setApPaterno((String)vRowTemp.get(2));
                            oHospitalizacion.getPaciente().setApMaterno((String)vRowTemp.get(3));
                            oHospitalizacion.getPaciente().setNombres((String)vRowTemp.get(4));
                            oHospitalizacion.getPaciente().setFechaNac((Date) vRowTemp.get(5));                            
                            edad=(String)vRowTemp.get(6);
                            //System.out.println(edad);
                            if(!edad.equals("")){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oHospitalizacion.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oHospitalizacion.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oHospitalizacion.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            }
                            else 
                                oHospitalizacion.getPaciente().setEdadNumero("0");
                            oHospitalizacion.getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(7));
                            oHospitalizacion.getPaciente().setCurp((String)vRowTemp.get(8));
                            oHospitalizacion.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                            oHospitalizacion.setNumIngresoHos(((Double) vRowTemp.get(10)).longValue());
                            oHospitalizacion.getEpisodioMedico().setClaveEpisodio(((Double) vRowTemp.get(11)).longValue());
                            fAlta=((Date) vRowTemp.get(12));
                            if(fAlta!=null){
                                FechaA=fecha.format(fAlta);
                                FechaH=fecha.format(fHoy);
                                if(FechaA.compareTo(FechaH)<=0){
                                  oHospitalizacion.setPlan("#A00000");  
                                }  
                            }
                            oHospitalizacion.getEpisodioMedico().getArea().setDescripcion((String)vRowTemp.get(13));
                            vObj.add(oHospitalizacion);
			}
		} 
                
		return vObj; 
	}                 
//**************************************************************************************************************************************        
	public boolean buscarDatosCita() throws Exception{
	boolean bRet = false;
        Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
        ArrayList<Hospitalizacion> vObj=null;
	String sQuery = "";
        int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosPacienteCita("+oPaciente.getFolioPaciente()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.oPaciente.setNombres((String) vRowTemp.get( 0 ));                                
                                this.oPaciente.setApPaterno((String) vRowTemp.get( 1 ));
                                this.oPaciente.setApMaterno((String) vRowTemp.get( 2 ));  
                                this.oPaciente.setFechaNac((Date) vRowTemp.get( 3 ));  
                                String Sexo=(String)vRowTemp.get(4);
                                if(Sexo.equals("M"))
                                    this.oPaciente.setSexoP("Masculino");                                                                   
                                else
                                    this.oPaciente.setSexoP("Femenino");
                                this.oPaciente.setTelefono((String) vRowTemp.get( 5 ));                                
                                this.oSeguro.setNumero((String) vRowTemp.get( 6 ));
                                this.oSeguro.setVigencia((Date) vRowTemp.get( 7 ));
                                if(((String) vRowTemp.get(8)).equals(""))
                                    this.oPaciente.setOtroPais("MÉXICO");
                                else
                                    this.oPaciente.setOtroPais((String) vRowTemp.get(8));
                                this.oEstado.setDescripcionEdo((String) vRowTemp.get( 9 ));
                                this.oMunicipio.setDescripcionMun((String) vRowTemp.get( 10 ));
                                this.oCiudad.setDescripcionCiu((String) vRowTemp.get( 11 ));                                

				bRet = true;
			}
		} 
		return bRet; 
	}         
//************************************************************************************************************************************** 
	public boolean buscarDatosEdoSalud() throws Exception{
	boolean bRet = false;
        Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
        ArrayList<Hospitalizacion> vObj=null;
	String sQuery = "";
        String edad="";
        int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaEdoSalud("+oPaciente.getFolioPaciente()+"::bigint, "+getNumIngresoHos()+"::bigint, "+oEpisodioMedico.getClaveEpisodio()+");"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.oPaciente.getExpediente().setNumero(((Double) vRowTemp.get( 0 )).intValue());
                                this.oPaciente.setNombres((String) vRowTemp.get( 1 ));                                
                                this.oPaciente.setApPaterno((String) vRowTemp.get( 2 ));
                                this.oPaciente.setApMaterno((String) vRowTemp.get( 3 ));  
                                edad=(String)vRowTemp.get(4);
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0'){
                                            if(edad.charAt(1)=='0'){
                                                if(edad.charAt(2)=='1'){
                                                    this.oPaciente.setEdadNumero(edad.substring(2, 3)+" AÑO");                                                    
                                                }else{
                                                    this.oPaciente.setEdadNumero(edad.substring(2, 3)+" AÑOS");
                                                }
                                            }else{
                                                this.oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");   
                                            }
                                        }else{
                                            this.oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                        }
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0){
                                            if(edad.charAt(4)=='0'){
                                                if(edad.charAt(5)=='1'){
                                                    this.oPaciente.setEdadNumero(edad.substring(5, 6)+" MES");
                                                }else{
                                                    this.oPaciente.setEdadNumero(edad.substring(5, 6)+" MESES");
                                                }
                                            }else{    
                                                this.oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                                            }
                                        }else{
                                            if(edad.charAt(8)=='1'){
                                                this.oPaciente.setEdadNumero(edad.substring(8, 9)+" DÍA");
                                            }else{
                                                this.oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                            }
                                        }
                                    }
                                this.oEpisodioMedico.getDiagIngreso().setDescripcionDiag((String) vRowTemp.get( 5 ));
                                this.oEpisodioMedico.setEdoSaludStr((String) vRowTemp.get( 6 ));                                                              
				bRet = true;
			}
		} 
		return bRet; 
	}         
//**************************************************************************************************************************************          
	public boolean buscarDatosEstanciaPaciente() throws Exception{
	boolean bRet = false;
        Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
        ArrayList<Hospitalizacion> vObj=null;
	String sQuery = "";
        String edad="";

        int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
                     //System.out.println("antes de la busqueda");
			sQuery = "SELECT * FROM buscaDatosPacienteCODE("+oPaciente.getFolioPaciente()+"::bigint, "+oPaciente.getExpediente().getNumero()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
                            //System.out.println("Si entra a este If");
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                                this.oPaciente.setNombres((String) vRowTemp.get( 1 ));                                
                                this.oPaciente.setApPaterno((String) vRowTemp.get( 2 ));
                                this.oPaciente.setApMaterno((String) vRowTemp.get( 3 ));  
                                edad=(String)vRowTemp.get(4);
                                //this.oPaciente.setEdadNumero((String) vRowTemp.get( 4 ));
                                this.oPaciente.setClaveEdadP((String) vRowTemp.get( 5 ));  
                                switch (oPaciente.getClaveEdadP()) {
                                case "AÑOS":
                                    {
                                        int edadN=Integer.parseInt(edad.substring(0, 3));
                                        this.oPaciente.setEdad(edadN);
                                        break;
                                    }
                                case "MESES":
                                    {
                                        int edadN=Integer.parseInt(edad.substring(4, 6));
                                        this.oPaciente.setEdad(edadN);
                                        if(edadN<=3){
                                            String nacHosp=(String)vRowTemp.get(6);
                                            if(nacHosp.equals("N"))
                                                oPaciente.setNacidoEnHospitalP("NO");
                                            else
                                                oPaciente.setNacidoEnHospitalP("SI");
                                        }   break;
                                    }
                                case "DÍAS":
                                    {
                                        int edadN=Integer.parseInt(edad.substring(7, 9));
                                        this.oPaciente.setEdad(edadN);
                                        String nacHosp=(String)vRowTemp.get(6);
                                        if(nacHosp.equals("N"))
                                            oPaciente.setNacidoEnHospitalP("NO");
                                        else
                                            oPaciente.setNacidoEnHospitalP("SI");
                                        break;
                                    }
                            }
                                String Sexo=(String)vRowTemp.get(7);
                                if(Sexo.equals("M")){
                                    this.oPaciente.setSexoP("Masculino");
                                    this.oPaciente.setSexos("true");                                    
                                }
                                else{
                                    this.oPaciente.setSexoP("Femenino");
                                    this.oPaciente.setSexos("false");
                                }
                                this.oPaciente.setCurp((String) vRowTemp.get( 8 ));
                                this.oPaciente.setPeso(((Double)vRowTemp.get( 9 )).floatValue());
                                this.oPaciente.setTalla(((Double)vRowTemp.get( 10 )).floatValue());  
                                this.oEstado.setDescripcionEdo((String) vRowTemp.get( 11 ));
                                this.oEstado.setClaveEdo((String) vRowTemp.get( 12 ));
                                this.oMunicipio.setDescripcionMun((String) vRowTemp.get( 13 ));
                                this.oMunicipio.setClaveMun((String) vRowTemp.get( 14 ));
                                this.oCiudad.setDescripcionCiu((String) vRowTemp.get( 15 ));
                                this.oCiudad.setClaveCiu((String) vRowTemp.get( 16 ));
                                this.oExpediente.setNumero(((Double)vRowTemp.get( 17 )).intValue());
                                this.oSeguro.setDerechohabienteP((String) vRowTemp.get( 18 ));
                                this.oSeguro.setNumero((String) vRowTemp.get( 19 ));
                                this.oEtnicidad.setPertenenciaGpoIndP((String) vRowTemp.get( 20 ));
                                this.oEtnicidad.setHablaLenguaIndP((String) vRowTemp.get( 21 ));
                                this.oEtnicidad.setHablaEspaniolP((String) vRowTemp.get( 22 ));
                                this.oEtnicidad.setClaveLengua((String) vRowTemp.get( 23 ));
				bRet = true;
			}
		} 
		return bRet; 
	}         
        
//**************************************************************************************************************************************
	public int buscarHospitalizado() throws Exception{
	int nHospitalizacion=0;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNumeroHospitalizacion("+oPaciente.getFolioPaciente()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				nHospitalizacion=(((Double)vRowTemp.get( 0 )).intValue());
			}
		} 
		return nHospitalizacion; 
	}         
        
//*+***********************************************************************************************************
        public Hospitalizacion[] llenarCode(long noFolio) throws Exception{
            System.out.println("LLegue aqui :D");
        Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaCODE("+noFolio+"::bigint);"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Hospitalizacion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oHospitalizacion = new Hospitalizacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            getPaciente().setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
			} 
		} 
                FacesContext.getCurrentInstance().getExternalContext().redirect("hospitalizacion/HojaCode");
		return arrRet; 
        }
//*************************************************************************************************************        
        
	public Hospitalizacion[] buscarTodos() throws Exception{
	Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosHospitalizacion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Hospitalizacion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHospitalizacion();"; 
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
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHospitalizacion();"; 
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

	//*************************************************************************************************************************
	public int modificarInsertarFolioCODE() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaInsertaFolioCODE('"+sUsuario+"', "+getNumIngresoHos()+"::bigint, "+oPaciente.getFolioPaciente()+"::bigint, " 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint, '"+getFolioCode()+"');";                                          
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
//*************************************************************************************************************************
	public int modificarInsertarEstancia() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";       
        String fAltaH;
        String sLlave=oPaciente.getFolioPaciente()+", "+oEpisodioMedico.getClaveEpisodio();                

        if(oEpisodioMedico.getAltaHospitalaria()!=null){
            Date fAltaHosp=(oEpisodioMedico.getAltaHospitalaria());
            Date hora= new Date();
            SimpleDateFormat fA=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat hA = new SimpleDateFormat("HH:mm:ss");
            fAltaH=fA.format(fAltaHosp)+" "+hA.format(hora);
        }else{
            fAltaH="";
        }

        String tap="";
        String ini="";
        String te="";
        String cveRef="";
        String translado="";
        String procedencia="";
        if(oEpisodioMedico.getTipoAfePrinc()=='1' || oEpisodioMedico.getTipoAfePrinc()=='2')
            tap=""+oEpisodioMedico.getTipoAfePrinc();

        if(oEpisodioMedico.getInfeccionIntrahospitalaria()=='S'||oEpisodioMedico.getInfeccionIntrahospitalaria()=='N')
            ini=""+oEpisodioMedico.getInfeccionIntrahospitalaria();

        if(getTipoEstancia()=='1'||getTipoEstancia()=='2')
            te=""+getTipoEstancia();
        //System.out.println("Procedencia "+getProcedenciaP());
        if(getProcedenciaP().equals("T0403")){
            if(oPaciente.getReferencia().getClave().compareTo("")==0)
                cveRef="null";
            else
                cveRef="'"+oPaciente.getReferencia().getClave()+"'";
        }else
            cveRef="null";
        
        String motivoEgreso="";
        
        if(oEpisodioMedico.getMotivoEgresoP()==null || oEpisodioMedico.getMotivoEgresoP().equals("     ") ||oEpisodioMedico.getMotivoEgresoP().equals(""))
            motivoEgreso="null";
        else
            motivoEgreso="'"+oEpisodioMedico.getMotivoEgresoP()+"'";
        
        if(oEpisodioMedico.getMotivoEgresoP().equals("T3804") || oEpisodioMedico.getMotivoEgresoP().equals("T3803"))
            translado="'"+oEpisodioMedico.getRazonAltaVolunTrasl()+"'";
        else
            translado="null";
        
        if(getProcedenciaP()==null || getProcedenciaP().equals("     ") || getProcedenciaP().equals(""))
            procedencia="null";
        else
            procedencia="'"+getProcedenciaP()+"'";
        
        if(oServicioIngreso.getAreaServicioSAEH().getClave()==null || oServicioIngreso.getAreaServicioSAEH().getClave().equals(""))
            oServicioSegundo.getAreaServicioSAEH().setClave("");
        
        if(oServicioSegundo.getAreaServicioSAEH().getClave()==null || oServicioSegundo.getAreaServicioSAEH().getClave().equals(""))
            oServicioTercero.getAreaServicioSAEH().setClave("");           
        
        if(oEpisodioMedico.getAfePrincipal().getCIE10().getClave()==null || oEpisodioMedico.getAfePrincipal().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfePrimera().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfePrimera().getCIE10().getClave()==null || oEpisodioMedico.getAfePrimera().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeSegunda().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeSegunda().getCIE10().getClave()==null || oEpisodioMedico.getAfeSegunda().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeTercera().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeTercera().getCIE10().getClave()==null || oEpisodioMedico.getAfeTercera().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeCuarta().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeCuarta().getCIE10().getClave()==null || oEpisodioMedico.getAfeCuarta().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeQuinta().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeQuinta().getCIE10().getClave()==null || oEpisodioMedico.getAfeQuinta().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeSexta().getCIE10().setClave("");
        
        if(oEpisodioMedico.getProceRe1().getCIE9().getClave()==null || oEpisodioMedico.getProceRe1().getCIE9().getClave().equals(""))
            oEpisodioMedico.getProceRe2().getCIE9().setClave("");
        
        if(oEpisodioMedico.getProceRe2().getCIE9().getClave()==null || oEpisodioMedico.getProceRe2().getCIE9().getClave().equals(""))
            oEpisodioMedico.getProceRe3().getCIE9().setClave("");
        
        if(oEpisodioMedico.getProceRe3().getCIE9().getClave()==null || oEpisodioMedico.getProceRe3().getCIE9().getClave().equals(""))
            oEpisodioMedico.getProceRe4().getCIE9().setClave("");
        
        if(oEpisodioMedico.getProceRe4().getCIE9().getClave()==null || oEpisodioMedico.getProceRe4().getCIE9().getClave().equals(""))
            oEpisodioMedico.getProceRe5().getCIE9().setClave("");
        
        if(oEpisodioMedico.getProceRe5().getCIE9().getClave()==null || oEpisodioMedico.getProceRe5().getCIE9().getClave().equals(""))
            oEpisodioMedico.getProceRe6().getCIE9().setClave("");
        
        if(oEpisodioMedico.getProceRe6().getCIE9().getClave()==null || oEpisodioMedico.getProceRe6().getCIE9().getClave().equals(""))
            oEpisodioMedico.getProceRe7().getCIE9().setClave("");
        
        if(oEpisodioMedico.getProceRe7().getCIE9().getClave()==null || oEpisodioMedico.getProceRe7().getCIE9().getClave().equals(""))
            oEpisodioMedico.getProceRe8().getCIE9().setClave("");
                    
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
                     //System.out.println(ObtenerNoTarjetaMedico(oEpisodioMedico.getProceRe1().getCirujano().getNombres()));
			sQuery = "SELECT * FROM modificaInsertaEstanciaCODE('"+sUsuario+"', "+oPaciente.getFolioPaciente()+"::bigint," 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint, "+getNumIngresoHos()+"::bigint, "
                                +((fAltaH.compareTo("")!=0)?"'"+fAltaH+"'":null)+", "+motivoEgreso+", "+translado+", '"
                                +tap+"', '"+ini+"', '"+te+"', "
                                +procedencia+", "+cveRef+", '"
                                +oServicioIngreso.getAreaServicioSAEH().getClave()+"', '"
                                +oServicioSegundo.getAreaServicioSAEH().getClave()+"', '"
                                +oServicioTercero.getAreaServicioSAEH().getClave()+"', '"
                                +oServicioEgreso.getAreaServicioSAEH().getClave()+"', "
                                +oSalaLabor.getTiempoUso()+", "
                                +oSalaExpulsion.getTiempoUso()+", "
                                +oSalaRecuperacion.getTiempoUso()+", "
                                +oTerapiaIntensiva.getTiempoUso()+", "
                                +oTerapiaIntermedia.getTiempoUso()+", '"
                                +oEpisodioMedico.getAfePrincipal().getCIE10().getClave()+"', '"   
                                +oEpisodioMedico.getAfePrimera().getCIE10().getClave()+"', '"                               
                                +oEpisodioMedico.getAfeSegunda().getCIE10().getClave()+"', '"
                                +oEpisodioMedico.getAfeTercera().getCIE10().getClave()+"', '"
                                +oEpisodioMedico.getAfeCuarta().getCIE10().getClave()+"', '"
                                +oEpisodioMedico.getAfeQuinta().getCIE10().getClave()+"', '"
                                +oEpisodioMedico.getAfeSexta().getCIE10().getClave()+"', '"
                                +oEpisodioMedico.getAfeResAP().getCIE10().getClave()+"', '"
                                +oEpisodioMedico.getProceRe1().getCIE9().getClave()+"', '"
                                +oEpisodioMedico.getProceRe1().getTipoAnestesiaP()+"', '"        
                                +oEpisodioMedico.getProceRe1().getDuracionP()+"', "
                                +obtenerNoTarjetaMedico(oEpisodioMedico.getProceRe1().getCirujano().getNombres())+", '"
                                +oEpisodioMedico.getProceRe1().getQuirofano()+"', '"                                
                                +oEpisodioMedico.getProceRe2().getCIE9().getClave()+"', '"
                                +((oEpisodioMedico.getProceRe2().getTipoAnestesiaP()==null)?"":oEpisodioMedico.getProceRe2().getTipoAnestesiaP())+"', '"        
                                +((oEpisodioMedico.getProceRe2().getDuracionP()==null)?"":oEpisodioMedico.getProceRe2().getDuracionP())+"', "
                                +obtenerNoTarjetaMedico((oEpisodioMedico.getProceRe2().getCirujano().getNombres()==null)?"":oEpisodioMedico.getProceRe2().getCirujano().getNombres())+", '"
                                +((oEpisodioMedico.getProceRe2().getQuirofano()==null)?"":oEpisodioMedico.getProceRe2().getQuirofano())+"', '"                                
                                +oEpisodioMedico.getProceRe3().getCIE9().getClave()+"', '"
                                +((oEpisodioMedico.getProceRe3().getTipoAnestesiaP()==null)?"":oEpisodioMedico.getProceRe3().getTipoAnestesiaP())+"', '"        
                                +((oEpisodioMedico.getProceRe3().getDuracionP()==null)?"":oEpisodioMedico.getProceRe3().getDuracionP())+"', "
                                +obtenerNoTarjetaMedico((oEpisodioMedico.getProceRe3().getCirujano().getNombres()==null)?"":oEpisodioMedico.getProceRe3().getCirujano().getNombres())+", '"
                                +((oEpisodioMedico.getProceRe3().getQuirofano()==null)?"":oEpisodioMedico.getProceRe3().getQuirofano())+"', '"                                
                                +oEpisodioMedico.getProceRe4().getCIE9().getClave()+"', '"
                                +((oEpisodioMedico.getProceRe4().getTipoAnestesiaP()==null)?"":oEpisodioMedico.getProceRe4().getTipoAnestesiaP())+"', '"        
                                +((oEpisodioMedico.getProceRe4().getDuracionP()==null)?"":oEpisodioMedico.getProceRe4().getDuracionP())+"', "
                                +obtenerNoTarjetaMedico((oEpisodioMedico.getProceRe4().getCirujano().getNombres()==null)?"":oEpisodioMedico.getProceRe4().getCirujano().getNombres())+", '"
                                +((oEpisodioMedico.getProceRe4().getQuirofano()==null)?"":oEpisodioMedico.getProceRe4().getQuirofano())+"', '"                                
                                +oEpisodioMedico.getProceRe5().getCIE9().getClave()+"', '"
                                +((oEpisodioMedico.getProceRe5().getTipoAnestesiaP()==null)?"":oEpisodioMedico.getProceRe5().getTipoAnestesiaP())+"', '"        
                                +((oEpisodioMedico.getProceRe5().getDuracionP()==null)?"":oEpisodioMedico.getProceRe5().getDuracionP())+"', "
                                +obtenerNoTarjetaMedico((oEpisodioMedico.getProceRe5().getCirujano().getNombres()==null)?"":oEpisodioMedico.getProceRe5().getCirujano().getNombres())+", '"
                                +((oEpisodioMedico.getProceRe5().getQuirofano()==null)?"":oEpisodioMedico.getProceRe5().getQuirofano())+"', '"                              
                                +oEpisodioMedico.getProceRe6().getCIE9().getClave()+"', '"
                                +((oEpisodioMedico.getProceRe6().getTipoAnestesiaP()==null)?"":oEpisodioMedico.getProceRe6().getTipoAnestesiaP())+"', '"        
                                +((oEpisodioMedico.getProceRe6().getDuracionP()==null)?"":oEpisodioMedico.getProceRe6().getDuracionP())+"', "
                                +obtenerNoTarjetaMedico((oEpisodioMedico.getProceRe6().getCirujano().getNombres()==null)?"":oEpisodioMedico.getProceRe6().getCirujano().getNombres())+", '"
                                +((oEpisodioMedico.getProceRe6().getQuirofano()==null)?"":oEpisodioMedico.getProceRe6().getQuirofano())+"', '"                                
                                +oEpisodioMedico.getProceRe7().getCIE9().getClave()+"', '"
                                +((oEpisodioMedico.getProceRe7().getTipoAnestesiaP()==null)?"":oEpisodioMedico.getProceRe7().getTipoAnestesiaP())+"', '"        
                                +((oEpisodioMedico.getProceRe7().getDuracionP()==null)?"":oEpisodioMedico.getProceRe7().getDuracionP())+"', "
                                +obtenerNoTarjetaMedico((oEpisodioMedico.getProceRe7().getCirujano().getNombres()==null)?"":oEpisodioMedico.getProceRe7().getCirujano().getNombres())+", '"
                                +((oEpisodioMedico.getProceRe7().getQuirofano()==null)?"":oEpisodioMedico.getProceRe7().getQuirofano())+"', '"                               
                                +oEpisodioMedico.getProceRe8().getCIE9().getClave()+"', '"
                                +((oEpisodioMedico.getProceRe8().getTipoAnestesiaP()==null)?"":oEpisodioMedico.getProceRe8().getTipoAnestesiaP())+"', '"        
                                +((oEpisodioMedico.getProceRe8().getDuracionP()==null)?"":oEpisodioMedico.getProceRe8().getDuracionP())+"', "
                                +obtenerNoTarjetaMedico((oEpisodioMedico.getProceRe8().getCirujano().getNombres()==null)?"":oEpisodioMedico.getProceRe8().getCirujano().getNombres())+", '"
                                +((oEpisodioMedico.getProceRe8().getQuirofano()==null)?"":oEpisodioMedico.getProceRe8().getQuirofano())+"', '" 
                                +sLlave+"');";                  
                        System.out.println("Referencia "+ oPaciente.getReferencia().getClave());
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
//*************************************************************************************************************************
	public int obtenerNoTarjetaMedico(String sMedico) throws Exception{
	ArrayList rst = null;
	int nRet = 0;        
        if(sMedico.compareTo("")!=0){
            int inicio=sMedico.indexOf("-");
            nRet = Integer.parseInt(sMedico.substring(inicio+1));       
        }
		return nRet; 
	}          

//*************************************************************************************************************************
	public int modificarInsertarDefuncion() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String sLlave=oPaciente.getFolioPaciente()+", "+oEpisodioMedico.getClaveEpisodio();
        
        if(oEpisodioMedico.getAfePrimera().getCIE10().getClave()==null || oEpisodioMedico.getAfePrimera().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeSegunda().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeSegunda().getCIE10().getClave()==null || oEpisodioMedico.getAfeSegunda().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeTercera().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeTercera().getCIE10().getClave()==null || oEpisodioMedico.getAfeTercera().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeCuarta().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeCuarta().getCIE10().getClave()==null || oEpisodioMedico.getAfeCuarta().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeQuinta().getCIE10().setClave("");
        
        if(oEpisodioMedico.getAfeQuinta().getCIE10().getClave()==null || oEpisodioMedico.getAfeQuinta().getCIE10().getClave().equals(""))
            oEpisodioMedico.getAfeSexta().getCIE10().setClave("");
            
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaInsertaDefuncionCODE('"+sUsuario+"', "+oPaciente.getFolioPaciente()+"::bigint," 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint, '"+oEpisodioMedico.getAvisoMinisterioDefun()+"','"+oPaciente.getFolioDefuncion()+"',"
                                +oEpisodioMedico.getAfePrimera().getNumEnfermedadMuerte()+"::smallint,'"+oEpisodioMedico.getAfePrimera().getTiempoEnfermedadMuerteP()+"','"
                                +oEpisodioMedico.getAfePrimera().getCIE10().getClave()+"',"
                                +oEpisodioMedico.getAfeSegunda().getNumEnfermedadMuerte()+"::smallint,'"+oEpisodioMedico.getAfeSegunda().getTiempoEnfermedadMuerteP()+"','"
                                +oEpisodioMedico.getAfeSegunda().getCIE10().getClave()+"',"
                                +oEpisodioMedico.getAfeTercera().getNumEnfermedadMuerte()+"::smallint,'"+oEpisodioMedico.getAfeTercera().getTiempoEnfermedadMuerteP()+"','"
                                +oEpisodioMedico.getAfeTercera().getCIE10().getClave()+"',"
                                +oEpisodioMedico.getAfeCuarta().getNumEnfermedadMuerte()+"::smallint,'"+oEpisodioMedico.getAfeCuarta().getTiempoEnfermedadMuerteP()+"','"
                                +oEpisodioMedico.getAfeCuarta().getCIE10().getClave()+"',"
                                +oEpisodioMedico.getAfeQuinta().getNumEnfermedadMuerte()+"::smallint,'"+oEpisodioMedico.getAfeQuinta().getTiempoEnfermedadMuerteP()+"','"
                                +oEpisodioMedico.getAfeQuinta().getCIE10().getClave()+"',"
                                +oEpisodioMedico.getAfeSexta().getNumEnfermedadMuerte()+"::smallint,'"+oEpisodioMedico.getAfeSexta().getTiempoEnfermedadMuerteP()+"','"
                                +oEpisodioMedico.getAfeSexta().getCIE10().getClave()+"','"
                                +oEpisodioMedico.getAfeResAPDef().getCIE10().getClave()+"','"+sLlave+"');";                                          
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
//************************************************************************************************************************* 
	public int modificarInsertarAtencionObstetrica() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";       
        String sLlave=oPaciente.getFolioPaciente()+", "+oEpisodioMedico.getClaveEpisodio();
        String ta="";
        String cp="";
        String extexp="";
        String cn1="", cn2="", cn3="";
        String rn1="", rn2="", rn3="";
        if(oAtencionObstetrica.getExtraccion()!=null)
            extexp=oAtencionObstetrica.getExtraccion();
        if(oAtencionObstetrica.getTipoAtencion()=='A' || oAtencionObstetrica.getTipoAtencion()=='P')
            ta=""+oAtencionObstetrica.getTipoAtencion();
        if(oAtencionObstetrica.getConProducto()=='U' || oAtencionObstetrica.getConProducto()=='M')
            cp=""+oAtencionObstetrica.getConProducto();
        if(oAtencionObstetrica.getP1().getCondicionNacimiento()=='M' || oAtencionObstetrica.getP1().getCondicionNacimiento()=='N')
            cn1=""+oAtencionObstetrica.getP1().getCondicionNacimiento();    
        if(oAtencionObstetrica.getP2().getCondicionNacimiento()=='M' || oAtencionObstetrica.getP2().getCondicionNacimiento()=='N')
            cn2=""+oAtencionObstetrica.getP2().getCondicionNacimiento(); 
        if(oAtencionObstetrica.getP3().getCondicionNacimiento()=='M' || oAtencionObstetrica.getP3().getCondicionNacimiento()=='N')
            cn3=""+oAtencionObstetrica.getP3().getCondicionNacimiento(); 
        if(oAtencionObstetrica.getTipoAtencion()=='A'){
            oAtencionObstetrica.setConProducto(' ');
            oAtencionObstetrica.setTipoNacimientoP("");
        }
        if(oAtencionObstetrica.getP1().getReanimacionNeonatal()!=null)
            rn1=oAtencionObstetrica.getP1().getReanimacionNeonatal();
        if(oAtencionObstetrica.getP2().getReanimacionNeonatal()!=null)
            rn2=oAtencionObstetrica.getP2().getReanimacionNeonatal();
        if(oAtencionObstetrica.getP3().getReanimacionNeonatal()!=null)
            rn3=oAtencionObstetrica.getP3().getReanimacionNeonatal();
        
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaInsertaAtencionObstetricaCODE('"+sUsuario+"', " 
                                +getNumIngresoHos()+"::bigint, "+oExpediente.getNumero()+", '"+extexp+"', '"
                                +ta+"', "+oAtencionObstetrica.getSemanasGestacion()+"::smallint, '"+cp+"', '"+oAtencionObstetrica.getTipoNacimientoP()+"', "
                                +oAtencionObstetrica.getAntecGinecoObstetricos().getGestaciones()+", "+oAtencionObstetrica.getAntecGinecoObstetricos().getPartos()+", "
                                +oAtencionObstetrica.getAntecGinecoObstetricos().getAbortos()+", '"+oAtencionObstetrica.getAntecGinecoObstetricos().getMetodoAnticonceptivo().getClave()+"', '"
                                +oAtencionObstetrica.getP1().getFolioCertificadoNac()+"', "+oAtencionObstetrica.getP1().getPesoAlNacer()+"::smallint, '"
                                +oAtencionObstetrica.getP1().getSexoProductoP()+"', '"+cn1+"', "+oAtencionObstetrica.getP1().getApgar5Min()+"::smallint, '"
                                +rn1+"', "+oAtencionObstetrica.getP1().getEstanciaEnCunero()+"::smallint, '"
                                +oAtencionObstetrica.getP1().getCondicion()+"', '"
                                +oAtencionObstetrica.getP2().getFolioCertificadoNac()+"', "+oAtencionObstetrica.getP2().getPesoAlNacer()+"::smallint, '"
                                +oAtencionObstetrica.getP2().getSexoProductoP()+"', '"+cn2+"', "+oAtencionObstetrica.getP2().getApgar5Min()+"::smallint, '"
                                +rn2+"', "+oAtencionObstetrica.getP2().getEstanciaEnCunero()+"::smallint, '"
                                +oAtencionObstetrica.getP2().getCondicion()+"', '"
                                +oAtencionObstetrica.getP3().getFolioCertificadoNac()+"', "+oAtencionObstetrica.getP3().getPesoAlNacer()+"::smallint, '"
                                +oAtencionObstetrica.getP3().getSexoProductoP()+"', '"+cn3+"', "+oAtencionObstetrica.getP3().getApgar5Min()+"::smallint, '"
                                +rn3+"', "+oAtencionObstetrica.getP3().getEstanciaEnCunero()+"::smallint, '"
                                +oAtencionObstetrica.getP3().getCondicion()+"');";     
                        
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
//*************************************************************************************************************************        
	public int modificaEstadoCODE(String estado) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEstadoCODE('"+sUsuario+"', "+getNumIngresoHos()+"::bigint, "+oPaciente.getFolioPaciente()+"::bigint, " 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint, '"+estado+"');";                                          
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
//*************************************************************************************************************************    
	public int modificaEstadoHojaAlta() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEstadoHojaAlta('"+sUsuario+"', "+getNumIngresoHos()+"::bigint, "+oPaciente.getFolioPaciente()+"::bigint, " 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint);";                                          
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
//*************************************************************************************************************************           
	public int modificaEdoSalud() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEdoSalud('"+sUsuario+"', "+oPaciente.getFolioPaciente()+"::bigint, " 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint, '"+oEpisodioMedico.getEdoSaludStr()+"', '"+oEpisodioMedico.getDiagIngreso().getClave()+"');";    
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
//*************************************************************************************************************************    

	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHospitalizacion();"; 
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

	//*************************************************************************************************************
	public boolean buscarCodeFolio() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        String fAlta="";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaFolioCode("+getNumIngresoHos()+"::bigint,"+oPaciente.getFolioPaciente()+"::bigint,"+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                System.out.println("Folio: "+(String) vRowTemp.get( 0 ));
                                this.setFolioCode((String) vRowTemp.get( 0 ));
				bRet = true;                               
			}
		} 
		return bRet; 
	}          
    
    //*********************************************************************************************************                  
	public boolean buscarCodeEstancia() throws Exception{                       
        SimpleDateFormat fechaIE = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            //fechaIngresoStr=(fechaIng.format(oAdmision.getFechaIngreso()));
                            //oAdmision.setFechaIngresoStr(fechaIngresoStr);    
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        String fAlta="";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosEstanciaCODE("+oPaciente.getFolioPaciente()+"::bigint,"+getNumIngresoHos()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                Date fIngreso=((Date) vRowTemp.get( 0 ));
                                Date fAltaHos=((Date) vRowTemp.get( 1 ));
                                SimpleDateFormat fI=new SimpleDateFormat("dd/MM/yyyy");
                                fIngre=fI.format(fIngreso);
                                //feIngre=fI.parse(fIngre);
                                this.setFechaIngresoHos(fIngreso);
                                this.oEpisodioMedico.setFIngreso(fIngre);
                                this.oEpisodioMedico.setAltaHospitalaria(fAltaHos);
                                if(((String) vRowTemp.get( 2 )).compareTo("")!=0)        
                                    this.setTipoEstancia(((String) vRowTemp.get( 2 )).charAt(0));
                                this.setProcedenciaP((String) vRowTemp.get( 3 ));
                                this.oPaciente.getReferencia().setClave((String) vRowTemp.get( 4 ));
                                this.oEpisodioMedico.setMotivoEgresoP((String) vRowTemp.get( 5 ));
                                System.out.println("Motivo Egreso: "+((String) vRowTemp.get( 5 )));
                                this.oEpisodioMedico.setRazonAltaVolunTrasl((String) vRowTemp.get( 6 ));
                                //System.out.println("Este char"+vRowTemp.get(6));
                                if(((String) vRowTemp.get( 7 )).compareTo("")!=0)
                                    this.oEpisodioMedico.setTipoAfePrinc(((String) vRowTemp.get( 7 )).charAt(0));     
                                if(((String) vRowTemp.get( 8 )).compareTo("")!=0)
                                    this.oEpisodioMedico.setInfeccionIntrahospitalaria(((String) vRowTemp.get( 8 )).charAt(0));
				bRet = true;                               
			}
                        //this.oAfePrincipal.getCIE10().setDescripcionDiag("");
		} 
		return bRet; 
	}          
        
//*********************************************************************************************************  
	public boolean buscarCodeAreasApoyo() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        Date feIngre=null;
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAreasApoyoCODE("+getNumIngresoHos()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                switch ((String) vRowTemp.get( 0 )) {
                                    case "1":
                                        this.oSalaLabor.getAreaApoyo().setClave((String) vRowTemp.get( 0 ));
                                        this.oSalaLabor.getAreaApoyo().setDescripcion((String) vRowTemp.get( 1 ));                                    
                                        this.oSalaLabor.setTiempoUso(((Double) vRowTemp.get( 2 )).intValue());
                                        break;
                                    case "2":
                                        this.oSalaExpulsion.getAreaApoyo().setClave((String) vRowTemp.get( 0 ));
                                        this.oSalaExpulsion.getAreaApoyo().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oSalaExpulsion.setTiempoUso(((Double) vRowTemp.get( 2 )).intValue());
                                        break;
                                    case "3":
                                        this.oSalaRecuperacion.getAreaApoyo().setClave((String) vRowTemp.get( 0 ));
                                        this.oSalaRecuperacion.getAreaApoyo().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oSalaRecuperacion.setTiempoUso(((Double) vRowTemp.get( 2 )).intValue());
                                        break;
                                    case "4":
                                        this.oTerapiaIntensiva.getAreaApoyo().setClave((String) vRowTemp.get( 0 ));
                                        this.oTerapiaIntensiva.getAreaApoyo().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oTerapiaIntensiva.setTiempoUso(((Double) vRowTemp.get( 2 )).intValue());
                                        break;
                                    case "5":
                                        this.oTerapiaIntermedia.getAreaApoyo().setClave((String) vRowTemp.get( 0 ));
                                        this.oTerapiaIntermedia.getAreaApoyo().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oTerapiaIntermedia.setTiempoUso(((Double) vRowTemp.get( 2 )).intValue());
                                        break;
                                }
                            }
				bRet = true;
			}
		} 
		return bRet;                
	}                  
        
//*********************************************************************************************************   
	public boolean buscarCodeAreasServicio() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        Date feIngre=null;
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAreasServicioCODE("+getNumIngresoHos()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                switch ((String) vRowTemp.get( 0 )) {
                                    case "T0301":
                                        this.oServicioIngreso.setOrden((String) vRowTemp.get( 0 ));                                 
                                        this.oServicioIngreso.getAreaServicioSAEH().setDescripcion((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0302":
                                        this.oServicioSegundo.setOrden((String) vRowTemp.get( 0 ));
                                        this.oServicioSegundo.getAreaServicioSAEH().setDescripcion((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0303":
                                        this.oServicioTercero.setOrden((String) vRowTemp.get( 0 ));
                                        this.oServicioTercero.getAreaServicioSAEH().setDescripcion((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0304":
                                        this.oServicioEgreso.setOrden((String) vRowTemp.get( 0 ));
                                        this.oServicioEgreso.getAreaServicioSAEH().setDescripcion((String) vRowTemp.get( 1 ));
                                        break;
                                }
                            }
				bRet = true;
			}
		} 
		return bRet;                
	}                  

//********************************************************************************************************   
	public boolean buscarCodeAfecciones() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("EpisodioMedico.buscarCODE: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAfeccionesTratadasCODE("+oPaciente.getFolioPaciente()+"::bigint,"+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                switch ((String) vRowTemp.get( 0 )) {
                                    case "T0600":
                                        this.oEpisodioMedico.getAfePrincipal().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0601":
                                        this.oEpisodioMedico.getAfePrimera().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0602":
                                        this.oEpisodioMedico.getAfeSegunda().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0603":
                                        this.oEpisodioMedico.getAfeTercera().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0604":
                                        this.oEpisodioMedico.getAfeCuarta().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0605":
                                        this.oEpisodioMedico.getAfeQuinta().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0606":
                                        this.oEpisodioMedico.getAfeSexta().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0619":
                                        this.oEpisodioMedico.getAfeResAP().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                }
                            }
				bRet = true;
			}
		} 
		return bRet; 
	}          
        
//*********************************************************************************************************    
	public boolean buscarCodeProcedimientos() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Lesion.buscarCODE: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaProcedimientosRealizadosCODE("+oPaciente.getFolioPaciente()+"::bigint,"+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;                                
                                switch(((Double) vRowTemp.get( 0 )).intValue()){
                                    case 1:
                                        this.oEpisodioMedico.getProceRe1().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe1().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe1().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe1().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe1().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe1().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe1().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;
                                    case 2:
                                        this.oEpisodioMedico.getProceRe2().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe2().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe2().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe2().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe2().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe2().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe2().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;                                        
                                    case 3:
                                        this.oEpisodioMedico.getProceRe3().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe3().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe3().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe3().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe3().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe3().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe3().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;                                        
                                    case 4:
                                        this.oEpisodioMedico.getProceRe4().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe4().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe4().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe4().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe4().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe4().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe4().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;                                        
                                    case 5:
                                        this.oEpisodioMedico.getProceRe5().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe5().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe5().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe5().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe5().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe5().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe5().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;                                        
                                    case 6:
                                        this.oEpisodioMedico.getProceRe6().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe6().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe6().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe6().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe6().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe6().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe6().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;                                        
                                    case 7:
                                        this.oEpisodioMedico.getProceRe7().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe7().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe7().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe7().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe7().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe7().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe7().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;
                                    case 8:                                                                                    
                                        this.oEpisodioMedico.getProceRe8().getCIE9().setDescripcion((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getProceRe8().setTipoAnestesiaP((String) vRowTemp.get( 2 ));
                                        this.oEpisodioMedico.getProceRe8().setQuirofano((String) vRowTemp.get( 3 ));
                                        this.oEpisodioMedico.getProceRe8().setDuracionP((String) vRowTemp.get( 4 ));
                                        this.oEpisodioMedico.getProceRe8().getCirujano().setNombres((String) vRowTemp.get( 5 ));
                                        //this.oEpisodioMedico.getProceRe8().getCirujano().setApPaterno((String) vRowTemp.get( 6 ));
                                        //this.oEpisodioMedico.getProceRe8().getCirujano().setApMaterno((String) vRowTemp.get( 7 ));
                                        break;
                                }
                            }
                                
				bRet = true;
			}
		} 
		return bRet; 
	}          
        
//*********************************************************************************************************  
	public boolean buscarCodeDatosDefuncion() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        Date feIngre=null;      
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosDefuncionCODE("+oPaciente.getFolioPaciente()+"::bigint,"+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.oPaciente.setFolioDefuncion((String) vRowTemp.get( 0 ));
                            /*if(((String) vRowTemp.get( 1 )).equals("S"))
                                this.oEpisodioMedico.setAvisoMinisterioDefun(true);
                            else if (((String) vRowTemp.get( 1 )).equals("N"))
                                this.oEpisodioMedico.setAvisoMinisterioDefun(false);*/
                            this.oEpisodioMedico.setAvisoMinisterioDefun((String) vRowTemp.get( 1 ));
                            
				bRet = true;                               
			}
                        //this.oAfePrincipal.getCIE10().setDescripcionDiag("");
		} 
		return bRet; 
	}          
        
//*********************************************************************************************************         
	public boolean buscarCodeAfeccionesDefuncion() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        Date feIngre=null;      
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAfeccionesDefuncionCODE("+oPaciente.getFolioPaciente()+"::bigint,"+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                switch ((String) vRowTemp.get( 0 )) {
                                    case "T0608":
                                        this.oEpisodioMedico.getAfePrimera().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getAfePrimera().setNumEnfermedadMuerte(((Double) vRowTemp.get( 2 )).intValue());
                                        this.oEpisodioMedico.getAfePrimera().setTiempoEnfermedadMuerteP((String) vRowTemp.get( 3 ));
                                        break;
                                    case "T0609":
                                        this.oEpisodioMedico.getAfeSegunda().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getAfeSegunda().setNumEnfermedadMuerte(((Double) vRowTemp.get( 2 )).intValue());
                                        this.oEpisodioMedico.getAfeSegunda().setTiempoEnfermedadMuerteP((String) vRowTemp.get( 3 ));
                                        break;
                                    case "T0610":
                                        this.oEpisodioMedico.getAfeTercera().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getAfeTercera().setNumEnfermedadMuerte(((Double) vRowTemp.get( 2 )).intValue());
                                        this.oEpisodioMedico.getAfeTercera().setTiempoEnfermedadMuerteP((String) vRowTemp.get( 3 ));
                                        break;
                                    case "T0611":
                                        this.oEpisodioMedico.getAfeCuarta().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getAfeCuarta().setNumEnfermedadMuerte(((Double) vRowTemp.get( 2 )).intValue());
                                        this.oEpisodioMedico.getAfeCuarta().setTiempoEnfermedadMuerteP((String) vRowTemp.get( 3 ));
                                        break;
                                    case "T0612":
                                        this.oEpisodioMedico.getAfeQuinta().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getAfeQuinta().setNumEnfermedadMuerte(((Double) vRowTemp.get( 2 )).intValue());
                                        this.oEpisodioMedico.getAfeQuinta().setTiempoEnfermedadMuerteP((String) vRowTemp.get( 3 ));
                                        break;
                                    case "T0613":
                                        this.oEpisodioMedico.getAfeSexta().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        this.oEpisodioMedico.getAfeSexta().setNumEnfermedadMuerte(((Double) vRowTemp.get( 2 )).intValue());
                                        this.oEpisodioMedico.getAfeSexta().setTiempoEnfermedadMuerteP((String) vRowTemp.get( 3 ));
                                        break;
                                    case "T0620":
                                        this.oEpisodioMedico.getAfeResAPDef().getCIE10().setDescripcionDiag((String) vRowTemp.get( 1 ));
                                        break;
                                }
                            }
				bRet = true;                               
			}
                        //this.oAfePrincipal.getCIE10().setDescripcionDiag("");
		} 
		return bRet; 
	}          
        
//*********************************************************************************************************  
	public boolean buscarCodeAtencionObstetrica() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        Date feIngre=null;      
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAtencionObstetricaCODE("+getNumIngresoHos()+"::bigint,"+oExpediente.getNumero()+");";
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            /*if(((String) vRowTemp.get( 0 )).equals("N"))
                                this.oAtencionObstetrica.setExtraccion("N");
                            else
                                this.oAtencionObstetrica.setExtraccion("S");*/
                            this.oAtencionObstetrica.setExtraccion(((String) vRowTemp.get( 0 )));
                            if(((String) vRowTemp.get( 1 )).compareTo("")!=0)
                                this.oAtencionObstetrica.setTipoAtencion(((String) vRowTemp.get( 1 )).charAt(0));
                            this.oAtencionObstetrica.setSemanasGestacion(((Double) vRowTemp.get( 2 )).intValue());
                            if(((String) vRowTemp.get( 3 )).compareTo("")!=0)
                                this.oAtencionObstetrica.setConProducto(((String) vRowTemp.get( 3 )).charAt(0));                                                         
                            this.oAtencionObstetrica.setTipoNacimientoP((String) vRowTemp.get( 4 ));				
                            bRet = true;                               
			}
		} 
		return bRet; 
	}          
        
//*********************************************************************************************************  
	public boolean buscarCodeAntecedentesGinecoObstetricos() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        Date feIngre=null;      
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAntcedentesGinecoObstetricosCODE("+oExpediente.getNumero()+");"; 
			System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.oAtencionObstetrica.getAntecGinecoObstetricos().setGestaciones(((Double) vRowTemp.get( 0 )).intValue());
                            this.oAtencionObstetrica.getAntecGinecoObstetricos().setPartos(((Double) vRowTemp.get( 1 )).intValue());
                            this.oAtencionObstetrica.getAntecGinecoObstetricos().setAbortos(((Double) vRowTemp.get( 2 )).intValue());
                            this.oAtencionObstetrica.getAntecGinecoObstetricos().getMetodoAnticonceptivo().setClave((String) vRowTemp.get( 3 ));
                            bRet = true;                               
			}
		} 
		return bRet; 
	}          
        
//*********************************************************************************************************         
	public boolean buscarCodeProductos() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String fIngre="";
        Date feIngre=null;      
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.buscarCODE: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosProductosCODE("+getNumIngresoHos()+"::bigint);"; 
			System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                if(((Double) vRowTemp.get( 0 )).intValue()==1){
                                    this.oAtencionObstetrica.getP1().setPesoAlNacer(((Double) vRowTemp.get( 1 )).intValue());
                                    this.oAtencionObstetrica.getP1().setSexoProductoP((String) vRowTemp.get( 2 ));
                                    if(((String) vRowTemp.get( 3 )).compareTo("")!=0)
                                        this.oAtencionObstetrica.getP1().setCondicionNacimiento(((String) vRowTemp.get( 3 )).charAt(0));
                                    this.oAtencionObstetrica.getP1().setFolioCertificadoNac((String) vRowTemp.get( 4 ));
                                    this.oAtencionObstetrica.getP1().setApgar5Min(((Double) vRowTemp.get( 5 )).intValue());
                                    /*if(((String) vRowTemp.get( 6 )).equals("S"))
                                    this.oAtencionObstetrica.getP1().setReanimacionNeonatal(true);     
                                    else
                                    this.oAtencionObstetrica.getP1().setReanimacionNeonatal(false);*/
                                    this.oAtencionObstetrica.getP1().setReanimacionNeonatal((String) vRowTemp.get( 6 ));
                                    this.oAtencionObstetrica.getP1().setEstanciaEnCunero(((Double) vRowTemp.get( 7 )).intValue());
                                    this.oAtencionObstetrica.getP1().setCondicion((String) vRowTemp.get( 8 ));
                                }
                                else if(((Double) vRowTemp.get( 0 )).intValue()==2){
                                    this.oAtencionObstetrica.getP2().setPesoAlNacer(((Double) vRowTemp.get( 1 )).intValue());
                                    this.oAtencionObstetrica.getP2().setSexoProductoP((String) vRowTemp.get( 2 ));
                                    if(((String) vRowTemp.get( 3 )).compareTo("")!=0)
                                        this.oAtencionObstetrica.getP2().setCondicionNacimiento(((String) vRowTemp.get( 3 )).charAt(0));
                                    this.oAtencionObstetrica.getP2().setFolioCertificadoNac((String) vRowTemp.get( 4 ));
                                    this.oAtencionObstetrica.getP2().setApgar5Min(((Double) vRowTemp.get( 5 )).intValue());
                                    /*if(((String) vRowTemp.get( 6 )).equals("S"))
                                    this.oAtencionObstetrica.getP2().setReanimacionNeonatal(true);     
                                    else
                                    this.oAtencionObstetrica.getP2().setReanimacionNeonatal(false);*/
                                    this.oAtencionObstetrica.getP2().setReanimacionNeonatal((String) vRowTemp.get( 6 )); 
                                    this.oAtencionObstetrica.getP2().setEstanciaEnCunero(((Double) vRowTemp.get( 7 )).intValue());
                                    this.oAtencionObstetrica.getP2().setCondicion((String) vRowTemp.get( 8 ));                                    
                                }
                                else if(((Double) vRowTemp.get( 0 )).intValue()==3){
                                    this.oAtencionObstetrica.getP3().setPesoAlNacer(((Double) vRowTemp.get( 1 )).intValue());
                                    this.oAtencionObstetrica.getP3().setSexoProductoP((String) vRowTemp.get( 2 ));
                                    if(((String) vRowTemp.get( 3 )).compareTo("")!=0)
                                        this.oAtencionObstetrica.getP3().setCondicionNacimiento(((String) vRowTemp.get( 3 )).charAt(0));
                                    this.oAtencionObstetrica.getP3().setFolioCertificadoNac((String) vRowTemp.get( 4 ));
                                    this.oAtencionObstetrica.getP3().setApgar5Min(((Double) vRowTemp.get( 5 )).intValue());
                                    /*if(((String) vRowTemp.get( 6 )).equals("S"))
                                    this.oAtencionObstetrica.getP3().setReanimacionNeonatal(true);     
                                    else
                                    this.oAtencionObstetrica.getP3().setReanimacionNeonatal(false);*/
                                    this.oAtencionObstetrica.getP3().setReanimacionNeonatal((String) vRowTemp.get( 6 )); 
                                    this.oAtencionObstetrica.getP3().setEstanciaEnCunero(((Double) vRowTemp.get( 7 )).intValue());
                                    this.oAtencionObstetrica.getP3().setCondicion((String) vRowTemp.get( 8 ));                                    
                                }
                            }
                            bRet = true;                               
			}
		} 
		return bRet; 
	}                           
        
//*********************************************************************************************************         
    public List<Hospitalizacion> reporteHospitalizacion(String sFechaI, String sFechaF, int nAreaSer) throws Exception{

	Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
	String sQuery = "";
        String edad="";
	int i=0, nTam=0;
        String sMotEgre="";        
            
		sQuery = "SELECT * FROM buscaReporteHosp('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);"; 
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                    lHospitalizacion = new ArrayList<Hospitalizacion>();
			//vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oHospitalizacion = new Hospitalizacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oHospitalizacion.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(0)).intValue());
                            oHospitalizacion.getPaciente().setNombres((String)vRowTemp.get(1));
                            oHospitalizacion.getPaciente().setApPaterno((String)vRowTemp.get(2));
                            oHospitalizacion.getPaciente().setApMaterno((String)vRowTemp.get(3));
                            edad=(String)vRowTemp.get(4);
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0'){
                                    if(edad.charAt(1)=='0'){
                                        if(edad.charAt(2)=='1'){
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(2, 3)+" AÑO");
                                            oHospitalizacion.getPaciente().setEdad(1);
                                        }else{
                                            if(edad.charAt(2)=='2' || edad.charAt(2)=='3' || edad.charAt(2)=='4' || edad.charAt(2)=='5')
                                                oHospitalizacion.getPaciente().setEdad(2);
                                            else if(edad.charAt(2)=='6' || edad.charAt(2)=='7' || edad.charAt(2)=='8' || edad.charAt(2)=='9')
                                                oHospitalizacion.getPaciente().setEdad(3);                                            
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(2, 3)+" AÑOS");
                                        }
                                    }else if(edad.substring(1, 3).equals("10") || edad.substring(1, 3).equals("11")){
                                        oHospitalizacion.getPaciente().setEdad(3);
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS"); 
                                    }else if(edad.substring(1, 3).equals("12") || edad.substring(1, 3).equals("13") || edad.substring(1, 3).equals("14") || edad.substring(1, 3).equals("15") || edad.substring(1, 3).equals("16") || edad.substring(1, 3).equals("17") ){
                                        oHospitalizacion.getPaciente().setEdad(4);            
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS"); 
                                    }else if(edad.substring(1, 3).equals("18") || edad.substring(1, 3).equals("19") || edad.substring(1, 3).equals("20") || edad.substring(1, 3).equals("21") || edad.substring(1, 3).equals("22") || edad.substring(1, 3).equals("23") || edad.substring(1, 3).equals("24") || edad.substring(1, 3).equals("25") 
                                            || edad.substring(1, 3).equals("26") || edad.substring(1, 3).equals("27") || edad.substring(1, 3).equals("28") || edad.substring(1, 3).equals("29") || edad.substring(1, 3).equals("30")){
                                        oHospitalizacion.getPaciente().setEdad(5);            
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS"); 
                                    }else if(edad.substring(1, 3).equals("31") || edad.substring(1, 3).equals("32") || edad.substring(1, 3).equals("33") || edad.substring(1, 3).equals("34") || edad.substring(1, 3).equals("35") || edad.substring(1, 3).equals("36") || edad.substring(1, 3).equals("37") 
                                            || edad.substring(1, 3).equals("38") || edad.substring(1, 3).equals("39") || edad.substring(1, 3).equals("40")){
                                        oHospitalizacion.getPaciente().setEdad(6);            
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS"); 
                                    }else if(edad.substring(1, 3).equals("41") || edad.substring(1, 3).equals("42") || edad.substring(1, 3).equals("43") || edad.substring(1, 3).equals("44") || edad.substring(1, 3).equals("45") || edad.substring(1, 3).equals("46") || edad.substring(1, 3).equals("47") 
                                            || edad.substring(1, 3).equals("48") || edad.substring(1, 3).equals("49") || edad.substring(1, 3).equals("50")){
                                        oHospitalizacion.getPaciente().setEdad(7);            
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS"); 
                                    }else{
                                        oHospitalizacion.getPaciente().setEdad(8);    
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");   
                                    }
                                }else{
                                    oHospitalizacion.getPaciente().setEdad(8);
                                    oHospitalizacion.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                }
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0){
                                    if(edad.charAt(4)=='0'){
                                        if(edad.charAt(5)=='1'){
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(5, 6)+" MES");
                                        }else{
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(5, 6)+" MESES");
                                        }
                                    }else{    
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                    }
                                    oHospitalizacion.getPaciente().setEdad(0);
                                }else{
                                    if(edad.charAt(8)=='1'){
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(8, 9)+" DÍA");
                                    }else{
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                                oHospitalizacion.getPaciente().setEdad(9);
                            }
                            oHospitalizacion.getPaciente().setTalla(((Double) vRowTemp.get(5)).floatValue());
                            oHospitalizacion.getPaciente().setPeso(((Double) vRowTemp.get(6)).floatValue());
                            oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(7));
                            oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setClave((String)vRowTemp.get(8));
                            switch ((String)vRowTemp.get(9)) {
                                case "T3801":
                                    sMotEgre="CURACIÓN";
                                    break;
                                case "T3802":
                                    sMotEgre="MEJORÍA";
                                    break;
                                case "T3803":
                                    sMotEgre="VOLUNTARIO";
                                    break;
                                case "T3804":
                                    sMotEgre="PASE A OTRO HOSPITAL";
                                    break;
                                case "T3805":
                                    sMotEgre="DEFUNCIÓN";
                                    break;
                                case "T3806":
                                    sMotEgre="OTRO MOTIVO";
                                    break;
                            }
                            oHospitalizacion.getEpisodioMedico().setMotivoEgresoP(sMotEgre);
                            oHospitalizacion.getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(10));
                            oHospitalizacion.getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(11));
                            oHospitalizacion.getEpisodioMedico().getArea().setDescripcion((String)vRowTemp.get(12));
                            oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().getCauses().setClave((String)vRowTemp.get(13));
                            lHospitalizacion.add(oHospitalizacion);
			}

		} 
                
		return lHospitalizacion; 
	}           
        
//*********************************************************************************************************     
    public List<Hospitalizacion> consultaCODES(String sFechaI, String sFechaF, int nAreaSer, int tipo) throws Exception{

	Hospitalizacion arrRet[]=null, oHospitalizacion=null;
	ArrayList rst = null;
        ArrayList<Hospitalizacion> vObj=null;
	String sQuery = "";
        String edad="";
	int i=0;
        String sMotEgre="";        
        
            switch(tipo){
                case 1:
                    sQuery = "SELECT * FROM buscaCodesHospArea('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;
                case 2:
                    sQuery = "SELECT * FROM buscaCodesHosp('"+sFechaI+"','"+sFechaF+"');";
                    break;
            }
		 
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oHospitalizacion = new Hospitalizacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oHospitalizacion.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                            oHospitalizacion.setFechaIngresoHos((Date)vRowTemp.get(1));                            
                            oHospitalizacion.getPaciente().setApPaterno((String)vRowTemp.get(2));
                            oHospitalizacion.getPaciente().setApMaterno((String)vRowTemp.get(3));
                            oHospitalizacion.getPaciente().setNombres((String)vRowTemp.get(4));   
                            oHospitalizacion.getPaciente().setFechaNac((Date)vRowTemp.get(5));
                            edad=(String)vRowTemp.get(6);
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0'){
                                    if(edad.charAt(1)=='0'){
                                        if(edad.charAt(2)=='1'){
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(2, 3)+" AÑO");
                                            oHospitalizacion.getPaciente().setEdad(1);
                                        }else{
                                            if(edad.charAt(2)=='2' || edad.charAt(2)=='3' || edad.charAt(2)=='4' || edad.charAt(2)=='5')
                                                oHospitalizacion.getPaciente().setEdad(2);
                                            else if(edad.charAt(2)=='6' || edad.charAt(2)=='7' || edad.charAt(2)=='8' || edad.charAt(2)=='9' || edad.substring(2, 3).equals("10") || edad.substring(2, 3).equals("11"))
                                                oHospitalizacion.getPaciente().setEdad(3);
                                            else if(edad.substring(2, 3).equals("12") || edad.substring(2, 3).equals("13") || edad.substring(2, 3).equals("14") || edad.substring(2, 3).equals("15") || edad.substring(2, 3).equals("16") || edad.substring(2, 3).equals("17") || edad.substring(2, 3).equals("18"))
                                                oHospitalizacion.getPaciente().setEdad(4);
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(2, 3)+" AÑOS");
                                        }
                                    }else{
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");   
                                    }
                                }else{
                                    oHospitalizacion.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                }
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0){
                                    if(edad.charAt(4)=='0'){
                                        if(edad.charAt(5)=='1'){
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(5, 6)+" MES");
                                        }else{
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(5, 6)+" MESES");
                                        }
                                    }else{    
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                    }
                                }else{
                                    if(edad.charAt(8)=='1'){
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(8, 9)+" DÍA");
                                    }else{
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                                oHospitalizacion.getPaciente().setEdad(0);
                            }
                            oHospitalizacion.getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(7));
                            oHospitalizacion.getPaciente().setCurp((String)vRowTemp.get(8));
                            oHospitalizacion.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                            oHospitalizacion.setNumIngresoHos(((Double) vRowTemp.get(10)).longValue());
                            oHospitalizacion.getEpisodioMedico().setClaveEpisodio(((Double) vRowTemp.get(11)).longValue());
                            oHospitalizacion.getEpisodioMedico().setAltaHospitalaria((Date)vRowTemp.get(12));
                            switch ((String)vRowTemp.get(13)) {
                                case "T3801":
                                    sMotEgre="CURACIÓN";
                                    break;
                                case "T3802":
                                    sMotEgre="MEJORÍA";
                                    break;
                                case "T3803":
                                    sMotEgre="VOLUNTARIO";
                                    break;
                                case "T3804":
                                    sMotEgre="PASE A OTRO HOSPITAL";
                                    break;
                                case "T3805":
                                    sMotEgre="DEFUNCIÓN";
                                    break;
                                case "T3806":
                                    sMotEgre="OTRO MOTIVO";
                                    break;
                            }
                            oHospitalizacion.getEpisodioMedico().setMotivoEgresoP(sMotEgre);                            
                            vObj.add(oHospitalizacion);
			}
		} 
                
		return vObj; 
	}           
        
//*********************************************************************************************************        
    public List<Hospitalizacion> buscarCODESFaltaFolio() throws Exception{

	Hospitalizacion oHospitalizacion=null;
	ArrayList rst = null;
        ArrayList<Hospitalizacion> vObj=null;
	String sQuery = "";
        String edad="";
	int i=0;
        String fIngre="";
        String fAlta="";
            
		sQuery = "SELECT * FROM buscaCODESFaltaFolio();"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oHospitalizacion = new Hospitalizacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);                            
                                Date fIngreso=((Date) vRowTemp.get( 0 ));
                                //Date fAltaHos=((Date) vRowTemp.get( 1 ));
                                SimpleDateFormat fI=new SimpleDateFormat("dd/MM/yyyy");
                                fIngre=fI.format(fIngreso);
                                //feIngre=fI.parse(fIngre);
                                //this.oEpisodioMedico.setFIngreso(fIngre);
                                //this.oEpisodioMedico.setAltaHospitalaria(fAltaHos);                            
                            oHospitalizacion.setFechaIngresoHos((Date) vRowTemp.get( 0 ));
                            oHospitalizacion.getPaciente().setNombres((String)vRowTemp.get(1));
                            oHospitalizacion.getPaciente().setApPaterno((String)vRowTemp.get(2));
                            oHospitalizacion.getPaciente().setApMaterno((String)vRowTemp.get(3));
                            edad=(String)vRowTemp.get(4);
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0'){
                                    if(edad.charAt(1)=='0'){
                                        if(edad.charAt(2)=='1'){
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(2, 3)+" AÑO");
                                        }else{
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(2, 3)+" AÑOS");
                                        }
                                    }else{
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");   
                                    }
                                }else{
                                    oHospitalizacion.getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                }
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0){
                                    if(edad.charAt(4)=='0'){
                                        if(edad.charAt(5)=='1'){
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(5, 6)+" MES");
                                        }else{
                                            oHospitalizacion.getPaciente().setEdadNumero(edad.substring(5, 6)+" MESES");
                                        }
                                    }else{    
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                    }
                                }else{
                                    if(edad.charAt(8)=='1'){
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(8, 9)+" DÍA");
                                    }else{
                                        oHospitalizacion.getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                    }
                                }
                            }
                            oHospitalizacion.getExpediente().setNumero(((Double)vRowTemp.get(5)).intValue());
                            oHospitalizacion.getEpisodioMedico().setAreaServicioHRRB((String)vRowTemp.get(6));
                            oHospitalizacion.getEpisodioMedico().setAltaHospitalaria((Date) vRowTemp.get( 7 ));
                            oHospitalizacion.getEpisodioMedico().getMedicoTratante().setNombres((String)vRowTemp.get(8));
                            oHospitalizacion.getPaciente().setFolioPaciente(((Double)vRowTemp.get(9)).longValue());
                            oHospitalizacion.setNumIngresoHos(((Double)vRowTemp.get(10)).longValue());
                            oHospitalizacion.getEpisodioMedico().setClaveEpisodio(((Double)vRowTemp.get(11)).longValue());
                            vObj.add(oHospitalizacion);
			}
		} 
                
		return vObj; 
	}         
//****************************************************************************************************************************************************
    	public boolean buscarCamaServicio() throws Exception{
            boolean bRet = false;
            Hospitalizacion arrRet[]=null, oHospitalizacion=null;
            ArrayList rst = null;
            ArrayList<Hospitalizacion> vObj=null;
            String sQuery = "";
            String edad="";
            int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaCamaServicio("+oPaciente.getFolioPaciente()+"::bigint, "+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
                            //System.out.println("Si entra a este If");
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                oEpisodioMedico.getCama().setNumero((String) vRowTemp.get( 0 ));
                                oEpisodioMedico.getArea().setDescripcion((String) vRowTemp.get( 1 ));                                
				bRet = true;
			}
		} 
		return bRet; 
	}     
//****************************************************************************************************************************************************
    	public boolean buscarHojaAlta() throws Exception{
            boolean bRet = false;
            Hospitalizacion arrRet[]=null, oHospitalizacion=null;
            ArrayList rst = null;
            ArrayList<Hospitalizacion> vObj=null;
            String sQuery = "";
            String edad="";
            int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosHojaAlta("+getNumIngresoHos()+"::bigint, "+oPaciente.getFolioPaciente()+"::bigint, "+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setReingreso((String) vRowTemp.get( 0 ));
                                setPeso(((Double)vRowTemp.get(1)).floatValue());
                                setTalla(((Double)vRowTemp.get(2)).floatValue());
                                setResumenEvolucion((String) vRowTemp.get( 3 ));
                                setManejo((String) vRowTemp.get( 4 ));
                                setProblemasPend((String) vRowTemp.get( 5 ));
                                setPlan((String) vRowTemp.get( 6 ));
                                setRecomendaciones((String) vRowTemp.get( 7 ));
                                setPronosticoP((String) vRowTemp.get( 8 ));
                                if(((String) vRowTemp.get( 9 )).equals("S"))
                                    setConsultaExt("SI");
                                else
                                    setConsultaExt("No Requiere");
                                setDiagnosticosIngreso((String) vRowTemp.get( 10 ));
                                setDiagnosticosEgreso((String) vRowTemp.get( 11 ));
                                setProcedimientosOperaciones((String) vRowTemp.get( 12 ));
                                setFechaElaboracion((Date) vRowTemp.get( 13 ));
                                oEpisodioMedico.setDestinoSTR((String) vRowTemp.get( 14 ));
				bRet = true;
			}
		} 
		return bRet; 
	}     
//****************************************************************************************************************************************************     
    	public boolean buscarSignosVitalesHojaAlta() throws Exception{
            boolean bRet = false;
            Hospitalizacion arrRet[]=null, oHospitalizacion=null;
            ArrayList rst = null;
            ArrayList<Hospitalizacion> vObj=null;
            String sQuery = "";
            String edad="";
            int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaSignosVitalesHojaAlta("+oPaciente.getFolioPaciente()+"::bigint, "+oEpisodioMedico.getClaveEpisodio()+"::bigint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                oEpisodioMedico.getSignosVitales().setTA((String) vRowTemp.get( 0 ));
                                oEpisodioMedico.getSignosVitales().setFR((String) vRowTemp.get( 1 ));
                                oEpisodioMedico.getSignosVitales().setFC((String) vRowTemp.get( 2 ));
                                oEpisodioMedico.getSignosVitales().setTemp((String) vRowTemp.get( 3 ));
				bRet = true;
			}
		} 
		return bRet; 
	}
//*************************************************************************************************************************        
	public String buscaEstadoCODE() throws Exception{
	ArrayList rst = null;
	String nRet = "";
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaEstadoCODE("+oPaciente.getFolioPaciente()+"::bigint, "+getNumIngresoHos()+"::bigint, " 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint);";                                          
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((String)vRowTemp.get(0));
				}
			}
		} 
		return nRet; 
	}           
//****************************************************************************************************************************************************            
    	public boolean buscarMedioHojaAlta() throws Exception{
            boolean bRet = false;
            Hospitalizacion arrRet[]=null, oHospitalizacion=null;
            ArrayList rst = null;
            ArrayList<Hospitalizacion> vObj=null;
            String sQuery = "";
            String edad="";
            int i=0, nTam=0;

		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaMedicoHojaAlta('"+sUsuario+"');"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}                        
			if (rst != null && rst.size() == 1) {
                            //System.out.println("Si entra a este If");
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                oEpisodioMedico.getMedicoTratante().setNombres((String) vRowTemp.get( 0 ));
                                oEpisodioMedico.getMedicoTratante().setApPaterno((String) vRowTemp.get( 1 ));
                                oEpisodioMedico.getMedicoTratante().setApMaterno((String) vRowTemp.get( 2 ));
                                oEpisodioMedico.getMedicoTratante().setCedProf((String) vRowTemp.get( 3 ));

				bRet = true;
			}
		} 
		return bRet; 
	}   
        public boolean buscarDatosMedicoHojaAlta() throws Exception{
        boolean bRet = false;
        Hospitalizacion arrRet[]=null, oHospitalizacion=null;
        ArrayList rst = null;
        ArrayList<Hospitalizacion> vObj=null;
        String sQuery = "";
        String edad="";
        int i=0, nTam=0;
            if( this==null){   //completar llave
                   throw new Exception("Hospitalizacion.buscarDatosMedicoHojaAlta: error de programacion, faltan datos");
            }else{ 
                sQuery = "SELECT * FROM buscarDatosMedicoHojaAlta("+getPaciente().getFolioPaciente()+","+getNumIngresoHos()+","+getEpisodioMedico().getClaveEpisodio()+");"; 
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                }                        
                if (rst != null && rst.size() == 1) {
                    //System.out.println("Si entra a este If");
                     ArrayList vRowTemp = (ArrayList)rst.get(0);
                     oEpisodioMedico.getMedicoTratante().setNombres((String) vRowTemp.get( 0 ));
                     oEpisodioMedico.getMedicoTratante().setApPaterno((String) vRowTemp.get( 1 ));
                     oEpisodioMedico.getMedicoTratante().setApMaterno((String) vRowTemp.get( 2 ));
                     oEpisodioMedico.getMedicoTratante().setCedProf((String) vRowTemp.get( 3 ));

                     bRet = true;
                }
           } 
            return bRet; 
	}

//****************************************************************************************************************************************************           
    public BarChartModel initBarModel(String sFechaI, String sFechaF, int nAreaSer, int tipo) throws Exception{

        BarChartModel model = new BarChartModel();        
	ArrayList rst = null;

	String sQuery = "";
        String edad="";
	int i=0, c0=0, c1=0, c2=0, c3=0, c4=0, c5=0;
        int c6=0, c7=0, c8=0, c9=0, c10=0, c11=0;
        int c12=0, c13=0, c14=0, c15=0, c16=0, c17=0;
        int c18=0, c19=0, c20=0, c21=0, c22=0, c23=0, c24=0, c25=0;
        int c26=0, c27=0, c28=0, c29=0, c30=0, c31=0, c32=0, c33=0, c34=0, c35=0, c36=0, c37=0, c38=0, c39=0, c40=0;
        int c41=0, c42=0, c43=0, c44=0, c45=0, c46=0, c47=0, c48=0, c49=0, c50=0, c51=0, c52=0, c53=0, c54=0, c55=0;
        int c56=0, c57=0, c58=0, c59=0;
        Hospitalizacion oHospitalizacion=null;
        ArrayList<Hospitalizacion> vObj=null;
        
        if(sFechaI.equals("")||sFechaF.equals("")){
            sQuery = "SELECT * FROM buscaEdadesGraficas(null, null,"+nAreaSer+"::smallint);";
        }else{
            switch(tipo){
                case 1:
                    sQuery = "SELECT * FROM buscaEdadesGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);"; 
                    break;
                case 2:
                    sQuery = "SELECT * FROM buscaSexosGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;
                case 3:
                    sQuery = "SELECT * FROM buscaMotivoEgresoGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;
                case 4:
                    sQuery = "SELECT * FROM buscaEgresoEdadGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;                     
                case 5:
                    sQuery = "SELECT * FROM buscaEgresoSexoGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;     
                case 6:
                    sQuery = "SELECT * FROM buscadiagnosticosgraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;     
            }
            
        }
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
                System.out.println("rst: "+rst.size());
		if (rst != null) {
                    System.out.println("entre");
                    lHospitalizacion = new ArrayList<Hospitalizacion>();
                    tamGra=rst.size();
			for (i = 0; i < rst.size(); i++) {
                            oHospitalizacion = new Hospitalizacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            switch(tipo){
                                case 1:
                                    edad=(String)vRowTemp.get(0);
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0'){
                                            if(edad.charAt(1)=='0'){
                                                if(edad.charAt(2)=='1')
                                                    c1++;
                                                else if(edad.charAt(2)=='2' || edad.charAt(2)=='3' || edad.charAt(2)=='4' || edad.charAt(2)=='5')
                                                    c2++;
                                                else if(edad.charAt(2)=='6' || edad.charAt(2)=='7' || edad.charAt(2)=='8' || edad.charAt(2)=='9')
                                                    c3++;
                                            }else{
                                                if(edad.substring(1, 3).equals("10") || edad.substring(1, 3).equals("11"))
                                                    c3++;
                                                else if(edad.substring(1, 3).equals("12") || edad.substring(1, 3).equals("13") || edad.substring(1, 3).equals("14") || edad.substring(1, 3).equals("15") || edad.substring(1, 3).equals("16") || edad.substring(1, 3).equals("17"))
                                                    c4++;
                                                else if(edad.substring(1, 3).equals("18") || edad.substring(1, 3).equals("19") || edad.substring(1, 3).equals("20") || edad.substring(1, 3).equals("21") || edad.substring(1, 3).equals("22")
                                                        || edad.substring(1, 3).equals("23") || edad.substring(1, 3).equals("24") || edad.substring(1, 3).equals("25") || edad.substring(1, 3).equals("26")
                                                        || edad.substring(1, 3).equals("27") || edad.substring(1, 3).equals("28") || edad.substring(1, 3).equals("29") || edad.substring(1, 3).equals("30"))
                                                    c5++;
                                                else if(edad.substring(1, 3).equals("31") || edad.substring(1, 3).equals("32") || edad.substring(1, 3).equals("33") || edad.substring(1, 3).equals("34") 
                                                        || edad.substring(1, 3).equals("35") || edad.substring(1, 3).equals("36") || edad.substring(1, 3).equals("37") || edad.substring(1, 3).equals("38") 
                                                        || edad.substring(1, 3).equals("39") || edad.substring(1, 3).equals("40"))
                                                    c6++;
                                                else if(edad.substring(1, 3).equals("41") || edad.substring(1, 3).equals("42") || edad.substring(1, 3).equals("43") || edad.substring(1, 3).equals("44") 
                                                        || edad.substring(1, 3).equals("45") || edad.substring(1, 3).equals("46") || edad.substring(1, 3).equals("47") || edad.substring(1, 3).equals("48") 
                                                        || edad.substring(1, 3).equals("49") || edad.substring(1, 3).equals("50"))
                                                    c7++;         
                                                else if(edad.substring(1, 3).equals("51"))
                                                    c8++;
                                                else
                                                    c8++;                                                
                                            }                                    
                                        }
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            c0++;                 
                                        else
                                            c9++;
                                    }
                                    break;
                                case 2:
                                    if(((String)vRowTemp.get(0)).charAt(0)=='M')
                                        c0++;
                                    else
                                        c1++;
                                    break;
                                case 3:
                                    switch ((String)vRowTemp.get(0)) {
                                        case "T3801":
                                            c0++;
                                            break;
                                        case "T3802":
                                            c1++;
                                            break;
                                        case "T3803":
                                            c2++;
                                            break;
                                        case "T3804":
                                            c3++;
                                            break;
                                        case "T3805":
                                            c4++;
                                            break;
                                        case "T3806":
                                            c5++;
                                            break;
                                    }
                                    break;
                                case 4:
                                    edad=(String)vRowTemp.get(1);
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0'){
                                            if(edad.charAt(1)=='0'){
                                                if(edad.charAt(2)=='1'){
                                                    //c1++;
                                                    switch ((String)vRowTemp.get(0)) {
                                                        case "T3801":
                                                            c6++;
                                                            break;
                                                        case "T3802":
                                                            c7++;
                                                            break;
                                                        case "T3803":
                                                            c8++;
                                                            break;
                                                        case "T3804":
                                                            c9++;                                                       
                                                            break;
                                                        case "T3805":
                                                            c10++;
                                                            break;
                                                        case "T3806":
                                                            c11++;
                                                            break;
                                                    }
                                                }else if(edad.charAt(2)=='2' || edad.charAt(2)=='3' || edad.charAt(2)=='4' || edad.charAt(2)=='5'){
                                                    //c2++;
                                                    switch ((String)vRowTemp.get(0)) {
                                                        case "T3801":
                                                            c12++;
                                                            break;
                                                        case "T3802":
                                                            c13++;
                                                            break;
                                                        case "T3803":
                                                            c14++;
                                                            break;
                                                        case "T3804":
                                                            c15++;                                                       
                                                            break;
                                                        case "T3805":
                                                            c16++;
                                                            break;
                                                        case "T3806":
                                                            c17++;
                                                            break;
                                                    }
                                                }else if(edad.charAt(2)=='6' || edad.charAt(2)=='7' || edad.charAt(2)=='8' || edad.charAt(2)=='9'){
                                                    //c3++;
                                                    switch ((String)vRowTemp.get(0)) {
                                                        case "T3801":
                                                            c18++;
                                                            break;
                                                        case "T3802":
                                                            c19++;
                                                            break;
                                                        case "T3803":
                                                            c20++;
                                                            break;
                                                        case "T3804":
                                                            c21++;                                                       
                                                            break;
                                                        case "T3805":
                                                            c22++;
                                                            break;
                                                        case "T3806":
                                                            c23++;
                                                            break;
                                                    }
                                                }
                                            }else{
                                                switch (edad.substring(1, 3)) {
                                                    case "10":
                                                    case "11":
                                                //c3++;
                                                switch ((String)vRowTemp.get(0)) {
                                                    case "T3801":
                                                        c18++;
                                                        break;
                                                    case "T3802":
                                                        c19++;
                                                        break;
                                                    case "T3803":
                                                        c20++;
                                                        break;
                                                    case "T3804":
                                                        c21++;
                                                        break;
                                                    case "T3805":
                                                        c22++;
                                                        break;
                                                    case "T3806":
                                                        c23++;
                                                        break;
                                                }
                                                        break;
                                                    case "12":
                                                    case "13":
                                                    case "14":
                                                    case "15":
                                                    case "16":
                                                    case "17":
                                                //c4++;
                                                switch ((String)vRowTemp.get(0)) {
                                                    case "T3801":
                                                        c24++;
                                                        break;
                                                    case "T3802":
                                                        c25++;
                                                        break;
                                                    case "T3803":
                                                        c26++;
                                                        break;
                                                    case "T3804":
                                                        c27++;
                                                        break;
                                                    case "T3805":
                                                        c28++;
                                                        break;
                                                    case "T3806":
                                                        c29++;
                                                        break;
                                                }
                                                        break;
                                                    case "18":
                                                    case "19":
                                                    case "20":
                                                    case "21":
                                                    case "22":
                                                    case "23":
                                                    case "24":
                                                    case "25":
                                                    case "26":
                                                    case "27":
                                                    case "28":
                                                    case "29":
                                                    case "30":
                                                        //c5++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c30++;
                                                                break;
                                                            case "T3802":
                                                                c31++;
                                                                break;
                                                            case "T3803":
                                                                c32++;
                                                                break;
                                                            case "T3804":
                                                                c33++;
                                                                break;
                                                            case "T3805":
                                                                c34++;
                                                                break;
                                                            case "T3806":
                                                                c35++;
                                                                break;
                                                        }
                                                        break;
                                                    case "31":
                                                    case "32":
                                                    case "33":
                                                    case "34":
                                                    case "35":
                                                    case "36":
                                                    case "37":
                                                    case "38":
                                                    case "39":
                                                    case "40":
                                                        //c6++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c36++;
                                                                break;
                                                            case "T3802":
                                                                c37++;
                                                                break;
                                                            case "T3803":
                                                                c38++;
                                                                break;
                                                            case "T3804":
                                                                c39++;
                                                                break;
                                                            case "T3805":
                                                                c40++;
                                                                break;
                                                            case "T3806":
                                                                c41++;
                                                            break;
                                                    }   break;
                                                    case "41":
                                                    case "42":
                                                    case "43":
                                                    case "44":
                                                    case "45":
                                                    case "46":
                                                    case "47":
                                                    case "48":
                                                    case "49":
                                                    case "50":
                                                        //c7++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c42++;
                                                                break;
                                                            case "T3802":
                                                                c43++;
                                                                break;
                                                            case "T3803":
                                                                c44++;
                                                                break;
                                                            case "T3804":
                                                                c45++;
                                                                break;
                                                            case "T3805":
                                                                c46++;
                                                                break;
                                                            case "T3806":
                                                                c47++;
                                                                break;
                                                        }
                                                        break;
                                                    case "51":
                                                        //c8++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c48++;
                                                                break;
                                                            case "T3802":
                                                                c49++;
                                                                break;
                                                            case "T3803":
                                                                c50++;
                                                                break;
                                                            case "T3804":
                                                                c51++;
                                                                break;
                                                            case "T3805":
                                                                c52++;
                                                                break;
                                                            case "T3806":
                                                                c53++;
                                                                break;
                                                        }
                                                        break;
                                                    default:
                                                        //c8++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c48++;
                                                                break;
                                                            case "T3802":
                                                                c49++;
                                                                break;
                                                            case "T3803":
                                                                c50++;
                                                                break;
                                                            case "T3804":
                                                                c51++;
                                                                break;
                                                            case "T3805":
                                                                c52++;
                                                                break;
                                                            case "T3806":                                                         
                                                                c53++;
                                                                break;
                                                        }
                                                        break;
                                                }
                                            }                                    
                                        }
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0){
                                            //c0++;
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c0++;
                                                    break;
                                                case "T3802":
                                                    c1++;
                                                    break;
                                                case "T3803":
                                                    c2++;
                                                    break;
                                                case "T3804":
                                                    c3++;                                            
                                                    break;
                                                case "T3805":
                                                    c4++;
                                                    break;
                                                case "T3806":
                                                    c5++;
                                                    break;
                                            }
                                        }else{
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c54++;
                                                    break;
                                                case "T3802":
                                                    c55++;
                                                    break;
                                                case "T3803":
                                                    c56++;
                                                    break;
                                                case "T3804":
                                                    c57++;                                                 
                                                    break;
                                                case "T3805":
                                                    c58++;
                                                    break;
                                                case "T3806":
                                                    c59++;
                                                    break;
                                            }
                                        }
                                    }
                                    break;    
                                case 5:
                                    switch ((String)vRowTemp.get(1)) {
                                        case "M":
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c0++;
                                                    break;
                                                case "T3802":
                                                    c1++;
                                                    break;
                                                case "T3803":
                                                    c2++;
                                                    break;
                                                case "T3804":
                                                    c3++;
                                                    break;
                                                case "T3805":
                                                    c4++;
                                                    break;
                                                case "T3806":
                                                    c5++;
                                                    break;
                                            }
                                            break;
                                        case "F":
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c6++;
                                                    break;
                                                case "T3802":
                                                    c7++;
                                                    break;
                                                case "T3803":
                                                    c8++;
                                                    break;
                                                case "T3804":
                                                    c9++;
                                                    break;
                                                case "T3805":
                                                    c10++;
                                                    break;
                                                case "T3806":
                                                    c11++;
                                                    break;
                                            }
                                            break;
                                    }
                                    break;      
                                case 6:
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setClave((String)vRowTemp.get(0));
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(1));
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setTotal(((Double)vRowTemp.get(2)).intValue());
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setCantidadH(((Double)vRowTemp.get(3)).intValue());
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setCantidadM(((Double)vRowTemp.get(4)).intValue());
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().getCauses().setClave((String)vRowTemp.get(5));
                                    lHospitalizacion.add(oHospitalizacion);
                                    break;                                
                            }                            

			}
		} 
                switch(tipo){
                    case 1:
                        ChartSeries edad0 = new ChartSeries();
                        edad0.setLabel("Total Egresados Por Edad");
                        edad0.set("0 Días - 1 Mes", c9);
                        edad0.set("1 Mes - 1 Año", c0);
                        edad0.set("1 Año - 2 Años", c1);
                        edad0.set("2 Años - 6 Años", c2);
                        edad0.set("6 Años - 12 Años", c3);
                        edad0.set("12 Años - 18 Años", c4);
                        edad0.set("18 Años - 30 Años", c5);
                        edad0.set("31 Años - 40 Años", c6);
                        edad0.set("41 Años - 50 Años", c7);
                        edad0.set("51 Años +", c8);                        
                        model.addSeries(edad0);
                        break;
                        
                    case 2:
                        ChartSeries sexo = new ChartSeries();
                        sexo.setLabel("Total De Egresos Por Sexo");
                        sexo.set("Hombres", c0);
                        sexo.set("Mujeres", c1);                                                
                        model.addSeries(sexo);
                        break;
                    case 3:
                        ChartSeries egreso = new ChartSeries();
                        egreso.setLabel("Total De Egresos Según El Motivo");
                        egreso.set("Curación", c0);
                        egreso.set("Mejoría", c1);                                                
                        egreso.set("Voluntario", c2);
                        egreso.set("Pase A Otro Hospital", c3); 
                        egreso.set("Defunción", c4);
                        egreso.set("Otro Motivo", c5); 
                        model.addSeries(egreso);
                        break;                   
                        
                    case 4:
                        ChartSeries egresoC = new ChartSeries();
                        egresoC.setLabel("Curación");
                        egresoC.set("0 Días - 1 Mes", c54);
                        egresoC.set("1 Mes - 1 Año", c0);
                        egresoC.set("1 Año - 2 Años", c6);                                              
                        egresoC.set("2 Años - 6 Años", c12);
                        egresoC.set("6 Años - 12 Años", c18); 
                        egresoC.set("12 Años - 18 Años", c24);
                        
                        egresoC.set("18 Años - 30 Años", c30);
                        egresoC.set("31 Años - 40 Años", c36);
                        egresoC.set("41 Años - 50 Años", c42);
                        egresoC.set("51 Años +", c48);                         
                        model.addSeries(egresoC);
                        
                        ChartSeries egresoM = new ChartSeries();
                        egresoM.setLabel("Mejoría");
                        egresoM.set("0 Días - 1 Mes", c55);
                        egresoM.set("1 Mes - 1 Año", c1);
                        egresoM.set("1 Año - 2 Años", c7);                                              
                        egresoM.set("2 Años - 6 Años", c13);
                        egresoM.set("6 Años - 12 Años", c19); 
                        egresoM.set("12 Años - 18 Años", c25);
                        
                        egresoM.set("18 Años - 30 Años", c31);
                        egresoM.set("31 Años - 40 Años", c37);
                        egresoM.set("41 Años - 50 Años", c43);
                        egresoM.set("51 Años +", c49); 
                        model.addSeries(egresoM);
                        
                        ChartSeries egresoV = new ChartSeries();
                        egresoV.setLabel("Voluntario");
                        egresoV.set("0 Días - 1 Mes", c56);
                        egresoV.set("1 Mes - 1 Año", c2);
                        egresoV.set("1 Año - 2 Años", c8);                                              
                        egresoV.set("2 Años - 6 Años", c14);
                        egresoV.set("6 Años - 12 Años", c20); 
                        egresoV.set("12 Años - 18 Años", c26);
                        
                        egresoV.set("18 Años - 30 Años", c32);
                        egresoV.set("31 Años - 40 Años", c38);
                        egresoV.set("41 Años - 50 Años", c44);
                        egresoV.set("51 Años +", c50);                         
                        model.addSeries(egresoV);
                        
                        ChartSeries egresoP = new ChartSeries();
                        egresoP.setLabel("Pase A Otro Hospital");
                        egresoP.set("0 Días - 1 Mes", c57);
                        egresoP.set("1 Mes - 1 Año", c3);
                        egresoP.set("1 Año - 2 Años", c9);                                              
                        egresoP.set("2 Años - 6 Años", c15);
                        egresoP.set("6 Años - 12 Años", c21); 
                        egresoP.set("12 Años - 18 Años", c27);
                        
                        egresoP.set("18 Años - 30 Años", c33);
                        egresoP.set("31 Años - 40 Años", c39);
                        egresoP.set("41 Años - 50 Años", c45);
                        egresoP.set("51 Años +", c51);                         
                        model.addSeries(egresoP);
                        
                        ChartSeries egresoD = new ChartSeries();
                        egresoD.setLabel("Defunción");
                        egresoD.set("0 Días - 1 Mes", c58);
                        egresoD.set("1 Mes - 1 Año", c4);
                        egresoD.set("1 Año - 2 Años", c10);                                              
                        egresoD.set("2 Años - 6 Años", c16);
                        egresoD.set("6 Años - 12 Años", c22); 
                        egresoD.set("12 Años - 18 Años", c28);
                        
                        egresoD.set("18 Años - 30 Años", c34);
                        egresoD.set("31 Años - 40 Años", c40);
                        egresoD.set("41 Años - 50 Años", c46);
                        egresoD.set("51 Años +", c52);                         
                        model.addSeries(egresoD);
                        
                        ChartSeries egresoO = new ChartSeries();
                        egresoO.setLabel("Otro Motivo");
                        egresoO.set("0 Días - 1 Mes", c59);
                        egresoO.set("1 Mes - 1 Año", c5);
                        egresoO.set("1 Año - 2 Años", c11);                                              
                        egresoO.set("2 Años - 6 Años", c17);
                        egresoO.set("6 Años - 12 Años", c23); 
                        egresoO.set("12 Años - 18 Años", c29);
                        
                        egresoO.set("18 Años - 30 Años", c35);
                        egresoO.set("31 Años - 40 Años", c41);
                        egresoO.set("41 Años - 50 Años", c47);
                        egresoO.set("51 Años +", c53);                         
                        model.addSeries(egresoO);
                        break;             

                    case 5:
                        ChartSeries sexoM = new ChartSeries();
                        sexoM.setLabel("Hombres");
                        sexoM.set("Curación", c0);
                        sexoM.set("Mejoría", c1);
                        sexoM.set("Voluntario", c2);                                              
                        sexoM.set("Pase A Otro Hospital", c3);
                        sexoM.set("Defunción", c4); 
                        sexoM.set("Otro Motivo", c5);
                        model.addSeries(sexoM);
                        
                        ChartSeries sexoF = new ChartSeries();
                        sexoF.setLabel("Mujeres");
                        sexoF.set("Curación", c6);
                        sexoF.set("Mejoría", c7);
                        sexoF.set("Voluntario", c8);                                              
                        sexoF.set("Pase A Otro Hospital", c9);
                        sexoF.set("Defunción", c10); 
                        sexoF.set("Otro Motivo", c11);
                        model.addSeries(sexoF);   
                        break;
                    case 6:
                        c0=lHospitalizacion.size();
                        System.out.println("vobj: "+c0);
                        ChartSeries sexoHo = new ChartSeries();
                        sexoHo.setLabel("Hombres");                        
                        for(i=0; i<c0; i++){
                            sexoHo.set(lHospitalizacion.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getClave(), lHospitalizacion.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getCantidadH());                            
                        }
                        model.addSeries(sexoHo);
                        
                        ChartSeries sexoMu = new ChartSeries();
                        sexoMu.setLabel("Mujeres");
                        for(i=0; i<c0; i++){
                            sexoMu.set(lHospitalizacion.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getClave(), lHospitalizacion.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getCantidadM());
                        }                        
                        model.addSeries(sexoMu);   
                        break;                        
                }
                
                
		return model;
    }               
    
    public LineChartModel initLineModel(String sFechaI, String sFechaF, int nAreaSer, int tipo) throws Exception{   
        LineChartModel model = new LineChartModel();
	ArrayList rst = null;

	String sQuery = "";
        String edad="";
	int i=0, c0=0, c1=0, c2=0, c3=0, c4=0, c5=0;     
        int c6=0, c7=0, c8=0, c9=0, c10=0, c11=0;
        int c12=0, c13=0, c14=0, c15=0, c16=0, c17=0;
        int c18=0, c19=0, c20=0, c21=0, c22=0, c23=0, c24=0, c25=0;
        int c26=0, c27=0, c28=0, c29=0, c30=0, c31=0, c32=0, c33=0, c34=0, c35=0, c36=0, c37=0, c38=0, c39=0, c40=0;
        int c41=0, c42=0, c43=0, c44=0, c45=0, c46=0, c47=0, c48=0, c49=0, c50=0, c51=0, c52=0, c53=0, c54=0, c55=0;
        int c56=0, c57=0, c58=0, c59=0;
                
        Hospitalizacion oHospitalizacion=null;
        ArrayList<Hospitalizacion> vObj=null;                
        
        if(sFechaI.equals("")||sFechaF.equals("")){
            sQuery = "SELECT * FROM buscaEdadesGraficas(null, null,"+nAreaSer+"::smallint);";
        }else{
            switch(tipo){
                case 1:
                    sQuery = "SELECT * FROM buscaEdadesGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);"; 
                    break;
                case 2:
                    sQuery = "SELECT * FROM buscaSexosGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;
                case 3:
                    sQuery = "SELECT * FROM buscaMotivoEgresoGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;           
                case 4:
                    sQuery = "SELECT * FROM buscaEgresoEdadGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;        
                case 5:
                    sQuery = "SELECT * FROM buscaEgresoSexoGraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;           
                case 6:
                    sQuery = "SELECT * FROM buscadiagnosticosgraficas('"+sFechaI+"','"+sFechaF+"',"+nAreaSer+"::smallint);";
                    break;                      
            }
            
        }
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                    vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oHospitalizacion = new Hospitalizacion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            switch(tipo){
                                case 1:
                                    edad=(String)vRowTemp.get(0);
                                    System.out.println("edad: "+edad);
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0'){
                                            if(edad.charAt(1)=='0'){
                                                if(edad.charAt(2)=='1')
                                                    c1++;
                                                else if(edad.charAt(2)=='2' || edad.charAt(2)=='3' || edad.charAt(2)=='4' || edad.charAt(2)=='5')
                                                    c2++;
                                                else if(edad.charAt(2)=='6' || edad.charAt(2)=='7' || edad.charAt(2)=='8' || edad.charAt(2)=='9')
                                                    c3++;
                                            }else{
                                                if(edad.substring(1, 3).equals("10") || edad.substring(1, 3).equals("11"))
                                                    c3++;
                                                else if(edad.substring(1, 3).equals("12") || edad.substring(1, 3).equals("13") || edad.substring(1, 3).equals("14") || edad.substring(1, 3).equals("15") || edad.substring(1, 3).equals("16") || edad.substring(1, 3).equals("17"))
                                                    c4++;
                                                else if(edad.substring(1, 3).equals("18") || edad.substring(1, 3).equals("19") || edad.substring(1, 3).equals("20") || edad.substring(1, 3).equals("21") || edad.substring(1, 3).equals("22")
                                                        || edad.substring(1, 3).equals("23") || edad.substring(1, 3).equals("24") || edad.substring(1, 3).equals("25") || edad.substring(1, 3).equals("26")
                                                        || edad.substring(1, 3).equals("27") || edad.substring(1, 3).equals("28") || edad.substring(1, 3).equals("29") || edad.substring(1, 3).equals("30"))
                                                    c5++;
                                                else if(edad.substring(1, 3).equals("31") || edad.substring(1, 3).equals("32") || edad.substring(1, 3).equals("33") || edad.substring(1, 3).equals("34") 
                                                        || edad.substring(1, 3).equals("35") || edad.substring(1, 3).equals("36") || edad.substring(1, 3).equals("37") || edad.substring(1, 3).equals("38") 
                                                        || edad.substring(1, 3).equals("39") || edad.substring(1, 3).equals("40"))
                                                    c6++;
                                                else if(edad.substring(1, 3).equals("41") || edad.substring(1, 3).equals("42") || edad.substring(1, 3).equals("43") || edad.substring(1, 3).equals("44") 
                                                        || edad.substring(1, 3).equals("45") || edad.substring(1, 3).equals("46") || edad.substring(1, 3).equals("47") || edad.substring(1, 3).equals("48") 
                                                        || edad.substring(1, 3).equals("49") || edad.substring(1, 3).equals("50"))
                                                    c7++;         
                                                else if(edad.substring(1, 3).equals("51"))
                                                    c8++;
                                                else
                                                    c8++;
                                            }                                    
                                        }
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0)
                                            c0++;       
                                        else
                                            c9++;
                                    }             
                                    break;
                                case 2:
                                    if(((String)vRowTemp.get(0)).charAt(0)=='M')
                                        c0++;
                                    else
                                        c1++;
                                    break;
                                case 3:
                                    switch ((String)vRowTemp.get(0)) {
                                        case "T3801":
                                            c0++;
                                            break;
                                        case "T3802":
                                            c1++;
                                            break;
                                        case "T3803":
                                            c2++;
                                            break;
                                        case "T3804":
                                            c3++;
                                            break;
                                        case "T3805":
                                            c4++;
                                            break;
                                        case "T3806":
                                            c5++;
                                            break;
                                    }
                                    break;            
                                case 4:
                                    edad=(String)vRowTemp.get(1);
                                    if(edad.substring(0, 3).compareTo("000")!=0){
                                        if (edad.charAt(0)=='0'){
                                            if(edad.charAt(1)=='0'){
                                                if(edad.charAt(2)=='1'){
                                                    //c1++;
                                                    switch ((String)vRowTemp.get(0)) {
                                                        case "T3801":
                                                            c6++;
                                                            break;
                                                        case "T3802":
                                                            c7++;
                                                            break;
                                                        case "T3803":
                                                            c8++;
                                                            break;
                                                        case "T3804":
                                                            c9++;                                                       
                                                            break;
                                                        case "T3805":
                                                            c10++;
                                                            break;
                                                        case "T3806":
                                                            c11++;
                                                            break;
                                                    }
                                                }else if(edad.charAt(2)=='2' || edad.charAt(2)=='3' || edad.charAt(2)=='4' || edad.charAt(2)=='5'){
                                                    //c2++;
                                                    switch ((String)vRowTemp.get(0)) {
                                                        case "T3801":
                                                            c12++;
                                                            break;
                                                        case "T3802":
                                                            c13++;
                                                            break;
                                                        case "T3803":
                                                            c14++;
                                                            break;
                                                        case "T3804":
                                                            c15++;                                                       
                                                            break;
                                                        case "T3805":
                                                            c16++;
                                                            break;
                                                        case "T3806":
                                                            c17++;
                                                            break;
                                                    }
                                                }else if(edad.charAt(2)=='6' || edad.charAt(2)=='7' || edad.charAt(2)=='8' || edad.charAt(2)=='9'){
                                                    //c3++;
                                                    switch ((String)vRowTemp.get(0)) {
                                                        case "T3801":
                                                            c18++;
                                                            break;
                                                        case "T3802":
                                                            c19++;
                                                            break;
                                                        case "T3803":
                                                            c20++;
                                                            break;
                                                        case "T3804":
                                                            c21++;                                                       
                                                            break;
                                                        case "T3805":
                                                            c22++;
                                                            break;
                                                        case "T3806":
                                                            c23++;
                                                            break;
                                                    }
                                                }
                                            }else{
                                                switch (edad.substring(1, 3)) {
                                                    case "10":
                                                    case "11":
                                                //c3++;
                                                switch ((String)vRowTemp.get(0)) {
                                                    case "T3801":
                                                        c18++;
                                                        break;
                                                    case "T3802":
                                                        c19++;
                                                        break;
                                                    case "T3803":
                                                        c20++;
                                                        break;
                                                    case "T3804":
                                                        c21++;
                                                        break;
                                                    case "T3805":
                                                        c22++;
                                                        break;
                                                    case "T3806":
                                                        c23++;
                                                        break;
                                                }
                                                        break;
                                                    case "12":
                                                    case "13":
                                                    case "14":
                                                    case "15":
                                                    case "16":
                                                    case "17":
                                                        //c4++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c24++;
                                                                break;
                                                            case "T3802":
                                                                c25++;
                                                                break;
                                                            case "T3803":
                                                                c26++;
                                                                break;
                                                            case "T3804":
                                                                c27++;
                                                                break;
                                                            case "T3805":
                                                                c28++;
                                                                break;
                                                            case "T3806":
                                                                c29++;
                                                                break;
                                                        }   break;
                                                    case "18":
                                                    case "19":
                                                    case "20":
                                                    case "21":
                                                    case "22":
                                                    case "23":
                                                    case "24":
                                                    case "25":
                                                    case "26":
                                                    case "27":
                                                    case "28":
                                                    case "29":
                                                    case "30":
                                                //c5++;
                                                switch ((String)vRowTemp.get(0)) {
                                                    case "T3801":
                                                        c30++;
                                                        break;
                                                    case "T3802":
                                                        c31++;
                                                        break;
                                                    case "T3803":
                                                        c32++;
                                                        break;
                                                    case "T3804":
                                                        c33++;
                                                        break;
                                                    case "T3805":
                                                        c34++;
                                                        break;
                                                    case "T3806":
                                                        c35++;
                                                        break;
                                                }
                                                        break;
                                                    case "31":
                                                    case "32":
                                                    case "33":
                                                    case "34":
                                                    case "35":
                                                    case "36":
                                                    case "37":
                                                    case "38":
                                                    case "39":
                                                    case "40":
                                                //c6++;
                                                switch ((String)vRowTemp.get(0)) {
                                                    case "T3801":
                                                        c36++;
                                                        break;
                                                    case "T3802":
                                                        c37++;
                                                        break;
                                                    case "T3803":
                                                        c38++;
                                                        break;
                                                    case "T3804":
                                                        c39++;
                                                        break;
                                                    case "T3805":
                                                        c40++;
                                                        break;
                                                    case "T3806":
                                                        c41++;
                                                        break;
                                                }
                                                        break;
                                                    case "41":
                                                    case "42":
                                                    case "43":
                                                    case "44":
                                                    case "45":
                                                    case "46":
                                                    case "47":
                                                    case "48":
                                                    case "49":
                                                    case "50":
                                                        //c7++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c42++;
                                                                break;
                                                            case "T3802":
                                                                c43++;
                                                                break;
                                                            case "T3803":
                                                                c44++;
                                                                break;
                                                            case "T3804":
                                                                c45++;
                                                                break;
                                                            case "T3805":
                                                                c46++;
                                                                break;
                                                            case "T3806":
                                                                c47++;
                                                                break;
                                                        }   break;
                                                    case "51":
                                                //c8++;
                                                switch ((String)vRowTemp.get(0)) {
                                                    case "T3801":
                                                        c48++;
                                                        break;
                                                    case "T3802":
                                                        c49++;
                                                        break;
                                                    case "T3803":
                                                        c50++;
                                                        break;
                                                    case "T3804":
                                                        c51++;
                                                        break;
                                                    case "T3805":
                                                        c52++;
                                                        break;
                                                    case "T3806":
                                                        c53++;
                                                        break;
                                                }
                                                        break;
                                                    default:
                                                        //c8++;
                                                        switch ((String)vRowTemp.get(0)) {
                                                            case "T3801":
                                                                c48++;
                                                                break;
                                                            case "T3802":
                                                                c49++;
                                                                break;
                                                            case "T3803":
                                                                c50++;
                                                                break;
                                                            case "T3804":
                                                                c51++;
                                                                break;
                                                            case "T3805":
                                                                c52++;
                                                                break;
                                                        case "T3806":
                                                            c53++;
                                                            break;
                                                    }   break;
                                                }
                                            }                                    
                                        }
                                    }else{
                                        if (edad.substring(4, 6).compareTo("00")!=0){
                                            //c0++;
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c0++;
                                                    break;
                                                case "T3802":
                                                    c1++;
                                                    break;
                                                case "T3803":
                                                    c2++;
                                                    break;
                                                case "T3804":
                                                    c3++;                                            
                                                    break;
                                                case "T3805":
                                                    c4++;
                                                    break;
                                                case "T3806":
                                                    c5++;
                                                    break;
                                            }
                                        }else{
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c54++;
                                                    break;
                                                case "T3802":
                                                    c55++;
                                                    break;
                                                case "T3803":
                                                    c56++;
                                                    break;
                                                case "T3804":
                                                    c57++;                                                 
                                                    break;
                                                case "T3805":
                                                    c58++;
                                                    break;
                                                case "T3806":
                                                    c59++;
                                                    break;
                                            }
                                        }
                                    }
                                    break;    
                                case 5:
                                    switch ((String)vRowTemp.get(1)) {
                                        case "M":
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c0++;
                                                    break;
                                                case "T3802":
                                                    c1++;
                                                    break;
                                                case "T3803":
                                                    c2++;
                                                    break;
                                                case "T3804":
                                                    c3++;
                                                    break;
                                                case "T3805":
                                                    c4++;
                                                    break;
                                                case "T3806":
                                                    c5++;
                                                    break;
                                            }
                                            break;
                                        case "F":
                                            switch ((String)vRowTemp.get(0)) {
                                                case "T3801":
                                                    c6++;
                                                    break;
                                                case "T3802":
                                                    c7++;
                                                    break;
                                                case "T3803":
                                                    c8++;
                                                    break;
                                                case "T3804":
                                                    c9++;
                                                    break;
                                                case "T3805":
                                                    c10++;
                                                    break;
                                                case "T3806":
                                                    c11++;
                                                    break;
                                            }
                                            break;
                                    }
                                    break;            
                                case 6:
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setClave((String)vRowTemp.get(0));
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(1));                                    
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setCantidadH(((Double)vRowTemp.get(3)).intValue());
                                    oHospitalizacion.getEpisodioMedico().getAfePrincipal().getCIE10().setCantidadM(((Double)vRowTemp.get(4)).intValue());
                                    vObj.add(oHospitalizacion);
                                    break;                                     
                            }                            

			}
		} 
                switch(tipo){
                    case 1:
                        LineChartSeries edad0 = new LineChartSeries();
                        edad0.setLabel("Total Egresados Por Edad");
                        edad0.set("0 Días - 1 Mes", c9);
                        edad0.set("1 Mes - 1 Año", c0);
                        edad0.set("1 Año - 2 Años", c1);
                        edad0.set("2 Años - 6 Años", c2);
                        edad0.set("6 Años - 12 Años", c3);
                        edad0.set("12 Años - 18 Años", c4);
                        edad0.set("18 Años - 30 Años", c5);
                        edad0.set("31 Años - 40 Años", c6);
                        edad0.set("41 Años - 50 Años", c7);
                        edad0.set("51 Años +", c8);                         
                        model.addSeries(edad0);
                        
                        break;
                        
                    case 2:
                        LineChartSeries sexo = new LineChartSeries();
                        sexo.setLabel("Total Egresados Por Edad");
                        sexo.set("Hombres", c0);
                        sexo.set("Mujeres", c1);
                        model.addSeries(sexo);
                        break;
                        
                    case 3:
                        LineChartSeries egreso = new LineChartSeries();
                        egreso.setLabel("Total De Egresos Según El Motivo");
                        egreso.set("Curación", c0);
                        egreso.set("Mejoría", c1);                                                
                        egreso.set("Voluntario", c2);
                        egreso.set("Pase A Otro Hospital", c3); 
                        egreso.set("Defunción", c4);
                        egreso.set("Otro Motivo", c5); 
                        model.addSeries(egreso);
                        break;                           
                        
                    case 4:
                        ChartSeries egresoC = new ChartSeries();
                        egresoC.setLabel("Curación");
                        egresoC.set("0 Días - 1 Mes", c54);
                        egresoC.set("1 Mes - 1 Año", c0);
                        egresoC.set("1 Año - 2 Años", c6);                                              
                        egresoC.set("2 Años - 6 Años", c12);
                        egresoC.set("6 Años - 12 Años", c18); 
                        egresoC.set("12 Años - 18 Años", c24);
                        
                        egresoC.set("18 Años - 30 Años", c30);
                        egresoC.set("31 Años - 40 Años", c36);
                        egresoC.set("41 Años - 50 Años", c42);
                        egresoC.set("51 Años +", c48);                         
                        model.addSeries(egresoC);
                        
                        ChartSeries egresoM = new ChartSeries();
                        egresoM.setLabel("Mejoría");
                        egresoM.set("0 Días - 1 Mes", c55);
                        egresoM.set("1 Mes - 1 Año", c1);
                        egresoM.set("1 Año - 2 Años", c7);                                              
                        egresoM.set("2 Años - 6 Años", c13);
                        egresoM.set("6 Años - 12 Años", c19); 
                        egresoM.set("12 Años - 18 Años", c25);
                        
                        egresoM.set("18 Años - 30 Años", c31);
                        egresoM.set("31 Años - 40 Años", c37);
                        egresoM.set("41 Años - 50 Años", c43);
                        egresoM.set("51 Años +", c49); 
                        model.addSeries(egresoM);
                        
                        ChartSeries egresoV = new ChartSeries();
                        egresoV.setLabel("Voluntario");
                        egresoV.set("0 Días - 1 Mes", c56);
                        egresoV.set("1 Mes - 1 Año", c2);
                        egresoV.set("1 Año - 2 Años", c8);                                              
                        egresoV.set("2 Años - 6 Años", c14);
                        egresoV.set("6 Años - 12 Años", c20); 
                        egresoV.set("12 Años - 18 Años", c26);
                        
                        egresoV.set("18 Años - 30 Años", c32);
                        egresoV.set("31 Años - 40 Años", c38);
                        egresoV.set("41 Años - 50 Años", c44);
                        egresoV.set("51 Años +", c50);                         
                        model.addSeries(egresoV);
                        
                        ChartSeries egresoP = new ChartSeries();
                        egresoP.setLabel("Pase A Otro Hospital");
                        egresoP.set("0 Días - 1 Mes", c57);
                        egresoP.set("1 Mes - 1 Año", c3);
                        egresoP.set("1 Año - 2 Años", c9);                                              
                        egresoP.set("2 Años - 6 Años", c15);
                        egresoP.set("6 Años - 12 Años", c21); 
                        egresoP.set("12 Años - 18 Años", c27);
                        
                        egresoP.set("18 Años - 30 Años", c33);
                        egresoP.set("31 Años - 40 Años", c39);
                        egresoP.set("41 Años - 50 Años", c45);
                        egresoP.set("51 Años +", c51);                         
                        model.addSeries(egresoP);
                        
                        ChartSeries egresoD = new ChartSeries();
                        egresoD.setLabel("Defunción");
                        egresoD.set("0 Días - 1 Mes", c58);
                        egresoD.set("1 Mes - 1 Año", c4);
                        egresoD.set("1 Año - 2 Años", c10);                                              
                        egresoD.set("2 Años - 6 Años", c16);
                        egresoD.set("6 Años - 12 Años", c22); 
                        egresoD.set("12 Años - 18 Años", c28);
                        
                        egresoD.set("18 Años - 30 Años", c34);
                        egresoD.set("31 Años - 40 Años", c40);
                        egresoD.set("41 Años - 50 Años", c46);
                        egresoD.set("51 Años +", c52);                         
                        model.addSeries(egresoD);
                        
                        ChartSeries egresoO = new ChartSeries();
                        egresoO.setLabel("Otro Motivo");
                        egresoO.set("0 Días - 1 Mes", c59);
                        egresoO.set("1 Mes - 1 Año", c5);
                        egresoO.set("1 Año - 2 Años", c11);                                              
                        egresoO.set("2 Años - 6 Años", c17);
                        egresoO.set("6 Años - 12 Años", c23); 
                        egresoO.set("12 Años - 18 Años", c29);
                        
                        egresoO.set("18 Años - 30 Años", c35);
                        egresoO.set("31 Años - 40 Años", c41);
                        egresoO.set("41 Años - 50 Años", c47);
                        egresoO.set("51 Años +", c53);                         
                        model.addSeries(egresoO);
                        break;                
                        
                    case 5:
                        LineChartSeries sexoM = new LineChartSeries();
                        sexoM.setLabel("Hombres");
                        sexoM.set("Curación", c0);
                        sexoM.set("Mejoría", c1);
                        sexoM.set("Voluntario", c2);                                              
                        sexoM.set("Pase A Otro Hospital", c3);
                        sexoM.set("Defunción", c4); 
                        sexoM.set("Otro Motivo", c5);
                        model.addSeries(sexoM);
                        
                        LineChartSeries sexoF = new LineChartSeries();
                        sexoF.setLabel("Mujeres");
                        sexoF.set("Curación", c6);
                        sexoF.set("Mejoría", c7);
                        sexoF.set("Voluntario", c8);                                              
                        sexoF.set("Pase A Otro Hospital", c9);
                        sexoF.set("Defunción", c10); 
                        sexoF.set("Otro Motivo", c11);
                        model.addSeries(sexoF);   
                        break;      
                        
                    case 6:
                        c0=vObj.size();
                        System.out.println("vobj: "+c0);
                        ChartSeries sexoHo = new ChartSeries();
                        sexoHo.setLabel("Hombres");                        
                        for(i=0; i<c0; i++){
                            sexoHo.set(vObj.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getClave(), vObj.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getCantidadH());
                        }
                        model.addSeries(sexoHo);
                        
                        ChartSeries sexoMu = new ChartSeries();
                        sexoMu.setLabel("Mujeres");
                        for(i=0; i<c0; i++){
                            sexoMu.set(vObj.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getClave(), vObj.get(i).getEpisodioMedico().getAfePrincipal().getCIE10().getCantidadM());
                        }                        
                        model.addSeries(sexoMu);   
                        break;                          
                } 
                
                return model;
    }      
//*********************************************************************************************************        		
    public String fechaActual(){
        Calendar fecha = new GregorianCalendar();
        int año=fecha.get(Calendar.YEAR);
        int mes=fecha.get(Calendar.MONTH);
        int dia=fecha.get(Calendar.DAY_OF_MONTH);
        
        String a=año+"";
        String hoy=dia+"/"+(mes+1)+"/"+a.substring(2, 4);

        return hoy;
    }
//*************************************************************************************************************************
	public int InsertarHojaAlta() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";       
        String fAltaH;
        String sLlave=oPaciente.getFolioPaciente()+", "+oEpisodioMedico.getClaveEpisodio();   
        
        String translado="";
        String motivoEgreso="";
        String destino="";
        String pronostico="";
        String resumen="";
        String manejo="";
        String problemas="";
        String plan="";
        String recomendaciones="";
        String diagIngreso="";
        String diagEgreso="";
        String operaciones="";
        
        if(oEpisodioMedico.getMotivoEgresoP()==null || oEpisodioMedico.getMotivoEgresoP().equals("     ") || oEpisodioMedico.getMotivoEgresoP().equals(""))
            motivoEgreso="null";
        else
            motivoEgreso="'"+oEpisodioMedico.getMotivoEgresoP()+"'";
        
        if(oEpisodioMedico.getMotivoEgresoP().equals("T3804") || oEpisodioMedico.getMotivoEgresoP().equals("T3803"))
            translado="'"+oEpisodioMedico.getRazonAltaVolunTrasl()+"'";
        else
            translado="null";
        
        if(oEpisodioMedico.getDestinoSTR()==null || oEpisodioMedico.getDestinoSTR().equals("     ") || oEpisodioMedico.getDestinoSTR().equals(""))
            destino="null";
        else
            destino="'"+oEpisodioMedico.getDestinoSTR()+"'";
        
        if(getPronosticoP()==null || getPronosticoP().equals("     ") || getPronosticoP().equals("") )
            pronostico="null";
        else
            pronostico="'"+getPronosticoP()+"'";
        
        if(getResumenEvolucion()==null || getResumenEvolucion().equals(""))
            resumen="null";
        else 
            resumen="'"+getResumenEvolucion().toUpperCase()+"'";
        
        if(getManejo()==null || getManejo().equals(""))
            manejo="null";
        else 
            manejo="'"+getManejo().toUpperCase()+"'";
        
        if(getProblemasPend()==null || getProblemasPend().equals(""))
            problemas="null";
        else 
            problemas="'"+getProblemasPend().toUpperCase()+"'";
        
        if(getPlan()==null || getPlan().equals(""))
            plan="null";
        else 
            plan="'"+getPlan().toUpperCase()+"'";
        
        if(getRecomendaciones()==null || getRecomendaciones().equals(""))
            recomendaciones="null";
        else 
            recomendaciones="'"+getRecomendaciones().toUpperCase()+"'";
        
        if(getDiagnosticosIngreso()==null || getDiagnosticosIngreso().equals(""))
            diagIngreso="null";
        else 
            diagIngreso="'"+getDiagnosticosIngreso().toUpperCase()+"'";
        
        if(getDiagnosticosEgreso()==null || getDiagnosticosEgreso().equals(""))
            diagEgreso="null";
        else 
            diagEgreso="'"+getDiagnosticosEgreso().toUpperCase()+"'";
        
        if(getProcedimientosOperaciones()==null || getProcedimientosOperaciones().equals(""))
            operaciones="null";
        else 
            operaciones="'"+getProcedimientosOperaciones().toUpperCase()+"'";
        
        if(oEpisodioMedico.getAltaHospitalaria()!=null){
            Date fAltaHosp=(oEpisodioMedico.getAltaHospitalaria());
            Date hora= new Date();
            SimpleDateFormat fA=new SimpleDateFormat("yyyy-MM-dd");
            SimpleDateFormat hA = new SimpleDateFormat("HH:mm:ss");
            fAltaH=fA.format(fAltaHosp)+" "+hA.format(hora);
        }else{
            fAltaH="";
        }

            
		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
                     //System.out.println(ObtenerNoTarjetaMedico(oEpisodioMedico.getProceRe1().getCirujano().getNombres()));
			sQuery = "SELECT * FROM insertaHojaAlta('"+sUsuario+"', "+oPaciente.getFolioPaciente()+"::bigint," 
                                +oEpisodioMedico.getClaveEpisodio()+"::bigint, "+getNumIngresoHos()+"::bigint, "
                                +((fAltaH.compareTo("")!=0)?"'"+fAltaH+"'":null)+", "+motivoEgreso+", "+translado+", '"
                                +getReingreso()+"', "
                                +getPeso()+", "
                                +getTalla()+", "
                                +resumen+", "
                                +manejo+", "
                                +problemas+", "
                                +plan+", "
                                +recomendaciones+", "
                                +pronostico+", "
                                +destino+", "
                                +diagIngreso+", "
                                +diagEgreso+", "
                                +operaciones+", '"
                                +oEpisodioMedico.getSignosVitales().getTA()+"', '"
                                +oEpisodioMedico.getSignosVitales().getFR()+"', '"
                                +oEpisodioMedico.getSignosVitales().getFC()+"', '"
                                +oEpisodioMedico.getSignosVitales().getTemp()+"', '"                                                             
                                +sLlave+"');";                  
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
//***********************************************************************************************************
	
        ////segunda Etapa
    
    public int buscaNumeroHospitalizacion(long clavepac, long claveepi) throws Exception{
	int nHospitalizacion=0;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNumeroHospitalizacion("+clavepac+"::bigint,"+claveepi+"::bigint);";
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				nHospitalizacion=(((Double)vRowTemp.get( 0 )).intValue());
                                return nHospitalizacion; 
			}else{
                            return 0;
                        }
		} 
		 
	}   
    /////
    public Hospitalizacion[] buscaEgresosHospitalariosPorFecha(Date dFech1, Date dFech2) throws Exception{
        Hospitalizacion arrRet[] = null, oHosp = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        SimpleDateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat fFech = new SimpleDateFormat("dd-MM-yyyy");
        if(this == null){
            throw new Exception("Hospitalización.buscaEgresosHospitalariosPorFecha: error de programación, faltan datos");
        }else{
            if(dFech1!=null && dFech2==null)
                sQuery = "SELECT * FROM egresoshospitalariosarchivoporfecha('"+fFech.format(dFech1)+"'::DATE,null);";
            else
                sQuery = "SELECT * FROM egresoshospitalariosarchivoporfecha('"+fFech.format(dFech1)+"'::DATE,'"+fFech.format(dFech2)+"'::DATE);";
                //System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new Hospitalizacion[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                    oHosp.getEpisodioMedico().setClaveEpisodio(((Double) vRowTemp.get(1)).longValue());
                    oHosp.setNumIngresoHos(((Double)vRowTemp.get(2)).intValue());
                    oHosp.getPaciente().setNombres((String) vRowTemp.get(3));
                    oHosp.getPaciente().setApPaterno((String) vRowTemp.get(4));
                    oHosp.getPaciente().setApMaterno((String) vRowTemp.get(5));
                    oHosp.getPaciente().setSexoP((String) vRowTemp.get(6));
                    oHosp.getPaciente().setFechaNac((Date) vRowTemp.get(7));
                    String convierte=edad.format(oHosp.getPaciente().getFechaNac());
                    oHosp.getPaciente().setFechaNacTexto(convierte);
                    oHosp.getPaciente().calculaEdad();
                    oHosp.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(8)).intValue());
                    oHosp.setFechaIngresoHos((Date)vRowTemp.get(9));
                    oHosp.getEpisodioMedico().setMotivoEgresoP((String)vRowTemp.get(11));
                    oHosp.getEpisodioMedico().setAltaHospitalaria((Date)vRowTemp.get(12));
                    oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(13));
                    oHosp.getEpisodioMedico().setAreaServicioHRRB((String)vRowTemp.get(14));
                    oHosp.getEpisodioMedico().setFechaIngreso((Date)vRowTemp.get(15));
                    String res=(String)vRowTemp.get(16);
                    if(res.equals("0")) res="NO"; else res="SI";
                    oHosp.setCodeFirmada(res);
                    oHosp.setFolioCode((String)vRowTemp.get(17));
                    oHosp.getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(18));
                    oHosp.getEpisodioMedico().getCama().setAreaServicioHRRB((String)vRowTemp.get(19));
                    arrRet[i]=oHosp;
                }
            }
        }
        return arrRet;
    }
public int registraEntregaCODE(long folpac, long claveepi, int numingresoHosp, int numexp) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		if(folpac==0 && claveepi==0 && numingresoHosp==0 && numexp==0){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
	   }else{ 
			sQuery = "SELECT * FROM registrarFirmaDeCODE('"+sUsuario+"', "+folpac+", "+claveepi+", "+numingresoHosp+", "+numexp+");";   
			//System.out.println(sQuery);
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
    
    public Hospitalizacion[] buscarHistorialCODESpaciente(long folioPac) throws Exception{
        Hospitalizacion arrRet[] = null, oHosp = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        if(folioPac==0){
            throw new Exception("Hospitalización.BuscarHistorialCODESpaciente: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialcodepaciente("+folioPac+");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new Hospitalizacion[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                    oHosp.setNumIngresoHos(((Double)vRowTemp.get(1)).intValue());
                    oHosp.getEpisodioMedico().setClaveEpisodio(((Double) vRowTemp.get(2)).longValue());
                    oHosp.setFechaIngresoHos((Date)vRowTemp.get(3));
                    oHosp.setFolioCode((String)vRowTemp.get(4));
                    oHosp.getEpisodioMedico().setAltaHospitalaria((Date)vRowTemp.get(5));
                    oHosp.getEpisodioMedico().setMotivoEgresoP((String)vRowTemp.get(6));
                    oHosp.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(7)).intValue());
                    arrRet[i]=oHosp;
                }
            }
        }
        return arrRet;
    }
    
    public Hospitalizacion[] buscahistorialHOJAALTApaciente(long folioPac) throws Exception{
        Hospitalizacion arrRet[] = null, oHosp = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        if(folioPac==0){
            throw new Exception("Hospitalización.buscahistorialHOJAALTApaciente: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialhojaaltapaciente("+folioPac+");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet=new Hospitalizacion[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oHosp = new Hospitalizacion();
                    oHosp.getPaciente().setFolioPaciente(((Double) vRowTemp.get(0)).longValue());
                    oHosp.setNumIngresoHos(((Double)vRowTemp.get(1)).intValue());
                    oHosp.getEpisodioMedico().setClaveEpisodio(((Double) vRowTemp.get(2)).longValue());
                    oHosp.setFechaIngresoHos((Date)vRowTemp.get(3));
                    oHosp.setFolioCode((String)vRowTemp.get(4));
                    oHosp.getEpisodioMedico().setAltaHospitalaria((Date)vRowTemp.get(5));
                    oHosp.getEpisodioMedico().setMotivoEgresoP((String)vRowTemp.get(6));
                    oHosp.getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(7)).intValue());
                    oHosp.setFechaElaboracion((Date)vRowTemp.get(8));
                    oHosp.setPronosticoP((String)vRowTemp.get(9));
                    arrRet[i]=oHosp;
                }
            }
        }
        return arrRet;
    }
    
        public String getEstadoCode() {
	return sEstadoCode;
	}

	public void setEstadoCode(String valor) {
	sEstadoCode=valor;
	}

	public String getReingreso() {
	return sReingreso;
	}

	public void setReingreso(String valor) {
	sReingreso=valor;
	}

	public Date getFechaElaboracion() {
	return dFechaElaboracion;
	}

	public void setFechaElaboracion(Date valor) {
	dFechaElaboracion=valor;
	}

	public Date getFechaIngresoHos() {
	return dFechaIngresoHos;
	}

	public void setFechaIngresoHos(Date valor) {
	dFechaIngresoHos=valor;
	}

	public long getNumIngresoHos() {
	return nNumIngresoHos;
	}

	public void setNumIngresoHos(long valor) {
	nNumIngresoHos=valor;
	}

	public float getPeso() {
	return nPeso;
	}

	public void setPeso(float valor) {
	nPeso=valor;
	}

	public float getTalla() {
	return nTalla;
	}

	public void setTalla(float valor) {
	nTalla=valor;
	}

	public Parametrizacion getProcedencia() {
	return oProcedencia;
	}

	public void setProcedencia(Parametrizacion valor) {
	oProcedencia=valor;
	}

	public Parametrizacion getPronostico() {
	return oPronostico;
	}

	public void setPronostico(Parametrizacion valor) {
	oPronostico=valor;
	}

	public String getFolioCode() {
	return sFolioCode;
	}

	public void setFolioCode(String valor) throws Exception {
	sFolioCode=valor;
        ArrayList rst = null;
	long nRet = 0;
	String sQuery = "";
        FacesMessage message=null;

            sQuery = "SELECT buscafolioCODE('"+sFolioCode+"');";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                        nRet = (((Double) vRowTemp.get( 0 )).longValue());
                        System.out.println(nRet);
                    }
                }
            System.out.println("nRet: "+nRet);
            System.out.println("Folio: "+getPaciente().getFolioPaciente());
            if(getPaciente().getFolioPaciente()!=nRet && !sFolioCode.equals("") && sFolioCode!=null && nRet!=0){
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro de Folio", "Este Folio Ya Ha Sido Asignado");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                sFolioCode="";
            }               
	}

	public String getManejo() {
	return sManejo;
	}

	public void setManejo(String valor) {
	sManejo=valor;
	}

	public String getPlan() {
	return sPlan;
	}

	public void setPlan(String valor) {
	sPlan=valor;
	}

	public String getProblemasPend() {
	return sProblemasPend;
	}

	public void setProblemasPend(String valor) {
	sProblemasPend=valor;
	}

	public String getRecomendaciones() {
	return sRecomendaciones;
	}

	public void setRecomendaciones(String valor) {
	sRecomendaciones=valor;
	}

	public String getResumenEvolucion() {
	return sResumenEvolucion;
	}

	public void setResumenEvolucion(String valor) {
	sResumenEvolucion=valor;
	}

	public char getTipoEstancia() {
	return sTipoEstancia;
	}

	public void setTipoEstancia(char valor) {
	sTipoEstancia=valor;
	}

	public ArrayList getrrAreasUsadas() {
	return arrAreasUsadas;
	}

	public void setrrAreasUsadas(ArrayList valor) {
	arrAreasUsadas=valor;
	}

	public ArrayList getrrOrdenUsoArea() {
	return arrOrdenUsoArea;
	}

	public void setrrOrdenUsoArea(ArrayList valor) {
	arrOrdenUsoArea=valor;
	}

	public HospitalPsiquiatrico getHospitalPsiquiatrico() {
	return oHospitalPsiquiatrico;
	}

	public void setHospitalPsiquiatrico(HospitalPsiquiatrico valor) {
	oHospitalPsiquiatrico=valor;
	}

	public AtencionObstetrica getAtencionObstetrica() {
	return oAtencionObstetrica;
	}

	public void setAtencionObstetrica(AtencionObstetrica valor) {
	oAtencionObstetrica=valor;
	}

	public EpisodioMedico getEpisodioMedico() {
	return oEpisodioMedico;
	}

	public void setEpisodioMedico(EpisodioMedico valor) {
	oEpisodioMedico=valor;
	}

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public Estado getEstado() {
        return oEstado;
    }

    public void setEstado(Estado oEstado) {
        this.oEstado = oEstado;
    }

    public Municipio getMunicipio() {
        return oMunicipio;
    }

    public void setMunicipio(Municipio oMunicipio) {
        this.oMunicipio = oMunicipio;
    }

    public Ciudad getCiudad() {
        return oCiudad;
    }

    public void setCiudad(Ciudad oCiudad) {
        this.oCiudad = oCiudad;
    }

    public Seguro getSeguro() {
        return oSeguro;
    }

    public void setSeguro(Seguro oSeguro) {
        this.oSeguro = oSeguro;
    }

    public Expediente getExpediente() {
        return oExpediente;
    }

    public void setExpediente(Expediente oExpediente) {
        this.oExpediente = oExpediente;
    }

    public Etnicidad getEtnicidad() {
        return oEtnicidad;
    }

    public void setEtnicidad(Etnicidad oEtnicidad) {
        this.oEtnicidad = oEtnicidad;
    }

    public String getFechaIngresoHospStr() {
        return fechaIngresoHospStr;
    }

    public void setFechaIngresoHospStr(String fechaIngresoHospStr) {
        this.fechaIngresoHospStr = fechaIngresoHospStr;
    }

    public String getProcedenciaP() {
        return sProcedenciaP;
    }

    public void setProcedenciaP(String sProcedenciaP) {
        this.sProcedenciaP = sProcedenciaP;
    }

    public AreasUsadas getSalaLabor() {
        return oSalaLabor;
    }

    public void setSalaLabor(AreasUsadas oSalaLabor) {
        this.oSalaLabor = oSalaLabor;
    }

    public AreasUsadas getSalaExpulsion() {
        return oSalaExpulsion;
    }

    public void setSalaExpulsion(AreasUsadas oSalaExpulsion) {
        this.oSalaExpulsion = oSalaExpulsion;
    }

    public AreasUsadas getSalaRecuperacion() {
        return oSalaRecuperacion;
    }

    public void setSalaRecuperacion(AreasUsadas oSalaRecuperacion) {
        this.oSalaRecuperacion = oSalaRecuperacion;
    }

    public AreasUsadas getTerapiaIntensiva() {
        return oTerapiaIntensiva;
    }

    public void setTerapiaIntensiva(AreasUsadas oTerapiaIntensiva) {
        this.oTerapiaIntensiva = oTerapiaIntensiva;
    }

    public AreasUsadas getTerapiaIntermedia() {
        return oTerapiaIntermedia;
    }

    public void setTerapiaIntermedia(AreasUsadas oTerapiaIntermedia) {
        this.oTerapiaIntermedia = oTerapiaIntermedia;
    }

    public OrdenUsoArea getServicioIngreso() {
        return oServicioIngreso;
    }

    public void setServicioIngreso(OrdenUsoArea oServicioIngreso) {
        this.oServicioIngreso = oServicioIngreso;
    }

    public OrdenUsoArea getServicioSegundo() {
        return oServicioSegundo;
    }

    public void setServicioSegundo(OrdenUsoArea oServicioSegundo) {
        this.oServicioSegundo = oServicioSegundo;
    }

    public OrdenUsoArea getServicioTercero() {
        return oServicioTercero;
    }

    public void setServicioTercero(OrdenUsoArea oServicioTercero) {
        this.oServicioTercero = oServicioTercero;
    }

    public OrdenUsoArea getServicioEgreso() {
        return oServicioEgreso;
    }

    public void setServicioEgreso(OrdenUsoArea oServicioEgreso) {
        this.oServicioEgreso = oServicioEgreso;
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

    public List<Hospitalizacion> getHospitalizacion() {
        return lHospitalizacion;
    }

    public void setHospitalizacion(List<Hospitalizacion> lHospitalizacion) {
        this.lHospitalizacion = lHospitalizacion;
    }

    public List<Hospitalizacion> getFiltaHospitalizacion() {
        return lFiltaHospitalizacion;
    }

    public void setFiltaHospitalizacion(List<Hospitalizacion> lFiltaHospitalizacion) {
        this.lFiltaHospitalizacion = lFiltaHospitalizacion;
    }

    /**
     * @return the oASerHRRB
     */
    public AreaServicioHRRB getASerHRRB() {
        return oASerHRRB;
    }

    /**
     * @param oASerHRRB the oASerHRRB to set
     */
    public void setASerHRRB(AreaServicioHRRB oASerHRRB) {
        this.oASerHRRB = oASerHRRB;
    }

    /**
     * @return the sPronosticoP
     */
    public String getPronosticoP() {
        return sPronosticoP;
    }

    /**
     * @param sPronosticoP the sPronosticoP to set
     */
    public void setPronosticoP(String sPronosticoP) {
        this.sPronosticoP = sPronosticoP;
    }

    /**
     * @return the sConsultaExt
     */
    public String getConsultaExt() {
        return sConsultaExt;
    }

    /**
     * @param sConsultaExt the sConsultaExt to set
     */
    public void setConsultaExt(String sConsultaExt) {
        this.sConsultaExt = sConsultaExt;
    }

    /**
     * @return the nTipo
     */
    public int getTipo() {
        return nTipo;
    }

    /**
     * @param nTipo the nTipo to set
     */
    public void setTipo(int nTipo) {
        this.nTipo = nTipo;
    }

    /**
     * @return the sDiagnosticosIngreso
     */
    public String getDiagnosticosIngreso() {
        return sDiagnosticosIngreso;
    }

    /**
     * @param sDiagnosticosIngreso the sDiagnosticosIngreso to set
     */
    public void setDiagnosticosIngreso(String sDiagnosticosIngreso) {
        this.sDiagnosticosIngreso = sDiagnosticosIngreso;
    }

    /**
     * @return the sDiagnosticosEgreso
     */
    public String getDiagnosticosEgreso() {
        return sDiagnosticosEgreso;
    }

    /**
     * @param sDiagnosticosEgreso the sDiagnosticosEgreso to set
     */
    public void setDiagnosticosEgreso(String sDiagnosticosEgreso) {
        this.sDiagnosticosEgreso = sDiagnosticosEgreso;
    }

    /**
     * @return the sProcedimientosOperaciones
     */
    public String getProcedimientosOperaciones() {
        return sProcedimientosOperaciones;
    }

    /**
     * @param sProcedimientosOperaciones the sProcedimientosOperaciones to set
     */
    public void setProcedimientosOperaciones(String sProcedimientosOperaciones) {
        this.sProcedimientosOperaciones = sProcedimientosOperaciones;
    }
    public String getCodeFirmada() {
        return bCodeFirmada;
    }

    public void setCodeFirmada(String bCodeFirmada) {
        this.bCodeFirmada = bCodeFirmada;
    }
} 

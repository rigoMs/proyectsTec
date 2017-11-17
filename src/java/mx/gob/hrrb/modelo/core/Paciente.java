/*
    ALBERTO
    AGREGUE EL ATIBUTO sLugarNacimiento CON SET Y GET
    ALBERTO 
    QUITE EL ATRIBUTO sGrupoRH Y AGREGUE LOS SIGUIENTES ATRIBUTOS
    Parametrizacion oTipoSangre
    Parametrizacion oFactorRH
    METODOS AFECTADOS
    buscaPaciente();
    buscaTodosloPacientes();
    buscaPacienteAltaHospit();

*/

package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;

import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.GregorianCalendar;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import org.primefaces.context.RequestContext;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Paciente extends Persona implements Serializable{
    private AccesoDatos oAD;
    private boolean bDiscapacitado;
    private boolean bNacidoEnHospital;
    private String bStatusPac;
    private Date dFechaRegistroNivelSoc;
    private Date dVigenciaNivelSoc;
    private float nPeso;
    private float nGramo;
    
    private float nTalla;
    private Parametrizacion oClaveEdad;
    private Parametrizacion oNivelSocioEco;
    private Parametrizacion oTipoSol;
    private String sCalleNum;
    private String sColonia;
    private String sFolioDefuncion;
    //private String sGrupoRH;
    private ArrayList arrEstudios;
    private ArrayList arrSeguro;
    private Referencia oReferencia;
    
    //etnia solo lleva si o no
    private Etnicidad oEtnicidad;
    //***********************************
    private Ubicacion oUbicacion;
    private long nFolioPaciente;
    private String sEdad;
    private Municipio oMunicipio;
    private Expediente oExpediente;
    private String sPais;
    private String sFechaNac;
    private Estado oEstado;
    
    private Ciudad oCiudad;
    private Seguro oSeg;
    private String sClaveEdadP;
    private String sNacidoEnHospitalP;
    private String sVigencia;
    private String sOtroPais;
    private String codigoPos;
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private int nOpcionUrg;
    private Date dFechaConsulta;
    private int nAdmUrg;
    private DiagnosticoCIE10 oDiagcie;
    private int des;
    private int cantEP;
    private Parametrizacion oParametrizacion;
    private Seguro oSeguro;
    private CiudadCP oCiudadCP;
    private String VigenciaNivelSTR;
    private int año;
    private int mes;
    private int dia;
    private String fechaSistema;
    private Calendar fecha=null;
    private String FechaStr;
    private long nHospitalizado;
    private long nClaveEpisodio;
    private String sHablaSpa;
    private String sGrat;
    private String sLugarNacimiento;
    private EpisodioMedico[] arrEpisodiosMedicos=null;  
    /*INICIAN ATRIBUTOS AGREGADOS POR ALBERTO 27052016*/
    private Parametrizacion oTipoSangre;
    private Parametrizacion oFactorRH;
    /*TERMINAN ATRIBUTOS AGREGADO POR ALBERTO */
        private float nImc;
        
    //*******Tipo de Vialidad y Asentamiento
    protected TipoVialidad oTipoVialidad;
    protected TipoAsentamiento oTipoAsentamiento;
    protected String sNumInterior;
    protected String sNumExterior;
    protected String sTelefono;
    private Estado oEstadoN;
    private Parametrizacion oGrat;
       

       
        
        
    /**
     *
     */
    public Paciente(){
        super();
        
        setSexoP("M");
        oClaveEdad=new Parametrizacion();
        VigenciaNivelSTR="";
        this.oMunicipio = new Municipio();
        this.oExpediente = new Expediente();
        sPais="MÉXICO";
        this.oEstado=new Estado();
        this.oCiudad=new Ciudad();
        this.oCiudadCP=new CiudadCP();
        this.oTipoSol=new Parametrizacion();
        
        this.oSeg=new Seguro();
        sOtroPais="";
        sFechaNac="";
        oTipoSol=new Parametrizacion();
        oClaveEdad=new Parametrizacion();
        oReferencia=new Referencia();
        this.oEstadoN= new Estado();
        //crea un nuevo objeto de etnicidad
        this.oEtnicidad= new Etnicidad();
        
        //**********************************
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
            nOpcionUrg=0;
            dFechaConsulta=new Date();
            //oAdmUrg=new AdmisionUrgs();
            oDiagcie=new DiagnosticoCIE10();
            des=0;
            cantEP=0;
            fecha = new GregorianCalendar();
            año = fecha.get(Calendar.YEAR);
            mes = fecha.get(Calendar.MONTH);
            dia = fecha.get(Calendar.DAY_OF_MONTH);
            generaFechaSis(dia,mes+1,año);
            oTipoVialidad=new TipoVialidad();
            oTipoAsentamiento= new TipoAsentamiento();
            sNumInterior="";
            sNumExterior="";
            sTelefono="";
            
            
    }
    
    
    
    public Paciente(boolean bDiscapacitado, boolean bNacidoEnHospital, String bStatusPac, Date dFechaRegistroNivelSoc,
            Date dVigenciaNivelSoc, float nPeso,float nGramo, float nTalla, Parametrizacion oClaveEdad, 
            Parametrizacion oNivelSocioEco,
            
            Parametrizacion oTipoSol,String sCalleNum, String sColonia, 
            String sFolioDefuncion, String sGrupoRH, 
            ArrayList arrEstudios, ArrayList arrSeguro, Referencia oReferencia, 
            Etnicidad oEtnicidad, Ubicacion oUbicacion,
            Date dFechaNac, int nEdad, Parametrizacion oEstadoCivil, 
            String sApMaterno, String sApPaterno, String sCurp,
            String sNombres, Parametrizacion oSexo, String sTelefono, String sSexo ) {
        super(dFechaNac, nEdad, oEstadoCivil, sApMaterno, sApPaterno, sCurp, sNombres, sTelefono, sSexo);
        this.bDiscapacitado = bDiscapacitado;
        this.bNacidoEnHospital = bNacidoEnHospital;
        this.bStatusPac = bStatusPac;
        this.dFechaRegistroNivelSoc = dFechaRegistroNivelSoc;
        this.dVigenciaNivelSoc = dVigenciaNivelSoc;
        this.nPeso = nPeso;
        this.nGramo= nGramo;
        
        
        this.nTalla = nTalla;
        this.oClaveEdad = oClaveEdad;
        this.oNivelSocioEco = oNivelSocioEco;
        this.oTipoSol = oTipoSol;
        this.sCalleNum = sCalleNum;
        this.sColonia = sColonia;
        this.sFolioDefuncion = sFolioDefuncion;
        //this.sGrupoRH = sGrupoRH;
        this.arrEstudios = arrEstudios;
        this.arrSeguro = arrSeguro;
        this.oReferencia = oReferencia;
        //**********************************
        this.oEtnicidad = oEtnicidad;
        //************************************
        this.oUbicacion = oUbicacion;
        
    }
    
    private String generaFechaSis(int d, int m, int a){
            String d1, m1, a1;
            if(d<10)
                d1="0"+d;
            else d1=Integer.toString(d);
            if(m<10)
                m1="0"+m;
            else m1=Integer.toString(m);
            a1=Integer.toString(a);

            String gf=a1+"/"+m1+"/"+d1;
            setFechaSistema(gf);
            return gf;
        }
/*METODO MODIFICADO POR DANIEL HERNANDEZ SANCHEZ 3 FASE*/
    //*****************************************************************************************
    //*********metodo para buscar paciente de consulta externa****************************************
    //*****************************************************************************************
    
	public String buscarPacCE(int iden) throws Exception{
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";        
        String v[];
        resetea();
            if( getFolioPaciente()<0){   //completar llave
                throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{  
                sQuery = "SELECT * FROM buscaExpediente("+getFolioPaciente()+"::bigint);";
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
                }
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    setFolioPaciente(((Double)vRowTemp.get(0)).longValue());                                
                    //getExpediente().setNumero(((Double)vRowTemp.get(1)).intValue());                                
                    setApPaterno((String) vRowTemp.get( 2 ));
                    setApMaterno((String) vRowTemp.get( 3 ));
                    setNombres((String) vRowTemp.get( 4 ));
                    setSexoP((String)vRowTemp.get(5));
                    if (vRowTemp.get(6)!=null)
                    v=vRowTemp.get(6).toString().split("-");
                    else
                            v="0000/00/00".split("/");
                    setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                    setFechaNac((Date)vRowTemp.get(6));
                    setTelefono((String)vRowTemp.get(7));
                    setCalleNum((String)vRowTemp.get(8));
                    setColonia((String)vRowTemp.get(9));
                    setCp((String)vRowTemp.get(10));
                    setCodigoPos((String)vRowTemp.get(10));
                    oEstado.setClaveEdo((String) vRowTemp.get( 11 ));
                    oEstado.buscar();
                    oMunicipio.setClaveMun((String) vRowTemp.get( 12 ));
                    oMunicipio.buscar(oEstado.getClaveEdo());
                    oCiudad.setClaveCiu((String) vRowTemp.get( 13 ));
                    oCiudad.buscar(oEstado.getClaveEdo(), oMunicipio.getClaveMun());
                    setEdadNumero((String)vRowTemp.get(14));
                    setOtroPais((String)vRowTemp.get(15));
                    if (getOtroPais().compareToIgnoreCase("")==0)
                            setOtroPais("MÉXICO");
                    setStatusPac((String)vRowTemp.get(16));
                    String x=(String)vRowTemp.get(17);
                    if (x.compareTo("")!=0){
                    getTipoSol().setValor(getTipoSol().buscaValorParametrizado(x));
                    getTipoSol().setTipoParametro(x.charAt(3)+""+x.charAt(4));}
                    else{
                            getTipoSol().setValor("");
                            getTipoSol().setTipoParametro("");}
                    getReferencia().setClave((String)vRowTemp.get(18));
                    getReferencia().buscar();
                    buscasegpop(getFolioPaciente());
                    getExpediente().getServicioIngreso().setDescripcion((String) vRowTemp.get( 19 ));
                    getExpediente().setFechaApertura((Date) vRowTemp.get(20));
                    getExpediente().setPrograma(new Programa());
                    getExpediente().getPrograma().setClave(((Double) vRowTemp.get( 21 )).intValue());
                    getExpediente().getPrograma().buscaDescripcionPorClave(getExpediente().getPrograma().getClave());
                    getExpediente().setRutaINE((String)vRowTemp.get(22));
                    oSeguro=new Seguro();
                    oSeguro.setRutaPOLIZA((String)vRowTemp.get(23));
                    bRet = true;
                }
            }
            if (iden==0) 
                return "consultaexterna/ModificarPacienteCE";
            else
                if(iden==1)
                    return "Expediente";
                else
                    return "RegistrarCita";
	}
        /******************************************************************/
        //*************************************************************************************
        public String calculaEdad(){
        if(getFechaNacTexto().compareTo("")!=0){
        String x[]=getFechaNacTexto().split("/");
        Date fActual=new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecAct=formato.format(fActual);
        int d1=Integer.parseInt(x[0]);
        int m1=Integer.parseInt(x[1]);
        int a1=Integer.parseInt(x[2]);
        String v[]=fecAct.split("/");
        int d2=Integer.parseInt(v[0]);
        int m2=Integer.parseInt(v[1]);
        int a2=Integer.parseInt(v[2]);
        int añofinal, mesfinal, diafinal=0;
        String mensaje="";
        int aux=0;
        int maux=m1;
        boolean b=false;
        añofinal=a2-a1;
        if (añofinal==1 && m2<=m1){
            añofinal=0;
            if (m1==m2){aux=1; b=true; if (d1<=d2)añofinal=1;}
            m2=12-m1+m2;
            m1=aux;
            
        }
        if (añofinal>0){
            if (m2<m1){ añofinal--; }
            if (m2==m1 && d2<d1){añofinal--;}
            if (añofinal==1)
            mensaje=añofinal+" AÑO";
            else
                mensaje=añofinal+" AÑOS";
          getClaveEdad().setClaveParametro("T25");
          getClaveEdad().setTipoParametro("03");
        }else{
            mesfinal=m2-m1;
            if(mesfinal>0){
                diafinal=d2-d1;
                if (mesfinal==1 && d2>=d1)
                mensaje=mesfinal+" MES";
                else{
                    if(mesfinal>1 && d2>=d1)
                    mensaje=mesfinal+" MESES";
                    else{
                        if((mesfinal-1)==1)
                        mensaje=(mesfinal-1)+" MES";
                        else{
                            if (mesfinal>1 && d2<d1 && b==true)
                                mensaje=mesfinal+" MESES";
                            else
                            mensaje=(mesfinal-1)+" MESES";
                        }
                    }
                }
                getClaveEdad().setClaveParametro("T25");
                getClaveEdad().setTipoParametro("02");
            }
            if (mesfinal==0){
                diafinal=d2-d1;
                if(diafinal==0 && añofinal==0)
                    diafinal=1;
              if (diafinal==1)
              mensaje=diafinal+" DÍA";
              else{
                  if(diafinal>1)
               mensaje=diafinal+" DÍAS";
              }
              getClaveEdad().setClaveParametro("T25");
              getClaveEdad().setTipoParametro("01");
            }
            m1=maux;
            if (mesfinal==1 && añofinal==0 && d1>d2){
                if (m1==1 || m1==3 || m1==5 || m1==7 || m1==8 || m1==10 || m1==12)
                    diafinal=(31-d1)+d2;
                if (m1==4 || m1==6 || m1==9 || m1==11)
                    diafinal=(30-d1)+d2;
                if (m1==2){
                    if ((a1 % 4 == 0) && ((a1 % 100 != 0) || (a1 % 400 == 0)))
                        diafinal=(29-d1)+d2;
                    else
                        diafinal=(28-d1)+d2;
                }
                mensaje=diafinal+" DÍAS";
                getClaveEdad().setClaveParametro("T25");
                getClaveEdad().setTipoParametro("01");
            }
        }
        setEdadNumero(mensaje);
        return mensaje;
        }else{
            return "";
        }
        }
        //*************************************************************************************
        public void resetea(){
            //getExpediente().setNumero(0);                                
            setApPaterno("");
            setApMaterno("");
            setNombres("");
            setSexoP("M");
            setFechaNacTexto("00/00/0000");
            setFechaNac(null);
            setTelefono("");
            setCalleNum("");
            setColonia("");
            setCp("");
            oEstado.setClaveEdo("");
            oEstadoN.setClaveEdo("");
            oMunicipio.setClaveMun("");
            oCiudad.setClaveCiu("");
            setEdadNumero("");
            oSeg.setNumero("");
            oSeg.setVigencia(null);
            setVigenciaTexto("00/00/0000");
            setOtroPais("MÉXICO");
        }
        //*************************************************************************************
        public void buscasegpop(long folio) throws Exception{
	ArrayList rst = null;
	String sQuery = "";
        String v[];
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscanumeroyvigsegpop("+getFolioPaciente()+"::bigint);";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                oSeg.setNumero((String)vRowTemp.get(0));
                                oSeg.setVigencia((Date)vRowTemp.get(1));
                                if (oSeg.getVigencia()!=null)
                                v=vRowTemp.get(1).toString().split("-");
                                else
                                 v="0000/00/00".split("/");
                                setVigenciaTexto(v[2]+"/"+v[1]+"/"+v[0]);
			}
		}
	}
        //******************************************************************
        @Override
	public Paciente[] buscarTodos() throws Exception{
	Paciente arrRet[]=null, oPaciente=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPaciente();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Paciente[rst.size()];
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
			throw new Exception("Paciente.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPaciente();"; 
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
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPaciente();"; 
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
        public int  modificaLugarDenacimiento(String Usuario, long foliopac, String  lugarnacimiento) throws Exception{
         int Resul=0; 
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from modificapacientelugardenacim('"+Usuario+"',"+foliopac+", "+this.isNull(lugarnacimiento)+");";
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				Resul=(((Double)vRowTemp.get( 0 )).intValue());
                        }
                 
		} 
                  return Resul; 
      } 
  
        //*******************************************************************************
        public int modificarPacCE() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if (getVigenciaTexto()==null || getVigenciaTexto().compareTo("")==0)
            setVigenciaTexto("00/00/0000");
        if (getFechaNacTexto()==null || getFechaNacTexto().compareTo("")==0)
            setFechaNacTexto("00/00/0000");
        String v[]=getFechaNacTexto().split("/");
        String v2[]=getVigenciaTexto().split("/");
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        
        String edo="", mun="", ciu="";
        try { if (getFechaNacTexto().compareTo("00/00/0000")!=0){
            setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0]));
        }else{ 
        setFechaNac(null);
        }
        if (getVigenciaTexto().compareTo("00/00/0000")!=0){
            getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
        }} catch (Exception ex) {ex.printStackTrace();}
		 if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
                        String cpAux="";
                        String vigenciaAux="'";
                        String aposFecha="'";
                        String ref="";
                        String sSolicitud="";
                        
                        if (getTipoSol().getTipoParametro()==null || getTipoSol().getTipoParametro().compareTo("")==0){sSolicitud="null";}else{sSolicitud="'T32"+getTipoSol().getTipoParametro()+"'";}
                        if (getReferencia().getClave().compareTo("")==0){ref="null";}else{ref="'"+getReferencia().getClave()+"'";}
                        if (getVigenciaTexto().compareTo("")==0 || getVigenciaTexto().compareTo("00/00/0000")==0){getSeg().setVigencia(null); vigenciaAux="";}
                         if (getFechaNac()==null){ aposFecha="";}
                        if (getCodigoPos().compareTo("")==0){cpAux="null";}else {cpAux="'"+getCodigoPos()+"'";}
                        if (getOtroPais().compareToIgnoreCase("MÉXICO")!=0 && getOtroPais().compareToIgnoreCase("MEXICO")!=0){edo="'99'"; mun="'999'"; ciu="'9999'"; setOtroPais("'"+getOtroPais()+"'");}else{edo="'"+getEstado().getClaveEdo()+"'"; mun="'"+getMunicipio().getClaveMun()+"'"; ciu="'"+getCiudad().getClaveCiu()+"'"; setOtroPais("null");}
			sQuery = "SELECT * FROM modificarPacCE('"+sUsuario+"', "+getFolioPaciente()+"::bigint, '"+getNombres()+"', '"+getApPaterno()+"', '"+getApMaterno()+"', '"+getSexoP()+"', "+aposFecha+getFechaNac()+aposFecha+", '"+getTelefono()+"', '"+getCalleNum()+"','"+getColonia()+"',"+cpAux+", "+getOtroPais().toUpperCase()+","+edo+", "+mun+", "+ciu+", '"+getSeg().getNumero()+"', "+vigenciaAux+getSeg().getVigencia()+vigenciaAux+",'"+getFolioPaciente()+"', "+sSolicitud+", "+ref+");";
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
        //*******************************************************************************
        //******************************insertar paciente consulta externa***************
        //*******************************************************************************
        public int insertarPacCE() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if (getVigenciaTexto()==null || getVigenciaTexto().compareTo("")==0)
            setVigenciaTexto("00/00/0000");
        if (getFechaNacTexto()==null || getFechaNacTexto().compareTo("")==0)
            setFechaNacTexto("00/00/0000");
        String v[]=getFechaNacTexto().split("/");
        String v2[]=getVigenciaTexto().split("/");
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        String aposNac="'";
        try { 
            if (getFechaNacTexto().compareTo("00/00/0000")!=0){
                setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0]));
            }
            else{
                setFechaNac(null);
                aposNac="";
            }
            if (getVigenciaTexto().compareTo("00/00/0000")!=0){
            getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));}
        } catch (Exception ex) {ex.printStackTrace();}
        
		 if( this==null){   //completar llave
			throw new Exception("Paciente.insertar: error de programación, faltan datos");
		}else{ 
                        String cpAux="";
                        String vigenciaAux="'";
                        String edo, mun, ciu;
                        edo=mun=ciu="";
                        if (getOtroPais().compareToIgnoreCase("MÉXICO")!=0 && getOtroPais().compareToIgnoreCase("MEXICO")!=0){edo="'99'"; mun="'999'"; ciu="'9999'"; setOtroPais("'"+getOtroPais()+"'");}else{edo="'"+getEstado().getClaveEdo()+"'"; mun="'"+getMunicipio().getClaveMun()+"'"; ciu="'"+getCiudad().getClaveCiu()+"'"; setOtroPais("null");}
                        if (getVigenciaTexto().compareTo("")==0 || getVigenciaTexto().compareTo("00/00/0000")==0){getSeg().setVigencia(null); vigenciaAux="";}
                        if (getCodigoPos().compareTo("")==0){cpAux="null";}else {cpAux="'"+getCodigoPos()+"'";}
			sQuery = "SELECT * FROM insertaPacCE('"+sUsuario+"', '"+getNombres()+"', '"+getApPaterno()+"', '"+getApMaterno()+"', "+aposNac+getFechaNac()+aposNac+", '"+getSexoP()+"', '"+getTelefono()+"', '"+getCalleNum()+"','"+getColonia()+"', 'T32"+getTipoSol().getTipoParametro()+"', '1', "+edo+", "+mun+", "+ciu+", "+cpAux+", "+getOtroPais().toUpperCase()+",'"+getReferencia().getClave()+"', '"+getClaveEdad().getClaveParametro()+getClaveEdad().getTipoParametro()+"', '"+getSeg().getNumero()+"', "+vigenciaAux+getSeg().getVigencia()+vigenciaAux+");";                        
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() > 0) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	} 
        //*******************************************************************************
        @Override
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Paciente.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPaciente();"; 
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
        
        public void buscaPacienteReferencia(EpisodioMedico epi, int idserv) throws Exception{
        ServicioRealizado[] arrServReal;
        Traslado oTraslado; 
        ArrayList rst = null; 
            arrEpisodiosMedicos=new EpisodioMedico[1];
        
            String sQuery="select * from  buscadatospacHojaReferencia("+epi.getPaciente().getFolioPaciente()+","+epi.getClaveEpisodio()+","+idserv+");";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null && rst.size()>0) {  
                 oTraslado=new Traslado();
                ArrayList vRowTemp = (ArrayList)rst.get(0); 
                this.setCalleNum((String)vRowTemp.get(0));
                this.getCiudad().setClaveCiu((String)vRowTemp.get(1));
                this.getMunicipio().setClaveMun((String)vRowTemp.get(2));
                this.getEstado().setClaveEdo((String)vRowTemp.get(3));
                this.setTelefono((String)vRowTemp.get(4));
                this.setSexoP((String)vRowTemp.get(5));
                this.setFechaNac((Date) vRowTemp.get(6));
                this.setPeso(((Double)vRowTemp.get(7)).floatValue());
                this.setTalla(((Double)vRowTemp.get(8)).floatValue());
                this.getEstado().buscar();
                this.getMunicipio().buscar(this.getEstado().getClaveEdo());
                this.getCiudad().buscar(this.getEstado().getClaveEdo(), this.getMunicipio().getClaveMun());
                oTraslado.setFechaDeseable((Date) vRowTemp.get(9));
                oTraslado.setHospital((String)vRowTemp.get(10));
                if (((String)vRowTemp.get(11)).equals("0"))
                    oTraslado.setUrgente(false);
                else
                    oTraslado.setUrgente(true);
                oTraslado.setFechaAcordada((Date) vRowTemp.get(12));
                oTraslado.setDiagEnvio((String)vRowTemp.get(13));
                oTraslado.setOtroMotiv((String)vRowTemp.get(14));
                oTraslado.getMotivoEnvio().setTipoParametro((String)vRowTemp.get(15));
                oTraslado.getMotivoEnvio().setClaveParametro((String)vRowTemp.get(16)); 
                oTraslado.setArea((String)vRowTemp.get(17));
                arrEpisodiosMedicos=new EpisodioMedico[1];
                arrServReal= new Traslado[1];
                arrServReal[0]=oTraslado;
                arrEpisodiosMedicos[0]=new EpisodioMedico();
                arrEpisodiosMedicos[0].setServiciosRealizados(arrServReal);
                this.setEpisodiosMedicos(arrEpisodiosMedicos);
                this.getEpisodioMedicoActual().setServiciosRealizados(arrServReal);
                this.getEpisodioMedicoActual().getNotaMedHrrb().setResumenInter((String)vRowTemp.get(18));
                this.getEpisodioMedicoActual().getNotaMedHrrb().setPronostico((String)vRowTemp.get(19));
                this.getEpisodioMedicoActual().getSignosVitales().setTA((String)vRowTemp.get(20));
                this.getEpisodioMedicoActual().getSignosVitales().setTemp((String)vRowTemp.get(21));
                this.getEpisodioMedicoActual().getSignosVitales().setFC((String)vRowTemp.get(22)); 
                this.getEpisodioMedicoActual().getSignosVitales().setFR((String)vRowTemp.get(23));
                this.getEpisodioMedicoActual().getSignosVitales().setPulso(((Double)vRowTemp.get(24)).intValue()); 
            }
    } 
     

//****************************************************************************************************************************************************
//***************************************busca paciente por nombres***********************
//****************************************************************************************        
        public Paciente[] buscarPac() throws Exception{
	Paciente arrRet[]=null, oPaciente=null;
	ArrayList rst = null;
        ArrayList<Paciente> vObj=null;
	String sQuery = "", aPaterno="", aMaterno="", nombre="", numExp="";
        String edad="";
	int i=0, nTam=0;
        
        if (getOpcionUrg()==0){
            aPaterno=this.getApPaterno();
            aMaterno=this.getApMaterno();
            nombre=this.getNombres();
            numExp="null";
        }else{
            aPaterno="";
            aMaterno="";
            nombre="";
            numExp=getExpediente().getNumero()+"";
        }
        if(getAdmUrg()==2){
            if (numExp.compareTo("null")==0)
		sQuery = "SELECT * FROM buscaPacienteAdmitido('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+numExp+");"; 
            else
                sQuery = "SELECT * FROM buscaPacienteAdmitido('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+Integer.parseInt(numExp)+");"; 
        }
        else if(getAdmUrg()==8 || getAdmUrg()==7 || getAdmUrg()==5){
            if (numExp.compareTo("null")==0)
		sQuery = "SELECT * FROM buscaPacienteAdmitidoHosp('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+numExp+");"; 
            else
                sQuery = "SELECT * FROM buscaPacienteAdmitidoHosp('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+Integer.parseInt(numExp)+");"; 
        }else if(getAdmUrg()==10){
            if (numExp.compareTo("null")==0)
		sQuery = "SELECT * FROM buscaPacienteConExpediente('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+numExp+");"; 
            else
                sQuery = "SELECT * FROM buscaPacienteConExpediente('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+Integer.parseInt(numExp)+");"; 
        }
        else
            if (numExp.compareTo("null")==0)
		sQuery = "SELECT * FROM buscaPaciente('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+numExp+");"; 
            else
                sQuery = "SELECT * FROM buscaPaciente('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+Integer.parseInt(numExp)+");"; 
            System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                            oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPaciente.setApPaterno((String)vRowTemp.get(1));
                            oPaciente.setApMaterno((String)vRowTemp.get(2));
                            oPaciente.setNombres((String)vRowTemp.get(3));
                            oPaciente.setFechaNac((Date) vRowTemp.get(4));
                            oPaciente.setCurp((String)vRowTemp.get(5));
                            oPaciente.setEdadNumero((String)vRowTemp.get(6));
                            edad=(String)vRowTemp.get(6);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            }
                            oPaciente.getMunicipio().setDescripcionMun((String)vRowTemp.get(7));
                            oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(8)).intValue());
                            oPaciente.getExpediente().setStatusExpediente((String)vRowTemp.get(9));
                            vObj.add(oPaciente);
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
                
		return arrRet; 
	}         
//****************************************************************************************************************************************************
     public Paciente[] buscarExp() throws Exception{
	Paciente arrRet[]=null, oPaciente=null;
	ArrayList rst = null;
        ArrayList<Paciente> vObj=null;
	String sQuery = "", aPaterno="", aMaterno="", nombre="", numExp="";
        String edad="";
	int i=0, nTam=0;

        if (getOpcionUrg()==0){
            aPaterno=this.getApPaterno();
            aMaterno=this.getApMaterno();
            nombre=this.getNombres();
            numExp="null";
        }else{
            aPaterno="";
            aMaterno="";
            nombre="";
            numExp=getExpediente().getNumero()+"";
        }
            if (numExp.compareTo("null")==0)
		sQuery = "SELECT * FROM buscarExpedientes('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+numExp+");"; 
            else
                sQuery = "SELECT * FROM buscarExpedientes('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+Integer.parseInt(numExp)+");"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                            oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPaciente.setApPaterno((String)vRowTemp.get(1));
                            oPaciente.setApMaterno((String)vRowTemp.get(2));
                            oPaciente.setNombres((String)vRowTemp.get(3));
                            oPaciente.setFechaNac((Date) vRowTemp.get(4));
                            oPaciente.setCurp((String)vRowTemp.get(5));
                            oPaciente.setEdadNumero((String)vRowTemp.get(6));
                            edad=(String)vRowTemp.get(6);
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            oPaciente.getMunicipio().setDescripcionMun((String)vRowTemp.get(7));
                            oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(8)).intValue());
                            oPaciente.getExpediente().setStatusExpediente((String)vRowTemp.get(9));
                            vObj.add(oPaciente);
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
                
		return arrRet; 
	} 

public String buscarPacARCHIVO() throws Exception{
    resetea();
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";        
    String v[];
        if( this==null){   //completar llave
               throw new Exception("Paciente.buscar: error de programación, faltan datos");
       }else{  
               sQuery = "SELECT * FROM buscadatosexpediente("+getFolioPaciente()+"::bigint,"+getExpediente().getNumero()+"::bigint );";
               oAD=new AccesoDatos(); 
               if (oAD.conectar()){ 
                       rst = oAD.ejecutarConsulta(sQuery); 
                       oAD.desconectar(); 
               }
               if (!rst.isEmpty()) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    setFolioPaciente(((Double)vRowTemp.get(0)).longValue());                                
                    //getExpediente().setNumero(((Double)vRowTemp.get(1)).intValue());                                
                    setApPaterno((String) vRowTemp.get( 2 ));
                    setApMaterno((String) vRowTemp.get( 3 ));
                    setNombres((String) vRowTemp.get( 4 ));
                    setSexoP((String)vRowTemp.get(5));
                    if (vRowTemp.get(6)!=null)
                    v=vRowTemp.get(6).toString().split("-");
                    else
                            v="0000/00/00".split("/");
                    setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                    setFechaNac((Date)vRowTemp.get(6));
                    setTelefono((String)vRowTemp.get(7));
                    setCalleNum((String)vRowTemp.get(8));
                    setColonia((String)vRowTemp.get(9));
                    setCp((String)vRowTemp.get(10));
                    setCodigoPos((String)vRowTemp.get(10));
                    oEstado.setClaveEdo((String) vRowTemp.get( 11 ));
                    oEstado.buscar();
                    oMunicipio.setClaveMun((String) vRowTemp.get( 12 ));
                    oMunicipio.buscar(oEstado.getClaveEdo());
                    oCiudad.setClaveCiu((String) vRowTemp.get( 13 ));
                    oCiudad.buscar(oEstado.getClaveEdo(), oMunicipio.getClaveMun());
                    setEdadNumero((String)vRowTemp.get(14));
                    setOtroPais((String)vRowTemp.get(15));
                    if (getOtroPais().compareToIgnoreCase("")==0)
                            setOtroPais("MÉXICO");
                    setStatusPac((String)vRowTemp.get(16));
                    buscasegpop(getFolioPaciente());
                    getExpediente().getServicioIngreso().setDescripcion((String) vRowTemp.get( 17 ));
                    getExpediente().setFechaApertura((Date) vRowTemp.get(18));
                    getExpediente().setPrograma(new Programa());
                    getExpediente().getPrograma().setDescripcion((String)vRowTemp.get( 19 ));
                    getExpediente().setRutaINE((String)vRowTemp.get(20));
                    oSeguro=new Seguro();
                    oSeguro.setRutaPOLIZA((String)vRowTemp.get(21));
                    bRet = true;
               }
       }
    return  "Expediente";
}
//************************************************************************
//metodo para busqueda por expediente*************************************
//************************************************************************
    public String buscaPacienteExp() throws Exception{
            resetea();
            ArrayList rst = null;
            String sQuery = "";
            String v[];
                     if( this==null){   //completar llave
                            throw new Exception("Expediente.buscar: error de programación, faltan datos");
                    }else{ 
                            sQuery = "SELECT * FROM buscaPacienteExp("+getFolioPaciente()+"::bigint);"; 
                            
                            oAD=new AccesoDatos(); 
                            if (oAD.conectar()){ 
                                    rst = oAD.ejecutarConsulta(sQuery); 
                                    oAD.desconectar(); 
                            }
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    setNombres((String) vRowTemp.get( 0 ));
                                    setApPaterno((String) vRowTemp.get( 1 ));
                                    setApMaterno((String) vRowTemp.get( 2 ));
                                    v=vRowTemp.get(3).toString().split("-");
                                    setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                    setFechaNac((Date)vRowTemp.get(3));
                                    setSexoP((String)vRowTemp.get(4));
                                    setCalleNum((String)vRowTemp.get(5));
                                    setColonia((String)vRowTemp.get(6));
                                    getEstado().setClaveEdo((String) vRowTemp.get( 7 ));
                                    getMunicipio().setClaveMun((String) vRowTemp.get( 8 ));
                                    getCiudad().setClaveCiu((String) vRowTemp.get( 9 ));
                                    getCiudadCP().setCp((String)vRowTemp.get(10));
                                        if(vRowTemp.get(11).toString().compareTo("")==0)
                                            setPais("MÉXICO");
                                        else
                                            setPais((String)vRowTemp.get(11));
                                    
                                    setTelefono((String)vRowTemp.get(12));
                                    getSeg().setNumero((String)vRowTemp.get(13));
                                    getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                    getExpediente().setInserta(true);
                            }
                    }
                    return "/AbrirExpediente"; 
            }
//***************************************************************************************************************************************************
     public Paciente[] buscaTodoslosPacientes() throws Exception{
	Paciente arrRet[]=null, oPaciente=null; 
	ArrayList rst = null;
        ArrayList<Paciente> vObj=null;
	String sQuery = "";
        String edad="";
	int i=0, nTam=0;
         SimpleDateFormat df;
         String estadocivil;
         String tiposol,referencia;
	sQuery = "SELECT * FROM buscatodosLospacientes("+ isNull(getApPaterno().toUpperCase()) +","+ isNull(getApMaterno().toUpperCase())+","+ isNull(getNombres().toUpperCase())+","+numnull(getExpediente().getNumero())+");";
           System.out.println(sQuery); 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                          oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPaciente.setNombres((String)vRowTemp.get(1));
                            oPaciente.setApPaterno((String)vRowTemp.get(2));
                            oPaciente.setApMaterno((String)vRowTemp.get(3));
                            oPaciente.setFechaNac((Date) vRowTemp.get(4));
                             df=new SimpleDateFormat("dd/MM/yyyy");
                            oPaciente.setFechaNacTexto(df.format(oPaciente.getFechaNac()));
                            String sex=(String)vRowTemp.get(5);
                            if(sex.compareTo("M")==0){
                                oPaciente.setSexoP("MASCULINO");
                            }else{
                                oPaciente.setSexoP("FEMENINO");
                            } 
                               oPaciente.setEdadNumero((String)vRowTemp.get(6)); 
                            edad=(String)vRowTemp.get(6);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            }
                            oPaciente.setCurp((String)vRowTemp.get(7)); 
                            oPaciente.getMunicipio().setDescripcionMun((String)vRowTemp.get(8));
                            oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                            estadocivil=(String)vRowTemp.get(10);
                            if(estadocivil.compareTo("")!=0){
                            oPaciente.getEstadoCivil().setValor(oPaciente.getEstadoCivil().buscaValorParametrizado(estadocivil));
                             }
                            else{
                             oPaciente.getEstadoCivil().setValor("");
                              }
                            oPaciente.setTelefono((String)vRowTemp.get(11));
                            oPaciente.setPeso(((Double)vRowTemp.get(12)).floatValue());
                            oPaciente.setTalla(((Double)vRowTemp.get(13)).floatValue());
                            oPaciente.setCalleNum((String)vRowTemp.get(14));
                            oPaciente.setColonia((String)vRowTemp.get(15)); 
                             tiposol=(String)vRowTemp.get(16); 
                                if (tiposol.compareTo("")!=0){
                                oPaciente.getTipoSol().setValor(oPaciente.getTipoSol().buscaValorParametrizado(tiposol));
                                oPaciente.getTipoSol().setTipoParametro(tiposol.charAt(3)+""+tiposol.charAt(4));}
                                else{
                                    getTipoSol().setValor("");
                                    getTipoSol().setTipoParametro("");}
                             /// oPaciente.setDiscapacitado((boolean)vRowTemp.get(17));
                              oPaciente.setStatusPac((String)vRowTemp.get(18));
                              oPaciente.setFolioDefuncion((String)vRowTemp.get(19));
                            oPaciente.getFactorRH().setClaveParametro((String)vRowTemp.get(20));
                              oPaciente.oEstado.setClaveEdo((String)vRowTemp.get(21));
                              oPaciente.oEstado.buscar();
                              oPaciente.oMunicipio.setClaveMun((String)vRowTemp.get(22));
                              oPaciente.oMunicipio.buscar(oEstado.getClaveEdo());
                              oPaciente.oCiudad.setClaveCiu((String)vRowTemp.get(23));
                              oPaciente.oCiudad.buscar(oPaciente.oEstado.getClaveEdo(), oPaciente.oMunicipio.getClaveMun());
                              oPaciente.setCp((String)vRowTemp.get(24));
                              oPaciente.setPais((String)vRowTemp.get(25));
                              oPaciente.oReferencia.setClave((String)vRowTemp.get(26));
                              oPaciente.oReferencia.buscar();
                              ///oPaciente.setNacidoEnHospital((boolean)vRowTemp.get(27));
                              oPaciente.setLugarNacimiento((String)vRowTemp.get(34));
                              
                              
                            vObj.add(oPaciente); 
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }  
		}
                   
                
		return arrRet; 
	} 

    
    //***********************************************************/
    
     public boolean getDiscapacitado() {
	return bDiscapacitado;
	}

	public void setDiscapacitado(boolean valor) {
	bDiscapacitado=valor;
	}

	public boolean getNacidoEnHospital() {
	return bNacidoEnHospital;
	}

	public void setNacidoEnHospital(boolean valor) {
	bNacidoEnHospital=valor;
	}

	public String getStatusPac() {
	return bStatusPac;
	}

	public void setStatusPac(String valor) {
	bStatusPac=valor;
	}

	public Date getFechaRegistroNivelSoc() {
	return dFechaRegistroNivelSoc;
	}

	public void setFechaRegistroNivelSoc(Date valor) {
	dFechaRegistroNivelSoc=valor;
	}

	public Date getVigenciaNivelSoc() {
	return dVigenciaNivelSoc;
	}

	public void setVigenciaNivelSoc(Date valor) {
	dVigenciaNivelSoc=valor;
	}

	public float getPeso() {
	return nPeso;
	}
       
        
        
	public void setPeso(float valor) {
	nPeso=valor;
	}
      public float getGramo(){
      return nGramo;
      }
      public void setGramo(float valor){
      nGramo=valor;
      }

	public float getTalla() {
	return nTalla;
	}

	public void setTalla(float valor) {
	nTalla=valor;
	}

	public Parametrizacion getClaveEdad() {
	return oClaveEdad;
	}

	public void setClaveEdad(Parametrizacion valor) {
	oClaveEdad=valor;
	}

	public Parametrizacion getNivelSocioEco() {
	return oNivelSocioEco;
	}

	public void setNivelSocioEco(Parametrizacion valor) {
	oNivelSocioEco=valor;
	}

	public Parametrizacion getTipoSol() {
	return oTipoSol;
	}

	public void setTipoSol(Parametrizacion valor) {
	oTipoSol=valor;
	}
      
      public Parametrizacion getGrat(){
      return oGrat;
      }
      public void setGratuidad(Parametrizacion valor){
      oGrat=valor;
      }

	public String getCalleNum() {
	return sCalleNum;
	}

	public void setCalleNum(String valor) {
	sCalleNum=valor;
	}

	public String getColonia() {
	return sColonia;
	}

	public void setColonia(String valor) {
	sColonia=valor;
	}
      public String getNumExterior() {
        return sNumExterior;
    }

    public void setNumExterior( String sNumExterior ) {
        this.sNumExterior = sNumExterior;
    }

    public String getNumInterior() {
        return sNumInterior;
    }

    public void setNumInterior( String sNumInterior ) {
        this.sNumInterior = sNumInterior;
    }

	public String getFolioDefuncion() {
	return sFolioDefuncion;
	}

	public void setFolioDefuncion(String valor) {
	sFolioDefuncion=valor;
	}

	/*public String getGrupoRH() {
	return sGrupoRH;
	}

	public void setGrupoRH(String valor) {
	sGrupoRH=valor;
	}*/

	public ArrayList getrrEstudios() {
	return arrEstudios;
	}

	public void setrrEstudios(ArrayList valor) {
	arrEstudios=valor;
	}

	public ArrayList getrrSeguro() {
	return arrSeguro;
	}

	public void setrrSeguro(ArrayList valor) {
	arrSeguro=valor;
	}

	public Referencia getReferencia() {
	return oReferencia;
	}

	public void setReferencia(Referencia valor) {
	oReferencia=valor;
	}
//***************************************************************
	public Etnicidad getEtnicidad() {
	return oEtnicidad;
	}
      

	public void setEtnicidad(Etnicidad valor) {
	oEtnicidad=valor;
	}
//***************************************************************  
	public Ubicacion getUbicacion() {
	return oUbicacion;
	}

	public void setUbicacion(Ubicacion valor) {
	oUbicacion=valor;
	}

    public long getFolioPaciente() {
        return nFolioPaciente;
    }

    public void setFolioPaciente(long nFolioPaciente) {
        this.nFolioPaciente = nFolioPaciente;
    }

    public String getEdadNumero() {
        return sEdad;
    }

    public void setEdadNumero(String sEdad) {
        this.sEdad = sEdad;
    }

    /**
     * @return the oMunicipio
     */
    public Municipio getMunicipio() {
        return oMunicipio;
    }

    /**
     * @param oMunicipio the oMunicipio to set
     */

    public void setMunicipio(Municipio oMunicipio) {
        this.oMunicipio = oMunicipio;
    }

    /**
     * @return the oExpediente
     */

    public Expediente getExpediente() {
        return oExpediente;
    }

    /**
     * @param oExpediente the oExpediente to set
     */

    public void setExpediente(Expediente oExpediente) {
        this.oExpediente = oExpediente;
    }

    /**
     * @return the sPais
     */
    public String getPais() {
        return sPais;
    }

    /**
     * @param sPais the sPais to set
     */
    public void setPais(String sPais) {
        this.sPais = sPais;
    }

    /**
     * @return the sFechaNac
     */
    public String getsFechaNacTexto() {
        return sFechaNac;
    }

    /**
     * @param sFechaNac the sFechaNac to set
     */
    public void setsFechaNacTexto(String sFechaNac) {
        this.sFechaNac = sFechaNac;
    }

    public Seguro getSeg() {
        return oSeg;
    }
    
    public String getClaveEdadP() {
        return sClaveEdadP;
    }
    
    public void setClaveEdadP(String sClaveEdadP) {
        this.sClaveEdadP = sClaveEdadP;
    }
    
    public String getNacidoEnHospitalP() {
        return sNacidoEnHospitalP;
    }
    
    public void setNacidoEnHospitalP(String sNacidoEnHospitalP) {
        this.sNacidoEnHospitalP = sNacidoEnHospitalP;
    }
    
    public void setSeg(Seguro oSeg) {
        this.oSeg = oSeg;
    }

    public Estado getEstado() {
        return oEstado;
    }

    
    public void setEstado(Estado oEstado) {
        this.oEstado = oEstado;
    }
    public void setEstadoN(Estado oEstadoN){
    this.oEstadoN=oEstado;
    }
    public Estado getoEstadoN(){
    return oEstadoN;
    }

    public Ciudad getCiudad() {
        return oCiudad;
    }
    
    public void setCiudad(Ciudad oCiudad) {
        this.oCiudad = oCiudad;
    }

    public String getFechaNacTexto() {
        return sFechaNac;
    }

    public void setFechaNacTexto(String sFechaNac) {
        this.sFechaNac = sFechaNac;
    }

    public String getVigenciaTexto() {
        return sVigencia;
    }

    public void setVigenciaTexto(String sVigencia) {
        this.sVigencia = sVigencia;
    }

    public String getOtroPais() {
        return sOtroPais;
    }

    public void setOtroPais(String sOtroPais) {
        this.sOtroPais = sOtroPais;
    }

    public String getCodigoPos() {
        return codigoPos;
    }

    public void setCodigoPos(String codigoPos) throws Exception{
        this.codigoPos = codigoPos;
        buscaDatosPorCP();
    }
    //***************************set y get tipo de vialidad y asentamiento********************************************************
    public TipoAsentamiento getTipoAsentamiento(){
    return oTipoAsentamiento;
    }
    
    public void setTipoAsentamiento(TipoAsentamiento oTipoAsentamiento){
    this.oTipoAsentamiento= oTipoAsentamiento;
    }
    
    public TipoVialidad getTipoVialidad(){
    return oTipoVialidad;
    }
    
    public void setTipoVialidad(TipoVialidad oTipoVialidad){
    this.oTipoVialidad= oTipoVialidad;
    }
    
    
    public String getsNumExterior() {
	return sNumExterior;
	}

	public void setsNumExterior(String valor) {
	sNumExterior=valor;
	}
      
      public String getsNumInterior() {
	return sNumInterior;
	}

	public void setsNumInterior(String valor) {
	sNumInterior=valor;
	}
      public String getsTelefono(){
      return sTelefono;
      }
      public void setsTelefono(String valor){
      sTelefono=valor;
      }
    
    
    
    
    //***************************************************************************************************

    public void buscaDatosPorCP() throws Exception{
        Estado arr[]=oEstado.buscaEstadoCP(getCodigoPos());
        if (arr.length>0){
        oEstado.setClaveEdo(arr[0].getClaveEdo());
        Municipio arm[]=oMunicipio.buscaMunicipioCP(getCodigoPos(), oEstado.getClaveEdo());
        oMunicipio.setClaveMun(arm[0].getClaveMun());
        }
    }

	public String getEstatusPac(){
        if(bStatusPac==null) return "";
        if (bStatusPac.compareTo("1")==0)
            return "VIGENTE";
        else return "NO VIGENTE";
    }    
    public String reseteaBusqueda() throws Exception{
        setFolioPaciente(0);
        getExpediente().setNumero(0);
        setSexoP("M");
        setFechaNacTexto("00/00/0000");
        setTelefono("");
        setCalleNum("");
        setColonia("");
        try{setCodigoPos("");}catch(Exception e){}
        setOtroPais("MÉXICO");
        getEstado().setClaveEdo("30");
        getMunicipio().setClaveMun("118");
        getCiudad().setClaveCiu("0001");
        setVigenciaTexto("00/00/0000");
        getSeg().setNumero("");
        setFechaNac(null);
        getSeg().setVigencia(null);
        setCp("");
        return "RegistrarCita";
    }
    
    public String restauraExp() throws Exception{
        getExpediente().setNumero(0);
        return "CitasEncontradas";
    }
    
    //****************************************************************************************************************************************************
    //******************************AREA DE URGENCIAS**************************
    //**************************************************************************
    
    public Paciente[] buscarDiferentesUrgencias() throws Exception{
        Paciente p[];
        SimpleDateFormat df;
        cantEP=des=0;
        String sfecha="",exp="", aPaterno="", apMaterno="", nombres="";
        if (getOpcionUrg()==0){
            aPaterno=getApPaterno();
            apMaterno=getApMaterno();
            nombres=getNombres();
            exp="null";
            sfecha="null";
            p=buscarPacUrgencias(aPaterno, apMaterno, nombres, exp);
        }else{
            if (getOpcionUrg()==1){
                aPaterno="";
                apMaterno="";
                nombres="";
                exp=oExpediente.getNumero()+"";
                sfecha="null";
                p=buscarPacUrgencias(aPaterno, apMaterno, nombres, exp);
            }else{
                aPaterno="";
                apMaterno="";
                nombres="";
                exp="null";
                df=new SimpleDateFormat("yyyy-MM-dd");
                sfecha="'"+df.format(getFechaConsulta())+"%'";
                p=buscarUrgFecha(sfecha);
            }
        }
        return p;
    }
    //********************************************************************************************************
    //busca pacinte de urgencias******************************************************************************
    //*******************************************************************************************************
    
    
    public Paciente[] buscarPacUrgencias(String aPaterno, String apMaterno, String nombres, String exp) throws Exception{
	Paciente arrRet[]=null, oPaciente=null;
	ArrayList rst = null;
        ArrayList<Paciente> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df;
		sQuery = "SELECT * FROM buscaPacUrgDatos('"+aPaterno+"','"+apMaterno+"','"+nombres+"',"+exp+");";
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                            oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPaciente.setApPaterno((String)vRowTemp.get(1));
                            oPaciente.setApMaterno((String)vRowTemp.get(2));
                            oPaciente.setNombres((String)vRowTemp.get(3));
                            oPaciente.setFechaNac((Date) vRowTemp.get(4));
                            df=new SimpleDateFormat("dd/MM/yyyy");
                            oPaciente.setFechaNacTexto(df.format(oPaciente.getFechaNac()));
                            buscarEPUrgencias(oPaciente);
                            vObj.add(oPaciente);
                            if (des==0){
                            cantEP=0;
                            }else{
                                i--;
                            }
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
                
		return arrRet; 
	}         
//****************************************************************************************************************************************************
     public void buscarEPUrgencias(Paciente oPac) throws Exception{
	ArrayList rst = null;
	String sQuery = "";
	int i=0, nTam=0;
        int episodio=0;

		sQuery = "SELECT * FROM buscarEPUrgencias("+oPac.getFolioPaciente()+");";
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
                        if (cantEP<rst.size()-1)
                            des=1;
                        else
                            des=0;
		if (rst != null && rst.size()>0) {
                            ArrayList vRowTemp = (ArrayList)rst.get(cantEP);                         
                            oPac.setFechaConsulta((Date)vRowTemp.get(0));
                            episodio=((Double) vRowTemp.get(1)).intValue();
                            oPac.getExpediente().setNumero(((Double) vRowTemp.get(2)).intValue());
                            oPac.getDiagcie().setClave((String)vRowTemp.get(3));
                            oPac.getDiagcie().setDescripcionDiag((String)vRowTemp.get(4));
                            buscarFolioUrgencias(oPac, episodio);
                            cantEP++;
		} 
                
	}         
//****************************************************************************************************************************************************
     public void buscarFolioUrgencias(Paciente oPac, int episodio) throws Exception{
	ArrayList rst = null;
	String sQuery = "";
	int i=0, nTam=0;

		sQuery = "SELECT * FROM buscarFolioUrgencias("+episodio+");";
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}

		if (rst != null) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);                         
                            oPac.setAdmUrg(((Double)vRowTemp.get(0)).intValue());                 
		} 
                
	}         
//*******************************************************************************************************
    public Paciente[] buscarUrgFecha(String fecha) throws Exception{
	Paciente arrRet[]=null, oPaciente=null;
	ArrayList rst = null;
        ArrayList<Paciente> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        int episodio=0;
        SimpleDateFormat df;
		sQuery = "SELECT * FROM buscarurgfecha("+fecha+");";
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                            oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPaciente.setFechaConsulta((Date)vRowTemp.get(1));
                            episodio=((Double)vRowTemp.get(2)).intValue();
                            oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                            oPaciente.getDiagcie().setClave((String)vRowTemp.get(4));
                            oPaciente.getDiagcie().setDescripcionDiag((String)vRowTemp.get(5));
                            buscaDatosPacFecha(oPaciente, episodio);
                            vObj.add(oPaciente);
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
                
		return arrRet; 
	}
    //**************************************************************************
    public void buscaDatosPacFecha(Paciente oPac, int episodio) throws Exception{
	ArrayList rst = null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df;

		sQuery = "SELECT * FROM buscaDatosPacFecha("+oPac.getFolioPaciente()+", "+episodio+");";
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}

		if (rst != null) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            oPac.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPac.setApPaterno((String)vRowTemp.get(1));
                            oPac.setApMaterno((String)vRowTemp.get(2));
                            oPac.setNombres((String)vRowTemp.get(3));
                            oPac.setFechaNac((Date) vRowTemp.get(4));
                            df=new SimpleDateFormat("dd/MM/yyyy");
                            oPac.setFechaNacTexto(df.format(oPac.getFechaNac()));
                            oPac.setAdmUrg(((Double)vRowTemp.get(5)).intValue());                 
		} 
                
	}
//MODIFICADO POR DANIEL HERNANDEZ SANCHEZ TERCERA FASE
    public int insertarExpediente(String lugarAper) throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery, aux, aux2, aux3="", auxCurp="";
         if( this==null){   //completar llave
            throw new Exception("Expediente.insercion: error de programación, faltan datos");
        }else{ 
                if(getExpediente().getPrograma().getClave()==0)
                    aux="null";
                else 
                    aux=String.valueOf(getExpediente().getPrograma().getClave());

               if(getExpediente().getNomArchINE()==null)
                   aux2="null";
                else
                   aux2="'"+getExpediente().getNomArchINE()+"'";

                if (getSeg().getNomArchPOLIZA()==null)
                    aux3="null";
                else 
                    aux3="'"+getSeg().getNomArchPOLIZA()+"'";  
                if(getCurp()==null)
                    auxCurp="null";
                else
                    auxCurp="'"+getCurp()+"'";
                    
            if(lugarAper.equals("TAJ1")){//ARCHIVO
                sQuery = "SELECT insertaExpediente('"+sUsuario+"',"+getExpediente().getNumero()+","+aux+","+getExpediente().getServicioIngreso().getClave()+"::smallint,'"+getTelefono()+"',"+getFolioPaciente()+"::bigint"+",'TAJ1',"+aux2+","+aux3+","+auxCurp+");";
                System.out.println(sQuery);
            }else{//URGENCIAS
                sQuery = "SELECT * FROM insertaexpedienteotraarea('"+sUsuario+"',"+getExpediente().getNumero2()+","+aux+","+getExpediente().getServicioIngreso().getClave()+"::smallint,'"+getTelefono()+"',"+getFolioPaciente()+"::bigint"+",'TAJ2',"+auxCurp+");";
                System.out.println(sQuery);
                }            
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet = ((Double)vRowTemp.get(0)).intValue();
                                      //  System.out.println("si entra a ingresar: "+nRet);
                }
            }
        }
        return nRet; 
    }
    
    public void buscaSeguroReciente() throws Exception{
   	ArrayList rst = null;
	String sQuery = "";  
        
        sQuery = "SELECT * FROM buscaseguropopularvigente("+this.getFolioPaciente()+" ::BIGINT);";
                System.out.println(sQuery);
                oAD=new AccesoDatos();
                this.setSeg(new Seguro());
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) {
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        this.getSeg().setNumero((String)vRowTemp.get(1));
                        this.getSeg().setVigencia((Date)vRowTemp.get(2));
                }
               
 }
 
//*******************************************************************************************************
        
    public int getOpcionUrg() {
        return nOpcionUrg;
    }

    public void setOpcionUrg(int nOpcionUrg) {
        this.nOpcionUrg = nOpcionUrg;
    }
    
    public String OpcionPacUrgs(int n){
        String pag="PacientesUrgencias";
        setOpcionUrg(n);
        return pag;
    }

    public Date getFechaConsulta() {
        return dFechaConsulta;
    }

    public void setFechaConsulta(Date dFechaConsulta) {
        this.dFechaConsulta = dFechaConsulta;
    }

    public int getAdmUrg() {
        return nAdmUrg;
    }

    public void setAdmUrg(int nAdmUrg) {
        this.nAdmUrg = nAdmUrg;
    }

    public DiagnosticoCIE10 getDiagcie() {
        return oDiagcie;
    }

    public void setDiagcie(DiagnosticoCIE10 oDiagcie) {
        this.oDiagcie = oDiagcie;
    }
    
    public String fechaConsultaTexto(){
        String f="";
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
        if (getFechaConsulta()!=null)
            f=df.format(getFechaConsulta());
        return f;
    }
    //****************************************************************************************************************************************************
    public Paciente[] buscarTodoPac() throws Exception{
	Paciente arrRet[]=null, oPaciente=null;
	ArrayList rst = null;
        ArrayList<Paciente> vObj=null;
	String sQuery = "";
        String edad="";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
        String aPaterno, aMaterno, nombre, numExp;
        if (getOpcionUrg()==0){
            aPaterno=getApPaterno();
            aMaterno=getApMaterno();
            nombre=getNombres();
            numExp="null";
        }else{
            numExp=oExpediente.getNumero()+"";
            aPaterno=aMaterno=nombre="";
        }
                if (numExp.compareTo("null")!=0)
                    sQuery = "SELECT * FROM buscarTodoPac('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+Integer.parseInt(numExp)+");";
                else
                    sQuery = "SELECT * FROM buscarTodoPac('"+aPaterno+"','"+aMaterno+"','"+nombre+"',"+numExp+");"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                            oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                            oPaciente.setApPaterno((String)vRowTemp.get(1));
                            oPaciente.setApMaterno((String)vRowTemp.get(2));
                            oPaciente.setNombres((String)vRowTemp.get(3));
                            oPaciente.setFechaNac((Date) vRowTemp.get(4));
                            if (oPaciente.getFechaNac()!=null)
                            oPaciente.setFechaNacTexto(df.format(oPaciente.getFechaNac()));
                            else
                                oPaciente.setFechaNacTexto("00/00/0000");
                            oPaciente.setSexoP((String)vRowTemp.get(5));
                            oPaciente.setCalleNum((String)vRowTemp.get(6));
                            if (((String)vRowTemp.get(7)).compareTo("")==0){
                                oPaciente.setOtroPais("MÉXICO");}
                            else{
                            oPaciente.setOtroPais((String)vRowTemp.get(7));}
                            oPaciente.getEstado().setClaveEdo((String)vRowTemp.get(8));
                            oPaciente.getMunicipio().setClaveMun((String)vRowTemp.get(9));
                            oPaciente.getCiudad().setClaveCiu((String)vRowTemp.get(10));
                            oPaciente.getEstado().buscar();
                            oPaciente.getMunicipio().buscar(oPaciente.getEstado().getClaveEdo());
                            oPaciente.getCiudad().buscar(oPaciente.getEstado().getClaveEdo(), oPaciente.getMunicipio().getClaveMun());
                            oPaciente.getExpediente().setNumero(oPaciente.getExpediente().buscaExpPac(oPaciente.getFolioPaciente()));
                            vObj.add(oPaciente);
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
                
		return arrRet; 
	}
	
/*METODO MODIFICADO POR DANIEL HERNANDEZ SANCHEZ 3 FASE*/	
    public String buscarPacExp() throws Exception{
       // resetea();
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        
        String v[];
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{  
			sQuery = "SELECT * FROM buscaPacExp("+getFolioPaciente()+"::bigint,"+getExpediente().getNumero()+");";
			System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setFolioPaciente(((Double)vRowTemp.get(0)).longValue());                                       
                                setApPaterno((String) vRowTemp.get( 1 ));
                                setApMaterno((String) vRowTemp.get( 2 ));
                                setNombres((String) vRowTemp.get( 3 ));
                                setSexoP((String)vRowTemp.get(4));
                                if (vRowTemp.get(5)!=null)
									v = vRowTemp.get(5).toString().split("-");
                                else
                                    v = "0000/00/00".split("/");
                                setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                setFechaNac((Date)vRowTemp.get(5));
                                setTelefono((String)vRowTemp.get(6));
                                setCalleNum((String)vRowTemp.get(7));
                                setColonia((String)vRowTemp.get(8));
                                setCp((String)vRowTemp.get(9));
                                setCodigoPos((String)vRowTemp.get(9));
                                oEstado.setClaveEdo((String) vRowTemp.get( 10 ));
                                oEstado.buscar();
                                oMunicipio.setClaveMun((String) vRowTemp.get( 11 ));
                                oMunicipio.buscar(oEstado.getClaveEdo());
                                oCiudad.setClaveCiu((String) vRowTemp.get( 12 ));
                                oCiudad.buscar(oEstado.getClaveEdo(), oMunicipio.getClaveMun());
                                setEdadNumero((String)vRowTemp.get(13));
                                setOtroPais((String)vRowTemp.get(14));
                                if (getOtroPais().compareToIgnoreCase("")==0)
                                    setOtroPais("MÉXICO");
                                setStatusPac((String)vRowTemp.get(15));
                                String x=(String)vRowTemp.get(16);
                                if (x.compareTo("")!=0){
                                getTipoSol().setValor(getTipoSol().buscaValorParametrizado(x));
                                getTipoSol().setTipoParametro(x.charAt(3)+""+x.charAt(4));}
                                else{
                                    getTipoSol().setValor("");
                                    getTipoSol().setTipoParametro("");}
                                getReferencia().setClave((String)vRowTemp.get(17));
                                getReferencia().buscar();
                                buscasegpop(getFolioPaciente());
                                oExpediente.setNumero(((Double)vRowTemp.get(18)).intValue());
                                oExpediente.setNumeroAux(((Double)vRowTemp.get(18)).intValue());
                                oExpediente.setPrograma(new Programa());
                                oExpediente.getPrograma().setClave(((Double)vRowTemp.get(19)).intValue());
                                oExpediente.getPrograma().buscaDescripcionPorClave(oExpediente.getPrograma().getClave());
                                oExpediente.getServicioIngreso().setClave(((Double)vRowTemp.get(20)).intValue());
                                oExpediente.setStatusExpediente((String)vRowTemp.get(21));
				bRet = true;
			}
		}
		 return "ModificarDatosExpediente";
	}    
	//*******************************************************************************
    public int modificarPacArchivo() throws Exception{
		ArrayList rst = null;
		int nRet = 0;
		String sQuery = "", auxStatus="";
			if (getVigenciaTexto()==null || getVigenciaTexto().compareTo("")==0)
				setVigenciaTexto("00/00/0000");
			if (getFechaNacTexto()==null || getFechaNacTexto().compareTo("")==0)
				setFechaNacTexto("00/00/0000");
			String v[]=getFechaNacTexto().split("/");
			String v2[]=getVigenciaTexto().split("/");
			SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
			
			fecha = null;
			String edo="", mun="", ciu="";
			try { if (getFechaNacTexto().compareTo("00/00/0000")!=0){
				setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0]));
			}else{ 
			setFechaNac(null);
			}
			if (v2!=null){
				getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
			}} catch (Exception ex) {ex.printStackTrace();}
			 if( this==null){   //completar llave
				throw new Exception("Paciente.modificar: error de programación, faltan datos");
			}else{ 
							String cpAux="";
							String vigenciaAux="'";
							String aposFecha="'";
							String ref="";
							String exp="", prog="", colonia="", aux2="", aux3="";
							if (getReferencia().getClave().compareTo("")==0){ref="null";}else{ref="'"+getReferencia().getClave()+"'";}
							if (getVigenciaTexto().compareTo("")==0 || getVigenciaTexto().compareTo("00/00/0000")==0){getSeg().setVigencia(null); vigenciaAux="";}
							 if (getFechaNac()==null){ aposFecha="";}
							if (getCodigoPos().compareTo("")==0){cpAux="'99999'";}else {cpAux="'"+getCodigoPos()+"'";}
							if (getOtroPais().compareToIgnoreCase("MÉXICO")!=0 && getOtroPais().compareToIgnoreCase("MEXICO")!=0){edo="'99'"; mun="'999'"; ciu="'9999'"; setOtroPais("'"+getOtroPais()+"'");}else{edo="'"+getEstado().getClaveEdo()+"'"; mun="'"+getMunicipio().getClaveMun()+"'"; ciu="'"+getCiudad().getClaveCiu()+"'"; setOtroPais("null");}
				//System.out.println("modificapacArchivo "+getExpediente().getNumero());
							if(getExpediente().getNumero()!=0)
								exp=""+getExpediente().getNumero();
							else
								exp="null";
							if(getExpediente().getPrograma().getClave()==0)
								prog="null";
							else
								prog=String.valueOf(getExpediente().getPrograma().getClave());
							if(getColonia().compareTo("")==0)
								colonia="null";
							else 
								colonia="'"+getColonia()+"'";
							
							if(getExpediente().getNomArchINE()==null)
								aux2="null";
							 else
								aux2="'"+getExpediente().getNomArchINE()+"'";

							 if (getSeg().getNomArchPOLIZA()==null)
								 aux3="null";
							 else 
								 aux3="'"+getSeg().getNomArchPOLIZA()+"'"; 
							
                                                         if(getExpediente().getStatusExpediente().equals("ACTIVO")) 
                                                             auxStatus="1";
                                                         else if(getExpediente().getStatusExpediente().equals("PASIVO"))
                                                             auxStatus="0";
                                                         else if(getExpediente().getStatusExpediente().equals("EN ARCHIVO MUERTO"))
                                                             auxStatus="2";
                                                         else
                                                             auxStatus="3";//depurado
							sQuery = "SELECT * FROM modificarPacArchivo('"+sUsuario+"', "+getFolioPaciente()+"::bigint, '"+getNombres()+"', '"+getApPaterno()+"', '"+getApMaterno()+"', '"+getSexoP()+"', "+aposFecha+getFechaNac()+aposFecha+", '"+getTelefono()+"', '"+getCalleNum()+"',"+colonia+","+cpAux+", "+getOtroPais().toUpperCase()+","+edo+", "+mun+", "+ciu+", '"+getSeg().getNumero()+"', "+vigenciaAux+getSeg().getVigencia()+vigenciaAux+","+exp+","+prog+"::integer,"+getExpediente().getServicioIngreso().getClave()+"::smallint,'"+auxStatus+"',"+getExpediente().getNumeroAux()+","+aux2+","+aux3+");";
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
//**************************************************************************************
//METODO PARA BUSCAR PACIENTE DE HOJA DE LESIONES MU************************************
//**************************************************************************************
	public String buscarPacLesionNueva() throws Exception{
        resetea();
        
	boolean bRet = false;
        String edad;
	ArrayList rst = null;
	String sQuery = "";
        FacesMessage message=null;
        
        String v[];
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{  
			sQuery = "SELECT * FROM buscaDatosLesionNueva("+getFolioPaciente()+"::bigint);";
			System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);                               
                                setApPaterno((String) vRowTemp.get( 1 ));
                                setApMaterno((String) vRowTemp.get( 2 ));
                                setNombres((String) vRowTemp.get( 0 ));
                                setSexoP((String)vRowTemp.get(3));
                                setCurp((String)vRowTemp.get(4));
                                if (vRowTemp.get(5)!=null)
                                v=vRowTemp.get(5).toString().split("-");
                                else
                                    v="0000/00/00".split("/");
                                setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                setFechaNac((Date)vRowTemp.get(5));
                                getSeg().setUnaDer(((String)vRowTemp.get(7)).substring(3, 5));
				getSeg().setNumero((String)vRowTemp.get(6));
                                
                                getReferencia().setClave((String)vRowTemp.get(8));
                                getReferencia().setDescripcion((String)vRowTemp.get(9));
                                getReferencia().setTipo((String)vRowTemp.get(10));
                                getExpediente().setNumero(((Double)vRowTemp.get(11)).intValue());
                                setEdadNumero((String)vRowTemp.get(12));
                                 edad=(String)vRowTemp.get(12);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            }
                            getEtnicidad().setPertenenciaGpoIndStr((String)vRowTemp.get(13));
                            getEtnicidad().setHablaLenguaIndStr((String)vRowTemp.get(14));
                            getEtnicidad().setClaveLengua((String)vRowTemp.get(15));
                            
				bRet = true;
			}
		}
                if (bRet==true){
                    return "urgencias/LlenarHojaLesion.xhtml";
                }
                else{
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Llenar Hoja Lesiones", "El Paciente ya ha sido de alta o su hoja de lesiones ya fue generada ");
                    RequestContext.getCurrentInstance().showMessageInDialog(message);
		    return "Inicio.xhtml";
                }
	}
	
    public Paciente[] buscaSinAltaFisica() throws Exception{
    Paciente arrRet[]=null; 
        if(this.buscaPaciente().length>0){   
           arrRet=buscaPaciente();
        }else{
          arrRet=buscaPaciAltaHospit();
        }
        return arrRet;
    }
    
    public Paciente[] buscaPaciente() throws Exception{
	Paciente arrRet[]=null, oPaciente=null; 
	ArrayList rst = null;
        ArrayList<Paciente> vObj=null;
	String sQuery = "";
        String edad="";
	int i=0, nTam=0;
         SimpleDateFormat df;
         String estadocivil;
         String tiposol,referencia;
	sQuery = "SELECT * FROM buscapacientetrabajosocial("+ 
                isNull(getApPaterno().toUpperCase()) +","+ 
                isNull(getApMaterno().toUpperCase())+","+ 
                isNull(getNombres().toUpperCase())+","+numnull(getExpediente().getNumero())+");";
           System.out.println(sQuery); 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                          oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPaciente.setNombres((String)vRowTemp.get(1));
                            oPaciente.setApPaterno((String)vRowTemp.get(2));
                            oPaciente.setApMaterno((String)vRowTemp.get(3));
                            oPaciente.setFechaNac((Date) vRowTemp.get(4));
                             df=new SimpleDateFormat("dd/MM/yyyy");
                            oPaciente.setFechaNacTexto(df.format(oPaciente.getFechaNac()));
                            String sex=(String)vRowTemp.get(5);
                            if(sex.compareTo("M")==0){
                                oPaciente.setSexoP("MASCULINO");
                            }else{
                                oPaciente.setSexoP("FEMENINO");
                            } 
                               oPaciente.setEdadNumero((String)vRowTemp.get(6)); 
                            edad=(String)vRowTemp.get(6);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            }
                            oPaciente.setCurp((String)vRowTemp.get(7)); 
                            oPaciente.getMunicipio().setDescripcionMun((String)vRowTemp.get(8));
                            oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                            estadocivil=(String)vRowTemp.get(10);
                            if(estadocivil.compareTo("")!=0){
                            oPaciente.getEstadoCivil().setValor(oPaciente.getEstadoCivil().buscaValorParametrizado(estadocivil));
                             }
                            else{
                             oPaciente.getEstadoCivil().setValor("");
                              }
                            oPaciente.setTelefono((String)vRowTemp.get(11));
                            oPaciente.setPeso(((Double)vRowTemp.get(12)).floatValue());
                            oPaciente.setTalla(((Double)vRowTemp.get(13)).floatValue());
                            oPaciente.setCalleNum((String)vRowTemp.get(14));
                            oPaciente.setColonia((String)vRowTemp.get(15)); 
                             tiposol=(String)vRowTemp.get(16); 
                                if (tiposol.compareTo("")!=0){
                                oPaciente.getTipoSol().setValor(oPaciente.getTipoSol().buscaValorParametrizado(tiposol));
                                oPaciente.getTipoSol().setTipoParametro(tiposol.charAt(3)+""+tiposol.charAt(4));}
                                else{
                                    getTipoSol().setValor("");
                                    getTipoSol().setTipoParametro("");}
                             /// oPaciente.setDiscapacitado((boolean)vRowTemp.get(17));
                              oPaciente.setStatusPac((String)vRowTemp.get(18));
                              oPaciente.setFolioDefuncion((String)vRowTemp.get(19));
                              oPaciente.getFactorRH().setClaveParametro((String)vRowTemp.get(20));
                              oPaciente.oEstado.setClaveEdo((String)vRowTemp.get(21));
                              oPaciente.oEstado.buscar();
                              oPaciente.oMunicipio.setClaveMun((String)vRowTemp.get(22));
                              oPaciente.oMunicipio.buscar(oEstado.getClaveEdo());
                              oPaciente.oCiudad.setClaveCiu((String)vRowTemp.get(23));
                              oPaciente.oCiudad.buscar(oPaciente.oEstado.getClaveEdo(), oPaciente.oMunicipio.getClaveMun());
                              oPaciente.setCp((String)vRowTemp.get(24));
                              oPaciente.setPais((String)vRowTemp.get(25));
                              oPaciente.oReferencia.setClave((String)vRowTemp.get(26));
                              oPaciente.oReferencia.buscar();
                              ///oPaciente.setNacidoEnHospital((boolean)vRowTemp.get(27));
                              oPaciente.setLugarNacimiento((String)vRowTemp.get(34));
                              
                              
                            vObj.add(oPaciente); 
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }  
		}
                   
                
		return arrRet; 
	} 

    
    public Paciente[] buscaPaciAltaHospit() throws Exception{
    Paciente arrRet[]=null, oPaciente=null; 
    ArrayList rst = null;
    ArrayList<Paciente> vObj=null;
    String sQuery = "";
    String edad="";
    int i=0, nTam=0;
    SimpleDateFormat df;
    String estadocivil;
    String tiposol,referencia;
	sQuery = "SELECT * FROM buscapacientealtamedica("+ isNull(getApPaterno().toUpperCase()) +","+ isNull(getApMaterno().toUpperCase())+","+ isNull(getNombres().toUpperCase())+","+numnull(getExpediente().getNumero())+");";
        System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Paciente>();
			for (i = 0; i < rst.size(); i++) {
                          oPaciente = new Paciente();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPaciente.setFolioPaciente(((Double) vRowTemp.get( 0 )).longValue());
                            oPaciente.setNombres((String)vRowTemp.get(1));
                            oPaciente.setApPaterno((String)vRowTemp.get(2));
                            oPaciente.setApMaterno((String)vRowTemp.get(3));
                            oPaciente.setFechaNac((Date) vRowTemp.get(4));
                             df=new SimpleDateFormat("dd/MM/yyyy");
                            oPaciente.setFechaNacTexto(df.format(oPaciente.getFechaNac()));
                            String sex=(String)vRowTemp.get(5);
                            if(sex.compareTo("M")==0){
                                oPaciente.setSexoP("MASCULINO");
                            }else{
                                oPaciente.setSexoP("FEMENINO");
                            } 
                               oPaciente.setEdadNumero((String)vRowTemp.get(6)); 
                            edad=(String)vRowTemp.get(6);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                    oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                    oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            }
                            oPaciente.setCurp((String)vRowTemp.get(7)); 
                            oPaciente.getMunicipio().setDescripcionMun((String)vRowTemp.get(8));
                            oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                            estadocivil=(String)vRowTemp.get(10);
                            if(estadocivil.compareTo("")!=0){
                            oPaciente.getEstadoCivil().setValor(oPaciente.getEstadoCivil().buscaValorParametrizado(estadocivil));
                             }
                            else{
                             oPaciente.getEstadoCivil().setValor("");
                              }
                            oPaciente.setTelefono((String)vRowTemp.get(11));
                            oPaciente.setPeso(((Double)vRowTemp.get(12)).floatValue());
                            oPaciente.setTalla(((Double)vRowTemp.get(13)).floatValue());
                            oPaciente.setCalleNum((String)vRowTemp.get(14));
                            oPaciente.setColonia((String)vRowTemp.get(15)); 
                             tiposol=(String)vRowTemp.get(16); 
                                if (tiposol.compareTo("")!=0){
                                oPaciente.getTipoSol().setValor(oPaciente.getTipoSol().buscaValorParametrizado(tiposol));
                                oPaciente.getTipoSol().setTipoParametro(tiposol.charAt(3)+""+tiposol.charAt(4));}
                                else{
                                    getTipoSol().setValor("");
                                    getTipoSol().setTipoParametro("");}
                             /// oPaciente.setDiscapacitado((boolean)vRowTemp.get(17));
                              oPaciente.setStatusPac((String)vRowTemp.get(18));
                              oPaciente.setFolioDefuncion((String)vRowTemp.get(19));
                              
                              //oPaciente.getFactorRH()((String)vRowTemp.get(20));
                              oPaciente.oEstado.setClaveEdo((String)vRowTemp.get(21));
                              oPaciente.oEstado.buscar();
                              oPaciente.oMunicipio.setClaveMun((String)vRowTemp.get(22));
                              oPaciente.oMunicipio.buscar(oEstado.getClaveEdo());
                              oPaciente.oCiudad.setClaveCiu((String)vRowTemp.get(23));
                              oPaciente.oCiudad.buscar(oPaciente.oEstado.getClaveEdo(), oPaciente.oMunicipio.getClaveMun());
                              oPaciente.setCp((String)vRowTemp.get(24));
                              oPaciente.setPais((String)vRowTemp.get(25));
                              oPaciente.oReferencia.setClave((String)vRowTemp.get(26));
                              oPaciente.oReferencia.buscar();
                              ///oPaciente.setNacidoEnHospital((boolean)vRowTemp.get(27));
                            vObj.add(oPaciente); 
			}
                    nTam = vObj.size();
                    arrRet = new Paciente[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                        System.out.println(arrRet);
                    }  
		}
                   
                
		return arrRet; 
	} 
    
    //MÉTODO 3ERA FASE PARA INSERTAR PACIENTE - EXPEDIENTE AL MISMO TIEMPO
    public int insertarPacienteConExpediente() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String v2[]=getFechaNacTexto().split("/");
        String v[];
        String numAux="";
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        
        switch(getSeg().getUnaDer().charAt(1)){
            case '0': getSeg().setVigencia(null);break;
            case '9': getSeg().setVigencia(null);break;
            case '1': {v2=getSeg().getVigenciaTexto().split("/");
                       try { 
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '2': {v2=getSeg().getVigenciaTexto2().split("/");
                       try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '3': {v2=getSeg().getVigenciaTexto3().split("/");
                       try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '4': {v2=getSeg().getVigenciaTexto4().split("/");
                       try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '5': {v2=getSeg().getVigenciaTexto5().split("/");
                       try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '6': {v2=getSeg().getVigenciaTexto6().split("/");
                       try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '7': {v2=getSeg().getVigenciaTexto7().split("/");
                      try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '8': {v2=getSeg().getVigenciaTexto8().split("/");
                       try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'G': {v2=getSeg().getVigenciaTextoG().split("/");
                      try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'P': {v2=getSeg().getVigenciaTextoP().split("/");
                      try {
                           getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}}break;
        }
        switch(getSeg().getUnaDer().charAt(1)){
              case '1': numAux=getSeg().getNumero();break;
              case '2': numAux=getSeg().getNumero2();break;
              case '3': numAux=getSeg().getNumero3();break;
              case '4': numAux=getSeg().getNumero4();break;
              case '5': numAux=getSeg().getNumero5();break;
              case '6': numAux=getSeg().getNumero6();break;
              case '7': numAux=getSeg().getNumero7();break;
              case '8': numAux=getSeg().getNumero8();break;
              case 'G': numAux=getSeg().getNumeroG();break;
              case 'P': numAux=getSeg().getNumeroP();break;
        }
        if( getExpediente().getNumero()==0){
            throw new Exception("Paciente.insertarPacienteConExpediente: error de programación, faltan datos");
	}else{
            String fechaAdm="", cpAux="", curp="", apmat="", vigencia="", pais="", edo="", mun="", ciu="", colonia="", edoCivil="", numseg="", tel="", prog="";
            //DATOS PACIENTE
            apmat = getApMaterno().compareTo("")==0 ? "NULL" : "'"+getApMaterno()+"'" ;
            if(getPais().compareTo("MÉXICO")==0 || getPais().compareTo("MEXICO")==0){
                cpAux = getCiudadCP().getCp().compareTo("")==0 ? "NULL" : "'"+getCiudadCP().getCp()+"'";
                edo="'"+getEstado().getClaveEdo()+"'";
                mun="'"+getMunicipio().getClaveMun()+"'";
                ciu="'"+getCiudad().getClaveCiu()+"'";
            } else {
                cpAux="NULL";
                edo="'99'";
                mun="'999'";
                ciu="'9999'"; 
            }
            colonia = getColonia().compareTo("")==0 ? "NULL" : "'"+getColonia()+"'" ;
            if(getCiudadCP().getCp()==null)
                cpAux="NULL";
            else
                cpAux = getCiudadCP().getCp().compareTo("")==0 ? "NULL" : "'"+getCiudadCP().getCp()+"'";
            curp = getCurp().compareTo("")==0 ? "null" : "'"+getCurp()+"'" ;
            numseg = numAux.compareTo("")==0 ? "null" :  "'"+numAux+"'"; 
            vigencia = getSeg().getVigencia()==null ? "null" : "'"+getSeg().getVigenciaTexto()+"'";
            if(getPais().compareTo("MEXICO")==0 || getPais().compareTo("MÉXICO")==0)
                pais="null";
            else
                pais="'"+getPais()+"'";
            edoCivil = getEdoCivilStr()==null ? "NULL" : "'"+getEdoCivilStr()+"'";
            tel = getTelefono().compareTo("")==0 ? "NULL" : "'"+getTelefono()+"'";
            prog = getExpediente().getPrograma().getClave()==0 ? "NULL" : ""+getExpediente().getPrograma().getClave();

            sQuery = "SELECT RegistrarPacienteConExpediente('"+sUsuario+"', '"+getNombres()+"','"+getApPaterno()+"',"+apmat+", '"+getFechaNacTexto().substring(6,10)+"-"+getFechaNacTexto().substring(3,5)+"-"+getFechaNacTexto().substring(0,2)+"', '"+getSexoP()+"', "+curp+", "+edoCivil+", '"+getCalleNum()+"',"+colonia+","+1+"::CHAR,"+edo+", "
                        +mun+", "+ciu+","+cpAux+","+pais+","+numseg+","+vigencia+"::DATE,'T01"+getSeg().getUnaDer()+"',"+getExpediente().getNumero()+"::INTEGER, "+tel+", "+prog+"::INTEGER, "+getExpediente().getServicioIngreso().getClave()+"::SMALLINT);";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar(); 
                    if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            nRet = ((Double)vRowTemp.get(0)).intValue();
                            System.out.println("si entra a ingresar: "+nRet);
                    }
            }
        }
        return nRet; 
    }
     

    public Parametrizacion getParametrizacion() {
        return oParametrizacion;
    }

    public void setParametrizacion(Parametrizacion oParametrizacion) {
        this.oParametrizacion = oParametrizacion;
    }

    public Seguro getSeguro() {
        return oSeguro;
    }

    public void setSeguro(Seguro oSeguro) {
        this.oSeguro = oSeguro;
    }

    public CiudadCP getCiudadCP() {
        return oCiudadCP;
    }

    public void setCiudadCP(CiudadCP oCiudadCP) {
        this.oCiudadCP = oCiudadCP;
    }

    public String getVigenciaNivelSTR() {
        return VigenciaNivelSTR;
    }

    public void setVigenciaNivelSTR(String VigenciaNivelSTR) {
        this.VigenciaNivelSTR = VigenciaNivelSTR;
    }

    public String getFechaStr() {
        return FechaStr;
    }

    public void setFechaStr(String FechaStr) {
        this.FechaStr = FechaStr;
    }

    public String getFechaSistema() {
        return fechaSistema;
    }

    public void setFechaSistema(String fechaSistema) {
        this.fechaSistema = fechaSistema;
    }

    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    public long getHospitalizado() {
        return nHospitalizado;
    }

    public void setHospitalizado(long nHospitalizado) {
        this.nHospitalizado = nHospitalizado;
    }
    
    
     public String getHablaLengua(){
        return sHablaSpa;
    }
    public void setHablaLengua(String hsp){
        this.sHablaSpa=hsp;
    }
    public String getLugarNacimiento(){
        return sLugarNacimiento;
    }
    public void setLugarNacimiento(String sLugarNacimiento){
        this.sLugarNacimiento = sLugarNacimiento;
    }
    
   public String getGratuidad(){
   return sGrat;
   }
   public void setGratuidad(String grt){
   this.sGrat=grt;
   }
    
    //METODO CREADO POR ALBERTO 3° FASE
    //BUSCA LOS DATOS DE UN PACIENTE QUE TIENE QUE TENER UN NUMERO DE EXPEDIENTE VIGENTE    
    //***********************************
     public Paciente[] buscarPacientesConExpediente()throws Exception{
        Paciente arrRet[] = null;
        Paciente oPaciente = null;
        ArrayList rst = null;
        ArrayList<Paciente> vObj = null;
        String sQuery = "";
        String nombre = "";
        String appaterno = "";
        String apmaterno = "";
        String numexp = "", edad="";
        int i = 0;
        int nTam = 0;
        if(this.getOpcionUrg() == 0){
            nombre = this.getNombres();
            appaterno = this.getApPaterno();
            apmaterno = this.getApMaterno();
            numexp = "null";
        }else{
            nombre = "";
            appaterno = "";
            apmaterno = "";
            numexp = this.getExpediente().getNumero() + "";
        }
        if(numexp.compareTo("null") == 0)
            sQuery = "SELECT * FROM buscapacienteexpedientehistoriaclinica('"+ nombre +"', '"+ appaterno +"','"+ apmaterno +"',"+ numexp + ");";
        else
            sQuery = "SELECT * FROM buscapacienteexpedientehistoriaclinica('"+ nombre +"', '" + appaterno + "','" + apmaterno + "'," + Integer.parseInt(numexp) + ");";                
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);            
            oAD.desconectar();
        }
        if(rst != null && rst.size() > 0){
            arrRet = new Paciente[rst.size()];
            for(i = 0; i < rst.size(); i++){
                oPaciente = new Paciente();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                oPaciente.setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                oPaciente.getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                oPaciente.setFechaNac((Date)vRowTemp.get(3));
                oPaciente.setNombres((String)vRowTemp.get(4).toString());
                oPaciente.setApPaterno((String)vRowTemp.get(5).toString());
                oPaciente.setApMaterno((String)vRowTemp.get(6).toString());
                edad=(String)vRowTemp.get(7);
                if (edad.compareTo("")!=0){
                if(edad.substring(0, 3).compareTo("000")!=0){
                    if (edad.charAt(0)=='0')
                       oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                    else
                       oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                }else{
                    if (edad.substring(4, 6).compareTo("00")!=0)
                        oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                    else
                       oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                }
                if(((String)vRowTemp.get(8)).equals("M")){
                        oPaciente.setSexoP("MASCULINO");
                    }else if(((String)vRowTemp.get(5)).equals("F")) {
                        oPaciente.setSexoP("FEMENINO");
                    }else{
                        oPaciente.setSexoP("NO ESPECIFICADO");
                    }
                arrRet[i] = oPaciente;
            }
        }
        return arrRet;
    }

    /* Metodo que busca datos de un paciente y expediente no nesesariamente debe de tener un expediente*/
    public Paciente[] buscarPacienteDatos() throws Exception{         
	Paciente arrRet[]=null, oPaciente = null;
	ArrayList rst = null;   
        ArrayList<Paciente> vObj=null;
	String sQuery = "", aPaterno="", aMaterno="", nombre="", numExp="";
        String edad="";
	int i=0, nTam=0;       
        if (getOpcionUrg()==0){
            aPaterno=this.getApPaterno();
            aMaterno=this.getApMaterno();
            nombre=this.getNombres();
            numExp="null";            
        }else{
            aPaterno="";
            aMaterno="";
            nombre="";
            numExp=getExpediente().getNumero()+"";            
        }        
        if(this.getNombres()!=null && !this.getNombres().equals("")
                || this.getApMaterno()!=null && !this.getApMaterno().equals("")
                || this.getApPaterno()!=null && !this.getApPaterno().equals("")
                || getExpediente().getNumero()!=0){
            if (numExp.compareTo("null")==0)
		sQuery = "SELECT * FROM buscarpacientesdatos('"+nombre+"','"+aPaterno+"','"+aMaterno+"',"+numExp+");"; 
            else
                sQuery = "SELECT * FROM buscarpacientesdatos('"+nombre+"','"+aPaterno+"','"+aMaterno+"',"+Integer.parseInt(numExp)+");";            
                        
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
                    
            if (rst != null && rst.size()>0) {
                arrRet = new Paciente[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    oPaciente= new Paciente();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oPaciente.setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oPaciente.setApPaterno((String)vRowTemp.get(2));
                    oPaciente.setApMaterno((String)vRowTemp.get(3));
                    oPaciente.setNombres((String)vRowTemp.get(4));
                    oPaciente.setFechaNac((Date)vRowTemp.get(5));
                    oPaciente.setCurp((String)vRowTemp.get(6));
                    edad=(String)vRowTemp.get(7);
                    if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                        if (edad.charAt(0)=='0')
                           oPaciente.setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                           oPaciente.setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                            oPaciente.setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                            oPaciente.setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oPaciente.getMunicipio().setDescripcionMun((String)vRowTemp.get(8));
                    oPaciente.getExpediente().setNumero(((Double) vRowTemp.get(9)).intValue());
                    arrRet[i]=oPaciente;
                }

            }	 
        }
        return arrRet; 
    }
    
    
     
     public Parametrizacion getTipoSangre(){
         return oTipoSangre;
     }
     public void setTipoSangre(Parametrizacion oTipoSangre){
         this.oTipoSangre = oTipoSangre;
     }
     public Parametrizacion getFactorRH(){
         return oFactorRH;
     }
     public void setFactorRH(Parametrizacion oFactorRH){
         this.oFactorRH = oFactorRH;
     }
     
    public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }
    
    public String numnull(int num){
        if(num==0){
            return null;
        }
        else{
            return ""+num;
        }
    }
    
    public EpisodioMedico getEpisodioMedicoActual(){
        if (this.arrEpisodiosMedicos != null && 
            this.arrEpisodiosMedicos.length>0)
            return this.arrEpisodiosMedicos[0];
        else
            return null;
    } 
    
    public EpisodioMedico[] getEpisodiosMedicos() {
        return arrEpisodiosMedicos;
    }

    public void setEpisodiosMedicos(EpisodioMedico[] oEpisodiosMedicos) {
        this.arrEpisodiosMedicos = oEpisodiosMedicos;
    }

    public float getImc() {
        return nImc=nPeso/((nTalla/100)*(nTalla/100));
    }
} 

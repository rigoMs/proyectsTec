package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.consultaexterna.Permiso;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.consultaexterna.Horario;
import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.util.CellRangeAddress;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFFont;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.primefaces.context.RequestContext;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/
public class Cita implements Serializable{
	private AccesoDatos oAD;
	private Date dFechaCita;
	private Date dFechaRegistro;
	private int nFolioCita;
	private int nNoFicha;
	private int nNumAprox;
	private Parametrizacion oTiempoAprox;
	private char sEmisorCita;
	private String sFolioPago;
	private char sStatusCita;
	private Paciente oPaciente;
        private int nNoConsulta;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private int bloque1, bloque2, bloque3;
        private String sDatosFechaFinal;
        private Permiso oPermiso;
        private Date dFechaIni, dFechaFin;
        private Date dFechaAux, dConsul2, dConsul3;
        private AreaServicioHRRB oAreaServicio;
        private PersonalHospitalario oPH;
        private int nNoConsultorio;
        private int nMaximo;
        private int nCitados;
        private String sHoraCita;
        private String primHos;
        private String primEsp;
        private DiagnosticoCIE10 oDiag;
        private boolean bOpcion;
        private int nEP;
        private int[] nArrCitados;
        private int j;
        private String sIdUsuario;
        private String sAsistencia;
        private int mostrar;
        private int nRepor;
        private int nConf;
        private Cita arrRetRep[];
        private int nCons;
        private Cita arrRetConf[], arrRetC1[], arrRetC2[], arrRetC3[], arrRetC4[], arrRet5[];
        private int nCon1, nCon2, nCon3, nCon4, nCon5;
        private int cuentacitasser;
        private String sTurno;
	private String sHora; ////CAPASITS
        private String sHoranu; ////CAPASITS
	private PacienteCapasits oPacCapa; ////CAPASITS
	private Horario oHora; ///CAPASITS
        private Consultorio oConsul; ///CAPASITS
        private String sTiempoAproxP;
        private String sStatusCitaP;
        private long nClaveEpisodio;
        private long nNumIngresoHosp;        
                
    public Cita(Date dFechaCita, Date dFechaRegistro, int nFolioCita, int nNoFicha, int nNumAprox, Parametrizacion oTiempoAprox, char sEmisorCita, String sFolioPago, char sStatusCita, Paciente oPaciente) {
        this.dFechaCita = dFechaCita;
        this.dFechaRegistro = dFechaRegistro;
        this.nFolioCita = nFolioCita;
        this.nNoFicha = nNoFicha;
        this.nNumAprox = nNumAprox;
        this.oTiempoAprox = oTiempoAprox;
        this.sEmisorCita = sEmisorCita;
        this.sFolioPago = sFolioPago;
        this.sStatusCita = sStatusCita;
        this.oPaciente = oPaciente;  
    }
        
        public Cita(){
	    oPacCapa= new PacienteCapasits(); //CAPASITS
	    oHora= new Horario(); //CAPASITS
	    oConsul=new Consultorio(); ///CAPASITS
            oPaciente=new Paciente();
            oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
            dFechaRegistro=new Date();
            bloque1=8;
            bloque2=9;
            bloque3=10;
            sDatosFechaFinal="";
            oPermiso=new Permiso();
            dFechaIni=new Date();
            dFechaFin=new Date();
            dFechaAux=new Date();
            dConsul2=new Date();
            oAreaServicio=new AreaServicioHRRB();
            oPH=new PersonalHospitalario();
            nNoConsultorio=0;
            nMaximo=0;
            nCitados=0;
            sHoraCita="";
            primHos="";
            primEsp="";
            oDiag=new DiagnosticoCIE10();
            sFolioPago="";
            oTiempoAprox=new Parametrizacion();
            bOpcion=false;
            nEP=0;
            nArrCitados=new int[18];
            j=0;
            sIdUsuario="";
            sAsistencia="";
            mostrar=0;
            nRepor=1;
            arrRetRep=null;
            nCons=1;
            nConf=1;
            arrRetConf=null;
            arrRetC1=arrRetC2=arrRetC3=arrRetC4=arrRet5=null;
            nCon1=nCon2=nCon3=nCon4=nCon5=0;
            cuentacitasser=0;
            sTurno="";
        }
        
        public void buscaNombreUsuario(){
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
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCita();"; 
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
	
//*********************************MÉTODO DE CAPASITS****************************************        
	public boolean buscarPacienteEncita() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPacienteEnCita("+getFolioCita()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.getPacienteCapa().setNombres((String)vRowTemp.get(0));
                            this.getPacienteCapa().setApPaterno((String)vRowTemp.get(1));
                            this.getPacienteCapa().setApMaterno((String)vRowTemp.get(2));
                            this.getPacienteCapa().getSeg().setVigencia((Date)vRowTemp.get(3));
                            this.getPacienteCapa().setFolioPaciente(((Double)vRowTemp.get(4)).intValue());
				bRet = true;
			}
		} 
		return bRet; 
	}  
        //*********************************MÉTODO DE CAPASITS*****************************************
        public Cita[] buscarCitaDePaciente() throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
        int i=0, nTam=0;
			sQuery = "SELECT * FROM buscaCitaDePaciente("+getPacienteCapa().getFolioPaciente()+","+this.oConsul.getNoConsultorio()+"::smallint,"+this.oHora.getClave()+"::smallint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null) {
			vObj = new ArrayList<Cita>();
			for (i = 0; i < rst.size(); i++) {
                            oCita=new Cita();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCita.oPacCapa.setNombres((String)vRowTemp.get(0));
                            oCita.oPacCapa.setApPaterno((String)vRowTemp.get(1));
                            oCita.oPacCapa.setApMaterno((String)vRowTemp.get(2));
                            oCita.setFechaCita((Date) vRowTemp.get(3));
                            oCita.setHoraAlmacenada((Date) vRowTemp.get(4));
                            oCita.oConsul.setNomConsultorio((String) vRowTemp.get(5));
                            vObj.add(oCita);
			}
			nTam = vObj.size();
                    arrRet = new Cita[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	}
        //*********************************MÉTODO DE CAPASITS*****************************************
        public Cita[] buscarTodasLasCitasDePaciente() throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
        int i=0, nTam=0;
			sQuery = "SELECT * FROM buscaTodasLasCitasDePaciente("+getPacienteCapa().getFolioPaciente()+");"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null) {
			vObj = new ArrayList<Cita>();
			for (i = 0; i < rst.size(); i++) {
                            oCita=new Cita();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCita.oPacCapa.setNombres((String)vRowTemp.get(0));
                            oCita.oPacCapa.setApPaterno((String)vRowTemp.get(1));
                            oCita.oPacCapa.setApMaterno((String)vRowTemp.get(2));
                            oCita.setFechaCita((Date) vRowTemp.get(3));
                            oCita.setHoraAlmacenada((Date) vRowTemp.get(4));
                            oCita.oConsul.setNomConsultorio((String) vRowTemp.get(5));
                            vObj.add(oCita);
			}
			nTam = vObj.size();
                    arrRet = new Cita[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	}
        //*********************************MÉTODO DE CAPASITS*****************************************
        public Cita[] buscarHistorialDeCitasDePaciente() throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
        int i=0, nTam=0;
			sQuery = "SELECT * FROM buscaHistorialDeCitasDePaciente("+getPacienteCapa().getFolioPaciente()+",'"+getFechaIni()+"','"+getFechaFin()+"');"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null) {
			vObj = new ArrayList<Cita>();
			for (i = 0; i < rst.size(); i++) {
                            oCita=new Cita();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCita.oPacCapa.setNombres((String)vRowTemp.get(0));
                            oCita.oPacCapa.setApPaterno((String)vRowTemp.get(1));
                            oCita.oPacCapa.setApMaterno((String)vRowTemp.get(2));
                            oCita.setFechaCita((Date) vRowTemp.get(3));
                            oCita.setHoraAlmacenada((Date) vRowTemp.get(4));
                            oCita.oConsul.setNomConsultorio((String) vRowTemp.get(5));
                            vObj.add(oCita);
			}
			nTam = vObj.size();
                    arrRet = new Cita[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	}        
	//*********************************MÉTODO DE CAPASITS*****************************************
        public boolean buscarPacienteEnConsultorio() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPacienteCitaConsultorio("+oPacCapa.getIdNacional()+",'"+getFechaCita()+"',"+oConsul.getNoConsultorio()+"::smallint);"; 
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
	//*********************************MÉTODO DE CAPASITS*****************************************
        public boolean buscarPacienteEnConsultoriofechayhora() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPacienteCitaFechaHora("+oPacCapa.getIdNacional()+",'"+getFechaHora()+"');"; 
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
	//*********************************MÉTODO DE CAPASITS*****************************************
        public boolean buscarCitaEnConsultoriofechayhora() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaCitaConsultorioFechaHora('"+getFechaHora()+"',"+oConsul.getNoConsultorio()+"::smallint);"; 
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
	//*********************************MÉTODO DE CAPASITS*****************************************
        public boolean buscarCitaEnConsultoriofechayhora(int valor) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaCitaConsultorioFechaHora('"+getFechaHora()+"',"+valor+"::smallint);"; 
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
	//*********************************MÉTODO DE CAPASITS*****************************************
	public boolean buscarUsuarioMedico(String valor) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		 if( this==null){   //completar llave
			throw new Exception("Medico.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaConsultorioEnAgenda('"+valor+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                oConsul.setNoConsultorio(((Double)vRowTemp.get(0)).intValue());
                                oHora.setClave(((Double)vRowTemp.get(1)).intValue());
				bRet = true;
			}
		} 
		return bRet; 
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
        public Cita[] buscarCitasCapasits() throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaCitasCapasitsConsultorio('"+this.getFechaCita()+"',"+this.oConsul.getNoConsultorio()+"::smallint,"+this.oHora.getClave()+"::smallint);"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Cita>();
			for (i = 0; i < rst.size(); i++) {
                            oCita=new Cita();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCita.setFechaCita((Date)vRowTemp.get(0));
                            oCita.setHoraAlmacenada((Date)vRowTemp.get(1));
                            oCita.oPacCapa.setIdNacional(((Double) vRowTemp.get(2)).intValue());
                            oCita.oPacCapa.setNombres((String)vRowTemp.get(3));
                            oCita.oPacCapa.setApPaterno((String)vRowTemp.get(4));
                            oCita.oPacCapa.setApMaterno((String)vRowTemp.get(5));
                            oCita.setFolioCita(((Double) vRowTemp.get(6)).intValue());
                            oCita.oConsul.setNoConsultorio(((Double) vRowTemp.get(7)).intValue());
                            vObj.add(oCita);
			}
                        nTam = vObj.size();
                    arrRet = new Cita[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	} 

	public Cita[] buscarTodos() throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCita();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Cita[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        //**********************************************************************
        public boolean buscarCitaCE(String[] v, long folio) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscarCitaCE("+v[1]+"::smallint, "+v[0]+", '"+df.format(getFechaCita())+"%', "+folio+", '"+v[4].substring(0, 3)+"');";
			
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				bRet = true;
			}
		} 
		return bRet; 
	}
        //**********************************************************************
        public int buscarTotalCitasMedico(String t, String c, String turno) throws Exception{
	ArrayList rst = null;
        int total=0;
	String sQuery = "";
        int Tarjeta=Integer.parseInt(t);
        int clave=Integer.parseInt(c);
		 if( this==null){   //completar llave
			throw new Exception("Cita.buscar: error de programación, faltan datos");
		}else{ 
                        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                       	sQuery = "SELECT * FROM buscarTotalCitasMedico("+Tarjeta+", "+clave+"::smallint, '"+df.format(getFechaCita())+"%', '"+turno.substring(0, 3)+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				total=((Double)vRowTemp.get(0)).intValue();
			}
		} 
		return total; 
	} 
        //**********************************************************************
        public Cita[] buscarCitasPorServicio() throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        int citaux=0;
        SimpleDateFormat df=new SimpleDateFormat("EEEE");
 
                       	sQuery = "SELECT * FROM buscaCitasPorServicio("+getAreaServicio().getClave()+"::smallint, '"+getFechaIni()+"', '"+getFechaFin()+"');"; 
			System.out.println(sQuery);
                        oAD=new AccesoDatos();
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<Cita>();
                            for (i = 0; i < rst.size(); i++) {
                                oCita=new Cita();
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oCita.setFechaCita((Date)vRowTemp.get(0));
                                oCita.getPH().setNoTarjeta(((Double)vRowTemp.get(1)).intValue());
                                oCita.getPH().setApPaterno((String)vRowTemp.get(2));
                                oCita.getPH().setApMaterno((String)vRowTemp.get(3));
                                oCita.getPH().setNombres((String)vRowTemp.get(4));
                                oCita.setNoConsultorio(((Double)vRowTemp.get(5)).intValue());
                                oCita.getAreaServicio().setDescripcion((String)vRowTemp.get(6));                            
                                oCita.setCitados(buscaCitadosServicio(oCita.getPH().getNoTarjeta(), oCita.getFechaCita(), ((String)vRowTemp.get(9))));
                                oCita.getAreaServicio().setClave(((Double)vRowTemp.get(8)).intValue());
                                oCita.setTurno(((String)vRowTemp.get(9)));
                                Calendar Cal=Calendar.getInstance();
                                Cal.setTime(oCita.getFechaCita());
                                String diaTexto="";
                                int numDia=Cal.get(Calendar.DAY_OF_WEEK);
                                switch(numDia){
                                    case 1: diaTexto="DOMINGO"; break;
                                    case 2: diaTexto="LUNES"; break;
                                    case 3: diaTexto="MARTES"; break;
                                    case 4: diaTexto="MIÉRCOLES"; break;
                                    case 5: diaTexto="JUEVES"; break;
                                    case 6: diaTexto="VIERNES"; break;
                                    case 7: diaTexto="SÁBADO"; break;
                                }
                                oCita.setMaximo(buscaMaxPrimSubs(oCita.getPH().getNoTarjeta(), oCita.getAreaServicio().getClave(), oCita.getNoConsultorio(),diaTexto, ((String)vRowTemp.get(9))));
                                if (oCita.getCitados()>0){
                                oCita.setPrimEsp(oCita.cuentaCitadosEsp(oCita.getPH().getNoTarjeta(), oCita.getFechaCita(), oCita.getTurno(), oCita.getAreaServicio().getClave(), "S")+"");
                                oCita.setPrimHos(oCita.cuentaCitadosEsp(oCita.getPH().getNoTarjeta(), oCita.getFechaCita(), oCita.getTurno(), oCita.getAreaServicio().getClave(), "N")+"");
				vObj.add(oCita);
                                }
                            }
                            nTam = vObj.size();
                            arrRet = new Cita[nTam];
                            for (i=0; i<nTam; i++){
                                arrRet[i] = vObj.get(i);
                            }
		}
        
		return arrRet; 
	}   
   //**********************************************************************************************
   public Cita[] buscarCitasAutorizadas() throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
 
                       	sQuery = "SELECT * FROM buscaCitasAutorizadas('"+getFechaIni()+"', '"+getFechaFin()+"');"; 
                        oAD=new AccesoDatos();
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<Cita>();
                            for (i = 0; i < rst.size(); i++) {
                                oCita=new Cita();
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oCita.getPaciente().setApPaterno((String)vRowTemp.get(0));
                                oCita.getPaciente().setApMaterno((String)vRowTemp.get(1));
                                oCita.getPaciente().setNombres((String)vRowTemp.get(2));
                                oCita.setFechaCita((Date)vRowTemp.get(3));
                                oCita.getPaciente().setTelefono((String)vRowTemp.get(4));
                                oCita.getAreaServicio().setClave(((Double)vRowTemp.get(5)).intValue());
                                oCita.getAreaServicio().setDescripcion((String)vRowTemp.get(6));      
                                oCita.setIdUsuario((String)vRowTemp.get(7));
                               vObj.add(oCita);                                
                            }
                            nTam = vObj.size();
                            arrRet = new Cita[nTam];
                            for (i=0; i<nTam; i++){
                                arrRet[i] = vObj.get(i);
                            }
		}
        
		return arrRet; 
	} 
  
   //**********************************************************************************************
    public int buscaMaxPrimSubs (int tarjeta, int area, int consultorio, String dia, String turno) throws Exception{
    int bRet = 0;
    ArrayList rst = null;
    String sQuery = "";
         if( this==null){   //completar llave
            throw new Exception("AsignaConsultorio.buscar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM buscaMaxTipoCita("+tarjeta+", "+area+"::smallint, "+consultorio+"::smallint, '"+turno+"', '"+dia.toUpperCase()+"');";
            oAD=new AccesoDatos();
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                bRet=((Double)vRowTemp.get(0)).intValue()+((Double)vRowTemp.get(1)).intValue();
            }
        } 
        return bRet; 
    }
        //**********************************************************************************************
    public int buscaCitadosServicio (int tarjeta, Date fecha, String turno) throws Exception{
    int bRet = 0;
    ArrayList rst = null;
    String sQuery = "";
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
         if( this==null){   //completar llave
            throw new Exception("AsignaConsultorio.buscar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM buscaCitadosServicio("+tarjeta+", '"+df.format(fecha)+"%', '"+turno.substring(0, 3)+"');";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                bRet=((Double)vRowTemp.get(0)).intValue();
            }
        } 
        return bRet; 
    }
    //**********************************************************************************************
    public int cuentaCitadosEsp(int tarjeta, Date fecha, String turno, int area, String tipo) throws Exception{
    int bRet = 0;
    ArrayList rst = null;
    String sQuery = "";
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
         if( this==null){   //completar llave
            throw new Exception("AsignaConsultorio.buscar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM cuentaCitadosEsp('"+df.format(fecha)+"%', "+tarjeta+", "+area+"::smallint, '"+turno.substring(0, 3)+"', '"+tipo+"');";
            oAD=new AccesoDatos();
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size() > 0) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                bRet=((Double)vRowTemp.get(0)).intValue();
            }
        } 
        return bRet; 
    }
    //********************************************************************************
	public int insertar(String medico, long folio, int maximoCitasPS, int primvez, int subs) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String datosMed[]=medico.split("-");
        String registro=calculaFechaHoraRegTexto(1)+" "+calculaFechaHoraRegTexto(2);
        String feCita;
        //System.out.println(maximoCitasPS+" "+(primvez+subs)+" "+datosMed[0]);
        if (datosMed[4].substring(0, 3).compareTo("MAT")==0)
        feCita=getFechaCitaTexto(1)+" "+horaTexto(maximoCitasPS, (primvez+subs), datosMed[0]);
        else
            feCita=getFechaCitaTexto(1)+" "+horaTextoVes(datosMed[0], datosMed[4]);
        sDatosFechaFinal=feCita;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaReg = null;
        Date fechaCita = null;
        try {fechaReg = formatoDelTexto.parse(registro); } catch (Exception ex) {ex.printStackTrace();}
        try {fechaCita = formatoDelTexto.parse(feCita); } catch (Exception ex) {ex.printStackTrace();}
		 if( this==null){   //completar llave
			throw new Exception("Cita.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCitaCE('"+fechaReg+"', '"+fechaCita+"', '"+sUsuario+"', "+datosMed[1]+"::smallint, "+datosMed[2]+"::smallint, "+datosMed[0]+", "+folio+");";
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
    //*********************************MÉTODO DE CAPASITS*****************************************
	public int insertarCitaCapasits() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCitaCapasits('"+sUsuario+"',"+oPacCapa.getFolioPaciente()+",'"+getFechaHora()+"',"+oConsul.getNoConsultorio()+"::smallint,"+oHora.getClave()+"::smallint);"; 
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
    
//*********************************MÉTODO DE CAPASITS*****************************************
	    public int modificarCitaCapasits() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCitaCapasits('"+sUsuario+"',"+this.getFolioCita()+",'"+getFechaHora()+"');"; 
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

        public int modificaCitaReservada(String medico, long folio, int maximoCitasPS, int primvez, int subs) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String datosMed[]=medico.split("-");
        String registro=calculaFechaHoraRegTexto(1)+" "+calculaFechaHoraRegTexto(2);
        String feCita;
        if (datosMed[4].substring(0, 3).compareTo("MAT")==0)
        feCita=getFechaCitaTexto(1)+" "+horaTexto(maximoCitasPS, (primvez+subs), datosMed[0]);
        else
            feCita=getFechaCitaTexto(1)+" "+horaTextoVes(datosMed[0], datosMed[4]);
        sDatosFechaFinal=feCita;
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        Date fechaReg = null;
        Date fechaCita = null;
        try {fechaReg = formatoDelTexto.parse(registro); } catch (Exception ex) {ex.printStackTrace();}
        try {fechaCita = formatoDelTexto.parse(feCita); } catch (Exception ex) {ex.printStackTrace();}
		 if( this==null){   //completar llave
			throw new Exception("Cita.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM ModificaCitaCE('"+fechaReg+"', '"+fechaCita+"', '"+sUsuario+"', "+datosMed[1]+"::smallint, "+datosMed[2]+"::smallint, "+datosMed[0]+", "+folio+", "+getFolioCita()+");";
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
        
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCita();"; 
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
        //**********************************************************************
        public int modificarCitaCE() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String foliop="";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if (getFolioPago().compareTo("0")==0 || getFolioPago().compareTo("")==0)
            foliop="null";
        else
            foliop=getFolioPago();
		 if( this==null){   //completar llave
			throw new Exception("Cita.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificarCitaCE('"+sUsuario+"',"
                                +getFolioCita()+", "+foliop+", "+
                                getPH().getNoTarjeta()+", "+
                                getAreaServicio().getClave()+"::smallint, '"+
                                df.format(getFechaAux())+"%', "+
                                getNoConsultorio()+"::smallint, '"+
                                getHoraCita()+"'::text, '"+
                                getDiag().getClave1CE()+"', "+
                                getPaciente().getFolioPaciente()+");";
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
        //**********************************************************************
        public int cambiarMedicoDeCitas(String fecha, int area1, int cons1, int tarjeta1, String area2, String cons2, String tarjeta2, String turno) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String fechaIni="";

		 if( this==null){   //completar llave
			throw new Exception("Cita.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM cambiarMedicoDeCitas('"+sUsuario+"', '"+fecha+"', "+area1+"::smallint, "+cons1+"::smallint, "+tarjeta1+", "+area2+"::smallint, "+cons2+"::smallint, "+tarjeta2+", '"+turno+"');";
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
        //**********************************************************************
        public int borraCitaCE() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        FacesMessage message;
		 if( this==null){   //completar llave
			throw new Exception("Cita.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM borraCitaCE('"+sUsuario+"', "+getFolioCita()+", "+getPaciente().getFolioPaciente()+", '"+df.format(getFechaAux())+"%', "+getPH().getNoTarjeta()+", "+getAreaServicio().getClave()+"::smallint);";
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
                if (nRet==0)
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmación de Cita", "Error :(");
                else
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Confirmación de Cita", "Registro Borrado :)");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
		return nRet; 
	}
        //**********************************************************************
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCita();"; 
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

	  //*****************************************************************************
    public Cita[] buscarCitasEnEspera(int cons) throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        String h=""; String h2="";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
   
                       	sQuery = "SELECT * FROM buscaCitasEnEspera2("+cons+"::smallint, '"+df.format(getFechaAux())+"%');";
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<Cita>();
                            for (i = 0; i < rst.size(); i++) {
                                oCita=new Cita();
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oCita.setNoConsulta(i+1);
                                oCita.setFolioCita(((Double)vRowTemp.get(0)).intValue());
                                oCita.getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).intValue());
                                oCita.getPH().setNoTarjeta(((Double)vRowTemp.get(2)).intValue());
                                oCita.getPaciente().setApPaterno((String)vRowTemp.get(3));
                                oCita.getPaciente().setApMaterno((String)vRowTemp.get(4));
                                oCita.getPaciente().setNombres((String)vRowTemp.get(5));
                                oCita.getAreaServicio().setClave(((Double)vRowTemp.get(6)).intValue());
                                oCita.getAreaServicio().setDescripcion((String)vRowTemp.get(7));
                                oCita.getPaciente().getExpediente().setNumero(oCita.getPaciente().getExpediente().buscaExpPac(oCita.getPaciente().getFolioPaciente()));
                                h=((Double)vRowTemp.get(8)).toString();
                                h2=h.substring(0, h.length()-2);
                                if (h2.length()==1)
                                    oCita.setHoraCita("0"+h2+":00");
                                else
                                    oCita.setHoraCita(h2+":00");
                                //************
                                if (((String)vRowTemp.get(9)).compareTo("S")==0)
                                oCita.setPrimHos("PRIMERA VEZ");
                                else
                                 oCita.setPrimHos("SUBSECUENTE");
                                if (((String)vRowTemp.get(10)).compareTo("S")==0)
                                oCita.setPrimEsp("PRIMERA VEZ");
                                else
                                 oCita.setPrimEsp("SUBSECUENTE");  
                                vObj.add(oCita);
                            }
                            nTam = vObj.size();
                            arrRet = new Cita[nTam];
                            for (i=0; i<nTam; i++){
                                arrRet[i] = vObj.get(i);
                            }                 
                 }
 
		return arrRet; 
	}
    
    //*****************************************************************************
        public void buscaDatosCita() throws Exception{
	ArrayList rst = null;
	String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        
                       	sQuery = "SELECT * FROM buscaDatosCita("+getAreaServicio().getClave()+"::smallint, '"+df.format(getFechaAux())+"%', "+getPaciente().getFolioPaciente()+");"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setFechaRegistro((Date)vRowTemp.get(0));
                                setFechaCita((Date)vRowTemp.get(1));
                                setFolioCita(((Double)vRowTemp.get(2)).intValue());
                                getAreaServicio().setClave(((Double)vRowTemp.get(3)).intValue());
                                setNoConsultorio(((Double)vRowTemp.get(4)).intValue());
                                getPH().setNoTarjeta(((Double)vRowTemp.get(5)).intValue());
                                getPH().setApPaterno((String)vRowTemp.get(6));
                                getPH().setApMaterno((String)vRowTemp.get(7));
                                getPH().setNombres((String)vRowTemp.get(8));
                                setNoFicha(((Double)vRowTemp.get(9)).intValue());
                                setFolioPago(((Double)vRowTemp.get(10)).intValue()+"");
                          } 
	}
        //*******************************************************************************
       public void buscaCitaEP() throws Exception{
		ArrayList rst = null;
		String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
                       	sQuery = "SELECT * FROM buscaCitaEP("+getAreaServicio().getClave()+"::smallint, '"+df.format(getFechaAux())+"%', "+getPaciente().getFolioPaciente()+");"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size()>0) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getDiag().setClave((String)vRowTemp.get(0));
                                if (((String)vRowTemp.get(1)).compareTo("S")==0)
                                    setPrimHos("PRIMERA VEZ");
                                else
                                    setPrimHos("SUBSECUENTE");
                                if (((String)vRowTemp.get(2)).compareTo("S")==0)
                                    setPrimEsp("PRIMERA VEZ");
                                else
                                    setPrimEsp("SUBSECUENTE");
                          } 
	} 
	//**************************************************************************************
       public Cita[] buscaEPCitasRes() throws Exception{
        Cita arrRet[]=null, oEP=null;
        ArrayList<Cita> vObj=null;
		int i=0, nTam=0;      
		ArrayList rst = null;
		String sQuery = "";
        
		 if( this==null){   //completar llave
			throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaEPCitasRes();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<Cita>();
                            for(i=0; i<rst.size(); i++){
				oEP=new Cita();
                                ArrayList vRowTemp = (ArrayList)rst.get(i);
				oEP.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                                oEP.setEP(((Double)vRowTemp.get(1)).intValue());
                                oEP.getDiag().setClave((String)vRowTemp.get(2));
                                oEP.getDiag().setDescripcionDiag((String)vRowTemp.get(3));
                                oEP.getAreaServicio().setClave(((Double)vRowTemp.get(4)).intValue());
                                oEP.getAreaServicio().setDescripcion((String)vRowTemp.get(5));
                                oEP.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());
                                buscaPacCitasRes(oEP);
                                vObj.add(oEP);
                            }
                         nTam = vObj.size();
                        arrRet = new Cita[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
			}
		}
                 }
		return arrRet; 
	}
          //****************************************************************************
        public void buscaPacCitasRes(Cita oEP) throws Exception{
	ArrayList rst = null;
	String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("dd/MM/yyyy");
		 if( this==null){   //completar llave
			throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPacCitasRes("+oEP.getPaciente().getFolioPaciente()+", "+oEP.getAreaServicio().getClave()+"::smallint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size()>0) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                oEP.setFolioCita(((Double)vRowTemp.get(0)).intValue());
				oEP.getPaciente().setApPaterno((String)vRowTemp.get(1));
                                oEP.getPaciente().setApMaterno((String)vRowTemp.get(2));
                                oEP.getPaciente().setNombres((String)vRowTemp.get(3));
                                oEP.setNumAprox(((Double)vRowTemp.get(4)).intValue());
                                oEP.getTiempoAprox().setValor(oEP.getTiempoAprox().buscaValorParametrizado((String)vRowTemp.get(5)));
                                oEP.getPaciente().setSexoP((String)vRowTemp.get(6));
                                oEP.getPaciente().setFechaNacTexto(df.format((Date)vRowTemp.get(7)));
                                oEP.getPaciente().setFechaNac((Date)vRowTemp.get(7));
                                oEP.getPaciente().setTelefono((String)vRowTemp.get(8));
                                oEP.getPaciente().setCalleNum((String)vRowTemp.get(9));
                                oEP.getPaciente().setColonia((String)vRowTemp.get(10));
                                oEP.getPaciente().setCp((String)vRowTemp.get(11));
                                oEP.getPaciente().setCodigoPos((String)vRowTemp.get(11));
                                oEP.getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 12 ));
                                oEP.getPaciente().getEstado().buscar();
                                oEP.getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 13 ));
                                oEP.getPaciente().getMunicipio().buscar(oEP.getPaciente().getEstado().getClaveEdo());
                                oEP.getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 14 ));
                                oEP.getPaciente().getCiudad().buscar(oEP.getPaciente().getEstado().getClaveEdo(), oEP.getPaciente().getMunicipio().getClaveMun());
                                oEP.getPaciente().setEdadNumero((String)vRowTemp.get(15));
                                oEP.getPaciente().setOtroPais((String)vRowTemp.get(16));
                                if (oEP.getPaciente().getOtroPais().compareTo("")==0)
                                    oEP.getPaciente().setOtroPais("MÉXICO");
                                String x=(String)vRowTemp.get(17);
                                if (x.compareTo("")!=0){
                                oEP.getPaciente().getTipoSol().setValor(oEP.getPaciente().getTipoSol().buscaValorParametrizado(x));
                                oEP.getPaciente().getTipoSol().setTipoParametro(x.charAt(3)+""+x.charAt(4));}
                                else{
                                    oEP.getPaciente().getTipoSol().setValor("");
                                    oEP.getPaciente().getTipoSol().setTipoParametro("");
                                }
                                oEP.getPaciente().getReferencia().setClave((String)vRowTemp.get(18));
                                oEP.getPaciente().getReferencia().buscar();
                                oEP.getPaciente().buscasegpop(oEP.getPaciente().getFolioPaciente());
				}
             }
		}
        //*********************************************************************
        public String tiempoAproximado(){
        String mensaje="";
        if(getTiempoAprox().getValor()!=null){
        if (getNumAprox()==1)
            mensaje=getNumAprox()+" "+getTiempoAprox().getValor();
        else{
            if (getTiempoAprox().getValor().compareTo("MES")==0)
                mensaje=getNumAprox()+" "+getTiempoAprox().getValor()+"ES";
            else
                mensaje=getNumAprox()+" "+getTiempoAprox().getValor()+"S";
        }
        }
        return mensaje;
    }
	//*****************************************************************************
    public Cita[] buscarReporteCE(int consultorio) throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        int pacientes=0;

        if (nRepor==consultorio){
            nRepor++;

            if( getFechaAux()==null){   //completar llave
                    throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{  
                sQuery = "SELECT * FROM buscarReporteCE2('"+df.format(getFechaAux())+"%', "+consultorio+"::smallint);";
                
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
                }
                if (rst != null) {
                    vObj = new ArrayList<Cita>();
                    for (i=0; i<rst.size(); i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oCita=new Cita();
                        oCita.setNoFicha(((Double)vRowTemp.get(0)).intValue());
                        oCita.getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                        oCita.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                        oCita.getPaciente().setApPaterno((String) vRowTemp.get( 3 ));
                        oCita.getPaciente().setApMaterno((String) vRowTemp.get( 4 ));
                        oCita.getPaciente().setNombres((String) vRowTemp.get( 5 ));
                        oCita.getPaciente().setSexoP((String)vRowTemp.get(6));
                        df=new SimpleDateFormat("dd/MM/yyyy");
                        if (vRowTemp.get(7)!=null)
                            oCita.getPaciente().setFechaNacTexto(df.format((Date)vRowTemp.get(7)));
                        else
                            oCita.getPaciente().setFechaNacTexto("00/00/0000");
                        oCita.getPaciente().setFechaNac((Date)vRowTemp.get(7));       
                        oCita.getPaciente().setTelefono((String)vRowTemp.get(8));
                        oCita.getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 9 ));
                        oCita.getPaciente().getEstado().setDescripcionEdo((String) vRowTemp.get( 10 ));
                        oCita.getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 11 ));
                        oCita.getPaciente().getMunicipio().setDescripcionMun((String) vRowTemp.get( 12 ));
                        oCita.getAreaServicio().setClave(((Double)vRowTemp.get(13)).intValue());
                        oCita.getAreaServicio().setDescripcion((String)vRowTemp.get(14));
                        oCita.getPaciente().getSeg().setNumero((String)vRowTemp.get(15));
                        oCita.getDiag().getCauses().setClave((String)vRowTemp.get(16));
                        oCita.getDiag().setClave((String)vRowTemp.get(17));
                        oCita.getDiag().setDescripcionDiag((String)vRowTemp.get(18));
                        oCita.getPH().setNoTarjeta(((Double)vRowTemp.get(19)).intValue());
                        oCita.setFolioPago((String)vRowTemp.get(20).toString());
                        oCita.getPaciente().setEdadNumero(oCita.getPaciente().calculaEdad());
                        oCita.getPaciente().getReferencia().setClave((String)vRowTemp.get(21));
                        oCita.getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(22));
                        pacientes++;
                        vObj.add(oCita);

                    }
                    nTam = vObj.size();
                    arrRet = new Cita[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            arrRetRep=arrRet;
            nArrCitados[(consultorio-1)]=pacientes;                 
        }
        if (consultorio==(nRepor-1)){
            arrRet=arrRetRep;
        }
        if (consultorio==18 && nRepor==1){
            arrRet=arrRetRep;}
        if (nRepor==19){
            nRepor=1;} 
        return arrRet;
    }
    //*************************************************************************
    public void buscarCie10Reporte(Cita oCita, int area, int medico, long folio) throws Exception{
	ArrayList rst = null;
	String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{  
			sQuery = "SELECT * FROM buscarCie10Reporte('"+df.format(getFechaAux())+"%', "+area+"::smallint, "+medico+", "+folio+");";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size()>0) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                oCita.getDiag().setClave((String)vRowTemp.get(0));
                                oCita.getDiag().setDescripcionDiag((String)vRowTemp.get(1));
			}
		}
	}
    //*************************************************************************
        public void postProcessXLS(Object document) {
        XSSFWorkbook wb = (XSSFWorkbook) document;
        XSSFSheet sheet = wb.getSheetAt(0);
        wb.setSheetName(0,"Reporte"); //nombre de la hoja de excel
        if (j==0){
        sheet.shiftRows (0, 50000, +3);
        
        XSSFRow row = sheet.createRow((short)0);
        XSSFCell cell = row.createCell((short)0);
        cell.setCellValue("HOSPITAL REGIONAL RÍO BLANCO");
        sheet.addMergedRegion(new CellRangeAddress(0,(short)0, 0, (short)15));
        
        row = sheet.createRow((short)1);
        cell = row.createCell((short)0);
        cell.setCellValue("REPORTE DE CONSULTA EXTERNA DEL "+obtenFechaAux());
        sheet.addMergedRegion(new CellRangeAddress(1,(short)1, 0, (short)15));
        
        XSSFFont fontTit = wb.createFont();
        fontTit.setFontName(HSSFFont.FONT_ARIAL);
        fontTit.setFontHeightInPoints((short) 16);
        fontTit.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        fontTit.setColor(HSSFColor.BLACK.index);
        
        XSSFCellStyle cellStyleTit = wb.createCellStyle();
        cellStyleTit.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyleTit.setFont(fontTit);
        XSSFRow titulo = sheet.getRow(0);
        for(int i=0; i < titulo.getPhysicalNumberOfCells();i++) {
            titulo.getCell(i).setCellStyle(cellStyleTit);
        }
        }
        XSSFFont font = wb.createFont();
        font.setFontName(HSSFFont.FONT_ARIAL);
        font.setFontHeightInPoints((short) 11);
        font.setBoldweight(HSSFFont.BOLDWEIGHT_BOLD);
        font.setColor(HSSFColor.BLACK.index);

        XSSFRow header;
        XSSFCellStyle cellStyle = wb.createCellStyle();
        XSSFCellStyle cellStyle2 = wb.createCellStyle();
        cellStyle2.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        cellStyle2.setFont(font);
        cellStyle.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        font.setFontHeightInPoints((short) 11);
        cellStyle.setBorderBottom((short)1);
        cellStyle.setBorderTop((short)1);
        cellStyle.setBorderLeft((short)1);
        cellStyle.setBorderRight((short)1);
        cellStyle.setFont(font);
        int celda;
        celda=sheet.getLastRowNum()-nArrCitados[j];
        j++;
            
        header = sheet.getRow(celda-1);
        
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle2);
        }
        
        header = sheet.getRow(1);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle2);
        }

        header = sheet.getRow(celda);
        for(int i=0; i < header.getPhysicalNumberOfCells();i++) {
            header.getCell(i).setCellStyle(cellStyle);
        }
        if (j==18){
            j=0;
            nArrCitados=new int[18];
        }
    }
	//*******************************************************************************
    public Cita[] buscarConsulta1(Medico datos, int op) throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        String v[]=datos.getNombres().split("-");
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        int con=0;
        int var=0;
        if (op==1){var=nCon1;}else{var=nCon2;}
        
        if (var==0){
        if (v.length>1){
            getPH().setNombres(v[5]);
            getPH().setNoTarjeta(Integer.parseInt(v[0]));
            getPH().setApPaterno(v[6]);
            getPH().setApMaterno(v[7]);
            if (v.length==9)
            getAreaServicio().setDescripcion(v[8]);
            setNoConsultorio(Integer.parseInt(v[2]));
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{  
                        if (op==1)
			sQuery = "SELECT * FROM buscarConsulta1('"+df.format(getFechaAux())+"%', "+v[2]+"::smallint, "+v[0]+", '"+v[4].substring(0, 3)+"');";
                        else
                            sQuery = "SELECT * FROM buscarConsulta2('"+df.format(getFechaAux())+"%', "+v[2]+"::smallint, "+v[0]+", '"+v[4].substring(0, 3)+"');";
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
                                if (op==1)nCon1++;
                                if (op==2)nCon2++;
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<Cita>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oCita=new Cita();
                                oCita.setNoFicha(((Double)vRowTemp.get(0)).intValue());
                                oCita.getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue()); 
                                oCita.setFolioCita(((Double)vRowTemp.get(2)).intValue());
                                oCita.getPaciente().setApPaterno((String) vRowTemp.get( 3 ));
                                oCita.getPaciente().setApMaterno((String) vRowTemp.get( 4 ));
                                oCita.getPaciente().setNombres((String) vRowTemp.get( 5 ));
                                oCita.getPaciente().getTipoSol().setValor(oCita.getPaciente().getTipoSol().buscaValorParametrizado((String) vRowTemp.get( 6 )));
                                oCita.getPaciente().getExpediente().setNumero(oCita.getPaciente().getExpediente().buscaExpPac(oCita.getPaciente().getFolioPaciente()));
                                oCita.getAreaServicio().setClave(Integer.parseInt(v[1]));
                                oCita.setFechaAux(getFechaAux());
                                oCita.buscaCitaEP();
                                oCita.getPaciente().buscasegpop(oCita.getPaciente().getFolioPaciente());
                                con++;
                                oCita.setNoConsulta(con);
				vObj.add(oCita);                              
                            }
                    nTam = vObj.size();
                    arrRet = new Cita[nTam];
                    for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                        }
                    if (op==1)
                    arrRetC1=arrRet;
                    else
                        arrRetC2=arrRet;
		    }
		}
            }
        }
            if (op==1)
            arrRet=arrRetC1;
            else
                arrRet=arrRetC2;
                 return arrRet;
	}
	
    //**********************************************************************************************
        public Cita[] buscarPacPorCons(String datos) throws Exception{
	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
        int i=0, cita=-1, nTam=0;
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        String vec[]=datos.split("-");
        int con=0;
        
        if (nCon3==0){
        if (vec.length>1){
		sQuery = "select * from buscaPacPorCons("+vec[0]+", "+vec[2]+"::smallint, '"+df.format(getFechaAux())+"%', '"+vec[4].substring(0, 3)+"');";
		oAD=new AccesoDatos();
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery);
                        nCon3++;
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Cita>();
			for (i = 0; i < rst.size(); i++) {
                            oCita=new Cita();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCita.setFolioCita(((Double)vRowTemp.get(0)).intValue());
                            oCita.getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                            oCita.getAreaServicio().setClave(((Double)vRowTemp.get(2)).intValue());
                            oCita.getAreaServicio().setDescripcion((String)vRowTemp.get(3));
                            oCita.getPaciente().setApPaterno((String)vRowTemp.get(4));
                            oCita.getPaciente().setApMaterno((String)vRowTemp.get(5));
                            oCita.getPaciente().setNombres((String)vRowTemp.get(6));
                            oCita.getPH().setNoTarjeta(((Double)vRowTemp.get(7)).intValue());
                            oCita.getPH().setApPaterno((String)vRowTemp.get(8));
                            oCita.getPH().setApMaterno((String)vRowTemp.get(9));
                            oCita.getPH().setNombres((String)vRowTemp.get(10));
                            oCita.getPaciente().getExpediente().setNumero(oCita.getPaciente().getExpediente().buscaExpPac(oCita.getPaciente().getFolioPaciente()));
                            con++;
                            oCita.setNoConsulta(con);
                            vObj.add(oCita);
                            
			}
                    nTam = vObj.size();
                    arrRet = new Cita[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		}
                arrRetC3=arrRet;
            }
        }
                arrRet=arrRetC3;
		return arrRet; 
	}       
        //**********************************************************************************************
        public String detalleCitasCons() throws Exception{
            String pag="DetalleCitaXCons";
            String x=getPaciente().buscarPacCE(2);
            buscaDatosCita();
            buscaCitaEP();
            buscarCie10Reporte(this, getAreaServicio().getClave(), getPH().getNoTarjeta(), getPaciente().getFolioPaciente());
            boolean y=getDiag().getCauses().buscar(getDiag().getClave());
            
            return pag;
        }
        
        //****************************************************************************
    public Cita[] buscarhistCitas() throws Exception{
        Cita arrRet[]=null, oEP=null;
        ArrayList<Cita> vObj=null;
	int i=0, nTam=0;      
	ArrayList rst = null;
	String sQuery = "";
        Date hoy=new Date();
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if(nCon4==0){
		 if( this==null){   //completar llave
			throw new Exception("EpisodioMedico.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscarhistCitas("+getPaciente().getFolioPaciente()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
                                nCon4++;
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<Cita>();
                            for(i=0; i<rst.size(); i++){
				oEP=new Cita();
                                ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oEP.setFechaCita((Date)vRowTemp.get(0));
                                oEP.getPH().setNoTarjeta(((Double)vRowTemp.get(1)).intValue());
                                oEP.getPH().setApPaterno((String)vRowTemp.get(2));
                                oEP.getPH().setApMaterno((String)vRowTemp.get(3));
                                oEP.getPH().setNombres((String)vRowTemp.get(4));
                                oEP.getAreaServicio().setClave(((Double)vRowTemp.get(5)).intValue());
                                oEP.getAreaServicio().setDescripcion((String)vRowTemp.get(6));
                                oEP.setNoConsultorio(((Double)vRowTemp.get(7)).intValue());
                                oEP.getPaciente().getTipoSol().setValor((String)vRowTemp.get(8));
                                oEP.setIdUsuario((String)vRowTemp.get(9));
                                oEP.setStatusCita(((String)vRowTemp.get(10)).charAt(4));
				oEP.getPaciente().getTipoSol().setValor(oEP.getPaciente().getTipoSol().buscaValorParametrizado(oEP.getPaciente().getTipoSol().getValor()));
                                oEP.setFechaAux(oEP.getFechaCita());
                                oEP.buscarCie10Reporte(oEP, oEP.getAreaServicio().getClave(), oEP.getPH().getNoTarjeta(), getPaciente().getFolioPaciente());
                                if(oEP.getStatusCita()=='0' && df.format(oEP.getFechaCita()).compareTo(df.format(hoy))<0)
                                    oEP.setAsistencia("NO ASISTIÓ");
                                else{
                                    if (oEP.getStatusCita()=='0' && df.format(oEP.getFechaCita()).compareTo(df.format(hoy))>=0)
                                        oEP.setAsistencia("EN ESPERA");
                                    else
                                        oEP.setAsistencia("ASISTIÓ");
                                }
                                vObj.add(oEP);
                            }
                         nTam = vObj.size();
                        arrRet = new Cita[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
			}
		    }
                 }
                 arrRetC4=arrRet;
           }
                arrRet=arrRetC4;
		return arrRet; 
	}
    //****************************************************************************
	    public String muestraTablas(int n){
        String mos="";
        if (n==0 && getMostrar()!=0)
            mos="mostrarbtn";
        if (n==1 && (getMostrar()==0 || getMostrar()==2))
            mos="mostrarbtn";
        if (n==2 && (getMostrar()==0 || getMostrar()==1))
            mos="mostrarbtn";
        return mos;
    }
    
    //*************************************************************************************************************************
	public int insertarReservacionCita(String sDiag, int nNumExpediente) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";

		 if( this==null){   //completar llave
			throw new Exception("Cita.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaReservacionCita('"+sUsuario+"', "+oPaciente.getFolioPaciente()+"::bigint, "+getNumAprox()+"::smallint, '"+
                                getTiempoAproxP()+"', "+oAreaServicio.getClave()+"::smallint, '"+sDiag+"', "+nNumExpediente+");";      
                        
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
	public List<Cita> buscarEstatusCitasReservadas(String sFechaI, String sFechaF) throws Exception{

	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;         
            
		sQuery = "SELECT * FROM buscaEstatusCitaReservada('"+sUsuario+"','"+sFechaI+"','"+sFechaF+"');"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Cita>();
			for (i = 0; i < rst.size(); i++) {
                            oCita = new Cita();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCita.getPaciente().setNombres((String)vRowTemp.get(0));
                            oCita.getPaciente().setApPaterno((String)vRowTemp.get(1));
                            oCita.getPaciente().setApMaterno((String)vRowTemp.get(2));
                            oCita.setStatusCitaP(obtenerValorParametrizacion((String)vRowTemp.get(3)));
                            oCita.setNumAprox(((Double) vRowTemp.get(4)).intValue());
                            oCita.setTiempoAproxP(obtenerValorParametrizacion((String)vRowTemp.get(5)));
                            oCita.setFechaCita((Date)vRowTemp.get(6));                           
                            vObj.add(oCita);
			}
		} 
                
		return vObj; 
	}
	//****************************************************************************************************************************************************
	public String obtenerValorParametrizacion(String sClave) throws Exception{
	ArrayList rst = null;
	String nRet = "";
	String sQuery = "";

		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaValorParametrizacion('"+sClave+"');";                                        
                        
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = (String)vRowTemp.get(0);
				}
			}
		} 
		return nRet; 
	}          
        
//************************************************************************************************************************* 

	public Date getFechaCita() {
	return dFechaCita;
	}

	public void setFechaCita(Date valor) {
	dFechaCita=valor;
	}

	public Date getFechaRegistro() {
	return dFechaRegistro;
	}
        
        public String getFecRegFmtFecha(){
            return calculaFechaHoraRegTexto(1);
        }
        public String getFecRegFmtHora(){
            return calculaFechaHoraRegTexto(2);
        }
        
        public String calculaFechaHoraRegTexto(int n){
        String f="";
        DateFormat fechaHora;
        if (n==1)
        fechaHora = new SimpleDateFormat("dd/MM/yyyy");
        else
            fechaHora = new SimpleDateFormat("HH:mm");
        if (dFechaRegistro==null)
            dFechaRegistro=new Date();
        f = fechaHora.format(dFechaRegistro);
        return f;
        }
        
        public Date getFechaDeHoy(){
        Date x=new Date();
            return x;
        }
        
        public String getFechaCitaTexto(int n){
        String f="";
        SimpleDateFormat fechaHora;
        if (n==1)
            fechaHora = new SimpleDateFormat("dd/MM/yyyy");
        else
            fechaHora = new SimpleDateFormat("HH:mm");
        f = fechaHora.format(dFechaCita);
        return f;
        }
        
        public String obtenFechaAux(){
        String f="";
        SimpleDateFormat fechaHora=new SimpleDateFormat("dd/MM/yyyy");      
        f = fechaHora.format(dFechaAux);
        return f;
        }
                
        public String horaTexto(int maximo, int citasAlMomento, String tarjeta) throws Exception{
        String h="";
        int h1, h2, h3;
        float h11, h22;
        h1=maximo/3;
        h11=maximo/3; 
        if ((h11-h1)>=0.5)
            h1=h1+1;
        h2=(maximo-h1)/2;
        h22=(maximo-h1)/2;
        if ((h22-h2)>=0.5)
            h2=h2+1;
        h3=maximo-h2-h1;
        int permiso=oPermiso.buscarPermisoHora(tarjeta, dFechaCita);
        if (permiso>0){bloque1=permiso; bloque2=bloque1+1; bloque3=bloque2+1;}
        else{bloque1=8; bloque2=9; bloque3=10;}
        
        if(citasAlMomento>=0 && citasAlMomento<h1)
            h=bloque1+":00";
        if (citasAlMomento>=h1 && citasAlMomento<(h1+h2))
            h=bloque2+":00";
        if (citasAlMomento>=(h1+h2))
            h=bloque3+":00";        
        if (h.length()<5)
            h="0"+h;
        return h;
  }
  
  public String horaTextoVes(String tarjeta, String turno) throws Exception{
        String h="";
        ArrayList rst = null;
	String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("EEEE");
        Calendar Cal=Calendar.getInstance();
        Cal.setTime(dFechaCita);
        String diaTexto="";
        int numDia=Cal.get(Calendar.DAY_OF_WEEK);
        switch(numDia){
            case 1: diaTexto="DOMINGO"; break;
            case 2: diaTexto="LUNES"; break;
            case 3: diaTexto="MARTES"; break;
            case 4: diaTexto="MIÉRCOLES"; break;
            case 5: diaTexto="JUEVES"; break;
            case 6: diaTexto="VIERNES"; break;
            case 7: diaTexto="SÁBADO"; break;
        }
        int permiso=oPermiso.buscarPermisoHora(tarjeta, dFechaCita);
        if (permiso>0){h=permiso+":00";}
        else{
            sQuery = "SELECT * FROM buscarhoraentrada('"+diaTexto+"', "+tarjeta+", '"+turno.substring(0, 3)+"');";
            oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                h=((String)vRowTemp.get(0)).charAt(0)+"";
                                h+=((String)vRowTemp.get(0)).charAt(1)+"";
                                h+=":00";
			}
        }
        if (h.length()<5)
            h="0"+h;
        return h;
  }
  
  //************************************************************************************************************
	public String obtenerFechaIngresoHosp() throws Exception{
	ArrayList rst = null;        
	String nRet = "";
	String sQuery = "";

		 if( oPaciente.getFolioPaciente()==0){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaFechaIngresoHosp("+getNumIngresoHosp()+"::bigint, "+oPaciente.getFolioPaciente()+"::bigint, "+getClaveEpisodio()+"::bigint);";                                        
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					setFechaIni((Date)vRowTemp.get(0));
				}
			}
                        SimpleDateFormat fecha=new SimpleDateFormat("yyyy-MM-dd");
                        nRet=fecha.format(getFechaIni());  
		} 
		return nRet; 
	}          
        
  //***********************************************************************************************************
  	public List<Cita> buscarCitasHojaAlta() throws Exception{

	Cita arrRet[]=null, oCita=null;
	ArrayList rst = null;
        ArrayList<Cita> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;         
        FacesMessage message=null;
        Date fechaActual=new Date();        
        SimpleDateFormat fecha=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat hora=new SimpleDateFormat("HH:mm");
        String FF=fecha.format(fechaActual);
        String h="";
            //buscaCitaHojaAlta(pnFolioPaciente Paciente.nFolioPaciente%TYPE, pdfechainicio date, pdfechafin date) 
		sQuery = "SELECT * FROM buscaCitaHojaAlta("+oPaciente.getFolioPaciente()+",'"+obtenerFechaIngresoHosp()+"','"+FF+"');"; 
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Cita>();
			for (i = 0; i < rst.size(); i++) {
                            oCita = new Cita();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);                           
                            oCita.setFechaCita((Date)vRowTemp.get(0));    
                            if(((Date)vRowTemp.get(0))!=null){
                                h=hora.format(oCita.getFechaCita());
                                oCita.setHoraCita(" - " +h);
                            }
                            if(oCita.getFechaCita()==null){
                                if(((String)vRowTemp.get(2)).equals("T450D"))
                                    oCita.setTiempoAproxP(((Double) vRowTemp.get(1)).intValue()+" DÍAS (PASAR A CONSULTA EXTERNA)");
                                else if(((String)vRowTemp.get(2)).equals("T450S"))
                                    oCita.setTiempoAproxP(((Double) vRowTemp.get(1)).intValue()+" SEMANAS (PASAR A CONSULTA EXTERNA)");
                                else if(((String)vRowTemp.get(2)).equals("T450M"))
                                    oCita.setTiempoAproxP(((Double) vRowTemp.get(1)).intValue()+" MESES (PASAR A CONSULTA EXTERNA)");
                            }else{
                                oCita.setTiempoAproxP("CITA CONFIRMADA");
                            }
                            oCita.getAreaServicio().setDescripcion((String)vRowTemp.get(3));                           
                            vObj.add(oCita);
                            modificaConExtHosp();
			}
		}
                if(rst.isEmpty()  && isOpcion()==true){
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Citas", "No hay citas reservadas o confirmadas para este paciente en caso de requerirlas ir a la opción de \"Reservar Cita\" del menú \"Citas\". Recuerde que si no hizo una reservación, aparecera que el paciente no requiere cita(s).");
                    RequestContext.getCurrentInstance().showMessageInDialog(message); 
                }
                setOpcion(false);
		return vObj; 
	}
	//****************************************************************************************************************************************************
  	public void modificaConExtHosp() throws Exception{
	ArrayList rst = null;        
        int nRet;
	String sQuery = "";

		 if( this==null){   //completar llave
			throw new Exception("Hospitalización.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaConExtHosp("+oPaciente.getFolioPaciente()+"::bigint, "+getClaveEpisodio()+"::bigint, "+getNumIngresoHosp()+"::bigint);";                                        
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
	}          
        
  //***********************************************************************************************************  
	public void setFechaRegistro(Date valor) {
            dFechaRegistro=valor;
	}

	public int getFolioCita() {
	return nFolioCita;
	}

	public void setFolioCita(int valor) {
	nFolioCita=valor;
	}

	public int getNoFicha() {
	return nNoFicha;
	}

	public void setNoFicha(int valor) {
	nNoFicha=valor;
	}

	public int getNumAprox() {
	return nNumAprox;
	}

	public void setNumAprox(int valor) {
	nNumAprox=valor;
	}

	public Parametrizacion getTiempoAprox() {
	return oTiempoAprox;
	}

	public void setTiempoAprox(Parametrizacion valor) {
	oTiempoAprox=valor;
	}

	public char getEmisorCita() {
	return sEmisorCita;
	}

	public void setEmisorCita(char valor) {
	sEmisorCita=valor;
	}

	public String getFolioPago() {
	return sFolioPago;
	}

	public void setFolioPago(String valor) {
	sFolioPago=valor;
	}

	public char getStatusCita() {
	return sStatusCita;
	}

	public void setStatusCita(char valor) {
	sStatusCita=valor;
	}

	public Paciente getPaciente() {
	return oPaciente;
	}

	public void setPaciente(Paciente valor) {
	oPaciente=valor;
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
	public PacienteCapasits getPacienteCapa() {
	return oPacCapa;
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
	public void setPacienteCapa(PacienteCapasits valor) {
	oPacCapa=valor;
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
	public String getHora() {
	return sHora;
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
	public void setHora(String valor) {
	sHora=valor;
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
	public String getFechaHora() throws ParseException{
            SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String fec=ff.format(this.dFechaCita);
            String fyh=fec+" "+getHora();
            
        return fyh;
        }
    //*********************************MÉTODO DE CAPASITS*****************************************
        public String getHoraAlmacenada() {
	return sHoranu;
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
	public void setHoraAlmacenada(Date valor) {
	SimpleDateFormat ff=new SimpleDateFormat("HH:mm");
            String fec=ff.format(valor);
         sHoranu=fec;
	}
	//*********************************MÉTODO DE CAPASITS*****************************************
	public Horario getHorarios(){
            return oHora;
        }
    //*********************************MÉTODO DE CAPASITS*****************************************
        public void setHorarios(Horario valor){
            oHora=valor;
        }
    //*********************************MÉTODO DE CAPASITS*****************************************     
        public Consultorio getNoconsult(){
            return oConsul;
        }
    //*********************************MÉTODO DE CAPASITS*****************************************    
        public void setNoconsult(Consultorio valor){
            oConsul=valor;
        }
    public int getNoConsulta() {
        return nNoConsulta;
    }

    public void setNoConsulta(int nNoConsulta) {
        this.nNoConsulta = nNoConsulta;
    }

    public String getDatosFechaFinal(){
        return sDatosFechaFinal;
    }
    
    public void setFechaIni(Date dFechaIni){
        this.dFechaIni=dFechaIni;
    }
    
    public Date getFechaIni(){
        return dFechaIni;
    }
    
    public void setFechaFin(Date dFechaFin){
        this.dFechaFin=dFechaFin;
    }
    
    public Date getFechaFin(){
        return dFechaFin;
    }
    
    public void setFechaAux(Date dFechaAux){
        this.dFechaAux=dFechaAux;
    }
    
    public Date getFechaAux(){
        return dFechaAux;
    }
    
    public AreaServicioHRRB getAreaServicio() {
        return oAreaServicio;
    }

    public void setAreaServicio(AreaServicioHRRB oAreaServicio) {
        this.oAreaServicio = oAreaServicio;
    }
    
    public PersonalHospitalario getPH() {
        return oPH;
    }

    public void setPH(PersonalHospitalario oPH) {
        this.oPH = oPH;
    }
    
     public int getNoConsultorio() {
        return nNoConsultorio;
    }

    public void setNoConsultorio(int nNoConsultorio) {
        this.nNoConsultorio = nNoConsultorio;
    }
    
    public int getMaximo() {
        return nMaximo;
    }

    public void setMaximo(int nMaximo) {
        this.nMaximo = nMaximo;
    }
    
    public int getCitados() {
        return nCitados;
    }

    public void setCitados(int nCitados) {
        this.nCitados = nCitados;
    }
  
    public void setHoraCita(String sHoraCita){
        this.sHoraCita=sHoraCita;
    }
    public String getHoraCita(){
        return sHoraCita;
    }
    public void setPrimHos(String primHos){
        this.primHos=primHos;
    }
    public String getPrimHos(){
        return primHos;
    }
    public void setPrimEsp(String primEsp){
        this.primEsp=primEsp;
    }
    public String getPrimEsp(){
        return primEsp;
    }

    public DiagnosticoCIE10 getDiag() {
        return oDiag;
    }

    public void setDiag(DiagnosticoCIE10 oDiag) {
        this.oDiag = oDiag;
    }

    public boolean isOpcion() {
        return bOpcion;
    }

    public void setOpcion(boolean bOpcion) {
        this.bOpcion = bOpcion;
    }

    public int getEP() {
        return nEP;
    }

    public void setEP(int nEP) {
        this.nEP = nEP;
    }
    
        
    public int[] getArrCitados() {
        return nArrCitados;
    }

    public void setArrCitados(int[] nCitados) {
        this.nArrCitados = nCitados;
    }
    
    public void setConsul2(Date dConsul2){
        this.dConsul2=dConsul2;
    }
    
    public Date getConsul2(){
        return dConsul2;
    }
    
    public void iniciaConsulta1(){
        nCon1=0;
    }
    
    public void iniciaConsulta2(){
        nCon2=0;
    }
    
    public void iniciaConsulta3(){
        nCon3=0;
    }
    
    public void iniciaConsulta4(){
        nCon4=0;
    }

    public String getIdUsuario() {
        return sIdUsuario;
    }

    public void setIdUsuario(String sIdUsuario) {
        this.sIdUsuario = sIdUsuario;
    }

    public String getAsistencia() {
        return sAsistencia;
    }

    public void setAsistencia(String sAsistencia) {
        this.sAsistencia = sAsistencia;
    }
    
    public int getMostrar() {
        return mostrar;
    }

    public void setMostrar(int mostrar) {
        this.mostrar = mostrar;
    }

    public String getTurno() {
        return sTurno;
    }

    public void setTurno(String sTurno) {
        this.sTurno = sTurno;
    }

    public String getTiempoAproxP() {
        return sTiempoAproxP;
    }

    public void setTiempoAproxP(String sTiempoAproxP) {
        this.sTiempoAproxP = sTiempoAproxP;
    }

    public String getStatusCitaP() {
        return sStatusCitaP;
    }

    public void setStatusCitaP(String sStatusCitaP) {
        this.sStatusCitaP = sStatusCitaP;
    }

    /**
     * @return the nClaveEpisodio
     */
    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    /**
     * @param nClaveEpisodio the nClaveEpisodio to set
     */
    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    /**
     * @return the nNumIngresoHosp
     */
    public long getNumIngresoHosp() {
        return nNumIngresoHosp;
    }

    /**
     * @param nNumIngresoHosp the nNumIngresoHosp to set
     */
    public void setNumIngresoHosp(long nNumIngresoHosp) {
        this.nNumIngresoHosp = nNumIngresoHosp;
    }    
} 

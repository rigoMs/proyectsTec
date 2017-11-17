package mx.gob.hrrb.modelo.capasits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Seguro;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author pedro
 */
public class CitamedicinaGeneralCapasits extends Cita implements Serializable {
    private AccesoDatos oAD;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private boolean bEnfermedadesTransmisibles;
    private boolean bEnfermedadesCroncronicas;
    private boolean bOtrasEnfermedades;
    private boolean bPlanificacion;
    private boolean bPrimerTrimestre;
    private boolean bSegundoTrimestre;
    private boolean bTercerTrimestre;
    private boolean bAnalisisClinocos;
    private boolean bAltoRiesgo;
    private boolean bApoyoTrasladoObstetrico;
    private boolean bAcidoFolico;
    private String sNutricio="--";
    private String sDesnutricion="--";
    private String sHidratacion="--";
    private boolean bPreservativo;
    private boolean bAnticoncepcionEmergencia;
    private boolean bOtro;
    private boolean bTuberculosis;
    private boolean bsifilis;
    private boolean bVih;
    private boolean bdislipidemia;
    private String sObservaciones;
    private String sEnfermedadesTras="--";
    private String sEnfermedadesCron="--";
    private String sOtrasEnfer="--";
    private String sPlanifica="--";
    private String sPrimerTri="--";
    private String sSegundoTri="--";
    private String sTercerTri="--";
    private String sAnalisisClinico="--";
    private String sAltories="--";
    private String sApoyoTrasladoObs="--";
    private String sAcidoFol="--";
    private String sPreserva="--";
    private String sAnticoncepcion="--";
    private String sOtr="--";
    private String sSifil="--";
    private String sTubercu="--";
    private String sVi="--";
    private String sDislipi="--";
    private String sObesSobre="--";
    private String sNormal="--"; 
    private String sLeve="-";
    private String sModerada="--";
    private String sGrave="--";
    private String sPlanA="--";
    private String sPlanB="--";
    private String sPlanC="--";
    private String sEdad="";
    private String sDisc="--";
    private String sIndi="--";
    private String sSeguro="--"; 
    private String sPrimeraVez="__";
    private String sSubSecuente="_";
    private String sEspontaneo="";
    private String sreferido="_";
    private int nNum;
    private int nNumAsistencia;
    private PacienteCapasits oPacCap;
    private Expediente oExp;
    private Medico oMed;

    public CitamedicinaGeneralCapasits(boolean bEnfermedadesTransmisibles, boolean bEnfermedadesCroncronicas, boolean bOtrasEnfermedades, boolean bPlanificacion, boolean bPrimerTrimestre, boolean bSegundoTrimestre, boolean bTercerTrimestre, boolean bAnalisisClinocos, boolean bAltoRiesgo, boolean bApoyoTrasladoObstetrico, boolean bAcidoFolico, String sNutricio, String sDesnutricion, String sHidratacion, boolean bPreservativo, boolean bAnticoncepcionEmergencia, boolean bOtro, boolean bTuberculosis, boolean bsifilis, boolean bVih, boolean bdislipidemia, String sObservaciones, Date dFechaCita, Date dFechaRegistro, int nFolioCita, int nNoFicha, int nNumAprox, Parametrizacion oTiempoAprox, char sEmisorCita, String sFolioPago, char sStatusCita, Paciente oPaciente) {
        super(dFechaCita, dFechaRegistro, nFolioCita, nNoFicha, nNumAprox, oTiempoAprox, sEmisorCita, sFolioPago, sStatusCita, oPaciente);
        this.bEnfermedadesTransmisibles = bEnfermedadesTransmisibles;
        this.bEnfermedadesCroncronicas = bEnfermedadesCroncronicas;
        this.bOtrasEnfermedades = bOtrasEnfermedades;
        this.bPlanificacion = bPlanificacion;
        this.bPrimerTrimestre = bPrimerTrimestre;
        this.bSegundoTrimestre = bSegundoTrimestre;
        this.bTercerTrimestre = bTercerTrimestre;
        this.bAnalisisClinocos = bAnalisisClinocos;
        this.bAltoRiesgo = bAltoRiesgo;
        this.bApoyoTrasladoObstetrico = bApoyoTrasladoObstetrico;
        this.bAcidoFolico = bAcidoFolico;
        this.sNutricio = sNutricio;
        this.sDesnutricion = sDesnutricion;
        this.sHidratacion = sHidratacion;
        this.bPreservativo = bPreservativo;
        this.bAnticoncepcionEmergencia = bAnticoncepcionEmergencia;
        this.bOtro = bOtro;
        this.bTuberculosis = bTuberculosis;
        this.bsifilis = bsifilis;
        this.bVih = bVih;
        this.bdislipidemia = bdislipidemia;
        this.sObservaciones = sObservaciones;
    }
    
    public CitamedicinaGeneralCapasits(){
        oPacCap= new PacienteCapasits();
        oExp=new Expediente();
        oMed= new Medico();
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
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CitaMedicinaGeneralCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCitaMedicinaGeneralCapasits("+getFolioCita()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setEnfermedadesTras((String)vRowTemp.get(1));
                                this.setEnfermedadesCron((String)vRowTemp.get(2));
                                this.setOtrasEnfer((String)vRowTemp.get(3));
                                this.setPlanifica((String)vRowTemp.get(4));
                                this.setPrimerTri((String)vRowTemp.get(5));
                                this.setSegundoTri((String)vRowTemp.get(6));
                                this.setTercerTri((String)vRowTemp.get(7));
                                this.setAnalisisClinico((String)vRowTemp.get(8));
                                this.setAltories((String)vRowTemp.get(9));
                                this.setApoyoTrasladoObs((String)vRowTemp.get(10));
                                this.setAcidoFol((String)vRowTemp.get(11));
                                this.setNutricio((String)vRowTemp.get(12));
                                this.setDesnutricion((String)vRowTemp.get(13));
                                this.setHidratacion((String)vRowTemp.get(14));
                                this.setPreserva((String)vRowTemp.get(15));
                                this.setAnticoncepcion((String)vRowTemp.get(16));
                                this.setOtr((String)vRowTemp.get(17));
                                this.setTuber((String)vRowTemp.get(18));
                                this.setSifil((String)vRowTemp.get(19));
                                this.setVi((String)vRowTemp.get(20));
                                this.setDislipi((String)vRowTemp.get(21));
                                this.setObservaciones((String)vRowTemp.get(22));
				bRet = true; System.out.println(getDesnutricion()+" "+getHidratacion());
                                convertirdatos2(); 
                        }
		} 
		return bRet; 
	}
    
        public boolean buscarMedicoDeHoja() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaMedicoDeHoja('"+sUsuario+"');"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.oMed.setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                            this.oMed.setNombres((String)vRowTemp.get(1));
                            this.oMed.setApPaterno((String)vRowTemp.get(2));
                            this.oMed.setApMaterno((String)vRowTemp.get(3));
                            this.oMed.getCons().setNoConsultorio(((Double)vRowTemp.get(4)).intValue());
                            this.oMed.getHorarios().setClave(((Double)vRowTemp.get(5)).intValue());
				bRet = true;
			}
		} 
		return bRet; 
	}
        
        public int asistencia(long FolioPac) throws Exception{
	int valor =0;
	ArrayList rst = null;
	String sQuery = "";
        if(getFechaCita()==null){
            Date fecha=new Date();
            SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
            String fec=ff.format(fecha);
            Date fech=ff.parse(fec);
            setFechaCita(fech);
        }
		 if(this==null){   //completar llave
			throw new Exception("buscaCitaPacienteMedicinaGeneralCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaCitaPacienteMedicinaGeneralCapasits('"+getFechaCita()+"',"+FolioPac+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			valor=rst.size();
		} 
		return valor; 
	}
    
	public CitamedicinaGeneralCapasits[] buscarTodos() throws Exception{
	CitamedicinaGeneralCapasits arrRet[]=null, oCitamedicinaGeneralCapasits=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<CitamedicinaGeneralCapasits> vObj=null;
	int i=0, nTam=0, n=1;
        String fec2; 
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String fec=ff.format(fecha);
        Date fech=ff.parse(fec);
		sQuery = "SELECT * FROM buscaTodosCitaMedicinaGeneralCapasits('2015-03-10',"+oMed.getCons().getNoConsultorio()+"::smallint,"+oMed.getHorarios().getClave()+"::smallint);"; 
		oAD=new AccesoDatos(); System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<CitamedicinaGeneralCapasits>();
			for (i = 0; i < rst.size(); i++) {
                             oCitamedicinaGeneralCapasits=new CitamedicinaGeneralCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCitamedicinaGeneralCapasits.getPac().setPrimerFechaIngreso((Date)vRowTemp.get(0));
                            oCitamedicinaGeneralCapasits.getPac().setNombres((String)vRowTemp.get(1));
                            oCitamedicinaGeneralCapasits.getPac().setApPaterno((String)vRowTemp.get(2));
                            oCitamedicinaGeneralCapasits.getPac().setApMaterno((String)vRowTemp.get(3));
                            oCitamedicinaGeneralCapasits.getPac().setFechaNac((Date)vRowTemp.get(4));
                            oCitamedicinaGeneralCapasits.getPac().setSexos((String)vRowTemp.get(5));
                            oCitamedicinaGeneralCapasits.getExp().setNumero(((Double) vRowTemp.get(6)).intValue());
                            oCitamedicinaGeneralCapasits.setFolioCita(((Double) vRowTemp.get(7)).intValue());
                            oCitamedicinaGeneralCapasits.setEnfermedadesTras((String)vRowTemp.get(8));
                            oCitamedicinaGeneralCapasits.setEnfermedadesCron((String)vRowTemp.get(9));
                            oCitamedicinaGeneralCapasits.setOtrasEnfer((String)vRowTemp.get(10));
                            oCitamedicinaGeneralCapasits.setPlanifica((String)vRowTemp.get(11));
                            oCitamedicinaGeneralCapasits.setPrimerTri((String)vRowTemp.get(12));
                            oCitamedicinaGeneralCapasits.setSegundoTri((String)vRowTemp.get(13));
                            oCitamedicinaGeneralCapasits.setTercerTri((String)vRowTemp.get(14));
                            oCitamedicinaGeneralCapasits.setAnalisisClinico((String)vRowTemp.get(15));
                            oCitamedicinaGeneralCapasits.setAltories((String)vRowTemp.get(16));
                            oCitamedicinaGeneralCapasits.setApoyoTrasladoObs((String)vRowTemp.get(17));
                            oCitamedicinaGeneralCapasits.setAcidoFol((String)vRowTemp.get(18));
                            oCitamedicinaGeneralCapasits.setNutricio((String)vRowTemp.get(19));
                            oCitamedicinaGeneralCapasits.setDesnutricion((String)vRowTemp.get(20));
                            oCitamedicinaGeneralCapasits.setHidratacion((String)vRowTemp.get(21));
                            oCitamedicinaGeneralCapasits.setPreserva((String)vRowTemp.get(22));
                            oCitamedicinaGeneralCapasits.setAnticoncepcion((String)vRowTemp.get(23));
                            oCitamedicinaGeneralCapasits.setOtr((String)vRowTemp.get(24));
                            oCitamedicinaGeneralCapasits.setTuber((String)vRowTemp.get(25));
                            oCitamedicinaGeneralCapasits.setSifil((String)vRowTemp.get(26));
                            oCitamedicinaGeneralCapasits.setVi((String)vRowTemp.get(27));
                            oCitamedicinaGeneralCapasits.setDislipi((String)vRowTemp.get(28));
                            oCitamedicinaGeneralCapasits.setObservaciones((String)vRowTemp.get(29));
                            oCitamedicinaGeneralCapasits.setDiscapacitado((String)vRowTemp.get(30));
                            oCitamedicinaGeneralCapasits.setIndigena((String)vRowTemp.get(31));
                            oCitamedicinaGeneralCapasits.SetSeguroPop((String)vRowTemp.get(32));
                            oCitamedicinaGeneralCapasits.getPac().setIdNacional(((Double) vRowTemp.get(33)).intValue());
                            oCitamedicinaGeneralCapasits.getPac().setFolioPaciente(((Double) vRowTemp.get(34)).intValue());
                            oCitamedicinaGeneralCapasits.setFechaRegistro((Date)vRowTemp.get(35));
                            if(oCitamedicinaGeneralCapasits.getEnfermedadesTras().equals(" "))oCitamedicinaGeneralCapasits.setEnfermedadesTras("--");
                            if(oCitamedicinaGeneralCapasits.getEnfermedadesCron().equals(" "))oCitamedicinaGeneralCapasits.setEnfermedadesCron("--");
                            if(oCitamedicinaGeneralCapasits.getOtrasEnfer().equals(" "))oCitamedicinaGeneralCapasits.setOtrasEnfer("--");
                            if(oCitamedicinaGeneralCapasits.getPlanifica().equals(" "))oCitamedicinaGeneralCapasits.setPlanifica("--");
                            if(oCitamedicinaGeneralCapasits.getPrimerTri().compareTo("X")!=0)oCitamedicinaGeneralCapasits.setPrimerTri("--");
                            if(oCitamedicinaGeneralCapasits.getSegundoTri().equals(" "))oCitamedicinaGeneralCapasits.setSegundoTri("--");
                            if(oCitamedicinaGeneralCapasits.getTercerTri().equals(" "))oCitamedicinaGeneralCapasits.setTercerTri("--");
                            if(oCitamedicinaGeneralCapasits.getAnalisisClinico().equals(" "))oCitamedicinaGeneralCapasits.setAnalisisClinico("--");
                            if(oCitamedicinaGeneralCapasits.getAltories().equals(" "))oCitamedicinaGeneralCapasits.setAltories("--");
                            if(oCitamedicinaGeneralCapasits.getApoyoTrasladoObs().equals(" "))oCitamedicinaGeneralCapasits.setApoyoTrasladoObs("--");
                            if(oCitamedicinaGeneralCapasits.getAcidoFol().equals(" "))oCitamedicinaGeneralCapasits.setAcidoFol("--");
                            if(oCitamedicinaGeneralCapasits.getPreserva().equals(" "))oCitamedicinaGeneralCapasits.setPreserva("--");
                            if(oCitamedicinaGeneralCapasits.getAnticoncepcion().equals(" "))oCitamedicinaGeneralCapasits.setAnticoncepcion("--");
                            if(oCitamedicinaGeneralCapasits.getOtr().equals(" "))oCitamedicinaGeneralCapasits.setOtr("--");
                            if(oCitamedicinaGeneralCapasits.getTuber().equals(" "))oCitamedicinaGeneralCapasits.setTuber("--");
                            if(oCitamedicinaGeneralCapasits.getSifil().equals(" "))oCitamedicinaGeneralCapasits.setSifil("--");
                            if(oCitamedicinaGeneralCapasits.getVi().equals(" "))oCitamedicinaGeneralCapasits.setVi("--");
                            if(oCitamedicinaGeneralCapasits.getDislipi().equals(" "))oCitamedicinaGeneralCapasits.setDislipi("--");
                            
                            if(oCitamedicinaGeneralCapasits.getNutricio().equals("1")){oCitamedicinaGeneralCapasits.setObessobre("X"); oCitamedicinaGeneralCapasits.setNormal("--");}
                            else{ if(oCitamedicinaGeneralCapasits.getNutricio().equals("2")){oCitamedicinaGeneralCapasits.setNormal("X"); oCitamedicinaGeneralCapasits.setObessobre("--");}
                            else {oCitamedicinaGeneralCapasits.setObessobre("--"); oCitamedicinaGeneralCapasits.setNormal("--");}
                            }
                            if (oCitamedicinaGeneralCapasits.getDesnutricion().equals("1")) {oCitamedicinaGeneralCapasits.setLeve("X"); oCitamedicinaGeneralCapasits.setModerada("--"); oCitamedicinaGeneralCapasits.setGrave("--");}  
                            else{if (oCitamedicinaGeneralCapasits.getDesnutricion().equals("2")) {oCitamedicinaGeneralCapasits.setLeve("--"); oCitamedicinaGeneralCapasits.setModerada("X"); oCitamedicinaGeneralCapasits.setGrave("--");} 
                            else{if (oCitamedicinaGeneralCapasits.getDesnutricion().equals("3")) {oCitamedicinaGeneralCapasits.setLeve("--"); oCitamedicinaGeneralCapasits.setModerada("--"); oCitamedicinaGeneralCapasits.setGrave("X");}
                            else {oCitamedicinaGeneralCapasits.setLeve("--"); oCitamedicinaGeneralCapasits.setModerada("--"); oCitamedicinaGeneralCapasits.setGrave("--");}
                            }
                            }
                            if(oCitamedicinaGeneralCapasits.getHidratacion().equals("1")){oCitamedicinaGeneralCapasits.setPlanA("X"); oCitamedicinaGeneralCapasits.setPlanB("_"); oCitamedicinaGeneralCapasits.setPlanC("_");}
                            else{if(oCitamedicinaGeneralCapasits.getHidratacion().equals("2")){oCitamedicinaGeneralCapasits.setPlanA("_"); oCitamedicinaGeneralCapasits.setPlanB("X"); oCitamedicinaGeneralCapasits.setPlanC("_");}
                            else{if(oCitamedicinaGeneralCapasits.getHidratacion().equals("3")){oCitamedicinaGeneralCapasits.setPlanA("_"); oCitamedicinaGeneralCapasits.setPlanB("_"); oCitamedicinaGeneralCapasits.setPlanC("X");}
                            else{oCitamedicinaGeneralCapasits.setPlanA("_"); oCitamedicinaGeneralCapasits.setPlanB("_"); oCitamedicinaGeneralCapasits.setPlanC("_");}     
                            }
                            }
                            oCitamedicinaGeneralCapasits.setNumAsistencia(asistencia(oCitamedicinaGeneralCapasits.getPac().getFolioPaciente()));
                            oCitamedicinaGeneralCapasits.setNum(n++);
                            oCitamedicinaGeneralCapasits.SetEdad(oCitamedicinaGeneralCapasits.getPac().getFechaNac());
                            if(oCitamedicinaGeneralCapasits.getPac().getSexos().equals("F"))oCitamedicinaGeneralCapasits.getPac().setSexos("F ");
                            fec2=ff.format(oCitamedicinaGeneralCapasits.getFechaRegistro());
                            if(fec2.equals(fec)) oCitamedicinaGeneralCapasits.setEspontaneo("*");
                            oCitamedicinaGeneralCapasits.setReferido("X");
                            vObj.add(oCitamedicinaGeneralCapasits);
                            if(oCitamedicinaGeneralCapasits.getPac().getPrimerFechaIngreso()==fech){oCitamedicinaGeneralCapasits.setPrimeraVez("X"); oCitamedicinaGeneralCapasits.setSubsecuente("_");}
                            else{oCitamedicinaGeneralCapasits.setPrimeraVez("_"); oCitamedicinaGeneralCapasits.setSubsecuente("X");}
                        } 
                        nTam = vObj.size();
                        arrRet = new CitamedicinaGeneralCapasits[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} setNum(n);
		return arrRet; 
	} 
        
        public CitamedicinaGeneralCapasits[] buscarPruebasDeHoy(int num) throws Exception{
	CitamedicinaGeneralCapasits arrRet[]=null, oCitamedicinaGeneralCapasits=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<CitamedicinaGeneralCapasits> vObj=null;
	int i=0, nTam=0;
		sQuery = "SELECT * FROM  buscaPruebasRapidas('2015-03-01','"+sUsuario+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){  System.out.println(sQuery);
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                    vObj = new ArrayList<CitamedicinaGeneralCapasits>();
			for (i = 0; i < rst.size(); i++) {
			 oCitamedicinaGeneralCapasits=new CitamedicinaGeneralCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCitamedicinaGeneralCapasits.getPac().setNombres((String)vRowTemp.get(0));
                            oCitamedicinaGeneralCapasits.getPac().setApPaterno((String)vRowTemp.get(1));
                            oCitamedicinaGeneralCapasits.getPac().setApMaterno((String)vRowTemp.get(2));
                            oCitamedicinaGeneralCapasits.getPac().setSexos((String)vRowTemp.get(3));
                            oCitamedicinaGeneralCapasits.sEdad=""+(((Double) vRowTemp.get(4)).intValue());
                            oCitamedicinaGeneralCapasits.setObservaciones((String)vRowTemp.get(5));
                            oCitamedicinaGeneralCapasits.setNum(num++);
                            oCitamedicinaGeneralCapasits.setEspontaneo("PR");
                            vObj.add(oCitamedicinaGeneralCapasits);
			} 
                        nTam = vObj.size();
                        arrRet = new CitamedicinaGeneralCapasits[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	}
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "", nutri="", desnutri="",hidrata=""; convertirdatos();
        if(getNutricio()!=null) nutri=getNutricio(); if(getDesnutricion()!=null)desnutri=getDesnutricion(); if(getHidratacion()!=null)hidrata=getHidratacion();
        		 if( this.getFolioCita()==0){   //completar llave
			throw new Exception("CitaMedicinaGeneralCapasits.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCitaMedicinaGeneralCapasits('"+sUsuario+"',"+getFolioCita()+",'"+getEnfermedadesTras()+"','"+getEnfermedadesCron()+"','"+getOtrasEnfer()+"','"+getPlanifica()+"','"+getPrimerTri()+"','"+getSegundoTri()+"','"+getTercerTri()+"','"+getAnalisisClinico()+"','"+getAltories()
                                +"','"+getApoyoTrasladoObs()+"','"+getAcidoFol()+"','"+nutri+"','"+desnutri+"','"+hidrata+"','"+getPreserva()+"','"+getAnticoncepcion()+"','"+getOtr()+"','"+getTuber()+"','"+getSifil()+"','"+getVi()+"','"+getDislipi()+"','"+getObservaciones()+"');"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
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
	String sQuery = "", nutri="", desnutri="",hidrata=""; convertirdatos();
        if(getNutricio()!=null) nutri=getNutricio(); if(getDesnutricion()!=null)desnutri=getDesnutricion(); if(getHidratacion()!=null)hidrata=getHidratacion();

		 if( this.getFolioCita()==0){   //completar llave
			throw new Exception("CitaTrabajoSocialCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCitaMedicinaGeneralCapasits('"+sUsuario+"',"+getFolioCita()+",'"+getEnfermedadesTras()+"','"+getEnfermedadesCron()+"','"+getOtrasEnfer()+"','"+getPlanifica()+"','"+getPrimerTri()+"','"+getSegundoTri()+"','"+getTercerTri()+"','"+getAnalisisClinico()+"','"+getAltories()
                                +"','"+getApoyoTrasladoObs()+"','"+getAcidoFol()+"','"+nutri+"','"+desnutri+"','"+hidrata+"','"+getPreserva()+"','"+getAnticoncepcion()+"','"+getOtr()+"','"+getTuber()+"','"+getSifil()+"','"+getVi()+"','"+getDislipi()+"','"+getObservaciones()+"');"; 

			oAD=new AccesoDatos(); System.out.println(sQuery);
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
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CitaTrabajoSocialCapasits.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCitaMedicinaGeneralCapasits();"; 
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

    public boolean getEnfermedadesTransmisibles() {
        return bEnfermedadesTransmisibles;
    }

    public void setEnfermedadesTransmisibles(boolean bEnfermedadesTransmisibles) {
        this.bEnfermedadesTransmisibles = bEnfermedadesTransmisibles;
    }

    public boolean getEnfermedadesCroncronicas() {
        return bEnfermedadesCroncronicas;
    }

    public void setEnfermedadesCroncronicas(boolean bEnfermedadesCroncronicas) {
        this.bEnfermedadesCroncronicas = bEnfermedadesCroncronicas;
    }

    public boolean getOtrasEnfermedades() {
        return bOtrasEnfermedades;
    }

    public void setOtrasEnfermedades(boolean bOtrasEnfermedades) {
        this.bOtrasEnfermedades = bOtrasEnfermedades;
    }

    public boolean getPlanificacion() {
        return bPlanificacion;
    }

    public void setPlanificacion(boolean bPlanificacion) {
        this.bPlanificacion = bPlanificacion;
    }

    public boolean getPrimerTrimestre() {
        return bPrimerTrimestre;
    }

    public void setPrimerTrimestre(boolean bPrimerTrimestre) {
        this.bPrimerTrimestre = bPrimerTrimestre;
    }

    public boolean getSegundoTrimestre() {
        return bSegundoTrimestre;
    }

    public void setSegundoTrimestre(boolean bSegundoTritrimestre) {
        this.bSegundoTrimestre = bSegundoTritrimestre;
    }

    public boolean getTercerTrimestre() {
        return bTercerTrimestre;
    }

    public void setTercerTrimestre(boolean bTercerTrimestre) {
        this.bTercerTrimestre = bTercerTrimestre;
    }

    public boolean getAnalisisClinocos() {
        return bAnalisisClinocos;
    }

    public void setAnalisisClinocos(boolean bAnalisisClinocos) {
        this.bAnalisisClinocos = bAnalisisClinocos;
    }

    public boolean getAltoRiesgo() {
        return bAltoRiesgo;
    }

    public void setAltoRiesgo(boolean bAltoRiesgo) {
        this.bAltoRiesgo = bAltoRiesgo;
    }

    public boolean getApoyoTrasladoObstetrico() {
        return bApoyoTrasladoObstetrico;
    }

    public void setApoyoTrasladoObstetrico(boolean bApoyoTrasladoObstetrico) {
        this.bApoyoTrasladoObstetrico = bApoyoTrasladoObstetrico;
    }

    public boolean getAcidoFolico() {
        return bAcidoFolico;
    }

    public void setAcidoFolico(boolean bAcidoFolico) {
        this.bAcidoFolico = bAcidoFolico;
    }

    public String getNutricio() {
        return sNutricio;
    }

    public void setNutricio(String sNutricio) {
        this.sNutricio =sNutricio;
    }

    public String getDesnutricion() {
        return sDesnutricion;
    }

    public void setDesnutricion(String sDesnutricion) {
        this.sDesnutricion =sDesnutricion;
    }

    public String getHidratacion() {
        return sHidratacion;
    }

    public void setHidratacion(String sHidratacion) {
        this.sHidratacion =sHidratacion;
    }

    public boolean getPreservativo() {
        return bPreservativo;
    }

    public void setPreservativo(boolean bPreservativo) {
        this.bPreservativo = bPreservativo;
    }

    public boolean getAnticoncepcionEmergencia() {
        return bAnticoncepcionEmergencia;
    }

    public void setAnticoncepcionEmergencia(boolean bAnticoncepcionEmergencia) {
        this.bAnticoncepcionEmergencia = bAnticoncepcionEmergencia;
    }

    public boolean getOtro() {
        return bOtro;
    }

    public void setOtro(boolean bOtro) {
        this.bOtro = bOtro;
    }

    public boolean getTuberculosis() {
        return bTuberculosis;
    }

    public void setTuberculosis(boolean bTuberculosis) {
        this.bTuberculosis = bTuberculosis;
    }

    public boolean getSifilis() {
        return bsifilis;
    }

    public void setSifilis(boolean bsifilis) { System.out.println(bsifilis);
        this.bsifilis = bsifilis;
    }

    public boolean getVih() {
        return bVih;
    }

    public void setVih(boolean bVih) {
        this.bVih = bVih;
    }

    public boolean getDislipidemia() {
        return bdislipidemia;
    }

    public void setDislipidemia(boolean bdislipidemia) {
        this.bdislipidemia = bdislipidemia;
    }

    public String getObservaciones() {
        return sObservaciones;
    }

    public void setObservaciones(String sObservaciones) {
        this.sObservaciones = sObservaciones;
    }

    public String getEnfermedadesTras() {
        return sEnfermedadesTras;
    }

    public void setEnfermedadesTras(String sEnfermedadesTras) {
        this.sEnfermedadesTras = sEnfermedadesTras;
    }

    public String getEnfermedadesCron() {
        return sEnfermedadesCron;
    }

    public void setEnfermedadesCron(String sEnfermedadesCron) {
        this.sEnfermedadesCron = sEnfermedadesCron;
    }

    public String getOtrasEnfer() {
        return sOtrasEnfer;
    }

    public void setOtrasEnfer(String sOtrasEnfer) {
        this.sOtrasEnfer = sOtrasEnfer;
    }

    public String getPlanifica() {
        return sPlanifica;
    }

    public void setPlanifica(String sPanifica) {
        this.sPlanifica = sPanifica;
    }

    public String getPrimerTri() {
        return sPrimerTri;
    }

    public void setPrimerTri(String sPrimerTri) {
        this.sPrimerTri = sPrimerTri;
    }

    public String getSegundoTri() {
        return sSegundoTri;
    }

    public void setSegundoTri(String sSegundoTri) {
        this.sSegundoTri = sSegundoTri;
    }

    public String getTercerTri() {
        return sTercerTri;
    }

    public void setTercerTri(String sTercerTri) {
        this.sTercerTri = sTercerTri;
    }

    public String getAnalisisClinico() {
        return sAnalisisClinico;
    }

    public void setAnalisisClinico(String sAnalisisClinico) {
        this.sAnalisisClinico = sAnalisisClinico;
    }

    public String getAltories() {
        return sAltories;
    }

    public void setAltories(String sAltories) {
        this.sAltories = sAltories;
    }

    public String getApoyoTrasladoObs() {
        return sApoyoTrasladoObs;
    }

    public void setApoyoTrasladoObs(String sApoyoTrasladoObs) {
        this.sApoyoTrasladoObs = sApoyoTrasladoObs;
    }

    public String getAcidoFol() {
        return sAcidoFol;
    }

    public void setAcidoFol(String sAcidoFol) {
        this.sAcidoFol = sAcidoFol;
    }

    public String getPreserva() {
        return sPreserva;
    }

    public void setPreserva(String sPreserva) {
        this.sPreserva = sPreserva;
    }

    public String getAnticoncepcion() {
        return sAnticoncepcion;
    }

    public void setAnticoncepcion(String sAnticoncepcion) {
        this.sAnticoncepcion = sAnticoncepcion;
    }

    public String getOtr() {
        return sOtr;
    }

    public void setOtr(String sOtr) {
        this.sOtr = sOtr;
    }

    public String getSifil() {
        return sSifil;
    }

    public void setSifil(String sSifil) {
        this.sSifil = sSifil;
    }

    public String getVi() {
        return sVi;
    }

    public void setVi(String sVi) {
        this.sVi = sVi;
    }

    public String getDislipi() {
        return sDislipi;
    }

    public void setDislipi(String sDislipi) {
        this.sDislipi = sDislipi;
    }
       
    public String getTuber() {
        return sTubercu;
    }

    public void setTuber(String valor) {
        this.sTubercu = valor;
    }
    
    public String getObesSobre(){
        return sObesSobre;
    }
    
    public void setObessobre(String valor){
        this.sObesSobre=valor;
    }
    
    public String getNormal(){
        return sNormal;
    }
    
    public void setNormal(String valor){
        this.sNormal=valor;
    }
    
    public String getLeve(){
        return sLeve;
    }
    
    public void setLeve(String valor){
        this.sLeve=valor;
    }

    public String getModerada() {
        return sModerada;
    }

    public void setModerada(String sModerada) {
        this.sModerada = sModerada;
    }

    public String getGrave() {
        return sGrave;
    }

    public void setGrave(String sGrave) {
        this.sGrave = sGrave;
    }

    public String getPlanA() {
        return sPlanA;
    }

    public void setPlanA(String sPlanA) {
        this.sPlanA = sPlanA;
    }

    public String getPlanB() {
        return sPlanB;
    }

    public void setPlanB(String sPlanB) {
        this.sPlanB = sPlanB;
    }

    public String getPlanC() {
        return sPlanC;
    }

    public void setPlanC(String sPlanC) {
        this.sPlanC = sPlanC;
    }
          
    public int getNum(){
        return nNum;
    }
    
    public void setNum(int valor){
        nNum=valor;
    }
    
    public int getNumAsistencia(){
        return nNumAsistencia;
    }
    
    public void setNumAsistencia(int valor){
        this.nNumAsistencia=valor;
    }
    
    public String getSeguroPop(){
        return sSeguro;
    }
    
    public void SetSeguroPop(String valor){
        if(valor.equals("T0108")) sSeguro="X";
        else sSeguro="_";
    }
    
    public String getPrimeraVez(){
        return sPrimeraVez;
    }
    
    public void setPrimeraVez(String valor){
        sPrimeraVez=valor;
    }
    
    public String getSubsecuente(){
        return sSubSecuente;
    }
    
    public void setSubsecuente(String valor){
        sSubSecuente=valor;
    }
    
    public PacienteCapasits getPac(){
        return oPacCap;
    }
    
    public void setPac(PacienteCapasits valor){
        oPacCap=valor;
    }
    
    public Expediente getExp(){
        return oExp;
    }
    
    public void setExp(Expediente valor){
        oExp=valor;
    }
        
    public String getEdad(){
        return sEdad;
    }
    
    public void SetEdad(Date fecha){
        Date fActual=new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechan=formato.format(fecha);
        String x[]=fechan.split("/");
        String fecAct=formato.format(fActual);
        int d1=Integer.parseInt(x[0]);
        int m1=Integer.parseInt(x[1]);
        int a1=Integer.parseInt(x[2]);
        String v[]=fecAct.split("/");
        int d2=Integer.parseInt(v[0]);
        int m2=Integer.parseInt(v[1]);
        int a2=Integer.parseInt(v[2]);
        int anofinal;
        String mensaje="";

        anofinal=a2-a1;
        if (anofinal>0){
            if (m2<m1){ anofinal--; }
            if (m2==m1 && d2<d1){anofinal--;}
            if (anofinal==1)
            mensaje=""+anofinal;
            else
                mensaje=""+anofinal;
        }
        if(anofinal<10)mensaje="0"+mensaje;
        sEdad=mensaje;
    }
    
    public String getDiscapacitado(){
        return sDisc;
    }
    
    public void setDiscapacitado(String valor){
        if(valor.equals("S")) sDisc="X";
        else sDisc="_";
    }
    
    public String getIndigena(){
        return sIndi;
    }
    
    public void setIndigena(String valor){
        if(valor.equals("01   ")) sIndi="X";
        else sIndi="_";
    }
    
    public Medico getMed(){
        return oMed;
    }
    
    public void setMed(Medico valor){
        oMed=valor;
    }
    
    public String getEspontaneo(){
        return sEspontaneo;
    }
    
    public void setEspontaneo(String valor){
        sEspontaneo=valor;
    }
    
    public String getReferido(){
        return sreferido;
    }
    
    public void setReferido(String valor){
        sreferido=valor;
    }
    
    public void convertirdatos(){
        if(getAcidoFolico())setAcidoFol("X");else setAcidoFol(""); if(getAltoRiesgo())setAltories("X"); else setAltories(""); if(getAnalisisClinocos())setAnalisisClinico("X"); else setAnalisisClinico(""); if(getAnticoncepcionEmergencia())setAnticoncepcion("X");else setAnticoncepcion(""); if(getApoyoTrasladoObstetrico())setApoyoTrasladoObs("X");else setApoyoTrasladoObs("");
        if(getDislipidemia())setDislipi("X");else setDislipi(""); if(getEnfermedadesCroncronicas())setEnfermedadesCron("X");else setEnfermedadesCron(""); if(getEnfermedadesTransmisibles())setEnfermedadesTras("X");else setEnfermedadesTras(""); if(getOtrasEnfermedades())setOtrasEnfer("X");else setOtrasEnfer(""); if(getOtro())setOtr("X");else setOtr(""); if(getPlanificacion())setPlanifica("X");else setPlanifica("");
        if(getPreservativo())setPreserva("X");else setPreserva(""); if(getPrimerTrimestre())setPrimerTri("X");else setPrimerTri(""); if(getSegundoTrimestre())setSegundoTri("X");else setSegundoTri(""); if(getSifilis())setSifil("X");else setSifil(""); if(getTercerTrimestre())setTercerTri("X");else setTercerTri(""); if(getTuberculosis())setTuber("X");else setTuber(""); if(getVih())setVi("X");else setVi(""); 
    }
    
    public void convertirdatos2(){
        if(getEnfermedadesTras().equals("X")) setEnfermedadesTransmisibles(true); if(getEnfermedadesCron().equals("X"))setEnfermedadesCroncronicas(true); if(getOtrasEnfer().equals("X"))setOtrasEnfermedades(true);
        if(getPlanifica().equals("X"))setPlanificacion(true); if(getPrimerTri().equals("X"))setPrimerTrimestre(true); if(getSegundoTri().equals("X")) setSegundoTrimestre(true); if(getTercerTri().equals("X")) setTercerTrimestre(true);
        if(getAnalisisClinico().equals("X"))setAnalisisClinocos(true); if(getAltories().equals("X"))setAltoRiesgo(true); if(getApoyoTrasladoObs().equals("X"))setApoyoTrasladoObstetrico(true); if(getAcidoFol().equals("X"))setAcidoFolico(true);
        if(getPreserva().equals("X")) setPreservativo(true); if(getAnticoncepcion().equals("X"))setAnticoncepcionEmergencia(true); if(getOtr().equals("X"))setOtro(true); if(getTuber().equals("X"))setTuberculosis(true);
        if(getSifil().equals("X"))setSifilis(true); if(getVi().equals("X"))setVih(true); if(getDislipi().equals("X"))setDislipidemia(true);

    }
}
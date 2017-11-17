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
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class CitaOdontologiaCapasits extends Cita implements Serializable{
	private AccesoDatos oAD;
        private boolean bAutoBucal;
        private boolean bApliFluour;
        private boolean bOdontox;
        private boolean bSelladoFF;
        private boolean bPlacbac;
        private boolean bTecCep;
        private boolean bUsoHilBu;
        private boolean bProfilax;
        private boolean bHigieProt;
        private boolean bTejidBuc;
        private boolean bSaludBuc;
        private boolean bCiruBuc;
        private boolean bTeraPul;
        private boolean bMateTem;
        private boolean bFarmacot;
        private boolean bOtrAten;
        private boolean bAmalga;
        private boolean bResi;
        private boolean bIonomVid;
        private boolean bPieTem;
        private boolean bPiePerm;
        private String sAutoExpoBucal="";
        private String sApilcaFluour="";
        private String sOdontoxesis="";
        private String sSelladoFiFo="";
        private String sPlacaBacte="";
        private String sTecniCepill="";
        private String sUsoHiloBuc="";
        private String sprofilaxis="";
        private String sHigienaProte="";
        private String stejidoBucal="";
        private String sSaludBucal="";
        private String sCirugBucal="";
        private String sTerapiaTulp="";
        private String sMaterialTem="";
        private String sFarmacoterapia="";
        private String sOtrasAten="";
        private String sAmalgama="";
        private String sResina="";
        private String sIonomeVidrio="";
        private String sPiezaTem="";
        private String sPiezaPer="";
	private String sObservaciones="";
        private Firmado oFirm;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private String sUsuario;
        private Medico oMed;
        private PacienteCapasits oPacCap;
        private Expediente oExp;
        private String sSeguro="--";
        private String sEdad="";
        private int nNum;
        private int nNumAsistencia;
        private String sEspontaneo="";
        private String sPrimeraVez="-";
        private String sSubSecuente="-";
        private String sreferido="-";

    public CitaOdontologiaCapasits(boolean bAutoBucal, boolean bApliFluour, boolean bOdontox, boolean bSelladoFF, boolean bPlacbac, boolean bTecCep, boolean bUsoHilBu, boolean bProfilax, boolean bHigieProt, boolean bTejidBuc, boolean bSaludBuc, boolean bCiruBuc, boolean bTeraPul, boolean bMateTem, boolean bFarmacot, boolean bOtrAten, boolean bAmalga, boolean bResi, boolean bIonomVid, boolean bPieTem, boolean bPiePerm, String sAutoExpoBucal, String sApilcaFluour, String sOdontoxesis, String sSelladoFiFo, String sPlacaBacte, String sTecniCepill, String sUsoHiloBuc, String sprofilaxis, String sHigienaProte, String stejidoBucal, String sSaludBucal, String sCirugBucal, String sTerapiaTulp, String sMaterialTem, String sFarmacoterapia, String sOtrasAten, String sAmalgama, String sResina, String sIonomeVidrio, String sPiezaTem, String sPiezaPer, String sObservaciones, Date dFechaCita, Date dFechaRegistro, int nFolioCita, int nNoFicha, int nNumAprox, Parametrizacion oTiempoAprox, char sEmisorCita, String sFolioPago, char sStatusCita, Paciente oPaciente) {
        super(dFechaCita, dFechaRegistro, nFolioCita, nNoFicha, nNumAprox, oTiempoAprox, sEmisorCita, sFolioPago, sStatusCita, oPaciente);
        this.bAutoBucal = bAutoBucal;
        this.bApliFluour = bApliFluour;
        this.bOdontox = bOdontox;
        this.bSelladoFF = bSelladoFF;
        this.bPlacbac = bPlacbac;
        this.bTecCep = bTecCep;
        this.bUsoHilBu = bUsoHilBu;
        this.bProfilax = bProfilax;
        this.bHigieProt = bHigieProt;
        this.bTejidBuc = bTejidBuc;
        this.bSaludBuc = bSaludBuc;
        this.bCiruBuc = bCiruBuc;
        this.bTeraPul = bTeraPul;
        this.bMateTem = bMateTem;
        this.bFarmacot = bFarmacot;
        this.bOtrAten = bOtrAten;
        this.bAmalga = bAmalga;
        this.bResi = bResi;
        this.bIonomVid = bIonomVid;
        this.bPieTem = bPieTem;
        this.bPiePerm = bPiePerm;
        this.sAutoExpoBucal = sAutoExpoBucal;
        this.sApilcaFluour = sApilcaFluour;
        this.sOdontoxesis = sOdontoxesis;
        this.sSelladoFiFo = sSelladoFiFo;
        this.sPlacaBacte = sPlacaBacte;
        this.sTecniCepill = sTecniCepill;
        this.sUsoHiloBuc = sUsoHiloBuc;
        this.sprofilaxis = sprofilaxis;
        this.sHigienaProte = sHigienaProte;
        this.stejidoBucal = stejidoBucal;
        this.sSaludBucal = sSaludBucal;
        this.sCirugBucal = sCirugBucal;
        this.sTerapiaTulp = sTerapiaTulp;
        this.sMaterialTem = sMaterialTem;
        this.sFarmacoterapia = sFarmacoterapia;
        this.sOtrasAten = sOtrasAten;
        this.sAmalgama = sAmalgama;
        this.sResina = sResina;
        this.sIonomeVidrio = sIonomeVidrio;
        this.sPiezaTem = sPiezaTem;
        this.sPiezaPer = sPiezaPer;
        this.sObservaciones = sObservaciones;
    }

    public CitaOdontologiaCapasits() {
        oMed= new Medico();
        oPacCap= new PacienteCapasits();
        oExp=new Expediente();
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
		 if( this.getFolioCita()==0){   //completar llave
			throw new Exception("CitaOdontologiaCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCitaOdontologiaCapasits("+getFolioCita()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setAutoExpoBucal((String)vRowTemp.get(1));
                                this.setApilcaFluour((String)vRowTemp.get(2));
                                this.setOdontoxesis((String)vRowTemp.get(3));
                                this.setSelladoFiFo((String)vRowTemp.get(4));
                                this.setPlacaBacte((String)vRowTemp.get(5));
                                this.setTecniCepill((String)vRowTemp.get(6));
                                this.setUsoHiloBuc((String)vRowTemp.get(7));
                                this.setProfilaxis((String)vRowTemp.get(8));
                                this.setHigienaProte((String)vRowTemp.get(9));
                                this.setTejidoBucal((String)vRowTemp.get(10));
                                this.setSaludBucal((String)vRowTemp.get(11));
                                this.setCirugBucal((String)vRowTemp.get(12));
                                this.setTerapiaTulp((String)vRowTemp.get(13));
                                this.setMaterialTem((String)vRowTemp.get(14));
                                this.setFarmacoterapia((String)vRowTemp.get(15));
                                this.setOtrasAten((String)vRowTemp.get(16));
                                this.setAmalgama((String)vRowTemp.get(17));
                                this.setResina((String)vRowTemp.get(18));
                                this.setIonomeVidrio((String)vRowTemp.get(19));
                                this.setPiezaTem((String)vRowTemp.get(20));
                                this.setPiezaPer((String)vRowTemp.get(21));
                                this.setObservaciones((String)vRowTemp.get(22));
				bRet = true;
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
			throw new Exception("buscaCitaPacienteNutricionCapasits.asistencia: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaCitaPacienteOdontologiaCapasits('"+getFechaCita()+"',"+FolioPac+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			valor=rst.size();
		} 
		return valor; 
	}
        
	public CitaOdontologiaCapasits[] buscarTodos() throws Exception{
	CitaOdontologiaCapasits arrRet[]=null, oCitaOdontologiaCapasits=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<CitaOdontologiaCapasits> vObj=null;
	int i=0, nTam=0, n=1;
        String fec2; 
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String fec=ff.format(fecha);
        Date fech=ff.parse(fec);
		sQuery = "SELECT * FROM buscaTodosCitaOdontologiaCapasits('2015-03-10',"+oMed.getCons().getNoConsultorio()+"::smallint,"+oMed.getHorarios().getClave()+"::smallint);"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<CitaOdontologiaCapasits>();
			for (i = 0; i < rst.size(); i++) {
                            oCitaOdontologiaCapasits=new CitaOdontologiaCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCitaOdontologiaCapasits.setAutoExpoBucal((String)vRowTemp.get(0));
                            oCitaOdontologiaCapasits.setApilcaFluour((String)vRowTemp.get(1));
                            oCitaOdontologiaCapasits.setOdontoxesis((String)vRowTemp.get(2));
                            oCitaOdontologiaCapasits.setSelladoFiFo((String)vRowTemp.get(3));
                            oCitaOdontologiaCapasits.setPlacaBacte((String)vRowTemp.get(4));
                            oCitaOdontologiaCapasits.setTecniCepill((String)vRowTemp.get(5));
                            oCitaOdontologiaCapasits.setUsoHiloBuc((String)vRowTemp.get(6));
                            oCitaOdontologiaCapasits.setProfilaxis((String)vRowTemp.get(7));
                            oCitaOdontologiaCapasits.setHigienaProte((String)vRowTemp.get(8));
                            oCitaOdontologiaCapasits.setTejidoBucal((String)vRowTemp.get(9));
                            oCitaOdontologiaCapasits.setSaludBucal((String)vRowTemp.get(10));
                            oCitaOdontologiaCapasits.setCirugBucal((String)vRowTemp.get(11));
                            oCitaOdontologiaCapasits.setTerapiaTulp((String)vRowTemp.get(12));
                            oCitaOdontologiaCapasits.setMaterialTem((String)vRowTemp.get(13));
                            oCitaOdontologiaCapasits.setFarmacoterapia((String)vRowTemp.get(1));
                            oCitaOdontologiaCapasits.setOtrasAten((String)vRowTemp.get(15));
                            oCitaOdontologiaCapasits.setAmalgama((String)vRowTemp.get(16));
                            oCitaOdontologiaCapasits.setResina((String)vRowTemp.get(17));
                            oCitaOdontologiaCapasits.setIonomeVidrio((String)vRowTemp.get(18));
                            oCitaOdontologiaCapasits.setPiezaTem((String)vRowTemp.get(19));
                            oCitaOdontologiaCapasits.setPiezaPer((String)vRowTemp.get(20));
                            oCitaOdontologiaCapasits.setObservaciones((String)vRowTemp.get(21));
                            oCitaOdontologiaCapasits.getPac().setIdNacional(((Double) vRowTemp.get(22)).intValue());
                            oCitaOdontologiaCapasits.getPac().setFolioPaciente(((Double) vRowTemp.get(23)).intValue());
                            oCitaOdontologiaCapasits.getPac().setNombres((String)vRowTemp.get(24));
                            oCitaOdontologiaCapasits.getPac().setApPaterno((String)vRowTemp.get(25));
                            oCitaOdontologiaCapasits.getPac().setApMaterno((String)vRowTemp.get(26));
                            oCitaOdontologiaCapasits.getPac().setFechaNac((Date)vRowTemp.get(27));
                            oCitaOdontologiaCapasits.getPac().setSexos((String)vRowTemp.get(28));
                            oCitaOdontologiaCapasits.getPac().setPrimerFechaIngreso((Date)vRowTemp.get(29));
                            oCitaOdontologiaCapasits.SetSeguroPop((String)vRowTemp.get(30));
                            oCitaOdontologiaCapasits.getExp().setNumero(((Double) vRowTemp.get(31)).intValue());
                            oCitaOdontologiaCapasits.setFechaRegistro((Date)vRowTemp.get(32));
                            
                            oCitaOdontologiaCapasits.setNumAsistencia(asistencia(oCitaOdontologiaCapasits.getPac().getFolioPaciente()));
                            oCitaOdontologiaCapasits.setNum(n++);
                            oCitaOdontologiaCapasits.SetEdad(oCitaOdontologiaCapasits.getPac().getFechaNac());
                             fec2=ff.format(oCitaOdontologiaCapasits.getFechaRegistro());
                            if(fec2.equals(fec)) oCitaOdontologiaCapasits.setEspontaneo("*");
                            oCitaOdontologiaCapasits.setReferido("X");
                            if(oCitaOdontologiaCapasits.getPac().getPrimerFechaIngreso()==fech){oCitaOdontologiaCapasits.setPrimeraVez("X"); oCitaOdontologiaCapasits.setSubsecuente("_");}
                            else{oCitaOdontologiaCapasits.setPrimeraVez("_"); oCitaOdontologiaCapasits.setSubsecuente("X");}
                            vObj.add(oCitaOdontologiaCapasits);
			} 
                        nTam = vObj.size();
                        arrRet = new CitaOdontologiaCapasits[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	} 
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = ""; convertirdatos();
                if( this.getFolioCita()==0){   //completar llave
			throw new Exception("CitaOdontologiaCapasits.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCitaOdontologiaCapasits('"+sUsuario+"',"+getFolioCita()+",'"+getAutoExpoBucal()+"','"+getApilcaFluour()+"','"+getOdontoxesis()+"','"+getSelladoFiFo()+"','"+getPlacaBacte()+"','"+getTecniCepill()+"','"+getUsoHiloBuc()+"','"+getProfilaxis()+"','"+getHigienaProte()+
                                "','"+getTejidoBucal()+"','"+getSaludBucal()+"','"+getCirugBucal()+"','"+getTerapiaTulp()+"','"+getMaterialTem()+"','"+getFarmacoterapia()+"','"+getOtrasAten()+"','"+getAmalgama()+"','"+getResina()+"','"+getIonomeVidrio()+"','"+getPiezaTem()+"','"+getPiezaPer()+"','"+getObservaciones()+"');"; 
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
	String sQuery = ""; convertirdatos();
		 if( this.getFolioCita()==0){   //completar llave
			throw new Exception("CitaOdontologiaCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCitaOdontologiaCapasits('"+sUsuario+"',"+getFolioCita()+",'"+getAutoExpoBucal()+"','"+getApilcaFluour()+"','"+getOdontoxesis()+"','"+getSelladoFiFo()+"','"+getPlacaBacte()+"','"+getTecniCepill()+"','"+getUsoHiloBuc()+"','"+getProfilaxis()+"','"+getHigienaProte()+
                                "','"+getTejidoBucal()+"','"+getSaludBucal()+"','"+getCirugBucal()+"','"+getTerapiaTulp()+"','"+getMaterialTem()+"','"+getFarmacoterapia()+"','"+getOtrasAten()+"','"+getAmalgama()+"','"+getResina()+"','"+getIonomeVidrio()+"','"+getPiezaTem()+"','"+getPiezaPer()+"','"+getObservaciones()+"');"; 
			oAD=new AccesoDatos();  System.out.println(sQuery);
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
			throw new Exception("CitaOdontologiaCapasits.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCitaOdontologiaCapasits();"; 
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

    public AccesoDatos getoAD() {
        return oAD;
    }

    public void setoAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public boolean getAutoBucal() {
        return bAutoBucal;
    }

    public void setAutoBucal(boolean bAutoBucal) {
        this.bAutoBucal = bAutoBucal;
    }

    public boolean getApliFluour() {
        return bApliFluour;
    }

    public void setApliFluour(boolean bApliFluour) {
        this.bApliFluour = bApliFluour;
    }

    public boolean getOdontox() {
        return bOdontox;
    }

    public void setOdontox(boolean bOdontox) {
        this.bOdontox = bOdontox;
    }

    public boolean getSelladoFF() {
        return bSelladoFF;
    }

    public void setSelladoFF(boolean bSelladoFF) {
        this.bSelladoFF = bSelladoFF;
    }

    public boolean getPlacbac() {
        return bPlacbac;
    }

    public void setPlacbac(boolean bPlacbac) {
        this.bPlacbac = bPlacbac;
    }

    public boolean getTecCep() {
        return bTecCep;
    }

    public void setTecCep(boolean bTecCep) {
        this.bTecCep = bTecCep;
    }

    public boolean getUsoHilBu() {
        return bUsoHilBu;
    }

    public void setUsoHilBu(boolean bUsoHilBu) {
        this.bUsoHilBu = bUsoHilBu;
    }

    public boolean getProfilax() {
        return bProfilax;
    }

    public void setProfilax(boolean bProfilax) {
        this.bProfilax = bProfilax;
    }

    public boolean getHigieProt() {
        return bHigieProt;
    }

    public void setHigieProt(boolean bHigieProt) {
        this.bHigieProt = bHigieProt;
    }

    public boolean getTejidBuc() {
        return bTejidBuc;
    }

    public void setTejidBuc(boolean bTejidBuc) {
        this.bTejidBuc = bTejidBuc;
    }

    public boolean getSaludBuc() {
        return bSaludBuc;
    }

    public void setSaludBuc(boolean bSaludBuc) {
        this.bSaludBuc = bSaludBuc;
    }

    public boolean getCiruBuc() {
        return bCiruBuc;
    }

    public void setCiruBuc(boolean bCiruBuc) {
        this.bCiruBuc = bCiruBuc;
    }

    public boolean getTeraPul() {
        return bTeraPul;
    }

    public void setTeraPul(boolean bTeraPul) {
        this.bTeraPul = bTeraPul;
    }

    public boolean getMateTem() {
        return bMateTem;
    }

    public void setMateTem(boolean bMateTem) {
        this.bMateTem = bMateTem;
    }

    public boolean getFarmacot() {
        return bFarmacot;
    }

    public void setFarmacot(boolean bFarmacot) {
        this.bFarmacot = bFarmacot;
    }

    public boolean getOtrAten() {
        return bOtrAten;
    }

    public void setOtrAten(boolean bOtrAten) {
        this.bOtrAten = bOtrAten;
    }

    public boolean getAmalga() {
        return bAmalga;
    }

    public void setAmalga(boolean bAmalga) {
        this.bAmalga = bAmalga;
    }

    public boolean getResi() {
        return bResi;
    }

    public void setResi(boolean bResi) {
        this.bResi = bResi;
    }

    public boolean getIonomVid() {
        return bIonomVid;
    }

    public void setIonomVid(boolean bIonomVid) {
        this.bIonomVid = bIonomVid;
    }

    public boolean getPieTem() {
        return bPieTem;
    }

    public void setPieTem(boolean bPieTem) {
        this.bPieTem = bPieTem;
    }

    public boolean getPiePerm() {
        return bPiePerm;
    }

    public void setPiePerm(boolean bPiePerm) {
        this.bPiePerm = bPiePerm;
    }

    public String getAutoExpoBucal() {
        return sAutoExpoBucal;
    }

    public void setAutoExpoBucal(String sAutoExpoBucal) {
        this.sAutoExpoBucal = sAutoExpoBucal;
    }

    public String getApilcaFluour() {
        return sApilcaFluour;
    }

    public void setApilcaFluour(String sApilcaFluour) {
        this.sApilcaFluour = sApilcaFluour;
    }

    public String getOdontoxesis() {
        return sOdontoxesis;
    }

    public void setOdontoxesis(String sOdontoxesis) {
        this.sOdontoxesis = sOdontoxesis;
    }

    public String getSelladoFiFo() {
        return sSelladoFiFo;
    }

    public void setSelladoFiFo(String sSelladoFiFo) {
        this.sSelladoFiFo = sSelladoFiFo;
    }

    public String getPlacaBacte() {
        return sPlacaBacte;
    }

    public void setPlacaBacte(String sPlacaBacte) {
        this.sPlacaBacte = sPlacaBacte;
    }

    public String getTecniCepill() {
        return sTecniCepill;
    }

    public void setTecniCepill(String sTecniCepill) {
        this.sTecniCepill = sTecniCepill;
    }

    public String getUsoHiloBuc() {
        return sUsoHiloBuc;
    }

    public void setUsoHiloBuc(String sUsoHiloBuc) {
        this.sUsoHiloBuc = sUsoHiloBuc;
    }

    public String getHigienaProte() {
        return sHigienaProte;
    }

    public void setHigienaProte(String sHigienaProte) {
        this.sHigienaProte = sHigienaProte;
    }

    public String getSaludBucal() {
        return sSaludBucal;
    }

    public void setSaludBucal(String sSaludBucal) {
        this.sSaludBucal = sSaludBucal;
    }

    public String getCirugBucal() {
        return sCirugBucal;
    }

    public void setCirugBucal(String sCirugBucal) {
        this.sCirugBucal = sCirugBucal;
    }

    public String getTerapiaTulp() {
        return sTerapiaTulp;
    }

    public void setTerapiaTulp(String sTerapiaTulp) {
        this.sTerapiaTulp = sTerapiaTulp;
    }

    public String getMaterialTem() {
        return sMaterialTem;
    }

    public void setMaterialTem(String sMaterialTem) {
        this.sMaterialTem = sMaterialTem;
    }

    public String getFarmacoterapia() {
        return sFarmacoterapia;
    }

    public void setFarmacoterapia(String sFarmacoterapia) {
        this.sFarmacoterapia = sFarmacoterapia;
    }

    public String getOtrasAten() {
        return sOtrasAten;
    }

    public void setOtrasAten(String sOtrasAten) {
        this.sOtrasAten = sOtrasAten;
    }

    public String getAmalgama() {
        return sAmalgama;
    }

    public void setAmalgama(String sAmalgama) {
        this.sAmalgama = sAmalgama;
    }

    public String getResina() {
        return sResina;
    }

    public void setResina(String sResina) {
        this.sResina = sResina;
    }

    public String getIonomeVidrio() {
        return sIonomeVidrio;
    }

    public void setIonomeVidrio(String sIonomeVidrio) {
        this.sIonomeVidrio = sIonomeVidrio;
    }

    public String getPiezaTem() {
        return sPiezaTem;
    }

    public void setPiezaTem(String sPiezaTem) {
        this.sPiezaTem = sPiezaTem;
    }

    public String getPiezaPer() {
        return sPiezaPer;
    }

    public void setPiezaPer(String sPiezaPer) {
        this.sPiezaPer = sPiezaPer;
    }
	
    public String getObservaciones() {
	return sObservaciones;
    }
    
    public String getProfilaxis(){
        return sprofilaxis;
    }
    
    public void setProfilaxis(String valor){
        this.sprofilaxis=valor;
    }
    public String getTejidoBucal(){
        return stejidoBucal;
    }
    
    public void setTejidoBucal(String valor){
        this.stejidoBucal=valor;
    }

    public void setObservaciones(String valor) {
	sObservaciones=valor;
    }
    
    public Medico getMed(){
        return oMed;
    }
    
    public void setMed(Medico valor){
        oMed=valor;
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
    
    public String getSeguroPop(){
        return sSeguro;
    }
    
    public void SetSeguroPop(String valor){
        if(valor.equals("T0108")) sSeguro="X";
        else sSeguro="_";
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
    
    public String getEspontaneo(){
        return sEspontaneo;
    }
    
    public void setEspontaneo(String valor){
        sEspontaneo=valor;
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
    
    public String getReferido(){
        return sreferido;
    }
    
    public void setReferido(String valor){
        sreferido=valor;
    }
    
    public void convertirdatos(){
        if(getAmalga())setAmalgama("X");else setAmalgama(""); if(getApliFluour())setApilcaFluour("X"); else setApilcaFluour(""); if(getAutoBucal())setAutoExpoBucal("X");else setAutoExpoBucal("");
        if(getCiruBuc())setCirugBucal("X");else setCirugBucal(""); if(getFarmacot())setFarmacoterapia("X");else setFarmacoterapia(""); if(getHigieProt())setHigienaProte("X"); else setHigienaProte("");
        if(getIonomVid())setIonomeVidrio("X"); else setIonomeVidrio(""); if(getMateTem())setMaterialTem("X"); else setMaterialTem(""); if(getOdontox())setOdontoxesis("X"); else setOdontoxesis("");
        if(getOtrAten())setOtrasAten("X"); else setOtrasAten(""); if(getPiePerm())setPiezaPer("X"); else setPiezaPer(""); if(getPieTem())setPiezaTem("X"); else setPiezaTem(""); if(getPlacbac())setPlacaBacte("X"); else setPlacaBacte("");
        if(getProfilax()) setProfilaxis("X"); else setProfilaxis(""); if(getResi())setResina("X"); else setResina(""); if(getSaludBuc())setSaludBucal("X"); else setSaludBucal(""); if(getSelladoFF()) setSelladoFiFo("X"); else setSelladoFiFo(""); 
        if(getTejidBuc()) setTejidoBucal("X"); else setTejidoBucal(""); if(getTecCep()) setTecniCepill("X"); else setTecniCepill(""); if(getTeraPul())setTerapiaTulp("X"); else setTerapiaTulp(""); if(getUsoHilBu())setUsoHiloBuc("X"); else setUsoHiloBuc("");		

    }
    
    public void convertirdatos2(){
        if(getAutoExpoBucal().equals("X")) setAutoBucal(true); if(getAmalgama().equals("X")) setAmalga(true); if(getOdontoxesis().equals("X")) setOdontox(true);
                                if(getSelladoFiFo().equals("X")) setSelladoFF(true); if(getPlacaBacte().equals("X")) setPlacbac(true); if(getTecniCepill().equals("X")) setTecCep(true);
                                if(getUsoHiloBuc().equals("X")) setUsoHilBu(true); if(getProfilaxis().equals("X")) setProfilax(true); if(getHigienaProte().equals("X")) setHigieProt(true);
                                if(getTejidoBucal().equals("X")) setTejidBuc(true); if(getSaludBucal().equals("X")) setSaludBuc(true); if(getCirugBucal().equals("X")) setCiruBuc(true);
                                if(getTerapiaTulp().equals("X")) setTeraPul(true); if(getMaterialTem().equals("X")) setMateTem(true); if(getFarmacoterapia().equals("X")) setFarmacot(true);
                                if(getOtrasAten().equals("X")) setOtrAten(true); if(getAmalgama().equals("X")) setAmalga(true); if(getResina().equals("X")) setResi(true);
                                if(getIonomeVidrio().equals("X")) setIonomVid(true); if(getPiezaTem().equals("X")) setPieTem(true); if(getPiezaPer().equals("X")) setPiePerm(true); if(getApilcaFluour().equals("X")) setApliFluour(true);
    }

} 
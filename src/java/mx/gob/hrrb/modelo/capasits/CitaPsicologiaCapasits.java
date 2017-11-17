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
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class CitaPsicologiaCapasits extends Cita implements Serializable{
	private AccesoDatos oAD;
	private boolean bApoyPsico;
        private boolean bMateEntre;
        private boolean bEstupsico;
        private boolean bPsicoInd;
        private boolean bFarma;
        private boolean bAlcoh;
        private boolean bTaba;
        private String sApoyopsicoemocional="_";
        private String smaterialEntregado="_";
        private String sEstudioPsicometrico="_";
        private String sPsicoterapiaInd="_";
        private String sFarmacos="_";
        private String sAlcohol="_";
        private String sTabaco="_";
	private String sObservaciones;
        private String sObservaciones2;
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
        private String sPrimeraVez="__";
        private String sSubSecuente="_";
        private String sreferido="_";

    public CitaPsicologiaCapasits(boolean bApoyPsico, boolean bMateEntre, boolean bEstupsico, boolean bPsicoInd, boolean bFarma, boolean bAlcoh, boolean bTaba, String sApoyopsicoemocional, String smaterialEntregado, String sEstudioPsicometrico, String sPsicoterapiaInd, String sFarmacos, String sAlcohol, String sTabaco, String sObservaciones, String sObservaciones2, Date dFechaCita, Date dFechaRegistro, int nFolioCita, int nNoFicha, int nNumAprox, Parametrizacion oTiempoAprox, char sEmisorCita, String sFolioPago, char sStatusCita, Paciente oPaciente) {
        super(dFechaCita, dFechaRegistro, nFolioCita, nNoFicha, nNumAprox, oTiempoAprox, sEmisorCita, sFolioPago, sStatusCita, oPaciente);
        this.bApoyPsico = bApoyPsico;
        this.bMateEntre = bMateEntre;
        this.bEstupsico = bEstupsico;
        this.bPsicoInd = bPsicoInd;
        this.bFarma = bFarma;
        this.bAlcoh = bAlcoh;
        this.bTaba = bTaba;
        this.sApoyopsicoemocional = sApoyopsicoemocional;
        this.smaterialEntregado = smaterialEntregado;
        this.sEstudioPsicometrico = sEstudioPsicometrico;
        this.sPsicoterapiaInd = sPsicoterapiaInd;
        this.sFarmacos = sFarmacos;
        this.sAlcohol = sAlcohol;
        this.sTabaco = sTabaco;
        this.sObservaciones = sObservaciones;
        this.sObservaciones2 = sObservaciones2;
    }

    public CitaPsicologiaCapasits() {
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
			throw new Exception("CitaPsicologiaCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCitaPsicologiaCapasits("+getFolioCita()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setApoyoPsicoemocional((String)vRowTemp.get(1));
                                this.setMaterialEntre((String)vRowTemp.get(2));
                                this.setEstudioPsicometrico((String)vRowTemp.get(3));
                                this.setPsicoterapiaInd((String)vRowTemp.get(4));
                                this.setFarmacos((String)vRowTemp.get(5));
                                this.setAlcohol((String)vRowTemp.get(6));
                                this.setTabaco((String)vRowTemp.get(7));
                                this.setObservaciones((String)vRowTemp.get(8));
                                this.setObservaciones2((String)vRowTemp.get(9));
				bRet = true;
                            convertidatos2();
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
			sQuery = "SELECT * FROM buscaCitaPacientePsicologiaCapasits('"+getFechaCita()+"',"+FolioPac+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			valor=rst.size();
		} 
		return valor; 
	}
        
	public CitaPsicologiaCapasits[] buscarTodos() throws Exception{
	CitaPsicologiaCapasits arrRet[]=null, oCitaPsicologiaCapasits=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<CitaPsicologiaCapasits> vObj=null;
	int i=0, nTam=0, n=1;
        String fec2; 
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String fec=ff.format(fecha);
        Date fech=ff.parse(fec);;
		sQuery = "SELECT * FROM buscaTodosCitaPsicologiaCapasits('2015-03-10',"+oMed.getCons().getNoConsultorio()+"::smallint,"+oMed.getHorarios().getClave()+"::smallint);"; 
		oAD=new AccesoDatos(); System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<CitaPsicologiaCapasits>();
			for (i = 0; i < rst.size(); i++) {
                            oCitaPsicologiaCapasits=new CitaPsicologiaCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCitaPsicologiaCapasits.setApoyoPsicoemocional((String)vRowTemp.get(0));
                            oCitaPsicologiaCapasits.setMaterialEntre((String)vRowTemp.get(1));
                            oCitaPsicologiaCapasits.setEstudioPsicometrico((String)vRowTemp.get(2));
                            oCitaPsicologiaCapasits.setPsicoterapiaInd((String)vRowTemp.get(3));
                            oCitaPsicologiaCapasits.setFarmacos((String)vRowTemp.get(4));
                            oCitaPsicologiaCapasits.setAlcohol((String)vRowTemp.get(5));
                            oCitaPsicologiaCapasits.setTabaco((String)vRowTemp.get(6));
                            oCitaPsicologiaCapasits.setObservaciones((String)vRowTemp.get(7));
                            oCitaPsicologiaCapasits.setObservaciones2((String)vRowTemp.get(8));
                            oCitaPsicologiaCapasits.getPac().setIdNacional(((Double) vRowTemp.get(9)).intValue());
                            oCitaPsicologiaCapasits.getPac().setFolioPaciente(((Double) vRowTemp.get(10)).intValue());
                            oCitaPsicologiaCapasits.getPac().setNombres((String)vRowTemp.get(11));
                            oCitaPsicologiaCapasits.getPac().setApPaterno((String)vRowTemp.get(12));
                            oCitaPsicologiaCapasits.getPac().setApMaterno((String)vRowTemp.get(13));
                            oCitaPsicologiaCapasits.getPac().setFechaNac((Date)vRowTemp.get(14));
                            oCitaPsicologiaCapasits.getPac().setSexos((String)vRowTemp.get(15));
                            oCitaPsicologiaCapasits.getPac().setPrimerFechaIngreso((Date)vRowTemp.get(16));    
                            oCitaPsicologiaCapasits.SetSeguroPop((String)vRowTemp.get(17));
                            oCitaPsicologiaCapasits.getExp().setNumero(((Double) vRowTemp.get(18)).intValue());
                            oCitaPsicologiaCapasits.setFechaRegistro((Date)vRowTemp.get(19));
                            if(oCitaPsicologiaCapasits.getApoyoPsicoemocional().equals(" ")) oCitaPsicologiaCapasits.setApoyoPsicoemocional("--");
                            if(oCitaPsicologiaCapasits.getMaterialEntre().equals(" "))oCitaPsicologiaCapasits.setMaterialEntre("--");
                            if(oCitaPsicologiaCapasits.getEstudioPsicometrico().equals(" "))oCitaPsicologiaCapasits.setEstudioPsicometrico("--");
                            if(oCitaPsicologiaCapasits.getPsicoterapiaInd().equals(" ")) oCitaPsicologiaCapasits.setPsicoterapiaInd("--");
                            if(oCitaPsicologiaCapasits.getFarmacos().equals(" "))oCitaPsicologiaCapasits.setFarmacos("--");
                            if(oCitaPsicologiaCapasits.getAlcohol().equals(" "))oCitaPsicologiaCapasits.setAlcohol("--");
                            if(oCitaPsicologiaCapasits.getTabaco().equals(" "))oCitaPsicologiaCapasits.setTabaco("--"); 
                            oCitaPsicologiaCapasits.setNumAsistencia(asistencia(oCitaPsicologiaCapasits.getPac().getFolioPaciente()));
                            oCitaPsicologiaCapasits.setNum(n++);
                            oCitaPsicologiaCapasits.SetEdad(oCitaPsicologiaCapasits.getPac().getFechaNac());
                            fec2=ff.format(oCitaPsicologiaCapasits.getFechaRegistro());
                            if(fec2.equals(fec)) oCitaPsicologiaCapasits.setEspontaneo("*");
                            oCitaPsicologiaCapasits.setReferido("X");
                            if(oCitaPsicologiaCapasits.getPac().getSexos().equals("F")) oCitaPsicologiaCapasits.getPac().setSexos("F ");
                            vObj.add(oCitaPsicologiaCapasits);
                            if(oCitaPsicologiaCapasits.getPac().getPrimerFechaIngreso()==fech){oCitaPsicologiaCapasits.setPrimeraVez("X"); oCitaPsicologiaCapasits.setSubsecuente("_");}
                            else{oCitaPsicologiaCapasits.setPrimeraVez("_"); oCitaPsicologiaCapasits.setSubsecuente("X");}
			}
                        nTam = vObj.size();
                        arrRet = new CitaPsicologiaCapasits[nTam];
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
			throw new Exception("CitaPsicologiaCapasits.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCitaPsicologiaCapasits('"+sUsuario+"',"+this.getFolioCita()+",'"+this.getApoyoPsicoemocional()+"','"+this.getMaterialEntre()
                                +"','"+this.getEstudioPsicometrico()+"','"+this.getPsicoterapiaInd()+"','"+this.getFarmacos()+"','"+this.getAlcohol()+"','"+this.getTabaco()+"','"+this.getObservaciones()+"','"+this.getObservaciones2()+"');"; 
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
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = ""; convertirdatos();
		 if( this.getFolioCita()==0){   //completar llave
			throw new Exception("CitaPsicologiaCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCitaPsicologiaCapasits('"+sUsuario+"',"+this.getFolioCita()+",'"+this.getApoyoPsicoemocional()+"','"+this.getMaterialEntre()
                                +"','"+this.getEstudioPsicometrico()+"','"+this.getPsicoterapiaInd()+"','"+this.getFarmacos()+"','"+this.getAlcohol()+"','"+this.getTabaco()+"','"+this.getObservaciones()+"','"+this.getObservaciones2()+"');"; 
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
			throw new Exception("CitaPsicologiaCapasits.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCitaPsicologiaCapasits();"; 
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
        
	public boolean getApoyPsico() {
	return bApoyPsico;
	}
	public void setApoyPsico(boolean valor) {
	bApoyPsico=valor;
	}
        
        public String getApoyoPsicoemocional(){
            return sApoyopsicoemocional;
        }
        public void setApoyoPsicoemocional(String valor){
            sApoyopsicoemocional=valor;
        }
        
        public boolean getMateEnt() {
	return bMateEntre;
	}
	public void setMateEnt(boolean valor) {
	bMateEntre=valor;
	}
        
        public String getMaterialEntre(){
            return smaterialEntregado;
        }
        public void setMaterialEntre(String valor){
            smaterialEntregado=valor;
        }
        
        public boolean getEstudioPsico() {
	return bEstupsico;
	}
	public void setEstudioPsico(boolean valor) {
	bEstupsico=valor;
	}
        
        public String getEstudioPsicometrico(){
            return sEstudioPsicometrico;
        }
        public void setEstudioPsicometrico(String valor){
            sEstudioPsicometrico=valor;
        }
        
         public boolean getPsicoInd() {
             return bPsicoInd;
	}
	public void setPsicoInd(boolean valor) {
	bPsicoInd=valor;
	}
        
        public String getPsicoterapiaInd(){
            return sPsicoterapiaInd;
        }
        public void setPsicoterapiaInd(String valor){
            sPsicoterapiaInd=valor;
        }
        
         public boolean getFarma() {
            return bFarma;
	}
	public void setFarma(boolean valor) {
	bFarma=valor;
	}
        
        public String getFarmacos(){
            return sFarmacos;
        }
        public void setFarmacos(String valor){
            sFarmacos=valor;
        }
        
        public boolean getAlcoh() {
            return bAlcoh;
	}
	public void setAlcoh(boolean valor) {
	bAlcoh=valor;
	}
        
        public String getAlcohol(){
            return sAlcohol;
        }
        public void setAlcohol(String valor){
            sAlcohol=valor;
        }
        
         public boolean getTaba() {
            return bTaba;
	}
	public void settaba(boolean valor) {
	bTaba=valor;
	}
        
        public String getTabaco(){
            return sTabaco;
        }
        public void setTabaco(String valor){
            sTabaco=valor;
        }

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}
        
        public String getObservaciones2() {
	return sObservaciones2;
	}

	public void setObservaciones2(String valor) {
	sObservaciones2=valor;
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
            if(this.getAlcoh())this.setAlcohol("X"); else this.setAlcohol(""); if(this.getApoyPsico()) this.setApoyoPsicoemocional("X"); else  this.setApoyoPsicoemocional(""); 
            if(this.getEstudioPsico()) this.setEstudioPsicometrico("X"); else this.setEstudioPsicometrico(""); if(this.getMateEnt()) this.setMaterialEntre("X");else this.setMaterialEntre("");
            if(this.getPsicoInd()) this.setPsicoterapiaInd("X"); else this.setPsicoterapiaInd(""); if(this.getFarma()) this.setFarmacos("X"); else this.setFarmacos(""); if(this.getTaba()) this.setTabaco("X"); else this.setTabaco("");  
        }
        
        public void convertidatos2(){
            if(getApoyoPsicoemocional().equals("X")) setApoyPsico(true); if(getMaterialEntre().equals("X"))setMateEnt(true); if(getEstudioPsicometrico().equals("X"))setEstudioPsico(true);
            if(getPsicoterapiaInd().equals("X"))setPsicoInd(true); if(getFarmacos().equals("X"))setFarma(true); if(getAlcohol().equals("X")) setAlcoh(true); if(getTabaco().equals("X"))settaba(true);
        }

} 

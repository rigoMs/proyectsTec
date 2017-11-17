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

public class CitaNutricionCapasits extends Cita implements Serializable{
	private AccesoDatos oAD;
	private String sObservaciones;
        private Firmado oFirm;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private String sUsuario;
        private Medico oMed;
        private PacienteCapasits oPacCap;
        private Expediente oExp;
        private String sDisc="--";
        private String sIndi="--";
        private String sSeguro="--";
        private String sEdad="";
        private int nNum;
        private int nNumAsistencia;
        private String sEspontaneo="";
        private String sPrimeraVez="__";
        private String sSubSecuente="_";
        private String sreferido="_";

    public CitaNutricionCapasits(String sObservaciones, Date dFechaCita, Date dFechaRegistro, int nFolioCita, int nNoFicha,
            int nNumAprox, Parametrizacion oTiempoAprox, char sEmisorCita, String sFolioPago, char sStatusCita, 
            Paciente oPaciente) {
        super(dFechaCita, dFechaRegistro, nFolioCita, nNoFicha, nNumAprox, oTiempoAprox, sEmisorCita, sFolioPago, sStatusCita, oPaciente);
        this.sObservaciones = sObservaciones;
    }
    
    public CitaNutricionCapasits(){
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
			throw new Exception("CitaNutricionCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCitaNutricionCapasits("+getFolioCita()+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setFolioCita(((Double)vRowTemp.get(0)).intValue());
                                this.setObservaciones((String)vRowTemp.get(1));
				bRet = true;
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
			sQuery = "SELECT * FROM buscaCitaPacienteNutricionCapasits('"+getFechaCita()+"',"+FolioPac+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			valor=rst.size();
		} 
		return valor; 
	}
        
	public CitaNutricionCapasits[] buscarTodos() throws Exception{
	CitaNutricionCapasits arrRet[]=null, oCitaNutricionCapasits=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<CitaNutricionCapasits> vObj=null;
	int i=0, nTam=0, n=1;
        String fec2; 
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String fec=ff.format(fecha);
        Date fech=ff.parse(fec);
		sQuery = "SELECT * FROM buscaTodosCitaNutricionCapasits('2015-03-10',"+oMed.getCons().getNoConsultorio()+"::smallint,"+oMed.getHorarios().getClave()+"::smallint);"; 
		oAD=new AccesoDatos(); System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<CitaNutricionCapasits>();
			for (i = 0; i < rst.size(); i++) {
                            oCitaNutricionCapasits= new CitaNutricionCapasits();
                             ArrayList vRowTemp = (ArrayList)rst.get(i);
                             oCitaNutricionCapasits.setObservaciones((String)vRowTemp.get(0));
                             oCitaNutricionCapasits.getPac().setIdNacional(((Double) vRowTemp.get(1)).intValue());
                             oCitaNutricionCapasits.getPac().setFolioPaciente(((Double) vRowTemp.get(2)).intValue());
                             oCitaNutricionCapasits.getPac().setNombres((String)vRowTemp.get(3));
                             oCitaNutricionCapasits.getPac().setApPaterno((String)vRowTemp.get(4));
                             oCitaNutricionCapasits.getPac().setApMaterno((String)vRowTemp.get(5));
                             oCitaNutricionCapasits.getPac().setFechaNac((Date)vRowTemp.get(6));
                             oCitaNutricionCapasits.getPac().setSexos((String)vRowTemp.get(7));
                             oCitaNutricionCapasits.getPac().setPrimerFechaIngreso((Date)vRowTemp.get(8));
                             oCitaNutricionCapasits.setDiscapacitado((String)vRowTemp.get(9));
                             oCitaNutricionCapasits.setIndigena((String)vRowTemp.get(10));
                             oCitaNutricionCapasits.SetSeguroPop((String)vRowTemp.get(11));
                             oCitaNutricionCapasits.getExp().setNumero(((Double) vRowTemp.get(12)).intValue());
                             oCitaNutricionCapasits.setFechaRegistro((Date)vRowTemp.get(13));
                             
                             oCitaNutricionCapasits.setNumAsistencia(asistencia(oCitaNutricionCapasits.getPac().getFolioPaciente()));
                             oCitaNutricionCapasits.setNum(n++);
                             oCitaNutricionCapasits.SetEdad(oCitaNutricionCapasits.getPac().getFechaNac());
                             fec2=ff.format(oCitaNutricionCapasits.getFechaRegistro());
                            if(fec2.equals(fec)) oCitaNutricionCapasits.setEspontaneo("*");
                            oCitaNutricionCapasits.setReferido("X");
                            if(oCitaNutricionCapasits.getPac().getSexos().equals("F")) oCitaNutricionCapasits.getPac().setSexos("F ");
                            vObj.add(oCitaNutricionCapasits);
                            if(oCitaNutricionCapasits.getPac().getPrimerFechaIngreso()==fech){oCitaNutricionCapasits.setPrimeraVez("X"); oCitaNutricionCapasits.setSubsecuente("_");}
                            else{oCitaNutricionCapasits.setPrimeraVez("_"); oCitaNutricionCapasits.setSubsecuente("X");}
			} 
                        nTam = vObj.size();
                        arrRet = new CitaNutricionCapasits[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	} 
        
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CitaNutricionCapasits.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCitaNutricionCapasits('"+sUsuario+"',"+this.getFolioCita()+",'"+this.getObservaciones()+"');"; 
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
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CitaNutricionCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCitaNutricionCapasits('"+sUsuario+"',"+getFolioCita()+",'"+getObservaciones()+"');"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp .get(0)).intValue();
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
			throw new Exception("CitaNutricionCapasits.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCitaNutricionCapasits();"; 
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
	public String getObservaciones() {
	return sObservaciones;
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
    
} 

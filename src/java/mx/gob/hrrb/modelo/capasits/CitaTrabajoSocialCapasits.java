package mx.gob.hrrb.modelo.capasits;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.core.Cita;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class CitaTrabajoSocialCapasits extends Cita implements Serializable{
	private AccesoDatos oAD;
	private boolean bEstSocEco;
        private boolean bReferencia;
        private boolean bContrareferencia;
        private boolean bOtrostramites;
	private Date dHoraTraslado;
	private String sGestiones;
	private String sObservaciones;
        private String sEstuSocioEco="";
        private String sRefen="";
        private String sContraRe="";
        private String sOtrostra="";
        private String sHora;
        private String[] gesti;
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

    public CitaTrabajoSocialCapasits(boolean bEstSocEco, boolean bReferencia, boolean bContrareferencia, boolean bOtrostramites, Date dHoraTraslado, String sGestiones, String sObservaciones, Date dFechaCita, Date dFechaRegistro, int nFolioCita, int nNoFicha, int nNumAprox, Parametrizacion oTiempoAprox, char sEmisorCita, String sFolioPago, char sStatusCita, Paciente oPaciente) {
        super(dFechaCita, dFechaRegistro, nFolioCita, nNoFicha, nNumAprox, oTiempoAprox, sEmisorCita, sFolioPago, sStatusCita, oPaciente);
        this.bEstSocEco = bEstSocEco;
        this.bReferencia = bReferencia;
        this.bContrareferencia = bContrareferencia;
        this.bOtrostramites = bOtrostramites;
        this.dHoraTraslado = dHoraTraslado;
        this.sGestiones = sGestiones;
        this.sObservaciones = sObservaciones;
    }

    
     public CitaTrabajoSocialCapasits(){
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
		 if( this==null){   //completar llave
			throw new Exception("CitaTrabajoSocialCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCitaTrabajoSocialCapasits("+getFolioCita()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setEstusocioEco((String)vRowTemp.get(1));
                                this.setRefen((String)vRowTemp.get(2));
                                this.setContraRe((String)vRowTemp.get(3));
                                this.setOtrostra((String)vRowTemp.get(4));
                                this.setGestiones((String)vRowTemp.get(5));
                                this.setHoraTraslado((Date)vRowTemp.get(6));
                                this.setObservaciones((String)vRowTemp.get(7));
				bRet = true;
                                if(getEstusocioEco().equals("X")) setEstSocEco(true);else setEstSocEco(false); if(getRefen().equals("X"))setReferencia(true);else setReferencia(false); if(getContraRe().equals("X")) setContrareferencia(true);
                                if(getOtrostra().equals("X")) setOtrostramites(true);
			}
		} 
		return bRet; 
	} 
        
        public boolean buscarMedicoDeHoja() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("citaTrabjoSocialCapasits.buscar: error de programación, faltan datos");
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
        
        public boolean totalDeHorasDeTraslado() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("citaTrabjoSocialCapasits.totalDeHoraDeTraslado: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM totaldeHorasdetraslado('2015-03-10',"+oMed.getHorarios().getClave()+"::smallint);"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.setHora((String)vRowTemp.get(0));
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
			throw new Exception("citaTrabjoSocialCapasits.asistencia: error de programación, faltan datos");
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
        
	public CitaTrabajoSocialCapasits[] buscarTodos() throws Exception{
	CitaTrabajoSocialCapasits arrRet[]=null, oCitaTrabajoSocialCapasits=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<CitaTrabajoSocialCapasits> vObj=null;
	int i=0, nTam=0, n=1;
        String fec2; 
        Date fecha=new Date();
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String fec=ff.format(fecha);
        Date fech=ff.parse(fec);
		sQuery = "SELECT * FROM buscaTodosCitaTrabajoSocialCapasits('2015-03-10',"+oMed.getCons().getNoConsultorio()+"::smallint,"+oMed.getHorarios().getClave()+"::smallint);"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<CitaTrabajoSocialCapasits>();
			for (i = 0; i < rst.size(); i++) {
                            oCitaTrabajoSocialCapasits= new CitaTrabajoSocialCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oCitaTrabajoSocialCapasits.setEstusocioEco((String)vRowTemp.get(0));
                            oCitaTrabajoSocialCapasits.setRefen((String)vRowTemp.get(1));
                            oCitaTrabajoSocialCapasits.setContraRe((String)vRowTemp.get(2));
                            oCitaTrabajoSocialCapasits.setOtrostra((String)vRowTemp.get(3));
                            oCitaTrabajoSocialCapasits.setGestiones((String)vRowTemp.get(4));
                            oCitaTrabajoSocialCapasits.setObservaciones((String)vRowTemp.get(6));
                            oCitaTrabajoSocialCapasits.getPac().setIdNacional(((Double) vRowTemp.get(7)).intValue());
                            oCitaTrabajoSocialCapasits.getPac().setFolioPaciente(((Double) vRowTemp.get(8)).intValue());
                            oCitaTrabajoSocialCapasits.getPac().setNombres((String)vRowTemp.get(9));
                            oCitaTrabajoSocialCapasits.getPac().setApPaterno((String)vRowTemp.get(10));
                            oCitaTrabajoSocialCapasits.getPac().setApMaterno((String)vRowTemp.get(11));
                            oCitaTrabajoSocialCapasits.getPac().setFechaNac((Date)vRowTemp.get(12));
                            oCitaTrabajoSocialCapasits.getPac().setSexos((String)vRowTemp.get(13));
                            oCitaTrabajoSocialCapasits.getPac().setPrimerFechaIngreso((Date)vRowTemp.get(14));
                            oCitaTrabajoSocialCapasits.SetSeguroPop((String)vRowTemp.get(15));
                            oCitaTrabajoSocialCapasits.getExp().setNumero(((Double) vRowTemp.get(16)).intValue());
                            oCitaTrabajoSocialCapasits.setFechaRegistro((Date)vRowTemp.get(17));
                            
                            oCitaTrabajoSocialCapasits.setNumAsistencia(asistencia(oCitaTrabajoSocialCapasits.getPac().getFolioPaciente()));
                            oCitaTrabajoSocialCapasits.setNum(n++);
                            oCitaTrabajoSocialCapasits.SetEdad(oCitaTrabajoSocialCapasits.getPac().getFechaNac());
                            fec2=ff.format(oCitaTrabajoSocialCapasits.getFechaRegistro());
                            if(fec2.equals(fec)) oCitaTrabajoSocialCapasits.setEspontaneo("*");
                            vObj.add(oCitaTrabajoSocialCapasits);
			} 
                        nTam = vObj.size();
                        arrRet = new CitaTrabajoSocialCapasits[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	} 
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if(getFolioCita()==0){   //completar llave
			throw new Exception("CitaTrabajoSocialCapasits.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCitaTrabajoSocialCapasits('"+sUsuario+"',"+getFolioCita()+",'"+getEstusocioEco()+"','"+getRefen()+"','"+getContraRe()+"','"+getOtrostra()+"','"+getGestiones()+"','"+getHora()+"','"+getObservaciones()+"');"; 
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
			throw new Exception("CitaTrabajoSocialCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCitaTrabajoSocialCapasits('"+sUsuario+"',"+getFolioCita()+",'"+getEstusocioEco()+"','"+getRefen()+"','"+getContraRe()+"','"+getOtrostra()+"','"+getGestiones()+"','"+getHora()+"','"+getObservaciones()+"');"; 
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
			throw new Exception("CitaTrabajoSocialCapasits.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCitaTrabajoSocialCapasits();"; 
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
	
	public boolean getEstSocEco() {
	return bEstSocEco;
	}

	public void setEstSocEco(boolean valor) {
            if(valor) setEstusocioEco("X"); else setEstusocioEco("");
	bEstSocEco=valor;
	}

	public Date getHoraTraslado() {
	return dHoraTraslado;
	}

	public void setHoraTraslado(Date valor) {
            SimpleDateFormat ff=new SimpleDateFormat("HH:mm");
            String hr=ff.format(valor);
            this.sHora=hr;
	dHoraTraslado=valor;
	}

	public String getGestiones() {
	return sGestiones;
	}

	public void setGestiones(String valor) {
            vector(valor);
	sGestiones=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

    public boolean getReferencia() {
        return bReferencia;
    }

    public void setReferencia(boolean bReferencia) {
        if(bReferencia) setRefen("X"); else setRefen("");
        this.bReferencia = bReferencia;
    }

    public boolean getContrareferencia() {
        return bContrareferencia;
    }

    public void setContrareferencia(boolean bContrareferencia) {
        if(bContrareferencia)setContraRe("X"); else setContraRe("");
        this.bContrareferencia = bContrareferencia;
    }

    public boolean getOtrostramites() {
        return bOtrostramites;
    }

    public void setOtrostramites(boolean bOtrostramites) {
        if(bOtrostramites)setOtrostra("X"); else setOtrostra("");
        this.bOtrostramites = bOtrostramites;
    }

    public String getRefen() {
        return sRefen;
    }

    public void setRefen(String sRefen) {
        this.sRefen = sRefen;
    }

    public String getContraRe() {
        return sContraRe;
    }

    public void setContraRe(String sContraRe) {
        this.sContraRe = sContraRe;
    }

    public String getOtrostra() {
        return sOtrostra;
    }

    public void setOtrostra(String sOtrostra) {
        this.sOtrostra = sOtrostra;
    }
    
    public String getHora() {
        return sHora;
    }

    public void setHora(String valor) {
        this.sHora = valor;
    }
    
    public String getEstusocioEco() {
        return sEstuSocioEco;
    }

    public void setEstusocioEco(String valor) {
        this.sEstuSocioEco = valor;
    }
      
    public String[] getGesti() {
        return gesti;
    }

    public void setGesti(String[] valor) { String x="";
        for(int i=0; i<valor.length;i++){
            if(x.equals(""))
                x=valor[i];
            else
            x=x+", "+valor[i];
        }
        setGestiones(x);
        this.gesti = valor;
    }
    
    public void vector(String valor){
        String[] gest= new String[5];
        for(int i=0; i<valor.length();i++){
                if(valor.charAt(i)=='1')
                    gest[0]="1";
                if(valor.charAt(i)=='2')
                    gest[1]="2";
                if(valor.charAt(i)=='3')
                    gest[2]="3";
                if(valor.charAt(i)=='4')
                    gest[3]="4";
                if(valor.charAt(i)=='5')
                    gest[4]="5";
        }
        this.gesti=gest;
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
} 

package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public abstract class DetalleHojaSupervision implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bAspiracionSecre;
	private boolean bCateter;
	private boolean bColutorios;
	private boolean bCuraciones;
	private boolean bDialisisPeritonial;
	private boolean bEjercicioResp;
	private boolean bEjercicioVesical;
	private boolean bGlicemias;
	private boolean bLavadoBronq;
	private boolean bMonitoreo;
	private boolean bNebulizaciones;
	private boolean bNPT;
	private boolean bOxigeno;
	private boolean bSondas;
	private boolean bTransfusion;
	private boolean bVenoclisis;
	private boolean bVentilador;
        private boolean bFisioPulmonar;
        private boolean bAseosOculares;        
	private long nIdSupervision;
        private long nIdProcedimientoEnfermeria;        
        private long nIdPorcPor;
        private boolean bValorProc;    
        
        private Parametrizacion oProce;
	private PersonalHospitalario oEnfermeraSuperviso;
	private Turno oTurno;
	private ArrayList<EpisodioMedico> arrEpiMedico;
        private EpisodioMedico oEpisodio;        
        private ProcedimientoEnfermeriaPor oProcePor;   
        
        
	public DetalleHojaSupervision(){
            
            oProce = new Parametrizacion();            
            oProcePor = new ProcedimientoEnfermeriaPor();
            oEnfermeraSuperviso = new PersonalHospitalario();
            oTurno = new Turno();
            oEpisodio= new EpisodioMedico();   
            bValorProc= true;           
            
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}              
           
            
	}        
        public abstract boolean buscar()throws Exception;      
        
        
	public boolean getAspiracionSecre() {
	   return bAspiracionSecre;
	}

	public void setAspiracionSecre(boolean valor) {
	   this.bAspiracionSecre=valor;
	}

	public boolean getCateter() {
	   return bCateter;
	}

	public void setCateter(boolean valor) {
	   this.bCateter=valor;
	}

	public boolean getColutorios() {
	   return bColutorios;
	}

	public void setColutorios(boolean valor) {
	    this.bColutorios=valor;
	}

	public boolean getCuraciones() {
	   return bCuraciones;
	}

	public void setCuraciones(boolean valor) {
	   this.bCuraciones=valor;
	}

	public boolean getDialisisPeritonial() {
	    return bDialisisPeritonial;
	}

	public void setDialisisPeritonial(boolean valor) {
	   this.bDialisisPeritonial=valor;
	}

	public boolean getEjercicioResp() {
	    return bEjercicioResp;
	}

	public void setEjercicioResp(boolean valor) {
	   this.bEjercicioResp=valor;
	}

	public boolean getEjercicioVesical() {
	    return bEjercicioVesical;
	}

	public void setEjercicioVesical(boolean valor) {
	   this.bEjercicioVesical=valor;
	}

	public boolean getGlicemias() {
	    return bGlicemias;
	}

	public void setGlicemias(boolean valor) {
	   this.bGlicemias=valor;
	}

	public boolean getLavadoBronq() {
	    return bLavadoBronq;
	}

	public void setLavadoBronq(boolean valor) {
	   this.bLavadoBronq=valor;
	}

	public boolean getMonitoreo() {
	   return bMonitoreo;
	}

	public void setMonitoreo(boolean valor) {
	  this.bMonitoreo=valor;
	}

	public boolean getNebulizaciones() {
	     return bNebulizaciones;
	}

	public void setNebulizaciones(boolean valor) {
	    this.bNebulizaciones=valor;
	}

	public boolean getNPT() {
	    return bNPT;
	}

	public void setNPT(boolean valor) {
	    this.bNPT=valor;
	}

	public boolean getOxigeno() {
	    return bOxigeno;
	}

	public void setOxigeno(boolean valor) {
	    this.bOxigeno=valor;
	}

	public boolean getSondas() {
	   return bSondas;
	}

	public void setSondas(boolean valor) {
	    this.bSondas=valor;
	}

	public boolean getTransfusion() {
	   return bTransfusion;
	}

	public void setTransfusion(boolean valor) {
	   this.bTransfusion=valor;
	}

	public boolean getVenoclisis() {
	   return bVenoclisis;
	}

	public void setVenoclisis(boolean valor) {
	   this.bVenoclisis=valor;
	}

	public boolean getVentilador() {
	   return bVentilador;
	}

	public void setVentilador(boolean valor) {
	   this.bVentilador=valor;
	}
        
        public boolean getAseosOculares() {
            return bAseosOculares;
	}

	public void setAseosOculares(boolean valor) {
            this.bAseosOculares=valor;
	}

	public long getIdSupervision() {
	   return nIdSupervision;
	}

	public void setIdSupervision(long valor) {
	   this.nIdSupervision=valor;
	}

	public PersonalHospitalario getEnfermeraSuperviso() {
	   return oEnfermeraSuperviso;
	}

	public void setEnfermeraSuperviso(PersonalHospitalario valor) {
	   this.oEnfermeraSuperviso=valor;
	}

	public Turno getTurno() {
	   return oTurno;
	}

	public void setTurno(Turno valor) {
	  this.oTurno=valor;
	}

	public ArrayList<EpisodioMedico> getArrEpisodioMedico() {
	   return arrEpiMedico;
	}

	public void setArrEpisodioMedico(ArrayList<EpisodioMedico> valor) {
	   this.arrEpiMedico=valor;
	}
        
        public EpisodioMedico getEpisodio(){
            return oEpisodio;
        }
        
        public void setEpisodio( EpisodioMedico oEp){
            this.oEpisodio=oEp;
        }
        
        public boolean getValorProc(){
            return bValorProc;
        }
        
        public void setValorProc(boolean valor){
            this.bValorProc= valor;
        }
        
        public Parametrizacion getProcedimientoEnfermeria(){
            return oProce;
        }
        
        public void setProcedimientoEnfermeria(Parametrizacion proce){
            this.oProce=proce;
        }
        
        public ProcedimientoEnfermeriaPor getProcedimientoEnfermeriaPor(){
            return oProcePor;
        }
        
        public void setProcedimientoEnfermeriaPor(ProcedimientoEnfermeriaPor valor){
            this.oProcePor= valor;
        }
       
    public long getIdProcedimientoEnfermeria() {
        return nIdProcedimientoEnfermeria;
    }

    public void setIdProcedimientoEnfermeria(long nIdProcedimientoEnfermeria) {
        this.nIdProcedimientoEnfermeria = nIdProcedimientoEnfermeria;
    }

  
    public long getIdPorcPor() {
        return nIdPorcPor;
    }

    
    public void setIdPorcPor(long nIdPorcPor) {
        this.nIdPorcPor = nIdPorcPor;
    }

    public boolean getFisioPulmonar() {
        return bFisioPulmonar;
    }

    public void setFisioPulmonar(boolean bFisioPulmonar) {
        this.bFisioPulmonar = bFisioPulmonar;
    }
    
   
         

} 

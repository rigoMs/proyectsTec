package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Intencionalidad;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.SitioOcurrencia;

import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.urgencias.HojaLesiones;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Lesion implements Serializable{
	private AccesoDatos oAD;
	private boolean bAtnPreHospitalaria;
	private char bDiaFestivo;//************
	private boolean bUnicaVez;
	private Date dFechaOcurrencia;
	private String dTiempoTraslado;
	private Parametrizacion oAgenteLesion;//Se modificó
	private Parametrizacion oAreaAnatomicaGrave;
	private Parametrizacion oConsecuenciaResultante;
	private Parametrizacion oEquipoSeguridad;
	private Parametrizacion oNomEqSeguridad;
	private Parametrizacion oPacBajoEfectos;
	private Parametrizacion oSiVehiculo;
	private Parametrizacion oTipoViolencia;
	private String sDomicilioOcurrencia;
	private Intencionalidad oIntencionalidad;
	private SitioOcurrencia oSitioOcurrencia;
//************************************************
         private String sCalleNum;
         private String sOtroLugO;
//************************************************
    private String sColonia;
    private AfeccionTratada oAfeccionTratada;
    private long nFolioPaciente;
    private long nClaveEpisodio;
    private long nNumIngresoHosp;
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private FacesMessage facesMessage;
    //Agregar
    private String sCasoEventoAuto;

	private Estado oEstado;
        private Municipio oMunicipio;
        private Ciudad oCiudad;

        
        private String horaOcurrencia;
 //nuevas agregadas**********************************************
        private Referencia oReferencia;
        private Estado oEstadoUni;
        private Estado oEstadoNac;
        private CiudadCP oCiudadCP;   
        
 //************************************************************** 
        protected TipoVialidad oTipoVialidad;
        protected TipoAsentamiento oTipoAsentamiento;
        protected String sNumInterior;
        protected String sNumExterior;
//***************************************************************
        public Lesion(){
             oTipoVialidad=new TipoVialidad();
            oTipoAsentamiento= new TipoAsentamiento();
            sNumInterior="";
            sNumExterior="";
            
            oEstadoUni= new Estado();
            oReferencia= new Referencia();
            oEstadoNac= new Estado();
            oCiudadCP= new CiudadCP();
            this.sCalleNum = sCalleNum;
            this.sOtroLugO= sOtroLugO;
            this.sColonia = sColonia;
      //*********************************************************      
            oEstado= new Estado();
            oMunicipio= new Municipio();
            oCiudad= new Ciudad();
            
            oAgenteLesion=new Parametrizacion();//Se modificó
	    oAreaAnatomicaGrave=new Parametrizacion();
            oConsecuenciaResultante=new Parametrizacion();
            oEquipoSeguridad=new Parametrizacion();
            oNomEqSeguridad=new Parametrizacion();
            oPacBajoEfectos=new Parametrizacion();
            oSiVehiculo=new Parametrizacion();
            oTipoViolencia=new Parametrizacion();
            oConsecuenciaResultante=new Parametrizacion();
            oAreaAnatomicaGrave=new Parametrizacion();
            oAgenteLesion=new Parametrizacion();
            oIntencionalidad = new Intencionalidad();
            oSitioOcurrencia = new SitioOcurrencia();
            oAfeccionTratada = new AfeccionTratada();
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
			throw new Exception("Lesion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveLesion();"; 
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
	public Lesion[] buscarTodos() throws Exception{
	Lesion arrRet[]=null, oLesion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosLesion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Lesion[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
//********************************************************************************************************
        public Lesion[] buscarLesionCODE(String tipo) throws Exception{
	Lesion arrRet[]=null, oLesion=null;
	ArrayList rst = null;
        ArrayList<Lesion> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaLesionCODE('"+tipo+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Lesion>();
			for (i = 0; i < rst.size(); i++) {
                            oLesion = new Lesion();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            if(tipo.equals("INTENCIONALIDAD")){
                                oLesion.getIntencionalidad().setClaveCode(((Double)vRowTemp.get(0)).intValue());
                                oLesion.getIntencionalidad().setDescripcion((String)vRowTemp.get(1));
                            }else{
                                oLesion.getSitioOcurrencia().setClaveCode(((Double)vRowTemp.get(0)).intValue());
                                oLesion.getSitioOcurrencia().setDescripcion((String)vRowTemp.get(1));
                            }
                            vObj.add(oLesion);
			}
                    nTam = vObj.size();
                    arrRet = new Lesion[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                        //System.out.println(arrRet[i].oIntencionalidad.getClaveCode()+arrRet[i].oIntencionalidad.getDescripcion());
                    }
		} 
		return arrRet; 
	}          
//********************************************************************************************************        
    public boolean buscarCode() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Lesion.buscarCODE: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosLesionCODE("+getFolioPaciente()+"::bigint,"+getNumIngresoHosp()+"::bigint,"+getClaveEpisodio()+"::bigint);"; 
			System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.oAfeccionTratada.getCIE10().setDescripcionDiag((String) vRowTemp.get( 0 ));
                                this.oIntencionalidad.setClaveCode(((Double) vRowTemp.get( 2 )).intValue());
                                this.oSitioOcurrencia.setClaveCode(((Double) vRowTemp.get( 3 )).intValue());
				bRet = true;
			}
		} 
		return bRet; 
	}          
        
//************************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Lesion.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaLesion();"; 
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
			throw new Exception("Lesion.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaLesion();"; 
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
	public int modificarInsertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String sLlave=getFolioPaciente()+", "+getClaveEpisodio()+", "+"T0614";
		 if( this==null){   //completar llave
			throw new Exception("Lesion.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaInsertaLesionCODE('"+sUsuario+"', "+getFolioPaciente()+"::bigint,"
                                +getClaveEpisodio()+"::bigint,"+getNumIngresoHosp()+"::bigint,"+oIntencionalidad.getClaveCode()
                                +"::smallint,"+oSitioOcurrencia.getClaveCode()+"::smallint,'"+oAfeccionTratada.getCIE10().getClave()+"','"+sLlave+"');";
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
//******************************************************************************************************

	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Lesion.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaLesion();"; 
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
	public boolean getAtnPreHospitalaria() {
	return bAtnPreHospitalaria;
	}

	public void setAtnPreHospitalaria(boolean valor) {
	bAtnPreHospitalaria=valor;
	}

	public char getDiaFestivo() {
	return bDiaFestivo;
	}

	public void setDiaFestivo(char valor) {
	bDiaFestivo=valor;
	}

	public boolean getUnicaVez() {
	return bUnicaVez;
	}

	public void setUnicaVez(boolean valor) {
	bUnicaVez=valor;
	}

	public Date getFechaOcurrencia() {
	return dFechaOcurrencia;
	}

	public void setFechaOcurrencia(Date valor) {
	dFechaOcurrencia=valor;
	}

	public String getTiempoTraslado() {
	return dTiempoTraslado;
	}

	public void setTiempoTraslado(String valor) {
	dTiempoTraslado=valor;
	}

	public Parametrizacion getAgenteLesion() {
	return oAgenteLesion;
	}

	public void setAgenteLesion(Parametrizacion valor) {
	oAgenteLesion=valor;
	}

	public Parametrizacion getAreaAnatomicaGrave() {
	return oAreaAnatomicaGrave;
	}

	public void setAreaAnatomicaGrave(Parametrizacion valor) {
	oAreaAnatomicaGrave=valor;
	}

	public Parametrizacion getConsecuenciaResultante() {
	return oConsecuenciaResultante;
	}

	public void setConsecuenciaResultante(Parametrizacion valor) {
	oConsecuenciaResultante=valor;
	}

	public Parametrizacion getEquipoSeguridad() {
	return oEquipoSeguridad;
	}

	public void setEquipoSeguridad(Parametrizacion valor) {
	oEquipoSeguridad=valor;
	}

	public Parametrizacion getNomEqSeguridad() {
	return oNomEqSeguridad;
	}

	public void setNomEqSeguridad(Parametrizacion valor) {
	oNomEqSeguridad=valor;
	}

	public Parametrizacion getPacBajoEfectos() {
	return oPacBajoEfectos;
	}

	public void setPacBajoEfectos(Parametrizacion valor) {
	oPacBajoEfectos=valor;
	}

	public Parametrizacion getSiVehiculo() {
	return oSiVehiculo;
	}

	public void setSiVehiculo(Parametrizacion valor) {
	oSiVehiculo=valor;
	}

	public Parametrizacion getTipoViolencia() {
	return oTipoViolencia;
	}

	public void setTipoViolencia(Parametrizacion valor) {
	oTipoViolencia=valor;
	}

	public String getDomicilioOcurrencia() {
	return sDomicilioOcurrencia;
	}

	public void setDomicilioOcurrencia(String valor) {
	sDomicilioOcurrencia=valor;
	}

	public Intencionalidad getIntencionalidad() {
	return oIntencionalidad;
	}

	public void setIntencionalidad(Intencionalidad valor) {
	oIntencionalidad=valor;
	}

	public SitioOcurrencia getSitioOcurrencia() {
	return oSitioOcurrencia;
	}

	public void setSitioOcurrencia(SitioOcurrencia valor) {
	oSitioOcurrencia=valor;
	}

	public AfeccionTratada getAfeccionTratada() {
        return oAfeccionTratada;
    }
    
    public void setAfeccionTratada(AfeccionTratada oAfeccionTratada) {
        this.oAfeccionTratada = oAfeccionTratada;
    }

    public long getFolioPaciente() {
        return nFolioPaciente;
    }

    public void setFolioPaciente(long nFolioPaciente) {
        this.nFolioPaciente = nFolioPaciente;
    }

    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    public long getNumIngresoHosp() {
        return nNumIngresoHosp;
    }

    public void setNumIngresoHosp(long nNumIngresoHosp) {
        this.nNumIngresoHosp = nNumIngresoHosp;
    }

    /**
     * @return the sCasoEventoAuto
     */
    public String getCasoEventoAuto() {
        return sCasoEventoAuto;
    }

    /**
     * @param sCasoEventoAuto the sCasoEventoAuto to set
     */
    public void setCasoEventoAuto(String sCasoEventoAuto) {
        this.sCasoEventoAuto = sCasoEventoAuto;
    }
    
//***************************************************************************
public Estado getEstadoNac(){
return oEstadoNac;
}

public void setEstadoNac(Estado oEstadoNac){
this.oEstadoNac=oEstadoNac;
}

public Estado getEstadoUni(){
return oEstadoUni;
}
public void setEstadoUni(Estado oEstadoUni){
this.oEstadoUni=oEstadoUni;
}

//**************************************************************************
   /**
     * @return the oEstado
     */
    public Estado getEstado() {
        return oEstado;
    }

    /**
     * @param oEstado the oEstado to set
     */
    public void setEstado(Estado oEstado) {
        this.oEstado = oEstado;
    }
    
    public CiudadCP getCiudadCP() {
        return oCiudadCP;
    }
     public void setCiudadCP(CiudadCP oCiudadCP) {
        this.oCiudadCP = oCiudadCP;
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
        
        public String getOtroLugarO(){
        return sOtroLugO;
        }
        public void setOtroLugarO(String valor){
        sOtroLugO=valor;
        }
     
//******************************************************************************
    public void setReferencia(Referencia oReferencia){
    this.oReferencia=oReferencia;
    }
    
    public Referencia getReferencia(){
    return oReferencia;
    }
//******************************************************************************
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
     * @return the oCiudad
     */
    public Ciudad getCiudad() {
        return oCiudad;
    }
    

    /**
     * @param oCiudad the oCiudad to set
     */
    public void setCiudad(Ciudad oCiudad) {
        this.oCiudad = oCiudad;
    }
    /**
     * @return the horaOcurrencia
     */
    public String getHoraOcurrencia() {
        return horaOcurrencia;
    }

    /**
     * @param horaOcurrencia the horaOcurrencia to set
     */
    public void setHoraOcurrencia(String horaOcurrencia) {
        this.horaOcurrencia = horaOcurrencia;
    }
    
    
    
} 

package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class DetalleSolicitudFormula implements Serializable{
    
	private AccesoDatos oAD;
        private boolean bDisableRegistrar;
        private boolean bDisableModificar;
	private int nCantidad;	
	private int nNoTomas;
        private int nCantidadApreparar;
        private long nIdDetalleFormula;
        private String sUsuarioFirmado;
	private Parametrizacion oTipoFormula;  
        private EpisodioMedico oEpisodio;
	private CabeceraSolicitudFormula oCbStdFormula;
	private ArrayList<EpisodioMedico> arrEpisodio;        
        private ArrayList<DetalleSolicitudFormula> oArrFormulas;        
        private static final String sTablaFormula="TAH";
        
	public DetalleSolicitudFormula(){            
            oEpisodio = new EpisodioMedico();           
            oTipoFormula = new Parametrizacion();
            oCbStdFormula= new CabeceraSolicitudFormula();
            //sUsuarioFirmado="ELEAZ23";
            
	    HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
               
	}
      
	public boolean buscarFormulaPaciente() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
		 if(this.getEpisodio().getPaciente().getFolioPaciente()==0
                         || this.getEpisodio().getPaciente().getClaveEpisodio()==0
                         || this.getIdDetalleFormula()==0
                         || this.getCabeceraSolicitudFormula().getIdSolicitudFormula()==0){
			throw new Exception("DetalleSolicitudFormula.buscarFormulaPaciente: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM BuscaFormulaPacienteDetalleSolicitudFormula("+this.getIdDetalleFormula()+"::bigint,"
                                +this.getCabeceraSolicitudFormula().getIdSolicitudFormula()+"::bigint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint);";                         
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setIdDetalleFormula(((Double)vRowTemp.get(0)).longValue());
                                this.getCabeceraSolicitudFormula().setIdSolicitudFormula(((Double)vRowTemp.get(1)).longValue());
                                this.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(2)).longValue());
                                this.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(3)).longValue());
                                this.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                                this.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(5));
                                this.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(6));
                                this.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(7));
                                this.getTipoFormula().setClaveParametro((String)vRowTemp.get(8));
                                this.getTipoFormula().setValor((String)vRowTemp.get(9));
                                this.setCantidad(((Double)vRowTemp.get(10)).intValue());
                                this.setNoTomas(((Double)vRowTemp.get(11)).intValue());
				bRet = true;
			}
		} 
		return bRet; 
	}       
        
        public DetalleSolicitudFormula[] buscaPacientesParaSolictudFormula() throws Exception{
            DetalleSolicitudFormula arrRet[]=null, oPacFor=null;
            ArrayList rst=null;
            String sQuery="",edad="";
            int i=0;
            if(this.getCabeceraSolicitudFormula().getFechaString().equals("")
                    || this.getCabeceraSolicitudFormula().getServicio().getClave()==0){
                throw new Exception("DetalleSolicitudFormula.buscaPacientesParaSolictudDeFormula: error, faltan datos");
            }else{
                sQuery="SELECT * FROM BuscaPacientesParaSolicitudFormulas('"
                        +this.getCabeceraSolicitudFormula().getFechaString()+"'::date,"
                        +this.getCabeceraSolicitudFormula().getServicio().getClave()+"::smallint);";
                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst!=null && rst.size()>0){
                        arrRet = new DetalleSolicitudFormula[rst.size()];
                        for(i=0;i<rst.size();i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPacFor = new DetalleSolicitudFormula();
                            oPacFor.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                            oPacFor.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                            oPacFor.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                            oPacFor.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(3));
                            oPacFor.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(4));
                            oPacFor.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(5));
                            oPacFor.getEpisodio().getPaciente().setSexoP((String)vRowTemp.get(6));
                            edad=(String)vRowTemp.get(7);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                               if (edad.charAt(0)=='0')
                                   oPacFor.getEpisodio().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                   oPacFor.getEpisodio().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                             }else{
                                 if (edad.substring(4, 6).compareTo("00")!=0)
                                   oPacFor.getEpisodio().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                 else
                                   oPacFor.getEpisodio().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                             }
                            }
                            oPacFor.getEpisodio().getCama().setNumero((String)vRowTemp.get(8));
                            oPacFor.getEpisodio().getDiagIngreso().setClave((String)vRowTemp.get(9));
                            oPacFor.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(10));
                            oPacFor.setIdDetalleFormula(((Double)vRowTemp.get(11)).longValue());
                            oPacFor.getCabeceraSolicitudFormula().setIdSolicitudFormula(((Double)vRowTemp.get(12)).longValue());
                            oPacFor.getTipoFormula().setValor((String)vRowTemp.get(13));
                            oPacFor.setCantidad(((Double)vRowTemp.get(14)).intValue());
                            oPacFor.setNoTomas(((Double)vRowTemp.get(15)).intValue());
                            oPacFor.setDisableRegistrar(oPacFor.getIdDetalleFormula()!=0);
                            oPacFor.setDisableModificar(oPacFor.getIdDetalleFormula()==0);
                            arrRet[i]=oPacFor;
                        }
                    }
                }
                
            }
            return arrRet;
        }
     
	public DetalleSolicitudFormula[] buscarDetalleSolicitudFormulasPorFechaYServicio() throws Exception{
            DetalleSolicitudFormula arrRet[]=null, oDe=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
                if(this.getCabeceraSolicitudFormula().getServicio().getClave()==0){
                    throw new Exception("DetalleSolicitudFormula.buscarDetalleSolicitudFormulasPorFechaYServicio: error, faltan datos");
                }else{
                    sQuery = "SELECT * FROM buscaSolicitudDeFormulasPorFechaYServicio('"
                            +this.getCabeceraSolicitudFormula().getFechaString()+"'::date,"
                            +this.getCabeceraSolicitudFormula().getServicio().getClave()+"::smallint);";
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery); 
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                            arrRet = new DetalleSolicitudFormula[rst.size()];
                            for (i = 0; i < rst.size(); i++) {
                                ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oDe= new DetalleSolicitudFormula();
                                oDe.getCabeceraSolicitudFormula().setIdSolicitudFormula(((Double)vRowTemp.get(0)).longValue());
                                oDe.getCabeceraSolicitudFormula().setFechaSolicitud((Date)vRowTemp.get(1));
                                oDe.getCabeceraSolicitudFormula().getTurno().setDescripcion((String)vRowTemp.get(2));
                                oDe.getCabeceraSolicitudFormula().getServicio().setDescripcion((String)vRowTemp.get(3));
                                oDe.getCabeceraSolicitudFormula().getPersonal().setNombres((String)vRowTemp.get(4));
                                oDe.getCabeceraSolicitudFormula().getPersonal().setApPaterno((String)vRowTemp.get(5));
                                oDe.getCabeceraSolicitudFormula().getPersonal().setApMaterno((String)vRowTemp.get(6));
                                oDe.setIdDetalleFormula(((Double)vRowTemp.get(7)).longValue());
                                oDe.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
                                oDe.getEpisodio().getCama().setNumero((String)vRowTemp.get(9));
                                oDe.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(10));
                                oDe.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(11));
                                oDe.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(12));
                                oDe.getTipoFormula().setValor((String)vRowTemp.get(13));
                                oDe.setCantidad(((Double)vRowTemp.get(14)).intValue());
                                oDe.setNoTomas(((Double)vRowTemp.get(15)).intValue());
                                oDe.setCantidadApreparar(((Double)vRowTemp.get(16)).intValue());
                                arrRet[i]=oDe;
                            } 
                    } 
                }		
		return arrRet; 
	} 
        
      
	public int insertarDetalleSolicitudFormula() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		if(this.getCabeceraSolicitudFormula().getIdSolicitudFormula()==0 
                        || this.getEpisodio().getPaciente().getFolioPaciente()==0
                        || this.getEpisodio().getPaciente().getClaveEpisodio()==0 
                        || this.getTipoFormula().getClaveParametro().equals("")
                        || this.getTipoFormula().getTipoParametro().equals("") 
                        || this.getCantidad()==0 
                        || this.getNoTomas()==0){  
			throw new Exception("DetalleSolicitudFormula.insertarDetalleSolicitudFormula: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaDetalleSolicitudFormulas("
                                +this.getCabeceraSolicitudFormula().getIdSolicitudFormula()+"::bigint,"
                                +this.getCantidad()+"::integer,"
                                +this.getNoTomas()+"::smallint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,'"
                                +this.getTipoFormula().getTipoParametro()+"'::character varying,'"
                                +this.getTipoFormula().getClaveParametro()+"'::character varying,"
                                +"'"+sUsuarioFirmado+"'::character varying);";                         
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

	public int modificarFormulaPaciente() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		 if( this.getIdDetalleFormula()==0
                        || this.getCabeceraSolicitudFormula().getIdSolicitudFormula()==0
                        || this.getEpisodio().getPaciente().getFolioPaciente()==0
                        || this.getEpisodio().getPaciente().getClaveEpisodio()==0
                        || this.getTipoFormula().getClaveParametro()==null 
                        || this.getTipoFormula().getClaveParametro().equals("")
                        || this.getCantidad()==0
                        || this.getNoTomas()==0
                         ){  
			throw new Exception("DetalleSolicitudFormula.modificarFormulaPaciente: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaFormulaPacienteDetalleSolicitudFormulas("
                                +this.getIdDetalleFormula()+"::bigint,"
                                +this.getCabeceraSolicitudFormula().getIdSolicitudFormula()+"::bigint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,'"
                                +this.getTipoFormula().getClaveParametro()+"'::character varying,"
                                +this.getCantidad()+"::integer,"
                                +this.getNoTomas()+"::smallint,'"+sUsuarioFirmado+"'::character varying);";                         
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
               
        public DetalleSolicitudFormula[] ArregloFormulas() throws Exception {            
            DetalleSolicitudFormula ArrFor[]=null, oDeFor=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            sQuery="SELECT * FROM buscarFormulasTodas();";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                ArrFor = new DetalleSolicitudFormula[rst.size()];
                for(i=0; i<rst.size();i++){
                    oDeFor = new DetalleSolicitudFormula();
                    ArrayList vRowTemp= (ArrayList)rst.get(i);
                    oDeFor.getTipoFormula().setTipoParametro((String)vRowTemp.get(0));
                    oDeFor.getTipoFormula().setClaveParametro((String)vRowTemp.get(1));
                    oDeFor.getTipoFormula().setValor((String)vRowTemp.get(2));
                    oArrFormulas.add(oDeFor);
                    ArrFor[i]=oDeFor;
                }
            }
            
            return ArrFor;
        }
        
	public int getCantidad() {
	    return nCantidad;
	}

	public void setCantidad(int valor) {
	  this.nCantidad=valor;
	}

	public long getIdDetalleFormula() {
	   return nIdDetalleFormula;
	}

	public void setIdDetalleFormula(long valor) {
	    this.nIdDetalleFormula=valor;
	}

	public int getNoTomas() {
	   return nNoTomas;
	}

	public void setNoTomas(int valor) {
	  this.nNoTomas=valor;
	}

	public Parametrizacion getTipoFormula() {
	   return oTipoFormula;
	}

	public void setTipoFormula(Parametrizacion valor) {
	   this.oTipoFormula=valor;
	}

	public CabeceraSolicitudFormula getCabeceraSolicitudFormula() {
	   return oCbStdFormula;
	}

	public void setCabeceraSolicitudFormula(CabeceraSolicitudFormula valor) {
            this.oCbStdFormula=valor;
	}

	public ArrayList<EpisodioMedico> getEpisodioMedico() {
	    return arrEpisodio;
	}

	public void setEpisodioMedico(ArrayList<EpisodioMedico> valor) {
	   this.arrEpisodio=valor;
	}
        
        public ArrayList<DetalleSolicitudFormula> getArrFormulas(){
            return oArrFormulas;
        }
        
        public void setArrFormulas(ArrayList<DetalleSolicitudFormula> arrPar){
            this.oArrFormulas= arrPar;
        }
        public EpisodioMedico getEpisodio(){
            return oEpisodio;
        }
        
        public void setEpisodio(EpisodioMedico valor){
            this.oEpisodio=valor;
        }

    public boolean getDisableRegistrar() {
        return bDisableRegistrar;
    }

    public void setDisableRegistrar(boolean bDisableRegistrar) {
        this.bDisableRegistrar = bDisableRegistrar;
    }

    public boolean getDisableModificar() {
        return bDisableModificar;
    }

    public void setDisableModificar(boolean bDisableModificar) {
        this.bDisableModificar = bDisableModificar;
    }

    public int getCantidadApreparar() {
        return nCantidadApreparar;
    }

    public void setCantidadApreparar(int nCantidadApreparar) {
        this.nCantidadApreparar = nCantidadApreparar;
    }
       

} 

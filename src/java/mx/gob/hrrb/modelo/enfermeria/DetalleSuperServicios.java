package mx.gob.hrrb.modelo.enfermeria;

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

public  class DetalleSuperServicios extends DetalleHojaSupervision implements Serializable{
    
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
        private String sObservacion; 
        private boolean bControlLiquidos;
        private boolean bDrenajesCerrados;
        private boolean bAlta;
	private Date dFechaAlta;	       
	private CabeceraSuperServicios oCab;        
        private ArrayList<DetalleSuperServicios> arrProcAgregado;        

	public DetalleSuperServicios(){
            oCab = new CabeceraSuperServicios();
            //sUsuarioFirmado="JAVIE28";
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
        
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   
			throw new Exception("DetalleSuperServicios.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDetalleSuperServicios();"; 
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
        
        
	public boolean buscarLlaveDetalleSupervicionServicios() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
		if(this.getCabeceraSupervision().getIdCabecera()==0 
                         || this.getEpisodio().getPaciente().getFolioPaciente()==0
                         || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
			throw new Exception("DetalleSupervisionServicios.buscarLlaveDetalleSupervicionServicios: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDetallesupervisionServicios("
                                +this.getCabeceraSupervision().getIdCabecera()+"::bigint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setIdSupervision(((Double)vRowTemp.get(0)).longValue());
                                this.getCabeceraSupervision().setIdCabecera(((Double)vRowTemp.get(1)).longValue());                                
				bRet = true;
			}
		} 
		return bRet; 
	} 
        
	public DetalleSuperServicios[] buscarProcedimientosEnfermeriaAplicados() throws Exception{
            DetalleSuperServicios arrRet[] = null,oDeSup = null;         
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
            if(this.getCabeceraSupervision().getFechaFormat().equals("")
                    || this.getCabeceraSupervision().getAreaServicio().getClave()==0){
                throw new Exception("DetalleSuperServicios.buscarProcedimientosEnfermeriaAplicados: error, faltan datos");
            }else{                
                sQuery = "SELECT * FROM buscaProcedimientosAplicadosEnfermeriaServicios('"
                        +this.getCabeceraSupervision().getFechaFormat()+"'::date,"
                        +this.getCabeceraSupervision().getAreaServicio().getClave()+"::smallint);";                 
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                }
                if (rst != null && rst.size()>0) {
                        arrRet = new DetalleSuperServicios[rst.size()];
                        for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp= (ArrayList)rst.get(i);
                            oDeSup= new DetalleSuperServicios();
                            oDeSup.getCabeceraSupervision().setFechaSupervicion((Date)vRowTemp.get(0));
                            oDeSup.getCabeceraSupervision().getAreaServicio().setClave(((Double)vRowTemp.get(1)).intValue());
                            oDeSup.setIdSupervision(((Double)vRowTemp.get(2)).longValue());
                            oDeSup.getCabeceraSupervision().setIdCabecera(((Double)vRowTemp.get(3)).longValue());
                            oDeSup.getEpisodio().getCama().setNumero((String)vRowTemp.get(4));
                            oDeSup.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(5)).intValue());
                            oDeSup.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(6));
                            oDeSup.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(7));
                            oDeSup.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(8));
                            oDeSup.getEpisodio().setFechaIngreso((Date)vRowTemp.get(9));
                            oDeSup.setObservacion((String)vRowTemp.get(10));
                            oDeSup.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(11));
                            oDeSup.setOxigeno(!((String)vRowTemp.get(12)).equals(""));
                            oDeSup.setCateter(!((String)vRowTemp.get(13)).equals(""));
                            oDeSup.setSondas(!((String)vRowTemp.get(14)).equals(""));
                            oDeSup.setVenoclisis(!((String)vRowTemp.get(15)).equals(""));
                            oDeSup.setTransfusion(!((String)vRowTemp.get(16)).equals(""));
                            oDeSup.setVentilador(!((String)vRowTemp.get(17)).equals(""));                            
                            oDeSup.setNPT(!((String)vRowTemp.get(18)).equals(""));
                            oDeSup.setControlLiquidos(!((String)vRowTemp.get(19)).equals(""));
                            oDeSup.setGlicemias(!((String)vRowTemp.get(20)).equals(""));
                            oDeSup.setCuraciones(!((String)vRowTemp.get(21)).equals(""));
                            oDeSup.setColutorios(!((String)vRowTemp.get(22)).equals(""));
                            oDeSup.setAspiracionSecre(!((String)vRowTemp.get(23)).equals(""));
                            oDeSup.setLavadoBronq(!((String)vRowTemp.get(24)).equals(""));
                            oDeSup.setNebulizaciones(!((String)vRowTemp.get(25)).equals(""));
                            oDeSup.setFisioPulmonar(!((String)vRowTemp.get(26)).equals(""));
                            oDeSup.setDialisisPeritonial(!((String)vRowTemp.get(27)).equals(""));
                            oDeSup.setDrenajesCerrados(!((String)vRowTemp.get(28)).equals(""));
                            oDeSup.setMonitoreo(!((String)vRowTemp.get(29)).equals(""));
                            oDeSup.setEjercicioVesical(!((String)vRowTemp.get(30)).equals(""));
                            oDeSup.setEjercicioResp(!((String)vRowTemp.get(31)).equals(""));
                            oDeSup.setAlta(!((String)vRowTemp.get(32)).equals(""));
                            arrRet[i]= oDeSup;
                        } 
                } 
            }
            return arrRet; 
	} 
        
        public int insertaCabeceraDetalleProcedimiento()throws Exception{
            int nRet=-1;
            ArrayList<String> rstTemp = new ArrayList<String>();
            if(this.getCabeceraSupervision().getInsertaCabeceraSupervisionServicios().equals("")
                    || this.getInsertarDetalleDeSupervicionServicios()==null
                    && this.getInsertarDetalleDeSupervicionServicios().equals("")
                    || this.getInsertaProcidimientoEnfermeriaServicios()==null){
                throw new Exception("DetalleSuperServicios.insertaCabeceraDetalleProcedimiento:error, faltan datos");
            }else{
                rstTemp.add(this.getCabeceraSupervision().getInsertaCabeceraSupervisionServicios());
                rstTemp.add(this.getInsertarDetalleDeSupervicionServicios());
                for(String oStr: this.getInsertaProcidimientoEnfermeriaServicios()){
                    rstTemp.add(oStr);
                }
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    nRet= oAD.ejecutarConsultaComando(rstTemp);
                    oAD.desconectar();
                }
            }
            return nRet;
        }
        
        public int insertaDetalleSupervicionProcedimientosEnfermeria()throws Exception{
            int nRet=1;
            ArrayList<String> rstTem = new ArrayList<String>();
            if(this.getInsertarDetalleDeSupervicionServicios()==null
                    && this.getInsertarDetalleDeSupervicionServicios().equals("")
                    || this.getInsertaProcidimientoEnfermeriaServicios()==null){
                throw new Exception("DetalleSuperServicios.insertaDetalleSupervicionProcedimientosEnfermeria: error, faltan datos");                
            }else{
                rstTem.add(this.getInsertarDetalleDeSupervicionServicios());
                for(String oSt: this.getInsertaProcidimientoEnfermeriaServicios()){
                    rstTem.add(oSt);
                }
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    nRet= oAD.ejecutarConsultaComando(rstTem);
                    oAD.desconectar();
                }
            }
            return nRet;
        }
	public int insertarDetalleDeSupervicionServicios() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
                     if(this.getCabeceraSupervision().getIdCabecera()<0
                             || this.getEpisodio().getPaciente().getFolioPaciente()==0 
                             || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
                            throw new Exception("DetalleSupervisionServicios.insertarDetalleDeSupervicionServicios: error, faltan datos");
                    }else{ 
                            sQuery = this.getInsertarDetalleDeSupervicionServicios();
                            oAD=new AccesoDatos(); 
                            if (oAD.conectar()){ 
                                    rst = oAD.ejecutarConsulta(sQuery);
                                    oAD.desconectar(); 
                                    if (rst != null && rst.size() == 1) {
                                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                                            nRet = ((Double)vRowTemp.get(0)).intValue();
                                            setIdSupervision(((Double)vRowTemp.get(1)).longValue());
                                    }
                            }
                    } 
	    return nRet; 
	} 
        public String getInsertarDetalleDeSupervicionServicios() throws Exception{           
            String sQuery = "";
                    if(this.getCabeceraSupervision().getIdCabecera()<0
                             || this.getEpisodio().getPaciente().getFolioPaciente()==0 
                             || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
                        throw new Exception("DetalleSupervisionServicios.insertarDetalleDeSupervicionServicios: error, faltan datos");
                    }else{ 
                        sQuery = "SELECT * FROM insertaDetalleSupervisionServicios("
                                +this.getCabeceraSupervision().getIdCabecera()+"::bigint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,"
                                +((this.getObservacion()==null || this.getObservacion().equals(""))?"null":"'"+this.getObservacion()+"'")+"::character varying,'"
                                +this.getEpisodio().getCama().getNumero()+"'::character varying,'"
                                +sUsuarioFirmado+"'::character varying);";                        
                    } 
	    return sQuery; 
	} 
        
        
        public int insertarProcedimientoEnfermeria()throws Exception{
            int nRtn=-1;                        
            if(this.getInsertaProcidimientoEnfermeriaServicios()==null
                    || this.getIdSupervision()==0
                    || this.getCabeceraSupervision().getIdCabecera()==0){
                throw new Exception("DetalleSuperServicios.insertarProcedimietoEnfemeria: ERROR, Faltan Datos");
            }else{
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRtn= oAD.ejecutarConsultaComando(this.getInsertaProcidimientoEnfermeriaServicios());
                    oAD.desconectar(); 
                }                               
            }
            return nRtn;
        }
        
        public ArrayList<String> getInsertaProcidimientoEnfermeriaServicios()throws Exception{
            ArrayList<String> rst=null;
            String sQuery="";            
            if(this.getIdSupervision()<0 
                    || this.getCabeceraSupervision().getIdCabecera()<0
                    || this.getProcedimientoEnfermeria().getTipoParametro().equals("")
                    || this.getArrProcAgregado()==null){
                throw new Exception("DetalleSuperServicios.getInsertarProcedimietoEnfemeria: ERROR, Faltan Datos");
            }else{
                rst = new ArrayList<String>();
                for(DetalleSuperServicios oDe: this.getArrProcAgregado()){                    
                    sQuery="SELECT * FROM insertaProcedimientosEnfermeriaServicios("
                            +this.getIdSupervision()+"::bigint,"
                            +this.getCabeceraSupervision().getIdCabecera()+"::bigint,'"
                            +this.getProcedimientoEnfermeria().getTipoParametro()+"'::character varying,'"
                            +oDe.getProcedimientoEnfermeria().getClaveParametro()+"'::character varying,"
                            +"'1'::character,"
                            +oDe.getProcedimientoEnfermeriaPor().getClavePor()+"::integer);";
                    rst.add(sQuery);
                }
                if(this.getObservacion()!=null
                    && !this.getObservacion().equals("")){
                    rst.add(this.getModificarDetalleSupervision());
                }
                
            }
            return rst;
        }
        
	public int modificar() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		 if( this.getIdSupervision()==0){  
			throw new Exception("DetalleSupervisionServicios.modificar: error, faltan datos");
		}else{ 
			sQuery = this.getModificarDetalleSupervision();
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
        
        public String getModificarDetalleSupervision()throws Exception{
            String sQuery="";
            if(this.getObservacion()==null
                    || this.getObservacion().equals("")){
                throw new Exception("DetalleSuperServicios.getModificarDetalleSupervision: error, faltan datos");
            }else{
                sQuery="SELECT * FROM modificaDetallesupervisionServicios("+this.getIdSupervision()+"::bigint,"
                        +this.getCabeceraSupervision().getIdCabecera()+"::bigint,'"
                        +this.getObservacion()+"'::character varying);";
            }
            return sQuery;
        }
        
        public DetalleSuperServicios[] buscaProcedimientosServicios(String tipo) throws Exception{
            DetalleSuperServicios arrRet[]=null, oDeS=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            sQuery = "SELECT * FROM buscaProcedimientosServicios('"+tipo+"'::character varying);";                
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
                    if(rst!=null && rst.size()>0){
                        arrRet= new DetalleSuperServicios[rst.size()];
                        for(i=0;i<rst.size();i++){
                            ArrayList vRowTemp= (ArrayList)rst.get(i);
                            oDeS= new DetalleSuperServicios();
                            oDeS.getProcedimientoEnfermeria().setTipoParametro((String)vRowTemp.get(0));
                            oDeS.getProcedimientoEnfermeria().setClaveParametro((String)vRowTemp.get(1));
                            oDeS.getProcedimientoEnfermeria().setValor((String)vRowTemp.get(2));
                            arrRet[i]=oDeS;
                        }
                    }                        
		}
            return arrRet;
        }
        
    public Date getFechaAlta() {
        return dFechaAlta;
    }

    public void setFechaAlta(Date valor) {
        this.dFechaAlta=valor;
    }

    public String getObservacion() {
        return sObservacion;
    }

    public void setObservacion(String valor) {
        this.sObservacion=valor;
    }

    public CabeceraSuperServicios getCabeceraSupervision() {
        return oCab;
    }

    public void setCabeceraSupervision(CabeceraSuperServicios valor) {
        this.oCab=valor;
    }

    public ArrayList<DetalleSuperServicios> getArrProcAgregado() {
        return arrProcAgregado;
    }

    public void setArrProcAgregado(ArrayList<DetalleSuperServicios> arrProcAgregado) {
        this.arrProcAgregado = arrProcAgregado;
    }

    public boolean getControlLiquidos() {
        return bControlLiquidos;
    }

    public void setControlLiquidos(boolean sControlLiquidos) {
        this.bControlLiquidos = sControlLiquidos;
    }

    public boolean getDrenajesCerrados() {
        return bDrenajesCerrados;
    }

    public void setDrenajesCerrados(boolean sDrenajesCerrados) {
        this.bDrenajesCerrados = sDrenajesCerrados;
    }

    public boolean getAlta() {
        return bAlta;
    }

    public void setAlta(boolean sAlta) {
        this.bAlta = sAlta;
    }
        

} 

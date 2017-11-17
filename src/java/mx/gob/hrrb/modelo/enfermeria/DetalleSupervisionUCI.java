package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class DetalleSupervisionUCI extends DetalleHojaSupervision implements Serializable{
    
	private AccesoDatos oAD;	
	private boolean bPVC;
        private String sUsuarioFirmado;
	private String sObservacionGeneral;
	private String sObservacionPaciente;
	private CabeceraSupervisionUCI oCbSupervUCI;   
        private ArrayList<DetalleSupervisionUCI> arrProcedimientos;
        private static final  String sTablaProc="TAG";        
       
        
	public DetalleSupervisionUCI(){            
            oCbSupervUCI = new CabeceraSupervisionUCI();            
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
		 if( this==null){   //completar llave
			throw new Exception("DetalleSupervisionUCI.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDetalleSupervisionUCI();"; 
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
       
        public boolean buscarLlaveDetalleSupervision()throws Exception{
            boolean bRtn=false;
            ArrayList rst=null;
            String sQuery="";
            if(this.getCabeceraSupervisionUCI().getIdCabeceraUCI()==0 
                    && this.getEpisodio().getPaciente().getFolioPaciente()==0
                    && this.getEpisodio().getPaciente().getClaveEpisodio()==0){
                throw new Exception("DetalleSupervisionUCI.buscaClaveDetalleSupervision: ERROR, Faltan Datos");
            }else{
                sQuery="SELECT * FROM buscaLlaveDetallesupervisionUCI("
                        +this.getCabeceraSupervisionUCI().getIdCabeceraUCI()+"::bigint,"
                        +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                        +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint);";
                //System.out.println(sQuery);
                oAD= new AccesoDatos();                
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst!=null && rst.size()==1){
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        this.setIdSupervision(((Double)vRowTemp.get(0)).longValue());
                        this.getCabeceraSupervisionUCI().setIdCabeceraUCI(((Double)vRowTemp.get(1)).longValue());
                        this.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(2)).longValue());
                        this.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(3)).longValue());
                        /*this.setObservacionPaciente((String)vRowTemp.get(4));
                        this.setObservacionGeneral((String)vRowTemp.get(5));*/
                        this.getTurno().setClave((String)vRowTemp.get(6));
                        this.getEnfermeraSuperviso().setNoTarjeta(((Double)vRowTemp.get(7)).intValue());
                        this.getEpisodio().getCama().setNumero((String)vRowTemp.get(8));
                        bRtn= true;
                    }
                }                
            }
            return bRtn;
        }
     
        public DetalleSupervisionUCI[] buscaProcedimientosAplicadosPorFechaYServicio() throws Exception{
            DetalleSupervisionUCI arrRet[]=null, oDeUCI=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
                if(this.getCabeceraSupervisionUCI().getFechaString().equals("")
                        || this.getCabeceraSupervisionUCI().getArea().getClave()==0){
                    throw new Exception("DetalleSupervisionUCI.buscaProcedimientosAplicadosPorFechaYServicio: error, faltan datos");
                }else{
                    sQuery = "SELECT * FROM buscaProcedimientosAplicadosEnfermeriaTerapiaIntenciva('"
                            +this.getCabeceraSupervisionUCI().getFechaString()+"'::date,"
                            +this.getCabeceraSupervisionUCI().getArea().getClave()+"::smallint);"; 
                    //System.out.println(sQuery);
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                    }
                    if (rst != null) {
                        arrRet = new DetalleSupervisionUCI[rst.size()];
                        for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDeUCI= new DetalleSupervisionUCI();
                            oDeUCI.getCabeceraSupervisionUCI().setFechaSupervision((Date)vRowTemp.get(0));
                            oDeUCI.getCabeceraSupervisionUCI().getArea().setClave(((Double)vRowTemp.get(1)).intValue());
                            oDeUCI.setIdSupervision(((Double)vRowTemp.get(2)).longValue());
                            oDeUCI.getCabeceraSupervisionUCI().setIdCabeceraUCI(((Double)vRowTemp.get(3)).longValue());
                            oDeUCI.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                            oDeUCI.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                            oDeUCI.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                            oDeUCI.setObservacionPaciente((String)vRowTemp.get(7));
                            oDeUCI.setObservacionGeneral((String)vRowTemp.get(8));
                            oDeUCI.getEpisodio().getCama().setNumero((String)vRowTemp.get(9));
                            oDeUCI.setOxigeno(!((String)vRowTemp.get(10)).equals(""));
                            oDeUCI.setCateter(!((String)vRowTemp.get(11)).equals(""));
                            oDeUCI.setSondas(!((String)vRowTemp.get(12)).equals(""));
                            oDeUCI.setVenoclisis(!((String)vRowTemp.get(13)).equals(""));
                            oDeUCI.setTransfusion(!((String)vRowTemp.get(14)).equals(""));
                            oDeUCI.setVentilador(!((String)vRowTemp.get(15)).equals(""));
                            oDeUCI.setNPT(!((String)vRowTemp.get(16)).equals(""));
                            oDeUCI.setGlicemias(!((String)vRowTemp.get(17)).equals(""));
                            oDeUCI.setCuraciones(!((String)vRowTemp.get(18)).equals(""));
                            oDeUCI.setColutorios(!((String)vRowTemp.get(19)).equals(""));
                            oDeUCI.setAspiracionSecre(!((String)vRowTemp.get(20)).equals(""));
                            oDeUCI.setLavadoBronq(!((String)vRowTemp.get(21)).equals(""));
                            oDeUCI.setNebulizaciones(!((String)vRowTemp.get(22)).equals(""));
                            oDeUCI.setFisioPulmonar(!((String)vRowTemp.get(23)).equals(""));
                            oDeUCI.setDialisisPeritonial(!((String)vRowTemp.get(24)).equals(""));
                            oDeUCI.setMonitoreo(!((String)vRowTemp.get(25)).equals(""));
                            oDeUCI.setEjercicioVesical(!((String)vRowTemp.get(26)).equals(""));
                            oDeUCI.setEjercicioResp(!((String)vRowTemp.get(27)).equals(""));
                            oDeUCI.setPVC(!((String)vRowTemp.get(28)).equals(""));
                            oDeUCI.setAseosOculares(!((String)vRowTemp.get(29)).equals(""));
                            oDeUCI.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(30));
                            oDeUCI.getEpisodio().setFechaIngreso((Date)vRowTemp.get(31));
                            oDeUCI.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(32)).intValue());
                            arrRet[i]=oDeUCI;
                        } 
                    }                     
                }		
		return arrRet; 
	}
        
        public int insertarCabeceraDetalleProcedimiento()throws Exception{
            int nRtn=-1;
            ArrayList<String> arrTem=null;
            ArrayList<String> rst=null;
            if(this.getCabeceraSupervisionUCI().getInsertarCabeceraSupervisionUCI().equals("")
                    || this.getInsertarDetalleSupervisionUCI().equals("")
                    || this.getInsertarPorcedimientoEnfermeriaUCI()==null){
                throw new Exception("DetalleSupervisionUCI.insertarCabeceraDetalleProcedimiento: error, faltan datos");
            }else{
                rst=new ArrayList<String>();
                rst.add(this.getCabeceraSupervisionUCI().getInsertarCabeceraSupervisionUCI());
                rst.add(this.getInsertarDetalleSupervisionUCI());                
                arrTem=this.getInsertarPorcedimientoEnfermeriaUCI();
                for(String oS:arrTem){
                    rst.add(oS);                    
                }               
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    nRtn= oAD.ejecutarConsultaComando(rst);
                    oAD.desconectar();                    
                }
            }
            return nRtn;
        }   
        
        public int insertaDetalleUCIyProcedimiento() throws Exception{
            int nRtn=-1;
            ArrayList<String> rst=null;
            if(this.getInsertarDetalleSupervisionUCI().equals("")
                    || this.getInsertarPorcedimientoEnfermeriaUCI()==null){
                throw new Exception("DetalleSupervisionUCI.insertaDetalleUCIyProcedimiento: error, faltan datos");
                
            }else{
                rst=new ArrayList<String>();
                rst.add(this.getInsertarDetalleSupervisionUCI());
                for(String oSt: this.getInsertarPorcedimientoEnfermeriaUCI()){
                    rst.add(oSt);
                }
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    nRtn= oAD.ejecutarConsultaComando(rst);
                    oAD.desconectar();
                }
            }
            return nRtn;
        }
        
        
        public int insertarDetalleSupervisionUCI() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		if(this.getCabeceraSupervisionUCI().getIdCabeceraUCI()==0
                        || this.getEpisodio().getPaciente().getFolioPaciente()==0
                        || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
			throw new Exception("DetalleSupervisionUCI.insertarDetalleSupervisionUCI: error, faltan datos");
		}else{ 
			sQuery = this.getInsertarDetalleSupervisionUCI();
			//System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
                                        this.setIdSupervision(((Double)vRowTemp.get(1)).longValue());
				}
			}
		} 
		return nRet; 
	} 

       
	public String getInsertarDetalleSupervisionUCI() throws Exception{           
            String sQuery = "";
		if(this.getCabeceraSupervisionUCI().getIdCabeceraUCI()<0
                        || this.getEpisodio().getPaciente().getFolioPaciente()==0
                        || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
			throw new Exception("DetalleSupervisionUCI.insertarDetalleSupervisionUCI: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaDetallesupervisionUCI("
                                +this.getCabeceraSupervisionUCI().getIdCabeceraUCI()+"::bigint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,"
                                +(this.getObservacionPaciente()==null || this.getObservacionPaciente().equals("")?"null":"'"+this.getObservacionPaciente()+"'")+"::character varying,"
                                +(this.getObservacionGeneral()==null || this.getObservacionGeneral().equals("")?"null":"'"+this.getObservacionGeneral()+"'")+"::character varying,'"
                                +this.getDeterminaTurno()+"'::character varying,'"
                                +this.getEpisodio().getCama().getNumero()+"'::character varying,"
                                +"'"+sUsuarioFirmado+"'::character varying);"; 
			//System.out.println(sQuery);
                        
		} 
		return sQuery; 
	} 

        public int insertarPorcedimientoEnfermeriaUCI()throws Exception{
            int nRtn=-1;
            String sQuery="";           
                if(this.getInsertarPorcedimientoEnfermeriaUCI()==null){
                    throw new Exception("DetalleSupervisionUCI.insertarProcedimientoEnfermeriaUCI: ERROR, Faltan Datos");
                }
                else{                                      
                    oAD= new AccesoDatos();
                    if(oAD.conectar()){                        
                        nRtn = oAD.ejecutarConsultaComando(this.getInsertarPorcedimientoEnfermeriaUCI());                     
                        oAD.desconectar();
                    }
                }                
                /*for(String oSt: this.getInsertarPorcedimientoEnfermeriaUCI()){
                    System.out.println(oSt);
                }*/
                return nRtn;
        }
        
        public ArrayList<String> getInsertarPorcedimientoEnfermeriaUCI()throws Exception{            
            String sQuery="";
            ArrayList<String> rst=null;
                if(this.getIdSupervision()<0 
                        || this.getCabeceraSupervisionUCI().getIdCabeceraUCI()<0){
                    throw new Exception("DetalleSupervisionUCI.insertarProcedimientoEnfermeriaUCI: ERROR, Faltan Datos");
                }
                else{
                    rst= new ArrayList<String>();
                    for(DetalleSupervisionUCI aUCI : this.getArrProcedimientos()){
                       sQuery="SELECT * FROM insertaProcedimientoEnfermeriaUCI("+this.getIdSupervision()+"::bigint,"
                               +this.getCabeceraSupervisionUCI().getIdCabeceraUCI()+"::bigint,'"
                               +aUCI.getProcedimientoEnfermeria().getTipoParametro()+"'::character varying,'"
                               +aUCI.getProcedimientoEnfermeria().getClaveParametro()+"'::character varying,"
                               +"'1'::character,"+aUCI.getProcedimientoEnfermeriaPor().getClavePor()+"::integer);";         
                       rst.add(sQuery);
                    }
                    if(!this.getModificar().equals("") && this.getIdSupervision()!=0){
                        rst.add(this.getModificar());
                    }                    
                }
                return rst;
        }  
        
	public int modificarDetalleSupervisionUCI() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		 if( !this.getObservacionPaciente().equals("")
                         || !this.getObservacionGeneral().equals("")){  
                     sQuery = this.getModificar();
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
        
        public String getModificar() throws Exception{            
            String sQuery = "";
            if(this.getObservacionGeneral()!=null
                    && !this.getObservacionGeneral().equals("")
                    || this.getObservacionPaciente()!=null
                    && !this.getObservacionPaciente().equals("")){
                sQuery = "SELECT * FROM modificaDetallesupervisionUCI('"+sUsuarioFirmado+"'::character varying,"
                            +this.getIdSupervision()+"::bigint,"
                            +this.getCabeceraSupervisionUCI().getIdCabeceraUCI()+"::bigint,"
                            +(this.getObservacionPaciente()==null || this.getObservacionPaciente().equals("")?"null":"'"+this.getObservacionPaciente()+"'")+"::character varying,"
                            +(this.getObservacionGeneral()==null || this.getObservacionGeneral().equals("")?"null":"'"+this.getObservacionGeneral()+"'")+"::character varying);";
            }
            return sQuery;  
        }
        
        public DetalleSupervisionUCI[] buscaProcedimientosTerapiaIntenciva(String tipo) throws Exception{
            DetalleSupervisionUCI arrRet[]=null, oUCI=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            sQuery = "SELECT * FROM buscaProcedimientosTerapiaIntenciva('"+tipo+"'::character varying);"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
                        if(rst!=null && rst.size()>0){
                            arrRet= new DetalleSupervisionUCI[rst.size()];
                            for(i=0;i<rst.size();i++){
                                ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oUCI= new DetalleSupervisionUCI();
                                oUCI.getProcedimientoEnfermeria().setTipoParametro((String)vRowTemp.get(0));
                                oUCI.getProcedimientoEnfermeria().setClaveParametro((String)vRowTemp.get(1));
                                oUCI.getProcedimientoEnfermeria().setValor((String)vRowTemp.get(2));
                                arrRet[i]=oUCI;
                            }
                            
                        }
		}
                return arrRet;           
        }
        
                 

       /* public AreaServicioHRRB[] buscarServiciosTerapiaIntensiva()throws Exception{
            AreaServicioHRRB arrRet[]=null, oArea=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
                sQuery="SELECT * FROM buscaServiciosTerapiaIntensiva();";
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst!=null && rst.size()>0){
                        arrRet= new AreaServicioHRRB[rst.size()];
                        for(i=0; i<rst.size();i++){
                            oArea= new AreaServicioHRRB();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oArea.setClave(((Double)vRowTemp.get(0)).intValue());
                            oArea.setDescripcion((String)vRowTemp.get(1));
                            oArea.setTipo((String)vRowTemp.get(2));
                            arrRet[i]=oArea;
                        }
                    }
                }
            return arrRet;
        }*/
        
        public boolean getPVC() {
            return bPVC;
	}

	public void setPVC(boolean valor) {
            this.bPVC=valor;
	}

	public String getObservacionGeneral() {
            return sObservacionGeneral;
	}

	public void setObservacionGeneral(String valor) {
            this.sObservacionGeneral=valor;
	}

	public String getObservacionPaciente() {
            return sObservacionPaciente;
	}

	public void setObservacionPaciente(String valor) {
            this.sObservacionPaciente=valor;
	}

	public CabeceraSupervisionUCI getCabeceraSupervisionUCI() {
            return oCbSupervUCI;
	}

	public void setCabeceraSupervisionUCI(CabeceraSupervisionUCI valor) {
            this.oCbSupervUCI=valor;
	}  
        
        public String getDeterminaTurno(){ 
         Date dFechaActual2= new Date();
         SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dFechaActual2);
         Double horaActual=Double.parseDouble(sT);
         sTurno= (horaActual>07.00 && horaActual<15.00)?"MAT": (horaActual > 15.00 && horaActual < 21.00)?"VES":"NOC";        
         return sTurno;
     } 

    public ArrayList<DetalleSupervisionUCI> getArrProcedimientos() {
        return arrProcedimientos;
    }

    public void setArrProcedimientos(ArrayList<DetalleSupervisionUCI> arrProcedimientos) {
        this.arrProcedimientos = arrProcedimientos;
    }

} 

package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Paciente;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class SolicitudSangre implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaRegistro;
	private Date dFechaSurtido;
	private float nHb;
	private float nHo;
	private int nNumPlaquetas;
        private int nIdSolicitud;
	private float nTp;
	private float nTTp;
	private Parametrizacion oTipoPacSolicita;
	private Parametrizacion oTipoSangre;
	private Parametrizacion oTipoSolicitud;
	private String sNombreInstitucion;
	private ArrayList<DetalleSolicitudHemoderivado> oDetalleHemoderivado;
        private Paciente oPaciente;
        private EpisodioMedico oEpisodio;
        private Parametrizacion oUrgencia;
        private Parametrizacion oRH;
        private boolean bEmbarazosPrevios;
        private boolean bTransfusionPrevia;
        private String sEmbPrevios;
        private String sTransPrevia;
        private Date dFechaUltTrans;
        private ServicioRealizado oSerReal;
        private DetalleSolicitudHemoderivado oDetalle;
        private int nCantidad;
        private Donador oDonador;

	public SolicitudSangre(){
        oTipoPacSolicita = new Parametrizacion();
        oDetalle = new DetalleSolicitudHemoderivado();
        oTipoSangre = new Parametrizacion();
        oRH = new Parametrizacion();
        oPaciente = new Paciente();
        oEpisodio = new EpisodioMedico();
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudSangre.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSolicitudSangre();"; 
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
	public SolicitudSangre[] buscarTodos() throws Exception{
	SolicitudSangre arrRet[]=null, oSolicitudSangre=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSolicitudSangre();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SolicitudSangre[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudSangre.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSolicitudSangre('"+sUsuarioFirmado+"');"; 
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
        
        public ProductoHemoderivado[] buscarProductoHemoderivado() throws Exception{
            ProductoHemoderivado arrRet[]=null, oProducto=null;
            ArrayList<ProductoHemoderivado> vObj=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0, nTam=0;
            sQuery = "SELECT * FROM buscaproductohemoderivado();";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ProductoHemoderivado>();
                for(i=0;i<rst.size();i++){
                    oProducto = new ProductoHemoderivado();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oProducto.setClave((String)vRowTemp.get(0));
                    oProducto.setDescripcion((String)vRowTemp.get(1));
                    vObj.add(oProducto);
                }
                nTam = vObj.size();
                arrRet = new ProductoHemoderivado[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
        
        public int insertaSolicitudApoyoInstitucional(ArrayList<ProductoHemoderivado> lSol, String sClave) throws Exception{
            int mod = 0;
            ArrayList rst=null, vRowTemp = null;
            String sQuery="";
            if(lSol.size()<1){
                throw new Exception("SolicitudSangre.insertaSolicitudApoyoInstitucional: error, faltan datos");
            }else{
                if(this.getNombreInstitucion()==null){
                    setNombreInstitucion("null");
                }
                sQuery = "SELECT * FROM insertaApoyoExterno('"+ sUsuarioFirmado +"'::character varying, "
                        + "'"+ getNombreInstitucion() +"'::character varying, "
                        + "'"+ getPaciente().getNombres() +"'::character varying, "
                        + "'"+ getPaciente().getApPaterno() +"'::character varying, "
                        + "'"+ getPaciente().getApMaterno() +"'::character varying, "
                        + "'"+ sClave +"'::character(6), "
                        + "'2'::character(3), "
                        + ""+ getHb() +"::numeric, "
                        + ""+ getHo() +"::numeric, "
                        + "'"+ getTipoSangre().getClaveParametro() +"'::character(3), "
                        + "'"+ getRH().getClaveParametro() +"'::character(3), "
                        + ""+ getTp() +"::numeric, "
                        + ""+ getTTp() +"::numeric, "
                        + ""+ getNumPlaquetas() +"::smallint, ARRAY[";
                    for(ProductoHemoderivado ss:lSol){
                        if(ss.getClave()!=null){
                            sQuery = sQuery + "'"+ ss.getClave() +"', ";
                        }
                    }
                    sQuery = sQuery.substring(0, sQuery.length()-2);
                    sQuery = sQuery + "]::character varying[], ARRAY[";
                    for(ProductoHemoderivado ss:lSol){
                        if(ss.getUnidades() != 0){
                            sQuery = sQuery + ""+ ss.getUnidades() +", ";
                        }
                    }
                    sQuery = sQuery.substring(0, sQuery.length()-2);
                    sQuery = sQuery + "]::smallint[]);";
                    System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    
                    if(rst != null && rst.size()==1){
                        vRowTemp = (ArrayList) rst.get(0);
                        mod = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return mod;
        }
        
        public void asosiacionDatos(List<ProductoHemoderivado> arrProd){
            for(ProductoHemoderivado ph:arrProd){
                if(ph.getDescripcion().equals(getDetalle().getProductoH().getDescripcion())){
                    this.getDetalle().getProductoH().setClave(ph.getClave());
                    this.getDetalle().getProductoH().setDescripcion(ph.getDescripcion());
                }
            }
        }
        
        public SolicitudSangre[] buscarSolicitudesBS(Date fecha) throws Exception{
            SolicitudSangre arrRet[]=null, oSolSan=null;
            ArrayList<SolicitudSangre> vObj=null;
            ArrayList rst=null;
            SimpleDateFormat dFec=new SimpleDateFormat("yyyy-MM-dd");
            int i=0, nTam=0;
            String sQuery="";
            oAD = new AccesoDatos();
            sQuery="SELECT * FROM buscarTodosSolProdBancoSangre('" + dFec.format(fecha) +"');";
            System.out.println(sQuery);
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj=new ArrayList<SolicitudSangre>();
                for(i=0; i<rst.size();i++){
                    oSolSan=new SolicitudSangre();
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oSolSan.oEpisodio=new EpisodioMedico();
                    oSolSan.oEpisodio.setPaciente(new Paciente());
                    oSolSan.setUrgencia(new Parametrizacion());
                    oSolSan.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oSolSan.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oSolSan.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oSolSan.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oSolSan.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(4));
                    oSolSan.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(5));
                    oSolSan.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(6));
                    oSolSan.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                    oSolSan.oUrgencia.setValor((String)vRowTemp.get(8));
                    oSolSan.setIdSolicitud(((Double)vRowTemp.get(9)).intValue());
                    vObj.add(oSolSan);
                    
                }
                nTam=vObj.size();
                arrRet=new SolicitudSangre[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i]=vObj.get(i);
                }
            }
            
            return arrRet;
        } 
        
        
        public SolicitudSangre buscarDetalleSolSan(int nIdSolicitud) throws Exception{
            boolean bRet=false;
            ArrayList rst=null;
            SolicitudSangre oSolSan=null;
            String sQuery="";
            if(nIdSolicitud == 0){
                throw new Exception("SolicitudSangre: error de programación, faltan datos");
            }
            else{
                sQuery="SELECT * FROM buscarDatosSolBanSan("+  nIdSolicitud +");";
                System.out.println(sQuery);
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null && rst.size()==1){
                    oSolSan = new SolicitudSangre();
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(0);
                    oSolSan.oEpisodio=new EpisodioMedico();
                    oSolSan.oDetalle = new DetalleSolicitudHemoderivado();
                    oSolSan.setTipoSangre(new Parametrizacion());
                    oSolSan.setUrgencia(new Parametrizacion());
                    oSolSan.setRH(new Parametrizacion());
                    oSolSan.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oSolSan.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oSolSan.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oSolSan.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oSolSan.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(4));
                    oSolSan.oEpisodio.getCama().setNumero((String)vRowTemp.get(5));
                    String edad=(String)vRowTemp.get(6);
                    if(edad.compareTo("")!=0){
                         if(edad.substring(0,3).compareTo("000")!=0){
                             if(edad.charAt(0)=='0')
                                 oSolSan.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                             else
                                 oSolSan.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                         }else{
                             if(edad.substring(4,6).compareTo("00")!=0)
                                 oSolSan.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                             else
                                 oSolSan.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                         }
                     }
                    oSolSan.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(7));
                    oSolSan.setTransPrevia((String)vRowTemp.get(8));
                    oSolSan.setFechaUltTrans((Date)vRowTemp.get(9));
                    int nEmb = ((Double)vRowTemp.get(10)).intValue();
                    oSolSan.sEmbPrevios = nEmb == 0 ? "NO":"SI"; 
                    oSolSan.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(11));
                    oSolSan.setHb(((Double)vRowTemp.get(12)).floatValue());
                    oSolSan.setHo(((Double)vRowTemp.get(13)).floatValue());
                    oSolSan.getTipoSangre().setValor((String)vRowTemp.get(14));
                    oSolSan.getRH().setValor((String)vRowTemp.get(15));
                    oSolSan.setTp(((Double)vRowTemp.get(16)).floatValue());
                    oSolSan.setTTp(((Double)vRowTemp.get(17)).floatValue());
                    oSolSan.setNumPlaquetas(((Double)vRowTemp.get(18)).intValue());
                    oSolSan.getUrgencia().setValor((String)vRowTemp.get(19));
                    oSolSan.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(20));
                    oSolSan.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(21));
                    oSolSan.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(22));
                    oSolSan.oDetalle.setFechaSolicitud((Date)vRowTemp.get(23));
                    oSolSan.setDetalleHemoderivado(new ArrayList<DetalleSolicitudHemoderivado>(Arrays.asList(new DetalleSolicitudHemoderivado().buscarTodosPorSolicitud(nIdSolicitud))));
                    bRet=true;
                }
            }
            return oSolSan;
        }
        
        public SolicitudSangre[] buscarSolicitudesAmbulatorias(Date fecha) throws Exception{
            SolicitudSangre arrRet[]=null, oSolSan=null;
            ArrayList<SolicitudSangre> vObj=null;
            ArrayList rst=null;
            SimpleDateFormat dFec=new SimpleDateFormat("yyyy-MM-dd");
            int i=0, nTam=0;
            String sQuery="";
            oAD = new AccesoDatos();
            sQuery="SELECT * FROM buscarTodosSolAmbulatorias('" + dFec.format(fecha) +"');";
            System.out.println(sQuery);
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj=new ArrayList<SolicitudSangre>();
                for(i=0; i<rst.size();i++){
                    oSolSan=new SolicitudSangre();
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oSolSan.oEpisodio=new EpisodioMedico();
                    oSolSan.oEpisodio.setPaciente(new Paciente());
                    oSolSan.setUrgencia(new Parametrizacion());
                    oSolSan.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oSolSan.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oSolSan.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oSolSan.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oSolSan.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(4));
                    oSolSan.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(5));
                    oSolSan.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(6));
                    oSolSan.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                    oSolSan.oUrgencia.setValor((String)vRowTemp.get(8));
                    oSolSan.setIdSolicitud(((Double)vRowTemp.get(9)).intValue());
                    vObj.add(oSolSan);
                    
                }
                nTam=vObj.size();
                arrRet=new SolicitudSangre[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i]=vObj.get(i);
                }
            }
            
            return arrRet;
        } 
        
        
        public boolean buscarDetTranAmbulatoria() throws Exception{
            boolean bRet=false;
            ArrayList rst=null;
            SolicitudSangre oSolSan=null;
            String sQuery="";
            if(this.getIdSolicitud()==0){
                throw new Exception("SolicituSangre:error de programación, faltan datos");
            }
            else{
                sQuery="SELECT * FROM buscarDetTranAmbulatoria("+ this.getIdSolicitud()  +");";
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null && rst.size() == 1){
                    oSolSan=new SolicitudSangre();
                    ArrayList<Object>vRowTemp=(ArrayList)rst.get(0);
                    oSolSan.oEpisodio=new EpisodioMedico();
                    oSolSan.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oSolSan.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oSolSan.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oSolSan.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oSolSan.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(4));
                    oSolSan.oEpisodio.getPaciente().setEdadNumero((String)vRowTemp.get(5));
                    String edad=(String)vRowTemp.get(7);
                   if(edad.compareTo("")!=0){
                        if(edad.substring(0,3).compareTo("000")!=0){
                            if(edad.charAt(0)=='0')
                                oSolSan.oPaciente.setEdadNumero(edad.substring(1,3)+" AÑOS");
                            else
                                oSolSan.oPaciente.setEdadNumero(edad.substring(0,3)+" AÑOS");
                        }else{
                            if(edad.substring(4,6).compareTo("00")!=0)
                                oSolSan.oPaciente.setEdadNumero(edad.substring(4,6)+ " MESES");
                            else
                                oSolSan.oPaciente.setEdadNumero(edad.substring(7,9)+ " DIAS");
                        }
                    }
                    oSolSan.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(7));
                    oSolSan.setTransfusionPrevia((boolean)vRowTemp.get(8));
                    oSolSan.setFechaUltTrans((Date)vRowTemp.get(9));
                    oSolSan.setEmbarazosPrevios((boolean)vRowTemp.get(10));
                    oSolSan.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(11));
                    oSolSan.setHb(((Double)vRowTemp.get(12)).floatValue());
                    oSolSan.setHo(((Double)vRowTemp.get(13)).intValue());
                    oSolSan.oTipoSangre.setValor((String)vRowTemp.get(14));
                    oSolSan.oRH.setValor((String)vRowTemp.get(15));
                    oSolSan.setTp(((Double)vRowTemp.get(16)).intValue());
                    oSolSan.setTTp(((Double)vRowTemp.get(17)).intValue());
                    oSolSan.setNumPlaquetas(((Double)vRowTemp.get(18)).intValue());
                    bRet=true;
                }
            }
            return bRet;
        }
        
        public SolicitudSangre buscarDetalleHojaEgreso(String sNombres, String sApPaterno, String sApMaterno, int nNumExpe, Date dFecSol) throws Exception{
            SolicitudSangre oSol = null;
            ArrayList rst=null;
            String sQuery="";
            SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
            if(!sNombres.equals("") && !sApPaterno.equals("")){
                sQuery = "SELECT * FROM buscardatospacientehojaegreso('"+ sNombres +"','"+ sApPaterno  +"','"+ sApMaterno +"',0,'"+ oFec.format(dFecSol) +"');";
            }else if(sNombres.equals("") && sApPaterno.equals("") && nNumExpe != 0){
                sQuery = "SELECT * FROM buscardatospacientehojaegreso('','','',"+ nNumExpe +",'"+ oFec.format(dFecSol) +"');";
            }else {
                throw new Exception("SolicitudSangre.buscarDetalleHojaEgreso: error, faltan  datos");
            }
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size()==1){
                oSol = new SolicitudSangre();
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                oSol.oEpisodio = new EpisodioMedico();
                oSol.setTipoSangre(new Parametrizacion());
                oSol.setRH(new Parametrizacion());
                oSol.oDetalle = new DetalleSolicitudHemoderivado();
                oSol.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oSol.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oSol.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oSol.getTipoSangre().setValor((String)vRowTemp.get(3));
                oSol.getRH().setValor((String)vRowTemp.get(4));
                oSol.oDetalle.setFechaSolicitud((Date)vRowTemp.get(5));
                oSol.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(6));
                oSol.oEpisodio.getCama().setNumero((String)vRowTemp.get(7));
                oSol.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
            }
            return oSol;
        }
        
        public SolicitudSangre[] buscarDonadoresPaciente(String sNombre, String sApPaterno, String sApMaterno, int nNumExpe, Date dFecSolicitud) throws Exception{
            SolicitudSangre arrRet[]=null, oSolicitud=null;
            ArrayList<SolicitudSangre> vObj=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0, nTam=0;
            SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
            if(!sNombre.equals("") && !sApPaterno.equals("") && nNumExpe == 0){
                sQuery = "SELECT * FROM buscarDonadoresPaciente('"+ sNombre +"','"+ sApPaterno +"','"+ sApMaterno  +"',0,'"+ oFec.format(dFecSolicitud) +"');";
            }else if(sNombre.equals("") && sApPaterno.equals("") && nNumExpe != 0){
                sQuery = "SELECT * FROM buscarDonadoresPaciente('','','',"+ nNumExpe +",'"+ oFec.format(dFecSolicitud) +"');";
            }else{
                throw new Exception("SolicitudSangre.buscarDonadoresPaciente: error, faltan datos");
            }
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<SolicitudSangre>();
                for(i=0;i<rst.size();i++){
                    oSolicitud = new SolicitudSangre();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oSolicitud.oEpisodio = new EpisodioMedico();
                    oSolicitud.oDetalle = new DetalleSolicitudHemoderivado();
                    oSolicitud.oDonador = new Donador();
                    oSolicitud.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oSolicitud.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oSolicitud.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oSolicitud.oEpisodio.getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oSolicitud.oDetalle.getProductoH().setDescripcion((String)vRowTemp.get(4));
                    oSolicitud.setCantidad(((Double)vRowTemp.get(5)).intValue());
                    oSolicitud.oDetalle.setFechaSolicitud((Date)vRowTemp.get(6));
                    oSolicitud.setIdSolicitud(((Double)vRowTemp.get(7)).intValue());
                    oSolicitud.oDonador.setNombreDonador((String)vRowTemp.get(8));
                    vObj.add(oSolicitud);
                }
                nTam = vObj.size();
                arrRet = new SolicitudSangre[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
        
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudSangre.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSolicitudSangre('"+sUsuarioFirmado+"');"; 
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
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudSangre.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSolicitudSangre('"+sUsuarioFirmado+"');"; 
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
	public Date getFechaRegistro() {
	return dFechaRegistro;
	}

	public void setFechaRegistro(Date valor) {
	dFechaRegistro=valor;
	}

	public Date getFechaSurtido() {
	return dFechaSurtido;
	}

	public void setFechaSurtido(Date valor) {
	dFechaSurtido=valor;
	}

	public float getHb() {
	return nHb;
	}

	public void setHb(float valor) {
	nHb=valor;
	}

	public float getHo() {
	return nHo;
	}

	public void setHo(float valor) {
	nHo=valor;
	}

	public int getNumPlaquetas() {
	return nNumPlaquetas;
	}

	public void setNumPlaquetas(int valor) {
	nNumPlaquetas=valor;
	}

	public float getTp() {
	return nTp;
	}

	public void setTp(float valor) {
	nTp=valor;
	}

	public float getTTp() {
	return nTTp;
	}

	public void setTTp(float valor) {
	nTTp=valor;
	}

	public Parametrizacion getTipoPacSolicita() {
	return oTipoPacSolicita;
	}

	public void setTipoPacSolicita(Parametrizacion valor) {
	oTipoPacSolicita=valor;
	}

	public Parametrizacion getTipoSangre() {
	return oTipoSangre;
	}

	public void setTipoSangre(Parametrizacion valor) {
	oTipoSangre=valor;
	}

	public Parametrizacion getTipoSolicitud() {
	return oTipoSolicitud;
	}

	public void setTipoSolicitud(Parametrizacion valor) {
	oTipoSolicitud=valor;
	}

	public String getNombreInstitucion() {
	return sNombreInstitucion;
	}

	public void setNombreInstitucion(String valor) {
	sNombreInstitucion=valor;
	}

	
    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public EpisodioMedico getEpisodio() {
        return oEpisodio;
    }

    public void setEpisodio(EpisodioMedico oEpisodio) {
        this.oEpisodio = oEpisodio;
    }

    public Parametrizacion getUrgencia() {
        return oUrgencia;
    }

    public void setUrgencia(Parametrizacion oUrgencia) {
        this.oUrgencia = oUrgencia;
    }

    public Parametrizacion getRH() {
        return oRH;
    }

    public void setRH(Parametrizacion oRH) {
        this.oRH = oRH;
    }

    public int getIdSolicitud() {
        return nIdSolicitud;
    }

    public void setIdSolicitud(int nIdSolicitud) {
        this.nIdSolicitud = nIdSolicitud;
    }

    public ArrayList<DetalleSolicitudHemoderivado> getDetalleHemoderivado() {
        return oDetalleHemoderivado;
    }

    public void setDetalleHemoderivado(ArrayList<DetalleSolicitudHemoderivado> oDetalleHemoderivado) {
        this.oDetalleHemoderivado = oDetalleHemoderivado;
    }

    public boolean getEmbarazosPrevios() {
        return bEmbarazosPrevios;
    }

    public void setEmbarazosPrevios(boolean bEmbarazosPrevios) {
        this.bEmbarazosPrevios = bEmbarazosPrevios;
    }

    public boolean getTransfusionPrevia() {
        return bTransfusionPrevia;
    }

    public void setTransfusionPrevia(boolean bTransfusionPrevia) {
        this.bTransfusionPrevia = bTransfusionPrevia;
    }

    public Date getFechaUltTrans() {
        return dFechaUltTrans;
    }

    public void setFechaUltTrans(Date dFechaUltTrans) {
        this.dFechaUltTrans = dFechaUltTrans;
    }

    public ServicioRealizado getSerReal() {
        return oSerReal;
    }

    public void setSerReal(ServicioRealizado oSerReal) {
        this.oSerReal = oSerReal;
    }

    public String getEmbPrevios() {
        return sEmbPrevios;
    }

    public void setEmbPrevios(String sEmbPrevios) {
        this.sEmbPrevios = sEmbPrevios;
    }

    public String getTransPrevia() {
        return sTransPrevia;
    }

    public void setTransPrevia(String sTransPrevia) {
        this.sTransPrevia = sTransPrevia;
    }

    public DetalleSolicitudHemoderivado getDetalle() {
        return oDetalle;
    }

    public void setDetalle(DetalleSolicitudHemoderivado oDetalle) {
        this.oDetalle = oDetalle;
    }

    public int getCantidad() {
        return nCantidad;
    }

    public void setCantidad(int nCantidad) {
        this.nCantidad = nCantidad;
    }

    public Donador getDonador() {
        return oDonador;
    }

    public void setDonador(Donador oDonador) {
        this.oDonador = oDonador;
    }

    

} 

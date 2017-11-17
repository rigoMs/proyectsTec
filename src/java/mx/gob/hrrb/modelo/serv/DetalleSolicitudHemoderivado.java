package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 * @author : Rafael, Pablo
 * @version: 1.0
*/

public class DetalleSolicitudHemoderivado extends ServicioRealizado implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private String sNumCruce;
private ProductoHemoderivado oProductoH;
private SolicitudSangre oSolicitudS;
	public DetalleSolicitudHemoderivado(){
            oProductoH = new ProductoHemoderivado();
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
        
        public DetalleSolicitudHemoderivado[] buscarTodosPorSolicitud(int nIdSolicitud) throws Exception{
            DetalleSolicitudHemoderivado arrRet[]=null, oDetSol=null;
            ArrayList<DetalleSolicitudHemoderivado> vObj=null;
            ArrayList rst=null;
            int i=0, nTam=0;
            String sQuery="";
            if(nIdSolicitud == 0){
                throw new Exception("DetalleSolicitudHemoderivado:error de programación, falta datos;");
            }
            else{
                sQuery="SELECT * FROM buscarProductosHemoPorSolicitud("+ nIdSolicitud +")";
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null){
                    vObj=new ArrayList<DetalleSolicitudHemoderivado>();
                    for(i=0;i<rst.size();i++){
                        oDetSol=new DetalleSolicitudHemoderivado();
                        ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                        oDetSol.oProductoH=new ProductoHemoderivado();
                        oDetSol.oSolicitudS = new SolicitudSangre();
                        oDetSol.oProductoH.setClave((String)vRowTemp.get(0));
                        oDetSol.oProductoH.setDescripcion((String)vRowTemp.get(1));
                        oDetSol.setCantSolicitada(((Double)vRowTemp.get(2)).shortValue());
                        oDetSol.setIdentificador(((Double)vRowTemp.get(3)).intValue());
                        vObj.add(oDetSol);
                    }
                    nTam=vObj.size();
                    arrRet=new DetalleSolicitudHemoderivado[nTam];
                    
                    for(i=0; i<nTam;i++){
                        arrRet[i]=vObj.get(i);
                    }
                }
            }
            
            return arrRet;
        }
        
        
        
        public int modificarEstadoProductoSolicitado(int nClave, int nIdentificador) throws Exception{
            int i=0;
            ArrayList rst= null;
            String sQuery="";
            if(nIdentificador == 0){
                throw new Exception("DetalleSolicitudHemoderivado.modificarEstadoProductoSolicitado: error, faltan datos");
            }else{
                if(nClave == 1){
                    sQuery ="SELECT * FROM modificarEstadoProductoPorSolicitud(1,'"+ sUsuarioFirmado +"'::character varying,"
                            + ""+ nIdentificador +"::bigint, "+ this.getCantSurtida() +"::smallint);";
                }else if(nClave == 2){
                    sQuery ="SELECT * FROM modificarEstadoProductoPorSolicitud(2,'"+ sUsuarioFirmado +"'::character varying,"
                            + ""+ nIdentificador +"::bigint, 0::smallint);";
                }
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst != null && rst.size()==1){
                        ArrayList vRowTemp=(ArrayList)rst.get(0);
                        i=((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return i;
        }
        
        
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("DetalleSolicitudHemoderivado.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDetalleSolicitudHemoderivado();"; 
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
        @Override
	public DetalleSolicitudHemoderivado[] buscarTodos() throws Exception{
	DetalleSolicitudHemoderivado arrRet[]=null, oDetalleSolicitudHemoderivado=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosDetalleSolicitudHemoderivado();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new DetalleSolicitudHemoderivado[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("DetalleSolicitudHemoderivado.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaDetalleSolicitudHemoderivado('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("DetalleSolicitudHemoderivado.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaDetalleSolicitudHemoderivado('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("DetalleSolicitudHemoderivado.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDetalleSolicitudHemoderivado('"+sUsuarioFirmado+"');"; 
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
        
        public int insertaSolBancoSangre(ArrayList<ProductoHemoderivado> lSol) throws Exception{
        int mod=0;
        ArrayList rst=null, vRowTemp=null;
        String sQuery="";
            if(this.oEpisodio == null && lSol==null){
                throw new Exception("DetalleSolicitudHemoderivado.insertaSolBancoSangre: error, faltan datos");
            }else{
                SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                sQuery = "SELECT * FROM insertasolicitudproductoshemo('"+ sUsuarioFirmado +"'::character varying,"
                        + getEpisodio().getPaciente().getFolioPaciente() +"::bigint,"
                        + getEpisodio().getClaveEpisodio() +"::bigint,"
                        + getAutorizadoPor().getNoTarjeta() +"::integer,'"
                        + getSolicitudS().getUrgencia().getClaveParametro() +"','"
                        + getSolicitudS().getTipoSangre().getClaveParametro() +"','"
                        + getSolicitudS().getTipoPacSolicita().getClaveParametro() +"',"
                        + getSolicitudS().getHb() +"::numeric,"
                        + getSolicitudS().getHo() +"::numeric,"
                        + getSolicitudS().getTp() +"::numeric,"
                        + getSolicitudS().getTTp() +"::numeric,"
                        + getSolicitudS().getNumPlaquetas() +"::smallint,"
                        + (getSolicitudS().getFechaUltTrans()==null?
                        "null":
                        "'"+format.format(getSolicitudS().getFechaUltTrans())+"'") +"::timestamp without time zone,"
                        + getSolicitudS().getEmbPrevios() +"::smallint,'"
                        + getSolicitudS().getRH().getClaveParametro() +"','"
                        + getSolicitudS().getTipoSolicitud().getClaveParametro() + "',ARRAY[";
                for(ProductoHemoderivado ss:lSol){
                    if(ss.getClave()!=null){
                        sQuery = sQuery + "'"+ ss.getClave() +"', ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::character varying[], ARRAY[";
                for(ProductoHemoderivado ss:lSol){
                    if(ss.getUnidades() != 0){
                        sQuery = sQuery + "" + ss.getUnidades() +", ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::smallint[]);";
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

    public DetalleSolicitudHemoderivado[] buscaIDServRealDonador() throws Exception{
    DetalleSolicitudHemoderivado arrRet[]=null, oID=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
        sQuery =  "select * from  buscaidServRealBancoDeSangreDonador("+
                this.getEpisodio().getPaciente().getFolioPaciente()+","+
                this.getEpisodio().getClaveEpisodio()+");"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null) {
            arrRet = new DetalleSolicitudHemoderivado[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oID=new DetalleSolicitudHemoderivado(); 
                ArrayList vRowTemp = (ArrayList)rst.get(i); 
                oID.setIdentificador(((Double)vRowTemp.get(0)).intValue());
                arrRet[i]=oID;
            } 
        } 
        return arrRet; 
    }
        
    public int modificarServicoRealCasosEspeci(String usuario) throws Exception{
    SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
    Date now = new Date();
    String strDate = SF.format(now);
    int Resul=0; 
    ArrayList rst = null;
    String sQuery = ""; 
        
        if( this.getIdentificador()==0){   //completar llave
            throw new Exception("DetalleSolicitudHemoderivado.ModificarServicoRealCasosEspeci: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from modificaServicioRealCasoEspecial('"+usuario+"', "+
                    this.getIdentificador()+", '"+strDate+"',"+
                    this.numnull(this.getCostoUnitACobrar().intValue())+","+
                    numnull(this.getAutorizadoPor().getNoTarjeta())+", 'T81', '"+
                    this.getSitPago().getTipoParametro()+"');";  
                        
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                Resul=(((Double)vRowTemp.get( 0 )).intValue());
            }
        } 
        return Resul; 
    }
     
    public String numnull(int num){
        if(num==0){
            return null;
        }
        else{
            return ""+num;
        }
    }   
    
    public String getNumCruce() {
    return sNumCruce;
    }

    public void setNumCruce(String valor) {
    sNumCruce=valor;
    }

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oProductoH;
    }

    public SolicitudSangre getSolicitudS() {
        return oSolicitudS;
    }

    public void setSolicitudS(SolicitudSangre oSolicitudS) {
        this.oSolicitudS = oSolicitudS;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable oValor) {
        this.oProductoH = (ProductoHemoderivado)oValor;
    }
    
    //Por EL
    public ProductoHemoderivado getProductoH(){
        return this.oProductoH;
    }
} 

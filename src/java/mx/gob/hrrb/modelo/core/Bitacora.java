
package mx.gob.hrrb.modelo.core;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import java.util.Date;

/**
 *
 * @author DEL
 */
public class Bitacora implements Serializable {
       	
        private long ClaveBitacora;
        private String IdUsuario;
        private String Accion;
        private Date FechaHoraAccion;
        private String Tabla;
        private String Llave;
        
        private AccesoDatos oAD;
        private Firmado oFirm;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        
        public  Bitacora(){
            buscarUsuario();
        }
        
        public boolean buscarUsuario(){
            boolean bRet = false;
            faceContext = FacesContext.getCurrentInstance();
            httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
            if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null )
            {
                oFirm = (Firmado)httpServletRequest.getSession().getAttribute( "oFirm" );
                IdUsuario = oFirm.getUsu().getIdUsuario();
                bRet = ( IdUsuario != null && !IdUsuario.isEmpty() );
            }
            return bRet;
        }
        
        public Bitacora[] buscarTodos() throws Exception{
	Bitacora arrRet[]=null, oBitacora=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
	sQuery = "SELECT * FROM buscaBitacora();"; 
            
        if (oAD == null){
               oAD = new AccesoDatos();
               if (oAD.conectar()){
                   rst = oAD.ejecutarConsulta(sQuery);
                   oAD.desconectar();
               }
               oAD = null;
            }
            else{
                rst = oAD.ejecutarConsulta(sQuery);
            }
            if (rst != null) {
                arrRet = new Bitacora[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    oBitacora = new Bitacora();
                     ArrayList vRowTemp = (ArrayList)rst.get(i);
                     oBitacora.setClaveBitacora(((Double) vRowTemp.get(0)).longValue());
                     oBitacora.setIdUsuario((String) vRowTemp.get(1));
                     oBitacora.setAccion((String) vRowTemp.get(2));
                     oBitacora.setFechaHoraAccion((Date) vRowTemp.get(3));
                     oBitacora.setTabla((String) vRowTemp.get(4));
                     oBitacora.setLlave((String) vRowTemp.get(5));
                    //fields = DATA
                    arrRet[ i ] = oBitacora;
                }
            }
            return arrRet;
        } 

        public boolean buscarTabla() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Bitacora.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAutocompletarTablas();"; 
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
        public boolean buscarUsu() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Bitacora.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAutocompletarNombreUsuario();"; 
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
   
    public long getClaveBitacora() {
        return ClaveBitacora;
    }

    public void setClaveBitacora(long ClaveBitacora) {
        this.ClaveBitacora = ClaveBitacora;
    }

    public String getIdUsuario() {
        return IdUsuario;
    }

    public void setIdUsuario(String IdUsuario) {
        this.IdUsuario = IdUsuario;
    }

    public String getAccion() {
        return Accion;
    }

    public void setAccion(String Accion) {
        this.Accion = Accion;
    }

    public Date getFechaHoraAccion() {
        return FechaHoraAccion;
    }

    public void setFechaHoraAccion(Date FechaHoraAccion) {
        this.FechaHoraAccion = FechaHoraAccion;
    }

    public String getTabla() {
        return Tabla;
    }

    public void setTabla(String Tabla) {
        this.Tabla = Tabla;
    }

    public String getLlave() {
        return Llave;
    }

    public void setLlave(String Llave) {
        this.Llave = Llave;
    }     
}

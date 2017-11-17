package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ProductoHemoderivado extends ServicioCobrable implements Serializable{
private String sClave;
private String sDescripcion;
private ArrayList<SolicitudSangre> oSolicitudSangre;
private int nUnidades; //Muestra la cantidad de un productos en una solicitud

    public ProductoHemoderivado(){
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
			throw new Exception("ProductoHemoderivado.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveProductoHemoderivado();"; 
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
	public ProductoHemoderivado[] buscarTodos() throws Exception{
	ProductoHemoderivado arrRet[]=null, oProductoHemoderivado=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosProductoHemoderivado();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ProductoHemoderivado[rst.size()];
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
			throw new Exception("ProductoHemoderivado.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaProductoHemoderivado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ProductoHemoderivado.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaProductoHemoderivado('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ProductoHemoderivado.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaProductoHemoderivado('"+sUsuarioFirmado+"');"; 
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
        
        /////////////////////////////////////////////////////////////////////////
        //Órdenes de servicio
        public ProductoHemoderivado[] buscarProductoHem() throws Exception{
	ProductoHemoderivado arrRet[]=null, oHem=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaproductohemoderivado();";
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ProductoHemoderivado[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oHem = new ProductoHemoderivado();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oHem.setClave(((String)vRowTemp.get(0)).trim());
                            oHem.setDescripcion(((String)vRowTemp.get(1)).trim());
                            arrRet[i]=oHem;
                            }
		} 
		return arrRet; 
	}
        
        
        public void equalsProductoHemo(ProductoHemoderivado arrProHemo[]){
                    for(ProductoHemoderivado ProHemo:arrProHemo){
                        if(this.getDescripcion().equals(ProHemo.getDescripcion())){
                            this.setClave(ProHemo.getClave());
                            this.setDescripcion(ProHemo.getDescripcion());
                        }
                    }
        }
        
        
/////////////////////////////////////////////////////////////////////

	public ArrayList<SolicitudSangre> getSolicitudSangre() {
	return oSolicitudSangre;
	}

	public void setSolicitudSangre(ArrayList<SolicitudSangre> valor) {
	oSolicitudSangre=valor;
	}

    public int getUnidades() {
        return nUnidades;
    }

    public void setUnidades(int nUnidades) {
        this.nUnidades = nUnidades;
    }

} 

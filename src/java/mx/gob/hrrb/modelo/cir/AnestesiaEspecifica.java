package mx.gob.hrrb.modelo.cir;

import mx.gob.hrrb.modelo.core.Parametrizacion;
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

public  class AnestesiaEspecifica implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private short nCve;
	private Parametrizacion oTipoAnestesia;
	private String sDescripcion;
        private String sClave;

	public AnestesiaEspecifica(){
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
			throw new Exception("AnestesiaEspecifica.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAnestesiaEspecifica();"; 
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
	//Modificaciones despues de entregar
        public AnestesiaEspecifica[] buscarTodos() throws Exception{
	AnestesiaEspecifica arrRet[]=null, oAnestesiaEspecifica=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<AnestesiaEspecifica> vObj = null;
        int res =0;
	int i=0, nTam=0;
		//sQuery = "SELECT * FROM buscaTodosAnestesiaEspecifica();"; 
                sQuery = "SELECT * FROM anestesiaespecifica;";
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                  /* arrRet = new AnestesiaEspecifica[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                        oAnestesiaEspecifica = new AnestesiaEspecifica();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oAnestesiaEspecifica.setCve(((Double) vRowTemp.get(0)).shortValue());
                        oAnestesiaEspecifica.setDescripcion((String) vRowTemp.get(1));
                        arrRet[i] = oAnestesiaEspecifica;
                    
                    } 
                   */
                    
                    vObj= new ArrayList<AnestesiaEspecifica>();
                    for(i=0; i<rst.size(); i++){
                        oAnestesiaEspecifica = new AnestesiaEspecifica();
                        ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                        oAnestesiaEspecifica.setClave((String) vRowTemp.get(0));
                        oAnestesiaEspecifica.setDescripcion((String) vRowTemp.get(1));
                        vObj.add(oAnestesiaEspecifica);
                    }
                    nTam = vObj.size();
                    arrRet = new AnestesiaEspecifica[nTam];
                    
                    for(i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                } 
                return arrRet; 
        }
        
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AnestesiaEspecifica.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAnestesiaEspecifica('"+sUsuarioFirmado+"');"; 
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
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AnestesiaEspecifica.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAnestesiaEspecifica('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("AnestesiaEspecifica.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAnestesiaEspecifica('"+sUsuarioFirmado+"');"; 
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
	public short getCve() {
	return nCve;
	}

	public void setCve(short valor) {
	nCve=valor;
	}

	public Parametrizacion getTipoAnestesia() {
	return oTipoAnestesia;
	}

	public void setTipoAnestesia(Parametrizacion valor) {
	oTipoAnestesia=valor;
	}

	public String getDescripcion() {
	return sDescripcion;
	}

	public void setDescripcion(String valor) {
	sDescripcion=valor;
	}

    /**
     * @return the sClave
     */
    public String getClave() {
        return sClave;
    }

    /**
     * @param sClave the sClave to set
     */
    public void setClave(String sClave) {
        this.sClave = sClave;
    }


} 

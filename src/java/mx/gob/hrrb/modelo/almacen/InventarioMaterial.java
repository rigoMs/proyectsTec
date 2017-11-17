package mx.gob.hrrb.modelo.almacen;

import mx.gob.hrrb.modelo.core.Parametrizacion;
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

public  class InventarioMaterial implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dCaducidad;
	private int nExistencia;
	private int nExistenciaConvertida;
	private double nPrecio;
	private Parametrizacion oEstado;
	private String sLote;
	public ArrayList<ControlMat> oControlMat;
	private Material oMaterial;
        private String sUnidadConvertida;

	public InventarioMaterial(){
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
			throw new Exception("InventarioMaterial.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveInventarioMaterial();"; 
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
	public InventarioMaterial[] buscarTodos() throws Exception{
	InventarioMaterial arrRet[]=null, oInventarioMaterial=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosInventarioMaterial();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new InventarioMaterial[rst.size()];
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
			throw new Exception("InventarioMaterial.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaInventarioMaterial('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("InventarioMaterial.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaInventarioMaterial('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("InventarioMaterial.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaInventarioMaterial('"+sUsuarioFirmado+"');"; 
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
        
        public InventarioMaterial[] buscarMaterialCEYE()throws Exception{
            InventarioMaterial oInventario = null, arrRet[] = null;
            ArrayList rst = null;
            ArrayList<InventarioMaterial> vObj = null;
            String sQuery = "";
            int i = 0, nTam = 0;
            if(this == null){
                throw new Exception("InventarioMaterial.buscar: error de programación, faltan datos");
            }else{
                sQuery = "SELECT * FROM buscaMaterialCEYE();";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null){
                    vObj = new ArrayList<InventarioMaterial>();
                    for(i=0; i<rst.size(); i++){
                        ArrayList vRowTemp = (ArrayList) rst.get(i);
                        oInventario = new InventarioMaterial();
                        oInventario.setMaterial(new Material());
                        oInventario.getMaterial().setPresentacion(new Parametrizacion());
                        //Datos
                        oInventario.getMaterial().setClaveMaterial((String) vRowTemp.get(0));
                        oInventario.getMaterial().setNombre((String) vRowTemp.get(1));
                        oInventario.setCaducidad((Date) vRowTemp.get(2));
                        oInventario.setExistencia(((Double) vRowTemp.get(3)).intValue());
                        oInventario.setExistenciaConvertida(((Double) vRowTemp.get(4)).intValue());
                        oInventario.setUnidadConvertida((String) vRowTemp.get(5));
                        oInventario.getMaterial().getPresentacion().setValor((String) vRowTemp.get(6));
                        vObj.add(oInventario);
                    }
                }
                nTam = vObj.size();
                arrRet = new InventarioMaterial[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
        
	public Date getCaducidad() {
	return dCaducidad;
	}

	public void setCaducidad(Date valor) {
	dCaducidad=valor;
	}

	public int getExistencia() {
	return nExistencia;
	}

	public void setExistencia(int valor) {
	nExistencia=valor;
	}

	public int getExistenciaConvertida() {
	return nExistenciaConvertida;
	}

	public void setExistenciaConvertida(int valor) {
	nExistenciaConvertida=valor;
	}

	public double getPrecio() {
	return nPrecio;
	}

	public void setPrecio(double valor) {
	nPrecio=valor;
	}

	public Parametrizacion getEstado() {
	return oEstado;
	}

	public void setEstado(Parametrizacion valor) {
	oEstado=valor;
	}

	public String getLote() {
	return sLote;
	}

	public void setLote(String valor) {
	sLote=valor;
	}

    public String getUnidadConvertida() {
        return sUnidadConvertida;
    }

    public void setUnidadConvertida(String sUnidadConvertida) {
        this.sUnidadConvertida = sUnidadConvertida;
    }

    public Material getMaterial() {
        return oMaterial;
    }

    public void setMaterial(Material oMaterial) {
        this.oMaterial = oMaterial;
    }

} 

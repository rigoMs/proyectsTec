package mx.gob.hrrb.modelo.almacen;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.DetalleValeMaterial;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ValeMaterial implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaEmision;
	private Date dFechaSurtido;
	private int nFolioVale;	
        private ArrayList<DetalleValeMaterial> arrDetVale;
        private ProcedimientosRealizados oProcReal;
        private int bCancelado;
        private String sSituacionVale;

	public ValeMaterial(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                arrDetVale = new ArrayList<DetalleValeMaterial>();
	}
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("ValeMaterial.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveValeMaterial();"; 
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
        
        public ValeMaterial[] buscarTodos() throws Exception{
	ValeMaterial arrRet[]=null, oValeMaterial=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosValeMaterial();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ValeMaterial[rst.size()];
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
			throw new Exception("ValeMaterial.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaValeMaterial('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValeMaterial.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaValeMaterial('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValeMaterial.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaValeMaterial('"+sUsuarioFirmado+"');"; 
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
        
        public int cancelarValeOsteosintesis(ArrayList<DetalleValeMaterial> arr)throws Exception{
            System.out.println("Estamos Cancelandolo");
            int nRet = 0;
            ArrayList rst = null, vRowTemp = null;
            String sQuery = "";
            if(arr == null || arr.isEmpty())
                throw new Exception("ValeMaterial.cancelarValeOsteosintesis: Faltan Datos");
            else{
                sQuery = "SELECT * FROM cancelaValeMaterialOsteosintesis('"+sUsuarioFirmado+"',"+nFolioVale+",ARRAY[";
                for(DetalleValeMaterial oDet : arr){
                    if(oDet.getIdentificador() != 0){
                        sQuery = sQuery + "" + oDet.getIdentificador()+", ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::bigint[]);";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst != null && rst.size()==1){
                        vRowTemp = (ArrayList) rst.get(0);
                        nRet = ((Double)vRowTemp.get(0)).intValue();
                    }
                }    
            }
            return nRet;
        }
        
        public int modificarConjunto(ArrayList<DetalleValeMaterial> arr) throws Exception{
            System.out.println("Estamos en ModificarConjunto");
            int nRet = -1;
            ArrayList<String> arrAfectaciones = new ArrayList<String>();
            
            if(arr == null || arr.isEmpty())
                throw new Exception("ValeColectivo.modicarConjunto: Faltan Datos");
            else{
                for(DetalleValeMaterial oDetalle: arr){
                    if(oDetalle.getCantSurtida()>0)
                        arrAfectaciones.add(oDetalle.getModificarTablaOsteositesis());
                }
                if(oAD == null){
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                        nRet = oAD.ejecutarConsultaComando(arrAfectaciones); 
                        oAD.desconectar(); 
                    }
                    oAD = null;
                }
                else{
                    nRet = oAD.ejecutarConsultaComando(arrAfectaciones);
                }
            }
            return nRet;
        }
        
        public Date getFechaEmision() {
	return dFechaEmision;
	}

	public void setFechaEmision(Date valor) {
	dFechaEmision=valor;
	}

	public Date getFechaSurtido() {
	return dFechaSurtido;
	}

	public void setFechaSurtido(Date valor) {
	dFechaSurtido=valor;
	}

	public int getFolioVale() {
	return nFolioVale;
	}

	public void setFolioVale(int valor) {
	nFolioVale=valor;
	}

	public ProcedimientosRealizados getProcReal() {
            return oProcReal;
        }
        
        public void setProcReal(ProcedimientosRealizados oProcReal) {
            this.oProcReal = oProcReal;
        }
        
        public ArrayList<DetalleValeMaterial> getArrDetalle(){
            return this.arrDetVale;
        }
        
        public void setArrDetalle(ArrayList<DetalleValeMaterial> oVal){
            this.arrDetVale = oVal;
        }
        
    public int getCancelado() {
        return bCancelado;
    }
    
    public void setCancelado(int bCancelado) {
        this.bCancelado = bCancelado;
    }

    public String getSituacionVale() {
        return sSituacionVale;
    }

    public void setSituacionVale(String sSituacionVale) {
        this.sSituacionVale = sSituacionVale;
    }
        
        

} 

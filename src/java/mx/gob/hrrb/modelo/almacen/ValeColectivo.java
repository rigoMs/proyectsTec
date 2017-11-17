package mx.gob.hrrb.modelo.almacen;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ValeColectivo implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaEmision;
	private int nIdHoja;
	private AreaServicioHRRB oAreaAtiende;
	private AreaServicioHRRB oAreaSolicita;
	private Parametrizacion oTipoVale;
        private PersonalHospitalario oPersonal;
	private InventarioMaterial oInventario;
        private ArrayList<DetalleValeColectivo> arrDetValeCol;
        private Date dFechaSurtido;

	public ValeColectivo(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oAreaAtiende = new AreaServicioHRRB();
                oAreaSolicita = new AreaServicioHRRB();
                oTipoVale = new Parametrizacion();
                oPersonal = new PersonalHospitalario();
	}
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("ValeColectivo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveValeColectivo();"; 
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
	
        public ValeColectivo[] buscarTodos() throws Exception{
	ValeColectivo arrRet[]=null, oValeColectivo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosValeColectivo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ValeColectivo[rst.size()];
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
			throw new Exception("ValeColectivo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaValeColectivo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValeColectivo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaValeColectivo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValeColectivo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaValeColectivo('"+sUsuarioFirmado+"');"; 
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
        
        
        public ValeColectivo[] buscarSolicitudesMaterialCEYE() throws Exception{
	ValeColectivo arrRet[]=null, oVale=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<ValeColectivo> vObj = null;
        DetalleValeColectivo oDetalle;
	int i=0, nTam = 0;
        System.out.println("La fecha que traemos es ----> "+ this.getFechaEmision());
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        if (this.getFechaEmision() == null){
            throw new Exception("ProcedimientosRealizados.insertar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscaSolicitudesdeMaterialdeCuracionCEYE('"+fecha.format(getFechaEmision())+"');"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            System.out.println(rst);
            if (rst != null && rst.size()>0) {
                vObj = new ArrayList<ValeColectivo>();
                oVale = new ValeColectivo();
                oVale.oAreaSolicita = new AreaServicioHRRB();
                oVale.oPersonal = new PersonalHospitalario();
                oVale.arrDetValeCol = new ArrayList<DetalleValeColectivo>();
                oVale.oInventario = new InventarioMaterial();
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    int nHoja = ((Double) vRowTemp.get(0)).intValue();
                    System.out.println("Llave de identificdor actual " +nHoja);
                    if(nHoja == oVale.getIdHoja() ){
                        System.out.println("Llave de identificdor de Vale Material " +nHoja + " " + "Llave con la que compara " + oVale.getIdHoja());
                        oDetalle = new DetalleValeColectivo();
                        oDetalle.setVale(new ValeColectivo());
                        oDetalle.setServicioCobrable(new Material());
                        oDetalle.setSituacion(new Parametrizacion());
                        //Manda Datos de los Materiales
                        oDetalle.setIdentificador(((Double) vRowTemp.get(4)).intValue());
                        oDetalle.getMaterial().setClaveMaterial((String) vRowTemp.get(5));
                        oDetalle.getMaterial().setNombre((String) vRowTemp.get(6));
                        oDetalle.setCantSolicitada(((Double) vRowTemp.get(7)).shortValue());
                        oDetalle.getMaterial().getPresentacion().setValor((String) vRowTemp.get(8));
                        oDetalle.setVale(oVale);
                        oVale.arrDetValeCol.add(oDetalle);
                        System.out.println("Esto es lo que va guardando en el arreglo  " + oVale.arrDetValeCol.size());
                    }else{
                        if(oVale.getIdHoja() != 0){
                            vObj.add(oVale);
                        }
                        oVale = new ValeColectivo();
                        oVale.oAreaSolicita = new AreaServicioHRRB();
                        oVale.arrDetValeCol = new ArrayList<DetalleValeColectivo>();
                        oVale.oInventario = new InventarioMaterial();
                        oDetalle = new DetalleValeColectivo();                  
                        oDetalle.setVale(new ValeColectivo());
                        oDetalle.setServicioCobrable(new Material());
                        oDetalle.setSituacion(new Parametrizacion());
                        //Datos del Vale
                        oVale.setIdHoja(((Double) vRowTemp.get(0)).intValue());
                        oVale.setFechaEmision((Date) vRowTemp.get(1));
                        oVale.oAreaSolicita.setClave(((Double) vRowTemp.get(2)).intValue());
                        oVale.oAreaSolicita.setDescripcion((String) vRowTemp.get(3));
                        //Manda Detalles los Materiales
                        oDetalle.setIdentificador(((Double) vRowTemp.get(4)).intValue());
                        oDetalle.getMaterial().setClaveMaterial((String) vRowTemp.get(5));
                        oDetalle.getMaterial().setNombre((String) vRowTemp.get(6));
                        oDetalle.setCantSolicitada(((Double) vRowTemp.get(7)).shortValue());
                        oDetalle.getMaterial().getPresentacion().setValor((String) vRowTemp.get(8));
                        oDetalle.setVale(oVale);
                        oVale.arrDetValeCol.add(oDetalle);
                        oVale.oInventario.setExistenciaConvertida(((Double) vRowTemp.get(9)).intValue());
                        oVale.oPersonal.setNoTarjeta(((Double) vRowTemp.get(10)).intValue());
                        oVale.oPersonal.setNombres((String) vRowTemp.get(11));
                        oVale.oPersonal.setApPaterno((String) vRowTemp.get(12));
                        oVale.oPersonal.setApMaterno((String) vRowTemp.get(13));
                    }
                }//fin del ciclo
                vObj.add(oVale);
                nTam = vObj.size();
                arrRet = new ValeColectivo[nTam];
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);                        
                }
                System.out.println("Cantidad que trae el arreglo" + arrRet.length); 
            }        
        } 
        return arrRet; 
        }
        
        public int modificarConjunto(ArrayList<DetalleValeColectivo> arr) throws Exception{
            System.out.println("Estamos en ModificarConjunto");
            int nRet = -1;
            ArrayList<String> arrAfectaciones = new ArrayList<String>();
            
            if(arr == null || arr.isEmpty())
                throw new Exception("ValeColectivo.modicarConjunto: Faltan Datos");
            else{
                for(DetalleValeColectivo oDetalle: arr){
                    if(oDetalle.getCantSurArea()>0)
                        arrAfectaciones.add(oDetalle.getModificarTabla());
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

	public int getIdHoja() {
	return nIdHoja;
	}

	public void setIdHoja(int valor) {
	nIdHoja=valor;
	}

	public AreaServicioHRRB getAreaAtiende() {
	return oAreaAtiende;
	}

	public void setAreaAtiende(AreaServicioHRRB valor) {
	oAreaAtiende=valor;
	}

	public AreaServicioHRRB getAreaSolicita() {
	return oAreaSolicita;
	}

	public void setAreaSolicita(AreaServicioHRRB valor) {
	oAreaSolicita=valor;
	}

	public Parametrizacion getTipoVale() {
	return oTipoVale;
	}

	public void setTipoVale(Parametrizacion valor) {
	oTipoVale=valor;
	}
        
    public ArrayList<DetalleValeColectivo> getArrDetalle(){
        return this.arrDetValeCol;
    }
   
    public void setArrDetalle(ArrayList<DetalleValeColectivo> oVal){
        this.arrDetValeCol = oVal;
    }

    /**
     * @return the oPersonal
     */
    public PersonalHospitalario getPersonal() {
        return oPersonal;
    }

    /**
     * @param oPersonal the oPersonal to set
     */
    public void setPersonal(PersonalHospitalario oPersonal) {
        this.oPersonal = oPersonal;
    }

    /**
     * @return the oInventario
     */
    public InventarioMaterial getInventario() {
        return oInventario;
    }

    /**
     * @param oInventario the oInventario to set
     */
    public void setInventario(InventarioMaterial oInventario) {
        this.oInventario = oInventario;
    }

    /**
     * @return the dFechaSurtido
     */
    public Date getFechaSurtido() {
        return dFechaSurtido;
    }

    /**
     * @param dFechaSurtido the dFechaSurtido to set
     */
    public void setFechaSurtido(Date dFechaSurtido) {
        this.dFechaSurtido = dFechaSurtido;
    }
    
    

} 

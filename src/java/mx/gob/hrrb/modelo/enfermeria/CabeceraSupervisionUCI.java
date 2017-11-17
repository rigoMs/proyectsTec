package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class CabeceraSupervisionUCI implements Serializable{
	private AccesoDatos oAD;
        private long nIdCabeceraUCI;
	private String sUsuarioFirmado;
	private Date dFechaSupervision;		
        private DetalleSupervisionUCI oDetalleUCI;
        private SimpleDateFormat format;
        private AreaServicioHRRB oArea;
        private ArrayList<DetalleSupervisionUCI> arrDetalleSupervisionUCI;
        
	public CabeceraSupervisionUCI(){            
            dFechaSupervision = new Date();
            format = new SimpleDateFormat("yyyy-MM-dd");
            oArea = new AreaServicioHRRB();
            //sUsuarioFirmado="JAVIE28";
            HttpServletRequest req;
            req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                    sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
            }
	}
       
	public boolean buscarCabeceraSupervision() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
		if(this.getFechaString().equals("") 
                        || this.getArea().getClave()==0){
			throw new Exception("CabeceraSupervisionUCI.buscarCabeceraSupervision: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCabecerasupervisionUCI('"+this.getFechaString()+"'::date,"
                                +this.getArea().getClave()+"::smallint);"; 
                        //System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
			    rst = oAD.ejecutarConsulta(sQuery); 
			    oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);                                
                                this.setIdCabeceraUCI(((Double)vRowTemp.get(0)).longValue());
                                this.setFechaSupervision((Date)vRowTemp.get(1));
                                this.getArea().setClave(((Double)vRowTemp.get(2)).intValue());
                                this.getArea().setDescripcion((String)vRowTemp.get(3));
                                bRet = true;
                            }
			}			
		} 
		return bRet; 
	}
        
        public int insertarCabeceraSupervisionUCI() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		 if(this.getArea().getClave()==0
                         || this.getFechaString().equals("")){  
			throw new Exception("CabeceraSupervisionUCI.insertarCabeceraSupervisionUCI: error, faltan datos");
		}else{ 
			sQuery = this.getInsertarCabeceraSupervisionUCI();
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
                                        this.setIdCabeceraUCI(((Double)vRowTemp.get(1)).longValue());
				}
			}
		} 
		return nRet; 
	}  
        
	public String getInsertarCabeceraSupervisionUCI() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		 if(this.getArea().getClave()==0
                         || this.getFechaString().equals("")){  
			throw new Exception("CabeceraSupervisionUCI.insertarCabeceraSupervisionUCI: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCabecerasupervisionUCI('"+this.getFechaString()+"'::date,"
                                +this.getArea().getClave()+"::smallint,"+"'"+sUsuarioFirmado+"'::character varying);"; 
                        //System.out.println(sQuery);			
		} 
		return sQuery; 
	} 
        
	/*public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CabeceraSupervisionUCI.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCabeceraSupervisionUCI('"+sUsuarioFirmado+"');"; 
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
	} */
        
        public ArrayList<AreaServicioHRRB> buscaAreaServiciosTerapiaIntensiva(){
            ArrayList<AreaServicioHRRB> arrRet= new ArrayList<AreaServicioHRRB>();
            AreaServicioHRRB oArea;
            oArea= new AreaServicioHRRB();
            oArea.setClave(72);oArea.setDescripcion("UCI");
            arrRet.add(oArea);
            oArea = new AreaServicioHRRB();
            oArea.setClave(73); oArea.setDescripcion("UCIP");
            arrRet.add(oArea);
            oArea= new AreaServicioHRRB();
            oArea.setClave(79); oArea.setDescripcion("UCIN");
            arrRet.add(oArea); 
            return arrRet;
        }
        
	public Date getFechaSupervision() {
            return dFechaSupervision;
	}

	public void setFechaSupervision(Date valor) {
            this.dFechaSupervision=valor;
	}

	public long getIdCabeceraUCI() {
            return nIdCabeceraUCI;
	}

	public void setIdCabeceraUCI(long valor) {
	 this.nIdCabeceraUCI=valor;
	}

	public ArrayList<DetalleSupervisionUCI> getArrDetalleSupervisionUCI() {
            return arrDetalleSupervisionUCI;
	}

	public void setArrDetalleSupervisionUCI(ArrayList<DetalleSupervisionUCI> valor) {
            this.arrDetalleSupervisionUCI=valor;
	}

    public AreaServicioHRRB getArea() {
        return oArea;
    }

    public void setArea(AreaServicioHRRB oArea) {
        this.oArea = oArea;
    }

    public String getFechaString(){
        return this.format.format(dFechaSupervision);
    }
    
    public String getFechaString2(){
        SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
        return f.format(dFechaSupervision);
    }
} 

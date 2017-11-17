package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class CabeceraSolicitudFormula implements Serializable{
    
	private AccesoDatos oAD;
        private long nIdSolicitudFormula;
	private String sUsuarioFirmado;
	private Date dFechaSolicitud;	
	private AreaServicioHRRB oServicio;
	private Turno oTurno;	
        private PersonalHospitalario oPersonal;
        private SimpleDateFormat format;
        private DetalleSolicitudFormula oDetalleSolicitud;//instanciar donde se vaya a ocupar
        private ArrayList<DetalleSolicitudFormula> arrDetalleSolicitudFormula;
        
	public CabeceraSolicitudFormula(){
            oServicio= new AreaServicioHRRB();
            oTurno= new Turno();
            oPersonal = new PersonalHospitalario();
            dFechaSolicitud = new  Date();
            format = new SimpleDateFormat("yyyy-MM-dd");
            //sUsuarioFirmado="ELEAZ23";
	    HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
       
	public boolean buscarCabeceraSolicitudFormulas() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
                if(this.getServicio().getClave()==0||
                        this.getFechaSolicitud()==null){  
                       throw new Exception("CabeceraSolicitudFormula.buscarCabeceraSolicitudFormulas: error, faltan datos");
                }else{ 
                       sQuery = "SELECT * FROM buscaCabeceraSolicitudFormulas('"+this.getFechaString()+"'::date,"
                               +this.getServicio().getClave()+"::smallint);";                       
                       oAD=new AccesoDatos(); 
                       if (oAD.conectar()){ 
                               rst = oAD.ejecutarConsulta(sQuery); 
                               oAD.desconectar(); 
                       }
                       if (rst != null && rst.size() == 1) {
                               ArrayList vRowTemp = (ArrayList)rst.get(0);
                               this.setIdSolicitudFormula(((Double)vRowTemp.get(0)).longValue());
                               this.setFechaSolicitud((Date)vRowTemp.get(1));
                               this.getTurno().setDescripcion((String)vRowTemp.get(2));
                               this.getServicio().setDescripcion((String)vRowTemp.get(3));
                               this.getPersonal().setNombres((String)vRowTemp.get(4));
                               this.getPersonal().setApPaterno((String)vRowTemp.get(5));
                               this.getPersonal().setApMaterno((String)vRowTemp.get(6));
                               bRet = true;
                       }
                } 
                return bRet; 
	} 
        
	public CabeceraSolicitudFormula[] buscarTodos() throws Exception{
	CabeceraSolicitudFormula arrRet[]=null, oCabeceraSolicitudFormula=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCabeceraSolicitudFormula();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CabeceraSolicitudFormula[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
        public CabeceraSolicitudFormula[] buscaServiciosTodos()throws Exception{
            CabeceraSolicitudFormula arrRet[]=null, oCb=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            sQuery="SELECT * FROM buscaTodosServicios();";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()>0){
                    arrRet = new CabeceraSolicitudFormula[rst.size()];
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oCb = new CabeceraSolicitudFormula();
                        oCb.getServicio().setClave(((Double)vRowTemp.get(0)).intValue());
                        oCb.getServicio().setDescripcion((String)vRowTemp.get(1));
                        oCb.getServicio().setTipo((String)vRowTemp.get(2));
                        arrRet[i]=oCb;
                    }
                }
            }
            return arrRet;
        }
       
	public int insertarCabeceraSolicitudFormula() throws Exception{            
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		if(this.getServicio().getClave()==0 
                        || this.getTurno().getClave().equals("") 
                        || this.getFechaSolicitud()==null
                        || this.sUsuarioFirmado.equals("")){ 
			throw new Exception("CabeceraSolicitudFormula.insertarCabeceraSolicitudFormula: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCabeceraSolicitudFormulas('"+this.getFechaString()+"'::date,'"
                                +this.getTurno().getClave()+"'::character varying,"
                                +this.getServicio().getClave()+"::smallint,'"
                                +sUsuarioFirmado+"'::character varying);";                         
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);                                        
					nRet = ((Double)vRowTemp.get(0)).intValue();
                                        this.setIdSolicitudFormula(((Double)vRowTemp.get(1)).longValue());
				}
			}
		} 
		return nRet; 
	} 
        
	/*public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("CabeceraSolicitudFormula.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCabeceraSolicitudFormula('"+sUsuarioFirmado+"');"; 
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

        
	public Date getFechaSolicitud() {
            return dFechaSolicitud;
	}

	public void setFechaSolicitud(Date valor) {
            this.dFechaSolicitud=valor;
	}

	public long getIdSolicitudFormula() {
            return nIdSolicitudFormula;
	}

	public void setIdSolicitudFormula(long valor) {
            this.nIdSolicitudFormula=valor;
	}

	public AreaServicioHRRB getServicio() {
            return oServicio;
	}

	public void setServicio(AreaServicioHRRB valor) {
            this.oServicio=valor;
	}

	public Turno getTurno() {
            return oTurno;
	}

	public void setTurno(Turno valor) {
            this.oTurno=valor;
	}

	public ArrayList<DetalleSolicitudFormula> getArrDetalleSolicitudFormula() {
            return arrDetalleSolicitudFormula;
	}

	public void setArrDetalleSolicitudFormula(ArrayList<DetalleSolicitudFormula> valor) {
            this.arrDetalleSolicitudFormula=valor;
	}

   
    public DetalleSolicitudFormula getDetalleSolicitud() {
        return oDetalleSolicitud;
    }

    
    public void setDetalleSolicitud(DetalleSolicitudFormula oDetalleSolicitud) {
        this.oDetalleSolicitud = oDetalleSolicitud;
    }

    
    public PersonalHospitalario getPersonal() {
        return oPersonal;
    }
    
    public void setPersonal(PersonalHospitalario personal){
        this.oPersonal= personal;
    }
    
    public String getFechaString(){
        return this.format.format(dFechaSolicitud);
    }
    
    public String getFechaString2(){
        SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
        return f.format(dFechaSolicitud);
    }

} 

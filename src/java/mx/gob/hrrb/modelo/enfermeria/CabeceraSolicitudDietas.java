package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Turno;
import java.text.SimpleDateFormat;
        
/**
 * Objetivo: 
 * @author : Javier
 * @version: 1.0
*/

public  class CabeceraSolicitudDietas implements Serializable{
	private AccesoDatos oAD;
        private long nFolioCabecera;
	private String sUsuarioFirmado;
	private Date dFechaSolicitud;	
	private AreaServicioHRRB oServicio;	
	private PersonalHospitalario oPersonalHospitalario;        
        private Turno oTurno;
        private ArrayList<DetalleSolicitudDietas> arrDietas;
        private SimpleDateFormat format;
	public CabeceraSolicitudDietas(){
            
            oServicio = new AreaServicioHRRB();
            oPersonalHospitalario = new PersonalHospitalario();
            oTurno = new Turno();
            dFechaSolicitud= new Date();
            format = new SimpleDateFormat("yyyy-MM-dd");
            //arrDietas = new ArrayList<DetalleSolicitudDietas>();
            //sUsuarioFirmado="ELEAZ23";
            
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	} 
        
        public boolean buscarLlaveCabeceraSolictudDietas() throws Exception{
            boolean bRtn=false;
            ArrayList rst=null;
            String sQuery="";
            if(this.getServicio().getClave()==0 || this.getFechaSolicitud()==null){
                throw new Exception("CabeceraSolicitudDialisis.buscarLlaveCabeceraSolictudDietas: ERROR, Faltan Datos");
            }else{
                sQuery="SELECT * FROM buscaCabeceraSolicitudDietas('"+this.format.format(dFechaSolicitud)+"'::date,"
                        +this.getServicio().getClave()+"::smallint);";                
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    setFolioCabecera(((Double)vRowTemp.get(0)).longValue());
                    setFechaSolicitud((Date)vRowTemp.get(1));
                    getTurno().setDescripcion((String)vRowTemp.get(2));
                    getServicio().setDescripcion((String)vRowTemp.get(3));
                    getPersonalHospitalario().setNombres((String)vRowTemp.get(4));
                    getPersonalHospitalario().setApPaterno((String)vRowTemp.get(5));
                    getPersonalHospitalario().setApMaterno((String)vRowTemp.get(6));
                    bRtn= true;
                }
            }
            return bRtn;
        }
        
        public CabeceraSolicitudDietas[] buscaServiciosTodos()throws Exception{
            CabeceraSolicitudDietas arrRet[]=null, oCb=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            sQuery="SELECT * FROM buscaTodosServicios();";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()>0){
                    arrRet = new CabeceraSolicitudDietas[rst.size()];
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oCb = new CabeceraSolicitudDietas();
                        oCb.getServicio().setClave(((Double)vRowTemp.get(0)).intValue());
                        oCb.getServicio().setDescripcion((String)vRowTemp.get(1));
                        oCb.getServicio().setTipo((String)vRowTemp.get(2));
                        arrRet[i]=oCb;
                    }
                }
            }
            return arrRet;
        }
       
	public int insertarCabeceraSiolicitudDietas() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
                if(this.getServicio().getClave()==0 || sUsuarioFirmado.equals("")){ 
                        throw new Exception("CabeceraSolicitudDietas.insertar: error, faltan datos");
                }else{ 
                    sQuery = "SELECT * FROM insertaCabeceraSolicitudDietas('"+this.format.format(this.getFechaSolicitud())+"'::date,'"
                            +this.getDeterminaTurno()+"'::character varying"+","+this.getServicio().getClave()+"::smallint,"
                            +"'"+sUsuarioFirmado+"'::character varying);";                     
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() ==1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)vRowTemp.get(0)).intValue();
                                    this.setFolioCabecera(((Double)vRowTemp.get(1)).longValue());
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
			throw new Exception("CabeceraSolicitudDietas.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCabeceraSolicitudDietas('"+sUsuarioFirmado+"');"; 
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

	public long getFolioCabecera() {
            return nFolioCabecera;
	}

	public void setFolioCabecera(long valor) {
            this.nFolioCabecera=valor;
	}

	public AreaServicioHRRB getServicio() {
            return oServicio;
	}

	public void setServicio(AreaServicioHRRB valor) {
            this.oServicio=valor;
	}

	public ArrayList<DetalleSolicitudDietas> getDetalleSolicitudDietas() {
            return arrDietas;
	}

	public void setDetalleSolicitudDietas(ArrayList<DetalleSolicitudDietas> valor) {
            this.arrDietas=valor;
	}

	public PersonalHospitalario getPersonalHospitalario() {
            return oPersonalHospitalario;
	}

	public void setPersonalHospitalario(PersonalHospitalario valor) {
            this.oPersonalHospitalario=valor;
	}

    public Turno getTurno() {
        return oTurno;
    }

    public void setTurno(Turno oTurno) {
        this.oTurno = oTurno;
    }
    
    public String getFechaSolicitudString(){
        return this.format.format(this.getFechaSolicitud());
    }
    
    public String getFechaSolicitudString2(){
        SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
        return f.format(this.getFechaSolicitud());
    }

      
    public String getDeterminaTurno(){
         Date dFechaActual2= new Date();
         SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dFechaActual2);
         Double horaActual=Double.parseDouble(sT);
         sTurno= (horaActual>08.00 && horaActual<14.00)?"MAT": (horaActual > 14.00 && horaActual < 20.00)?"VES":"NOC";
        return sTurno;
    }
   

} 

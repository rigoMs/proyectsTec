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
import mx.gob.hrrb.modelo.enfermeria.reporte.MovimientoPacientes;
import mx.gob.hrrb.modelo.enfermeria.reporte.ConteoProcDetalles;


/**
 * Objetivo: Cabecera de supervision 
 * @author : Javier
 * @version: 1.0
*/

public  class CabeceraSuperServicios implements Serializable{
	private AccesoDatos oAD;		
	private long nIdCabecera;
	private String sObservacionTM;
	private String sObservacionTN;
	private String sObservacionTV;
        private String sUsuarioFirmado;
        private Date dFechaSupervicion;
	private ArrayList<DetalleSuperServicios> arrDetalle;
        private MovimientoPacientes oMovPac;
        private ConteoProcDetalles oConPro;
        private AreaServicioHRRB oArea;        
        private SimpleDateFormat format;
        
	public CabeceraSuperServicios(){
            oArea= new AreaServicioHRRB();
            //oMovPac= new MovimientoPacientes();
            //oConPro = new ConteoProcDetalles();
            dFechaSupervicion= new Date();
            format = new SimpleDateFormat("yyyy-MM-dd");
            //sUsuarioFirmado="JAVIE28";
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
        
	public boolean buscarCabeceraSupervisionServicios() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
                if( this.getFechaFormat().equals("")
                        || this.getAreaServicio().getClave()==0){   
                       throw new Exception("CabeceraSupervisionServicios.buscarCabeceraSupervisionServicios: Error, Faltan Datos");
                }else{ 
                       sQuery = "SELECT * FROM buscaLlaveCabecerasupervisionServicios('"
                               +this.getFechaFormat()+"'::date"+","
                               +this.getAreaServicio().getClave()+"::smallint);"; 
                       //System.out.println(sQuery);
                       oAD=new AccesoDatos(); 
                       if (oAD.conectar()){ 
                               rst = oAD.ejecutarConsulta(sQuery); 
                               oAD.desconectar(); 
                       }
                       if (rst != null && rst.size() == 1) {
                               ArrayList vRowTemp = (ArrayList)rst.get(0);
                               this.setIdCabecera(((Double)vRowTemp.get(0)).longValue()); 
                               this.setFechaSupervicion((Date)vRowTemp.get(1));
                               this.getAreaServicio().setClave(((Double)vRowTemp.get(2)).intValue());
                               this.setObservacionTM((String)vRowTemp.get(3));
                               this.setObservacionTV((String)vRowTemp.get(4));
                               this.setObservacionTN((String)vRowTemp.get(5));
                               bRet = true;
                       }
                } 
                return bRet; 
	}
        
	public int insertarCabeceraSupervisionServicios() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
                     if( this.getAreaServicio().getClave()==0 
                            || this.getInsertaCabeceraSupervisionServicios().equals("")){  
                        throw new Exception("CabeceraSupervisionServicios.insertarCabeceraSupervisionServicios: error, faltan datos");
                    }else{ 
                            sQuery = this.getInsertaCabeceraSupervisionServicios();                           
                            oAD=new AccesoDatos(); 
                            if (oAD.conectar()){ 
                                    rst = oAD.ejecutarConsulta(sQuery);
                                    oAD.desconectar(); 
                                    if (rst != null && rst.size() == 1) {
                                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                                            nRet = ((Double)vRowTemp.get(0)).intValue();                                           
                                    }
                            }
                    }
                return nRet; 
	} 
        
        public String getInsertaCabeceraSupervisionServicios()throws Exception{
            String sQuery="";
            if(this.getAreaServicio().getClave()==0
                    || this.getFechaFormat().equals("")){
                throw new Exception("CabeceraSuperServicios.getInsertaCabeceraSupervisionServicios: error, faltan datos");
            }else{
                sQuery="SELECT * FROM insertaCabeceraSupervisionServicios('"+this.getFechaFormat()+"'::date,"
                        +this.getAreaServicio().getClave()+"::smallint,"
                        +((this.getObservacionTM()==null || this.getObservacionTM().equals(""))?"null":"'"+this.getObservacionTM()+"'")+"::text,"
                        +((this.getObservacionTV()==null || this.getObservacionTV().equals(""))?"null":"'"+this.getObservacionTV()+"'")+"::text,"
                        +((this.getObservacionTN()==null || this.getObservacionTN().equals(""))?"null":"'"+this.getObservacionTN()+"'")+"::text,'"
                        +sUsuarioFirmado+"'::character varying);";
            }
            return sQuery;
        }
        
        public int modificarCabeceraSupervision() throws Exception {
            int nRtn=-1;
            ArrayList rst=null;
            String sQuery="";
            if(this.getIdCabecera()==0){
                throw new Exception("CbeceraSuperServicios.modificarCabeceraSupervision: ERROR, Faltan Datos");
            }else{
                sQuery="SELECT * FROM  modificaCabecerasupervisionServicios("+this.getIdCabecera()+"::bigint,"
                        +(this.getObservacionTM()==null || this.getObservacionTM().equals("")?"null":"'"+this.getObservacionTM()+"'")+"::text,"
                        +(this.getObservacionTV()==null || this.getObservacionTV().equals("")?"null":"'"+this.getObservacionTV()+"'")+"::text,"
                        +(this.getObservacionTN()==null || this.getObservacionTN().equals("")?"null":"'"+this.getObservacionTN()+"'")+"::text,'"
                        +sUsuarioFirmado+"'::character varying);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRtn = ((Double)vRowTemp.get(0)).intValue();
                }
            }            
            return nRtn;
        }
        
        public ArrayList<AreaServicioHRRB> buscarServiciosTodosHojaSup(){
            ArrayList<AreaServicioHRRB> arrRet= new ArrayList<AreaServicioHRRB>();
            AreaServicioHRRB oArea;
            oArea = new AreaServicioHRRB();
            oArea.setClave(74);oArea.setDescripcion("URGENCIAS ADULTOS");
            arrRet.add(oArea);
            oArea= new AreaServicioHRRB();
            oArea.setClave(75);oArea.setDescripcion("URGENCIAS GINECOLÓGICAS");
            arrRet.add(oArea);
            oArea= new AreaServicioHRRB();
            oArea.setClave(76);oArea.setDescripcion("URGENCIAS PEDIÁTRICAS");
            arrRet.add(oArea);
            oArea= new AreaServicioHRRB();
            oArea.setClave(21); oArea.setDescripcion("DISTINCION");
            arrRet.add(oArea);           
            oArea= new AreaServicioHRRB();
            oArea.setClave(56); oArea.setDescripcion("PEDIATRIA");
            arrRet.add(oArea);
            //
            oArea= new AreaServicioHRRB();
            oArea.setClave(13); oArea.setDescripcion("CIRUGÍA GENERAL");
            arrRet.add(oArea);
            oArea= new AreaServicioHRRB();
            oArea.setClave(29); oArea.setDescripcion("GINECO-OBSTETRICIA");
            arrRet.add(oArea);
            oArea= new AreaServicioHRRB();
            oArea.setClave(37); oArea.setDescripcion("MEDICINA INTERNA");
            arrRet.add(oArea);
            oArea= new AreaServicioHRRB();
            oArea.setClave(38); oArea.setDescripcion("MODULO MAPE");
            arrRet.add(oArea);
            return arrRet;
        }
	        
	public Date getFechaSupervicion() {
            return dFechaSupervicion;
	}

	public void setFechaSupervicion(Date valor) {
            this.dFechaSupervicion=valor;
	}

	public long getIdCabecera() {
            return nIdCabecera;
	}

	public void setIdCabecera(long valor) {
            this.nIdCabecera=valor;
	}

	public String getObservacionTM() {
            return sObservacionTM;
	}

	public void setObservacionTM(String valor) {
            this.sObservacionTM=valor;
	}

	public String getObservacionTN() {
            return sObservacionTN;
	}

	public void setObservacionTN(String valor) {
            this.sObservacionTN=valor;
	}

	public String getObservacionTV() {
            return sObservacionTV;
	}

	public void setObservacionTV(String valor) {
            this.sObservacionTV=valor;
	}

	public ArrayList<DetalleSuperServicios> getArrDetalle() {
            return arrDetalle;
	}

	public void setArrDetalle(ArrayList<DetalleSuperServicios> valor) {
            this.arrDetalle=valor;
	}
        
        public AreaServicioHRRB getAreaServicio(){
            return oArea;
        }
        
        public void setAreaServicio(AreaServicioHRRB area){
            this.oArea=area;
        }
        
        public String getFechaFormat(){
            return this.format.format(dFechaSupervicion);
        }


} 

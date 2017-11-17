package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class DetalleSolicitudDietas implements Serializable{
	private AccesoDatos oAD;
	private boolean bDisableRegistrar;
        private boolean bDisableModificar;
	private long nIdDetalleDietas;
        private String sUsuarioFirmado;
	private String sObservacion;
	private Dietas oDietas;	
	private CabeceraSolicitudDietas oCabeceraSolicitudDietas;
        private EpisodioMedico oEpisodio;
        private ArrayList<EpisodioMedico> arrEpisodioMedico;
        
	public DetalleSolicitudDietas(){
            oCabeceraSolicitudDietas = new CabeceraSolicitudDietas();
            oDietas = new Dietas();
            oEpisodio = new EpisodioMedico();
            //sUsuarioFirmado="JAVIE28";
            HttpServletRequest req;
                    req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    if (req.getSession().getAttribute("oFirm") != null) {
                            sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
                    }
	}
     
	public boolean buscaDietaPaciente() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this.getEpisodio().getPaciente().getFolioPaciente()==0
                         || this.getEpisodio().getPaciente().getClaveEpisodio()==0
                         || this.getIdDetalleDietas()==0
                         || this.getDietas().getFolioDieta()==0){ 
			throw new Exception("DetalleSolicitudDietas.buscaDietaPaciente: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM BuscaDietaPacienteDetalleSolicitudDietas("+this.getIdDetalleDietas()+"::bigint,"
                                +this.getDietas().getFolioDieta()+"::bigint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setIdDetalleDietas(((Double)vRowTemp.get(0)).longValue());
                                this.getCabeceraSolicitudDietas().setFolioCabecera(((Double)vRowTemp.get(1)).intValue());
                                this.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(2)).longValue());
                                this.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(3)).longValue());
                                this.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                                this.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                                this.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                                this.getDietas().setFolioDieta(((Double)vRowTemp.get(7)).intValue());                                
                                this.getDietas().setNombre((String)vRowTemp.get(8));
                                this.setObservacion((String)vRowTemp.get(9));
                                this.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(10)).intValue());
				bRet = true;
			}
		} 
		return bRet; 
	} 
      
        public DetalleSolicitudDietas[] buscaPacientesParaSolictudDietasYPacientesConDietas()throws Exception{
            DetalleSolicitudDietas arrPacStd[]=null, oDe=null;
            ArrayList rst=null;
            String sQuery="",edad="";
            int i=0;
            if(this.getCabeceraSolicitudDietas().getFechaSolicitud()==null
                    || this.getCabeceraSolicitudDietas().getServicio().getClave()==0){
                throw new Exception("DetalleSolicitudDietas. buscaPacientesParaSolictudDietasYPacientesConDietas: error, faltan datos");
            }else{
                sQuery="SELECT * FROM BuscaPacientesParaSolicitudDietasYPacientesConDietaPorServicio('"+this.getCabeceraSolicitudDietas().getFechaSolicitudString()+"'::date,"
                        +this.getCabeceraSolicitudDietas().getServicio().getClave()+"::smallint);";
                //System.out.println(sQuery);
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst!=null && rst.size()>0){
                        arrPacStd = new DetalleSolicitudDietas[rst.size()];
                        for(i=0;i<rst.size();i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDe= new DetalleSolicitudDietas();
                            oDe.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                            oDe.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                            oDe.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                            oDe.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(3));
                            oDe.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(4));
                            oDe.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(5));
                            oDe.getEpisodio().getPaciente().setSexoP((String)vRowTemp.get(6));
                            edad=(String)vRowTemp.get(7);
                            if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                               if (edad.charAt(0)=='0')
                                   oDe.getEpisodio().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                   oDe.getEpisodio().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                 if (edad.substring(4, 6).compareTo("00")!=0)
                                   oDe.getEpisodio().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                 else
                                   oDe.getEpisodio().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                            }
                            }
                            oDe.getEpisodio().setFechaIngreso((Date)vRowTemp.get(8));
                            oDe.getEpisodio().getCama().setNumero((String)vRowTemp.get(9));
                            oDe.getEpisodio().getDiagIngreso().setClave((String)vRowTemp.get(10));
                            oDe.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(11));
                            oDe.getDietas().setNombre((String)vRowTemp.get(12));                            
                            oDe.setObservacion((String)vRowTemp.get(13));
                            oDe.setIdDetalleDietas(((Double)vRowTemp.get(14)).longValue());
                            oDe.getDietas().setFolioDieta(((Double)vRowTemp.get(15)).intValue());
                            oDe.setDisableRegistrar((!oDe.getDietas().getNombre().equals("")));
                            oDe.setDisableModificar(oDe.getDietas().getNombre().equals(""));
                            arrPacStd[i]=oDe;
                        }
                    }
                }                
            }
            return arrPacStd;
        }
                
      
	public DetalleSolicitudDietas[] buscaSolicitudDeDietasPorFechaYServicio() throws Exception{
            DetalleSolicitudDietas arrRet[]=null, oDSD=null;
            ArrayList rst = null;
            String sQuery = "";
            String edad="";
            int i=0;
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
             if(this.getCabeceraSolicitudDietas().getFechaSolicitud()==null 
                     || this.getCabeceraSolicitudDietas().getServicio().getClave()==0){
                throw new Exception("DetalleSolicitudDietas.buscaSolicitudDeDietasPorFechaYServicio: error, falta datos");
             }else{
                 sQuery = "SELECT * FROM buscaSolicitudDeDietasPorFechaYServicio('"+df.format(this.getCabeceraSolicitudDietas().getFechaSolicitud())+"'::date,"
                         +this.getCabeceraSolicitudDietas().getServicio().getClave()+"::smallint);"; 
                    //System.out.println(sQuery);
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery); 
                            oAD.desconectar(); 
                    }
                    if (rst != null) {
                            arrRet = new DetalleSolicitudDietas[rst.size()];                            
                            for (i = 0; i < rst.size(); i++) {
                                oDSD= new DetalleSolicitudDietas();
                                ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oDSD.getCabeceraSolicitudDietas().setFechaSolicitud((Date)vRowTemp.get(0));
                                oDSD.getCabeceraSolicitudDietas().getTurno().setDescripcion((String)vRowTemp.get(1));
                                oDSD.getCabeceraSolicitudDietas().getServicio().setDescripcion((String)vRowTemp.get(2));
                                oDSD.getCabeceraSolicitudDietas().getPersonalHospitalario().setNombres((String)vRowTemp.get(3));
                                oDSD.getCabeceraSolicitudDietas().getPersonalHospitalario().setApPaterno((String)vRowTemp.get(4));
                                oDSD.getCabeceraSolicitudDietas().getPersonalHospitalario().setApMaterno((String)vRowTemp.get(5));
                                oDSD.setIdDetalleDietas(((Double)vRowTemp.get(6)).longValue());
                                oDSD.getCabeceraSolicitudDietas().setFolioCabecera(((Double)vRowTemp.get(7)).longValue());
                                oDSD.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
                                oDSD.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(9));
                                oDSD.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(10));
                                oDSD.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(11));
                                edad=(String)vRowTemp.get(12);
                                if (edad.compareTo("")!=0){
                                if(edad.substring(0, 3).compareTo("000")!=0){
                                   if (edad.charAt(0)=='0')
                                       oDSD.getEpisodio().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                    else
                                       oDSD.getEpisodio().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                                }else{
                                     if (edad.substring(4, 6).compareTo("00")!=0)
                                       oDSD.getEpisodio().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                     else
                                       oDSD.getEpisodio().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                 }
                                }
                                oDSD.getEpisodio().getPaciente().setSexoP((String)vRowTemp.get(13));
                                oDSD.getDietas().setNombre((String)vRowTemp.get(14));
                                oDSD.setObservacion((String)vRowTemp.get(15));
                                oDSD.getEpisodio().getCama().setNumero((String)vRowTemp.get(16));
                                arrRet[i]=oDSD;
                            } 
                    } 
             }
             return arrRet; 
	} 
     
	public int insertarDetalleSolicitudDietas() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
		 if(this.getCabeceraSolicitudDietas().getFolioCabecera()==0 
                        || this.getDietas().getFolioDieta()==0 
                        || this.getEpisodio().getPaciente().getFolioPaciente()==0 
                        || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
		    throw new Exception("DetalleSolicitudDietas.insertarDetalleSolicitudDietas: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM  insertaDetalleSolicitudDietas("+this.getCabeceraSolicitudDietas().getFolioCabecera()+"::bigint,"
                                +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                                +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,"
                                +this.getDietas().getFolioDieta()+"::smallint,"
                                +(this.getObservacion().equals("")?"null":"'"+this.getObservacion()+"'")+"::character varying"
                                +",'"+sUsuarioFirmado+"'::character varying);";                       
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp .get(0)).intValue();
				}
			}
		} 
		return nRet; 
	} 
     
	public int modificarDietaPaciente() throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
                if(this.getIdDetalleDietas()==0
                        || this.getCabeceraSolicitudDietas().getFolioCabecera()==0
                        || this.getEpisodio().getPaciente().getFolioPaciente()==0
                        || this.getEpisodio().getPaciente().getClaveEpisodio()==0
                        || this.getDietas().getFolioDieta()==0){   //completar llave
                       throw new Exception("DetalleSolicitudDietas.modificar: error, faltan datos");
                }else{ 
                       sQuery = "SELECT * FROM modificaDetalleSolicitudDietasPaciente("+this.getIdDetalleDietas()+"::bigint,"
                               +this.getCabeceraSolicitudDietas().getFolioCabecera()+"::bigint,"
                               +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                               +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,"
                               +this.getDietas().getFolioDieta()+"::smallint,"
                               +(this.getObservacion().equals("")?"null":"'"+this.getObservacion()+"'")+"::character varying,"
                               +"'"+sUsuarioFirmado+"'::character varying);"; 
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
        
	public long getIdDetalleDietas() {
	    return nIdDetalleDietas;
	}

	public void setIdDetalleDietas(long valor) {
	   this.nIdDetalleDietas=valor;
	}

	public String getObservacion() {
	   return sObservacion;
	}

	public void setObservacion(String valor) {
	   this.sObservacion=valor;
	}

	public Dietas getDietas() {
	   return oDietas;
	}

	public void setDietas(Dietas valor) {
	   this.oDietas=valor;
	}

	public ArrayList<EpisodioMedico> getArrEpisodioMedico() {
	   return arrEpisodioMedico;
	}

	public void setArrEpisodioMedico(ArrayList<EpisodioMedico> valor) {
	   this.arrEpisodioMedico=valor;
	}

	public CabeceraSolicitudDietas getCabeceraSolicitudDietas() {
	    return oCabeceraSolicitudDietas;
	}

	public void setCabeceraSolicitudDietas(CabeceraSolicitudDietas valor) {
	   this.oCabeceraSolicitudDietas=valor;
	}
        
        public EpisodioMedico getEpisodio(){
            return oEpisodio;
        }
        
        public void setEpisodio(EpisodioMedico valor){
            this.oEpisodio= valor;
        }

    public boolean getDisableRegistrar() {
        return bDisableRegistrar;
    }

    public void setDisableRegistrar(boolean bDisable) {
        this.bDisableRegistrar = bDisable;
    }

    public boolean getDisableModificar() {
        return bDisableModificar;
    }

    public void setDisableModificar(boolean bDisableModificar) {
        this.bDisableModificar = bDisableModificar;
    }

} 

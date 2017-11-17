package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Cama;
import mx.gob.hrrb.modelo.core.Turno;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class CabeceraDialisis implements Serializable{
    
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaAplicDialisis;
	private int nCabeceraDialisis;
	private int nNoHoja;
	private AreaServicioHRRB oArea;
        
	private String sObservacionT1;
	private String sObservacionT3;
	private String sObservcionT2;
        
	private ArrayList<IndicacionMedicaDialisis> arrIndicacionMedicaDialisis;        
	private ArrayList<DetalleDialisis> arrDetalleDialisis;
        
	private EpisodioMedico oEpisodioMedico;
        private SimpleDateFormat format;
        private Turno oTurno;
        
	public CabeceraDialisis(){
            oEpisodioMedico = new EpisodioMedico();
            format = new SimpleDateFormat("yyyy-MM-dd");
            dFechaAplicDialisis= new Date();  
            oArea = new AreaServicioHRRB();
            oTurno= new Turno();
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
			throw new Exception("CabeceraDialisis.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCabeceraDialisis();"; 
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
        
	public CabeceraDialisis[] buscarTodos() throws Exception{
	CabeceraDialisis arrRet[]=null, oCabeceraDialisis=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosCabeceraDialisis();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new CabeceraDialisis[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
	public int insertarCabeceraDialisis() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if(this.getEpisodioMedico().getPaciente().getFolioPaciente()==0
                         && this.getEpisodioMedico().getPaciente().getClaveEpisodio()==0 
                         && this.getFechaAplicDialisis()==null && this.getArea().getClave()==0){  
			throw new Exception("CabeceraDialisis.insertarCabeceraDialisis: error, faltan datos");
		}else{ 
			//sQuery = "SELECT * FROM insertaCabeceraDialisis('"+sUsuarioFirmado+"');"; 
                     sQuery="SELECT * FROM insertaCabeceraDialisis("+this.getEpisodioMedico().getPaciente().getFolioPaciente()+"," + this.getEpisodioMedico().getPaciente().getClaveEpisodio()+",'"
                             +this.getEpisodioMedico().getCama().getNumero()+"',"+ this.format.format(dFechaAplicDialisis)+","+this.getNoHoja()+","+this.getArea().getClave()+");";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() <0) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)rst.get(0)).intValue();
                                        this.setIdCabeceraDialisis(((Double)vRowTemp.get(1)).intValue());
				}
			}
		} 
		return nRet; 
	} 
        
        // metodo que inserta una observacion por turno 
        public int insertarObservacion() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this.getIdCabeceraDialisis()==0 && this.getTurno().getClave().equals("")
                         && this.getObservacionT1().equals("")){  
			throw new Exception("CabeceraDialisis.insertarObservacion: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaCabeceraDialisis('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CabeceraDialisis.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCabeceraDialisis('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("CabeceraDialisis.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCabeceraDialisis('"+sUsuarioFirmado+"');"; 
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
        
        // metodo que busca la llave de una cabecera de dialisis
        public boolean buscarIdentificadorCabeceraDialisis() throws Exception{
            boolean bRtn=false;
            int nIdn=-1;
            String sQuery="";
            ArrayList rst=null;
            if(this.getFechaAplicDialisis()==null && this.getEpisodioMedico().getPaciente().getFolioPaciente()==0
                    && this.getEpisodioMedico().getPaciente().getClaveEpisodio()==0){
                 throw new Exception("CabeceraDialisis.buscarIdentificadorCabeceraDialisis: Error, Faltan Datos");               
            }else{
                sQuery="SELECT * FROM buscaLlaveCabeceraDialisis("+this.getEpisodioMedico().getPaciente().getFolioPaciente()+","
                        + this.getEpisodioMedico().getPaciente().getClaveEpisodio()+","+this.getFechaAplicDialisis()+"::date);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nIdn= ((Double)vRowTemp.get(0)).intValue();
                    this.setIdCabeceraDialisis(nIdn);//((Double)vRowTemp.get(0)).intValue()
                    bRtn=true;
                }
            }
            return bRtn;
        }
        
	public Date getFechaAplicDialisis() {
            return dFechaAplicDialisis;
	}

	/*public void setFechaAplicDialisis(Date valor) {
            this.dFechaAplicDialisis=valor;
	}*/

	public int getIdCabeceraDialisis() {
            return nCabeceraDialisis;
	}

	public void setIdCabeceraDialisis(int valor) {
            this.nCabeceraDialisis=valor;
	}

	public int getNoHoja() {
            return nNoHoja;
	}

	public void setNoHoja(int valor) {
            this.nNoHoja=valor;
	}

	public AreaServicioHRRB getArea() {
            return oArea;
	}

	public void setArea(AreaServicioHRRB valor) {
            this.oArea=valor;
	}

	public String getObservacionT1() {
            return sObservacionT1;
	}

	public void setObservacionT1(String valor) {
            this.sObservacionT1=valor;
	}

	public String getObservacionT3() {
            return sObservacionT3;
	}

	public void setObservacionT3(String valor) {
            this.sObservacionT3=valor;
	}

	public String getObservcionT2() {
            return sObservcionT2;
	}

	public void setObservcionT2(String valor) {
            this.sObservcionT2=valor;
	}

	public ArrayList<IndicacionMedicaDialisis> getArrIndicacionMedicaDialisis() {
            return arrIndicacionMedicaDialisis;
	}

	public void setArrIndicacionMedicaDialisis(ArrayList<IndicacionMedicaDialisis> valor) {
            this.arrIndicacionMedicaDialisis=valor;
	}

	public ArrayList<DetalleDialisis> getArrDetalleDialisis() {
            return arrDetalleDialisis;
	}

	public void setArrDetalleDialisis(ArrayList<DetalleDialisis> valor) {
            this.arrDetalleDialisis=valor;
	}

	public EpisodioMedico getEpisodioMedico() {
            return oEpisodioMedico;
	}

	public void setEpisodioMedico(EpisodioMedico valor) {
            this.oEpisodioMedico=valor;
	} 
        
    public Turno getTurno() {
        return oTurno;
    }

    public void setTurno(Turno oTurno) {
        this.oTurno = oTurno;
    }

    

} 

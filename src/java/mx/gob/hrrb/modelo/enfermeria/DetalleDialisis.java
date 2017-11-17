package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.core.Medicamento;
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

public  class DetalleDialisis implements Serializable{
    
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dHoraFinEgreso;
	private Date dHoraFinIngreso;
	private Date dHoraIniciaEgreso;
	private Date dHoraIniciaIngreso;
	private Date dTiempoCavidad;
       
	private int nBalanceParcial;
	private int nBalanceTotal;
	private long nFolioDialisis;
	private int nNoRecam;
	private int nVolumenEgresa;
	private int nVolumenIngresa;
	private Turno oTurno;
	private Medicamento sMedicamentos;
	private CabeceraDialisis oCabeceraDialisis;
        private SimpleDateFormat format;
        private Date dFechaActual;
        
	public DetalleDialisis(){
            oCabeceraDialisis = new CabeceraDialisis();
            sMedicamentos = new Medicamento();
            format = new SimpleDateFormat("HH:mm"); 
            dFechaActual = new Date();
            
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
			throw new Exception("DetalleDialisis.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDetalleDialisis();"; 
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
        
	public DetalleDialisis[] buscarDialisisAgregadosPaciente() throws Exception{
            SimpleDateFormat formatFecha= new SimpleDateFormat();
            DetalleDialisis arrRet[]=null, oDeDialisis=null;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
            if(this.getCabeceraDialisis().getEpisodioMedico().getPaciente().getFolioPaciente()==0 &&
                    this.getCabeceraDialisis().getEpisodioMedico().getPaciente().getClaveEpisodio()==0){
                throw new Exception("DetalleDialisis.buscarDialisisAgregadosPaciente:ERROR, Falta Datos");
                
            }else{
                sQuery = "SELECT * FROM buscaTodosDetalleDialisis("+this.getCabeceraDialisis().getEpisodioMedico().getPaciente().getFolioPaciente()+"::bigint,"
                        +this.getCabeceraDialisis().getEpisodioMedico().getPaciente().getClaveEpisodio()+"::bigint,"+formatFecha.format(dFechaActual)+");"; 
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                }
            }
            if (rst != null) {
                    arrRet = new DetalleDialisis[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oDeDialisis = new DetalleDialisis();
                        oDeDialisis.getCabeceraDialisis().setIdCabeceraDialisis(((Double)vRowTemp.get(0)).intValue());
                        arrRet[i]= oDeDialisis;
                    } 
            } 
            return arrRet; 
	}
        
	public int insertarDetalleDialisis() throws Exception{
            
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if(this.getCabeceraDialisis().getIdCabeceraDialisis()==0){   
			throw new Exception("DetalleDialisis.insertar: error, faltan datos");
		}else{ 
			//sQuery = "SELECT * FROM insertaDetalleDialisis('"+sUsuarioFirmado+"');"; 
                     sQuery ="FROM insertaDetalleDialisis("+this.getCabeceraDialisis().getIdCabeceraDialisis()+","
                             +this.getNoRecam()+","+this.getVolumenIngresa()+","+(this.getSHoraInicIngreso().equals("")?"null":this.getSHoraInicIngreso())+","
                             +(this.getSHoraFinIngreso().equals("")?"null":this.getSHoraFinIngreso())+","+ (this.getSTiempoCavidad().equals("")?"null":this.getSTiempoCavidad())+","
                             +(this.getSHoraInicEgreso().equals("")?"null":this.getSHoraInicEgreso())+","+(this.getSHoraFinEgreso().equals("")?"null":this.getSHoraFinEgreso())+","
                             +this.getVolumenEgresa()+","+(this.getMedicamentos().getClaveMedicamento().equals("")?"null":"'"+this.getMedicamentos().getClaveMedicamento()+"'")+","
                             +(this.getMedicamentos().getPresentacion().equals("")?"null":"'"+this.getMedicamentos().getPresentacion()+"'")+","+this.getMedicamentos().getCodBarras()+",'"
                             +this.getTurno().getClave()+"','"+sUsuarioFirmado+"');";
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
        
	public int modificarDetalleDialisis() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if(this.getNoRecam()==0 && this.getCabeceraDialisis().getIdCabeceraDialisis()==0){
			throw new Exception("DetalleDialisis.modificarDetalleDialisis: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaDetalleDialisis("+this.getNoRecam()+","+this.getCabeceraDialisis().getIdCabeceraDialisis()+","
                                +this.getVolumenIngresa()+","+(this.getSHoraInicIngreso().equals("")?"null":this.getSHoraInicIngreso())+","
                                +(this.getSHoraFinIngreso().equals("")?"null":this.getSHoraFinIngreso())+","+(this.getSTiempoCavidad().equals("")?"null":this.getSTiempoCavidad())+","
                                +(this.getSHoraInicEgreso().equals("")?"null":this.getSHoraInicEgreso())+","+(this.getSHoraFinEgreso().equals("")?"null":this.getSHoraFinEgreso())+","
                                +this.getVolumenEgresa()+","+(this.getMedicamentos().getClaveMedicamento().equals("")?"null":"'"+this.getMedicamentos().getClaveMedicamento()+"'")+","
                                +(this.getMedicamentos().getPresentacion().equals("")?"null":"'"+this.getMedicamentos().getPresentacion()+"'")+","+this.getMedicamentos().getCodBarras()+",'"
                                +sUsuarioFirmado+"');"; 
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
			throw new Exception("DetalleDialisis.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDetalleDialisis('"+sUsuarioFirmado+"');"; 
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
        
	public Date getHoraFinEgreso() {
            return dHoraFinEgreso;
	}

	public void setHoraFinEgreso(Date valor) {            
            this.dHoraFinEgreso=valor;
	}

	public Date getHoraFinIngreso() {
	    return dHoraFinIngreso;
	}

	public void setHoraFinIngreso(Date valor) {            
            this.dHoraFinIngreso=valor;
	}

	public Date getHoraIniciaEgreso() {
            return dHoraIniciaEgreso;
	}

	public void setHoraIniciaEgreso(Date valor) {    
            this.dHoraIniciaEgreso=valor;
	}

	public Date getHoraIniciaIngreso() {
            return dHoraIniciaIngreso;
	}

	public void setHoraIniciaIngreso(Date valor) {            
            this.dHoraIniciaIngreso=valor;
	}

	public Date getTiempoCavidad() {
            return dTiempoCavidad;
	}

	public void setTiempoCavidad(Date valor) {           
            this.dTiempoCavidad=valor;
	}

	public int getBalanceParcial() {
            return nBalanceParcial;
	}

	public void setBalanceParcial(int valor) {
            this.nBalanceParcial=valor;
	}

	public int getBalanceTotal() {
            return nBalanceTotal;
	}

	public void setBalanceTotal(int valor) {
            this.nBalanceTotal=valor;
	}

	public long getFolioDialisis() {
            return nFolioDialisis;
	}

	public void setFolioDialisis(long valor) {
            this.nFolioDialisis=valor;
	}

	public int getNoRecam() {
            return nNoRecam;
	}

	public void setNoRecam(int valor) {
            this.nNoRecam=valor;
	}

	public int getVolumenEgresa() {
            return nVolumenEgresa;
	}

	public void setVolumenEgresa(int valor) {
            this.nVolumenEgresa=valor;
	}

	public int getVolumenIngresa() {
            return nVolumenIngresa;
	}

	public void setVolumenIngresa(int valor) {
            this.nVolumenIngresa=valor;
	}

	public Turno getTurno() {
            return oTurno;
	}

	public void setTurno(Turno valor) {
            this.oTurno=valor;
	}

	public Medicamento getMedicamentos() {
            return sMedicamentos;
	}

	public void setMedicamentos(Medicamento valor) {
	 this.sMedicamentos=valor;
	}

	public CabeceraDialisis getCabeceraDialisis() {
            return oCabeceraDialisis;
	}

	public void setCabeceraDialisis(CabeceraDialisis valor) {
            this.oCabeceraDialisis=valor;
	}

    //solo para mostrar en vista ya son formato
    public String getSHoraInicIngreso() {
        if(this.getHoraIniciaIngreso()!=null)
            return this.format.format(this.getHoraIniciaIngreso());
        else
            return "";        
    }    

    public String getSHoraFinIngreso() {
        if(this.getHoraFinIngreso()!=null)
            return this.format.format(this.getHoraFinIngreso());
        else
          return "";        
    }   

    
    public String getSHoraInicEgreso() {
        if(this.getHoraIniciaEgreso()!=null)
            return this.format.format(this.getHoraIniciaEgreso());
        else
            return "";
               
    }

    public String getSHoraFinEgreso() {
        if(this.getHoraFinEgreso()!=null)
          return this.format.format(this.getHoraFinEgreso());  
        else
            return "";  
    }
        
    public String getSTiempoCavidad() {        
        if(this.getTiempoCavidad()!=null)
            return this.format.format(this.getTiempoCavidad());        
        else
             return "";               
    }

    public Date getFechaActual() {
        return dFechaActual;
    }

   

} 

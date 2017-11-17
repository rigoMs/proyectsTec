package mx.gob.hrrb.modelo.hospitalizacion;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class HospitalPsiquiatrico implements Serializable{
	private AccesoDatos oAD;
	private Parametrizacion oServiciosHC;
	private Parametrizacion oServiciosHP;
	private Parametrizacion oTipoUnidad;
    private String sTipoUnidadP;
    private String sServiciosHCP;
    private String sServiciosHPP;
    private Hospitalizacion oHospitalizacion;
    private Paciente oPaciente;
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private FacesMessage facesMessage;

    public HospitalPsiquiatrico(){
            oHospitalizacion = new Hospitalizacion();
            oPaciente = new Paciente();
            oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }            
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HospitalPsiquiatrico.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveHospitalPsiquiatrico();"; 
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

	public boolean buscarCode() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HospitalPsiquiatrico.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosHospitalPsiquiatrico("+oHospitalizacion.getNumIngresoHos()+"::bigint,"+oPaciente.getFolioPaciente()+"::bigint);"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setTipoUnidadP((String) vRowTemp.get( 0 ));
                                if(getTipoUnidadP().equals("T1101"))
                                    this.setServiciosHCP((String) vRowTemp.get( 1 ));
                                else if(getTipoUnidadP().equals("T1102"))
                                    this.setServiciosHPP((String) vRowTemp.get( 2 ));
				bRet = true;
			}
		} 
		return bRet; 
	}        

	public HospitalPsiquiatrico[] buscarTodos() throws Exception{
	HospitalPsiquiatrico arrRet[]=null, oHospitalPsiquiatrico=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosHospitalPsiquiatrico();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new HospitalPsiquiatrico[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HospitalPsiquiatrico.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHospitalPsiquiatrico();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HospitalPsiquiatrico.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHospitalPsiquiatrico();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HospitalPsiquiatrico.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHospitalPsiquiatrico();"; 
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

	//*************************************************************************************************************************
	public int modificarInsertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String sLlave=""+oHospitalizacion.getNumIngresoHos();
        if(getTipoUnidadP().equals("T1103")){
            setServiciosHCP("");
            setServiciosHPP("");
        }
        else if(getTipoUnidadP().equals("T1101"))
            setServiciosHPP("");
        else 
            setServiciosHCP("");
		 if( this==null){   //completar llave
			throw new Exception("HospitalPsiquiatrico.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaInsertaHospitalPsiquiatricoCODE('"+sUsuario+"', "+oHospitalizacion.getNumIngresoHos()+
                                ", '"+getTipoUnidadP()+"','"+getServiciosHCP()+"','"+getServiciosHPP()+"','"+sLlave+"');";                                                                        
                        System.out.println(sQuery);
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
//********************************************************************

	public Parametrizacion getServiciosHC() {
	return oServiciosHC;
	}

	public void setServiciosHC(Parametrizacion valor) {
	oServiciosHC=valor;
	}

	public Parametrizacion getServiciosHP() {
	return oServiciosHP;
	}

	public void setServiciosHP(Parametrizacion valor) {
	oServiciosHP=valor;
	}

	public Parametrizacion getTipoUnidad() {
	return oTipoUnidad;
	}

	public void setTipoUnidad(Parametrizacion valor) {
	oTipoUnidad=valor;
	}

    public String getTipoUnidadP() {
        return sTipoUnidadP;
    }

    public void setTipoUnidadP(String sTipoUnidadP) {
        this.sTipoUnidadP = sTipoUnidadP;
    }

    public Hospitalizacion getHospitalizacion() {
        return oHospitalizacion;
    }

    public void setHospitalizacion(Hospitalizacion oHospitalizacion) {
        this.oHospitalizacion = oHospitalizacion;
    }

    public String getServiciosHCP() {
        return sServiciosHCP;
    }

    public void setServiciosHCP(String sServiciosHCP) {
        this.sServiciosHCP = sServiciosHCP;
    }

    public String getServiciosHPP() {
        return sServiciosHPP;
    }

    public void setServiciosHPP(String sServiciosHPP) {
        this.sServiciosHPP = sServiciosHPP;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

} 

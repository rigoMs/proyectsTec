package mx.gob.hrrb.modelo.urgencias;

import mx.gob.hrrb.modelo.core.Lesion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Atencion;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class HojaLesiones implements Serializable{
	private AccesoDatos oAD;
	private String bEsEmbarazada;
        
//***********************************************
        private String bMujerEdadFertil;
//**********************************************
	private char bSabeLeerEscribir;
//**********************************************
	private Parametrizacion oEscolaridad;
	private Lesion oLesion;
	private Atencion oAtencion;
	private Agresor oAgresor;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private FacesMessage facesMessage;
        
        //********************************************
        private char oTipoEsco;
        //*****************************************
        
        public HojaLesiones(){
            oLesion=new Lesion();
            oAgresor=new Agresor();
            oAtencion=new Atencion();
            oEscolaridad=new Parametrizacion();
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
			throw new Exception("HojaLesiones.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveHojaLesiones();"; 
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
	public HojaLesiones[] buscarTodos() throws Exception{
	HojaLesiones arrRet[]=null, oHojaLesiones=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosHojaLesiones();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new HojaLesiones[rst.size()];
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
			throw new Exception("HojaLesiones.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHojaLesiones();"; 
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
			throw new Exception("HojaLesiones.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHojaLesiones();"; 
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
			throw new Exception("HojaLesiones.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHojaLesiones();"; 
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
        
        public int insertarHojaLesionNuevo(Paciente oPaciente, AdmisionUrgs oAdmision) throws Exception{
            int nRet = 0;
            ArrayList rst = null;
            String sQuery="";
            
            String embarazada="", tipoViolencia="", evenAutoInf="", siAuto="", usoEquipoSeg="", nomEquipoSeg="", tipoAgresor="", edadAgresor="", sexoAgresor="", parenAgresor="", agresorEfectos="", horaAtnUrgs="", afe1="", afe2="", afe3="", afe4="", afe5="", discap="", fechaAtencion="", fechaOcurrencia="", atnPre="";
            
            if(getEsEmbarazada()!=null){
            if(getEsEmbarazada().compareTo("")==0)
                embarazada="null";
            else
                embarazada="'"+getEsEmbarazada()+"'";
            }
            else 
                embarazada="null";
            
            if(oLesion.getTipoViolencia().getClaveParametro().compareTo("")==0)
                tipoViolencia="null";
            else
                tipoViolencia="'"+oLesion.getTipoViolencia().getClaveParametro()+"'";
                
            if(oLesion.getCasoEventoAuto()!=null){
            if(oLesion.getCasoEventoAuto().compareTo("")==0)
                evenAutoInf="null";
            else
                evenAutoInf="'"+oLesion.getCasoEventoAuto()+"'";
            }
            else 
                evenAutoInf="null";
            
            
            if(oLesion.getSiVehiculo()!=null){
            if(oLesion.getSiVehiculo().getClaveParametro().compareTo("")==0)
                siAuto="null";
            else 
                siAuto="'"+oLesion.getSiVehiculo().getClaveParametro()+"'";
            }
            else siAuto="null";
            
            
            if(oLesion.getEquipoSeguridad()!=null){
            if(oLesion.getEquipoSeguridad().getClaveParametro().compareTo("")==0)
                usoEquipoSeg="null";
            else
                usoEquipoSeg="'"+oLesion.getEquipoSeguridad().getClaveParametro()+"'";
            }
            else usoEquipoSeg="null";
            
            if(oLesion.getNomEqSeguridad()!=null){
            if(oLesion.getNomEqSeguridad().getClaveParametro().compareTo("")==0)
                nomEquipoSeg="null";
            else
                nomEquipoSeg="'"+oLesion.getNomEqSeguridad().getClaveParametro()+"'";
            }else
                nomEquipoSeg="null";
            
            if(oAgresor.getTipoAgresor()=='1')
                tipoAgresor="'1'";
            else if(oAgresor.getTipoAgresor()=='2')
                tipoAgresor="'2'";
            else 
                tipoAgresor="null";
            
            if(oAgresor.getEdadAgresor()!=0)
                edadAgresor=oAgresor.getEdadAgresor()+"::smallint";
            else
                edadAgresor="null";
            
            if(oAgresor.getSexoAgresor()=='F')
                sexoAgresor="'F'";
            else if(oAgresor.getSexoAgresor()=='M')
                sexoAgresor="'M'";
            else 
                sexoAgresor="null";
            
            if(oAgresor.getParentescoConAfectado()!=null){
            if(oAgresor.getParentescoConAfectado().getClaveParametro().compareTo("")==0)
                parenAgresor="null";
            else 
                parenAgresor="'"+oAgresor.getParentescoConAfectado().getClaveParametro()+"'";
            }
            else
                parenAgresor="null";
            
            
            if(oAgresor.getAgresorBajoEfecto()!=null){
            if(oAgresor.getAgresorBajoEfecto().getClaveParametro().compareTo("")==0)
                agresorEfectos="null";
            else
                agresorEfectos="'"+oAgresor.getAgresorBajoEfecto().getClaveParametro()+"'";
            }
            else
                agresorEfectos="null";
            
            if(oAtencion.getHoraLesionUrgs()!=null){
            if(oAtencion.getHoraLesionUrgs().compareTo("")==0)
                horaAtnUrgs="null";
            else
                horaAtnUrgs="'"+oAdmision.fechaActual()+" "+oAtencion.getHoraLesionUrgs()+"'";
            }
            else
                horaAtnUrgs="null";
            
            
            if(oAdmision.getAfePrincipal().getCIE10().getClaveCIE10().compareTo("")==0)
                afe1="null";
            else
                afe1="'"+oAdmision.getAfePrincipal().getCIE10().getClaveCIE10()+"'";
            
            if(oAdmision.getAfeSegunda().getCIE10().getClaveCIE10().compareTo("")==0)
                afe2="null";
            else
                afe2="'"+oAdmision.getAfeSegunda().getCIE10().getClaveCIE10()+"'";
            
            if(oAdmision.getAfeTercera().getCIE10().getClaveCIE10().compareTo("")==0)
                afe3="null";
            else
                afe3="'"+oAdmision.getAfeTercera().getCIE10().getClaveCIE10()+"'";
            
            if(oAdmision.getAfeResAP().getCIE10().getClaveCIE10().compareTo("")==0)
                afe4="null";
            else
                afe4="'"+oAdmision.getAfeResAP().getCIE10().getClaveCIE10()+"'";
            
            if(oAdmision.getAfeCuarta().getCIE10().getClaveCIE10().compareTo("")==0)
                afe5="null";
            else
                afe5="'"+oAdmision.getAfeCuarta().getCIE10().getClaveCIE10()+"'";
            
            if(oPaciente.getDiscapacitado()==true)
                discap="S";
            else
                discap="N";
            if(oLesion.getAtnPreHospitalaria()==true)
                atnPre="S";
            else
                atnPre="N";
                
            SimpleDateFormat fechaAten = new SimpleDateFormat("dd/MM/yyyy");
                                //System.out.println("fechaIngreso: "+fechaIng.format(getFechaIngreso()));
                                fechaAtencion=(fechaAten.format(oAtencion.getFechaAtencion()));
                               // setFechaIngresoStr(fechaIngresoStr);
            fechaOcurrencia=(fechaAten.format(oLesion.getFechaOcurrencia()));
            sQuery="SELECT * FROM insertaHojaLesion("+oPaciente.getFolioPaciente()+"::bigint,'"+sUsuario+"',"+embarazada+",'"+getSabeLeerEscribir()+"','"+getEscolaridad().getClaveParametro()+"','"+oLesion.getDomicilioOcurrencia()+"','"
                    +oLesion.getEstado().getClaveEdo()+"','"+oLesion.getMunicipio().getClaveMun()+"','"+oLesion.getCiudad().getClaveCiu()+"','"+fechaOcurrencia+" "+oLesion.getHoraOcurrencia()+"','"
                    +oLesion.getDiaFestivo()+"','"+discap+"',"+oLesion.getIntencionalidad().getClave()+"::smallint,"+tipoViolencia+","+evenAutoInf+",'"+oLesion.getPacBajoEfectos().getClaveParametro()+"',"+oLesion.getSitioOcurrencia().getClave()+"::smallint,"
                    +oLesion.getAgenteLesion().getClaveParametro()+"::smallint,"+siAuto+","+usoEquipoSeg+","+nomEquipoSeg+","+oLesion.getAreaAnatomicaGrave().getClaveParametro()+"::smallint,"+oLesion.getConsecuenciaResultante().getClaveParametro()+"::smallint,'"
                    +atnPre+"','"+oAdmision.fechaActual()+" "+oLesion.getTiempoTraslado()+"',"+tipoAgresor+","+edadAgresor+","+sexoAgresor+","+parenAgresor+","+agresorEfectos+",'"+fechaAtencion+" "+oAtencion.getHoraAtencion()+"','"
                    +oAtencion.getServicioAtencion().getClaveParametro()+"',"+horaAtnUrgs+",'"+oAtencion.getTipoAtencion().getClaveParametro()+"','"+oAtencion.getReferidoPor().getClaveParametro()+"','"
                    +oAtencion.getDestinoDespAtencion().getClaveParametro()+"',"+afe1+","+afe2+","+afe3+","+afe4+","+afe5+",'"+oAtencion.getMinisterioPublico()+"');";
           System.out.print(sQuery);
           oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
                                        System.out.println("si entra a ingresar: "+nRet);
				}
			}
            return nRet;
        }
    
        
    public HojaLesiones[] buscaHistorialHojaLesion(long foliopac) throws Exception{
        HojaLesiones oHoja=null, arrHojaLesiones[]=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        if(foliopac==0){
          throw new Exception("HojaLesiones.buscaHistorialHojaLesion: Error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialhojalesion("+foliopac+"::BIGINT);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrHojaLesiones = new HojaLesiones[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oHoja = new HojaLesiones();
                    oHoja.getLesion().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oHoja.getLesion().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oHoja.getLesion().setFechaOcurrencia((Date)vRowTemp.get(2));
                    oHoja.getLesion().getSitioOcurrencia().setDescripcion((String)vRowTemp.get(3));
                    oHoja.getLesion().getAreaAnatomicaGrave().setValor((String)vRowTemp.get(4));
                    oHoja.getLesion().getAgenteLesion().setValor((String)vRowTemp.get(5)); 
                    arrHojaLesiones[i]=oHoja;
                } 
            }
        }
        return arrHojaLesiones; 
    }
    
        
	public String getEsEmbarazada() {
	return bEsEmbarazada;
	}

	public void setEsEmbarazada(String valor) {
	bEsEmbarazada=valor;
	}
//set y get edad fetil*******************************************
        public String bMujerEdadFertil(){
        return bMujerEdadFertil;
        }
        public void setMujerEdadFertil(String valor){
        bMujerEdadFertil= valor;
        }
//***************************************************************
        
        
	public char getSabeLeerEscribir() {
	return bSabeLeerEscribir;
	}

	public void setSabeLeerEscribir(char valor) {
	bSabeLeerEscribir=valor;
	}
        
//*****************************************************************
        public void setTipoEsco(char valor){
        oTipoEsco=valor;
        }
        public char getTipoEsco(){
        return oTipoEsco;
        }
//*****************************************************************       
	public Parametrizacion getEscolaridad() {
	return oEscolaridad;
	}

	public void setEscolaridad(Parametrizacion valor) {
	oEscolaridad=valor;
	}
//******************************************************************
	public Lesion getLesion() {
	return oLesion;
	}

	public void setLesion(Lesion valor) {
	oLesion=valor;
	}

	public Atencion getAtencion() {
	return oAtencion;
	}

	public void setAtencion(Atencion valor) {
	oAtencion=valor;
	}

	public Agresor getAgresor() {
	return oAgresor;
	}

	public void setAgresor(Agresor valor) {
	oAgresor=valor;
	}

} 

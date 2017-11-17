package mx.gob.hrrb.modelo.urgencias;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Usuario;
import org.primefaces.context.RequestContext;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AdmisionUrgs extends EpisodioMedico implements Serializable{
	private AccesoDatos oAD;
	private Date dFechaAtencion;
	private int nFolioAdmision;
	private int nSobres;
	private int oAreaServicio;
	private Parametrizacion oEDAS;
	private String sMotivoAtencion;
	private Parametrizacion oTriage;
	private char sIRAS;
	private String sMotivoConsulta;
	private String sObservaciones;
	private String sTipoUrgs;
        private String sDig1;
        private String sDig2;
        private String sDig3;
        private String sFechaAdm;
        private String sMedicoRecibeStr;
        
        //-----------
        
        private String altaHospitalariaStr;
        private String fechaIngresoStr;
        private String sFechaStr;
	private String NivelSocioEcoStr;
        private Hospitalizacion oHospitalizacion;
        private String dVigenciaNivelSocStr;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        
        private String oAreaServicioSTR;
        private String bEstadoHosp;
        private String bCamaElegida;
        private String bEstadoAlta;
        private String sTipoCamaUrgs;
        private String sTriageColor;
        
        private int año;
        private int mes;
        private int dia;
        private int hora;
        private int minuto;
        private int segundo;
        public String folio;
        public String fechaSistema;
        public String horaSistema;
        private String fechaCompleta="";
        private Calendar fecha=null;
        public boolean bandNH=true;

        private String altaHosp = "";
        private String ingresoHosp = "";
        
        private boolean bAdmisionUrgs;
        private boolean bHospitalizacion;
        private boolean bAdmisionUrgs2;
        private boolean bHospitalizacion2;
        private boolean bAmbos;
        private int cont;
        private boolean bHabAltaFisica;
        private boolean btnAltaHosp;
        private boolean bBuscarFolio;
        private String sDestinoUrg;
        private String sEDAStr;
        private int total, totalMujeres, totalHombres;
        private int nTotalMedicina;
        private int nTotalGineco; 
        private int nTotalNeonato; 
        private int nTotalCirugia;
        private int nTotalPedia;
        private Usuario oUsuario;
        private Parametrizacion oTipCamUrgs;
        
        public AdmisionUrgs(){
            oUsuario=new Usuario();
            bBuscarFolio=false;
            btnAltaHosp=false;
            bHabAltaFisica=false;
            bCamaElegida="display: none;";
            bEstadoHosp="display: none;";
            bEstadoAlta="display: none;";
            cont=0;
            bAmbos=false;
            bAdmisionUrgs=false;
            bHospitalizacion=false;
            bAdmisionUrgs2=false;
            bHospitalizacion2=false;
            oHospitalizacion=new Hospitalizacion();
            sDig2="1";
            sMedicoRecibeStr="";
            NivelSocioEcoStr="";
            nFolioAdmision=0;
            fecha = new GregorianCalendar();    
            fechaCompleta="";
            año = fecha.get(Calendar.YEAR);
            mes = fecha.get(Calendar.MONTH);
            dia = fecha.get(Calendar.DAY_OF_MONTH);
            hora = fecha.get(Calendar.HOUR_OF_DAY);
            minuto = fecha.get(Calendar.MINUTE);
            segundo = fecha.get(Calendar.SECOND);
            String f=dia + "/" + (mes+1) + "/" + año;
            generaFechaSis(dia,mes+1,año);
            generaHora(hora,minuto,segundo);
            setFolio();
            bandNH=true;
            oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
                oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
                sUsuario=oFirm.getUsu().getIdUsuario();
            }
            oTipCamUrgs=new Parametrizacion();
        }

      /*  @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AdmisionUrgs.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAdmisionUrgs();"; 
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
        @Override*/
	/*public AdmisionUrgs[] buscarTodos() throws Exception{
	AdmisionUrgs arrRet[]=null, oAdmisionUrgs=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAdmisionUrgs();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AdmisionUrgs[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        @Override*/
	/*public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AdmisionUrgs.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAdmisionUrgs();"; 
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
        @Override*/
	/*public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AdmisionUrgs.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAdmisionUrgs();"; 
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
        @Override*/
	/*public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AdmisionUrgs.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAdmisionUrgs();"; 
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
	public Date getFechaAtencion() {
	return dFechaAtencion;
	}

	public void setFechaAtencion(Date valor) {
	dFechaAtencion=valor;
	}

	public int getFolioAdmision() {
	return nFolioAdmision;
	}

	public void setFolioAdmision(int valor) throws Exception {
	nFolioAdmision=valor;
        
        ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        FacesMessage message=null;
        cont++;
        if(cont>2 && bBuscarFolio==false){
            sQuery = "SELECT buscaFolioAdm("+nFolioAdmision+");";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet = ((Double)vRowTemp.get(0)).intValue();
                    
                    }
                }
            if(nRet!=0){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro de Paciente", "El folio de Admision ya ha sido asignado");
            RequestContext.getCurrentInstance().showMessageInDialog(message);
            //nFolioAdmision=0;
            }
        }
	}

	public int getSobres() {
	return nSobres;
	}

	public void setSobres(int valor) {
	nSobres=valor;
	}

	public int getAreaServicio() {
	return oAreaServicio;
	}

	public void setAreaServicio(int valor) {
	oAreaServicio=valor;
	}

	public Parametrizacion getEDAS() {
	return oEDAS;
	}

	public void setEDAS(Parametrizacion valor) {
	oEDAS=valor;
	}

	public String getMotivoAtencion() {
	return sMotivoAtencion;
	}

	public void setMotivoAtencion(String valor) {
	sMotivoAtencion=valor;
	}

	public Parametrizacion getTriage() {
	return oTriage;
	}

	public void setTriage(Parametrizacion valor) {
	oTriage=valor;
	}

	public char getIRAS() {
	return sIRAS;
	}

	public void setIRAS(char valor) {
	sIRAS=valor;
	}

	public String getMotivoConsulta() {
	return sMotivoConsulta;
	}

	public void setMotivoConsulta(String valor) {
	sMotivoConsulta=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public String getTipoUrgs() {
	return sTipoUrgs;
	}

	public void setTipoUrgs(String valor) {
	sTipoUrgs=valor;
	}

    /**
     * @return the sFechaAdm
     */
    public String getFechaAdm() {
        return sFechaAdm;
    }

    /**
     * @param sFechaAdm the sFechaAdm to set
     */
    public void setFechaAdm(String sFechaAdm) {
        this.sFechaAdm = sFechaAdm;
    }

    /**
     * @return the sDig1
     */
    public String getDig1() {
        return sDig1;
    }

    /**
     * @param sDig1 the sDig1 to set
     */
    public void setDig1(String sDig1) {
        this.sDig1 = sDig1;
    }

    /**
     * @return the sDig2
     */
    public String getDig2() {
        return sDig2;
    }

    /**
     * @param sDig2 the sDig2 to set
     */
    public void setDig2(String sDig2) throws Exception {
        this.sDig2 = sDig2;
    }

    /**
     * @return the sDig3
     */
    public String getDig3() {
        return sDig3;
    }

    /**
     * @param sDig3 the sDig3 to set
     */
    public void setDig3(String sDig3) throws Exception {
        this.sDig3 = sDig3;
        setFolioAdmision(Integer.parseInt(getDig1()+getDig2()+getDig3()));
        
        ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        FacesMessage message=null;
        
        sQuery = "SELECT buscaFolioAdm("+getFolioAdmision()+");";
        oAD=new AccesoDatos(); 
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery);
	    oAD.desconectar(); 
	    if (rst != null && rst.size() == 1) {
		ArrayList vRowTemp = (ArrayList)rst.get(0);
		nRet = ((Double)vRowTemp.get(0)).intValue();
		}
	}
        if(nRet!=0){
        //sDig3="0000";
        this.sDig3 = "";
        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro de Paciente", "El folio de Admision ya ha sido asignado");
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        
        }
    }

    /**
     * @return the sMedicoRecibeStr
     */
    public String getMedicoRecibeStr() {
        return sMedicoRecibeStr;
    }

    /**
     * @param sMedicoRecibeStr the sMedicoRecibeStr to set
     */
    public void setMedicoRecibeStr(String sMedicoRecibeStr) {
        this.sMedicoRecibeStr = sMedicoRecibeStr;
    }

//*******************************************************************************
        public int insertarPacUrg() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String v2[]=getFechaStr().split("/");
        String v[];
        String numAux="";
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        
        switch(getPaciente().getSeg().getUnaDer().charAt(1)){
            case '0': getPaciente().getSeg().setVigencia(null);break;
            case '9': getPaciente().getSeg().setVigencia(null);break;
            case '1': {v2=getPaciente().getSeg().getVigenciaTexto().split("/");
                       try {
                           //setVigencia(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
		
            }break;
            case '2': {v2=getPaciente().getSeg().getVigenciaTexto2().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
                       
            }break;
            case '3': {v2=getPaciente().getSeg().getVigenciaTexto3().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '4': {v2=getPaciente().getSeg().getVigenciaTexto4().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
                       
            }break;
            case '5': {v2=getPaciente().getSeg().getVigenciaTexto5().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '6': {v2=getPaciente().getSeg().getVigenciaTexto6().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            
            case '7': {v2=getPaciente().getSeg().getVigenciaTexto7().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '8': {v2=getPaciente().getSeg().getVigenciaTexto8().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'G': {v2=getPaciente().getSeg().getVigenciaTextoG().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'P': {v2=getPaciente().getSeg().getVigenciaTextoP().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}}break;
       }
        
        switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                                    
              case '1': numAux=getPaciente().getSeg().getNumero();break;
              case '2': numAux=getPaciente().getSeg().getNumero2();break;
              case '3': numAux=getPaciente().getSeg().getNumero3();break;
              case '4': numAux=getPaciente().getSeg().getNumero4();break;
              case '5': numAux=getPaciente().getSeg().getNumero5();break;
              case '6': numAux=getPaciente().getSeg().getNumero6();break;
              case '7': numAux=getPaciente().getSeg().getNumero7();break;
              case '8': numAux=getPaciente().getSeg().getNumero8();break;
              case 'G': numAux=getPaciente().getSeg().getNumeroG();break;
              case 'P': numAux=getPaciente().getSeg().getNumeroP();break;
        }
       
        
         if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
                        
             
                        String fechaAdm="", exp="", cpAux="", curp="", cveleng="",numseg="", vig="", apmat="", vigencia="", pais="", nacido="", edo="", mun="", ciu="", colonia="",edoNac="";
                        String edoCivil="";
                        if(getPaciente().getApMaterno().compareTo("")==0) apmat="null";
                        else apmat="'"+getPaciente().getApMaterno()+"'";
                        
                        if(getPaciente().getPais().compareTo("MÉXICO")==0 || getPaciente().getPais().compareTo("MEXICO")==0)
                        {
                            if(getPaciente().getCiudadCP().getCp().compareTo("")!=0)
                                cpAux="'"+getPaciente().getCiudadCP().getCp()+"'"; 
                            else 
                                cpAux="NULL";
                            
                            
                            edo="'"+getPaciente().getEstado().getClaveEdo()+"'";
                            edoNac="'"+getPaciente().getEstado().getClaveEdo()+"'";
                            mun="'"+getPaciente().getMunicipio().getClaveMun()+"'";ciu="'"+getPaciente().getCiudad().getClaveCiu()+"'";
                        }
                        else
                        {cpAux="NULL";edo="'99'";mun="'999'";ciu="'9999'"; }
                        
                        if(getPaciente().getColonia().compareTo("")==0)colonia="null";
                        else colonia="'"+getPaciente().getColonia()+"'";
                        
                        if(getPaciente().getCiudadCP().getCp()==null){cpAux="NULL";}
                        else{
                        if (getPaciente().getCiudadCP().getCp().compareTo("")==0){cpAux="NULL";}
                            else {cpAux="'"+getPaciente().getCiudadCP().getCp()+"'";}
                        }
                        if (getPaciente().getCurp().compareTo("")==0){curp="null";}
                            else {curp="'"+getPaciente().getCurp()+"'";}
                        
                        if (getPaciente().getEtnicidad().getClaveLengua()==null){cveleng="null";}
                            else {cveleng="'"+getPaciente().getEtnicidad().getClaveLengua()+"'";}
                        
                        if (numAux.compareTo("")==0){numseg="null";}
                            else {numseg="'"+numAux+"'";}
                        
                        if(getPaciente().getSeg().getVigencia()==null){vigencia="null";}
                        else {vigencia="'"+getPaciente().getSeg().getVigenciaTexto()+"'";}
                        
                        if(getPaciente().getPais().compareTo("MEXICO")==0 || getPaciente().getPais().compareTo("MÉXICO")==0){pais="null";}
                        else {pais="'"+getPaciente().getPais()+"'";}
                        
                        if(getPaciente().getNacidoEnHospitalP()==null){nacido="null";}
                        else{nacido="'"+getPaciente().getNacidoEnHospitalP()+"'";}
                        /*
                        if(getFechaAdm().substring(3, 5)=="jan"){
                            fechaAdm=getFechaAdm().substring(0, 3)+"01"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="feb"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"02"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="mar"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"03"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="apr"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"04"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="may"){
                        fechaAdm=getFechaAdm().substring(0, 4)+"05"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="jun"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"06"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="jul"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"07"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="aug"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"08"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="sep"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"09"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="oct"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"10"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 5)=="nov"){
                        fechaAdm=getFechaAdm().substring(0, 3)+"11"+getFechaAdm().substring(6,getFechaAdm().length());
                        }
                        else{
                        fechaAdm=getFechaAdm().substring(0, 3)+"12"+getFechaAdm().substring(6,getFechaAdm().length());
                        }*/
			
                        if(getPaciente().getExpediente().getNumero()==0) exp="null";
                        else exp=""+getPaciente().getExpediente().getNumero();
                        
                        if(getPaciente().getEdoCivilStr()==null){edoCivil="null";}
                        else {edoCivil="'"+getPaciente().getEdoCivilStr()+"'";}
                        
                        
                        sQuery = "SELECT admisionPacienteUrgs('"+sUsuario+"', '"+getPaciente().getNombres()+"','"
                                +getPaciente().getApPaterno()
                                +"',"+apmat
                                +",'"+getFechaStr().substring(6,10)
                                +"-"+getFechaStr().substring(3,5)
                                +"-"+getFechaStr().substring(0,2)
                                +"','"+getPaciente().getSexoP()
                                +"',"+curp
                                +","+edoCivil
                                +",'"+getPaciente().getTelefono()
                                +"',"+getPaciente().getPeso()
                                +","+getPaciente().getTalla()
                                +",'"+getPaciente().getCalleNum()
                                +"',"+colonia+","
                                +1+"::CHAR,"
                                +edo
                                +","+mun
                                +","+ciu
                                +","+cpAux
                                +","+pais
                                +",'"+getPaciente().getReferencia().getClave()
                                +"','"+getPaciente().getEdadNumero()
                                +"',"+nacido
                                +",'"+getPaciente().getEtnicidad().getPertenenciaGpoIndStr()
                                +"','"+getPaciente().getEtnicidad().getHablaLenguaIndStr()
                                +"',"+cveleng
                                +",'"+getPaciente().getEtnicidad().getHablaEspaniolStr()
                                +"','"+getPaciente().getNumExterior()
                                +"','"+getPaciente().getNumInterior()
                                +"','"+getPaciente().getTipoVialidad().getCveTipoVial()
                                +"',"+getPaciente().getTipoAsentamiento().getCveAsenta()+"::smallint,"
                                +edoNac
                                +","+numseg
                                +","+vigencia+",'T01"
                                +getPaciente().getSeg().getUnaDer()+"','"
                                +fechaSistema+horaSistema+"','"
                                +getMedicoRecibe().getNoTarjeta()+"','"
                                +getDiagIngreso().getClave1()+"',"
                                +getMedicoRecibe().getArea().getClave()+"::smallint,"
                                +getFolioAdmision()+","
                                +exp+");";
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
        
        public String buscarPacienteUrg(long folio) throws Exception{
        resetea2();
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        String edad;
        String v[], v2[];
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscapacienteadm("+folio+"::bigint);"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());  
                                getPaciente().setNombres((String) vRowTemp.get( 1 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 3 ));
                                v=vRowTemp.get(4).toString().split("-");
                                setFechaStr(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(4));
                                getPaciente().setSexoP((String)vRowTemp.get(5));
                                getPaciente().setCurp((String)vRowTemp.get(6));
                                if(vRowTemp.get(7).toString().compareTo("")!=0)
                                getPaciente().setEdoCivilStr(((String)vRowTemp.get(7)));
                                getPaciente().setPeso(((Double)vRowTemp.get(8)).floatValue());
                                getPaciente().setTalla(((Double)vRowTemp.get(9)).floatValue());
                                getPaciente().setCalleNum((String)vRowTemp.get(10));
                                getPaciente().setColonia((String)vRowTemp.get(11));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 12 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 13 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 14 ));
                                
                                if((String)vRowTemp.get(15)!=null)
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(15));
                                
                                if(vRowTemp.get(16).toString().compareTo("")==0)
                                     getPaciente().setPais("MÉXICO");
                                else
                                getPaciente().setPais((String)vRowTemp.get(16));
                                
                                getPaciente().getReferencia().setClave((String)vRowTemp.get(17));
                                getPaciente().setNacidoEnHospitalP((String)vRowTemp.get(18));
                                getPaciente().getEtnicidad().setPertenenciaGpoIndStr((String)vRowTemp.get(19));
                                getPaciente().getEtnicidad().setHablaLenguaIndStr((String)vRowTemp.get(20));
                                getPaciente().getEtnicidad().setClaveLengua((String)vRowTemp.get(21));
                                getPaciente().getEtnicidad().setHablaEspaniolStr((String)vRowTemp.get(22));
                                getPaciente().getSeg().setUnaDer(((String)vRowTemp.get(25)).substring(3, 5));
				if((String)vRowTemp.get(23)==null)
                                    getPaciente().getSeg().setNumero(null);
                                else{
                                    switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                                    
                                        case '1': getPaciente().getSeg().setNumero((String)vRowTemp.get(23));break;
                                        case '2': getPaciente().getSeg().setNumero2((String)vRowTemp.get(23));break;
                                        case '3': getPaciente().getSeg().setNumero3((String)vRowTemp.get(23));break;
                                        case '4': getPaciente().getSeg().setNumero4((String)vRowTemp.get(23));break;
                                        case '5': getPaciente().getSeg().setNumero5((String)vRowTemp.get(23));break;
                                        case '6': getPaciente().getSeg().setNumero6((String)vRowTemp.get(23));break;
                                        case '7': getPaciente().getSeg().setNumero7((String)vRowTemp.get(23));break;
                                        case '8': getPaciente().getSeg().setNumero8((String)vRowTemp.get(23));break;
                                        case 'G': getPaciente().getSeg().setNumeroG((String)vRowTemp.get(23));break;
                                        case 'P': getPaciente().getSeg().setNumeroP((String)vRowTemp.get(23));break;
                                    }
                                }
                                
                                if(vRowTemp.get(24)==null){
                                    getPaciente().getSeg().setVigencia(null);
                                    getPaciente().getSeg().setVigenciaTexto("");
                                }
                                else{
                                    switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                                    
                                        case '1':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case '2':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto2(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case '3':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto3(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case '4':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto4(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case '5':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto5(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case '6':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto6(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case '7':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto7(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case '8':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTexto8(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case 'G':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTextoG(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                        case 'P':{
                                                v2=vRowTemp.get(24).toString().split("-");
                                                getPaciente().getSeg().setVigenciaTextoP(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                                getPaciente().getSeg().setVigencia((Date)vRowTemp.get(24));break;
                                        }
                                    }
                                }
                                
                                bRet = true;
			}
		}
		return "RegPacAdm"; 
	}
         public void resetea2() throws Exception{                               
            getPaciente().setApPaterno("");
            getPaciente().setApMaterno("");
            getPaciente().setNombres("");
            getPaciente().setSexoP("");
            getPaciente().setCurp("");
            getPaciente().setEdoCivilStr("");
            getPaciente().setPeso(0);
            getPaciente().setGramo(0);
            getPaciente().setTalla(0);
            setFechaStr("00/00/0000");
            getPaciente().setFechaNac(null);
            getPaciente().setCalleNum("");
            getPaciente().setColonia("");
            getPaciente().getMunicipio().getEstado().setClaveEdo("30");
            getPaciente().getMunicipio().setClaveMun("");
            getPaciente().getCiudadCP().getCiudad().setClaveCiu("");
            getPaciente().getCiudadCP().setCp("");
            getPaciente().setOtroPais("");
            getPaciente().getReferencia().setClave("0");
            getPaciente().setNacidoEnHospitalP("");
            getPaciente().getEtnicidad().setPertenenciaGpoIndStr("T0202");
            getPaciente().getEtnicidad().setHablaLenguaIndStr("T0202");
            getPaciente().getEtnicidad().setClaveLengua("99");
            getPaciente().getEtnicidad().setHablaEspaniolStr("T0201");
            getPaciente().getSeg().setNumero("");getPaciente().getSeg().setNumero2("");getPaciente().getSeg().setNumero3("");getPaciente().getSeg().setNumero4("");getPaciente().getSeg().setNumero5("");getPaciente().getSeg().setNumero6("");
            getPaciente().getSeg().setNumero7("");getPaciente().getSeg().setNumero8("");getPaciente().getSeg().setNumeroG("");getPaciente().getSeg().setNumeroP("");
            getPaciente().getSeg().setVigencia(null);
            getPaciente().getSeg().setVigenciaTexto2(null);getPaciente().getSeg().setVigenciaTexto3(null);getPaciente().getSeg().setVigenciaTexto4(null);getPaciente().getSeg().setVigenciaTexto5(null);
            getPaciente().getSeg().setVigenciaTexto6(null);getPaciente().getSeg().setVigenciaTexto7(null);getPaciente().getSeg().setVigenciaTexto8(null);getPaciente().getSeg().setVigenciaTextoG(null);getPaciente().getSeg().setVigenciaTextoP(null);
            getPaciente().setVigenciaTexto("00/00/0000");
            getPaciente().getSeg().setUnaDer("");
            bandNH=true;
        }
         
		 //*************************************************************************************
        

    /**
     * @return the sFechaStr
     */
    public String getFechaStr() {
        return sFechaStr;
    }

    /**
     * @param sFechaStr the sFechaStr to set
     */
    public void setFechaStr(String sFechaStr) {
        this.sFechaStr = sFechaStr;
    }
    
     
    
    /**
     * @return the fecha
     */
    public Calendar getFecha() {
        return fecha;
    }

    /**
     * @param fecha the fecha to set
     */
    public void setFecha(Calendar fecha) {
        this.fecha = fecha;
    }

   
    //*******************************************************************************
        public int modificarPacUrg() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String v2[]=getFechaStr().split("/");
        String v[];
        String numAux="";
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        
        switch(getPaciente().getSeg().getUnaDer().charAt(1)){
            case '0': getPaciente().getSeg().setVigencia(null);break;
            case '9': getPaciente().getSeg().setVigencia(null);break;
            case '1': {v2=getPaciente().getSeg().getVigenciaTexto().split("/");
                       try {
                           //setVigencia(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
		
            }break;
            case '2': {v2=getPaciente().getSeg().getVigenciaTexto2().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
                       
            }break;
            case '3': {v2=getPaciente().getSeg().getVigenciaTexto3().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '4': {v2=getPaciente().getSeg().getVigenciaTexto4().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
                       
            }break;
            case '5': {v2=getPaciente().getSeg().getVigenciaTexto5().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '6': {v2=getPaciente().getSeg().getVigenciaTexto6().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            
            case '7': {v2=getPaciente().getSeg().getVigenciaTexto7().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '8': {v2=getPaciente().getSeg().getVigenciaTexto8().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'G': {v2=getPaciente().getSeg().getVigenciaTextoG().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'P': {v2=getPaciente().getSeg().getVigenciaTextoP().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}}break;
       }
        
        switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                                    
              case '1': numAux=getPaciente().getSeg().getNumero();break;
              case '2': numAux=getPaciente().getSeg().getNumero2();break;
              case '3': numAux=getPaciente().getSeg().getNumero3();break;
              case '4': numAux=getPaciente().getSeg().getNumero4();break;
              case '5': numAux=getPaciente().getSeg().getNumero5();break;
              case '6': numAux=getPaciente().getSeg().getNumero6();break;
              case '7': numAux=getPaciente().getSeg().getNumero7();break;
              case '8': numAux=getPaciente().getSeg().getNumero8();break;
              case 'G': numAux=getPaciente().getSeg().getNumeroG();break;
              case 'P': numAux=getPaciente().getSeg().getNumeroP();break;
        }
       
        
         if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
                       
                        String fechaAdm="", cpAux="", curp="", cveleng="",numseg="", vig="", apmat="", vigencia="", pais="", nacido="", edo="", mun="", ciu="", colonia="";
                        String noExp="",edoCivil="";
                        if(getPaciente().getApMaterno().compareTo("")==0) apmat="null";
                        else apmat="'"+getPaciente().getApMaterno()+"'";
                        
                        if(getPaciente().getPais().compareTo("MÉXICO")==0 || getPaciente().getPais().compareTo("MEXICO")==0)
                        {
                            if(getPaciente().getCiudadCP().getCp().compareTo("")!=0)
                                cpAux="'"+getPaciente().getCiudadCP().getCp()+"'"; 
                            else 
                                cpAux="NULL";
                            
                            edo="'"+getPaciente().getEstado().getClaveEdo()+"'";
                            mun="'"+getPaciente().getMunicipio().getClaveMun()+"'";ciu="'"+getPaciente().getCiudad().getClaveCiu()+"'";
                        }
                        else
                        {cpAux="NULL";edo="'99'";mun="'999'";ciu="'9999'"; }
                        
                        if(getPaciente().getColonia().compareTo("")==0)colonia="null";
                        else colonia="'"+getPaciente().getColonia()+"'";
                        
                        if(getPaciente().getCiudadCP().getCp()==null){cpAux="NULL";}
                        else{
                        if (getPaciente().getCiudadCP().getCp().compareTo("")==0){cpAux="NULL";}
                            else {cpAux="'"+getPaciente().getCiudadCP().getCp()+"'";}
                        }
                        if (getPaciente().getCurp().compareTo("")==0){curp="null";}
                            else {curp="'"+getPaciente().getCurp()+"'";}
                        if (getPaciente().getEtnicidad().getHablaLenguaIndStr().compareTo("T0202")==0){cveleng="null";}
                            else {cveleng="'"+getPaciente().getEtnicidad().getClaveLengua()+"'";}
                       
                        if (numAux.compareTo("")==0){numseg="null";}
                            else {numseg="'"+numAux+"'";}
                        
                        if(getPaciente().getSeg().getVigencia()==null){vigencia="null";}
                        else {vigencia="'"+getPaciente().getSeg().getVigenciaTexto()+"'";}
                        
                        if(getPaciente().getPais().compareTo("MEXICO")==0 || getPaciente().getPais().compareTo("MÉXICO")==0){pais="null";}
                        else {pais="'"+getPaciente().getPais()+"'";}
                        if(bandNH==true){nacido="null";}
                        else{nacido="'"+getPaciente().getNacidoEnHospitalP()+"'";}
                        /*if(getFechaAdm().substring(3, 6).compareToIgnoreCase("ene")==0){
                            fechaAdm=getFechaAdm().substring(0, 2)+"-01-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("feb")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-02-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("mar")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-03-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("abr")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-04-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("may")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-05-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("jun")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-06-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("jul")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-07-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("ago")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-08-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("sep")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-09-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("oct")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-10-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else if(getFechaAdm().substring(3, 6).compareToIgnoreCase("nov")==0){
                        fechaAdm=getFechaAdm().substring(0, 2)+"-11-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }
                        else{
                        fechaAdm=getFechaAdm().substring(0, 2)+"-12-"+getFechaAdm().substring(7,getFechaAdm().length());
                        }*/
                        
                        if(getPaciente().getExpediente().getNumero()==0) noExp="null";
                        else noExp=""+getPaciente().getExpediente().getNumero();
                        
                        if(getPaciente().getEdoCivilStr()==null){edoCivil="null";}
                        else {edoCivil="'"+getPaciente().getEdoCivilStr()+"'";}
                        
			sQuery = "SELECT modificaPacienteUrgs('"+sUsuario+"', "+getPaciente().getFolioPaciente()+"::bigint, '"+getPaciente().getNombres()+"','"+getPaciente().getApPaterno()+"',"+apmat+", '"+getFechaStr().substring(6,10)+"-"+getFechaStr().substring(3,5)+"-"+getFechaStr().substring(0,2)+"', '"+getPaciente().getSexoP()+"', "+curp+","+edoCivil+", "+getPaciente().getPeso()+", "+getPaciente().getTalla()+", '"+getPaciente().getCalleNum()+"',"+colonia+","+1+"::CHAR,"+edo+", "
                                +mun+", "+ciu+","+cpAux+","+pais+", '"+getPaciente().getReferencia().getClave()+"', '"+getPaciente().getEdadNumero()+"', "+nacido+", '"+getPaciente().getEtnicidad().getPertenenciaGpoIndStr()+"', '"
                                +getPaciente().getEtnicidad().getHablaLenguaIndStr()+"', "+
                                cveleng+", '"+
                                getPaciente().getEtnicidad().getHablaEspaniolStr()+
                                "', "+numseg+","+vigencia+",'T01"+
                                getPaciente().getSeg().getUnaDer()+"','"+
                                fechaSistema+horaSistema+"','"+
                                getMedicoRecibe().getNoTarjeta()+"','"+
                                getDiagIngreso().getClave1()+"',"+
                                getMedicoRecibe().getArea().getClave()+"::smallint,"+getFolioAdmision()+","+noExp+");";
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
        //*************************************************************************************
        //*******trae los datos de paciente y los visualiza************************************
        //*************************************************************************************
        public String buscarPacienteCMUrg() throws Exception{
        resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String edad, diasEst="";
        String v[], v2[], v3[], v4[], v5[], v6[], v7[],v8[],v9[],v10[];
        
            if(getPaciente()==null ||
                getPaciente().getFolioPaciente()==0){   //completar llave
                throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
            }else{ 
                sQuery = "SELECT * FROM buscaPacienteCMUrgs("+getPaciente().getFolioPaciente()+"::bigint);"; 
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                }
                if (rst != null && rst.size() == 1) {
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        getPaciente().setNombres((String) vRowTemp.get( 1 ));
                        getPaciente().setApPaterno((String) vRowTemp.get( 2 ));
                        getPaciente().setApMaterno((String) vRowTemp.get( 3 ));
                        v=vRowTemp.get(4).toString().split("-");
                        setFechaStr(v[2]+"/"+v[1]+"/"+v[0]);
                        getPaciente().setFechaNac((Date)vRowTemp.get(4));
                        getPaciente().setSexoP((String)vRowTemp.get(5));
                        getPaciente().setCurp((String)vRowTemp.get(6));
                        getPaciente().setEdoCivilStr(((String)vRowTemp.get(7)));
                        getPaciente().setPeso(((Double)vRowTemp.get(8)).floatValue());
                        getPaciente().setTalla(((Double)vRowTemp.get(9)).floatValue());
                        getPaciente().setCalleNum((String)vRowTemp.get(10));
                        getPaciente().setColonia((String)vRowTemp.get(11));
                        getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 12 ));
                        getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 13 ));
                        getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 14 ));
                        if((String)vRowTemp.get(15)!=null)
                            getPaciente().getCiudadCP().setCp((String)vRowTemp.get(15));
                                
                        if(vRowTemp.get(16).toString().compareTo("")==0)
                             getPaciente().setPais("MÉXICO");
                        else
                            getPaciente().setPais((String)vRowTemp.get(16));
                                                                
                        getPaciente().getReferencia().setClave((String)vRowTemp.get(17));
                        getPaciente().setNacidoEnHospitalP((String)vRowTemp.get(18));
                        getPaciente().getEtnicidad().setPertenenciaGpoIndStr((String)vRowTemp.get(19));
                        getPaciente().getEtnicidad().setHablaLenguaIndStr((String)vRowTemp.get(20));
                        getPaciente().getEtnicidad().setClaveLengua((String)vRowTemp.get(21));
                        getPaciente().getEtnicidad().setHablaEspaniolStr((String)vRowTemp.get(22));
                        getPaciente().setTelefono((String)vRowTemp.get(23));
                        getPaciente().setNumExterior((String)vRowTemp.get(24));
                        getPaciente().setNumInterior((String)vRowTemp.get(25));
                        getPaciente().getTipoVialidad().setCveTipoVial((String)vRowTemp.get(26));
                        getPaciente().getTipoAsentamiento().setCveAsenta(((Double)vRowTemp.get(27)).intValue());
                        getPaciente().getoEstadoN().setClaveEdo((String)vRowTemp.get(28));
                        getPaciente().getSeg().setUnaDer(((String)vRowTemp.get(32)).substring(3, 5));
                        getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(29)).intValue());  
                        if((String)vRowTemp.get(30)==null)
                            getPaciente().getSeg().setNumero(null);
                        else{
                            switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                                case '1': getPaciente().getSeg().setNumero((String)vRowTemp.get(30));break;
                                case '2': getPaciente().getSeg().setNumero2((String)vRowTemp.get(30));break;
                                case '3': getPaciente().getSeg().setNumero3((String)vRowTemp.get(30));break;
                                case '4': getPaciente().getSeg().setNumero4((String)vRowTemp.get(30));break;
                                case '5': getPaciente().getSeg().setNumero5((String)vRowTemp.get(30));break;
                                case '6': getPaciente().getSeg().setNumero6((String)vRowTemp.get(30));break;
                                case '7': getPaciente().getSeg().setNumero7((String)vRowTemp.get(30));break;
                                case '8': getPaciente().getSeg().setNumero8((String)vRowTemp.get(30));break;
                                case 'G': getPaciente().getSeg().setNumeroG((String)vRowTemp.get(30));break;
                                case 'P': getPaciente().getSeg().setNumeroP((String)vRowTemp.get(30));break;
                            }
                        }
                        if(vRowTemp.get(31)==null){
                            getPaciente().getSeg().setVigencia(null);
                            getPaciente().getSeg().setVigenciaTexto("");
                        }else{
                            switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                                case '1':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case '2':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto2(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case '3':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto3(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case '4':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto4(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case '5':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto5(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case '6':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto6(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(21));break;
                                }
                                case '7':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto7(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case '8':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTexto8(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case 'G':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTextoG(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                                case 'P':{
                                    v2=vRowTemp.get(31).toString().split("-");
                                    getPaciente().getSeg().setVigenciaTextoP(v2[2]+"/"+v2[1]+"/"+v2[0]);
                                    getPaciente().getSeg().setVigencia((Date)vRowTemp.get(31));break;
                                }
                            }
                        }
                        
                        setFechaIngreso(((Date)vRowTemp.get(33)));
                        SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                        fechaIngresoStr=(fechaIng.format(getFechaIngreso()));
                        setFechaIngresoStr(fechaIngresoStr);

                        if(vRowTemp.get(34)!=null){
                            setAltaHospitalaria((Date)vRowTemp.get(34));
                            setAltaHosp(fechaIng.format(getAltaHospitalaria()));
                            setAltaHospitalariaStr(getAltaHosp());
                        }
                                
                        if(getAltaHospitalaria()==null){
                            bHospitalizacion=false;
                            bAdmisionUrgs=false;
                        }
                        else{
                            bHospitalizacion=true;
                            bAdmisionUrgs=true;
                        }
                  
                        getMedicoRecibe().setNoTarjeta(((Double)vRowTemp.get(35)).intValue());
                        getDiagIngreso().setClave((String)vRowTemp.get(36));
                        setAreaServicio(((Double)vRowTemp.get(37)).intValue());
                        setFolioAdmision(((Double)vRowTemp.get(38)).intValue());
                        oHospitalizacion.setNumIngresoHos(((Double)vRowTemp.get(39)).intValue());
                                
                        if(oHospitalizacion.getNumIngresoHos()!=0){
                            bAdmisionUrgs2=false;
                            bHospitalizacion2=false;
                        }
                        else{
                            bAdmisionUrgs2=true;
                            bHospitalizacion2=true;
                        }
                                
                        bAmbos = !(oHospitalizacion.getNumIngresoHos()!=0 && getAltaHospitalaria()==null);

                        if(vRowTemp.get(40)!=null){
                            oHospitalizacion.setFechaIngresoHos((Date)vRowTemp.get(40));
                            setIngresoHosp(fechaIng.format(oHospitalizacion.getFechaIngresoHos()));
                            oHospitalizacion.setFechaIngresoHospStr(getIngresoHosp());
                        }
                        if(vRowTemp.get(41).toString().compareTo("0.0")!=0){
                            getMedicoTratante().setNoTarjeta(((Double)vRowTemp.get(41)).intValue());
                        }
                                                                
                        if(vRowTemp.get(42).toString().length()!=0){
                            getCama().setNumero((String)vRowTemp.get(42));}
                                
                    if(vRowTemp.get(43).toString().compareTo("")!=0){
                    getCama().setCensable((String)vRowTemp.get(43));}

                    if(vRowTemp.get(44)!=(Double)0.0){
                        setAreaServicioHRRBSTR(((Double)vRowTemp.get(44)).intValue());
                    }

                    if(vRowTemp.get(45)!=(Double)0.0){
                    setSubServicioHRRB(((Double)vRowTemp.get(45)).intValue());}

                    if(vRowTemp.get(46).toString().compareTo("")!=0){
                    setNivelSocioEcoStr((0+(String)vRowTemp.get(46).toString().substring(0, 1)));
                    }

                    if((Date)vRowTemp.get(47)!=null){
                        getPaciente().setVigenciaNivelSoc((Date)vRowTemp.get(47));
                        v9=vRowTemp.get(47).toString().split("-");
                        getPaciente().setVigenciaNivelSTR(v9[2]+"/"+v9[1]+"/"+v9[0]);
                    }

                    diasEst=(String)vRowTemp.get(48);
                    if(diasEst.compareTo("")!=0){       
                        if(diasEst.substring(0, 3).compareTo("000")!=0){
                            if (diasEst.charAt(0)=='0')
                                setDiasEstanciaSTR(diasEst.substring(1, 3)+" AÑOS");
                            else
                                setDiasEstanciaSTR(diasEst.substring(0, 3)+" AÑOS");
                        }else{
                            if (diasEst.substring(4, 6).compareTo("00")!=0)
                                setDiasEstanciaSTR(diasEst.substring(4, 6)+" MESES");
                            else
                                setDiasEstanciaSTR(diasEst.substring(7, 9)+" DÍAS");
                        }
                    }

                    if(vRowTemp.get(49).toString().compareTo("")!=0){
                        setEdoSaludStr((String)vRowTemp.get(49));
                    }

                    if(vRowTemp.get(50).toString().compareTo("")!=0){
                    getFamiliarCercano().setNombresFam((String)vRowTemp.get(50));}

                    if(vRowTemp.get(51).toString().compareTo("")!=0){
                    getFamiliarCercano().setApPaternoFam((String)vRowTemp.get(51));}

                    if(vRowTemp.get(52).toString().compareTo("")!=0){
                    getFamiliarCercano().setApMaternoFam((String)vRowTemp.get(52));}

                    if(vRowTemp.get(53).toString().compareTo("")!=0){
                    getFamiliarCercano().setNoTel1((String)vRowTemp.get(53));}

                    if(vRowTemp.get(54).toString().compareTo("")!=0){
                    getFamiliarCercano().setNoTel2((String)vRowTemp.get(54));}

                    if(vRowTemp.get(55).toString().compareTo("")!=0){
                    getFamiliarCercano().setNoTel3((String)vRowTemp.get(55));}

                    if(vRowTemp.get(56).toString().compareTo("")!=0){
                    getFamiliarCercano().setParentescoStr((String)vRowTemp.get(56));}

                    getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(57));
                    System.out.println(vRowTemp);
                    return "urgencias/ConsultaModificaPac.xhtml";
                }
                else{
                    throw new Exception("Paciente no encontrado");
                }
            }
	}

           

   
    /**
     * @return the NivelSocioEcoStr
     */
    public String getNivelSocioEcoStr() {
        return NivelSocioEcoStr;
    }

    /**
     * @param NivelSocioEcoStr the NivelSocioEcoStr to set
     */
    public void setNivelSocioEcoStr(String NivelSocioEcoStr) {
        this.NivelSocioEcoStr = NivelSocioEcoStr;
    }

    /**
     * @return the oHospitalizacion
     */
    public Hospitalizacion getHospitalizacion() {
        return oHospitalizacion;
    }

    /**
     * @param oHospitalizacion the oHospitalizacion to set
     */
    public void setHospitalizacion(Hospitalizacion oHospitalizacion) {
        this.oHospitalizacion = oHospitalizacion;
    }

    /**
     * @return the dVigenciaNivelSocStr
     */
    public String getdVigenciaNivelSocStr() {
        return dVigenciaNivelSocStr;
    }

    /**
     * @param dVigenciaNivelSocStr the dVigenciaNivelSocStr to set
     */
    public void setdVigenciaNivelSocStr(String dVigenciaNivelSocStr) {
        this.dVigenciaNivelSocStr = dVigenciaNivelSocStr;
    }

     private void generaFechaSis(int d, int m, int a){
            String d1, m1, a1;
            if(d<10)
                d1="0"+d;
            else d1=Integer.toString(d);
            if(m<10)
                m1="0"+m;
            else m1=Integer.toString(m);
            a1=Integer.toString(a);

            String gf=a1+"-"+m1+"-"+d1;
            setFechaSistema(gf);
        }
        
        public String fechaActual(){
            String hoy=fechaSistema.substring(8,10)+"/"+fechaSistema.substring(5,7)+"/"+fechaSistema.substring(2,4);
            return hoy;
        }

        private void generaHora(int h, int m, int s){
            String ho, mi, se;
            if(h<10)
                ho="0"+h;
            else ho=Integer.toString(h);
            if(m<10)
                mi="0"+m;
            else mi=Integer.toString(m);
            if(s<10)
               se="0"+s;
            else se=Integer.toString(s);

            String gh=" "+ho+":"+mi+":"+se;
            horaSistema=gh;
        }

        private String setFolio(){
            String aux2="";
            mes++;
            if(dia>=26){
                mes++;

            aux2=Integer.toString(mes);
            }
            
            if(mes==13){
                mes=1;
                año++;
            }

            if (mes<10)
                aux2="0"+(mes);
            else 
                aux2=""+mes;

            folio=Integer.toString(año).substring(2)+aux2;
            return folio;
        }
    
        public String getFolio() {
            return folio;
        }
    
     /**
     * @return the fechaCompleta
     */
    public String getFechaCompleta() {
        return fechaCompleta;
    }

        /**
         * @param fechaCompleta the fechaCompleta to set
         */
        public void setFechaCompleta(String fechaCompleta) {
            this.fechaCompleta = fechaCompleta;
        }
        
        public void setFechaSistema(String fechaSistema){
        this.fechaSistema=fechaSistema;
        }
    
        public String getFechaSistema() {
            return fechaSistema;
        }

        public void setHoraSistema(String horaSistema){
            this.horaSistema=horaSistema;
        }

        public String getHoraSistema() 
        {return horaSistema;}
        
    
    /**
     * @return the altaHospitalariaStr
     */
    public String getAltaHospitalariaStr() {
        return altaHospitalariaStr;
    }

    /**
     * @param altaHospitalariaStr the altaHospitalariaStr to set
     */
    public void setAltaHospitalariaStr(String altaHospitalariaStr) {
        this.altaHospitalariaStr = altaHospitalariaStr;
    }

    /**
     * @return the fechaIngresoStr
     */
    public String getFechaIngresoStr() {
        return fechaIngresoStr;
    }

    /**
     * @param fechaIngresoStr the fechaIngresoStr to set
     */
    public void setFechaIngresoStr(String fechaIngresoStr) {
        this.fechaIngresoStr = fechaIngresoStr;
    }

    /**
     * @return the altaHosp
     */
    public String getAltaHosp() {
        return altaHosp;
    }

    /**
     * @param altaHosp the altaHosp to set
     */
    public void setAltaHosp(String altaHosp) {
        this.altaHosp = altaHosp;
    }

    /**
     * @return the ingresoHosp
     */
    public String getIngresoHosp() {
        return ingresoHosp;
    }

    /**
     * @param ingresoHosp the ingresoHosp to set
     */
    public void setIngresoHosp(String ingresoHosp) {
        this.ingresoHosp = ingresoHosp;
    }

    //******************************************************************************modifica al paciente de urgencias******
        public int modificarPacUrgCM() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String v2[]=getFechaStr().split("/");
        String v[];
        String numAux="";
        SimpleDateFormat formatoDelTexto = new SimpleDateFormat("yyyy-MM-dd");
        //fecha = null;
        
        switch(getPaciente().getSeg().getUnaDer().charAt(1)){
            case '0': getPaciente().getSeg().setVigencia(null);break;
            case '9': getPaciente().getSeg().setVigencia(null);break;
            case '1': v2=getPaciente().getSeg().getVigenciaTexto().split("/");
                        try {
                            getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                        } catch (Exception ex) {
                            ex.printStackTrace();
                        }
                        break;
            case '2': v2=getPaciente().getSeg().getVigenciaTexto2().split("/");
                       try {
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
                       break;
            case '3': v2=getPaciente().getSeg().getVigenciaTexto3().split("/");
                       try {
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {
                           ex.printStackTrace();
                       }
                        break;
            case '4': {v2=getPaciente().getSeg().getVigenciaTexto4().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
                       
            }break;
            case '5': {v2=getPaciente().getSeg().getVigenciaTexto5().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '6': {v2=getPaciente().getSeg().getVigenciaTexto6().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            
            case '7': {v2=getPaciente().getSeg().getVigenciaTexto7().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case '8': {v2=getPaciente().getSeg().getVigenciaTexto8().split("/");
                       try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'G': {v2=getPaciente().getSeg().getVigenciaTextoG().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}
            }break;
            case 'P': {v2=getPaciente().getSeg().getVigenciaTextoP().split("/");
                      try {
                           //setFechaNac(formatoDelTexto.parse(v[2]+"-"+v[1]+"-"+v[0])); 
                           getPaciente().getSeg().setVigencia(formatoDelTexto.parse(v2[2]+"-"+v2[1]+"-"+v2[0]));
                       } catch (Exception ex) {ex.printStackTrace();}}break;
       }
        
        switch(getPaciente().getSeg().getUnaDer().charAt(1)){
                                    
              case '1': numAux=getPaciente().getSeg().getNumero();break;
              case '2': numAux=getPaciente().getSeg().getNumero2();break;
              case '3': numAux=getPaciente().getSeg().getNumero3();break;
              case '4': numAux=getPaciente().getSeg().getNumero4();break;
              case '5': numAux=getPaciente().getSeg().getNumero5();break;
              case '6': numAux=getPaciente().getSeg().getNumero6();break;
              case '7': numAux=getPaciente().getSeg().getNumero7();break;
              case '8': numAux=getPaciente().getSeg().getNumero8();break;
              case 'G': numAux=getPaciente().getSeg().getNumeroG();break;
              case 'P': numAux=getPaciente().getSeg().getNumeroP();break;
        }
       
        
         if( this.getPaciente()==null ||
             this.getPaciente().getApPaterno()==null ||
             this.getPaciente().getApMaterno()==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
                       
                        String fechaAdm="",telefono="",numExt="",numInt="",edoNac="", cpAux="", curp="", cveleng="",numseg="", vig="", apmat="", vigencia="", pais="", nacido="", edo="", mun="", ciu="", colonia="";
                        if(getPaciente().getApMaterno().compareTo("")==0) apmat="null";
                        else apmat="'"+getPaciente().getApMaterno()+"'";
                        
                        if(getPaciente().getPais().compareTo("MÉXICO")==0 || getPaciente().getPais().compareTo("MEXICO")==0)
                        {
                            edo="'"+getPaciente().getEstado().getClaveEdo()+"'";
                            edoNac="'"+getPaciente().getoEstadoN().getClaveEdo()+"'";
                            mun="'"+getPaciente().getMunicipio().getClaveMun()+"'";ciu="'"+getPaciente().getCiudad().getClaveCiu()+"'";
                        }
                        else
                        {cpAux="NULL";edo="'99'";mun="'999'";ciu="'9999'"; edoNac="'99'"; }
                        
                        if(getPaciente().getColonia().compareTo("")==0)colonia="null";
                        else colonia="'"+getPaciente().getColonia()+"'";
                        
                        if(getPaciente().getCiudadCP().getCp()==null){cpAux="NULL";}
                        else{
                        if (getPaciente().getCiudadCP().getCp().compareTo("")==0){cpAux="NULL";}
                            else {cpAux="'"+getPaciente().getCiudadCP().getCp()+"'";}
                        }
                        if (getPaciente().getCurp().compareTo("")==0){curp="null";}
                            else {curp="'"+getPaciente().getCurp()+"'";}
                        
                           if (getPaciente().getEtnicidad().getClaveLengua()==null){cveleng="null";}
                            else {cveleng="'"+getPaciente().getEtnicidad().getClaveLengua()+"'";}
                        
                        if (getPaciente().getEtnicidad().getHablaLenguaIndStr().compareTo("T0201")!=0){cveleng="null";}
                            else {cveleng="'"+getPaciente().getEtnicidad().getClaveLengua()+"'";}
                        
                        if (getPaciente().getTelefono().compareTo("")==0){telefono="null";}
                        else {telefono="'"+getPaciente().getTelefono()+"'";}
                        
                        if(getPaciente().getNumExterior().compareTo("")==0){numExt="null";}
                        else {numExt="'"+getPaciente().getNumExterior()+"'";}
                        
                        if(getPaciente().getNumInterior().compareTo("")==0){numInt="null";}
                        else {numInt="'"+getPaciente().getNumInterior()+"'";}
                       
                        
                        if (numAux.compareTo("")==0){numseg="null";}
                            else {numseg="'"+numAux+"'";}
                        
                        if(getPaciente().getSeg().getVigencia()==null){vigencia="null";}
                        else {vigencia="'"+getPaciente().getSeg().getVigenciaTexto()+"'";}
                        
                        if(getPaciente().getPais().compareTo("MEXICO")==0 || getPaciente().getPais().compareTo("MÉXICO")==0){pais="null";}
                        else {pais="'"+getPaciente().getPais()+"'";}
                        if(bandNH==true){nacido="null";}
                        else{nacido="'"+getPaciente().getNacidoEnHospitalP()+"'";}
                        
                        String medicoTratante="", areaServicio="", folioAdmision="", medicoRecibe="", diagIngreso="", areaServicioUrgs="", subServicio="";
                        String nivSoc="", edoSalud="", nombreFam="", appatFam="", apmatFam="", tel1="",tel2="",tel3="", parentesco="", fecRegNiv="null", vigenciaNiv="";
                        String noExp="", edoCivil="";
                        if(getMedicoTratante().getNoTarjeta()==0){medicoTratante="null";}
                            else {medicoTratante=""+getMedicoTratante().getNoTarjeta();}
                        
                        if(getAreaServicio()==0){areaServicioUrgs="null";}
                        else {areaServicioUrgs=getAreaServicio()+"::smallint";}
                        
                        if(getFolioAdmision()==0){folioAdmision="null";}
                        else{folioAdmision=""+getFolioAdmision();}
                        
                        if(getMedicoRecibe().getNoTarjeta()==0){medicoRecibe="null";}
                        else{medicoRecibe=""+getMedicoRecibe().getNoTarjeta()+"";}
                        
                        if(getDiagIngreso().getClave1().compareTo("")==0){diagIngreso="null";}
                        else{diagIngreso="'"+getDiagIngreso().getClave1()+"'";}
                        
                        if(getAreaServicioHRRBSTR()==0){areaServicio="null";}
                        else {areaServicio=getAreaServicioHRRBSTR()+"::smallint";}
                        
                        if(getSubServicioHRRB()==0){subServicio="null";}
                        else {subServicio=getSubServicioHRRB()+"::smallint";}
                        
                        if(getNivelSocioEcoStr().compareTo("")==0){nivSoc="null";}
                        else {nivSoc=""+getNivelSocioEcoStr().charAt(1);}
                        
                        if(getEdoSaludStr().compareTo("")==0){edoSalud="null";}
                        else{edoSalud="'"+getEdoSaludStr()+"'";}
                        
                        if(getFamiliarCercano().getNombresFam().compareTo("")==0){nombreFam="null";}
                        else{nombreFam="'"+getFamiliarCercano().getNombresFam()+"'";}
                        
                        if(getFamiliarCercano().getApPaternoFam().compareTo("")==0){appatFam="null";}
                        else{appatFam="'"+getFamiliarCercano().getApPaternoFam()+"'";}
                        
                        if(getFamiliarCercano().getApMaternoFam().compareTo("")==0){apmatFam="null";}
                        else{apmatFam="'"+getFamiliarCercano().getApMaternoFam()+"'";}
                        
                        if(getFamiliarCercano().getParentescoStr().compareTo("")==0){parentesco="null";}
                        else{parentesco="'"+getFamiliarCercano().getParentescoStr()+"'";}
                        
                        if(getFamiliarCercano().getNoTel1().compareTo("")==0){tel1="null";}
                        else{tel1="'"+getFamiliarCercano().getNoTel1()+"'";}
                        
                        if(getFamiliarCercano().getNoTel2().compareTo("")==0){tel2="null";}
                        else{tel2="'"+getFamiliarCercano().getNoTel2()+"'";}
                        
                        if(getFamiliarCercano().getNoTel3().compareTo("")==0){tel3="null";}
                        else{tel3="'"+getFamiliarCercano().getNoTel3()+"'";}
                        
                        if(getPaciente().getVigenciaNivelSTR().compareTo("")==0){vigenciaNiv="null";}
                        else{vigenciaNiv="'"+getPaciente().getVigenciaNivelSTR()+"'";}
                        
                        if(getPaciente().getExpediente().getNumero()==0) noExp="null";
                        else noExp=""+getPaciente().getExpediente().getNumero();
                        
                        if(getPaciente().getEdoCivilStr()==null){edoCivil="null";}
                        else {edoCivil="'"+getPaciente().getEdoCivilStr()+"'";}
                        
                        sQuery = "SELECT * FROM modificaPacienteCMUrgs('"+sUsuario
                                +"',"+getPaciente().getFolioPaciente()+"::bigint, '"
                                +getPaciente().getNombres()
                                +"','"+getPaciente().getApPaterno()
                                +"', "+apmat
                                +",'"+getFechaStr().substring(6,10)+"-"+getFechaStr().substring(3,5)+"-"+getFechaStr().substring(0,2)
                                +"','"+getPaciente().getSexoP()
                                +"', "+curp
                                +",  "+edoCivil
                                +",  "+getPaciente().getPeso()
                                +",  "+getPaciente().getTalla()
                                +", '"+getPaciente().getCalleNum()
                                +"', "+colonia
                                +",  "+edo
                                +",  "+mun
                                +",  "+ciu
                                +",  "+cpAux
                                +",  "+pais
                                +", '"+getPaciente().getReferencia().getClave()
                                +"', "+nacido
                                +", '"+getPaciente().getEtnicidad().getPertenenciaGpoIndStr()
                                +"','"+getPaciente().getEtnicidad().getHablaLenguaIndStr()
                                +"', "+cveleng
                                +", '"+getPaciente().getEtnicidad().getHablaEspaniolStr()
                                +"',  '"+getPaciente().getTelefono()
                                +"', '"+getPaciente().getNumExterior()
                                +"', '"+getPaciente().getNumInterior()
                                +"', '"+getPaciente().getTipoVialidad().getCveTipoVial()
                                +"'," +getPaciente().getTipoAsentamiento().getCveAsenta()
                                +"::smallint,'"
                                      +getPaciente().getoEstadoN().getClaveEdo()
                                +"'," +numseg
                                +", " +vigencia
                                +",'T01"
                                      +getPaciente().getSeg().getUnaDer()
                                +"', "+medicoRecibe
                                +",  "+diagIngreso
                                +",  "+areaServicioUrgs
                                +",  "+folioAdmision
                                +",  "+medicoTratante
                                +",  "+areaServicio
                                +",  "+subServicio
                                +",  "+nivSoc
                                      +"::CHAR,"
                                      +vigenciaNiv
                                +",  "+edoSalud
                                +",  "+nombreFam
                                +",  "+appatFam
                                +",  "+apmatFam
                                +",  "+tel1
                                +",  "+tel2
                                +",  "+tel3
                                +",  "+parentesco
                                +",  "+fecRegNiv
                                +", '"+getPaciente().getEdadNumero()
                                +"', "+
                                       noExp+");";
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
            setPaciente(new Paciente());
            return nRet; 
	}
     
        public String habilitaAdmision(){
       String btn="";
        if(bAdmisionUrgs==false)
            return btn="";
        else if(bAdmisionUrgs==true)
            return btn="mostrarbtn";
        else if(bAdmisionUrgs2==false)
            return btn="";
        else if(bAdmisionUrgs2==true)
            return btn="mostrarbtn";
        else if(bAmbos==false)
            return btn="";
        else 
            return btn="mostrarbtn";
        
        
        }
        
        public String getHabilitaHosp(){
         
         String btn="";
            if(bHospitalizacion2==true)
                return btn="mostrarbtn";
            else if(bHospitalizacion==true)
                return btn="mostrarbtn";
            else if( bHospitalizacion2==false)
                return btn="";
            else 
                return btn="";
        }
        
        public String getStatusPac(){
            String status="";
            if(getAltaHospitalaria()!=null)
                return status="AMBULATORIO";
            else if(oHospitalizacion.getNumIngresoHos()!=0)
                return status="HOSPITALIZADO";
            else 
                return status="ADMITIDO";
        }
        
       
     //****************************************************************************************************************************************************
    public AdmisionUrgs[] buscarIngresos() throws Exception{
        
	AdmisionUrgs arrRet[]=null, oAdmisionUrgs=null;
	ArrayList rst = null;
        ArrayList<AdmisionUrgs> vObj=null;
	String sQuery = "";
        String edad="";
	int i=0, nTam=0;
        String v[];
		sQuery = "SELECT * FROM buscaIngresosPaciente("+getPaciente().getFolioPaciente()+"::bigint,"+getPaciente().getExpediente().getNumero()+");"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmisionUrgs = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmisionUrgs.getPaciente().setNombres((String)vRowTemp.get(0));
                            oAdmisionUrgs.getPaciente().setApPaterno((String)vRowTemp.get(1));
                            oAdmisionUrgs.getPaciente().setApMaterno((String)vRowTemp.get(2));
                            oAdmisionUrgs.getPaciente().setSexoP((String)vRowTemp.get(3));
                            oAdmisionUrgs.getPaciente().setCurp((String)vRowTemp.get(4));
                            v=vRowTemp.get(5).toString().split("-");
                            oAdmisionUrgs.getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]); 
                            oAdmisionUrgs.getPaciente().setFechaNac((Date) vRowTemp.get(5));
                            oAdmisionUrgs.getPaciente().setCalleNum((String)vRowTemp.get(6));
                            oAdmisionUrgs.getPaciente().setColonia((String)vRowTemp.get(7));
                            oAdmisionUrgs.getPaciente().getMunicipio().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                            oAdmisionUrgs.getPaciente().getCiudad().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                            oAdmisionUrgs.getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                            oAdmisionUrgs.getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                            oAdmisionUrgs.getPaciente().setOtroPais((String)vRowTemp.get(12));
                            oAdmisionUrgs.getPaciente().getSeg().setUnaDer((String)vRowTemp.get(13));
                            oAdmisionUrgs.getPaciente().getSeg().setNumero((String)vRowTemp.get(14));
                            
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            
                            if(vRowTemp.get(15)!=null){
                            oAdmisionUrgs.setFechaIngreso(((Date)vRowTemp.get(15)));
                            oAdmisionUrgs.setFechaIngresoStr(fechaIng.format(oAdmisionUrgs.getFechaIngreso()));
                            //oAdmisionUrgs.setFechaIngresoStr(getFechaIngresoStr());
                            }                          
                            if(vRowTemp.get(16)!=null){ 
                                oAdmisionUrgs.setAltaHospitalaria((Date)vRowTemp.get(16));
                                oAdmisionUrgs.setAltaHospitalariaStr(fechaIng.format(oAdmisionUrgs.getAltaHospitalaria()));
                                //oAdmisionUrgs.setAltaHospitalariaStr(getAltaHosp());
                                }
                            
                            oAdmisionUrgs.setFolioAdmision(((Double)vRowTemp.get(17)).intValue());
                            oAdmisionUrgs.getPaciente().getReferencia().setClave((String)vRowTemp.get(18));
                            oAdmisionUrgs.getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(19));
                            oAdmisionUrgs.getDiagIngreso().setClave((String)vRowTemp.get(20));
                            oAdmisionUrgs.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(21));
                            oAdmisionUrgs.setAreaServicio(((Double)vRowTemp.get(22)).intValue());
                            oAdmisionUrgs.setAreaServicioSTR((String)vRowTemp.get(23));
                            oAdmisionUrgs.getMedicoRecibe().setNoTarjeta(((Double)vRowTemp.get(24)).intValue());
                            oAdmisionUrgs.getMedicoRecibe().setNombres((String)vRowTemp.get(25));
                            oAdmisionUrgs.setAreaServicioHRRBSTR(((Double)vRowTemp.get(26)).intValue());
                            oAdmisionUrgs.setAreaServicioHRRBCad((String)vRowTemp.get(27));
                            oAdmisionUrgs.getMedicoTratante().setNoTarjeta(((Double)vRowTemp.get(28)).intValue());
                            oAdmisionUrgs.getMedicoTratante().setNombres((String)vRowTemp.get(29));
                            if(vRowTemp.get(30).toString().compareTo("T3700")==0)
                                oAdmisionUrgs.setDestinoSTR("DOMICILIO");
                            else if(vRowTemp.get(30).toString().compareTo("T3701")==0)
                                oAdmisionUrgs.setDestinoSTR("CENTRO DE SALUD");
                            else if(vRowTemp.get(30).toString().compareTo("T3702")==0)
                                oAdmisionUrgs.setDestinoSTR("TERCER NIVEL");
                            else if(vRowTemp.get(30).toString().compareTo("T3703")==0)
                                oAdmisionUrgs.setDestinoSTR("CONSULTA EXTERNA");
                            else if(vRowTemp.get(30).toString().compareTo("T3704")==0)
                                oAdmisionUrgs.setDestinoSTR("ESPECIALIDAD");
                            else if(vRowTemp.get(30).toString().compareTo("T3705")==0)
                                oAdmisionUrgs.setDestinoSTR("OTRO");
                            else
                                oAdmisionUrgs.setDestinoSTR("");
                            
                            vObj.add(oAdmisionUrgs);
			}
                    nTam = vObj.size();
                    arrRet = new AdmisionUrgs[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
                //buscarBasicosIngreso();
		return arrRet; 
	}         

//****************************************************************************************************************************************************

    /**
     * @return the oAreaServicioSTR
     */
    public String getAreaServicioSTR() {
        return oAreaServicioSTR;
    }

    /**
     * @param oAreaServicioSTR the oAreaServicioSTR to set
     */
    public void setAreaServicioSTR(String oAreaServicioSTR) {
        this.oAreaServicioSTR = oAreaServicioSTR;
    }

    public String buscarBasicosIngreso() throws Exception{
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String edad;
        String v[], v2[];
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosBasicosIngresos("+getPaciente().getFolioPaciente()+"::bigint,"+getPaciente().getExpediente().getNumero()+");"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setNombres((String) vRowTemp.get(0 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 1 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setSexoP((String)vRowTemp.get(3));
                                getPaciente().setCurp((String)vRowTemp.get(4));
                                v=vRowTemp.get(5).toString().split("-");
                                getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(5));
                                getPaciente().setCalleNum((String)vRowTemp.get(6));
                                getPaciente().setColonia((String)vRowTemp.get(7));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                                
                                if(vRowTemp.get(12).toString().compareTo("")==0)
                                    getPaciente().setOtroPais("MÉXICO");
                                else
                                getPaciente().setOtroPais((String)vRowTemp.get(12));
                                
                                getPaciente().getSeg().setNumero((String)vRowTemp.get(13));
                                getPaciente().getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                
			}
		}
		return ""; 
	}
   
     public String buscarBasicosHospitalizar() throws Exception{
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String edad;
        String v[], v2[];
        FacesMessage message=null;
		 if( getPaciente().getFolioPaciente()==0){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaDatosParaHospitalizar("+getPaciente().getFolioPaciente()+"::bigint);"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false) {
                                setEstadoHosp("");
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setNombres((String) vRowTemp.get(0 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 1 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setSexoP((String)vRowTemp.get(3));
                                getPaciente().setCurp((String)vRowTemp.get(4));
                                v=vRowTemp.get(5).toString().split("-");
                                getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(5));
                                getPaciente().setCalleNum((String)vRowTemp.get(6));
                                getPaciente().setColonia((String)vRowTemp.get(7));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                                
                                if(vRowTemp.get(12).toString().compareTo("")==0)
                                     getPaciente().setPais("MÉXICO");
                                else
                                getPaciente().setPais((String)vRowTemp.get(12));
                                
                                getPaciente().getSeg().setNumero((String)vRowTemp.get(13));
                                getPaciente().getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                setAreaServicioSTR((String)vRowTemp.get(16));
                                getMedicoRecibe().setNombres((String)vRowTemp.get(18));
                                getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(20)).intValue());
                                
                                if(getPaciente().getExpediente().getNumero()==0){
                                    setEstadoHosp("display: none;");
                                    message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Hospitalización de Paciente", "ERROR el paciente debe tener un Número de Expediente");
                                    RequestContext.getCurrentInstance().showMessageInDialog(message);
                                    return "Inicio.xhtml";
                                }
                                else
                                    return "urgencias/Hospitalizar.xhtml";
                        }
                        else{
                            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Hospitalización de Paciente", "ERROR el paciente ya ha sido hospitalizado o no se encuentra admitido");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                            return "Inicio.xhtml";
                        }
		}
	}
   

     public int insertarPacHospitalizacion() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
         if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
                	sQuery = "SELECT * FROM insertaHospitalizacion('"+sUsuario+"', "+getPaciente().getFolioPaciente()+"::bigint,"+getAreaServicioHRRBSTR()+
                                "::smallint,"+getSubServicioHRRB()+"::smallint,"+getMedicoTratante().getNoTarjeta()+",'"+getCama().getNumero()+"');";
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

    /**
     * @return the bEstadoHosp
     */
    public String getEstadoHosp() {
        return bEstadoHosp;
    }

    /**
     * @param bEstadoHosp the bEstadoHosp to set
     */
    public void setEstadoHosp(String bEstadoHosp) {
        this.bEstadoHosp = bEstadoHosp;
    }
  
    
    //*******************BUSCA DATOS BASICOS DE PACIENTE EN x CAMA**********************
    public String buscarPacienteEnCama() throws Exception{
        
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String edad;
        String v[], v2[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("PacienteCama.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPacienteEnCama('"+getCama().getNumeroOcupada()+"');"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setNombres((String) vRowTemp.get(0 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 1 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setSexoP((String)vRowTemp.get(3));
                                getPaciente().setCurp((String)vRowTemp.get(4));
                                v=vRowTemp.get(5).toString().split("-");
                                getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(5));
                                getPaciente().setCalleNum((String)vRowTemp.get(6));
                                getPaciente().setColonia((String)vRowTemp.get(7));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                                
                                if(vRowTemp.get(12).toString().compareTo("")==0)
                                     getPaciente().setPais("MÉXICO");
                                else
                                getPaciente().setPais((String)vRowTemp.get(12));
                                
                                getPaciente().getSeg().setNumero((String)vRowTemp.get(13));
                                getPaciente().getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                setAreaServicioSTR((String)vRowTemp.get(16));
                                setSubServicioHRRBSTR((String)vRowTemp.get(18));
                                getMedicoTratante().setNombres((String)vRowTemp.get(20));
                                getCama().setNumero((String)vRowTemp.get(22));
                                getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(23)).intValue());
                                bCamaElegida="";
                        }
                        else{
                            bCamaElegida="mostrarbtn";
                        }
                        
		}
		return ""; 
	}
   
    
       
    //*********************************CAMBIO CAMAS*********************************
     public String cambioCamas() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        
        afec=cambiarCamas();
            
        if(afec!=0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cambio de Camas", "Cambio Exitoso!!!");
            setPaciente(new Paciente());
            setAreaServicioSTR("");
            setSubServicioHRRBSTR("");
            getMedicoTratante().setNombres("");
            getCama().setNumero("");
            bCamaElegida="display: none;";
            pag="/faces/sesiones/Inicio.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Cambio de Camas", "Cambio Fallido!!!");
            setPaciente(new Paciente());
            setAreaServicioSTR("");
            setSubServicioHRRBSTR("");
            getMedicoTratante().setNombres("");
            getCama().setNumero("");
            bCamaElegida="display: none;";
        }
       
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

     public int cambiarCamas() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
         if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
                	sQuery = "SELECT * FROM CambioCamas('"+sUsuario+"', '"+getCama().getNumeroOcupada()+"','"+getCama().getNumeroDesocupada()+"');";
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
                setPaciente(new Paciente());
		return nRet; 
	}
     
     public String habilitaDatosCama(){
            return bCamaElegida;        
        }
     
     
        //*******************ALTA HOSPITALARIA***************************
     
        public String buscarPacienteAlta() throws Exception{
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String edad;
        String v[], v2[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPacienteAlta("+getPaciente().getFolioPaciente()+"::bigint);"; 
                        System.out.println(sQuery);
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false) {
                                setEstadoAlta("");
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setNombres((String) vRowTemp.get(0 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 1 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setSexoP((String)vRowTemp.get(3));
                                getPaciente().setCurp((String)vRowTemp.get(4));
                                v=vRowTemp.get(5).toString().split("-");
                                getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(5));
                                getPaciente().setCalleNum((String)vRowTemp.get(6));
                                getPaciente().setColonia((String)vRowTemp.get(7));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                                
                                if(vRowTemp.get(12).toString().compareToIgnoreCase("")==0)
                                    getPaciente().setOtroPais("MÉXICO");
                                else
                                getPaciente().setOtroPais((String)vRowTemp.get(12));
                                
                                getPaciente().getSeg().setNumero((String)vRowTemp.get(13));
                                getPaciente().getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                setAreaServicioSTR((String)vRowTemp.get(16));
                                getMedicoRecibe().setNombres((String)vRowTemp.get(18));
                                setAreaServicioHRRB((String)vRowTemp.get(20));
                                setSubServicioHRRBSTR((String)vRowTemp.get(22));
                                getMedicoTratante().setNombres((String)vRowTemp.get(24));
                                getCama().setNumero((String)vRowTemp.get(26));
                                setEdoSaludStr((String)vRowTemp.get(28));
                                setDestinoSTR((String)vRowTemp.get(30));
                                setRazonDestino((String)vRowTemp.get(31));
                                setMotivoEgresoP((String)vRowTemp.get(32));
                                setRazonAltaVolunTrasl((String)vRowTemp.get(33));
                                setAltaHospitalaria((Date)vRowTemp.get(29));
                                if(((Date)vRowTemp.get(29))==null){
                                    setBtnAltaHosp(false);
                                    setHabAltaFisica(true);
                                }
                                else{
                                    setBtnAltaHosp(true);
                                    setHabAltaFisica(false);}
                                return "urgencias/AltaHospitalaria.xhtml";
                        }
                        else{
                            setEstadoAlta("display: none;");
                            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Alta Hospitalaria", "ERROR el paciente ya ha sido dado de alta, no está hospitalizado o no se encuentra admitido");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                        return "Inicio.xhtml";
                        }
		}
	}
        
        
     public int insertarAltaHospi() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
         if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
             
             String razonDestino="null", razonAltaVolun="null", avisoMP="", cama="";
             
             if(getRazonDestino()==null )
                 razonDestino="null";
             else if(getRazonDestino().compareTo("")!=0)
                 razonDestino="'"+getRazonDestino()+"'";
             if(getRazonAltaVolunTrasl()==null)
                 razonAltaVolun="null";
             else if(getRazonAltaVolunTrasl().compareTo("")!=0)
                 razonAltaVolun="'"+getRazonAltaVolunTrasl()+"'";
                          
                 
             if(getCama().getNumero().compareTo("")==0)
                 cama="null";
             else
                 cama="'"+getCama().getNumero()+"'";
                	sQuery = "SELECT * FROM insertaAltaHospitalaria('"+sUsuario+"', "+getPaciente().getFolioPaciente()+"::bigint,'"+getDestinoSTR()+
                                "',"+razonDestino+",'"+getDocEntregada()+"','"+getMotivoEgresoP()+"',"+razonAltaVolun+
                                ","+cama+");";
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
     
     
     public int insertarAltaFisica() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
         if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
             
             String razonDestino="null", razonAltaVolun="null", avisoMP="", cama="";
             
             if(getRazonDestino()==null )
                 razonDestino="null";
             else if(getRazonDestino().compareTo("")!=0)
                 razonDestino="'"+getRazonDestino()+"'";
             if(getRazonAltaVolunTrasl()==null)
                 razonAltaVolun="null";
             else if(getRazonAltaVolunTrasl().compareTo("")!=0)
                 razonAltaVolun="'"+getRazonAltaVolunTrasl()+"'";
                          
                 
             if(getCama().getNumero().compareTo("")==0)
                 cama="null";
             else
                 cama="'"+getCama().getNumero()+"'";
                	sQuery = "SELECT * FROM insertaAltaFisica('"+sUsuario+"', "+getPaciente().getFolioPaciente()+"::bigint,'"+getDestinoSTR()+
                                "',"+razonDestino+",'"+getDocEntregada()+"','"+getMotivoEgresoP()+"',"+razonAltaVolun+
                                ","+cama+");";
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


        public List<Parametrizacion> getListaDestinos(){
        List<Parametrizacion> lLista = null;
            try {
                lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                        (new Parametrizacion()).buscarTabla("T37")));
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        return lLista;
       }
        
        public List<Parametrizacion> getListaMotivosEgreso() throws Exception{
        List<Parametrizacion> lLista = null;
            
                lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                        (new Parametrizacion()).buscarTabla("T38")));
            
        return lLista;
       } 

        
        //*********************************REPORTE DE ADMISIÓN HOSPITALARIA******************************************
 public List<AdmisionUrgs> BuscaReporteAdmHosp(String sFechaI, String sFechaF) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        totalMujeres=0;
        String v[], v2[]=null, diasEst; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM BuscaReporteAdmisionHospitalaria('"+sFechaI+"','"+sFechaF+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(2));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(3));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(4));
                            oAdmision.getPaciente().getSeg().setNumero((String)vRowTemp.get(5));
                            oAdmision.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());
                            oAdmision.getDiagIngreso().setClave((String)vRowTemp.get(7));
                            oAdmision.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(8));
                            
                            if(((Double)vRowTemp.get(9)).intValue()==76)
                                oAdmision.setAreaServicioHRRB("UP");
                            else if(((Double)vRowTemp.get(9)).intValue()==74)
                                oAdmision.setAreaServicioHRRB("UA");
                            else
                                oAdmision.setAreaServicioHRRB("UG");
                            
                            v=vRowTemp.get(11).toString().split("-");
                            oAdmision.getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]); 
                            oAdmision.getPaciente().setEdadNumero(oAdmision.getPaciente().calculaEdad());
                            oAdmision.setFechaIngreso((Date)vRowTemp.get(13));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            fechaIngresoStr=(fechaIng.format(oAdmision.getFechaIngreso()));
                            oAdmision.setFechaIngresoStr(fechaIngresoStr);
                            
                            diasEst=(String)vRowTemp.get(14);
                                if(diasEst.compareTo("")!=0){       
                                if(diasEst.substring(0, 3).compareTo("000")!=0){
                                   if (diasEst.charAt(0)=='0')
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(1, 3)+" AÑOS");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(0, 3)+" AÑOS");
                            }else{
                                if (diasEst.substring(4, 6).compareTo("00")!=0)
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(4, 6)+" MESES");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(7, 9)+" DÍAS");
                                }
                                }
                            oAdmision.getMedicoRecibe().setNombres((String)vRowTemp.get(16));
                            
                            if(((String)vRowTemp.get(18)).compareTo("")!=0)
                                oAdmision.getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(18));
                            else if(((String)vRowTemp.get(20)).compareTo("")!=0)
                                oAdmision.getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(20));
                            else 
                                oAdmision.getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(8));
                            
                            oAdmision.getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(22));
                            
                            if(vRowTemp.get(23)!=null)
                            {
                            oAdmision.setAltaHospitalaria((Date)vRowTemp.get(23));
                            dVigenciaNivelSocStr=(fechaIng.format(oAdmision.getAltaHospitalaria()));
                            oAdmision.setAltaHosp(dVigenciaNivelSocStr);
                            
                            }
                           
                            if(vRowTemp.get(25).toString().compareTo("T3700")==0)
                                oAdmision.setDestinoSTR("DOMICILIO");
                            else if(vRowTemp.get(25).toString().compareTo("T3701")==0)
                                oAdmision.setDestinoSTR("CENTRO DE SALUD");
                            else if(vRowTemp.get(25).toString().compareTo("T3702")==0)
                                oAdmision.setDestinoSTR("TERCER NIVEL");
                            else if(vRowTemp.get(25).toString().compareTo("T3703")==0)
                                oAdmision.setDestinoSTR("CONSULTA EXTERNA");
                            else if(vRowTemp.get(25).toString().compareTo("T3704")==0)
                                oAdmision.setDestinoSTR("ESPECIALIDAD");
                            else if(vRowTemp.get(25).toString().compareTo("T3705")==0)
                                oAdmision.setDestinoSTR("OTRO");
                            else
                                oAdmision.setDestinoSTR("PENDIENTE");
                            
                             
                            if(vRowTemp.get(23)==null && ((Double)(vRowTemp.get(24))).intValue()!=0){
                                oAdmision.setDestinoSTR("HOSPITALIZACIÓN");
                            }
                           
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
    /**
     * @return the bEstadoAlta
     */
    public String getEstadoAlta() {
        return bEstadoAlta;
    }

    /**
     * @param bEstadoAlta the bEstadoAlta to set
     */
    public void setEstadoAlta(String bEstadoAlta) {
        this.bEstadoAlta = bEstadoAlta;
    }

    /**
     * @return the bHabAltaFisica
     */
    public boolean getHabAltaFisica() {
        return bHabAltaFisica;
    }

    /**
     * @param bHabAltaFisica the bHabAltaFisica to set
     */
    public void setHabAltaFisica(boolean bHabAltaFisica) {
        this.bHabAltaFisica = bHabAltaFisica;
    }

    /**
     * @return the btnAltaHosp
     */
    public boolean getBtnAltaHosp() {
        return btnAltaHosp;
    }

    /**
     * @param btnAltaHosp the btnAltaHosp to set
     */
    public void setBtnAltaHosp(boolean btnAltaHosp) {
        this.btnAltaHosp = btnAltaHosp;
    }
    
    //*******************BUSCA DATOS DE FOLIO ADMISION***************************
     
        public void buscarFolioAdmision() throws Exception{
        bBuscarFolio=true;
	ArrayList rst = null;
	String sQuery = "";
        String v[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("RecuperaFolios.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPacienteFolioAdm("+getFolioAdmision()+");"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setNombres((String) vRowTemp.get(0 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 1 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setSexoP((String)vRowTemp.get(3));
                                getPaciente().setCurp((String)vRowTemp.get(4));
                                v=vRowTemp.get(5).toString().split("-");
                                getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(5));
                                getPaciente().setCalleNum((String)vRowTemp.get(6));
                                getPaciente().setColonia((String)vRowTemp.get(7));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                                
                                if(vRowTemp.get(12).toString().compareToIgnoreCase("")==0)
                                    getPaciente().setOtroPais("MÉXICO");
                                else
                                getPaciente().setOtroPais((String)vRowTemp.get(12));
                                
                                getPaciente().getSeg().setNumero((String)vRowTemp.get(13));
                                getPaciente().getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                setAreaServicioSTR((String)vRowTemp.get(16));
                                setAreaServicioHRRB((String)vRowTemp.get(18));
                                setSubServicioHRRBSTR((String)vRowTemp.get(20));
                                getMedicoTratante().setNombres((String)vRowTemp.get(22));
                                getCama().setNumero((String)vRowTemp.get(24));
                                setDestinoSTR((String)vRowTemp.get(25));
                                setMotivoEgresoP((String)vRowTemp.get(26));
                                getMedicoRecibe().setNombres((String)vRowTemp.get(28));
                                bCamaElegida="";
                        }
                        else{
                            bCamaElegida="display: none;";
                            message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "RecuperaFolios", "ERROR El Paciente con el Folio: "+getFolioAdmision()+" no ha sido dado de alta o el folio no pertenece al ultimo ingreso");
                            RequestContext.getCurrentInstance().showMessageInDialog(message);
                        }
		}
	}

             //*********************************RECUPERAR FOLIOS*********************************
     public String recuperaFolio() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        
        afec=modificaRecuperaFolio();
          
        if(afec!=0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Recuperar Folios", "Recuperación Exitosa!!!");
            pag="/faces/sesiones/Inicio.xhtml";
            setPaciente(new Paciente());
            setAreaServicioSTR("");
            setSubServicioHRRBSTR("");
            getMedicoTratante().setNombres("");
            getMedicoRecibe().setNombres("");
            getCama().setNumero("");
            setFolioAdmision(0);
            bCamaElegida="display: none;";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Recuperar Folios", "Recuperación Fallida!!!");
            setPaciente(new Paciente());
            setAreaServicioSTR("");
            setSubServicioHRRBSTR("");
            getMedicoTratante().setNombres("");
            getMedicoRecibe().setNombres("");
            getCama().setNumero("");
            setFolioAdmision(0);
            bCamaElegida="display: none;";
        }
       
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

     public int modificaRecuperaFolio() throws Exception{
	ArrayList rst = null;
        
	int nRet = 0;
	String sQuery = "";
         if( this==null){   //completar llave
			throw new Exception("RecuperarFolios: error de programación, faltan datos");
		}else{ 
                    String sCama="";
                    if(getCama().getNumeroDesocupada()==null)
                        sCama="NULL";
                    else
                        sCama="'"+getCama().getNumeroDesocupada()+"'";
                    
                	sQuery = "SELECT * FROM recuperaFolio('"+sUsuario+"', "+getFolioAdmision()+","+sCama+");";
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

    /**
     * @return the sTipoCamaUrgs
     */
    public String getTipoCamaUrgs() {
        return sTipoCamaUrgs;
    }

    /**
     * @param sTipoCamaUrgs the sTipoCamaUrgs to set
     */
    public void setTipoCamaUrgs(String sTipoCamaUrgs) {
        this.sTipoCamaUrgs = sTipoCamaUrgs;
    }

    /**
     * @return the sDestinoUrg
     */
    public String getDestinoUrg() {
        return sDestinoUrg;
    }

    /**
     * @param sDestinoUrg the sDestinoUrg to set
     */
    public void setDestinoUrg(String sDestinoUrg) {
        this.sDestinoUrg = sDestinoUrg;
    }
    
    public String getCalculaEdadUrg(){
            bandNH=false;
        if(getFechaStr()!=null){
        if(getFechaStr().compareTo("")!=0){
        String x[]=getFechaStr().split("/");
        Date fActual=new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fecAct=formato.format(fActual);
        int d1=Integer.parseInt(x[0]);
        int m1=Integer.parseInt(x[1]);
        int a1=Integer.parseInt(x[2]);
        String v[]=fecAct.split("/");
        int d2=Integer.parseInt(v[0]);
        int m2=Integer.parseInt(v[1]);
        int a2=Integer.parseInt(v[2]);
        int añofinal, mesfinal, diafinal=0;
        String mensaje="";
        int aux=0;
        int maux=m1;
        boolean b=false;
        añofinal=a2-a1;
        if (añofinal==1 && m2<=m1){
            añofinal=0;
            if (m1==m2){aux=1; b=true; if (d1<=d2)añofinal=1;}
            m2=12-m1+m2;
            m1=aux;
            
        }
        if (añofinal>0){
            if (m2<m1){ añofinal--; }
            if (m2==m1 && d2<d1){añofinal--;}
            if (añofinal==1)
            mensaje=añofinal+" AÑO";
            else
                mensaje=añofinal+" AÑOS";
        getPaciente().setEdadNumero("T2503");
        }else{
            mesfinal=m2-m1;
            if(mesfinal>0){
                diafinal=d2-d1;
                if (mesfinal==1 && d2>=d1)
                mensaje=mesfinal+" MES";
                else{
                    if(mesfinal>1 && d2>=d1)
                    mensaje=mesfinal+" MESES";
                    else{
                        if((mesfinal-1)==1)
                        mensaje=(mesfinal-1)+" MES";
                        else{
                            if (mesfinal>1 && d2<d1 && b==true)
                                mensaje=mesfinal+" MESES";
                            else
                            mensaje=(mesfinal-1)+" MESES";
                        }
                    }
                }
                getPaciente().setEdadNumero("T2502");
            }
            if (mesfinal==0){
                diafinal=d2-d1;
                if(diafinal==0 && añofinal==0)
                    diafinal=1;
              if (diafinal==1)
              mensaje=diafinal+" DÍA";
              else{
                  if(diafinal>1)
               mensaje=diafinal+" DÍAS";
              }
              getPaciente().setEdadNumero("T2501");
            }
            m1=maux;
            if (mesfinal==1 && añofinal==0 && d1>d2){
                if (m1==1 || m1==3 || m1==5 || m1==7 || m1==8 || m1==10 || m1==12)
                    diafinal=(31-d1)+d2;
                if (m1==4 || m1==6 || m1==9 || m1==11)
                    diafinal=(30-d1)+d2;
                if (m1==2){
                    if ((a1 % 4 == 0) && ((a1 % 100 != 0) || (a1 % 400 == 0)))
                        diafinal=(29-d1)+d2;
                    else
                        diafinal=(28-d1)+d2;
                }
                mensaje=diafinal+" DÍAS";
                getPaciente().setEdadNumero("T2501");
            }
        }
        
        if(mensaje.contains("DÍAS") || mensaje.contains("DÍA"))
            bandNH=false;
        else if(mensaje.contains("AÑO") || mensaje.contains("AÑOS"))
            bandNH=true;
        else if(mensaje.contains("MES") || mensaje.contains("MESES")){
            String mesA="";
            int mesAux=0;
            mesA=mensaje.substring(0, 1);
            mesAux=Integer.parseInt(mesA);
            
            bandNH = mesAux > 3;
        }
            
        return mensaje;
        }
        else{ 
            bandNH=true;
            return "";}
        }else{
            bandNH=true;
            return "";
        }
        }

    
      public boolean habilitaNHos(){
        return bandNH;
    }
    
    
    

     //*******************BUSCA DATOS BASICOS DE PACIENTE ADULTO PARA NOTA MEDICA**********************
    public String buscarPacienteNotaAdultos() throws Exception{
        
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String v[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("PacienteCama.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNotaMedicaAdultosNueva("+getPaciente().getFolioPaciente()+"::BIGINT);"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setNombres((String) vRowTemp.get(0 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 1 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setSexoP((String)vRowTemp.get(3));
                                getPaciente().setCurp((String)vRowTemp.get(4));
                                v=vRowTemp.get(5).toString().split("-");
                                getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(5));
                                if(getPaciente().calculaEdad()!=null){
                                if(getPaciente().calculaEdad().compareTo("5 AÑOS")==0 || getPaciente().calculaEdad().compareTo("4 AÑOS")==0
                                        || getPaciente().calculaEdad().compareTo("3 AÑOS")==0 || getPaciente().calculaEdad().compareTo("2 AÑOS")==0
                                        || getPaciente().calculaEdad().compareTo("1 AÑO")==0 || getPaciente().calculaEdad().compareTo("1 AÑOS")==0
                                        || getPaciente().calculaEdad().compareTo("0 AÑO")==0 || getPaciente().calculaEdad().compareTo("0 AÑOS")==0
                                        || getPaciente().calculaEdad().contains("MESES")==true || getPaciente().calculaEdad().contains("MES")==true
                                        || getPaciente().calculaEdad().contains("DÍAS")==true || getPaciente().calculaEdad().contains("DÍA")==true)
                                    setEstadoHosp("");
                                }
                                
                                getPaciente().setCalleNum((String)vRowTemp.get(6));
                                getPaciente().setColonia((String)vRowTemp.get(7));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                                
                                if(vRowTemp.get(12).toString().compareTo("")==0)
                                     getPaciente().setPais("MÉXICO");
                                else
                                getPaciente().setPais((String)vRowTemp.get(12));
                                
                                getPaciente().getSeg().setNumero((String)vRowTemp.get(13));
                                getPaciente().getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                
                                getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(16));
                                getPaciente().getReferencia().setTipo((String)vRowTemp.get(17));
                                getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(18)).intValue());
                                getPaciente().setFolioDefuncion((String)vRowTemp.get(19));
                                getPaciente().setPeso(((Double)vRowTemp.get(20)).floatValue());
                                getPaciente().setTalla(((Double)vRowTemp.get(21)).floatValue());
                                setFolioAdmision(((Double)vRowTemp.get(22)).intValue());
                                SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                setFechaIngreso(((Date)vRowTemp.get(23)));
                                setFechaIngresoStr(fechaIng.format(getFechaIngreso()));
                                bCamaElegida="";
                                return "urgencias/NotaMedicaAdultos.xhtml";
                            }
                            else{
                                bCamaElegida="display: none;";
                                message = new FacesMessage(FacesMessage.SEVERITY_FATAL, "Nota Médica Adultos", "ERROR El Paciente no se encuentra admitido o su nota ya fue generada");
                                RequestContext.getCurrentInstance().showMessageInDialog(message);
                                return "Inicio.xhtml";
                            }
                 }
	}
    
     public int insertarNotaAdultos() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
         if( this==null){   //completar llave
			throw new Exception("Paciente.modificar: error de programación, faltan datos");
		}else{ 
                String folioDefun="", TA="", FC="", FR="", Temp="", ocular="", verbal="", motora="", total="", afe1="", afe2="", afe3="";
                String proc1="", proc2="", proc3="", medCve1="", medCve2="", medCve3="", medPres1="", medPres2="", medPres3="";
                String medCod1="", medCod2="", medCod3="", motivoConsulta="", observaciones="", iras="", edas="", sobres="";
                
                if(getMotivoConsulta().compareTo("")==0)motivoConsulta="null";
                else motivoConsulta="'"+getMotivoConsulta()+"'";
                
                if(getObservaciones().compareTo("")==0)observaciones="null";
                else observaciones="'"+getObservaciones()+"'";
                
                if(getPaciente().getFolioDefuncion().compareTo("")==0)folioDefun="null";
                else folioDefun="'"+getPaciente().getFolioDefuncion()+"'";
                
                if(getSignosVitales().getTA().compareTo("")==0)TA="null";
                else TA="'"+getSignosVitales().getTA()+"'";
                
                if(getSignosVitales().getFC().compareTo("")==0)FC="null";
                else FC="'"+getSignosVitales().getFC()+"'";
                
                if(getSignosVitales().getFR().compareTo("")==0)FR="null";
                else FR="'"+getSignosVitales().getFR()+"'";
                
                if(getSignosVitales().getTemp().compareTo("")==0)Temp="null";
                else Temp="'"+getSignosVitales().getTemp()+"'";
               
                if(getSignosVitales().getNgOcular()==0)ocular="null";
                else ocular=getSignosVitales().getNgOcular()+"::smallint";
                
                if(getSignosVitales().getNgVerbal()==0)verbal="null";
                else verbal=getSignosVitales().getNgVerbal()+"::smallint";
                
                if(getSignosVitales().getNgMotora()==0)motora="null";
                else motora=getSignosVitales().getNgMotora()+"::smallint";
                
                if(getSignosVitales().getNgTotal()==0)total="null";
                else total=getSignosVitales().getNgTotal()+"::smallint";
                
                if(getAfePrincipal().getCIE10().getClaveCIE10().compareTo("")==0)afe1="null";
                else afe1="'"+getAfePrincipal().getCIE10().getClaveCIE10()+"'";
                
                if(getAfeSegunda().getCIE10().getClaveCIE10().compareTo("")==0)afe2="null";
                else afe2="'"+getAfeSegunda().getCIE10().getClaveCIE10()+"'";
               
                if(getAfeResAP().getCIE10().getClaveCIE10().compareTo("")==0)afe3="null";
                else afe3="'"+getAfeResAP().getCIE10().getClaveCIE10()+"'";
                
                if(getProceRe1().getCIE9().getClaveCIE9Urg().compareTo("")==0)proc1="null";
                else proc1="'"+getProceRe1().getCIE9().getClaveCIE9Urg()+"'";
                
                if(getProceRe2().getCIE9().getClaveCIE9Urg().compareTo("")==0)proc2="null";
                else proc2="'"+getProceRe2().getCIE9().getClaveCIE9Urg()+"'";
                
                if(getProceRe3().getCIE9().getClaveCIE9Urg().compareTo("")==0)proc3="null";
                else proc3="'"+getProceRe3().getCIE9().getClaveCIE9Urg()+"'";
                
                if(getMedicamento1().getMedicamento().claveMedi().compareTo("")==0)medCve1="null";
                else medCve1="'"+getMedicamento1().getMedicamento().claveMedi()+"'";
                
                if(getMedicamento1().getMedicamento().clavePres().compareTo("")==0)medPres1="null";
                else medPres1="'"+getMedicamento1().getMedicamento().clavePres()+"'";
                
                if(getMedicamento1().getMedicamento().claveCB().compareTo("")==0)medCod1="null";
                else medCod1="'"+getMedicamento1().getMedicamento().claveCB()+"'";
                
                if(getMedicamento2().getMedicamento().claveMedi().compareTo("")==0)medCve2="null";
                else medCve2="'"+getMedicamento2().getMedicamento().claveMedi()+"'";
                
                if(getMedicamento2().getMedicamento().clavePres().compareTo("")==0)medPres2="null";
                else medPres2="'"+getMedicamento2().getMedicamento().clavePres()+"'";
                
                if(getMedicamento2().getMedicamento().claveCB().compareTo("")==0)medCod2="null";
                else medCod2="'"+getMedicamento2().getMedicamento().claveCB()+"'";
                
                if(getMedicamento3().getMedicamento().claveMedi().compareTo("")==0)medCve3="null";
                else medCve3="'"+getMedicamento3().getMedicamento().claveMedi()+"'";
                
                if(getMedicamento3().getMedicamento().clavePres().compareTo("")==0)medPres3="null";
                else medPres3="'"+getMedicamento3().getMedicamento().clavePres()+"'";
                
                if(getMedicamento3().getMedicamento().claveCB().compareTo("")==0)medCod3="null";
                else medCod3="'"+getMedicamento3().getMedicamento().claveCB()+"'";
                
                if(getIRAS()=='S')
                    iras="'S'";
                else if(getIRAS()=='A')
                        iras="'A'";
                else iras="null";
                
                if(getEDAStr().compareTo("")==0)
                    edas="null";
                else
                    edas="'"+getEDAStr()+"'";
                
                if(getSobres()==0)
                    sobres="null";
                else
                    sobres=getSobres()+"::SMALLINT";
                    
                sQuery = "SELECT insertaNotaMedicaAdultos('"+sUsuario+"', "+getPaciente().getFolioPaciente()+"::bigint,'"+getTipoCamaUrgs()+
                                "',"+folioDefun+","+getPaciente().getPeso()+","+getPaciente().getTalla()+","+TA+","+FC+","+FR+","+Temp+",'"+getMotivoAtencion()+"','"+
                                getSignosVitales().getAspecto()+"',"+ocular+","+verbal+","+motora+","+total+","+afe1+","+afe2+","+afe3+","+proc1+","+proc2+","+proc3+
                                ","+medCve1+","+medCve2+","+medCve3+","+medPres1+","+medPres2+","+medPres3+","+medCod1+","+medCod2+","+medCod3+",'"+getDestinoUrg()+"','"+getTipoUrgs()+"','"+fechaSistema+horaSistema+"',"+
                        motivoConsulta+","+observaciones+",'"+getTriageColor()+"',"+iras+","+edas+","+sobres+");";
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


    /**
     * @return the sTriageColor
     */
    public String getTriageColor() {
        return sTriageColor;
    }

    /**
     * @param sTriageColor the sTriageColor to set
     */
    public void setTriageColor(String sTriageColor) {
        this.sTriageColor = sTriageColor;
    }

    /**
     * @return the sEDAStr
     */
    public String getEDAStr() {
        return sEDAStr;
    }

    /**
     * @param sEDAStr the sEDAStr to set
     */
    public void setEDAStr(String sEDAStr) {
        this.sEDAStr = sEDAStr;
    }
    
    
     //*******************BUSCA DATOS NOTA MEDICA LLENA**********************
    public void buscarNotaMedicaLlena() throws Exception{
        
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String v[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNotaMedicaLlena("+getPaciente().getFolioPaciente()+"::BIGINT"+","+getClaveEpisodio()+"::BIGINT);"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                getPaciente().setNombres((String) vRowTemp.get(0 ));
                                getPaciente().setApPaterno((String) vRowTemp.get( 1 ));
                                getPaciente().setApMaterno((String) vRowTemp.get( 2 ));
                                getPaciente().setSexoP((String)vRowTemp.get(3));
                                getPaciente().setCurp((String)vRowTemp.get(4));
                                v=vRowTemp.get(5).toString().split("-");
                                getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                                getPaciente().setFechaNac((Date)vRowTemp.get(5));
                                if(getPaciente().calculaEdad()!=null){
                                if(getPaciente().calculaEdad().compareTo("5 AÑOS")==0 || getPaciente().calculaEdad().compareTo("4 AÑOS")==0
                                        || getPaciente().calculaEdad().compareTo("3 AÑOS")==0 || getPaciente().calculaEdad().compareTo("2 AÑOS")==0
                                        || getPaciente().calculaEdad().compareTo("1 AÑO")==0 || getPaciente().calculaEdad().compareTo("1 AÑOS")==0
                                        || getPaciente().calculaEdad().compareTo("0 AÑO")==0 || getPaciente().calculaEdad().compareTo("0 AÑOS")==0
                                        || getPaciente().calculaEdad().contains("MESES")==true || getPaciente().calculaEdad().contains("MES")==true
                                        || getPaciente().calculaEdad().contains("DÍAS")==true || getPaciente().calculaEdad().contains("DÍA")==true)
                                    setEstadoHosp("");
                                }
                                
                                getPaciente().setCalleNum((String)vRowTemp.get(6));
                                getPaciente().setColonia((String)vRowTemp.get(7));
                                getPaciente().getEstado().setClaveEdo((String) vRowTemp.get( 8 ));
                                getPaciente().getMunicipio().setClaveMun((String) vRowTemp.get( 9 ));
                                getPaciente().getCiudad().setClaveCiu((String) vRowTemp.get( 10 ));
                                getPaciente().getCiudadCP().setCp((String)vRowTemp.get(11));
                                
                                if(vRowTemp.get(12).toString().compareTo("")==0)
                                     getPaciente().setPais("MÉXICO");
                                else
                                getPaciente().setPais((String)vRowTemp.get(12));
                                
                                getPaciente().getSeg().setNumero((String)vRowTemp.get(13));
                                getPaciente().getSeg().setDerechohabienteP((String)vRowTemp.get(14));
                                
                                getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(16));
                                getPaciente().getReferencia().setTipo((String)vRowTemp.get(17));
                                getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(19)).intValue());
                                getPaciente().setFolioDefuncion((String)vRowTemp.get(20));
                                getPaciente().setPeso(((Double)vRowTemp.get(21)).floatValue());
                                getPaciente().setTalla(((Double)vRowTemp.get(22)).floatValue());
                                setFolioAdmision(((Double)vRowTemp.get(23)).intValue());
                                SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                                setFechaIngreso(((Date)vRowTemp.get(24)));
                                setFechaIngresoStr(fechaIng.format(getFechaIngreso()));
                                
                                //***************************************************************************
                                setTipoCamaUrgs((String)vRowTemp.get(25));
                                getSignosVitales().setTA((String)vRowTemp.get(26));
                                getSignosVitales().setFC((String)vRowTemp.get(27));
                                getSignosVitales().setFR((String)vRowTemp.get(28));
                                getSignosVitales().setTemp((String)vRowTemp.get(29));
                                setMotivoAtencion((String)vRowTemp.get(30));
                                getSignosVitales().setAspecto((String)vRowTemp.get(31));
                                getSignosVitales().setNgOcular(((Double)vRowTemp.get(32)).intValue());
                                getSignosVitales().setNgVerbal(((Double)vRowTemp.get(33)).intValue());
                                getSignosVitales().setNgMotora(((Double)vRowTemp.get(34)).intValue());
                                getSignosVitales().setNgTotal(((Double)vRowTemp.get(35)).intValue());
                                setDestinoUrg((String)vRowTemp.get(36));
                                setTipoUrgs((String)vRowTemp.get(37));
                                setFechaAtencion(((Date)vRowTemp.get(38)));
                                setFechaCompleta(fechaIng.format(getFechaAtencion()));
                                setMotivoConsulta((String)vRowTemp.get(39));
                                setObservaciones((String)vRowTemp.get(40));
                                setTriageColor((String)vRowTemp.get(41));
                                if(((String)vRowTemp.get(42)).compareTo("")!=0)
                                    setIRAS(((String)vRowTemp.get(42)).charAt(0));
                                else
                                    setIRAS(' ');
                                if(((String)vRowTemp.get(43)).compareTo("")!=0)
                                    setEDAStr((String)vRowTemp.get(43));
                                else
                                    setEDAStr("");
                                if(((Double)vRowTemp.get(44)).intValue()!=0)
                                    setSobres(((Double)vRowTemp.get(44)).intValue());
                                else
                                    setSobres(0);
                            }
                            
                 }
	}
    
   //*******************BUSCA DATOS NOTA MEDICA LLENA DIAGNOSTICOS**********************
    public void buscarNotaMedicaLlenaDiag() throws Exception{
        
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String v[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNotaMedicaLlenaDiag("+getPaciente().getFolioPaciente()+"::BIGINT"+","+getClaveEpisodio()+"::BIGINT);"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                switch ((String) vRowTemp.get( 0 )) {
                                    case "T0621":
                                        this.getAfePrincipal().getCIE10().setDescripcionDiag((String) vRowTemp.get( 2 ));
                                        this.getAfePrincipal().getCIE10().setClave((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0622":
                                        this.getAfeSegunda().getCIE10().setDescripcionDiag((String) vRowTemp.get( 2 ));
                                        this.getAfeSegunda().getCIE10().setClave((String) vRowTemp.get( 1 ));
                                        break;
                                    case "T0623":
                                        this.getAfeResAP().getCIE10().setDescripcionDiag((String) vRowTemp.get( 2 ));
                                        this.getAfeResAP().getCIE10().setClave((String) vRowTemp.get( 1 ));
                                        break;
                                }
                            }
                        }
                            
                 }
	}

        
   //*******************BUSCA DATOS NOTA MEDICA LLENA PROCEDIMIENTOS**********************
    public void buscarNotaMedicaLlenaProc() throws Exception{
        
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String v[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNotaMedicaLlenaProc("+getPaciente().getFolioPaciente()+"::BIGINT"+","+getClaveEpisodio()+"::BIGINT);"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                if(((Double) vRowTemp.get( 0 )).intValue()==9){
                                    this.getProceRe1().getCIE9().setClave((String) vRowTemp.get( 1 ));
                                    this.getProceRe1().getCIE9().setDescripcionCompleta((String) vRowTemp.get( 2 ));
                                }
                                else if(((Double) vRowTemp.get( 0 )).intValue()==10){
                                    this.getProceRe2().getCIE9().setClave((String) vRowTemp.get( 1 ));
                                    this.getProceRe2().getCIE9().setDescripcionCompleta((String) vRowTemp.get( 2 ));
                                }
                                else if(((Double) vRowTemp.get( 0 )).intValue()==11){
                                    this.getProceRe3().getCIE9().setClave((String) vRowTemp.get( 1 ));
                                    this.getProceRe3().getCIE9().setDescripcionCompleta((String) vRowTemp.get( 2 ));
                                }
                            }
                        }
                            
                 }
	}
    
    
   //*******************BUSCA DATOS NOTA MEDICA LLENA MEDICAMENTOS**********************
    public void buscarNotaMedicaLlenaMed() throws Exception{
        
       // resetea2();
	ArrayList rst = null;
	String sQuery = "";
        String v[];
        FacesMessage message=null;
		 if( this==null){   //completar llave
			throw new Exception("NOTAMEDICA.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNotaMedicaLlenaMed("+getPaciente().getFolioPaciente()+"::BIGINT"+","+getClaveEpisodio()+"::BIGINT);"; 
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst.isEmpty()==false && rst.size() >= 1) {
                            for (Object rst1 : rst) {
                                ArrayList vRowTemp = (ArrayList) rst1;
                                if(((Double) vRowTemp.get( 0 )).intValue()==1)
                                    this.getMedicamento1().getMedicamento().setNombreCve((String) vRowTemp.get( 2 )+"~"+(String) vRowTemp.get( 1 ));
                                else if(((Double) vRowTemp.get( 0 )).intValue()==2)
                                    this.getMedicamento2().getMedicamento().setNombreCve((String) vRowTemp.get( 2 )+"~"+(String) vRowTemp.get( 1 ));
                                else if(((Double) vRowTemp.get( 0 )).intValue()==3)
                                    this.getMedicamento3().getMedicamento().setNombreCve((String) vRowTemp.get( 2 )+"~"+(String) vRowTemp.get( 1 ));
                            }
                        }
                            
                 }
    }
    
    //***************************BUSCA NOTAS MEDICAS LLENAS**************************************         
    public List<AdmisionUrgs> BuscaNotasLlenas(String sFechaI, String sFechaF) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[];      
        
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaNotasMedicas('"+sFechaI+"','"+sFechaF+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			//vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(0));
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(1));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(2));
                            v=vRowTemp.get(3).toString().split("-");
                            oAdmision.getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]);
                            oAdmision.getPaciente().setFechaNac((Date)vRowTemp.get(3));
                            oAdmision.setFolioAdmision(((Double)vRowTemp.get(4)).intValue());
                            
                            oAdmision.setFechaIngreso(((Date)vRowTemp.get(5)));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            oAdmision.setFechaIngresoStr(fechaIng.format(oAdmision.getFechaIngreso()));
                            
                            oAdmision.getPaciente().setFolioPaciente(((Double)vRowTemp.get(6)).longValue());
                            oAdmision.setClaveEpisodio(((Double)vRowTemp.get(7)).longValue());
                            
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}           

    //***************************BUSCA ACUMULADO DE PACIENTES**************************************         
    public List<AdmisionUrgs> BuscaAcumuladoPac(String año) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        nTotalPedia=0;
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaAcumuladoPacienteUrgs("+año+");"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(1));
                            oAdmision.setTotal(((Double)vRowTemp.get(2)).intValue());
                            oAdmision.setTotalHombres(((Double)vRowTemp.get(3)).intValue());
                            oAdmision.setTotalMujeres(((Double)vRowTemp.get(4)).intValue());
                            nTotalPedia=nTotalPedia+((Double)vRowTemp.get(2)).intValue();
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}           

 //***************************BUSCA PACIENTES REFERIDOS**************************************         
 public List<AdmisionUrgs> BuscaPacientesReferidos(String sFechaI, String sFechaF) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[];      
        totalMujeres=0;
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaPacientesRefHosp('"+sFechaI+"','"+sFechaF+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			//vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmision.getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(1));
                            oAdmision.setTotal(((Double)vRowTemp.get(2)).intValue());
                            oAdmision.setTotalHombres(((Double)vRowTemp.get(3)).intValue());
                            oAdmision.setTotalMujeres(((Double)vRowTemp.get(4)).intValue());
                            totalMujeres=totalMujeres+((Double)vRowTemp.get(2)).intValue();
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}           

 //***************************BUSCA TOTAL PACIENTES HOSPITALIZADOS POR MUNICIPIO**************************************         
 public List<AdmisionUrgs> BuscaPacientesPorMunicipio(String sFechaI, String sFechaF) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[];      
        totalMujeres=0;
        
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaPacientesPorMunicipio('"+sFechaI+"','"+sFechaF+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			//vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmision.getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(2));
                            oAdmision.setTotalMedicina(((Double)vRowTemp.get(3)).intValue());
                            oAdmision.setTotalGineco(((Double)vRowTemp.get(4)).intValue());
                            oAdmision.setTotalNeonato(((Double)vRowTemp.get(5)).intValue());
                            oAdmision.setTotalCirugia(((Double)vRowTemp.get(6)).intValue());
                            oAdmision.setTotalPedia(((Double)vRowTemp.get(7)).intValue());
                            oAdmision.setTotal(((Double)vRowTemp.get(8)).intValue());
                            totalMujeres=totalMujeres+((Double)vRowTemp.get(8)).intValue();
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}           

    //***************************BUSCA TOTAL PACIENTES HOSPITALIZADOS EN CADA SERVICIO**************************************         
 public List<AdmisionUrgs> BuscaTotalPacientesHosp() throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[];      
        nTotalPedia=0;
        
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaTotalesHosp();"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			//vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(1));
                            oAdmision.setTotalHombres(((Double)vRowTemp.get(2)).intValue());
                            oAdmision.setTotalMujeres(((Double)vRowTemp.get(3)).intValue());
                            oAdmision.setTotal(((Double)vRowTemp.get(4)).intValue());
                            nTotalPedia=nTotalPedia+((Double)vRowTemp.get(4)).intValue();
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}           
 
 //***************************BITÁCORA DE INGRESOS URGENICIAS**************************************         
 public List<AdmisionUrgs> BuscaBitacoraUrgs(String sFechaI, String sFechaF) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[];      
        
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM BitacoraUrgs('"+sFechaI+"','"+sFechaF+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			//vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmision.setTotal(((Double)vRowTemp.get(0)).intValue());
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(1));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(2));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(3));
                            oAdmision.setFechaIngreso(((Date)vRowTemp.get(4)));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            fechaIngresoStr=(fechaIng.format(oAdmision.getFechaIngreso()));
                            oAdmision.setFechaIngresoStr(fechaIngresoStr);
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(6));
                            oAdmision.getUsuario().getPersonal().setApPaterno((String)vRowTemp.get(7));
                            oAdmision.getUsuario().getPersonal().setApMaterno((String)vRowTemp.get(8));
                            oAdmision.getUsuario().getPersonal().setNombres((String)vRowTemp.get(9));
                            oAdmision.getUsuario().setIdUsuario((String)vRowTemp.get(10));
                            oAdmision.setFolioAdmision(((Double)vRowTemp.get(11)).intValue());
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}           

 //***************************RELACIÓN TIPO URGS**************************************         
 public List<AdmisionUrgs> BuscaTipoUrgs(String sFechaI, String sFechaF) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        totalMujeres=0;
        String v[]; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaRelacionTipoUrg('"+sFechaI+"','"+sFechaF+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			//vObj = new ArrayList<Hospitalizacion>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(1));
                            
                            if(((String)vRowTemp.get(2)).compareTo("T2601")==0)
                                oAdmision.setTipoUrgs("URGENCIA CALIFICADA");
                            else if(((String)vRowTemp.get(2)).compareTo("T2602")==0)
                                oAdmision.setTipoUrgs("URGENCIA NO CALIFICADA");
                            else
                                oAdmision.setTipoUrgs("APOYO A SERVICIOS DE MEDICINA GENERAL");
                            
                            oAdmision.setTotal(((Double)vRowTemp.get(3)).intValue()); 
                            totalMujeres=totalMujeres+((Double)vRowTemp.get(3)).intValue();
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}           

    public String getSemaforo() {
        String sSemaforo = "";
        
         if(getAltaHospitalaria()!=null)
                return sSemaforo="#9BFCA5";//Verde
            else if(oHospitalizacion.getNumIngresoHos()!=0)
                return sSemaforo = "#F36248";//Rojo
            else 
                return sSemaforo="#FCF762"; //amarillo
    }
 
    /**
     * @return the total
     */
    public int getTotal() {
        return total;
    }

    /**
     * @param total the total to set
     */
    public void setTotal(int total) {
        this.total = total;
    }

    /**
     * @return the totalMujeres
     */
    public int getTotalMujeres() {
        return totalMujeres;
    }

    /**
     * @param totalMujeres the totalMujeres to set
     */
    public void setTotalMujeres(int totalMujeres) {
        this.totalMujeres = totalMujeres;
    }

    /**
     * @return the totalHombres
     */
    public int getTotalHombres() {
        return totalHombres;
    }

    /**
     * @param totalHombres the totalHombres to set
     */
    public void setTotalHombres(int totalHombres) {
        this.totalHombres = totalHombres;
    }

    /**
     * @return the nTotalMedicina
     */
    public int getTotalMedicina() {
        return nTotalMedicina;
    }

    /**
     * @param nTotalMedicina the nTotalMedicina to set
     */
    public void setTotalMedicina(int nTotalMedicina) {
        this.nTotalMedicina = nTotalMedicina;
    }

    /**
     * @return the nTotalGineco
     */
    public int getTotalGineco() {
        return nTotalGineco;
    }

    /**
     * @param nTotalGineco the nTotalGineco to set
     */
    public void setTotalGineco(int nTotalGineco) {
        this.nTotalGineco = nTotalGineco;
    }

    /**
     * @return the nTotalNeonato
     */
    public int getTotalNeonato() {
        return nTotalNeonato;
    }

    /**
     * @param nTotalNeonato the nTotalNeonato to set
     */
    public void setTotalNeonato(int nTotalNeonato) {
        this.nTotalNeonato = nTotalNeonato;
    }

    /**
     * @return the nTotalCirugia
     */
    public int getTotalCirugia() {
        return nTotalCirugia;
    }

    /**
     * @param nTotalCirugia the nTotalCirugia to set
     */
    public void setTotalCirugia(int nTotalCirugia) {
        this.nTotalCirugia = nTotalCirugia;
    }

    /**
     * @return the nTotalPedia
     */
    public int getTotalPedia() {
        return nTotalPedia;
    }

    /**
     * @param nTotalPedia the nTotalPedia to set
     */
    public void setTotalPedia(int nTotalPedia) {
        this.nTotalPedia = nTotalPedia;
    }

    /**
     * @return the oUsuario
     */
    public Usuario getUsuario() {
        return oUsuario;
    }

    /**
     * @param oUsuario the oUsuario to set
     */
    public void setUsuario(Usuario oUsuario) {
        this.oUsuario = oUsuario;
    }
    
    
    //*********************************REPORTE DE ALTAS DIARIAS******************************************
 public List<AdmisionUrgs> BuscaReporteAltasDiarias(String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        totalMujeres=0;
        String v[], v2[]=null; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaReporteAltasDiarias('"+sFechaI+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(2));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(3));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(4));
                            oAdmision.getPaciente().getSeg().setNumero((String)vRowTemp.get(6));
                            oAdmision.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(7)).intValue());
                            if(((String)vRowTemp.get(9)).compareTo("")!=0)
                                oAdmision.setNivelSocioEcoStr((String)vRowTemp.get(9));
                            
                            oAdmision.getDiagIngreso().setClave((String)vRowTemp.get(10));
                            
                            if(((String)vRowTemp.get(23)).compareTo("T4602")==0 || ((String)vRowTemp.get(23)).compareTo("T4606")==0 
                                    ||((String)vRowTemp.get(23)).compareTo("T4607")==0 ||((String)vRowTemp.get(23)).compareTo("T4609")==0)
                            oAdmision.getDiagIngreso().getCauses().setClave((String)vRowTemp.get(11));
                           
                            oAdmision.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(12));
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(16));
                            
                            if(vRowTemp.get(17).toString().compareTo("T3801")==0)
                                oAdmision.setDestinoSTR("CUR");//CURACIÓN
                            else if(vRowTemp.get(17).toString().compareTo("T3802")==0)
                                oAdmision.setDestinoSTR("MEJ");//MEJORÍA
                            else if(vRowTemp.get(17).toString().compareTo("T3803")==0)
                                oAdmision.setDestinoSTR("VOL");//VOLUNTARIO
                            else if(vRowTemp.get(17).toString().compareTo("T3804")==0)
                                oAdmision.setDestinoSTR("PAS");//PASE A OTRO HOSPITAL
                            else if(vRowTemp.get(17).toString().compareTo("T3805")==0)
                                oAdmision.setDestinoSTR("DEF");//DEFUNCIÓN
                            else
                                oAdmision.setDestinoSTR("OTR");//OTRO
                            
                            v=vRowTemp.get(18).toString().split("-");
                            oAdmision.getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]); 
                            oAdmision.getPaciente().setEdadNumero(oAdmision.getPaciente().calculaEdad());
                            
                            oAdmision.setAltaHospitalaria((Date)vRowTemp.get(20));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("HH:mm:ss");
                            altaHospitalariaStr=(fechaIng.format(oAdmision.getAltaHospitalaria()));
                            oAdmision.setFechaAdm(altaHospitalariaStr);
                            oAdmision.setDocEntregada(vRowTemp.get(21).toString().charAt(0));
                            oAdmision.getCama().setNumero((String)vRowTemp.get(22));
                            
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
 //*********************************REPORTE DE EMISION DE ESTADOS DE SALUD******************************************
 public List<AdmisionUrgs> BuscaReporteEmisionEdoSalud(int area, String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "", diasEst="";
	int i=0;
        totalMujeres=0;
        String v[], v2[]=null; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscareporteemisionedossalud("+area+"::smallint,'"+sFechaI+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.getPaciente().getSeg().setNumero((String)vRowTemp.get(3));
                            oAdmision.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                            oAdmision.getCama().setNumero((String)vRowTemp.get(5));
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(6));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(7));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(8));
                            v=vRowTemp.get(9).toString().split("-");
                            oAdmision.getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]); 
                            oAdmision.getPaciente().setEdadNumero(oAdmision.getPaciente().calculaEdad());
                            oAdmision.getPaciente().setSexoP((String)vRowTemp.get(10));
                            oAdmision.getDiagIngreso().setClave((String)vRowTemp.get(11));
                            
                            if(((String)vRowTemp.get(21)).compareTo("T4602")==0 || ((String)vRowTemp.get(21)).compareTo("T4606")==0 
                                    ||((String)vRowTemp.get(21)).compareTo("T4607")==0 ||((String)vRowTemp.get(21)).compareTo("T4609")==0)
                            oAdmision.getDiagIngreso().getCauses().setClave((String)vRowTemp.get(12));
                            
                            oAdmision.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(13));
                            oAdmision.setFechaIngreso((Date)vRowTemp.get(14));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy");
                            fechaIngresoStr=(fechaIng.format(oAdmision.getFechaIngreso()));
                            oAdmision.setFechaAdm(fechaIngresoStr);
                            
                            oAdmision.getHospitalizacion().setFechaIngresoHos((Date)vRowTemp.get(15));
                            ingresoHosp=(fechaIng.format(oAdmision.getHospitalizacion().getFechaIngresoHos()));
                            oAdmision.setIngresoHosp(ingresoHosp);
                            
                            diasEst=(String)vRowTemp.get(16);
                                if(diasEst.compareTo("")!=0){       
                                if(diasEst.substring(0, 3).compareTo("000")!=0){
                                   if (diasEst.charAt(0)=='0')
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(1, 3)+" AÑOS");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(0, 3)+" AÑOS");
                            }else{
                                if (diasEst.substring(4, 6).compareTo("00")!=0)
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(4, 6)+" MESES");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(7, 9)+" DÍAS");
                                }
                                }                            
                           if(vRowTemp.get(17).toString().compareTo("")!=0){
                               if(((String)vRowTemp.get(17)).compareTo("T3900")==0) 
                                    oAdmision.setEdoSaludStr("GRAVE");
                               else if(((String)vRowTemp.get(17)).compareTo("T3901")==0) 
                                    oAdmision.setEdoSaludStr("DELICADO");
                               else if(((String)vRowTemp.get(17)).compareTo("T3902")==0) 
                                    oAdmision.setEdoSaludStr("ESTABLE");
                               else if(((String)vRowTemp.get(17)).compareTo("T3903")==0) 
                                    oAdmision.setEdoSaludStr("MEJORADO");
                               else oAdmision.setEdoSaludStr("");
                           }
                           
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(19));
                            oAdmision.setAreaServicioHRRBCad((String)vRowTemp.get(23));
                            oAdmision.setSubServicioHRRBSTR((String)vRowTemp.get(20));
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
//*********************************REPORTE DE EMISION DE ESTADOS DE SALUD******************************************
 public List<AdmisionUrgs> BuscaReporteEmisionEdoSaludHosp(int area, String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "", diasEst="";
	int i=0;
        totalMujeres=0;
        String v[], v2[]=null; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscareporteemisionedossaludAP("+area+"::smallint,'"+sFechaI+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.getPaciente().getSeg().setNumero((String)vRowTemp.get(3));
                            oAdmision.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                            oAdmision.getCama().setNumero((String)vRowTemp.get(5));
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(6));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(7));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(8));
                            v=vRowTemp.get(9).toString().split("-");
                            oAdmision.getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]); 
                            oAdmision.getPaciente().setEdadNumero(oAdmision.getPaciente().calculaEdad());
                            oAdmision.getPaciente().setSexoP((String)vRowTemp.get(10));
                            oAdmision.getDiagIngreso().setClave((String)vRowTemp.get(11));
                            
                            if(((String)vRowTemp.get(21)).compareTo("T4602")==0 || ((String)vRowTemp.get(21)).compareTo("T4606")==0 
                                    ||((String)vRowTemp.get(21)).compareTo("T4607")==0 ||((String)vRowTemp.get(21)).compareTo("T4609")==0)
                            oAdmision.getDiagIngreso().getCauses().setClave((String)vRowTemp.get(12));
                            
                            oAdmision.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(13));
                            
                            oAdmision.setFechaIngreso((Date)vRowTemp.get(14));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy");
                            fechaIngresoStr=(fechaIng.format(oAdmision.getFechaIngreso()));
                            oAdmision.setFechaAdm(fechaIngresoStr);
                            
                            oAdmision.getHospitalizacion().setFechaIngresoHos((Date)vRowTemp.get(15));
                            ingresoHosp=(fechaIng.format(oAdmision.getHospitalizacion().getFechaIngresoHos()));
                            oAdmision.setIngresoHosp(ingresoHosp);
                            
                            diasEst=(String)vRowTemp.get(16);
                                if(diasEst.compareTo("")!=0){       
                                if(diasEst.substring(0, 3).compareTo("000")!=0){
                                   if (diasEst.charAt(0)=='0')
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(1, 3)+" AÑOS");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(0, 3)+" AÑOS");
                            }else{
                                if (diasEst.substring(4, 6).compareTo("00")!=0)
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(4, 6)+" MESES");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(7, 9)+" DÍAS");
                                }
                                }                            
                           if(vRowTemp.get(17).toString().compareTo("")!=0){
                               if(((String)vRowTemp.get(17)).compareTo("T3900")==0) 
                                    oAdmision.setEdoSaludStr("GRAVE");
                               else if(((String)vRowTemp.get(17)).compareTo("T3901")==0) 
                                    oAdmision.setEdoSaludStr("DELICADO");
                               else if(((String)vRowTemp.get(17)).compareTo("T3902")==0) 
                                    oAdmision.setEdoSaludStr("ESTABLE");
                               else if(((String)vRowTemp.get(17)).compareTo("T3903")==0) 
                                    oAdmision.setEdoSaludStr("MEJORADO");
                               else oAdmision.setEdoSaludStr("");
                           }
                           
                            oAdmision.setAreaServicioHRRBCad((String)vRowTemp.get(19));
                            oAdmision.setSubServicioHRRBSTR((String)vRowTemp.get(20));
                            
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
  
//*********************************BUSCA EPISODIOS MEDICOS ABIERTOS******************************************
 public List<AdmisionUrgs> BuscaEpisodiosAbiertos(long nfoliopac) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "", diasEst="";
	int i=0;
        totalMujeres=0;
        String v[], v2[]=null; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaEpisodiosAbiertos("+nfoliopac+"::bigint);"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                            oAdmision.getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
        //*********************************REPORTE DE HOSPITALIZACION POR DIA******************************************
 public List<AdmisionUrgs> buscareportehospipordia(int area, String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[], diasEst; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaReporteHospiPorDia("+area+"::smallint,'"+sFechaI+"');"; 
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.getHospitalizacion().setNumIngresoHos(((Double)vRowTemp.get(2)).longValue());
                            oAdmision.getPaciente().getSeg().setNumero((String)vRowTemp.get(3));
                            oAdmision.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(5));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(6));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(7));
                            oAdmision.getDiagIngreso().setClave((String)vRowTemp.get(8));
                            if(((String)vRowTemp.get(21)).compareTo("T4602")==0 || ((String)vRowTemp.get(21)).compareTo("T4606")==0 
                                    ||((String)vRowTemp.get(21)).compareTo("T4607")==0 ||((String)vRowTemp.get(21)).compareTo("T4609")==0)
                            oAdmision.getDiagIngreso().getCauses().setClave((String)vRowTemp.get(9));
                            
                            oAdmision.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(10));
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(12));
                            oAdmision.getCama().setNumero((String)vRowTemp.get(13));
                            oAdmision.setFechaIngreso((Date)vRowTemp.get(14));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("HH:mm:ss");
                            fechaIngresoStr=(fechaIng.format(oAdmision.getFechaIngreso()));
                            oAdmision.setFechaIngresoStr(fechaIngresoStr);
                            diasEst=(String)vRowTemp.get(15);
                                if(diasEst.compareTo("")!=0){       
                                if(diasEst.substring(0, 3).compareTo("000")!=0){
                                   if (diasEst.charAt(0)=='0')
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(1, 3)+" AÑOS");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(0, 3)+" AÑOS");
                            }else{
                                if (diasEst.substring(4, 6).compareTo("00")!=0)
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(4, 6)+" MESES");
                                else
                                    oAdmision.setDiasEstanciaSTR(diasEst.substring(7, 9)+" DÍAS");
                                }
                                }
                            
                            oAdmision.getMedicoRecibe().setNombres((String)vRowTemp.get(17));
                            
                           if(vRowTemp.get(18).toString().compareTo("")!=0){
                               if(((String)vRowTemp.get(18)).compareTo("T3900")==0) 
                                    oAdmision.setEdoSaludStr("GRAVE");
                               else if(((String)vRowTemp.get(18)).compareTo("T3901")==0) 
                                    oAdmision.setEdoSaludStr("DELICADO");
                               else if(((String)vRowTemp.get(18)).compareTo("T3902")==0) 
                                    oAdmision.setEdoSaludStr("ESTABLE");
                               else if(((String)vRowTemp.get(18)).compareTo("T3903")==0) 
                                    oAdmision.setEdoSaludStr("MEJORADO");
                               else oAdmision.setEdoSaludStr("");
                           }
                           if(((String)vRowTemp.get(19)).compareTo("")!=0)
                           oAdmision.setNivelSocioEcoStr((String)vRowTemp.get(19));
                            oAdmision.setFolioAdmision(((Double)vRowTemp.get(20)).intValue());
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
 public List<AdmisionUrgs> buscareportePorTurno1(int turno, int area, String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[], diasEst; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscareporteturnotab1('"+sFechaI+"',"+turno+"::smallint,"+area+"::smallint);"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.setSubServicioHRRB(((Double)vRowTemp.get(0)).intValue());//Serial
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(3));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(4));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(5));
                            oAdmision.getPaciente().getSeg().setNumero((String)vRowTemp.get(6));
                            oAdmision.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(7)).intValue());
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(9));
                            
                            v=vRowTemp.get(10).toString().split("-");
                            oAdmision.getPaciente().setFechaNacTexto(v[2]+"/"+v[1]+"/"+v[0]); 
                            oAdmision.getPaciente().setEdadNumero(oAdmision.getPaciente().calculaEdad());
                            oAdmision.setFechaIngreso((Date)vRowTemp.get(12));
                            SimpleDateFormat fechaIng = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
                            fechaIngresoStr=(fechaIng.format(oAdmision.getFechaIngreso()));
                            oAdmision.setFechaIngresoStr(fechaIngresoStr);
                            
                            oAdmision.getMedicoRecibe().setNombres((String)vRowTemp.get(14));
                            if(vRowTemp.get(15).toString().compareTo("T3700")==0)
                                oAdmision.setDestinoSTR("DOMICILIO");
                            else if(vRowTemp.get(15).toString().compareTo("T3701")==0)
                                oAdmision.setDestinoSTR("CENTRO DE SALUD");
                            else if(vRowTemp.get(15).toString().compareTo("T3702")==0)
                                oAdmision.setDestinoSTR("TERCER NIVEL");
                            else if(vRowTemp.get(15).toString().compareTo("T3703")==0)
                                oAdmision.setDestinoSTR("CONSULTA EXTERNA");
                            else if(vRowTemp.get(15).toString().compareTo("T3704")==0)
                                oAdmision.setDestinoSTR("ESPECIALIDAD");
                            else if(vRowTemp.get(15).toString().compareTo("T3705")==0)
                                oAdmision.setDestinoSTR("OTRO");
                            else 
                                oAdmision.setDestinoSTR("PENDIENTE"); 
                            
                            
                            if(((Double)(vRowTemp.get(17))).intValue()!=0 && vRowTemp.get(17)!=null)
                                oAdmision.setDestinoSTR("HOSPITALIZACIÓN");
                            
                            oAdmision.setFolioAdmision(((Double)vRowTemp.get(16)).intValue());
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
        
 public List<AdmisionUrgs> buscareportePorTurno2(int turno, int area, String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[], diasEst; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscareporteturnotab2('"+sFechaI+"',"+turno+"::smallint,"+area+"::smallint);"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.setTotal(((Double)vRowTemp.get(0)).intValue());//ingresos
                            oAdmision.setTotalHombres(((Double)vRowTemp.get(1)).intValue());//domiciliados
                            oAdmision.setTotalMujeres(((Double)vRowTemp.get(2)).intValue());//hospitalizados
                            oAdmision.setTotalMedicina(oAdmision.getTotal()-(oAdmision.getTotalHombres()+oAdmision.getTotalMujeres()));//pendientes
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
 public List<AdmisionUrgs> buscareportePorTurno3(int turno, int area, String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[], diasEst; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscareporteturnotab3('"+sFechaI+"',"+turno+"::smallint,"+area+"::smallint);"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.setTotalHombres(((Double)vRowTemp.get(0)).intValue());//curacion
                            oAdmision.setTotalMujeres(((Double)vRowTemp.get(1)).intValue());//mejoria
                            oAdmision.setTotalCirugia(((Double)vRowTemp.get(2)).intValue());//voluntario
                            oAdmision.setTotalGineco(((Double)vRowTemp.get(3)).intValue());//pase
                            oAdmision.setTotalNeonato(((Double)vRowTemp.get(4)).intValue());//defuncion
                            oAdmision.setTotalPedia(((Double)vRowTemp.get(5)).intValue());//otro
                            oAdmision.setTotalMedicina(oAdmision.getTotalHombres()+oAdmision.getTotalMujeres()+oAdmision.getTotalCirugia()+oAdmision.getTotalGineco()+oAdmision.getTotalPedia());//total
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
 public List<AdmisionUrgs> buscareportePorTurno4(int turno, int area, String sFechaI) throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        String v[], diasEst; 
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscareporteturnotab4('"+sFechaI+"',"+turno+"::smallint,"+area+"::smallint);"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.setAreaServicioHRRB((String)vRowTemp.get(1));
                            oAdmision.setTotalMujeres(((Double)vRowTemp.get(2)).intValue());//Ingresos
                            oAdmision.setTotalHombres(((Double)vRowTemp.get(3)).intValue());//Egresos
                            oAdmision.setTotal(((Double)vRowTemp.get(4)).intValue());//Inicial
                            oAdmision.setTotalCirugia(oAdmision.getTotal()-oAdmision.getTotalMujeres()+oAdmision.getTotalHombres());
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
        
 public List<AdmisionUrgs> buscaUsuarioUrgencias() throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        List<AdmisionUrgs> lAdmisionUrgs=null;
            
		sQuery = "SELECT * FROM buscaUsuarioUrgs('"+sUsuario+"');"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
                    lAdmisionUrgs = new ArrayList<AdmisionUrgs>();
			for (i = 0; i < rst.size(); i++) {
                            oAdmision = new AdmisionUrgs();
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAdmision.getMedicoTratante().setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                            oAdmision.getPaciente().setApPaterno((String)vRowTemp.get(1));
                            oAdmision.getPaciente().setApMaterno((String)vRowTemp.get(2));
                            oAdmision.getPaciente().setNombres((String)vRowTemp.get(3));
                            lAdmisionUrgs.add(oAdmision);
                        } 
                }
                
		return lAdmisionUrgs; 
	}
 
 public void buscaDatosMedicoUrg() throws Exception{
        AdmisionUrgs oAdmision=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
            
		sQuery = "SELECT * FROM buscaMedicoReporteUrg('"+sUsuario+"');"; 
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null || rst.isEmpty()!=true) {
			for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            getMedicoRecibe().setApPaterno((String)vRowTemp.get(0));
                            getMedicoRecibe().setApMaterno((String)vRowTemp.get(1));
                            getMedicoRecibe().setNombres((String)vRowTemp.get(2));
                            getMedicoRecibe().setCedProf((String)vRowTemp.get(3));
                          
                        } 
                }
                
	}
 
           
        //Retorna lista de SubServicios de Hospitalizacion
     public List<AreaServicioHRRB> getListaSubServicioHosp(){
        List<AreaServicioHRRB> lLista = null;
       try {
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarSubServiciosHosp(getAreaServicioHRRBSTR())));
       } catch (Exception ex) {
           ex.printStackTrace();
       }
        return lLista;
    }
     //Retorna lista de Servicios de Hospitalizacion
     public List<AreaServicioHRRB> getListaAreasHosp() throws Exception{
        List<AreaServicioHRRB> lLista = null;
       
           lLista = new ArrayList<AreaServicioHRRB>(Arrays.asList(
                   (new AreaServicioHRRB()).buscarAreasHosp(getPaciente().getSexoP())));
       
        return lLista;
    }
    public AdmisionUrgs[] buscaHistorialNotaMedicaPrimerContacto(long foliopac) throws Exception{
        AdmisionUrgs oAdmision=null, lAdmisionUrgs[]=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        if(foliopac==0){
          throw new Exception("AdmisionUrgs.buscaHistorialNotaMedicaPrimerContacto: Error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialnotamedicaprimercontacto("+foliopac+"::BIGINT);"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                lAdmisionUrgs = new AdmisionUrgs[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oAdmision = new AdmisionUrgs();
                    oAdmision.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oAdmision.setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oAdmision.setFolioAdmision(((Double)vRowTemp.get(2)).intValue());
                    oAdmision.setFechaAtencion((Date)vRowTemp.get(3));
                    oAdmision.setTipoUrgs((String)vRowTemp.get(4));
                    oAdmision.setMotivoAtencion((String)vRowTemp.get(5));
                    oAdmision.getArea().setDescripcion((String)vRowTemp.get(6));
                    lAdmisionUrgs[i]=oAdmision;
                } 
            }
        }
        return lAdmisionUrgs; 
    }
    

    public Parametrizacion getTipCamUrgs() {
        return oTipCamUrgs;
    }

    public void setTipCamUrgs(Parametrizacion oTipCamUrgs) {
        this.oTipCamUrgs = oTipCamUrgs;
    }
} 
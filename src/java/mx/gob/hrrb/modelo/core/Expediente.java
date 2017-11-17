package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;
import org.primefaces.context.RequestContext;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Expediente implements Serializable{
	private AccesoDatos oAD;
	private Date dFechaApertura;
	private int nNumero;
        private int nNumero2;
	private AreaServicioHRRB oServicioIngreso;
	private Programa oPrograma;
	private Paciente oPaciente;
	private AntecGinecoObstetricos oAntecGinecoObstetricos;
	private AntecPerinatales oAntecPerinatales;
	private AntecPatologicos oAntecPatologicos;
	private AntecNoPatologicos oAntecNoPatologicos;
	private AntecHeredoFamiliares oAntecHeredoFamiliares;
	private ArrayList arrEpisodioMedico;
	private boolean binserta;
        private String sStatusExpediente;
        private int nNumeroAux;
        //AGREGADO POR DANIEL HERNANDEZ SANCHEZ        
        private String sRutaIne;//nomArch
        private Parametrizacion oLugarApertura;
        private Date dFechaDepuracion;
        private boolean bDepurado = false;
        private boolean bAperturaExp = false;
        public final static String RUTA_INE="/resources/docPac";
        private String sUsuFirm;
        /**************************************************/
	public Expediente(){
            oServicioIngreso=new AreaServicioHRRB();            
            oLugarApertura=new Parametrizacion();
            binserta=false;
            nNumero=0;
            nNumero2=0;
            oAntecNoPatologicos = new AntecNoPatologicos();
            oAntecHeredoFamiliares = new AntecHeredoFamiliares();
            oAntecPatologicos = new AntecPatologicos();
            oAntecGinecoObstetricos = new AntecGinecoObstetricos();
            oPrograma = new Programa();
            HttpServletRequest req;
                    req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                    if (req.getSession().getAttribute("oFirm") != null) {
                            sUsuFirm = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
                    }
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this.nNumero==0){   //completar llave
			throw new Exception("Expediente.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveExpediente("+this.nNumero+");"; 
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
        //************************************************************
        public int buscaExpPac(long folio) throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        int i=0;
        int valor=0;
             
		sQuery = "select * from buscaExpxPac("+folio+");"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            valor=((Double)vRowTemp.get(0)).intValue();

			}
		} 
		return valor; 
	}
        //************************************************************
	public Expediente[] buscarTodos() throws Exception{
	Expediente arrRet[]=null, oExpediente=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosExpediente();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Expediente[rst.size()];
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
			throw new Exception("Expediente.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaExpediente();"; 
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
			throw new Exception("Expediente.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaExpediente();"; 
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
			throw new Exception("Expediente.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaExpediente();"; 
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
	public Date getFechaApertura() {
	return dFechaApertura;
	}

	public void setFechaApertura(Date valor) {
	dFechaApertura=valor;
	}

	public int getNumero() {
	return nNumero;
	}

	public void setNumero(int valor) throws Exception{
	nNumero=valor;
        ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        FacesMessage message=null;
        if(getNumero()!=0 && binserta==true){
            sQuery = "SELECT buscaNoExp("+getNumero()+");";
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
                message = new FacesMessage(FacesMessage.SEVERITY_ERROR,"Abrir expediente", "El Numero de Expediente ya ha sido asignado");
                RequestContext.getCurrentInstance().showMessageInDialog(message);
                setNumero(0);
            }
        }
	}
        
	//*******************************************************************
        public boolean buscarExpedientePaciente(int numero, long folio) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Expediente.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaExpedientePac("+numero+", "+folio+"::bigint);";
                        //System.out.println(sQuery);
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
        
        public int insertaVariosExp(ArrayList<Expediente> arrExp)throws Exception{
            int nRet = 0;
            ArrayList rstQuery=null;
            String sQuery="";
            if(arrExp==null)
                throw new Exception("Expediente.insertaVariossExp:ERROR, FALTANDATOS");
            else{
                rstQuery=new ArrayList();
                for (Expediente arrExp1 : arrExp) {
                    sQuery = "SELECT * FROM insertaExpedientePendientePorAbrir('"+sUsuFirm+"'," + arrExp1.nNumero + " , '" + arrExp1.getLugarApertura().getTipoParametro() + "', '1');";
                    rstQuery.add(sQuery);
                }
                if(rstQuery.size()>0){
                    oAD=new AccesoDatos();
                    if(oAD.conectar()){
                        nRet=oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();
                    }
                }
            }
            return nRet;
        }
        /*
        * METODO CREADO POR DANIEL HERNANDEZ SANCHEZ 3 FASE
        */
        public Expediente[] buscaExpPendientesPorAbrir(String sLugarAper)throws Exception{
            Expediente arrExp[]= null, oExpediente=null;
            ArrayList rst= null;
            String sQuery="";
            int i=0;
            sQuery="SELECT * FROM buscaexppendientesporabrir('"+sLugarAper+"');";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrExp= new Expediente[rst.size()];
                for(i = 0; i < rst.size(); i++){
                    oExpediente=new Expediente();
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oExpediente.setNumero(((Double)vRowTemp.get(0)).intValue());
                    arrExp[i]=oExpediente;
                }
            }
            return arrExp;
        }
        
        
        public EpisodioMedico[] buscaDatosExpParaDepurar()throws Exception{
            EpisodioMedico arrEM[] = null, oEM=null;
            SimpleDateFormat edad=new SimpleDateFormat("dd/MM/yyyy");
            ArrayList rst=null;
            String sQuery;
            int i=0;
                sQuery = "SELECT * FROM buscaExpParaDepurar();";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null){
                    arrEM = new EpisodioMedico[rst.size()];
                    for(i=0;i<rst.size();i++){
                        oEM=new EpisodioMedico();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oEM.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());  
                        oEM.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(1)).intValue());
                        oEM.setFechaIngreso((Date)vRowTemp.get(2));
                        oEM.getPaciente().setNombres((String)vRowTemp.get(3));
                        oEM.getPaciente().setApPaterno((String)vRowTemp.get(4));
                        oEM.getPaciente().setApMaterno((String)vRowTemp.get(5));
                        oEM.getPaciente().setCurp((String)vRowTemp.get(6));//vacio
                        oEM.getPaciente().setFechaNac((Date)vRowTemp.get(7));
                        oEM.getPaciente().setSexoP((String)vRowTemp.get(8));
                        oEM.getPaciente().getEstado().setDescripcionEdo((String)vRowTemp.get(9));
                        oEM.getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(10));
                        oEM.getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(11));
                        oEM.getPaciente().getExpediente().setFechaApertura((Date)vRowTemp.get(12));
                        //oEM.getPaciente().setCalleNum((String)vRowTemp.get(7));
                        //oPac.setEdoCivilStr((String)vRowTemp.get(7));
                        //oPac.setColonia((String)vRowTemp.get(9));                        
                        //oEM.setClaveEpisodio(((Double)vRowTemp.get(11)).intValue());
                        //oEM.setAdmisionUrgs(new AdmisionUrgs());
                        //oEM.getAdmisionUrgs().setFolioAdmision(((Double)vRowTemp.get(12)).intValue());
                        //oEM.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(13));
                        //oEM.getPaciente().setParametrizacion(new Parametrizacion());
                        //oEM.getPaciente().getParametrizacion().setValor((String)vRowTemp.get(14));
                        //oEM.setFechaIngreso((Date)vRowTemp.get(15));
                        //oEM.setAltaHospitalaria((Date)vRowTemp.get(16));
                        String convierte = edad.format(oEM.getPaciente().getFechaNac());
                        oEM.getPaciente().setFechaNacTexto(convierte);
                        oEM.getPaciente().calculaEdad();
                        arrEM[i]=oEM;
                    }
                }
                return arrEM;
        }
        
        public EpisodioMedico[] buscaExpedientesDepurados()throws Exception{
            EpisodioMedico arrEM[] = null, oEM=null;
            SimpleDateFormat edad=new SimpleDateFormat("dd/MM/yyyy");
            ArrayList rst=null;
            String sQuery;
            int i=0;
                sQuery = "SELECT * FROM buscaExpDepurados();";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null){
                    arrEM = new EpisodioMedico[rst.size()];
                    for(i=0;i<rst.size();i++){
                        oEM=new EpisodioMedico();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oEM.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());  
                        oEM.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(1)).intValue());
                        oEM.setFechaIngreso((Date)vRowTemp.get(2));
                        oEM.getPaciente().setNombres((String)vRowTemp.get(3));
                        oEM.getPaciente().setApPaterno((String)vRowTemp.get(4));
                        oEM.getPaciente().setApMaterno((String)vRowTemp.get(5));
                        oEM.getPaciente().setCurp((String)vRowTemp.get(6));//vacio
                        oEM.getPaciente().setFechaNac((Date)vRowTemp.get(7));
                        oEM.getPaciente().setSexoP((String)vRowTemp.get(8));
                        oEM.getPaciente().getExpediente().setFechaApertura((Date)vRowTemp.get(9));
                        oEM.getPaciente().getExpediente().setFechaDepuracion((Date)vRowTemp.get(10));
                        String convierte = edad.format(oEM.getPaciente().getFechaNac());
                        oEM.getPaciente().setFechaNacTexto(convierte);
                        oEM.getPaciente().calculaEdad();
                        arrEM[i]=oEM;
                    }
                }
                return arrEM;
        }
        
        
        public int insertaDepuracionExp()throws Exception{
            ArrayList rst= null;
            int nReg=0;
            String sQuery="";
            if(this==null){
                throw new Exception("Expediente.Insercion:error de programación, faltan datos");
            }else{
                sQuery="SELECT * FROM registraDepuracionExp('"+sUsuFirm+"',"+this.nNumero2+");";
                //System.out.println(sQuery);
                oAD= new AccesoDatos();
                if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar(); 
                    if (rst != null && rst.size() == 1) {
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        nReg = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return nReg;
        }
        
        public EpisodioMedico[] buscaExpedientesDepuradosPorCriterio(int nAño)throws Exception{
            EpisodioMedico arrEM[] = null, oEM=null;
            SimpleDateFormat edad=new SimpleDateFormat("dd/MM/yyyy");
            ArrayList rst=null;
            String sQuery;
            int i=0, año=0;
                sQuery = "SELECT * FROM buscaExpDepuradosPorCriterio("+nAño+");";
                //System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null){
                    arrEM = new EpisodioMedico[rst.size()];
                    for(i=0;i<rst.size();i++){
                        oEM=new EpisodioMedico();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oEM.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());  
                        oEM.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(1)).intValue());
                        oEM.setFechaIngreso((Date)vRowTemp.get(2));
                        oEM.getPaciente().setNombres((String)vRowTemp.get(3));
                        oEM.getPaciente().setApPaterno((String)vRowTemp.get(4));
                        oEM.getPaciente().setApMaterno((String)vRowTemp.get(5));
                        oEM.getPaciente().setCurp((String)vRowTemp.get(6));//vacio
                        oEM.getPaciente().setFechaNac((Date)vRowTemp.get(7));
                        oEM.getPaciente().setSexoP((String)vRowTemp.get(8));
                        oEM.getPaciente().getExpediente().setFechaApertura((Date)vRowTemp.get(9));
                        oEM.getPaciente().getExpediente().setFechaDepuracion((Date)vRowTemp.get(10));
                        String convierte = edad.format(oEM.getPaciente().getFechaNac());
                        oEM.getPaciente().setFechaNacTexto(convierte);
                        oEM.getPaciente().calculaEdad();
                        arrEM[i]=oEM;
                    }
                }
                return arrEM;
        }
    
     public void buscaEstatusExpedienteBD(long foliopa, long nnumexp)throws Exception{
            ArrayList rst= null;
            String sQuery="";
                sQuery="SELECT * FROM buscaStatusExp("+foliopa+","+nnumexp+");";
                oAD= new AccesoDatos();
                if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar(); 
                    if (!rst.isEmpty()) {
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        setStatusExpediente((String)vRowTemp.get(0));
                    }
                }
            
        }    
    
    //******************************************************************************************************************
        
	public AreaServicioHRRB getServicioIngreso() {
	return oServicioIngreso;
	}

	public void setServicioIngreso(AreaServicioHRRB valor) {
	oServicioIngreso=valor;
	}

	public Programa getPrograma() {
	return oPrograma;
	}

	public void setPrograma(Programa valor) {
	oPrograma=valor;
	}

	public Paciente getPaciente() {
	return oPaciente;
	}

	public void setPaciente(Paciente valor) {
	oPaciente=valor;
	}

	public AntecGinecoObstetricos getAntecGinecoObstetricos() {
	return oAntecGinecoObstetricos;
	}

	public void setAntecGinecoObstetricos(AntecGinecoObstetricos valor) {
	oAntecGinecoObstetricos=valor;
	}

	public AntecPerinatales getAntecPerinatales() {
	return oAntecPerinatales;
	}

	public void setAntecPerinatales(AntecPerinatales valor) {
	oAntecPerinatales=valor;
	}

	public AntecPatologicos getAntecPatologicos() {
	return oAntecPatologicos;
	}

	public void setAntecPatologicos(AntecPatologicos valor) {
	oAntecPatologicos=valor;
	}

	public AntecNoPatologicos getAntecNoPatologicos() {
	return oAntecNoPatologicos;
	}

	public void setAntecNoPatologicos(AntecNoPatologicos valor) {
	oAntecNoPatologicos=valor;
	}

	public AntecHeredoFamiliares getAntecHeredoFamiliares() {
	return oAntecHeredoFamiliares;
	}

	public void setAntecHeredoFamiliares(AntecHeredoFamiliares valor) {
	oAntecHeredoFamiliares=valor;
	}

	public ArrayList getrrEpisodioMedico() {
	return arrEpisodioMedico;
	}

	public void setrrEpisodioMedico(ArrayList valor) {
	arrEpisodioMedico=valor;
	}

	public boolean getInserta() {
        return binserta;
    }

    public void setInserta(boolean binserta) {
        this.binserta = binserta;
    }

    public String getStatusExpediente() {
        return sStatusExpediente;
    }

    public void setStatusExpediente(String  valor) {
        sStatusExpediente = valor;
    }

    public int getNumeroAux() {
        return nNumeroAux;
    }

    public void setNumeroAux(int nNumeroAux) {
        this.nNumeroAux = nNumeroAux;
    }
    
    public String getNomArchINE(){return sRutaIne;}
    public String getRutaINE(){return RUTA_INE+"/"+sRutaIne;}
    public void setRutaINE(String sRuta){this.sRutaIne=sRuta;}
    
    public Parametrizacion getLugarApertura(){return oLugarApertura;}
    public void setLugarApertura(Parametrizacion valor){oLugarApertura=valor;}
    
    public Date getFechaDepuracion(){return dFechaDepuracion;}
    public void setFechaDepuracion(Date valor){dFechaDepuracion=valor;}
    
    public boolean getDepurado(){return bDepurado;}
    public void setDepurado(boolean valor){bDepurado=valor;}
    
    public boolean getAperturaExp(){return bAperturaExp;}
    public void setAperturaExp(boolean valor){bAperturaExp=valor;}

    /**
     * @return the nNumero2
     */
    public int getNumero2() {
        return nNumero2;
    }

    /**
     * @param nNumero2 the nNumero2 to set
     */
    public void setNumero2(int nNumero2) {
        this.nNumero2 = nNumero2;
    }
} 


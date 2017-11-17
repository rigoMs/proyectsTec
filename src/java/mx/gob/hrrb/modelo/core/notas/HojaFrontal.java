package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;

/**
 * Objetivo: Lograr el control completo del formato de la Hoja Frontal
 * @author : Alberto
 * @version: 1.0
*/

public  class HojaFrontal implements Serializable{        
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private String sPlan;
        private int nInterconsultas;
        private String sclavediagnostico;
        private Hospitalizacion oHospitalizacion;
	private EpisodioMedico oEpisodioMedico;
        private DateFormat edad = new SimpleDateFormat("dd/MM/yyyy"); 

	public HojaFrontal(){
        sUsuarioFirmado = "ALBER57";
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oEpisodioMedico = new EpisodioMedico();
                oHospitalizacion = new Hospitalizacion();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HojaFrontal.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveHojaFrontal();"; 
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
	public HojaFrontal[] buscarTodos() throws Exception{
	HojaFrontal arrRet[]=null, oHojaFrontal=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosHojaFrontal();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new HojaFrontal[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("HojaFrontal.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHojaFrontal('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("HojaFrontal.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHojaFrontal('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("HojaFrontal.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHojaFrontal('"+sUsuarioFirmado+"');"; 
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
        //***********************************************************************************************
        public void buscaDatosEgreso()throws Exception{
            ArrayList  rst = null;
            String sQuery = "";
            int i = 0;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("buscadatosegresohojafrontal:noesposibleprocesarlainformacion");
            else{
                sQuery = "SELECT * FROM consultainformacionsecundariahojafrontal("+ this.getEpisodioMedico().getPaciente().getFolioPaciente()+"::BIGINT, "+
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() +"::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    this.setInterconsultas(((Double)vRowTemp.get(0)).intValue());
                    this.getHospitalizacion().setDiagnosticosEgreso((String)vRowTemp.get(1).toString());
                    this.getEpisodioMedico().setAltaFisica((Date)vRowTemp.get(2));
                    this.getEpisodioMedico().getProceRe1().getCIE9().setDescripcion((String)vRowTemp.get(3).toString());
                }
            }
        }
        public void buscaDatosHojaFrontal()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            int i = 0;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("buscadatosinsertados:noesposibleprocesarlainformacion");
            else{
                sQuery = "SELECT * FROM  buscadatosinsertadosfrontal("+ this.getEpisodioMedico().getPaciente().getFolioPaciente() +"::BIGINT,"+
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() +"::BIGINT);";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    this.setPlan((String)vRowTemp.get(0).toString());
                    this.setClaveDiagnostico((String)vRowTemp.get(1).toString() + " " + (String)vRowTemp.get(2).toString());
                }else{
                    this.setPlan("");
                    this.setClaveDiagnostico("");
                }
            }
        }
        public EpisodioMedico buscaPaciente(long indice)throws Exception{
            EpisodioMedico ep = null;
            ArrayList rst=null;
            String sQuery;
            int i=0;
            if(indice==0)
                throw new Exception("NO ES POSIBLE PROCESAR INFORMACION");
            else{
                sQuery="SELECT * FROM buscaPacienteFrontal("+indice+"::BIGINT);";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();                    
                }
                if(rst.isEmpty())                    
                    return ep = null;
                else{                 
                    ep = new EpisodioMedico();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    ep.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                    ep.getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                    ep.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    ep.getPaciente().setNombres((String)vRowTemp.get(3));
                    ep.getPaciente().setApPaterno((String)vRowTemp.get(4));
                    ep.getPaciente().setApMaterno((String)vRowTemp.get(5));
                    ep.getPaciente().setFechaNac((Date)vRowTemp.get(6));
                    ep.getPaciente().setSexoP((String)vRowTemp.get(7));
                    ep.getPaciente().setEdoCivilStr((String)vRowTemp.get(8));
                    ep.getPaciente().setCalleNum((String)vRowTemp.get(9));
                    ep.getPaciente().setColonia((String)vRowTemp.get(10));
                    ep.setFechaIngreso((Date)vRowTemp.get(11));
                    ep.getCama().setNumero((String)vRowTemp.get(12));
                    ep.getArea().setDescripcion((String)vRowTemp.get(13));                    
                    ep.getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(14));
                    ep.getPaciente().setNivelSocioEco(new Parametrizacion());
                    ep.getPaciente().getNivelSocioEco().setValor((String)vRowTemp.get(15));
                    String convertido = edad.format(ep.getPaciente().getFechaNac());
                    ep.getPaciente().setFechaNacTexto(convertido);
                    ep.getPaciente().calculaEdad();
                    ep.getAfePrincipal().getCIE10().setClave((String)vRowTemp.get(16).toString());
                    ep.getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(17).toString());
                    return ep;
                }
            }
        }
        public void buscaPacienteDetalles()throws Exception{
            ArrayList rst=null;
            String sQuery;
            int i=0;
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("buscaPacienteDetalles:noesposibleprocesarlainformacion");
            else{
                sQuery="SELECT * FROM buscadetallesHojafrontal("+getEpisodioMedico().getPaciente().getFolioPaciente()+","+getEpisodioMedico().getPaciente().getClaveEpisodio()+");";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();                    
                }
                if(!rst.isEmpty()){               
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    this.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                    this.getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                    this.getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    this.getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(3));
                    this.getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(4));
                    this.getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(5));
                    this.getEpisodioMedico().getPaciente().setFechaNac((Date)vRowTemp.get(6));
                    this.getEpisodioMedico().getPaciente().setSexoP((String)vRowTemp.get(7));
                    this.getEpisodioMedico().getPaciente().setEdoCivilStr((String)vRowTemp.get(8));
                    this.getEpisodioMedico().getPaciente().setCalleNum((String)vRowTemp.get(9));
                    this.getEpisodioMedico().getPaciente().setColonia((String)vRowTemp.get(10));
                    this.getEpisodioMedico().setFechaIngreso((Date)vRowTemp.get(11));
                    this.getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(12));
                    this.getEpisodioMedico().getArea().setDescripcion((String)vRowTemp.get(13));                    
                    this.getEpisodioMedico().getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(14));
                    this.getEpisodioMedico().getPaciente().setNivelSocioEco(new Parametrizacion());
                    this.getEpisodioMedico().getPaciente().getNivelSocioEco().setValor((String)vRowTemp.get(15));
                    String convertido = edad.format(this.getEpisodioMedico().getPaciente().getFechaNac());
                    this.getEpisodioMedico().getPaciente().setFechaNacTexto(convertido);
                    this.getEpisodioMedico().getPaciente().calculaEdad();
                    this.getEpisodioMedico().getAfePrincipal().getCIE10().setClave((String)vRowTemp.get(16).toString());
                    this.getEpisodioMedico().getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(17).toString());
                    this.getEpisodioMedico().getMedicoTratante().setNombres((String)vRowTemp.get(18));
                    this.getEpisodioMedico().getMedicoTratante().setApPaterno((String)vRowTemp.get(19));
                    this.getEpisodioMedico().getMedicoTratante().setApMaterno((String)vRowTemp.get(20));
                    this.getEpisodioMedico().getMedicoTratante().setCedProf((String)vRowTemp.get(21));
                }
            }
        }
        
        //***********************************************************************************************
        public PersonalHospitalario informacionMedico()throws Exception{
            PersonalHospitalario oPersonal = new PersonalHospitalario();
            oPersonal.setUsuar(new Usuario());
            oPersonal.getUsuar().setIdUsuario(sUsuarioFirmado);
            if(oPersonal.buscaPersonalHospitalarioDatos())
                return oPersonal;
            else
                return null;
        }
        public boolean insertActualizaHojaFrontal(EpisodioMedico ep)throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = null;
            ArrayList<String> rstTemporal = null;
            String sQuery = "";
            int nRet = -1;
            String sclavedx = 
                    (ep.getAfePrincipal().getCIE10().getClaveCIE10() == null || 
                    ep.getAfePrincipal().getCIE10().getClaveCIE10().isEmpty()) ? 
                     "null::CHARACTER(6)," : "'" + ep.getAfePrincipal().getCIE10().getClaveCIE10() + "'::CHARACTER(6),";             
            String splan = 
                    (this.getPlan() == null || this.getPlan().isEmpty()) ? 
                     "null::TEXT);" : "'" + this.getPlan().toUpperCase() + "'::TEXT);";
            if(this.getEpisodioMedico().getPaciente().getFolioPaciente() == 0 || this.getEpisodioMedico().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("HOJAFRONTAL.INSERTACTUALIZAHOJAFRONTAL:FALTANDATOS");
            else{
                rstQuery = new ArrayList<String>();
                sQuery = "SELECT * FROM insertaactualizahojafrontal("+ this.getEpisodioMedico().getPaciente().getFolioPaciente() +"::BIGINT," +
                        this.getEpisodioMedico().getPaciente().getClaveEpisodio() + "::BIGINT," + "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING(10)," +
                        sclavedx + splan;
                rstQuery.add(sQuery);
                /*if(!arrDiagnostico.isEmpty()){
                    SegundaMitadEmbarazo oSegundaMitad = new SegundaMitadEmbarazo();
                    rstTemporal = oSegundaMitad.armaQueryPartoGrama(this.getEpisodioMedico().getPaciente().getFolioPaciente(), this.getEpisodioMedico().getPaciente().getClaveEpisodio(), arrDiagnostico, tam);
                    for(int i = 0; i < rstTemporal.size(); i++)
                        rstQuery.add((String)rstTemporal.get(i));
                }*/
                if(rstQuery.size() > 0){
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        nRet = oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();                        
                        bandera = nRet >= 0;
                    }
                }                
                return bandera;
            }
        }
        public HojaFrontal[] buscaHojaFrontal()throws Exception{
            HojaFrontal arrRet[] = null;
            HojaFrontal oFrontal = null;
            ArrayList rst = null;
            ArrayList<HojaFrontal> vObj = null;
            String sQuery = "";
            String nombre = "";
            String aPaterno = "";
            String aMaterno = "";
            String numExp = "";
            int i = 0;
            if(this.getHospitalizacion().getEpisodioMedico().getPaciente().getOpcionUrg() == 0){
                nombre = this.getHospitalizacion().getEpisodioMedico().getPaciente().getNombres();
                aPaterno = this.getHospitalizacion().getEpisodioMedico().getPaciente().getApPaterno();
                aMaterno = this.getHospitalizacion().getEpisodioMedico().getPaciente().getApMaterno();
                numExp = "null";                
            }else{
                nombre = "";
                aPaterno = "";
                aMaterno = "";
                numExp = this.getHospitalizacion().getEpisodioMedico().getPaciente().getExpediente().getNumero() + "";
            }
            if(numExp.compareTo("null") == 0)
                sQuery = "SELECT * FROM buscahojafrontal('"+ nombre +"', '"+ aPaterno +"','"+ aMaterno +"',"+ numExp +");";
            else
                sQuery = "SELECT * FROM buscahojafrontal('"+ nombre +"', '"+ aPaterno +"','"+ aMaterno +"',"+ Integer.parseInt(numExp) +");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() > 0){
                arrRet = new HojaFrontal[rst.size()];
                for(i = 0; i < rst.size(); i++){
                    oFrontal = new HojaFrontal();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    oFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().setFechaNac((Date)vRowTemp.get(3));
                    this.getHospitalizacion().getEpisodioMedico().getPaciente().setFechaNac((Date)vRowTemp.get(3));
                    oFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(4).toString());
                    this.getHospitalizacion().getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(4).toString());
                    oFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(5).toString());
                    this.getHospitalizacion().getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(5).toString());
                    oFrontal.getHospitalizacion().getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(6).toString());
                    this.getHospitalizacion().getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(6).toString());
                    arrRet[i] = oFrontal;
                }
            }
            return arrRet;
        }
        public HojaFrontal[] buscaHistorialHojaFrontal(long folioPac, int nnumexp) throws Exception{
        HojaFrontal arrRet[]=null, oHojaF=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("HojaFrontal.buscaHistorialHojaFrontal: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialhojafrontal("+folioPac+","+nnumexp+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new HojaFrontal[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oHojaF= new HojaFrontal();
                        oHojaF.getHospitalizacion().getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                        oHojaF.getHospitalizacion().getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                        oHojaF.setPlan((String)vRowTemp.get(2).toString());
                        oHojaF.setClaveDiagnostico((String)vRowTemp.get(3).toString() + " " + (String)vRowTemp.get(4).toString());
                        oHojaF.getHospitalizacion().getEpisodioMedico().setFechaIngreso((Date)vRowTemp.get(5));
                        arrRet[i]=oHojaF;
                    } 
            } 
        }
        return arrRet; 
    }
    
	public String getPlan() {
	return sPlan;
	}

	public void setPlan(String valor) {
	sPlan=valor;
	}

	public EpisodioMedico getEpisodioMedico() {
	return oEpisodioMedico;
	}

	public void setEpisodioMedico(EpisodioMedico valor) {
	oEpisodioMedico=valor;
	}
        public String getClaveDiagnostico(){
            return sclavediagnostico;
        }
        public void setClaveDiagnostico(String sclavediagnostico){
            this.sclavediagnostico = sclavediagnostico;
        }
        public int getInterconsultas(){
            return nInterconsultas;
        }
        public void setInterconsultas(int nInterconsultas){
            this.nInterconsultas = nInterconsultas;
        }
        public Hospitalizacion getHospitalizacion(){
            return oHospitalizacion;
        }
        public void setHospitalizacion(Hospitalizacion oHospitalizacion){
            this.oHospitalizacion = oHospitalizacion;
        } 
} 

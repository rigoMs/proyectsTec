/*
* CLASE MODIFICADA POR ALBERTO
* AGREGUE UNA INSTACIA A HISTORIA CLINICAPERINATAL
*/
package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class EvaluacionEdadGestacionalCapurro implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Parametrizacion oFormaPezon;
	private Parametrizacion oFormaOreja;
	private Parametrizacion oPlieguesPlantares;
	private Parametrizacion oTexturaPiel;
	private Parametrizacion oTglandulaMama;
        private HistoriaClinicaPerinatal oPerinatal;
	private int oTotal;

	public EvaluacionEdadGestacionalCapurro(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oPerinatal = new HistoriaClinicaPerinatal();
                this.oFormaPezon = new Parametrizacion();
                this.oFormaOreja = new Parametrizacion();
                this.oPlieguesPlantares = new Parametrizacion();
                this.oTexturaPiel = new Parametrizacion();
                this.oTglandulaMama = new Parametrizacion();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("EvaluacionEdadGestacionalCapurro.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveEvaluacionEdadGestacionalCapurro();"; 
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
	public EvaluacionEdadGestacionalCapurro[] buscarTodos() throws Exception{
	EvaluacionEdadGestacionalCapurro arrRet[]=null, oEvaluacionEdadGestacionalCapurro=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosEvaluacionEdadGestacionalCapurro();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new EvaluacionEdadGestacionalCapurro[rst.size()];
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
			throw new Exception("EvaluacionEdadGestacionalCapurro.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaEvaluacionEdadGestacionalCapurro('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("EvaluacionEdadGestacionalCapurro.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEvaluacionEdadGestacionalCapurro('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("EvaluacionEdadGestacionalCapurro.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaEvaluacionEdadGestacionalCapurro('"+sUsuarioFirmado+"');"; 
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
        //****************INICIAN METODOS DE CONTROL DE DATOS*******************************
        public String armaQueryEvaluacionEdadGestacional(){
            String sQuery = "";
            if(this.oPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oPerinatal.getPacientenNeonato().getClaveEpisodio() == 0)
                return sQuery;
            else{
                String tabtextura = "'TBC'::CHARACTER(3),";
                String valtextura = this.oTexturaPiel.getTipoParametro() == null || this.oTexturaPiel.getTipoParametro().isEmpty() ?  "null::CHARACTER," : "'" + this.oTexturaPiel.getTipoParametro() + "'::CHARACTER(2),";
                String tabpliegues = "'TBD'::CHARACTER(3),";
                String valpliegues = this.oPlieguesPlantares.getTipoParametro() == null || this.oPlieguesPlantares.getTipoParametro().isEmpty() ?  "null::CHARACTER," : "'" + this.oPlieguesPlantares.getTipoParametro() + "'::CHARACTER(2),";
                String tabformaoreja = "'TBE'::CHARACTER(3),";
                String valformaoreja = this.oFormaOreja.getTipoParametro() == null || this.oFormaOreja.getTipoParametro().isEmpty() ?  "null::CHARACTER," : "'" + this.oFormaOreja.getTipoParametro() + "'::CHARACTER(2),";
                String tabglandula = "'TBF'::CHARACTER(3),";
                String valglandula = this.oTglandulaMama.getTipoParametro() == null || this.oTglandulaMama.getTipoParametro().isEmpty() ?  "null::CHARACTER," : "'" + this.oTglandulaMama.getTipoParametro() + "'::CHARACTER(2),";
                String tabpezon = "'TBG'::CHARACTER(3),";
                String valpezon = this.oFormaPezon.getTipoParametro() == null || this.oFormaPezon.getTipoParametro().isEmpty() ?  "null::CHARACTER);" : "'" + this.oFormaPezon.getTipoParametro() + "'::CHARACTER(2));";
                sQuery = "SELECT * FROM insertactualizaevaluacionedadgestacional(" + this.oPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.oPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                        tabtextura + valtextura + tabpliegues + valpliegues + tabformaoreja + valformaoreja + tabglandula + valglandula +
                        tabpezon + valpezon;
            }
            return sQuery;
        }
        public boolean insertaEdadGestacionalCapurro()throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = new ArrayList<String>();
            int nRet = -1;
            System.out.println(this.armaQueryEvaluacionEdadGestacional());
            rstQuery.add(this.armaQueryEvaluacionEdadGestacional());
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                nRet = oAD.ejecutarConsultaComando(rstQuery);
                oAD.desconectar();
                bandera = nRet > 0;
            }
            return bandera;
        }
        public void consultaEspecificaEdadGestacionalCapurro()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.oPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oPerinatal.getPacientenNeonato().getClaveEpisodio() == 0 || this.oPerinatal.getNumeroHistoriaClinica() == 0)
                throw new Exception("CONSULTAESPECIFICAEDADGESTACIONAL:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM detallespecificoedadgestacionalcapurro(" + this.oPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.oPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," + 
                        this.oPerinatal.getNumeroHistoriaClinica() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.oTexturaPiel.setTipoParametro((String)vRowTemp.get(0).toString().trim());
                    this.oPlieguesPlantares.setTipoParametro((String)vRowTemp.get(1).toString().trim());
                    this.oFormaOreja.setTipoParametro((String)vRowTemp.get(2).toString().trim());
                    this.oTglandulaMama.setTipoParametro((String)vRowTemp.get(3).toString().trim());
                    this.oFormaPezon.setTipoParametro((String)vRowTemp.get(4).toString().trim());
                }
            }
        }
        public void consultaEdadGestacionalCapurro()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.oPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oPerinatal.getPacientenNeonato().getClaveEpisodio() == 0)
                throw new Exception("CONSULTADATOSEDADGESTACIONA:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM  detalledadgestacionalcapurro(" + this.oPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT,"
                        + this.oPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);                     
                    this.oTexturaPiel.setTipoParametro((String)vRowTemp.get(0).toString().trim());
                    this.oPlieguesPlantares.setTipoParametro((String)vRowTemp.get(1).toString().trim());
                    this.oFormaOreja.setTipoParametro((String)vRowTemp.get(2).toString().trim());
                    this.oTglandulaMama.setTipoParametro((String)vRowTemp.get(3).toString().trim());
                    this.oFormaPezon.setTipoParametro((String)vRowTemp.get(4).toString().trim());
                }
            }
        }
        public void consultaEspecificaEdadGestacionalCapurroEXP(int valor)throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(valor ==0 || this.oPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oPerinatal.getPacientenNeonato().getClaveEpisodio() == 0 || this.oPerinatal.getNumeroHistoriaClinica() == 0)
                throw new Exception("CONSULTAESPECIFICAEDADGESTACIONALEXP:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM detallespecificoedadgestacionalcapurroEXP(" + this.oPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.oPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," + 
                        this.oPerinatal.getNumeroHistoriaClinica() +"::BIGINT," +
                        valor+"::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.oTexturaPiel.setTipoParametro((String)vRowTemp.get(0).toString().trim());
                    this.oPlieguesPlantares.setTipoParametro((String)vRowTemp.get(1).toString().trim());
                    this.oFormaOreja.setTipoParametro((String)vRowTemp.get(2).toString().trim());
                    this.oTglandulaMama.setTipoParametro((String)vRowTemp.get(3).toString().trim());
                    this.oFormaPezon.setTipoParametro((String)vRowTemp.get(4).toString().trim());
                }
            }
        }

        //****************TERMINAN METODOS DE CONTROL DE DATOS*******************************
	public Parametrizacion getFormaPezon() {
	return oFormaPezon;
	}

	public void setFormaPezon(Parametrizacion valor) {
	oFormaPezon=valor;
	}

	public Parametrizacion getFormaOreja() {
	return oFormaOreja;
	}

	public void setFormaOreja(Parametrizacion valor) {
	oFormaOreja=valor;
	}

	public Parametrizacion getPlieguesPlantares() {
	return oPlieguesPlantares;
	}

	public void setPlieguesPlantares(Parametrizacion valor) {
	oPlieguesPlantares=valor;
	}

	public Parametrizacion getTexturaPiel() {
	return oTexturaPiel;
	}

	public void setTexturaPiel(Parametrizacion valor) {
	oTexturaPiel=valor;
	}

	public Parametrizacion getTglandulaMama() {
	return oTglandulaMama;
	}

	public void setTglandulaMama(Parametrizacion valor) {
	oTglandulaMama=valor;
	}

	public int getTotal() {
	return oTotal;
	}

	public void setTotal(int valor) {
	oTotal=valor;
	}
        public HistoriaClinicaPerinatal getHistoriaClinicaPerinatal(){
            return oPerinatal;
        }
        public void setHistoriaClinicaPerinatal(HistoriaClinicaPerinatal oPerinatal){
            this.oPerinatal = oPerinatal;
        }

} 

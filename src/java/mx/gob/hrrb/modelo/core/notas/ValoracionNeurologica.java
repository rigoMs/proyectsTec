package mx.gob.hrrb.modelo.core.notas;

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

public  class ValoracionNeurologica implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private int nAnguloCodo;
	private int nAnguloMuneca;
	private int nAnguloPopliteo;
	private int nCalificacion;
	private int nGenitalesHombre;
	private int nGenitalesMujer;
	private int nLamugo;
	private int nMamas;
	private int nOido;
	private int nPiel;
	private int nPlieguesPlantares;
	private int nPostura;
	private int nSemanas;
	private int nSignoBufanda;
	private int nTalonOreja;
        private long nFolioValoracion;
        
	private HistoriaClinicaPerinatal oHistoriaClinicaPerinatal;

	public ValoracionNeurologica(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oHistoriaClinicaPerinatal = new HistoriaClinicaPerinatal();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("ValoracionNeurologica.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveValoracionNeurologica();"; 
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
	public ValoracionNeurologica[] buscarTodos() throws Exception{
	ValoracionNeurologica arrRet[]=null, oValoracionNeurologica=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosValoracionNeurologica();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ValoracionNeurologica[rst.size()];
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
			throw new Exception("ValoracionNeurologica.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaValoracionNeurologica('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValoracionNeurologica.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaValoracionNeurologica('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("ValoracionNeurologica.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaValoracionNeurologica('"+sUsuarioFirmado+"');"; 
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
        //*****************INICIAN METODOS DE CONTROL DE DATOS****************************
        public String armaQueryValoracionNeurologica(){
            String sQuery = "";  
            if(this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() == 0)
                return sQuery;
            else{
                String postura = this.getPostura() == 0 ? 0 + "::SMALLINT," : this.getPostura() + "::SMALLINT,";
                String angulomuneca = this.getAnguloMuneca() == 0 ? 0 + "::SMALLINT," : this.getAnguloMuneca() + "::SMALLINT,";
                String angulocodo = this.getAnguloCodo() == 0 ? 0 + "::SMALLINT," : this.getAnguloCodo() + "::SMALLINT,";
                String angulopopliteo = this.getAnguloPopliteo() == 0 ? 0 + "::SMALLINT," : this.getAnguloPopliteo() + "::SMALLINT,";
                String signobufanda = this.getSignoBufanda() == 0 ? 0 + "::SMALLINT," : this.getSignoBufanda() + "::SMALLINT,";
                String talonoreja = this.getTalonOreja() == 0 ? 0 + "::SMALLINT," : this.getTalonOreja() + "::SMALLINT,";
                String piel = this.getPiel() == 0 ? 0 + "::SMALLINT," : this.getPiel() + "::SMALLINT,";
                String lamugo = this.getLamugo() == 0 ? 0 + "::SMALLINT," : this.getLamugo() + "::SMALLINT,";
                String plieguesplantares = this.getPlieguesPlantares() == 0 ? 0 + "::SMALLINT," : this.getPlieguesPlantares() + "::SMALLINT,";
                String mamas = this.getMamas() == 0 ? 0 + "::SMALLINT," : this.getMamas() + "::SMALLINT,";
                String oido = this.getOido() == 0 ? 0 + "::SMALLINT," : this.getOido() + "::SMALLINT,";
                String ghombre = this.getGenitalesHombre() == 0 ? 0 + "::SMALLINT," : this.getGenitalesHombre() + "::SMALLINT,";
                String gmujer = this.getGenitalesMujer() == 0 ? 0 + "::SMALLINT);" : this.getGenitalesMujer() + "::SMALLINT);";
                sQuery = "SELECT * FROM insertactualizavaloracioneurologica(" + this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT,'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                        postura + angulomuneca + angulocodo + angulopopliteo + signobufanda + talonoreja + piel + lamugo + plieguesplantares + mamas +
                        oido + ghombre + gmujer;
            }
            return sQuery;
        }
        public boolean insertaValoracionNeurologica() throws Exception{
            boolean bandera = false;
            ArrayList<String> rstQuery = new ArrayList<String>();
            int nRet = -1;            
            rstQuery.add(this.armaQueryValoracionNeurologica());
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                nRet = oAD.ejecutarConsultaComando(rstQuery);
                oAD.desconectar();
                bandera = nRet > 0;
            }
            return bandera;
        }        
        public void consultaEspecificaValoracionNeurologica()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() == 0 || this.oHistoriaClinicaPerinatal.getNumeroHistoriaClinica() == 0)
                throw new Exception("CONSULTAESPECIFICAVALORACIONNEUROLOGICA:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM detallespecificoevolucioneurologica(" + this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT,"+
                        this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," +
                        this.oHistoriaClinicaPerinatal.getNumeroHistoriaClinica() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp =(ArrayList) rst.get(0);
                    this.setPostura(((Double)vRowTemp.get(1)).intValue());
                    this.setAnguloMuneca(((Double)vRowTemp.get(2)).intValue());
                    this.setAnguloCodo(((Double)vRowTemp.get(3)).intValue());
                    this.setAnguloPopliteo(((Double)vRowTemp.get(4)).intValue());
                    this.setSignoBufanda(((Double)vRowTemp.get(5)).intValue());
                    this.setTalonOreja(((Double)vRowTemp.get(6)).intValue());
                    this.setPiel(((Double)vRowTemp.get(7)).intValue());
                    this.setLamugo(((Double)vRowTemp.get(8)).intValue());
                    this.setPlieguesPlantares(((Double)vRowTemp.get(9)).intValue());
                    this.setMamas(((Double)vRowTemp.get(10)).intValue());
                    this.setOido(((Double)vRowTemp.get(11)).intValue());
                    this.setGenitalesHombre(((Double)vRowTemp.get(12)).intValue());
                    this.setGenitalesMujer(((Double)vRowTemp.get(13)).intValue());
                }
            }
        }
        public void consultaValoracionNeurologica()throws Exception{
            ArrayList rst = null;
            String sQuery = null;
            if(this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() == 0)
                throw new Exception("VALORACIONNEUROLOGICCA.CONSULTAVALORACIONEUROLOGICA:NOPODEMOSPROCESARLAINFORMACION");
            else{
                sQuery = "SELECT * FROM detallevolucioneurologica(" + this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT," +
                        this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    this.setPostura(((Double)vRowTemp.get(1)).intValue());
                    this.setAnguloMuneca(((Double)vRowTemp.get(2)).intValue());
                    this.setAnguloCodo(((Double)vRowTemp.get(3)).intValue());
                    this.setAnguloPopliteo(((Double)vRowTemp.get(4)).intValue());
                    this.setSignoBufanda(((Double)vRowTemp.get(5)).intValue());
                    this.setTalonOreja(((Double)vRowTemp.get(6)).intValue());
                    this.setPiel(((Double)vRowTemp.get(7)).intValue());
                    this.setLamugo(((Double)vRowTemp.get(8)).intValue());
                    this.setPlieguesPlantares(((Double)vRowTemp.get(9)).intValue());
                    this.setMamas(((Double)vRowTemp.get(10)).intValue());
                    this.setOido(((Double)vRowTemp.get(11)).intValue());
                    this.setGenitalesHombre(((Double)vRowTemp.get(12)).intValue());
                    this.setGenitalesMujer(((Double)vRowTemp.get(13)).intValue());
                }
            }
        }
        public void consultaEspecificaValoracionNeurologicaEXP( long nvaloracion)throws Exception{
        ArrayList rst = null;
        String sQuery = null;
        if(nvaloracion ==0 || this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() == 0 || this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() == 0 || this.oHistoriaClinicaPerinatal.getNumeroHistoriaClinica() == 0)
            throw new Exception("CONSULTAESPECIFICAVALORACIONNEUROLOGICAEXP:NOPODEMOSPROCESARLAINFORMACION");
        else{
            sQuery = "SELECT * FROM detallespecificoevolucioneurologicaEXP(" + this.oHistoriaClinicaPerinatal.getPacientenNeonato().getFolioPaciente() + "::BIGINT,"+
                    this.oHistoriaClinicaPerinatal.getPacientenNeonato().getClaveEpisodio() + "::BIGINT," +
                    this.oHistoriaClinicaPerinatal.getNumeroHistoriaClinica() + "::BIGINT,"+
                    nvaloracion+"::BIGINT);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp =(ArrayList) rst.get(0);
                this.setPostura(((Double)vRowTemp.get(1)).intValue());
                this.setAnguloMuneca(((Double)vRowTemp.get(2)).intValue());
                this.setAnguloCodo(((Double)vRowTemp.get(3)).intValue());
                this.setAnguloPopliteo(((Double)vRowTemp.get(4)).intValue());
                this.setSignoBufanda(((Double)vRowTemp.get(5)).intValue());
                this.setTalonOreja(((Double)vRowTemp.get(6)).intValue());
                this.setPiel(((Double)vRowTemp.get(7)).intValue());
                this.setLamugo(((Double)vRowTemp.get(8)).intValue());
                this.setPlieguesPlantares(((Double)vRowTemp.get(9)).intValue());
                this.setMamas(((Double)vRowTemp.get(10)).intValue());
                this.setOido(((Double)vRowTemp.get(11)).intValue());
                this.setGenitalesHombre(((Double)vRowTemp.get(12)).intValue());
                this.setGenitalesMujer(((Double)vRowTemp.get(13)).intValue());
            }
        }
    }
    
        //*****************TERMINAN METODOS DE CONTROL DE DATOS****************************
	public int getAnguloCodo() {
	return nAnguloCodo;
	}

	public void setAnguloCodo(int valor) {
	nAnguloCodo=valor;
	}

	public int getAnguloMuneca() {
	return nAnguloMuneca;
	}

	public void setAnguloMuneca(int valor) {
	nAnguloMuneca=valor;
	}

	public int getAnguloPopliteo() {
	return nAnguloPopliteo;
	}

	public void setAnguloPopliteo(int valor) {
	nAnguloPopliteo=valor;
	}

	public int getCalificacion() {
	return nCalificacion;
	}

	public void setCalificacion(int valor) {
	nCalificacion=valor;
	}

	public int getGenitalesHombre() {
	return nGenitalesHombre;
	}

	public void setGenitalesHombre(int valor) {
	nGenitalesHombre=valor;
	}

	public int getGenitalesMujer() {
	return nGenitalesMujer;
	}

	public void setGenitalesMujer(int valor) {
	nGenitalesMujer=valor;
	}

	public int getLamugo() {
	return nLamugo;
	}

	public void setLamugo(int valor) {
	nLamugo=valor;
	}

	public int getMamas() {
	return nMamas;
	}

	public void setMamas(int valor) {
	nMamas=valor;
	}

	public int getOido() {
	return nOido;
	}

	public void setOido(int valor) {
	nOido=valor;
	}

	public int getPiel() {
	return nPiel;
	}

	public void setPiel(int valor) {
	nPiel=valor;
	}

	public int getPlieguesPlantares() {
	return nPlieguesPlantares;
	}

	public void setPlieguesPlantares(int valor) {
	nPlieguesPlantares=valor;
	}

	public int getPostura() {
	return nPostura;
	}

	public void setPostura(int valor) {
	nPostura=valor;
	}

	public int getSemanas() {
	return nSemanas;
	}

	public void setSemanas(int valor) {
	nSemanas=valor;
	}

	public int getSignoBufanda() {
	return nSignoBufanda;
	}

	public void setSignoBufanda(int valor) {
	nSignoBufanda=valor;
	}

	public int getTalonOreja() {
	return nTalonOreja;
	}

	public void setTalonOreja(int valor) {
	nTalonOreja=valor;
	}

	public HistoriaClinicaPerinatal getHistoriaClinicaPerinatal() {
	return oHistoriaClinicaPerinatal;
	}

	public void setHistoriaClinicaPerinatal(HistoriaClinicaPerinatal valor) {
	oHistoriaClinicaPerinatal=valor;
	}
        

    public long getFolioValoracion() {
        return nFolioValoracion;
    }

    public void setFolioValoracion(long nFolioValoracion) {
        this.nFolioValoracion = nFolioValoracion;
    }
} 

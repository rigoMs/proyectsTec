package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.Parametrizacion;
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

public  class PrimeraMitadEmbarazo implements Serializable{
	private AccesoDatos oAD;
        private SegundaMitadEmbarazo oSegundaMitadEmbarazo;
	private String sUsuarioFirmado;
	private boolean bHemorragia;
	private Date dFecha;
	private int nDolorIntesidad;
	private int nPulso;
	private int nSemanasAmerorrea;
	private Parametrizacion oSitio;
	private String sCervix;
	private String sObservaciones;
	private String sPlan;
	private String sUtero;
        //AGREGAR A LA BASE DE DATOS
        private String sOtroSintomas;
        private String sOtrosDatos;
        private PartoGrama oPartoGrama;

	public PrimeraMitadEmbarazo(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oSitio = new Parametrizacion();
                oPartoGrama = new PartoGrama();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PrimeraMitadEmbarazo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePrimeraMitadEmbarazo();"; 
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
	public PrimeraMitadEmbarazo[] buscarTodos() throws Exception{
	PrimeraMitadEmbarazo arrRet[]=null, oPrimeraMitadEmbarazo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPrimeraMitadEmbarazo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new PrimeraMitadEmbarazo[rst.size()];
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
			throw new Exception("PrimeraMitadEmbarazo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPrimeraMitadEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("PrimeraMitadEmbarazo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPrimeraMitadEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("PrimeraMitadEmbarazo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPrimeraMitadEmbarazo('"+sUsuarioFirmado+"');"; 
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
        public PrimeraMitadEmbarazo cargaDetallePaciente()throws Exception{
            PrimeraMitadEmbarazo oPrimeraMitadEmbarazo = null;
            ArrayList rst = null;
            String sQuery = "";
            int i = 0;
            if(this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getPartoGrama().getConsecutivo() == 0 || this.getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
            else{
                sQuery = "SELECT * FROM buscadetallepacientepartograma(" + this.getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," +
                        this.getPartoGrama().getConsecutivo() + "::SMALLINT," + this.getPartoGrama().getNpartograma() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return null;
                else{
                    oPrimeraMitadEmbarazo = new PrimeraMitadEmbarazo();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oPrimeraMitadEmbarazo.setSemanasAmerorrea(((Double)vRowTemp.get(7)).intValue());
                    oPrimeraMitadEmbarazo.setDolorIntesidad(((Double)vRowTemp.get(8)).intValue());
                    oPrimeraMitadEmbarazo.getSitio().setValor((String)vRowTemp.get(9).toString());                                        
                    oPrimeraMitadEmbarazo.setFecha((Date)vRowTemp.get(10));
                    String sHemorragia = (String)vRowTemp.get(11).toString();
                    boolean bHemorragia = sHemorragia.compareTo("1") == 0 ? true : false;
                    oPrimeraMitadEmbarazo.setHemorragia(bHemorragia);
                    oPrimeraMitadEmbarazo.setObservaciones((String)vRowTemp.get(12).toString());
                    oPrimeraMitadEmbarazo.setOtroSintomas((String)vRowTemp.get(13).toString());
                    oPrimeraMitadEmbarazo.setUtero((String)vRowTemp.get(14).toString());
                    oPrimeraMitadEmbarazo.setCervix((String)vRowTemp.get(15).toString());
                    oPrimeraMitadEmbarazo.setOtrosDatos((String)vRowTemp.get(16).toString());
                    oPrimeraMitadEmbarazo.setPlan((String)vRowTemp.get(17).toString());
                    oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(18).toString());                    
                    oPrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setClave((String)vRowTemp.get(55).toString());
                    oPrimeraMitadEmbarazo.getPartoGrama().getEpiMed().getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(56).toString());
                    String sitio = (String)vRowTemp.get(81).toString().trim() + "" + (String)vRowTemp.get(82).toString();                    
                    oPrimeraMitadEmbarazo.getSitio().setTipoParametro(sitio);
                    String nombres = (String)vRowTemp.get(19).toString() + " " + (String)vRowTemp.get(20).toString() + " " + (String)vRowTemp.get(21).toString();
                    oPrimeraMitadEmbarazo.getPartoGrama().getMedicoSupervisor().setNombres(nombres);
                }
            }
            return oPrimeraMitadEmbarazo;
        }
	public boolean getHemorragia() {
	return bHemorragia;
	}

	public void setHemorragia(boolean valor) {
	bHemorragia=valor;
	}

	public Date getFecha() {
	return dFecha;
	}

	public void setFecha(Date valor) {
	dFecha=valor;
	}

	public int getDolorIntesidad() {
	return nDolorIntesidad;
	}

	public void setDolorIntesidad(int valor) {
	nDolorIntesidad=valor;
	}

	public int getPulso() {
	return nPulso;
	}

	public void setPulso(int valor) {
	nPulso=valor;
	}

	public int getSemanasAmerorrea() {
	return nSemanasAmerorrea;
	}

	public void setSemanasAmerorrea(int valor) {
	nSemanasAmerorrea=valor;
	}

	public Parametrizacion getSitio() {
	return oSitio;
	}

	public void setSitio(Parametrizacion valor) {
	oSitio=valor;
	}

	public String getCervix() {
	return sCervix;
	}

	public void setCervix(String valor) {
	sCervix=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public String getPlan() {
	return sPlan;
	}

	public void setPlan(String valor) {
	sPlan=valor;
	}

	public String getUtero() {
	return sUtero;
	}

	public void setUtero(String valor) {
	sUtero=valor;
	}
        public String getOtroSintomas(){
            return sOtroSintomas;
        }
        public void setOtroSintomas(String sOtroSintomas){
            this.sOtroSintomas = sOtroSintomas;
        }
        public String getOtrosDatos(){
            return sOtrosDatos;
        }
        public void setOtrosDatos(String sOtrosDatos){
            this.sOtrosDatos = sOtrosDatos;
        }
        public PartoGrama getPartoGrama(){
            return oPartoGrama;
        }
        public void setPartoGrama(PartoGrama oPartoGrama){
            this.oPartoGrama = oPartoGrama;
        }
        //METODO CREADO POR ALBERTO [TRADUCE UN BOOLEAN A UNA AFIRMACION SI O NO]
        public String getTieneHemorragia(){
            return this.getHemorragia() ? "SÍ" : "NO";
        }
} 

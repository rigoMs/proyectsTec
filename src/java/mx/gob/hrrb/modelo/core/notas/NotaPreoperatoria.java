/*
* CLASE MODIFICADA POR ALBERTO
* AGREGUE EL OBJETO TERMINACION EMBARAZO
* AGREGUE UN OBJETO PROCEDIMIENTO REALIZADOS
*/
package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.NotaMedicaHRRB;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.SignosVitales;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class NotaPreoperatoria extends NotaMedicaHRRB implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private boolean bSalpingo;
	private DiagnosticoCIE10 oDiagnosticoPreoperatorio;
	private String sCuidadosPlanTerapeuticoPreoperatorio;
	private String sIndicacionPrincipal;
	private String sNotasComplementarias;
	private String sObservaciones;
	private String sPlanQuirurgico;
	private String sPLanTerapeuticoPreoperatorio;
	private String sRiesgoQuirurgico;
        private Parametrizacion oDescripcionCirugia;
	private ProcedimientosRealizados oProcedimientosRealizados;
        private TerminacionEmbarazo oTerminacionEmbarazo;
        
        
        private Date dFechaCirugia;
        private Date dHoraCirugia;
        private SignosVitales oSignos;
        private String sInterrogacion;
        private String sResumenEstudios;
        private Date dFechaRealizacion;

	public NotaPreoperatoria(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oTerminacionEmbarazo = new TerminacionEmbarazo();
                oProcedimientosRealizados = new ProcedimientosRealizados();
                this.oProcedimientosRealizados.inicializar();
                oDiagnosticoPreoperatorio = new DiagnosticoCIE10();
                oDescripcionCirugia = new Parametrizacion();
                oSignos= new SignosVitales();
                dFechaRealizacion= new Date();
	}
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaPreoperatoria.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveNotaPreoperatoria();"; 
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
        @Override
	public NotaPreoperatoria[] buscarTodos() throws Exception{
	NotaPreoperatoria arrRet[]=null, oNotaPreoperatoria=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosNotaPreoperatoria();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new NotaPreoperatoria[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaPreoperatoria.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaPreoperatoria('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaPreoperatoria.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaNotaPreoperatoria('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaPreoperatoria.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaPreoperatoria('"+sUsuarioFirmado+"');"; 
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
        public NotaPreoperatoria[] buscaNotasPaciente()throws Exception{
            NotaPreoperatoria arrRet[] = null;
            NotaPreoperatoria oNota = null;
            ArrayList rst = null;
            String sQuery = "";
            String nombre = "";
            String apaterno = "";
            String amaterno = "";
            String numexp = "";
            if(this.getEpiMed().getPaciente().getOpcionUrg() == 0){
                nombre = this.getEpiMed().getPaciente().getNombres();
                apaterno = this.getEpiMed().getPaciente().getApPaterno();
                amaterno = this.getEpiMed().getPaciente().getApMaterno();
                numexp = "null";
            }else{
                nombre = "";
                apaterno = "";
                amaterno = "";
                numexp = this.getEpiMed().getPaciente().getExpediente().getNumero() + "";
            }
            if(numexp.compareTo("null") == 0)
                sQuery = "SELECT * FROM buscanotaspreoperatorias('"+ nombre +"', '"+ apaterno +"','"+ amaterno +"',"+ numexp + ");";
            else
                sQuery = "SELECT * FROM buscanotaspreoperatorias('"+ nombre +"', '" + apaterno + "','" + amaterno + "'," + Integer.parseInt(numexp) + ");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                arrRet = new NotaPreoperatoria[rst.size()];
                for(int i = 0; i < rst.size(); i++){
                    oNota = new NotaPreoperatoria();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNota.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(0));
                    oNota.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(1).toString());
                    oNota.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(2).toString());
                    oNota.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(3).toString());
                    oNota.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(4)).longValue());
                    oNota.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(5)).longValue());                    
                    oNota.setConsecutivo(((Double)vRowTemp.get(6)).intValue());
                    oNota.getCIE9().setClave((String)vRowTemp.get(7).toString());
                    oNota.getCIE9().setDescripcion((String)vRowTemp.get(8).toString());
                    oNota.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(9)).intValue());                    
                    oNota.setMaxConsecutivo(((Double)vRowTemp.get(10)).intValue());
                    arrRet[i] = oNota;
                }
            }
            return arrRet;
        }
        public String getQueryPreoperatorio(){
            DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            String sQuery = "";
            if(this.getFechaRegistro() == null)
                return sQuery;
            else{
                String tarjeta = 
                        this.getTerminacionEmbarazo().getPartoGrama()
                                .getMedicoSupervisor().getNoTarjeta() != 0 ? 
                        this.getTerminacionEmbarazo().getPartoGrama().
                                getMedicoSupervisor().getNoTarjeta() + "::INTEGER," :  "null::INTEGER,";
                String clavecirugia = 
                        this.getProcedimientosRealizados().getCIE9().getClave() == 
                        null || 
                        this.getProcedimientosRealizados().getCIE9().getClave()
                        .isEmpty() ?  
                        "null::CHARACTER(6)," : 
                        "'" + this.getProcedimientosRealizados().getCIE9()
                                .getClave().toUpperCase() + "'::CHARACTER(6),";
                String fecha = this.getFechaRegistro() == null  ? 
                        "null::timestamp without time zone," : 
                        "'" + format.format(this.getFechaRegistro()) + 
                        "'::timestamp without time zone,";
                String indicacion = this.getIndicacionPrincipal() == null || 
                        this.getIndicacionPrincipal().isEmpty() ? 
                        "null::TEXT," : 
                        "'" + this.getIndicacionPrincipal().toUpperCase() + 
                        "'::TEXT,";
                String diagnostico = 
                        this.getDiagnosticoPreoperatorio().getClaveCIE10()==null
                        || this.getDiagnosticoPreoperatorio().getClaveCIE10().
                                isEmpty() ? 
                        "null::CHARACTER(6)," : 
                        "'" + this.getDiagnosticoPreoperatorio().
                            getClaveCIE10().toUpperCase() + "'::CHARACTER(6),";
                String plan = this.getPLanTerapeuticoPreoperatorio() == null || 
                        this.getPLanTerapeuticoPreoperatorio().isEmpty() ? 
                        "null::TEXT," : 
                        "'" + this.getPLanTerapeuticoPreoperatorio()
                                .toUpperCase() + "'::TEXT,";
                String planquirurgico = this.getPlanQuirurgico() == null || 
                        this.getPlanQuirurgico().isEmpty() ? 
                        "null::TEXT," : 
                        "'" + this.getPlanQuirurgico().toUpperCase()+"'::TEXT,";
                String riesgoqx = this.getRiesgoQuirurgico() == null || 
                        this.getRiesgoQuirurgico().isEmpty() ? 
                        "null::TEXT," : 
                        "'" + this.getRiesgoQuirurgico().toUpperCase() + 
                        "'::TEXT,";
                String notascomplementarias = 
                        this.getNotasComplementarias() == null || 
                        this.getNotasComplementarias().isEmpty() ? 
                        "null::TEXT," : 
                        "'" + this.getNotasComplementarias().toUpperCase() + 
                        "'::TEXT,";
                String salpingoclasia = this.getSalpingo() ? 
                        "'1'::CHARACTER," : "'0'::CHARACTER,";
                String observaciones = this.getObservaciones() == null || 
                        this.getObservaciones().isEmpty() ? 
                        "null::TEXT," : 
                        "'" + this.getObservaciones().toUpperCase()+ "'::TEXT,";
                String tab = 
                        this.getDescripcionCirugia().getClaveParametro() == null 
                        || this.getDescripcionCirugia().getClaveParametro()
                                .isEmpty() ? 
                        "null::CHARACTER(3)," : 
                        "'" + this.getDescripcionCirugia().getClaveParametro()
                            .substring(0, 3).toUpperCase() + "'::CHARACTER(3),";
                String val = 
                        this.getDescripcionCirugia().getClaveParametro() == null 
                        || this.getDescripcionCirugia().getClaveParametro()
                                .isEmpty() ? 
                        "null::CHARACTER(2));" : 
                        "'" + this.getDescripcionCirugia().getClaveParametro()
                                .substring(3, 5).toUpperCase() + 
                        "'::CHARACTER(2));";
                sQuery = "SELECT * FROM insertactualizanotapreoperatoria(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT,'" +
                        this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                        tarjeta + clavecirugia + fecha + indicacion +
                        diagnostico + plan + planquirurgico + riesgoqx + notascomplementarias + salpingoclasia + observaciones + tab + val;
            }            
            return sQuery;
        }
        public void detalleNotaPreoperatoria()throws Exception{
            ArrayList rst = null;
            String sQuery = "";
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0)
                throw new Exception("NO ESPOSIBLE PROCESAR LA INFORMACION : FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM  buscadetallenotapreoperatoriapartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() +"::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT," + this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() +"::SMALLINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
            }
            if(!rst.isEmpty()){
                ArrayList vRowTemp = (ArrayList)rst.get(0);                
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres((String)vRowTemp.get(0).toString() + " " + (String)vRowTemp.get(1).toString() + " " + (String)vRowTemp.get(2).toString());
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(3).toString());
                this.getDescripcionCirugia().setClaveParametro((String)vRowTemp.get(4).toString() + " " + (String)vRowTemp.get(5).toString());
                this.setFechaRegistro((Date)vRowTemp.get(6));
                this.setIndicacionPrincipal((String)vRowTemp.get(7).toString());
                this.getDiagnosticoPreoperatorio().setClave((String)vRowTemp.get(8).toString());
                this.getDiagnosticoPreoperatorio().setDescripcionDiag((String)vRowTemp.get(9).toString());
                this.setPLanTerapeuticoPreoperatorio((String)vRowTemp.get(10).toString());
                this.setPlanQuirurgico((String)vRowTemp.get(11).toString());
                this.setRiesgoQuirurgico((String)vRowTemp.get(12).toString());
                this.setNotasComplementarias((String)vRowTemp.get(13).toString());
                this.setSalpingo(vRowTemp.get(14).toString().compareTo("1") == 0);
                this.setObservaciones((String)vRowTemp.get(15).toString());
                this.getDescripcionCirugia().setClaveParametro((String)vRowTemp.get(16) + "" + (String)vRowTemp.get(17).toString());
            }else{
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApPaterno("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setApMaterno("");
                this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf("");
                this.getDescripcionCirugia().setClaveParametro("");
                this.setFechaRegistro(null);
                this.setIndicacionPrincipal("");
                this.getDiagnosticoPreoperatorio().setClave("");
                this.getDiagnosticoPreoperatorio().setDescripcionDiag("");
                this.setPLanTerapeuticoPreoperatorio("");
                this.setPlanQuirurgico("");
                this.setRiesgoQuirurgico("");
                this.setNotasComplementarias("");
                this.setSalpingo(false);
                this.setObservaciones("");
                this.getDiagnosticoPreoperatorio().setClave("");
            }
        }
	public boolean getSalpingo() {
	return bSalpingo;
	}

	public void setSalpingo(boolean valor) {
	bSalpingo=valor;
	}

	public DiagnosticoCIE10 getDiagnosticoPreoperatorio() {
	return oDiagnosticoPreoperatorio;
	}

	public void setDiagnosticoPreoperatorio(DiagnosticoCIE10 valor) {
	oDiagnosticoPreoperatorio=valor;
	}

	public String getCuidadosPlanTerapeuticoPreoperatorio() {
	return sCuidadosPlanTerapeuticoPreoperatorio;
	}

	public void setCuidadosPlanTerapeuticoPreoperatorio(String valor) {
	sCuidadosPlanTerapeuticoPreoperatorio=valor;
	}

	public String getIndicacionPrincipal() {
	return sIndicacionPrincipal;
	}

	public void setIndicacionPrincipal(String valor) {
	sIndicacionPrincipal=valor;
	}

	public String getNotasComplementarias() {
	return sNotasComplementarias;
	}

	public void setNotasComplementarias(String valor) {
	sNotasComplementarias=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public String getPlanQuirurgico() {
	return sPlanQuirurgico;
	}

	public void setPlanQuirurgico(String valor) {
	sPlanQuirurgico=valor;
	}

	public String getPLanTerapeuticoPreoperatorio() {
	return sPLanTerapeuticoPreoperatorio;
	}

	public void setPLanTerapeuticoPreoperatorio(String valor) {
	sPLanTerapeuticoPreoperatorio=valor;
	}

	public String getRiesgoQuirurgico() {
	return sRiesgoQuirurgico;
	}

	public void setRiesgoQuirurgico(String valor) {
	sRiesgoQuirurgico=valor;
	}

	public ProcedimientosRealizados getProcedimientosRealizados() {
	return oProcedimientosRealizados;
	}

	public void setProcedimientosRealizados(ProcedimientosRealizados valor) {
	oProcedimientosRealizados=valor;
	}
        public TerminacionEmbarazo getTerminacionEmbarazo(){
            return oTerminacionEmbarazo;
        }
        public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
            this.oTerminacionEmbarazo = oTerminacionEmbarazo;
        }
        public Parametrizacion getDescripcionCirugia(){
            return oDescripcionCirugia;
        }
        public void setDescripcionCirugia(Parametrizacion oDescripcionCirugia){
            this.oDescripcionCirugia = oDescripcionCirugia;
        }
        public String getValorSalpingo(){
            return  getSalpingo() ? "SÍ(X) NO( )" : "SI( ) NO(X)";
        }

    public Date getFechaCirugia() {
        return dFechaCirugia;
    }

    public void setFechaCirugia(Date dFechaCirugia) {
        this.dFechaCirugia = dFechaCirugia;
    }

    public Date getHoraCirugia() {
        return dHoraCirugia;
    }

    public void setHoraCirugia(Date dHoraCirugia) {
        this.dHoraCirugia = dHoraCirugia;
    }
    
    public SignosVitales getSignos() {
        return oSignos;
    }
    
    public void setSignos(SignosVitales oS){
        this.oSignos= oS;
    }

    public String getInterrogacion() {
        return sInterrogacion;
    }

    public void setInterrogacion(String sInterrogacion) {
        this.sInterrogacion = sInterrogacion;
    }

    public String getResumenEstudios() {
        return sResumenEstudios;
    }

    public void setResumenEstudios(String sResumenEstudios) {
        this.sResumenEstudios = sResumenEstudios;
    }
    
    public Date getFechaRealizacion() {
        return dFechaRealizacion;
    }

    public void setFechaRealizacion(Date dFechaRealizacion) {
        this.dFechaRealizacion = dFechaRealizacion;
    }

    public String getFechaRealStr(){
        SimpleDateFormat df= new SimpleDateFormat("dd/MM/yyyy HH:mm a");
        return df.format(this.getFechaRealizacion());
    }
    
    public boolean getEditarTA(){
            return this.oSignos.getTA()!=null && !this.oSignos.getTA().equals("");
    }
    
    public boolean getEditarPulso(){
            return this.oSignos.getPulso()!=0;
    }
    
    public boolean getEditarTemp(){
            return this.oSignos.getTemp()!=null && !this.oSignos.getTemp().equals("");
    }
    
    public boolean getEditarFR(){
            return this.oSignos.getFR()!=null && !this.oSignos.getFR().equals("");
    }
    
    public Medico[] buscaMedicos(short nOpcion)throws Exception{
        if(nOpcion == 1)
            return buscaMedico((String) "SELECT * FROM buscaMedicoPartoGrama();");
        return null;
    }
    
    
        
    public Medico[] buscaMedico(String sQuery)throws Exception{
        Medico arrRet[] = null, oMedico = null;
        ArrayList rst = null;            
        int i = 0;            
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }if(rst.isEmpty())
            return null;
        else{
            arrRet= new Medico[rst.size()];
            for(i = 0; i < rst.size(); i++){
                oMedico = new Medico();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oMedico.setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                oMedico.setNombres((String)vRowTemp.get(1).toString());
                oMedico.setApPaterno((String)vRowTemp.get(2).toString());
                oMedico.setApMaterno((String)vRowTemp.get(3).toString());
                oMedico.setCedProf((String)vRowTemp.get(4).toString());
                arrRet[i]=oMedico;
            }
        }
        return arrRet;
    }
    
    public ArrayList<NotaPreoperatoria> buscarNotasAgregadas()throws Exception{
        ArrayList<NotaPreoperatoria> arrRet=null;
        NotaPreoperatoria oNota;
        ArrayList rst=null;
        int i=0;
        String sQuery="",edad;
        if(this.getEpiMed().getPaciente().getFolioPaciente()==0 || this.getEpiMed().getPaciente().getClaveEpisodio()==0){
            throw new Exception("NotaPreoperatoria.buscarNotasAgredas: Error, Fantan datos");
        }else{
            sQuery="SELECT * FROM buscaNotasPreoperatoriasAgregadas("+this.getEpiMed().getPaciente().getFolioPaciente()+"::bigint,"+
                    this.getEpiMed().getPaciente().getClaveEpisodio()+"::bigint);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrRet= new ArrayList<NotaPreoperatoria>();
                for(i=0; i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNota= new NotaPreoperatoria();
                    oNota.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNota.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNota.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(2));
                    oNota.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(3));
                    oNota.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(4));
                    switch ((String)vRowTemp.get(5)) {
                        case "M":
                            oNota.getEpiMed().getPaciente().setSexoP("MASCULINO");
                            break;
                        case "F":
                            oNota.getEpiMed().getPaciente().setSexoP("FEMENINO");
                            break;
                        default:
                            oNota.getEpiMed().getPaciente().setSexoP("NO ESPECIFICADO");
                            break;
                    }
                    edad=(String)vRowTemp.get(6);
                    if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                        if (edad.charAt(0)=='0')
                           oNota.getEpiMed().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                           oNota.getEpiMed().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                            oNota.getEpiMed().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                            oNota.getEpiMed().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                    }
                    oNota.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(7)).intValue());
                    oNota.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(8));
                    oNota.getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(9));
                    oNota.setConsecutivo((((Double)vRowTemp.get(10)).intValue()));
                    arrRet.add(oNota);
                }
            }
        }
        return arrRet;
    }
    
    public boolean buscarInfomacionNota() throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        String sQuery="", edad="";
        if(this.getEpiMed().getPaciente().getFolioPaciente()==0 
                || this.getEpiMed().getPaciente().getClaveEpisodio()==0
                || this.getProcedimientosRealizados().getCIE9().getClave().equals("")
                || this.getConsecutivo()==0){
            throw new Exception("NotaPreoperatoria.buscarInfomacionNota: Error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaInformacionNotaPreoperatoria("+this.getEpiMed().getPaciente().getFolioPaciente()+"::bigint,"
                    +this.getEpiMed().getPaciente().getClaveEpisodio()+"::bigint,"
                    +this.getConsecutivo()+"::smallint,'"
                    +this.getProcedimientosRealizados().getCIE9().getClave()+"'::character varying);";             
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                ArrayList vRowTemp=(ArrayList)rst.get(0);
                this.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                this.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                this.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(2));
                this.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(3));
                this.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(4));                
                switch ((String)vRowTemp.get(5)) {
                    case "M":
                        this.getEpiMed().getPaciente().setSexoP("MASCULINO");
                        break;
                    case "F":
                        this.getEpiMed().getPaciente().setSexoP("FEMENINO");
                        break;
                    default:
                        this.getEpiMed().getPaciente().setSexoP("NO ESPECIFICADO");
                        break;
                }
                edad=(String)vRowTemp.get(6);
                if (edad.compareTo("")!=0){
                if(edad.substring(0, 3).compareTo("000")!=0){
                    if (edad.charAt(0)=='0')
                       this.getEpiMed().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                    else
                       this.getEpiMed().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                }else{
                    if (edad.substring(4, 6).compareTo("00")!=0)
                        this.getEpiMed().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                    else
                        this.getEpiMed().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                }
                this.setFechaRealizacion((Date)vRowTemp.get(7));
                this.getMedicoSupervisor().setCedProf((String)vRowTemp.get(8));
                this.getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(9));
                this.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(10));
                this.getDiagnosticoPreoperatorio().setDescripcionDiag((String)vRowTemp.get(11));
                this.getDiagnosticoPreoperatorio().setClave((String)vRowTemp.get(12));
                this.getSignos().setTA((String)vRowTemp.get(13));
                this.getSignos().setPulso(((Double)vRowTemp.get(14)).intValue());
                this.getSignos().setTemp((String)vRowTemp.get(15));
                this.getSignos().setFR((String)vRowTemp.get(16));
                this.setInterrogacion((String)vRowTemp.get(17));
                this.setResumenEstudios((String)vRowTemp.get(18));
                this.setPLanTerapeuticoPreoperatorio((String)vRowTemp.get(19));
                this.setPlanQuirurgico((String)vRowTemp.get(20));
                this.setRiesgoQuirurgico((String)vRowTemp.get(21));
                this.setCuidadosPlanTerapeuticoPreoperatorio((String)vRowTemp.get(22));
                this.setFechaCirugia((Date)vRowTemp.get(23));
                this.setHoraCirugia((Date)vRowTemp.get(24));
                this.setConsecutivo(((Double)vRowTemp.get(25)).intValue());
                this.getMedicoSupervisor().setNoTarjeta(((Double)vRowTemp.get(26)).intValue());
                this.getMedicoSupervisor().setNombres((String)vRowTemp.get(27));
                this.getMedicoSupervisor().setApPaterno((String)vRowTemp.get(28));
                this.getMedicoSupervisor().setApMaterno((String)vRowTemp.get(29));
                bRet=true;
            }
        }
        return bRet;
    }

     
    public NotaPreoperatoria[] buscaDianosticosAgregados()throws Exception{
            NotaPreoperatoria arrRet[] = null, oDiagnostico = null;
            ArrayList rst = null;            
            String sQuery = "";
            int i = 0;
            if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 
                    || this.getEpiMed().getPaciente().getClaveEpisodio() == 0)
                throw new Exception("NotaPreoperatoria.buscaDianosticosAgregados: Error, Faltan Datos");
            else{
                sQuery = "SELECT * FROM consultaDiagnosticosTriage(" +this.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT, " 
                        +this.getEpiMed().getPaciente().getClaveEpisodio()+ "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null && rst.size() > 0){
                    arrRet = new NotaPreoperatoria[rst.size()];
                    for(i = 0; i < rst.size(); i++){
                        oDiagnostico = new NotaPreoperatoria();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oDiagnostico.getDiagnosticoPreoperatorio().setClave((String)(vRowTemp).get(1).toString());
                        oDiagnostico.getDiagnosticoPreoperatorio().setDescripcionDiag((String)(vRowTemp).get(0).toString());
                        arrRet[i] = oDiagnostico;
                    }
            }
            }
            return arrRet;
        }    
    
    public String  getModificarNotaPreoperatoriaGeneral() throws Exception{
        String sQuery="";
        if(this.getEpiMed().getPaciente().getFolioPaciente()==0
                || this.getEpiMed().getPaciente().getClaveEpisodio()==0
                || this.getProcedimientosRealizados().getCIE9().getClave().equals("")
                || this.getConsecutivo()==0){
            throw new Exception("NotaPreoperatoria.getModificarNotaPreoperatoriaGeneral: Error, faltan datos");
        }else{
            sQuery="SELECT * FROM modificarNotaPreoperatoriaGeneral('"+this.sUsuarioFirmado+"'::character varying,"
                    +this.getEpiMed().getPaciente().getFolioPaciente()+"::bigint,"
                    +this.getEpiMed().getPaciente().getClaveEpisodio()+"::bigint,'"
                    +this.getProcedimientosRealizados().getCIE9().getClave()+"'::character varying,"
                    +this.getConsecutivo()+"::smallint,"
                    +(this.getInterrogacion()==null || this.getInterrogacion().equals("")?"null":"'"+this.getInterrogacion()+"'")+"::character varying,"
                    +(this.getResumenEstudios()==null || this.getResumenEstudios().equals("")?"null":"'"+this.getResumenEstudios()+"'")+"::character varying,"
                    +(this.getPLanTerapeuticoPreoperatorio()==null || this.getPLanTerapeuticoPreoperatorio().equals("")?"null":"'"+this.getPLanTerapeuticoPreoperatorio()+"'")+"::character varying,"
                    +(this.getPlanQuirurgico()==null || this.getPlanQuirurgico().equals("")?"null":"'"+this.getPlanQuirurgico()+"'")+"::character varying,"
                    +(this.getRiesgoQuirurgico()==null || this.getRiesgoQuirurgico().equals("")?"null":"'"+this.getRiesgoQuirurgico()+"'")+"::character varying,"
                    +(this.getCuidadosPlanTerapeuticoPreoperatorio()==null || this.getCuidadosPlanTerapeuticoPreoperatorio().equals("")?"null":"'"+this.getCuidadosPlanTerapeuticoPreoperatorio()+"'")+"::character varying);";
        }
        return sQuery;
    }
    
    public String getModificaNotaPreoperatorioSignos() throws Exception{
        String sQuery="";
        if(this.getEpiMed().getPaciente().getFolioPaciente()==0
                || this.getEpiMed().getPaciente().getClaveEpisodio()==0
                || this.getProcedimientosRealizados().getCIE9().getClave().equals("")
                || this.getConsecutivo()==0){
            throw new Exception("NotaPreoperatoria.getModificaNotaPreoperatorioSignos: Error, faltan datos");
        }else{
            sQuery="SELECT * FROM modificaSignosNotaPreoperatoria('"+this.sUsuarioFirmado+"'::character varying,"
                    +this.getEpiMed().getPaciente().getFolioPaciente()+"::bigint,"
                    +this.getEpiMed().getPaciente().getClaveEpisodio()+"::bigint,"
                    +this.getConsecutivo()+"::smallint,"
                    +(this.getSignos().getTA()==null || this.getSignos().getTA().equals("")?"null":"'"+this.getSignos().getTA()+"'")+"::character varying,"
                    +(this.getSignos().getPulso()==0?"null":this.getSignos().getPulso())+"::smallint,"
                    +(this.getSignos().getTemp()==null || this.getSignos().getTemp().equals("")?"null":"'"+this.getSignos().getTemp()+"'")+"::character varying,"
                    +(this.getSignos().getFR()==null || this.getSignos().getFR().equals("")?"null":"'"+this.getSignos().getFR()+"'")+"::character varying);";
        }
        return sQuery;
    }
    
    public int modificaNotaPreoperatoriaGeneral() throws Exception{
        ArrayList arrQuerys= new ArrayList();
        int nRet=0;
        if(this.getEpiMed().getPaciente().getFolioPaciente()==0
                || this.getEpiMed().getPaciente().getClaveEpisodio()==0
                || this.getProcedimientosRealizados().getCIE9().getClave().equals("")
                || this.getConsecutivo()==0){
            throw new Exception("NotaPreoperatoria.modificaNotaPreoperatorioGeneral: Error, faltan datos");
        }else{
            arrQuerys.add(this.getModificarNotaPreoperatoriaGeneral());
            if(this.getSignos().getTA()!=null && !this.getSignos().getTA().equals("") || 
                    this.getSignos().getPulso()!=0 || this.getSignos().getTemp()!=null && 
                    !this.getSignos().getTemp().equals("") || this.getSignos().getFR()!=null && 
                    !this.getSignos().getFR().equals("")){
                arrQuerys.add(this.getModificaNotaPreoperatorioSignos());
            }            
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                nRet=oAD.ejecutarConsultaComando(arrQuerys);
                oAD.desconectar();
            }
            for(Object od:arrQuerys){
                System.out.println((String)od);
            }
        }
        
        return nRet;
    }
    
    public int insertarNotaPreoperatoriaGeneral()throws Exception{
        String sQuery="";
        ArrayList rst=null;
        int nRet=-1;
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat dh= new SimpleDateFormat("HH:mm");
        if(this.getEpiMed().getPaciente().getFolioPaciente()==0 || 
                this.getEpiMed().getPaciente().getClaveEpisodio()==0 ||
                this.getProcedimientosRealizados().getCIE9().getClave().equals("") ||
                this.getDiagnosticoPreoperatorio().getClave().equals("")
                || this.getMedicoSupervisor().getNoTarjeta()==0){
            throw new Exception("NotaPreoperatoria.getInsertarNotaPreoperatoriaGeneral:Error, faltan datos");
        }else{
            sQuery="SELECT * FROM insertaNotaPreoperatoriaGeneral('"+this.sUsuarioFirmado+"'::character varying,"
                    +this.getEpiMed().getPaciente().getFolioPaciente()+"::bigint,"
                    +this.getEpiMed().getPaciente().getClaveEpisodio()+"::bigint,'"
                    +this.getProcedimientosRealizados().getCIE9().getClave()+"'::character varying,'"
                    +this.getDiagnosticoPreoperatorio().getClave()+"'::character varying,"
                    +this.getMedicoSupervisor().getNoTarjeta()+"::integer,'"
                    +f.format(this.getFechaCirugia())+" "+dh.format(this.getHoraCirugia())+"'::timestamp without time zone,"
                    +(this.getInterrogacion()==null || this.getInterrogacion().equals("")?"null":"'"+this.getInterrogacion()+"'")+"::character varying,"
                    +(this.getResumenEstudios()==null || this.getResumenEstudios().equals("")?"null":"'"+this.getResumenEstudios()+"'")+"::character varying,"
                    +(this.getPLanTerapeuticoPreoperatorio()==null || this.getPLanTerapeuticoPreoperatorio().equals("")?"null":"'"+this.getPLanTerapeuticoPreoperatorio()+"'")+"::character varying,"
                    +(this.getPlanQuirurgico()==null || this.getPlanQuirurgico().equals("")?"null":"'"+this.getPlanQuirurgico()+"'")+"::character varying,"
                    +(this.getRiesgoQuirurgico()==null || this.getRiesgoQuirurgico().equals("")?"null":"'"+this.getRiesgoQuirurgico()+"'")+"::character varying,"
                    +(this.getCuidadosPlanTerapeuticoPreoperatorio()==null || this.getCuidadosPlanTerapeuticoPreoperatorio().equals("")?"null":"'"+this.getCuidadosPlanTerapeuticoPreoperatorio()+"'")+"::character varying,"
                    +(this.getSignos().getTA()==null || this.getSignos().getTA().equals("")?"null":"'"+this.getSignos().getTA()+"'")+"::character varying,"
                    +(this.getSignos().getPulso()==0?"null":this.getSignos().getPulso())+"::smallint,"
                    +(this.getSignos().getTemp()==null || this.getSignos().getTemp().equals("")?"null":"'"+this.getSignos().getTemp()+"'")+"::character varying,"
                    +(this.getSignos().getFR()==null || this.getSignos().getFR().equals("")?"null":"'"+this.getSignos().getFR()+"'")+"::character varying);";
            
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                ArrayList vR=(ArrayList)rst.get(0);
                nRet=((Double)vR.get(0)).intValue();
            }
        }
        //System.out.println(sQuery);
        return nRet;
    }
    
    public NotaPreoperatoria[] buscaHistorialNotaPreoperatoria(long folioPac) throws Exception{
        NotaPreoperatoria arrRet[]=null, oNP=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("NotaPreoperatoria.buscaHistorialNotaPreoperatoria: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialnotaspreoperatorias("+folioPac+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new NotaPreoperatoria[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNP= new NotaPreoperatoria();
                    oNP.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNP.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNP.setConsecutivo((((Double)vRowTemp.get(2)).intValue()));
                    oNP.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(3));
                    oNP.getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(4));
                    oNP.setFechaRegistro((Date)vRowTemp.get(5));
                    arrRet[i]=oNP;
                } 
            } 
        }
        return arrRet; 
    }
    
    
    public boolean buscarDetalleNotaPreoperatoria() throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        String sQuery="", edad="";
        if(this.getEpiMed().getPaciente().getFolioPaciente()==0 
                || this.getEpiMed().getPaciente().getClaveEpisodio()==0
                || this.getProcedimientosRealizados().getCIE9().getClave().equals("")
                || this.getConsecutivo()==0){
            throw new Exception("NotaPreoperatoria.buscarDetalleNotaPreoperatoria: Error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscainformacionnotapreoperatoriaEXP("
                    +this.getEpiMed().getPaciente().getFolioPaciente()+"::bigint,"
                    +this.getEpiMed().getPaciente().getClaveEpisodio()+"::bigint,"
                    +this.getConsecutivo()+"::smallint,'"
                    +this.getProcedimientosRealizados().getCIE9().getClave()+"'::character varying);";             
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                ArrayList vRowTemp=(ArrayList)rst.get(0);
                this.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                this.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                this.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(2));
                this.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(3));
                this.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(4));                
                switch ((String)vRowTemp.get(5)) {
                    case "M":
                        this.getEpiMed().getPaciente().setSexoP("MASCULINO");
                        break;
                    case "F":
                        this.getEpiMed().getPaciente().setSexoP("FEMENINO");
                        break;
                    default:
                        this.getEpiMed().getPaciente().setSexoP("NO ESPECIFICADO");
                        break;
                }
                edad=(String)vRowTemp.get(6);
                if (edad.compareTo("")!=0){
                if(edad.substring(0, 3).compareTo("000")!=0){
                    if (edad.charAt(0)=='0')
                       this.getEpiMed().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                    else
                       this.getEpiMed().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                }else{
                    if (edad.substring(4, 6).compareTo("00")!=0)
                        this.getEpiMed().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                    else
                        this.getEpiMed().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                }
                this.setFechaRealizacion((Date)vRowTemp.get(7));
                this.getMedicoSupervisor().setCedProf((String)vRowTemp.get(8));
                this.getProcedimientosRealizados().getCIE9().setDescripcion((String)vRowTemp.get(9));
                this.getProcedimientosRealizados().getCIE9().setClave((String)vRowTemp.get(10));
                this.getDiagnosticoPreoperatorio().setDescripcionDiag((String)vRowTemp.get(11));
                this.getDiagnosticoPreoperatorio().setClave((String)vRowTemp.get(12));
                this.getSignos().setTA((String)vRowTemp.get(13));
                this.getSignos().setPulso(((Double)vRowTemp.get(14)).intValue());
                this.getSignos().setTemp((String)vRowTemp.get(15));
                this.getSignos().setFR((String)vRowTemp.get(16));
                this.setInterrogacion((String)vRowTemp.get(17));
                this.setResumenEstudios((String)vRowTemp.get(18));
                this.setPLanTerapeuticoPreoperatorio((String)vRowTemp.get(19));
                this.setPlanQuirurgico((String)vRowTemp.get(20));
                this.setRiesgoQuirurgico((String)vRowTemp.get(21));
                this.setCuidadosPlanTerapeuticoPreoperatorio((String)vRowTemp.get(22));
                this.setFechaCirugia((Date)vRowTemp.get(23));
                this.setHoraCirugia((Date)vRowTemp.get(24));
                this.setConsecutivo(((Double)vRowTemp.get(25)).intValue());
                this.getMedicoSupervisor().setNoTarjeta(((Double)vRowTemp.get(26)).intValue());
                this.getMedicoSupervisor().setNombres((String)vRowTemp.get(27));
                this.getMedicoSupervisor().setApPaterno((String)vRowTemp.get(28));
                this.getMedicoSupervisor().setApMaterno((String)vRowTemp.get(29));
                bRet=true;
            }
        }
        return bRet;
    }
    
    public boolean getHabilitaMedico(){
        return this.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().getNoTarjeta() != 0;        
    }
    public boolean getHabilitaCirugia(){
        return this.getDescripcionCirugia().getClaveParametro().compareTo("") != 0;
    }
    public boolean getHabilitaCompletePreoperatorio(){        
            return this.getDiagnosticoPreoperatorio().getClave().compareTo("") != 0;        
        }
} 

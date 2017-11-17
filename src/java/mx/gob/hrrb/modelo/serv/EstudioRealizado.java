package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 * @author : Pablo
 * @version: 1.0
*/

public class EstudioRealizado extends ServicioRealizado implements Serializable{
protected String sUsuarioFirmado;
protected boolean bPortatil;
protected Date dFechaProgramado;
protected Date dFechaRecepcion;
protected AreaServicioHRRB oArea;
protected DiagnosticoCIE10 oDiag;
protected Parametrizacion oMedioContraste;
protected Parametrizacion oTipoPlaca;
protected Parametrizacion oTipoProcedimiento;
protected String sEspecimenMuestraTejido;
protected String sImpresionDiagnostica;
protected Estudios oEstudio;
protected Recibo oRecibo;
protected String sOtrodx;
protected String sFechaCompleta;
protected String sRutaArchRpt;

    public EstudioRealizado(){
        oEstudio = new Estudios();
        //oEpisodio = new EpisodioMedico();
        //JMHG
                    oArea = new AreaServicioHRRB();
                    oEstudio = new Estudios();
                    oEpisodio = new EpisodioMedico();
                    //---
        HttpServletRequest req;
                req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
                if (req.getSession().getAttribute("oFirm") != null) {
                        sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
                }
    }
    @Override
    public boolean buscar() throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("EstudioRealizado.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveEstudioRealizado();"; 
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
    public EstudioRealizado[] buscarTodos() throws Exception{
    EstudioRealizado arrRet[]=null, oEstudioRealizado=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosEstudioRealizado();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new EstudioRealizado[rst.size()];
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
                    throw new Exception("EstudioRealizado.insertar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaEstudioRealizado('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("EstudioRealizado.modificar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaEstudioRealizado('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("EstudioRealizado.eliminar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaEstudioRealizado('"+sUsuarioFirmado+"');"; 
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

    ////
    public EstudioRealizado[] buscaAuxiliarDeDiag(EpisodioMedico epi) throws Exception{
    EstudioRealizado arrRet[]=null, oEstudioRealizado=null;
    ArrayList rst = null;
    String sQuery = "select * from buscaAuxiliarDiagnostiReferencia("+epi.getPaciente().getFolioPaciente()+","+epi.getClaveEpisodio()+");";
    String tipoTabla;
    String tipoValor;
    int i=0; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
            if (rst != null && rst.size()>0) {
                    arrRet = new EstudioRealizado[rst.size()];
                    for (i = 0; i < rst.size(); i++) { 
                        oEstudioRealizado=new EstudioRealizado(); 
                            oTipoProcedimiento =new Parametrizacion();
                            oEstudioRealizado.oEstudio = new Estudios();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEstudioRealizado.setFechaSolicitud((Date) vRowTemp.get(0)); 
                            oEstudioRealizado.setFechaSurtidoRealizacion((Date) vRowTemp.get(1));
                            oEstudioRealizado.setImpresionDiagnostica((String)vRowTemp.get(2));
                            tipoTabla=(String)vRowTemp.get(3);
                            tipoValor=(String)vRowTemp.get(4); 
                            oEstudioRealizado.oEstudio.setClasifDeEstudio(
                                    oTipoProcedimiento.buscaValorParametrizado(
                                            tipoTabla+tipoValor)); 
                            arrRet[i]=oEstudioRealizado;
			} 
		} 
		return arrRet; 
    }
    
    public EstudioRealizado[] buscarEstudiosRealizadosPreviosFechas(
            long nFolioPac, long nCveEpiMed, Date fechaIni, Date fechaFin) 
            throws Exception{
    EstudioRealizado  arrRet[] = null, oEstReal = null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");           
    ArrayList <EstudioRealizado> vObj = null;
    String sQuery = "";     
    int i=0, nTam=0;    
        if(nFolioPac==0){
            throw new Exception("EstudioRealizado.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarImpresionDiagnosticaImagenNotaMedica("
                    +nFolioPac+"::bigint,"+nCveEpiMed+"::bigint,'"+ 
                    oFec.format(fechaIni)+"','"+oFec.format(fechaFin)+"');"; 
                        oAD=new AccesoDatos(); 
                        if (oAD.conectar()){
                                rst = oAD.ejecutarConsulta(sQuery); 
                                oAD.desconectar(); 
                        }
                        if(rst != null){
                            vObj = new ArrayList<EstudioRealizado>();
                            for(i=0; i<rst.size(); i++){
                                oEstReal=new EstudioRealizado();                       
                                ArrayList vRowTemp=(ArrayList)rst.get(i);
                                oEstReal.setImpresionDiagnostica((String) vRowTemp.get(0));
                                oEstReal.getEstudio().setConcepto((String) vRowTemp.get(1));
                                oEstReal.setFechaSolicitud((Date) vRowTemp.get(2));

                            vObj.add(oEstReal);
                        }
                        nTam = vObj.size();
                        arrRet = new EstudioRealizado[nTam];
                        for (i = 0; i < nTam; i++) {
                            arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
        }
    
    public EstudioRealizado[] buscarRangoFechas(long nFolioPac, long nCveEpiMed, 
    Date fechaIni, Date fechaFin) throws Exception{
    EstudioRealizado  arrRet[] = null, oEstReal = null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");           
    ArrayList <EstudioRealizado> vObj = null;
    String sQuery = "";     
    int i=0, nTam=0;    
            if(nFolioPac==0){
                throw new Exception("EstudioRealizado.buscarRangoFechas: error de programación, faltan datos");
            }else{
                sQuery = "SELECT * FROM buscarDescargaResultadosEstudios("+
                        nFolioPac+"::bigint,"+nCveEpiMed+"::bigint,'"+ 
                        oFec.format(fechaIni)+"','"+oFec.format(fechaFin)+"');";
                        oAD=new AccesoDatos(); 
                        if (oAD.conectar()){
                                rst = oAD.ejecutarConsulta(sQuery); 
                                oAD.desconectar(); 
                        }
                        if(rst != null){
                            vObj = new ArrayList<EstudioRealizado>();
                            for(i=0; i<rst.size(); i++){
                                oEstReal=new EstudioRealizado();                       
                                ArrayList vRowTemp=(ArrayList)rst.get(i);
                                oEstReal.setRutaArchRpt((String) vRowTemp.get(0));
                                oEstReal.getEstudio().setConcepto((String) vRowTemp.get(1));
                                oEstReal.setFechaSolicitud((Date) vRowTemp.get(2));

                            vObj.add(oEstReal);
                        }
                        nTam = vObj.size();
                        arrRet = new EstudioRealizado[nTam];
                        for (i = 0; i < nTam; i++) {
                            arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
        }
    
    public EstudioRealizado[] buscarOrdenesPrevias(long folioPac, 
            long cveEpiMed, Date fechaIni,Date fechaFin) throws Exception{
	EstudioRealizado arrRet[]=null, oEstReal=null;
	ArrayList rst = null;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd"); 
        ArrayList<EstudioRealizado> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
            if( folioPac==0 ||cveEpiMed==0){
                throw new Exception("EstudioRealizado.buscarOrdenesPrevias: faltan datos");
            }else{
                    sQuery = "SELECT * FROM buscarserviciosrealizadosprevios("+
                            folioPac+"::bigint,"+cveEpiMed+"::bigint,'"+ 
                            oFec.format(fechaIni) +"','"+ 
                            oFec.format(fechaFin) +"');";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<EstudioRealizado>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oEstReal = new EstudioRealizado();
                                oEstReal.setEstudio(new Estudios());
                                oEstReal.getEstudio().setClasificacion(new Parametrizacion());
                                oEstReal.setSituacion(new Parametrizacion());
                                oEstReal.getEpisodio().setProceRe1(new ProcedimientosRealizados());
                                oEstReal.getEpisodio().getProceRe1().setCIE9(new ProcedimientoCIE9());
                                oEstReal.setIdentificador(((Double) vRowTemp.get(0)).intValue());
                                oEstReal.setFechaSolicitud((Date) vRowTemp.get(1));
                                oEstReal.getEstudio().getClasificacion().setValor((String) vRowTemp.get(2));
                                oEstReal.getEstudio().setConcepto((String) vRowTemp.get(3));
                                oEstReal.getEpisodio().getProceRe1().getCIE9().setDescripcion((String) vRowTemp.get(4));
                                oEstReal.getSituacion().setValor((String) vRowTemp.get(5));
                                
				vObj.add(oEstReal);                              
                            }
                            nTam = vObj.size();
                            arrRet = new EstudioRealizado[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
    
    public EstudioRealizado[] buscarEstudiosOto(String EstOto) throws Exception {
        EstudioRealizado arrRet[] = null, oEst = null;
        ArrayList rst = null;
        ArrayList<EstudioRealizado> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarestudiosdesolicitudotoacusticos('" + EstOto + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<EstudioRealizado>();
            for (i = 0; i < rst.size(); i++) {
                oEst = new EstudioRealizado();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEst.getEstudio().setConcepto(((String) vRowTemp.get(0)).trim());
                vObj.add(oEst);
            }
            nTam = vObj.size();
            arrRet = new EstudioRealizado[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public List<EstudioRealizado> getListaEstudiosOto(String txt) {
        List<EstudioRealizado> lListaEst = null;
        try {
            lListaEst = new ArrayList<EstudioRealizado>(Arrays.asList(
                    (new EstudioRealizado()).buscarEstudiosOto(txt)));
        } catch (Exception ex) {
            Logger.getLogger(EstudioRealizado.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaEst;
    }
        public List<String> completarEstOto(String sTxt) throws Exception {
        ArrayList<String> arrRet = new ArrayList<String>();
        List<EstudioRealizado> lis = getListaEstudiosOto(sTxt);
        for (EstudioRealizado li : lis) {
            if (sp_ascii(li.getEstudio().getConcepto()).contains(sTxt)) {
                arrRet.add(li.getEstudio().getConcepto());
            }
        }
        return arrRet;
    }
        
        

    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public boolean buscarClavesEstudiosOto() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscarestudiosotoacusticosclaves('" + this.getEstudio().getConcepto() + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.getEstudio().setClaveInterna(((Double) vRowTemp.get(0)).intValue());
            this.getEstudio().setClaveStudio(((String) vRowTemp.get(1)).trim());
            bRet = true;
        }
        return bRet;
    }

    public String getInsertaSolOto() throws Exception {
        String sQuery = "";
        if (getEstudio().getClaveInterna() == 0) {
            throw new Exception("Estudio.Modificar: error, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            sQuery = "SELECT * FROM insertasolicitudotoacusticos('" + sUsuarioFirmado + "'::character varying,"
                    + this.getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + getEstudio().getClaveInterna() + "::smallint,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEpisodio().getDiagIngreso().getClave() + "');";
        }
        return sQuery;
    }

    public void insertaEstudiosOto(ArrayList<EstudioRealizado> arrEstudioOto) throws Exception {
        System.out.println("Dentro del método insertaEstudiosOto");
        ArrayList<String> vSolicLab = new ArrayList<>();
        int nRegAfectados = 0;
        for (EstudioRealizado Es : arrEstudioOto) {
            vSolicLab.add(Es.getInsertaSolOto());
        }
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            nRegAfectados = oAD.ejecutarConsultaComando(vSolicLab);
            oAD.desconectar();
        }
    }

    public int insertarElectro() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        System.out.println("Dentro de insertarElectro: " + this.getEpisodio().getPaciente().getFolioPaciente());
        if (this == null) {
            throw new Exception("EstudioRealizado.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertasolicitudelectro('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + 355 + "::smallint,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEpisodio().getDiagIngreso().getClave() + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
        
        ////
        
        
	public boolean getPortatil() {
	return bPortatil;
	}

	public void setPortatil(boolean valor) {
	bPortatil=valor;
	}

	public Date getFechaProgramado() {
	return dFechaProgramado;
	}

	public void setFechaProgramado(Date valor) {
	dFechaProgramado=valor;
	}

	public Date getFechaRecepcion() {
	return dFechaRecepcion;
	}

	public void setFechaRecepcion(Date valor) {
	dFechaRecepcion=valor;
	}

	public AreaServicioHRRB getArea() {
	return oArea;
	}

	public void setArea(AreaServicioHRRB valor) {
	oArea=valor;
	}

	public DiagnosticoCIE10 getDiag() {
	return oDiag;
	}

	public void setDiag(DiagnosticoCIE10 valor) {
	oDiag=valor;
	}

	public Parametrizacion getMedioContraste() {
	return oMedioContraste;
	}

	public void setMedioContraste(Parametrizacion valor) {
	oMedioContraste=valor;
	}

	public Parametrizacion getTipoPlaca() {
	return oTipoPlaca;
	}

	public void setTipoPlaca(Parametrizacion valor) {
	oTipoPlaca=valor;
	}

	public Parametrizacion getTipoProcedimiento() {
            return oTipoProcedimiento;
	}

	public void setTipoProcedimiento(Parametrizacion valor) {
            oTipoProcedimiento=valor;
	}
        
	public String getEspecimenMuestraTejido() {
            return sEspecimenMuestraTejido;
	}

	public void setEspecimenMuestraTejido(String valor) {
	sEspecimenMuestraTejido=valor;
	}

	public String getImpresionDiagnostica() {
	return sImpresionDiagnostica;
	}

	public void setImpresionDiagnostica(String valor) {
	sImpresionDiagnostica=valor;
	}

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oEstudio;
    }
    

    public Estudios getEstudio() {
        return ((Estudios) getServicioCobrable());
    }

    public void setEstudio(Estudios oEstudio) {
        this.setServicioCobrable(oEstudio);
    }

    public ServicioRealizado getServReal() {
        return this;
    }

    public Recibo getRecibo() {
        return oRecibo;
    }

    public void setRecibo(Recibo oRecibo) {
        this.oRecibo = oRecibo;
    }

    
    @Override
    public void setServicioCobrable(ServicioCobrable oValor) {
        this.oEstudio = (Estudios) oValor;
    }

    public String getOtrodx() {
        return sOtrodx;
    }

    public void setOtrodx(String sOtrodx) {
        this.sOtrodx = sOtrodx;
    }
    
    public EstudioRealizado[] buscarPacientesCitadosPorServicio(int nCveArea, Date dFecCita) throws Exception{
        EstudioRealizado arrRet[]=null, oEst=null;
        ArrayList<EstudioRealizado> vObj = null;
        ArrayList rst = null;
        String sQuery="";
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd"); 
        SimpleDateFormat oFec2 = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        int i=0, nTam=0;
        if(nCveArea == 0 &&  dFecCita == null){
            throw new Exception("EstudioRealizado.buscarPacientesCitadosPorServicio: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarPacientesCitadosPorServicio("+ nCveArea +"::smallint, '"+ oFec.format(dFecCita) +"');";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<EstudioRealizado>();
                for(i=0;i<rst.size();i++){
                    oEst = new EstudioRealizado();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oEst.oEpisodio = new EpisodioMedico();
                    oEst.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oEst.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oEst.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oEst.getEstudio().setDescripcion((String)vRowTemp.get(3));
                    oEst.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                    oEst.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(5));
                    Date dFecha = ((Date)vRowTemp.get(6));
                    oEst.setFechaCompleta(oFec2.format(dFecha));
                    vObj.add(oEst);
                }
                
                nTam = vObj.size();
                arrRet = new EstudioRealizado[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            } 
        }
        return arrRet;
    }
    
    
    public EstudioRealizado[] buscaHistorialEstudiosRealizadosExp(long foliopac, int clave) throws Exception{
        EstudioRealizado arrRet[]=null, oEstReal=null;
        ArrayList rst=null;
        String sQuery="";
        /*clave
        1 LABORATORIALES
        2 IMAGENOLOGIA
        3 PATOLOGIA
        4 ENDOSCOPIA, SOLICITUD DE PROCEDIMIENTO QUIRÚRGICO
        5 OTROS:COLPOSCOPÍA, ESTUDIOS OTOACúSTICOS, ELECTROCARDIOGRAMA, SOLICITUD DE TRANFUSION AMBULATORIA, SOLICITUD DE PRODUCTOS DE BANCO DE SANGRE, OTROS
        6 TODOS LOS ESTUDIOS REALIZADOS*/
        int i=0;
        if(foliopac==0 && clave==0){
            throw new Exception("EstudioRealizado.buscaHistorialEstudiosRealizadosExp: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarhistorialestudiosrealizadosEXP("+foliopac+"::BIGINT, "+clave+"::SMALLINT);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                arrRet = new EstudioRealizado[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oEstReal= new EstudioRealizado();
                    oEstReal.oEpisodio = new EpisodioMedico();
                    //oEstReal.oEpisodio.setPaciente(new Paciente());
                    oEstReal.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oEstReal.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oEstReal.setIdentificador(((Double)vRowTemp.get(2)).intValue());
                    oEstReal.setEstudioClaveInterna(((Double)vRowTemp.get(3)).intValue());
                    oEstReal.getEstudio().setDescripcion((String)vRowTemp.get(4));
                    oEstReal.setFechaSolicitud((Date)vRowTemp.get(5));
                    oEstReal.setDiag(new DiagnosticoCIE10());
                    oEstReal.getDiag().setClave((String)vRowTemp.get(6));
                    oEstReal.getDiag().setDescripcionDiag((String)vRowTemp.get(7));
                    oEstReal.setRutaArchRpt((String)vRowTemp.get(8));
                    oEstReal.setImpresionDiagnostica((String)vRowTemp.get(9));
                    oEstReal.getEstudio().setClasificacion(new Parametrizacion());
                    oEstReal.getEstudio().getClasificacion().setValor((String)vRowTemp.get(10));
                    oEstReal.getEstudio().getClasificacion().setClaveParametro((String)vRowTemp.get(11));
                    arrRet[i]=oEstReal;
                }
            }
        }
        return arrRet;
    }
    
    
    // JMHG
    public EstudioRealizado[] buscarEstudiosServPac() throws Exception
    {
        EstudioRealizado oEstudiosPac, arrRet[] = null;
        ArrayList<EstudioRealizado> vObj = null;
        ArrayList rst = null;
        long nFolio;
        int nClaveArea;
        int i;
        int nTam;
        
        String sQuery = "";
        
        nClaveArea = oArea.getClave();
        nFolio = oEpisodio.getPaciente().getFolioPaciente();
        
        sQuery = "SELECT * FROM buscaPacEstServPagadoSinCita(" +
            nFolio + "::bigint, " +
            nClaveArea + "::smallint);";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if( oAD.conectar() )
        {
            rst = oAD.ejecutarConsulta( sQuery );
            oAD.desconectar();
        }
        if( rst != null || rst.size() > 0 )
        {
            vObj = new ArrayList<EstudioRealizado>();
            for( i = 0; i < rst.size(); i++ )
            {
                ArrayList vRowTemp = (ArrayList)rst.get( i );
                oEstudiosPac = new EstudioRealizado();
                oEstudiosPac.oArea.setClave( nClaveArea );
                //oEstudiosPac.oEpisodio = new EpisodioMedico();
                oEstudiosPac.oEpisodio.getPaciente().setApPaterno( (String)vRowTemp.get( 0 ) );
                oEstudiosPac.oEpisodio.getPaciente().setApMaterno( (String)vRowTemp.get( 1 ) );
                oEstudiosPac.oEpisodio.getPaciente().setNombres( (String)vRowTemp.get( 2 ) );
                oEstudiosPac.oEpisodio.getPaciente().getExpediente().setNumero( ( (Double)vRowTemp.get( 3 ) ).intValue() );
                oEstudiosPac.oEstudio.setDescripcion( (String)vRowTemp.get( 4 ) );
                oEstudiosPac.nIdentificador = ( (Double)vRowTemp.get( 5 ) ).intValue();
                oEstudiosPac.oEstudio.setClaveInterna( ( (Double)vRowTemp.get( 6 ) ).intValue() );
                oEstudiosPac.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(7)).longValue());
                oEstudiosPac.oEpisodio.getPaciente().getSeg().setNumero((String) vRowTemp.get(8));
                //ADDED 12/05/16
                oEstudiosPac.oEpisodio.getPaciente().setFolioPaciente( nFolio );
                vObj.add( oEstudiosPac );
            }
        }
        nTam = vObj.size();
        arrRet = new EstudioRealizado[ nTam ];
        
        for( i = 0; i < nTam; i++ )
        {
            arrRet[ i ] = vObj.get( i );
        }
        
        return arrRet;
    }
    
    public long buscarTotalCitados() throws Exception
    {
        long nRet = -1;
        ArrayList rst = null;
        String sQuery = "";
        
        if( dFechaProgramado == null || oArea == null ||
            oArea.getClave() < 1 || oArea.getTurno() == null )
        {
            throw new Exception( "EstudioRealizado.buscarTotalCitados: error, faltan datos." );
        }
        else
        {
            SimpleDateFormat sf = new SimpleDateFormat( "yyyy-MM-dd" );
            String sFecha = sf.format( dFechaProgramado );
            String sTurno = oArea.getTurno().buscaHorarioTurno( 1 ); // busca la hora del turno establecido en oTurno
            String sFechaTurno = sFecha + " " + sTurno;
            
            sQuery = "SELECT * FROM buscarCitadosEstudiosRealAreaFecha(" +
                oArea.getClave() + "::smallint, '" + sFechaTurno + "');";
            
            oAD = new AccesoDatos();
            if( oAD.conectar() )
            {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                
                if( rst != null && rst.size() == 1 )
                {
                    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
                    nRet = ( (Double)vRowTemp.get( 0 ) ).longValue();
                }
            }
        }
        
        return nRet;
    }
    
    public int insertaCitaServicio() throws Exception //insertaCitaImagen
    {
        int nRet = -1;
        ArrayList rst = null;
        String sQuery = "";
        
        if( nIdentificador == 0 || oEstudio.getClaveInterna() == 0 || dFechaProgramado == null )
        {
            throw new Exception( "EstudioRealizado.insertaCitaServ: error, faltan datos." );
        }
        else
        {
            sQuery = "SELECT * FROM insertaCitaServ(" + 
                nIdentificador + "::bigint, " +
                oEstudio.getClaveInterna() + "::smallint, " +
                "CAST('" + dFechaProgramado + "' AS TIMESTAMP));";
            
            oAD = new AccesoDatos();
            if( oAD.conectar() )
            {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                
                if( rst != null && rst.size() == 1 )
                {
                    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
                    nRet = ( (Double)vRowTemp.get( 0 ) ).intValue();
                }
            }
        }
        return nRet;
    }
    
    public int insertaCitasLab() throws Exception
    {
        int nRet = -1;
        long nFolio;
        int nClaveArea;
        ArrayList rst = null;
        String sQuery = "";
        
        if( oEpisodio == null || oArea == null || dFechaProgramado == null )
        {
            throw new Exception( "EstudioRealizado.insertaCitasLab: error, faltan datos." );
        }
        else
        {
            nFolio = oEpisodio.getPaciente().getFolioPaciente();
            nClaveArea = oArea.getClave();
            sQuery = "SELECT * FROM insertaCitasLab(" + 
                nFolio + "::bigint, " +
                nClaveArea + "::smallint, " +
                "CAST('" + dFechaProgramado + "' AS TIMESTAMP),"+ this.oEpisodio.getClaveEpisodio() +"::bigint);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if( oAD.conectar() )
            {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                
                if( rst != null && rst.size() == 1 )
                {
                    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
                    nRet = ( (Double)vRowTemp.get( 0 ) ).intValue();
                }
            }
        }
        return nRet;
    }
    
    public void horarioTurno() throws Exception
    {
        SimpleDateFormat formato;
        String sFecha = "";
        String sHora = "";
        String sFechaHora = "";
        if( oArea == null || oArea.getTurno() == null )
        {
            throw new Exception( "EstudioRealizado.horarioTurno: Error, faltan datos." );
        }
        else
        {
            formato = new SimpleDateFormat( "yyyy-MM-dd" );
            sFecha = formato.format( dFechaProgramado );
            sHora = oArea.getTurno().buscaHorarioTurno( 1 );
            
            sFechaHora = sFecha + " " + sHora;
            formato = new SimpleDateFormat( "yyyy-MM-dd HH:mm" );
            dFechaProgramado = formato.parse( sFechaHora );
        }
    }
    
    public void setEstudioClaveInterna( int nEstudioClaveInterna )
    {
        this.oEstudio.setClaveInterna( nEstudioClaveInterna );
    }
    
    public int getEstudioClaveInterna()
    {
        return oEstudio.getClaveInterna();
    }
    
    public void setEstudioDescripcion( String sEstudioDescricpion )
    {
        this.oEstudio.setDescripcion( sEstudioDescricpion );
    }
    
    public String getEstudioDescripcion()
    {
        return oEstudio.getDescripcion();
    }
    //----

    public String getFechaCompleta() {
        return sFechaCompleta;
    }

    public void setFechaCompleta(String sFechaCompleta) {
        this.sFechaCompleta = sFechaCompleta;
    }

    public String getRutaArchRpt() {
        return sRutaArchRpt;
    }

    public void setRutaArchRpt(String sRutaArchRpt) {
        this.sRutaArchRpt = sRutaArchRpt;
    }
} 

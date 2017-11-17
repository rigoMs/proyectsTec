package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class NotaMedicaHRRB implements Serializable{
	private AccesoDatos oAD;
        protected EpisodioMedico oEpiMed;
        protected PersonalHospitalario oMedFirm;
        protected Medico oMedicoSupervisor;
	protected String sUsuarioFirmado;
        protected String sResumenInter;
        protected String sExploracionFisica;
        protected String sResultadosServAuxDiag;
        protected String sPlanDeTratamiento;
        protected String sPronostico;
        protected String sIndicacionTer;
        protected String sResumenMotivoRef;
        protected String sPronosticoMotivoRef;
        private ProcedimientoCIE9 oCIE9;
        protected int nConsecutivo;
//***************INICIAN ATRIBUTOS AGREGADO****************
        protected Date dFechaRegistro;
        protected String sComentarios;
        protected String sImpresionDiagnostica;
        protected String sResumenEstadoMental;
        protected String sIncidentesAccidentes;
        protected int nMaxConsecutivo;
//***************TERMINAN ATRIBUTOS AGREGADO****************
	

	public NotaMedicaHRRB(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oMedFirm = new PersonalHospitalario();
                oCIE9 = new ProcedimientoCIE9();
                oEpiMed = new EpisodioMedico();
                oMedicoSupervisor = new Medico();
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaMedicaHRRB.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveNotaMedicaHRRB();"; 
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
	public NotaMedicaHRRB[] buscarTodos() throws Exception{
	NotaMedicaHRRB arrRet[]=null, oNotaMedicaHRRB=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosNotaMedicaHRRB();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new NotaMedicaHRRB[rst.size()];
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
			throw new Exception("NotaMedicaHRRB.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaMedicaHRRB('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaMedicaHRRB.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaNotaMedicaHRRB('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaMedicaHRRB.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaMedicaHRRB('"+sUsuarioFirmado+"');"; 
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
        
        public int insertaNotaMedica(ArrayList<DiagnosticoCIE10> lNotaMed, 
                ArrayList<DetalleRecetaHRRB> arrMed)throws Exception{
        int mod = 0;
        ArrayList rst = null, vRowTemp = null;
        ArrayList<Medicamento> lMed = new ArrayList<Medicamento>(); 
        String sQuery="";
            if(getEpiMed() == null){
                throw new Exception("NotaMedicaHRRB.insertaNotaMedica: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM insertanotamedicahrrbarr('" + 
                        sUsuarioFirmado + "'::character varying,"
                        + getEpiMed().getPaciente().getFolioPaciente() + "::bigint,"
                        + getEpiMed().getClaveEpisodio() + "::bigint,"
                        + getMedFirm().getNoTarjeta() + "::integer,'"
                        + getEpiMed().getSignosVitales().getTA() + "'::character varying,'"
                        + getEpiMed().getSignosVitales().getFC() + "'::character varying,'"
                        + getEpiMed().getSignosVitales().getFR() + "'::character varying,'"
                        + getEpiMed().getSignosVitales().getTemp() + "'::character varying,"
                        + getEpiMed().getPaciente().getPeso() + "::numeric,"
                        + getEpiMed().getPaciente().getTalla() + "::numeric,'"
                        + sResumenInter + "'::text,'"
                        + sExploracionFisica + "'::text,'"
                        + sResultadosServAuxDiag + "'::text,ARRAY["; 
                if(!lNotaMed.isEmpty()){
                     for(DiagnosticoCIE10 nm:lNotaMed){
                       if(nm.getClave() != null){
                           sQuery = sQuery + "'" + nm.getClave() + "', ";
                       }
                   }
                   sQuery = sQuery.substring(0, sQuery.length()-2);
                   sQuery = sQuery + "]::character(6)[],'";
                }else{
                    sQuery = sQuery + "''" + "]::character(6)[],'";
                }
                sQuery = sQuery
                + sPlanDeTratamiento + "'::text,'"
                + getCIE9().getClave() + "'::character(6),'"
                + sPronostico + "'::text, ";
                if(!arrMed.isEmpty()){
                    for(int i=0;i<arrMed.size();i++){
                        if(i==0){
                            System.out.println("Valor de i: " + i);
                            sQuery = sQuery + "'CLAVE: " 
                                + arrMed.listIterator(i).next().getMedicamento().getClaveMedicamento() + ", NOMBRE MEDICAMENTO: " 
                                + arrMed.listIterator(i).next().getMedicamento().getNombre() + ", PRESENTACION: "
                                + arrMed.listIterator(i).next().getMedicamento().getPresentacion() + ", DOSIS: "
                                + arrMed.listIterator(i).next().getDosis() +", VIA DE ADMINISTRACION: "
                                + arrMed.listIterator(i).next().getVia().getValor() +", FRECUENCIA: "
                                + arrMed.listIterator(i).next().getFrecuencia() + ", DURACION: "
                                + arrMed.listIterator(i).next().getDuracion() + ", INDICACIÓN TERAPEUTICA: "
                                + this.getIndicacionTer() +"; ";
                        }else if (i > 0){
                            System.out.println("Valor de i: " + i);
                            sQuery = sQuery + "CLAVE: " + arrMed.listIterator(i).next().getMedicamento().getClaveMedicamento() + ", NOMBRE MEDICAMENTO: " 
                                + arrMed.listIterator(i).next().getMedicamento().getNombre() + ", PRESENTACION: "
                                + arrMed.listIterator(i).next().getMedicamento().getPresentacion() + ", DOSIS: "
                                + arrMed.listIterator(i).next().getDosis() +", VIA DE ADMINISTRACION: "
                                + arrMed.listIterator(i).next().getVia().getValor() +", FRECUENCIA: "
                                + arrMed.listIterator(i).next().getFrecuencia() + ", DURACION: "
                                + arrMed.listIterator(i).next().getDuracion() + ", INDICACIÓN TERAPEUTICA: "
                                + this.getIndicacionTer() +"; ";
                        }
                    }
                    sQuery = sQuery + "'::text);";
                }else{
                    sQuery = sQuery + "'" + "'::text);";
                }                                               

                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();

                    if(rst != null && rst.size() == 1){
                        vRowTemp = (ArrayList) rst.get(0);
                        mod = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
                    return mod;
            }
        }
        
        public NotaMedicaHRRB[] buscarMotivoReferencia(long FolioPac, long CveEpiMed) throws Exception{
	NotaMedicaHRRB arrRet[]=null, oMotivo=null;
	ArrayList rst = null;
        ArrayList<NotaMedicaHRRB> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		 if( this==null){
			throw new Exception("NotaMedicaHRRB.buscar: error de programación, faltan datos");
		}else{
                            sQuery = "SELECT * FROM buscaresumencliniypronosticopac("+FolioPac+"::bigint,"+CveEpiMed+"::bigint);";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<NotaMedicaHRRB>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oMotivo=new NotaMedicaHRRB();
                                this.setResumenMotivoRef((String) vRowTemp.get(0));
                                this.setPronosticoMotivoRef((String) vRowTemp.get(1));
                                
				vObj.add(oMotivo);                              
                            }
                            nTam = vObj.size();
                            arrRet = new NotaMedicaHRRB[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
        
    public NotaMedicaHRRB[] buscaHistorialNotaIngresoEvolucionEXP(long folioPac) throws Exception{
        NotaMedicaHRRB arrRet[]=null, oNotaMed=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("NotaMedicaHRRB.buscaHistorialNotaIngresoEvolucionEXP: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialnotaingresoevolucion("+folioPac+"::BIGINT);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new NotaMedicaHRRB[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNotaMed= new NotaMedicaHRRB();
                    oNotaMed.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNotaMed.getEpiMed().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNotaMed.setConsecutivo(((Double)vRowTemp.get(2)).intValue()); 
                    oNotaMed.setFechaRegistro((Date)vRowTemp.get(3));
                    oNotaMed.getEpiMed().getSignosVitales().setNgVerbal(((Double)vRowTemp.get(4)).intValue());
                    arrRet[i]=oNotaMed;
                } 
            } 
        }
        return arrRet; 
    }
    
    public void buscaDetallesNotaMedicaIngresoEvolucion() throws Exception{
        ArrayList rst = null; 
        String sQuery, edad;
        if(getEpiMed().getPaciente().getFolioPaciente()==0 && getEpiMed().getClaveEpisodio()==0 && getConsecutivo()==0)
            throw new Exception("NotaMedicaHRRB.buscaDetallesNotaMedicaIngresoEvolucion: Error, Falta datos");
        else{
            sQuery="select * from  buscaDetallesNotaMedicaIngresoEvolucionEXP("+getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT," +
                                                                        getEpiMed().getClaveEpisodio()+"::BIGINT," +
                                                                        getConsecutivo()+"::SMALLINT," +
                                                                        getEpiMed().getSignosVitales().getNgVerbal()+"::BIGINT);";
            //System.out.println(sQuery); 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (!rst.isEmpty()) {  
                ArrayList vRowTemp = (ArrayList)rst.get(0); 
                getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                getEpiMed().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                getEpiMed().getPaciente().setNombres((String)vRowTemp.get(2)); 
                getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(3));
                getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(4));
                edad=(String)vRowTemp.get(5);
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
                if(((String)vRowTemp.get(6)).compareTo("M")==0)
                    getEpiMed().getPaciente().setSexoP("MASCULINO");
                else if(((String)vRowTemp.get(6)).compareTo("F")==0)
                    getEpiMed().getPaciente().setSexoP("FEMENINO");
                    else
                        getEpiMed().getPaciente().setSexoP("");
                getEpiMed().getPaciente().setFechaNac((Date) vRowTemp.get(7));
                getEpiMed().getPaciente().setPeso(((Double)vRowTemp.get(8)).floatValue());
                getEpiMed().getPaciente().setTalla(((Double)vRowTemp.get(9)).floatValue());
                getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(10)).intValue());
                getEpiMed().getPaciente().getSeg().setNumero((String)vRowTemp.get(11));
                getEpiMed().setAreaServicioHRRB((String)vRowTemp.get(12));
                getEpiMed().getCama().setNumero((String)vRowTemp.get(13));
                setResumenInter((String)vRowTemp.get(14));
                setExploracionFisica((String)vRowTemp.get(15)); 
                setResultadosServAuxDiag((String)vRowTemp.get(16));
                setPlanDeTratamiento((String)vRowTemp.get(17));
                setPronostico((String)vRowTemp.get(18));
                setIndicacionTer((String)vRowTemp.get(19));
                getEpiMed().getAfePrincipal().getCIE10().setDescripcionDiag((String)vRowTemp.get(20));
                getEpiMed().getProceRe1().getCIE9().setDescripcion((String)vRowTemp.get(21));
                getEpiMed().getSignosVitales().setTA((String)vRowTemp.get(22));
                getEpiMed().getSignosVitales().setFC((String)vRowTemp.get(23)); 
                getEpiMed().getSignosVitales().setFR((String)vRowTemp.get(24));
                getEpiMed().getSignosVitales().setTemp((String)vRowTemp.get(25));
                setFechaRegistro((Date)vRowTemp.get(26));
                getEpiMed().getProceRe1().getCIE9().setClave((String)vRowTemp.get(27));
            }
        }
    }
        
    public String getPlanDeTratamiento() {
	return sPlanDeTratamiento;
    }

	public void setPlanDeTratamiento(String valor) {
	sPlanDeTratamiento=valor;
	}

    public String getExploracionFisica() {
        return sExploracionFisica;
    }

    public void setExploracionFisica(String sExploracionFisica) {
        this.sExploracionFisica = sExploracionFisica;
    }

    public String getPronostico() {
        return sPronostico;
    }

    public void setPronostico(String sPronostico) {
        this.sPronostico = sPronostico;
    }

    public String getIndicacionTer() {
        return sIndicacionTer;
    }

    public void setIndicacionTer(String sIndicacionTer) {
        this.sIndicacionTer = sIndicacionTer;
    }

    public String getResumenInter() {
        return sResumenInter;
    }

    public void setResumenInter(String sResumenInter) {
        this.sResumenInter = sResumenInter;
    }

    public String getResultadosServAuxDiag() {
        return sResultadosServAuxDiag;
    }

    public void setResultadosServAuxDiag(String sResultadosServAuxDiag) {
        this.sResultadosServAuxDiag = sResultadosServAuxDiag;
    }

    public EpisodioMedico getEpiMed() {
        return oEpiMed;
    }

    public void setEpiMed(EpisodioMedico oEpiMed) {
        this.oEpiMed = oEpiMed;
    }

    public PersonalHospitalario getMedFirm() {
        return oMedFirm;
    }

    public void setMedFirm(PersonalHospitalario oMedFirm) {
        this.oMedFirm = oMedFirm;
    }

    public String getResumenMotivoRef() {
        return sResumenMotivoRef;
    }

    public void setResumenMotivoRef(String sResumenMotivoRef) {
        this.sResumenMotivoRef = sResumenMotivoRef;
    }

    public String getPronosticoMotivoRef() {
        return sPronosticoMotivoRef;
    }

    public void setPronosticoMotivoRef(String sPronosticoMotivoRef) {
        this.sPronosticoMotivoRef = sPronosticoMotivoRef;
    }

    public ProcedimientoCIE9 getCIE9() {
        return oCIE9;
    }

    public void setCIE9(ProcedimientoCIE9 oCIE9) {
        this.oCIE9 = oCIE9;
    }
    public Medico getMedicoSupervisor(){
        return oMedicoSupervisor;
    }
    public void setMedicoSupervisor(Medico oMedicoSupervisor){
        this.oMedicoSupervisor = oMedicoSupervisor;
    }
    public int getConsecutivo(){
        return nConsecutivo;
    }
    public void setConsecutivo(int nConsecutivo){
        this.nConsecutivo = nConsecutivo;        
    }
    public Date getFechaRegistro(){
        return dFechaRegistro;
    }
    public void setFechaRegistro(Date dFechaRegistro){
        this.dFechaRegistro = dFechaRegistro;        
    }
    public String getComentarios(){
        return sComentarios;
    }
    public void setComentarios(String sComentarios){
        this.sComentarios = sComentarios;
    }
    public String getImpresionDiagnostica(){
        return sImpresionDiagnostica;
    }
    public void setImpresionDiagnostica(String sImpresionDiagnostica){
        this.sImpresionDiagnostica = sImpresionDiagnostica;
    }
    public String getResumenEstadoMental(){
        return sResumenEstadoMental;
    }
    public void setResumenEstadoMental(String sResumenEstadoMental){
        this.sResumenEstadoMental = sResumenEstadoMental;
    }
    public String getIncidentesAccidentes(){
        return sIncidentesAccidentes;
    }
    public void setIncidentesAccidentes(String sIncidentesAccidentes){
        this.sIncidentesAccidentes = sIncidentesAccidentes;
    }
    public int getMaxConsecutivo(){
        return nMaxConsecutivo;
    }
    public void setMaxConsecutivo(int nMaxConsecutivo){
        this.nMaxConsecutivo = nMaxConsecutivo;
    }
} 

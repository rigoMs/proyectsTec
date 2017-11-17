package mx.gob.hrrb.modelo.core;
 
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date; 
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.serv.SolicitudOtroServicio;

public class Traslado extends SolicitudOtroServicio implements Serializable {
private Date dFechaDeseable;
private Date dFechaAcordada;
private String sDocumentacion;
private PersonalHospitalario oMedicoAuto;
private PersonalHospitalario oTstramito;
private String sHospitalDestino;
private String sDiagEnvio;
private Parametrizacion oParamMotEnvio;
private String sOtroMotivo;  
private boolean bUrgente;
private String sServicioDestino;
private String sArea;
private AccesoDatos oAD;

private SignosVitales oSignVit; //No aparece en la bd
private Date dFechaCita;//No aparece en la bd
    
    public Traslado(){
      oParamMotEnvio=new Parametrizacion(); 
      oMedicoAuto = new PersonalHospitalario();
      oTstramito = new PersonalHospitalario();
    }
    
     public boolean buscaTraslado(long foliopac, long CalvEpi) throws Exception{  
         ArrayList rst = null;
	  String sQuery = "", sFecFmt="";
          SimpleDateFormat df;
          df=new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
          sQuery = "select * from buscaTrasladopac("+foliopac+","+CalvEpi+");";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
                 this.oMedicoAuto=new PersonalHospitalario();
		if (rst != null && rst.size()==1) { 
                   ArrayList vRowTemp = (ArrayList)rst.get(0);
                   this.setFechaDeseable((Date)vRowTemp.get(0));
                   this.getMedicoAuto().setNoTarjeta(((Double) vRowTemp.get(1)).intValue());
                   if(this.getMedicoAuto().getNoTarjeta()!=0){
                       this.getMedicoAuto().buscarCapa();
                   }else{}
                   this.setHospital((String)vRowTemp.get(2));
                   this.setIdentificador(((Double) vRowTemp.get(3)).intValue());                   
                                     return true;
                }else{
                  return false;
                }
                
      }
     
      public int modificaTrasladoTs(String idUsua, int idServ, String documen, int numTra,String hosp, String fech) throws Exception{
         int puntaje=0;
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from modificaTrasladoTS('"+idUsua+"', "+idServ+", "+this.isNull(documen)+","+numTra+",'"+hosp+"','"+fech+"');";
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				puntaje=(((Double)vRowTemp.get( 0 )).intValue());
                        }
                 
		} 
                  return puntaje; 
      } 
      
      public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }
      
      public String numnull(int num){
    if(num==0){
        return null;
    }
    else{
        return ""+num;
    }
    
       } 
      
    public Traslado[] buscaNotaDeReferencia() throws Exception{
        Traslado arrRet[]=null, oT=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaSolicitudesReferencia();"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Traslado[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oT=new  Traslado(); 
                            oT.setEpisodio(new EpisodioMedico());
                            oT.setMedicoAuto(new PersonalHospitalario()); 
                            oT.getEpisodio().setPaciente(new Paciente()); 
                         ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                        oT.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(0));
                        oT.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(1));
                        oT.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(2));
                        oT.getEpisodio().getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                        oT.getEpisodio().getArea().setClave(((Double) vRowTemp.get(4)).intValue());
                        oT.getEpisodio().getArea().setDescripcion(oT.getEpisodio().getArea().buscar());
                        oT.oMedicoAuto.setNoTarjeta(((Double) vRowTemp.get(5)).intValue()); 
                        oT.oMedicoAuto.buscarCapa();
                        oT.setIdentificador(((Double) vRowTemp.get(6)).intValue());
                        oT.getEpisodio().getPaciente().setFolioPaciente(((Double) vRowTemp.get(7)).longValue());
                        oT.getEpisodio().setClaveEpisodio(((Double) vRowTemp.get(8)).longValue());
                        arrRet[i]=oT; 
			} 
		} 
		return arrRet; 
    }
    
    
    
     public Traslado[] buscaNotaDeReferenciaAnt(Date fechini, Date fechfin,Paciente oPaciente) throws Exception{
        Traslado arrRet[]=null, oT=null;
        SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
         String fechainicail=df.format(fechini);
         String fechaFinal=df.format(fechfin); 
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaSolicitudesDeReferenciaAnteriores("+this.isNull(fechainicail)+", "+this.isNull(fechaFinal)+" , "+this.isNull(oPaciente.getApPaterno())+","+this.isNull(oPaciente.getApMaterno())+", "+this.isNull(oPaciente.getNombres())+", "+this.numnull(oPaciente.getExpediente().getNumero())+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new Traslado[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oT=new  Traslado(); 
                            oT.setEpisodio(new EpisodioMedico()); 
                            oT.getEpisodio().setPaciente(new Paciente());
                            oT.setMedicoAuto(new PersonalHospitalario()); 
                            oT.setTstramito(new PersonalHospitalario());
                         ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                        oT.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(0));
                        oT.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(1));
                        oT.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(2));
                        oT.getEpisodio().getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                        oT.getEpisodio().getArea().setClave(((Double) vRowTemp.get(4)).intValue());
                        oT.oMedicoAuto.setNoTarjeta(((Double) vRowTemp.get(5)).intValue()); 
                        oT.oMedicoAuto.buscarCapa();
                        oT.oTstramito.setNoTarjeta(((Double) vRowTemp.get(6)).intValue());
                        oT.setFechaSolicitud((Date)vRowTemp.get(7));
                        oT.setIdentificador(((Double) vRowTemp.get(8)).intValue());
                        oT.getEpisodio().getPaciente().setFolioPaciente(((Double) vRowTemp.get(9)).longValue());
                        oT.getEpisodio().setClaveEpisodio(((Double) vRowTemp.get(10)).longValue()); 
                        arrRet[i]=oT; 
			} 
		} 
		return arrRet; 
    }
     
       public int modificarServicioReal(String sUsuario, int idServReal) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "select * from modificaSituacionServicioRealizado('"+sUsuario+"',"+idServReal+",'T84', '13 ');"; 
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
      
    public int insertaNotaRefMedico() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
                if( this==null ){   //completar llave
                    throw new Exception("Traslado.insertar: error de programación, faltan datos");
		}else{
                    SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
                    sQuery = "SELECT * FROM insertanotareferencia('"+ sUsuarioFirmado +"'::character varying,"
                            + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                            + getEpisodio().getClaveEpisodio() + "::bigint,"
                            + getAutorizadoPor().getNoTarjeta() + "::integer,"
                            + getSignVit().getPulso() + "::smallint,'"
                            + format.format(dFechaCita) +"'::timestamp without time zone,'"
                            + sHospitalDestino +"'::character varying,"
                            + bUrgente +"::boolean,'"
                            + this.sDiagEnvio +"'::text,'"
                            + sOtroMotivo + "'::text,'"
                            + getMotivoEnvio().getTipoParametro() +"'::char(3),'"
                            + getMotivoEnvio().getClaveParametro() + "'::char(3),'"
                            + sServicioDestino +"'::character varying);";
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
    
    public Traslado[] buscaHistorialHojaReferencia(long foliopac) throws Exception{
        Traslado oTraslado=null, arrTraslados[]=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        if(foliopac==0){
          throw new Exception("Traslado.buscaHistorialHojaReferencia: Error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialhojareferencia("+foliopac+"::BIGINT);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrTraslados = new Traslado[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oTraslado = new Traslado();
                    oTraslado.setEpisodio(new EpisodioMedico()); 
                    //oTraslado.getEpisodio().setPaciente(new Paciente());
                    oTraslado.setIdentificador(((Double) vRowTemp.get(0)).intValue());
                    oTraslado.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                    oTraslado.getEpisodio().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                    //oTraslado.setFechaSolicitud((Date)vRowTemp.get(3));
                    oTraslado.setFechaAcordada((Date)vRowTemp.get(3));
                    oTraslado.setHospital((String)vRowTemp.get(4));
                    oTraslado.setDiagEnvio((String)vRowTemp.get(5));
                    arrTraslados[i]=oTraslado;
                } 
            }
        }
        return arrTraslados; 
    }
    
    public void buscaPacienteReferencia() throws Exception{
        Traslado oTraslado; 
        ArrayList rst = null; 
        if(getEpisodio().getPaciente().getFolioPaciente()==0 && getEpisodio().getClaveEpisodio()==0 && getIdentificador()==0)
            throw new Exception("Traslado.buscaPacienteReferencia: Error, Falta datos");
        else{
            String sQuery="select * from  buscadatospachojareferenciaEXP("+getEpisodio().getPaciente().getFolioPaciente()+"::BIGINT," +
                                                                        getEpisodio().getClaveEpisodio()+"::BIGINT," +
                                                                        getIdentificador()+"::BIGINT);";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (!rst.isEmpty()) {  
                ArrayList vRowTemp = (ArrayList)rst.get(0); 
                getEpisodio().getPaciente().setCalleNum((String)vRowTemp.get(0));
                getEpisodio().getPaciente().getCiudad().setClaveCiu((String)vRowTemp.get(1));
                getEpisodio().getPaciente().getMunicipio().setClaveMun((String)vRowTemp.get(2));
                getEpisodio().getPaciente().getEstado().setClaveEdo((String)vRowTemp.get(3));
                getEpisodio().getPaciente().setTelefono((String)vRowTemp.get(4));
                if(((String)vRowTemp.get(5)).compareTo("M")==0)
                    getEpisodio().getPaciente().setSexoP("MASCULINO");
                else if(((String)vRowTemp.get(5)).compareTo("F")==0)
                    getEpisodio().getPaciente().setSexoP("FEMENINO");
                    else
                        getEpisodio().getPaciente().setSexoP("");
                getEpisodio().getPaciente().setFechaNac((Date) vRowTemp.get(6));
                getEpisodio().getPaciente().setPeso(((Double)vRowTemp.get(7)).floatValue());
                getEpisodio().getPaciente().setTalla(((Double)vRowTemp.get(8)).floatValue());
                getEpisodio().getPaciente().getEstado().buscar();
                getEpisodio().getPaciente().getMunicipio().buscar(getEpisodio().getPaciente().getEstado().getClaveEdo());
                getEpisodio().getPaciente().getCiudad().buscar(getEpisodio().getPaciente().getEstado().getClaveEdo(), getEpisodio().getPaciente().getMunicipio().getClaveMun());
                //setFechaDeseable((Date) vRowTemp.get(9));
                setHospital((String)vRowTemp.get(9));
                setUrgente((((String)vRowTemp.get(10)).compareTo("1")==0));
                setFechaAcordada((Date) vRowTemp.get(11));
                setDiagEnvio((String)vRowTemp.get(12));
                setOtroMotiv((String)vRowTemp.get(13));
                getMotivoEnvio().setTipoParametro((String)vRowTemp.get(14));
                getMotivoEnvio().setClaveParametro((String)vRowTemp.get(15)); 
                setServicioDestino((String)vRowTemp.get(16));
                getEpisodio().setNotaMedHrrb(new NotaMedicaHRRB());
                getEpisodio().getNotaMedHrrb().setResumenInter((String)vRowTemp.get(17));
                getEpisodio().getNotaMedHrrb().setPronostico((String)vRowTemp.get(18));
                getEpisodio().getSignosVitales().setTA((String)vRowTemp.get(19));
                getEpisodio().getSignosVitales().setTemp((String)vRowTemp.get(20));
                getEpisodio().getSignosVitales().setFC((String)vRowTemp.get(21)); 
                getEpisodio().getSignosVitales().setFR((String)vRowTemp.get(22));
                getEpisodio().getSignosVitales().setPulso(((Double)vRowTemp.get(23)).intValue());
                getTstramito().setCedProf((String)vRowTemp.get(24));
                getTstramito().setNombres((String)vRowTemp.get(25));
                getTstramito().setApPaterno((String)vRowTemp.get(26));
                getTstramito().setApMaterno((String)vRowTemp.get(27));
                //MÉDICO QUE REALIZA LA NOTA DE REFERENCIA
                getMedicoAuto().setCedProf((String)vRowTemp.get(28));
                getMedicoAuto().setNombres((String)vRowTemp.get(29));
                getMedicoAuto().setApPaterno((String)vRowTemp.get(30));
                getMedicoAuto().setApMaterno((String)vRowTemp.get(31));
                setFechaSolicitud((Date) vRowTemp.get(32));
                getMotivoEnvio().setValor((String)vRowTemp.get(33));
            }
        }
    }
    
    public String getHospital() {
        return sHospitalDestino;
    }

    public void setHospital(String sNombreHospi) {
        this.sHospitalDestino = sNombreHospi;
    }

    public Date getFechaDeseable() {
        return dFechaDeseable;
    }

    public void setFechaDeseable(Date dFechaDeseable) {
        this.dFechaDeseable = dFechaDeseable;
    }

    public PersonalHospitalario getMedicoAuto() {
        return oMedicoAuto;
    }

    public void setMedicoAuto(PersonalHospitalario oMedicoAuto) {
        this.oMedicoAuto = oMedicoAuto;
    }
  
    public PersonalHospitalario getTstramito() {
        return oTstramito;
    }

    public void setTstramito(PersonalHospitalario oTstramito) {
        this.oTstramito = oTstramito;
    }

    public boolean isUrgente() {
        return bUrgente;
    }

    public void setUrgente(boolean bUrgente) {
        this.bUrgente = bUrgente;
    }

    public Date getFechaAcordada() {
        return dFechaAcordada;
    }

    public void setFechaAcordada(Date dFechaAcordada) {
        this.dFechaAcordada = dFechaAcordada;
    }

    public String getDiagEnvio() {
        return sDiagEnvio;
    }

    public void setDiagEnvio(String sDiagEnvio) {
        this.sDiagEnvio = sDiagEnvio;
    }

    public String getOtroMotivo() {
        return sOtroMotivo;
    }

    public void setOtroMotiv(String sOtroMotivo) {
        this.sOtroMotivo = sOtroMotivo;
    }

    public Parametrizacion getMotivoEnvio() {
        return oParamMotEnvio;
    }

    public void setMotivoEnvio(Parametrizacion oParamMotEnvio) {
        this.oParamMotEnvio = oParamMotEnvio;
    }

    public SignosVitales getSignVit() {
        return oSignVit;
    }

    public void setSignVit(SignosVitales oSignVit) {
        this.oSignVit = oSignVit;
    }

    public Date getFechaCita() {
        return dFechaCita;
    }

    public void setFechaCita(Date dFechaCita) {
        this.dFechaCita = dFechaCita;
    }
    
    public String getServicioDestino(){
        return this.sServicioDestino;
    }
    public void setServicioDestino(String val){
        this.sServicioDestino = val;
    }
    
    public String getDocumentacion(){
        return this.sDocumentacion;
    }
    public void setDocumentacion(String val){
        this.sDocumentacion = val;
    }
    
    public String getArea() {
        return sArea;
    }

    public void setArea(String sArea) {
        this.sArea = sArea;
    }
}


package mx.gob.hrrb.modelo.enfermeria;

import java.io.Serializable;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.Date;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import java.text.SimpleDateFormat;
/**
 * Objetivo: Transfusiones de un paciente
 * @author : Javier
 * @version: 1.0
*/
public class HojaDeTransfusion implements Serializable {
    
   private AccesoDatos oAD;   
   private AreaServicioHRRB oArea;
   private EpisodioMedico oEpi;
   private String sUsuarioFirmado;
   private Firmado oFir;
   private long nFolio;
   private int nNoHoja=1;
   private Date dFechaTras;
   private int nNoUnidad;
   private Parametrizacion oTipoUnidad;
   private Parametrizacion oTipoFactor;
   private Date dHoraIT;
   private Date dHoraFT;
   private int nVolumenTras;
   private String sObsGenerales;
   private Turno oTurno;   
   private ArrayList<SignosVitalesEnTransfusion> arrSignos;   
   private PersonalHospitalario oPer;
   private SignosVitalesEnTransfusion oSignos;
   private Parametrizacion oRevisado;
   private SimpleDateFormat format;
   private SimpleDateFormat formatFecha;
   
   private final String  sTablaTipoSangre="T78";
   private final String sTblaTipofactor="T80";
   private long nIdServicioRealizado;
   
    public HojaDeTransfusion( AccesoDatos pAD){
        this.oAD=pAD;
    }
    
    public HojaDeTransfusion(){        
        oFir = new Firmado();
        oTipoUnidad= new Parametrizacion();
        oTipoFactor  = new Parametrizacion(); 
        oTurno= new Turno(); 
        oPer = new PersonalHospitalario();
        oEpi= new EpisodioMedico();        
        oSignos = new SignosVitalesEnTransfusion();
        //sUsuarioFirmado="JAVIE28";
        dFechaTras = new Date();        
        format =  new SimpleDateFormat("yyyy-MM-dd");
        formatFecha = new SimpleDateFormat("HH:mm");    
       HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
    }
    
    public boolean buscaUnaTransfusion() throws Exception{
        boolean bRtn=false;
        ArrayList rst=null;
        String sQuery="";
        if(this.nFolio==0){
            throw  new Exception("HojaDeTransfusion.Buscar: Error de programacion");
        }else{
            sQuery=" SELECT * FROM buscaDatosDeUnaTransfusion("+this.nFolio+"::bigint);";
            //System.out.println(sQuery);
            oAD= new AccesoDatos();
            if(oAD.conectar()){
               rst= oAD.ejecutarConsulta(sQuery);
               oAD.desconectar();
            }
            if(rst!=null && rst.size()==1){
               ArrayList vRowTemp = (ArrayList)rst.get(0);               
               this.setFolio(((Double)vRowTemp.get(0)).intValue());
               this.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
               this.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
               this.setNoHoja(((Double)vRowTemp.get(3)).intValue());
               this.setFechaTras((Date)vRowTemp.get(4));
               this.setNoUnidad(((Double)vRowTemp.get(5)).intValue());
               this.oTipoUnidad.setValor((String)vRowTemp.get(6));
               this.oTipoFactor.setValor((String)vRowTemp.get(7));
               this.setHoraIT((Date)vRowTemp.get(8));
               this.setHoraFT((Date)vRowTemp.get(9));
               this.setVolumenTras(((Double)vRowTemp.get(10)).intValue());
               this.setObsGenerales((String)vRowTemp.get(11));
               this.oPer.setApPaterno((String)vRowTemp.get(12));
               this.oPer.setApMaterno((String)vRowTemp.get(13));
               this.oPer.setNombres((String)vRowTemp.get(14));               
               bRtn=true;
            }
            
        }       
        
        return bRtn;
    }
    
   
    
    public HojaDeTransfusion[] buscarTransfunsionesPaciente() throws Exception{
        HojaDeTransfusion arrTras[]=null, oTran=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        
            if(this.getEpisodio().getPaciente().getFolioPaciente()==0 || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
                throw new Exception("HojaDeTransfusion.BuscarTransfusionPaciente: Error, Falta datos");
            }else{                
                sQuery="SELECT * FROM buscaTransfusionPaciente("+this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                        +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint"+");";
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst !=null && rst.size()>0){
                    arrTras = new HojaDeTransfusion[rst.size()];
                    for(i=0; i<rst.size();i++){
                        oTran= new HojaDeTransfusion();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oTran.setFolio(((Double)vRowTemp.get(0)).longValue());
                        buscaSignosVitalesEnTransfusion(((Double)vRowTemp.get(0)).longValue());                        
                        oTran.setNoHoja(((Double)vRowTemp.get(3)).intValue());
                        oTran.setFechaTras((Date)vRowTemp.get(4));
                        oTran.setNoUnidad(((Double)vRowTemp.get(5)).intValue());
                        oTran.getTipoUnidad().setValor((String)vRowTemp.get(6));
                        oTran.getTipoFactor().setValor((String)vRowTemp.get(7));
                        oTran.setHoraIT((Date)vRowTemp.get(8));
                        oTran.setHoraFT((Date)vRowTemp.get(9));
                        oTran.setVolumenTras(((Double)vRowTemp.get(10)).intValue());
                        oTran.setObsGenerales((String)vRowTemp.get(11));
                        oTran.getPersonal().setNombres((String)vRowTemp.get(12));
                        oTran.getPersonal().setApPaterno((String)vRowTemp.get(13));
                        oTran.getPersonal().setApMaterno((String)vRowTemp.get(14));
                        oTran.setArraySigno(buscaSignosVitalesEnTransfusion(((Double)vRowTemp.get(0)).longValue()));
                        arrTras[i]=oTran;
                    }
                }
            }
        
        return arrTras;
    }
   
    public ArrayList<SignosVitalesEnTransfusion> buscaSignosVitalesEnTransfusion(long idT) throws Exception{
        ArrayList rst = null;
        SignosVitalesEnTransfusion oS=null;
        ArrayList<SignosVitalesEnTransfusion> sig= new ArrayList<SignosVitalesEnTransfusion>();
        String sQuery="";
        int i=0;
        if(idT==0){
            throw new Exception("HojaDeTransfusion.BuscaSignosVitalesEnTransfusion: ERROR, Faltan Datos");
        }else{
            sQuery="SELECT * FROM buscaSignosVitalesDeUnaTransfusion("+idT+"::bigint);";
            //System.out.println(sQuery);
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
              for(i=0; i<rst.size();i++){
                  oS= new SignosVitalesEnTransfusion();
                  ArrayList vRowTemp = (ArrayList)rst.get(i);
                  oS.setIdSignos(((Double)vRowTemp.get(0)).longValue());
                  oS.setHojaDeTransfusion(new HojaDeTransfusion());
                  oS.getHojaDeTransfusion().setFolio(((Double)vRowTemp.get(1)).longValue());
                  oS.getTipoRevisado().setValor((String)vRowTemp.get(2));
                  oS.setTa((String)vRowTemp.get(3));
                  oS.setFc((String)vRowTemp.get(4));
                  oS.setTemp((String)vRowTemp.get(5));
                  oS.setFr((String)vRowTemp.get(6));
                  sig.add(oS);
                  
              }
            }
        }
        return sig;
    }
    
    public int insertaTransfusion()throws Exception{
        int nRet=-1;
        String sQuery="";
        ArrayList rst=null;
        if(this.getEpisodio().getPaciente().getFolioPaciente()==0 
                || this.getEpisodio().getPaciente().getClaveEpisodio()==0
                ||this.getHoraIT()==null){
            throw new Exception("HojaDeTransfusion.insertarTransfusion: ERROR, Faltan Datos");
        }else{
            sQuery=this.getInsertarTransfusion();
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet =(((Double)vRowTemp.get(0)).intValue())  ;
                    this.setFolio(((Double)vRowTemp.get(1)).longValue());
                }
            }
            
        }
        
        return nRet;
    }
    
    public String getInsertarTransfusion()throws Exception{        
        String sQuery="";
        if(this.getEpisodio().getPaciente().getFolioPaciente()==0 
                || this.getEpisodio().getPaciente().getClaveEpisodio()==0
                ||this.getHoraIT()==null){
            throw new Exception("HojaDeTransfusion.insertarTransfusion: ERROR, Faltan Datos");
        }else{
            sQuery="SELECT * FROM insertaTransfusion("+this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                    +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,"
                    +this.getNoHoja()+"::integer,'"+this.format.format(this.getFechaTras())+"'::date,"+this.getNoUnidad()+"::integer,'"
                    +this.getTipoUnidad().getClaveParametro()+"'::character varying,'"+this.getTipoUnidad().getTipoParametro()+"'::character varying,'"
                    +this.getTipoFactor().getClaveParametro()+"'::character varying,'"+this.getTipoFactor().getTipoParametro()+"'::character varying, '"
                    +this.getFechaTransString()+" "+this.getHoraInicioString()+"'::timestamp without time zone,"+(this.getHoraFT()==null?"null":"'"+this.getFechaTransString()+" "+this.getHoraFinString()+"'")+"::timestamp without time zone,"
                    +(this.getVolumenTras()==0?"null": this.getVolumenTras())+"::integer,"+(this.getObsGenerales().equals("")?"null":"'"+this.getObsGenerales()+"'")+"::character varying,'"
                    +this.getTurno().getClave()+"'::character varying,'"+sUsuarioFirmado+"'::character varying,"+this.getIdServicioRealizado()+"::bigint);";
            //System.out.println(sQuery);
            
        }
        return sQuery;
    }
    
    public HojaDeTransfusion[] buscaSolicitudHemoderivadoPaciente() throws Exception{
        HojaDeTransfusion arrTran[]=null, oTran=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        if(this.getEpisodio().getPaciente().getFolioPaciente()==0 || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
            throw new Exception("HojaDeTransfucion. buscaSolicitudHemoderivadoPaciente: Error, Faltan Datos");
        }else{
            sQuery="SELECT * FROM buscaSolicitudHemoderivadoPacienteSinAplicar("+this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"+
                    this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint)";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrTran = new HojaDeTransfusion[rst.size()];
                for(i=0; i<rst.size();i++){
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oTran= new HojaDeTransfusion();
                    oTran.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oTran.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oTran.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(2));
                    oTran.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(3));
                    oTran.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(4));
                    oTran.getTipoUnidad().setValor((String)vRowTemp.get(6));
                    oTran.getTipoFactor().setValor((String)vRowTemp.get(7));
                    oTran.getTipoUnidad().setClaveParametro((String)vRowTemp.get(8));
                    oTran.getTipoUnidad().setTipoParametro((String)vRowTemp.get(9));
                    oTran.getTipoFactor().setClaveParametro((String)vRowTemp.get(10));
                    oTran.getTipoFactor().setTipoParametro((String)vRowTemp.get(11));
                    oTran.setIdServicioRealizado(((Double)vRowTemp.get(12)).longValue());
                    arrTran[i]= oTran;
                }
            }
        }
        return arrTran;
    }

    public String getModificarTransfusion() throws Exception{        
        String sQuery ="";
        if(this.getFolio()==0 || this.getHoraIT()==null || this.getHoraFT()==null){
            throw new Exception("HojaDeTransfusion.modificarTransfusion: ERROR, Faltan Datos");
        }else{
            sQuery="SELECT * FROM modificaHojaDeTransfusion("+this.getFolio()+"::bigint,"+this.getNoUnidad()+"::integer,"
                    +"'"+this.getFechaTransString()+" "+this.getHoraFinString()+"'::timestamp without time zone,"
                    +this.getVolumenTras()+"::integer,"+(this.getObsGenerales().equals("")?"null":"'"+this.getObsGenerales()+"'")+"::character varying,'"+sUsuarioFirmado+"'::character varying);";
            //System.out.println(sQuery);
        }
        return sQuery;
    }
    
    public HojaDeTransfusion[] buscarHistorialTransfunsionesPaciente( long foliopac) throws Exception{
        HojaDeTransfusion arrTras[]=null, oTran=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
            if(foliopac==0){
                throw new Exception("HojaDeTransfusion.BuscarHistorialTransfunsionesPaciente: Error, Falta datos");
            }else{                
                sQuery="SELECT * FROM BuscarHistorialTransfunsionesPaciente("+foliopac+"::bigint);";
                System.out.println(sQuery);
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst !=null && rst.size()>0){
                    arrTras = new HojaDeTransfusion[rst.size()];
                    for(i=0; i<rst.size();i++){
                        oTran= new HojaDeTransfusion();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oTran.setFolio(((Double)vRowTemp.get(0)).longValue());
                        oTran.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                        oTran.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                        oTran.setNoHoja(((Double)vRowTemp.get(3)).intValue());
                        //BuscaSignosVitalesEnTransfusion(((Double)vRowTemp.get(0)).longValue());                        
                        oTran.setFechaTras((Date)vRowTemp.get(4));
                        oTran.setNoUnidad(((Double)vRowTemp.get(5)).intValue());
                        oTran.getTipoUnidad().setValor((String)vRowTemp.get(6));
                        oTran.getTipoFactor().setValor((String)vRowTemp.get(7));
                        oTran.setHoraIT((Date)vRowTemp.get(8));
                        oTran.setHoraFT((Date)vRowTemp.get(9));
                        oTran.setVolumenTras(((Double)vRowTemp.get(10)).intValue());
                        oTran.setObsGenerales((String)vRowTemp.get(11));
                        oTran.getPersonal().setNombres((String)vRowTemp.get(12));
                        oTran.getPersonal().setApPaterno((String)vRowTemp.get(13));
                        oTran.getPersonal().setApMaterno((String)vRowTemp.get(14));
                        //oTran.setArraySigno(BuscaSignosVitalesEnTransfusion(((Double)vRowTemp.get(0)).longValue()));
                        arrTras[i]=oTran;
                    }
                }
            }
        
        return arrTras;
    }
    
    public HojaDeTransfusion[] buscaDatosBasicosHojaTransfunsion() throws Exception{
        HojaDeTransfusion arrTras[]=null, oTran=null;
        ArrayList rst=null;
        String sQuery="", edad="";
        int i=0;
        
            if(this.getEpisodio().getPaciente().getFolioPaciente()==0 || this.getEpisodio().getPaciente().getClaveEpisodio()==0 || this.getFolio()==0){
                throw new Exception("HojaDeTransfusion.BuscaDatosBasicosHojaTransfunsion: Error, Falta datos");
            }else{                
                sQuery="SELECT * FROM BuscaDatosBasicosHojaTransfunsion("+this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"+
                                                                        this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,"+
                                                                        this.getFolio()+"::bigint);";
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst !=null && rst.size()>0){
                    arrTras = new HojaDeTransfusion[rst.size()];
                    for(i=0; i<rst.size();i++){
                        oTran= new HojaDeTransfusion();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oTran.setFolio(((Double)vRowTemp.get(0)).longValue());
                        oTran.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                        oTran.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                        buscaSignosVitalesEnTransfusion(((Double)vRowTemp.get(0)).longValue());                        
                        oTran.setNoHoja(((Double)vRowTemp.get(3)).intValue());
                        oTran.setFechaTras((Date)vRowTemp.get(4));
                        oTran.setNoUnidad(((Double)vRowTemp.get(5)).intValue());
                        oTran.getTipoUnidad().setValor((String)vRowTemp.get(6));
                        oTran.getTipoFactor().setValor((String)vRowTemp.get(7));
                        oTran.setHoraIT((Date)vRowTemp.get(8));
                        oTran.setHoraFT((Date)vRowTemp.get(9));
                        oTran.setVolumenTras(((Double)vRowTemp.get(10)).intValue());
                        oTran.setObsGenerales((String)vRowTemp.get(11));
                        oTran.getPersonal().setNombres((String)vRowTemp.get(12));
                        oTran.getPersonal().setApPaterno((String)vRowTemp.get(13));
                        oTran.getPersonal().setApMaterno((String)vRowTemp.get(14));
                        oTran.setArraySigno(buscaSignosVitalesEnTransfusion(((Double)vRowTemp.get(0)).longValue()));
                        //Información Básica de paciente
                        oTran.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(15)).intValue());
                        oTran.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(16));
                        oTran.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(17));
                        oTran.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(18));
                        oTran.getEpisodio().getCama().setNumero(((String)vRowTemp.get(19)));
                        oTran.getEpisodio().getPaciente().setSexoP((String)vRowTemp.get(20));
                        oTran.getEpisodio().getPaciente().setFechaNac((Date)vRowTemp.get(21));
                        edad=(String)vRowTemp.get(22);
                        if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                   oTran.getEpisodio().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                   oTran.getEpisodio().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oTran.getEpisodio().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oTran.getEpisodio().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                }
                        }
                        oTran.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(23));
                        oTran.getEpisodio().getArea().setDescripcion((String)vRowTemp.get(24));
                        oTran.getEpisodio().setFechaIngreso((Date)vRowTemp.get(25));
                        arrTras[i]=oTran;
                    }
                }
            }
        return arrTras;
    }
    
    public AreaServicioHRRB geArea() {
        return oArea;
    }

    
    public void setArea(AreaServicioHRRB oArea) {
        this.oArea = oArea;
    }

    
    public EpisodioMedico getEpisodio() {
        return oEpi;
    }

  
    public void setEpisodio(EpisodioMedico oEpi) {
        this.oEpi = oEpi;
    }

    
    public long getFolio() {
        return nFolio;
    }

   
    public void setFolio(long nFolio) {
        this.nFolio = nFolio;
    }

    
    public int getNoHoja() {
        return nNoHoja;
    }

   
    public void setNoHoja(int nNoHoja) {
        this.nNoHoja = nNoHoja;
    }

    
    public Date getFechaTras() {
        return dFechaTras;
    }

    
    public void setFechaTras(Date dFechaTras) {
        this.dFechaTras = dFechaTras;
    }

    
    public int getNoUnidad() {
        return nNoUnidad;
    }

    
    public void setNoUnidad(int nNoUnidad) {
        this.nNoUnidad = nNoUnidad;
    }

    public Parametrizacion getTipoUnidad() {
        return oTipoUnidad;
    }

    
    public void setTipoUnidad(Parametrizacion oTipoUnidad) {
        this.oTipoUnidad = oTipoUnidad;
    }

    
    public Parametrizacion getTipoFactor() {
        return oTipoFactor;
    }

    
    public void setTipoFactor(Parametrizacion oTipoFactor) {
        this.oTipoFactor = oTipoFactor;
    }

    
    public Date getHoraIT() {
        return dHoraIT;
    }

    
    public void setHoraIT(Date dHoraIT) {
        this.dHoraIT = dHoraIT;
    }

    
    public Date getHoraFT() {
        return dHoraFT;
    }

   
    public void setHoraFT(Date dHoraFT) {
        this.dHoraFT = dHoraFT;
    }

   
    public int getVolumenTras() {
        return nVolumenTras;
    }

    
    public void setVolumenTras(int nVolumenTras) {
        this.nVolumenTras = nVolumenTras;
    }

    
    public String getObsGenerales() {
        return sObsGenerales;
    }

   
    public void setObsGenerales(String sObsGenerales) {
        this.sObsGenerales = sObsGenerales;
    }

    
    public Turno getTurno() {
        return oTurno;
    }

    
    public void setTurno(Turno oTurno) {
        this.oTurno = oTurno;
    }

    
    public ArrayList<SignosVitalesEnTransfusion> getArraySigno() {
        return arrSignos;
    }

    
    public void setArraySigno(ArrayList<SignosVitalesEnTransfusion> Signo) {
        this.arrSignos = Signo;
    }
    
    public PersonalHospitalario getPersonal() {
        return oPer;
    }

    
    public void setPersonal(PersonalHospitalario oPer) {
        this.oPer = oPer;
    }
    
    public SignosVitalesEnTransfusion getSisnosVitales(){
        return oSignos;
    }
    
    public void setSisnosVitales(SignosVitalesEnTransfusion  b){
        this.oSignos=b;
    }    

    public long getIdServicioRealizado() {
        return nIdServicioRealizado;
    }

    public void setIdServicioRealizado(long nIdServicioRealizado) {
        this.nIdServicioRealizado = nIdServicioRealizado;
    }
    
    // formata de hora
    
    public String getHoraInicioString(){
        if(this.getHoraIT()!=null){
            return this.formatFecha.format(this.getHoraIT());
        }else{
            return "";
        }
    }
    
    public String getHoraFinString(){
        if(this.getHoraFT()!=null){
            return this.formatFecha.format(this.getHoraFT());
        }else{
            return "";
        }
    }
    
    public String getFechaTransString(){
        if(this.getFechaTras()!=null){
            return this.format.format(this.getFechaTras());
        }else{
            return "";
        }
    }
    
    public String getFechaTransString2(){
        if(this.getFechaTras()!=null){
            SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
            return f.format(this.getFechaTras());
        }else{
            return "";
        }
    }
    
    public String getMinimoHora(){
        if(this.getHoraIT()!=null){
           String sH= formatFecha.format(this.getHoraIT());
           String v[]=sH.split(":");           
           return v[0];
        }else
         return "";
    }
    
    public Parametrizacion getRevisado(){
        return this.oRevisado;
    }
    public void setRevisado(Parametrizacion val){
        this.oRevisado = val;
    }
}

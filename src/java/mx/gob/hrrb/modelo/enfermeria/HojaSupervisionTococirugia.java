
package mx.gob.hrrb.modelo.enfermeria;

import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;

/**
 *
 * @author Javier
 */
public class HojaSupervisionTococirugia implements Serializable{
     
    private AccesoDatos oAD;
    private boolean bInduccion;
    private boolean bUS;
    private boolean bRCT;
    private long nIdHojaSuper;
    private String sObservacion;
    private String sUsuarioFirmado;
    private Date dFechaSupervicion;
    private EpisodioMedico oEpisodio;
    private ProcedimientosRealizados oProcedimiento;
    private Hospitalizacion oHosp;
    /*********************/
    private String sInduccion;
    private String sUS;
    private String sRCT;
    /********************/
    
    public HojaSupervisionTococirugia(){
        oEpisodio= new EpisodioMedico();
        oProcedimiento = new ProcedimientosRealizados();
        oProcedimiento.inicializar();
        oHosp = new Hospitalizacion();
        dFechaSupervicion= new Date();
        //sUsuarioFirmado="JAVIE28";
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
    }   
    
    public HojaSupervisionTococirugia[] BuscaTodosHojaSupervisionTococirugia()throws Exception{
        HojaSupervisionTococirugia arrRet[]=null, oHt=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        if(this.getFechaSupervicion()==null){
            throw new Exception("HojaSupervisionTococirugia.BuscaTodosHojaSupervisionTococirugia: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaTodosHojaSupervisionTococirugia('"+this.getFechaString()+"'::date);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()>0){
                    arrRet= new HojaSupervisionTococirugia[rst.size()];
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oHt= new HojaSupervisionTococirugia();
                        oHt.setIdHojaSuper(((Double)vRowTemp.get(0)).longValue());
                        oHt.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                        oHt.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(2)).longValue());
                        oHt.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                        oHt.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                        oHt.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                        oHt.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                        oHt.getProcedimiento().getCIE9().setClave((String)vRowTemp.get(7));
                        oHt.getEpisodio().getCama().setNumero((String)vRowTemp.get(8));
                        oHt.setFechaSupervicion((Date)vRowTemp.get(9));
                        oHt.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(10));
                        oHt.getProcedimiento().getCIE9().setDescripcion((String)vRowTemp.get(11));
                        oHt.setInduccion(((String)vRowTemp.get(12)).equals("1"));
                        oHt.setUs(((String)vRowTemp.get(13)).equals("1"));
                        oHt.setRct(((String)vRowTemp.get(14)).equals("1"));
                        oHt.setObservacion((String)vRowTemp.get(15));
                        oHt.setInduccionS(((String)vRowTemp.get(12)).equals("1")?"●":"");
                        oHt.setUS(((String)vRowTemp.get(13)).equals("1")?"●":"");
                        oHt.setRCT(((String)vRowTemp.get(14)).equals("1")?"●":"");
                        arrRet[i]=oHt;
                    }
                }
            }
        }              
        return arrRet;
    }
    
    public HojaSupervisionTococirugia[] BuscarPacientesToco(int nCveArea) throws Exception{
        HojaSupervisionTococirugia[] arrToco = null;
        HojaSupervisionTococirugia oSupToco = null;
        
        ArrayList rst=null;
        String sQuery ="", edad="";
        int i=0;
        if(nCveArea==0){
            throw new Exception("EpisodioMedico.BuscarPacientesToco: Error Faltan Datos");
        }else{
            sQuery="SELECT * FROM buscaPacientesDeTococirugia("+nCveArea+"::bigint,'"+this.getFechaString()+"'::date);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrToco = new HojaSupervisionTococirugia[rst.size()];
                for(i=0; i<rst.size();i++){
                    oSupToco= new HojaSupervisionTococirugia();
                   ArrayList vRowTemp= (ArrayList)rst.get(i);                    
                   oSupToco.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                   oSupToco.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                   oSupToco.getProcedimiento().getCIE9().setClave((String)vRowTemp.get(2));
                   oSupToco.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                   oSupToco.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                   oSupToco.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                   oSupToco.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                   oSupToco.getEpisodio().getPaciente().setSexoP((String)vRowTemp.get(7));                   
                   oSupToco.getEpisodio().setFechaIngreso((Date)vRowTemp.get(8));
                   oSupToco.getHospitalizacion().setFechaIngresoHos((Date)vRowTemp.get(9));
                   oSupToco.getEpisodio().getCama().setNumero((String)vRowTemp.get(10));                   
                   oSupToco.getEpisodio().getArea().setClave(((Double)vRowTemp.get(11)).intValue());
                   oSupToco.getEpisodio().getArea().setDescripcion((String)vRowTemp.get(12));
                   edad=(String)vRowTemp.get(13);
                   if (edad.compareTo("")!=0){
                   if(edad.substring(0, 3).compareTo("000")!=0){
                      if (edad.charAt(0)=='0')
                          oSupToco.getEpisodio().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                       else
                          oSupToco.getEpisodio().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                   }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                          oSupToco.getEpisodio().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                          oSupToco.getEpisodio().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                    }
                  }
                   oSupToco.getEpisodio().getDiagIngreso().setClave((String)vRowTemp.get(14));
                   oSupToco.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(15));
                   oSupToco.getProcedimiento().getCIE9().setDescripcion((String)vRowTemp.get(16));
                   oSupToco.setInduccion(((String)vRowTemp.get(17)).equals("1"));
                   oSupToco.setUs(((String)vRowTemp.get(18)).equals("1"));
                   oSupToco.setRct(((String)vRowTemp.get(19)).equals("1"));
                   oSupToco.setObservacion((String)vRowTemp.get(20));
                   oSupToco.setIdHojaSuper(((Double)vRowTemp.get(21)).longValue());
                   arrToco[i]= oSupToco;
                }                  
                
            }
        }        
        return arrToco;
    }
    
    public int insertarHojaSupervisionTococirugia()throws Exception{
        int nRet=-1;
        String sQuery="";
        ArrayList rst=null;
        if(this.getEpisodio().getPaciente().getFolioPaciente()==0
                || this.getEpisodio().getPaciente().getClaveEpisodio()==0){
            throw new Exception("HojaSupervisionTococirugia.insertarHojaSupervisionTococirugia: error, faltan datos");
        }else{
            sQuery="SELECT * FROM insertaHojaSupervisionTococirugia("                    
                    +this.getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                    +this.getEpisodio().getPaciente().getClaveEpisodio()+"::bigint,"
                    +(this.getProcedimiento().getCIE9().getClave().equals("")?"null":"'"+this.getProcedimiento().getCIE9().getClave()+"'")+"::character varying,'"
                    +this.getEpisodio().getCama().getNumero()+"'::character varying,'"
                    +this.getFechaString()+"'::date,"+(this.isInduccion()?"'1'":"'0'")+"::character,"
                    +(this.isUs()?"'1'":"'0'")+"::character,"+(this.isRct()?"'1'":"'0'")+"::character,"
                    +(this.getObservacion()==null || this.getObservacion().equals("")?"null":"'"+this.getObservacion()+"'")+"::character varying,'"
                    +sUsuarioFirmado+"'::character varying);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public int modificarHojaSupervisionTococirugia()throws Exception{
        int nRet=-1;
        ArrayList rst=null;
        String sQuery="";
        if(this.getIdHojaSuper()==0){
            throw new Exception("HojaSupervisionTococirugia.modificarHojaSupervisionTococirugia: error, faltan datos");
        }else{
            sQuery="SELECT * FROM modificaHojaSupervisionTococirugia("+this.getIdHojaSuper()+"::bigint,"
                    +(this.getProcedimiento().getCIE9().getClave().equals("")?"null":"'"+this.getProcedimiento().getCIE9().getClave()+"'")+"::character varying,"
                    +(this.isInduccion()?"'1'":"'0'")+"::character,"
                    +(this.isUs()?"'1'":"'0'")+"::character,"+(this.isRct()?"'1'":"'0'")+"::character,"
                    +(this.getObservacion()==null || this.getObservacion().equals("")?"null":"'"+this.getObservacion()+"'")+"::character varying,'"
                    +sUsuarioFirmado+"'::character varying);"; 
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()==1){
                    ArrayList vRowTemp= (ArrayList)rst.get(0);
                    nRet= ((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public EpisodioMedico getEpisodio(){
        return oEpisodio;
    }
    
    public void setEpisodio(EpisodioMedico oep){
        this.oEpisodio=oep;
    }

    public ProcedimientosRealizados getProcedimiento() {
        return oProcedimiento;
    }

    public void setProcedimiento(ProcedimientosRealizados oProcedimiento) {
        this.oProcedimiento = oProcedimiento;
    }

    public boolean isInduccion() {
        return bInduccion;
    }

    public void setInduccion(boolean bInduccion) {
        this.bInduccion = bInduccion;
    }

    public boolean isUs() {
        return bUS;
    }

    public void setUs(boolean bUS) {
        this.bUS = bUS;
    }

    public boolean isRct() {
        return bRCT;
    }

    public void setRct(boolean bRCT) {
        this.bRCT = bRCT;
    }

    public String getObservacion() {
        return sObservacion;
    }

    public void setObservacion(String sObservacion) {
        this.sObservacion = sObservacion;
    }

    public Hospitalizacion getHospitalizacion() {
        return oHosp;
    }

    public void setHospitalizacion(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    public Date getFechaSupervicion() {
        return dFechaSupervicion;
    }

    public void setFechaSupervicion(Date dFechaSupervicion) {
        this.dFechaSupervicion = dFechaSupervicion;
    }
    
    public String getFechaString(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dFechaSupervicion);
    }
    
    public String getFechaString2(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dFechaSupervicion);
    }
    
    public long getIdHojaSuper() {
        return nIdHojaSuper;
    }

    public void setIdHojaSuper(long nIdHojaSuper) {
        this.nIdHojaSuper = nIdHojaSuper;
    }

    public String getInduccionS() {
        return sInduccion;
    }

    public void setInduccionS(String sInduccion) {
        this.sInduccion = sInduccion;
    }

    public String getUS() {
        return sUS;
    }

    public void setUS(String sUS) {
        this.sUS = sUS;
    }

    public String getRCT() {
        return sRCT;
    }

    public void setRCT(String sRCT) {
        this.sRCT = sRCT;
    }
    
}

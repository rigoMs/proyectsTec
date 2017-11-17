
package mx.gob.hrrb.modelo.enfermeria;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Javier
 */
public class DetalleActividadesEnfermeria implements Serializable {
    
    private AccesoDatos oAD;
    private long nIdActividad;
    private int nCantidadMAT;
    private int nCantidadVES;
    private int nCantidadNOC;
    private int nTotal;   
    private String sNombreTipoActividad;
    private String sUsuarioFirmado;
    private Parametrizacion oTipoActividad;    
    private CabeceraActividadesEnfermeria oCbAct;
    private ArrayList<DetalleActividadesEnfermeria> arrActividades;
    
    public DetalleActividadesEnfermeria(){
        oTipoActividad = new Parametrizacion();
        arrActividades= new ArrayList<DetalleActividadesEnfermeria>();
        oCbAct= new CabeceraActividadesEnfermeria();
        //sUsuarioFirmado="JAVIE28";
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
        
    }
  
    public DetalleActividadesEnfermeria[] buscaActividades()throws Exception{
        DetalleActividadesEnfermeria arrRet[]=null,oDe;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        sQuery="SELECT * FROM buscaActividadesEnfermeria();";
        oAD= new AccesoDatos();
        if(oAD.conectar()){
           rst = oAD.ejecutarConsulta(sQuery);
           oAD.desconectar();
           if(rst!=null && rst.size()>0){
               arrRet= new DetalleActividadesEnfermeria[rst.size()];
               for(i=0; i<rst.size();i++){
                   ArrayList vRowTemp= (ArrayList)rst.get(i);
                   oDe= new DetalleActividadesEnfermeria();
                   oDe.getTipoActividad().setTipoParametro((String)vRowTemp.get(0));
                   oDe.getTipoActividad().setClaveParametro((String)vRowTemp.get(1));
                   oDe.getTipoActividad().setValor((String)vRowTemp.get(2));
                   arrRet[i]=oDe;
               }
           }
        }
        return arrRet;
    }
     
    public void BuscaActividadesEnfermeriaRegistradas()throws Exception{
        DetalleActividadesEnfermeria oTAZ,oTBA,oTBB,oAgr;
        this.setArrActividades(new ArrayList<DetalleActividadesEnfermeria>());
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        if(this.getCabeceraAct().getAreaServicio().getClave()==0
                || this.getCabeceraAct().getFechaStr().equals("")){
            throw new Exception("DetalleActividadesEnfermeria.BuscaActividadesEnfermeriaRegistradas: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaDetalleActividadesEnfermeria('"+this.getCabeceraAct().getFechaStr()+"'::date,"
                    +this.getCabeceraAct().getAreaServicio().getClave()+"::smallint);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                oTAZ= new DetalleActividadesEnfermeria();
                oTBA= new DetalleActividadesEnfermeria();
                oTBB= new DetalleActividadesEnfermeria();
                oTAZ.setNombreTipoActividad("ADMINISTRATIVAS");
                oTBA.setNombreTipoActividad("TÉCNICAS Y PROCEDIMINETOS");
                oTBB.setNombreTipoActividad("ACTIVIDADES DOCENTES");
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oAgr= new DetalleActividadesEnfermeria();
                    oAgr.getCabeceraAct().setnIdCebeceraActEnf(((Double)vRowTemp.get(0)).longValue());
                    oAgr.getCabeceraAct().getAreaServicio().setClave(((Double)vRowTemp.get(1)).intValue());
                    oAgr.getCabeceraAct().setFechaRegistroo((Date)vRowTemp.get(2));
                    oAgr.getCabeceraAct().setEnfermeraMAT(new PersonalHospitalario());
                    oAgr.getCabeceraAct().getEnfermeraMAT().setNombres((String)vRowTemp.get(3));
                    oAgr.getCabeceraAct().getEnfermeraMAT().setApPaterno((String)vRowTemp.get(4));
                    oAgr.getCabeceraAct().getEnfermeraMAT().setApMaterno((String)vRowTemp.get(5));
                    oAgr.getCabeceraAct().setEnfermeraVES(new PersonalHospitalario());
                    oAgr.getCabeceraAct().getEnfermeraVES().setNombres((String)vRowTemp.get(6));
                    oAgr.getCabeceraAct().getEnfermeraVES().setApPaterno((String)vRowTemp.get(7));
                    oAgr.getCabeceraAct().getEnfermeraVES().setApMaterno((String)vRowTemp.get(8));
                    oAgr.getCabeceraAct().setEnfermeraNOC(new PersonalHospitalario());
                    oAgr.getCabeceraAct().getEnfermeraNOC().setNombres((String)vRowTemp.get(9));
                    oAgr.getCabeceraAct().getEnfermeraNOC().setApPaterno((String)vRowTemp.get(10));
                    oAgr.getCabeceraAct().getEnfermeraNOC().setApMaterno((String)vRowTemp.get(11));
                    oAgr.setIdActividad(((Double)vRowTemp.get(12)).longValue());
                    oAgr.getTipoActividad().setTipoParametro((String)vRowTemp.get(13));
                    oAgr.getTipoActividad().setClaveParametro((String)vRowTemp.get(14));
                    oAgr.getTipoActividad().setValor((String)vRowTemp.get(15));
                    oAgr.setCantidadMAT(((Double)vRowTemp.get(16)).intValue());
                    oAgr.setCantidadVES(((Double)vRowTemp.get(17)).intValue());
                    oAgr.setCantidadNOC(((Double)vRowTemp.get(18)).intValue());
                    oAgr.setTotal(((Double)vRowTemp.get(19)).intValue());
                    if(oAgr.getTipoActividad().getTipoParametro().equals("TAZ")){
                        oTAZ.getArrActividades().add(oAgr);
                    }else if(oAgr.getTipoActividad().getTipoParametro().equals("TBA")){
                        oTBA.getArrActividades().add(oAgr);
                    }else if(oAgr.getTipoActividad().getTipoParametro().equals("TBB")){
                        oTBB.getArrActividades().add(oAgr);
                    }
                }                
                this.getArrActividades().add(oTAZ);
                this.getArrActividades().add(oTBA);
                this.getArrActividades().add(oTBB);
            }
        }
    }
    
    public ArrayList<DetalleActividadesEnfermeria> buscaActividadesEnfermeriaReporteDiario()throws Exception{
        DetalleActividadesEnfermeria oTAZ,oTBA,oTBB,oAgr;   
        ArrayList<DetalleActividadesEnfermeria> arrRet=null;        
        String sQuery="";               
        ArrayList rst=null;
        int i=0;
        if(this.getCabeceraAct().getFechaStr().equals("")
                || this.getCabeceraAct().getAreaServicio().getClave()==0){
            throw new Exception("DetalleActividadesEnfermeria.buscaActividadesEnfermeriaReporteDiario: error, falta datos");
        }else{               
            sQuery="SELECT * FROM buscaActividadEnfermeriaGeneraReporteDiario('"
                    +this.getCabeceraAct().getFechaStr()+"'::date,"
                    +this.getCabeceraAct().getAreaServicio().getClave()+"::smallint);";
           
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);                
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrRet= new ArrayList<DetalleActividadesEnfermeria>();
                oTAZ= new DetalleActividadesEnfermeria();
                oTBA= new DetalleActividadesEnfermeria();
                oTBB= new DetalleActividadesEnfermeria();
                oTAZ.setNombreTipoActividad("ADMINISTRATIVAS");
                oTBA.setNombreTipoActividad("TÉCNICAS Y PROCEDIMINETOS");
                oTBB.setNombreTipoActividad("ACTIVIDADES DOCENTES");
                for( i=0;i<rst.size();i++){
                    oAgr= new DetalleActividadesEnfermeria();
                    ArrayList vRowTemp= (ArrayList)rst.get(i);
                    oAgr.getCabeceraAct().setnIdCebeceraActEnf(((Double)vRowTemp.get(0)).longValue());
                    oAgr.getCabeceraAct().getAreaServicio().setClave(((Double)vRowTemp.get(1)).intValue());
                    oAgr.getCabeceraAct().setFechaRegistroo((Date)vRowTemp.get(2));
                    oAgr.getCabeceraAct().setEnfermeraMAT(new PersonalHospitalario());
                    oAgr.getCabeceraAct().getEnfermeraMAT().setNombres((String)vRowTemp.get(3));
                    oAgr.getCabeceraAct().getEnfermeraMAT().setApPaterno((String)vRowTemp.get(4));
                    oAgr.getCabeceraAct().getEnfermeraMAT().setApMaterno((String)vRowTemp.get(5));
                    oAgr.getCabeceraAct().setEnfermeraVES(new PersonalHospitalario());
                    oAgr.getCabeceraAct().getEnfermeraVES().setNombres((String)vRowTemp.get(6));
                    oAgr.getCabeceraAct().getEnfermeraVES().setApPaterno((String)vRowTemp.get(7));
                    oAgr.getCabeceraAct().getEnfermeraVES().setApMaterno((String)vRowTemp.get(8));
                    oAgr.getCabeceraAct().setEnfermeraNOC(new PersonalHospitalario());
                    oAgr.getCabeceraAct().getEnfermeraNOC().setNombres((String)vRowTemp.get(9));
                    oAgr.getCabeceraAct().getEnfermeraNOC().setApPaterno((String)vRowTemp.get(10));
                    oAgr.getCabeceraAct().getEnfermeraNOC().setApMaterno((String)vRowTemp.get(11));
                    oAgr.setIdActividad(((Double)vRowTemp.get(12)).longValue());
                    oAgr.getTipoActividad().setTipoParametro((String)vRowTemp.get(13));
                    oAgr.getTipoActividad().setClaveParametro((String)vRowTemp.get(14));
                    oAgr.getTipoActividad().setValor((String)vRowTemp.get(15));
                    oAgr.setCantidadMAT(((Double)vRowTemp.get(16)).intValue());
                    oAgr.setCantidadVES(((Double)vRowTemp.get(17)).intValue());
                    oAgr.setCantidadNOC(((Double)vRowTemp.get(18)).intValue());
                    oAgr.setTotal(((Double)vRowTemp.get(19)).intValue());
                    if(oAgr.getTipoActividad().getTipoParametro().equals("TAZ")){
                        oTAZ.getArrActividades().add(oAgr);
                    }else if(oAgr.getTipoActividad().getTipoParametro().equals("TBA")){
                        oTBA.getArrActividades().add(oAgr);
                    }else if(oAgr.getTipoActividad().getTipoParametro().equals("TBB")){
                        oTBB.getArrActividades().add(oAgr);
                    }
                }
                arrRet.add(oTAZ);
                arrRet.add(oTBA);
                arrRet.add(oTBB);
            }
        }
        return arrRet;
    }
    
    public int insertaCabeceraDetalleActividadesEnfermeria()throws Exception{
        int nRet=-1;        
        ArrayList<String> arrTemp = null;
        if(this.getCabeceraAct().getAreaServicio().getClave()==0
                ||this.getArrActividades().isEmpty()){
            throw new Exception("DetalleActividadesEnfermeria.insertaCabeceraDetalleActividadesEnfermeria: error, faltan datos");
        }else{
            arrTemp= new ArrayList<String>();
            arrTemp.add(this.getCabeceraAct().getInsertaCabeceraActividadEnfermeria());
            arrTemp.addAll(this.getInsertaDetalleActividadesEnfermeria());             
            if(arrTemp.size()>0){
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    nRet= oAD.ejecutarConsultaComando(arrTemp);
                    oAD.desconectar();
                }                
            }
        }
        return nRet;
    }
    
    public int insertarDetalleActividadesEnfermeria() throws Exception{
        int nRet=-1;        
        if(this.getCabeceraAct().getnIdCebeceraActEnf()==0
                || this.getInsertaDetalleActividadesEnfermeria().isEmpty()){
            throw new Exception("DetalleActividadesEnfermeria.insertarDetalleActividadesEnfermeria:error, faltal datos");
        }else{           
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                nRet= oAD.ejecutarConsultaComando(this.getInsertaDetalleActividadesEnfermeria());
                oAD.desconectar();
            }            
        }
        return nRet;
    }
    
    public ArrayList<String> getInsertaDetalleActividadesEnfermeria()throws Exception{
        ArrayList<String> arrRet=null;
        String sQuery="";
        if(this.getArrActividades().isEmpty()){
            throw new Exception("DetalleActividadesEnfermeria.getInsertaDetalleActividadesEnfermeria: error, faltan datos");
        }else{
            arrRet= new ArrayList<String>();
            for(DetalleActividadesEnfermeria oD: this.getArrActividades()){
                for(DetalleActividadesEnfermeria oS: oD.getArrActividades()){                   
                    sQuery="SELECT * FROM insertaDetalleActividadesEnfermeria("
                            +this.getCabeceraAct().getnIdCebeceraActEnf()+"::bigint,'"
                            +oS.getTipoActividad().getTipoParametro()+"'::character varying,'"
                            +oS.getTipoActividad().getClaveParametro()+"'::character varying,"
                            +oS.getCantidadMAT()+"::smallint,"
                            +oS.getCantidadVES()+"::smallint,"
                            +oS.getCantidadNOC()+"::smallint,'"
                            +sUsuarioFirmado+"'::character varying );";
                    arrRet.add(sQuery);
                }
            }
            
        }        
        return arrRet;
    }
    
    public int modificarActividadesEnfermeriaCabeceraDetalle()throws Exception{
        int nRet=-1;
        ArrayList vTrans= new ArrayList();
        if(this.getCabeceraAct().getModificarCabeceraActEnfermeria().equals("")
                || this.getModificarDetalleActividades().size()==0){
            throw new Exception("DetalleActividadesEnfermeria.modificarActividadesEnfermeriaCabeceraDetalle(): error, faltan datos");
        }else{
            vTrans.add(this.getCabeceraAct().getModificarCabeceraActEnfermeria());
            vTrans.addAll(this.getModificarDetalleActividades());
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                nRet= oAD.ejecutarConsultaComando(vTrans);
                oAD.desconectar();
            }
        }
        return nRet;
    }
    
    public ArrayList getModificarDetalleActividades()throws Exception{
        ArrayList arrTem= new ArrayList();
        String sQuery="";
        if(this.getArrActividades().size()==0
                || this.getArrActividades().isEmpty()){
            throw new Exception("DetalleActividadesEnfermeria.getModificarDetalleActividades: error, faltan datos");
        }else{
            for(DetalleActividadesEnfermeria oM: this.getArrActividades()){
                if(oM.getArrActividades().size()>0){
                    for(DetalleActividadesEnfermeria oI: oM.getArrActividades()){
                        sQuery="SELECT * FROM modificarDetalleActividadesEnfermeria("+oI.getIdActividad()+"::bigint,"
                        +oI.getCabeceraAct().getnIdCebeceraActEnf()+"::bigint,"
                        +oI.getCantidadMAT()+"::smallint,"
                        +oI.getCantidadVES()+"::smallint,"
                        +oI.getCantidadNOC()+"::smallint,'"
                        +sUsuarioFirmado+"'::character varying);";
                        arrTem.add(sQuery);
                    }
                }
            }           
        }
        return arrTem;
    }
    
    public long getIdActividad() {
        return nIdActividad;
    }

    public void setIdActividad(long nIdActividad) {
        this.nIdActividad = nIdActividad;
    }

    public Parametrizacion getTipoActividad() {
        return oTipoActividad;
    }

    public void setTipoActividad(Parametrizacion oTipoActividad) {
        this.oTipoActividad = oTipoActividad;
    }

    public int getTotal() {
        return nTotal;
    }

    public void setTotal(int nTotal) {
        this.nTotal = nTotal;
    }
    
    public int getCantidadMAT() {
        return nCantidadMAT;
    }

    public void setCantidadMAT(int nCantidadMAT) {
        this.nCantidadMAT = nCantidadMAT;
    }

    public int getCantidadVES() {
        return nCantidadVES;
    }

    public void setCantidadVES(int nCantidadVES) {
        this.nCantidadVES = nCantidadVES;
    }

    public int getCantidadNOC() {
        return nCantidadNOC;
    }

    public void setCantidadNOC(int nCnatidadNOC) {
        this.nCantidadNOC = nCnatidadNOC;
    }

    public CabeceraActividadesEnfermeria getCabeceraAct() {
        return oCbAct;
    }

    public void setCabeceraAct(CabeceraActividadesEnfermeria oCbAct) {
        this.oCbAct = oCbAct;
    }
    
    public ArrayList<DetalleActividadesEnfermeria> getArrActividades() {
        return arrActividades;
    }
    
    public void setArrActividades(ArrayList<DetalleActividadesEnfermeria> arrActividades) {
        this.arrActividades = arrActividades;
    }

    public String getNombreTipoActividad() {
        return sNombreTipoActividad;
    }

    public void setNombreTipoActividad(String sNombreTipoActividad) {
        this.sNombreTipoActividad = sNombreTipoActividad;
    }
    
    public int getTotalCalculado(){
        return this.getCantidadMAT()+this.getCantidadVES()+this.getCantidadNOC();
    }
   
    public String getCantidadMatSt(){
        if(this.getCantidadMAT()==0)
            return "";
        return String.valueOf(this.getCantidadMAT());    
    }
    
    public String getCantidadVesStr(){
        if(this.getCantidadVES()==0)
            return "";        
          return String.valueOf(this.getCantidadVES());                 
    }
    
    public String getCantidadNocStr(){
        if(this.getCantidadNOC()==0)
            return "";
        return String.valueOf(this.getCantidadNOC());                
    }
    
    public String getTotalStr(){
        if(this.getTotal()==0)
            return "";
        return String.valueOf(this.getTotal());
    }
}

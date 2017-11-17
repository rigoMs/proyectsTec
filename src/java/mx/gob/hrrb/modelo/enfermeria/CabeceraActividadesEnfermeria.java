
package mx.gob.hrrb.modelo.enfermeria;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Javier
 */
public class CabeceraActividadesEnfermeria implements Serializable {
    
    private AccesoDatos oAD;
    private long nIdCebeceraActEnf;
    private String sUsuarioFirmado;
    private Date dFechaRegistroo;
    private AreaServicioHRRB oAreaServicio;
    private PersonalHospitalario oEnfermeraMAT;
    private PersonalHospitalario oEnfermeraVES;
    private PersonalHospitalario oEnfermeraNOC;
    private ArrayList<DetalleActividadesEnfermeria> arrActividades;
    
    public CabeceraActividadesEnfermeria(){
        oAreaServicio= new AreaServicioHRRB();
        dFechaRegistroo= new Date();
        //sUsuarioFirmado="JAVIE28";
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
    }    
 
    public boolean buscaLLaveCabeceraActividadEnfermeria() throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        String sQuery="";
        if(this.getAreaServicio().getClave()==0
                || this.getFechaRegistroo()==null){
            throw new Exception("CabeceraActividadesEnfermeria.buscaLLaveCabeceraActividadesEnfermeria: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaLlaveCabeceraActividadesEnfermeria('"+this.getFechaStr()+"'::date,"
                    +this.getAreaServicio().getClave()+"::smallint);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()>0){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    this.setnIdCebeceraActEnf(((Double)vRowTemp.get(0)).longValue());
                    this.getAreaServicio().setClave(((Double)vRowTemp.get(1)).intValue());
                    this.setFechaRegistroo((Date)vRowTemp.get(2));
                    bRet=true;
                }
            }
        }
        return bRet;
    }
    
    public CabeceraActividadesEnfermeria[] buscaServiciosTodos()throws Exception{
        CabeceraActividadesEnfermeria arrRet[]=null, oCb;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        sQuery="SELECT * FROM buscaTodosServicios();";
        oAD= new AccesoDatos();
        if(oAD.conectar()){
            rst= oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
            if(rst!=null && rst.size()>0){
                arrRet = new CabeceraActividadesEnfermeria[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp= (ArrayList)rst.get(i);
                    oCb = new CabeceraActividadesEnfermeria();
                    oCb.getAreaServicio().setClave(((Double)vRowTemp.get(0)).intValue());
                    oCb.getAreaServicio().setDescripcion((String)vRowTemp.get(1));
                    oCb.getAreaServicio().setTipo((String)vRowTemp.get(2));
                    arrRet[i]=oCb;
                }
            }
        }
        return arrRet;
    }
    
    public int insertaCabeceraActividadesEnfermeria() throws Exception{
        int nRet=-1;
        ArrayList rst=null;        
        if(this.getAreaServicio().getClave()==0
                || this.getInsertaCabeceraActividadEnfermeria().equals("")){
            throw new Exception("CabeceraActividadesEnfermeria.insertaCabeceraActividadesEnfermeria: error, faltan datos");
        }else{
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(this.getInsertaCabeceraActividadEnfermeria());
                oAD.desconectar();
                if(rst!=null){
                    ArrayList vRowTemp= (ArrayList)rst.get(0);
                    nRet=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public String getInsertaCabeceraActividadEnfermeria()throws Exception{
        String sQuery="";
        int sUsu1=(this.getDeterminaTurno().equals("MAT"))?1:0;
        int sUsu2=(this.getDeterminaTurno().equals("VES"))?1:0;
        int sUsu3=(this.getDeterminaTurno().equals("NOC"))?1:0;
        if(this.getAreaServicio().getClave()==0
                || this.getFechaRegistroo()==null){
            throw new Exception("CabeceraActividadesEnfermeria.getInsertaCabeceraActividadEnfermeria: error, faltan datos");
        }else{
            sQuery="SELECT * FROM insertaCabeceraActividadesEnfermeria('"+this.getFechaStr()+"'::date,"
                    +this.getAreaServicio().getClave()+"::smallint,"
                    +(sUsu1)+"::smallint,"
                    +(sUsu2)+"::smallint,"
                    +(sUsu3)+"::smallint,'"
                    +sUsuarioFirmado+"'::character varying);";
        }
        return sQuery;
    }
    
    public int modificarCabeceraActividadesEnfermeria()throws Exception{
        int nRet=-1;
        ArrayList rst=null;
        if(this.getnIdCebeceraActEnf()==0
              || this.getModificarCabeceraActEnfermeria().equals("")){
            throw new Exception("CabeceraActividadesEnfermeria.modificarCabeceraActividadesEnfermeria: error, faltan datos");
        }else{
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(this.getModificarCabeceraActEnfermeria());
                oAD.desconectar();
                if(rst!=null){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    nRet=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public String getModificarCabeceraActEnfermeria() throws Exception{
        String sQuery="";
        int sUsu1=(this.getDeterminaTurno().equals("MAT"))?1:0;
        int sUsu2=(this.getDeterminaTurno().equals("VES"))?1:0;
        int sUsu3=(this.getDeterminaTurno().equals("NOC"))?1:0;
        if(this.getnIdCebeceraActEnf()==0){
            throw new Exception("CabeceraActividadesEnfermeria.getModificarCabeceraActEnfermeria: error, faltan datos");
        }else{
            sQuery="SELECT * FROM modificarCabeceraActividadEnfermeria("+this.getnIdCebeceraActEnf()+"::bigint,"
                    +(sUsu1)+"::smallint,"
                    +(sUsu2)+"::smallint,"
                    +(sUsu3)+"::smallint,'"
                    +sUsuarioFirmado+"'::character varying);";
        }
        return sQuery;
    }
   
    public long getnIdCebeceraActEnf() {
        return nIdCebeceraActEnf;
    }

  
    public void setnIdCebeceraActEnf(long nIdCebeceraActEnf) {
        this.nIdCebeceraActEnf = nIdCebeceraActEnf;
    }

    public Date getFechaRegistroo() {
        return dFechaRegistroo;
    }
   
    public void setFechaRegistroo(Date dFechaRegistroo) {
        this.dFechaRegistroo = dFechaRegistroo;
    }

    public AreaServicioHRRB getAreaServicio() {
        return oAreaServicio;
    }

    public void setAreaServicio(AreaServicioHRRB oAreaServicio) {
        this.oAreaServicio = oAreaServicio;
    }

    public PersonalHospitalario getEnfermeraMAT() {
        return oEnfermeraMAT;
    }

    public void setEnfermeraMAT(PersonalHospitalario oEnfermeraMAT) {
        this.oEnfermeraMAT = oEnfermeraMAT;
    }

    public PersonalHospitalario getEnfermeraVES() {
        return oEnfermeraVES;
    }

    public void setEnfermeraVES(PersonalHospitalario oEnfermeraVES) {
        this.oEnfermeraVES = oEnfermeraVES;
    }

    public PersonalHospitalario getEnfermeraNOC() {
        return oEnfermeraNOC;
    }

    public void setEnfermeraNOC(PersonalHospitalario oEnfermeraNOC) {
        this.oEnfermeraNOC = oEnfermeraNOC;
    }
    
    public String getFechaStr(){
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        return df.format(dFechaRegistroo);
    }
    
    public String getFechaStr2(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dFechaRegistroo);
    }
    
    public String getDeterminaTurno(){ 
         Date dFechaActual2= new Date();
         SimpleDateFormat hf= new SimpleDateFormat("HH.mm");
         String sTurno="";
         String sT=hf.format(dFechaActual2);
         Double horaActual=Double.parseDouble(sT);
         sTurno= (horaActual>=07.00 && horaActual<15.00)?"MAT": (horaActual >= 15.00 && horaActual < 21.00)?"VES":"NOC";         
         return sTurno;
    } 
}

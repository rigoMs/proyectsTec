package mx.gob.hrrb.modelo.archivo;

import java.io.IOException;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;


/**
 *
 * @author JDanny
 */
public class PrestamoExp implements Serializable{
    private AccesoDatos oAD;
    private long nFolioPresExp;
    private Expediente oExp;
    private Date dFechaPres;
    private Date dFechaDev;
    private boolean bStatusPres;
    private AreaServicioHRRB oSerUbicacion;
    private PersonalHospitalario oPersonalEntrega;
    private PersonalHospitalario oPersonalRecibe;
    private PersonalHospitalario oPersonalGuarda;
    private PersonalHospitalario oPersonalFirm;
    private DateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
    private Usuario oUsuario;
    private String sUsuario;
    
    private Paciente oPac;
    
    public PrestamoExp(){
        oExp=new Expediente();
        oSerUbicacion= new AreaServicioHRRB();
        oUsuario=new Usuario();
        oPersonalEntrega= new PersonalHospitalario();
        oPersonalRecibe=new PersonalHospitalario();
        oPersonalRecibe.setNoTarjeta(-1);
        oPersonalGuarda= new PersonalHospitalario();
        oPersonalFirm= new PersonalHospitalario();
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuario = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();     
                oUsuario.setIdUsuario(sUsuario);
                oUsuario.setCvePerfil(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getCvePerfil());
        }
        try {
            oPersonalFirm.setIdUsuario(sUsuario);
            oPersonalFirm.buscaPersonalHospitalarioDatos();                   
        } catch (Exception ex) {
            Logger.getLogger(PrestamoExp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    
    //busca paciente para generar orden de apertura
    public EpisodioMedico buscaPaciente(long folioPac)throws Exception{
            EpisodioMedico oEM = null;
            ArrayList rst=null;
            String sQuery;
            int i=0;
            if(folioPac==0)
                throw new Exception("NO ES POSIBLE PROCESAR INFORMACION");
            else{
                sQuery = "SELECT * FROM buscaPacienteFolio("+folioPac+");";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return oEM = null;
                else{
                    oEM = new EpisodioMedico();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oEM.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());                    
                    oEM.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oEM.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oEM.getPaciente().setNombres((String)vRowTemp.get(3));
                    oEM.getPaciente().setFechaNac((Date)vRowTemp.get(4));
                    oEM.getPaciente().setCurp((String)vRowTemp.get(5));//vacio
                    oEM.getPaciente().setSexoP((String)vRowTemp.get(6));
                    oEM.getPaciente().setCalleNum((String)vRowTemp.get(7));
                    //oPac.setEdoCivilStr((String)vRowTemp.get(7));
                    //oPac.setColonia((String)vRowTemp.get(9));
                    oEM.getPaciente().getMunicipio().setDescripcionMun((String)vRowTemp.get(8));
                    oEM.getPaciente().getEstado().setDescripcionEdo((String)vRowTemp.get(9));
                    oEM.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(10)).intValue());
                    oEM.setClaveEpisodio(((Double)vRowTemp.get(11)).intValue());
                    oEM.setAdmisionUrgs(new AdmisionUrgs());
                    oEM.getAdmisionUrgs().setFolioAdmision(((Double)vRowTemp.get(12)).intValue());
                    oEM.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(13));
                    oEM.getPaciente().setParametrizacion(new Parametrizacion());
                    oEM.getPaciente().getParametrizacion().setValor((String)vRowTemp.get(14));
                    oEM.setFechaIngreso((Date)vRowTemp.get(15));
                    oEM.setAltaHospitalaria((Date)vRowTemp.get(16));
                    String convierte = edad.format(oEM.getPaciente().getFechaNac());
                    oEM.getPaciente().setFechaNacTexto(convierte);
                    oEM.getPaciente().calculaEdad();
                    return oEM;
                }
            }
    }
    
    //Busca datos del expediente para prestamo
    public Paciente buscaPac(long folioPac, int nnumero)throws Exception{
        Paciente oPac;
        ArrayList rst= null;
        String sQuery="";
        int i=0;
        if(folioPac==0 && nnumero==0)
            throw new Exception("ERROR AL PROCESAR LA INFORMACION");
            else{
                sQuery="SELECT * FROM buscaDatosPacienteArchivo("+folioPac+","+nnumero+");";
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst=oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return oPac=null;
                else{
                    oPac= new Paciente();
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oPac.setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                    oPac.getExpediente().setNumero(((Double)vRowTemp.get(1)).intValue());
                    oPac.setApPaterno((String)vRowTemp.get(2));
                    oPac.setApMaterno((String)vRowTemp.get(3));
                    oPac.setNombres((String)vRowTemp.get(4));
                    oPac.setFechaNac((Date)vRowTemp.get(5));
                    oPac.setCurp((String)vRowTemp.get(6));//vacio
                    oPac.setSexoP((String)vRowTemp.get(7));
                    oPac.setMunicipio(new Municipio());
                    oPac.getMunicipio().setDescripcionMun((String)vRowTemp.get(8));
                    String convierte = edad.format(oPac.getFechaNac());
                    oPac.setFechaNacTexto(convierte);
                    oPac.calculaEdad();
                    return oPac;
                }
            }
    }
    
    //busca datos de expedientes prestados
    public PrestamoExp[] buscaExpedientesPrestados()throws Exception{
        PrestamoExp arrPrestamo[]= null, oPrestamo=null;
        ArrayList rst= null;
        String sQuery="";
        int i=0;
        sQuery="SELECT * FROM buscaExpPrestados();";
        oAD= new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst!=null){
            arrPrestamo= new PrestamoExp[rst.size()];
            for(i = 0; i < rst.size(); i++){
                oPrestamo=new PrestamoExp();
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oPrestamo.setFolioPresExp(((Double)vRowTemp.get(0)).intValue());
                oPrestamo.getExp().setPaciente(new Paciente());
                oPrestamo.getExp().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).intValue());
                oPrestamo.getExp().setNumero(((Double)vRowTemp.get(2)).intValue());
                oPrestamo.getExp().getPaciente().setApPaterno((String)vRowTemp.get(3));
                oPrestamo.getExp().getPaciente().setApMaterno((String)vRowTemp.get(4));
                oPrestamo.getExp().getPaciente().setNombres((String)vRowTemp.get(5));
                oPrestamo.getExp().getPaciente().setFechaNac((Date)vRowTemp.get(6));
                oPrestamo.getExp().getPaciente().setSexoP((String)vRowTemp.get(7));
                oPrestamo.setFechaPres((Date)vRowTemp.get(8));
                oPrestamo.getSerUbicacion().setDescripcion((String)vRowTemp.get(9));
                oPrestamo.getPersonalEntrega().setNombres((String)vRowTemp.get(10));
                oPrestamo.getPersonalEntrega().setApPaterno((String)vRowTemp.get(11));
                oPrestamo.getPersonalEntrega().setApMaterno((String)vRowTemp.get(12));
                oPrestamo.getPersonalRecibe().setNombres((String)vRowTemp.get(13));
                oPrestamo.getPersonalRecibe().setApPaterno((String)vRowTemp.get(14));
                oPrestamo.getPersonalRecibe().setApMaterno((String)vRowTemp.get(15));
                String convierte=edad.format(oPrestamo.getExp().getPaciente().getFechaNac());
                oPrestamo.getExp().getPaciente().setFechaNacTexto(convierte);
                oPrestamo.getExp().getPaciente().calculaEdad();
                arrPrestamo[i]=oPrestamo;
            }
        }
        return arrPrestamo;
    }
    
    public PrestamoExp[] buscaExpedientesPrestadosPorCriterio(Date dIni, Date dFin, int nnum)throws Exception{
        PrestamoExp arrPrestamo[]=null, oPrestamo=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        SimpleDateFormat fmtTxt = new SimpleDateFormat("dd-MM-yyyy");
        if(nnum!=0){
            sQuery="select * from buscaExpPrestadosPorCriterio("+nnum+",null,null);";
        }else if(dIni!=null && dFin==null){
            sQuery="select * from buscaExpPrestadosPorCriterio(null,'"+(fmtTxt.format(dIni))+"'::date,null);";
        }
        else
            sQuery="select * from buscaExpPrestadosPorCriterio(null,'"+(fmtTxt.format(dIni))+"'::date,'"+(fmtTxt.format(dFin))+"'::date);";
        //System.out.println(sQuery);
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst!=null){
            arrPrestamo=new PrestamoExp[rst.size()];
            for(i=0; i<rst.size();i++){
                oPrestamo=new PrestamoExp();
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oPrestamo.setFolioPresExp(((Double)vRowTemp.get(0)).intValue());
                oPrestamo.getExp().setPaciente(new Paciente());
                oPrestamo.getExp().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).intValue());
                oPrestamo.getExp().setNumero(((Double)vRowTemp.get(2)).intValue());
                oPrestamo.getExp().getPaciente().setApPaterno((String)vRowTemp.get(3));
                oPrestamo.getExp().getPaciente().setApMaterno((String)vRowTemp.get(4));
                oPrestamo.getExp().getPaciente().setNombres((String)vRowTemp.get(5));
                oPrestamo.getExp().getPaciente().setFechaNac((Date)vRowTemp.get(6));
                oPrestamo.getExp().getPaciente().setSexoP((String)vRowTemp.get(7));
                oPrestamo.setFechaPres((Date)vRowTemp.get(8));
                oPrestamo.getSerUbicacion().setDescripcion((String)vRowTemp.get(9));
                oPrestamo.getPersonalEntrega().setNombres((String)vRowTemp.get(10));
                oPrestamo.getPersonalEntrega().setApPaterno((String)vRowTemp.get(11));
                oPrestamo.getPersonalEntrega().setApMaterno((String)vRowTemp.get(12));
                oPrestamo.getPersonalRecibe().setNombres((String)vRowTemp.get(13));
                oPrestamo.getPersonalRecibe().setApPaterno((String)vRowTemp.get(14));
                oPrestamo.getPersonalRecibe().setApMaterno((String)vRowTemp.get(15));
                String convierte=edad.format(oPrestamo.getExp().getPaciente().getFechaNac());
                oPrestamo.getExp().getPaciente().setFechaNacTexto(convierte);
                oPrestamo.getExp().getPaciente().calculaEdad();
                arrPrestamo[i]=oPrestamo;
            }
        }
        return arrPrestamo;
    }
    
    public PrestamoExp[] buscaExpedientesDevueltosPorCriterio(Date dIni, Date dFin, int nnum)throws Exception{
        PrestamoExp arrPrestamo[]=null, oPrestamo=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
        SimpleDateFormat fmtTxt = new SimpleDateFormat("dd-MM-yyyy");
        if(nnum!=0){
            sQuery="select * from buscaExpDevueltosPorCriterio("+nnum+",null,null);";
        }else if(dIni!=null && dFin==null){
            sQuery="select * from buscaExpDevueltosPorCriterio(null,'"+(fmtTxt.format(dIni))+"'::date,null);";
        }
        else
            sQuery="select * from buscaExpDevueltosPorCriterio(null,'"+(fmtTxt.format(dIni))+"'::date,'"+(fmtTxt.format(dFin))+"'::date);";
        //System.out.println(sQuery);
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst!=null){
            arrPrestamo=new PrestamoExp[rst.size()];
            for(i=0; i<rst.size();i++){
                oPrestamo=new PrestamoExp();
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oPrestamo.setFolioPresExp(((Double)vRowTemp.get(0)).intValue());
                oPrestamo.getExp().setPaciente(new Paciente());
                oPrestamo.getExp().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).intValue());
                oPrestamo.getExp().setNumero(((Double)vRowTemp.get(2)).intValue());
                oPrestamo.getExp().getPaciente().setApPaterno((String)vRowTemp.get(3));
                oPrestamo.getExp().getPaciente().setApMaterno((String)vRowTemp.get(4));
                oPrestamo.getExp().getPaciente().setNombres((String)vRowTemp.get(5));
                oPrestamo.getExp().getPaciente().setFechaNac((Date)vRowTemp.get(6));
                oPrestamo.getExp().getPaciente().setSexoP((String)vRowTemp.get(7));
                oPrestamo.setFechaPres((Date)vRowTemp.get(8));
                oPrestamo.setFechaDev((Date)vRowTemp.get(9));
                oPrestamo.getSerUbicacion().setDescripcion((String)vRowTemp.get(10));
                oPrestamo.getPersonalEntrega().setNombres((String)vRowTemp.get(11));
                oPrestamo.getPersonalEntrega().setApPaterno((String)vRowTemp.get(12));
                oPrestamo.getPersonalEntrega().setApMaterno((String)vRowTemp.get(13));
                oPrestamo.getPersonalRecibe().setNombres((String)vRowTemp.get(14));
                oPrestamo.getPersonalRecibe().setApPaterno((String)vRowTemp.get(15));
                oPrestamo.getPersonalRecibe().setApMaterno((String)vRowTemp.get(16));
                oPrestamo.getPersonalGuarda().setNombres((String)vRowTemp.get(17));
                oPrestamo.getPersonalGuarda().setApPaterno((String)vRowTemp.get(18));
                oPrestamo.getPersonalGuarda().setApMaterno((String)vRowTemp.get(19));
                String convierte=edad.format(oPrestamo.getExp().getPaciente().getFechaNac());
                oPrestamo.getExp().getPaciente().setFechaNacTexto(convierte);
                oPrestamo.getExp().getPaciente().calculaEdad();
                arrPrestamo[i]=oPrestamo;
            }
        }
        return arrPrestamo;
    }
    
    public int insertarPrestamoExp() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
         if(this==null){   //completar llave
            throw new Exception("PrestamoExp.insercion: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM insertaprestamoexp('"+sUsuario+"',"+getExp().getNumero()+","+getSerUbicacion().getClave()+","+getPersonalFirm().getNoTarjeta()+","+getPersonalRecibe().getNoTarjeta()+");";
            //System.out.println(sQuery);
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
    
    public int RegistraDevolucionExp(long folioprestamo, int numero)throws Exception{
        ArrayList rst =null;
        int nRet=0;
        String sQuery ="";
        
        if(folioprestamo==0 || numero==0){
            throw new Exception("PrestamoExp.Update: error de programación, faltan datos");
        }else{
            sQuery="SELECT * FROM registraDevolucionExp('"+sUsuario+"',"+folioprestamo+","+numero+","+getPersonalFirm().getNoTarjeta()+");";
            //System.out.println(sQuery);
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    nRet=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    
    /**
     * @return the nFolioPresExp
     */
    public long getFolioPresExp() {
        return nFolioPresExp;
    }

    /**
     * @param nFolioPresExp the nFolioPresExp to set
     */
    public void setFolioPresExp(long nFolioPresExp) {
        this.nFolioPresExp = nFolioPresExp;
    }

    /**
     * @return the dFechaPres
     */
    public Date getFechaPres() {
        return dFechaPres;
    }

    /**
     * @param dFechaPres the dFechaPres to set
     */
    public void setFechaPres(Date dFechaPres) {
        this.dFechaPres = dFechaPres;
    }

    /**
     * @return the dFechaDev
     */
    public Date getFechaDev() {
        return dFechaDev;
    }

    /**
       * @param dFechaDev the dFechaDev to set
     */
    public void setFechaDev(Date dFechaDev) {
        this.dFechaDev = dFechaDev;
    }

    /**
     * @return the bStatusPres
     */
    public boolean getStatusPres() {
        return bStatusPres;
    }

    /**
     * @param bStatusPres the bStatusPres to set
     */
    public void setStatusPres(boolean bStatusPres) {
        this.bStatusPres = bStatusPres;
    }

    /**
     * @return the oSerUbicacion
     */
    public AreaServicioHRRB getSerUbicacion() {
        return oSerUbicacion;
    }

    /**
     * @param oSerUbicacion the oSerUbicacion to set
     */
    public void setSerUbicacion(AreaServicioHRRB oSerUbicacion) {
        this.oSerUbicacion = oSerUbicacion;
    }

    /**
     * @return the oPersonalEntrega
     */
    public PersonalHospitalario getPersonalEntrega() {
        return oPersonalEntrega;
    }

    /**
     * @param oPersonalEntrega the oPersonalEntrega to set
     */
    public void setPersonalEntrega(PersonalHospitalario oPersonalEntrega) {
        this.oPersonalEntrega = oPersonalEntrega;
    }

    /**
     * @return the oPersonalRecibe
     */
    public PersonalHospitalario getPersonalRecibe() {
        return oPersonalRecibe;
    }

    /**
     * @param oPersonalRecibe the oPersonalRecibe to set
     */
    public void setPersonalRecibe(PersonalHospitalario oPersonalRecibe) {
        this.oPersonalRecibe = oPersonalRecibe;
    }

    /**
     * @return the oPersonalGuarda
     */
    public PersonalHospitalario getPersonalGuarda() {
        return oPersonalGuarda;
    }

    /**
     * @param oPersonalGuarda the oPersonalGuarda to set
     */
    public void setPersonalGuarda(PersonalHospitalario oPersonalGuarda) {
        this.oPersonalGuarda = oPersonalGuarda;
    }
    
    public PersonalHospitalario getPersonalFirm(){
        return oPersonalFirm;
    }

    /**
     * @return the oExp
     */
    public Expediente getExp() {
        return oExp;
    }

    /**
     * @param oExp the oExp to set
     */
    public void setExp(Expediente oExp) {
        this.oExp = oExp;
    }
    
    public Date buscaFechaActual()throws Exception{
        ArrayList rst = null;
        Date dFech = null;
        String sQuery = "";
            sQuery = "SELECT * FROM devuelvefechaactual()";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    dFech =(Date)vRowTemp.get(0);
                }
            }
        return dFech;
    }
}
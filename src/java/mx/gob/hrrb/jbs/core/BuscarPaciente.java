package mx.gob.hrrb.jbs.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.consultaexterna.ProgConsultorios;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Hospitalizacion;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Perfil;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
import org.primefaces.event.TabChangeEvent;
/**
 *
 * @author KarDan91
 */
@ManagedBean(name="oBuscarPac")
@SessionScoped
public class BuscarPaciente implements Serializable{

    /**
     * Creates a new instance of BuscarPaciente
     */

    private Paciente oPaciente;
    private Expediente oExpediente;
    private String mostrar="none";
    private Hospitalizacion oHosp;
    private String sRedireccionar="hospitalizacion/HojaCode";
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private Perfil oPerfil;
    private boolean bper;
    private boolean broot;
    private int nTipo;
    private AccesoDatos oAD;
    private String url;
    private boolean bNom;
    private boolean bExp;
    private String sPagina;
    private int nBorrado;

    //JMHG
    private EstudioRealizado oEstudioReal;
    private boolean bServ;
    private boolean bLabs;
    //----
    
    //Pablo
    private Date dFecCitado;
    private int nCveArea;
    private List<EstudioRealizado> lPacCitados;
    //
    private PersonalHospitalario oPersonal;

    public BuscarPaciente() {  
        oPaciente = new Paciente();
        oExpediente = new Expediente();
        oHosp = new Hospitalizacion();
        oPerfil=new Perfil();
        //JMHG
        oEstudioReal = new EstudioRealizado();
        bServ = false;
        //----
        bper=false;
        broot=false;
        nTipo=0;
        bNom=true;
        bExp=false;
        nBorrado=0;
        oFirm=new Firmado();
        oPersonal= new PersonalHospitalario();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            url=httpServletRequest.getRequestURL().toString().toLowerCase();
            //JMHG
            oPerfil = (Perfil)oFirm.getUsu().getPerfil().get( 0 );
            if( oPerfil.getClave().trim().compareTo( Perfil.CLAVE_RECEP_LABORATORIO ) == 0 )
                bLabs = true;
            //----
            }
        try{
            oPersonal.setIdUsuario(sUsuario);
            oPersonal.setUsuar(oFirm.getUsu());
            oPersonal.buscaPersonalHospitalarioDatos();
            System.out.println("Nada "+oPersonal.getAreaServicioHRRB());
        //bper=oPerfil.buscaPerfilCE();
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    
    public void requerir(TabChangeEvent event){        
        if(event.getTab().getId().equals("tabE")){
            bNom=false;
            bExp=true;
        }else if(event.getTab().getId().equals("tabN")){
            bNom=true;
            bExp=false;
        }
        //if(event.getTab()==)
    }
    
    public String getRenderizaBoton(){
        int nClave=0;
        String svalor="false";
        try{
            nClave=(((ArrayList<AreaServicioHRRB>)oPersonal.getAreaServicioHRRB()).get(0).getClave());
            if(nClave==3)//ARCHIVO
                svalor="true";
            else
                svalor="false";
        }catch(Exception ex) {
            Logger.getLogger(BuscarPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
        return svalor;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }    
    
    public Expediente getExpediente() {
        return oExpediente;
    }

    public void setExpediente(Expediente oExpediente) {
        this.oExpediente = oExpediente;
    }        

    /**
     * @return the mostrar
     */
    public String getMostrar() {
        return mostrar;
    }
    
    public void mostrarTabla(int numero){
        int opc=1;
        if(opc ==numero)
            setMostrar("block");
    }

    /**
     * @param mostrar the mostrar to set
     */
    public void setMostrar(String mostrar) {
        this.mostrar = mostrar;
    }

    /**
     * @return the redireccionar
     */
    public String getRedireccionar() {
        return sRedireccionar;
    }

    /**
     * @param redireccionar the redireccionar to set
     */
    public void setRedireccionar(String redireccionar) {
        this.sRedireccionar = redireccionar;
    }
    
    public String mostrarBotonCE() throws Exception{
        if (getTipo()==9)
        return "";
        else
            return "mostrarbtn";
    }
    
   public String mostrarBotonRegistroPaciente() throws Exception{
        if(getTipo()==1)
            return "";
        else
            return "display:none";
    }
    public String mostrarBotonLlenarNotaMedica() throws Exception{
        if(getTipo()==7)
            return "";
        else
            return "display:none";
    }    
    public String mostrarBotonLlenarHojaLesiones() throws Exception{
        if(getTipo()==5)
            return "";
        else
            return "display:none";
    }    
    public String mostrarBotonConsultarModificar() throws Exception{
        if(getTipo()==6)
            return "";
        else
            return "display:none";
    }    
    public String mostrarBotonAbrirExpediente() throws Exception{
        if(getTipo()==3)
            return "";
        else
            return "display:none";
    }    
    public String mostrarBotonVerIngresos() throws Exception{
        if(getTipo()==4)
            return "";
        else
            return "display:none";
    }    
    public String mostrarBotonHospitalizar() throws Exception{
        if(getTipo()==2)
            return "";
        else
            return "display:none";
    }    
    public String mostrarBotonAltaPaciente() throws Exception{
        if(getTipo()==8)
            return "";
        else
            return "display:none";
    }
    
	    public String mostrarBotonModExpediente() throws Exception{
        if(getTipo()==10)
            return "";
        
        else
         return "display:none";
    }
    public boolean habilitaListas(){
        if (oPaciente.getOtroPais().compareToIgnoreCase("MÉXICO")==0 || oPaciente.getOtroPais().compareToIgnoreCase("MEXICO")==0)
            return false;
        else
            return true;
    }
    
    public List<Ciudad> getListaCiudades(){
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList((new Ciudad().buscaCiudadCP(oPaciente.getEstado().getClaveEdo(), oPaciente.getMunicipio().getClaveMun(), oPaciente.getCodigoPos()))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
     public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList((new Municipio().buscarMunicipio(oPaciente.getEstado().getClaveEdo()))));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    //Pablo
    public void buscarPacientesCitadosServicio(){
        try{
            lPacCitados = new ArrayList<EstudioRealizado>(Arrays.asList(new EstudioRealizado().buscarPacientesCitadosPorServicio(getCveArea(), getFecCitado())));
        }catch(Exception ex){
            Logger.getLogger(ProgConsultorios.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    //
    
    
    public String introduceTipo(int n){
        nTipo=n;
        String pag="/sesiones/BuscarPaciente.xhtml";
        switch (nTipo){
                case 1: setPagina("Registrar Paciente");break;
                case 2: setPagina("Hospitalizar");break;
                case 3: setPagina("Abrir Expediente");break;
                case 4: setPagina("Ver Ingresos del Paciente");break;
                case 5: setPagina("Llenar Hoja Lesiones");break;
                case 6: setPagina("Consultar/Modificar Pacientes");break;
                case 7: setPagina("Llenar Nota Médica");break;
                case 8: setPagina("Alta Paciente");break;
                case 9: setPagina("Modificar Paciente CE");break;
                case 10: setPagina("Modificar Expediente");break;
                    
        }
        System.out.println(nTipo);
        oPaciente.setAdmUrg(nTipo);
        return pag;
    }
    
    public int borraCampos() throws Exception{
        if (nBorrado==1){
            getPaciente().setApPaterno("");
            getPaciente().setApMaterno("");
            getPaciente().setNombres("");
            getPaciente().getExpediente().setNumero(0);
            nBorrado=0;
        }
        return nBorrado;
    }
    
    public int getTipo() throws Exception{
        return nTipo;
    }

    public boolean getNom() {
        return bNom;
    }

    public void setNom(boolean bNom) {
        this.bNom = bNom;
    }

    public boolean getExp() {
        return bExp;
    }

    public void setExp(boolean bExp) {
        this.bExp = bExp;
    }
	
    public String getPagina() {
        return sPagina;
    }

    public void setPagina(String sPagina) {
        this.sPagina = sPagina;
    }
    
    public int getBorrado() {
        return nBorrado;
    }

    public void setBorrado(int nBorrado) {
        this.nBorrado = nBorrado;
    }
    
    //JMHG
    public EstudioRealizado getEstudioReal()
    {
        return oEstudioReal;
}
    
    public void setEstudioReal( EstudioRealizado oEstudioReal )
    {
        this.oEstudioReal = oEstudioReal;
    }
    
    public boolean getServicio()
    {
        return bServ;
    }
    
    public void setServicio( boolean bServ )
    {
        this.bServ = bServ;
    }
    
    public Perfil getPerfil()
    {
        return oPerfil;
    }
    
    public void setPerfil( Perfil oPerfil )
    {
        this.oPerfil = oPerfil;
    }
    
    public boolean getServLab()
    {
        System.out.println( "BuscarPaciente.getServLab: " + bLabs );
        return bLabs;
    }
    //----

    public Date getFecCitado() {
        return dFecCitado;
    }

    public void setFecCitado(Date dFecCitado) {
        this.dFecCitado = dFecCitado;
    }

    public int getCveArea() {
        return nCveArea;
    }

    public void setCveArea(int nCveArea) {
        this.nCveArea = nCveArea;
    }

    public List<EstudioRealizado> getPacCitados() {
        return lPacCitados;
    }

    public void setPacCitados(List<EstudioRealizado> lPacCitados) {
        this.lPacCitados = lPacCitados;
    }
    
}
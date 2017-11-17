/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.jbs.urgencias;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.FamiliarCercano;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
import org.primefaces.context.RequestContext;
//*****************************************************
import mx.gob.hrrb.modelo.core.TipoVialidad;
import mx.gob.hrrb.modelo.core.TipoAsentamiento;

/**
 *
 * @author Betia Ochoa
 */

@ManagedBean(name = "oRegPac")
@SessionScoped
//@ViewScoped
//*************************************************************************************
//registro de paciente desde el jefe de admision de urgencias************************
//************************************************************************************
public class registrarPaciente {
    public boolean band=false;//Bandera para habilitar o desabilitar campos de num seguro y vigencia
    public boolean band2=false;
    public boolean bandCP=false;
    private String sBandBtn="";
    List<String> arrRet;
    private boolean redir;
    
    private AdmisionUrgs oAdmisionUrgs;
    
    private String sOpe="";
    
    private long nFolioPac=0;
    private boolean bBuscado = false;
    private int nNumeroExpediente=0;
    int a,m,d;

//***************************Tipo de vialidad y asentamiento*************************
    private List<TipoVialidad>lTipoVialidad;
    private List<TipoAsentamiento>lTipoAsentamiento;


    
    public registrarPaciente(){
        crearListaTipoVialidad();
        crearListaTipoAsentamiento();
        
        this.sBandBtn="";
        arrRet=null;
        //fechaCompleta="";
        //oAdmisionUrgs= new AdmisionUrgs();
    }
     //Retorna Lista de Estados Civiles
     public List<Parametrizacion> getListaEdoCivil(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarEdosCiviles()));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    //RETORNA LISTA DE PARAMETRIZACION DE GRATUIDAD*************************************************************
     public List<Parametrizacion> getListaGratuidad(){
        List<Parametrizacion> lLista =null;
        try{
        lLista = new ArrayList<Parametrizacion>(Arrays.asList(
        (new Parametrizacion()).buscaGratuidad()));
        }
        catch(Exception ex){
        Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null , ex);
        }
         return lLista;
     }
     //*********************************************************************************************************
     
     //Retorna Lista de pertenencia a gpo indigena
     public List<Parametrizacion> getListaPertGpoIndigena(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarPertGpoInd()));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
    public boolean habilitaGratuidad (){
    boolean gra= false;
    if(getPacUrg().getPaciente().getSeg().getUnaDer().compareToIgnoreCase("TB2")==1)
        gra=true;
        else 
        
        gra=false;
    
    return gra;
    }
     //Retorna lista de opciones de derechohabiente
     public List<Parametrizacion> getListaDerechobiente(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarDerechohabiencia()));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    //Habilita o deshabilita los campos para vigencia seguro
    public boolean habilitaNumSeg(String opc){
        
        boolean hab=true;
        
            if (getPacUrg().getPaciente().getSeg().getUnaDer().compareTo(opc)==0){
                hab=false;
            }
         
        return hab;
    }

    public boolean habilitaEdad(){
        band=true;
       // System.out.println("Habilita Edad");
        if(oAdmisionUrgs.getPaciente().getFechaNacTexto()!=null)
            band=false;
        else
            band=true;
        
        return band;
    }
  
    public boolean habilitaPais(){
        //System.out.println("HabilitaPAis: "+oAdmisionUrgs.getPaciente().getPais());
        boolean p=false;
        if(oAdmisionUrgs.getPaciente().getPais().compareToIgnoreCase("MÉXICO")==0 || oAdmisionUrgs.getPaciente().getPais().compareToIgnoreCase("MEXICO")==0 )
            p=false;
        else{
            p=true;
        }
        return p;
    }
    
    public boolean habilitaDir(){
        boolean p=true;
        System.out.println("habilidadir");
        if(oAdmisionUrgs.getPaciente().getPais().compareToIgnoreCase("MÉXICO")==0 || oAdmisionUrgs.getPaciente().getPais().compareToIgnoreCase("MEXICO")==0 )
            p=true;
        else{
            p=false;
        }
        return p;
    }
    
     public boolean habilitaLengua(){
        boolean b=false;
        //System.out.println("habilita lengua: "+oPaciente.getEtnicidad().getHablaLenguaIndStr());
        if(oAdmisionUrgs.getPaciente().getEtnicidad().getHablaLenguaIndStr().compareToIgnoreCase("T0202")==1)
            b=true;
        else
            b=false;
        return b;
    }
     
     
    public boolean activaVigenciaNumero1(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("01")==0)
            return false;
        else
            return true;
    }
    public boolean activaVigenciaNumero2(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("02")==0)
            return false;
        else
            return true;
    }
    
    public boolean activaVigenciaNumero3(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("03")==0)
            return false;
        else
            return true;
    }
    
    public boolean activaVigenciaNumero4(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("04")==0)
            return true;
        else
            return false;
    }
    
    public boolean activaVigenciaNumero5(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("05")==0)
            return true;
        else
            return false;
    }
    
    public boolean activaVigenciaNumero6(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("06")==0)
            return true;
        else
            return false;
    }
    
    public boolean activaVigenciaNumero7(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("07")==0)
            return true;
        else
            return false;
    }
    
    public boolean activaVigenciaNumero8(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("08")==0)
            return true;
        else
            return false;
    }
    
    public boolean activaVigenciaNumeroG(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("0G")==0)
            return true;
        else
            return false;
    }
    
    public boolean activaVigenciaNumeroP(){
        if(getAdmisionUrgs().getPaciente().getSeg().getUnaDer().compareTo("0P")==0)
            return true;
        else
            return false;
    }
    
    public String getOpe() {return sOpe;}

    public void setOpe(String sOpe) {this.sOpe = sOpe; System.out.println(sOpe);}
    
    public long getFolioPac() { System.out.println(nFolioPac); return nFolioPac; }

    public void setFolioPac(long nFolioPac) { this.nFolioPac = nFolioPac; System.out.println(nFolioPac);}

    public boolean getBuscado() { return bBuscado;}

    public void setBuscado(boolean bBuscado) {this.bBuscado = bBuscado;System.out.println(bBuscado);}
    
    public int getNumeroExpediente() {
        System.out.println(nNumeroExpediente);
        return nNumeroExpediente;
    }

    public void setNumeroExpediente(int nNumeroExpediente) {
        this.nNumeroExpediente = nNumeroExpediente;
        System.out.println(nNumeroExpediente);
    }
    
    public AdmisionUrgs getPacUrg(){
        setBandBtn("");
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                setAdmisionUrgs(new AdmisionUrgs());
                getAdmisionUrgs().setDiagIngreso(new DiagnosticoCIE10());
                getAdmisionUrgs().setFamiliarCercano(new FamiliarCercano());
            }
            else{
                try{              
                    getAdmisionUrgs().setDiagIngreso(new DiagnosticoCIE10());
                    getAdmisionUrgs().setFamiliarCercano(new FamiliarCercano());//oPaciente.getPaciente().getExpediente().setNumero(nNumeroExpediente);
                    //oconsul.setCveUsu(nCveUsu);
                    getAdmisionUrgs().getPaciente().setFolioPaciente(nFolioPac);
                    getAdmisionUrgs().getPaciente().getExpediente().setNumero(getNumeroExpediente());
                                            
                }catch(Exception e){
                    e.printStackTrace();
                    bBuscado = false;
                }
            }
        }
        return oAdmisionUrgs;
    }
    
    public String redirAdmision() throws Exception{
        setAdmisionUrgs(new AdmisionUrgs());
        List<AdmisionUrgs> rst = null;
               //System.out.println("redirAdmision"+getRedir());
               rst=getAdmisionUrgs().BuscaEpisodiosAbiertos(nFolioPac);
                    if(rst.isEmpty()){
                    //System.out.println("Entra a if");
                    setBandBtn("");
                    getAdmisionUrgs().buscarPacienteUrg(nFolioPac);
                    getPacUrg();
                     return "urgencias/RegPacAdm";
                    }
                    else{
                        //System.out.println("Entra a else");
                        setBandBtn("mostrarbtn");
                        //System.out.println("boton "+getBandBtn());
                        FacesMessage message=null;
                        message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Registro de Paciente", "El paciente ya se encuentra admitido");
                        RequestContext.getCurrentInstance().showMessageInDialog(message);
                        setAdmisionUrgs(getPacUrg());
                        return "Inicio.xhtml";
                    }
    }

    public String habilitaBotonA(){
        if(getOpe().compareTo("a")==0){
            getPacUrg().setDig1(getAdmisionUrgs().getFolio());
            //System.out.println("GeneraFechaAdm: ");
            getPacUrg().setFechaAdm(getAdmisionUrgs().getFecha().getTime().toLocaleString());}
        
        if(getOpe().compareTo("a")==0)
            return "";
        else
            return "mostrarbtn";
    }
    
    public String habilitaBotonM(){
        if(getOpe().compareTo("m")==0){
            getPacUrg().setDig1(getAdmisionUrgs().getFolio());
            getPacUrg().setFechaAdm(getAdmisionUrgs().getFecha().getTime().toLocaleString());}
        if(getOpe().compareTo("m")==0)
            return "";
        else
            return "mostrarbtn";
    }

    /**
     * @return the oAdmisionUrgs
     */
    public AdmisionUrgs getAdmisionUrgs() {
        return oAdmisionUrgs;
    }

    /**
     * @param oAdmisionUrgs the oAdmisionUrgs to set
     */
    public void setAdmisionUrgs(AdmisionUrgs oAdmisionUrgs) {
        this.oAdmisionUrgs = oAdmisionUrgs;
    }

    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    //lista para estados de nacimiento********************************************************************
    public List<Estado>getListaEstados1(){
        List<Estado> iList=null;
        try{
            iList =  new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
        }
        catch(Exception ex){
        Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
        }
    return iList;
    }
    
    public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
        //System.out.println("Entra a lista municipios: "+oEstado.getClaveEdo());
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList(
                   (new Municipio()).buscarMunicipio(oAdmisionUrgs.getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    
    public List<Ciudad> getListaCiudades(){
         //System.out.println("Entra a lista Ciudades: "+oMunicipio.getEstado().getClaveEdo()+" "+ oMunicipio.getClaveMun());
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList(
                   (new Ciudad()).buscarCiudad(oAdmisionUrgs.getPaciente().getEstado().getClaveEdo(), oAdmisionUrgs.getPaciente().getMunicipio().getClaveMun())));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(oAdmisionUrgs.getPaciente().getCiudad().getClaveCiu(),oAdmisionUrgs.getPaciente().getMunicipio().getClaveMun(),oAdmisionUrgs.getPaciente().getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(registrarPaciente.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return lListaCP;
    }
    
    public void crearListaTipoVialidad(){
    TipoVialidad[] arrTipoVialidad= null;
    String sDescripcion ="";
    try{
    arrTipoVialidad = (new TipoVialidad()).buscarTodos();
    if(arrTipoVialidad !=null){
    for(TipoVialidad oTipoVial : arrTipoVialidad){
    sDescripcion="";
    sDescripcion = sp_ascii(oTipoVial.getDescripcion());
    oTipoVial.setDescripcion(sDescripcion);
    }
    lTipoVialidad = new ArrayList<>(Arrays.asList(arrTipoVialidad));
    }
    }
    catch(Exception ex){
    ex.printStackTrace();
    }
    
    }
    public void crearListaTipoAsentamiento(){
    TipoAsentamiento[] arrTipoAsentamiento = null;
    String sDescripcion="";
    try{
        arrTipoAsentamiento = (new TipoAsentamiento()).buscarTodos();
        if(arrTipoAsentamiento !=null){
        for(TipoAsentamiento oTipoAsenta : arrTipoAsentamiento){
        sDescripcion="";
        sDescripcion=sp_ascii(oTipoAsenta.getDescripcion());
        oTipoAsenta.setDescripcion(sDescripcion);
        }
        lTipoAsentamiento= new ArrayList<>(Arrays.asList(arrTipoAsentamiento));
        }
    
    
    }
    
    catch(Exception ex){
    ex.printStackTrace();
    
    }
    
    }
    public String sp_ascii( String input ) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for( int i = 0; i < original.length(); i++ ) {
            // Reemplazamos los caracteres especiales.
            output = output.replace( original.charAt( i ), ascii.charAt( i ) );
        }//for i
        return output;
    }

    
    public List<TipoVialidad> getListaTipoVialidad(){
    return lTipoVialidad;
    }
    public List<TipoAsentamiento> getListaTipoAsentamiento(){
    return lTipoAsentamiento;
    }

    /**
     * @return the bandBtn
     */
    public String getBandBtn() {
        return sBandBtn;
    }

    /**
     * @param bandBtn the bandBtn to set
     */
    public void setBandBtn(String sBandBtn) {
        this.sBandBtn = sBandBtn;
    }

    /**
     * @return the redir
     */
    public boolean getRedir() {
        return redir;
    }

    /**
     * @param redir the redir to set
     */
    public void setRedir(boolean redir) {
        this.redir = redir;
    }
    
    public String insertaPac() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        String encontrado="", encontradoAux="";
        FacesMessage message=null;
        
        switch(oAdmisionUrgs.getPaciente().getSeg().getUnaDer().charAt(1)){
            case '0':encontrado="[]";
                    oAdmisionUrgs.getPaciente().getSeg().setNumero("");
                    oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto("");break;
            case '9':encontrado="[]";
                    oAdmisionUrgs.getPaciente().getSeg().setNumero("");
                    oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto("");break;
            case '1':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto());break;
            case '2':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero2(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente());
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero2());
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto2());break;
            case '3':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero3(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero3());
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto3());break;
            case '4':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero4(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero4()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto4());break;
            case '5':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero5(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente());
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero5());
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto5());break;
            case '6':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero6(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero6()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto6());break;
            case '7':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero7(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero7()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto7());break;
            case '8':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero8(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero8()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto8());break;
            case 'P':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumeroP(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente());
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumeroP()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTextoP());break;
            case 'G':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumeroG(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumeroG()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTextoG());break;
        }
        System.out.println("Existe Seguro: "+encontradoAux);
        
        encontradoAux=encontrado.substring(1, (encontrado.length()-1));
       
        if (encontradoAux.compareTo("")==0){
        afec=oAdmisionUrgs.insertarPacUrg();
            
        if(afec==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Paciente", "Registro fallido!!!");
            pag="RegPacAdm.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Paciente", "Registro exitoso!!!");
            //getPaciente()=new Paciente();
        }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Paciente", "El número de seguro popular ya existe para el paciente: "+encontradoAux);
            pag="RegPacAdm.xhtml";
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }
    
    public String modificaPac() throws Exception{
        String pag="/faces/sesiones/Inicio.xhtml";
        int afec=0;
        String encontrado="", encontradoAux="";
        FacesMessage message=null;
        
        switch(oAdmisionUrgs.getPaciente().getSeg().getUnaDer().charAt(1)){
            case '0':encontrado="[]";
                    oAdmisionUrgs.getPaciente().getSeg().setNumero("");
                    oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto("");break;
            case '9':encontrado="[]";
                    oAdmisionUrgs.getPaciente().getSeg().setNumero("");
                    oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto("");break;
            case '1':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto());break;
            case '2':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero2(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente());
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero2());
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto2());break;
            case '3':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero3(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero3());
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto3());break;
            case '4':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero4(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero4()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto4());break;
            case '5':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero5(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente());
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero5());
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto5());break;
            case '6':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero6(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero6()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto6());break;
            case '7':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero7(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero7()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto7());break;
            case '8':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumero8(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumero8()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTexto8());break;
            case 'P':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumeroP(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente());
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumeroP()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTextoP());break;
            case 'G':encontrado=oAdmisionUrgs.getPaciente().getSeg().existeSeguro(oAdmisionUrgs.getPaciente().getSeg().getNumeroG(),oAdmisionUrgs.getPaciente().getSeg().getUnaDer(), oAdmisionUrgs.getPaciente().getFolioPaciente()); 
                        oAdmisionUrgs.getPaciente().getSeg().setNumero(oAdmisionUrgs.getPaciente().getSeg().getNumeroG()); 
                        oAdmisionUrgs.getPaciente().getSeg().setVigenciaTexto(oAdmisionUrgs.getPaciente().getSeg().getVigenciaTextoG());break;
                
        }
        encontradoAux=encontrado.substring(1, (encontrado.length()-1));
       // System.out.println("Existe Seguro: "+encontradoAux);
        
        if (encontradoAux.compareTo("")==0){
        afec=oAdmisionUrgs.modificarPacUrg();
           
        if(afec==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Admisión de Paciente", "Registro fallido!!!");
            pag="/faces/sesiones/Inicio.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Admisión de Paciente", "Registro exitoso!!!");
            //getPaciente()=new Paciente();
        }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Admisión de Paciente", "El número de seguro popular ya existe para el paciente: "+encontradoAux);
            pag="RegPacAdm.xhtml";
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

     
}
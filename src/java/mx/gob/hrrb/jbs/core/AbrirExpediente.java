package mx.gob.hrrb.jbs.core;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Programa;
import mx.gob.hrrb.modelo.core.Usuario;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Betia Ochoa
 * Modificado por José Daniel Hernández (Fase III)
 */

@ManagedBean(name = "oExp")
@SessionScoped
//@ViewScoped

public class AbrirExpediente {
    private Paciente oPaciente;
    private String sOpe="";
    private long nFolioPac=0;
    private boolean bBuscado = false;
    private String sUsuFirm;
    private Usuario oUsuario;
    private PersonalHospitalario oPersonal;
    
    //MODIFICADO POR DANIEL HERNANDEZ SANCHEZ TERCERA FASE
    private int nClave;
    private boolean bRendArch;
    private boolean bRendUrg;
    
    public AbrirExpediente(){
        oPaciente=new Paciente();
        oPaciente.setExpediente(new Expediente());
        oPaciente.getExpediente().setPrograma(new Programa());
        HttpServletRequest req;
        oUsuario=new Usuario();
        oPersonal=new PersonalHospitalario();
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuFirm = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();     
                oUsuario.setIdUsuario(sUsuFirm);
                oUsuario.setCvePerfil(((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getCvePerfil());
        }
        try {
            oPersonal.setIdUsuario(sUsuFirm);
            oPersonal.buscaPersonalHospitalarioDatos();                   
        } catch (Exception ex) {
            Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
        }
        renderiza();
    }
    
    public  void renderiza(){
        try{
            nClave=(((ArrayList<AreaServicioHRRB>)oPersonal.getAreaServicioHRRB()).get(0).getClave());
            if(nClave==3){//archivo
                bRendArch=true;
                bRendUrg=false;
            }else{//urgencias
                    bRendArch=false;
                    bRendUrg=true;
            }
        }catch(Exception ex) {
            Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public String getOpe() {return sOpe;}

    public void setOpe(String sOpe) {this.sOpe = sOpe;}
    
    public long getFolioPac() {return nFolioPac; }

    public void setFolioPac(long nFolioPac) { this.nFolioPac = nFolioPac;}

    public boolean getBuscado() { return bBuscado;}

    public void setBuscado(boolean bBuscado) {this.bBuscado = bBuscado;}
    
    public boolean getRenArch(){
        return bRendArch;
    }
        
    public boolean getRenUrg(){
        return bRendUrg;
    }
    
    public Paciente getExp() throws Exception{
        if (!bBuscado){
            bBuscado = true;
            if (this.sOpe.equals("a")){
                getPaciente().setExpediente(new Expediente());
                getPaciente().getExpediente().setInserta(true);
                System.out.println("==>"+nFolioPac);
                oPaciente.setFolioPaciente(nFolioPac);
                getPaciente().buscaPacienteExp();
            }
            else{
                try{              
                    getPaciente().setExpediente(new Expediente());
                    getPaciente().setFolioPaciente(nFolioPac);
                    
                    getPaciente().buscaPacienteExp();
                }catch(Exception e){
                    e.printStackTrace();
                    bBuscado = false;
                }
            }
        }
        return oPaciente;
    }

    /**
     * @return the oPaciente
     */
    public Paciente getPaciente() {
        return oPaciente;
    }

    /**
     * @param oPaciente the oPaciente to set
     */
    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
       } catch (Exception ex) {
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
        //System.out.println("Entra a lista municipios: "+oEstado.getClaveEdo());
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList(
                   (new Municipio()).buscarMunicipio(oPaciente.getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    
    public List<Ciudad> getListaCiudades(){
         //System.out.println("Entra a lista Ciudades: "+oMunicipio.getEstado().getClaveEdo()+" "+ oMunicipio.getClaveMun());
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList(
                   (new Ciudad()).buscarCiudad(oPaciente.getEstado().getClaveEdo(), oPaciente.getMunicipio().getClaveMun())));
       } catch (Exception ex) {
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(oPaciente.getCiudad().getClaveCiu(),oPaciente.getMunicipio().getClaveMun(),oPaciente.getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
       
       
        return lListaCP;
    }
    
    public String insertaExpediente(){
        String pag="/sesiones/Inicio.xhtml";
        int afec=0;
        FacesMessage message=null;
        
        try{
            if(nClave==3){
                //inserta desde archivo
                if(oPaciente.getExpediente().getNumero()!=0)
                    afec=oPaciente.insertarExpediente("TAJ1");
                else
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Apertura de Expediente", "El número de expediente no puede ser 0");
            }else{
                // inserta desde urgencias
                afec=oPaciente.insertarExpediente("TAJ2");
            }
        }catch(Exception e){
            e.printStackTrace();
            afec=0;
        }
        
        if(afec==0){
            message = new FacesMessage(FacesMessage.SEVERITY_ERROR, "Apertura de Expediente", "Apertura fallida!!!");
            pag="/sesiones/Inicio.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Apertura de Expediente", "Apertura exitosa!!!");
            //getPaciente()=new Paciente();
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }
    
    public List<Expediente> getListaExpPendientesPorAbrir(){//expedientes por abrir en urgencias
        List<Expediente> lLista = null;
       try {//URGENCIAS
            lLista = new ArrayList<Expediente>(Arrays.asList((new Expediente().buscaExpPendientesPorAbrir("TAJ2"))));
       } catch (Exception ex) {
           ex.getMessage();
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
       return lLista;
    }
    
    public List<Programa> getListaProgramas(){
        List<Programa> lLista = null;
       try {
           lLista = new ArrayList<Programa>(Arrays.asList(
                   (new Programa()).buscaListProgram()));
       } catch (Exception ex) {
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public void cargaPDFine(FileUploadEvent event) throws IOException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    String sRuta=facesContext.getExternalContext().getRealPath("")+Expediente.RUTA_INE,
          sNomArch="", sTxtMsj="";
    OutputStream out = null;
    InputStream filecontent = null;
    FacesMessage msg=null;
            try {
                sNomArch=String.valueOf(nFolioPac)+"credINE.pdf";
                uploadFile(event, sRuta, sNomArch);
                //System.out.println("1.- "+ sRuta +"   "+ sNomArch);
                oPaciente.getExpediente().setRutaINE(sNomArch);
                System.out.println(oPaciente.getExpediente().getRutaINE());
                sTxtMsj = sNomArch + " almacenado.";
                msg = new FacesMessage("Resultado", sTxtMsj );
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e){
                e.printStackTrace();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",event.getFile().getFileName() + " no almacenado."));
                System.out.println("Error no almacenado"+event.getFile().getFileName());
            }finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
    }
    
    public void cargaPDFpoliza(FileUploadEvent event) throws IOException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    String sRuta=facesContext.getExternalContext().getRealPath("")+Expediente.RUTA_INE,
          sNomArch="", sTxtMsj="";
    OutputStream out = null;
    InputStream filecontent = null;
    FacesMessage msg=null;
            try {
                sNomArch=String.valueOf(nFolioPac)+"PolizaSegPop.pdf";
                uploadFile(event, sRuta, sNomArch);
                //System.out.println("1.- "+ sRuta +"   "+ sNomArch);
                oPaciente.getSeg().setRutaPOLIZA(sNomArch);
                System.out.println(oPaciente.getSeg().getRutaPOLIZA());
                sTxtMsj = sNomArch + " almacenado.";
                msg = new FacesMessage("Resultado", sTxtMsj );
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e){
                e.printStackTrace();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",event.getFile().getFileName() + " no almacenado."));
                System.out.println("Error no almacenado"+event.getFile().getFileName());
            }finally {
                if (out != null) {
                    out.close();
                }
                if (filecontent != null) {
                    filecontent.close();
                }
            }
    }
    
    private void uploadFile(FileUploadEvent event, String sRuta, String sNomArch)throws IOException{
    OutputStream out = null;
    InputStream filecontent = null;
    byte[] bytes = new byte[1024];
    int nLeidos = 0;
        try {
            out = new FileOutputStream(new File(sRuta + File.separator
                    + sNomArch));
            filecontent =event.getFile().getInputstream();
            while ((nLeidos = filecontent.read(bytes)) != -1) {
                out.write(bytes, 0, nLeidos);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }finally {
            if (out != null) {
                out.close();
            }
            if (filecontent != null) {
                filecontent.close();
            }
        }
    }
    public boolean getRenderizaPolizaSP(){
        boolean b=false;
        try{
                if(!getPaciente().getSeg().buscaSegPopxPac(nFolioPac).equals("")){
                        b=true;
                }
        }catch(Exception e){
                e.toString(); 
        }
        return b;
    }
    public List<Parametrizacion> getListaLugarAperturaExp(){
        List<Parametrizacion> lLista = null;
       try {
           lLista = new ArrayList<Parametrizacion>(Arrays.asList((new Expediente().getLugarApertura().buscaLugarAperturaExp())));
       } catch (Exception ex) {
           ex.getMessage();
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
       return lLista;
    }

}
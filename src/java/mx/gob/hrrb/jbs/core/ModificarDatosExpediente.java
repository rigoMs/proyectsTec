/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

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
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.CiudadCP;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Programa;
import mx.gob.hrrb.modelo.core.Seguro;
import org.primefaces.context.RequestContext;
import org.primefaces.event.FileUploadEvent;

/**
 *
 * @author Betia, José Daniel
 */
@ManagedBean(name = "oModExp")
@SessionScoped
public class ModificarDatosExpediente{
    private Paciente oPac;
    private Seguro oSeg;
    private long nFolioPac=0;
    private boolean bBuscado = false;
    
    public ModificarDatosExpediente(){
        oPac=new Paciente();
        oSeg=new Seguro();
        //oPac.setSeg(new Seguro());
    }
    
    public String cargaDatos(){
        String res="";
        try{
            res=oPac.buscarPacExp();
        }catch(Exception e){
            e.printStackTrace();
        }
        return res;
    }

    public boolean habilitaListas(){
        if (getPac().getOtroPais().compareToIgnoreCase("MÉXICO")==0 || getPac().getOtroPais().compareToIgnoreCase("MEXICO")==0)
            return false;
        else
            return true;
    }
    
    public boolean requiereNumero(){
              if(getPac().getSeg().getVigenciaTexto()!=null){
                  if (getPac().getSeg().getVigenciaTexto().compareTo("")!=0){
                     System.out.println("REQUIERE NUMERO ********* TRUE");
                      return true;
                  }
              
              else {
                  System.out.println("REQUIERE NUMERO ********* FALSE");
                  return false;}
              }
              else {
                  System.out.println("REQUIERE NUMERO ********* FALSE");
                  return false;}
    }
    
    public boolean requiereVigencia(){
              if(getPac().getSeg().getNumero()!=null){
                  if(getPac().getSeg().getNumero().compareTo("")!=0){
                     System.out.println("REQUIERE VIGENCIA ********* TRUE");
                  return true;}
                  else{System.out.println("REQUIERE VIGENCIA ********* FALSE");
                  return false;}
              }
              else{
                     System.out.println("REQUIERE VIGENCIA ********* FALSE");
                  return false;}
    }
    
    
    public List<Ciudad> getListaCiudades(){
        List<Ciudad> lLista = null;
       try {
           lLista = new ArrayList<Ciudad>(Arrays.asList((new Ciudad().buscaCiudadCP(getPac().getEstado().getClaveEdo(), getPac().getMunicipio().getClaveMun(), getPac().getCodigoPos()))));
      // System.out.println(lLista.get(0).getClaveCiu());
       } catch (Exception ex) {
           Logger.getLogger(ModificarDatosExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
     public List<Municipio> getListaMunicipios(){
        List<Municipio> lLista = null;
       try {
           lLista = new ArrayList<Municipio>(Arrays.asList((new Municipio().buscarMunicipio(getPac().getEstado().getClaveEdo()))));
       } catch (Exception ex) {
           Logger.getLogger(ModificarDatosExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
     
    public List<Estado> getListaEstados(){
        List<Estado> lLista = null;
       try {
           lLista = new ArrayList<Estado>(Arrays.asList((new Estado().buscarEstados())));
           
       } catch (Exception ex) {
           Logger.getLogger(ModificarDatosExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    
    public List<CiudadCP> getListaCP(){
       List<CiudadCP> lListaCP = null;
       try {
           lListaCP = new ArrayList<CiudadCP>(Arrays.asList(
                   (new CiudadCP()).buscarCP(oPac.getCiudad().getClaveCiu(),oPac.getMunicipio().getClaveMun(),oPac.getEstado().getClaveEdo())));
       } catch (Exception ex) {
           Logger.getLogger(AbrirExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lListaCP;
    }
    
    public String modificaPacExp() throws Exception{
        String pag="/faces/sesiones/BuscarPaciente.xhtml";
        int afec=0;
        FacesMessage message=null;
        boolean encontrado=oSeg.buscarSeguroPop(getPac().getSeg().getNumero(), getPac().getFolioPaciente());
        boolean encExp=getPac().getExpediente().buscarExpedientePaciente(getPac().getExpediente().getNumero(), getPac().getFolioPaciente());
        if (encontrado==false){
            if(encExp==false){
                afec=getPac().modificarPacArchivo();
        if(afec==0){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Expediente", "Modificación fallida :(");
            pag="/faces/sesiones/ModificarDatosExpediente.xhtml";
        }else if (afec==23505 || afec==23503){
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Expediente", "Modificación fallida, El numero de expediente tiene información asociada que impide su modificación");
            pag="/faces/sesiones/ModificarDatosExpediente.xhtml";
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Expediente", "Modificación exitosa :)");
                    setPac(new Paciente());
        }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Expediente", "El número de Expediente ya existe");
            pag="/faces/sesiones/ModificarDatosExpediente.xhtml";
        }
        }else{
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Modificacion de Expediente", "El número de seguro popular ya existe para otro paciente");
            pag="/faces/sesiones/ModificarDatosExpediente.xhtml";
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return pag;
    }

    public List<Programa> getListaProgramas(){
        List<Programa> lLista = null;
        //System.out.println("Entra a lista municipios: "+oEstado.getClaveEdo());
       try {
           lLista = new ArrayList<Programa>(Arrays.asList(
                   (new Programa()).buscaListProgram()));
       } catch (Exception ex) {
           Logger.getLogger(ModificarDatosExpediente.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lLista;
    }
    /*
    public String getMuestraStatus(){
        String sStatusExp="";
        if(getPac().getExpediente().getStatusExpediente()=='1')
            sStatusExp="ACTIVO";
        else if(getPac().getExpediente().getStatusExpediente()=='0')
            sStatusExp="PASIVO";
        else
            sStatusExp="EN ARCHIVO MUERTO";
        return sStatusExp;
    }
    */
        
    public Seguro getSeg() {
        return oSeg;
    }

    public void setSeg(Seguro oSeg) {
        this.oSeg = oSeg;
    }

    /**
     * @return the nFolioPac
     */
    public long getFolioPac() {
        return nFolioPac;
    }

    /**
     * @param nFolioPac the nFolioPac to set
     */
    public void setFolioPac(long nFolioPac) {
        this.nFolioPac = nFolioPac;
    }

    /**
     * @return the bBuscado
     */
    public boolean getBuscado() {
        return bBuscado;
    }

    /**
     * @param bBuscado the bBuscado to set
     */
    public void setBuscado(boolean bBuscado) {
        this.bBuscado = bBuscado;
    }

    /**
     * @return the oPac
     */
    public Paciente getPac() {
        return oPac;
    }

    /**
     * @param oPac the oPac to set
     */
    public void setPac(Paciente oPac) {
        this.oPac = oPac;
    }
    
    public void cargaPDFine(FileUploadEvent event) throws IOException{
    FacesContext facesContext = FacesContext.getCurrentInstance();
    String sRuta=facesContext.getExternalContext().getRealPath("")+Expediente.RUTA_INE,
          sNomArch="", sTxtMsj="";
    OutputStream out = null;
    InputStream filecontent = null;
    FacesMessage msg=null;
            try {
                sNomArch=String.valueOf(oPac.getFolioPaciente())+"credINE.pdf";
                uploadFile(event, sRuta, sNomArch);
                //System.out.println("1.- "+ sRuta +"   "+ sNomArch);
                oPac.getExpediente().setRutaINE(sNomArch);
                System.out.println(oPac.getExpediente().getRutaINE());
                sTxtMsj = sNomArch + " modificado.";
                msg = new FacesMessage("Resultado", sTxtMsj );
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e){
                e.printStackTrace();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",event.getFile().getFileName() + " no modificado."));
                System.out.println("Error no modificado"+event.getFile().getFileName());
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
                sNomArch=String.valueOf(oPac.getFolioPaciente())+"PolizaSegPop.pdf";
                uploadFile(event, sRuta, sNomArch);
                //System.out.println("1.- "+ sRuta +"   "+ sNomArch);
                oPac.getSeg().setRutaPOLIZA(sNomArch);
                System.out.println(oPac.getSeg().getRutaPOLIZA());
                sTxtMsj = sNomArch + " modificado.";
                msg = new FacesMessage("Resultado", sTxtMsj );
                FacesContext.getCurrentInstance().addMessage(null, msg);
            } catch (Exception e){
                e.printStackTrace();
                facesContext.addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,
                    "Error",event.getFile().getFileName() + " no modificado."));
                System.out.println("Error no modificado"+event.getFile().getFileName());
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

}
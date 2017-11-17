package mx.gob.hrrb.jbs.capasits;

import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.core.Estudios;

/**
 *
 * @author pedro
 */
@ManagedBean (name="modEst")
@SessionScoped
public class ConsutaModificaEstudio {
    private Estudios oEstu;
    private PacienteCapasits oPCapa;
    private String ed, hoy;
    private Estudios[] arrEstu;

    
    public ConsutaModificaEstudio() {
        oEstu= new Estudios();
        oPCapa= new PacienteCapasits();
    }
    
    public Estudios getEstu(){
        return oEstu;
    }
    
    public PacienteCapasits getPac(){
        return oPCapa;
    }
    
    public void bus() throws Exception{
        FacesMessage msg = null;
         FacesContext context = FacesContext.getCurrentInstance();
         if(oPCapa.getIdNacional()<1){
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","Id invalido"));
         }
         else{    
         if(oPCapa.buscarIdNacional()){
             Date fecha= new Date();
             oEstu.setFechaEstudio(fecha);
             oEstu.setPac(oPCapa);
            if (oEstu.buscarEstudiosDePaciente()){ 
         try{  
             calculaEdad();
         }catch(Exception e){
         e.printStackTrace();
         FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
         }
          }
            else {
                FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_WARN, "ALERTA","No se a realizado la solicitud de estudios de laboratorio de "+oPCapa.getNombreCompleto()));
            }
         }
         else{
                 msg = new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","no se encuentra El Id en la base de datos");
             FacesContext.getCurrentInstance().addMessage(null, msg);
             
            }      
        } 
    }
    
    public void calculaEdad(){
        Date fActual=new Date();
        SimpleDateFormat formato = new SimpleDateFormat("dd/MM/yyyy");
        String fechan=formato.format(oPCapa.getFechaNac());
        String x[]=fechan.split("/");
        String fecAct=formato.format(fActual);
        int d1=Integer.parseInt(x[0]);
        int m1=Integer.parseInt(x[1]);
        int a1=Integer.parseInt(x[2]);
        String v[]=fecAct.split("/");
        int d2=Integer.parseInt(v[0]);
        int m2=Integer.parseInt(v[1]);
        int a2=Integer.parseInt(v[2]);
        int anofinal;
        String mensaje="";

        anofinal=a2-a1;
        if (anofinal>0){
            if (m2<m1){ anofinal--; }
            if (m2==m1 && d2<d1){anofinal--;}
            if (anofinal==1)
            mensaje=""+anofinal;
            else
                mensaje=""+anofinal;
        }
        setEdad(mensaje);
        setHoy(fecAct);
    }
    
    public void modifica() throws Exception{
        int nAfec =0;
        Date fecha= new Date();
             oEstu.setFechaEstudio(fecha);
             oEstu.setPac(oPCapa);
        oEstu.eliminarEstudioDePaciente();
        if(oEstu.getCv()){
            oEstu.setClaveInterna(29);
         try{ 
            nAfec=this.oEstu.insertarEstudioPac(); 
         } catch(Exception e){
            e.printStackTrace();
           FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
         }
        }
            if(oEstu.getCd4()){
              oEstu.setClaveInterna(28);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getBetagch()){
              oEstu.setClaveInterna(24);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getBilirrubina()){
              oEstu.setClaveInterna(12);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getCalcio()){
              oEstu.setClaveInterna(21);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getCloro()){
              oEstu.setClaveInterna(20);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getCoprop()){
              oEstu.setClaveInterna(25);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getCreatinina()){
              oEstu.setClaveInterna(11);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getDhl()){
              oEstu.setClaveInterna(15);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getFebriles()){
              oEstu.setClaveInterna(17);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getFosfatasa()){
              oEstu.setClaveInterna(16);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getGch()){
              oEstu.setClaveInterna(23);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getGlucosa()){
              oEstu.setClaveInterna(9);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getGpoyhr()){
              oEstu.setClaveInterna(6);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getLcr()){
              oEstu.setClaveInterna(18);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getOrina()){
              oEstu.setClaveInterna(22);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getPlaquetas()){
              oEstu.setClaveInterna(3);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getReticulositos()){
              oEstu.setClaveInterna(4);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getSblanca()){
              oEstu.setClaveInterna(2);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getSedimentacion()){
              oEstu.setClaveInterna(5);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getSodiop()){
              oEstu.setClaveInterna(19);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getSroja()){
              oEstu.setClaveInterna(1);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getTgo()){
              oEstu.setClaveInterna(13);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getTgp()){
              oEstu.setClaveInterna(14);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getTp()){
              oEstu.setClaveInterna(7);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getTpt()){
              oEstu.setClaveInterna(8);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getUrea()){
              oEstu.setClaveInterna(10);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getCitobact().compareTo("")!=0){
              oEstu.setObservaciones(oEstu.getCitobact());
              oEstu.setClaveInterna(27);
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getCultivo().compareTo("")!=0){
              oEstu.setClaveInterna(26);
              oEstu.setObservaciones(oEstu.getCultivo());
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             }
            }
            if(oEstu.getOtros().compareTo("")!=0){
              oEstu.setClaveInterna(30);
              oEstu.setObservaciones(oEstu.getOtros());
             try{ 
             nAfec=this.oEstu.insertarEstudioPac(); 
             } catch(Exception e){
             e.printStackTrace();
             FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al insertar"));
             } 
            }
            oEstu=new Estudios();
            oPCapa= new PacienteCapasits();
    }
        
    public String  getEdad(){
        return ed;
    }
    public void setEdad(String valor){
      this.ed=valor;  
    }
    
    public String  getHoy(){
        return hoy;
    }
    public void setHoy(String valor){
      this.hoy=valor;  
    }
    
    public void borrar(){
        oEstu= new Estudios();
        oPCapa= new PacienteCapasits();
    }
}

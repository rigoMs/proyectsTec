package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.CitamedicinaGeneralCapasits;
import mx.gob.hrrb.modelo.capasits.PruebasRapidas;

/**
 *
 * @author pedro
 */
@ManagedBean (name="ConsExt")
@SessionScoped
public class ConsultaHojaDiariaCE implements Serializable{
    public CitamedicinaGeneralCapasits oCitameGe;
    public CitamedicinaGeneralCapasits[] arrCE=null;
    public PruebasRapidas oPruebas;
    public CitamedicinaGeneralCapasits[] arrPR=null;
    public CitamedicinaGeneralCapasits[] arrH=null;
    public String sD1;
    public String sD2;
    public String sM1;
    public String sM2;
    public String sA1;
    public String sA2;

    public ConsultaHojaDiariaCE() {
        oCitameGe=new CitamedicinaGeneralCapasits();
        oPruebas= new PruebasRapidas();
        FechaCe();
    }
    
    public CitamedicinaGeneralCapasits getCExterna(){
        return oCitameGe;        
    }
    
    public PruebasRapidas getPruebasr(){
        return oPruebas;
    }
    
    public CitamedicinaGeneralCapasits[] getLista(){
        try{oCitameGe.buscarMedicoDeHoja();
            arrCE=(CitamedicinaGeneralCapasits[])oCitameGe.buscarTodos();   
        }
        catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
        }
        return arrCE;
    }
    
    public CitamedicinaGeneralCapasits[] getLista2(){
        try{
            arrPR=(CitamedicinaGeneralCapasits[])oCitameGe.buscarPruebasDeHoy(oCitameGe.getNum());   
        }
        catch(Exception e){
            e.printStackTrace();
            FacesContext.getCurrentInstance().addMessage(null,new FacesMessage(FacesMessage.SEVERITY_ERROR, "ERROR","al buscar"));
        }
        int col=arrPR.length+arrCE.length;
        System.out.println(col);
        return arrPR;
    }
     
    public CitamedicinaGeneralCapasits[] getLista3(){
         ArrayList<CitamedicinaGeneralCapasits> vObj= new ArrayList<CitamedicinaGeneralCapasits>();
        int n=0,nTam=0,no=0;
        getLista();
        getLista2();
        arrH=Arrays.copyOf(arrCE, arrCE.length+arrPR.length);
        System.arraycopy(arrPR, 0, arrH, arrCE.length, arrPR.length);
        return arrH;
    }
    
    public void FechaCe(){
        String fechace;
        if(oCitameGe.getFechaCita()==null){
            Date fecha=new Date();
            oCitameGe.setFechaCita(fecha);
        }
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String fec=ff.format(oCitameGe.getFechaCita()); 
        setD1(""+fec.charAt(8));
        setD2(""+fec.charAt(9));
        setM1(""+fec.charAt(5));
        setM2(""+fec.charAt(6));
        setA1(""+fec.charAt(2));
        setA2(""+fec.charAt(3));
    }
    
    public String getNombreMedico() throws Exception{
        String nombrec;
        oCitameGe.buscarMedicoDeHoja();
        nombrec=oCitameGe.getMed().getNombreCompleto();
        return nombrec;
    }

    public String getD1() {
        return sD1;
    }

    public void setD1(String sD1) {
        this.sD1 = sD1;
    }

    public String getD2() {
        return sD2;
    }

    public void setD2(String sD2) {
        this.sD2 = sD2;
    }

    public String getM1() {
        return sM1;
    }

    public void setM1(String sM1) {
        this.sM1 = sM1;
    }

    public String getM2() {
        return sM2;
    }

    public void setM2(String sM2) {
        this.sM2 = sM2;
    }

    public String getA1() {
        return sA1;
    }

    public void setA1(String sA1) {
        this.sA1 = sA1;
    }

    public String getA2() {
        return sA2;
    }

    public void setA2(String sA2) {
        this.sA2 = sA2;
    }
    
}

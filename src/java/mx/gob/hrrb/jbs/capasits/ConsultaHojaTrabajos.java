package mx.gob.hrrb.jbs.capasits;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.capasits.CitaTrabajoSocialCapasits;
import mx.gob.hrrb.modelo.capasits.GruposCapasits;

/**
 *
 * @author pedro
 */
@ManagedBean (name="HTrabS")
@SessionScoped
public class ConsultaHojaTrabajos implements Serializable{
    public CitaTrabajoSocialCapasits oTrabajoS;
    public GruposCapasits oGrupo;
    public CitaTrabajoSocialCapasits[] arrTS;
    public String sD1;
    public String sD2;
    public String sM1;
    public String sM2;
    public String sA1;
    public String sA2;

    
    public ConsultaHojaTrabajos() {
        oTrabajoS= new CitaTrabajoSocialCapasits();
        oGrupo=new GruposCapasits();
        FechaCe();
    }
    
    public CitaTrabajoSocialCapasits getTrabajoS(){
        return oTrabajoS;
    }
    
    public GruposCapasits getGrupo(){
        return oGrupo; 
    }
    
    public CitaTrabajoSocialCapasits[] getLista() throws Exception{
        oTrabajoS.buscarMedicoDeHoja();
            arrTS=(CitaTrabajoSocialCapasits[])oTrabajoS.buscarTodos();
            oTrabajoS.totalDeHorasDeTraslado();
        return arrTS;
    }
    
    public void FechaCe(){
        String fechace;
        if(oTrabajoS.getFechaCita()==null){
            Date fecha=new Date();
            oTrabajoS.setFechaCita(fecha);
        }
        SimpleDateFormat ff=new SimpleDateFormat("yyyy/MM/dd");
        String fec=ff.format(oTrabajoS.getFechaCita()); 
        setD1(""+fec.charAt(8));
        setD2(""+fec.charAt(9));
        setM1(""+fec.charAt(5));
        setM2(""+fec.charAt(6));
        setA1(""+fec.charAt(2));
        setA2(""+fec.charAt(3));
    }
     
    public String getNombreMedico() throws Exception{
        String nombrec;
        try {oGrupo.buscar();
        } catch(Exception e){
            e.printStackTrace();
        e.getMessage();}
        oTrabajoS.buscarMedicoDeHoja();
        nombrec=oTrabajoS.getMed().getNombreCompleto();
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

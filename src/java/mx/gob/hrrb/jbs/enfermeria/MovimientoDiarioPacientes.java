
package mx.gob.hrrb.jbs.enfermeria;

import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.enfermeria.reporte.MovimientoPacientes;
/**
 *
 * @author javier
 */
@ManagedBean(name="oMovDiario")
@ViewScoped
public class MovimientoDiarioPacientes implements Serializable {
    
    private MovimientoPacientes oMovimientoDiario;
    private boolean bRender;
    private String sPrueva="●";
    private Map<String,Integer> mapServicio;
    private MovimientoPacientes[] arrMovimiento=null;
    
    private int nTotalIngreso;
    private int nTotalPaseDe;
    private int nTotalPaseA;
    private int nTotalVivos;
    private int nTotalMuertos;
    private int nTotalDiasEstancia;    
    private int nTotalPacientes;
    private int nCamasDispo;    
    private int nExistenciaPacientes;
    private int nExis24Hrs;
    private int nIngresoEgresoDia;
    
    public MovimientoDiarioPacientes() {
        oMovimientoDiario = new MovimientoPacientes();        
        mapServicio= new HashMap<String,Integer>();
        cargaServicio();
        buscaNombreJefaEnfermera();
    }    
     
    private void buscaNombreJefaEnfermera(){
        try {
            oMovimientoDiario.buscaNombreJefeEnfermeria();            
        } catch (Exception ex) {
            Logger.getLogger(MovimientoDiarioPacientes.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    private void cargaServicio(){
        getMapServicio().put("CIRUGIA GENERAL", 13);
        getMapServicio().put("GINECO-OBSTETRICIA", 29);
        getMapServicio().put("MEDICINA INTERNA", 37);
        getMapServicio().put("PEDIATRIA", 56);
    }
    
    public void BuscaNombreServicio(){
        for (Entry<String,Integer> oSer : mapServicio.entrySet()){
            if(oSer.getValue()==getMovimientoDiario().getServicio().getClave()){
               getMovimientoDiario().getServicio().setDescripcion(oSer.getKey());
               break;
            }
        }
    }
        
    public void consultarMovimiento(){
        nTotalIngreso=0;nTotalPaseDe=0;nTotalPaseA=0;
        nTotalVivos=0; nTotalMuertos=0; nTotalDiasEstancia=0;
        nTotalPacientes=0; nCamasDispo=0;nTotalPacientes=0;
        nCamasDispo=0;nExistenciaPacientes=0;
        nIngresoEgresoDia=0;
        if(getMovimientoDiario().getFechaMovimiento()!=null
                || getMovimientoDiario().getServicio().getClave()!=0){
            try {
                bRender=true;
                BuscaNombreServicio();
                arrMovimiento= getMovimientoDiario().buscaMovimientoDiarioDePacientesPorServicioYfecha();
                if(arrMovimiento!=null && arrMovimiento.length>0){                    
                    this.getMovimientoDiario().buscaResumenMovimientoDiarioPacientes();
                    cargaTotales();
                }
                //RequestContext.getCurrentInstance().update(":frm_centrado");               
            } catch (Exception ex) {
                Logger.getLogger(MovimientoDiarioPacientes.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }
    
    public void cargaTotales(){
     try{
         for(MovimientoPacientes oMv:arrMovimiento){
             nTotalIngreso +=(oMv.getHoraIngreso()==null || oMv.getHoraIngreso().equals(""))?0:1;
             nTotalPaseDe +=(oMv.getPaseDe()==null || oMv.getPaseDe().equals("")?0:1);
             nTotalPaseA +=(oMv.getPaseA()==null || oMv.getPaseA().equals(""))?0:1;
             nTotalVivos +=(oMv.getHoraVivoStr()==null || oMv.getHoraVivoStr().equals(""))?0:1;
             nTotalMuertos +=(oMv.getHoraMuertoStr()==null || oMv.getHoraMuertoStr().equals(""))?0:1;
             nTotalDiasEstancia +=oMv.getEpisodio().getDiasEstancia();
             
         }
         int suma=nTotalVivos+nTotalMuertos;
         nExistenciaPacientes= this.getMovimientoDiario().getExistendiaPac()+nTotalVivos+nTotalMuertos;         
         nExis24Hrs = (nExistenciaPacientes+nTotalIngreso+nTotalPaseDe+nTotalPaseA)-(nTotalVivos+nTotalMuertos);
         nTotalPacientes=((nExistenciaPacientes+nTotalIngreso)-suma)+nIngresoEgresoDia;        
         nCamasDispo=this.getMovimientoDiario().getTotalCamas()-( (nExistenciaPacientes+nTotalIngreso)-suma);
     }catch(Exception ex){
          Logger.getLogger(MovimientoDiarioPacientes.class.getName()).log(Level.SEVERE, null, ex);
     }   
    }   
    
    public boolean getRender(){
        return bRender;
    }
    
    public MovimientoPacientes getMovimientoDiario() {
        return oMovimientoDiario;
    }

    public Map<String,Integer> getMapServicio() {
        return mapServicio;
    }

    public MovimientoPacientes[] getArrMovimiento() {
        return arrMovimiento;
    }

    public int getTotalIngreso() {
        return nTotalIngreso;
    }

    public int getTotalPaseDe() {
        return nTotalPaseDe;
    }

    public int getTotalPaseA() {
        return nTotalPaseA;
    }

    public int getTotalVivos() {
        return nTotalVivos;
    }

    public int getTotalMuertos() {
        return nTotalMuertos;
    }

    public int getTotalDiasEstancia() {
        return nTotalDiasEstancia;
    }

    public int getTotalPacientes() {
        return nTotalPacientes;
    }

    public int getCamasDispo() {
        return nCamasDispo;
    }

    public int getExistenciaPacientes() {
        return nExistenciaPacientes;
    }

    public int getExis24Hrs() {
        return nExis24Hrs;
    }

    public int getIngresoEgresoDia() {
        return nIngresoEgresoDia;
    }



    
}

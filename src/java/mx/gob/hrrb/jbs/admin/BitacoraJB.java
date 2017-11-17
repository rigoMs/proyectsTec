
package mx.gob.hrrb.jbs.admin;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.core.Bitacora;

/**
 *
 * @author DEL
 */

@ManagedBean(name="oBBi")
@ViewScoped
public class BitacoraJB implements Serializable{
        private long ClaveBitacora;
        private String IdUsuario;
        private String Accion;
        private Date FechaHoraAccion;
        private String Tabla;
        private String Llave;
   
        private Bitacora bitacora;
        
        private String visible="hidden";
        
    private ArrayList<Bitacora> arrBitacora= null;
    private Bitacora oBitacoraSeleccion=new Bitacora();
       
    public BitacoraJB(){
    Bitacora oBitacora = new Bitacora(); 
        try{
                arrBitacora = new ArrayList<Bitacora>(
                    Arrays.asList(
    
                            (Bitacora[])oBitacora.buscarTodos()));
        } catch(Exception e){
            e.printStackTrace();
        }
    }
    public ArrayList<Bitacora> getLista(){
        return arrBitacora;
    }
    
 public void agregarTabla(){
        Bitacora oBitacora = new Bitacora();
        try{
            arrBitacora = new ArrayList<Bitacora>(
            Arrays.asList((Bitacora[])oBitacora.buscarTodos()));
        }catch(Exception e){
            e.printStackTrace();
        }
        visible="visible";
    }
    
    public String getVisible(){
        return visible;
    }
    
    public void setVisible(String v){
        visible=v;
    }    
    public ArrayList<Bitacora> getArrBitacora() {
        return arrBitacora;
    }
    
    public Bitacora getBit(){
        return bitacora;
    }
    public Bitacora setBit(){
        return bitacora;
    }
    
    public Bitacora getSeleccion() {
        return this.oBitacoraSeleccion;
    }
    public void setSeleccion(Bitacora oBitacoraSeleccion) {
        this.oBitacoraSeleccion = oBitacoraSeleccion;
    }
    public long getClaveBitacora() {
        return ClaveBitacora;
    }
    public void setClaveBitacora(long ClaveBitacora) {
        this.ClaveBitacora = ClaveBitacora;
    }
    public String getIdUsuario() {
        return IdUsuario;
    }
    public void setIdUsuario(String IdUsuario) {
        this.IdUsuario = IdUsuario;
    }
    public String getsAccion() {
        return Accion;
    }
    public void setsAccion(String Accion) {
        this.Accion = Accion;
    }
    public Date getdFechaHoraAccion() {
        return FechaHoraAccion;
    }
    public void setdFechaHoraAccion(Date FechaHoraAccion) {
        this.FechaHoraAccion = FechaHoraAccion;
    }
    public String getsTabla() {
        return Tabla;
    }
    public void setsTabla(String Tabla) {
        this.Tabla = Tabla;
    }
    public String getsLlave() {
        return Llave;
    }
    public void setsLlave(String Llave) {
        this.Llave = Llave;
    }
    
}

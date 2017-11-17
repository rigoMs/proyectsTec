package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import java.util.Date;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.almacen.BitacoraMateriales;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;

/**
 *
 * @author GIL
 */
@ManagedBean(name = "oBitacora")
@ViewScoped
public class BitacoraReporte implements Serializable {
private BitacoraMateriales bitacora = null;
private BitacoraMateriales bitacoraSalidas=null;
private InventarioMateriales inventario = null;
private BitacoraMateriales[] arrBitacora = null;
private BitacoraMateriales[] arrBitacoraSalidas=null;
private Date fechaIn=null;
private Date fechaFi=null;

    public BitacoraReporte() {
        bitacora = new BitacoraMateriales();
        inventario = new InventarioMateriales();
    }

    public void rebotes(ActionEvent ae) throws Exception {
        listaEntradas();
    }
    public void eventoSalidas(ActionEvent even)throws Exception{
        listaSalidas();
    }
    
    public void eventoMovimientos(ActionEvent em)throws Exception{
        listaMovimientos();
    }
    
    public void listaEntradas() throws Exception {
        arrBitacora = (BitacoraMateriales[])  new BitacoraMateriales().buscarRangoFechas(getFechaIn(), getFechaFi());
        setFechaIn(null);
        setFechaFi(null);
    }
    
    public void listaSalidas(){
        try{
            arrBitacoraSalidas = (BitacoraMateriales[]) new 
            BitacoraMateriales().buscarRangoSalidas(getFechaIn(), getFechaFi());
            setFechaIn(null);
            setFechaFi(null);
        }catch(Exception ex){
            ex.printStackTrace();
        }
    }
    
    public void listaMovimientos(){
        try{
            arrBitacoraSalidas = (BitacoraMateriales[]) 
                new BitacoraMateriales().buscarRangoMovimientos(
                        getFechaIn(), getFechaFi());
            setFechaIn(null);
            setFechaFi(null);
        }catch(Exception ex){
            ex.printStackTrace();
        }          
    }
    
    public void eventoLimpiar(ActionEvent ae) throws Exception {
        setArrBitacora(null);
    }
    
    public void eventoLimpiarSalidas(ActionEvent ae) throws Exception {
        this.setArrBitacoraSalidas(null);
    }
    
    public BitacoraMateriales[] getArrBitacora() {
        return arrBitacora;
    }
    public void setArrBitacora(BitacoraMateriales[] arrBitacora) {
        this.arrBitacora = arrBitacora;
    }
    public BitacoraMateriales getBitacora() {
        return bitacora;
    }
    public void setBitacora(BitacoraMateriales bitacora) {
        this.bitacora = bitacora;
    }
    public InventarioMateriales getInventario() {
        return inventario;
    }
    public void setInventario(InventarioMateriales inventario) {
        this.inventario = inventario;
    }
    public Date getFechaIn() {
        return fechaIn;
    }
    public void setFechaIn(Date fechaIn) {
        this.fechaIn = fechaIn;
    }
    public Date getFechaFi() {
        return fechaFi;
    }
    public void setFechaFi(Date fechaFi) {
        this.fechaFi = fechaFi;
    }
    public BitacoraMateriales[] getArrBitacoraSalidas() {
        return arrBitacoraSalidas;
    }

    public void setArrBitacoraSalidas(BitacoraMateriales[] arrBitacoraSalidas) {
        this.arrBitacoraSalidas = arrBitacoraSalidas;
    }

    public BitacoraMateriales getBitacoraSalidas() {
        return bitacoraSalidas;
    }

    public void setBitacoraSalidas(BitacoraMateriales bitacoraSalidas) {
        this.bitacoraSalidas = bitacoraSalidas;
    }
}

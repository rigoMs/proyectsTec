package mx.gob.hrrb.modelo.almacen;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Proveedor;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author GIL
 */
public class BitacoraMateriales extends Material implements Serializable{
private Date fechaInicial;
private Date fechaFinal;
private CabeceraControl cabecera;
private DetalleValeColectivo detalle;
private Proveedor proveedor;
private AreaServicioHRRB area;
private Parametrizacion parametrizacion;
private InventarioMateriales inventario;
private AccesoDatos oAD;
    
    public BitacoraMateriales(){
        cabecera=new CabeceraControl();
        detalle=new DetalleValeColectivo();
        proveedor=new Proveedor();
        parametrizacion=new Parametrizacion();
        inventario=new InventarioMateriales();
        fechaInicial=null;
        fechaFinal=null;
    } 

    public BitacoraMateriales(Date fechaInicial,Date fechaFinal){
        this.fechaInicial=fechaInicial;
        this.fechaFinal=fechaFinal;

    }
    
    public BitacoraMateriales[] buscarRangoFechas(Date fechaIni,Date fechaFin) throws Exception{
	BitacoraMateriales  arrRet[]=null,oBitacora=null;
         
	ArrayList rst = null;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");           
        ArrayList<BitacoraMateriales>vObj=null;
	String sQuery = "";     
	int i=0;            
            sQuery = "SELECT * FROM buscaRangosEntradas('"+ oFec.format(fechaIni)+"','"+oFec.format(fechaFin)+"');"; 
            System.out.println("fechas "+sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
                if(rst!=null){
                    arrRet=new BitacoraMateriales[rst.size()];
                    vObj=new ArrayList <BitacoraMateriales>();
                    for(i=0;i<rst.size();i++){
                        oBitacora=new BitacoraMateriales();
                        oBitacora.setParametrizacion(new Parametrizacion());
                        oBitacora.setProveedor(new Proveedor());
                        oBitacora.setInventario(new InventarioMateriales());
                        ArrayList vRowTemp=(ArrayList)rst.get(i);
                        oBitacora.setClaveMaterial(((String)vRowTemp.get(0)));
                        oBitacora.setNombre(((String) vRowTemp.get(1)));
                        oBitacora.getPresentacion().setValor(((String) vRowTemp.get(2)));
                        oBitacora.getProveedor().setNombre(((String)vRowTemp.get(3)));
                        oBitacora.getParametrizacion().setValor(((String)vRowTemp.get(4)));
                        oBitacora.getCabecera().setFechaRecepcion((Date)vRowTemp.get(5));
                        oBitacora.getInventario().setLote(((String)vRowTemp.get(6)));
                        oBitacora.getInventario().setPrecio(((Double)vRowTemp.get(7)).intValue());
                        oBitacora.getInventario().setCant(((Double)vRowTemp.get(8)).intValue());
                                                            
                        vObj.add(oBitacora);
                    }
             int nTam = vObj.size();
            arrRet = new BitacoraMateriales[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public BitacoraMateriales[] buscarRangoSalidas(Date fechainicial,
    Date fechafinal) throws Exception{
    BitacoraMateriales  arrRet[]=null, oBitacora=null;         
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<BitacoraMateriales>vObj=null;
    String sQuery = "";     
    int i=0;  
          
        sQuery = "SELECT * FROM buscaRangosSalidas('"+ 
                oFec.format(fechainicial)+"','"+oFec.format(fechafinal)+"');"; 
        System.out.println("fechas "+sQuery);
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new BitacoraMateriales[rst.size()];
            vObj=new ArrayList <BitacoraMateriales>();
            for(i=0;i<rst.size();i++){
                oBitacora=new BitacoraMateriales();
                oBitacora.setArea(new AreaServicioHRRB());
                oBitacora.setCabecera(new CabeceraControl());
                oBitacora.setInventario(new InventarioMateriales());
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oBitacora.getArea().setDescripcion(((String)vRowTemp.get(0)));
                oBitacora.setClaveMaterial(((String)vRowTemp.get(1)));
                oBitacora.setNombre(((String) vRowTemp.get(2)));
                oBitacora.getPresentacion().setValor(((String) vRowTemp.get(3)));
                oBitacora.getCabecera().setFechaRecepcion((Date)vRowTemp.get(4));
                oBitacora.getInventario().setLote(((String)vRowTemp.get(5)));                       
                oBitacora.getInventario().setCant(((Double)vRowTemp.get(6)).intValue());

                vObj.add(oBitacora);
            }
            int nTam = vObj.size();
            arrRet = new BitacoraMateriales[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public BitacoraMateriales[] buscarRangoMovimientos (Date fechainicial,Date fechafinal) throws Exception{    
    BitacoraMateriales  arrRet[]=null, 
    oBitacora=null;         
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<BitacoraMateriales>vObj=null;
    String sQuery = "";     
    int i=0;  
          
        sQuery = "SELECT * FROM buscaRangosMovimintos('"+ 
                oFec.format(fechainicial)+"','"+oFec.format(fechafinal)+"');"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new BitacoraMateriales[rst.size()];
            vObj=new ArrayList <BitacoraMateriales>();
            for(i=0;i<rst.size();i++){
                oBitacora=new BitacoraMateriales();
                oBitacora.setArea(new AreaServicioHRRB());
                oBitacora.setCabecera(new CabeceraControl());
                oBitacora.setInventario(new InventarioMateriales());
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oBitacora.getArea().setDescripcion(((String)vRowTemp.get(0)));
                oBitacora.setClaveMaterial(((String)vRowTemp.get(1)));
                oBitacora.setNombre(((String) vRowTemp.get(2)));
                oBitacora.getParametrizacion().setValor(((String) vRowTemp.get(3)));
                oBitacora.getCabecera().setFechaRecepcion((Date)vRowTemp.get(4));
                oBitacora.getInventario().setLote(((String)vRowTemp.get(5)));                       
                oBitacora.getInventario().setCant(((Double)vRowTemp.get(6)).intValue());
                vObj.add(oBitacora);
            }
            int nTam = vObj.size();
            arrRet = new BitacoraMateriales[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    
    public Date getFechaInicial() {
        return fechaInicial;
    }

    public void setFechaInicial(Date fechaInicial) {
        this.fechaInicial = fechaInicial;
    }

    public Date getFechaFinal() {
        return fechaFinal;
    }

    public void setFechaFinal(Date fechaFinal) {
        this.fechaFinal = fechaFinal;
    }

    public CabeceraControl getCabecera() {
        return cabecera;
    }

    public void setCabecera(CabeceraControl cabecera) {
        this.cabecera = cabecera;
    }

    public DetalleValeColectivo getDetalle() {
        return detalle;
    }

    public void setDetalle(DetalleValeColectivo detalle) {
        this.detalle = detalle;
    }

    public Proveedor getProveedor() {
        return proveedor;
    }

    public void setProveedor(Proveedor proveedor) {
        this.proveedor = proveedor;
    }

    public Parametrizacion getParametrizacion() {
        return parametrizacion;
    }

    public void setParametrizacion(Parametrizacion parametrizacion) {
        this.parametrizacion = parametrizacion;
    }

    public InventarioMateriales getInventario() {
        return inventario;
    }

    public void setInventario(InventarioMateriales inventario) {
        this.inventario = inventario;
    }

    public AreaServicioHRRB getArea() {
        return area;
    }

    public void setArea(AreaServicioHRRB area) {
        this.area = area;
    }
}
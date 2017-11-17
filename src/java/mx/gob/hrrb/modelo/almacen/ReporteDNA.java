package mx.gob.hrrb.modelo.almacen;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.DetalleValeColectivo;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author GIL
 */
public class ReporteDNA extends Material implements Serializable{
private DetalleValeColectivo colectivo;
private InventarioMateriales inventario;
private ValeColectivo vale;
private AccesoDatos oAD;
private String area;

    public ReporteDNA(){
       vale=new ValeColectivo();
       colectivo=new DetalleValeColectivo();
       inventario=new InventarioMateriales();
    }
        
    public ReporteDNA[] buscarRangoDNA(Date fechaIni,Date fechaFin) throws Exception{
    ReporteDNA  arrRet[]=null,oDNA=null;
    ArrayList rst = null;
    ArrayList<ReporteDNA>vObj=null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");  
    String sQuery = "";     
    int i=0;            
        sQuery = "SELECT * FROM reporteDemandaNoatendida('"+ oFec.format(fechaIni)+"','"+oFec.format(fechaFin)+"');"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new ReporteDNA[rst.size()];
            vObj=new ArrayList <ReporteDNA>();
            for(i=0;i<rst.size();i++){
                oDNA=new ReporteDNA();
                oDNA.setInventario(new InventarioMateriales());
                oDNA.setColectivo(new DetalleValeColectivo());
                oDNA.setVale(new ValeColectivo());
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oDNA.getVale().setFechaEmision((Date)vRowTemp.get(0));
                oDNA.setClaveMaterial(((String)vRowTemp.get(1))); 
                oDNA.setNombre(((String) vRowTemp.get(2)));                        
                oDNA.setArea(((String) vRowTemp.get(3)));
                oDNA.getColectivo().setCantSolicitada(((Double)vRowTemp.get(4)).shortValue());
                vObj.add(oDNA);
            }
            int nTam = vObj.size();
            arrRet = new ReporteDNA[nTam];
            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

     
    public InventarioMateriales getInventario() {
        return inventario;
    }
 
    public void setInventario(InventarioMateriales inventario) {
        this.inventario = inventario;
    }

    
    public ValeColectivo getVale() {
        return vale;
    }

     
    public void setVale(ValeColectivo vale) {
        this.vale = vale;
    }

    public DetalleValeColectivo getColectivo() {
        return colectivo;
    }

    public void setColectivo(DetalleValeColectivo colectivo) {
        this.colectivo = colectivo;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }
}

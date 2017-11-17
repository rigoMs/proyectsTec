package mx.gob.hrrb.modelo.almacen;

import java.io.Serializable;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author GIL
 */
public class ControlMaterial implements Serializable{
private AccesoDatos oAD;
private Parametrizacion oSubprograma;
private Parametrizacion oFuenteFinanciamiento;
private CabeceraControl oCab;
private InventarioMateriales oInv;
private int nCant;
    
    public int insertarMaterialACabecera() throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        if (this == null) {           
            throw new Exception("Cabecera:insertar:error de programacion ,faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaInventarioAndControlMaterial('"+
                    oInv.getMaterial().getClave() +"'::character varying,'"+
                    oInv.getLote()+"'::character varying,'" +
                    "ALMA"+"'::character varying,'" +
                    oInv.getCaducidad()+"'::date,"+
                    oInv.getExistencia()+"::smallint,'"+                
                    "E"+"'::character, "+
                    oInv.getPrecio()+"::numeric);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = (((Double) vRowTemp.get(0)).intValue());
                    
                  }
            }
        }
        return nRet;
    }
 
    public Parametrizacion getSubprograma() {
        return oSubprograma;
    }

    public void setSubprograma(Parametrizacion oSubprograma) {
        this.oSubprograma = oSubprograma;
    }

    public Parametrizacion getFuenteFinanciamiento() {
        return oFuenteFinanciamiento;
    }

    public void setFuenteFinanciamiento(Parametrizacion oFuenteFinanciamiento) {
        this.oFuenteFinanciamiento = oFuenteFinanciamiento;
    }
    
    public int getCant() {
        return nCant;
    }

    public void setCant(int nCant) {
        this.nCant = nCant;
    }
    
    public InventarioMateriales getInv(){
        return this.oInv;
    }
    
    public void setInv(InventarioMateriales val){
        this.oInv = val;
    }
}

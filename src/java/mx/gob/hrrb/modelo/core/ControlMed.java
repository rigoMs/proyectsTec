package mx.gob.hrrb.modelo.core;

import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author 
 */
public class ControlMed extends Control {
private String sNumFactura;
private String sNumPedido;
private int nPartida;
private DetalleMedicamentos oDetalle;
    
    
    public ControlMed[] buscarXdia(Date fecha) throws Exception {
        ControlMed arrRet[] = null, oCtl = null;
        ArrayList rst = null;
        String sQuery = "";
        ArrayList<ControlMed> vObj = null;
        int i = 0;
        if (this == null) {   //completar llave
            throw new Exception("Control.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaControlXdia('" + fecha + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                vObj = new ArrayList<ControlMed>();
                for (i = 0; i < rst.size(); i++) {
                    oCtl = new ControlMed();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oCtl.getDetalle().getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                    oCtl.getDetalle().getMedicamento().setNombre(((String) vRowTemp.get(1)));
                    oCtl.getDetalle().getMedicamento().setPresentacion(((String) vRowTemp.get(2)));                    
                    oCtl.getDetalle().getMedicamento().setDescripcion(((String) vRowTemp.get(3)));
                    oCtl.getDetalle().getMedicamento().setCodBarras(((Double) vRowTemp.get(4)).intValue());
                    oCtl.getDetalle().setLote(((String) vRowTemp.get(5)));
                    oCtl.setTipoMovimiento(((String) vRowTemp.get(6)));
                    oCtl.setCant(((Double) vRowTemp.get(7)).intValue());
                    vObj.add(oCtl);
                }
                int nTam = vObj.size();
                arrRet = new ControlMed[nTam];

                for (i = 0; i < nTam; i++) {
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
    }    

    public String getNumFactura() {
        return sNumFactura;
    }

    public void setNumFactura(String sNumFactura) {
        this.sNumFactura = sNumFactura;
    }

    public String getNumPedido() {
        return sNumPedido;
    }

    public void setNumPedido(String sNumPedido) {
        this.sNumPedido = sNumPedido;
    }

    public int getPartida() {
        return nPartida;
    }

    public void setPartida(int nPartida) {
        this.nPartida = nPartida;
    }

    public DetalleMedicamentos getDetalle() {
        return oDetalle;
    }

    public void setDetalle(DetalleMedicamentos oDetalle) {
        this.oDetalle = oDetalle;
    }
}

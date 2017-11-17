package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class Control implements Serializable {
    protected AccesoDatos oAD;
    protected long nIdControl;
    protected Date dRegistro;
    protected Date dFechaRecepcion;
    protected Destino oDestino;
    protected String sTipoMov;
    protected Parametrizacion oAdquisicion;
    protected PersonalHospitalario oQuienRegistra;
    protected Parametrizacion oMotivo;
    protected String sOtroMotivo;
    protected Proveedor oProv;
    protected AreaServicioHRRB oArea;
    protected int nCant;
    protected String sObs;
    
    public Control() {
    }
    
    public int insertar(String sUsuario,String sClaveMedicamento, 
            String sPresentacion, String sLote, String nTipoMov, 
            int nCantidad, long nCodBarras) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (sClaveMedicamento == null || sClaveMedicamento.equals("")) {   //completar llave
            throw new Exception("Control.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaControl('" + sUsuario + "','" + sClaveMedicamento + "','" + sPresentacion + "','" + sLote + "','" + nTipoMov.charAt(1) + "'," + nCantidad + "," + nCodBarras + "::bigint);";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;

    }

    public long getIdControl() {
        return nIdControl;
    }

    public void setIdControl(long nIdControl) {
        this.nIdControl = nIdControl;
    }

    public String getTipoMovimiento() {
        return sTipoMov;
    }

    public void setTipoMovimiento(String sTipoMov) {
        this.sTipoMov = sTipoMov;
    }

    public Date getRegistro() {
        return dRegistro;
    }

    public void setRegistro(Date dRegistro) {
        this.dRegistro = dRegistro;
    }

    public int getCant() {
        return nCant;
    }

    public void setCant(int nCant) {
        this.nCant = nCant;
    }

    public Date getFechaRecepcion() {
        return dFechaRecepcion;
    }

    public void setFechaRecepcion(Date dFechaRecepcion) {
        this.dFechaRecepcion = dFechaRecepcion;
    }

    

    public Parametrizacion getAdquisicion() {
        return oAdquisicion;
    }

    public void setAdquisicion(Parametrizacion oAdquisicion) {
        this.oAdquisicion = oAdquisicion;
    }

    public Destino getDestino() {
        return oDestino;
    }

    public void setDestino(Destino oDestino) {
        this.oDestino = oDestino;
    }

    public String getObs() {
        return sObs;
    }

    public void setObs(String sObs) {
        this.sObs = sObs;
    }

    public PersonalHospitalario getQuienRegistra() {
        return oQuienRegistra;
    }

    public void setQuienRegistra(PersonalHospitalario oQuienRegistra) {
        this.oQuienRegistra = oQuienRegistra;
    }

    public Parametrizacion getMotivo() {
        return oMotivo;
    }

    public void setMotivo(Parametrizacion oMotivo) {
        this.oMotivo = oMotivo;
    }

    public String getOtroMotivo() {
        return sOtroMotivo;
    }

    public void setOtroMotivo(String sOtroMotivo) {
        this.sOtroMotivo = sOtroMotivo;
    }
    
    public Proveedor getProveedor(){
        return this.oProv;
    }
    public void setProveedor(Proveedor val){
        this.oProv = val;
    }
    
    public AreaServicioHRRB getArea(){
        return this.oArea;
    }
    public void setArea(AreaServicioHRRB val){
        this.oArea = val;
    }
}

package mx.gob.hrrb.modelo.caja;

import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public abstract class Recibo implements Serializable{
    protected AccesoDatos oAD;
    protected String sUsuarioFirmado;
    protected ServicioRealizado arrServiciosCubiertos[];
    protected Date dFechaCancelacion;
    protected Date dFechaEmision;
    protected int nFolio;
    protected long nFolioInterno;
    protected BigDecimal nMonto;
    protected Parametrizacion oSituacion;
    protected Cuenta oCuenta;
    protected Caja oCajaEmite;
    protected Caja oCajaCancela;
        
    public Recibo(){
	HttpServletRequest req;
	req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
	if (req.getSession().getAttribute("oFirm") != null) {
            sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
	}
    }
        
    public boolean buscar() throws Exception{
        return false; 
    }
        
    public boolean buscarReciboCancelado() throws Exception{
        return false; 
    }
        
    public Recibo[] buscarTodos() throws Exception{
        return null; 
    }
        
    public Recibo[] buscarHistorialTurno() throws Exception{
        return null; 
    }
        
    public Recibo[] buscarFoliosEmitidosTurnoActual(int nIdCaja)throws Exception{
        return null; 
    }
        
    public Recibo[] buscarRecibosCanceladosTurno(Date dFecha,String sCveTurno) throws Exception{
        return null; 
    }
        
    public Recibo[] buscarRelacionCuentasVales(Date dFecha)throws Exception{
        return null; 
    }
        
    public int insertar() throws Exception{
        return 0; 
    } 
        
    public int modificar() throws Exception{
        return 0; 
    } 

    public int eliminar() throws Exception{
        return 0; 
    } 
        
    public Recibo[] buscarCancelados(Date dFechaI,Date dFechaF) throws Exception{
	return null; 
    }
        
    public int cancelar() throws Exception{
        return 0;
    }
        
    public boolean cancelarFolioDisponible(ArrayList<String> vTransac) throws Exception{
        return false;
    }
    
    public String getFoliosConcat(Recibo[] arrRecibo){
        String sFolios="";
        for(int i=0;i<arrRecibo.length;i++){
            sFolios+=(i==arrRecibo.length)?arrRecibo[i].getFolio():arrRecibo[i].getFolio()+",";
        }
        return sFolios;
    }
                        
    public ServicioRealizado[] getServiciosCubiertos() {
	return arrServiciosCubiertos;
    }

    public void setServiciosCubiertos(ServicioRealizado[] valor) {
	arrServiciosCubiertos=valor;
    }

    public Date getFechaCancelacion() {
	return dFechaCancelacion;
    }

    public void setFechaCancelacion(Date valor) {
	dFechaCancelacion=valor;
    }

    public Date getFechaEmision() {
        return dFechaEmision;
    }

    public void setFechaEmision(Date valor) {
	dFechaEmision=valor;
    }

    public int getFolio() {
	return nFolio;
    }

    public void setFolio(int valor) {
	nFolio=valor;
    }

    public long getFolioInterno() {
	return nFolioInterno;
    }

    public void setFolioInterno(long valor) {
	nFolioInterno=valor;
    }

    public BigDecimal getMonto() {
        return nMonto;
    }

    public void setMonto(BigDecimal valor) {
	nMonto=valor;
    }

    public Parametrizacion getSituacion() {
	return oSituacion;
    }

    public void setSituacion(Parametrizacion valor) {
	oSituacion=valor;
    }

    public Cuenta getCuenta() {
	return oCuenta;
    }

    public void setCuenta(Cuenta valor) {
	oCuenta=valor;
    }

    public Caja getCajaEmite() {
        return oCajaEmite;
    }

    public void setCajaEmite(Caja oCaja) {
        this.oCajaEmite = oCaja;
    }

    public Caja getCajaCancela() {
        return oCajaCancela;
    }

    public void setCajaCancela(Caja oCajaCancela) {
        this.oCajaCancela = oCajaCancela;
    }
}
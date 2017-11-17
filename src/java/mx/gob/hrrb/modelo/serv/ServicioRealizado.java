package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.caja.AutorizacionPago;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.math.BigDecimal;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
//import mx.gob.hrrb.modelo.caja.Recibo;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public abstract class ServicioRealizado implements Serializable{
	protected AccesoDatos oAD;
	protected String sUsuarioFirmado;
	protected boolean bSurtido;
	protected Date dFechaAutorizacion;
	protected Date dFechaPago;
	protected Date dFechaSolicitud;
	/**
	 * Fecha de surtido (medicamento, material, quimio) o realización (estudio,
	 * procedimiento, otros)
	 */
	protected Date dFechaSurtidoRealizacion;
	protected short nCantACobrar;
	protected short nCantAdquiridaPac;
	protected short nCantAplicada;
	protected short nCantSolicitada;
	protected short nCantSurtida;
	protected BigDecimal nCostoUnitACobrar;
        protected BigDecimal nMontocobrado;
	protected int nIdentificador;
	protected PersonalHospitalario oAutorizadoPor;
	protected Parametrizacion oSitPago;
	protected Parametrizacion oSituacion;
	protected AutorizacionPago oAutorizacionPago;
        protected EpisodioMedico oEpisodio;

	public ServicioRealizado(){
        
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
	/**
	 * Obtiene el Servicio Cobrable específico, a redefinir en cada hijo; por ejemplo,
	 * en DetalleRecetarioColectivo deberá devolver un objeto de tipo Medicamento (que
	 * hereda de Servicio Cobrable)
     * @return 
	 */
	public abstract ServicioCobrable getServicioCobrable();
	public abstract void setServicioCobrable(ServicioCobrable oValor);
	public abstract boolean buscar() throws Exception;	
	public abstract ServicioRealizado[] buscarTodos() throws Exception; 
	public abstract int insertar() throws Exception;
	public abstract int modificar() throws Exception;
	public abstract int eliminar() throws Exception;
	
	public boolean getSurtido() {
	return bSurtido;
	}

	public void setSurtido(boolean valor) {
	bSurtido=valor;
	}

	public Date getFechaAutorizacion() {
	return dFechaAutorizacion;
	}

	public void setFechaAutorizacion(Date valor) {
	dFechaAutorizacion=valor;
	}

	public Date getFechaPago() {
	return dFechaPago;
	}

	public void setFechaPago(Date valor) {
	dFechaPago=valor;
	}

	public Date getFechaSolicitud() {
	return dFechaSolicitud;
	}

	public void setFechaSolicitud(Date valor) {
	dFechaSolicitud=valor;
	}

	public Date getFechaSurtidoRealizacion() {
	return dFechaSurtidoRealizacion;
	}

	public void setFechaSurtidoRealizacion(Date valor) {
	dFechaSurtidoRealizacion=valor;
	}

	public short getCantACobrar() {
	return nCantACobrar;
	}

	public void setCantACobrar(short valor) {
	nCantACobrar=valor;
	}

	public short getCantAdquiridaPac() {
	return nCantAdquiridaPac;
	}

	public void setCantAdquiridaPac(short valor) {
	nCantAdquiridaPac=valor;
	}

	public short getCantAplicada() {
	return nCantAplicada;
	}

	public void setCantAplicada(short valor) {
	nCantAplicada=valor;
	}

	public short getCantSolicitada() {
	return nCantSolicitada;
	}

	public void setCantSolicitada(short valor) {
	nCantSolicitada=valor;
	}

	public short getCantSurtida() {
	return nCantSurtida;
	}

	public void setCantSurtida(short valor) {
	nCantSurtida=valor;
	}

	public BigDecimal getCostoUnitACobrar() {
	return nCostoUnitACobrar;
	}

	public void setCostoUnitACobrar(BigDecimal valor) {
	nCostoUnitACobrar=valor;
	}

	public int getIdentificador() {
	return nIdentificador;
	}

	public void setIdentificador(int valor) {
	nIdentificador=valor;
	}

	public PersonalHospitalario getAutorizadoPor() {
	return oAutorizadoPor;
	}

	public void setAutorizadoPor(PersonalHospitalario valor) {
	oAutorizadoPor=valor;
	}

	public Parametrizacion getSitPago() {
	return oSitPago;
	}

	public void setSitPago(Parametrizacion valor) {
	oSitPago=valor;
	}

	public Parametrizacion getSituacion() {
	return oSituacion;
	}

	public void setSituacion(Parametrizacion valor) {
	oSituacion=valor;
	}

	public AutorizacionPago getAutorizacionPago() {
	return oAutorizacionPago;
	}

	public void setAutorizacionPago(AutorizacionPago valor) {
	oAutorizacionPago=valor;
	}

        public EpisodioMedico getEpisodio(){
            return this.oEpisodio;
        }
        public void setEpisodio(EpisodioMedico valor){
            this.oEpisodio = valor;
        }
    public BigDecimal getMontocobrado() {
        return nMontocobrado;
    }

    public void setMontocobrado(BigDecimal nMontocobrado) {
        this.nMontocobrado = nMontocobrado;
    }
} 

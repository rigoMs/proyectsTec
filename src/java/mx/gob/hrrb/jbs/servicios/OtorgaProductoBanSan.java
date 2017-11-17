/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.jbs.servicios;

import java.io.Serializable;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.application.FacesMessage;
import javax.faces.event.ActionEvent;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.serv.DetalleSolicitudHemoderivado;
import mx.gob.hrrb.modelo.serv.Donador;
import mx.gob.hrrb.modelo.serv.ProductoHemoderivado;
import mx.gob.hrrb.modelo.serv.ReporteHojaEgresoHemoderivados;
import mx.gob.hrrb.modelo.serv.SolicitudSangre;
import mx.gob.hrrb.modelo.core.Parametrizacion;


/**
 *
 * @author Pablo
 */
@ManagedBean (name="oBancoSan")
@ViewScoped
public class OtorgaProductoBanSan extends OtorgarServicio implements Serializable {

    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario;
    private String sUrl;
    private ProductoHemoderivado oProducto;
    private DetalleSolicitudHemoderivado oDetalle;
    private DetalleSolicitudHemoderivado oSelec;
    private SolicitudSangre oSolicitud;
    private SolicitudSangre oSolSan;
    private List<SolicitudSangre> lSolicitudes;
    private List<SolicitudSangre> lSolAmbulatoria;
    private List<SolicitudSangre> lDonadores;
    private List<ReporteHojaEgresoHemoderivados> lReporteSol;
    private List<ReporteHojaEgresoHemoderivados> lReporteAmb;
    private List<Parametrizacion> lTipoPac;
    private List<Parametrizacion> lGrupoSan;
    private List<Parametrizacion> lRH;
    private List<ProductoHemoderivado> lProducto;
    private Date dFecSolicitud;
    private Date dFecActual;
    private SolicitudSangre oSeleccionado;
    private SolicitudSangre oSelecAmbulatorio;
    private SolicitudSangre oSeleccionadoDonador;
    private ReporteHojaEgresoHemoderivados oReporte;
    private String sNombre;
    private String sApPaterno;
    private String sApMaterno;
    private int nNumExpe;
    private Donador oDonador;
    private boolean bShowButton=true;
    private Date dFecIni;
    private Date dFecFin;
    private ArrayList<ProductoHemoderivado> lProdAgregado;
    private DiagnosticoCIE10 oDiag;
    
    public OtorgaProductoBanSan() {
        oDiag = new DiagnosticoCIE10();
        dFecActual = new Date();
        oDonador = new Donador();
        oDetalle = new DetalleSolicitudHemoderivado();
        // = new ArrayList<ProductoHemoderivado>();
        oFirm = new Firmado();
        oSolicitud = new SolicitudSangre();
        faceContext = FacesContext.getCurrentInstance();
        httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
        if(httpServletRequest.getSession().getAttribute("oFirm") !=null){
            oFirm = (Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario = oFirm.getUsu().getIdUsuario();
            sUrl = httpServletRequest.getRequestURL().toString().toLowerCase();
        }
    }

    @Override
    public List buscarDetalleSolicitud() {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public void listaOrdenes() {
        try{
            lSolicitudes = new ArrayList<SolicitudSangre>(Arrays.asList(new SolicitudSangre().buscarSolicitudesBS(getFecSolicitud())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public List<ProductoHemoderivado> getListaProductos(){
        try{
            lProducto = new ArrayList<ProductoHemoderivado>(Arrays.asList(new SolicitudSangre().buscarProductoHemoderivado()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lProducto;
    }
    
    public List<Parametrizacion> getListaTipoPac(){
        try{
            lTipoPac = new ArrayList<Parametrizacion>(Arrays.asList(new Parametrizacion().buscarTipoPaciente()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lTipoPac;
    }
    
    public List<Parametrizacion> getListaGrupoSan(){
        try{
            lGrupoSan = new ArrayList<Parametrizacion>(Arrays.asList(new Parametrizacion().buscarGrupoSanguineo()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lGrupoSan;
    }
    
    public List<Parametrizacion> getListaRH(){
        try{
            lRH = new ArrayList<Parametrizacion>(Arrays.asList(new Parametrizacion().buscarRH()));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
        return lRH;
    }
    
    public void listaOrdenesAmbulatorias(){
        try{
            lSolAmbulatoria = new ArrayList<SolicitudSangre>(Arrays.asList(new SolicitudSangre().buscarSolicitudesAmbulatorias(getFecSolicitud())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void rebotes(ActionEvent ae) throws Exception{
        listaOrdenes();
    }
    
    public void rebotesSolPend(ActionEvent ae) throws Exception{
        listaOrdenesAmbulatorias();
    }
    
    public void detalleSolicitudProducto(){
        try{
            if(oSeleccionado == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un paciente",""));
            }else if(oSeleccionado != null){
                oSolSan = new SolicitudSangre().buscarDetalleSolSan(oSeleccionado.getIdSolicitud());
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void agregarListaProducto(){
        if(this.getSolicitud().getDetalle().getProductoH().getDescripcion() == null){
            FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Seleccione un Producto"));
        }else if(oSolicitud.getDetalle().getProductoH().getDescripcion() != null){
            if(this.getProdAgregado() == null){
                this.setProdAgregado(new ArrayList<ProductoHemoderivado>());
                oSolicitud.asosiacionDatos(lProducto);
                lProdAgregado.add(this.getSolicitud().getDetalle().getProductoH());
                oSolicitud.getDetalle().setServicioCobrable(new ProductoHemoderivado());
                System.out.println("Tamaño del arreglo: " + lProdAgregado.size());
            }else{
                oSolicitud.asosiacionDatos(lProducto);
                lProdAgregado.add(this.getSolicitud().getDetalle().getProductoH());
                oSolicitud.getDetalle().setServicioCobrable(new ProductoHemoderivado());
                System.out.println("Tamaño del arreglo: " + lProdAgregado.size());
            } 
        }
       
    }
    
    public void guardarSolicitudApoyoInstitucional(){
        try{
            if(lProdAgregado.isEmpty()){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"ERROR","Ingrese productos"));
            }else if (!lProdAgregado.isEmpty()){
                if(oSolicitud.insertaSolicitudApoyoInstitucional(lProdAgregado, getDiag().getClave())==1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"EXITO","Registro guardado correctamente"));
                    oSolicitud.getPaciente().setNombres("");
                    oSolicitud.getPaciente().setApPaterno("");
                    oSolicitud.getPaciente().setApMaterno("");
                    oSolicitud.setNombreInstitucion("");
                    oSolicitud.setNumPlaquetas(0);
                    oSolicitud.getDetalle().getProductoH().setUnidades(0);
                    oSolicitud.setHb(0);
                    oSolicitud.setHo(0);
                    oSolicitud.setTp(0);
                    oSolicitud.setTTp(0);
                    oSolicitud.getTipoSangre().setValor("");
                    //oSolicitud.getTipoPacSolicita().setValor("");
                    oSolicitud.getRH().setValor("");
                    this.setProdAgregado(null);
                    oSolicitud.getDetalle().getProductoH().setClave("");
                    oSolicitud.getDetalle().getProductoH().setDescripcion("");
                    oSolicitud.getDetalle().getProductoH().setUnidades(0);
                }else if(oSolicitud.insertaSolicitudApoyoInstitucional(lProdAgregado, getDiag().getClave()) != 1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","Error en la base de datos"));
                }
            }
            
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void borrarProducto(ProductoHemoderivado oProd){
        lProdAgregado.remove(oProd);
        System.out.println("Tamaño del arreglo: " + lProdAgregado.size());
    }
    
    public void detalleSolicitudProducto2(){
        try{
            if(oSelecAmbulatorio == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un paciente",""));
            }else if(oSelecAmbulatorio != null){
                oSolSan = new SolicitudSangre().buscarDetalleSolSan(oSelecAmbulatorio.getIdSolicitud());
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void datosHojaEgreso(){
        try{
            oSolicitud = new SolicitudSangre().buscarDetalleHojaEgreso(getNombre(), getApPaterno(), getApMaterno(), getNumExpe(), getFecSolicitud());
            oReporte = new ReporteHojaEgresoHemoderivados().oReporteCantidad(getNombre(), getApPaterno(), getApMaterno(), getNumExpe(), getFecSolicitud());
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void listaPacientesDonadores(){
        try{
            lDonadores = new ArrayList<SolicitudSangre>(Arrays.asList(new SolicitudSangre().buscarDonadoresPaciente(getNombre(), getApPaterno(), getApMaterno(), getNumExpe(), getFecSolicitud())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void reporteProductoSolicitado(){
        try{
            lReporteSol = new ArrayList<ReporteHojaEgresoHemoderivados>(Arrays.asList(new ReporteHojaEgresoHemoderivados().buscarCantidadesSurtidas(getFecIni(), getFecFin())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
   
    public void reporteTranAmbulatoria(){
        try{
            lReporteAmb = new ArrayList<ReporteHojaEgresoHemoderivados>(Arrays.asList(new ReporteHojaEgresoHemoderivados().buscarTodasSolAmbulatorias(getFecIni(), getFecFin())));
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void asociacionDatos(ArrayList<DetalleSolicitudHemoderivado> arrDet){
            for(DetalleSolicitudHemoderivado ds:arrDet){
                if(ds.getIdentificador() != 0){
                    if(ds.getIdentificador() == getSelec().getIdentificador()){
                        getDetalle().setCantSurtida(ds.getCantSolicitada());
                    }
            }
    }
    }
    
    public void modificarEstadoProducto(int nClave){
        try{
            if(oSelec == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Producto",""));
            }else if(oSelec != null){
                asociacionDatos(getSolSan().getDetalleHemoderivado());
                if(oDetalle.modificarEstadoProductoSolicitado(nClave, oSelec.getIdentificador())==1){
                    if(nClave == 1){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Producto Surtido",""));
                        oSolSan = new SolicitudSangre().buscarDetalleSolSan(oSeleccionado.getIdSolicitud());
                    }else if(nClave == 2){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Producto Eliminado",""));
                        oSolSan = new SolicitudSangre().buscarDetalleSolSan(oSeleccionado.getIdSolicitud());
                    }
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error en la base de datos",""));
                }
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void modificarEstadoProducto2(int nClave){
        try{
            if(oSelec == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un Producto",""));
            }else if(oSelec != null){
                asociacionDatos(getSolSan().getDetalleHemoderivado());
                if(oDetalle.modificarEstadoProductoSolicitado(nClave, oSelec.getIdentificador())==1){
                    if(nClave == 1){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Producto Surtido",""));
                        oSolSan = new SolicitudSangre().buscarDetalleSolSan(oSelecAmbulatorio.getIdSolicitud());
                    }else if(nClave == 2){
                        FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Producto Eliminado",""));
                        oSolSan = new SolicitudSangre().buscarDetalleSolSan(oSelecAmbulatorio.getIdSolicitud());
                    }
                }else{
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error en la base de datos",""));
                }
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void rebotesInsert(ActionEvent ae) throws Exception{
        insertaDonadorAltruista();
    }
    
    public void insertaDonador(){
        try{
                if(oDonador.insertaDonador(oSeleccionadoDonador.getIdSolicitud())==1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Donador registrado exitosamente",""));
                    oDonador.setNombreDonador(null);
                    oDonador.setConsecutivo(0);
                    oDonador.setFechaDonacion(null);
                    
                }else if(oDonador.insertaDonador(oSeleccionadoDonador.getIdSolicitud())!= 1){
                    FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Ya tiene registrado un donador",""));
                    oDonador.setNombreDonador(null);
                    oDonador.setConsecutivo(0);
                    oDonador.setFechaDonacion(null);
                }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void insertaDonadorAltruista(){
        System.out.println(oDonador.getFechaDonacion());
        try{
            if(oDonador.insertaDonadorAltruista()==1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_INFO,"Donador Registrado",""));
                oDonador.setNombreDonador(null);
                oDonador.setConsecutivo(0);
                oDonador.setFechaDonacion(null);
            }else if (oDonador.insertaDonadorAltruista()!= 1){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Error","La clave del donador ya fue asignada"));
                oDonador.setNombreDonador(null);
                oDonador.setConsecutivo(0);
                oDonador.setFechaDonacion(null);
            }
        }catch(Exception ex){
            Logger.getLogger(OtorgaEstudioRealEndos.class.getName()).log(Level.SEVERE,null,ex);
        }
    }
    
    public void rebotesSeleccion(ActionEvent ae) throws Exception{
        validaSeleccion();
    }
    
    public void validaSeleccion(){
        if(oSeleccionadoDonador == null){
                FacesContext.getCurrentInstance().addMessage(null, new FacesMessage(FacesMessage.SEVERITY_ERROR,"Seleccione un registro",""));
        }else if(oSeleccionadoDonador != null){
                bShowButton=false;
        }

    }

    public Firmado getFirm() {
        return oFirm;
    }

    public void setFirm(Firmado oFirm) {
        this.oFirm = oFirm;
    }

    public HttpServletRequest getHttpServletRequest() {
        return httpServletRequest;
    }

    public void setHttpServletRequest(HttpServletRequest httpServletRequest) {
        this.httpServletRequest = httpServletRequest;
    }

    public FacesContext getFaceContext() {
        return faceContext;
    }

    public void setFaceContext(FacesContext faceContext) {
        this.faceContext = faceContext;
    }

    public String getUsuario() {
        return sUsuario;
    }

    public void setUsuario(String sUsuario) {
        this.sUsuario = sUsuario;
    }

    public String getUrl() {
        return sUrl;
    }

    public void setUrl(String sUrl) {
        this.sUrl = sUrl;
    }

    public ProductoHemoderivado getProducto() {
        return oProducto;
    }

    public void setProducto(ProductoHemoderivado oProducto) {
        this.oProducto = oProducto;
    }

    public DetalleSolicitudHemoderivado getDetalle() {
        return oDetalle;
    }

    public void setDetalle(DetalleSolicitudHemoderivado oDetalle) {
        this.oDetalle = oDetalle;
    }

    public SolicitudSangre getSolicitud() {
        return oSolicitud;
    }

    public void setSolicitud(SolicitudSangre oSolicitud) {
        this.oSolicitud = oSolicitud;
    }

    public List<SolicitudSangre> getSolicitudes() {
        return lSolicitudes;
    }

    public void setSolicitudes(List<SolicitudSangre> lSolicitudes) {
        this.lSolicitudes = lSolicitudes;
    }

    public Date getFecSolicitud() {
        return dFecSolicitud;
    }

    public void setFecSolicitud(Date dFecSolicitud) {
        this.dFecSolicitud = dFecSolicitud;
    }

    public SolicitudSangre getSeleccionado() {
        return oSeleccionado;
    }

    public void setSeleccionado(SolicitudSangre oSeleccionado) {
        this.oSeleccionado = oSeleccionado;
    }

    public DetalleSolicitudHemoderivado getSelec() {
        return oSelec;
    }

    public void setSelec(DetalleSolicitudHemoderivado oSelec) {
        this.oSelec = oSelec;
    }

    public SolicitudSangre getSelecAmbulatorio() {
        return oSelecAmbulatorio;
    }

    public void setSelecAmbulatorio(SolicitudSangre oSelecAmbulatorio) {
        this.oSelecAmbulatorio = oSelecAmbulatorio;
    }

    public List<SolicitudSangre> getSolAmbulatoria() {
        return lSolAmbulatoria;
    }

    public void setSolAmbulatoria(List<SolicitudSangre> lSolAmbulatoria) {
        this.lSolAmbulatoria = lSolAmbulatoria;
    }

    public ReporteHojaEgresoHemoderivados getReporte() {
        return oReporte;
    }

    public void setReporte(ReporteHojaEgresoHemoderivados oReporte) {
        this.oReporte = oReporte;
    }

    public String getNombre() {
        return sNombre;
    }

    public void setNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public String getApPaterno() {
        return sApPaterno;
    }

    public void setApPaterno(String sApPaterno) {
        this.sApPaterno = sApPaterno;
    }

    public String getApMaterno() {
        return sApMaterno;
    }

    public void setApMaterno(String sApMaterno) {
        this.sApMaterno = sApMaterno;
    }

    public int getNumExpe() {
        return nNumExpe;
    }

    public void setNumExpe(int nNumExpe) {
        this.nNumExpe = nNumExpe;
    }

    public List<SolicitudSangre> getDonadores() {
        return lDonadores;
    }

    public void setDonadores(List<SolicitudSangre> lDonadores) {
        this.lDonadores = lDonadores;
    }

    public SolicitudSangre getSeleccionadoDonador() {
        return oSeleccionadoDonador;
    }

    public void setSeleccionadoDonador(SolicitudSangre oSeleccionadoDonador) {
        this.oSeleccionadoDonador = oSeleccionadoDonador;
    }

    public Donador getDonador() {
        return oDonador;
    }

    public void setDonador(Donador oDonador) {
        this.oDonador = oDonador;
    }

    public boolean getShowButton() {
        return bShowButton;
    }

    public void setShowButton(boolean bShowButton) {
        this.bShowButton = bShowButton;
    }

    public List<ReporteHojaEgresoHemoderivados> getReporteSol() {
        return lReporteSol;
    }

    public void setReporteSol(List<ReporteHojaEgresoHemoderivados> lReporteSol) {
        this.lReporteSol = lReporteSol;
    }

    public Date getFecIni() {
        return dFecIni;
    }

    public void setFecIni(Date dFecIni) {
        this.dFecIni = dFecIni;
    }

    public Date getFecFin() {
        return dFecFin;
    }

    public void setFecFin(Date dFecFin) {
        this.dFecFin = dFecFin;
    }

    public List<ReporteHojaEgresoHemoderivados> getReporteAmb() {
        return lReporteAmb;
    }

    public void setReporteAmb(List<ReporteHojaEgresoHemoderivados> lReporteAmb) {
        this.lReporteAmb = lReporteAmb;
    }

    public List<Parametrizacion> getTipoPac() {
        return lTipoPac;
    }

    public void setTipoPac(List<Parametrizacion> lTipoPac) {
        this.lTipoPac = lTipoPac;
    }

    public List<Parametrizacion> getGrupoSan() {
        return lGrupoSan;
    }

    public void setGrupoSan(List<Parametrizacion> lGrupoSan) {
        this.lGrupoSan = lGrupoSan;
    }

    public List<Parametrizacion> getRH() {
        return lRH;
    }

    public void setRH(List<Parametrizacion> lRH) {
        this.lRH = lRH;
    }

    public List<ProductoHemoderivado> getProductos() {
        return lProducto;
    }

    public void setProductos(List<ProductoHemoderivado> lProducto) {
        this.lProducto = lProducto;
    }

    public ArrayList<ProductoHemoderivado> getProdAgregado() {
        return lProdAgregado;
    }

    public void setProdAgregado(ArrayList<ProductoHemoderivado> lProdAgregado) {
        this.lProdAgregado = lProdAgregado;
    }

    public Date getFecActual() {
        return dFecActual;
    }

    public void setFecActual(Date dFecActual) {
        this.dFecActual = dFecActual;
    }

    public SolicitudSangre getSolSan() {
        return oSolSan;
    }

    public void setSolSan(SolicitudSangre oSolSan) {
        this.oSolSan = oSolSan;
    }

    public DiagnosticoCIE10 getDiag() {
        return oDiag;
    }

    public void setDiag(DiagnosticoCIE10 oDiag) {
        this.oDiag = oDiag;
    }
    
}

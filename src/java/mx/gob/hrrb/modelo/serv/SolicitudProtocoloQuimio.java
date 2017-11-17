package mx.gob.hrrb.modelo.serv;

/**
 *
 * @author Pablo
 */

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

public class SolicitudProtocoloQuimio extends ServicioRealizado implements Serializable{
private AccesoDatos oAD;
private ProtocoloQuimio oProtocolo;
private ArrayList<DetalleSolicitudProtoQuimio> arrDetalle;
private DetalleSolicitudProtoQuimio oDet;
private long nIdSolicitud;
    
    public SolicitudProtocoloQuimio(){
        oDet = new DetalleSolicitudProtoQuimio();
        HttpServletRequest req;
        req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm")!=null){
            sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
    }

    
    public SolicitudProtocoloQuimio[] buscarProtocolosPorPaciente(String sNombres, String sApPaterno, String sApMaterno, int nNumExpe, Date dFec) throws Exception{
        SolicitudProtocoloQuimio arrRet[] = null, oProt=null;
        ArrayList<SolicitudProtocoloQuimio> vObj=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0, nTam=0;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        if(!sNombres.equals("") && !sApPaterno.equals("") && dFec != null){
            sQuery = "SELECT * FROM buscarProtocolosQuimioPorPaciente('"+ sNombres +"','"+ sApPaterno +"','"+ sApMaterno +"',0,'"+ oFec.format(dFec) +"')";
        }else if(nNumExpe != 0 && dFec != null){
            sQuery = "SELECT * FROM buscarProtocolosQuimioPorPaciente('','','',0,'"+ oFec.format(dFec) +"')";
        }else{
            throw new Exception("SolicitudProtocoloQuimio.buscarProtocolosPorPaciente: error, falta datos");
        }
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj = new ArrayList<SolicitudProtocoloQuimio>();
            for(i=0;i<rst.size();i++){
                oProt = new SolicitudProtocoloQuimio();
                ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                oProt.oDet = new DetalleSolicitudProtoQuimio();
                oProt.oDet.setTipoVia(new Parametrizacion());
                oProt.oDet.setFechaSolicitud((Date)vRowTemp.get(0));
                oProt.oDet.getMedicamento().setNombre((String)vRowTemp.get(1));
                oProt.oDet.getMedicamento().setPresentacion((String)vRowTemp.get(2));
                oProt.oDet.setDosis((String)vRowTemp.get(3));
                oProt.oDet.getTipoVia().setValor((String)vRowTemp.get(4));
                oProt.oDet.setIdentificador(((Double)vRowTemp.get(5)).intValue());
                oProt.setIdSolicitud(((Double)vRowTemp.get(6)).longValue());
                oProt.oDet.setCantSolicitada(((Double)vRowTemp.get(7)).shortValue());
                vObj.add(oProt);
            }
            nTam = vObj.size();
            arrRet = new SolicitudProtocoloQuimio[nTam];
            
            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        }
        
        return arrRet;
    }
    
    public int modificarEstadoProtocolo(int nIdServReal, int nCveAccion) throws Exception{
        int i=0;
        ArrayList rst =null;
        String sQuery="";
        if(nIdServReal == 0 ){
            throw new Exception("SoicitudProtocoloQuimio.modificarEstadoProtocolo: error, faltan datos");
        }else{
            sQuery="SELECT * FROM modificarEstadoMedicamentoProtocoloQuimio('"+ sUsuarioFirmado +"'::character varying, "+ nIdServReal +"::bigint, "+ this.getDet().getCantAplicada() +"::smallint, "+ nCveAccion +"::int)";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                
                if(rst != null && rst.size() == 1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    i = ((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }
    
    public ArrayList<DetalleSolicitudProtoQuimio> getDetalle() {
        return arrDetalle;
    }

    public void setDetalle(ArrayList<DetalleSolicitudProtoQuimio> oDetalle) {
        this.arrDetalle = oDetalle;
    }
    
    //Por EL
    public ProtocoloQuimio getProtocolo(){
        return this.oProtocolo;
    }

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oProtocolo;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable oValor) {
        this.oProtocolo = (ProtocoloQuimio)oValor;
    }

    @Override
    public boolean buscar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ServicioRealizado[] buscarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int modificar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int eliminar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    public DetalleSolicitudProtoQuimio getDet() {
        return oDet;
    }

    public void setDet(DetalleSolicitudProtoQuimio oDet) {
        this.oDet = oDet;
    }

    public long getIdSolicitud() {
        return nIdSolicitud;
    }

    public void setIdSolicitud(long nIdSolicitud) {
        this.nIdSolicitud = nIdSolicitud;
    }
    
    
}

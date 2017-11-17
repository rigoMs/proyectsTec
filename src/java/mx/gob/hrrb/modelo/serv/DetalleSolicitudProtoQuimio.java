/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.serv;

/**
 *
 * @author Pablo
 */

import java.util.ArrayList;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

public class DetalleSolicitudProtoQuimio extends ServicioRealizado {  
private AccesoDatos oAD;
private String sUsuarioFirmado;
private SolicitudProtocoloQuimio oSolicitudProtocolo;
private Medicamento oMedicamento;
private String sDosis;
private Parametrizacion oTipoVia;
    
    public DetalleSolicitudProtoQuimio(){
        HttpServletRequest req;
        req = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if(req.getSession().getAttribute("oFirm")!=null){
            sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
    }
    
    public int insertaProtocoloQuimio(
            ArrayList<DetalleSolicitudProtoQuimio> lProQuim) throws Exception{
    int mod=0;
    ArrayList rst=null, vRowTemp=null;
    String sQuery="";
        if(this==null && lProQuim==null){
            throw new Exception("DetalleSolicitudProtoQuimio"+
                    ".insertaProtocoloQuimio: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM insertaprotocoloquimiooncoarr('"+ 
                    sUsuarioFirmado +"'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                    + getEpisodio().getClaveEpisodio() +"::bigint,"
                    + getAutorizadoPor().getNoTarjeta() +"::integer,'"
                    + this.oSolicitudProtocolo.getProtocolo().getClave() + 
                    "'::character varying,ARRAY[";
            for(DetalleSolicitudProtoQuimio mq:lProQuim){
                if(mq.getMedicamento().getClaveMedicamento()!=null){
                    sQuery = sQuery + "'" + 
                            mq.getMedicamento().getClaveMedicamento()+"', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";
            for(DetalleSolicitudProtoQuimio mq:lProQuim){
                if(mq.getMedicamento().getPresentacion()!=null){
                    sQuery = sQuery + "'" + 
                            mq.getMedicamento().getPresentacion()+"', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";
            for(DetalleSolicitudProtoQuimio mq:lProQuim){
                if(mq.getMedicamento().getCodBarras()>=0){
                    sQuery = sQuery + "" + 
                            mq.getMedicamento().getCodBarras()+", ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::bigint[], ARRAY[";
            for(DetalleSolicitudProtoQuimio mq:lProQuim){
                if(mq.getDosis()!=null){
                    sQuery = sQuery + "'" + mq.getDosis()+"', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";
            for(DetalleSolicitudProtoQuimio mq:lProQuim){
                if(mq.getCantSolicitada()!=0){
                    sQuery = sQuery + "" + mq.getCantSolicitada()+", ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::smallint[], ARRAY[";
            for(DetalleSolicitudProtoQuimio rh:lProQuim){
                if(rh.getTipoVia().getClaveParametro()!=null){
                    sQuery = sQuery + "'" + 
                            rh.getTipoVia().getClaveParametro()+ "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character(3)[]);";

            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();

                if(rst != null && rst.size()==1){
                    vRowTemp = (ArrayList) rst.get(0);
                    mod = ((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return mod;
    }
    

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oMedicamento;
    }
    
    
    @Override
    public void setServicioCobrable(ServicioCobrable oValor) {
        this.oMedicamento = (Medicamento)oValor;
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

    public String getDosis() {
        return sDosis;
    }

    public void setDosis(String sDosis) {
        this.sDosis = sDosis;
    }

    public Parametrizacion getTipoVia() {
        return oTipoVia;
    }

    public void setTipoVia(Parametrizacion oTipoVia) {
        this.oTipoVia = oTipoVia;
    }

    public SolicitudProtocoloQuimio getSolicitudProtocolo() {
        return oSolicitudProtocolo;
    }

    public void setSolicitudProtocolo(SolicitudProtocoloQuimio oSolicitudProtocolo) {
        this.oSolicitudProtocolo = oSolicitudProtocolo;
    }
    
    //Por EL
    public Medicamento getMedicamento() {
        return oMedicamento;
    }
    
}

package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ProtocoloQuimio extends ServicioCobrable implements Serializable{
private short nDuracion;
private Parametrizacion oUnidadDur;

    public ProtocoloQuimio(){
    HttpServletRequest req;
            req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                    sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
            }
    }
    @Override
    public boolean buscar() throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("ProtocoloQuimio.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveProtocoloQuimio();"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery); 
                            oAD.desconectar(); 
                    }
                    if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            bRet = true;
                    }
            } 
            return bRet; 
    } 
    @Override
    public ProtocoloQuimio[] buscarTodos() throws Exception{
    ProtocoloQuimio arrRet[]=null, oProtocoloQuimio=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosProtocoloQuimio();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new ProtocoloQuimio[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                    } 
            } 
            return arrRet; 
    } 
    @Override
    public int insertar() throws Exception{
    ArrayList rst = null;
    int nRet = -1;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("ProtocoloQuimio.insertar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaProtocoloQuimio('"+sUsuarioFirmado+"');"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)rst.get(0)).intValue();
                            }
                    }
            } 
            return nRet; 
    } 
    @Override
    public int modificar() throws Exception{
    ArrayList rst = null;
    int nRet = -1;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("ProtocoloQuimio.modificar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaProtocoloQuimio('"+sUsuarioFirmado+"');"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)rst.get(0)).intValue();
                            }
                    }
            } 
            return nRet; 
    } 
    @Override
    public int eliminar() throws Exception{
    ArrayList rst = null;
    int nRet = -1;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("ProtocoloQuimio.eliminar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaProtocoloQuimio('"+sUsuarioFirmado+"');"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)rst.get(0)).intValue();
                            }
                    }
            } 
            return nRet; 
    } 

    public ProtocoloQuimio[] buscarProtocoloQuimioIntExt() throws Exception{
    ProtocoloQuimio arrRet[]=null, oQuim=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
        sQuery = "SELECT * FROM buscaProtocoloExtInt();";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar(); 
        }
        if (rst != null) {
            arrRet = new ProtocoloQuimio[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oQuim = new ProtocoloQuimio();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oQuim.setClave((String)vRowTemp.get(0));
                oQuim.setDescripcion(((String)vRowTemp.get(1)).trim());
                arrRet[i]=oQuim;
            }
        } 
        return arrRet; 
    }

    public void equalsProQuim(ProtocoloQuimio arrProtQuim[]){
        for(ProtocoloQuimio ProtQuim:arrProtQuim){
            if(this.getClave().equals(ProtQuim.getClave())){
                this.setClave(ProtQuim.getClave());
                this.setDescripcion(ProtQuim.getDescripcion());
            }
        }
    }

    public boolean buscarPorNombreProtocolo() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaprotocolonombre('" + this.getClave()+ "');";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setDuracion(((Double)vRowTemp.get(0)).shortValue());
            this.getUnidadDur().setValor(((String)vRowTemp.get(1)).trim());
            bRet = true;
        }
        return bRet;
    }
        
        
    public short getDuracion() {
	return nDuracion;
    }

    public void setDuracion(short valor) {
	nDuracion=valor;
    }

    public Parametrizacion getUnidadDur() {
	return oUnidadDur;
    }

    public void setUnidadDur(Parametrizacion valor) {
	oUnidadDur=valor;
    }
} 

package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class EstudioInterconsulta extends EstudioRealizado implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private String sMotivo;
private AreaServicioHRRB oAreaServ;

    public EstudioInterconsulta(){
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
                    throw new Exception("EstudioInterconsulta.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveEstudioInterconsulta();"; 
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
    public EstudioInterconsulta[] buscarTodos() throws Exception{
    EstudioInterconsulta arrRet[]=null, oEstudioInterconsulta=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosEstudioInterconsulta();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new EstudioInterconsulta[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                    } 
            } 
            return arrRet; 
    } 
    
    public int modificarSituacionInter() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("EstudioInterconsulta.insertar: error de programación, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            System.out.println("IDENTIFICADOR: "+this.getEpisodio().getEstInter().getIdentificador());
            sQuery = "SELECT * FROM modificarsituacioninterconsulta('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getEpisodio().getEstInter().getIdentificador() + "::bigint);";
            System.out.println(sQuery);
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
    
    
    //////////////////////////////////////////////////////////////////////////// 
    //órdenes de servicio
    public int insertarInterColpos() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
    System.out.println("Dentro de insertasolicitudcolpos: "+this.getMotivo());
            if( this.getMotivo()==null){
                throw new Exception("EstudioInterconsulta.insertar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertasolicitudcolpos('"+ sUsuarioFirmado +"'::character varying,"
                            + this.getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                            + getEpisodio().getClaveEpisodio() + "::bigint,"
                            + getAutorizadoPor().getNoTarjeta() + "::integer,"
                            + 356 + "::smallint,"
                            + getEpisodio().getArea().getClave() +"::smallint,'"
                            + getEpisodio().getDiagIngreso().getClave() +"','"
                            + getMotivo()+"'::character varying);";
                    System.out.println(sQuery);
                    oAD=new AccesoDatos();
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar(); 
                            if (rst != null && rst.size() == 1) {
                                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                                    nRet = ((Double)vRowTemp.get(0)).intValue();
                            }
                    }
            } 
            return nRet; 
    }

    public int insertarInterconsulta() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        if( this.getMotivo()==null){
            throw new Exception("EstudioInterconsulta.insertar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM insertasolicitudinterconsulta('"+ sUsuarioFirmado +"'::character varying,"
                + this.getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                + getEpisodio().getClaveEpisodio() + "::bigint,"
                + getAutorizadoPor().getNoTarjeta() + "::integer,"
                + 192 + "::smallint,"
                + getAreaServ().getClave() +"::smallint,'"
                + getEpisodio().getDiagIngreso().getClave() +"','"
                + getMotivo()+"'::character varying);";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    nRet = ((Double)vRowTemp.get(0)).intValue();
                }
            }
        } 
        return nRet; 
    }        
        
    ///////////////////////////////////////////////////////////////////////////
    public String getMotivo() {
    return sMotivo;
    }

    public void setMotivo(String valor) {
    sMotivo=valor;
    }

    public AreaServicioHRRB getAreaServ() {
        return oAreaServ;
    }

    public void setAreaServ(AreaServicioHRRB valor) {
        this.oAreaServ = valor;
    }

} 

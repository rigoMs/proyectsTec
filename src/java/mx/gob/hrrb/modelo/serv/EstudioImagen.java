package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : Pablo
 * @version: 1.0
*/

public  class EstudioImagen extends Estudios implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private Parametrizacion arrRegion;
private Estudios oEstudio;
private long nCantidad;
private Parametrizacion oRegion;

    public EstudioImagen(){
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
                    throw new Exception("EstudioImagen.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveEstudioImagen();"; 
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
    public int insertar() throws Exception{
    ArrayList rst = null;
    int nRet = -1;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("EstudioImagen.insertar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaEstudioImagen('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("EstudioImagen.modificar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaEstudioImagen('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("EstudioImagen.eliminar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaEstudioImagen('"+sUsuarioFirmado+"');"; 
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

    public EstudioImagen[] buscarReporteRegiones(int nCveOpcion, Date dFecIni, Date dFecFin) throws Exception{
        EstudioImagen arrRet[]=null, oEstImagen=null;
        ArrayList<EstudioImagen> vObj=null;
        ArrayList rst=null;
        int i=0, nTam=0;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        String sQuery="";
        if(dFecIni == null && dFecFin ==null){
            throw new Exception("EstudioImagen.buscarReporteRegiones: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarDatosReporteEstImagen('"+ oFec.format(dFecIni) +"'::Date,'"+ oFec.format(dFecFin) +"'::Date,"+ nCveOpcion +");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<EstudioImagen>();
                for(i=0;i<rst.size();i++){
                    oEstImagen = new EstudioImagen();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oEstImagen.setRegion(new Parametrizacion());
                    oEstImagen.getRegion().setValor((String)vRowTemp.get(0));
                    oEstImagen.setCantidad(((Double)vRowTemp.get(1)).longValue());
                    vObj.add(oEstImagen);
                }
                nTam = vObj.size();
                arrRet  = new EstudioImagen[nTam];

                for(i=0;i<nTam;i++){
                    arrRet[i]=vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    public Parametrizacion getrrRegion() {
    return arrRegion;
    }

    public void setrrRegion(Parametrizacion valor) {
	arrRegion=valor;
	}

    public Estudios getEstudio() {
        return oEstudio;
    }

    public void setEstudio(Estudios oEstudio) {
        this.oEstudio = oEstudio;
    }

    public long getCantidad() {
        return nCantidad;
    }

    public void setCantidad(long nCantidad) {
        this.nCantidad = nCantidad;
    }

    public Parametrizacion getRegion() {
        return oRegion;
    }

    public void setRegion(Parametrizacion oRegion) {
        this.oRegion = oRegion;
    }

}
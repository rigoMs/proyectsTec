package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
 
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Proveedor implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private String sId;
private String sNombre;
private String sRFC;
private String sCFISCAL;

    public Proveedor(){   
    HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
            sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
    }
        
    public boolean buscar() throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
        if( getId().equals("")){   //completar llave
                throw new Exception("Proveedor.buscar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM buscaLlaveProveedor('"+getId()+"');"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null&& rst.size() >=1) {			                           
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setNombre(((String)vRowTemp.get(1)));
                this.setRFC(((String)vRowTemp.get(2)));
                this.setCFISCAL(((String)vRowTemp.get(3)));
               bRet = true;
            }
	}
        return bRet; 
    }
        
    public Proveedor[] buscarTodos() throws Exception{
    Proveedor arrRet[]=null, oProveedor=null;
    ArrayList rst = null;
    String sQuery = "";
    ArrayList<Proveedor> vObj=null;
    int i=0, nTam;
        sQuery = "SELECT * FROM buscaTodosProveedor();"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null && rst.size() >= 1) {
            vObj = new ArrayList<Proveedor>();
            for (i = 0; i < rst.size(); i++) {
                oProveedor= new Proveedor();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oProveedor.setId((String)vRowTemp.get(0));
                oProveedor.setNombre((String)vRowTemp.get(1));
                oProveedor.setRFC(((String) vRowTemp.get(2)));
                oProveedor.setCFISCAL(((String) vRowTemp.get(3)));
                vObj.add(oProveedor);
            }
            nTam = vObj.size();
            arrRet = new Proveedor[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }

    public int insertar() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        if( this.sId==null || this.sId.equals("") ||
            this.sNombre==null || this.sNombre.equals("")){   //completar llave
            throw new Exception("Proveedor.insertar: faltan datos");
        }else{ 
            sQuery = "SELECT * FROM insertaProveedor('" + getId() + "','" + 
                   getNombre() + "','" + getRFC() + "','" + getCFISCAL()+" ');";
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
    
    public Proveedor[] buscarProvedoresDeMedicamento(String sClaveMedicamento) 
            throws Exception{
    Proveedor arrRet[]=null, oProv=null;
    ArrayList rst = null;
    ArrayList<Proveedor> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;

        sQuery = "SELECT * FROM busca_Proveedor_Medicamento('"+sClaveMedicamento+"');";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null && rst.size() >= 1) {
            vObj = new ArrayList<Proveedor>();
            for (i = 0; i < rst.size(); i++) {
                oProv= new Proveedor();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oProv.setNombre((String)vRowTemp.get(0));
                oProv.setId((String)vRowTemp.get(1));
                oProv.setRFC((String)vRowTemp.get(2));
                oProv.setCFISCAL((String)vRowTemp.get(3));
                vObj.add(oProv);
            }
            nTam = vObj.size();
            arrRet = new Proveedor[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }
    
    public Proveedor[] buscarProvedoresDeMateriales(String sClaveMaterial) 
            throws Exception{
    Proveedor arrRet[]=null, oProv=null;
    ArrayList rst = null;
    ArrayList<Proveedor> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
        
        sQuery = "SELECT * FROM busca_Provedores_De_Medicamentos('"+
                sClaveMaterial+"');";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null && rst.size() >= 1) {
            vObj = new ArrayList<Proveedor>();
            for (i = 0; i < rst.size(); i++) {
                oProv= new Proveedor();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oProv.setNombre((String)vRowTemp.get(0));
                oProv.setId((String)vRowTemp.get(1));
                oProv.setRFC((String)vRowTemp.get(2));
                oProv.setCFISCAL((String)vRowTemp.get(3));
                vObj.add(oProv);
            }
            nTam = vObj.size();
            arrRet = new Proveedor[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }
        
    
    public Proveedor[]buscarProveedor()throws Exception{
     Proveedor arrRet[]=null,oProv=null;
     ArrayList rst=null;
     ArrayList<Proveedor>vObj=null;
     String sQuery="";
     int i=0,nTam=0;
         sQuery="SELECT *FROM buscaTodosXNombreProveedor();";
         oAD=new AccesoDatos();
         if(oAD.conectar()){
             rst=oAD.ejecutarConsulta(sQuery);
             oAD.desconectar();
         }
         if (rst != null) {
            vObj = new ArrayList<Proveedor>();
            for (i = 0; i < rst.size(); i++) {
                oProv = new Proveedor();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oProv.setNombre((String)vRowTemp.get(0));
                vObj.add(oProv);
            }
            nTam = vObj.size();
            arrRet = new Proveedor[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }
    
    public int modificaDatosProvmat() throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        if (this.getId()==null) {   
            throw new Exception("proveedor.modificar: faltan datos");
        } else {
            sQuery = "select * from modificaDatosProveedor('" + sUsuarioFirmado + 
                    "','" + this.getId()+ "','" + this.getNombre() + "','" +
                    this.getRFC()+"','"+this.getCFISCAL()+"');";
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
        
    public String getId() {
    return sId;
    }

    public void setId(String valor) {
    sId=valor;
    }

    public String getNombre() {
    return sNombre;
    }

    public void setNombre(String valor) {
    sNombre=valor;
    }
    
    public String getRFC() {
        return sRFC;
    }

    public void setRFC(String valor) {
        this.sRFC = valor;
    }

    public String getCFISCAL() {
        return sCFISCAL;
    }

    public void setCFISCAL(String valor) {
        this.sCFISCAL = valor;
    }

} 

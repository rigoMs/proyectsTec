package mx.gob.hrrb.modelo.almacen;

import mx.gob.hrrb.modelo.core.Proveedor;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
 
/**
 *
 * @author GIL
 */
public class InventarioMateriales implements Serializable{
private AccesoDatos oAD;
private String sLote;   
private String sTipoAlmacen; //ALMA o CEYE
private Date dCaducidad;
private String cantidad;
private int cant;
private int nExistencia;
private char sEdo;
private float nPrecio;
private int nExistenciaConvertida;   
private Proveedor oProveedor;  
private Material material;
private String tipoLotes;
private String tipoMovimiento;
private Date fecha=null;
private String sUsuarioFirmado;

    public InventarioMateriales(){
    HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
        oProveedor=new Proveedor();
        fecha=new Date();
        
    }
    
    public InventarioMateriales[] buscaproximoscaducados() throws Exception {
    InventarioMateriales arrRet[] = null, oDetInv = null;
    ArrayList rst = null;
    ArrayList<InventarioMateriales> vObj = null;
    String sQuery = "";
    int i = 0;
        sQuery = "SELECT * FROM reportePorCaducar();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<InventarioMateriales>();
            for (i = 0; i < rst.size(); i++) {
                oDetInv = new InventarioMateriales();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetInv.getMaterial().setClaveMaterial(((String) vRowTemp.get(0)));
                oDetInv.getMaterial().setNombre(((String) vRowTemp.get(1)));
                                
                oDetInv.setLote(((String) vRowTemp.get(2)));              
                oDetInv.setCaducidad((Date) vRowTemp.get(3));
                  oDetInv.setExistencia(((Double) vRowTemp.get(4)).intValue());
                vObj.add(oDetInv);
            }
            int nTam = vObj.size();
            arrRet = new InventarioMateriales[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public InventarioMateriales[] buscarTodosInventario() throws Exception {
    InventarioMateriales arrRet[] = null, oInventario = null;
    ArrayList rst = null;
    ArrayList<InventarioMateriales> vObj = null;
    String sQuery = "";
    int i = 0;
        sQuery = "SELECT * FROM buscaMantenimientoInventario();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new InventarioMateriales[rst.size()];
            vObj = new ArrayList<InventarioMateriales>();
            for (i = 0; i < rst.size(); i++) {
                oInventario = new InventarioMateriales();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oInventario.getMaterial().setClaveMaterial(((String) vRowTemp.get(0)));
                oInventario.getMaterial().setNombre(((String) vRowTemp.get(1)));                
                oInventario.getMaterial().getPresentacion().setValor(((String) vRowTemp.get(2)));               
                oInventario.setLote(((String)vRowTemp.get(3)));
                oInventario.setCaducidad((Date)vRowTemp.get(4));
                oInventario.setExistencia(((Double) vRowTemp.get(5)).intValue());
                vObj.add(oInventario);
            }
            int nTam = vObj.size();
            arrRet = new InventarioMateriales[nTam];

            for (i = 0; i < nTam; i++) { 
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public InventarioMateriales[] buscarExistencia() throws Exception {
    InventarioMateriales arrRet[] = null, oInventario = null;
    ArrayList rst = null;
    ArrayList<InventarioMateriales> vObj = null;
    String sQuery = "";
    int i = 0;
        sQuery = "SELECT * FROM buscaTodosInventario();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new InventarioMateriales[rst.size()];
            vObj = new ArrayList<InventarioMateriales>();
            for (i = 0; i < rst.size(); i++) {
                oInventario = new InventarioMateriales();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oInventario.getMaterial().setClaveMaterial(((String) vRowTemp.get(0)));
                oInventario.getMaterial().setNombre(((String) vRowTemp.get(1)));                
                oInventario.getMaterial().getPresentacion().setValor(((String) vRowTemp.get(2)));                                   
                oInventario.setExistencia(((Double) vRowTemp.get(3)).intValue());
                vObj.add(oInventario);
            }
            int nTam = vObj.size();
            arrRet = new InventarioMateriales[nTam];

            for (i = 0; i < nTam; i++) { 
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
        
    public int insertarSalidad() throws Exception {
     ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.material == null) {           
            throw new Exception("Inven:error de programacion ,faltan datos");
        } else {
            sQuery = "SELECT * FROM actualizainventarioandinsertacontrol('"+
                 getMaterial().getClaveMaterial() +"'::character varying,'"+
                 getTipoLotes()+"'::character varying," +          
                 getCant()+"::integer);";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = (((Double) vRowTemp.get(0)).intValue());
                    
                  }
            }
        }
        return nRet;
    }
    
    public boolean notificaExistenciaNegativa(String clave) 
            throws Exception {
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
             
        sQuery = "SELECT * FROM notificaExistenciaNegativa('"+ clave+
                "'::character varying,'"+
                getTipoLotes()+"'::character varying);";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);     
            this.setExistencia(((Double) vRowTemp.get(0)).intValue());  
            bRet = true;
        }
        return bRet;
    }
    
    public boolean notificaExistenciaNegativaOnEdit(String clavematerial,
            String lote) throws Exception {
    boolean bRet = false;
    ArrayList rst = null;        
    String sQuery = "";         
             
        sQuery = "SELECT * FROM notificaExistenciaNegativa('"+
              clavematerial+"'::character varying,'"+
              lote+"'::character varying);";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);     
            this.setExistencia(((Double) vRowTemp.get(0)).intValue());  
            bRet = true;
        }
        return bRet;
    }
    /*******************************************************************************************************************************/
     
    public InventarioMateriales[] buscarNombreMaterial(String nombre) 
            throws Exception {
    InventarioMateriales arrRet[] = null, oDiag = null;
    ArrayList rst = null;
    ArrayList<InventarioMateriales> vObj = null;
    String sQuery = "";
    int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaXNombreMaterial('"+nombre+"');";
        
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<InventarioMateriales>();
            for (i = 0; i < rst.size(); i++) {
                oDiag = new InventarioMateriales();
                ArrayList vRowTemp = (ArrayList) rst.get(i);    
                oDiag.getMaterial().setNombre((String) vRowTemp.get(0));
                vObj.add(oDiag);
            }
            nTam = vObj.size();
            arrRet = new InventarioMateriales[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public int insertarSalidaMaterial(String sClaveMaterial, 
            String sLote,int nCant, Date registro,String tipoMotivo) 
            throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        if (nCant == 0) {   //completar llave
            throw new Exception("InventarioMateriales.insertarSalidaMaterial: faltan datos");
        } else {
            sQuery = "SELECT * FROM GuardaSalidaMaterial("
                    + "'" + sUsuarioFirmado + "'::character varying,'" +
                    sClaveMaterial +  "'::character varying,'"  +
                    sLote + "'::character varying,'"  +
                    nCant + "'::smallint,'"+
                    registro + "'::date,'" + 
                    tipoMotivo + "'::character varying );";
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
     
    
     public String getLote() {
        return sLote;
    }

    public void setLote(String valor) {
        this.sLote = valor;
    }
    
    public float getPrecio() {
        return nPrecio;
    }

    public void setPrecio(float nPrecio) {
        this.nPrecio = nPrecio;
    }   
 public float getSubTotal() {
        return getPrecio() * getExistencia();
    }

    

    public Date getCaducidad() {
        return dCaducidad;
    }

    public void setCaducidad(Date valor) {
        this.dCaducidad = valor;
    }

    public int getExistencia() {
        return nExistencia;
    }

    public void setExistencia(int valor) {
        this.nExistencia = valor;
    }

    public char getEdo() {
        return sEdo;
    }

    public void setEdo(char valor) {
        this.sEdo = valor;
    }

   

    public int getExistenciaConvertida() {
        return nExistenciaConvertida;
    }

    public void setExistenciaConvertida(int valor) {
        this.nExistenciaConvertida = valor;
    }

    public Proveedor getProveedor() {
        return oProveedor;
    }

    public void setProveedor(Proveedor oProveedor) {
        this.oProveedor = oProveedor;
    }
 


    
    
   public String getSemaforo(){
       String sSemaforo="";
       Calendar dosMeses,seisMeses;
       dosMeses=Calendar.getInstance();
       dosMeses.setTime(new Date());
       dosMeses.add(Calendar.MONTH,2);
       
       if(dCaducidad.after(new Date (dosMeses.getTime().toString()))){
           sSemaforo="#F7FE2E";// amarillo
       }else{
           sSemaforo="#FF0000";//rojo
       }
           return sSemaforo;
       }

   

    public Material getMaterial() {
        return material;
    }

    public void setMaterial(Material valor) {
        this.material = valor;
    }

    public String getCantidad() {
        return cantidad;
    }

    public void setCantidad(String cantidad) {
        this.cantidad = cantidad;
    }

    public String getTipoLotes() {        
        return tipoLotes;
    }

    public void setTipoLotes(String tipoLotes) {
        this.tipoLotes = tipoLotes;
    }

    public String getTipoMovimiento() {
       // System.out.println("Tipo movimiento: " + tipoMovimiento);
        return tipoMovimiento;
    }

    public void setTipoMovimiento(String tipoMovimiento) {
        this.tipoMovimiento = tipoMovimiento;
    }

    public Date getFecha() {
        return fecha;
    }

    public void setFecha(Date fecha) {
        this.fecha = fecha;
    }

     public String fechaActual() {
        Calendar fec = new GregorianCalendar();
        int anio = fec.get(Calendar.YEAR);
        int mes = fec.get(Calendar.MONTH);
        int dia = fec.get(Calendar.DAY_OF_MONTH);

        String a = anio + "";
        String hoy = dia + "/" + (mes + 1) + "/" + a.substring(2, 4);

     //   System.out.println(hoy);
        return hoy;
    }

    public int getCant() {
        return cant;
    }

    public void setCant(int cant) {
        this.cant = cant;
    }
     
}
            
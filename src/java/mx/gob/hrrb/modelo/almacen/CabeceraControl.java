package mx.gob.hrrb.modelo.almacen;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.model.SelectItem;
import mx.gob.hrrb.modelo.core.Control;
import mx.gob.hrrb.modelo.core.Destino;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Proveedor;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author GIL
 */
public class CabeceraControl extends Control implements Serializable{
private String sIdEntrada=null;    
private String sInfoDocProv;
private ArrayList<ControlMaterial> arrControlMaterial;
private char  nTipoMov;
private String sRuta="";

    public CabeceraControl(){
        super();
        oProv = new Proveedor();
        oDestino = new Destino();
    }
    
    public boolean buscarLlaveEntrada() throws Exception {
    boolean bRet = false;
    ArrayList rst = null;
        
        String sQuery = "";
        sQuery = "SELECT * FROM buscaLlaveEntrada('"+getIdEntrada()+"');"; 

        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
             bRet = true;
        }
        return bRet;
    }
 
    public boolean buscarQuienCaptura( ) throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        
        String sQuery = "";
        sQuery = "SELECT * FROM buscaPersonaHospitalaria("+
                this.oQuienRegistra.getNoTarjeta()+");"; 
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();

        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.oQuienRegistra.setNombres((String)vRowTemp.get(0));
            this.oQuienRegistra.setApPaterno((String)vRowTemp.get(1));
            this.oQuienRegistra.setApMaterno((String)vRowTemp.get(2));
            bRet = true;
        }
        return bRet;
    }
    
    public CabeceraControl[] buscarRangoFechas(Date fechaIni,Date fechaFin) 
            throws Exception{
    CabeceraControl  arrRet[]=null,oCabecera=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");           
    ArrayList<CabeceraControl>vObj=null;
    String sQuery = "";     
    int i=0;            
            sQuery = "SELECT * FROM descargafacturas('"+ oFec.format(fechaIni)+
                    "','"+oFec.format(fechaFin)+"');"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if(rst!=null){
                arrRet=new CabeceraControl[rst.size()];
                vObj=new ArrayList<CabeceraControl>();
                for(i=0;i<rst.size();i++){
                    oCabecera=new CabeceraControl();                       
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oCabecera.setIdEntrada(((String) vRowTemp.get(0)));
                    oCabecera.setFechaRecepcion((Date)vRowTemp.get(1));
                    oCabecera.getProveedor().setNombre((String) vRowTemp.get(2));
                    oCabecera.setRuta(((String) vRowTemp.get(3)));
                    vObj.add(oCabecera);
                }
            int nTam = vObj.size();
            arrRet = new CabeceraControl[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public CabeceraControl[] buscarTodosCabecera() throws Exception {
    CabeceraControl arrRet[] = null, oCabecera = null;
    ArrayList rst = null;
    ArrayList<CabeceraControl> vObj = null;
    String sQuery = "";
    int i = 0;
        sQuery = "SELECT * FROM buscaTodosCabecera();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new CabeceraControl[rst.size()];
            vObj = new ArrayList<CabeceraControl>();
            for (i = 0; i < rst.size(); i++) {
                oCabecera = new CabeceraControl();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oCabecera.setRegistro(((Date) vRowTemp.get(0)));
                oCabecera.setFechaRecepcion(((Date) vRowTemp.get(1)));    
                oCabecera.setIdEntrada(((String) vRowTemp.get(3)));
                vObj.add(oCabecera);
            }
            int nTam = vObj.size();
            arrRet = new CabeceraControl[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public int insertarCabecera(String sUsuario) throws Exception {
    ArrayList rst = null;
    int uRegistro = 0;
    String sQuery = "";
        if (this == null) {           
            throw new Exception("Cabecera.insertar:faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaCabecera('"+sUsuario +
                    "'::character varying,'" +
                    this.dFechaRecepcion+"'::date,'"+
                    this.dRegistro+"'::date,"+
                    1+"::integer,'"+
                    "1"+"'::character varying,'"+
                    getIdEntrada()+ "'::character varying,'" +
                    getInfoDocProv()+ "'::character varying,'"+
                    getMovEntrada()+"'::character varying,'"+
                    "T63"+"'::character varying,'"+
                    "1"+"'::character varying,"+
                    getQuienRegistra().getNoTarjeta()+"::integer);";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    uRegistro = (((Double) vRowTemp.get(0)).intValue());
                  }
            }
        }
        return uRegistro;
    }
 
    public int insertarMaterialACabecera() throws Exception {
    ArrayList rst = null;
    InventarioMateriales oInv;
    int nRet = 0;
    String sQuery = "";
        if (this.getArrControlMaterial() == null ) {           
            throw new Exception("Cabecera.insertarMaterialACabecera:faltan datos");
        } else {
            oInv = getArrControlMaterial().get(0).getInv();
            sQuery = "SELECT * FROM insertaInventarioAndControlMaterial('"+
                oInv.getMaterial().getClaveMaterial() +"'::character varying,'"+
                oInv.getLote()+"'::character varying,'" +
                "ALMA"+"'::character varying," +
                (oInv.getCaducidad()==null?"null":"'"+oInv.getCaducidad()+"'")+
                "::date,"+ oInv.getExistencia()+"::smallint,'E'::character, "+
                oInv.getPrecio()+"::numeric);";
            
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
    
    public boolean buscarIdControl() throws Exception {
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
        if (getArrControlMaterial()==null || getArrControlMaterial().get(0)==null){           
            throw new Exception("Cabecera.buscarIdControl:faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaLlaveMaterial('"+
            getArrControlMaterial().get(0).getInv().getMaterial().getClaveMaterial()
            +"');"; 

            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                getArrControlMaterial().get(0).getInv().getMaterial().setNombre(((String) vRowTemp.get(1)));        
                getArrControlMaterial().get(0).getInv().getMaterial().setPresentacion(new Parametrizacion());
                getArrControlMaterial().get(0).getInv().getMaterial().getPresentacion().setValor((String) vRowTemp.get(2));
                getArrControlMaterial().get(0).getInv().getMaterial().setConcentrado(new Parametrizacion());
                getArrControlMaterial().get(0).getInv().getMaterial().getConcentrado().setValor(((String) vRowTemp.get(3)));
                bRet = true;
            }
        }
        return bRet;
    }
    
    public List<SelectItem> getParametrizaciones() throws Exception {
    List<SelectItem> sMot = new ArrayList<SelectItem>();
    Parametrizacion parametrizaciones[] = null;

        parametrizaciones = (new Parametrizacion()).buscarTipoAdquisicion();
        if (parametrizaciones != null) {
            for (Parametrizacion n : parametrizaciones) {
                sMot.add(new SelectItem(n.getClaveParametro(), n.getValor()));
            }
        } else {
            parametrizaciones = (new Parametrizacion()).buscarTodos();
            
            for (Parametrizacion n : parametrizaciones) {
                sMot.add(new SelectItem(n.getClaveParametro(), n.getValor()));
            }
        }
        return sMot;
    }
    
    public CabeceraControl[] buscaproximoscaducados() throws Exception {
    CabeceraControl arrRet[] = null, oDetInv = null;
    ArrayList rst = null;
    ArrayList<CabeceraControl> vObj = null;
    String sQuery = "";
    int i = 0;
        if (getArrControlMaterial()==null || getArrControlMaterial().get(0)==null){
            this.arrControlMaterial = new ArrayList<ControlMaterial>();
            this.arrControlMaterial.add(new ControlMaterial());
        }
        sQuery = "SELECT * FROM reportePorCaducar();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<CabeceraControl>();
            for (i = 0; i < rst.size(); i++) {
                oDetInv = new CabeceraControl();
                oDetInv.arrControlMaterial = new ArrayList<ControlMaterial>();
                oDetInv.arrControlMaterial.add(new ControlMaterial());
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetInv.arrControlMaterial.get(0).getInv().getMaterial().setClaveMaterial(((String) vRowTemp.get(0)));
                oDetInv.arrControlMaterial.get(0).getInv().getMaterial().setNombre(((String) vRowTemp.get(1)));
                                
                oDetInv.arrControlMaterial.get(0).getInv().setLote(((String) vRowTemp.get(2)));              
                oDetInv.arrControlMaterial.get(0).getInv().setCaducidad((Date) vRowTemp.get(3));
                oDetInv.arrControlMaterial.get(0).getInv().setExistencia(((Double) vRowTemp.get(4)).intValue());
                vObj.add(oDetInv);
            }
            int nTam = vObj.size();
            arrRet = new CabeceraControl[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public String getSemaforo(){
    String sSemaforo="";
    Calendar dosMeses;
    dosMeses=Calendar.getInstance();
    dosMeses.setTime(new Date());
    dosMeses.add(Calendar.MONTH,2);
       
        if(this.arrControlMaterial.get(0).getInv().getCaducidad().after(new Date (dosMeses.getTime().toString()))){
           sSemaforo="#F7FE2E";// amarillo
        }else{
           sSemaforo="#FF0000";//rojo
        }
        return sSemaforo;
    }   

    
    public String getIdEntrada() {
        return sIdEntrada;
    }

    public void setIdEntrada(String valor) {
        this.sIdEntrada = valor;
    }

    public String getInfoDocProv() {
        return sInfoDocProv;
    }

    public void setInfoDocProv(String valor) {
        this.sInfoDocProv = valor;
    }
    
    public char getMovEntrada(){
        return 'E';
    }
    
    public ArrayList<ControlMaterial> getArrControlMaterial(){
        return this.arrControlMaterial;
    }
    public void setArrControlMaterial(ArrayList<ControlMaterial> val){
        this.arrControlMaterial = val;
    }
    
    public char getTipoMov() {
        return nTipoMov;
    }

    public void setTipoMov(char val) {
        this.nTipoMov = val;
    }

    
    public String getRuta() {
        return sRuta;
    }

    public void setRuta(String val) {
        this.sRuta = val;
    }
    
    //para que funcione EL en XHTML
    public ControlMaterial getPrimero(){
        return (this.arrControlMaterial==null || this.arrControlMaterial.isEmpty()?null:this.arrControlMaterial.get(0));
    }
    public void setPrimero(ControlMaterial val){
        if (this.arrControlMaterial==null || this.arrControlMaterial.isEmpty()){
            this.arrControlMaterial = new ArrayList<ControlMaterial>();
            this.arrControlMaterial.add(val);
        }
        else
            this.arrControlMaterial.add(0, val);
    }
}

package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.almacen.ValeColectivo;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 * @author : GIL
 * @version: 1.0
*/

public  class DetalleValeColectivo extends ServicioRealizado implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private ValeColectivo oVale;
private Material oMaterial;
private short nCantSolArea;
private short nCantSurArea;
//Por la parte de surtido NO USAR
private List<DetalleValeColectivo> lListaLotes;
private String lote;
private int existencia;  
private String sTiposLotes;
private short cantAnterior=0;

    public DetalleValeColectivo(){
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
                    throw new Exception("DetalleValeColectivo.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveDetalleValeColectivo();"; 
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
    public DetalleValeColectivo[] buscarTodos() throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetalleValeColectivo=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosDetalleValeColectivo();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new DetalleValeColectivo[rst.size()];
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
                    throw new Exception("DetalleValeColectivo.insertar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaDetalleValeColectivo('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("DetalleValeColectivo.modificar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaDetalleValeColectivo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("DetalleValeColectivo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDetalleValeColectivo('"+sUsuarioFirmado+"');"; 
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

    ////////////////////////////////////////////////////////////////////////////
    //órdenes de servicio
    public int insertaSolMatCol(ArrayList<DetalleValeColectivo> lMatCol) 
            throws Exception{
        int mod=0;
        ArrayList rst=null, vRowTemp=null;
        String sQuery="";
        if(this.oEpisodio == null && lMatCol==null){
            throw new Exception("DetalleValeColectivo.insertaSolMatCEYE: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM insertasolicitudmaterialcolectivoarr('"+ 
                    sUsuarioFirmado +"'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() +"::bigint,"
                    + getEpisodio().getClaveEpisodio() +"::bigint,"
                    + getAutorizadoPor().getNoTarjeta() +"::integer,"
                    + getEpisodio().getArea().getClave()+ "::smallint,ARRAY[";
            for(DetalleValeColectivo vc:lMatCol){
                if(vc.getCantSolicitada()!=0){
                    sQuery = sQuery + "" + vc.getCantSolicitada()+", ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::smallint[], ARRAY[";
            for(DetalleValeColectivo vc:lMatCol){
                if(((Material)vc.getServicioCobrable()).getClaveMaterial()!=null){
                    sQuery = sQuery + "'" + 
                        ((Material)vc.getServicioCobrable()).getClaveMaterial()+
                            "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[]);";
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

    public String getInsertaSolMatCol() throws Exception{
    String sQuery="";
        if(getEpisodio().getPaciente()==null){
            throw new Exception("DetalleValeColectivo.getInsertaSolMatCol: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM insertasolicitudmaterialcolectivo('"+ 
                    sUsuarioFirmado +"'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                    + getEpisodio().getClaveEpisodio() +"::bigint,"
                    + getAutorizadoPor().getNoTarjeta() +"::integer,"
                    + getCantSolArea() + "::smallint,'"
                    + ((Material)this.getServicioCobrable()).getClaveMaterial()+
                    "'::character varying,'"
                    + getEpisodio().getArea().getClave()+ "'::smallint);";
        }
        return sQuery;
    }

    public void insertaDetalleValeColectivo(
    ArrayList<DetalleValeColectivo> arrDetValCol) throws Exception{
    ArrayList<String> vSolicMatCol=new ArrayList<>();
    int nRegAfectados=0;
        for(DetalleValeColectivo MatCol:arrDetValCol){
            vSolicMatCol.add(MatCol.getInsertaSolMatCol());
        }
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            nRegAfectados = oAD.ejecutarConsultaComando(vSolicMatCol);
            oAD.desconectar();
        }
    }

    public int insertaSolMatCEYE(ArrayList<DetalleValeColectivo> lCeye) throws Exception{
    int mod=0;
    ArrayList rst=null, vRowTemp=null;
    String sQuery="";
        if(this.oEpisodio == null && lCeye==null){
            throw new Exception("DetalleValeColectivo.insertaSolMatCEYE: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM insertasolicitudceyearr('"+ sUsuarioFirmado 
                    +"'::character varying,"
                    + this.getVale().getAreaAtiende().getClave() 
                    + "::smallint,ARRAY[";
            for(DetalleValeColectivo vc:lCeye){
                if(((Material)vc.getServicioCobrable())!=null){
                    sQuery = sQuery + "'" + 
                            ((Material)vc.getServicioCobrable()) +"', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";
            for(DetalleValeColectivo vc:lCeye){
                if(vc.getCantSolArea() != 0){
                    sQuery = sQuery + "" + vc.getCantSolArea() + ", ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::smallint[]);";
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
    
    public DetalleValeColectivo[] buscarMaterialesSolicitadosValeColectivoPaciente(
            Date dFecAux, int AreaServ, long FolioPac, long CveEpiMed) 
            throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetVal=null;
    ArrayList rst = null;
    ArrayList<DetalleValeColectivo> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if( this==null){
            throw new Exception("ValeMaterial.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscamaterialsolicitadosurtidovalematerialcolectivo('"+df.format(dFecAux)+"%',"
                    +AreaServ+"::smallint,"
                    +FolioPac+"::bigint,"
                    +CveEpiMed+"::bigint);";
            oAD=new AccesoDatos();
                        
            if (oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
            }
            if (rst != null) {
                vObj = new ArrayList<DetalleValeColectivo>();
                for (i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oDetVal=new DetalleValeColectivo();
                    ((Material)oDetVal.getServicioCobrable()).setNombre((String) vRowTemp.get(0));
                    oDetVal.setCantSolicitada(((Double) vRowTemp.get(1)).shortValue());
                    oDetVal.setCantSurtida(((Double) vRowTemp.get(2)).shortValue());

                    vObj.add(oDetVal);                              
                }
                nTam = vObj.size();
                arrRet = new DetalleValeColectivo[nTam];
                for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
        
    public DetalleValeColectivo[] buscarMaterialesSolicitadosCEYE(
            Date dFecAux, int AreaServ) throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetVal=null;
    ArrayList rst = null;
    ArrayList<DetalleValeColectivo> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if( this==null){
            throw new Exception("ValeMaterial.buscar: error de programación, faltan datos");
        }else{
            sQuery ="SELECT * FROM buscamaterialsolicitadosurtidovalematerialceye('"+
                    df.format(dFecAux)+"%',"
                    +AreaServ+"::smallint);";
            oAD=new AccesoDatos();
                        
            if (oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
            }
            if (rst != null) {
                vObj = new ArrayList<DetalleValeColectivo>();
                for (i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oDetVal=new DetalleValeColectivo();
                    ((Material)oDetVal.getServicioCobrable()).setNombre((String) vRowTemp.get(0));
                    oDetVal.setCantSolArea(((Double) vRowTemp.get(1)).shortValue());
                    oDetVal.setCantSurArea(((Double) vRowTemp.get(2)).shortValue());
                    vObj.add(oDetVal);                              
                }
                nTam = vObj.size();
                arrRet = new DetalleValeColectivo[nTam];
                for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    ////////////////////////////////////////////////////////////////////////////
    
    //*********************************************************
    //Por surtido
    public DetalleValeColectivo[] buscarQuirofano(Date dFecha) throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetalle=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery = "";     
    int i=0;
         
        sQuery = "SELECT * FROM buscadetallecolectivoQUIROFANORECUPERACION('"+ 
                oFec.format(dFecha)+"');"; 
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new DetalleValeColectivo[rst.size()];
            vObj=new ArrayList <DetalleValeColectivo>();
            for(i=0;i<rst.size();i++){
                oDetalle=new DetalleValeColectivo();
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oDetalle.getVale().setIdHoja(((Double)vRowTemp.get(0)).intValue());
                oDetalle.getMaterial().setClaveMaterial(((String)vRowTemp.get(1)));
                oDetalle.getMaterial().setNombre(((String) vRowTemp.get(2)));
                oDetalle.getMaterial().getPresentacion().setValor(((String) vRowTemp.get(3)));                         
                oDetalle.setCantSolicitada(((Double) vRowTemp.get(4)).shortValue());
                oDetalle.setCantSurtida(((Double) vRowTemp.get(5)).shortValue());
                vObj.add(oDetalle);
            }
            int nTam = vObj.size();
            arrRet = new DetalleValeColectivo[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public InventarioMateriales[]buscaLotesQuirofano(String a)throws Exception{
    InventarioMateriales arrRet[]=null,oProv=null;
    ArrayList rst=null;
    ArrayList<InventarioMateriales>vObj=null;
    String sQuery="";
    int i=0,nTam=0;
         
        sQuery="SELECT *FROM buscaLotes('"+a+"');";
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<InventarioMateriales>();
            for (i = 0; i < rst.size(); i++) {
                oProv = new InventarioMateriales();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oProv.setLote((String)vRowTemp.get(0));
                vObj.add(oProv);
            }
            nTam = vObj.size();
            arrRet = new InventarioMateriales[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }
  
    public DetalleValeColectivo[] buscarAreas(int clave,Date dFecha) 
            throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetalle=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery = "";     
    int i=0;
         
        sQuery = "SELECT * FROM buscaDetalleAreas("+clave+"::smallint,'"+
                oFec.format(dFecha)+"');";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new DetalleValeColectivo[rst.size()];
            vObj=new ArrayList <DetalleValeColectivo>();
            for(i=0;i<rst.size();i++){
                oDetalle=new DetalleValeColectivo();
                oDetalle.setEpisodio(new EpisodioMedico());
                oDetalle.getEpisodio().setPaciente(new Paciente());
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oDetalle.getVale().setIdHoja(((Double)vRowTemp.get(0)).intValue());
                oDetalle.getVale().getPersonal().setNombres(((String)vRowTemp.get(1)));
                oDetalle.getVale().getPersonal().setApPaterno(((String)vRowTemp.get(2)));
                oDetalle.getVale().getPersonal().setApMaterno(((String)vRowTemp.get(3)));
                oDetalle.getEpisodio().getPaciente().getSeg().setNumero(((String)vRowTemp.get(4)));
                oDetalle.getEpisodio().getExpediente().setNumero(((Double) vRowTemp.get(5)).intValue());   
                oDetalle.getMaterial().setClaveMaterial(((String)vRowTemp.get(6)));
                oDetalle.getMaterial().setNombre(((String) vRowTemp.get(7))); 
                oDetalle.getMaterial().getPresentacion().setValor(((String)vRowTemp.get(8)));
                oDetalle.setCantSolicitada(((Double) vRowTemp.get(9)).shortValue());
                oDetalle.setCantSurtida(((Double)vRowTemp.get(10)).shortValue());
                vObj.add(oDetalle);
            }
            int nTam = vObj.size();
            arrRet = new DetalleValeColectivo[nTam];
            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public DetalleValeColectivo[] buscaLotesDetalle(String a)throws Exception{
    DetalleValeColectivo arrRet[]=null,oDet=null;
    ArrayList rst=null;
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery="";
    int i=0,nTam=0;
         
        sQuery="SELECT *FROM buscaLotes('"+a+"');";
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleValeColectivo>();
            for (i = 0; i < rst.size(); i++) {
                oDet = new DetalleValeColectivo();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oDet.setLote((String)vRowTemp.get(0));
                vObj.add(oDet);
            }
            nTam = vObj.size();
            arrRet = new DetalleValeColectivo[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }
 
    public DetalleValeColectivo[] buscarCeye(Date dFecha) throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetalle=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery = "";     
    int i=0;
         
        sQuery = "SELECT * FROM buscaDetalleColectivoCEYE('"+ oFec.format(dFecha)+"');";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new DetalleValeColectivo[rst.size()];
            vObj=new ArrayList <DetalleValeColectivo>();
            for(i=0;i<rst.size();i++){
                oDetalle=new DetalleValeColectivo();
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oDetalle.getVale().setIdHoja(((Double)vRowTemp.get(0)).intValue());
                oDetalle.getMaterial().setClaveMaterial(((String)vRowTemp.get(1)));
                oDetalle.getMaterial().setNombre(((String) vRowTemp.get(2)));
                oDetalle.getMaterial().getPresentacion().setValor(((String) vRowTemp.get(3)));
                oDetalle.setCantSolicitada(((Double) vRowTemp.get(4)).shortValue());
                oDetalle.setCantSurtida(((Double) vRowTemp.get(5)).shortValue());
                vObj.add(oDetalle);
            }
            int nTam = vObj.size();
            arrRet = new DetalleValeColectivo[nTam];
            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public DetalleValeColectivo[]buscaLotesDetalleExistencia(String a)
            throws Exception{
    DetalleValeColectivo arrRet[]=null,oDet=null;
    ArrayList rst=null;
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery="";
    int i=0,nTam=0;
         
        sQuery="SELECT *FROM buscalotesExistencia('"+a+"'::character varying);";
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleValeColectivo>();
            for (i = 0; i < rst.size(); i++) {
                oDet = new DetalleValeColectivo();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oDet.setLote((String)vRowTemp.get(0));
                oDet.setExistencia(((Double)vRowTemp.get(1)).intValue());
                vObj.add(oDet);
            }
            nTam = vObj.size();
            arrRet = new DetalleValeColectivo[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }
    
    public int insertarSalidaCEYE(int idhoja,String sClaveMaterial, 
            String sLote,int nCantSurtida,int total) throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        
        if (idhoja == 0) {   //completar llave
            throw new Exception("DetalleValeColectivo.insertarSalidadCEYE: faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaValeCEYE("
                    + "'" + sUsuarioFirmado + "'::character varying," +
                    idhoja+"::integer,'"+
                    sClaveMaterial +  "'::character varying,'"  +
                    sLote + "'::character varying,"  +
                    nCantSurtida+"::smallint,"+
                    total + "::smallint );";
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
 
    public DetalleValeColectivo[] arrayExistencias(Date dFecha)throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetalle=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery = "";     
    int i=0;
         
        sQuery = "SELECT * FROM buscaDetalleExistencia('"+ 
                oFec.format(dFecha)+"');";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new DetalleValeColectivo[rst.size()];
            vObj=new ArrayList <DetalleValeColectivo>();
            for(i=0;i<rst.size();i++){
                oDetalle=new DetalleValeColectivo();
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oDetalle.getMaterial().setClaveMaterial(((String)vRowTemp.get(0)));
                oDetalle.setLote(((String )vRowTemp.get(1)));
                oDetalle.setExistencia(((Double)vRowTemp.get(2)).intValue());
                vObj.add(oDetalle);
            }
            int nTam = vObj.size();
            arrRet = new DetalleValeColectivo[nTam];
            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public int insertarSalidaQuirofano(int idhoja,String sClaveMaterial, 
            String sLote,int nCantSurtida,int total) throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        if (idhoja ==0 || sLote == null) {   
            throw new Exception("DetalleValeColectivo.insertarSalidaQuirofano: faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaValeQuirofano("
                    + "'" + sUsuarioFirmado + "'::character varying," +
                    idhoja+"::integer,'"+
                    sClaveMaterial +  "'::character varying,'"  +
                    sLote + "'::character varying,"  +
                    nCantSurtida+"::smallint,"+
                    total + "::smallint );";
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
    
    public DetalleValeColectivo[] buscarValePorArea(int clave,Date dFecha) 
            throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetalle=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery = "";     
    int i=0;
         
        sQuery = "SELECT * FROM buscavalecolectivoporarea("+clave+
                "::smallint,'"+oFec.format(dFecha)+"%');";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if(rst!=null){
            arrRet=new DetalleValeColectivo[rst.size()];
            vObj=new ArrayList <DetalleValeColectivo>();
            for(i=0;i<rst.size();i++){
                oDetalle=new DetalleValeColectivo();
                ArrayList vRowTemp=(ArrayList)rst.get(i);
                oDetalle.getVale().setIdHoja(
                        ((Double)vRowTemp.get(0)).intValue());
                oDetalle.getMaterial().setClaveMaterial(
                        ((String)vRowTemp.get(1)));
                oDetalle.getMaterial().setNombre(((String) vRowTemp.get(2)));
                oDetalle.getMaterial().getPresentacion().setValor(
                        ((String) vRowTemp.get(3)));                         
                oDetalle.setCantSolArea(
                        ((Double) vRowTemp.get(4)).shortValue());
                vObj.add(oDetalle);
            }
            int nTam = vObj.size();
            arrRet = new DetalleValeColectivo[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public int surtirValeColectivoPorArea(int idhoja,int clave,
            String sClaveMaterial, String sLote,int nCantSurtida,int total) 
            throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        
        if (idhoja==0 || clave==0 || sClaveMaterial == null) { //completar llave
            throw new Exception("Control.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaporarea("
                    + "'" 
                    +sUsuarioFirmado + "'::character varying," 
                    +idhoja+"::integer,"
                    +clave+"::smallint,'"
                    +sClaveMaterial +  "'::character varying,'" 
                    +sLote + "'::character varying,"  
                    +nCantSurtida+"::smallint,"
                    +total + "::smallint );";
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
    
    public DetalleValeColectivo[] buscarValePorPaciente(int clave,Date dFecha)
            throws Exception{
    DetalleValeColectivo arrRet[]=null, oDetalle=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeColectivo>vObj=null;
    String sQuery = "";     
    int i=0;
        if (dFecha == null){
            throw new Exception("DetalleValeColectivo.buscarValePorPaciente: error, no se ha ingresado la Fecha");
        }
        else {
            sQuery = "SELECT * FROM buscavalecolectivoporpaciente("+clave+
                    "::smallint,'"+oFec.format(dFecha)+"%');";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if(rst!=null){
                arrRet=new DetalleValeColectivo[rst.size()];
                vObj=new ArrayList <DetalleValeColectivo>();
                for(i=0;i<rst.size();i++){
                    oDetalle=new DetalleValeColectivo();
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oDetalle.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oDetalle.getEpisodio().getPaciente().setNombres(((String) vRowTemp.get(1)));                           
                    oDetalle.getEpisodio().getPaciente().setApPaterno(((String) vRowTemp.get(2)));
                    oDetalle.getEpisodio().getPaciente().setApMaterno(((String) vRowTemp.get(3)));
                    oDetalle.getEpisodio().getPaciente().getSeg().setNumero(((String) vRowTemp.get(4)));
                    oDetalle.getEpisodio().getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(5)).intValue());
                    vObj.add(oDetalle);
                }
                int nTam = vObj.size();
                arrRet = new DetalleValeColectivo[nTam];
                for (i = 0; i < nTam; i++) {
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }    
    
    public DetalleValeColectivo[] buscaMaterialesValePaciente(
            Date fecha,int area, long folio) throws Exception {
    DetalleValeColectivo arrRet[] = null,oDetalle = null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeColectivo> vObj = null;
    String sQuery = "";
    int i = 0;
        if (fecha == null) {
            throw new Exception("DetalleValeColectivoError.buscaMaterialesValePaciente: No se ha ingresado la Fecha");
        } else {
            sQuery = "SELECT * FROM buscamaterialesdelpaciente('"+ 
                    oFec.format(fecha) + "%'," + area + 
                    "::smallint,"+folio+"::bigint);";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                arrRet = new DetalleValeColectivo[rst.size()];
                vObj = new ArrayList<DetalleValeColectivo>();
                for (i = 0; i < rst.size(); i++) {
                    oDetalle = new DetalleValeColectivo();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oDetalle.getMaterial().setClaveMaterial(((String)vRowTemp.get(0)));
                    oDetalle.getMaterial().setNombre(((String)vRowTemp.get(1)));
                    oDetalle.setCantSolicitada(((Double)vRowTemp.get(2)).shortValue());                    
                    vObj.add(oDetalle);
                }
                int nTam = vObj.size();
                arrRet = new DetalleValeColectivo[nTam];
                for (i = 0; i < nTam; i++) {
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    public int insertarCantidaSurtidaPaciente(int clave, 
            long folio,String sClaveMaterial, String sLote,int nCantSurtida,
            int total) throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        
        if (clave==0 || folio==0|| sClaveMaterial == null) {   //completar llave
            throw new Exception(
                "DetalleValeColectivo.insertarCantidaSurtidaPaciente: faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaPorPacientes("
                    +folio+"::bigint,"
                    +clave+"::smallint,'"
                    +sClaveMaterial +"'::character varying,'" 
                    +sLote +"'::character varying," 
                    +nCantSurtida+"::smallint,"
                    +total + "::smallint );";
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
  
    
        
        public String getModificarTabla() throws Exception{
            String sQuery = "";
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(this.getCantSurArea() == 0 && this.getCantSolArea() < this.getCantSurArea()){
                throw new Exception("DetalleValeColectivo.getModificar: error de programación, Faltan datos");
            }else{
                sQuery = "SELECT * FROM modificavalecolectivosolicitudceye('"+sUsuarioFirmado+"'::character varying,"+oVale.getIdHoja()+","+this.getIdentificador()+"::bigint,"+this.getCantSurArea()+"::smallint,'"+fd.format(oVale.getFechaSurtido())+"'::timestamp);"; 
                System.out.println("El query es ----> " + sQuery);
            }
            return sQuery;
        }

    //*********************************************************
    
    
    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oMaterial;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable val) {
        this.oMaterial = (Material)val;
    }
    
    public ValeColectivo getVale(){
        return this.oVale;
    }
    public void setVale(ValeColectivo oval){
        this.oVale = oval;
    }
    public short getCantSolArea() {
        return nCantSolArea;
    }

    public void setCantSolArea(short nCantSolArea) {
        this.nCantSolArea = nCantSolArea;
    }

    public short getCantSurArea() {
        return nCantSurArea;
    }
    public void setCantSurArea(short val){
        this.nCantSurArea = val;
    }
    
    //POR EL
    public Material getMaterial(){
        return this.oMaterial;
    }
    
    /////////////////////////////////////////
    //Relacionados con surtido NO USAR
    ////////////////////////////////////////
    public List<DetalleValeColectivo> getListaLotes() {
        return lListaLotes;
    }
 
    public void setListaLotes(List<DetalleValeColectivo> lListaLotes) {
        this.lListaLotes = lListaLotes;
    }
    
    public String getLote() {
        return lote;
    }

    public void setLote(String lote) {
        this.lote = lote;
    }
    
    public int getExistencia() {
        return existencia;
    }

    public void setExistencia(int existencia) {
        this.existencia = existencia;
    }

    public String getTiposLotes() {
        return sTiposLotes;
    }

    public void setTiposLotes(String tiposLotes) {
        this.sTiposLotes = tiposLotes;
    }
    
    public short getCantAnterior() {
        return cantAnterior;
    }

    public void setCantAnterior(short cantAnterior) {
        this.cantAnterior = cantAnterior;
    }
} 

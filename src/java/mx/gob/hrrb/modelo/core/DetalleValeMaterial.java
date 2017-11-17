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
import mx.gob.hrrb.modelo.almacen.InventarioMateriales;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.almacen.ValeMaterial;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 * @author : Rafael, GIL
 * @version: 1.0
*/

public  class DetalleValeMaterial extends ServicioRealizado implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private Material oMaterial;
private ValeMaterial oValeMat;
private String sObservaciones;
private AreaServicioHRRB oAreaOrigen;
//Para surtido NO USAR
private InventarioMateriales oInventarioMateriales;

    public DetalleValeMaterial(){
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
                    throw new Exception("DetalleValeMaterial.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveDetalleValeMaterial();"; 
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
    public DetalleValeMaterial[] buscarTodos() throws Exception{
    DetalleValeMaterial arrRet[]=null, oDetalleValeMaterial=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosDetalleValeMaterial();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new DetalleValeMaterial[rst.size()];
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
                    throw new Exception("DetalleValeMaterial.insertar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaDetalleValeMaterial('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("DetalleValeMaterial.modificar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaDetalleValeMaterial('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("DetalleValeMaterial.eliminar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaDetalleValeMaterial('"+sUsuarioFirmado+"');"; 
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
    
    
        
        public String getModificarTablaOsteositesis() throws Exception{
            String sQuery = "";
            SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            if(getCantSurtida() == 0 && getCantSolicitada() < getCantSurtida()){
                throw new Exception("DetalleValeColectivo.getModificar: error de programación, Faltan datos");
            }else{
                sQuery = "SELECT * FROM modificaValeMaterialOsteosintesis('"+sUsuarioFirmado+"'::character varying,"+oValeMat.getFolioVale()+","+getIdentificador()+"::bigint,"+getCantSurtida()+"::smallint,'"+fd.format(oValeMat.getFechaSurtido())+"'::Date);"; 
                
            }
            return sQuery;
        }


    /////////////////////////////////////////////////////////////////////
    // Órdenes de servicio

    public DetalleValeMaterial[] buscarMaterialesSolicitadosPaciente(Date dFecAux, 
            int AreaServ, long FolioPac, long CveEpiMed) throws Exception{
    DetalleValeMaterial arrRet[]=null, oVale=null;
    ArrayList rst = null;
    ArrayList<DetalleValeMaterial> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if( this==null){
                throw new Exception("DetalleValeMaterial.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscamaterialsolicitadosurtidovalematerialpaciente('"+df.format(dFecAux)+"%',"
                    +AreaServ+"::smallint,"
                    +FolioPac+"::bigint,"
                    +CveEpiMed+"::bigint);";
            System.out.println(sQuery);
            oAD=new AccesoDatos();

            if (oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar(); 
            }
            if (rst != null) {
                vObj = new ArrayList<DetalleValeMaterial>();
                for (i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oVale=new DetalleValeMaterial();
                    ((Material)oVale.getServicioCobrable()).setNombre((String) vRowTemp.get(0));
                    oVale.setCantSolicitada(((Double) vRowTemp.get(1)).shortValue());
                    oVale.setCantSurtida(((Double) vRowTemp.get(2)).shortValue());

                    vObj.add(oVale);                              
                }
                nTam = vObj.size();
                arrRet = new DetalleValeMaterial[nTam];
                for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    
    public int insertaSolMatOst(ArrayList<DetalleValeMaterial> lMatInd) throws Exception{
    int mod=0;
    ArrayList rst=null, vRowTemp=null;
    String sQuery="";
        if(this.oEpisodio == null && lMatInd==null){
            throw new Exception("DetalleValeMaterial.insertaSolMatOst: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM insertasolicitudmaterialosteosintesisarr('"+ sUsuarioFirmado +"'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() +"::bigint,"
                    + getEpisodio().getClaveEpisodio() +"::bigint,"
                    + getAutorizadoPor().getNoTarjeta() +"::integer,'"
                    + this.getValeMat().getProcReal().getCIE9().getClave() +
                    "', ARRAY[";
            for(DetalleValeMaterial vc:lMatInd){
                if(vc.getCantSolicitada()!=0){
                    sQuery = sQuery + "" + vc.getCantSolicitada()+", ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::smallint[], ARRAY[";
            for(DetalleValeMaterial vc:lMatInd){
                if(vc.getServicioCobrable()!=null){
                    sQuery = sQuery + "'" + 
                    ((Material)vc.getServicioCobrable()).getClaveMaterial()+ "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";
            for(DetalleValeMaterial vc:lMatInd){
                if(vc.getObservaciones()!=null){
                    sQuery = sQuery + "'" + vc.getObservaciones()+ "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::text[]);";
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
        
    public String getInsertaSolMatOst() throws Exception{
        String sQuery="";
        if(this==null){
            throw new Exception("DetalleValeMaterial.Modificar: error, faltan datos");
        }else{
            System.out.println("Dentro del Else");
            sQuery = "SELECT * FROM insertasolicitudmaterialosteosintesis('"+
                    sUsuarioFirmado +"'::character varying,"
                + getEpisodio().getPaciente().getFolioPaciente() +"::bigint,"
                + getEpisodio().getClaveEpisodio() +"::bigint,"
                + getAutorizadoPor().getNoTarjeta() +"::integer,"
                + getCantSolicitada() + "::smallint,'"
                + ((Material)getServicioCobrable()).getClaveMaterial() + "'::character varying,'"
                + getObservaciones() + "'::text,'"
                + getEpisodio().getProceRe1().getCIE9().getClave() +"');";
        }
        return sQuery;
    }
        
    public void insertaDetalleValeMaterialOst(
            ArrayList<DetalleValeMaterial> arrDetValMatOst)throws Exception{
    ArrayList<String> vSolicMatOst=new ArrayList<>();
    int nRegAfectados=0;
        for(DetalleValeMaterial MatOst:arrDetValMatOst){
            vSolicMatOst.add(MatOst.getInsertaSolMatOst());
        }
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            nRegAfectados = oAD.ejecutarConsultaComando(vSolicMatOst);
            oAD.desconectar();
        }
    }
        
    public int insertaSolMatInd(ArrayList<DetalleValeMaterial> lMatInd) throws Exception{
    int mod=0;
    ArrayList rst=null, vRowTemp=null;
    String sQuery="";
        if(this.oEpisodio == null && lMatInd==null){
            throw new Exception("DetalleValeMaterial.insertaSolMatInd: error, faltan datos");
        }else{      
            sQuery ="SELECT * FROM insertasolicitudmaterialindividualarr('"+
                    sUsuarioFirmado +"'::character varying,"
                + getEpisodio().getPaciente().getFolioPaciente()+"::bigint,"
                + getEpisodio().getClaveEpisodio() +"::bigint,"
                + getAutorizadoPor().getNoTarjeta() +"::integer,ARRAY[";
            for(DetalleValeMaterial vc:lMatInd){
                if(vc.getCantSolicitada()!=0){
                    sQuery = sQuery + "" + vc.getCantSolicitada()+", ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::smallint[], ARRAY[";
            for(DetalleValeMaterial vc:lMatInd){
                if(((Material)vc.getServicioCobrable()).getClaveMaterial()!=null){
                    sQuery = sQuery + "'" + 
                    ((Material)vc.getServicioCobrable()).getClaveMaterial()+
                            "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[]);";
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
        
    /////////////////////////////////////////////////////////////////////
    //para surtido
    /////////////////////////////////////////////////////////////////////
    public DetalleValeMaterial[] buscarValeIndividual(Date dFecha) 
            throws Exception{
    DetalleValeMaterial arrRet[]=null, oDetalle=null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeMaterial>vObj=null;
    String sQuery = "";     
    int i=0;
        if (dFecha == null){
            throw new Exception("DetalleValeMaterial.buscarValeIndividual: No se ha ingresado la Fecha");
        }else {
            sQuery = "SELECT * FROM buscavaleindividual('"+
                    oFec.format(dFecha)+"%');";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if(rst!=null){
                arrRet=new DetalleValeMaterial[rst.size()];
                vObj=new ArrayList<DetalleValeMaterial>();
                for(i=0;i<rst.size();i++){
                    oDetalle=new DetalleValeMaterial();
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oDetalle.setAreaOrigen(new AreaServicioHRRB());
                    oDetalle.getAreaOrigen().setDescripcion(((String)vRowTemp.get(0)));
                    oDetalle.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                    oDetalle.getEpisodio().getPaciente().setNombres(((String) vRowTemp.get(2)));                           
                    oDetalle.getEpisodio().getPaciente().setApPaterno(((String) vRowTemp.get(3)));
                    oDetalle.getEpisodio().getPaciente().setApMaterno(((String) vRowTemp.get(4)));
                    oDetalle.getEpisodio().getPaciente().getSeg().setNumero(((String) vRowTemp.get(5)));
                    oDetalle.getEpisodio().getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(6)).intValue());
                    vObj.add(oDetalle);
                }
                int nTam = vObj.size();
                arrRet = new DetalleValeMaterial[nTam];
                for (i = 0; i < nTam; i++) {
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }  
    
    public DetalleValeMaterial[] buscaMaterialesValeIndividual(
            Date fecha, long folio) throws Exception {
    DetalleValeMaterial arrRet[] = null,oDetalle = null;
    ArrayList rst = null;
    SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
    ArrayList<DetalleValeMaterial> vObj = null;
    String sQuery = "";
    int i = 0;
        if (fecha == null) {
            throw new Exception("DetalleValeMaterial.buscaMaterialesValeIndividual: no se ha ingresado la Fecha");
        } else {
            sQuery = "SELECT * FROM buscamaterialesdelpacienteindividual('"+ 
                    oFec.format(fecha) + "%',"  +folio+"::bigint);";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                arrRet = new DetalleValeMaterial[rst.size()];
                vObj = new ArrayList<DetalleValeMaterial>();
                for (i = 0; i < rst.size(); i++) {
                    oDetalle = new DetalleValeMaterial();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oDetalle.getMaterial().setClaveMaterial(((String)vRowTemp.get(0)));
                    oDetalle.getMaterial().setNombre(((String)vRowTemp.get(1)));
                    oDetalle.setCantSolicitada(((Double)vRowTemp.get(2)).shortValue());                    
                    vObj.add(oDetalle);
                }
                int nTam = vObj.size();
                arrRet = new DetalleValeMaterial[nTam];

                for (i = 0; i < nTam; i++) {
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    
    public DetalleValeMaterial[]buscaLotesDetalleExistencia(String a)
            throws Exception{
    DetalleValeMaterial arrRet[]=null,oDet=null;
    ArrayList rst=null;
    ArrayList<DetalleValeMaterial>vObj=null;
    String sQuery="";
    int i=0,nTam=0;
        sQuery="SELECT *FROM buscalotesExistencia('"+a+"'::character varying);";
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleValeMaterial>();
            for (i = 0; i < rst.size(); i++) {
                oDet = new DetalleValeMaterial();
                oDet.setInv(new InventarioMateriales());
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oDet.getInv().setLote((String)vRowTemp.get(0));
                oDet.getInv().setExistencia(((Double)vRowTemp.get(1)).intValue());
                vObj.add(oDet);
            }
            nTam = vObj.size();
            arrRet = new DetalleValeMaterial[nTam];
            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        } 
        return arrRet; 
    }
    
    public int insertarCantidadSurtidaPacienteIndividual(long folio,
            String sClaveMaterial, String sLote,int nCantSurtida,int total) 
            throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
        
        if (folio==0 || sClaveMaterial == null) {
            throw new Exception("DetalleValeMaterial.insertarCantidadSurtidaPacienteIndividual: faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaPacienteIndividual("
                    + folio+"::bigint,'"                   
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
   
     
    /////////////////////////////////////////////////////////////////////
    
    
    
    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oMaterial;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable oValor) {
        this.oMaterial = (Material)oValor;
    }

    public ValeMaterial getValeMat() {
        return oValeMat;
    }

    public void setValeMat(ValeMaterial oValeMat) {
        this.oValeMat = oValeMat;
    }

    public String getObservaciones() {
        return sObservaciones;
    }

    public void setObservaciones(String sObservaciones) {
        this.sObservaciones = sObservaciones;
    }
    
    public AreaServicioHRRB getAreaOrigen(){
        return this.oAreaOrigen;
    }
    public void setAreaOrigen(AreaServicioHRRB val){
        this.oAreaOrigen = val;
    }
    
    //Por EL
    public Material getMaterial(){
        return this.oMaterial;
    }
    
    //Por surtido NO USAR
    public InventarioMateriales getInv() {
        return oInventarioMateriales;
    }

    public void setInv(InventarioMateriales oInventarioMateriales) {
        this.oInventarioMateriales = oInventarioMateriales;
    }
} 

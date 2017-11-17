package mx.gob.hrrb.modelo.caja;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.text.SimpleDateFormat;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Daniel
 */
public class FoliosDisponibles implements Serializable{
    private AccesoDatos oAD;
    private String sUsuarioFirmado;
    private Date dFechaRecepcion;
    private Parametrizacion oTipoRecibo;
    private Parametrizacion oTipoCaja;
    private int nFolioInicial;
    private int nFolioFinal;
    
    
    public FoliosDisponibles(){
        HttpServletRequest req;
	req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
		sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
            }
    }
    
    public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if( this==null){   //completar llave
            throw new Exception("FoliosDisponibles.insertar: error de programación, faltan datos");
	}
        else{ 
            sQuery = "SELECT * FROM insertaFoliosDisponibles('"
            + this.sUsuarioFirmado+"','"
            + this.oTipoRecibo.getClaveParametro()+"','"
            + this.oTipoRecibo.getTipoParametro()+"','"
            + this.oTipoCaja.getClaveParametro()+"','"
            + this.oTipoCaja.getTipoParametro()+"',"
            + this.nFolioInicial+","
            + this.nFolioFinal+");"; 
            oAD=new AccesoDatos(); 
            System.out.println(sQuery);
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
    
    public FoliosDisponibles[] buscarFoliosDisponiblesCajaRecibo() throws Exception{
        FoliosDisponibles arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
	sQuery = "SELECT * FROM buscaFoliosDisponiblesCaja("+isNull(this.oTipoCaja.getTipoParametro())+"::character(3),'"+(this.oTipoRecibo.getTipoParametro())+"'::character(3));"; 
        oAD=new AccesoDatos(); 
        System.out.println(sQuery);
	if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
	}
	if (rst != null) {
            arrRet = new FoliosDisponibles[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oFD=new FoliosDisponibles();
                oFD.setTipoCaja(new Parametrizacion());
                oFD.setTipoRecibo(new Parametrizacion());
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oFD.setFechaRecepcion((Date) vRowTemp.get(0));
                oFD.getTipoRecibo().setClaveParametro((String)vRowTemp.get(1));
                oFD.getTipoRecibo().setTipoParametro((String)vRowTemp.get(2));
                oFD.getTipoCaja().setClaveParametro((String)vRowTemp.get(3));
                oFD.getTipoCaja().setTipoParametro((String)vRowTemp.get(4));
                oFD.getTipoCaja().setValor((String)vRowTemp.get(5));
                oFD.setFolioInicial(((Double) vRowTemp.get(6)).intValue());
                oFD.setFolioFinal(((Double) vRowTemp.get(7)).intValue());
                arrRet[i]=oFD;
            } 
	} 
	return arrRet; 
    }
    public void insertaFoliosDisponibles(ArrayList<FoliosDisponibles> arrFolios) throws Exception{
        ArrayList<String> vTransac=new ArrayList<>();
        int nRegAfectados=0;
        for(FoliosDisponibles fd:arrFolios){
            vTransac.add(fd.getInserta());
        }
        oAD=new AccesoDatos();
            if (oAD.conectar()){ 
                nRegAfectados=oAD.ejecutarConsultaComando(vTransac);
                oAD.desconectar(); 
        }
        
    }
        
    public ArrayList<String> entregaFoliosDisponiblesFoliosCaja(FoliosDisponibles arrFolios1[],FoliosDisponibles arrFolios2[],FoliosCaja oFoliosCaja,int nCantEntrega) throws Exception{
        String msj="";
        int nRegAfectados=0;
        ArrayList<String> vTransac=new ArrayList<>();
        ArrayList<String> vMsj=new ArrayList<>();
        int tmpEntrega=nCantEntrega;
        int tmpTotalRango=0;
        if(nCantEntrega<1){
            throw new Exception("FoliosDisponibles.Entregar: error, faltan datos");
        }
        else{
            for(FoliosDisponibles fd:arrFolios1){
                tmpTotalRango=(fd.getFolioFinal()-fd.getFolioInicial())+1;
                if(tmpTotalRango<=tmpEntrega){
                    oFoliosCaja.setFolioInicial(fd.getFolioInicial());
                    oFoliosCaja.setFolioFinal(fd.getFolioFinal());
                    vTransac.add(oFoliosCaja.getInsertar());
                    vTransac.add(fd.getElimina());
                    vMsj.add(oFoliosCaja.getFolioInicial()+" a "+oFoliosCaja.getFolioFinal()+" de "+fd.getTipoCaja().getValor()+".");
                }
                else{
                    oFoliosCaja.setFolioInicial(fd.getFolioInicial());
                    oFoliosCaja.setFolioFinal((fd.getFolioInicial()+tmpEntrega)-1);
                    vTransac.add(oFoliosCaja.getInsertar());
                    fd.setFolioInicial(fd.getFolioInicial()+tmpEntrega);
                    vTransac.add(fd.getModificaFolioInicial());
                    vMsj.add(oFoliosCaja.getFolioInicial()+" a "+oFoliosCaja.getFolioFinal()+" de "+fd.getTipoCaja().getValor()+".");
                }
                tmpEntrega=tmpEntrega-tmpTotalRango;
            }
            if(tmpEntrega>0){
                for(FoliosDisponibles fd:arrFolios2){
                    tmpTotalRango=(fd.getFolioFinal()-fd.getFolioInicial())+1;
                    if(tmpEntrega>0){
                        if(tmpTotalRango<=tmpEntrega){
                            oFoliosCaja.setFolioInicial(fd.getFolioInicial());
                            oFoliosCaja.setFolioFinal(fd.getFolioFinal());
                            vTransac.add(oFoliosCaja.getInsertar());
                            vTransac.add(fd.getElimina());
                            vMsj.add(oFoliosCaja.getFolioInicial()+" a "+oFoliosCaja.getFolioFinal()+" de "+fd.getTipoCaja().getValor()+".");
                        }
                        else{
                            oFoliosCaja.setFolioInicial(fd.getFolioInicial());
                            oFoliosCaja.setFolioFinal((fd.getFolioInicial()+tmpEntrega)-1);
                            vTransac.add(oFoliosCaja.getInsertar());
                            fd.setFolioInicial(fd.getFolioInicial()+tmpEntrega);
                            vTransac.add(fd.getModificaFolioInicial());
                            vMsj.add(oFoliosCaja.getFolioInicial()+" a "+oFoliosCaja.getFolioFinal()+" de "+fd.getTipoCaja().getValor()+".");
                        }
                        tmpEntrega=tmpEntrega-tmpTotalRango;
                    }
                }           
            }
            oAD=new AccesoDatos();
            if (oAD.conectar()){ 
                nRegAfectados=oAD.ejecutarConsultaComando(vTransac);
                oAD.desconectar(); 
            }
        }
        if(nRegAfectados!=vTransac.size()){
            vMsj.clear();
            vMsj.add("No se pudo completar el registro.Contacte al administrador.");
        }
        return vMsj;
    }
    
    public String getInserta() throws Exception{
        String sQuery = "";
        if(this.getTipoRecibo()==null||this.getTipoCaja()==null||this.getFolioInicial()==0||this.getFolioFinal()==0){
	throw new Exception("FoliosDisponibles.Modificar: error, faltan datos");}
        else{
            sQuery = "SELECT * FROM insertaFoliosDisponibles('"
                                + this.sUsuarioFirmado+"','"
                                + this.oTipoRecibo.getClaveParametro()+"','"
                                + this.oTipoRecibo.getTipoParametro()+"','"
                                + this.oTipoCaja.getClaveParametro()+"','"
                                + this.oTipoCaja.getTipoParametro()+"',"
                                + this.nFolioInicial+","
                                + this.nFolioFinal+");"; 
        }
        return sQuery;
    }
    
    public String getModificaFolioInicial() throws Exception{
        String sQuery = "";
        if(this.getFechaRecepcion()==null||this.getTipoRecibo()==null||this.getTipoCaja()==null||this.getFolioInicial()==0){
	throw new Exception("FoliosDisponibles.ModificarFolioInicial: error, faltan datos");}
        else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sQuery ="SELECT * FROM modificaFoliosDisponiblesFolioInicial('"+
                    this.sUsuarioFirmado+ "'::character varying,'"+
                    format.format(this.getFechaRecepcion())+"'::timestamp without time zone,'"+
                    this.oTipoRecibo.getClaveParametro()+"'::character(3),'"+
                    this.oTipoRecibo.getTipoParametro()+"'::character(3),'"+
                    this.oTipoCaja.getClaveParametro()+"'::character(3),'"+
                    this.oTipoCaja.getTipoParametro()+"'::character(3),"+
                    this.getFolioInicial()
                    +"::integer);";
        }
        return sQuery;
    }
    
    public String getModificaFolioFinal() throws Exception{
        String sQuery = "";
        SimpleDateFormat format;
        if(this.getFechaRecepcion()==null||this.getTipoRecibo()==null||this.getTipoCaja()==null||this.getFolioFinal()==0){
	throw new Exception("FoliosDisponibles.ModificarFolioFinal: error, faltan datos");}
        else{
            format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sQuery ="SELECT * FROM modificaFoliosDisponiblesFolioFinal('"+
                    this.sUsuarioFirmado+ "'::character varying,'"+
                    format.format(this.dFechaRecepcion)+"'::timestamp without time zone,'"+
                    this.oTipoRecibo.getClaveParametro()+"'::character(3),'"+
                    this.oTipoRecibo.getTipoParametro()+"'::character(3),'"+
                    this.oTipoCaja.getClaveParametro()+"'::character(3),'"+
                    this.oTipoCaja.getTipoParametro()+"'::character(3),"+
                    this.getFolioFinal()
                    +"::integer);";
        }
        return sQuery;
    }
    
    public String getElimina() throws Exception{
        String sQuery = "";
        if( this==null){   //completar llave
	throw new Exception("FoliosDisponibles.Eliminar: error, faltan datos");}
        else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sQuery ="SELECT * FROM eliminaFoliosDisponibles('"+
                    this.sUsuarioFirmado+ "'::character varying,'"+
                    format.format(this.dFechaRecepcion)+"'::timestamp without time zone,'"+
                    this.oTipoRecibo.getClaveParametro()+"'::character(3),'"+
                    this.oTipoRecibo.getTipoParametro()+"'::character(3),'"+
                    this.oTipoCaja.getClaveParametro()+"'::character(3),'"+
                    this.oTipoCaja.getTipoParametro()+"'::character(3));";
        }
        return sQuery;
    }
    
    public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }

    public Date getFechaRecepcion() {
        return dFechaRecepcion;
    }

    public void setFechaRecepcion(Date dFechaRecepcion) {
        this.dFechaRecepcion = dFechaRecepcion;
    }

    public Parametrizacion getTipoRecibo() {
        return oTipoRecibo;
    }

    public void setTipoRecibo(Parametrizacion oTipoRecibo) {
        this.oTipoRecibo = oTipoRecibo;
    }

    public Parametrizacion getTipoCaja() {
        return oTipoCaja;
    }

    public void setTipoCaja(Parametrizacion oTipoCaja) {
        this.oTipoCaja = oTipoCaja;
    }

    public int getFolioInicial() {
        return nFolioInicial;
    }

    public void setFolioInicial(int nFolioInicial) {
        this.nFolioInicial = nFolioInicial;
    }

    public int getFolioFinal() {
        return nFolioFinal;
    }

    public void setFolioFinal(int nFolioFinal) {
        this.nFolioFinal = nFolioFinal;
    }
}
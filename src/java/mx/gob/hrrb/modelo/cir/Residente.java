package mx.gob.hrrb.modelo.cir;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
 */
public class Residente extends PersonalHospitalario implements Serializable{
    private AccesoDatos oAD;
    private String sUsuarioFirmado;
    private Date oInicio;
    private Date oFin;
    private Date oInicio2;
    private Date oFin2;
    private Date oInicio3;
    private Date oFin3;
    private Parametrizacion oNivelResid;
    
    
    public Residente(){
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
			throw new Exception("Residente.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertarResidente();"; 
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
    
    public Residente[] buscarResidentes() throws Exception{
        Residente arrRet[] = null, oResid = null;
        ArrayList rst = null;
        String sQuery = "";
        ArrayList<Residente> vObj = null;
        int i=0, nTam=0;
            sQuery = "SELECT * FROM buscaTodosResidente();";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<Residente>();
                for(i=0; i<rst.size(); i++){
                    oResid = new Residente();
                    oResid.oNivelResid = new Parametrizacion();
                    ArrayList<Object> vRowTemp = (ArrayList) rst.get(i);
                    oResid.setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                    oResid.setNombres((String) vRowTemp.get(1));
                    oResid.setApPaterno((String) vRowTemp.get(2));
                    oResid.setApMaterno((String) vRowTemp.get(3));
                    oResid.oNivelResid.setValor((String) vRowTemp.get(4));
                    vObj.add(oResid);
                }
                nTam = vObj.size();
                arrRet = new Residente[nTam];
                
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
    }
    
    public Residente[] buscaResidentesVigentes() throws Exception{
        Residente arrRet[] = null, oResid = null;
        ArrayList rst = null;
        String sQuery = "";
        ArrayList<Residente> vObj = null;
        int i=0, nTam=0;        
            sQuery = "SELECT * FROM buscaInformacionResidente();";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<Residente>();
                for(i=0; i<rst.size(); i++){
                    oResid = new Residente();
                    oResid.oNivelResid = new Parametrizacion();
                    ArrayList<Object> vRowTemp = (ArrayList) rst.get(i);
                    oResid.setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                    oResid.oNivelResid.setValor((String) vRowTemp.get(1));
                    oResid.setNombres((String) vRowTemp.get(2));
                    oResid.setApPaterno((String) vRowTemp.get(3));
                    oResid.setApMaterno((String) vRowTemp.get(4));
                    oResid.setInicio((Date) vRowTemp.get(5));
                    oResid.setFin((Date) vRowTemp.get(6));
                    oResid.setInicio2((Date) vRowTemp.get(7));
                    oResid.setFin2((Date) vRowTemp.get(8));
                    oResid.setInicio3((Date) vRowTemp.get(9));
                    oResid.setFin3((Date) vRowTemp.get(10));
                    vObj.add(oResid);
                }
                nTam = vObj.size();
                arrRet = new Residente[nTam];
                
                for(i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
    }
    
    public int regitraResidente() throws Exception{
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        SimpleDateFormat fd = new SimpleDateFormat("yyyy-MM-dd");
        System.out.println("Datos que recibe \n" 
                +"Nombre ---> " + getApPaterno()+" "+ getApMaterno()+" "+getNombres()+ " \n"+
                "Fecha Nacimiento ---->" + fd.format(getFechaNac())+ "\n" +
                "CURP --->" + getCurp() + "\n"+
                "Telefono --->" + getTelefono()+ "\n" +
                "Fecha inicio y fin ---->" +getInicio()+ " "+getFin());
        if(this == null){
            throw new Exception("Residente.RegistraResidente: error de programación, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM insertaPersonalHospitalarioResidente('"+this.sUsuarioFirmado+"'"
                    + ",'"+getApPaterno()+"'"
                    + ",'"+getApMaterno()+"'"
                    + ",'"+getNombres()+"'"
                    + ",'"+fd.format(getFechaNac())+"'"
                    + ",'"+getSexoP()+"'"
                    + ",'"+getCurp()+"'"
                    + ",'"+getTelefono()+"'"
                    + ",'"+fd.format(getInicio())+"'"
                    + ",'"+fd.format(getFin())+"');";
        }
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
            if(rst != null && rst.size() == 1){
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                nRet = ((Double) vRowTemp.get(0)).intValue();
            }
        }
        return nRet;        
    }
    
    public Date getInicio() { return oInicio; }
    public void setInicio(Date oInicio) { this.oInicio = oInicio; }
    public Date getFin() { return oFin; }
    public void setFin(Date oFin) { this.oFin = oFin; }
    public Date getInicio2() { return oInicio2; }
    public void setInicio2(Date oInicio2) { this.oInicio2 = oInicio2; }
    public Date getFin2() { return oFin2; }
    public void setFin2(Date oFin2) { this.oFin2 = oFin2; }
    public Date getInicio3() { return oInicio3; }
    public void setInicio3(Date oInicio3) { this.oInicio3 = oInicio3; }
    public Date getFin3() { return oFin3; }
    public void setFin3(Date oFin3) { this.oFin3 = oFin3; }
    public Parametrizacion getNivelResid() { return oNivelResid; }
    public void setNivelResid(Parametrizacion oNivelResid) { this.oNivelResid = oNivelResid; }
}

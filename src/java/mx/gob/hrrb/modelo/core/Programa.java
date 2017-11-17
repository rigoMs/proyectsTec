package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.List;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class Programa implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private int nClave;
private String sDescripcion;
private Parametrizacion oNivelSocioEconomico;

    public Programa(){
        HttpServletRequest req;
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
                sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
        }
        oNivelSocioEconomico= new  Parametrizacion(); 
    }
    
    
    public Programa[] buscaListProgram() throws Exception{
     ArrayList rst = null;
     Programa arrRet[]=null, oPh=null;
     String sQuery = "";     
     int i=0,nTam=0;
     List<Programa> vObj=null;
        sQuery = "SELECT * FROM buscaprogramas();"; 
        System.out.println(sQuery);
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null) {
            vObj = new ArrayList<Programa>();
            for (i=0; i<rst.size(); i++){
                oPh=new  Programa();
                ArrayList<Object> vRowTemp = (ArrayList)rst.get(i); 
                oPh.setClave(((Double) vRowTemp.get(0)).intValue());
                oPh.setDescripcion((String)vRowTemp.get(1)); 
                vObj.add(oPh); 
            }
            nTam = vObj.size();
            arrRet = new Programa[nTam];

            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }                         
        }
        return arrRet;
    }
 
 public void buscasDescripcion(EpisodioMedico Epi, String NivelSocio) throws Exception{
     	ArrayList rst = null;
	String sQuery = "select * from buscaDescripcionPrograma("+Epi.getPaciente().getFolioPaciente()+",'"+NivelSocio+"');"; 
        System.out.println(sQuery);
         oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
         if (rst != null && rst.size()==1) {
              ArrayList vRowTemp = (ArrayList)rst.get(0);
              this.setDescripcion((String)vRowTemp.get(0));
         }
 }
 
 public void buscaDescripcionPorClave(int claveProg) throws Exception{
     	ArrayList rst = null;
	String sQuery = "select * from buscadescripcionprogramaclave('"+claveProg+"');"; 
        System.out.println(sQuery);
         oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
         if (rst != null && rst.size()==1) {
              ArrayList vRowTemp = (ArrayList)rst.get(0);
              this.setDescripcion((String)vRowTemp.get(0));
         }else{
            this.setDescripcion(""); 
         }
 }

    
    public int getClave() {
	return nClave;
	}

    public void setClave(int valor) {
    nClave=valor;
    }

    public String getDescripcion() {
    return sDescripcion;
    }

    public void setDescripcion(String valor) {
	sDescripcion=valor;
	}

} 

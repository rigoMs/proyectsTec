package mx.gob.hrrb.modelo.capasits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Usuario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author pedro
 */
public class PruebasRapidas implements Serializable{
    private AccesoDatos oAD;
    private int nEdad;
    private String sNombre;
    private String sApPaterno;
    private String sApMaterno;
    private String sSexo;
    private String sDiagnostico;   
    private Date dFecha;
    private Firmado oFirm;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    private String sUsuario; 
    private int nNo;

    public PruebasRapidas() {
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
    }
    
    public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PrubasRapidas.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePruabasRapidas();"; 
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
        
        public PruebasRapidas[] buscarPruebasDeHoy(int num) throws Exception{
	PruebasRapidas arrRet[]=null, oEstudios=null;
	ArrayList rst = null;
        ArrayList<PruebasRapidas> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM  buscaPruebasRapidas('2015-03-01','"+sUsuario+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){  System.out.println(sQuery);
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<PruebasRapidas>();
			for (i = 0; i < rst.size(); i++) {
                            oEstudios= new PruebasRapidas();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEstudios.setNombre((String)vRowTemp.get(0));
                            oEstudios.setApPaterno((String)vRowTemp.get(1));
                            oEstudios.setApMaterno((String)vRowTemp.get(2));
                            oEstudios.setSexo((String)vRowTemp.get(3));
                            oEstudios.setEdad(((Double) vRowTemp.get(4)).intValue());
                            oEstudios.setDiagnostico((String)vRowTemp.get(5));
                            oEstudios.setNo(num++);
                            vObj.add(oEstudios);
			} 
                        nTam = vObj.size();
                        arrRet = new PruebasRapidas[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	}
        
	public PruebasRapidas[] buscarTodos() throws Exception{
	PruebasRapidas arrRet[]=null, oEstudios=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodospPruebasRapidas();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new PruebasRapidas[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PruebasRapidas.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPruebaRapida('"+sUsuario+"','"+getNombre()+"','"+getApPaterno()+"','"+getApMaterno()+"','"+getSexo()+"',"+getEdad()+",'"+getFecha()+"','"+getDiagnostico()+"');"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
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
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PruebasRapidas.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPruebasRapidas();"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
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
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PruebasRapidas.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPruebasRapidas();"; 
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

    public int getEdad() {
        return nEdad;
    }

    public void setEdad(int nEdad) {
        this.nEdad = nEdad;
    }

    public String getNombre() {
        return sNombre;
    }

    public void setNombre(String sNombre) {
        this.sNombre = sNombre;
    }

    public String getApPaterno() {
        return sApPaterno;
    }

    public void setApPaterno(String sApPaterno) {
        this.sApPaterno = sApPaterno;
    }

    public String getApMaterno() {
        return sApMaterno;
    }

    public void setApMaterno(String sApMaterno) {
        this.sApMaterno = sApMaterno;
    }

    public String getSexo() {
        return sSexo;
    }

    public void setSexo(String sSexo) {
        this.sSexo = sSexo;
    }
    
    public String getNombreCompleto(){
        return this.sNombre+" " + this.sApPaterno + " " + this.sApMaterno;
    }

    public String getDiagnostico() {
        return sDiagnostico;
    }

    public void setDiagnostico(String sDiagnostico) {
        this.sDiagnostico = sDiagnostico;
    }
    
    public Date getFecha() {
        return dFecha;
    }

    public void setFecha(Date valor) {
        this.dFecha = valor;
    }      
    
    public int getNo(){
        return nNo;
    }
    
    public void setNo(int valor){
        nNo=valor;
    }
        
}
package mx.gob.hrrb.modelo.capasits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class GruposCapasits implements Serializable{
	private AccesoDatos oAD;
	private int nAsistencia;
	private int nIntegrantes;
        private int nTipogrupo;
        private Date dFecha;
	private String sTipo;
        private Firmado oFirm;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private String sUsuario;
        private String sFormaciondegrupos2;
        private String sOtrasAct1;
        private int nGAVAsistenci;
        private int nGAVAIntegrantes;
        private int nFG2Asitencia;
        private int nFG2Integrantes;
        private int nGAAsitencia;
        private int nGAIntegrantes;
        private int nGAMAsistencia;
        private int nGAMintegrantes;
        private int nGAuAsistencia;
        private int nGAuIntegrantes;
        private int nG1Asistencia;
        private int nG1integrantes;
        private int nG2Asistencia;
        private int nG2Integrantes;
        private int nGOAsitencia;
        private int nGOIntegrantes;
        private int nO1Asistencia;
        private int nO1Integrantes;
                
    public GruposCapasits() {
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
    }

	public GruposCapasits[] buscar() throws Exception{
        GruposCapasits oGruposCapasits=null, arrRet[]=null;	
	ArrayList rst = null;
        ArrayList<GruposCapasits> vObj=null;
        int i=0, nTam=0;
	String sQuery = "";
			sQuery = "SELECT * FROM buscaGruposCapasits('"+sUsuario+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ System.out.println(sQuery);
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<GruposCapasits>();                            
                                for (i=0; i < rst.size(); i++) {
                                   oGruposCapasits=new GruposCapasits();
                                   ArrayList vRowTemp = (ArrayList)rst.get(i); 
                                    oGruposCapasits.setTipo((String)vRowTemp.get(0));
                                    oGruposCapasits.setAsistencia(((Double) vRowTemp.get(1)).intValue());
                                    oGruposCapasits.setIntegrantes(((Double) vRowTemp.get(2)).intValue());
                                    oGruposCapasits.setTipogrupo(((Double) vRowTemp.get(3)).intValue());
                                    vObj.add(oGruposCapasits);
                                    if(oGruposCapasits.getTipogrupo()==1){
                                        if(oGruposCapasits.getTipo().equals("GRUPOS AUTOAYUDA VIOLENCIA")){
                                            setGAVAsistenci(oGruposCapasits.getAsistencia());
                                            setGAVAIntegrantes(oGruposCapasits.getIntegrantes());                                             
                                        }
                                        else{
                                            setFormaciondegrupos2(oGruposCapasits.getTipo());
                                            setFG2Asitencia(oGruposCapasits.getAsistencia());
                                            setFG2Integrantes(oGruposCapasits.getIntegrantes());                                            
                                        }
                                    }        
                                    if(oGruposCapasits.getTipogrupo()==2){
                                        if(oGruposCapasits.getTipo().equals("GRUPOS DE ADICTOS")){
                                            setGAAsitencia(oGruposCapasits.getAsistencia());
                                            setGAIntegrantes(oGruposCapasits.getIntegrantes());
                                        }
                                        if(oGruposCapasits.getTipo().equals("GRUPOS DE AYUDA MUTUA")){
                                            setGAMAsistencia(oGruposCapasits.getAsistencia());
                                            setGAMintegrantes(oGruposCapasits.getIntegrantes());
                                        }
                                        if(oGruposCapasits.getTipo().equals("GRUPOS AUTOAYUDA VIOLENCIA")){
                                            setGAuAsistencia(oGruposCapasits.getAsistencia());
                                            setGAuIntegrantes(oGruposCapasits.getIntegrantes());
                                        }
                                    }
                                    if(oGruposCapasits.getTipogrupo()==3){
                                        if(oGruposCapasits.getTipo().equals("GRUPOS DE ADICTOS")){
                                            setG1Asistencia(oGruposCapasits.getAsistencia());
                                            setG1integrantes(oGruposCapasits.getIntegrantes());
                                        }
                                        if(oGruposCapasits.getTipo().equals("GRUPOS DE AYUDA MUTUA")){
                                            setG2Asistencia(oGruposCapasits.getAsistencia());
                                            setG2Integrantes(oGruposCapasits.getIntegrantes());                                            
                                        }
                                    }
                                    if(oGruposCapasits.getTipogrupo()==4){
                                        setGOAsitencia(oGruposCapasits.getAsistencia());
                                        setGOIntegrantes(oGruposCapasits.getIntegrantes());
                                    }
                                    if(oGruposCapasits.getTipogrupo()==5){
                                        setOtrasAct1(oGruposCapasits.getTipo());
                                        setO1Asistencia(oGruposCapasits.getAsistencia());
                                        setO1Integrantes(oGruposCapasits.getIntegrantes());
                                    }                                      
                                }				
                                nTam = vObj.size();
                                arrRet = new GruposCapasits[nTam];
                                for (i=0; i<nTam; i++){
                                arrRet[i] = vObj.get(i);}
		} 
		return arrRet; 
	} 
        
	public GruposCapasits[] buscarTodos() throws Exception{
	GruposCapasits arrRet[]=null, oGruposCapasits=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosGruposCapasits();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new GruposCapasits[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);    
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("GruposCapasits.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaGruposCapasits('"+sUsuario+"','"+getTipo()+"',"+getAsistencia()+"::smallint,"+getIntegrantes()+"::smallint,'"+getFecha()+"',"+getTipogrupo()+");"; 
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
			throw new Exception("GruposCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaGruposCapasits();"; 
			oAD=new AccesoDatos();  
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
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("GruposCapasits.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaGruposCapasits();"; 
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
	public int getAsistencia() {
	return nAsistencia;
	}

	public void setAsistencia(int valor) {
	nAsistencia=valor;
	}

	public int getIntegrantes() {
	return nIntegrantes;
	}

	public void setIntegrantes(int valor) {
	nIntegrantes=valor;
	}

	public String getTipo() {
	return sTipo;
	}

	public void setTipo(String valor) {
	sTipo=valor;
	}

    public int getTipogrupo() {
        return nTipogrupo;
    }

    public void setTipogrupo(int nTipogrupo) {
        this.nTipogrupo = nTipogrupo;
    }

    public Date getFecha() {
        return dFecha;
    }

    public void setFecha(Date dFecha) {
        this.dFecha = dFecha;
    }

    public String getFormaciondegrupos2() {
        return sFormaciondegrupos2;
    }

    public void setFormaciondegrupos2(String sFormaciondegrupos2) {
        this.sFormaciondegrupos2 = sFormaciondegrupos2;
    }

    public String getOtrasAct1() {
        return sOtrasAct1;
    }

    public void setOtrasAct1(String sOtrasAct1) {
        this.sOtrasAct1 = sOtrasAct1;
    }

    public int getGAVAsistenci() {
        return nGAVAsistenci;
    }

    public void setGAVAsistenci(int nGAVAsistenci) {
        this.nGAVAsistenci = nGAVAsistenci;
    }

    public int getGAVAIntegrantes() {
        return nGAVAIntegrantes;
    }

    public void setGAVAIntegrantes(int nGAVAIntegrantes) {
        this.nGAVAIntegrantes = nGAVAIntegrantes;
    }

    public int getFG2Asitencia() {
        return nFG2Asitencia;
    }

    public void setFG2Asitencia(int nFG2Asitencia) {
        this.nFG2Asitencia = nFG2Asitencia;
    }

    public int getFG2Integrantes() {
        return nFG2Integrantes;
    }

    public void setFG2Integrantes(int nFG2Integrantes) {
        this.nFG2Integrantes = nFG2Integrantes;
    }

    public int getGAAsitencia() {
        return nGAAsitencia;
    }

    public void setGAAsitencia(int nGAAsitencia) {
        this.nGAAsitencia = nGAAsitencia;
    }

    public int getGAIntegrantes() {
        return nGAIntegrantes;
    }

    public void setGAIntegrantes(int nGAIntegrantes) {
        this.nGAIntegrantes = nGAIntegrantes;
    }

    public int getGAMAsistencia() {
        return nGAMAsistencia;
    }

    public void setGAMAsistencia(int nGAMAsistencia) {
        this.nGAMAsistencia = nGAMAsistencia;
    }

    public int getGAMintegrantes() {
        return nGAMintegrantes;
    }

    public void setGAMintegrantes(int nGAMintegrantes) {
        this.nGAMintegrantes = nGAMintegrantes;
    }

    public int getGAuAsistencia() {
        return nGAuAsistencia;
    }

    public void setGAuAsistencia(int nGAuAsistencia) {
        this.nGAuAsistencia = nGAuAsistencia;
    }

    public int getGAuIntegrantes() {
        return nGAuIntegrantes;
    }

    public void setGAuIntegrantes(int nGAuIntegrantes) {
        this.nGAuIntegrantes = nGAuIntegrantes;
    }

    public int getG1Asistencia() {
        return nG1Asistencia;
    }

    public void setG1Asistencia(int nG1Asistencia) {
        this.nG1Asistencia = nG1Asistencia;
    }

    public int getG1integrantes() {
        return nG1integrantes;
    }

    public void setG1integrantes(int nG1integrantes) {
        this.nG1integrantes = nG1integrantes;
    }

    public int getG2Asistencia() {
        return nG2Asistencia;
    }

    public void setG2Asistencia(int nG2Asistencia) {
        this.nG2Asistencia = nG2Asistencia;
    }

    public int getG2Integrantes() {
        return nG2Integrantes;
    }

    public void setG2Integrantes(int nG2Integrantes) {
        this.nG2Integrantes = nG2Integrantes;
    }

    public int getGOAsitencia() {
        return nGOAsitencia;
    }

    public void setGOAsitencia(int nGOAsitencia) {
        this.nGOAsitencia = nGOAsitencia;
    }

    public int getGOIntegrantes() {
        return nGOIntegrantes;
    }

    public void setGOIntegrantes(int nGOIntegrantes) {
        this.nGOIntegrantes = nGOIntegrantes;
    }

    public int getO1Asistencia() {
        return nO1Asistencia;
    }

    public void setO1Asistencia(int nO1Asistencia) {
        this.nO1Asistencia = nO1Asistencia;
    }

    public int getO1Integrantes() {
        return nO1Integrantes;
    }

    public void setO1Integrantes(int nO1Integrantes) {
        this.nO1Integrantes = nO1Integrantes;
    }

} 

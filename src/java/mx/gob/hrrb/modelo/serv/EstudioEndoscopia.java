package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objetivo: 
 * @author : Pablo, Rafael
 * @version: 1.0
*/

public  class EstudioEndoscopia extends Estudios implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private Material arrMaterial;
private Medicamento arrMedicamentos;

	public EstudioEndoscopia(){
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
			throw new Exception("EstudioEndoscopia.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveEstudioEndoscopia();"; 
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
	public EstudioEndoscopia[] buscarTodos() throws Exception{
	EstudioEndoscopia arrRet[]=null, oEstudioEndoscopia=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosEstudioEndoscopia();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new EstudioEndoscopia[rst.size()];
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
			throw new Exception("EstudioEndoscopia.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaEstudioEndoscopia('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("EstudioEndoscopia.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEstudioEndoscopia('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("EstudioEndoscopia.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaEstudioEndoscopia('"+sUsuarioFirmado+"');"; 
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
	
        public EstudioEndoscopia[] buscarEstudiosEndoscopia(String EstEnd) throws Exception {
        EstudioEndoscopia arrRet[] = null, oEst = null;
        ArrayList rst = null;
        ArrayList<EstudioEndoscopia> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarestudiosdesolicitudendoscopia('" + EstEnd + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<EstudioEndoscopia>();
            for (i = 0; i < rst.size(); i++) {
                oEst = new EstudioEndoscopia();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEst.setConcepto(((String) vRowTemp.get(0)).trim());
                vObj.add(oEst);
            }
            nTam = vObj.size();
            arrRet = new EstudioEndoscopia[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public List<EstudioEndoscopia> getListaEstudiosEnd(String txt) {
        List<EstudioEndoscopia> lListaEst = null;
        try {
            lListaEst = new ArrayList<EstudioEndoscopia>(Arrays.asList(
                    (new EstudioEndoscopia()).buscarEstudiosEndoscopia(txt)));
        } catch (Exception ex) {
            Logger.getLogger(EstudioEndoscopia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaEst;
    }

    public List<String> completarEstEnd(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<EstudioEndoscopia> lis = getListaEstudiosEnd(sTxt);
        for (EstudioEndoscopia li : lis) {
            if (sp_ascii(li.getConcepto()).contains(sTxt)) {
                arrRet.add(li.getConcepto());
            }
        }
        return arrRet;
    }

    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }
    
    public boolean buscarClavesEstudiosEnd() throws Exception{
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscarestudiosendoscopiaclaves('" + getConcepto() + "');";
         oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setClaveInterna(((Double) vRowTemp.get(0)).intValue());
            this.setClaveStudio(((String) vRowTemp.get(1)).trim());
            bRet = true;
        }
        return bRet;
    }
    
        
        public Material getArrMaterial() {
            return arrMaterial;
	}

	public void setArrMaterial(Material valor) {
	arrMaterial=valor;
	}

	public Medicamento getArrMedicamentos() {
	return arrMedicamentos;
	}

	public void setArrMedicamentos(Medicamento valor) {
	arrMedicamentos=valor;
	}
} 

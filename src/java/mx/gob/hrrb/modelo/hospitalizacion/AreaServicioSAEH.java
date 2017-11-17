package mx.gob.hrrb.modelo.hospitalizacion;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class AreaServicioSAEH implements Serializable{
	private AccesoDatos oAD;
	private int nTipo;
	private String sClave;
	private String sDescripcion;
	List<String> arrRet;
    List<AreaServicioSAEH>lListaAreacve;

    public AreaServicioSAEH(){
            arrRet = null;
            lListaAreacve = null;
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AreaServicioSAEH.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAreaServicioSAEH();"; 
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
	public AreaServicioSAEH[] buscarTodos() throws Exception{
	AreaServicioSAEH arrRet[]=null, oAreaServicioSAEH=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAreaServicioSAEH();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new AreaServicioSAEH[rst.size()];
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
			throw new Exception("AreaServicioSAEH.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAreaServicioSAEH();"; 
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
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AreaServicioSAEH.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAreaServicioSAEH();"; 
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
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AreaServicioSAEH.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAreaServicioSAEH();"; 
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

	public AreaServicioSAEH[] buscarAreaServicioSAEH(String area) throws Exception{
	AreaServicioSAEH arrRet[]=null, oArea=null;
	ArrayList rst = null;
        ArrayList<AreaServicioSAEH> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaAreaServicioSAEH('"+area+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AreaServicioSAEH>();
			for (i = 0; i < rst.size(); i++) {
                            oArea = new AreaServicioSAEH();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oArea.setDescripcion((String)vRowTemp.get(0));
                            vObj.add(oArea);
                            System.out.println("buscarCIE10: Diag-->"+oArea.getDescripcion().toString());
			}
                    nTam = vObj.size();
                    arrRet = new AreaServicioSAEH[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        
         //Busca Coincidencias en descricion de diagnostico
        public AreaServicioSAEH[] buscarClave(String area) throws Exception{
	AreaServicioSAEH arrRet[]=null, oArea=null;
	ArrayList rst = null;
        ArrayList<AreaServicioSAEH> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaClaveArea('"+area+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AreaServicioSAEH>();
			for (i = 0; i < rst.size(); i++) {
                            oArea = new AreaServicioSAEH();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oArea.setClave((String)vRowTemp.get(0));
                            vObj.add(oArea);
                            System.out.println("buscarCIE10: CIE10-->"+oArea.getClave().toString());
                        }
                    nTam = vObj.size();
                    arrRet = new AreaServicioSAEH[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}         
        
       //Retorna lista Diagnosticos
     public List<AreaServicioSAEH> getListaAreasServicio(String txt){
       List<AreaServicioSAEH>lListaArea = null;
       
       try {
           lListaArea= new ArrayList<AreaServicioSAEH>(Arrays.asList(
                   (new AreaServicioSAEH()).buscarAreaServicioSAEH(txt)));
       } catch (Exception ex) {
           Logger.getLogger(DiagnosticoCIE10.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lListaArea;
    }    
    
     public List<String> completar(String sTxt){
         arrRet = new ArrayList<String>();
        List<AreaServicioSAEH> lis= getListaAreasServicio(sTxt);
            for (int i=0; i< lis.size(); i++){
                  if (sp_ascii(lis.get(i).getDescripcion()).contains(sTxt)){
                   arrRet.add(lis.get(i).getDescripcion().toString());
                }
            }
    return arrRet;
    }            
     
     public List<AreaServicioSAEH> getClave(String txt){
         lListaAreacve = null;
         System.out.println("getClaves: "+txt);
       try {
           lListaAreacve= new ArrayList<AreaServicioSAEH>(Arrays.asList(
                   (new AreaServicioSAEH()).buscarClave(txt)));
       } catch (Exception ex) {
           Logger.getLogger(DiagnosticoCIE10.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lListaAreacve;
    }     
        
    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
            for (int i=0; i<original.length(); i++) {
                // Reemplazamos los caracteres especiales.
                output = output.replace(original.charAt(i), ascii.charAt(i));
            }//for i
        return output;
    }  
    
    public String claveArea() {
        String x="";
        if(lListaAreacve==null || lListaAreacve.isEmpty()){
            x="";
            setClave(x);
        }
        else{
            x=lListaAreacve.get(0).getClave().toString();
            setClave(x);
        }
        return x;
    } 
     
	public int getTipo() {
	return nTipo;
	}

	public void setTipo(int valor) {
	nTipo=valor;
	}

	public String getClave() {
	return sClave;
	}

	public void setClave(String valor) {
	sClave=valor;
	}

	public String getDescripcion() {
	return sDescripcion;
	}

	public void setDescripcion(String valor) {
	sDescripcion=valor;
        getClave(valor);
	}

} 

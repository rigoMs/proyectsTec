/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.cir;

import java.io.Serializable;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
/**
 *
 * @author jeSer
 */
public class Quirofanos implements Serializable{
    private AccesoDatos oAD;
    private String sClave;
    private String sNombre;
    
    public Quirofanos(){
        
    }
    
    public boolean buscar() throws Exception{
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if( this==null){   //completar llave
            throw new Exception("Quirofanos.buscar: error de programación, faltan datos");
        }else{ 
            sQuery = "SELECT * FROM quirofano"; 
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
    
    public Quirofanos[] buscarTodos() throws Exception{
        Quirofanos arrRet[]= null, oQuirofanos= null;
        ArrayList rst= null;
        String sQuery = "";
        ArrayList<Quirofanos> vObj=null;
        int res=0;
        int i=0, nTam=0;
        sQuery = "SELECT * FROM buscaTodosQuirofano();";
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst= oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj=new ArrayList<Quirofanos>();
            for(i=0; i<rst.size(); i++){
                oQuirofanos = new Quirofanos();
                ArrayList<Object> vRowTemp = (ArrayList) rst.get(i);
                oQuirofanos.setClave((String) vRowTemp.get(0));
                oQuirofanos.setNombre((String) vRowTemp.get(1));
                vObj.add(oQuirofanos);
            }
            nTam=vObj.size();
            arrRet=new Quirofanos[nTam];
            
            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        }
        return arrRet;
    }
    
    	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Quirofanos.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaQuirofano();"; 
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
			throw new Exception("Quirofanos.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaQuirofano();"; 
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
			throw new Exception("Quirofanos.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaQuirofano();"; 
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

    /**
     * @return the sClave
     */
    public String getClave() {
        return sClave;
    }

    /**
     * @param sClave the sClave to set
     */
    public void setClave(String sClave) {
        this.sClave = sClave;
    }

    /**
     * @return the sNombre
     */
    public String getNombre() {
        return sNombre;
    }

    /**
     * @param sNombre the sNombre to set
     */
    public void setNombre(String sNombre) {
        this.sNombre = sNombre;
    }
}

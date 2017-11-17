package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class FamiliarCercano implements Serializable{
	private AccesoDatos oAD;
	private Parametrizacion oParentesco;
	private String sApMaternoFam;
	private String sApPaternoFam;
	private String sNombresFam;
	private String sNoTel1;
	private String sNoTel2;
	private String sNoTel3;
	private String parentescoStr;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("FamiliarCercano.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveFamiliarCercano();"; 
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
	public FamiliarCercano[] buscarTodos() throws Exception{
	FamiliarCercano arrRet[]=null, oFamiliarCercano=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosFamiliarCercano();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new FamiliarCercano[rst.size()];
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
			throw new Exception("FamiliarCercano.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaFamiliarCercano();"; 
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
			throw new Exception("FamiliarCercano.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaFamiliarCercano();"; 
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
			throw new Exception("FamiliarCercano.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaFamiliarCercano();"; 
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

	 //Retorna lista de parentescos
   public List<Parametrizacion> getListaParentescos() throws Exception{
        List<Parametrizacion> lLista = null;
       
           lLista = new ArrayList<Parametrizacion>(Arrays.asList(
                   (new Parametrizacion()).buscarTabla("T40")));
       
        return lLista;
    } 
     
    public void buscaNombreDeMadre(long foliopac) throws Exception{
    ArrayList rst = null;
    String sQuery = "";  

        sQuery = "select * from buscaNombreDeMadrePac("+foliopac+");";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null && rst.size()==1) {
            ArrayList vRowTemp = (ArrayList)rst.get(0);   
            this.setNombresFam((String)vRowTemp.get(0));
            this.setApPaternoFam((String)vRowTemp.get(1));
            this.setApMaternoFam((String)vRowTemp.get(2));      
        }else{
            this.setNombresFam("");
            this.setApPaternoFam("");
            this.setApMaternoFam("");
        }        
    }
    
    public int insertafamiliarcercano(String Usuario, long foliopac,long claveepi) throws Exception{
    int Resul=0; 
    ArrayList rst = null;
    String sQuery = "";
    
        if( foliopac==0){   //completar llave
            throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from insertafamiliarcercano('"+Usuario+"'::character varying,"+foliopac+","+claveepi+","+this.isNullTelefono(this.getNoTel1())+", '"+this.getNombresFam().toUpperCase()+"', '"+this.getApPaternoFam().toUpperCase()+"', '"+this.getApMaternoFam().toUpperCase()+"', 'T40"+this.getParentesco().getTipoParametro()+"')";

            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    Resul=(((Double)vRowTemp.get( 0 )).intValue());
            }
        } 
        return Resul; 
    }
    
    
	public Parametrizacion getParentesco() {
	return oParentesco;
	}

	public void setParentesco(Parametrizacion valor) {
	oParentesco=valor;
	}

	public String getApMaternoFam() {
	return sApMaternoFam;
	}

	public void setApMaternoFam(String valor) {
	sApMaternoFam=valor;
	}

	public String getApPaternoFam() {
	return sApPaternoFam;
	}

	public void setApPaternoFam(String valor) {
	sApPaternoFam=valor;
	}

	public String getNombresFam() {
	return sNombresFam;
	}

	public void setNombresFam(String valor) {
	sNombresFam=valor;
	}

	public String getNoTel1() {
	return sNoTel1;
	}

	public void setNoTel1(String valor) {
	sNoTel1=valor;
	}

	public String getNoTel2() {
	return sNoTel2;
	}

	public void setNoTel2(String valor) {
	sNoTel2=valor;
	}

	public String getNoTel3() {
	return sNoTel3;
	}

	public void setNoTel3(String valor) {
	sNoTel3=valor;
	}

	public String getParentescoStr() {
        return parentescoStr;
    }

    public void setParentescoStr(String parentescoStr) {
        this.parentescoStr = parentescoStr;
    }
    
    public String isNullTelefono(String param){
        if( param==null||param.equals("") || param.isEmpty())
            param="'99999'";
        else
            param="'"+param.trim()+"'";
        return param;
    }

} 

/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.capasits;

import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author sam
 */
public class NotaMedica {
    private AccesoDatos oAD;
    private long nFolioPaciente;
    private long nClaveEpisodio;
    private long nNoTarjeta;
    private Date dFechaRegistro;
    private String sObservacion;

    public NotaMedica() {

    }
    
    public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this.nFolioPaciente==0){   //completar llave
			throw new Exception("NotaMedica.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscarPacienteNota("+ getFolioPaciente()+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setFolioPaciente(((Double) vRowTemp.get( 0 )).intValue());
                                setClaveEpisodio(((Double) vRowTemp.get( 1 )).intValue());
                                setNoTarjeta(((Double) vRowTemp.get( 2 )).intValue());
                                setFechaRegistro(((Date) vRowTemp.get( 3 )));
                                setObservacion(((String) vRowTemp.get( 4 )));
				bRet = true;
			}
		} 
		return bRet; 
	} 
    
    public boolean buscarNoTarjeta() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this.nNoTarjeta==0){   //completar llave
			throw new Exception("NotaMedica.buscarNoTarjeta: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaNoTarjeta("+ getNoTarjeta()+");"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);                              
                                setNoTarjeta(((Double) vRowTemp.get( 0 )).intValue());          
				bRet = true;
			}
		} 
		return bRet; 
	}
    
    public int insertar(String usu, long tarjeta) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaMedica.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaMedica('"+usu+"',"+ getFolioPaciente()+","+ getNoTarjeta()+",'"+ getObservacion()+"');"; 
			System.out.println(sQuery);
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
    
    public int modificar(String usu) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( getClaveEpisodio()<= 0 || getFechaRegistro()== null || getFolioPaciente()== 0 || getNoTarjeta()<= 0 || getObservacion()== null){   //completar llave
			throw new Exception("NotaMedica.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaNotaMedica('"+usu+"',"+ getFolioPaciente()+","+ getClaveEpisodio()+","+ getNoTarjeta()+",'"+ getObservacion()+"');"; 
			System.out.println(sQuery);
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
    
    public int eliminar(String usu) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( getClaveEpisodio()<= 0 || getFechaRegistro()== null || getFolioPaciente()== 0 || getNoTarjeta()<= 0 || getObservacion()== null){   //completar llave
			throw new Exception("NotaMedica.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaMedica('"+usu+"',"+ getFolioPaciente()+","+ getClaveEpisodio()+","+ getNoTarjeta()+");"; 
			System.out.println(sQuery);
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

    public long getFolioPaciente() {
        return nFolioPaciente;
    }

    public void setFolioPaciente(long nFolioPaciente) {
        this.nFolioPaciente = nFolioPaciente;
    }

    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    public long getNoTarjeta() {
        return nNoTarjeta;
    }

    public void setNoTarjeta(long nNoTarjeta) {
        this.nNoTarjeta = nNoTarjeta;
    }

    public Date getFechaRegistro() {
        return dFechaRegistro;
    }

    public void setFechaRegistro(Date dFechaRegistro) {
        this.dFechaRegistro = dFechaRegistro;
    }

    public String getObservacion() {
        return sObservacion;
    }

    public void setObservacion(String sObservacion) {
        this.sObservacion = sObservacion;
    }

}

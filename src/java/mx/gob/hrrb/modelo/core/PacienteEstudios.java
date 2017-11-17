package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class PacienteEstudios implements Serializable{
	private AccesoDatos oAD;
	private Date dFechaEstudio;
	private String sObservaciones;
	private Paciente oPaciente;
	private Estudios oEstudios;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteEstudios.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePacienteEstudios();"; 
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
	public PacienteEstudios[] buscarTodos() throws Exception{
	PacienteEstudios arrRet[]=null, oPacienteEstudios=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPacienteEstudios();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new PacienteEstudios[rst.size()];
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
			throw new Exception("PacienteEstudios.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPacienteEstudios();"; 
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
			throw new Exception("PacienteEstudios.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPacienteEstudios();"; 
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
			throw new Exception("PacienteEstudios.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPacienteEstudios();"; 
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
	public Date getFechaEstudio() {
            return dFechaEstudio;
	}

	public void setFechaEstudio(Date valor) {
            dFechaEstudio=valor;
	}

	public String getObservaciones() {
	return sObservaciones;
	}

	public void setObservaciones(String valor) {
	sObservaciones=valor;
	}

	public Paciente getPaciente() {
	return oPaciente;
	}

	public void setPaciente(Paciente valor) {
	oPaciente=valor;
	}

	public Estudios getEstudios() {
	return oEstudios;
	}

	public void setEstudios(Estudios valor) {
	oEstudios=valor;
	}

} 

package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Asignacion implements Serializable{
	private AccesoDatos oAD;
	private Puesto oPuesto;
	private Turno oTurno;
	private PersonalHospitalario oPersonalHospitalario;
	private AreaServicioHRRB oAreaServicioHRRB;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Asignacion.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAsignacion();"; 
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
	public Asignacion[] buscarTodos() throws Exception{
	Asignacion arrRet[]=null, oAsignacion=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosAsignacion();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Asignacion[rst.size()];
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
			throw new Exception("Asignacion.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAsignacion();"; 
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
			throw new Exception("Asignacion.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAsignacion();"; 
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
			throw new Exception("Asignacion.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAsignacion();"; 
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
	public Puesto getPuesto() {
	return oPuesto;
	}

	public void setPuesto(Puesto valor) {
	oPuesto=valor;
	}

	public Turno getTurno() {
	return oTurno;
	}

	public void setTurno(Turno valor) {
	oTurno=valor;
	}

	public PersonalHospitalario getPersonalHospitalario() {
	return oPersonalHospitalario;
	}

	public void setPersonalHospitalario(PersonalHospitalario valor) {
	oPersonalHospitalario=valor;
	}

	public AreaServicioHRRB getAreaServicioHRRB() {
	return oAreaServicioHRRB;
	}

	public void setAreaServicioHRRB(AreaServicioHRRB valor) {
	oAreaServicioHRRB=valor;
	}

} 

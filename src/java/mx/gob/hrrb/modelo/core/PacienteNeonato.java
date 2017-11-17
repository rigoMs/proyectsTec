package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.notas.TerminacionEmbarazo;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class PacienteNeonato extends Paciente implements Serializable{
private AccesoDatos oAD;
private Producto oProducto;
private String sFolioCertificadoNac;
    
    public PacienteNeonato(){
        this.oProducto = new Producto();
        this.oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
    }
    public PacienteNeonato(Producto oProducto, String sFolioCertificadoNac, boolean bDiscapacitado, boolean bNacidoEnHospital, String bStatusPac, Date dFechaRegistroNivelSoc, Date dVigenciaNivelSoc, float nPeso,float nGramo, float nTalla, Parametrizacion oClaveEdad, Parametrizacion oNivelSocioEco, Parametrizacion oTipoSol, String sCalleNum,String sNumExterior,String sNumInterior,String sColonia, String sFolioDefuncion, String sGrupoRH, ArrayList arrEstudios, ArrayList arrSeguro, Referencia oReferencia, Etnicidad oEtnicidad, Ubicacion oUbicacion, Date dFechaNac, int nEdad, Parametrizacion oEstadoCivil, String sApMaterno, String sApPaterno, String sCurp, String sNombres, Parametrizacion oSexo, String sTelefono, String sSexo) {
        super(bDiscapacitado, bNacidoEnHospital, bStatusPac,  
                dFechaRegistroNivelSoc, dVigenciaNivelSoc, nPeso,nGramo, nTalla, oClaveEdad, 
                oNivelSocioEco, oTipoSol, sCalleNum, sColonia, sFolioDefuncion, 
                sGrupoRH, arrEstudios, arrSeguro, oReferencia, 
                oEtnicidad, oUbicacion, dFechaNac, nEdad, oEstadoCivil, 
                sApMaterno, sApPaterno, sCurp, sNombres,oSexo, sTelefono, sSexo);
        this.oProducto = oProducto;
        this.sFolioCertificadoNac = sFolioCertificadoNac;
    }

        @Override
  	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteNeonato.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePacienteNeonato();"; 
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
	public PacienteNeonato[] buscarTodos() throws Exception{
	PacienteNeonato arrRet[]=null, oPacienteNeonato=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPacienteNeonato();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new PacienteNeonato[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteNeonato.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPacienteNeonato();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteNeonato.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPacienteNeonato();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteNeonato.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPacienteNeonato();"; 
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
        
    //METODO CREADO POR ALBERTO QUE BUSCA PACIENTES NEONATALES
     public PacienteNeonato[] buscaPacientesNeonatos()throws Exception{
         PacienteNeonato arrRet[] = null;
         PacienteNeonato oPaciente = null;
         ArrayList rst = null;
         ArrayList<Paciente> vObj = null;
         String sQuery = "";
         String nombre = "";
         String appaterno = "";
         String apmaterno = "";
         String numexp = "";
         int i = 0;
         int nTam = 0;
         if(this.getOpcionUrg() == 0){
             nombre = this.getNombres();
             appaterno = this.getApPaterno();
             apmaterno = this.getApMaterno();
             numexp = "null";
         }else{
             nombre = "";
             appaterno = "";
             apmaterno = "";
             numexp = this.getExpediente().getNumero() + "";
         }
         if(numexp.compareTo("null") == 0)
             sQuery = "SELECT * FROM buscapacienteneonatoperinatal('"+nombre+"','"+appaterno+"','"+apmaterno+"',"+numexp+");";
         else
             sQuery = "SELECT * FROM buscapacienteneonatoperinatal('"+nombre+"','"+appaterno+"','"+apmaterno+"',"+Integer.parseInt(numexp)+");";         
         oAD = new AccesoDatos();
         if(oAD.conectar()){
             rst = oAD.ejecutarConsulta(sQuery);
             oAD.desconectar();
         }
         if(rst != null && rst.size() > 0){
             arrRet = new PacienteNeonato[rst.size()];
             for(i = 0; i < rst.size(); i++){
                 oPaciente = new PacienteNeonato();
                 ArrayList vRowTemp = (ArrayList)rst.get(i);
                 oPaciente.setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                 oPaciente.setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                 oPaciente.getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                 oPaciente.getProducto().setFechaNacimiento((Date)vRowTemp.get(3));
                 oPaciente.setNombres((String)vRowTemp.get(4).toString());
                 oPaciente.setApPaterno((String)vRowTemp.get(5).toString());
                 oPaciente.setApMaterno((String)vRowTemp.get(6).toString());
                 oPaciente.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(7)).longValue());
                 oPaciente.getProducto().getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(8)).longValue());
                 oPaciente.getProducto().getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(9)).intValue());
                 oPaciente.getProducto().getTerminacionEmbarazo().getPartoGrama().setNpartograma(((Double)vRowTemp.get(10)).longValue());
                 arrRet[i] = oPaciente;
            }
        }
        return arrRet;
    }
     
     
	public Producto getProducto() {
	return oProducto;
	}

	public void setProducto(Producto valor) {
	oProducto=valor;
	}

	public String getFolioCertificadoNac() {
	return sFolioCertificadoNac;
	}

	public void setFolioCertificadoNac(String valor) {
	sFolioCertificadoNac=valor;
	}

} 

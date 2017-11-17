/*
* MODIFICACIONES RELAIZADAS POR ALBERTO 3 FASE
* AGREGUE EL ATRIBUTO DE NUMERO DE PARTOGRAMA nPartoGrama
*/
package mx.gob.hrrb.modelo.core.notas;

import mx.gob.hrrb.modelo.core.NotaEmbarazo;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Ciudad;
import mx.gob.hrrb.modelo.core.Municipio;
import mx.gob.hrrb.modelo.core.Estado;
import mx.gob.hrrb.modelo.core.Seguro;
import mx.gob.hrrb.modelo.core.Cama;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class PartoGrama extends NotaEmbarazo implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private TerminacionEmbarazo oTerminacionEmbarazo;
private SeguimientoTrabajoParto oSeguimientoTrabajoParto;
private SegundaMitadEmbarazo oSegundaMitadEmbarazo;
private PrimeraMitadEmbarazo oPrimeraMitadEmbarazo;
private String sFechaHora;
private AdmisionUrgs oMotivoConsulta;
private long nPartoGrama;
        
	public PartoGrama(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oMotivoConsulta = new AdmisionUrgs();
	}
@Override
    public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PartoGrama.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePartoGrama();"; 
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
    public PartoGrama[] buscarTodos() throws Exception{
	PartoGrama arrRet[]=null, oPartoGrama=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPartoGrama();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new PartoGrama[rst.size()];
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
			throw new Exception("PartoGrama.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPartoGrama('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("PartoGrama.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPartoGrama('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("PartoGrama.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPartoGrama('"+sUsuarioFirmado+"');"; 
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
        /*************************METODO  QUE CARGA LOS DATOS GENERALES DEL PACIENTE*************************/
        public PartoGrama cargarDatos(long fpaciente, long clavepisodio)throws Exception{
            PartoGrama oPartoGrama = null;
            ArrayList rst = null;
            ArrayList rst1 = null;
            String sQuery = "";
            String sQuery1 = "";
            int i = 0;
            if(fpaciente == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
            else{
                sQuery = "SELECT * FROM consultaDatosPartoGrama(" + fpaciente + "::BIGINT, " + clavepisodio + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return null;
                else{
                    oPartoGrama = new PartoGrama();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oPartoGrama.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oPartoGrama.getEpiMed().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oPartoGrama.getEpiMed().getPaciente().setNombres((String)(vRowTemp).get(2).toString());
                    oPartoGrama.getEpiMed().getPaciente().setApPaterno((String)(vRowTemp).get(3).toString());
                    oPartoGrama.getEpiMed().getPaciente().setApMaterno((String)(vRowTemp).get(4).toString());
                    DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                    oPartoGrama.getEpiMed().getPaciente().setFechaNacTexto(format.format((Date)vRowTemp.get(5)));
                    oPartoGrama.getEpiMed().getPaciente().calculaEdad();
                    String grupoRH = (String)(vRowTemp).get(6).toString() + " ";
                            grupoRH += (String)(vRowTemp).get(7).toString();
                    oPartoGrama.getEpiMed().getPaciente().setTipoSangre(new Parametrizacion());
                    oPartoGrama.getEpiMed().getPaciente().getTipoSangre().setValor(grupoRH);
                    oPartoGrama.getEpiMed().getPaciente().setCalleNum((String)(vRowTemp).get(11).toString());
                    oPartoGrama.getEpiMed().getPaciente().setColonia((String)(vRowTemp).get(12).toString());
                    oPartoGrama.getEpiMed().getPaciente().setCiudad(new Ciudad());
                    oPartoGrama.getEpiMed().getPaciente().getCiudad().setDescripcionCiu((String)(vRowTemp).get(8).toString());
                    oPartoGrama.getEpiMed().getPaciente().setMunicipio(new Municipio());
                    oPartoGrama.getEpiMed().getPaciente().getMunicipio().setDescripcionMun((String)(vRowTemp).get(9).toString());
                    oPartoGrama.getEpiMed().getPaciente().setEstado(new Estado());
                    oPartoGrama.getEpiMed().getPaciente().getEstado().setDescripcionEdo((String)(vRowTemp).get(10));
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().setNumero(((Double)(vRowTemp).get(13)).intValue());
                    oPartoGrama.getEpiMed().getPaciente().setSeguro(new Seguro());
                    oPartoGrama.getEpiMed().getPaciente().getSeg().setNumero((String)(vRowTemp).get(14).toString());
                    oPartoGrama.getEpiMed().setFechaIngreso((Date)(vRowTemp).get(15));
                    oPartoGrama.getEpiMed().setCama(new Cama());
                    oPartoGrama.getEpiMed().getCama().setNumero((String)(vRowTemp).get(16).toString());
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setReligion((String)(vRowTemp).get(17).toString());
                    oPartoGrama.getEpiMed().getPaciente().setEdoCivilStr((String)(vRowTemp).get(18).toString());
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setGestaciones(((Double)(vRowTemp).get(19)).intValue());
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setPartos(((Double)(vRowTemp).get(20)).intValue());
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setAbortos(((Double)(vRowTemp).get(21)).intValue());
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setCesareas(((Double)(vRowTemp).get(22)).intValue());
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setUltimaMenstruacion((Date)(vRowTemp).get(23));
                    
                    sQuery1 = "SELECT * FROM buscadetallepacientepartograma(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
				this.getEpiMed().getPaciente().getClaveEpisodio() +"::BIGINT," + this.getConsecutivo() + "::SMALLINT," + 
				this.getNpartograma() + "::BIGINT);";
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        rst1 = oAD.ejecutarConsulta(sQuery1);
                        oAD.desconectar();
                    }
                    if(!rst1.isEmpty()){
                        ArrayList vRowTemp1 = (ArrayList)rst1.get(i);                        
			SimpleDateFormat format1 = new SimpleDateFormat ("HH:mm");                    
			String fecha = format.format((Date)vRowTemp1.get(0));
			String hora = format1.format((Date)vRowTemp1.get(0));
                        oPartoGrama.setFechaRegistro((Date)vRowTemp1.get(0));
			oPartoGrama.setFechaHora(fecha + " " + hora);
			oPartoGrama.getEpiMed().setAdmisionUrgs(new AdmisionUrgs());
			oPartoGrama.getEpiMed().getAdmisionUrgs().setMotivoConsulta((String)vRowTemp1.get(1).toString());
			oPartoGrama.getEpiMed().getSignosVitales().setPulso(((Double)vRowTemp1.get(2)).intValue());
			oPartoGrama.getEpiMed().getSignosVitales().setTemp((String)vRowTemp1.get(3).toString());
			oPartoGrama.getEpiMed().getSignosVitales().setTA((String)vRowTemp1.get(4).toString());
			oPartoGrama.getEpiMed().getSignosVitales().setFR((String)vRowTemp1.get(5).toString());
			String consciente = (String)vRowTemp1.get(6).toString();
			boolean bconsciente = consciente.compareTo("1") == 0;
			oPartoGrama.getEpiMed().getSignosVitales().setConsiente(bconsciente);
                        oPartoGrama.getMotivoConsulta().setMotivoAtencion((String)vRowTemp1.get(59).toString().trim() + "" + (String)vRowTemp1.get(60).toString());
                    }
                }
            }
            return oPartoGrama;
        }
        public PartoGrama CargaDetallePaciente()throws Exception{
	PartoGrama oPartoGrama = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	if(this.getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getNpartograma() == 0 || this.getConsecutivo() == 0)
		throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
	else{
		sQuery = "SELECT * FROM buscadetallepacientepartograma(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
				this.getEpiMed().getPaciente().getClaveEpisodio() +"::BIGINT," + this.getConsecutivo() + "::SMALLINT," + 
				this.getNpartograma() + "::BIGINT);";
		oAD = new AccesoDatos();
		if(oAD.conectar()){
			rst = oAD.ejecutarConsulta(sQuery);
			oAD.desconectar();
		}
		if(rst.isEmpty())
			return null;
		else{
			oPartoGrama = new PartoGrama();
			ArrayList vRowTemp = (ArrayList)rst.get(i);
			SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
			SimpleDateFormat format1 = new SimpleDateFormat ("HH:mm");                    
			String fecha = format.format((Date)vRowTemp.get(0));
			String hora = format1.format((Date)vRowTemp.get(0));                    
			oPartoGrama.setFechaHora(fecha + " " + hora);
			oPartoGrama.getEpiMed().setAdmisionUrgs(new AdmisionUrgs());
			oPartoGrama.getEpiMed().getAdmisionUrgs().setMotivoConsulta((String)vRowTemp.get(1).toString());
			oPartoGrama.getEpiMed().getSignosVitales().setPulso(((Double)vRowTemp.get(2)).intValue());
			oPartoGrama.getEpiMed().getSignosVitales().setTemp((String)vRowTemp.get(3).toString());
			oPartoGrama.getEpiMed().getSignosVitales().setTA((String)vRowTemp.get(4).toString());
			oPartoGrama.getEpiMed().getSignosVitales().setFR((String)vRowTemp.get(5).toString());
			String consciente = (String)vRowTemp.get(6).toString();
			boolean bconsciente = consciente.compareTo("1") == 0;
			oPartoGrama.getEpiMed().getSignosVitales().setConsiente(bconsciente);                    
		}
            }
            return oPartoGrama;
    }
        
    @Override
    public PartoGrama[] buscaPacienteDatos()throws Exception{
            PartoGrama arrRet[] = null;
            PartoGrama oPartoGrama = null;
            ArrayList rst = null;
            ArrayList<PartoGrama> vObj = null;
            String sQuery = "", nombre = "", aPaterno = "", aMaterno = "", numExp = "";
            int i = 0;
            if(this.getEpiMed().getPaciente().getOpcionUrg() == 0){
                nombre = this.getEpiMed().getPaciente().getNombres();
                aPaterno =this.getEpiMed().getPaciente().getApPaterno();
                aMaterno = this.getEpiMed().getPaciente().getApMaterno();
                numExp = "null";
            }else{
                nombre = "";
                aPaterno = "";
                aMaterno = "";
                numExp = this.getEpiMed().getPaciente().getExpediente().getNumero() + ""; 
            }
            if(numExp.compareTo("null") == 0)
                sQuery = "SELECT * FROM buscadatospacientepartograma('" + nombre + "'," + "'" + aPaterno + "'," + "'" + aMaterno + "'," + numExp + ");";
            else
                sQuery = "SELECT * FROM buscadatospacientepartograma('" + nombre + "'," + "'" + aPaterno + "'," + "'" + aMaterno + "'," + Integer.parseInt(numExp) +");";            
            oAD = new AccesoDatos();            
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);                
                oAD.desconectar(); 
            }
            if(rst != null && rst.size() > 0){                
                arrRet = new PartoGrama[rst.size()];
                for(i = 0; i < rst.size(); i++){
                    oPartoGrama = new PartoGrama();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oPartoGrama.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oPartoGrama.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oPartoGrama.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    oPartoGrama.setConsecutivo(((Double)vRowTemp.get(3)).intValue());
                    oPartoGrama.setNpartograma(((Double)vRowTemp.get(4)).longValue());
                    oPartoGrama.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(5));
                    oPartoGrama.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(6).toString());
                    oPartoGrama.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(7).toString());
                    oPartoGrama.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(8).toString());
                    oPartoGrama.setMaxConsecutivo(((Double)vRowTemp.get(9)).intValue());
                    arrRet[i] = oPartoGrama;
                }
            }
            return arrRet;
    }
    
    public PartoGrama cargarDatosParaConsultaExpediente(long fpaciente, long clave, int consecutivo)throws Exception{
        PartoGrama oPartoGrama = null;
        ArrayList rst = null;
        ArrayList rst1 = null;
        String sQuery = "";
        String sQuery1 = "";
        int i = 0;
        if(fpaciente == 0 || clave==0 || consecutivo==0)
            throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACIÓN");
        else{
            sQuery = "SELECT * FROM consultadatospartogramaparaexpediente(" + fpaciente + "::BIGINT," + clave +"::BIGINT," + consecutivo + "::SMALLINT);";                
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst.isEmpty())
                return null;
            else{
                oPartoGrama = new PartoGrama();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oPartoGrama.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                oPartoGrama.getEpiMed().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                oPartoGrama.getEpiMed().getPaciente().setNombres((String)(vRowTemp).get(2).toString());
                oPartoGrama.getEpiMed().getPaciente().setApPaterno((String)(vRowTemp).get(3).toString());
                oPartoGrama.getEpiMed().getPaciente().setApMaterno((String)(vRowTemp).get(4).toString());
                DateFormat format = new SimpleDateFormat("dd/MM/yyyy");
                oPartoGrama.getEpiMed().getPaciente().setFechaNacTexto(format.format((Date)vRowTemp.get(5)));
                oPartoGrama.getEpiMed().getPaciente().calculaEdad();
                String grupoRH = (String)(vRowTemp).get(6).toString() + " ";
                        grupoRH += (String)(vRowTemp).get(7).toString();
                oPartoGrama.getEpiMed().getPaciente().setTipoSangre(new Parametrizacion());
                oPartoGrama.getEpiMed().getPaciente().getTipoSangre().setValor(grupoRH);
                oPartoGrama.getEpiMed().getPaciente().setCalleNum((String)(vRowTemp).get(11).toString());
                oPartoGrama.getEpiMed().getPaciente().setColonia((String)(vRowTemp).get(12).toString());
                oPartoGrama.getEpiMed().getPaciente().setCiudad(new Ciudad());
                oPartoGrama.getEpiMed().getPaciente().getCiudad().setDescripcionCiu((String)(vRowTemp).get(8).toString());
                oPartoGrama.getEpiMed().getPaciente().setMunicipio(new Municipio());
                oPartoGrama.getEpiMed().getPaciente().getMunicipio().setDescripcionMun((String)(vRowTemp).get(9).toString());
                oPartoGrama.getEpiMed().getPaciente().setEstado(new Estado());
                oPartoGrama.getEpiMed().getPaciente().getEstado().setDescripcionEdo((String)(vRowTemp).get(10));
                oPartoGrama.getEpiMed().getPaciente().getExpediente().setNumero(((Double)(vRowTemp).get(13)).intValue());
                oPartoGrama.getEpiMed().getPaciente().setSeguro(new Seguro());
                oPartoGrama.getEpiMed().getPaciente().getSeguro().setNumero((String)(vRowTemp).get(14).toString());
                oPartoGrama.getEpiMed().setFechaIngreso((Date)(vRowTemp).get(15));
                oPartoGrama.getEpiMed().setCama(new Cama());
                oPartoGrama.getEpiMed().getCama().setNumero((String)(vRowTemp).get(16).toString());
                oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setReligion((String)(vRowTemp).get(17).toString());
                oPartoGrama.getEpiMed().getPaciente().setEdoCivilStr((String)(vRowTemp).get(18).toString());
                oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setGestaciones(((Double)(vRowTemp).get(19)).intValue());
                oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setPartos(((Double)(vRowTemp).get(20)).intValue());
                oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setAbortos(((Double)(vRowTemp).get(21)).intValue());
                oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setCesareas(((Double)(vRowTemp).get(22)).intValue());
                oPartoGrama.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setUltimaMenstruacion((Date)(vRowTemp).get(23));

                sQuery1 = "SELECT * FROM buscadetallepacientepartograma(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                            this.getEpiMed().getPaciente().getClaveEpisodio() +"::BIGINT," + this.getConsecutivo() + "::SMALLINT," + 
                            this.getNpartograma() + "::BIGINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst1 = oAD.ejecutarConsulta(sQuery1);
                    oAD.desconectar();
                }
                if(!rst1.isEmpty()){
                    ArrayList vRowTemp1 = (ArrayList)rst1.get(i);                        
                    SimpleDateFormat format1 = new SimpleDateFormat ("HH:mm");                    
                    String fecha = format.format((Date)vRowTemp1.get(0));
                    String hora = format1.format((Date)vRowTemp1.get(0));
                    oPartoGrama.setFechaRegistro((Date)vRowTemp1.get(0));
                    oPartoGrama.setFechaHora(fecha + " " + hora);
                    oPartoGrama.getEpiMed().setAdmisionUrgs(new AdmisionUrgs());
                    oPartoGrama.getEpiMed().getAdmisionUrgs().setMotivoConsulta((String)vRowTemp1.get(1).toString());
                    oPartoGrama.getEpiMed().getSignosVitales().setPulso(((Double)vRowTemp1.get(2)).intValue());
                    oPartoGrama.getEpiMed().getSignosVitales().setTemp((String)vRowTemp1.get(3).toString());
                    oPartoGrama.getEpiMed().getSignosVitales().setTA((String)vRowTemp1.get(4).toString());
                    oPartoGrama.getEpiMed().getSignosVitales().setFR((String)vRowTemp1.get(5).toString());
                    String consciente = (String)vRowTemp1.get(6).toString();
                    boolean bconsciente = consciente.compareTo("1") == 0 ? true : false;
                    oPartoGrama.getEpiMed().getSignosVitales().setConsiente(bconsciente);
                    oPartoGrama.getMotivoConsulta().setMotivoAtencion((String)vRowTemp1.get(59).toString().trim() + "" + (String)vRowTemp1.get(60).toString());
                }
            }
        }
        return oPartoGrama;
    }
        
    public PartoGrama[] buscaHistorialPartograma(long folioPac) throws Exception{
        PartoGrama arrRet[]=null, oPartograma=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("PartoGrama.buscaHistorialPartograma: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialpartograma("+folioPac+");";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new PartoGrama[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oPartograma= new PartoGrama();
                    oPartograma.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oPartograma.getEpiMed().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oPartograma.setConsecutivo(((Double)vRowTemp.get(2)).intValue());
                    oPartograma.setNpartograma(((Double)vRowTemp.get(3)).longValue());
                    oPartograma.setFechaRegistro((Date)vRowTemp.get(4));
                    arrRet[i]=oPartograma;
                } 
            } 
        }
        return arrRet; 
    }
        
    public TerminacionEmbarazo getTerminacionEmbarazo() {
	return oTerminacionEmbarazo;
    }

	public void setTerminacionEmbarazo(TerminacionEmbarazo valor) {
	oTerminacionEmbarazo=valor;
	}

	public SeguimientoTrabajoParto getSeguimientoTrabajoParto() {
	return oSeguimientoTrabajoParto;
	}

	public void setSeguimientoTrabajoParto(SeguimientoTrabajoParto valor) {
	oSeguimientoTrabajoParto=valor;
	}

	public SegundaMitadEmbarazo getSegundaMitadEmbarazo() {
	return oSegundaMitadEmbarazo;
	}

	public void setSegundaMitadEmbarazo(SegundaMitadEmbarazo valor) {
	oSegundaMitadEmbarazo=valor;
	}

	public PrimeraMitadEmbarazo getPrimeraMitadEmbarazo() {
	return oPrimeraMitadEmbarazo;
	}

	public void setPrimeraMitadEmbarazo(PrimeraMitadEmbarazo valor) {
	oPrimeraMitadEmbarazo=valor;
	}
        public String getFechaHora(){
            return sFechaHora;
        }
        public void setFechaHora(String dFechaHora){
            this.sFechaHora = dFechaHora;
        }
        public AdmisionUrgs getMotivoConsulta(){
            return oMotivoConsulta;
        }
        public void setMotivoConsulta(AdmisionUrgs oMotivoConsulta){
            this.oMotivoConsulta = oMotivoConsulta;
        }
        public long getNpartograma(){
            return nPartoGrama;
        }
        public void setNpartograma(long nPartoGrama){
            this.nPartoGrama = nPartoGrama;
        }
} 

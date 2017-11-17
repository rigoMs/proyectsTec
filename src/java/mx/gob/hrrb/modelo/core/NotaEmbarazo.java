package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import mx.gob.hrrb.modelo.core.notas.SegundaMitadEmbarazo;
import mx.gob.hrrb.modelo.urgencias.AdmisionUrgs;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class NotaEmbarazo extends NotaMedicaHRRB implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private String sFondoUterino;
private Parametrizacion oSituacionProducto;
private Parametrizacion oPresentacion;
private boolean bAbocado;
private boolean bEncajado;
private String sFCF;
private String sLA;
private String sPlacenta;
private String sObservacionesUltrasonido;
private short nDilatacion;
private short nBorramiento;
private String sPlano;
/*AGREGADOS COMO EN LA BASE DE DATOS*/
private Parametrizacion oAmnios;
private Parametrizacion oLiqAmniotico;
private Parametrizacion oPelvis;
private String sObsCardio;
private Parametrizacion oTrazoCardio;
/*INICIA CODIGO ROJO*/
private boolean bParoCardioDuranteEmb;
private boolean bAltEdoConciencia;
private boolean bInsufResp;
private boolean bCefaleaIntensa;
private boolean bPreeclampsiaSevera;
private boolean bChoqueHipovolemico;
private boolean bSepsisEmbarazo;
private boolean bProlapsoCordonUmb;
/*ATRIBUTOS QUE NO ESTABAN EN LA CLASE NOTA EMBARAZO Y 
  EN EL DIAGRAMA DE BASE DE DATOS*/
private boolean bConvulsiones;
private boolean bHemorragia;
/*****************************************************/
/*TERMINA CODIGO ROJO*/
/*INICIA CODIGO AMARILLO*/
private boolean bTrabajoParto;
private boolean bSalidaLiqSangVag;
private boolean bPreeclampsiaLeve;
private boolean bHipomotilidadFetal;
private boolean bRupPrematuraMembranas;
private boolean bEmbPatolCronica;
private boolean bAborto;
private boolean bPartoPretermino;
/*TERMINA CODIGO AMARILLO*/
/*INICIA CODIGO VERDE*/
private boolean bEmbMenor3SDGsinTrabParto;
private boolean bEmbAsintomatico;
private boolean bPuerperioFisiologico;
private boolean bPuerperioQxsinCompl;
/*TERMINA CODIGO VERDE*/
private boolean bAdecuada;
private boolean bIntegro;
private boolean bLANormal;
private boolean bLimite;
private boolean bReactivo;
private boolean bRota;
private String sMeconio;
private String sSometria;
/*ATRIBUTOS QUE NO ESTABAN EL DIAGRAMA DE CLASE Y EN EL DIAGRAMA
    DE BASE DE DATOS*/
private Date dHoraCodigoRojo;
private Date dHoraCodigoAmarillo;
private Date dHoraCodigoVerde;
private Date dHoraAtnCodigo; 
private PersonalHospitalario oPersonal;
/****************************/     
/*INICIAN ATRIBUTOS DERIVADOS*/
private DateFormat edad = new SimpleDateFormat("dd/MM/yyyy");
private SimpleDateFormat dateFormat = new SimpleDateFormat("dd-MM-yyyy HH:mm:ss");
private DateFormat fultmens = new SimpleDateFormat("dd/MM/yyyy");
/*TERMINAN ATRIBUTOS DERIVADOS*/
        
	public NotaEmbarazo(){
            this.oPelvis = new Parametrizacion();
            this.oPresentacion = new Parametrizacion();
            this.oSituacionProducto = new Parametrizacion();
            this.oTrazoCardio = new Parametrizacion();
            this.oAmnios = new Parametrizacion();
            this.oPersonal = new PersonalHospitalario();
            this.oPersonal.setUsuar(new Usuario());
            this.oLiqAmniotico = new Parametrizacion();
	HttpServletRequest req;        
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                this.oPersonal.getUsuar().setIdUsuario(this.sUsuarioFirmado);
	}
        public void buscaPersonal()throws Exception{
            this.oPersonal.buscaPersonalHospitalarioDatos();
        }
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("NotaEmbarazo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveNotaEmbarazo();"; 
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
	public NotaEmbarazo[] buscarTodos() throws Exception{
	NotaEmbarazo arrRet[]=null, oNotaEmbarazo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosNotaEmbarazo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new NotaEmbarazo[rst.size()];
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
			throw new Exception("NotaEmbarazo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaNotaEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaEmbarazo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaNotaEmbarazo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("NotaEmbarazo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaNotaEmbarazo('"+sUsuarioFirmado+"');"; 
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
        //******METODO QUE CARGA LOS DATOS GENERALES DEL PACIENTE******
        public EpisodioMedico datosPacienteTriage(long idPaciente, long clavepisodio) throws Exception{
            EpisodioMedico oEpisodioMedico = null;
            ArrayList rst = null;
            String sQuery;
            int i = 0;            
            if(idPaciente == 0)
                throw new Exception("NO ES POCIBLE PROCESAR INFORMACION");
            else{
                sQuery = "SELECT * FROM buscaPacienteTriage("+idPaciente+"::BIGINT," + clavepisodio + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return oEpisodioMedico = null;
                else{
                    oEpisodioMedico = new EpisodioMedico();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oEpisodioMedico.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                    oEpisodioMedico.setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                    oEpisodioMedico.setAdmisionUrgs(new AdmisionUrgs());
                    oEpisodioMedico.getAdmisionUrgs().setClaveEpisodio(((Double)vRowTemp.get(2)).intValue());
                    oEpisodioMedico.getAdmisionUrgs().setFolioAdmision(((Double)vRowTemp.get(3)).intValue());
                    oEpisodioMedico.getPaciente().setNombres((String)vRowTemp.get(4).toString());
                    oEpisodioMedico.getPaciente().setApPaterno((String)vRowTemp.get(5).toString());
                    oEpisodioMedico.getPaciente().setApMaterno((String)vRowTemp.get(6).toString());
                    oEpisodioMedico.getPaciente().setSeguro(new Seguro());
                    oEpisodioMedico.getPaciente().getSeguro().setNumero((String)vRowTemp.get(7).toString());
                    oEpisodioMedico.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
                    String fecha  = dateFormat.format((Date)vRowTemp.get(9)); 
                    oEpisodioMedico.setFechaIngreso((Date)vRowTemp.get(9));
                    oEpisodioMedico.setFIngreso(fecha);                    
                    oEpisodioMedico.getPaciente().setFechaNac((Date)vRowTemp.get(10));
                    String convertido = edad.format(oEpisodioMedico.getPaciente().getFechaNac());
                    oEpisodioMedico.getPaciente().setFechaNacTexto(convertido);
                    oEpisodioMedico.getPaciente().calculaEdad();
                    oEpisodioMedico.getAdmisionUrgs().setMotivoConsulta((String)vRowTemp.get(11).toString());
                    oEpisodioMedico.getPaciente().setTelefono((String)vRowTemp.get(12).toString());
                    oEpisodioMedico.getPaciente().getReferencia().setTipo((String)vRowTemp.get(13).toString());
                    oEpisodioMedico.getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(14).toString());
/*---CIUDAD>*/      oEpisodioMedico.getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(15).toString());
                    oEpisodioMedico.getPaciente().setColonia((String)vRowTemp.get(16).toString());
                    oEpisodioMedico.getPaciente().setCalleNum((String)vRowTemp.get(17).toString());
                    oEpisodioMedico.getPaciente().setTalla(((Double)vRowTemp.get(18)).floatValue());
                    oEpisodioMedico.getPaciente().setPeso(((Double)vRowTemp.get(19)).floatValue());
                    oEpisodioMedico.getSignosVitales().setTA((String)vRowTemp.get(20).toString());
                    oEpisodioMedico.getSignosVitales().setFC((String)vRowTemp.get(21).toString());
                    oEpisodioMedico.getSignosVitales().setFR((String)vRowTemp.get(22).toString());
                    oEpisodioMedico.getPaciente().setLugarNacimiento((String)vRowTemp.get(23).toString());
                    oEpisodioMedico.getPaciente().setEstadoCivil(new Parametrizacion());
                    oEpisodioMedico.getPaciente().getEstadoCivil().setValor((String)vRowTemp.get(24).toString());
                    oEpisodioMedico.getPaciente().setTipoSangre(new Parametrizacion());
                    oEpisodioMedico.getPaciente().getTipoSangre().setValor((String)vRowTemp.get(25).toString());
                    oEpisodioMedico.getPaciente().getTipoSangre().setClaveParametro((String)vRowTemp.get(27).toString());
                    oEpisodioMedico.getPaciente().getTipoSangre().setTipoParametro((String)vRowTemp.get(28).toString());
                    oEpisodioMedico.getPaciente().setFactorRH(new Parametrizacion());
                    oEpisodioMedico.getPaciente().getFactorRH().setValor((String)vRowTemp.get(26).toString());
                    oEpisodioMedico.getPaciente().getFactorRH().setClaveParametro((String)vRowTemp.get(29).toString());
                    oEpisodioMedico.getPaciente().getFactorRH().setTipoParametro((String)vRowTemp.get(30).toString());
                    oEpisodioMedico.getPaciente().getEstadoCivil().setClaveParametro((String)vRowTemp.get(31).toString());
                }
            }
            return oEpisodioMedico;
        }
        public Medico[] buscaMedicos(short nOpcion)throws Exception{
            if(nOpcion == 0)
                return buscaMedico((String) "SELECT * FROM buscaMedicoTriage();");
            if(nOpcion == 1)
                return buscaMedico((String) "SELECT * FROM buscaMedicoPartoGrama();");
            return null;
        }
        public Medico[] buscaMedico(String sQuery)throws Exception{
            Medico arrRet[] = null, oMedico = null;
            ArrayList rst = null;
            ArrayList <Medico> vObj = null;            
            int i = 0, nTam = 0;            
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }if(rst.isEmpty())
                return null;
            else{
                vObj = new ArrayList<Medico>();
                for(i = 0; i < rst.size(); i++){
                    oMedico = new Medico();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oMedico.setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                    oMedico.setNombres((String)vRowTemp.get(1).toString());
                    oMedico.setApPaterno((String)vRowTemp.get(2).toString());
                    oMedico.setApMaterno((String)vRowTemp.get(3).toString());
                    oMedico.setCedProf((String)vRowTemp.get(4).toString());
                    vObj.add(oMedico);
                }
                nTam = vObj.size();
                arrRet = new Medico[nTam];
                for(i = 0; i < nTam; i++)
                    arrRet[i] = vObj.get(i);
            }
            return arrRet;
        }
        public Parametrizacion[] buscaValorParametrizacion(short nOpcion)throws Exception{  
            if(nOpcion == 0)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaNivelEscolar();");
            if(nOpcion == 1)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaGrupoTriage();");
            if(nOpcion == 2)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaFactorTriage();");
            if(nOpcion == 3)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaSituacionTriage();");
            if(nOpcion == 4)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaPresentacionTriage();");
            if(nOpcion == 5)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaPelvisTriage();");
            if(nOpcion == 6)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaTrazoTriage();");
            if(nOpcion == 7)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaPronosticoTriage();");
            if(nOpcion == 8)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaMotivoConsultaPartoGrama();");
            if(nOpcion == 9)
                return buscaTablasParametrizacion((String) "SELECT * FROM consultaSitioPartoGrama();");
            if(nOpcion == 10)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaValoresPelvisPartoGrama();");
            if(nOpcion == 11)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscavariedadposicionforceps();");
            if(nOpcion == 12)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscacesarea();");
            if(nOpcion == 13)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscatipoalumbramiento();");
            if(nOpcion == 14)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscatiporeciennacido();");
            if(nOpcion == 15)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscasexoreciennacido();");
            if(nOpcion == 16)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscacordonrn();");
            if(nOpcion == 17)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscacordonrn1();");
            if(nOpcion == 18)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaTipoLegrado();");
            if(nOpcion == 19)
                return buscaTablasParametrizacion((String) "SELECT * FROM buscaTipocirugiapartograma()");
            return null;
        }
        public Parametrizacion[] buscaTablasParametrizacion(String sQuery)throws Exception{
            Parametrizacion arrRet[] = null , oParametrizacion = null;
            ArrayList rst = null;
            ArrayList<Parametrizacion> vObj = null;
            int i = 0, nTam = 0;
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }if(rst.isEmpty())
                return null;
            else{
                vObj = new ArrayList<Parametrizacion>();
                for(i = 0; i < rst.size(); i++){
                    oParametrizacion = new Parametrizacion();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oParametrizacion.setClaveParametro((String)vRowTemp.get(0));
                    oParametrizacion.setTipoParametro((String)vRowTemp.get(1));
                    oParametrizacion.setValor((String)vRowTemp.get(2));
                    vObj.add(oParametrizacion);
                }
                nTam = vObj.size();
                arrRet = new Parametrizacion[nTam];
                for(i = 0; i < nTam; i++)
                    arrRet[i] = vObj.get(i);
            }
            return arrRet;
        }
        public MetodoAnticonceptivo[] buscaMetodoTriage()throws Exception{
            MetodoAnticonceptivo arrRet[] = null;
            MetodoAnticonceptivo oMetodo = null;
            ArrayList rst = null;
            ArrayList<MetodoAnticonceptivo> vObj = null;
            String sQuery = "";
            int i = 0;            
            sQuery = "SELECT * FROM buscaMetodoAntTriage();";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }if(rst.isEmpty())
                return null;
            else{
                vObj = new ArrayList<MetodoAnticonceptivo>();
                for(i = 0; i < rst.size(); i++){
                    oMetodo = new MetodoAnticonceptivo();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oMetodo.setClave((String)vRowTemp.get(0).toString());
                    oMetodo.setDescripcion((String)vRowTemp.get(1).toString());
                    vObj.add(oMetodo);
                }               
                arrRet = new MetodoAnticonceptivo[vObj.size()];
                for(i = 0; i<vObj.size(); i++)
                    arrRet[i] = vObj.get(i);
            }
            return arrRet;
        }
        public int insertaTodo(NotaEmbarazo oNotaEmbarazo, EpisodioMedico oEpisodioMedico)throws Exception{
            ArrayList<String> rstQuery = null;
            int nRet = -1;
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBARAZO.INSERTARDATOS:ERROR, FALTAN DATOS");
            else{
                rstQuery = new ArrayList<String>();
                rstQuery.add(insertaDatosEmbarazo(oNotaEmbarazo));
                rstQuery.add(insertaAntecHeredoFamiliares(oNotaEmbarazo));
                rstQuery.add(insertaDatos(oNotaEmbarazo, oEpisodioMedico));
                rstQuery.add(insertantecPatologicos(oNotaEmbarazo, oEpisodioMedico));
                rstQuery.add(insertaGineco(oNotaEmbarazo));
                rstQuery.add(insertaSignosVitales(oNotaEmbarazo));
                if(rstQuery.size()>0){
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        nRet = oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();
                    }
                }
            }
            return nRet;
        }
        public int insertaModificacionesAnverso(NotaEmbarazo oNotaEmbarazo, EpisodioMedico oEpisodioMedico)throws Exception{
            ArrayList<String> rstQuery = null;
            int nRet = -1;
            if(oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente() == 0 || oNotaEmbarazo.getEpiMed().getClaveEpisodio() == 0 ||
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getNumero() == 0 || oNotaEmbarazo.getConsecutivo() == 0)
                throw new Exception("NOTAEMBARAZO.INSERTAMODIFICACIONES:ERROR,FALTANDATOS");
            else{
                rstQuery = new ArrayList<String>();
                rstQuery.add(this.queryModificacion(oNotaEmbarazo));
                rstQuery.add(this.insertaAntecHeredoFamiliares(oNotaEmbarazo));
                rstQuery.add(this.insertaDatos(oNotaEmbarazo, oEpisodioMedico));
                rstQuery.add(this.insertantecPatologicos(oNotaEmbarazo, oEpisodioMedico));
                rstQuery.add(this.insertaGineco(oNotaEmbarazo));
                rstQuery.add(this.insertaSignosVitales(oNotaEmbarazo));
                if(rstQuery.size() > 0){
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        nRet = oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();
                    }
                }                    
            }
            return nRet;
        }
        public int insertaReverso(NotaEmbarazo oNotaEmbarazo, ArrayList<DiagnosticoCIE10> arrDiagCie10, int nTam)throws Exception{
            ArrayList<String> rstQuery = null;
            ArrayList<String> rstTemporal = null;
            int nRet = -1;
            String sQuery = "";
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBARAZO.INSERTARDATOS:ERROR, FALTAN DATOS");
            else{
                rstQuery = new ArrayList<String>();
                rstQuery.add(armaQuery(oNotaEmbarazo));
                if(!arrDiagCie10.isEmpty()){
                    SegundaMitadEmbarazo oConsulta = new SegundaMitadEmbarazo();
                    rstTemporal = oConsulta.armaQueryPartoGrama(oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente(), oNotaEmbarazo.getEpiMed().getClaveEpisodio(), arrDiagCie10, nTam );
                    for (String rstTemporal1 : rstTemporal) {
                        rstQuery.add((String) rstTemporal1);
                    }
                }
                if(rstQuery.size() > 0){
                    oAD = new AccesoDatos();
                    if(oAD.conectar()){
                        nRet = oAD.ejecutarConsultaComando(rstQuery);
                        oAD.desconectar();
                    }
                }
            }
            return nRet;
        }
        public String queryModificacion(NotaEmbarazo oNotaEmbarazo){
            if(oNotaEmbarazo.getHoraCodigoRojo() != null)
                return this.modificaCodigoRojo(oNotaEmbarazo);
            else if(oNotaEmbarazo.getHoraCodigoAmarillo() != null)
                return this.modificaCodigoAmarillo(oNotaEmbarazo);
            else if(oNotaEmbarazo.getHoraCodigoVerde() != null)
                return this.modificaCodigoVerde(oNotaEmbarazo);
            return null;
        }
        
        public String modificaCodigoRojo(NotaEmbarazo oNotaEmbarazo){
            String sQuery = "";
            String valor0 = oNotaEmbarazo.isParoCardioDuranteEmb() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor1 = oNotaEmbarazo.isAltEdoConciencia() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor2 = oNotaEmbarazo.isInsufResp() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor3 = oNotaEmbarazo.isConvulsiones() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor4 = oNotaEmbarazo.isCefaleaIntensa() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor5 = oNotaEmbarazo.isPreeclampsiaSevera() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor6 = oNotaEmbarazo.isHemorragia() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor7 = oNotaEmbarazo.isChoqueHipovolemico() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor8 = oNotaEmbarazo.isSepsisEmbarazo() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor9 = oNotaEmbarazo.isProlapsoCordonUmb() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String hatencion = oNotaEmbarazo.getHoraAtnCodigo() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + oNotaEmbarazo.getHoraAtnCodigo() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String hatencionrojo = oNotaEmbarazo.getHoraCodigoRojo() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + oNotaEmbarazo.getHoraCodigoRojo() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String tarjeta = oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta() == 0 ? "null::INTEGER);" : oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta() + "::INTEGER);";
            return sQuery = "SELECT * FROM modificadatostriagecodigorojo(" + oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                        oNotaEmbarazo.getEpiMed().getClaveEpisodio() + "::BIGINT," +
                                                                        oNotaEmbarazo.getConsecutivo() + "::SMALLINT,'" +
                                                                        this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                        valor0 + valor1 + valor2 + valor3 + valor4 + valor5 + valor6 + valor7 +
                                                                        valor8 + valor9 + hatencion + hatencionrojo + tarjeta;
        }
        public String modificaCodigoAmarillo(NotaEmbarazo oNotaEmbarazo){
            String sQuery = "";
            String valor10 = oNotaEmbarazo.isTrabajoParto() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor11 = oNotaEmbarazo.isSalidaLiqSangVag() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor12 = oNotaEmbarazo.isPreeclampsiaLeve() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor13 = oNotaEmbarazo.isHipomotilidadFetal() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor14 = oNotaEmbarazo.isRupPrematuraMembranas() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor15 = oNotaEmbarazo.isEmbPatolCronica() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor16 = oNotaEmbarazo.isAborto() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor17 = oNotaEmbarazo.isPartoPretermino() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String hatencion = oNotaEmbarazo.getHoraAtnCodigo() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + oNotaEmbarazo.getHoraAtnCodigo() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String hatencionama = oNotaEmbarazo.getHoraCodigoAmarillo() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + oNotaEmbarazo.getHoraCodigoAmarillo() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String tarjeta = oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta() == 0 ? "null::INTEGER);" : oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta() + "::INTEGER);";
            return sQuery = "SELECT * FROM modificadatostriagecodigoamarillo(" + oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                                oNotaEmbarazo.getEpiMed().getClaveEpisodio() + "::BIGINT," +
                                                                                oNotaEmbarazo.getConsecutivo() + "::SMALLINT,'" +
                                                                                this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                                valor10 + valor11 + valor12 + valor13 + valor14 + valor15 + valor16 + valor17 +
                                                                                hatencion + hatencionama + tarjeta;
        }
        public String modificaCodigoVerde(NotaEmbarazo oNotaEmbarazo){
            String sQuery = "";
            String valor18 = oNotaEmbarazo.isEmbMenor3SDGsinTrabParto() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor19 = oNotaEmbarazo.isEmbAsintomatico() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor20 = oNotaEmbarazo.isPuerperioFisiologico() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String valor21 = oNotaEmbarazo.isPuerperioQxsinCompl() ? "'1'::CHARACTER," : "'0'::CHARACTER,";
            String hatencion = oNotaEmbarazo.getHoraAtnCodigo() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + oNotaEmbarazo.getHoraAtnCodigo() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String hatencionverde = oNotaEmbarazo.getHoraCodigoVerde() == null ? "null::TIMESTAMP WITHOUT TIME ZONE," : "'" + oNotaEmbarazo.getHoraCodigoVerde() + "'::TIMESTAMP WITHOUT TIME ZONE,";
            String tarjeta = oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta() == 0 ? "null::INTEGER);" : oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta() + "::INTEGER);";
            return sQuery = "SELECT * FROM modificadatostriagecodigoverde(" + oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                            oNotaEmbarazo.getEpiMed().getClaveEpisodio() + "::BIGINT," +
                                                                            oNotaEmbarazo.getConsecutivo() + "::SMALLINT,'" +
                                                                            this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                            valor18 + valor19 + valor20 + valor21 + hatencion + hatencionverde + tarjeta;
        }
        
        public String insertaDatosEmbarazo(NotaEmbarazo oNotaEmbarazo)throws Exception{ 
            String sQuery = "";
            if(oNotaEmbarazo.getHoraCodigoRojo() != null){                
                String valor0 = oNotaEmbarazo.isParoCardioDuranteEmb() ? "1" :"0";
                String valor1 = oNotaEmbarazo.isAltEdoConciencia() ? "1" :"0";
                String valor2 = oNotaEmbarazo.isInsufResp() ? "1" : "0";
                String valor3 = oNotaEmbarazo.isConvulsiones() ? "1" : "0";
                String valor4 = oNotaEmbarazo.isCefaleaIntensa() ? "1" : "0";
                String valor5 = oNotaEmbarazo.isPreeclampsiaSevera() ? "1" : "0";
                String valor6 = oNotaEmbarazo.isHemorragia() ? "1" : "0";
                String valor7 = oNotaEmbarazo.isChoqueHipovolemico() ? "1" : "0";
                String valor8 = oNotaEmbarazo.isSepsisEmbarazo() ? "1" : "0";                
                String valor9 = oNotaEmbarazo.isProlapsoCordonUmb() ? "1" : "0";
                sQuery = insertaCodigos(valor0, valor1, valor2, valor3, valor4, valor5, valor6, valor7, valor8, valor9, oNotaEmbarazo);
                            }else{
                if(oNotaEmbarazo.getHoraCodigoAmarillo() != null){
                    String valor10 = oNotaEmbarazo.isTrabajoParto() ? "1" : "0";
                    String valor11 = oNotaEmbarazo.isSalidaLiqSangVag() ? "1" : "0";
                    String valor12 = oNotaEmbarazo.isPreeclampsiaLeve() ? "1" : "0";
                    String valor13 = oNotaEmbarazo.isHipomotilidadFetal() ? "1" : "0";
                    String valor14 = oNotaEmbarazo.isRupPrematuraMembranas() ? "1" : "0";
                    String valor15 = oNotaEmbarazo.isEmbPatolCronica() ? "1" : "0";
                    String valor16 = oNotaEmbarazo.isAborto() ? "1" : "0";
                    String valor17 = oNotaEmbarazo.isPartoPretermino() ? "1" : "0";
                    sQuery = insertaCodigos(valor10, valor11, valor12, valor13, valor14, valor15, valor16, valor17, oNotaEmbarazo);
                    
                }else{
                    if(oNotaEmbarazo.getHoraCodigoVerde() != null){
                        String valor18 = oNotaEmbarazo.isEmbMenor3SDGsinTrabParto() ? "1" : "0";
                        String valor19 = oNotaEmbarazo.isEmbAsintomatico() ? "1" : "0";
                        String valor20 = oNotaEmbarazo.isPuerperioFisiologico() ? "1" : "0";
                        String valor21 = oNotaEmbarazo.isPuerperioQxsinCompl() ? "1" : "0";                       
                        sQuery = insertaCodigos(valor18, valor19, valor20, valor21, oNotaEmbarazo);
                    }
                }
            }
            return sQuery;
        }
        
        public String insertaCodigos(String valor18, String valor19, String valor20, String valor21, NotaEmbarazo oNotaEmbarazo)throws Exception{
            String sQuery = "";
            if(oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()<0)
                throw new Exception("NOTA EMBARAZO.INSERTAR:ERROR FALTAN DATOS");
            else{
                sQuery="SELECT * FROM insertaDatosTriageCodigoVerde("+oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT"+","+
                                                                      oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT"+","+
                                                                      "'" + this.sUsuarioFirmado +"'::CHARACTER VARYING,"+
                                                                        "'"+valor18+"'"+"::character"+","+
                                                                        "'"+valor19+"'"+"::character"+","+
                                                                        "'"+valor20+"'"+"::character"+","+
                                                                        "'"+valor21+"'"+"::character"+","+
                                                                        "'"+oNotaEmbarazo.getHoraAtnCodigo()+"'::timestamp without time zone"+","+
                                                                        "'"+oNotaEmbarazo.getHoraCodigoVerde()+"'::timestamp without time zone"+","+
                                                                        oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta()+"::integer);";                
                }
            return sQuery;
        }
        public String insertaCodigos(String valor10, String valor11, String valor12, String valor13, String valor14, String valor15, String valor16, String valor17, NotaEmbarazo oNotaEmbarazo)throws Exception{
            String sQuery = "";
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBARAZO.INSERTAR:ERROR FALATAN DATOS");
            else{
                sQuery="SELECT * FROM insertaDatosTriageCodigoAmarillo("+oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT"+","+
                                                                      oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT"+","+
                                                                      "'" + this.sUsuarioFirmado +"'::CHARACTER VARYING,"+
                                                                        "'"+valor10+"'"+"::character"+","+
                                                                        "'"+valor11+"'"+"::character"+","+
                                                                        "'"+valor12+"'"+"::character"+","+
                                                                        "'"+valor13+"'"+"::character"+","+
                                                                        "'"+valor14+"'"+"::character"+","+
                                                                        "'"+valor15+"'"+"::character"+","+
                                                                        "'"+valor16+"'"+"::character"+","+
                                                                        "'"+valor17+"'"+"::character"+","+
                                                                        "'"+oNotaEmbarazo.getHoraAtnCodigo()+"'::timestamp without time zone"+","+
                                                                        "'"+oNotaEmbarazo.getHoraCodigoAmarillo()+"'::timestamp without time zone"+","+
                                                                        oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta()+"::integer);";
                
                
            }
            return sQuery;
        }
        public String insertaCodigos( String valor0, String valor1, String valor2, String valor3, String valor4, String valor5, String valor6, String valor7, String valor8, String valor9, NotaEmbarazo oNotaEmbarazo)throws Exception{
            ArrayList rst = null;
            int nRet = -1;
            String sQuery = "";
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBARAZO.ISERTAR:ERROR FALTAN DATOS");
            else{
                sQuery="SELECT * FROM insertaDatosTriageCodigoRojo("+oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT"+","+
                                                                      oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT"+","+
                                                                      "'" + this.sUsuarioFirmado +"'::CHARACTER VARYING,"+
                                                                        "'"+valor0+"'"+"::character"+","+
                                                                        "'"+valor1+"'"+"::character"+","+
                                                                        "'"+valor2+"'"+"::character"+","+
                                                                        "'"+valor3+"'"+"::character"+","+
                                                                        "'"+valor4+"'"+"::character"+","+
                                                                        "'"+valor5+"'"+"::character"+","+
                                                                        "'"+valor6+"'"+"::character"+","+
                                                                        "'"+valor7+"'"+"::character"+","+
                                                                        "'"+valor8+"'"+"::character"+","+
                                                                        "'"+valor9+"'"+"::character"+","+
                                                                        "'"+oNotaEmbarazo.getHoraAtnCodigo()+"'::timestamp without time zone"+","+
                                                                        "'"+oNotaEmbarazo.getHoraCodigoRojo()+"'::timestamp without time zone"+","+
                                                                        oNotaEmbarazo.getMedicoSupervisor().getNoTarjeta()+"::integer);";                
                
            }
            return sQuery;
        }
        public String insertaAntecHeredoFamiliares(NotaEmbarazo oNotaEmbarazo)throws Exception{
            String sQuery = "";
            String padre = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getPadre() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getPadre().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getPadre().toUpperCase() + "'::CHARACTER VARYING,";
            String madre = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getMadre() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getMadre().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getMadre().toUpperCase() + "'::CHARACTER VARYING,";
            String abueloMaterno = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbueloMaterno() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbueloMaterno().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbueloMaterno().toUpperCase() + "'::CHARACTER VARYING,";
            String abuelaMaterna = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbuelaMaterna() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbuelaMaterna().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbuelaMaterna().toUpperCase() + "'::CHARACTER VARYING,";
            String abueloPaterno = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbueloPaterno() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbueloPaterno().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbueloPaterno().toUpperCase() + "'::CHARACTER VARYING,";
            String abuelaPaterna = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbuelaPaterna() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbuelaPaterna().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getAbuelaPaterna().toUpperCase() + "'::CHARACTER VARYING,";
            String otro = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getOtros() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getOtros().isEmpty() ? "null::CHARACTER VARYING);" : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().getOtros() + "'::CHARACTER VARYING);";   
            if(oNotaEmbarazo ==  null)
                throw new Exception("NOTA EMBARAZO.INSERTAR:ERROR FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM insertaAntecHeredoFamiliaresTriage("+
                                            oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT,"+
                                            oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT,"+
                                            oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getNumero()+"::INTEGER,"+
                                            oNotaEmbarazo.getConsecutivo() + "::SMALLINT,"+
                                            "'" + this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                            padre + madre + abueloMaterno + abuelaMaterna + abueloPaterno  +abuelaPaterna + otro;                
               
                }
            
            return sQuery;
        }
        public String insertaDatos(NotaEmbarazo oNotaEmbarazo, EpisodioMedico oEpisodioMedico)throws Exception{
            String sQuery = "";
            String tabaquismo = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getTabaquismo() ? "1" : "0";
            String alcoholismo = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getAlcoholismo() ? "1" : "0";
            String drogas = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getConsumeDrogas() ? "1" : "0";
            String otro = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOtro() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOtro().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOtro().toUpperCase() + "'::CHARACTER VARYING,";
            String lugarNacimiento = oEpisodioMedico.getPaciente().getLugarNacimiento() == null || oEpisodioMedico.getPaciente().getLugarNacimiento().isEmpty() ? "'" + oNotaEmbarazo.getEpiMed().getPaciente().getLugarNacimiento().toUpperCase() + "'::CHARACTER VARYING," : "'" + oEpisodioMedico.getPaciente().getLugarNacimiento() + "'::CHARACTER VARYING,";    
            String estadoCivil = oNotaEmbarazo.getEpiMed().getPaciente().getEdoCivilStr() == null || oNotaEmbarazo.getEpiMed().getPaciente().getEdoCivilStr().isEmpty() ? "null::CHARACTER VARYING);" : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getEdoCivilStr().trim().toUpperCase() + "'::CHARACTER VARYING);";             
            String escolaridad = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getEscolaridad().toUpperCase() + "'::CHARACTER VARYING,"; 
            String religion = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getReligion() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getReligion().isEmpty() ? "null::CHARACTER VARYING," :  "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getReligion().toUpperCase() + "'::CHARACTER VARYING,";
            String ocupacion = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOcupacion() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOcupacion().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().getOcupacion().toUpperCase() + "'::CHARACTER VARYING,"; 
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBARAZO.INSERTAR:ERROR FALTAN DATOS");
            else{
                sQuery ="SELECT * FROM insertaAntecNoPatologicosTriage("+oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT,"+
                                                                        oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT,"+
                                                                        oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getNumero()+"::integer,"+
                                                                        oNotaEmbarazo.getConsecutivo() + "::SMALLINT,'"+
                                                                        this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                        escolaridad + religion + ocupacion +
                                                                        "'"+tabaquismo+"'::character,'"+alcoholismo+"'::character,'"+drogas+"'::character,"+
                                                                        otro+
                                                                        lugarNacimiento+
                                                                        estadoCivil;               
            }            
            return sQuery;
        }        
        public String insertantecPatologicos(NotaEmbarazo oNotaEmbarazo, EpisodioMedico oEpisodio)throws Exception{
            String sQuery = "";
            String diabetico = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getEsDiabetico() ? "1" :"0";
            String hipertenso = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getEsHipertenso() ? "1" :"0";
            String oncologico = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getOncologico() ? "1" : "0";
            String alergia = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getAlergias() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getAlergias().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getAlergias().toUpperCase() + "'::CHARACTER VARYING,";
            String tsangre = oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().getClaveParametro() == null || oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().getClaveParametro().isEmpty() ? "'" + oEpisodio.getPaciente().getTipoSangre().getClaveParametro() + "'::CHARACTER(3)," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().getClaveParametro().substring(0,3).toUpperCase() + "'::CHARACTER(3),";
            String vsangre = oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().getClaveParametro() == null || oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().getClaveParametro().isEmpty() ? "'" + oEpisodio.getPaciente().getTipoSangre().getTipoParametro() + "'::CHARACTER(3)," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getTipoSangre().getClaveParametro().substring(3,4).toUpperCase() + "'::CHARACTER(3),";
            String trh = oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().getClaveParametro() == null || oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().getClaveParametro().isEmpty() ? "'" + oEpisodio.getPaciente().getFactorRH().getClaveParametro() + "'::CHARACTER(3)," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().getClaveParametro().substring(0,3).toUpperCase() + "'::CHARACTER(3),";
            String vrh = oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().getClaveParametro() == null || oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().getClaveParametro().isEmpty() ? "'" + oEpisodio.getPaciente().getFactorRH().getTipoParametro() + "'::CHARACTER(3)," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getFactorRH().getClaveParametro().substring(3,4).toUpperCase() + "'::CHARACTER(3),";
            String qx = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getQuirurgico() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getQuirurgico().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getQuirurgico().toUpperCase() + "'::CHARACTER VARYING,";
            String transfusion = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getTransfusion() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getTransfusion().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getTransfusion().toUpperCase() + "'::CHARACTER VARYING,";
            String cardioVasculares = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getCardioVasculares() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getCardioVasculares().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getCardioVasculares() + "'::CHARACTER VARYING,";
            String otro = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getOtro() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getOtro().isEmpty() ? "null::CHARACTER VARYING);" : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().getOtro() + "'::CHARACTER VARYING);";
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBARAZO.INSERTAR: ERORR FALTAN DATOS");
            else{
                sQuery="SELECT * FROM insertaAntecPatologicosTriage("+oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT,"+
                                                                    oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT,"+
                                                                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getNumero()+"::INTEGER,"+
                                                                    oNotaEmbarazo.getConsecutivo() + "::SMALLINT,'"+
                                                                    this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                    tsangre + vsangre + trh + vrh + alergia + qx + transfusion + cardioVasculares +
                                                                    "'"+diabetico+"'::CHARACTER VARYING,'"+ hipertenso+"'::CHARACTER VARYING,'"+
                                                                    oncologico+"'::CHARACTER," + otro;
            }
            return sQuery;
        }
        public String insertaGineco(NotaEmbarazo oNotaEmbarazo)throws Exception{
            String sQuery = "";
            String ciclo = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getCiclo() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getCiclo().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getCiclo().toUpperCase() + "'::CHARACTER VARYING,";  
            String pf = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getDescripcion() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getDescripcion().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getMetodoAnticonceptivo().getDescripcion().toUpperCase() + "'::CHARACTER VARYING,";
            String fechaMensConv = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion() == null ? "null::DATE," : "'" + fultmens.format(oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getUltimaMenstruacion())+"'::DATE,";
            String fechaparto = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getFechaPosibleParto() == null ? "null::DATE," : "'" + fultmens.format(oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getFechaPosibleParto())+"'::DATE,"; 
            String doc = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getDoc() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getDoc().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getDoc().toUpperCase() + "'::CHARACTER VARYING,";
            String ets = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getETS() == null || oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getETS().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getETS().toUpperCase() + "'::CHARACTER VARYING,";
            String controlprenatal = oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getControlPrenatal() ? "1" : "0";
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBRAZO.INSERTAR: ERROR FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM insertaAntecGinecoTriage("+oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT,"+
                                                                oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT,"+
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getNumero()+"::INTEGER,"+
                                                                oNotaEmbarazo.getConsecutivo() + "::SMALLINT,'" +
                                                                this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getMenarca()+"::SMALLINT," +
                                                                ciclo +
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getIVSA()+"::SMALLINT,"+
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getGestaciones()+"::INTEGER,"+
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getPartos()+"::INTEGER,"+
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getCesareas()+"::SMALLINT,"+
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getAbortos()+"::INTEGER,"+
                                                                pf + fechaMensConv +
                                                                oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().getParejasSexuales()+"::SMALLINT,"+
                                                                fechaparto + doc + ets + "'" + controlprenatal+"'::CHARACTER(1));";
            }
            return sQuery;
        }
        public String armaQuery(NotaEmbarazo oNotaEmbarazo) throws Exception{
            String sQuery = "";
            String sUterino = oNotaEmbarazo.getFondoUterino() == null || oNotaEmbarazo.getFondoUterino().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getFondoUterino().toUpperCase() + "'::CHARACTER VARYING,";
            String sClaveSit = oNotaEmbarazo.getSituacionProducto().getClaveParametro() == null || oNotaEmbarazo.getSituacionProducto().getClaveParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getSituacionProducto().getClaveParametro().toUpperCase() + "'::CHARACTER(3),";
            String sTipoSit = oNotaEmbarazo.getSituacionProducto().getTipoParametro() == null || oNotaEmbarazo.getSituacionProducto().getTipoParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getSituacionProducto().getTipoParametro().toUpperCase() + "'::CHARACTER(3),"; 
            String sClavePresentacion = oNotaEmbarazo.getPresentacion().getClaveParametro() == null || oNotaEmbarazo.getPresentacion().getClaveParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getPresentacion().getClaveParametro().toUpperCase() + "'::CHARACTER(3),";
            String sTipoPresentacion = oNotaEmbarazo.getPresentacion().getTipoParametro() == null || oNotaEmbarazo.getPresentacion().getTipoParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getPresentacion().getTipoParametro().toUpperCase() + "'::CHARACTER(3),";
            String abocado = getAbocado() ? "1" : "0";
            String encajado = getEncajado() ? "1" : "0";
            String sFcf = oNotaEmbarazo.getFCF() == null || oNotaEmbarazo.getFCF().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getFCF() + "'::CHARACTER VARYING,";
            String sometria = oNotaEmbarazo.getSometria() == null || oNotaEmbarazo.getSometria().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getSometria().toUpperCase() + "'::CHARACTER VARYING,"; 
            String la = oNotaEmbarazo.getLA() == null || oNotaEmbarazo.getLA().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getLA().toUpperCase() + "'::CHARACTER VARYING,";
            String placenta = oNotaEmbarazo.getPlacenta() == null || oNotaEmbarazo.getPlacenta().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getPlacenta().toUpperCase() + "'::CHARACTER VARYING,";
            String observacionesUltra = oNotaEmbarazo.getObservacionesUltrasonido() == null || oNotaEmbarazo.getObservacionesUltrasonido().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getObservacionesUltrasonido().toUpperCase() + "'::CHARACTER VARYING,";
            String plano = oNotaEmbarazo.getPlano() == null || oNotaEmbarazo.getPlano().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getPlano().toUpperCase() + "'::CHARACTER VARYING,";
            String sClaveAmnios = oNotaEmbarazo.getAmnios().getClaveParametro() == null || oNotaEmbarazo.getAmnios().getClaveParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getAmnios().getClaveParametro().toUpperCase() + "'::CHARACTER(3),";
            String sTipoAmnios = oNotaEmbarazo.getAmnios().getTipoParametro() == null || oNotaEmbarazo.getAmnios().getTipoParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getAmnios().getTipoParametro().toUpperCase() + "'::CHARACTER(3),"; 
            String sClaveLiquido = oNotaEmbarazo.getLiqAmniotico().getClaveParametro() == null || oNotaEmbarazo.getLiqAmniotico().getClaveParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getLiqAmniotico().getClaveParametro().toUpperCase() + "'::CHARACTER(3),";
            String sTipoLiquido = oNotaEmbarazo.getLiqAmniotico().getTipoParametro() == null || oNotaEmbarazo.getLiqAmniotico().getTipoParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getLiqAmniotico().getTipoParametro().toUpperCase() + "'::CHARACTER(3),";
            String sClavePelvis = oNotaEmbarazo.getPelvis().getClaveParametro() == null || oNotaEmbarazo.getPelvis().getClaveParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getPelvis().getClaveParametro().toUpperCase() + "'::CHARACTER(3),";
            String sTipoPelvis = oNotaEmbarazo.getPelvis().getTipoParametro() == null || oNotaEmbarazo.getPelvis().getTipoParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getPelvis().getTipoParametro().toUpperCase() +"'::CHARACTER(3),";  
            String sClaveCardio = oNotaEmbarazo.getTrazoCardio().getClaveParametro() == null || oNotaEmbarazo.getTrazoCardio().getClaveParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getTrazoCardio().getClaveParametro().toUpperCase() + "'::CHARACTER(3),";
            String sTipoCardio = oNotaEmbarazo.getTrazoCardio().getTipoParametro() == null || oNotaEmbarazo.getTrazoCardio().getTipoParametro().isEmpty() ? "null::CHARACTER(3)," : "'" + oNotaEmbarazo.getTrazoCardio().getTipoParametro().toUpperCase() + "'::CHARACTER(3),";
            String obsCardio = oNotaEmbarazo.getObsCardio() == null || oNotaEmbarazo.getObsCardio().isEmpty() ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getObsCardio().toUpperCase() + "'::CHARACTER VARYING,";
            String padAct = oNotaEmbarazo.getResumenInter() == null || oNotaEmbarazo.getResumenInter().isEmpty() ? "null::TEXT," : "'" + oNotaEmbarazo.getResumenInter().toUpperCase() + "'::TEXT,";
            sPronostico = oNotaEmbarazo.getPronostico() == null || oNotaEmbarazo.getPronostico().isEmpty() ? "null::TEXT," : "'" + oNotaEmbarazo.getPronostico().toUpperCase() + "'::TEXT,";
            String indTerapeutica = oNotaEmbarazo.getIndicacionTer() == null || oNotaEmbarazo.getIndicacionTer().isEmpty() ? "null::TEXT," : "'" + oNotaEmbarazo.getIndicacionTer().toUpperCase() + "'::TEXT,";
            String medelab = this.oPersonal.getNoTarjeta() == 0 ? "null::INTEGER," : this.oPersonal.getNoTarjeta() + "::INTEGER,";
            String medsuperviso = oNotaEmbarazo.getEpiMed().getMedicoTratante().getNoTarjeta() == 0 ? "null::INTEGER);" : oNotaEmbarazo.getEpiMed().getMedicoTratante().getNoTarjeta() + "::INTEGER);";
            if(oNotaEmbarazo == null)
                throw new Exception("NOTA EMBRAZO.INSERTAR: ERROR FALTAN DATOS");
            else{
                sQuery = "SELECT * FROM insertaReversoTriage("+oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente()+"::BIGINT,"+
                                                                oNotaEmbarazo.getEpiMed().getClaveEpisodio()+"::BIGINT,"+
                                                                + oNotaEmbarazo.getConsecutivo() + "::SMALLINT,'" +
                                                                this.sUsuarioFirmado + "'::CHARACTER VARYING," +
                                                                sUterino + sClaveSit + sTipoSit + sClavePresentacion + sTipoPresentacion +
                                                                "'" + abocado + "'::CHARACTER,'" + encajado + "'::CHARACTER," + sFcf +
                                                                sometria + la + placenta + observacionesUltra + oNotaEmbarazo.getDilatacion() + "::SMALLINT," +
                                                                oNotaEmbarazo.getBorramiento() + "::NUMERIC," + plano + sClaveAmnios + sTipoAmnios +
                                                                sClaveLiquido + sTipoLiquido + sClavePelvis + sTipoPelvis + sClaveCardio + sTipoCardio +
                                                                obsCardio + padAct + sPronostico + indTerapeutica + medelab + medsuperviso;
            }            
            return sQuery;
        }
        public ArrayList armaQueryDiagnostico(ArrayList<DiagnosticoCIE10> arrCie10, NotaEmbarazo oNotaEmbarazo)throws Exception{
            ArrayList rstQuery = null;
            String sQuery = "";            
            Date fecha = new Date();
            DateFormat fechaDiag = new SimpleDateFormat("dd/MM/yyyy");
            String fechadx = fechaDiag.format(fecha);
            if(arrCie10 == null)
                throw new Exception("NOTAMEDICAEMBARAZO.INSERTARDIAGNOSTICO:ERROR, FALTANDATOS");
            else{
                rstQuery = new ArrayList();
                for(int i = 0 ; i < arrCie10.size() ; i ++){                                    
                    sQuery = "SELECT * FROM insertaDiagnosticosTriage("+ oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                        oNotaEmbarazo.getEpiMed().getClaveEpisodio() + "::BIGINT,";
                    if(i == 0)
                        sQuery += "'T0600'::CHARACTER(5),";
                    if(i == 1)
                        sQuery += "'T0601'::CHARACTER(5),";
                    if(i == 2)
                        sQuery += "'T0602'::CHARACTER(5),";
                    if(i == 3)
                        sQuery += "'T0603'::CHARACTER(5),";
                    if(i == 4)
                        sQuery += "'T0604'::CHARACTER(5),";
                    if(i == 5)
                        sQuery += "'T0605'::CHARACTER(5),";
                    if(i == 6)
                        sQuery += "'T0606'::CHARACTER(5),";
                    sQuery += "'" +((String) arrCie10.get(i).getClave()) + "'::CHARACTER(6),'" + fechadx + "'::DATE,'C'::CHARACTER,'R'::CHARACTER);";
                    rstQuery.add(sQuery);
                }
            }
            return rstQuery;
        }
        public NotaEmbarazo[] buscaPacientesValoracionTriage()throws Exception{
            NotaEmbarazo arrRet[] = null;
            NotaEmbarazo oNotaEmbarazo = null;
            ArrayList rst = null;
            String sQuery = "";
            String nombre = "";
            String apaterno = "";
            String amaterno = "";
            String nexp = "null";
            if(this.getEpiMed().getPaciente().getOpcionUrg() == 0){
                nombre = this.getEpiMed().getPaciente().getNombres();
                apaterno = this.getEpiMed().getPaciente().getApPaterno();
                amaterno = this.getEpiMed().getPaciente().getApMaterno();
            }else{
                nombre = "";
                apaterno = "";
                amaterno = "";
                nexp = this.getEpiMed().getPaciente().getExpediente().getNumero() + "";                
            }
            if(nexp.compareTo("null") == 0)
                sQuery = "SELECT * FROM buscapacientevaloraciontriage('" + nombre + "'::CHARACTER VARYING,'" + apaterno +"'::CHARACTER VARYING, '" + amaterno + "'::CHARACTER VARYING," + nexp + "::INTEGER);";
            else
                sQuery = "SELECT * FROM buscapacientevaloraciontriage('" + nombre + "'::CHARACTER VARYING,'" + apaterno +"'::CHARACTER VARYING, '" + amaterno + "'::CHARACTER VARYING," + Integer.parseInt(nexp) + "::INTEGER);";
            //System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(!rst.isEmpty()){
                arrRet = new NotaEmbarazo[rst.size()];
                for(int i = 0; i < rst.size(); i++){
                    oNotaEmbarazo = new NotaEmbarazo();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNotaEmbarazo.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(2).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(3).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(4).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().setSexoP((String)vRowTemp.get(5).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(7));
                    arrRet[i] = oNotaEmbarazo;
                }
            }
            return arrRet;
        }
        public NotaEmbarazo[] buscaPacienteDatos()throws Exception{
            NotaEmbarazo arrRet[] = null, oNotaEmbarazo = null;
            ArrayList rst = null;
            ArrayList<NotaEmbarazo> vObj = null;
            String sQuery = "", nombre = "", aPaterno = "", aMaterno = "", numExp = "";
            int i = 0, nTam = 0;
            /*if(this.getEpiMed().getPaciente().getOpcionUrg() == 0){
                nombre = this.getEpiMed().getPaciente().getNombres();
                aPaterno = this.getEpiMed().getPaciente().getApPaterno();
                aMaterno = this.getEpiMed().getPaciente().getApMaterno();
                numExp = "null";
            }else{
                nombre = "";
                aPaterno = "";
                aMaterno = "";
                numExp = this.getEpiMed().getPaciente().getExpediente().getNumero()+"";
            }
            if(numExp.compareTo("null") == 0)
                sQuery = "SELECT * FROM buscaDatosPacienteTriage('" + nombre +"'," +
                                                                "'" + aPaterno + "'," +
                                                                "'" + aMaterno + "'," +
                                                                      numExp + ");";
            else
                sQuery = "SELECT * FROM buscaDatosPacienteTriage('"+ nombre +"'," +
                                                                "'" + aPaterno + "'," +
                                                                "'" + aMaterno + "'," +
                                                                      Integer.parseInt(numExp) + ");";*/
            sQuery = "SELECT * FROM buscadatospacientetriage(" + this.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                                                                this.getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT);";
            //System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() > 0){
                arrRet = new NotaEmbarazo[rst.size()];
                for( i = 0; i < rst.size(); i++){
                    oNotaEmbarazo = new NotaEmbarazo();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oNotaEmbarazo.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(2)).intValue());
                    oNotaEmbarazo.setConsecutivo(((Double)vRowTemp.get(3)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(4));
                    oNotaEmbarazo.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(5).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(6).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(7).toString());
                    oNotaEmbarazo.setMaxConsecutivo(((Double)vRowTemp.get(8)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().setSexoP((String)vRowTemp.get(9).toString());
                    arrRet[i] = oNotaEmbarazo;
                }
            }
            return arrRet;
        }        
        public NotaEmbarazo datosPaciente(long fpaciente, long clavep, long expediente, int consecutivo)throws Exception{
            NotaEmbarazo oNotaEmbarazo = null;
            oNotaEmbarazo = new NotaEmbarazo();
            ArrayList rst = null;
            ArrayList rst1 = null;
            String sQuery = "";
            String sQuery1 = "";
            int i = 0;            
            if (fpaciente == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
            else{
                if(expediente == 0)
                    sQuery = "SELECT * FROM consultaDatosAnversoTriage(" + fpaciente + "::BIGINT," + 
                                                                            clavep + "::BIGINT," + 
                                                                            consecutivo + "::SMALLINT);";
                else
                    sQuery ="SELECT * FROM consultaDatosAnversoTriage1(" + fpaciente + "::BIGINT, " +
                                                                            clavep + "::BIGINT, " + 
                                                                            consecutivo + "::SMALLINT, " + 
                                                                            expediente + "::bigint);";                    
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return oNotaEmbarazo = null;
                else{                    
                    ArrayList vRowTemp = (ArrayList)rst.get(i);                    
                    boolean codrojo0 = (vRowTemp.get(1).toString().compareTo("1") == 0);             
                    boolean codrojo1 = (vRowTemp.get(2).toString().compareTo("1") == 0);
                    boolean codrojo2 = (vRowTemp.get(3).toString().compareTo("1") == 0);
                    boolean codrojo3 = (vRowTemp.get(4).toString().compareTo("1") == 0);
                    boolean codrojo4 = (vRowTemp.get(5).toString().compareTo("1") == 0);
                    boolean codrojo5 = (vRowTemp.get(6).toString().compareTo("1") == 0);
                    boolean codrojo6 = (vRowTemp.get(7).toString().compareTo("1") == 0);
                    boolean codrojo7 = (vRowTemp.get(8).toString().compareTo("1") == 0);
                    boolean codrojo8 = (vRowTemp.get(9).toString().compareTo("1") == 0);
                    boolean codrojo9 = (vRowTemp.get(10).toString().compareTo("1") == 0);
                    boolean codama0 = (vRowTemp.get(11).toString().compareTo("1") == 0);
                    boolean codama1 = (vRowTemp.get(12).toString().compareTo("1") == 0);
                    boolean codama2 = (vRowTemp.get(13).toString().compareTo("1") == 0);
                    boolean codama3 = (vRowTemp.get(14).toString().compareTo("1") == 0);
                    boolean codama4 = (vRowTemp.get(15).toString().compareTo("1") == 0);
                    boolean codama5 = (vRowTemp.get(16).toString().compareTo("1") == 0);
                    boolean codama6 = (vRowTemp.get(17).toString().compareTo("1") == 0);
                    boolean codama7 = (vRowTemp.get(18).toString().compareTo("1") == 0);
                    boolean codver0 = (vRowTemp.get(19).toString().compareTo("1") == 0);
                    boolean codver1 = (vRowTemp.get(20).toString().compareTo("1") == 0);
                    boolean codver2 = (vRowTemp.get(21).toString().compareTo("1") == 0);
                    boolean codver3 = (vRowTemp.get(22).toString().compareTo("1") == 0);
                    if(codrojo0 == true || codrojo1 == true || codrojo2 == true || codrojo3 == true || codrojo4 == true ||
                            codrojo5 == true || codrojo6 == true || codrojo7 == true || codrojo8 == true || codrojo9 == true){
                        oNotaEmbarazo.setHoraCodigoRojo((Date)vRowTemp.get(0));
                        oNotaEmbarazo.setParoCardioDuranteEmb(codrojo0);
                        oNotaEmbarazo.setAltEdoConciencia(codrojo1);
                        oNotaEmbarazo.setInsufResp(codrojo2);
                        oNotaEmbarazo.setConvulsiones(codrojo3);
                        oNotaEmbarazo.setCefaleaIntensa(codrojo4);
                        oNotaEmbarazo.setPreeclampsiaSevera(codrojo5);
                        oNotaEmbarazo.setHemorragia(codrojo6);
                        oNotaEmbarazo.setChoqueHipovolemico(codrojo7);
                        oNotaEmbarazo.setSepsisEmbarazo(codrojo8);
                        oNotaEmbarazo.setProlapsoCordonUmb(codrojo9);
                    }else{
                        if(codama0 == true || codama1 == true || codama2 == true || codama3 == true ||
                                codama4 == true || codama5 == true || codama6 == true || codama7 == true){
                            oNotaEmbarazo.setHoraCodigoAmarillo((Date)vRowTemp.get(0));
                            oNotaEmbarazo.setTrabajoParto(codama0);
                            oNotaEmbarazo.setSalidaLiqSangVag(codama1);
                            oNotaEmbarazo.setPreeclampsiaLeve(codama2);
                            oNotaEmbarazo.setHipomotilidadFetal(codama3);
                            oNotaEmbarazo.setRupPrematuraMembranas(codama4);
                            oNotaEmbarazo.setEmbPatolCronica(codama5);
                            oNotaEmbarazo.setAborto(codama6);
                            oNotaEmbarazo.setPartoPretermino(codama7);
                        }else{
                            if(codver0 == true || codver1 == true || codver2 == true ||
                                    codver3 == true){
                                oNotaEmbarazo.setHoraCodigoVerde((Date)vRowTemp.get(0));
                                oNotaEmbarazo.setEmbMenor3SDGsinTrabParto(codver0);
                                oNotaEmbarazo.setEmbAsintomatico(codver1);
                                oNotaEmbarazo.setPuerperioFisiologico(codver2);
                                oNotaEmbarazo.setPuerperioQxsinCompl(codver3);
                            }
                        }
                    }
                    oNotaEmbarazo.setHoraAtnCodigo((Date)vRowTemp.get(23));
                    String medNombreCompleto = (String)(vRowTemp).get(24).toString();
                    medNombreCompleto += " "+(String)(vRowTemp).get(25).toString();
                    medNombreCompleto += " "+(String)(vRowTemp).get(26).toString();
                    oNotaEmbarazo.getMedicoSupervisor().setNombres(medNombreCompleto);
                    oNotaEmbarazo.getMedicoSupervisor().setCedProf((String)(vRowTemp).get(63).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().setPadre((String)(vRowTemp).get(27).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().setMadre((String)(vRowTemp).get(28).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().setAbueloPaterno((String)(vRowTemp).get(29).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().setAbuelaPaterna((String)(vRowTemp).get(30).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().setAbueloMaterno((String)(vRowTemp).get(31).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().setAbuelaMaterna((String)(vRowTemp).get(32).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecHeredoFamiliares().setOtros((String)(vRowTemp).get(33).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setEscolaridad((String)(vRowTemp).get(34).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setReligion((String)(vRowTemp).get(35).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setOcupacion((String)(vRowTemp).get(36).toString());
                    boolean tabaquismo = (vRowTemp.get(37).toString().compareTo("1") == 0); 
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setTabaquismo(tabaquismo);
                    boolean alcoholismo = (vRowTemp.get(38).toString().compareTo("1") == 0);
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setAlcoholismo(alcoholismo);                    
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setDrogas((String)(vRowTemp).get(39).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecNoPatologicos().setOtro((String)(vRowTemp).get(40).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setAlergias((String)(vRowTemp).get(41).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setQuirurgico((String)(vRowTemp).get(42).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setTransfusion((String)(vRowTemp).get(43).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setCardioVasculares((String)(vRowTemp).get(44).toString());
                    boolean oncologico = (vRowTemp.get(45).toString().compareTo("1") == 0);
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setOncologico(oncologico);
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setDiabetico((String)(vRowTemp).get(46).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setHTA((String)(vRowTemp).get(47).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecPatologicos().setOtro((String)(vRowTemp).get(48).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setMenarca(((Double)(vRowTemp).get(49)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setCiclo((String)(vRowTemp).get(50).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setIVSA(((Double)(vRowTemp).get(51)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setGestaciones(((Double)(vRowTemp).get(52)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setPartos(((Double)(vRowTemp).get(53)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setCesareas(((Double)(vRowTemp).get(54)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setAbortos(((Double)(vRowTemp).get(55)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setPF((String)(vRowTemp).get(56).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setUltimaMenstruacion((Date)(vRowTemp).get(57));
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setParejasSexuales(((Double)(vRowTemp).get(58)).intValue());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setFechaPosibleParto((Date)(vRowTemp).get(59));
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setDoc((String)(vRowTemp).get(60).toString());
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setETS((String)(vRowTemp).get(61).toString());
                    boolean prenatal = (vRowTemp.get(62).toString().compareTo("1") == 0);
                    oNotaEmbarazo.getEpiMed().getPaciente().getExpediente().getAntecGinecoObstetricos().setControlPrenatal(prenatal);
                    oNotaEmbarazo.getEpiMed().getPaciente().setLugarNacimiento((String)(vRowTemp).get(64).toString());
                    oNotaEmbarazo.getEpiMed().getSignosVitales().setTA((String)(vRowTemp).get(65).toString());
                    oNotaEmbarazo.getEpiMed().getSignosVitales().setFC((String)(vRowTemp).get(66).toString());
                    oNotaEmbarazo.getEpiMed().getSignosVitales().setFR((String)(vRowTemp).get(67).toString());
                    
                sQuery1 = "SELECT * FROM consultadatosReversoTriage(" + fpaciente + "::BIGINT, " + clavep + "::BIGINT, " + consecutivo + "::SMALLINT);";
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst1 = oAD.ejecutarConsulta(sQuery1);                        
                    oAD.desconectar();
                }
                    if(!rst1.isEmpty()){
                        ArrayList vRowTemp1 = (ArrayList)rst1.get(i);
                        oNotaEmbarazo.setFondoUterino((String)(vRowTemp1).get(0).toString());
                    oNotaEmbarazo.setSituacionProducto(new Parametrizacion());
                        oNotaEmbarazo.getSituacionProducto().setValor((String)(vRowTemp1).get(1).toString());
                    oNotaEmbarazo.setPresentacion(new Parametrizacion());
                        oNotaEmbarazo.getPresentacion().setValor((String)(vRowTemp1).get(2).toString());
                        boolean abocado = (vRowTemp1.get(3).toString().compareTo("1") == 0);
                    oNotaEmbarazo.setAbocado(abocado);
                        boolean encajado = (vRowTemp1.get(4).toString().compareTo("1") == 0);
                    oNotaEmbarazo.setEncajado(encajado);
                        oNotaEmbarazo.setFCF((String)(vRowTemp1).get(5).toString());
                        oNotaEmbarazo.setSometria((String)(vRowTemp1).get(6).toString());
                        oNotaEmbarazo.setLA((String)(vRowTemp1).get(7).toString());
                        oNotaEmbarazo.setPlacenta((String)(vRowTemp1).get(8).toString());
                        oNotaEmbarazo.setObservacionesUltrasonido((String)(vRowTemp1).get(9).toString());                        
                        oNotaEmbarazo.setDilatacion(((Double)(vRowTemp1).get(10)).shortValue());
                        oNotaEmbarazo.setBorramiento(((Double)(vRowTemp1).get(11)).shortValue());
                        oNotaEmbarazo.setPlano((String)(vRowTemp1).get(12).toString());
                    oNotaEmbarazo.setAmnios(new Parametrizacion());
                        oNotaEmbarazo.getAmnios().setValor((String)(vRowTemp1).get(13).toString());
                    oNotaEmbarazo.setLiqAmniotico(new Parametrizacion());
                        oNotaEmbarazo.getLiqAmniotico().setValor((String)(vRowTemp1).get(14).toString());
                    oNotaEmbarazo.setPelvis(new Parametrizacion());
                        oNotaEmbarazo.getPelvis().setValor((String)(vRowTemp1).get(15).toString());
                    oNotaEmbarazo.setTrazoCardio(new Parametrizacion());
                        oNotaEmbarazo.getTrazoCardio().setValor((String)(vRowTemp1).get(16).toString());
                        oNotaEmbarazo.setObsCardio((String)(vRowTemp1).get(17).toString());
                        oNotaEmbarazo.setResumenInter((String)(vRowTemp1).get(18).toString());
                        oNotaEmbarazo.setPronostico((String)(vRowTemp1).get(19).toString());
                        oNotaEmbarazo.setIndicacionTer((String)(vRowTemp1).get(20).toString());
                        oNotaEmbarazo.getPersonalHospitalario().setNombres((String)vRowTemp1.get(21).toString());
                        oNotaEmbarazo.getPersonalHospitalario().setApPaterno((String)vRowTemp1.get(22).toString());
                        oNotaEmbarazo.getPersonalHospitalario().setApMaterno((String)vRowTemp1.get(23).toString());
                        oNotaEmbarazo.getPersonalHospitalario().setCedProf((String)vRowTemp1.get(24).toString());
                        oNotaEmbarazo.getPersonalHospitalario().setNoTarjeta(((Double)vRowTemp1.get(25)).intValue());
                        oNotaEmbarazo.getEpiMed().getMedicoTratante().setNombres((String)vRowTemp1.get(26).toString());
                        oNotaEmbarazo.getEpiMed().getMedicoTratante().setApPaterno((String)vRowTemp1.get(27).toString());
                        oNotaEmbarazo.getEpiMed().getMedicoTratante().setApMaterno((String)vRowTemp1.get(28).toString());
                        oNotaEmbarazo.getEpiMed().getMedicoTratante().setCedProf((String)vRowTemp1.get(29).toString());
                        oNotaEmbarazo.getEpiMed().getMedicoTratante().setNoTarjeta(((Double)vRowTemp1.get(30)).intValue());
                        oNotaEmbarazo.getSituacionProducto().setClaveParametro((String)vRowTemp1.get(31).toString());                        
                        oNotaEmbarazo.getSituacionProducto().setTipoParametro((String)vRowTemp1.get(32).toString());
                        oNotaEmbarazo.getPresentacion().setClaveParametro((String)vRowTemp1.get(33).toString());
                        oNotaEmbarazo.getPresentacion().setTipoParametro((String)vRowTemp1.get(34).toString());
                        oNotaEmbarazo.getAmnios().setClaveParametro((String)vRowTemp1.get(35).toString());
                        oNotaEmbarazo.getAmnios().setTipoParametro((String)vRowTemp1.get(36).toString());
                        oNotaEmbarazo.getLiqAmniotico().setClaveParametro((String)vRowTemp1.get(37).toString());
                        oNotaEmbarazo.getLiqAmniotico().setTipoParametro((String)vRowTemp1.get(38).toString());
                        oNotaEmbarazo.getPelvis().setClaveParametro((String)vRowTemp1.get(39).toString());
                        oNotaEmbarazo.getPelvis().setTipoParametro((String)vRowTemp1.get(40).toString());
                        oNotaEmbarazo.getTrazoCardio().setClaveParametro((String)vRowTemp1.get(41).toString());
                        oNotaEmbarazo.getTrazoCardio().setTipoParametro((String)vRowTemp1.get(42).toString());
                    }
                }
            }
            return oNotaEmbarazo;
        }
       
        public DiagnosticoCIE10[] datosPaciente(long fpaciente, long clavepisodio)throws Exception{
            DiagnosticoCIE10 arrRet[] = null, oDiagnostico = null;
            ArrayList rst = null;            
            String sQuery = "";
            int i = 0, nTam = 0;
            /*if(fpaciente == 0 && clavepisodio == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INORMACION");
            else{*/
                sQuery = "SELECT * FROM consultaDiagnosticosTriage(" + fpaciente + "::BIGINT, " + clavepisodio + "::BIGINT);";                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null && rst.size() > 0){
                    arrRet = new DiagnosticoCIE10[rst.size()];
                    for(i = 0; i < rst.size(); i++){
                        oDiagnostico = new DiagnosticoCIE10();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oDiagnostico.setClave((String)(vRowTemp).get(1).toString());
                        oDiagnostico.setDescripcionDiag((String)(vRowTemp).get(0).toString());
                        arrRet[i] = oDiagnostico;
                    }
                //}
            }
            return arrRet;
        }
        public String insertaSignosVitales(NotaEmbarazo oNotaEmbarazo){
            String sQuery = "";
            String TA = (oNotaEmbarazo.getEpiMed().getSignosVitales().getTA() == null || oNotaEmbarazo.getEpiMed().getSignosVitales().getTA().isEmpty()) ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getSignosVitales().getTA() + "'::CHARACTER VARYING,";
            String FC = (oNotaEmbarazo.getEpiMed().getSignosVitales().getFC() == null || oNotaEmbarazo.getEpiMed().getSignosVitales().getFC().isEmpty()) ? "null::CHARACTER VARYING," : "'" + oNotaEmbarazo.getEpiMed().getSignosVitales().getFC() + "'::CHARACTER VARYING,";
            String FR = (oNotaEmbarazo.getEpiMed().getSignosVitales().getFR() == null ||oNotaEmbarazo.getEpiMed().getSignosVitales().getFR().isEmpty()) ? "null::CHARACTER VARYING);" : "'" + oNotaEmbarazo.getEpiMed().getSignosVitales().getFR() + "'::CHARACTER VARYING);";
            sQuery = "SELECT * FROM insertasignosVitalesNota(" + oNotaEmbarazo.getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT,"
                                                                      + TA + FC + FR ;
            return sQuery;
        }
        public NotaEmbarazo[] buscaHistorialTriage(long folioPac) throws Exception{
        NotaEmbarazo arrRet[]=null, oNotaEmb=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;

        if(folioPac==0){
            throw new Exception("NotaEmbarazo.buscaHistorialNotaEmbarazo: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialTriage("+folioPac+");"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new NotaEmbarazo[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oNotaEmb= new NotaEmbarazo();
                        oNotaEmb.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                        oNotaEmb.getEpiMed().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                        oNotaEmb.setConsecutivo(((Double)vRowTemp.get(2)).intValue());
                        oNotaEmb.getEpiMed().setAdmisionUrgs(new AdmisionUrgs());
                        oNotaEmb.getEpiMed().getAdmisionUrgs().setFolioAdmision(((Double)vRowTemp.get(3)).intValue());
                        oNotaEmb.getEpiMed().setFechaIngreso((Date)vRowTemp.get(4));
                        arrRet[i]=oNotaEmb;
                    } 
            } 
        }
        return arrRet; 
    }
    
        //******METODO QUE CARGA LOS DATOS GENERALES DEL PACIENTE para la consulta del expediente******
        public NotaEmbarazo buscaDetallesPacienteTriage(long idPaciente, long clavepisodio) throws Exception{
            NotaEmbarazo oHojaTriage = null;
            ArrayList rst = null;
            String sQuery;
            int i = 0;            
            if(idPaciente == 0)
                throw new Exception("NO ES POSIBLE PROCESAR INFORMACIÓN");
            else{
                sQuery = "SELECT * FROM buscadetallespacientetriage("+idPaciente+"::BIGINT," + clavepisodio + "::BIGINT);"; 
                //System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst.isEmpty())
                    return oHojaTriage = null;
                else{
                    oHojaTriage = new NotaEmbarazo();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oHojaTriage.getEpiMed().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                    oHojaTriage.getEpiMed().setClaveEpisodio(((Double)vRowTemp.get(1)).intValue());
                    oHojaTriage.getEpiMed().setAdmisionUrgs(new AdmisionUrgs());
                    oHojaTriage.getEpiMed().getAdmisionUrgs().setClaveEpisodio(((Double)vRowTemp.get(2)).intValue());
                    oHojaTriage.getEpiMed().getAdmisionUrgs().setFolioAdmision(((Double)vRowTemp.get(3)).intValue());
                    oHojaTriage.getEpiMed().getPaciente().setNombres((String)vRowTemp.get(4).toString());
                    oHojaTriage.getEpiMed().getPaciente().setApPaterno((String)vRowTemp.get(5).toString());
                    oHojaTriage.getEpiMed().getPaciente().setApMaterno((String)vRowTemp.get(6).toString());
                    oHojaTriage.getEpiMed().getPaciente().setSeguro(new Seguro());
                    oHojaTriage.getEpiMed().getPaciente().getSeguro().setNumero((String)vRowTemp.get(7).toString());
                    oHojaTriage.getEpiMed().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
                    String fecha  = dateFormat.format((Date)vRowTemp.get(9)); 
                    oHojaTriage.getEpiMed().setFechaIngreso((Date)vRowTemp.get(9));
                    oHojaTriage.getEpiMed().setFIngreso(fecha);                    
                    oHojaTriage.getEpiMed().getPaciente().setFechaNac((Date)vRowTemp.get(10));
                    String convertido = edad.format(oHojaTriage.getEpiMed().getPaciente().getFechaNac());
                    oHojaTriage.getEpiMed().getPaciente().setFechaNacTexto(convertido);
                    oHojaTriage.getEpiMed().getPaciente().calculaEdad();
                    oHojaTriage.getEpiMed().getAdmisionUrgs().setMotivoConsulta((String)vRowTemp.get(11).toString());
                    oHojaTriage.getEpiMed().getPaciente().setTelefono((String)vRowTemp.get(12).toString());
                    oHojaTriage.getEpiMed().getPaciente().getReferencia().setTipo((String)vRowTemp.get(13).toString());
                    oHojaTriage.getEpiMed().getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(14).toString());
                    oHojaTriage.getEpiMed().getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(15).toString());
                    oHojaTriage.getEpiMed().getPaciente().setColonia((String)vRowTemp.get(16).toString());
                    oHojaTriage.getEpiMed().getPaciente().setCalleNum((String)vRowTemp.get(17).toString());
                    oHojaTriage.getEpiMed().getPaciente().setTalla(((Double)vRowTemp.get(18)).floatValue());
                    oHojaTriage.getEpiMed().getPaciente().setPeso(((Double)vRowTemp.get(19)).floatValue());
                    oHojaTriage.getEpiMed().getSignosVitales().setTA((String)vRowTemp.get(20).toString());
                    oHojaTriage.getEpiMed().getSignosVitales().setFC((String)vRowTemp.get(21).toString());
                    oHojaTriage.getEpiMed().getSignosVitales().setFR((String)vRowTemp.get(22).toString());
                    oHojaTriage.getEpiMed().getPaciente().setLugarNacimiento((String)vRowTemp.get(23).toString());
                    oHojaTriage.getEpiMed().getPaciente().setEstadoCivil(new Parametrizacion());
                    oHojaTriage.getEpiMed().getPaciente().getEstadoCivil().setValor((String)vRowTemp.get(24).toString());
                    oHojaTriage.getEpiMed().getPaciente().setTipoSangre(new Parametrizacion());
                    oHojaTriage.getEpiMed().getPaciente().getTipoSangre().setValor((String)vRowTemp.get(25).toString());
                    oHojaTriage.getEpiMed().getPaciente().getTipoSangre().setClaveParametro((String)vRowTemp.get(27).toString());
                    oHojaTriage.getEpiMed().getPaciente().getTipoSangre().setTipoParametro((String)vRowTemp.get(28).toString());
                    oHojaTriage.getEpiMed().getPaciente().setFactorRH(new Parametrizacion());
                    oHojaTriage.getEpiMed().getPaciente().getFactorRH().setValor((String)vRowTemp.get(26).toString());
                    oHojaTriage.getEpiMed().getPaciente().getFactorRH().setClaveParametro((String)vRowTemp.get(29).toString());
                    oHojaTriage.getEpiMed().getPaciente().getFactorRH().setTipoParametro((String)vRowTemp.get(30).toString());
                    oHojaTriage.getEpiMed().getPaciente().getEstadoCivil().setClaveParametro((String)vRowTemp.get(31).toString());
                }
            }
            return oHojaTriage;
        }
    
	public boolean getAbocado() {
	return bAbocado;
	}

	public void setAbocado(boolean valor) {
	bAbocado=valor;
	}

        public String getValorAbocado(){
            return this.getAbocado() ? "SÍ" : "NO";
        }

	public boolean getAdecuada() {
	return bAdecuada;
	}

	public void setAdecuada(boolean valor) {
	bAdecuada=valor;
	}

	/*public boolean getAmnios() {
	return bAmnios;
	}

	public void setAmnios(boolean valor) {
	bAmnios=valor;
	}*/

	public boolean getEncajado() {
	return bEncajado;
	}

	public void setEncajado(boolean valor) {
	bEncajado=valor;
	}
        public String getValorEncajado(){
            return this.getEncajado() ? "SÍ" : "NO";
        }

	public boolean getIntegro() {
	return bIntegro;
	}

	public void setIntegro(boolean valor) {
	bIntegro=valor;
	}

	public boolean getLANormal() {
	return bLANormal;
	}

	public void setLANormal(boolean valor) {
	bLANormal=valor;
	}

	public boolean getLimite() {
	return bLimite;
	}

	public void setLimite(boolean valor) {
	bLimite=valor;
	}

	public boolean getReactivo() {
	return bReactivo;
	}

	public void setReactivo(boolean valor) {
	bReactivo=valor;
	}

	public boolean getRota() {
	return bRota;
	}

	public void setRota(boolean valor) {
	bRota=valor;
	}

	public short getBorramiento() {
	return nBorramiento;
	}

	public void setBorramiento(short valor) {
	nBorramiento=valor;
	}

	public short getDilatacion() {
	return nDilatacion;
	}

	public void setDilatacion(short valor) {
	nDilatacion=valor;
	}

	public Parametrizacion getPresentacion() {
	return oPresentacion;
	}

	public void setPresentacion(Parametrizacion valor) {
	oPresentacion=valor;
	}

	public Parametrizacion getSituacionProducto() {
	return oSituacionProducto;
	}

	public void setSituacionProducto(Parametrizacion valor) {
	oSituacionProducto=valor;
	}

	public String getFCF() {
	return sFCF;
	}

	public void setFCF(String valor) {
	sFCF=valor;
	}

	public String getFondoUterino() {
	return sFondoUterino;
	}

	public void setFondoUterino(String valor) {
	sFondoUterino=valor;
	}

	public String getLA() {
	return sLA;
	}

	public void setLA(String valor) {
	sLA=valor;
	}

	public String getMeconio() {
	return sMeconio;
	}

	public void setMeconio(String valor) {
	sMeconio=valor;
	}

	public String getObservacionesUltrasonido() {
	return sObservacionesUltrasonido;
	}

	public void setObservacionesUltrasonido(String valor) {
	sObservacionesUltrasonido=valor;
	}

	public String getPlacenta() {
	return sPlacenta;
	}

	public void setPlacenta(String valor) {
	sPlacenta=valor;
	}

	public String getPlano() {
	return sPlano;
	}

	public void setPlano(String valor) {
	sPlano=valor;
	}

    public String getSometria() {
    return sSometria;
    }

    public void setSometria(String valor) {
    sSometria=valor;
    }

    public Parametrizacion getAmnios() {
        return oAmnios;
    }

    public void setAmnios(Parametrizacion oAmnios) {
        this.oAmnios = oAmnios;
    }

    public Parametrizacion getLiqAmniotico() {
        return oLiqAmniotico;
    }

    public void setLiqAmniotico(Parametrizacion oLiqAmniotico) {
        this.oLiqAmniotico = oLiqAmniotico;
    }

    public Parametrizacion getPelvis() {
        return oPelvis;
    }

    public void setPelvis(Parametrizacion oPelvis) {
        this.oPelvis = oPelvis;
    }

    public String getObsCardio() {
        return sObsCardio;
    }

    public void setObsCardio(String sCardio) {
        this.sObsCardio = sCardio;
    }

    public Parametrizacion getTrazoCardio() {
        return oTrazoCardio;
    }

    public void setTrazoCardio(Parametrizacion oTrazoCardio) {
        this.oTrazoCardio = oTrazoCardio;
    }

    public boolean isParoCardioDuranteEmb() {
        return bParoCardioDuranteEmb;
    }

    public void setParoCardioDuranteEmb(boolean bParoCardioDuranteEmb) {
        this.bParoCardioDuranteEmb = bParoCardioDuranteEmb;
    }
    public String getValorParoCardioDuranteEmb(){
        return this.isParoCardioDuranteEmb() ? "( X )" : "(   )";
    }

    public boolean isAltEdoConciencia() {
        return bAltEdoConciencia;
    }

    public void setAltEdoConciencia(boolean bAltEdoConciencia) {
        this.bAltEdoConciencia = bAltEdoConciencia;
    }
    
    public String getValorAltEdoConciencia(){
        return this.isAltEdoConciencia() ? "( X )" : "(   )";
    }

    public boolean isInsufResp() {
        return bInsufResp;
    }

    public void setInsufResp(boolean bInsufResp) {
        this.bInsufResp = bInsufResp;
    }
    public String getValorInsufResp(){
        return this.isInsufResp() ? "( X )" : "(   )";
    }

    public boolean isCefaleaIntensa() {
        return bCefaleaIntensa;
    }

    public void setCefaleaIntensa(boolean bCefaleaIntensa) {
        this.bCefaleaIntensa = bCefaleaIntensa;
    }
    public String getValorCefaleaIntensa(){
        return this.isCefaleaIntensa() ? "( X )" : "(   )";
    }

    public boolean isPreeclampsiaSevera() {
        return bPreeclampsiaSevera;
    }

    public void setPreeclampsiaSevera(boolean bPreeclampsiaSevera) {
        this.bPreeclampsiaSevera = bPreeclampsiaSevera;
    }
    public String getValorPreeclampsiaSevera(){
        return this.isPreeclampsiaSevera() ? "( X )" : "(   )";
    }

    public boolean isChoqueHipovolemico() {
        return bChoqueHipovolemico;
    }

    public void setChoqueHipovolemico(boolean bChoqueHipovolemico) {
        this.bChoqueHipovolemico = bChoqueHipovolemico;
    }
    public String getValorChoqueHipovolemico(){
        return this.isChoqueHipovolemico() ? "( X )" : "(   )";
    }

    public boolean isSepsisEmbarazo() {
        return bSepsisEmbarazo;
    }

    public void setSepsisEmbarazo(boolean bSepsisEmbarazo) {
        this.bSepsisEmbarazo = bSepsisEmbarazo;
    }
    public String getValorSepsisEmbarazo(){
        return this.isSepsisEmbarazo() ? "( X )" : "(   )"; 
    }

    public boolean isProlapsoCordonUmb() {
        return bProlapsoCordonUmb;
    }

    public void setProlapsoCordonUmb(boolean bProlapsoCordonUmb) {
        this.bProlapsoCordonUmb = bProlapsoCordonUmb;
    }
    public String getValorProlapsoCordonUmb(){
        return this.isProlapsoCordonUmb() ? "( X )" : "(   )";
    }

    public boolean isConvulsiones() {
        return bConvulsiones;
    }

    public void setConvulsiones(boolean bConvulsiones) {
        this.bConvulsiones = bConvulsiones;
    }
    public String getValorConvulsiones(){
        return this.isConvulsiones() ? "( X )" : "(   )";
    }

    public boolean isHemorragia() {
        return bHemorragia;
    }

    public void setHemorragia(boolean bHemorragia) {
        this.bHemorragia = bHemorragia;
    }
    public String getValorHemorragia(){
        return this.isHemorragia() ? "( X )" : "(   )";
    }

    public boolean isTrabajoParto() {
        return bTrabajoParto;
    }

    public void setTrabajoParto(boolean bTrabajoParto) {
        this.bTrabajoParto = bTrabajoParto;
    }
    public String getValorTrabajoParto(){
        return this.isTrabajoParto() ? "( X )" : "(   )";
    }

    public boolean isSalidaLiqSangVag() {
        return bSalidaLiqSangVag;
    }

    public void setSalidaLiqSangVag(boolean bSalidaLiqSangVag) {
        this.bSalidaLiqSangVag = bSalidaLiqSangVag;
    }
    public String getValorSalidaLiqSangVag(){
        return this.isSalidaLiqSangVag() ? "( X )" : "(   )";
    }

    public boolean isPreeclampsiaLeve() {
        return bPreeclampsiaLeve;
    }

    public void setPreeclampsiaLeve(boolean bPreeclampsiaLeve) {
        this.bPreeclampsiaLeve = bPreeclampsiaLeve;
    }
    public String getValorPreeclampsiaLeve(){
        return this.isPreeclampsiaLeve() ? "( X )" : "(   )";
    }

    public boolean isHipomotilidadFetal() {
        return bHipomotilidadFetal;
    }

    public void setHipomotilidadFetal(boolean bHipomotilidadFetal) {
        this.bHipomotilidadFetal = bHipomotilidadFetal;
    }
    public String getValorHipomotilidadFetal(){
        return this.isHipomotilidadFetal() ? "( X )" : "(   )"; 
    }

    public boolean isRupPrematuraMembranas() {
        return bRupPrematuraMembranas;
    }

    public void setRupPrematuraMembranas(boolean bRupPrematuraMembranas) {
        this.bRupPrematuraMembranas = bRupPrematuraMembranas;
    }
    public String getValorRupPrematuraMembranas(){
        return isRupPrematuraMembranas() ? "( X )" : "(   )";
    }

    public boolean isEmbPatolCronica() {
        return bEmbPatolCronica;
    }

    public void setEmbPatolCronica(boolean bEmbPatolCronica) {
        this.bEmbPatolCronica = bEmbPatolCronica;
    }
    public String getValorEmbPatolCronica(){
        return this.isEmbPatolCronica() ? "( X )" : "(   )"; 
    }

    public boolean isAborto() {
        return bAborto;
    }

    public void setAborto(boolean bAborto) {
        this.bAborto = bAborto;
    }
    public String getValorAborto(){
        return this.isAborto() ? "( X )" : "(   )";
    }

    public boolean isPartoPretermino() {
        return bPartoPretermino;
    }

    public void setPartoPretermino(boolean bPartoPretermino) {
        this.bPartoPretermino = bPartoPretermino;
    }
    public String getValorPartoPretermino(){
        return this.isPartoPretermino() ? "( X )" : "(   )";
    }

    public boolean isEmbMenor3SDGsinTrabParto() {
        return bEmbMenor3SDGsinTrabParto;
    }

    public void setEmbMenor3SDGsinTrabParto(boolean bEmbMenor3SDGsinTrabParto) {
        this.bEmbMenor3SDGsinTrabParto = bEmbMenor3SDGsinTrabParto;
    }
    public String getValorEmbMenor3SDGsinTrabParto(){
        return this.isEmbMenor3SDGsinTrabParto() ? "( X )" : "(   )";
    }

    public boolean isEmbAsintomatico() {
        return bEmbAsintomatico;
    }

    public void setEmbAsintomatico(boolean bEmbAsintomatico) {
        this.bEmbAsintomatico = bEmbAsintomatico;
    }
    public String getValorEmbAsintomatico(){
        return this.isEmbAsintomatico() ? "( X )" : "(   )";
    }

    public boolean isPuerperioFisiologico() {
        return bPuerperioFisiologico;
    }

    public void setPuerperioFisiologico(boolean bPuerperioFisiologico) {
        this.bPuerperioFisiologico = bPuerperioFisiologico;
    }
    public String getValorPuerperioFisiologico(){
        return this.isPuerperioFisiologico() ? "( X )" : "(   )";
    }

    public boolean isPuerperioQxsinCompl() {
        return bPuerperioQxsinCompl;
    }

    public void setPuerperioQxsinCompl(boolean bPuerperioQxsinCompl) {
        this.bPuerperioQxsinCompl = bPuerperioQxsinCompl;
    }
    public String getValorPuerperioQxsinCompl(){
        return this.isPuerperioQxsinCompl() ? "( X )" : "(   )";
    }

    public Date getHoraCodigoRojo() {
        return dHoraCodigoRojo;
    }

    public void setHoraCodigoRojo(Date dHoraCodigoRojo) {
        this.dHoraCodigoRojo = dHoraCodigoRojo;
    }
    public Date getHoraCodigoAmarillo(){
        return dHoraCodigoAmarillo;
    }
    public void setHoraCodigoAmarillo(Date dHoraCodigoAmarillo){
        this.dHoraCodigoAmarillo = dHoraCodigoAmarillo;
    }
    public Date getHoraCodigoVerde(){
        return dHoraCodigoVerde;
    }
    public void setHoraCodigoVerde(Date dHoraCodigoVerde){
        this.dHoraCodigoVerde = dHoraCodigoVerde;
    }
    public Date getHoraAtnCodigo() {
        return dHoraAtnCodigo;
    }

    public void setHoraAtnCodigo(Date dHoraAtnCodigo) {
        this.dHoraAtnCodigo = dHoraAtnCodigo;
    }
    public PersonalHospitalario getPersonalHospitalario(){
        return oPersonal;
    } 
    public void setPersonalHospitalario(PersonalHospitalario oPersonal){
        this.oPersonal = oPersonal;
    }
} 

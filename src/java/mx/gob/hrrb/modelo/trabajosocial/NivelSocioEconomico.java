package mx.gob.hrrb.modelo.trabajosocial;  
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Ubicacion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

public class NivelSocioEconomico {
    private Paciente oPac;
    private Date dRegistro;
    private Date dVigencia;
    private int npuntaje;
    private Parametrizacion oNivelAlcanzado;
    private PersonalHospitalario otrabSocCapt;
    private PersonalHospitalario otrabSocModif;
    private boolean bCasoMedicoLegal;
    private Parametrizacion oAtencionPrioritaria;
    private String sOcupacion;
    private String sReligion;
    private Parametrizacion oNivMaxEstudios;
    private boolean bNivMaxEstTerminado;
    private String sCallenumTemp;
    private String sColoniaTemp;
    private Ubicacion oUbicTemp;
    private String sTelTem;
    private String sCalleNumResp;
    private String sColoniaResp;
    private Ubicacion oUbicResp;
    private String sTelRes;
    private IngresoFamiliar oIngreFamSelecc;
    private short oColIngreFamSelecc ;
    private EncuestaTrabsocPregBasicas[] arrRespPregBasicas;
    private String sObsAdicionales;
    private Parametrizacion oRazonRecla;
    private String sRazonRecla;
    private Parametrizacion[] oDocumEntregados;
    private AccesoDatos oAD; 
    
    public NivelSocioEconomico(){
        oAD = new AccesoDatos();
        oNivelAlcanzado= new Parametrizacion();
        oPac= new Paciente(); 
        otrabSocCapt=new PersonalHospitalario();
        otrabSocModif = new PersonalHospitalario();
        oAtencionPrioritaria = new Parametrizacion();
        oNivMaxEstudios = new Parametrizacion();
        oIngreFamSelecc = new IngresoFamiliar();
        oRazonRecla = new Parametrizacion();
    }
    
    public int insertanivelsocioeconomi(String Usuario, long foliopac, String fecha,int puntaje, String nivel, String tablanivel, int numtarcap, String casoleg, String estuTermi, String callerep, String calletempo,
                  String CalveciuResp, String ClaveciuTempo, String CalveEstaResp, String  CalveEstaTemp, String CalveMuniResp, String CalveMuniTemp, String ColiniaResp, String ColoniaTemp, String cpResp, String cpTemp
          ,String ocupacion, String tablAtencionprio, String valorAtencionprio, String TablEstudi, String valorEst, String TelefonoResp, String TelefonoTemp) throws Exception{
         int Resul=0; 
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from insertanivelsocioeconomi('"+Usuario+"'::character varying, "+foliopac+"::bigint, '"+fecha+"'::date, "+puntaje+"::smallint, '"+nivel+"'::char(3),'"+tablanivel+"'::char(3), "+numtarcap+"::integer, '"+casoleg+"'::character, '"+estuTermi+"'::character, "
                                + ""+isNull(callerep)+"::character varying, "+isNull(calletempo)+"::character varying,"+isNull(CalveciuResp)+"::char(4), "+isNull(ClaveciuTempo)+"::char(4), "+isNull(CalveEstaResp)+"::char(2), "+isNull(CalveEstaTemp)+"::char(2), "+isNull(CalveMuniResp)+"::char(3), "+isNull(CalveMuniTemp)+"::char(3),"
                                + ""+isNull(ColiniaResp)+"::character varying, "+isNull(ColoniaTemp)+"::character varying, "+isNull(cpResp)+"::char(5), "+isNull(cpTemp)+"::char(5),"+isNull(ocupacion)+"::character varying, '"+tablAtencionprio+"'::char(3),'"+valorAtencionprio+"'::char(3), '"+TablEstudi+"'::char(3), '"+valorEst+"'::char(3),"
                                + " "+isNull(TelefonoResp)+"::character varying, "+isNull(TelefonoTemp)+"::character varying);";
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
         
         public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }
         
          public int insertaresptrabsocegrefam(String Usuario, long foliopac, String fecha, String tablapara, String ValorTab, float costo) throws Exception{
         int Resul=0; 
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from insertaresptrabsocegrefam ('"+Usuario+"'::character varying,"+foliopac+",'"+fecha+"'::date,'"+tablapara+"'::character(3),'"+ValorTab+"'::character(3),"+costo+"::numeric);";
                        System.out.println(sQuery);
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
          
          
           public boolean buscaDatosDeEstudioSocioEconomico(long foliopac) throws Exception{  
         ArrayList rst = null;
	  String sQuery = "";
          SimpleDateFormat df;
          df=new SimpleDateFormat("dd/MM/yyyy");
          sQuery = "select * from buscadatosDenivelsocioeco("+foliopac+");";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) { 
                   ArrayList vRowTemp = (ArrayList)rst.get(0);
                   this.setRegistro((Date)vRowTemp.get(0)); 
                   this.setVigencia((Date)vRowTemp.get(1));
                   this.setPuntaje(((Double) vRowTemp.get(2)).intValue());
                   this.getNivelAlcanzado().setClaveParametro((String)vRowTemp.get(3)); 
                   this.setCasoMedicoLegal(trasfroma((String)vRowTemp.get(4)));
                   this.setOcupacion((String)vRowTemp.get(5));
                   this.getAtencionPrioritaria().setValor((String)vRowTemp.get(6)); 
                    return true;
                }else{
                  return false;
                }
      }
         
               public boolean trasfroma(String cos){
        return "1".equals(cos);    
} 
               
  public void buscaestudiosocioeconomico(Paciente oPac) throws Exception{
      ArrayList rst = null;
	String sQuery = ""; 
        SimpleDateFormat df;

		sQuery = "SELECT * FROM buscanivelsocioeconomicopaciente("+oPac.getFolioPaciente()+" ::BIGINT);";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
                        
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.setRegistro((Date)vRowTemp.get(0)); 
                            this.setVigencia((Date)vRowTemp.get(1)); 
                            this.getNivelAlcanzado().setClaveParametro((String)vRowTemp.get(3)); 
                            this.setPuntaje(((Double)vRowTemp.get(4)).intValue());
                                    
                
	}else{
                this.getNivelAlcanzado().setClaveParametro("06 ");
                }   
       
  }
    
    
      public boolean BuscaTieneEstudiosocioEconomico(Paciente oPac) throws Exception{
      ArrayList rst = null;
	String sQuery = "";  

		sQuery = "SELECT * FROM buscanivelsocioeconomicopaciente("+oPac.getFolioPaciente()+" ::BIGINT);";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
                        
			oAD.desconectar(); 
		}
        return rst != null && rst.size()==1;   
       
  }
      
       public NivelSocioEconomico[] BuscaEstudiosAnteriores(long folipac) throws Exception{
     ArrayList rst = null;
     NivelSocioEconomico arrRet[]=null, oEstuAnt=null;
     String sQuery = "";     
     int i=0,nTam=0;
     List<NivelSocioEconomico> vObj=null;
     sQuery = "select * from buscaEstudiosAnteriores("+folipac+");";  
      System.out.println(sQuery);
                oAD=new AccesoDatos();  
		if (oAD.conectar()){ 
	            rst = oAD.ejecutarConsulta(sQuery); 
	            oAD.desconectar(); 
		}
                if (rst != null && rst.size()>0) {
                    vObj = new ArrayList<NivelSocioEconomico>();
                    for (i=0; i<rst.size(); i++){
                        oEstuAnt=new  NivelSocioEconomico();
                        ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                        oEstuAnt.setRegistro((Date)vRowTemp.get(0));
                        oEstuAnt.setVigencia((Date)vRowTemp.get(1));
                        oEstuAnt.setPuntaje(((Double) vRowTemp.get(2)).intValue());
                        oEstuAnt.getNivelAlcanzado().setClaveParametro((String)vRowTemp.get(3));
                        oEstuAnt.otrabSocCapt.setNoTarjeta(((Double) vRowTemp.get(4)).intValue()); 
                        if(oEstuAnt.otrabSocCapt.getNoTarjeta()!=0){
                            oEstuAnt.otrabSocCapt.buscarCapa();
                        }else{}
                        oEstuAnt.otrabSocModif.setNoTarjeta(((Double) vRowTemp.get(5)).intValue());
                        if(oEstuAnt.otrabSocModif.getNoTarjeta()!=0){
                            oEstuAnt.otrabSocModif.buscarCapa();
                        }else{} 
                        oEstuAnt.setCasoMedicoLegal(this.trasfroma((String)vRowTemp.get(6))); 
                        
                        vObj.add(oEstuAnt); 
                    }
                      nTam = vObj.size();
                    arrRet = new NivelSocioEconomico[nTam];
                    
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                }
                }
                 return arrRet;
 } 
       
        public int insertaDocumenRecalifi(String Usuario, long foliopac, String fecha, String DocSelecc) throws Exception{
         int Resul=0; 
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from insertaDocumenRecalifi('"+Usuario+"'::character varying,"+foliopac+",'"+fecha+"'::DATE,'TA7'::character(3), '"+DocSelecc+"'::character(3));";
                        System.out.println(sQuery);
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
        
         public int modificarEstudioSocioEconomico(String Usuario, long foliopac, String fecha, String nuveonivel, int numetarmod, String otraCau, String obseadi, String motivoRecali) throws Exception{
         int Resul=0; 
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from modificaEstudioSocioEconomico('"+Usuario+"', "+foliopac+", '"+fecha+"', '"+nuveonivel+"', "+numetarmod+","+this.isNull(otraCau)+", "+this.isNull(obseadi)+", 'TAA', '"+motivoRecali+"'); ";
                        System.out.println(sQuery);
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
    
    public void buscaestudiosocioeconomico() throws Exception{
      ArrayList rst = null;
	String sQuery = ""; 
        SimpleDateFormat df;

		sQuery = "SELECT * FROM buscanivelsocioeconomicopaciente("+this.getPac().getFolioPaciente()+" ::BIGINT);";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
                        
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.setRegistro((Date)vRowTemp.get(0)); 
                            this.setVigencia((Date)vRowTemp.get(1)); 
                            this.getNivelAlcanzado().setClaveParametro((String)vRowTemp.get(3)); 
                            this.setPuntaje(((Double)vRowTemp.get(4)).intValue()); 
                                    
                
	}else{
                this.getNivelAlcanzado().setClaveParametro("06 ");
                }   
       
  }
    public NivelSocioEconomico[] buscaHistorialEstudioSocioEconomico(long folioPac) throws Exception{
        NivelSocioEconomico arrRet[]=null, oEstudio=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
        if(folioPac==0){
            throw new Exception("NivelSocioEconomico.buscahistorialestudiosocioeconomico: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialestudiosocioeconomico("+folioPac+"::BIGINT);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new NivelSocioEconomico[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oEstudio= new NivelSocioEconomico();
                    oEstudio.getPac().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oEstudio.setRegistro((Date)vRowTemp.get(1));
                    oEstudio.setVigencia((Date)vRowTemp.get(2));
                    oEstudio.setPuntaje(((Double)vRowTemp.get(3)).intValue());
                    oEstudio.getNivelAlcanzado().setValor((String)vRowTemp.get(4));
                    oEstudio.setCasoMedicoLegal(((String)vRowTemp.get(5)).compareTo("1")==0);
                    arrRet[i]=oEstudio;
                }
            }
        }
        return arrRet;
    }
    
    public void buscaDetallesEstudioSocioEconomicoAnverso1() throws Exception{
        ArrayList rst = null;
        String sQuery="", edad="", clasificacionsocioeconomica="";
        if(getPac().getFolioPaciente()==0 || getRegistro()==null)
            throw new Exception("NivelSocioEconomico.buscaestudiosocioeconomicoanverso1: Error, Falta datos");
        else{
            sQuery="select * from  buscaestudiosocioeconomicoanverso1("+getPac().getFolioPaciente()+"::BIGINT,'" + getRegistro()+"'::DATE);";
            System.out.println(sQuery); 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (!rst.isEmpty()) {  
                ArrayList vRowTemp = (ArrayList)rst.get(0); 
               getPac().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
               getPac().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
               getPac().setNombres((String)vRowTemp.get(2)); 
               getPac().setApPaterno((String)vRowTemp.get(3));
               getPac().setApMaterno((String)vRowTemp.get(4));
               getPac().setFechaNac((Date) vRowTemp.get(5));
               edad=(String)vRowTemp.get(6);
                if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                        if (edad.charAt(0)=='0')
                           this.getPac().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                           this.getPac().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                            this.getPac().setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                            this.getPac().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                }
               if(((String)vRowTemp.get(7)).compareTo("M")==0)
                    getPac().setSexoP("MASCULINO");
                else if(((String)vRowTemp.get(7)).compareTo("F")==0)
                    getPac().setSexoP("FEMENINO");
                    else
                        getPac().setSexoP("");
               getPac().setPeso(((Double)vRowTemp.get(8)).floatValue());
               getPac().setTalla(((Double)vRowTemp.get(9)).floatValue());                
               getPac().getExpediente().setNumero(((Double)vRowTemp.get(10)).intValue());
               getPac().getSeg().setNumero((String)vRowTemp.get(11));
               getPac().getExpediente().getServicioIngreso().setDescripcion((String)vRowTemp.get(12));
               getPac().setPais((String)vRowTemp.get(13)); //cama
               getPac().getDiagcie().setDescripcionDiag((String)vRowTemp.get(14));
               getPac().getReferencia().setDescripcion((String)vRowTemp.get(15));
               getPac().setParametrizacion(new Parametrizacion());
               if(((String)vRowTemp.get(16)).compareTo("S")==0) 
                   getPac().getParametrizacion().setValor("PRIMERA VEZ"); 
               else if (((String)vRowTemp.get(16)).compareTo("N")==0)
                   getPac().getParametrizacion().setValor("REINGRESO");
               getNivelAlcanzado().setTipoParametro((String)vRowTemp.get(17)); //puntuación obtenida
                //De acuerdo a la puntuación obtenida, obtener la clasificación socioeconomica
                if(((String)vRowTemp.get(17)).compareTo("0-12")==0)
                    clasificacionsocioeconomica="00(Excento)";
                if(((String)vRowTemp.get(17)).compareTo("13-24")==0)
                    clasificacionsocioeconomica="01";
                if(((String)vRowTemp.get(17)).compareTo("25-36")==0)
                    clasificacionsocioeconomica="02";
                if(((String)vRowTemp.get(17)).compareTo("37-52")==0)
                    clasificacionsocioeconomica="03";
                if(((String)vRowTemp.get(17)).compareTo("53-68")==0)
                    clasificacionsocioeconomica="04";
                if(((String)vRowTemp.get(17)).compareTo("69-84")==0)
                    clasificacionsocioeconomica="05";
                if(((String)vRowTemp.get(17)).compareTo("85-100")==0)
                    clasificacionsocioeconomica="06";
               getNivelAlcanzado().setValor(clasificacionsocioeconomica); //clasificacion obtenida manualmente
               setCasoMedicoLegal(trasfroma((String)vRowTemp.get(18)));
               setRegistro((Date)vRowTemp.get(19));
               //médico responsable
               getOtrabSocCapt().setNombres((String)vRowTemp.get(20));
               getOtrabSocCapt().setApPaterno((String)vRowTemp.get(21));
               getOtrabSocCapt().setApMaterno((String)vRowTemp.get(22));
               getOtrabSocCapt().setCedProf((String)vRowTemp.get(23));
               getAtencionPrioritaria().setValor((String)vRowTemp.get(24));
               setOcupacion((String)vRowTemp.get(25));
               setReligion((String)vRowTemp.get(26));
               getPac().setLugarNacimiento((String)vRowTemp.get(27));
               getPac().setEdoCivilStr((String)vRowTemp.get(28));
               getNivMaxEstudios().setValor((String)vRowTemp.get(29));
               setNivMaxEstTerminado(((String)vRowTemp.get(30)).compareTo("1")==0);
               //Ubicacion real del paciente
               getPac().setCalleNum((String)vRowTemp.get(31));
               getPac().setColonia((String)vRowTemp.get(32));
               getPac().setTelefono((String)vRowTemp.get(33));
               getPac().setCp((String)vRowTemp.get(34));
               getPac().getEstado().setDescripcionEdo((String)vRowTemp.get(35));
               getPac().getMunicipio().setDescripcionMun((String)vRowTemp.get(36));
               getPac().getCiudad().setDescripcionCiu((String)vRowTemp.get(37));
               //Ubicacion temporal del paciente
               setCallenumTemp((String)vRowTemp.get(38));
               setColoniaTemp((String)vRowTemp.get(39));
               setTelTem((String)vRowTemp.get(40));
               setUbicTemp(new Ubicacion());
               getUbicTemp().getCiudadCP().setCp((String)vRowTemp.get(41));
               getUbicTemp().getCiudadCP().getEstado().setClaveEdo((String)vRowTemp.get(42));
               getUbicTemp().getCiudadCP().getEstado().buscar(); //getDescripcionEdo();
               getUbicTemp().getCiudadCP().getMunicipio().setClaveMun((String) vRowTemp.get(43));
               getUbicTemp().getCiudadCP().getMunicipio().buscar(getUbicTemp().getCiudadCP().getEstado().getClaveEdo()); //getDescripcionMun()
               getUbicTemp().getCiudadCP().getCiudad().setClaveCiu((String) vRowTemp.get(44));
               getUbicTemp().getCiudadCP().getCiudad().buscar(getUbicTemp().getCiudadCP().getEstado().getClaveEdo(), getUbicTemp().getCiudadCP().getMunicipio().getClaveMun()); //getDescripcionCiu()
               //Ubicacion familiar responsable
               setCalleNumResp((String)vRowTemp.get(45));
               setColoniaResp((String)vRowTemp.get(46));
               setTelRes((String)vRowTemp.get(47));
               setUbicResp(new Ubicacion());
               getUbicResp().getCiudadCP().setCp((String)vRowTemp.get(48));
               getUbicResp().getCiudadCP().getEstado().setClaveEdo((String)vRowTemp.get(49));
               getUbicResp().getCiudadCP().getEstado().buscar(); //getDescripcionEdo();
               getUbicResp().getCiudadCP().getMunicipio().setClaveMun((String) vRowTemp.get(50));
               getUbicResp().getCiudadCP().getMunicipio().buscar(getUbicResp().getCiudadCP().getEstado().getClaveEdo()); //getDescripcionMun()
               getUbicResp().getCiudadCP().getCiudad().setClaveCiu((String) vRowTemp.get(51));
               getUbicResp().getCiudadCP().getCiudad().buscar(getUbicResp().getCiudadCP().getEstado().getClaveEdo(), getUbicResp().getCiudadCP().getMunicipio().getClaveMun()); //getDescripcionCiu()
               //familiar responsable
               getOtrabSocModif().setNombres((String)vRowTemp.get(52));
               getOtrabSocModif().setApPaterno((String)vRowTemp.get(53));
               getOtrabSocModif().setApMaterno((String)vRowTemp.get(54));
               getOtrabSocModif().setStatus((String)vRowTemp.get(55));//parentesco
               getNivelAlcanzado().setClaveParametro((String)vRowTemp.get(56)); //clasificacion obtenida desde la base
               setPuntaje(((Double)vRowTemp.get(57)).intValue());
               getPac().getSeg().setDerechohabienteP((String)vRowTemp.get(58));
               getIngreFamSelecc().setAnio(((Double)vRowTemp.get(59)).shortValue());
               getIngreFamSelecc().setOrden(((Double)vRowTemp.get(60)).shortValue());
               //información del ingreso familiar mensual
               setColIngreFamSelecc(((Double)vRowTemp.get(61)).shortValue());//columna seleccionada
               getIngreFamSelecc().setTxtSalario((String)vRowTemp.get(62));
               getIngreFamSelecc().setTxtIngreso((String)vRowTemp.get(63));
               getIngreFamSelecc().setPtjDepen1_2(((Double)vRowTemp.get(64)).shortValue());
               getIngreFamSelecc().setPtjDepen3_4(((Double)vRowTemp.get(65)).shortValue());
               getIngreFamSelecc().setPtjDepen5_6(((Double)vRowTemp.get(66)).shortValue());
               getIngreFamSelecc().setPtjDepen7_8(((Double)vRowTemp.get(67)).shortValue());
               getIngreFamSelecc().setPtjDepen9(((Double)vRowTemp.get(68)).shortValue());
               //datos reclasificación socioeconómica
               getRazonRecla().setValor((String)vRowTemp.get(69)); 
               setsRazonRecla((String)vRowTemp.get(70));
               setObsAdicionales((String)vRowTemp.get(71));
               //TRABAJADORA SOCIAL REALIZA
               getOtrabSocCapt().setActividad((String)vRowTemp.get(72) +" "+ (String)vRowTemp.get(73)+" "+(String)vRowTemp.get(74));//nombre
               getOtrabSocCapt().setCedEsp((String)vRowTemp.get(75));//cedprof
               //buscaDocumentacionReclasificacionTS();
            }
        }
    }
    public void buscaDocumentacionReclasificacionTS() throws Exception{
        this.oDocumEntregados=null;
        Parametrizacion oRP=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        if( getPac().getFolioPaciente()==0 || getRegistro()==null){
            throw new Exception("NivelSocioEconomico.buscaDocumentacionReclasificacionTS: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from buscaDocumentacionReclasificacionTS("+getPac().getFolioPaciente()+"::BIGINT,'"+getRegistro()+"'::DATE);";
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null) {
                this.oDocumEntregados = new Parametrizacion[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    oRP=new Parametrizacion();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oRP.setValor((String)vRowTemp.get(2));
                    oRP.setTipoParametro((String)vRowTemp.get(3));
                    oRP.setClaveParametro((String)vRowTemp.get(4));
                    this.oDocumEntregados[i]=oRP;
                }
            }
        }
    }
    
    public Paciente getPac() {
        return oPac;
    }

    public void setPac(Paciente oPac) {
        this.oPac = oPac;
    }

    public Date getRegistro() {
        return dRegistro;
    }

    public void setRegistro(Date dRegistro) {
        this.dRegistro = dRegistro;
    }

    public Date getVigencia() {
        return dVigencia;
    }

    public void setVigencia(Date dVigencia) {
        this.dVigencia = dVigencia;
    }

    public int getPuntaje() {
        return npuntaje;
    }

    public void setPuntaje(int npuntaje) {
        this.npuntaje = npuntaje;
    }

    public Parametrizacion getNivelAlcanzado() {
        return oNivelAlcanzado;
    }

    public void setNivelAlcanzado(Parametrizacion oNivelAlcanzado) {
        this.oNivelAlcanzado = oNivelAlcanzado;
    }

    public PersonalHospitalario getOtrabSocCapt() {
        return otrabSocCapt;
    }

    public void setOtrabSocCapt(PersonalHospitalario otrabSocCapt) {
        this.otrabSocCapt = otrabSocCapt;
    }

    public PersonalHospitalario getOtrabSocModif() {
        return otrabSocModif;
    }

    public void setOtrabSocModif(PersonalHospitalario otrabSocModif) {
        this.otrabSocModif = otrabSocModif;
    }

    public boolean isCasoMedicoLegal() {
        return bCasoMedicoLegal;
    }

    public void setCasoMedicoLegal(boolean bCasoMedicoLegal) {
        this.bCasoMedicoLegal = bCasoMedicoLegal;
    }

    public Parametrizacion getAtencionPrioritaria() {
        return oAtencionPrioritaria;
    }

    public void setAtencionPrioritaria(Parametrizacion oAtencionPrioritaria) {
        this.oAtencionPrioritaria = oAtencionPrioritaria;
    }

    public String getOcupacion() {
        return sOcupacion;
    }

    public void setOcupacion(String sOcupacion) {
        this.sOcupacion = sOcupacion;
    }

    public String getReligion() {
        return sReligion;
    }

    public void setReligion(String sReligion) {
        this.sReligion = sReligion;
    }

    public Parametrizacion getNivMaxEstudios() {
        return oNivMaxEstudios;
    }

    public void setNivMaxEstudios(Parametrizacion oNivMaxEstudios) {
        this.oNivMaxEstudios = oNivMaxEstudios;
    }

    public boolean isNivMaxEstTerminado() {
        return bNivMaxEstTerminado;
    }

    public void setNivMaxEstTerminado(boolean bNivMaxEstTerminado) {
        this.bNivMaxEstTerminado = bNivMaxEstTerminado;
    }

    public String getCallenumTemp() {
        return sCallenumTemp;
    }

    public void setCallenumTemp(String sCallenumTemp) {
        this.sCallenumTemp = sCallenumTemp;
    }

    public String getColoniaTemp() {
        return sColoniaTemp;
    }

    public void setColoniaTemp(String sColoniaTemp) {
        this.sColoniaTemp = sColoniaTemp;
    }

    public Ubicacion getUbicTemp() {
        return oUbicTemp;
    }

    public void setUbicTemp(Ubicacion oUbicTemp) {
        this.oUbicTemp = oUbicTemp;
    }

    public String getTelTem() {
        return sTelTem;
    }

    public void setTelTem(String sTelTem) {
        this.sTelTem = sTelTem;
    }

    public String getCalleNumResp() {
        return sCalleNumResp;
    }

    public void setCalleNumResp(String sCalleNumResp) {
        this.sCalleNumResp = sCalleNumResp;
    }

    public String getColoniaResp() {
        return sColoniaResp;
    }

    public void setColoniaResp(String sColoniaResp) {
        this.sColoniaResp = sColoniaResp;
    }

    public Ubicacion getUbicResp() {
        return oUbicResp;
    }

    public void setUbicResp(Ubicacion oUbicResp) {
        this.oUbicResp = oUbicResp;
    }

    public String getTelRes() {
        return sTelRes;
    }

    public void setTelRes(String sTelRes) {
        this.sTelRes = sTelRes;
    }

    public IngresoFamiliar getIngreFamSelecc() {
        return oIngreFamSelecc;
    }

    public void setIngreFamSelecc(IngresoFamiliar oIngreFamSelecc) {
        this.oIngreFamSelecc = oIngreFamSelecc;
    }

    public short getColIngreFamSelecc() {
        return oColIngreFamSelecc;
    }

    public void setColIngreFamSelecc(short oColIngreFamSelecc) {
        this.oColIngreFamSelecc = oColIngreFamSelecc;
    }

    public EncuestaTrabsocPregBasicas[] getArrRespPregBasicas() {
        return arrRespPregBasicas;
    }

    public void setArrRespPregBasicas(EncuestaTrabsocPregBasicas[] arrRespPregBasicas) {
        this.arrRespPregBasicas = arrRespPregBasicas;
    }

    public String getObsAdicionales() {
        return sObsAdicionales;
    }

    public void setObsAdicionales(String sObsAdicionales) {
        this.sObsAdicionales = sObsAdicionales;
    }

    public Parametrizacion getRazonRecla() {
        return oRazonRecla;
    }

    public void setRazonRecla(Parametrizacion oRazonRecla) {
        this.oRazonRecla = oRazonRecla;
    }  

    public Parametrizacion[] getDocumEntregados() {
        return oDocumEntregados;
    }

    public void setDocumEntregados(Parametrizacion[] oDocumEntregados) {
        this.oDocumEntregados = oDocumEntregados;
    }

    public String getsRazonRecla() {
        return sRazonRecla;
    }

    public void setsRazonRecla(String sRazonRecla) {
        this.sRazonRecla = sRazonRecla;
    }
    
    
    
}

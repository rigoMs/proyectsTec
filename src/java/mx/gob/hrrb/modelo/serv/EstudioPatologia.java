package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.core.AntecGinecoObstetricos;
import mx.gob.hrrb.modelo.core.AntecNoPatologicos;
import mx.gob.hrrb.modelo.core.AntecPatologicos;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class EstudioPatologia extends EstudioRealizado implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private String sClaveUsuario;
private boolean bCitolAdecuada;
private String sCitAdecuada;
private Date dFechaAproxSgteConsul;
private Date dFechaDescMicro;
private Date dFechaEntrArchivo;
private Date dFechaInclusion;
private Date dFechaProcedimiento;
private Parametrizacion oCatGral;
private Parametrizacion oTipoBiopPzaQx;
private Parametrizacion oTipoCitol;
private String sDescripMacro;
private String sDescripMicro;
private String sNombreQuienEntregaMuestra;
private String sNotas;
private String sOtroTipoBiop;
private String sRazonCitolAdecuada;
private String sRazonPosNeg;
private Paciente oPaciente;
private Parametrizacion oSitAnatomico;
private Parametrizacion oNivelUrg;
private Parametrizacion oTipoProcedimiento;
private String sMaterialEnviado;
private String sDescripcionTecnica;
public static final String TAB_TIPO_PROCEDIM = "T99";
private String sNumeroEstudio;
private int nRecibioMuestra;
private Parametrizacion oSitGineco;
private Parametrizacion oAntecCitol;

    public EstudioPatologia(){
    oPaciente = new Paciente();
    oTipoProcedimiento = new Parametrizacion();
    oTipoCitol = new Parametrizacion();
    oCatGral = new Parametrizacion();
    HttpServletRequest req;
            req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                    sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();

            }
    }

    @Override
    public boolean buscar() throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("EstudioPatologia.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveEstudioPatologia();"; 
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

    public EstudioPatologia[] buscarSolPatologia(String sTipoTabla, String sClaveValor, Date fecha) throws Exception{
        EstudioPatologia arrRet[]=null, oEstPat=null;
        SimpleDateFormat oFec=new SimpleDateFormat("yyyy-MM-dd");
        ArrayList<EstudioPatologia> vObj=null;
        ArrayList rst=null;
        int i=0, nTam=0;
        String sQuery="";
        oAD=new AccesoDatos();
        sQuery="SELECT * FROM buscarSolPatologia('"+ sTipoTabla +"','"+ sClaveValor + "', '"+ oFec.format(fecha) +"')";
        System.out.println(sQuery);
        if(fecha == null){
            throw new Exception("EstudioPatologia: error de programación, faltan datos");
        }
        else{
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj=new ArrayList<EstudioPatologia>();
                for(i=0;i<rst.size();i++){
                    oEstPat=new EstudioPatologia();
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oEstPat.oPaciente=new Paciente();
                    oEstPat.oEpisodio=new EpisodioMedico();
                    oEstPat.setCatGral(new Parametrizacion());
                    oEstPat.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oEstPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                    oEstPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                    oEstPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                    oEstPat.setFechaSolicitud((Date)vRowTemp.get(4));
                    oEstPat.getCatGral().setValor((String)vRowTemp.get(5));
                    oEstPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(6));
                    oEstPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(7));
                    oEstPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(8));
                    ((Estudios)oEstPat.getServicioCobrable()).setClaveInterna(((Double)vRowTemp.get(9)).intValue());
                    oEstPat.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(10)).longValue());
                    oEstPat.setIdentificador(((Double)vRowTemp.get(11)).intValue());
                    vObj.add(oEstPat);
                }
                nTam=vObj.size();
                arrRet = new EstudioPatologia[nTam];

                for(i=0;i<nTam;i++){
                    arrRet[i]=vObj.get(i);
                }
            }
        }

        return arrRet;
    }

    public EstudioPatologia[] buscarReportes(int nClave, Date fecIni, Date fecFin) throws Exception{
            EstudioPatologia arrRet[]=null, oPat=null;
            ArrayList<EstudioPatologia> vObj=null;
            ArrayList rst=null;
            SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
            System.out.println("La clave del usuario es: " + sClaveUsuario);
            String sQuery="";
            int i=0, nTam=0;
            if(nClave == 1){
                sQuery="SELECT * FROM buscarReportesPatologia("+ nClave +", '"+ oFec.format(fecIni) +"','"+ oFec.format(fecFin) +"')";
            }else if (nClave == 2){
                sQuery="SELECT * FROM buscarReportesPatologia("+ nClave +", '"+ oFec.format(fecIni) +"','"+ oFec.format(fecFin) +"')";
            }else if (nClave == 3){
                sQuery="SELECT * FROM buscarReportesPatologia("+ nClave +", '"+ oFec.format(fecIni) +"','"+ oFec.format(fecFin) +"')";
            }else{
                throw new Exception("EstudioPatologia.buscarReportes: error, faltan datos");
            }
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj=new ArrayList<EstudioPatologia>();
                for(i=0;i<rst.size();i++){
                    oPat = new EstudioPatologia();
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oPat.oEpisodio =new EpisodioMedico();
                    oPat.oEpisodio.setPaciente(new Paciente());
                    oPat.setFechaEntrArchivo((Date)vRowTemp.get(0));
                    oPat.setNumeroEstudio((String)vRowTemp.get(1));
                    oPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(2));
                    oPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(3));
                    oPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(4));
                    oPat.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(5)).intValue());
                    oPat.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(6));
                    oPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(7));
                    oPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(8));
                    oPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(9));
                    oPat.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(10)).longValue());
                    oPat.setIdentificador(((Double)vRowTemp.get(11)).intValue());
                    vObj.add(oPat);
                }
                nTam=vObj.size();
                arrRet= new EstudioPatologia[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i]=vObj.get(i);
                }
            }
            return arrRet;
        }
        
    public EstudioPatologia buscarDetEstCervical(int nIdentificador) throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        String sQuery="";     
        EstudioPatologia oEstPat=null;
        System.out.println("Identificador " + nIdentificador);
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia:error de programación, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM  buscarDetSolCitCervical("+ nIdentificador +")";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() == 1){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                oEstPat =new EstudioPatologia();
                oEstPat.oEpisodio = new EpisodioMedico();
                oEstPat.setTipoCitol(new Parametrizacion());
                oEstPat.setSituacion(new Parametrizacion());
                oEstPat.oEpisodio.getPaciente().getExpediente().setAntecGinecoObstetricos(new AntecGinecoObstetricos());
                oEstPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstPat.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstPat.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(4));
                oEstPat.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(5));
                oEstPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(6));
                oEstPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(7));
                oEstPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(8));
                String edad=(String)vRowTemp.get(9);
                if(edad.compareTo("")!=0){
                     if(edad.substring(0,3).compareTo("000")!=0){
                         if(edad.charAt(0)=='0')
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                         else
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                     }else{
                         if(edad.substring(4,6).compareTo("00")!=0)
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                         else
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                     }
                 }
                oEstPat.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(10));
                oEstPat.oEpisodio.getCama().setNumero((String)vRowTemp.get(11));
                oEstPat.oEpisodio.getProceRe1().setFechaRealizacion((Date)vRowTemp.get(12));
                oEstPat.setFechaRecepcion((Date)vRowTemp.get(13));
                ((Estudios)oEstPat.getServicioCobrable()).setClaveStudio((String)vRowTemp.get(14));
                oEstPat.oTipoCitol.setValor((String)vRowTemp.get(15));
                oEstPat.oSituacion.setValor((String)vRowTemp.get(16));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecGinecoObstetricos().setIVSA(((Double)vRowTemp.get(17)).intValue());
                oEstPat.setPaciente(oEpisodio.getPaciente());
                bRet=true;
            }
        }

        return oEstPat;
    }

    public EstudioPatologia buscarDetLiqCor(int nIdentificador) throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        String sQuery="";  
        EstudioPatologia oEstPat=null;
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia: error de programación, faltan datos");
        }
        else{
            sQuery="SELECT * FROM buscarDetalleSolLiqCor("+ nIdentificador +");";
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size() == 1){
                ArrayList vRowTemp=(ArrayList)rst.get(0);
                oEstPat =new EstudioPatologia();
                oEstPat.oPaciente=new Paciente();
                oEstPat.oEpisodio = new EpisodioMedico();
                oEstPat.setSitAnatomico(new Parametrizacion());
                oEstPat.setTipoCitol(new Parametrizacion());
                oEstPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstPat.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                String edad=(String)vRowTemp.get(4);
                if(edad.compareTo("")!=0){
                     if(edad.substring(0,3).compareTo("000")!=0){
                         if(edad.charAt(0)=='0')
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                         else
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                     }else{
                         if(edad.substring(4,6).compareTo("00")!=0)
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                         else
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                     }
                 }
                oEstPat.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(5));
                oEstPat.oEpisodio.getCama().setNumero((String)vRowTemp.get(6));
                oEstPat.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(7));
                oEstPat.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(8));
                oEstPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(9));
                oEstPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(10));
                oEstPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(11));
                oEstPat.oEpisodio.getProceRe1().setFechaRealizacion((Date)vRowTemp.get(12));
                oEstPat.setFechaRecepcion((Date)vRowTemp.get(13));
                ((Estudios)oEstPat.getServicioCobrable()).setClaveStudio((String)vRowTemp.get(14));
                oEstPat.oTipoCitol.setValor((String)vRowTemp.get(15));
                oEstPat.oSitAnatomico.setValor((String)vRowTemp.get(16));
                bRet=true;
            }
        }

        return oEstPat;
    }

    public EstudioPatologia buscarDetBiopsia(int nIdentificador) throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        EstudioPatologia oEstPat=null;
        String sQuery="";
        System.out.println("El identificador es: " + nIdentificador);
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia: error de programación, faltan datos");
        }
        else{
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                sQuery="SELECT * FROM buscarDetSolBioPieQui("+ nIdentificador +")";
                System.out.println(sQuery);
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size()==1){
                oEstPat = new EstudioPatologia();
                ArrayList vRowTemp=(ArrayList)rst.get(0);
                oEstPat.oPaciente = new Paciente();
                oEstPat.oEpisodio = new EpisodioMedico();
                oEstPat.oEpisodio.setPaciente(oPaciente);
                oEstPat.oEpisodio.getPaciente().setExpediente(new Expediente());
                oEstPat.oEpisodio.getPaciente().getExpediente().setAntecNoPatologicos(new AntecNoPatologicos());
                oEstPat.oEpisodio.getPaciente().getExpediente().setAntecPatologicos(new AntecPatologicos());
                oEstPat.setSitAnatomico(new Parametrizacion());
                oEstPat.setTipoBiopPzaQx(new Parametrizacion());
                oEstPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstPat.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                String edad=(String)vRowTemp.get(4);
                if(edad.compareTo("")!=0){
                     if(edad.substring(0,3).compareTo("000")!=0){
                         if(edad.charAt(0)=='0')
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                         else
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                     }else{
                         if(edad.substring(4,6).compareTo("00")!=0)
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                         else
                             oEstPat.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                     }
                 }
                oEstPat.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(5));
                oEstPat.oEpisodio.getCama().setNumero((String)vRowTemp.get(6));
                oEstPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(7));
                oEstPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(8));
                oEstPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(9));
                oEstPat.oEpisodio.getProceRe1().setFechaRealizacion((Date)vRowTemp.get(10));
                oEstPat.setFechaRecepcion((Date)vRowTemp.get(11));
                oEstPat.setFechaAproxSgteConsul((Date)vRowTemp.get(12));
                ((Estudios)oEstPat.getServicioCobrable()).setClaveStudio((String)vRowTemp.get(13));
                oEstPat.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(14));
                oEstPat.oSitAnatomico.setValor((String)vRowTemp.get(15));
                String sEspe = ((String)vRowTemp.get(16)), sEspec;
                sEspec = sEspe.equals("null") ? "":sEspe;
                oEstPat.setEspecimenMuestraTejido(sEspec);
                oEstPat.getTipoBiopPzaQx().setValor((String)vRowTemp.get(17));
                oEstPat.setImpresionDiagnostica((String)vRowTemp.get(18));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setAlergias((String)vRowTemp.get(19));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setCardioPatias((String)vRowTemp.get(20));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setQuirurgico((String)vRowTemp.get(21));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setTransfusion((String)vRowTemp.get(22));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setDiabetico((String)vRowTemp.get(23));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setCardioVasculares((String)vRowTemp.get(24));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setTraumat((String)vRowTemp.get(25));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setHTA((String)vRowTemp.get(26));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setReligion((String)vRowTemp.get(27));
                String sTab = ((String)vRowTemp.get(28));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setTabaquismo(sTab.equals("S"));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setEscolaridad((String)vRowTemp.get(29));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setOcupacion((String)vRowTemp.get(30));
                String sAlc = ((String)vRowTemp.get(31));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setAlcoholismo(sAlc.equals("S"));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setDrogas((String)vRowTemp.get(32));
                String sAguaP = ((String)vRowTemp.get(33));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setAguaPotable(sAguaP.equals("S"));
                String sElect = ((String)vRowTemp.get(34));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setElectricidad(sElect.equals("S"));
                String sDre = ((String)vRowTemp.get(35));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setDrenaje(sDre.equals("S"));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setAnimales((String)vRowTemp.get(36));
                String sCH = ((String)vRowTemp.get(37));
                if (sCH!= null && !sCH.equals(""))
                    oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setTipoCasaHab(sCH.charAt(0));
                String sServS = ((String)vRowTemp.get(38));
                if (sServS!= null && !sServS.equals(""))
                    oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setServSanit(sServS.equals("S"));
                oEstPat.oEpisodio.getPaciente().getExpediente().getAntecNoPatologicos().setOtro((String)vRowTemp.get(39));
                oEstPat.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(40));
                String sExpF = ((String)vRowTemp.get(41)), sExplo;
                sExplo = sExpF.equals("null") ? "": sExpF;
                oEstPat.oEpisodio.getNotaMedHrrb().setExploracionFisica(sExplo);               
                bRet=true;
            }
        }
        return oEstPat;
    }

    public EstudioPatologia buscarDetalleRepCerv(int nIdentificador) throws Exception{
        EstudioPatologia oPat=null;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador==0){
            throw new Exception("EstudioPatologia.buscarDetalleRepCerv: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscarDetalleRepCerv("+ nIdentificador +");";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()==1){
                oPat=new EstudioPatologia();
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                oPat.oEpisodio=new EpisodioMedico();
                oPat.setTipoCitol(new Parametrizacion());
                oPat.setNumeroEstudio((String)vRowTemp.get(0));
                oPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                oPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                oPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                oPat.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                oPat.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(5));
                oPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(6));
                oPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(7));
                oPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(8));
                oPat.getTipoCitol().setValor((String)vRowTemp.get(9));
                int nCitA= ((Double)vRowTemp.get(10)).intValue();
                oPat.sCitAdecuada = nCitA == 0 ? "NO ADECUADA":"ADECUADA";
                oPat.setRazonCitolAdecuada((String)vRowTemp.get(11));
                oPat.getCatGral().setValor((String)vRowTemp.get(12));
                oPat.setRazonPosNeg((String)vRowTemp.get(13));
                oPat.setNotas((String)vRowTemp.get(14));     
            }
        }
        return oPat;
    }

    public EstudioPatologia buscarDetalleRepLiq(int nIdentificador) throws Exception{
        EstudioPatologia oPat=null;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.buscarDetalleRepLiq: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscarDetalleRepLiq("+ nIdentificador +");";
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()==1){
                oPat=new EstudioPatologia();
                ArrayList vRowTemp=(ArrayList)rst.get(0);
                oPat = new EstudioPatologia();
                oPat.oEpisodio = new EpisodioMedico();
                oPat.setTipoProcedimiento(new Parametrizacion());
                oPat.setCatGral(new Parametrizacion());
                oPat.setNumeroEstudio((String)vRowTemp.get(0));
                oPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                oPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                oPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                oPat.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                oPat.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(5));
                oPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(6));
                oPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(7));
                oPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(8));
                oPat.getTipoProcedimiento().setValor((String)vRowTemp.get(9));
                oPat.setFechaRecepcion((Date)vRowTemp.get(10));
                oPat.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(11));
                oPat.setMaterialEnviado((String)vRowTemp.get(12));
                oPat.setDescripcionTecnica((String)vRowTemp.get(13));
                oPat.setDescripMacro((String)vRowTemp.get(14));
                oPat.setImpresionDiagnostica((String)vRowTemp.get(15));
            }
        }
        return oPat;
    }

    public EstudioPatologia buscarDetalleRepBiop(int nIdentificador) throws Exception{
        EstudioPatologia oPat = null;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.buscarDetalleRepBiop: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscarDetalleRepBiop("+ nIdentificador +");";
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size()==1){
                oPat = new EstudioPatologia();
                ArrayList vRowTemp=(ArrayList)rst.get(0);
                oPat.oEpisodio = new EpisodioMedico();
                oPat.setTipoProcedimiento(new Parametrizacion());
                oPat.setNumeroEstudio((String)vRowTemp.get(0));
                oPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                oPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                oPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                oPat.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                oPat.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(5));
                oPat.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(6));
                oPat.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(7));
                oPat.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(8));
                oPat.getTipoProcedimiento().setValor((String)vRowTemp.get(9));
                oPat.setFechaRecepcion((Date)vRowTemp.get(10));
                oPat.setDescripMicro((String)vRowTemp.get(11));
                oPat.setDescripMacro((String)vRowTemp.get(12));
                oPat.setImpresionDiagnostica((String)vRowTemp.get(13));
            }
        }
        return oPat;
    }

    public EstudioPatologia[] buscarProcPat(int nClave) throws Exception{
         EstudioPatologia arrRet[] = null, oPat=null;
         ArrayList<EstudioPatologia> vObj=null;
         ArrayList rst=null;
         String sQuery="";
         int i=0, nTam=0;
         if(nClave == 1){
             sQuery="SELECT * FROM buscarTipoProc();";
         }else if(nClave == 2){
             sQuery="SELECT * FROM buscarTipoProc2();";
         }
         System.out.println(sQuery);
         oAD = new AccesoDatos();
         if(oAD.conectar()){
             rst=oAD.ejecutarConsulta(sQuery);
             oAD.desconectar();
         }
         if(rst != null){
             vObj = new ArrayList<EstudioPatologia>();
             for(i=0;i<rst.size();i++){
                 oPat = new EstudioPatologia();
                 ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                 oPat.setTipoProcedimiento(new Parametrizacion());
                 oPat.getTipoProcedimiento().setClaveParametro((String)vRowTemp.get(0));
                 oPat.getTipoProcedimiento().setValor((String)vRowTemp.get(1));
                 vObj.add(oPat);
             }

             nTam=vObj.size();
             arrRet = new EstudioPatologia[nTam];

             for(i=0;i<nTam;i++){
                 arrRet[i] = vObj.get(i);
             }
         }
         return arrRet;
     }

    public EstudioPatologia[] buscarTipoCitologia() throws Exception{
         EstudioPatologia arrRet[]=null, oPat=null;
         ArrayList rst=null;
         ArrayList<EstudioPatologia> vObj=null;
         String sQuery="";
         int i=0, nTam=0;
         sQuery="SELECT * FROM buscarTipoCitologia()";
         oAD=new AccesoDatos();
         if(oAD.conectar()){
             rst=oAD.ejecutarConsulta(sQuery);
         }
         if(rst!=null){
             vObj=new ArrayList<EstudioPatologia>();
             for(i=0;i<rst.size();i++){
                 oPat = new EstudioPatologia();
                 oPat.setTipoCitol(new Parametrizacion());
                 ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                 oPat.getTipoCitol().setClaveParametro((String)vRowTemp.get(0));
                 oPat.getTipoCitol().setValor((String)vRowTemp.get(1));
                 vObj.add(oPat);
             }
             nTam=vObj.size();
             arrRet = new EstudioPatologia[nTam];

             for(i=0;i<nTam;i++){
                 arrRet[i] = vObj.get(i);
             }
         }

         return arrRet;
     }

    public EstudioPatologia[] buscarCatGeneral() throws Exception{
            EstudioPatologia arrRet[]=null, oPat=null;
            ArrayList rst=null;
            ArrayList<EstudioPatologia> vObj=null;
            String sQuery="";
            int i=0, nTam=0;
            sQuery="SELECT * FROM buscarCatGeneralCitologia()";
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
            }
            if(rst!=null){
                vObj=new ArrayList<EstudioPatologia>();
                for(i=0;i<rst.size();i++){
                    oPat = new EstudioPatologia();
                    oPat.setCatGral(new Parametrizacion());
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oPat.getCatGral().setClaveParametro((String)vRowTemp.get(0));
                    oPat.getCatGral().setValor((String)vRowTemp.get(1));
                    vObj.add(oPat);
                }
                nTam=vObj.size();
                arrRet = new EstudioPatologia[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            
            return arrRet;
        }
        
    public EstudioPatologia[] buscarSitiosAnatomicos() throws Exception{
        EstudioPatologia arrRet[]=null, oPat=null;
        ArrayList<EstudioPatologia> vObj = null;
        ArrayList rst=null;
        String sQuery="";
        int i=0, nTam=0;
        sQuery = "SELECT * FROM buscarSitiosAnatomicos();";
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj = new ArrayList<EstudioPatologia>();
            for(i=0;i<rst.size();i++){
                oPat = new EstudioPatologia();
                ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                oPat.setSitAnatomico(new Parametrizacion());
                oPat.getSitAnatomico().setClaveParametro((String)vRowTemp.get(0));
                oPat.getSitAnatomico().setValor((String)vRowTemp.get(1));
                vObj.add(oPat);
            }
            nTam = vObj.size();
            arrRet = new EstudioPatologia[nTam];

            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        }
        return arrRet;
    }

    public EstudioPatologia[] buscarPacientesExterno(String sNom, String sApPat, String sApMat) throws Exception{
        EstudioPatologia arrRet[]=null, oEstPat=null;
        ArrayList<EstudioPatologia> vObj=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0, nTam=0;
        System.out.println("Nombre del Paciente: " + sNom);
        if(sNom.equals("") && sApPat.equals("")){
            throw new Exception("EstudioPatologia.buscarPacientesExternos: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarPacienteExternosPatologia('"+ sNom +"','"+ sApPat +"','"+ sApMat +"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<EstudioPatologia>();
                for(i=0;i<rst.size();i++){
                    oEstPat = new EstudioPatologia();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oEstPat.oEpisodio = new EpisodioMedico();
                    oEstPat.oEpisodio.setPaciente(new Paciente());
                    oEstPat.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oEstPat.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oEstPat.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oEstPat.setEspecimenMuestraTejido((String)vRowTemp.get(3));
                    oEstPat.setFechaRecepcion((Date)vRowTemp.get(4));
                    oEstPat.setFechaProgramado((Date)vRowTemp.get(5));
                    oEstPat.setIdentificador(((Double)vRowTemp.get(6)).intValue());
                    vObj.add(oEstPat);
                }
                nTam = vObj.size();
                arrRet = new EstudioPatologia[nTam];

                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }

        return arrRet;
    }

    public EstudioPatologia buscarDetallePacExt(int nIdentificador) throws Exception{
        EstudioPatologia oEstP = null;
        ArrayList rst =null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.buscarDetallePacExt: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarDetallePacienteExternoPat("+ nIdentificador +");";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size()==1){
                oEstP  = new EstudioPatologia();
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                oEstP.oEpisodio = new EpisodioMedico();
                oEstP.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstP.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstP.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstP.setEspecimenMuestraTejido((String)vRowTemp.get(3));
                oEstP.setFechaRecepcion((Date)vRowTemp.get(4));
                oEstP.setFechaProgramado((Date)vRowTemp.get(5));
                oEstP.setNombreQuienEntregaMuestra((String)vRowTemp.get(6));
            }
        }
        return oEstP;
    }

    public int modificarDatosReporteLiqCor(int nIdentificador, Date dFecInc, Date dFecPro) throws Exception{
        int i=0;
        ArrayList rst = null;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.modificarDatosReporteLiqCor: error, faltan datos de sesión");
        }else{
            //this.setTipoProcedimiento(new Parametrizacion());
            sQuery ="SELECT * FROM modificarDatosReporteLiquidosCorporales('"+ sUsuarioFirmado +"'::character varying,"+ nIdentificador +"::bigint, ";
            sQuery = sQuery + "'"+ getTipoProcedimiento().getClaveParametro() +"'::character, ";
            sQuery = sQuery + "'"+  getMaterialEnviado() +"'::text,'"+ getDescripcionTecnica() +"'::text,'"+ getDescripMicro() +"'::text, '"+ oFec.format(dFecPro) +"'::Date, ";
            sQuery = sQuery + "'"+ oFec.format(dFecInc) +"'::Date);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    i = ((Double)vRowTemp.get(0)).intValue();
                }
            }   
        }
        return i;
    }

    public int modificarDatosReporteCitCervical(int nIdentificador) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.modificarDatosReporteCitCervical: error, faltan datos");
        }
        else{
            sQuery="SELECT * FROM modificarReporteCitCer('"+ sUsuarioFirmado +"'::character varying, "+ nIdentificador +"::bigint, '"+ getTipoCitol().getClaveParametro() +"'::character varying, ";
            sQuery = sQuery + ""+ getCitAdecuada() +"::smallint, '"+ getRazonCitolAdecuada() +"'::text, '"+ getCatGral().getClaveParametro() +"'::character varying, '"+ getRazonPosNeg() +"'::character varying, '"+ getNotas() +"'::character varying);";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    i = ((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    public int modificarReporteBiopsia(int nIdentificador) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.modificarReporteBiopsia: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM modificarReporteBiopsiaPieza('"+ sUsuarioFirmado +"'::character varying, "+ nIdentificador +"::bigint, ";
            sQuery = sQuery + "'"+ getDescripMacro() +"'::text,'"+ 
                    getDescripMicro() +"'::text, '"+ 
                    getImpresionDiagnostica() +"'::text,'"+ 
                    oFec.format(getFechaInclusion()) +"','"+ 
                    oFec.format(getFechaProcedimiento()) +"')";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()==1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    i = ((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    public int modificarRecepcionPieza(int nIdentificador) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.modificarRecepcionPieza: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM modificarRecepcionPiezaPatologia('"+ sUsuarioFirmado +"', "+ nIdentificador +",'"+ getNumeroEstudio() +"', ";
            sQuery = sQuery + "'"+ getEspecimenMuestraTejido() +"', '"+ 
                    oFec.format(getFechaRecepcion()) +"');";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    i=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    public int modificarEntregaArchivo(int nIdentificador, Date fecha, int nCveEst, long lFolioPac) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        if(nIdentificador == 0){
            throw new Exception("EstudioPatologia.modificarEntregarArchivo: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM modificarEntregaArchivo('"+ sUsuarioFirmado +"'::character varying, "+ nIdentificador +"::bigint, '"+ oFec.format(fecha) +"',"+ nCveEst +"::smallint,"+ lFolioPac +"::bigint)";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    i =((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    public PersonalHospitalario buscarNombreUsuario(String sIdUsuario) throws Exception {
        PersonalHospitalario oPersonal=null;
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (sIdUsuario.equals("")) { 
            throw new Exception("EstudioPatologia.buscarNombreUsuario: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscarDatosUsuario('" + sIdUsuario + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                oPersonal = new PersonalHospitalario();
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                oPersonal.setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                oPersonal.setNombres((String)vRowTemp.get(1));
                oPersonal.setApPaterno((String)vRowTemp.get(2));
                oPersonal.setApMaterno((String)vRowTemp.get(3));
            }
        }
        return oPersonal;
    }

    @Override
    public EstudioPatologia[] buscarTodos() throws Exception{
    EstudioPatologia arrRet[]=null, oEstudioPatologia=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosEstudioPatologia();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new EstudioPatologia[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                    } 
            } 
            return arrRet; 
    } 

    public int insertaPacienteExterno(int sClaveEst, String sClaveDiag, int nNoTarj) throws Exception{
            int i=0;
            ArrayList rst=null;
            String sQuery="";
            SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
            if(nNoTarj == 0){
                throw new Exception("EstudioPatologia.insertaPacienteExterno: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM insertaPacienteExternoPatologia('"+ sUsuarioFirmado +"'::character varying, '"+ getPaciente().getNombres() +"'::character varying, ";
                sQuery = sQuery + "'"+ getPaciente().getApPaterno() +"'::character varying,'"+ getPaciente().getApMaterno() +"'::character varying,'"+ getPaciente().getSexoP() +"'::character, "+ nNoTarj +"::integer, ";
                sQuery = sQuery + "'"+ sClaveDiag +"'::char(6), '"+ oFec.format(getEpisodio().getPaciente().getFechaNac()) +"'::Date,";
                sQuery = sQuery + "'"+ oFec.format(getFechaRecepcion()) +"'::Date, '"+ 
                        oFec.format(getFechaProgramado()) +"'::Date, ";
                sQuery = sQuery + "'"+ getNombreQuienEntregaMuestra() +"'::text,'"+ 
                        getEspecimenMuestraTejido() +"'::character varying,"+ sClaveEst +"::smallint);";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst != null && rst.size()==1){
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        i=((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return i;
        }
        
    
    @Override
    public int insertar() throws Exception{
    ArrayList rst = null;
    int nRet = -1;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("EstudioPatologia.insertar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaEstudioPatologia('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("EstudioPatologia.modificar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaEstudioPatologia('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("EstudioPatologia.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaEstudioPatologia('"+sUsuarioFirmado+"');"; 
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
        
    ////////////////////////////////////////////////////////////////////////////////
    //órdenes de servicio
    public EstudioPatologia[] buscarEstudiosPatBiop(String EstPat) throws Exception {
        EstudioPatologia arrRet[] = null, oEst = null;
        ArrayList rst = null;
        ArrayList<EstudioPatologia> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarestudiosdesolicitudpatologiabiopsia('" + EstPat + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<EstudioPatologia>();
            for (i = 0; i < rst.size(); i++) {
                oEst = new EstudioPatologia();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEst.getEstudio().setConcepto(((String) vRowTemp.get(0)).trim());
                vObj.add(oEst);
            }
            nTam = vObj.size();
            arrRet = new EstudioPatologia[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public boolean buscarClavesEstudiosPatoBiop() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscarestudiospatologiabiopsiaclaves('" + this.getEstudio().getConcepto() + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.getEstudio().setClaveInterna(((Double) vRowTemp.get(0)).intValue());
            this.getEstudio().setClaveStudio(((String) vRowTemp.get(1)).trim());
            bRet = true;
        }
        return bRet;
    }

    public List<EstudioPatologia> getListaEstudiosPatBiop(String txt) {
        List<EstudioPatologia> lListaEst = null;
        try {
            lListaEst = new ArrayList<EstudioPatologia>(Arrays.asList(
                    (new EstudioPatologia()).buscarEstudiosPatBiop(txt)));
        } catch (Exception ex) {
            Logger.getLogger(EstudioPatologia.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaEst;
    }

    public List<String> completarEstPatBiop(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<EstudioPatologia> lis = getListaEstudiosPatBiop(sTxt);
        for (EstudioPatologia li : lis) {
            if (sp_ascii(li.getEstudio().getConcepto()).contains(sTxt)) {
                arrRet.add(li.getEstudio().getConcepto());
            }
        }
        return arrRet;
    }

    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }

    public int insertaSolPatBiopPzaQx() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.getTipoBiopPzaQx().getClaveParametro() == null && this.getSitAnatomico().getClaveParametro() == null) {   //completar llave
            throw new Exception("EstudioPatologia.insertar: error de programación, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sQuery = "SELECT * FROM insertasolicitudpatobiopsia('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + getEstudio().getClaveInterna() + "::smallint,'"
                    + getImpresionDiagnostica() + "'::character varying,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEspecimenMuestraTejido() + "'::character varying,'"
                    + getEpisodio().getDiagIngreso().getClave() + "','"
                    + getTipoBiopPzaQx().getTipoParametro() + "','"
                    + getTipoBiopPzaQx().getClaveParametro() + "','"
                    + format.format(dFechaAproxSgteConsul) + "'::timestamp without time zone,'"
                    + getSitAnatomico().getTipoParametro() + "','"
                    + getSitAnatomico().getClaveParametro() + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    public int insertaSolPatCitCerv() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.getAntecCitol().getClaveParametro() == null && this.getSitGineco().getClaveParametro() == null) {   //completar llave
            throw new Exception("EstudioPatologia.insertar: error de programación, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            sQuery = "SELECT * FROM insertasolicitudpatocervvag('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + 385 + "::smallint,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEpisodio().getDiagIngreso().getClave() + "','"
                    + getAntecCitol().getTipoParametro() + "','"
                    + getAntecCitol().getClaveParametro() + "','"
                    + getSitGineco().getTipoParametro() + "','"
                    + getSitGineco().getClaveParametro() + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    public int insertaSolPatLiqCorp() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("EstudioPatologia.insertar: error de programación, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sQuery = "SELECT * FROM insertasolicitudpatoliqcorp('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + 386 + "::smallint,'"
                    + getImpresionDiagnostica() + "'::character varying,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEpisodio().getDiagIngreso().getClave() + "','"
                    + getTipoCitol().getTipoParametro() + "','"
                    + getTipoCitol().getClaveParametro() + "','"
                    + getSitAnatomico().getTipoParametro() + "','"
                    + getSitAnatomico().getClaveParametro() + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    ////////////////////////////////////////////////////////////////////////////////  
        
        
	public boolean getCitolAdecuada() {
	return bCitolAdecuada;
	}

	public void setCitolAdecuada(boolean valor) {
	bCitolAdecuada=valor;
	}

	public Date getFechaAproxSgteConsul() {
	return dFechaAproxSgteConsul;
	}

	public void setFechaAproxSgteConsul(Date valor) {
	dFechaAproxSgteConsul=valor;
	}

	public Date getFechaDescMicro() {
	return dFechaDescMicro;
	}

	public void setFechaDescMicro(Date valor) {
	dFechaDescMicro=valor;
	}

	public Date getFechaEntrArchivo() {
	return dFechaEntrArchivo;
	}

	public void setFechaEntrArchivo(Date valor) {
	dFechaEntrArchivo=valor;
	}

	public Date getFechaInclusion() {
	return dFechaInclusion;
	}

	public void setFechaInclusion(Date valor) {
	dFechaInclusion=valor;
	}

	public Parametrizacion getCatGral() {
	return oCatGral;
	}

	public void setCatGral(Parametrizacion valor) {
	oCatGral=valor;
	}

	public Parametrizacion getTipoBiopPzaQx() {
	return oTipoBiopPzaQx;
	}

	public void setTipoBiopPzaQx(Parametrizacion valor) {
	oTipoBiopPzaQx=valor;
	}

	public Parametrizacion getTipoCitol() {
	return oTipoCitol;
	}

	public void setTipoCitol(Parametrizacion valor) {
	oTipoCitol=valor;
	}

	public String getDescripMacro() {
	return sDescripMacro;
	}

	public void setDescripMacro(String valor) {
	sDescripMacro=valor;
	}

	public String getDescripMicro() {
	return sDescripMicro;
	}

	public void setDescripMicro(String valor) {
	sDescripMicro=valor;
	}

	public String getNombreQuienEntregaMuestra() {
	return sNombreQuienEntregaMuestra;
	}

	public void setNombreQuienEntregaMuestra(String valor) {
	sNombreQuienEntregaMuestra=valor;
	}

	public String getNotas() {
	return sNotas;
	}

	public void setNotas(String valor) {
	sNotas=valor;
	}

	public String getOtroTipoBiop() {
	return sOtroTipoBiop;
	}

	public void setOtroTipoBiop(String valor) {
	sOtroTipoBiop=valor;
	}

	public String getRazonCitolAdecuada() {
	return sRazonCitolAdecuada;
	}

	public void setRazonCitolAdecuada(String valor) {
	sRazonCitolAdecuada=valor;
	}

	public String getRazonPosNeg() {
	return sRazonPosNeg;
	}

	public void setRazonPosNeg(String valor) {
	sRazonPosNeg=valor;
	}

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    @Override
    public ServicioRealizado getServReal() {
        return this;
    }

    public Parametrizacion getSitAnatomico() {
        return oSitAnatomico;
    }

    public void setSitAnatomico(Parametrizacion oSitAnatomico) {
        this.oSitAnatomico = oSitAnatomico;
    }

    public Parametrizacion getNivelUrg() {
        return oNivelUrg;
    }

    public void setNivelUrg(Parametrizacion oNivelUrg) {
        this.oNivelUrg = oNivelUrg;
    }

    public String getMaterialEnviado() {
        return sMaterialEnviado;
    }

    public void setMaterialEnviado(String sMaterialEnviado) {
        this.sMaterialEnviado = sMaterialEnviado;
    }

    public String getDescripcionTecnica() {
        return sDescripcionTecnica;
    }

    public void setDescripcionTecnica(String sDescripcionTecnica) {
        this.sDescripcionTecnica = sDescripcionTecnica;
    }

    public Date getFechaProcedimiento() {
        return dFechaProcedimiento;
    }

    public void setFechaProcedimiento(Date dFechaProcedimiento) {
        this.dFechaProcedimiento = dFechaProcedimiento;
    }

    public String getCitAdecuada() {
        return sCitAdecuada;
    }

    public void setCitAdecuada(String sCitAdecuada) {
        this.sCitAdecuada = sCitAdecuada;
    }

    public String getNumeroEstudio() {
        return sNumeroEstudio;
    }

    public void setNumeroEstudio(String sNumeroEstudio) {
        this.sNumeroEstudio = sNumeroEstudio;
    }

    public String getClaveUsuario() {
        return sClaveUsuario;
    }

    public void setClaveUsuario(String sClaveUsuario) {
        this.sClaveUsuario = sClaveUsuario;
    }

    public int getRecibioMuestra() {
        return nRecibioMuestra;
    }

    public void setRecibioMuestra(int nRecibioMuestra) {
        this.nRecibioMuestra = nRecibioMuestra;
    }

    public Parametrizacion getSitGineco() {
        return oSitGineco;
    }

    public void setSitGineco(Parametrizacion oSitGineco) {
        this.oSitGineco = oSitGineco;
    }

    public Parametrizacion getAntecCitol() {
        return oAntecCitol;
    }

    public void setAntecCitol(Parametrizacion oAntecCitol) {
        this.oAntecCitol = oAntecCitol;
    }
} 

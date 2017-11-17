package mx.gob.hrrb.modelo.serv;

/**
 *
 * @author pablo, Rafa
 */

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import mx.gob.hrrb.modelo.core.AntecPatologicos;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.DiagnosticoCIE10;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Medico;


public class EstudioRealImagen extends EstudioRealizado implements Serializable{
    private boolean bPortatil;
    private Parametrizacion oTipoPlaca;
    private Parametrizacion oRegionSolicitada;
    private Parametrizacion oMedioContraste;
    private String sUsuarioFirmado="";
    private AccesoDatos oAD=null;
    private EstudioImagen oEstImg=null;
    private short nCantMedio;
    private AreaServicioHRRB oArea=null;
    private Date fechaInicio=null;
    private Date fechaFin=null;
    private String sPortat;
    private String sTransoperatorioProbable;
    private String sEstPortatil;
    private Parametrizacion oImagenRegion;
    public static final String RUTA_ARCHIVO = "/resources/estudios/imagenologia/";
    
    public EstudioRealImagen(){
    oArea=new AreaServicioHRRB();
    HttpServletRequest req;
            req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                    sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
            }
    }
    
    //Buscar estudios de Imagenología por fecha
        public EstudioRealImagen[] buscarTodosImagen(int nClave, Date fecIni, Date fecFin) throws Exception{
	EstudioRealImagen arrRet[]=null, oEstRealImagen=null;
        ArrayList<EstudioRealImagen> vObj=null;
        ArrayList rst=null;
        int i=0, nTam=0;
	String sQuery = "";
        Date f1=fecIni;
        Date f2=fecFin;
        
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fecha2= new SimpleDateFormat("yyyy-MM-dd");
        if(nClave == 0 && f1 == null && f2 == null){
            throw new Exception("EstudioRealImagen.buscarTodosImagen: Error de programación, faltan datos");
        }
        else{
            //System.out.println("La clave es " + this.oArea.getClave());
            //System.out.println("Fecha 1: " + fecha.format(f1));
            //System.out.println("Fecha 2: " + fecha.format(f2));
            sQuery = "SELECT * FROM buscatodosestrealimagen("+ nClave +",'"+ fecha.format(f1) +"', '" + fecha2.format(f2) +"');"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
		rst = oAD.ejecutarConsulta(sQuery); 
		oAD.desconectar(); 
            }
            if (rst != null) {
                vObj=new ArrayList<EstudioRealImagen>();
                for(i=0;i<rst.size();i++){
                    oEstRealImagen = new EstudioRealImagen();
                    oEstRealImagen.oEpisodio = new EpisodioMedico();
                    oEstRealImagen.oEpisodio.setExpediente(new Expediente());
                    oEstRealImagen.oEpisodio.setPaciente(new Paciente());
                    oEstRealImagen.setSitPago(new Parametrizacion());
                    oEstRealImagen.setSituacion(new Parametrizacion());
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oEstRealImagen.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oEstRealImagen.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oEstRealImagen.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oEstRealImagen.oEpisodio.getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oEstRealImagen.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(4));
                    oEstRealImagen.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(5));
                    oEstRealImagen.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(6));
                    oEstRealImagen.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                    ((Estudios)oEstRealImagen.getServicioCobrable()).setConcepto((String)vRowTemp.get(8));
                    oEstRealImagen.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(9));
                    oEstRealImagen.getSitPago().setValor((String)vRowTemp.get(10));
                    oEstRealImagen.getSituacion().setValor((String)vRowTemp.get(11));
                    oEstRealImagen.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(12)).longValue());
                    oEstRealImagen.setIdentificador(((Double)vRowTemp.get(13)).intValue());
                    vObj.add(oEstRealImagen);      
            }
            nTam=vObj.size();
                arrRet = new EstudioRealImagen[nTam];
                
            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        } 
        }
                
        return arrRet; 
    }

        
    public EstudioRealImagen[] buscarSolPendImagen(int nClave, Date fecIni, Date fecFin) throws Exception{
	EstudioRealImagen arrRet[]=null, oEstRealImagen=null;
        ArrayList<EstudioRealImagen> vObj=null;
        ArrayList rst=null;
        int i=0, nTam=0;
	String sQuery = "";
        Date f1=fecIni;
        Date f2=fecFin;
        
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fecha2= new SimpleDateFormat("yyyy-MM-dd");
        if(nClave == 0 && f1 == null && f2 == null){
            throw new Exception("EstudioRealImagen.buscarSolPendImagen: Error de programación, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM buscarSolPendEstImagen("+ nClave +"::smallint,'"+ fecha.format(f1) +"', '" + fecha2.format(f2) +"');"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
		rst = oAD.ejecutarConsulta(sQuery); 
		oAD.desconectar(); 
            }
            if (rst != null) {
                vObj=new ArrayList<EstudioRealImagen>();
                for(i=0;i<rst.size();i++){
                    oEstRealImagen = new EstudioRealImagen();
                    oEstRealImagen.oEpisodio = new EpisodioMedico();
                    oEstRealImagen.oEpisodio.setExpediente(new Expediente());
                    oEstRealImagen.oEpisodio.setPaciente(new Paciente());
                    oEstRealImagen.setSitPago(new Parametrizacion());
                    oEstRealImagen.setSituacion(new Parametrizacion());
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oEstRealImagen.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oEstRealImagen.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                    oEstRealImagen.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                    oEstRealImagen.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                    oEstRealImagen.oEpisodio.getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                    oEstRealImagen.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(5));
                    oEstRealImagen.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(6));
                    oEstRealImagen.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(7));
                    oEstRealImagen.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(8));
                    ((Estudios)oEstRealImagen.getServicioCobrable()).setDescripcion((String)vRowTemp.get(9));
                    ((Estudios)oEstRealImagen.getServicioCobrable()).setClaveInterna(((Double)vRowTemp.get(10)).intValue());
                    oEstRealImagen.getSitPago().setValor((String)vRowTemp.get(11));
                    oEstRealImagen.getSituacion().setValor((String)vRowTemp.get(12));
                    oEstRealImagen.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(13)).longValue());
                    oEstRealImagen.setIdentificador(((Double)vRowTemp.get(14)).intValue());
                    vObj.add(oEstRealImagen);      
            }
            nTam=vObj.size();
                arrRet = new EstudioRealImagen[nTam];
                
            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        } 
        }
        return arrRet; 
    }
        
    public EstudioRealImagen[] buscarRepEstImagen(int claveSol, Date fecIni, Date fecFin) throws Exception{
	EstudioRealImagen arrRet[]=null, oEstRealImagen=null;
        ArrayList<EstudioRealImagen> vObj=null;
        ArrayList rst=null;
        int i=0, nTam=0;
	String sQuery = "";
        Date f1=fecIni;
        Date f2=fecFin;
        SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fecha2= new SimpleDateFormat("yyyy-MM-dd");
        if(claveSol == 0 && f1 == null && f2 == null){
            throw new Exception("EstudioRealImagen.buscarRepEstImagen: Error de programación, faltan datos");
        }
        else{
            sQuery = "SELECT * FROM buscarRepEstImagen("+ claveSol +",'"+ fecha.format(f1) +"', '" + fecha2.format(f2) +"');"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
		rst = oAD.ejecutarConsulta(sQuery); 
		oAD.desconectar(); 
            }
            if (rst != null) {
                vObj=new ArrayList<EstudioRealImagen>();
                for(i=0;i<rst.size();i++){
                    oEstRealImagen = new EstudioRealImagen();
                    oEstRealImagen.oEpisodio = new EpisodioMedico();
                    oEstRealImagen.oEpisodio.setExpediente(new Expediente());
                    oEstRealImagen.oEpisodio.setPaciente(new Paciente());
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oEstRealImagen.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oEstRealImagen.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oEstRealImagen.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oEstRealImagen.oEpisodio.getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oEstRealImagen.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(4));
                    oEstRealImagen.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(5));
                    oEstRealImagen.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(6));
                    oEstRealImagen.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                    ((Estudios)oEstRealImagen.getServicioCobrable()).setConcepto((String)vRowTemp.get(8));
                    oEstRealImagen.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(9)).longValue());
                    oEstRealImagen.setIdentificador(((Double)vRowTemp.get(10)).intValue());
                    vObj.add(oEstRealImagen);      
                    System.out.println("Encontrados: " + vObj.size());
            }
            nTam=vObj.size();
                arrRet = new EstudioRealImagen[nTam];
                
            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        } 
        }
                
        return arrRet; 
    }
    
    public EstudioRealImagen buscarDetallePacImg(int nIdentificador) throws Exception{
        ArrayList rst=null;
        System.out.println("Dentro del método de buscar detalles");
        String sQuery="";
        EstudioRealImagen oEstImagen=null;
        System.out.println("El identficador es: " + nIdentificador);
        if(nIdentificador == 0){
            throw new Exception("EstudioImagenologia: error de programación, faltan datos"); 
        }
        else{
            sQuery="SELECT * FROM  buscarDetalleEstImagen("+ nIdentificador + ")";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size() == 1){
                ArrayList vRowTemp= (ArrayList)rst.get(0);
                oEstImagen = new EstudioRealImagen();
                oEstImagen.oEpisodio=new EpisodioMedico();
                oEstImagen.oEpisodio.setDiagIngreso(new DiagnosticoCIE10());
                oEstImagen.setSituacion(new Parametrizacion());
                oEstImagen.setSitPago(new Parametrizacion());
                oEstImagen.oEpisodio.setPaciente(new Paciente());
                oEstImagen.oEpisodio.getPaciente().setExpediente(new Expediente());
                oEstImagen.oEpisodio.getPaciente().getExpediente().setAntecPatologicos(new AntecPatologicos());
                oEstImagen.setRegionSolicitada(new Parametrizacion());
                oEstImagen.setMedioContraste(new Parametrizacion());
                oEstImagen.setTipoPlaca(new Parametrizacion());
                oEstImagen.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstImagen.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstImagen.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstImagen.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstImagen.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(4));
                oEstImagen.oEpisodio.getCama().setNumero((String)vRowTemp.get(5));
                String edad=(String)vRowTemp.get(6);
                if(edad.compareTo("")!=0){
                    if(edad.substring(0,3).compareTo("000")!=0){
                        if(edad.charAt(0)=='0')
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                        else
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                    }else{
                        if(edad.substring(4,6).compareTo("00")!=0)
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                        else
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                    }
                }
                oEstImagen.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(7));
                oEstImagen.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(8));
                oEstImagen.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(9));
                oEstImagen.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(10));
                oEstImagen.setFechaSolicitud((Date)vRowTemp.get(11));
                oEstImagen.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(12));
                ((Estudios)oEstImagen.getServicioCobrable()).setClaveInterna(((Double)vRowTemp.get(13)).intValue());
                ((Estudios)oEstImagen.getServicioCobrable()).setDescripcion((String)vRowTemp.get(14));
                ((Estudios)oEstImagen.getServicioCobrable()).setClaveStudio((String)vRowTemp.get(15));
                oEstImagen.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setAlergias((String)vRowTemp.get(16));
                oEstImagen.setCantMedio(((Double)vRowTemp.get(17)).shortValue());
                oEstImagen.getMedioContraste().setValor((String)vRowTemp.get(18));
                int nPort = ((Double)vRowTemp.get(19)).intValue();
                oEstImagen.sPortat = nPort == 0 ? "NO" : "SI";
                oEstImagen.getTipoPlaca().setValor((String)vRowTemp.get(20));
                oEstImagen.oSituacion.setValor((String)vRowTemp.get(21));
                oEstImagen.oSitPago.setValor((String)vRowTemp.get(22));
                oEstImagen.getRegionSolicitada().setValor((String)vRowTemp.get(23));
                oEstImagen.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(24)).longValue());
            }
        }
        return oEstImagen;
    }
    
     public EstudioRealImagen buscarDetalleRepImagen(int nIdentificador) throws Exception{
        ArrayList rst=null;
        System.out.println("Dentro del método de buscar detalles");
        String sQuery="";
        EstudioRealImagen oEstImagen=null;
        if(nIdentificador == 0){
            throw new Exception("EstudioImagenologia: error de programación, faltan datos"); 
        }
        else{
            sQuery="SELECT * FROM  buscarDetalleRepImagen("+ nIdentificador + ")";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size() == 1){
                ArrayList vRowTemp= (ArrayList)rst.get(0);
                oEstImagen = new EstudioRealImagen();
                oEstImagen.oEpisodio=new EpisodioMedico();
                oEstImagen.oEpisodio.setDiagIngreso(new DiagnosticoCIE10());
                oEstImagen.oEpisodio.setMedicoTratante(new Medico());
                oEstImagen.oEpisodio.setPaciente(new Paciente());
                oEstImagen.oEpisodio.getPaciente().setExpediente(new Expediente());
                oEstImagen.oEpisodio.getPaciente().getExpediente().setAntecPatologicos(new AntecPatologicos());
                oEstImagen.setRegionSolicitada(new Parametrizacion());
                oEstImagen.setMedioContraste(new Parametrizacion());
                oEstImagen.setTipoPlaca(new Parametrizacion());
                oEstImagen.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstImagen.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstImagen.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstImagen.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstImagen.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(4));
                oEstImagen.oEpisodio.getCama().setNumero((String)vRowTemp.get(5));
                String edad=(String)vRowTemp.get(6);
                if(edad.compareTo("")!=0){
                    if(edad.substring(0,3).compareTo("000")!=0){
                        if(edad.charAt(0)=='0')
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                        else
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                    }else{
                        if(edad.substring(4,6).compareTo("00")!=0)
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                        else
                            oEstImagen.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                    }
                }
                oEstImagen.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(7));
                oEstImagen.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(8));
                oEstImagen.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(9));
                oEstImagen.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(10));
                oEstImagen.setFechaSolicitud((Date)vRowTemp.get(11));
                oEstImagen.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(12));
                ((Estudios)oEstImagen.getServicioCobrable()).setConcepto((String)vRowTemp.get(13));
                ((Estudios)oEstImagen.getServicioCobrable()).setClaveStudio((String)vRowTemp.get(14));
                oEstImagen.oEpisodio.getPaciente().getExpediente().getAntecPatologicos().setAlergias((String)vRowTemp.get(15));
                oEstImagen.getRegionSolicitada().setValor((String)vRowTemp.get(16));
                oEstImagen.setCantMedio(((Double)vRowTemp.get(17)).shortValue());
                oEstImagen.getMedioContraste().setValor((String)vRowTemp.get(18));
                int nPort = ((Double)vRowTemp.get(19)).intValue();
                oEstImagen.sPortat = nPort == 0 ? "NO" : "SI";
                oEstImagen.getTipoPlaca().setValor((String)vRowTemp.get(20));
                oEstImagen.setImpresionDiagnostica((String)vRowTemp.get(21));
            }
        }
        return oEstImagen;
    }

    public int modificaEstadoSolicitud(int nIdentificador, int nCveAccion, int nCveEstudio, long nFolioPac)  throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador==0){
            throw new Exception("EstudioRealImagen.modificaEstadoSolicitud: error, faltan datos");
        }else{
            if(nCveAccion == 3){
                sQuery="SELECT * FROM modificaEstadoSolicitudImagen('"+ 
                        sUsuarioFirmado +"'::character varying, "+ 
                        nIdentificador +"::bigint, "+ nCveEstudio +
                        "::smallint, "+ nFolioPac +"::bigint,'"+ 
                        this.getImpresionDiagnostica() +"'::text, "+ 
                        nCveAccion +");";
            }else if(nCveAccion != 3){
                sQuery="SELECT * FROM modificaEstadoSolicitudImagen('"+ sUsuarioFirmado +"'::character varying, "+ nIdentificador +"::bigint, "+ nCveEstudio +"::smallint, "+ nFolioPac +"::bigint,''::text, "+ nCveAccion +")";
            }
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }   
            if(rst != null && rst.size()==1){
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                i = ((Double)vRowTemp.get(0)).intValue();
            }
        }
        return i;
    }
     
    public int modificarInterpretacion(int nIdentificador, String sNombreArchivo) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioRealImagen.modificarInterpretacion: Error de programación, faltan datos");
        }else{
            if(sNombreArchivo.equals("") &&  
                !this.getImpresionDiagnostica().equals("")){ //Registro de interpretación de Tomografía
                sQuery="SELECT * FROM modificaInterpretacionEstudioImagen('"+ 
                        sUsuarioFirmado +"'::character varying, " + 
                        nIdentificador + "::bigint, '"+ 
                        this.getImpresionDiagnostica() +"'::text, "
                    + "''::character varying);";
            }else if(!sNombreArchivo.equals("")){
                sQuery="SELECT * FROM modificaInterpretacionEstudioImagen('"+ 
                        sUsuarioFirmado +"'::character varying, " + 
                        nIdentificador + "::bigint, ''::text, " 
                        + "'"+ EstudioRealImagen.RUTA_ARCHIVO + "" + 
                        sNombreArchivo +"'::character varying);";
            }
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    i= ((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }
    
    ////////////////////////////////////////////////////////////////////////////////
    //Órdenes de servicio
    
    public EstudioRealImagen[] buscarEstudiosImag(String EstIma, int CveArea) throws Exception {
        EstudioRealImagen arrRet[] = null, oEst = null;
        ArrayList rst = null;
        ArrayList<EstudioRealImagen> vObj = null;
        System.out.println("Área REAL IMAGEN: " + CveArea);
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarestudiosdesolicitudimagenologia('" + EstIma + "'::character varying," + CveArea + "::smallint);";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<EstudioRealImagen>();
            for (i = 0; i < rst.size(); i++) {
                oEst = new EstudioRealImagen();
                oEst.setImagenRegion(new Parametrizacion());
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEst.getEstudio().setConcepto(((String) vRowTemp.get(0)).trim());
                vObj.add(oEst);
            }
            nTam = vObj.size();
            arrRet = new EstudioRealImagen[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public boolean buscarClavesEstudiosImag() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscarestudiosimagenologiaclaves('" + 
                getEstudio().getConcepto() + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            getEstudio().setClaveInterna(((Double) vRowTemp.get(0)).intValue());
            getEstudio().setClaveStudio(((String) vRowTemp.get(1)).trim());
            bRet = true;
        }
        return bRet;
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

    public String getInsertaSolImagenología() throws Exception {
        String sQuery = "";
        if (this == null) {
            throw new Exception("EstudioRealImagen.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertasolicitudimagenologia('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + getEstudio().getClaveInterna() + "::smallint,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEpisodio().getDiagIngreso().getClave() + "',"
                    + getEstPortatil() + "::smallint,'"
                    + getTransoperatorioProbable() + "','"
                    + getImagenRegion().getClaveParametro() + "');";
            System.out.println(sQuery);
        }
        return sQuery;
    }

    public void insertaEstudiosImg(ArrayList<EstudioRealImagen> arrEstudioRealImagen) throws Exception {
        System.out.println("Dentro del método insertaEstudiosImg");
        ArrayList<String> vSolicLab = new ArrayList<>();
        int nRegAfectados = 0;
        for (EstudioRealImagen Es : arrEstudioRealImagen) {
            vSolicLab.add(Es.getInsertaSolImagenología());
        }
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            nRegAfectados = oAD.ejecutarConsultaComando(vSolicLab);
            oAD.desconectar();
        }
    }
    
    public EstudioRealImagen[] buscarEstudiosTomo(String EstTomo, int CveArea2) throws Exception {
        EstudioRealImagen arrRet[] = null, oEst = null;
        ArrayList rst = null;
        ArrayList<EstudioRealImagen> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarestudiosdesolicitudtomografia('" + EstTomo + "'::character varying," + CveArea2 + "::smallint);";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<EstudioRealImagen>();
            for (i = 0; i < rst.size(); i++) {
                oEst = new EstudioRealImagen();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEst.getEstudio().setConcepto(((String) vRowTemp.get(0)).trim());
                vObj.add(oEst);
            }
            nTam = vObj.size();
            arrRet = new EstudioRealImagen[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public boolean buscarClavesEstudiosTomo() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscarestudiostomografiaclaves('" + 
                getEstudio().getConcepto() + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            getEstudio().setClaveInterna(((Double) vRowTemp.get(0)).intValue());
            getEstudio().setClaveStudio(((String) vRowTemp.get(1)).trim());
            bRet = true;
        }
        return bRet;
    }

    public int insertaSolTomografia() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        System.out.println("Dentro de insertaSolTomografia");
        if (this.getRegionSolicitada().getClaveParametro() == null 
                && this.getEstudio().getClaveInterna() == 0) {   //completar llave
            throw new Exception("EstudioRealImagen.insertar: error de programación, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            sQuery = "SELECT * FROM insertasolicitudtomografia('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + getEstudio().getClaveInterna() + "::smallint,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEpisodio().getDiagIngreso().getClave() + "',"
                    + getPortatil() + "::boolean,'"
                    + oRegionSolicitada.getTipoParametro() + "','"
                    + oRegionSolicitada.getClaveParametro() + "');";
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
    
    public String fechaActual(){
        Calendar fecha = new GregorianCalendar();
        int anio=fecha.get(Calendar.YEAR);
        int mes=fecha.get(Calendar.MONTH);
        int dia=fecha.get(Calendar.DAY_OF_MONTH);
        
        String a=anio+"";
        String hoy=dia+"/"+(mes+1)+"/"+a.substring(2, 4);

        return hoy;
    } 

    public Parametrizacion getRegionSolicitada() {
        return oRegionSolicitada;
    }

    public void setRegionSolicitada(Parametrizacion oRegionSolicitada) {
        this.oRegionSolicitada = oRegionSolicitada;
    }

    public ServicioRealizado getSerReal() {
        return this;
    }

    public EstudioImagen getEstImg() {
        return oEstImg;
    }

    public void setEstImg(EstudioImagen oEstImg) {
        this.oEstImg = oEstImg;
    }

    /**
     * @return the nCantMedio
     */
    public short getCantMedio() {
        return nCantMedio;
    }

    /**
     * @param nCantMedio the nCantMedio to set
     */
    public void setCantMedio(short nCantMedio) {
        this.nCantMedio = nCantMedio;
    }

    public Date getFechaInicio() {
        return fechaInicio;
    }

    public void setFechaInicio(Date fechaInicio) {
        this.fechaInicio = fechaInicio;
    }

    public Date getFechaFin() {
        return fechaFin;
    }

    public void setFechaFin(Date fechaFin) {
        this.fechaFin = fechaFin;
    }

    public String getPortat() {
        return sPortat;
    }
    
    public String getTransoperatorioProbable() {
        return sTransoperatorioProbable;
    }

    public void setTransoperatorioProbable(String sTransoperatorioProbable) {
        this.sTransoperatorioProbable = sTransoperatorioProbable;
    }
    
    

    public String getEstPortatil() {
        return sEstPortatil;
    }

    public void setEstPortatil(String sEstPortatil) {
        this.sEstPortatil = sEstPortatil;
    }

    public Parametrizacion getImagenRegion() {
        return oImagenRegion;
    }

    public void setImagenRegion(Parametrizacion oImagenRegion) {
        this.oImagenRegion = oImagenRegion;
    }

}

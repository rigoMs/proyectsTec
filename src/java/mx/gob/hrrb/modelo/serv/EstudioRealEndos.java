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
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.cir.AnestesiaEspecifica;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Medicamento;
import mx.gob.hrrb.modelo.core.Medico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.ProcedimientoCIE9;
import mx.gob.hrrb.modelo.core.ProcedimientosRealizados;

public class EstudioRealEndos extends EstudioRealizado implements Serializable {
private String sDetalleDiagIni="";
private Paciente oPaciente;
private String sUsuarioFirmado;
private AccesoDatos oAD;
private EstudioInterconsulta oInter;
private String sFecSol;
private Material oMaterial;
private Medicamento oMedicamento;
private ProcedimientosRealizados oProcedimientos;
private String sMotivo="";
public static final String RUTA_ARCHIVO = "/resources/estudios/endoscopia/";
    
    public EstudioRealEndos(){
        oMaterial = new Material();
        oEpisodio = new EpisodioMedico();
         HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
    }

    public EstudioRealEndos[] buscarOrdenesServicio(Date fechaSol) throws Exception{
        EstudioRealEndos arrRet[]=null, oEstRealEnd=null;
        ArrayList<EstudioRealEndos> vObj=null;
        ArrayList rst=null;
        SimpleDateFormat oFec=new SimpleDateFormat("yyyy-MM-dd");
        int i=0, nTam=0;
        String sQuery="";
        if(fechaSol==null){
            throw new Exception("EstudioRealEndo:error de programación, faltan datos");
        }
        else{
            sQuery="SELECT * FROM buscarSolEndos('"+ oFec.format(fechaSol)+ "');";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj=new ArrayList<EstudioRealEndos>();
                for(i=0;i<rst.size();i++){
                    oEstRealEnd=new EstudioRealEndos();
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oEstRealEnd.oEpisodio=new EpisodioMedico();
                    oEstRealEnd.setSituacion(new Parametrizacion());
                    oEstRealEnd.setSitPago(new Parametrizacion());
                    oEstRealEnd.oEpisodio.setPaciente(new Paciente());
                    oEstRealEnd.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oEstRealEnd.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oEstRealEnd.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oEstRealEnd.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oEstRealEnd.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(4));
                    oEstRealEnd.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(5));
                    oEstRealEnd.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(6));
                    oEstRealEnd.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(7));
                    oEstRealEnd.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(8));
                    oEstRealEnd.getSituacion().setValor((String)vRowTemp.get(9));
                    oEstRealEnd.getSitPago().setValor((String)vRowTemp.get(10));
                    oEstRealEnd.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(11)).longValue());
                    oEstRealEnd.setIdentificador(((Double)vRowTemp.get(12)).intValue());
                    oEstRealEnd.oPaciente = oEstRealEnd.oEpisodio.getPaciente();
                    vObj.add(oEstRealEnd);
                }
                nTam=vObj.size();
                arrRet =new EstudioRealEndos[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i]=vObj.get(i);
                }
            }
        }                
        return arrRet;
    }
    
    public EstudioRealEndos buscarDetOrdServEndos(int nIdentificador) throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        EstudioRealEndos oEstRealEndos=null;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd HH:MM:SS");
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioRealEndos:error de programación, faltan datos");
        }
        else{
            sQuery="SELECT * FROM buscarDetSolEndos("+ nIdentificador +");";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() == 1){
                ArrayList<Object> vRowTemp=(ArrayList)rst.get(0);
                oEstRealEndos = new EstudioRealEndos();
                oEstRealEndos.oEpisodio = new EpisodioMedico();
                oEstRealEndos.oEpisodio.setPaciente(new Paciente());
                oEstRealEndos.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                oEstRealEndos.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                oEstRealEndos.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                oEstRealEndos.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                oEstRealEndos.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                oEstRealEndos.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(5));
                oEstRealEndos.oEpisodio.getCama().setNumero((String)vRowTemp.get(6));
                String edad=(String)vRowTemp.get(7);
                if(edad.compareTo("")!=0){
                        if(edad.substring(0,3).compareTo("000")!=0){
                            if(edad.charAt(0)=='0')
                                oEstRealEndos.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                            else
                                oEstRealEndos.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                        }else{
                            if(edad.substring(4,6).compareTo("00")!=0)
                                oEstRealEndos.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                            else
                                oEstRealEndos.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                        }
                }
                oEstRealEndos.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(8));
                oEstRealEndos.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(9));
                oEstRealEndos.oEpisodio.setFechaIngreso((Date)vRowTemp.get(10));
                oEstRealEndos.setDetalleDiagIni((String)vRowTemp.get(11));
                ((Estudios)oEstRealEndos.getEstReal().getServicioCobrable()).setClaveInterna(((Double)vRowTemp.get(12)).intValue());
                ((Estudios)oEstRealEndos.getEstReal().getServicioCobrable()).setDescripcion((String)vRowTemp.get(13));
                oEstRealEndos.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(14));
                oEstRealEndos.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(15));
                oEstRealEndos.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(16));
                Date feSol = ((Date)vRowTemp.get(17));
                SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                oEstRealEndos.setFecSol(fecha.format(feSol));
                bRet=true;
            }
        }
        return oEstRealEndos;
    }
    
    public EstudioRealEndos[] buscarPacienteEndos(String sNombres, String sApPaterno, String sApMaterno, int nNumExpe) throws Exception{
        EstudioRealEndos arrRet[]=null, oEndos=null;
        ArrayList<EstudioRealEndos> vObj=null;
        ArrayList rst=null;
        String sQuery="";
        System.out.println("Nombre completo: " + sNombres + " " + sApPaterno + " " + sApMaterno + ", número de expediente: " + nNumExpe);
        int i=0, nTam=0;
        if(!sNombres.equals("") || !sApPaterno.equals("") || !sApMaterno.equals("")){
            sQuery="SELECT * FROM buscarPacienteEndoscopia('"+ sNombres +"', '"+ sApPaterno +"','"+ sApMaterno +"',0);";
        }else if(!sNombres.equals("") || !sApMaterno.equals("")){
            sQuery="SELECT * FROM buscarPacienteEndoscopia('"+ sNombres +"','"+ sApPaterno +"','',0);";
        }else if(sNombres.equals("") || sApPaterno.equals("") || sApMaterno.equals("") || nNumExpe != 0){
            sQuery="SELECT * FROM buscarPacienteEndoscopia('','','',"+ nNumExpe +");";
        }else{
            throw new Exception("EstudioRealEndos.buscarPacienteEndos: error, faltan datos");
        }
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst!=null){
            vObj = new ArrayList<EstudioRealEndos>();
            for(i=0;i<rst.size();i++){
                oEndos = new EstudioRealEndos();
                ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                oEndos.oEpisodio = new EpisodioMedico();
                oEndos.oEpisodio.setPaciente(new Paciente());
                oEndos.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEndos.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEndos.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEndos.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEndos.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(4));
                oEndos.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(5));
                oEndos.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(6));
                ((Estudios)oEndos.getServicioCobrable()).setDescripcion((String)vRowTemp.get(7));
                oEndos.setIdentificador(((Double)vRowTemp.get(8)).intValue());
                vObj.add(oEndos);
            }
            nTam = vObj.size();
            arrRet = new EstudioRealEndos[nTam];
            
            for(i=0;i<nTam;i++){
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public int modificarReporteEndos(int nIdentificador, String sNombreArchivo, int nCveEst, long lFolioP) throws Exception{
        int i=0;
        ArrayList rst = null;
        String sQuery="";
        if(nIdentificador == 0 && sNombreArchivo.equals("")){
            throw new Exception("EstudioRealEndos.modificarReporteEndos: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM modificarreporteendoscopia('"+ 
                    sUsuarioFirmado +"'::character varying,"+ nIdentificador +
                    ", '"+ EstudioRealEndos.RUTA_ARCHIVO + "" + sNombreArchivo +
                    "'::character varying,"+ nCveEst +"::smallint, "+ 
                    lFolioP +"::bigint);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size() == 1){
                    ArrayList vRowTemp = (ArrayList)rst.get(0);
                    i = ((Double)vRowTemp.get(0)).intValue();
                }
                System.out.println("La base devolvió: " + i);
            }
        }
        return i;
    }
    
    public EstudioRealEndos[] buscarMaterialPorEstEndos() throws Exception{
            EstudioRealEndos arrRet[]=null, oEndos=null;
            ArrayList<EstudioRealEndos> vObj=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0, nTam=0;
            sQuery="SELECT * FROM buscarMaterialPorEstEndos();";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<EstudioRealEndos>();
                for(i=0;i<rst.size();i++){
                    oEndos = new EstudioRealEndos();
                    oEndos.oMaterial = new Material();
                    ArrayList<Object> vRowTemp = (ArrayList) rst.get(i);
                    ((Estudios)oEndos.getEstReal().getServicioCobrable()).setDescripcion((String)vRowTemp.get(0));
                    oEndos.oMaterial.setNombre((String)vRowTemp.get(1));
                    vObj.add(oEndos);
                }
                
                nTam = vObj.size();
                arrRet = new EstudioRealEndos[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
    
    public EstudioRealEndos[] buscarMedEstEndos() throws Exception{
        EstudioRealEndos arrRet[] = null, oEndos=null;
        ArrayList<EstudioRealEndos> vObj=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0, nTam=0;
        sQuery = "SELECT * FROM buscarMedicamentosPorEstEndos();";
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj = new ArrayList<EstudioRealEndos>();
            for(i=0;i<rst.size();i++){
                oEndos = new EstudioRealEndos();
                ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                oEndos.oMedicamento = new Medicamento();
                ((Estudios)oEndos.getServicioCobrable()).setDescripcion((String)vRowTemp.get(0));
                oEndos.getMedicamento().setNombre((String)vRowTemp.get(1));
                oEndos.getMedicamento().setPresentacion((String)vRowTemp.get(2));
                vObj.add(oEndos);
            }
            nTam = vObj.size();
            arrRet = new EstudioRealEndos[nTam];
            
            for(i=0;i<nTam;i++){
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public EstudioRealEndos[] buscarProcedimientosQuirofano(Date dFecha) throws Exception{
        EstudioRealEndos arrRet[]=null, oEndos=null;
        ArrayList<EstudioRealEndos> vObj=null;
        ArrayList rst=null;
        String sQuery ="";
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        int i=0, nTam=0;
        if(dFecha == null){
            throw new Exception("EstudioRealEndos.buscarProcedimientosQuirofano: error, faltan datos");
        }else{
            sQuery ="SELECT * FROM buscaprocedimientosendoscopia('"+ oFec.format(dFecha) +"')";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!= null){
                vObj = new ArrayList<EstudioRealEndos>();
                for(i=0;i<rst.size();i++){
                    oEndos = new EstudioRealEndos();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oEndos.oProcedimientos = new ProcedimientosRealizados();
                    oEndos.oEpisodio = new EpisodioMedico();
                    oEndos.oEpisodio.setExpediente(new Expediente());
                    oEndos.oProcedimientos.setCIE9(new ProcedimientoCIE9());
                    oEndos.oProcedimientos.setResidente(new Medico());
                    oEndos.oProcedimientos.setAnestesio(new Medico());
                    oEndos.oProcedimientos.setCirujano(new Medico());
                    oEndos.oProcedimientos.setAnestEspecifica(new AnestesiaEspecifica());
                    oEndos.oProcedimientos.getAnestesio().setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                    oEndos.oProcedimientos.getAnestesio().setNombres((String)vRowTemp.get(1));
                    oEndos.oProcedimientos.getAnestesio().setApPaterno((String)vRowTemp.get(2));
                    oEndos.oProcedimientos.getAnestesio().setApMaterno((String)vRowTemp.get(3));
                    oEndos.oProcedimientos.getCirujano().setNoTarjeta(((Double)vRowTemp.get(4)).intValue());
                    oEndos.oProcedimientos.getCirujano().setNombres((String)vRowTemp.get(5));
                    oEndos.oProcedimientos.getCirujano().setApPaterno((String)vRowTemp.get(6));
                    oEndos.oProcedimientos.getCirujano().setApMaterno((String)vRowTemp.get(7));
                    oEndos.oProcedimientos.getResidente().setNoTarjeta(((Double)vRowTemp.get(8)).intValue());
                    oEndos.oProcedimientos.getResidente().setNombres((String)vRowTemp.get(9));
                    oEndos.oProcedimientos.getResidente().setApPaterno((String)vRowTemp.get(10));
                    oEndos.oProcedimientos.getResidente().setApMaterno((String)vRowTemp.get(11));
                    oEndos.oProcedimientos.getCIE9().setClave((String)vRowTemp.get(12));
                    oEndos.oProcedimientos.getCIE9().setDescripcion((String)vRowTemp.get(13));
                    oEndos.oProcedimientos.setQuirofano((String)vRowTemp.get(14));
                    oEndos.oProcedimientos.getAnestEspecifica().setDescripcion((String)vRowTemp.get(15));
                    oEndos.oProcedimientos.setFechaRealizacion((Date)vRowTemp.get(16));
                    oEndos.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(17)).longValue());
                    oEndos.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(18));
                    oEndos.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(19));
                    oEndos.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(20));
                    oEndos.oEpisodio.getExpediente().setNumero(((Double)vRowTemp.get(21)).intValue());
                    oEndos.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(22)).longValue());
                    oEndos.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(23));
                    oEndos.oEpisodio.getPaciente().setFechaNac((Date)vRowTemp.get(24));
                    oEndos.oEpisodio.getPaciente().setEdadNumero((String)vRowTemp.get(25));
                    oEndos.oEpisodio.getPaciente().getSeguro().setNumero((String)vRowTemp.get(26));
                    oEndos.setIdentificador(((Double)vRowTemp.get(27)).intValue());
                    oEndos.oProcedimientos.setTipoTurno((String)vRowTemp.get(28));
                    vObj.add(oEndos);
                }
                nTam = vObj.size();
                arrRet = new EstudioRealEndos[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i]=vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    
    public int insertarInterEndos() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.getMotivo().compareTo("null") == 0) {   //completar llave
            throw new Exception("EstudioEndoscopia.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertasolicitudendos('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + getEstudio().getClaveInterna() + "::smallint,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEpisodio().getDiagIngreso().getClave() + "','"
                    + getMotivo() + "'::character varying);";
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
    
    public EstudioRealEndos[] buscarEstudiosEndoscopia(String EstEnd) 
            throws Exception {
        EstudioRealEndos arrRet[] = null, oEst = null;
        ArrayList rst = null;
        ArrayList<EstudioRealEndos> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarestudiosdesolicitudendoscopia('" + EstEnd + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<EstudioRealEndos>();
            for (i = 0; i < rst.size(); i++) {
                oEst = new EstudioRealEndos();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEst.getEstudio().setConcepto(((String) vRowTemp.get(0)).trim());
                vObj.add(oEst);
            }
            nTam = vObj.size();
            arrRet = new EstudioRealEndos[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    
    public List<EstudioRealEndos> getListaEstudiosEnd(String txt) {
        List<EstudioRealEndos> lListaEst = null;
        try {
            lListaEst = new ArrayList<EstudioRealEndos>(Arrays.asList(
                    (new EstudioRealEndos()).buscarEstudiosEndoscopia(txt)));
        } catch (Exception ex) {
            ex.printStackTrace();
        }
        return lListaEst;
    }
    
    public List<String> completarEstEnd(String sTxt) throws Exception {
    ArrayList arrRet = new ArrayList<String>();
    List<EstudioRealEndos> lis = getListaEstudiosEnd(sTxt);
        for (EstudioRealEndos li : lis) {
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
    
    public String getDetalleDiagIni() {
        return sDetalleDiagIni;
    }

    public void setDetalleDiagIni(String sDetalleDiagIni) {
        this.sDetalleDiagIni = sDetalleDiagIni;
    }

    public EstudioRealizado getEstReal() {
        return this;
    }

    public ServicioRealizado getSerReal() {
        return this;
    }

    public Paciente getPaciente() {
        return oPaciente;
    }

    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    public EstudioInterconsulta getInter() {
        return oInter;
    }

    public void setInter(EstudioInterconsulta oInter) {
        this.oInter = oInter;
    }

    public String getFecSol() {
        return sFecSol;
    }

    public void setFecSol(String sFecSol) {
        this.sFecSol = sFecSol;
    }

    public Material getMaterial() {
        return oMaterial;
    }

    public void setMaterial(Material oMaterial) {
        this.oMaterial = oMaterial;
    }

    public Medicamento getMedicamento() {
        return oMedicamento;
    }

    public void setMedicamento(Medicamento oMedicamento) {
        this.oMedicamento = oMedicamento;
    }

    public ProcedimientosRealizados getProcedimientos() {
        return oProcedimientos;
    }

    public void setProcedimientos(ProcedimientosRealizados oProcedimientos) {
        this.oProcedimientos = oProcedimientos;
    }
    
    public String getMotivo() {
    return sMotivo;
    }

    public void setMotivo(String valor) {
    sMotivo=valor;
    }
}

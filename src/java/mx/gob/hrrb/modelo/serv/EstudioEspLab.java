package mx.gob.hrrb.modelo.serv;

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
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.EpisodioMedico;

/**
 * Objetivo: 
 * @author : Pablo, Rafa
 * @version: 1.0
*/

public  class EstudioEspLab extends EstudioRealizado implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
/**
 * En el caso de estudios BAAR, llegan a ser hasta tres muestras
 */
private Date dMuestras;
private short nTempCent;
private String sDescripcion;
private String sObs;
private Date dFechaDiagnostico;
private Paciente oPaciente;
private Expediente oExpe;
private EstudioImagen oEstImg;
private int nCantBaarExp;
private boolean bTratAntifimico;
private int nFolio;
private float nTemp;
private float nFiO2;
private float nHmg;
private String sLabDestino;
private String sFecHoraM;
private String sTratAntif;
private Date dFechaEnvio;
private Date dFechaResult;

    public EstudioEspLab(){
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
                    throw new Exception("EstudioEspLab.buscar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveEstudioEspLab();"; 
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
    public EstudioEspLab[] buscarTodos() throws Exception{
    EstudioEspLab arrRet[]=null, oEstudioEspLab=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosEstudioEspLab();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new EstudioEspLab[rst.size()];
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
                    throw new Exception("EstudioEspLab.insertar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaEstudioEspLab('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("EstudioEspLab.modificar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaEstudioEspLab('"+sUsuarioFirmado+"');"; 
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
                    throw new Exception("EstudioEspLab.eliminar: error, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaEstudioEspLab('"+sUsuarioFirmado+"');"; 
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

    //Modificar el estado de una solicitud de envío solicitada al área de Epidemiología
    public int modificarEstadoSolicitud(int nIdentificador) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioEspLab.modificarEstadoSolicitud: error, faltan datos");
        }
        else{
            sQuery="SELECT * FROM modificarEstadoSolicitudEnvio('"+ sUsuarioFirmado +"', "+ nIdentificador +");";
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

    public int modificarSolicitudRealizadaGabinete(int nIdentificador, int nClaveEstudio, long lFolioPac) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioEspLab.modificarSolicitudRealizadaGabinte: error, faltan datos");
        }else{
            sQuery="SELECT * FROM modificarEstadoSolicitudGabineteRealizada('"+ sUsuarioFirmado +"'::character varying,"+ nIdentificador +"::bigint,"+ lFolioPac +"::bigint, "+ nClaveEstudio +"::smallint);";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp =(ArrayList)rst.get(0);
                    i=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    public int modificarSolicitudCanceladaGabinete(int nIdentificador)throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioEspLab.modificarSolicitudCanceladaGabinete: error, faltan datos");
        }else{
            sQuery="SELECT * FROM modificarestadosolicitudgabinetecancelada('"+ sUsuarioFirmado +"',"+ nIdentificador +");";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    i=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    public int modificarSolicitudEstBaarRealizado(int nIdentificador, int nClaveEst, long lFolioPac) throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioEspLab.modificarSolicitudEstBaarRealizado: error, faltan datos");
        }
        else{
            sQuery="SELECT * FROM modificarEstadoSolBaar('"+ sUsuarioFirmado +"'::character varying, "+ nIdentificador +"::bigint,"+ nClaveEst +"::smallint,"+ lFolioPac +"::bigint); ";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    i=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    public int modificarSolEstBaarCancel(int nIdentificador)throws Exception{
        int i=0;
        ArrayList rst=null;
        String sQuery="";
        if(nIdentificador == 0){
            throw new Exception("EstudioEspLab.modificarSolEstBaarCancel: error, faltan datos");
        }
        else{
            sQuery="SELECT * FROM modificarEstadoSolBaarCancel('"+ sUsuarioFirmado +"',"+ nIdentificador +"); ";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst != null && rst.size()==1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    i=((Double)vRowTemp.get(0)).intValue();
                }
            }
        }
        return i;
    }

    //Buscar los pacientes citados en Laboratorio
    public EstudioEspLab[] buscarPacCitadosLab(Date dFecha) throws Exception{
        EstudioEspLab arrRet[]=null, oEstLab=null;
        ArrayList<EstudioEspLab> vObj=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0, nTam=0;
        SimpleDateFormat oFec=new SimpleDateFormat("yyyy-MM-dd");
        if(dFecha == null){
            throw new Exception("EstudioEspLab.buscarPacCitados: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarAgendadosLaboratorio('"+  oFec.format(dFecha) +"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<EstudioEspLab>();
                for(i=0;i<rst.size();i++){
                    oEstLab = new EstudioEspLab();
                    ArrayList<Object> vRowTemp=(ArrayList) rst.get(i);
                    oEstLab.oEpisodio = new EpisodioMedico();
                    oEstLab.oEpisodio.setPaciente(new Paciente());
                    oEstLab.setSituacion(new Parametrizacion());
                    oEstLab.setSitPago(new Parametrizacion());
                    oEstLab.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oEstLab.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                    oEstLab.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                    oEstLab.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                    oEstLab.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(4));
                    ((Estudios)oEstLab.getServicioCobrable()).setClaveInterna(((Double)vRowTemp.get(5)).intValue());
                    ((Estudios)oEstLab.getServicioCobrable()).setDescripcion((String)vRowTemp.get(6));
                    oEstLab.setFolio(((Double)vRowTemp.get(7)).intValue());
                    oEstLab.oEpisodio.getPaciente().getSeg().setNumero((String)vRowTemp.get(8));
                    oEstLab.getSituacion().setValor((String)vRowTemp.get(9));
                    oEstLab.getSitPago().setValor((String)vRowTemp.get(10));
                    oEstLab.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(11)).longValue());
                    oEstLab.setIdentificador(((Double)vRowTemp.get(12)).intValue());
                    //oEstLab.oPaciente=oEstEsp.oEpisodio.getPaciente();
                    vObj.add(oEstLab);
                }

                nTam = vObj.size();
                arrRet = new EstudioEspLab[nTam];

                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    //Buscar Solicitudes de Laboratorio, estudios de gabinete, fecha
    public EstudioEspLab[] buscarEstGab(Date fecha) throws Exception{
       EstudioEspLab arrRet[]=null, oEstEsp=null;
       ArrayList rst=null;
       ArrayList<EstudioEspLab> vObj=null;
       SimpleDateFormat fec=new SimpleDateFormat("yyyy-MM-dd");
       String sQuery="";
       int i=0, nTam=0;
       oAD=new AccesoDatos();
       sQuery="SELECT * FROM buscarTodosEstGabinete('"+fec.format(fecha)+"');";
       System.out.println(sQuery);
       if(oAD.conectar()){
           rst=oAD.ejecutarConsulta(sQuery);
           oAD.desconectar();
       }
       if(rst != null){
           vObj=new ArrayList<EstudioEspLab>();
           for(i=0; i<rst.size();i++){
               oEstEsp = new EstudioEspLab();
               oEstEsp.oEpisodio = new EpisodioMedico();
               oEstEsp.setSituacion(new Parametrizacion());
               oEstEsp.setSitPago(new Parametrizacion());
               ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
               oEstEsp.oEpisodio.setPaciente(new Paciente());
               oEstEsp.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
               oEstEsp.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
               oEstEsp.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
               oEstEsp.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
               oEstEsp.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(4));
               ((Estudios)oEstEsp.getEstRealizado().getServicioCobrable()).setClaveInterna(((Double)vRowTemp.get(5)).intValue());
               ((Estudios)oEstEsp.getEstRealizado().getServicioCobrable()).setDescripcion((String)vRowTemp.get(6));
               oEstEsp.setFolio(((Double)vRowTemp.get(7)).intValue());
               oEstEsp.oEpisodio.getPaciente().getSeg().setNumero((String)vRowTemp.get(8));
               oEstEsp.getSituacion().setValor((String)vRowTemp.get(9));
               oEstEsp.getSitPago().setValor((String)vRowTemp.get(10));
               oEstEsp.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(11)).longValue());
               oEstEsp.setIdentificador(((Double)vRowTemp.get(12)).intValue());
               oEstEsp.oPaciente=oEstEsp.oEpisodio.getPaciente();
               vObj.add(oEstEsp);
           }
           nTam=vObj.size();
           arrRet = new EstudioEspLab[nTam];

           for(i=0; i<nTam;i++){
               arrRet[i]=vObj.get(i);
           }
       }
       return arrRet;
    }

    //Buscar estudios de BAAR, Laboratorio
    public EstudioEspLab[] buscarEstBaar(String sNombre, String sApPaterno, String sApMaterno, int nNumExpe) throws Exception{
        EstudioEspLab arrRet[]=null, oEstReal=null;
        ArrayList rst=null;
        ArrayList<EstudioEspLab> vObj=null;
        System.out.println("El número de expediente es:  " + nNumExpe);
        int i=0, nTam=0;
        String sQuery="";
        SimpleDateFormat fec=new SimpleDateFormat("yyyy-MM-dd");
        if(!sNombre.equals("") && !sApPaterno.equals("") &&  !sApPaterno.equals("")){
            sQuery="SELECT * FROM  buscarTodosEstBaar('"+ sNombre  +"', '"+ sApPaterno +"', '"+ sApMaterno  +"', 0);";
        }  
            else if (!sNombre.equals("") &&  !sApPaterno.equals("") && sApPaterno.equals("") && nNumExpe == 0){
                sQuery="SELECT * FROM buscarTodosEstBaar('"+ sNombre +"', '"+ sApPaterno +"','',0) ";
            }     
            else if(nNumExpe != 0){
                    sQuery="SELECT * FROM buscarTodosEstBaar('', '','', "+ nNumExpe +") ";

        }
            else{
                throw new Exception("EstudioEspLab:buscarEstBaar. Faltan datos");
            }
        System.out.println(sQuery);
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj=new ArrayList<EstudioEspLab>();
            for(i=0;i<rst.size();i++){
                oEstReal=new EstudioEspLab();
                ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                oEstReal.oEpisodio = new EpisodioMedico();
                oEstReal.oEpisodio.setPaciente(new Paciente());
                oEstReal.setSitPago(new Parametrizacion());
                oEstReal.setSituacion(new Parametrizacion());
                oEstReal.oEpisodio.getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                oEstReal.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(1));
                oEstReal.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(2));
                oEstReal.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(3));
                oEstReal.setFechaSolicitud((Date)vRowTemp.get(4));
                ((Estudios)oEstReal.getServicioCobrable()).setClaveInterna(((Double)vRowTemp.get(5)).intValue());
                ((Estudios)oEstReal.getServicioCobrable()).setDescripcion((String)vRowTemp.get(6));
                oEstReal.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(7)).intValue());
                oEstReal.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(8));
                oEstReal.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(9));
                oEstReal.oEpisodio.getArea().setTipo((String)vRowTemp.get(10));
                oEstReal.getSitPago().setValor((String)vRowTemp.get(11));
                oEstReal.getSituacion().setValor((String)vRowTemp.get(12));
                oEstReal.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(13)).longValue());
                oEstReal.setIdentificador(((Double)vRowTemp.get(14)).intValue());
                oEstReal.oPaciente = oEstReal.oEpisodio.getPaciente();
                vObj.add(oEstReal);
            }
            nTam=vObj.size();
            arrRet=new EstudioEspLab[nTam];

            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        }
        return arrRet;
    } 

    public EstudioEspLab[] buscarSolEnvio(String sNombre, String sApPaterno, String sApMaterno, int nNumExpe) throws Exception{
        EstudioEspLab arrRet[]=null, oEstLab=null;
        ArrayList<EstudioEspLab> vObj=null;
        ArrayList rst=null;
        SimpleDateFormat oFec=new SimpleDateFormat("yyyy-MM--dd");
        int i=0, nTam=0;
        String sQuery="";
        if(!sNombre.equals("") && !sApPaterno.equals("") &&  !sApPaterno.equals("")){
            sQuery="SELECT * FROM  buscarTodosSolEnv('"+ sNombre  +"', '"+ sApPaterno +"', '"+ sApMaterno  +"', 0);";
        }  
            else if (!sNombre.equals("") &&  !sApPaterno.equals("") && sApPaterno.equals("") && nNumExpe == 0){
                sQuery="SELECT * FROM buscarTodosSolEnv('"+ sNombre +"', '"+ sApPaterno +"','',0); ";
            }     
            else if(sNombre.equals("") &&  sApPaterno.equals("") && sApPaterno.equals("") && nNumExpe != 0){
                    sQuery="SELECT * FROM buscarTodosSolEnv('', '','', "+ nNumExpe +"); ";

        }
            else{
                throw new Exception("EstudioEspLab:buscarSolEnvío. Faltan datos");
            }
        System.out.println(sQuery);
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj=new ArrayList<EstudioEspLab>();
            for(i=0;i<rst.size();i++){
                oEstLab=new EstudioEspLab();
                ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                oEstLab.oEpisodio = new EpisodioMedico();
                oEstLab.setSituacion(new Parametrizacion());
                oEstLab.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstLab.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstLab.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstLab.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstLab.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(4));
                ((Estudios)oEstLab.getEstRealizado().getServicioCobrable()).setConcepto((String)vRowTemp.get(5));
                oEstLab.getSituacion().setValor((String)vRowTemp.get(6));
                oEstLab.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(7)).longValue());
                oEstLab.setIdentificador(((Double)vRowTemp.get(8)).intValue());
                vObj.add(oEstLab);
            }

            nTam=vObj.size();
            arrRet=new EstudioEspLab[nTam];

            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        }
        return arrRet;
    }

    public EstudioEspLab[] buscarSolEnv(String sNombre, String sApPaterno, String sApMaterno, int nNumExpe) throws Exception{
        EstudioEspLab arrRet[]=null, oEstLab=null;
        ArrayList<EstudioEspLab> vObj=null;
        ArrayList rst=null;
        SimpleDateFormat oFec=new SimpleDateFormat("yyyy-MM--dd");
        int i=0, nTam=0;
        String sQuery="";
        if(!sNombre.equals("") && !sApPaterno.equals("") &&  !sApPaterno.equals("")){
            sQuery="SELECT * FROM  buscarTodosSolEnvio('"+ sNombre  +"', '"+ sApPaterno +"', '"+ sApMaterno  +"', 0);";
        }  
            else if (!sNombre.equals("") &&  !sApPaterno.equals("") && sApPaterno.equals("") && nNumExpe == 0){
                sQuery="SELECT * FROM buscarTodosSolEnvio('"+ sNombre +"', '"+ sApPaterno +"','',0); ";
            }     
            else if(sNombre.equals("") &&  sApPaterno.equals("") && sApPaterno.equals("") && nNumExpe != 0){
                    sQuery="SELECT * FROM buscarTodosSolEnvio('', '','', "+ nNumExpe +"); ";

        }
            else{
                throw new Exception("EstudioEspLab:buscarSolEnvío. Faltan datos");
            }
        System.out.println(sQuery);
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst=oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj=new ArrayList<EstudioEspLab>();
            for(i=0;i<rst.size();i++){
                oEstLab=new EstudioEspLab();
                ArrayList<Object>vRowTemp=(ArrayList)rst.get(i);
                oEstLab.oEpisodio = new EpisodioMedico();
                oEstLab.setSituacion(new Parametrizacion());
                oEstLab.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstLab.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstLab.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstLab.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstLab.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(4));
                ((Estudios)oEstLab.getServicioCobrable()).setConcepto((String)vRowTemp.get(5));
                oEstLab.getSituacion().setValor((String)vRowTemp.get(6));
                oEstLab.oEpisodio.setClaveEpisodio(((Double)vRowTemp.get(7)).longValue());
                oEstLab.setIdentificador(((Double)vRowTemp.get(8)).intValue());
                vObj.add(oEstLab);
            }

            nTam=vObj.size();
            arrRet=new EstudioEspLab[nTam];

            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        }
        return arrRet;
    }

    public EstudioEspLab[] buscarPacientesLaboratorio(String sNombre, String sApPaterno, String sApMaterno,int nNumExpe, Date dFecIni, Date dFecFin) throws Exception{
        EstudioEspLab arrRet[]=null, oEstLab=null;
        ArrayList<EstudioEspLab> vObj=null;
        SimpleDateFormat dFec = new SimpleDateFormat("yyyy-MM-dd");
        ArrayList rst=null;
        String sQuery="";
        int i=0, nTam=0;
        if(!sNombre.equals("") && !sApPaterno.equals("") && !sApMaterno.equals("") && dFecIni !=null && dFecFin != null){
            sQuery="SELECT * FROM buscarSolicitudesPorPacienteLab('"+ sNombre +"','"+ sApPaterno +"','"+ sApMaterno +"',0, '"+ dFec.format(dFecIni) +"', '"+ dFec.format(dFecFin) +"')";
        }else if(!sNombre.equals("") && !sApPaterno.equals("") && dFecIni != null && dFecFin != null){
            sQuery="SELECT * FROM buscarSolicitudesPorPacienteLab('"+ sNombre +"','"+ sApPaterno +"','',0,'"+ dFec.format(dFecIni) +"', '"+ dFec.format(dFecFin) +"')";
        }else if(sNombre.equals("") && sApPaterno.equals("") && sApMaterno.equals("") && nNumExpe > 0 && dFecIni != null && dFecFin != null){
            sQuery="SELECT * FROM buscarSolicitudesPorPacienteLab('','','',"+ nNumExpe +", '"+ dFec.format(dFecIni) +"', '"+ dFec.format(dFecFin) +"')";
        }else{
            throw new Exception("EstudioEspLab.buscarPacientesLaboratorio: error, faltan datos");
        }
        System.out.println(sQuery);
        oAD=new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if(rst != null){
            vObj = new ArrayList<EstudioEspLab>();
            for(i=0;i<rst.size();i++){
                oEstLab = new EstudioEspLab();
                ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                oEstLab.oEpisodio = new EpisodioMedico();
                oEstLab.oEpisodio.setPaciente(new Paciente());
                oEstLab.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstLab.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstLab.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstLab.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                ((Estudios)oEstLab.getServicioCobrable()).setConcepto((String)vRowTemp.get(4));
                oEstLab.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(5));
                oEstLab.setFechaSolicitud((Date)vRowTemp.get(6));
                oEstLab.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(7));
                oEstLab.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(8));
                oEstLab.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(9));
                oEstLab.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(10));
                oEstLab.oPaciente = oEstLab.oEpisodio.getPaciente();
                vObj.add(oEstLab);
            }
            nTam = vObj.size();
            arrRet = new EstudioEspLab[nTam];

            for(i=0;i<nTam;i++){
                arrRet[i]=vObj.get(i);
            }
        }
        return arrRet;
    }

    public EstudioEspLab buscarDetPacBaar(int nIdentificador) throws Exception{
        boolean bRet=false;
        String sQuery="";
        ArrayList rst=null;
        EstudioEspLab oEstLab=null;
        System.out.println("El identificador es: " + nIdentificador);
        if(nIdentificador == 0){
            throw new Exception("EstudioEspLab: error de programación; faltan datos");
        }
        else{
            sQuery="SELECT * FROM buscarDetalleEstBaar("+ nIdentificador +")";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size()==1){
                oEstLab = new EstudioEspLab();
                ArrayList vRowTemp=(ArrayList)rst.get(0);
                oEstLab.oEpisodio=new EpisodioMedico();
                oEstLab.oEpisodio.setPaciente(new Paciente());
                oEstLab.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstLab.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstLab.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstLab.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstLab.oEpisodio.getPaciente().getSeg().setNumero((String)vRowTemp.get(4));
                String edad=(String)vRowTemp.get(5);
                if(edad.compareTo("")!=0){
                     if(edad.substring(0,3).compareTo("000")!=0){
                         if(edad.charAt(0)=='0')
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                         else
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                     }else{
                         if(edad.substring(4,6).compareTo("00")!=0)
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                         else
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                     }
                 }
                oEstLab.oEpisodio.getCama().setNumero((String)vRowTemp.get(6));
                oEstLab.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(7));
                oEstLab.oEpisodio.getPaciente().setFechaNac((Date)vRowTemp.get(8));
                oEstLab.oEpisodio.getPaciente().setCalleNum((String)vRowTemp.get(9));
                oEstLab.oEpisodio.getPaciente().setColonia((String)vRowTemp.get(10));
                oEstLab.oEpisodio.getPaciente().setCodigoPos((String)vRowTemp.get(11));
                oEstLab.oEpisodio.getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(12));
                oEstLab.oEpisodio.getPaciente().getEstado().setDescripcionEdo((String)vRowTemp.get(13));
                oEstLab.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(14));
                oEstLab.oEpisodio.getArea().setTipo((String)vRowTemp.get(15));
                oEstLab.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(16));
                oEstLab.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(17));
                oEstLab.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(18));
                oEstLab.setFechaSolicitud((Date)vRowTemp.get(19));
                oEstLab.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(20));
                oEstLab.setFechaDiagnostico((Date)vRowTemp.get(21));
                ((Estudios)(oEstLab.getServicioCobrable())).setConcepto((String)vRowTemp.get(22));
                ((Estudios)(oEstLab.getServicioCobrable())).setClaveStudio((String)vRowTemp.get(23));
                oEstLab.setCantBaarExp(((Double)vRowTemp.get(24)).intValue());
                oEstLab.setObs((String)vRowTemp.get(25));
                int nTratAnt = ((Double)vRowTemp.get(26)).intValue();
                oEstLab.sTratAntif = nTratAnt == 1 ? "SI":"NO";
                oEstLab.oEpisodio.getNota().setObservacion((String)vRowTemp.get(27));
                bRet=true;
            }
        }
        return oEstLab;
    }

    public EstudioEspLab buscarDetEstGab(int nIdentificador) throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        String sQuery="";
        EstudioEspLab oEstLab=null;
        System.out.println("El identificador es: " + nIdentificador);
        if(nIdentificador == 0){
            throw new Exception("EstudioEspLab:buscarDetEstGab, faltan datos");
        }
        else{
            sQuery="SELECT * FROM buscarDetalleEstGabinete("+ nIdentificador +")";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() == 1){
                oEstLab = new EstudioEspLab();
                ArrayList vRowTemp= (ArrayList)rst.get(0);
                oEstLab.oEpisodio = new EpisodioMedico();
                oEstLab.setSitPago(new Parametrizacion());
                oEstLab.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstLab.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstLab.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstLab.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstLab.oEpisodio.getCama().setNumero((String)vRowTemp.get(4));
                oEstLab.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(5));
                oEstLab.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(6));
                oEstLab.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(7));
                String edad=(String)vRowTemp.get(8);
                if(edad.compareTo("")!=0){
                     if(edad.substring(0,3).compareTo("000")!=0){
                         if(edad.charAt(0)=='0')
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                         else
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                     }else{
                         if(edad.substring(4,6).compareTo("00")!=0)
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                         else
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                     }
                }
                oEstLab.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(9));
                oEstLab.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(10));
                oEstLab.oEpisodio.getArea().setTipo((String)vRowTemp.get(11));
                oEstLab.oEpisodio.getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(12));
                ((Estudios)(oEstLab.getServicioCobrable())).setDescripcion((String)vRowTemp.get(13));
                ((Estudios)(oEstLab.getServicioCobrable())).setClaveStudio((String)vRowTemp.get(14));
                oEstLab.setFechaSolicitud((Date)vRowTemp.get(15));
                oEstLab.setTemp(((Double)vRowTemp.get(16)).floatValue());
                oEstLab.setFiO2(((Double)vRowTemp.get(17)).floatValue());
                oEstLab.setHmg(((Double)vRowTemp.get(18)).floatValue());
                oEstLab.setEspecimenMuestraTejido((String)vRowTemp.get(19));
                oEstLab.getSitPago().setValor((String)vRowTemp.get(20));
                bRet=true;
            }
        }
        return oEstLab;
    }

    public EstudioEspLab buscarDetSolEnvio(int nIdentificador) throws Exception{
        boolean bRet=false;
        ArrayList rst=null;
        String sQuery="";
        EstudioEspLab oEstLab=null;
        if(nIdentificador == 0){
            throw new Exception("buscarDetSolEnvio:error, faltan datos");
        }
        else{
            sQuery="SELECT * FROM buscardetallesolenvio("+ nIdentificador +")";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() == 1){
                oEstLab = new EstudioEspLab();
                ArrayList vRowTemp=(ArrayList) rst.get(0);
                oEstLab.oEpisodio = new EpisodioMedico();
                oEstLab.oEpisodio.setPaciente(new Paciente());
                oEstLab.setSituacion(new Parametrizacion());
                oEstLab.setSitPago(new Parametrizacion());
                oEstLab.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                oEstLab.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                oEstLab.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                oEstLab.oEpisodio.getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                oEstLab.setFechaSolicitud((Date)vRowTemp.get(4));
                oEstLab.oEpisodio.getPaciente().setFechaNac((Date)vRowTemp.get(5));
                oEstLab.oEpisodio.getCama().setNumero((String)vRowTemp.get(6));
                String edad=(String)vRowTemp.get(7);
                if(edad.compareTo("")!=0){
                     if(edad.substring(0,3).compareTo("000")!=0){
                         if(edad.charAt(0)=='0')
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(1,3)+" AÑOS");
                         else
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(0,3)+" AÑOS");
                     }else{
                         if(edad.substring(4,6).compareTo("00")!=0)
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(4,6)+ " MESES");
                         else
                             oEstLab.oEpisodio.getPaciente().setEdadNumero(edad.substring(7,9)+ " DIAS");
                     }
                 }
                oEstLab.oEpisodio.getPaciente().setSexoP((String)vRowTemp.get(8));
                oEstLab.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(9));
                oEstLab.oEpisodio.getArea().setTipo((String)vRowTemp.get(10));
                oEstLab.oEpisodio.getMedicoTratante().setNombres((String)vRowTemp.get(11));
                oEstLab.oEpisodio.getMedicoTratante().setApPaterno((String)vRowTemp.get(12));
                oEstLab.oEpisodio.getMedicoTratante().setApMaterno((String)vRowTemp.get(13));
                oEstLab.oEpisodio.getPaciente().getDiagcie().setDescripcionDiag((String)vRowTemp.get(14));
                ((Estudios)oEstLab.getServicioCobrable()).setConcepto((String)vRowTemp.get(15));
                oEstLab.setEspecimenMuestraTejido((String)vRowTemp.get(16));
                Date dFeHoMu = ((Date)vRowTemp.get(17));
                SimpleDateFormat fecha = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
                oEstLab.setFecHoraM(fecha.format(dFeHoMu));
                oEstLab.setTempCent(((Double)vRowTemp.get(18)).shortValue());
                oEstLab.oEpisodio.getNota().setObservacion((String)vRowTemp.get(19));
                oEstLab.oEpisodio.getPaciente().setColonia((String)vRowTemp.get(20));
                oEstLab.oEpisodio.getPaciente().setCp((String)vRowTemp.get(21));
                oEstLab.oEpisodio.getPaciente().getCiudad().setDescripcionCiu((String)vRowTemp.get(22));
                oEstLab.oEpisodio.getPaciente().getEstado().setDescripcionEdo((String)vRowTemp.get(23));
                oEstLab.setLabDestino((String)vRowTemp.get(24));
                oEstLab.getSituacion().setValor((String)vRowTemp.get(25));
                oEstLab.getSitPago().setValor((String)vRowTemp.get(26));
                oEstLab.setDescripcion((String)vRowTemp.get(27));
            }
        }
        return oEstLab;
    }

    //////////////////////////////////////////////////////////////////////////////// 
    public EstudioEspLab[] buscarEstudiosLab(String EstEspLab) throws Exception {
    EstudioEspLab arrRet[] = null, oEst = null;
    ArrayList rst = null;
    ArrayList<EstudioEspLab> vObj = null;
    String sQuery = "";
    int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarestudiosdesolicitudlaboratorio('" + EstEspLab + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<EstudioEspLab>();
            for (i = 0; i < rst.size(); i++) {
                oEst = new EstudioEspLab();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEst.getEstudio().setConcepto(((String) vRowTemp.get(0)).trim());
                vObj.add(oEst);
            }
            nTam = vObj.size();
            arrRet = new EstudioEspLab[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public boolean buscarClavesEstudiosLab() throws Exception {
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
        sQuery = "SELECT * FROM buscarEstudiosLaboratorioClaves('" + getEstudio().getConcepto() + "');";
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

    public List<EstudioEspLab> getListaEstudiosLab(String txt) {
        List<EstudioEspLab> lListaEst = null;
        try {
            lListaEst = new ArrayList<EstudioEspLab>(Arrays.asList(
                    (new EstudioEspLab()).buscarEstudiosLab(txt)));
        } catch (Exception ex) {
            Logger.getLogger(EstudioEspLab.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaEst;
    }
    
    public List<String> completarEstLab(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<EstudioEspLab> lis = getListaEstudiosLab(sTxt);
        for (EstudioEspLab li : lis) {
            if (sp_ascii(li.getEstudio().getConcepto()).contains(sTxt)) {
                arrRet.add(li.getEstudio().getConcepto());
            }
        }
        return arrRet;
    }


    public String getInsertaSolLab() throws Exception {
        String sQuery = "";
        if (this.getEstudio().getClaveInterna() == 0) {
            throw new Exception("Estudios.Modificar: error, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sQuery = "SELECT * FROM insertasolicitudlabgabgas('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + getEstudio().getClaveInterna() + "::smallint,'"
                    + getImpresionDiagnostica() + "'::character varying,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEspecimenMuestraTejido() + "'::character varying,'"
                    + getEpisodio().getDiagIngreso().getClave() + "','"
                    + getEstudio().getConcepto() + "'::character varying,"
                    + nTempCent + ",'"
                    + format.format(dMuestras) + "'::timestamp without time zone,'"
                    + sObs + "'::character varying,"
                    + nFiO2 + ","
                    + nHmg + ");";
        }
        return sQuery;
    }

    public void insertaEstudiosLab(ArrayList<EstudioEspLab> arrEstudioEspLab) throws Exception {
    System.out.println("Dentro del método insertaestudioslab");
    ArrayList<String> vSolicLab = new ArrayList<>();
    int nRegAfectados = 0;
        for (EstudioEspLab Es : arrEstudioEspLab) {
            vSolicLab.add(Es.getInsertaSolLab());
        }
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            nRegAfectados = oAD.ejecutarConsultaComando(vSolicLab);
            oAD.desconectar();
        }
    }

    public int insertaSolBAAR() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        System.out.println("Dentro de insertaSolBAAR");
        if (dFechaDiagnostico == null) {   //completar llave
            throw new Exception("EstudioEspLab.insertar: error de programación, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss.SSS");
            sQuery = "SELECT * FROM insertasolicitudlabbaar('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + 354 + "::smallint,'"
                    + getImpresionDiagnostica() + "'::character varying,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEspecimenMuestraTejido() + "'::character varying,'"
                    + getEpisodio().getDiagIngreso().getClave() + "','"
                    + "B.A.A.R EN EXPECTORACION" + "'::character varying,"
                    + getTratAntifimico() + "::boolean,'"
                    + format.format(dFechaDiagnostico) + "'::timestamp without time zone,'"
                    + getObs() + "'::character varying);";
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

    public int insertaSolEnvio() throws Exception {
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
    SimpleDateFormat oFec=new SimpleDateFormat("dd/MM/yyyy HH:mm");
        System.out.println("Dentro de insertaSolEnvio");
        if (this.getEstudio().getConcepto()==null) {   //completar llave
            throw new Exception("EstudioEspLab.insertar: error de programación, faltan datos");
        } else {
            System.out.println("Dentro del Else");
            this.sFecHoraM = oFec.format(this.dMuestras);
            sQuery = "SELECT * FROM insertasolicitudlabenvio('" + sUsuarioFirmado + "'::character varying,"
                    + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                    + getEpisodio().getClaveEpisodio() + "::bigint,"
                    + getAutorizadoPor().getNoTarjeta() + "::integer,"
                    + 358 + "::smallint,'"
                    + getImpresionDiagnostica() + "'::character varying,"
                    + getEpisodio().getArea().getClave() + "::smallint,'"
                    + getEspecimenMuestraTejido() + "'::character varying,'"
                    + getEpisodio().getDiagIngreso().getClave() + "','"
                    + getEstudio().getConcepto() + "'::character varying,'"
                    + getFecHoraM().substring(6, 10) + "-"
                    + getFecHoraM().substring(3, 5) + "-"
                    + getFecHoraM().substring(0, 2) + " "
                    + getFecHoraM().substring(11, 13) + ":"
                    + getFecHoraM().substring(14, 16) + ":"
                    + "00" + "':: timestamp without time zone,'"
                    + getLabDestino() + "'::character varying,"
                    + nTemp + "::smallint);";
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
    //Emmanuel
    public EstudioEspLab[] buscaTodosEstudiosDeEnvioTS() throws Exception{
    EstudioEspLab arrRet[]=null, oEstEsp=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
        sQuery = "select * from buscaEstudiosEspeciales("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+");";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null && rst.size()>0) {
            arrRet = new EstudioEspLab[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oEstEsp = new EstudioEspLab();
                oEstEsp.dMuestras = new Date();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oEstEsp.setDescripcion((String) vRowTemp.get(0));
                oEstEsp.dMuestras = (Date) vRowTemp.get(1);
                oEstEsp.setLabDestino((String) vRowTemp.get(2));
                oEstEsp.setTemp(((Double) vRowTemp.get(3)).floatValue());
                oEstEsp.setIdentificador(((Double) vRowTemp.get(4)).intValue());
                arrRet[i] = oEstEsp;
            }
        } 
        return arrRet; 
    }
    
    public int modificaEstudiosEspecialesLabTS(String sUsuario) throws Exception {
    int Resul = 0;
    SimpleDateFormat df;
    df=new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
    String FechaEnv=df.format(this.getFechaEnvio());
    String FechaResul=df.format(this.getFechaResult());
    ArrayList rst = null;
    String sQuery = "";
        if (sUsuario == null) {   //completar llave
            throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
        } else {
            sQuery = "select * from modificaEstudiosEspecLaboTS('" + sUsuario + 
                    "'," + this.getIdentificador() + ", '" + 
                    this.getLabDestino() + "', '"+FechaEnv+"', '"+
                    FechaResul+"');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                Resul = (((Double) vRowTemp.get(0)).intValue());
            }

        }
        return Resul;
    }
    
    //Emmanuel
    
    public Date getFecMuestras() {
    return dMuestras;
    }

    public void setFecMuestras(Date valor) {
    dMuestras=valor;
    }

    public short getTempCent() {
    return nTempCent;
    }

    public void setTempCent(short valor) {
    nTempCent=valor;
    }

    public String getDescripcion() {
    return sDescripcion;
    }

    public void setDescripcion(String valor) {
    sDescripcion=valor;
    }

    public String getObs() {
    return sObs;
    }

    public void setObs(String valor) {
    sObs=valor;
    }

    /**
     * @return the dFechaDiagnostico
     */
    public Date getFechaDiagnostico() {
        return dFechaDiagnostico;
    }

    /**
     * @param dFechaDiagnostico the dFechaDiagnostico to set
     */
    public void setFechaDiagnostico(Date dFechaDiagnostico) {
        this.dFechaDiagnostico = dFechaDiagnostico;
    }

    /**
     * @return the oPaciente
     */
    public Paciente getPaciente() {
        return oPaciente;
    }

    /**
     * @param oPaciente the oPaciente to set
     */
    public void setPaciente(Paciente oPaciente) {
        this.oPaciente = oPaciente;
    }

    /**
     * @return the oExpe
     */
    public Expediente getExpe() {
        return oExpe;
    }

    /**
     * @param oExpe the oExpe to set
     */
    public void setExpe(Expediente oExpe) {
        this.oExpe = oExpe;
    }

    /**
     * @return the oServReal
     */
        @Override
    public ServicioRealizado getServReal() {
        return this;
    }

    /**
     * @return the oEstImg
     */
    public EstudioImagen getEstImg() {
        return oEstImg;
    }

    /**
     * @param oEstImg the oEstImg to set
     */
    public void setEstImg(EstudioImagen oEstImg) {
        this.oEstImg = oEstImg;
    }

    public EstudioRealizado getEstRealizado() {
        return this;
    }

    public boolean getTratAntifimico() {
        return bTratAntifimico;
    }

    public void setTratAntifimico(boolean bTratAntifimico) {
        this.bTratAntifimico = bTratAntifimico;
    }

    public int getFolio() {
        return nFolio;
    }

    public void setFolio(int nFolio) {
        this.nFolio = nFolio;
    }

    public float getTemp() {
        return nTemp;
    }

    public void setTemp(float nTemp) {
        this.nTemp = nTemp;
    }

    public float getFiO2() {
        return nFiO2;
    }

    public void setFiO2(float nFiO2) {
        this.nFiO2 = nFiO2;
    }

    public float getHmg() {
        return nHmg;
    }

    public void setHmg(float nHmg) {
        this.nHmg = nHmg;
    }

    public int getCantBaarExp() {
        return nCantBaarExp;
    }

    public void setCantBaarExp(int nCantBaarExp) {
        this.nCantBaarExp = nCantBaarExp;
    }

    public String getLabDestino() {
        return sLabDestino;
    }

    public void setLabDestino(String sLabDestino) {
        this.sLabDestino = sLabDestino;
    }

    public String getFecHoraM() {
        return sFecHoraM;
    }

    public void setFecHoraM(String sFecHoraM) {
        this.sFecHoraM = sFecHoraM;
    }

    public String getTratAntif() {
        return sTratAntif;
    }

    public void setTratAntif(String sTratAntif) {
        this.sTratAntif = sTratAntif;
    }
    
    public Date getFechaEnvio() {
        return dFechaEnvio;
    }

    public void setFechaEnvio(Date dFechaEnvio) {
        this.dFechaEnvio = dFechaEnvio;
    }

    public Date getFechaResult() {
        return dFechaResult;
    }

    public void setFechaResult(Date dFechaResult) {
        this.dFechaResult = dFechaResult;
    }
} 

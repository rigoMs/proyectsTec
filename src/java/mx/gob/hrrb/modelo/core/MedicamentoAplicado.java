package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;

/**
 * Objetivo: 
 * @author : Carlos, Rafa, Javier
 * @version: 1.0
*/

public class MedicamentoAplicado implements Serializable{
private AccesoDatos oAD;
private Date dFechaAplicacion;
private EpisodioMedico oEpisodioMedico;
private Medicamento oMedicamento;
//Agregados en Fase II
private PersonalHospitalario oAplico;
private Turno oTurno;
private short nCantAplicada;
private String sDosis;
private Parametrizacion oVia;
private String sUsuarioFirmado;
private ServicioRealizado oServReal;
private NotaMedicaHRRB oNotaMedica;

    public MedicamentoAplicado(){
        HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
            oMedicamento=new Medicamento();
            oTurno=new Turno();
            oVia=new Parametrizacion();
            oAplico=new PersonalHospitalario();
    }

    public boolean buscar() throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("MedicamentoAplicado.buscar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaLlaveMedicamentoAplicado();"; 
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
    
    public MedicamentoAplicado[] buscarTodos() throws Exception{
    MedicamentoAplicado arrRet[]=null, oMedicamentoAplicado=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosMedicamentoAplicado();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new MedicamentoAplicado[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                    } 
            } 
            return arrRet; 
    } 
    
    ////////////////////////////////////////////////////////////////////////////
    // Órdenes de servicio  
    public MedicamentoAplicado[] buscarMedicamentoAplicadoPorFecha(Date dFecAux, long Folio, long CveEpi) throws Exception{
    MedicamentoAplicado arrRet[]=null, oMed=null;
    ArrayList rst = null;
    ArrayList<MedicamentoAplicado> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
    SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        if( this==null){
                throw new Exception("MedicamentoAplicado.buscar: error de programación, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahojaenfermeriamedicamentoaplicado('"+
                    df.format(dFecAux)+"%',"+Folio+"::bigint,"+CveEpi+"::bigint);";
            
            oAD=new AccesoDatos();

            if (oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar(); 
            }
            if (rst != null) {
                vObj = new ArrayList<MedicamentoAplicado>();
                for (i=0; i<rst.size(); i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oMed = new MedicamentoAplicado();
                    oMed.getMedicamento().setNombre((String) vRowTemp.get(0));
                    oMed.getMedicamento().setPresentacion((String) vRowTemp.get(1));
                    oMed.setDosis((String) vRowTemp.get(2));
                    oMed.getVia().setValor((String) vRowTemp.get(3));
                    oMed.getTurno().setDescripcion((String) vRowTemp.get(4));
                    oMed.getAplico().setNombres((String) vRowTemp.get(5));
                    oMed.getAplico().setApPaterno((String) vRowTemp.get(6));
                    oMed.getAplico().setApMaterno((String) vRowTemp.get(7));
                    oMed.setFechaAplicacion((Date) vRowTemp.get(8));

                    vObj.add(oMed);                              
                }
                nTam = vObj.size();
                arrRet = new MedicamentoAplicado[nTam];
                for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }   
            }
        }
        return arrRet;
    }
        
    public String getInsertaHojaEnfermeria() throws Exception{
    String sQuery="";
        if(this==null){
            throw new Exception("MedicamentoAplicado.Modificar: error, faltan datos");
        }else{
            System.out.println("Dentro del Else");
            sQuery = "SELECT * FROM insertahojaenfermeria('"+ 
                    sUsuarioFirmado +"'::character varying,"
            + oEpisodioMedico.getPaciente().getFolioPaciente() +"::bigint,"
            + oEpisodioMedico.getClaveEpisodio() +"::bigint,"
            + oAplico.getNoTarjeta() +"::integer,'"
            + oMedicamento.getClaveMedicamento() +"'::character varying,'"
            + oMedicamento.getPresentacion() + "'::character varying,"
            + oMedicamento.getCodBarras() + "::bigint,'"
            + oTurno.getClave() + "','"
            + sDosis + "'::character varying,'"
            + oVia.getClaveParametro() + "');";
        }
        return sQuery;
    }

    public void insertaHojaEnfermeriaMedAplic(ArrayList<MedicamentoAplicado> arrMedAplic) throws Exception{
    ArrayList<String> vMedAp=new ArrayList<>();
    int nRegAfectados=0;
        for(MedicamentoAplicado MedAplic:arrMedAplic){
            vMedAp.add(MedAplic.getInsertaHojaEnfermeria());
        }
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            nRegAfectados = oAD.ejecutarConsultaComando(vMedAp);
            oAD.desconectar();
        }
    }
   
///////////////////////////////////////////////////////////////////////
    
    
    public int insertar() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("MedicamentoAplicado.insertar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaMedicamentoAplicado();"; 
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
                    throw new Exception("MedicamentoAplicado.modificar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaMedicamentoAplicado();"; 
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
                    throw new Exception("MedicamentoAplicado.eliminar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaMedicamentoAplicado();"; 
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
    
    // metodos que busca medicamentos para ser aplicados en hoja de quirofano (de tipos soluciones)
    public MedicamentoAplicado[] buscarMedicamentoDiferentesDeSoluciones(Date dFechaActual, long Folio, long CveEpi) throws Exception{
	MedicamentoAplicado arrRet[]=null, oPar=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sQuery = "SELECT * FROM buscaMedicamentoParaAplicarDiferenteDeSoluciones('"+df.format(dFechaActual)+"'::timestamp,"+Folio+"::bigint,"+CveEpi+"::bigint);";
        //System.out.println(sQuery);
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null) {
            arrRet = new MedicamentoAplicado[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oPar = new MedicamentoAplicado();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oPar.getMedicamento().setNombre(((String)vRowTemp.get(0)).trim());
                oPar.getMedicamento().setClaveMedicamento(((String)vRowTemp.get(1)).trim());
                oPar.getMedicamento().setPresentacion(((String)vRowTemp.get(2)).trim());
                oPar.getMedicamento().setDetalle(new DetalleMedicamentos());
                oPar.getMedicamento().setCodBarras(((Double) vRowTemp.get(3)).longValue());
                oPar.getServReal().setIdentificador(((Double) vRowTemp.get(4)).intValue());
                arrRet[i]=oPar;
            }
        } 
        return arrRet; 
    }
    
    public MedicamentoAplicado[] buscarMedicamentoConDeSoluciones(Date dFechaActual, long Folio, long CveEpi) throws Exception{
	MedicamentoAplicado arrRet[]=null, oPar=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        sQuery = "SELECT * FROM buscaMedicamentoParaAplicarSoluciones('"+df.format(dFechaActual)+"'::timestamp,"+Folio+"::bigint,"+CveEpi+"::bigint);";
        //System.out.println(sQuery);
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null) {
                arrRet = new MedicamentoAplicado[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    oPar = new MedicamentoAplicado();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oPar.getMedicamento().setNombre(((String)vRowTemp.get(0)).trim());
                    oPar.getMedicamento().setClaveMedicamento(((String)vRowTemp.get(1)).trim());
                    oPar.getMedicamento().setPresentacion(((String)vRowTemp.get(2)).trim());
                    oPar.getMedicamento().setDetalle(new DetalleMedicamentos());
                    oPar.getMedicamento().setCodBarras(((Double) vRowTemp.get(3)).longValue());
                    oPar.getServReal().setIdentificador(((Double) vRowTemp.get(4)).intValue());
                    arrRet[i]=oPar;
                    }
        } 
        return arrRet; 
    }

    public Date getFechaAplicacion() {
    return dFechaAplicacion;
    }

    public void setFechaAplicacion(Date valor) {
    dFechaAplicacion=valor;
    }

    public EpisodioMedico getEpisodioMedico() {
    return oEpisodioMedico;
    }

    public void setEpisodioMedico(EpisodioMedico valor) {
	oEpisodioMedico=valor;
	}

    public Medicamento getMedicamento() {
    return oMedicamento;
    }

    public void setMedicamento(Medicamento valor) {
	oMedicamento=valor;
	}
    public PersonalHospitalario getAplico() {
        return oAplico;
    }
    public void setAplico(PersonalHospitalario valor) {
        this.oAplico = valor;
    }

    public Turno getTurno() {
        return oTurno;
    }
    public void setTurno(Turno valor) {
        this.oTurno = valor;
    }

    public short getCantAplicada() {
        return nCantAplicada;
    }
    public void setCantAplicada(short valor) {
        this.nCantAplicada = valor;
    }

    public String getDosis() {
        return sDosis;
    }

    public void setDosis(String valor) {
        this.sDosis = valor;
    }

    public Parametrizacion getVia() {
        return oVia;
    }
    public void setVia(Parametrizacion valor) {
        this.oVia = valor;
    }

    public ServicioRealizado getServReal() {
        return oServReal;
    }

    public void setServReal(ServicioRealizado oServReal) {
        this.oServReal = oServReal;
    }
        
        public String getFechaAplicacionFormat(){
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");            
            return df.format(dFechaAplicacion);
        }
        public String getFechaAplicacionString(){
            SimpleDateFormat df = new SimpleDateFormat("HH:mm");            
            return df.format(dFechaAplicacion);
        }
} 

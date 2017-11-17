package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.modelo.serv.ServicioRealizado;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class DetalleReceta extends ServicioRealizado implements Serializable {
    protected AccesoDatos oAD;
    protected Receta oReceta;
    protected int nCantidad;
    protected Date fechaSurtido;
    protected boolean bSurtido;
    protected Medicamento oMedicamento;
    
    //Por la primera fase, NO USAR POSTERIORMENTE
    private ArrayList<DetalleMedicamentos> listaDetalleMed;
    private PacienteCapasits oPac;

    public DetalleReceta() {
        oPac = new PacienteCapasits();
        listaDetalleMed = new ArrayList<DetalleMedicamentos>();
        fechaSurtido = new Date();
    }

    public DetalleMedicamentos[] buscarPorReceta() throws Exception {
        DetalleMedicamentos arrRet[] = null, oDetMed = null;
        ArrayList<DetalleMedicamentos> vObj = null;
        ArrayList rst = null;
        String sQuery = "";
        int i, nTam = 0;
        if (this == null) {   //completar llave
            throw new Exception("DetalleReceta.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaDetalleReceta(" + 
                    this.oReceta.getConsecReceta() + ");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                vObj = new ArrayList<DetalleMedicamentos>();
                for (i = 0; i < rst.size(); i++) {
                    oDetMed = new DetalleMedicamentos();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oDetMed.getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                    oDetMed.getMedicamento().setNombre(((String) vRowTemp.get(1)));
                    oDetMed.getMedicamento().setPresentacion(((String) vRowTemp.get(2)));
                    oDetMed.getMedicamento().setDescripcion(((String) vRowTemp.get(3)));
                    oDetMed.getMedicamento().setCodBarras(((Double) vRowTemp.get(4)).intValue());
                    oDetMed.setLote(((String) vRowTemp.get(5)));
                    oDetMed.setCaducidad(((Date) vRowTemp.get(6)));
                    oDetMed.setExistencia(((Double) vRowTemp.get(7)).intValue());
                    if (vRowTemp.get(9).equals("S")) {
                        oDetMed.setSurtido(true);
                    } else {
                        oDetMed.setSurtido(false);
                    }
                    vObj.add(oDetMed);
                }
                nTam = vObj.size();
                arrRet = new DetalleMedicamentos[nTam];

                for (i = 0; i < nTam; i++) {
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
    }
    
        public PacienteCapasits[] buscaRecetasConDetalle(Date fecha) throws Exception {
       PacienteCapasits arrRet[] = null, oDetPac = null;
        ArrayList rst = null;
        ArrayList<PacienteCapasits> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaRecetasDiariasConDetalle('"+fecha+"');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<PacienteCapasits>();
            for (i = 0; i < rst.size(); i++) {
                oDetPac = new PacienteCapasits();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetPac.setCalleNum(((String) vRowTemp.get(0)));//claveMedicamento
                oDetPac.setClaveEdadP(((String) vRowTemp.get(1)));//NombreMedicamento
                oDetPac.setCodigoPos(((String) vRowTemp.get(2)));//Presentacion
                oDetPac.setColonia(((String) vRowTemp.get(3)));//Descripcion
                oDetPac.setCp(((String) vRowTemp.get(4)));//lote
                oDetPac.setCodigoBarras(((Double) vRowTemp.get(5)).intValue());//cantidad
                oDetPac.setIdNacional(((Double) vRowTemp.get(6)).intValue());//idNacional
                oDetPac.setFolioPaciente(((Double) vRowTemp.get(7)).intValue());//folioPaciente
                oDetPac.setNombres(((String) vRowTemp.get(8)));//nombres
                oDetPac.setApPaterno(((String) vRowTemp.get(9)));//apPaterno
                oDetPac.setApMaterno(((String) vRowTemp.get(10)));//apMaterno
                oDetPac.setHospitalizado(((Double) vRowTemp.get(11)).intValue());//folioReceta
                vObj.add(oDetPac);
            }
            int nTam = vObj.size();
            arrRet = new PacienteCapasits[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    @Override
    public DetalleReceta[] buscarTodos() throws Exception {
        DetalleReceta arrRet[] = null, oDetalleReceta = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosDetalleReceta();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new DetalleReceta[rst.size()];
            for (i = 0; i < rst.size(); i++) {
            }
        }
        return arrRet;
    }

    public boolean insertar(String sUsuario) throws Exception {
        ArrayList rst = null;
        boolean bRet = true;
        String sQuery = "";
        if (this.oReceta.getConsecReceta() == 0) {
            throw new Exception("DetalleReceta.insertar: error de programación, faltan datos");
        } else {
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                for (DetalleMedicamentos listaDetalleMed1 : listaDetalleMed) {
                    String medsurtido = "S";
                    if (!listaDetalleMed1.getSurtido()) {
                        medsurtido = "N";
                    }
                    sQuery = "SELECT * FROM insertaDetalleReceta('" + sUsuario + 
                            "'," + this.oReceta.getConsecReceta() + ",'" + listaDetalleMed1.getMedicamento().getClaveMedicamento() + "','" + listaDetalleMed1.getMedicamento().getPresentacion() + "'," + listaDetalleMed1.getMedicamento().getCodBarras() + ",'" + listaDetalleMed1.getLote() + "'," + listaDetalleMed1.getExistencia() + "::smallint,'" + medsurtido + "','" + getFechaSurtido() + "');";
                    System.out.println(sQuery);
                    rst = oAD.ejecutarConsulta(sQuery);
                    if (rst != null && rst.size() == 1) {
                    } else {
                        bRet = false;
                        break;
                    }
                }
            }
            oAD.desconectar();
        }
        return bRet;
    }

    @Override
    public int modificar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("DetalleReceta.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaDetalleReceta();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) rst.get(0)).intValue();
                }
            }
        }
        return nRet;
    }

    public int modificarMedicamento(String sUsuario, long consecReceta, DetalleMedicamentos oDetMed) throws Exception {
        String surtido = "N";
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("DetalleReceta.modificar: error de programación, faltan datos");
        } else {
            if (oDetMed.getSurtido()) {
                surtido = "S";
            }
            sQuery = "SELECT * FROM modificaDetalleReceta('" + sUsuario + "'," + 
                    consecReceta + ",'" + oDetMed.getMedicamento().getClaveMedicamento() + 
                    "','" + oDetMed.getMedicamento().getPresentacion() + "'," + 
                    oDetMed.getMedicamento().getCodBarras() + ",'" + 
                    oDetMed.getLote() + "'," + oDetMed.getExistencia() + 
                    "::smallint,'" + surtido + "');";
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

    @Override
    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("DetalleReceta.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaDetalleReceta();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) rst.get(0)).intValue();
                }
            }
        }
        return nRet;
    }
    
    public DetalleReceta[] buscaHistorialRecetas(long folioPac) throws Exception{
        DetalleReceta arrRet[]=null, oRec=null;
        ArrayList rst = null;
        String sQuery = "";
        int i=0;
        if(folioPac==0){
            throw new Exception("DetalleReceta.buscaHistorialRecetas: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscahistorialrecetas("+folioPac+"::BIGINT);"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new DetalleReceta[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oRec= new DetalleReceta();
                    oRec.setEpisodio(new EpisodioMedico());
                    oRec.setReceta(new Receta());
                    oRec.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oRec.getEpisodio().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oRec.getReceta().setConsecReceta(((Double)vRowTemp.get(2)).longValue());
                    oRec.getReceta().setFolioReceta(((Double)vRowTemp.get(3)).longValue());
                    oRec.setFechaSolicitud((Date)vRowTemp.get(4));
                    oRec.setFechaSurtido((Date)vRowTemp.get(5));
                    arrRet[i]=oRec;
                } 
            } 
        }
        return arrRet;
    }
    
    public void buscaInformacionRecetaEXP() throws Exception{
        ArrayList rst = null; 
        String sQuery, edad;
        if(getEpisodio().getPaciente().getFolioPaciente()==0 && getEpisodio().getClaveEpisodio()==0 && getReceta().getConsecReceta()==0)
            throw new Exception("DetalleReceta.buscaInformacionRecetaEXP: Error, Falta datos");
        else{
            sQuery="select * from  buscainformacionrecetaEXP("+getReceta().getConsecReceta()+"::BIGINT," +
                                                                        getEpisodio().getClaveEpisodio()+"::BIGINT," +
                                                                        getEpisodio().getPaciente().getFolioPaciente()+"::BIGINT);";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (!rst.isEmpty()) {  
                ArrayList vRowTemp = (ArrayList)rst.get(0); 
                getEpisodio().getPaciente().setNombres((String)vRowTemp.get(0));
                getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(1));
                getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(2));
                edad=(String)vRowTemp.get(3);
                if (edad.compareTo("")!=0){
                    if(edad.substring(0, 3).compareTo("000")!=0){
                        if (edad.charAt(0)=='0')
                           getEpisodio().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                        else
                           getEpisodio().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                    }else{
                        if (edad.substring(4, 6).compareTo("00")!=0)
                            getEpisodio().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                        else
                            getEpisodio().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                        }
                }
                getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(4)).intValue());
                setFechaSolicitud((Date)vRowTemp.get(5));
                getEpisodio().getPaciente().getSeg().setNumero((String)vRowTemp.get(6));
                getEpisodio().getArea().setDescripcion((String)vRowTemp.get(7));
                getEpisodio().getArea().setTipo((String)vRowTemp.get(8));
                //setListaDetalleMed( new ArrayList<DetalleMedicamentos>());
                buscaDetallesMedicamentoEXP(getReceta().getConsecReceta());
            }
        }
    }
    
    public void buscaDetallesMedicamentoEXP(long consecutivo) throws Exception{
        ArrayList rst = null;
        DetalleMedicamentos oS=null;
        //ArrayList<DetalleMedicamentos> sig= new ArrayList<DetalleMedicamentos>();
        String sQuery="";
        int i=0;
        if(consecutivo==0){
            throw new Exception("DetalleRecetaHRRB.BuscaDetallesMedicamentoEXP: ERROR, Faltan Datos");
        }else{
            sQuery="SELECT * FROM buscadetallerecetaEXP("+consecutivo+"::bigint);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
              for(i=0; i<rst.size();i++){
                  oS= new DetalleMedicamentos();
                  ArrayList vRowTemp = (ArrayList)rst.get(i);
                  oS.getMedicamento().setClaveMedicamento((String)vRowTemp.get(0)); 
                  oS.getMedicamento().setNombre((String)vRowTemp.get(1)); 
                  oS.getMedicamento().setPresentacion((String)vRowTemp.get(2)); 
                  oS.getMedicamento().setDetRecetaHRRB(new DetalleRecetaHRRB());
                  oS.getMedicamento().getDetRecetaHRRB().setDosis((String)vRowTemp.get(3));
                  oS.getMedicamento().getDetRecetaHRRB().getVia().setValor((String)vRowTemp.get(4));
                  oS.getMedicamento().getDetRecetaHRRB().setFrecuencia((String)vRowTemp.get(5));
                  oS.getMedicamento().getDetRecetaHRRB().setDuracion((String)vRowTemp.get(6));
                  oS.getMedicamento().getDetRecetaHRRB().setCantidad(((Double)vRowTemp.get(7)).intValue());
                  getListaDetalleMed().add(oS);
              }
            }
        }
    }

    public int getCantidad() {
        return nCantidad;
    }

    public void setCantidad(int valor) {
        nCantidad = valor;
    }

    public Receta getReceta() {
        return oReceta;
    }

    public void setReceta(Receta valor) {
        oReceta = valor;
    }

    public Medicamento getMedicamento() {
        return oMedicamento;
    }

    public void setMedicamento(Medicamento valor) {
        oMedicamento = valor;
    }

    public ArrayList<DetalleMedicamentos> getListaDetalleMed() {
        return listaDetalleMed;
    }

    public void setListaDetalleMed(ArrayList<DetalleMedicamentos> listaDetalleMed) {
        this.listaDetalleMed = listaDetalleMed;
    }

    public Date getFechaSurtido() {
        return fechaSurtido;
    }

    public void setFechaSurtido(Date fechaSurtido) {
        this.fechaSurtido = fechaSurtido;
    }

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oMedicamento;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable oValor) {
        this.oMedicamento = (Medicamento)oValor;
    }

    @Override
    public boolean buscar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
}

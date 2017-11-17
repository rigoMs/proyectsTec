package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo:
 *
 * @author : Sampier, América, Rafael
 * @version: 1.0
 */
public class Medicamento extends ServicioCobrable implements Serializable {

    private long nCodBarras = 0;
    private String sClaveMedicamento;
    private String sDescripcion;
    private String sNombre;
    private String sPresentacion;
    private String sSustanciaActiva;
    private String sNombreCve;
    private List<Medicamento> lListaDiagcve;
    private DetalleMedicamentos oDetalle; //Para facilitar consultas, incluido desde CAPASITS
    
    /*Creados para Fase II*/
    private MedicamentoAplicado oMedAplic; //Para facilitar consultas
    private boolean bEsCapasits;
    private boolean bIncluidoEnSegPop;
    private Parametrizacion oFormaFarmaceutica;
    private Parametrizacion oGrupoTerapeutico;
    private Parametrizacion oNivelAtencion;	
    private String sConcentracion;
    private String sGpoLeySalud;
    private ArrayList<Receta> oReceta;
    private ArrayList<RecetarioColectivo> oRecetarioColectivo;
    private ArrayList<MedicamentoAplicado> oMedicamentoAplicado;
    private DetalleRecetaHRRB oDetRecetaHRRB;
    private DetalleMedicamentos oDetalleMedicamentos[];

    public Medicamento(String sClaveMedicamento, String sDescripcion, String sNombre, String sSustanciaActiva, DetalleMedicamentos oDetalle, String sPresentacion, int nCodBarras) {
        this.sClaveMedicamento = sClaveMedicamento;
        this.sDescripcion = sDescripcion;
        this.sNombre = sNombre;
        this.sSustanciaActiva = sSustanciaActiva;
        this.oDetalle = oDetalle;
        this.sPresentacion = sPresentacion;
        this.nCodBarras = nCodBarras;
    }

    public Medicamento() {
    }

    public Medicamento(String sPresentacion) {
        this.sPresentacion = sPresentacion;
    }

    //********************MÉTODO DE CAPASITS**************************
    @Override
    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaBarrasMedicamento(" + getCodBarras() + ");";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setClaveMedicamento(((String) vRowTemp.get(0)));
            this.setPresentacion(((String) vRowTemp.get(1)));
            this.setDescripcion(((String) vRowTemp.get(2)));
            this.setNombre(((String) vRowTemp.get(3)));
            this.setSustanciaActiva(((String) vRowTemp.get(4)));

            bRet = true;
        }
        return bRet;
    }

    //*************************MÉTODO CAPASITS****************************
    @Override
    public Medicamento[] buscarTodos() throws Exception {
        Medicamento arrRet1[] = null, oMedicamento = null;
        ArrayList rst = null;
        ArrayList<Medicamento> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscatodosmedicamento();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet1 = new Medicamento[rst.size()];
            vObj = new ArrayList<Medicamento>();
            for (i = 0; i < rst.size(); i++) {
                oMedicamento = new Medicamento();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMedicamento.setClaveMedicamento(((String) vRowTemp.get(0)));
                oMedicamento.setDescripcion(((String) vRowTemp.get(2)));
                oMedicamento.setNombre(((String) vRowTemp.get(4)));
                oMedicamento.setSustanciaActiva(((String) vRowTemp.get(6)));

                vObj.add(oMedicamento);
            }
            int nTam = vObj.size();
            arrRet1 = new Medicamento[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }

    public Medicamento[] buscaproximoscaducados() throws Exception {
        Medicamento arrRet1[] = null, oMedicamento = null;
        ArrayList rst = null;
        ArrayList<Medicamento> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaproximoscaducados();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Medicamento>();
            for (i = 0; i < rst.size(); i++) {
                oMedicamento = new Medicamento();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMedicamento.setClaveMedicamento(((String) vRowTemp.get(0)));
                oMedicamento.setNombre(((String) vRowTemp.get(1)));
                oMedicamento.setDescripcion(((String) vRowTemp.get(2)));
                vObj.add(oMedicamento);
            }
            int nTam = vObj.size();
            arrRet1 = new Medicamento[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }

    public boolean buscarPorClave() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this.sClaveMedicamento==null || this.sClaveMedicamento.equals("")) {
            throw new Exception("Medicamento.buscarPorClave: Error de programación falta la clave");
        } else {
            sQuery = "SELECT * FROM buscaLlaveMedicamento('" + this.sClaveMedicamento + "');";
            //System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                this.setClaveMedicamento(((String) vRowTemp.get(0)));
                this.setPresentacion(((String) vRowTemp.get(1)));
                this.setCodBarras(((Double)vRowTemp.get(2)).longValue());
                this.setDescripcion(((String) vRowTemp.get(3)));
                this.setNombre(((String) vRowTemp.get(4)));
                this.setSustanciaActiva(((String) vRowTemp.get(5)));
                this.setEsCapasits(((String)vRowTemp.get(6)).equals("S"));
                this.setClave( Integer.toString(((Double)vRowTemp.get(7)).intValue()) );

                bRet = true;
            }
        }
        return bRet;
    }

    public int insertar(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Medicamento.insertar: faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaMedicamento('" + sUsuario + "','" + 
                    getClaveMedicamento() + "','" + getPresentacion() + "'," + 
                    getCodBarras() + ", '" + getDescripcion() + "','" + 
                    getNombre() + "','" + getSustanciaActiva() + "','S');";
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

    public int modificar(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Medicamento.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaMedicamento('" + sUsuario + "','" + 
                    getClaveMedicamento() + "','" + getPresentacion() + "','" + 
                    getDescripcion() + "'," + getCodBarras() + ",'" + 
                    getNombre() + "','" + getSustanciaActiva() + "','S');";
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
            throw new Exception("Medicamento.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaMedicamento();";
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

    //*********************MÉTODO CAPASITS**********************
    public Medicamento[] buscarPresentaciones(String sIdProveedor, String sClaveMedicamento) throws Exception {
        Medicamento arrRet1[] = null, oMed = null;
        ArrayList rst = null;
        ArrayList<Medicamento> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;

        sQuery = "SELECT * FROM buscapresentacionesproveedor('" + sIdProveedor +
                "','" + sClaveMedicamento + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            vObj = new ArrayList<Medicamento>();
            for (i = 0; i < rst.size(); i++) {
                oMed = new Medicamento();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMed.setPresentacion((String) vRowTemp.get(0));
                vObj.add(oMed);
            }
            nTam = vObj.size();
            arrRet1 = new Medicamento[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }

    //Busca Coincidencias en descripcion de medicamentos
    public Medicamento[] buscarMedicamento(String Medi) throws Exception {
        Medicamento arrRet1[] = null, oDiag = null;
        ArrayList rst = null;
        ArrayList<Medicamento> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaMedicamento('" + Medi + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Medicamento>();
            for (i = 0; i < rst.size(); i++) {
                oDiag = new Medicamento();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDiag.setNombre((String) vRowTemp.get(0));
                oDiag.setClaveMedicamento((String) vRowTemp.get(1));
                vObj.add(oDiag);
            }
            nTam = vObj.size();
            arrRet1 = new Medicamento[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }

    //Retorna lista medicamentos
    public List<Medicamento> getListaMedicamento(String txt) throws Exception{
        List<Medicamento> lListaProce = null;

            lListaProce = new ArrayList<Medicamento>(Arrays.asList(
                    (new Medicamento()).buscarMedicamento(txt)));
        
        return lListaProce;
    }

    public List<String> completar(String sTxt) throws Exception{
    List<String> arrRet;
        arrRet = new ArrayList<String>();
        List<Medicamento> lis = getListaMedicamento(sTxt);
        for (Medicamento li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre() + "~" + li.getClaveMedicamento());
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

    //Retorna Claves medicamento 
    public List<Medicamento> getClavesMedicamento(String txt) throws Exception{
    ArrayList<Medicamento> lista = null;
            lista = new ArrayList<Medicamento>(Arrays.asList(
                    (new Medicamento()).buscarClavesMED(txt)));
        
        return lista;
    }

    public String claveMED() {
        String x = "";
        if (lListaDiagcve == null || lListaDiagcve.isEmpty()) {
            x = "";
        } else {
            if (lListaDiagcve.get(0).getClaveMedicamento().length() != 15) {
                x = "";
            } else {
                x = lListaDiagcve.get(0).getClaveMedicamento().substring(8, 12);
            }
        }
        return x;
    }

    //Busca Claves de Medicamentos
    public Medicamento[] buscarClavesMED(String diag) throws Exception {
    Medicamento arrRet1[] = null, oDiag = null;
    ArrayList rst = null;
        if (diag.compareToIgnoreCase("") != 0) {
            String v[] = diag.split("~");

            ArrayList<Medicamento> vObj = null;
            String sQuery = "";
            int i = 0, nTam = 0;
            sQuery = "SELECT * FROM buscaClavesMed('" + v[1] + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                vObj = new ArrayList<Medicamento>();
                for (i = 0; i < rst.size(); i++) {
                    oDiag = new Medicamento();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oDiag.setClaveMedicamento((String) vRowTemp.get(0));
                    oDiag.setPresentacion((String) vRowTemp.get(1));
                    oDiag.setCodBarras(((Double) vRowTemp.get(2)).intValue());
                    vObj.add(oDiag);
                }
                nTam = vObj.size();
                arrRet1 = new Medicamento[nTam];

                for (i = 0; i < nTam; i++) {
                    arrRet1[i] = vObj.get(i);
                }
            }
        }
        return arrRet1;
    }

    public String claveMedi() {
        String x = "";
        if (lListaDiagcve == null || lListaDiagcve.isEmpty()) {
            x = "";
        } else {
            x = lListaDiagcve.get(0).getClaveMedicamento();
        }
        return x;
    }

    public String clavePres() {
        String x = "";
        if (lListaDiagcve == null || lListaDiagcve.isEmpty()) {
            x = "";
        } else {
            x = lListaDiagcve.get(0).getPresentacion();
        }
        return x;
    }

    public String claveCB() {
        String x = "";
        if (lListaDiagcve == null || lListaDiagcve.isEmpty()) {
            x = "";
        } else {
            x = lListaDiagcve.get(0).getCodBarras() + "";
        }
        return x;
    }

    ///////////////////////////////////////////////////////////////////////////
    // Órdenes de servicio
    public List<String> completarMed(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<Medicamento> lis = getListaMedicamento(sTxt.toUpperCase());
        for (Medicamento li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre());
            }
        }
        return arrRet;
    }
    
    public Medicamento[] buscarMedicamentoNoCntrl(String Medi) throws Exception {
    Medicamento arrRet1[] = null, oDiag = null;
    ArrayList rst = null;
    ArrayList<Medicamento> vObj = null;
    String sQuery = "";
    int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarmedicamentosnocontrolados('" + Medi + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Medicamento>();
            for (i = 0; i < rst.size(); i++) {
                oDiag = new Medicamento();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDiag.setNombre((String) vRowTemp.get(0));
                oDiag.setClaveMedicamento(((String)vRowTemp.get(1)).trim());
                vObj.add(oDiag);
            }
            nTam = vObj.size();
            arrRet1 = new Medicamento[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }
    
    public List<Medicamento> getListaMedicamentoNoCntrl(String txt) throws Exception{
        List<Medicamento> lListaProce = null;

            lListaProce = new ArrayList<Medicamento>(Arrays.asList((new Medicamento()).buscarMedicamentoNoCntrl(txt)));
        
        return lListaProce;
    }
    
    public List<String> completarMedNoCntrl(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<Medicamento> lis = getListaMedicamentoNoCntrl(sTxt.toUpperCase());
        for (Medicamento li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre());
            }
        }
        return arrRet;
    }
    
    public Medicamento[] buscarMedicamentoCntrl(String Medi) throws Exception {
    Medicamento arrRet1[] = null, oDiag = null;
    ArrayList rst = null;
    ArrayList<Medicamento> vObj = null;
    String sQuery = "";
    int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarmedicamentoscontrolados('" + Medi + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Medicamento>();
            for (i = 0; i < rst.size(); i++) {
                oDiag = new Medicamento();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDiag.setNombre((String) vRowTemp.get(0));
                oDiag.setClaveMedicamento(((String)vRowTemp.get(1)).trim());
                vObj.add(oDiag);
            }
            nTam = vObj.size();
            arrRet1 = new Medicamento[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }
    
    public List<Medicamento> getListaMedicamentoCntrl(String txt) throws Exception {
        List<Medicamento> lListaProce = null;

            lListaProce = new ArrayList<Medicamento>(Arrays.asList((new Medicamento()).buscarMedicamentoCntrl(txt)));
        
        return lListaProce;
    }
    
    public List<String> completarMedCntrl(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<Medicamento> lis = getListaMedicamentoCntrl(sTxt.toUpperCase());
        for (Medicamento li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre());
            }
        }
        return arrRet;
    }
    
    public Medicamento[] buscarMedicamentoOnco() throws Exception{
    Medicamento arrRet[]=null, oMed=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
        sQuery = "SELECT * FROM buscarmedicamentosoncologia();";
        oAD=new AccesoDatos(); 
        if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
        }
        if (rst != null) {
            arrRet = new Medicamento[rst.size()];
            for (i = 0; i < rst.size(); i++) {
                oMed = new Medicamento();
                ArrayList vRowTemp = (ArrayList)rst.get(i);
                oMed.setNombre(((String)vRowTemp.get(0)).trim());
                oMed.setClaveMedicamento(((String)vRowTemp.get(1)).trim());
                arrRet[i]=oMed;
            }
        } 
        return arrRet; 
    }
        
    public void equalsMedOnco(Medicamento arrMed[]){
        for(Medicamento Med:arrMed){
            if(this.getClaveMedicamento().equals(Med.getClaveMedicamento())){
                this.setNombre(Med.getNombre());
                this.setClaveMedicamento(Med.getClaveMedicamento());
            }
        }
    }
    
    public boolean buscarPorNombre() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaMedicamentoNombre('" + this.getNombre() + "');";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setDetalle(new DetalleMedicamentos());
            this.setClaveMedicamento(((String) vRowTemp.get(0)));
            this.setPresentacion(((String) vRowTemp.get(1)));
            this.setCodBarras(((Double) vRowTemp.get(2)).longValue());
            this.getDetalle().setLote(((String) vRowTemp.get(3)));
            this.setGpoLeySalud(((String) vRowTemp.get(4)));
            bRet = true;
        }
        return bRet;
    }
    
    public boolean buscarPorNombreOnco() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscamedicamentonombreoncologia('" + 
                this.getNombre()+ "','"+this.getClaveMedicamento()+"');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setClaveMedicamento(((String) vRowTemp.get(0)));
            this.setPresentacion(((String) vRowTemp.get(1)));
            this.setCodBarras(((Double) vRowTemp.get(2)).longValue());
            this.setDetalle(new DetalleMedicamentos());
            
            bRet = true;
        }
        return bRet;
    }
    
    public Medicamento[] buscarMedicamentoAplicar(Date dFechaActual, 
            long folio, long cveEpi) throws Exception{
	Medicamento arrRet[]=null, oPar=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            sQuery = "SELECT * FROM buscamedicamentoparaaplicar('"+
                    df.format(dFechaActual)+"'::timestamp,"+folio+"::bigint,"+
                    cveEpi+"::bigint);";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new Medicamento[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    oPar = new Medicamento();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oPar.setNombre(((String)vRowTemp.get(0)).trim());
                    oPar.setClaveMedicamento(((String)vRowTemp.get(1)).trim());
                    oPar.setPresentacion(((String)vRowTemp.get(2)).trim());
                    oPar.setCodBarras(((Double) vRowTemp.get(3)).longValue());
                    arrRet[i]=oPar;
                }
            } 
        return arrRet; 
    }
        
    public void equalsMedicApli(Medicamento arrMedApli[]) {
        for (Medicamento MA : arrMedApli) {
            if (this.getNombre().equals(MA.getNombre())) {
                this.setNombre(MA.getNombre());
                this.setClaveMedicamento(MA.getClaveMedicamento());
                this.setPresentacion(MA.getPresentacion());
                this.setCodBarras(MA.getCodBarras());
            }
        }
    }
        
    //////////////////////////////////////////////////////////////////////////
    public String getNombreCve() {
        return sNombreCve;
    }

    public void setNombreCve(String sNombreCve) throws Exception{
        this.sNombreCve = sNombreCve;
        if (sNombreCve.compareTo("") != 0) {
            getClavesMedicamento(sNombreCve);
        }
    }
    
    public int equalsMedicamento(ArrayList<Medicamento> oMedicamento){
        int i=0;
        int nRet=0;
        for(;i<oMedicamento.size();i++){
            if(this.getClave().equals(oMedicamento.get(i).getClave())){
               nRet=i; 
            }
        }
        return nRet;
    } 

    public long getCodBarras() {
        return nCodBarras;
    }

    public void setCodBarras(long valor) {
        nCodBarras = valor;
    }

    public String getClaveMedicamento() {
        return sClaveMedicamento;
    }

    public void setClaveMedicamento(String valor) {
        sClaveMedicamento = valor;
    }

    public String getNombre() {
        return sNombre;
    }

    public void setNombre(String valor) {
        sNombre = valor;
    }

    public String getPresentacion() {
        return sPresentacion;
    }

    public void setPresentacion(String valor) {
        sPresentacion = valor;
    }

    public String getSustanciaActiva() {
        return sSustanciaActiva;
    }

    public void setSustanciaActiva(String valor) {
        sSustanciaActiva = valor;
    }
    
    public boolean isEsCapasits() {
        return bEsCapasits;
    }

    public void setEsCapasits(boolean bEsCapasits) {
        this.bEsCapasits = bEsCapasits;
    }

    public boolean isIncluidoEnSegPop() {
        return bIncluidoEnSegPop;
    }

    public void setIncluidoEnSegPop(boolean bIncluidoEnSegPop) {
        this.bIncluidoEnSegPop = bIncluidoEnSegPop;
    }

    public Parametrizacion getFormaFarmaceutica() {
        return oFormaFarmaceutica;
    }

    public void setFormaFarmaceutica(Parametrizacion formaFarmaceutica) {
        oFormaFarmaceutica = formaFarmaceutica;
    }

    public Parametrizacion getGrupoTerapeutico() {
        return oGrupoTerapeutico;
    }

    public void setGrupoTerapeutico(Parametrizacion ogrupoTerapeutico) {
        oGrupoTerapeutico = ogrupoTerapeutico;
    }
    
    public Parametrizacion getNivelAtencion() {
        return oNivelAtencion;
    }

    public void setNivelAtencion(Parametrizacion onivelAtencion) {
        oNivelAtencion = onivelAtencion;
    }
    
    public String getConcentracion() {
        return sConcentracion;
    }

    public void setConcentracion(String sConcentracion) {
        this.sConcentracion = sConcentracion;
    }

    public ArrayList<Receta> getReceta() {
        return oReceta;
    }

    public void setReceta(ArrayList<Receta> oReceta) {
        this.oReceta = oReceta;
    }

    public ArrayList<RecetarioColectivo> getRecetarioColectivo() {
        return oRecetarioColectivo;
    }

    public void setRecetarioColectivo(ArrayList<RecetarioColectivo> oRecetarioColectivo) {
        this.oRecetarioColectivo = oRecetarioColectivo;
    }

    public ArrayList<MedicamentoAplicado> getMedicamentoAplicado() {
        return oMedicamentoAplicado;
    }

    public void setMedicamentoAplicado(ArrayList<MedicamentoAplicado> oMedicamentoAplicado) {
        this.oMedicamentoAplicado = oMedicamentoAplicado;
    }

    public DetalleMedicamentos getDetalle() {
        return oDetalle;
    }

    public void setDetalle(DetalleMedicamentos oDetalle) {
        this.oDetalle = oDetalle;
    }

    public DetalleRecetaHRRB getDetRecetaHRRB() {
        return oDetRecetaHRRB;
    }

    public void setDetRecetaHRRB(DetalleRecetaHRRB oDetRecetaHRRB) {
        this.oDetRecetaHRRB = oDetRecetaHRRB;
    }

    public DetalleMedicamentos[] getDetalleMedicamentos() {
        return oDetalleMedicamentos;
    }

    public void setDetalleMedicamentos(DetalleMedicamentos[] oDetalleMedicamentos) {
        this.oDetalleMedicamentos = oDetalleMedicamentos;
    }
    
    public String getGpoLeySalud() {
        return sGpoLeySalud;
    }

    public void setGpoLeySalud(String sGpoLeySalud) {
        this.sGpoLeySalud = sGpoLeySalud;
    }
}

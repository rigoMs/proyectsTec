package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;

/**
 * Objetivo:
 * @author : Pedro, Rafa, Libertad
 * @version: 1.0
 */
public class DetalleMedicamentos implements Serializable {
private AccesoDatos oAD;
private String sLote;
private Date dCaducidad;
private int nExistencia;
private float nPrecio;
private boolean bSurtido;
private Parametrizacion oEstado;
private Proveedor oProveedor;
private Medicamento oMedicamento;

    public DetalleMedicamentos(String sPresentacion, String sLote, int nCodBarras, Date dCaducidad, int nExistencia, float nPrecio, Parametrizacion oEstado, String sClaveMedicamento, String sDescripcion, String sNombre, String sSustanciaActiva, DetalleMedicamentos oDetalle) {
        this.oMedicamento = new Medicamento(sClaveMedicamento, sDescripcion, sNombre, sSustanciaActiva, oDetalle, sPresentacion, nCodBarras);
        this.sLote = sLote;
        this.dCaducidad = dCaducidad;
        this.nExistencia = nExistencia;
        this.nPrecio = nPrecio;
        this.oEstado = oEstado;
        oProveedor = new Proveedor();
    }

    public DetalleMedicamentos() {
        super();
        oProveedor = new Proveedor();
        oMedicamento = new Medicamento();
        bSurtido = true;
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("DetalleMedicamentos.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaBarrasMedicamento(" + 
                    getMedicamento().getCodBarras() + ");";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                bRet = true;
            }
        }
        return bRet;
    }

    public DetalleMedicamentos[] buscarTodos() throws Exception {
        DetalleMedicamentos arrRet[] = null, oDetMed = null;
        ArrayList rst = null;
        ArrayList<DetalleMedicamentos> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscatodosmedicamento();";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleMedicamentos>();
            for (i = 0; i < rst.size(); i++) {
                oDetMed = new DetalleMedicamentos();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetMed.getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                oDetMed.getMedicamento().setPresentacion(((String) vRowTemp.get(1)));
                oDetMed.getMedicamento().setDescripcion(((String) vRowTemp.get(2)));
                oDetMed.getMedicamento().setCodBarras(((Double) vRowTemp.get(3)).intValue());
                oDetMed.getMedicamento().setNombre(((String) vRowTemp.get(4)));
                oDetMed.setPrecio(((Double) vRowTemp.get(5)).intValue());
                oDetMed.getMedicamento().setSustanciaActiva(((String) vRowTemp.get(6)));
                oDetMed.setLote(((String) vRowTemp.get(7)));
                oDetMed.setExistencia(((Double) vRowTemp.get(8)).intValue());
                oDetMed.setCaducidad((Date) vRowTemp.get(9));
                vObj.add(oDetMed);
            }
            int nTam = vObj.size();
            arrRet = new DetalleMedicamentos[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public DetalleMedicamentos[] buscaproximoscaducados() throws Exception {
        DetalleMedicamentos arrRet[] = null, oDetMed = null;
        ArrayList rst = null;
        ArrayList<DetalleMedicamentos> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaproximoscaducados();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleMedicamentos>();
            for (i = 0; i < rst.size(); i++) {
                oDetMed = new DetalleMedicamentos();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetMed.getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                oDetMed.getMedicamento().setNombre(((String) vRowTemp.get(1)));
                oDetMed.getMedicamento().setDescripcion(((String) vRowTemp.get(2)));
                oDetMed.getMedicamento().setPresentacion(((String) vRowTemp.get(3)));
                oDetMed.setLote(((String) vRowTemp.get(4)));
                oDetMed.setExistencia(((Double) vRowTemp.get(5)).intValue());
                oDetMed.setCaducidad((Date) vRowTemp.get(6));
                vObj.add(oDetMed);
            }
            int nTam = vObj.size();
            arrRet = new DetalleMedicamentos[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public DetalleMedicamentos[] buscaCodigoBarras() throws Exception {
        DetalleMedicamentos arrRet[] = null, oDetMed = null;
        ArrayList rst = null;
        ArrayList<DetalleMedicamentos> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaBarrasDetalleMedicamento(" + 
                getMedicamento().getCodBarras() + ");";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleMedicamentos>();
            for (i = 0; i < rst.size(); i++) {
                oDetMed = new DetalleMedicamentos();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetMed.getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                oDetMed.getMedicamento().setPresentacion(((String) vRowTemp.get(1)));
                oDetMed.setLote(((String) vRowTemp.get(2)));
                oDetMed.getMedicamento().setCodBarras(((Double) vRowTemp.get(3)).intValue());
                oDetMed.setCaducidad((Date) vRowTemp.get(4));
                oDetMed.getMedicamento().setDescripcion(((String) vRowTemp.get(5)));
                oDetMed.getMedicamento().setNombre(((String) vRowTemp.get(6)));
                oDetMed.getMedicamento().setSustanciaActiva(((String) vRowTemp.get(7)));

                vObj.add(oDetMed);
            }
            int nTam = vObj.size();
            arrRet = new DetalleMedicamentos[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public DetalleMedicamentos[] buscaXnombre(String nom) throws Exception {
        DetalleMedicamentos arrRet[] = null, oDetMed = null;
        ArrayList rst = null;
        ArrayList<DetalleMedicamentos> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaDetalleMedicamentoXnombre('" + nom + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleMedicamentos>();
            for (i = 0; i < rst.size(); i++) {
                oDetMed = new DetalleMedicamentos();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetMed.getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                oDetMed.getMedicamento().setPresentacion(((String) vRowTemp.get(1)));
                oDetMed.setLote(((String) vRowTemp.get(2)));
                oDetMed.getMedicamento().setCodBarras(((Double) vRowTemp.get(3)).intValue());
                oDetMed.setCaducidad((Date) vRowTemp.get(4));
                oDetMed.getMedicamento().setDescripcion(((String) vRowTemp.get(5)));
                oDetMed.getMedicamento().setNombre(((String) vRowTemp.get(6)));
                oDetMed.getMedicamento().setSustanciaActiva(((String) vRowTemp.get(7)));
                oDetMed.setExistencia(((Double) vRowTemp.get(8)).intValue());

                vObj.add(oDetMed);
            }
            int nTam = vObj.size();
            arrRet = new DetalleMedicamentos[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public int insertar(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.nExistencia == 0) {   //completar llave
            throw new Exception("DetalleMedicamentos.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertadetallemedicamentos('" + 
                    sUsuario + "','" + 
                    this.getMedicamento().getClaveMedicamento() + 
                    "','" + this.getMedicamento().getPresentacion() + 
                    "','" + getLote() + "'," + 
                    this.getMedicamento().getCodBarras() + "::bigint,'" + 
                    getCaducidad() + "'," + getExistencia() + "::smallint," + 
                    getPrecio() + ",'1' );";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                    if(nRet == 1)
                    insertaMedicamento_Proveedor(sUsuario);
                }
            }
        }        
        return nRet;
    }

    public int modificar(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (getExistencia() < 0) {   //completar llave
            throw new Exception("DetalleMedicamentos.modificar: error de programación, faltan datos");
        } else {
            sQuery = "select * from modificaDetalleMedicamentos_Existencia('" + 
                    sUsuario + "','" + this.getMedicamento().getClaveMedicamento() + 
                    "','" + this.getMedicamento().getPresentacion() + "','" + 
                    getLote() + "'," + this.getMedicamento().getCodBarras()+","+ 
                    getExistencia() + "::smallint);";
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

    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("DetalleMedicamentos.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaDetalleMedicamentos();";
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

    public int insertaMedicamento_Proveedor(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.nExistencia == 0) {   //completar llave
            throw new Exception("DetalleMedicamentos.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaMedicamento_Proveedor('" + 
                    sUsuario + "','" + 
                    this.getMedicamento().getClaveMedicamento() + "','" + 
                    this.getMedicamento().getPresentacion() + "','" + getLote()+ 
                    "','" + getProveedor().getId() + "' );";
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

    public int surtirEnReceta(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.nExistencia < 0) {   //completar llave
            throw new Exception("DetalleMedicamentos.insertar: error de programación, faltan datos");
        } else {
            sQuery = "select * from insertaDetalleReceta('" + 
                    sUsuario + "',3,'" + 
                    this.getMedicamento().getClaveMedicamento() + "','" + 
                    this.getMedicamento().getPresentacion() + "'," + 
                    this.getMedicamento().getCodBarras() + ",'" + getLote()+"'," + 
                    getExistencia() + "::smallint,current_date,'S');";
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

    public DetalleMedicamentos[] buscarDetalleRecetaXDia(Date fechaDia) throws Exception {
        DetalleMedicamentos arrRet[] = null, oDetMed = null;
        ArrayList rst = null;
        ArrayList<DetalleMedicamentos> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaDetalleRecetaXDia('" + fechaDia + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleMedicamentos>();
            for (i = 0; i < rst.size(); i++) {
                oDetMed = new DetalleMedicamentos();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetMed.getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                oDetMed.getMedicamento().setNombre(((String) vRowTemp.get(1)));
                oDetMed.getMedicamento().setDescripcion(((String) vRowTemp.get(2)));
                oDetMed.getMedicamento().setPresentacion(((String) vRowTemp.get(3)));
                oDetMed.setExistencia(((Double) vRowTemp.get(4)).intValue());
                vObj.add(oDetMed);
            }
            int nTam = vObj.size();
            arrRet = new DetalleMedicamentos[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public DetalleMedicamentos[] buscarDetalleRecetaXRango(Date fechaDiaInicio, Date fechaDiaFin) throws Exception {
        DetalleMedicamentos arrRet[] = null, oDetMed = null;
        ArrayList rst = null;
        ArrayList<DetalleMedicamentos> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscadetallerecetaxrango('" + fechaDiaInicio + "','" + fechaDiaFin + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<DetalleMedicamentos>();
            for (i = 0; i < rst.size(); i++) {
                oDetMed = new DetalleMedicamentos();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDetMed.getMedicamento().setClaveMedicamento(((String) vRowTemp.get(0)));
                oDetMed.getMedicamento().setNombre(((String) vRowTemp.get(1)));
                oDetMed.getMedicamento().setDescripcion(((String) vRowTemp.get(2)));
                oDetMed.getMedicamento().setPresentacion(((String) vRowTemp.get(3)));
                oDetMed.setExistencia(((Double) vRowTemp.get(4)).intValue());
                vObj.add(oDetMed);
            }
            int nTam = vObj.size();
            arrRet = new DetalleMedicamentos[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public Date getCaducidad() {
        return dCaducidad;
    }

    public void setCaducidad(Date valor) {
        dCaducidad = valor;
    }

    public int getExistencia() {
        return nExistencia;
    }

    public void setExistencia(int valor) {
        nExistencia = valor;
    }

    public Parametrizacion getEstado() {
        return oEstado;
    }

    public void setEstado(Parametrizacion valor) {
        oEstado = valor;
    }

    public String getLote() {
        return sLote;
    }

    public void setLote(String valor) {
        sLote = valor;
    }

    public String getDescripcionPresentacion() {
        return this.oMedicamento.getPresentacion() + " " + 
                this.oMedicamento.getDescripcion();
    }

    public String getSemaforo() {
        String sSemaforo = "";
        Calendar dosMeses, seisMeses;
        dosMeses = Calendar.getInstance();
        dosMeses.setTime(new Date());
        dosMeses.add(Calendar.MONTH, 2);

        if (dCaducidad.after(new Date(dosMeses.getTime().toString()))) {
            sSemaforo = "#F7FE2E"; //amarillo
        } else {
            sSemaforo = "#FF0000"; //rojo
        }
        return sSemaforo;
    }

    public boolean getSurtido() {
        return bSurtido;
    }

    public void setSurtido(boolean bSurtido) {
        this.bSurtido = bSurtido;
    }   

    public float getPrecio() {
        return nPrecio;
    }

    public void setPrecio(float nPrecio) {
        this.nPrecio = nPrecio;
    }

    public float getSubTotal() {
        return getPrecio() * getExistencia();
    }

    public Proveedor getProveedor() {
        return oProveedor;
    }

    public void setProveedor(Proveedor oProveedor) {
        this.oProveedor = oProveedor;
    }
    
    public Medicamento getMedicamento(){
        return this.oMedicamento;
    }
    public void setMedicamento(Medicamento val){
        this.oMedicamento = val;
    }

}

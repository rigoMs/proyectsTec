package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.Vector;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class Temperaturas implements Serializable {

    private AccesoDatos oAD;
    private Date dRegistro = null;
    private double nTemAmb = 0.0;
    private double nHumAmb = 0.0;
    private double nTemRef = 0.0;

    public Temperaturas() {
    }

    //************************************************************************************************
    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Teperaturas.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaLlaveTeperatura();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }

    //************************************************************************************************
    public Temperaturas[] buscarTodos() throws Exception {
        Temperaturas arrRet[] = null, oTemperaturas = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosTeperatura();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new Temperaturas[rst.size()];
            for (i = 0; i < rst.size(); i++) {
            }
        }
        return arrRet;
    }

    //************************************************************************************************
    /**
     * Agrega nuevo Registro de Tempearuras al repositorio Regresa el número de
     * registros afectados (insertados)
     */
    public int insertar(String sUsuario) throws Exception {
        setRegistro(new Date());
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.dRegistro == null) {
            throw new Exception("Teperaturas.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaTemperatura('" + sUsuario + "','" + getRegistro() + "', " + nTemAmb + "," + nTemRef + "," + nHumAmb + ");";
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

    //************************************************************************************************
    public int modificar(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Teperaturas.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaTemperatura('" + sUsuario + "','" + this.getRegistro() + "'," + this.getTemAmb() + "," + this.getTemRef() + "," + this.getHumAmb() + ");";
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

    //************************************************************************************************
    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Teperaturas.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaTeperatura();";
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

    //************************************************************************************************
    public int buscaTemperaturaTurno() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.dRegistro == null) {
            sQuery = "SELECT * FROM buscaTemperaturaTurno();";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                }
            }
        } else {
            throw new Exception("Teperaturas.buscaTemperaturaTurno: error de programación, dRegistro ya existe");
        }
        return nRet;
    }

    //************************************************************************************************
    public Temperaturas[] buscarTodos(int anio, String mes, int turno) throws Exception {
        Temperaturas arrRet[] = null, oTem = null;
        ArrayList rst = null;
        ArrayList<Temperaturas> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        if (turno == 1) {
            sQuery = "SELECT * FROM buscaTodosTemperaturaDeMes('" + anio + "-" + mes + "'," + 1 + ");";
        }
        if (turno == 2) {
            sQuery = "SELECT * FROM buscaTodosTemperaturaDeMes('" + anio + "-" + mes + "'," + 2 + ");";
        }
        if (turno == 3) {
            sQuery = "SELECT * FROM buscaTodosTemperaturaDeMes('" + anio + "-" + mes + "'," + 3 + ");";
        }
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Temperaturas>();
            Calendar calendar = Calendar.getInstance();
            for (i = 0; i < rst.size(); i++) {
                oTem = new Temperaturas();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                calendar.setTime((Date) vRowTemp.get(0));
                Date now = calendar.getTime();
                oTem.setRegistro(new Timestamp(now.getTime()));
                oTem.setTemAmb(((Double) vRowTemp.get(1)));
                oTem.setTemRef(((Double) vRowTemp.get(2)));
                oTem.setHumAmb(((Double) vRowTemp.get(3)));
                vObj.add(oTem);
            }
            nTam = vObj.size();

            Calendar cal = Calendar.getInstance();
            for (int j = 0; j < vObj.size(); j++) {
                if (vObj.get(j).getRegistro() != null) {
                    cal.setTime(vObj.get(j).getRegistro());
                    break;
                }
            }

            int dias = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
            arrRet = new Temperaturas[dias];

            for (i = 0; i < nTam; i++) {//coloca los dias extraidos de la BD en el dia que les corresponde en el mes
                Calendar dia = Calendar.getInstance();
                dia.setTime(vObj.get(i).getRegistro());
                int diaM = dia.getTime().getDate();//******
                arrRet[diaM - 1] = vObj.get(i);
            }
            for (i = 0; i < dias; i++) {//llena el resto del vector asignandole solo el dia que corresponde al indice
                if (arrRet[i] == null) {
                    Calendar calAux = Calendar.getInstance();
                    calAux.set(Calendar.YEAR, Calendar.MONTH, i + 1);
                    arrRet[i] = new Temperaturas();
                    arrRet[i].setRegistro(calAux.getTime());
                }
            }
        }
        return arrRet;
    }

    //************************************************************************************************
    /**
     * Agrega nueva Temperaturas proveniente de ediciondeTemperaturas
        *
     */
    public int insertarEditado(String sUsuario) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this.dRegistro == null) {
            throw new Exception("Teperaturas.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaTemperatura('" + sUsuario + "','" + getRegistro() + "', " + nTemAmb + "," + nTemRef + "," + nHumAmb + ");";
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

    public Date getRegistro() {
        return dRegistro;
    }

    public void setRegistro(Date dRegistro) {
        this.dRegistro = dRegistro;
    }

    public double getTemAmb() {
        return nTemAmb;
    }

    public void setTemAmb(double nTemAmb) {
        this.nTemAmb = nTemAmb;
    }

    public double getHumAmb() {
        return nHumAmb;
    }

    public void setHumAmb(double nHumAmb) {
        this.nHumAmb = nHumAmb;
    }

    public double getTemRef() {
        return nTemRef;
    }

    public void setTemRef(double nTemRef) {
        this.nTemRef = nTemRef;
    }
}

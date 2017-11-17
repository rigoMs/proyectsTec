package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class Receta implements Serializable {

    private AccesoDatos oAD;
    private long nConsecReceta;
    private long nFolioReceta;
    private ArrayList arrMedicamento;
    private EpisodioMedico oEpisodio;

    public Receta() {
        nFolioReceta = 0;
    }

    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Receta.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaReceta("+getFolioReceta()+","+
                    this.oEpisodio.getPaciente().getFolioPaciente()+");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                this.oEpisodio = new EpisodioMedico();
                this.oEpisodio.setPaciente(new Paciente());
               ArrayList vRowTemp = (ArrayList) rst.get(0);
                   nConsecReceta = ((Double) vRowTemp.get(0)).intValue();
                   nFolioReceta = ((Double) vRowTemp.get(1)).intValue();
                   oEpisodio.getPaciente().setFolioPaciente(((Double) vRowTemp.get(2)).longValue());
                   oEpisodio.setClaveEpisodio(((Double) vRowTemp.get(3)).longValue());
                bRet = true;
            }
        }
        return bRet;
    }

    public Receta[] buscarTodos() throws Exception {
        Receta arrRet[] = null, oReceta = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosReceta();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new Receta[rst.size()];
            for (i = 0; i < rst.size(); i++) {
            }
        }
        return arrRet;
    }

    public int buscaRecetasSurtidasXdia(Date dia) throws Exception {
        int total = 0;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaNumeroRecetasSurtidasPorDia('" + dia + "');";
        oAD = new AccesoDatos();
        System.out.println(sQuery);
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() == 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            total = ((Double) vRowTemp.get(0)).intValue();
        }
        return total;
    }

    public int buscaRecetasSurtidasXRango(Date diaIni, Date diaFin) throws Exception {
        int total = 0;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscaNumeroRecetasSurtidasPorRango('" + diaIni + "','" + diaFin + "');";
        oAD = new AccesoDatos();
        System.out.println(sQuery);
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() == 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            total = ((Double) vRowTemp.get(0)).intValue();
        }
        return total;
    }

    public long insertar(String sUsuario) throws Exception {
        ArrayList rst = null;
        long nRet = 0;
        String sQuery = "";
        if (this.nFolioReceta == 0) {   //completar llave
            throw new Exception("Receta.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaReceta('" + sUsuario + "'," + 
                    getFolioReceta() + ", " + 
                    this.oEpisodio.getPaciente().getFolioPaciente() + "," + 
                    getEpisodioMedico().getClaveEpisodio() + ");";
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

    public int modificar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Receta.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaReceta();";
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

    public int eliminar() throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Receta.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaReceta();";
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

    public long getFolioReceta() {
        return nFolioReceta;
    }

    public void setFolioReceta(long valor) {
        nFolioReceta = valor;
    }

    public ArrayList getrrMedicamento() {
        return arrMedicamento;
    }

    public void setrrMedicamento(ArrayList valor) {
        arrMedicamento = valor;
    }

    public EpisodioMedico getEpisodioMedico() {
        return this.oEpisodio;
    }

    public void setEpisodioMedico(EpisodioMedico valor) {
        this.oEpisodio = valor;
    }

    public long getConsecReceta() {
        return nConsecReceta;
    }

    public void setConsecReceta(long nConsecReceta) {
        this.nConsecReceta = nConsecReceta;
    }
}

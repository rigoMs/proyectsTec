/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */

package mx.gob.hrrb.modelo.core;

import java.util.ArrayList;
import java.util.Date;
import java.util.Vector;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author sam
 */
public class ReporteMes {
    private AccesoDatos oAD;
    private String Clave = "";
    private String nombre = "";
    private int Vtseis = 0;
    private int Vtsiete = 0;
    private int Vtocho = 0;//dia veintiocho
    private int Vtnueve = 0;//dia veintinueve
    private int Tr = 0;//treinta
    private int Truno = 0;//treinta y uno
    private int uno = 0;//dia primero
    private int dos = 0;//dia dos
    private int tres = 0;//etc.....
    private int cuatro = 0;
    private int cinco = 0;
    private int seis = 0;
    private int siete = 0;
    private int ocho = 0;
    private int nueve = 0;
    private int Dz = 0;
    private int once = 0;
    private int doce = 0;
    private int trece = 0;
    private int catorce = 0;
    private int quince = 0;
    private int Dzseis = 0;
    private int Dzsiete = 0;
    private int Dzocho = 0;
    private int Dznueve = 0;
    private int Vt = 0;
    private int Vtuno = 0;
    private int Vtdos = 0;
    private int Vttres = 0;
    private int Vtcuatro  = 0;
    private int Vtcinco = 0;
    
    private int SubTotal = 0;

    public String getClave() {
        return Clave;
    }

    public void setClave(String Clave) {
        this.Clave = Clave;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }    
    
    public int getVtocho() {
        return Vtocho;
    }

    public void setVtocho(int Vtocho) {
        this.Vtocho += Vtocho;
    }

    public int getVtnueve() {
        return Vtnueve;
    }

    public void setVtnueve(int Vtnueve) {
        this.Vtnueve += Vtnueve;
    }

    public int getTr() {
        return Tr;
    }

    public void setTr(int Tr) {
        this.Tr += Tr;
    }

    public int getTruno() {
        return Truno;
    }

    public void setTruno(int Truno) {
        this.Truno += Truno;
    }

    public int getUno() {
        return uno;
    }

    public void setUno(int uno) {
        this.uno += uno;
    }

    public int getDos() {
        return dos;
    }

    public void setDos(int dos) {
        this.dos += dos;
    }

    public int getTres() {
        return tres;
    }

    public void setTres(int tres) {
        this.tres += tres;
    }

    public int getCuatro() {
        return cuatro;
    }

    public void setCuatro(int cuatro) {
        this.cuatro += cuatro;
    }

    public int getCinco() {
        return cinco;
    }

    public void setCinco(int cinco) {
        this.cinco += cinco;
    }

    public int getSeis() {
        return seis;
    }

    public void setSeis(int seis) {
        this.seis += seis;
    }

    public int getSiete() {
        return siete;
    }

    public void setSiete(int siete) {
        this.siete += siete;
    }

    public int getOcho() {
        return ocho;
    }

    public void setOcho(int ocho) {
        this.ocho += ocho;
    }

    public int getNueve() {
        return nueve;
    }

    public void setNueve(int nueve) {
        this.nueve += nueve;
    }

    public int getDz() {
        return Dz;
    }

    public void setDz(int Dz) {
        this.Dz += Dz;
    }

    public int getOnce() {
        return once;
    }

    public void setOnce(int once) {
        this.once += once;
    }

    public int getDoce() {
        return doce;
    }

    public void setDoce(int doce) {
        this.doce += doce;
    }

    public int getTrece() {
        return trece;
    }

    public void setTrece(int trece) {
        this.trece += trece;
    }

    public int getCatorce() {
        return catorce;
    }

    public void setCatorce(int catorce) {
        this.catorce += catorce;
    }

    public int getQuince() {
        return quince;
    }

    public void setQuince(int quince) {
        this.quince += quince;
    }

    public int getDzseis() {
        return Dzseis;
    }

    public void setDzseis(int Dzseis) {
        this.Dzseis += Dzseis;
    }

    public int getDzsiete() {
        return Dzsiete;
    }

    public void setDzsiete(int Dzsiete) {
        this.Dzsiete += Dzsiete;
    }

    public int getDzocho() {
        return Dzocho;
    }

    public void setDzocho(int Dzocho) {
        this.Dzocho += Dzocho;
    }

    public int getDznueve() {
        return Dznueve;
    }

    public void setDznueve(int Dznueve) {
        this.Dznueve += Dznueve;
    }

    public int getVt() {
        return Vt;
    }

    public void setVt(int Vt) {
        this.Vt += Vt;
    }

    public int getVtuno() {
        return Vtuno;
    }

    public void setVtuno(int Vtuno) {
        this.Vtuno += Vtuno;
    }

    public int getVtdos() {
        return Vtdos;
    }

    public void setVtdos(int Vtdos) {
        this.Vtdos += Vtdos;
    }

    public int getVttres() {
        return Vttres;
    }

    public void setVttres(int Vttres) {
        this.Vttres += Vttres;
    }

    public int getVtcuatro() {
        return Vtcuatro;
    }

    public void setVtcuatro(int Vtcuatro) {
        this.Vtcuatro += Vtcuatro;
    }

    public int getVtcinco() {
        return Vtcinco;
    }

    public void setVtcinco(int Vtcinco) {
        this.Vtcinco += Vtcinco;
    }

    public int getVtseis() {
        return Vtseis;
    }

    public void setVtseis(int Vtseis) {
        this.Vtseis += Vtseis;
    }

    public int getVtsiete() {
        return Vtsiete;
    }

    public void setVtsiete(int Vtsiete) {
        this.Vtsiete += Vtsiete;
    }

    public int getSubTotal() {
        return SubTotal;
    }

    public void setSubTotal(int SubTotal) {
        this.SubTotal = SubTotal;
    }
    
    
    public ReporteMes[] buscarDetalleRecetaXMes(Date fechaDia) throws Exception {
        ReporteMes arrRet[] = null, oRepMen = null;
        ArrayList rst = null;
        Vector<ReporteMes> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaMedicamentosSurtidosXMes('" + fechaDia + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new Vector<ReporteMes>();
            for (i = 0; i < rst.size(); i++) {
                oRepMen = new ReporteMes();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                
                oRepMen.setClave(((String) vRowTemp.get(0)));
                oRepMen.setNombre(((String) vRowTemp.get(1)));
                oRepMen.setVtseis(((Double) vRowTemp.get(2)).intValue());
                oRepMen.setVtsiete(((Double) vRowTemp.get(3)).intValue());
                oRepMen.setVtocho(((Double) vRowTemp.get(4)).intValue());
                oRepMen.setVtnueve(((Double) vRowTemp.get(5)).intValue());
                oRepMen.setTr(((Double) vRowTemp.get(6)).intValue());
                oRepMen.setTruno(((Double) vRowTemp.get(7)).intValue());
                oRepMen.setUno(((Double) vRowTemp.get(8)).intValue());
                oRepMen.setDos(((Double) vRowTemp.get(9)).intValue());
                oRepMen.setTres(((Double) vRowTemp.get(10)).intValue());
                
                oRepMen.setCuatro(((Double) vRowTemp.get(11)).intValue());
                oRepMen.setCinco(((Double) vRowTemp.get(12)).intValue());
                oRepMen.setSeis(((Double) vRowTemp.get(13)).intValue());
                oRepMen.setSiete(((Double) vRowTemp.get(14)).intValue());
                oRepMen.setOcho(((Double) vRowTemp.get(15)).intValue());
                oRepMen.setNueve(((Double) vRowTemp.get(16)).intValue());
                oRepMen.setDz(((Double) vRowTemp.get(17)).intValue());
                oRepMen.setOnce(((Double) vRowTemp.get(18)).intValue());
                oRepMen.setDoce(((Double) vRowTemp.get(19)).intValue());
                oRepMen.setTrece(((Double) vRowTemp.get(20)).intValue());
                oRepMen.setCatorce(((Double) vRowTemp.get(21)).intValue());
                oRepMen.setQuince(((Double) vRowTemp.get(22)).intValue());
                oRepMen.setDzseis(((Double) vRowTemp.get(23)).intValue());
                oRepMen.setDzsiete(((Double) vRowTemp.get(24)).intValue());
                oRepMen.setDzocho(((Double) vRowTemp.get(25)).intValue());
                oRepMen.setDznueve(((Double) vRowTemp.get(26)).intValue());
                oRepMen.setVt(((Double) vRowTemp.get(27)).intValue());
                oRepMen.setVtuno(((Double) vRowTemp.get(28)).intValue());
                oRepMen.setVtdos(((Double) vRowTemp.get(29)).intValue());
                oRepMen.setVttres(((Double) vRowTemp.get(30)).intValue());
                oRepMen.setVtcuatro(((Double) vRowTemp.get(31)).intValue());
                oRepMen.setVtcinco(((Double) vRowTemp.get(32)).intValue());
                
                oRepMen.setSubTotal(((Double) vRowTemp.get(33)).intValue());
                
                vObj.add(oRepMen);
            }
            int nTam = vObj.size();
            arrRet = new ReporteMes[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.elementAt(i);
            }
        }
        return arrRet;
    }
    
}

package mx.gob.hrrb.modelo.core;

import java.util.ArrayList;
import java.io.Serializable;
import java.util.Arrays;
import java.util.List;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

public class Etnicidad implements Serializable{

	private Parametrizacion oHablaEspaniol;
	private Parametrizacion oHablaLenguaInd;
	private Parametrizacion oPertenenciaGpoInd;
        private String oHablaEspaniolStr;
	private String oHablaLenguaIndStr;
	private String oPertenenciaGpoIndStr;
        private String sHablaEspaniolP;
	private String sHablaLenguaIndP;
	private String sLenguaIndígenaP;
	private String sPertenenciaGpoIndP;
        private String sClaveLengua;
        private String sDescripcionLengua;
        private AccesoDatos oAD;
        private String sPerteneceGpoInd;
	

        public Etnicidad(){
            oHablaEspaniolStr="T0201";
            oHablaLenguaIndStr="T0202";
        oPertenenciaGpoIndStr="T0202";
        }
        
        //***************************************************************
        public Etnicidad[] buscarLenguasIndigenas() throws Exception{
	Etnicidad arrRet[]=null, oEtnicidad=null;
	ArrayList rst = null;
        ArrayList<Etnicidad> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "select * from buscaLenguaIndigena();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Etnicidad>();
			for (i = 0; i < rst.size(); i++) {
                            oEtnicidad = new Etnicidad();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEtnicidad.setClaveLengua((String)vRowTemp.get(0));
                            oEtnicidad.setDescripcionLengua((String)vRowTemp.get(1));
                            vObj.add(oEtnicidad);
			}
                    nTam = vObj.size();
                    arrRet = new Etnicidad[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}

    public String getClaveLengua() {
        return sClaveLengua;
    }

    public void setClaveLengua(String sClaveLengua) {
        this.sClaveLengua = sClaveLengua;
    }

    public String getDescripcionLengua() {
        return sDescripcionLengua;
    }

    public void setDescripcionLengua(String sDescripcionLengua) {
        this.sDescripcionLengua = sDescripcionLengua;
    }
    
    public String getPerteneceGpoInd(){
        return sPerteneceGpoInd;
    }
    
    public void setPerteneceGpoInd(String valor){
        sPerteneceGpoInd=valor;
    }

    public String getHablaEspaniolP() {
        return sHablaEspaniolP;
    }

    public void setHablaEspaniolP(String sHablaEspaniolP) {
        this.sHablaEspaniolP = sHablaEspaniolP;
    }

    public String getHablaLenguaIndP() {
        return sHablaLenguaIndP;
    }

    public void setHablaLenguaIndP(String sHablaLenguaIndP) {
        this.sHablaLenguaIndP = sHablaLenguaIndP;
    }

    public String getLenguaIndígenaP() {
        return sLenguaIndígenaP;
    }

    public void setLenguaIndígenaP(String sLenguaIndígenaP) {
        this.sLenguaIndígenaP = sLenguaIndígenaP;
    }

    public String getPertenenciaGpoIndP() {
        return sPertenenciaGpoIndP;
    }

    public void setPertenenciaGpoIndP(String sPertenenciaGpoIndP) {
        this.sPertenenciaGpoIndP = sPertenenciaGpoIndP;
    }

    public String getHablaEspaniolStr() {
        return oHablaEspaniolStr;
    }

    public void setHablaEspaniolStr(String oHablaEspaniolStr) {
        this.oHablaEspaniolStr = oHablaEspaniolStr;
    }

    public String getHablaLenguaIndStr() {
        return oHablaLenguaIndStr;
    }

    public void setHablaLenguaIndStr(String oHablaLenguaIndStr) {
        this.oHablaLenguaIndStr = oHablaLenguaIndStr;
        //System.out.println("sethablalenguaind: "+oHablaLenguaIndStr);
    }

    public String getPertenenciaGpoIndStr() {
        return oPertenenciaGpoIndStr;
    }

    public void setPertenenciaGpoIndStr(String oPertenenciaGpoIndStr) {
        this.oPertenenciaGpoIndStr = oPertenenciaGpoIndStr;
    }

    //Retorna Lista de lenguas indigenas
     public List<Etnicidad> getListaLenguasIndigenas() throws Exception{
        List<Etnicidad> lLista = null;
       
           lLista = new ArrayList<Etnicidad>(Arrays.asList(
                   (new Etnicidad()).buscarLenguasIndigenas()));
       
        return lLista;
    }     
}
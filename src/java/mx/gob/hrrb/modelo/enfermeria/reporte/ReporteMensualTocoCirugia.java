
package mx.gob.hrrb.modelo.enfermeria.reporte;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author J2GOV
 */
public class ReporteMensualTocoCirugia implements Serializable{
    
    private AccesoDatos oAD;
    private Date dFechaInicio;
    private Date dFechaFin;
    private Date dDia;
    private String sTotalAbortos;
    private String sMadresMenor20A;
    private String sPartoEutocico;
    private String sPartoDistocico;
    private String sCesare;
    private String sMadresMenor20N;
    private String s2500grMenorV35;
    private String s2500grMayorV35;
    private String s2500grMenorV37;
    private String s2500grMayorV37;
    private String sDF20a26;
    private String sDF27Mas;
    private String sLUI;
    private String sDFMaterna;
    private String sDFRN;
    private String sDUIPostParto;
    private String sDIUCesarea;
    private String sDIUPostAborto;
    private String sOTSPostParto;
    private String sOTSCesarea;
    private String sOTSPostAborto;
    private String sSolucionesHormonales;
    private String sAcp20Anhos;
    private String sOTB;
    
    private SimpleDateFormat df;
    
    public ReporteMensualTocoCirugia(){
        //dFechaInicio= new Date();
        dFechaFin = new Date();
        df= new SimpleDateFormat("yyyy-MM-dd");
    }
    
    public ReporteMensualTocoCirugia[] buscaReporteMensualTococirugia() throws Exception{
        ReporteMensualTocoCirugia arrRet[]=null, oRp;
        ArrayList rst=null;        
        String sQuery="";
        int i=0;
        if(this.getFechaInicio()==null || this.getFechaFin()==null){
            throw new Exception("ReporteMensualTocoCirugia.buscaReporteMensualTococirugia:error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaReporteMensualTococirugia('"
                    +this.getFechaInicioStr() +"'::date,'"
                    +this.getFechaFinStr()+"'::date);";
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrRet= new ReporteMensualTocoCirugia[rst.size()];
                for(i=0; i<rst.size();i++){
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oRp= new ReporteMensualTocoCirugia();
                    oRp.setDia((Date)vRowTemp.get(0));
                    oRp.setTotalAbortos((((Double)vRowTemp.get(1)).longValue())==0?"":String.valueOf(((Double)vRowTemp.get(1)).intValue()));
                    oRp.setMadresMenor20A((((Double)vRowTemp.get(2)).longValue())==0?"":String.valueOf(((Double)vRowTemp.get(2)).intValue()));
                    oRp.setPartoEutocico((((Double)vRowTemp.get(3)).longValue())==0?"":String.valueOf(((Double)vRowTemp.get(3)).intValue()));
                    oRp.setPartoDistocico((((Double)vRowTemp.get(4)).longValue())==0?"":String.valueOf(((Double)vRowTemp.get(4)).intValue()));
                    oRp.setCesare((((Double)vRowTemp.get(5)).longValue())==0?"":String.valueOf(((Double)vRowTemp.get(5)).intValue()));
                    oRp.setMadresMenor20N((((Double)vRowTemp.get(6)).longValue())==0?"":String.valueOf(((Double)vRowTemp.get(6)).intValue())); 
                    oRp.setS2500grMenorV35(((Double)vRowTemp.get(7)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(7)).intValue()));
                    oRp.setS2500grMayorV35(((Double)vRowTemp.get(8)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(8)).intValue()));
                    oRp.setS2500grMenorV37(((Double)vRowTemp.get(9)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(9)).intValue()));
                    oRp.setS2500grMayorV37(((Double)vRowTemp.get(10)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(10)).intValue()));
                    oRp.setDF20a26(((Double)vRowTemp.get(11)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(11)).intValue()));
                    oRp.setDF27Mas(((Double)vRowTemp.get(12)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(12)).intValue()));
                    oRp.setLUI(((Double)vRowTemp.get(13)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(13)).intValue()));
                    oRp.setDFMaterna(((Double)vRowTemp.get(14)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(14)).intValue()));
                    oRp.setDFRN(((Double)vRowTemp.get(15)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(15)).intValue()));
                    oRp.setDUIPostParto(((Double)vRowTemp.get(16)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(16)).intValue()));
                    oRp.setDIUCesarea(((Double)vRowTemp.get(17)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(17)).intValue()));
                    oRp.setDIUPostAborto(((Double)vRowTemp.get(18)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(18)).intValue()));
                    oRp.setOTSPostParto(((Double)vRowTemp.get(19)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(19)).intValue()));
                    oRp.setOTSCesarea(((Double)vRowTemp.get(20)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(20)).intValue()));
                    oRp.setOTSPostAborto(((Double)vRowTemp.get(21)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(21)).intValue()));
                    oRp.setSolucionesHormonales(((Double)vRowTemp.get(22)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(22)).intValue()));
                    oRp.setAcp20Anhos(((Double)vRowTemp.get(23)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(23)).intValue()));
                    oRp.setOTB(((Double)vRowTemp.get(24)).intValue()==0?"":String.valueOf(((Double)vRowTemp.get(24)).intValue()));
                    arrRet[i]=oRp;
                }
            }
        }
        return arrRet;
    }

    public Date getFechaInicio() {
        return dFechaInicio;
    }

    public void setFechaInicio(Date dFechaInicio) {
        this.dFechaInicio = dFechaInicio;
    }

    public Date getFechaFin() {
        return dFechaFin;
    }

    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    public Date getDia() {
        return dDia;
    }

    public void setDia(Date dDia) {
        this.dDia = dDia;
    }

    public String getTotalAbortos() {
        return sTotalAbortos;
    }

    public void setTotalAbortos(String sTotalAbortos) {
        this.sTotalAbortos = sTotalAbortos;
    }

    public String getMadresMenor20A() {
        return sMadresMenor20A;
    }

    public void setMadresMenor20A(String sMadresMenor20A) {
        this.sMadresMenor20A = sMadresMenor20A;
    }

    public String getPartoEutocico() {
        return sPartoEutocico;
    }

    public void setPartoEutocico(String sPartoEutocico) {
        this.sPartoEutocico = sPartoEutocico;
    }

    public String getPartoDistocico() {
        return sPartoDistocico;
    }

    public void setPartoDistocico(String sPartoDistocico) {
        this.sPartoDistocico = sPartoDistocico;
    }

    public String getCesare() {
        return sCesare;
    }

    public void setCesare(String sCesare) {
        this.sCesare = sCesare;
    }

    public String getMadresMenor20N() {
        return sMadresMenor20N;
    }

    public void setMadresMenor20N(String sMadresMenor20N) {
        this.sMadresMenor20N = sMadresMenor20N;
    }

    public String getS2500grMenorV35() {
        return s2500grMenorV35;
    }

    public void setS2500grMenorV35(String s2500grMenorV35) {
        this.s2500grMenorV35 = s2500grMenorV35;
    }

    public String getS2500grMayorV35() {
        return s2500grMayorV35;
    }

    public void setS2500grMayorV35(String s2500grMayorV35) {
        this.s2500grMayorV35 = s2500grMayorV35;
    }

    public String getS2500grMenorV37() {
        return s2500grMenorV37;
    }

    public void setS2500grMenorV37(String s2500grMenorV37) {
        this.s2500grMenorV37 = s2500grMenorV37;
    }

    public String getS2500grMayorV37() {
        return s2500grMayorV37;
    }

    public void setS2500grMayorV37(String s2500grMayorV37) {
        this.s2500grMayorV37 = s2500grMayorV37;
    }

    public String getDF20a26() {
        return sDF20a26;
    }

    public void setDF20a26(String sDF20a26) {
        this.sDF20a26 = sDF20a26;
    }

    public String getDF27Mas() {
        return sDF27Mas;
    }

    public void setDF27Mas(String sDF27Mas) {
        this.sDF27Mas = sDF27Mas;
    }

    public String getLUI() {
        return sLUI;
    }

    public void setLUI(String sLUI) {
        this.sLUI = sLUI;
    }

    public String getDFMaterna() {
        return sDFMaterna;
    }

    public void setDFMaterna(String sDFMaterna) {
        this.sDFMaterna = sDFMaterna;
    }

    public String getDFRN() {
        return sDFRN;
    }

    public void setDFRN(String sDFRN) {
        this.sDFRN = sDFRN;
    }

    public String getDUIPostParto() {
        return sDUIPostParto;
    }

    public void setDUIPostParto(String sDUIPostParto) {
        this.sDUIPostParto = sDUIPostParto;
    }

    public String getDIUCesarea() {
        return sDIUCesarea;
    }

    public void setDIUCesarea(String sDIUCesarea) {
        this.sDIUCesarea = sDIUCesarea;
    }

    public String getDIUPostAborto() {
        return sDIUPostAborto;
    }

    public void setDIUPostAborto(String sDIUPostAborto) {
        this.sDIUPostAborto = sDIUPostAborto;
    }

    public String getOTSPostParto() {
        return sOTSPostParto;
    }

    public void setOTSPostParto(String sOTSPostParto) {
        this.sOTSPostParto = sOTSPostParto;
    }

    public String getOTSCesarea() {
        return sOTSCesarea;
    }

    public void setOTSCesarea(String sOTSCesarea) {
        this.sOTSCesarea = sOTSCesarea;
    }

    public String getOTSPostAborto() {
        return sOTSPostAborto;
    }

    public void setOTSPostAborto(String sOTSPostAborto) {
        this.sOTSPostAborto = sOTSPostAborto;
    }

    public String getSolucionesHormonales() {
        return sSolucionesHormonales;
    }

    public void setSolucionesHormonales(String sSolucionesHormonales) {
        this.sSolucionesHormonales = sSolucionesHormonales;
    }

    public String getAcp20Anhos() {
        return sAcp20Anhos;
    }

    public void setAcp20Anhos(String sAcp20Anhos) {
        this.sAcp20Anhos = sAcp20Anhos;
    }

    public String getOTB() {
        return sOTB;
    }

    public void setOTB(String sOTB) {
        this.sOTB = sOTB;
    }
    
    public String getFechaInicioStr(){
        if(dFechaInicio==null){
            return "";
        }else{
        return df.format(dFechaInicio);
        }
    }
    
    public String getFechaFinStr(){
        return df.format(dFechaFin);
    }
    
    public String getFechaInicioStr2(){
        if(dFechaInicio==null){
            return "";
        }else{
            SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
        return f.format(dFechaInicio);
        }
    }
    
    public String getFechaFinStr2(){
        SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
        return f.format(dFechaFin);
    }
    
    public String getDiaStr(){
        SimpleDateFormat f= new SimpleDateFormat("MM/dd");
        return f.format(dDia);
    }
            
}

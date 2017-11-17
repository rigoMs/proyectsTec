package mx.gob.hrrb.modelo.serv;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Seguro;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Pablo
 */
public class ReporteHojaEgresoHemoderivados {
    
    private AccesoDatos oAD;
    private String sUsuarioFirmado;
    private SolicitudSangre oSolicitud;
    private DetalleSolicitudHemoderivado oDetalle;
    private int nCant1;
    private int nCant2;
    private int nCant3;
    private int nCant4;
    private int nCant5;
    private int nCant6;
    private int nCant7;
    private int nCant8;
    private int nCant9;
    private int nCant10;
    private int nCant11;
    private int nCant12;
    private int nCant13;
    private int nCant14;
    private int nCant15;
    private int nCant16;
    private int nCant17;
    private int nCant18;
    private int nCant19;
    private int nCant20;
    private int nCant21;
    private int nCant22;
    private int nCant23;
    private int nCant24;
    private int nCant25;
    private int nCant26;
    private int nCant27;
    private int nCant28;
    private int nCant29;
    private int nCant30;
    private int nCant31;
    private int nCant32;
    private int nCant33;
    private int nCant34;
    private int nCant35;
    private int nCant36;
    private int nCant37;
    private int nCant38;
    private int nCant39;
    private int nCant40;
    private int nCant41;
    private int nCant42;
    private int nCant43;
    private int nCant44;
    private int nCant45;
    private int nCant46;
    private int nCant47;
    private int nCant48;
    private int nCE;
    private int nPFC;
    private int nCP;
    private int nCCP;
    private int nOtros;
    private int nPlasma;
    private int nST;
    private int nPRP;
    private EpisodioMedico oEpisodio;
    private int nFolio;
    
    
    public ReporteHojaEgresoHemoderivados(){
        HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
    }
    
    public ReporteHojaEgresoHemoderivados oReporteCantidad(String sNombre, String sApPaterno, String sApMaterno, int nNumExpe, Date dFecSol) throws Exception{
       ReporteHojaEgresoHemoderivados oCantidad=null;
       String sQuery="";
       ArrayList rst=null;
       System.out.println("Fecha: " + dFecSol);
       SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
       if(!sNombre.equals("") && !sApPaterno.equals("")){
           sQuery = "SELECT * FROM buscarProductosHojaEgresoHemoderivados('"+ sNombre +"','"+ sApPaterno +"', '"+ sApMaterno +"',0, '"+ oFec.format(dFecSol) +"')";
       }else if(sNombre.equals("") && sApPaterno.equals("") && nNumExpe != 0){
           sQuery = "SELECT * FROM buscarProductosHojaEgresoHemoderivados('','', '',"+ nNumExpe +", '"+ oFec.format(dFecSol) +"')";
       }else{
           throw new Exception("ReporteHojaEgresoHemoderivados.oReporteCantidad: error, faltan datos");
       }
       System.out.println(sQuery);
       oAD = new AccesoDatos();
       if(oAD.conectar()){
           rst = oAD.ejecutarConsulta(sQuery);
           oAD.desconectar();
       }
       if(rst != null && rst.size() == 1){
           oCantidad = new ReporteHojaEgresoHemoderivados();
           ArrayList vRowTemp = (ArrayList)rst.get(0);
           oCantidad.setCant1(((Double)vRowTemp.get(0)).intValue());
           oCantidad.setCant2(((Double)vRowTemp.get(1)).intValue());
           oCantidad.setCant3(((Double)vRowTemp.get(2)).intValue());
           oCantidad.setCant4(((Double)vRowTemp.get(3)).intValue());
           oCantidad.setCant5(((Double)vRowTemp.get(4)).intValue());
           oCantidad.setCant6(((Double)vRowTemp.get(5)).intValue());
           oCantidad.setCant7(((Double)vRowTemp.get(6)).intValue());
           oCantidad.setCant8(((Double)vRowTemp.get(7)).intValue());
           oCantidad.setCant9(((Double)vRowTemp.get(8)).intValue());
           oCantidad.setCant10(((Double)vRowTemp.get(9)).intValue());
           oCantidad.setCant11(((Double)vRowTemp.get(10)).intValue());
           oCantidad.setCant12(((Double)vRowTemp.get(11)).intValue());
           oCantidad.setCant13(((Double)vRowTemp.get(12)).intValue());
           oCantidad.setCant14(((Double)vRowTemp.get(13)).intValue());
           oCantidad.setCant15(((Double)vRowTemp.get(14)).intValue());
           oCantidad.setCant16(((Double)vRowTemp.get(15)).intValue());
           oCantidad.setCant17(((Double)vRowTemp.get(16)).intValue());
           oCantidad.setCant18(((Double)vRowTemp.get(17)).intValue());
           oCantidad.setCant19(((Double)vRowTemp.get(18)).intValue());
           oCantidad.setCant20(((Double)vRowTemp.get(19)).intValue());
           oCantidad.setCant21(((Double)vRowTemp.get(20)).intValue());
           oCantidad.setCant22(((Double)vRowTemp.get(21)).intValue());
           oCantidad.setCant23(((Double)vRowTemp.get(22)).intValue());
           oCantidad.setCant24(((Double)vRowTemp.get(23)).intValue());
           oCantidad.setCant25(((Double)vRowTemp.get(24)).intValue());
           oCantidad.setCant26(((Double)vRowTemp.get(25)).intValue());
           oCantidad.setCant27(((Double)vRowTemp.get(26)).intValue());
           oCantidad.setCant28(((Double)vRowTemp.get(27)).intValue());
           oCantidad.setCant29(((Double)vRowTemp.get(28)).intValue());
           oCantidad.setCant30(((Double)vRowTemp.get(29)).intValue());
           oCantidad.setCant31(((Double)vRowTemp.get(30)).intValue());
           oCantidad.setCant32(((Double)vRowTemp.get(31)).intValue());
           oCantidad.setCant33(((Double)vRowTemp.get(32)).intValue());
           oCantidad.setCant34(((Double)vRowTemp.get(33)).intValue());
           oCantidad.setCant35(((Double)vRowTemp.get(34)).intValue());
           oCantidad.setCant36(((Double)vRowTemp.get(35)).intValue());
           oCantidad.setCant37(((Double)vRowTemp.get(36)).intValue());
           oCantidad.setCant38(((Double)vRowTemp.get(37)).intValue());
           oCantidad.setCant39(((Double)vRowTemp.get(38)).intValue());
           oCantidad.setCant40(((Double)vRowTemp.get(39)).intValue());
           oCantidad.setCant41(((Double)vRowTemp.get(40)).intValue());
           oCantidad.setCant42(((Double)vRowTemp.get(41)).intValue());
           oCantidad.setCant43(((Double)vRowTemp.get(42)).intValue());
           oCantidad.setCant44(((Double)vRowTemp.get(43)).intValue());
           oCantidad.setCant45(((Double)vRowTemp.get(44)).intValue());
           oCantidad.setCant46(((Double)vRowTemp.get(45)).intValue());
           oCantidad.setCant47(((Double)vRowTemp.get(46)).intValue());
           oCantidad.setCant48(((Double)vRowTemp.get(47)).intValue());
       }
       return oCantidad;
    }
    
    public ReporteHojaEgresoHemoderivados[] buscarCantidadesSurtidas(Date dFecIni, Date dFecFin) throws Exception{
        ReporteHojaEgresoHemoderivados arrRet[]=null, oReporte=null;
        ArrayList<ReporteHojaEgresoHemoderivados> vObj = null;
        ArrayList rst= null;
        String sQuery="";
        int i=0, nTam=0;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        if(dFecIni == null && dFecFin == null){
            throw new Exception("ReporteHojaEgresoHemoderivados.buscarCantidadesSurtidas: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscarReporteProductoHemoPorPac('"+ oFec.format(dFecIni) +"','"+ oFec.format(dFecFin) +"');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ReporteHojaEgresoHemoderivados>();
                for(i=0;i<rst.size();i++){
                    oReporte = new ReporteHojaEgresoHemoderivados();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oReporte.oEpisodio = new EpisodioMedico();
                    oReporte.oDetalle = new DetalleSolicitudHemoderivado();
                    oReporte.oDetalle.setServicioCobrable(new ProductoHemoderivado());
                    oReporte.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oReporte.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oReporte.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oReporte.oEpisodio.getArea().setDescripcion((String)vRowTemp.get(3));
                    ((ProductoHemoderivado)oReporte.oDetalle.getServicioCobrable()).setDescripcion((String)vRowTemp.get(4));
                    oReporte.oDetalle.setCantSurtida(((Double)vRowTemp.get(5)).shortValue());
                    oReporte.oDetalle.setFechaSurtidoRealizacion((Date)vRowTemp.get(6));
                    vObj.add(oReporte);
                }
                nTam = vObj.size();
                arrRet = new ReporteHojaEgresoHemoderivados[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }
    
    public ReporteHojaEgresoHemoderivados[] buscarTodasSolAmbulatorias(Date dFecIni, Date dFecFin) throws Exception{
        ReporteHojaEgresoHemoderivados arrRet[]=null, oReporte = null;
        ArrayList<ReporteHojaEgresoHemoderivados> vObj = null;
        ArrayList rst= null;
        String sQuery="";
        int i=0, nTam=0;
        SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
        if(dFecIni == null && dFecFin == null){
            throw new Exception("ReporteHojaEgresoHemoderivados.buscarTodasSolAmbulatorias: error, faltan datos");
        }else{
            sQuery = "SELECT * FROM buscarReporteSolAmbulatorias('"+ oFec.format(dFecIni) +"','"+ oFec.format(dFecFin) +"')";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null){
                vObj = new ArrayList<ReporteHojaEgresoHemoderivados>();
                for(i=0;i<rst.size();i++){
                    oReporte = new ReporteHojaEgresoHemoderivados();
                    ArrayList<Object> vRowTemp=(ArrayList)rst.get(i);
                    oReporte.oEpisodio = new EpisodioMedico();
                    oReporte.oEpisodio.setExpediente(new Expediente());
                    oReporte.oSolicitud = new SolicitudSangre();
                    oReporte.oSolicitud.setTipoSangre(new Parametrizacion());
                    oReporte.oSolicitud.setRH(new Parametrizacion());
                    oReporte.oEpisodio.getPaciente().setSeguro(new Seguro());
                    oReporte.oDetalle = new DetalleSolicitudHemoderivado();
                    oReporte.oEpisodio.getPaciente().setNombres((String)vRowTemp.get(0));
                    oReporte.oEpisodio.getPaciente().setApPaterno((String)vRowTemp.get(1));
                    oReporte.oEpisodio.getPaciente().setApMaterno((String)vRowTemp.get(2));
                    oReporte.oEpisodio.getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                    oReporte.oEpisodio.getPaciente().getSeguro().setNumero(((String)vRowTemp.get(4)));
                    oReporte.oSolicitud.getTipoSangre().setValor((String)vRowTemp.get(5));
                    oReporte.oSolicitud.getRH().setValor((String)vRowTemp.get(6));
                    ((ProductoHemoderivado)oReporte.oDetalle.getServicioCobrable()).setDescripcion((String)vRowTemp.get(7));
                    oReporte.setFolio(((Double)vRowTemp.get(8)).intValue());
                    oReporte.oDetalle.setFechaSolicitud((Date)vRowTemp.get(9));
                    vObj.add(oReporte);
                }
                nTam = vObj.size();
                arrRet = new ReporteHojaEgresoHemoderivados[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
        }
        return arrRet;
    }

    public AccesoDatos getAD() {
        return oAD;
    }

    public void setAD(AccesoDatos oAD) {
        this.oAD = oAD;
    }

    public String getUsuarioFirmado() {
        return sUsuarioFirmado;
    }

    public void setUsuarioFirmado(String sUsuarioFirmado) {
        this.sUsuarioFirmado = sUsuarioFirmado;
    }

    public SolicitudSangre getSolicitud() {
        return oSolicitud;
    }

    public void setSolicitud(SolicitudSangre oSolicitud) {
        this.oSolicitud = oSolicitud;
    }

    public DetalleSolicitudHemoderivado getDetalle() {
        return oDetalle;
    }

    public void setDetalle(DetalleSolicitudHemoderivado oDetalle) {
        this.oDetalle = oDetalle;
    }

    public int getCant1() {
        return nCant1;
    }

    public void setCant1(int nCant1) {
        this.nCant1 = nCant1;
    }

    public int getCant2() {
        return nCant2;
    }

    public void setCant2(int nCant2) {
        this.nCant2 = nCant2;
    }

    public int getCant3() {
        return nCant3;
    }

    public void setCant3(int nCant3) {
        this.nCant3 = nCant3;
    }

    public int getCant4() {
        return nCant4;
    }

    public void setCant4(int nCant4) {
        this.nCant4 = nCant4;
    }

    public int getCant5() {
        return nCant5;
    }

    public void setCant5(int nCant5) {
        this.nCant5 = nCant5;
    }

    public int getCant6() {
        return nCant6;
    }

    public void setCant6(int nCant6) {
        this.nCant6 = nCant6;
    }

    public int getCant7() {
        return nCant7;
    }

    public void setCant7(int nCant7) {
        this.nCant7 = nCant7;
    }

    public int getCant8() {
        return nCant8;
    }

    public void setCant8(int nCant8) {
        this.nCant8 = nCant8;
    }

    public int getCant9() {
        return nCant9;
    }

    public void setCant9(int nCant9) {
        this.nCant9 = nCant9;
    }

    public int getCant10() {
        return nCant10;
    }

    public void setCant10(int nCant10) {
        this.nCant10 = nCant10;
    }

    public int getCant11() {
        return nCant11;
    }

    public void setCant11(int nCant11) {
        this.nCant11 = nCant11;
    }

    public int getCant12() {
        return nCant12;
    }

    public void setCant12(int nCant12) {
        this.nCant12 = nCant12;
    }

    public int getCant13() {
        return nCant13;
    }

    public void setCant13(int nCant13) {
        this.nCant13 = nCant13;
    }

    public int getCant14() {
        return nCant14;
    }

    public void setCant14(int nCant14) {
        this.nCant14 = nCant14;
    }

    public int getCant15() {
        return nCant15;
    }

    public void setCant15(int nCant15) {
        this.nCant15 = nCant15;
    }

    public int getCant16() {
        return nCant16;
    }

    public void setCant16(int nCant16) {
        this.nCant16 = nCant16;
    }

    public int getCant17() {
        return nCant17;
    }

    public void setCant17(int nCant17) {
        this.nCant17 = nCant17;
    }

    public int getCant18() {
        return nCant18;
    }

    public void setCant18(int nCant18) {
        this.nCant18 = nCant18;
    }

    public int getCant19() {
        return nCant19;
    }

    public void setCant19(int nCant19) {
        this.nCant19 = nCant19;
    }

    public int getCant20() {
        return nCant20;
    }

    public void setCant20(int nCant20) {
        this.nCant20 = nCant20;
    }

    public int getCant21() {
        return nCant21;
    }

    public void setCant21(int nCant21) {
        this.nCant21 = nCant21;
    }

    public int getCant22() {
        return nCant22;
    }

    public void setCant22(int nCant22) {
        this.nCant22 = nCant22;
    }

    public int getCant23() {
        return nCant23;
    }

    public void setCant23(int nCant23) {
        this.nCant23 = nCant23;
    }

    public int getCant24() {
        return nCant24;
    }

    public void setCant24(int nCant24) {
        this.nCant24 = nCant24;
    }

    public int getCant25() {
        return nCant25;
    }

    public void setCant25(int nCant25) {
        this.nCant25 = nCant25;
    }

    public int getCant26() {
        return nCant26;
    }

    public void setCant26(int nCant26) {
        this.nCant26 = nCant26;
    }

    public int getCant27() {
        return nCant27;
    }

    public void setCant27(int nCant27) {
        this.nCant27 = nCant27;
    }

    public int getCant28() {
        return nCant28;
    }

    public void setCant28(int nCant28) {
        this.nCant28 = nCant28;
    }

    public int getCant29() {
        return nCant29;
    }

    public void setCant29(int nCant29) {
        this.nCant29 = nCant29;
    }

    public int getCant30() {
        return nCant30;
    }

    public void setCant30(int nCant30) {
        this.nCant30 = nCant30;
    }

    public int getCant31() {
        return nCant31;
    }

    public void setCant31(int nCant31) {
        this.nCant31 = nCant31;
    }

    public int getCant32() {
        return nCant32;
    }

    public void setCant32(int nCant32) {
        this.nCant32 = nCant32;
    }

    public int getCant33() {
        return nCant33;
    }

    public void setCant33(int nCant33) {
        this.nCant33 = nCant33;
    }

    public int getCant34() {
        return nCant34;
    }

    public void setCant34(int nCant34) {
        this.nCant34 = nCant34;
    }

    public int getCant35() {
        return nCant35;
    }

    public void setCant35(int nCant35) {
        this.nCant35 = nCant35;
    }

    public int getCant36() {
        return nCant36;
    }

    public void setCant36(int nCant36) {
        this.nCant36 = nCant36;
    }

    public int getCant37() {
        return nCant37;
    }

    public void setCant37(int nCant37) {
        this.nCant37 = nCant37;
    }

    public int getCant38() {
        return nCant38;
    }

    public void setCant38(int nCant38) {
        this.nCant38 = nCant38;
    }

    public int getCant39() {
        return nCant39;
    }

    public void setCant39(int nCant39) {
        this.nCant39 = nCant39;
    }

    public int getCant40() {
        return nCant40;
    }

    public void setCant40(int nCant40) {
        this.nCant40 = nCant40;
    }

    public int getCant41() {
        return nCant41;
    }

    public void setCant41(int nCant41) {
        this.nCant41 = nCant41;
    }

    public int getCant42() {
        return nCant42;
    }

    public void setCant42(int nCant42) {
        this.nCant42 = nCant42;
    }

    public int getCant43() {
        return nCant43;
    }

    public void setCant43(int nCant43) {
        this.nCant43 = nCant43;
    }

    public int getCant44() {
        return nCant44;
    }

    public void setCant44(int nCant44) {
        this.nCant44 = nCant44;
    }

    public int getCant45() {
        return nCant45;
    }

    public void setCant45(int nCant45) {
        this.nCant45 = nCant45;
    }

    public int getCant46() {
        return nCant46;
    }

    public void setCant46(int nCant46) {
        this.nCant46 = nCant46;
    }

    public int getCant47() {
        return nCant47;
    }

    public void setCant47(int nCant47) {
        this.nCant47 = nCant47;
    }

    public int getCant48() {
        return nCant48;
    }

    public void setCant48(int nCant48) {
        this.nCant48 = nCant48;
    }

    public int getCE() {
        return nCE;
    }

    public void setCE(int nCE) {
        this.nCE = nCE;
    }

    public int getPFC() {
        return nPFC;
    }

    public void setPFC(int nPFC) {
        this.nPFC = nPFC;
    }

    public int getCP() {
        return nCP;
    }

    public void setCP(int nCP) {
        this.nCP = nCP;
    }

    public int getCCP() {
        return nCCP;
    }

    public void setCCP(int nCCP) {
        this.nCCP = nCCP;
    }

    public int getOtros() {
        return nOtros;
    }

    public void setOtros(int nOtros) {
        this.nOtros = nOtros;
    }

    public int getPlasma() {
        return nPlasma;
    }

    public void setPlasma(int nPlasma) {
        this.nPlasma = nPlasma;
    }

    public int getST() {
        return nST;
    }

    public void setST(int nST) {
        this.nST = nST;
    }

    public int getPRP() {
        return nPRP;
    }

    public void setPRP(int nPRP) {
        this.nPRP = nPRP;
    }

    public EpisodioMedico getEpisodio() {
        return oEpisodio;
    }

    public void setEpisodio(EpisodioMedico oEpisodio) {
        this.oEpisodio = oEpisodio;
    }

    public int getFolio() {
        return nFolio;
    }

    public void setFolio(int nFolio) {
        this.nFolio = nFolio;
    }
    
    
    
}

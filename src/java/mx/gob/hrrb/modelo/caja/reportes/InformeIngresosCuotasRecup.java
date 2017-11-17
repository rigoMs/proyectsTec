package mx.gob.hrrb.modelo.caja.reportes;

import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.caja.Caja;
import mx.gob.hrrb.modelo.caja.ComprobanteExencion;
import mx.gob.hrrb.modelo.caja.CorteCaja;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion;
import mx.gob.hrrb.modelo.caja.FoliosCaja;
import mx.gob.hrrb.modelo.caja.ReciboSeguroPopular;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Daniel
 */
public class InformeIngresosCuotasRecup {
    private AccesoDatos oAD;
    private CorteCaja oCorteCaja;
    private BigDecimal nTotalGlobalServ;
    private BigDecimal nSubsidio;
    private BigDecimal nExencion;
    private BigDecimal nExencionProg;
    private BigDecimal nTotalNetoServ;
    private BigDecimal nCxCRecuperadas;
    private BigDecimal nCxC;
    private BigDecimal nReintegro;
    private BigDecimal nTotalRecuperado;
    private CuotaRecuperacion arrRecCanceladosCuotas[];
    private ComprobanteExencion arrRecCanceladosExencion[];
    private FoliosCaja arrRecCuotaUtilizados[];
    private FoliosCaja arrComprobanteExUtilizados[];
    private FoliosCaja arrRecCuotaExistentes[];
    private FoliosCaja arrComprobanteExExistentes[];
    private PersonalHospitalario oCajero;
    private PersonalHospitalario oJefeCajaGral;      
    
    public void buscar() throws Exception{
        ArrayList rst = null;
        String sQuery = "";
        CuotaRecuperacion oCR;
        ComprobanteExencion oCE;
        ReciboSeguroPopular oRSP;
        FoliosCaja oFoliosCE,oFoliosCR;
        if(this.getCorteCaja().getFechaCorte()==null||this.getCorteCaja().getCaja().getIdCaja()==0){
            throw new Exception("InformeIngresosCuotasRecup.buscar: error, faltan datos");
	}
        else{
            SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
            sQuery = "SELECT * FROM buscaInformeIngresosCuotasRecup('"+isNull(format.format(this.getCorteCaja().getFechaCorte()))+"'::date,"+this.getCorteCaja().getCaja().getIdCaja()+"::smallint,'"+isNull(this.getCorteCaja().getTurno().getClave())+"'::character(3));"; 
            System.out.println(sQuery);
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null && rst.size()>0) {
                ArrayList vRowTemp = (ArrayList)rst.get(0);
                this.setTotalGlobalServ((BigDecimal.valueOf((Double)vRowTemp.get(0))));
                this.setSubsidio((BigDecimal.valueOf((Double)vRowTemp.get(1))));
                this.setExencion((BigDecimal.valueOf((Double)vRowTemp.get(2))));
                this.setExencionProg((BigDecimal.valueOf((Double)vRowTemp.get(3))));
                this.setTotalNetoServ((BigDecimal.valueOf((Double)vRowTemp.get(4))));
                this.setCxCRecuperadas((BigDecimal.valueOf((Double)vRowTemp.get(5))));
                this.setCxC((BigDecimal.valueOf((Double)vRowTemp.get(6))));
                this.setReintegro((BigDecimal.valueOf((Double)vRowTemp.get(7))));
                this.setTotalRecuperado((BigDecimal.valueOf((Double)vRowTemp.get(8))));
            }
        }
        oCR=new CuotaRecuperacion();
        oCE=new ComprobanteExencion();
        oRSP=new ReciboSeguroPopular();
        oCR.setCajaCancela(this.getCorteCaja().getCaja());
        oCE.setCajaCancela(this.getCorteCaja().getCaja());
        arrRecCanceladosCuotas=(new CuotaRecuperacion()).buscarRecibosCanceladosTurno(this.getCorteCaja().getFechaCorte(), this.getCorteCaja().getTurno().getClave());
        arrRecCanceladosExencion=(new ComprobanteExencion()).buscarRecibosCanceladosTurno(this.getCorteCaja().getFechaCorte(), this.getCorteCaja().getTurno().getClave());
        oFoliosCE=new FoliosCaja();
        oFoliosCR=new FoliosCaja();
        oFoliosCE.setCaja(this.getCorteCaja().getCaja());
        oFoliosCR.setCaja(this.getCorteCaja().getCaja());
        oFoliosCE.setTipoRecibo(new Parametrizacion());
        oFoliosCR.setTipoRecibo(new Parametrizacion());
        oFoliosCE.getTipoRecibo().setTipoParametro(Parametrizacion.RECIBO_EXENTO);
        oFoliosCR.getTipoRecibo().setTipoParametro(Parametrizacion.RECIBO_CUOTAS);
        arrRecCuotaUtilizados=oFoliosCR.buscarFoliosUtilizadosDuranteTurno(this.getCorteCaja().getFechaCorte(), this.getCorteCaja().getTurno().getClave());
        arrComprobanteExUtilizados=oFoliosCE.buscarFoliosUtilizadosDuranteTurno(this.getCorteCaja().getFechaCorte(), this.getCorteCaja().getTurno().getClave());
        arrRecCuotaExistentes=oFoliosCR.buscarFoliosExistentesDespuesTurno(this.getCorteCaja().getFechaCorte(), this.getCorteCaja().getTurno().getClave());
        arrComprobanteExExistentes=oFoliosCE.buscarFoliosExistentesDespuesTurno(this.getCorteCaja().getFechaCorte(), this.getCorteCaja().getTurno().getClave());
        oCajero=new PersonalHospitalario();
        oJefeCajaGral=new PersonalHospitalario();
        
    }
    
    public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }

    public BigDecimal getTotalGlobalServ() {
        return nTotalGlobalServ;
    }

    public void setTotalGlobalServ(BigDecimal nTotalGlobalServ) {
        this.nTotalGlobalServ = nTotalGlobalServ;
    }

    public BigDecimal getSubsidio() {
        return nSubsidio;
    }

    public void setSubsidio(BigDecimal nSubsidio) {
        this.nSubsidio = nSubsidio;
    }

    public BigDecimal getExencion() {
        return nExencion;
    }

    public void setExencion(BigDecimal nExencion) {
        this.nExencion = nExencion;
    }

    public BigDecimal getExencionProg() {
        return nExencionProg;
    }

    public void setExencionProg(BigDecimal nExencionProg) {
        this.nExencionProg = nExencionProg;
    }

    public BigDecimal getTotalNetoServ() {
        return nTotalNetoServ;
    }

    public void setTotalNetoServ(BigDecimal nTotalNetoServ) {
        this.nTotalNetoServ = nTotalNetoServ;
    }

    public BigDecimal getCxCRecuperadas() {
        return nCxCRecuperadas;
    }

    public void setCxCRecuperadas(BigDecimal nCxCRecuperadas) {
        this.nCxCRecuperadas = nCxCRecuperadas;
    }

    public BigDecimal getCxC() {
        return nCxC;
    }

    public void setCxC(BigDecimal nCxC) {
        this.nCxC = nCxC;
    }

    public BigDecimal getReintegro() {
        return nReintegro;
    }

    public void setReintegro(BigDecimal nReintegro) {
        this.nReintegro = nReintegro;
    }

    public BigDecimal getTotalRecuperado() {
        return nTotalRecuperado;
    }

    public void setTotalRecuperado(BigDecimal nTotalRecuperado) {
        this.nTotalRecuperado = nTotalRecuperado;
    }

    public String getRecCanceladosCuotas() {
        String sRecibos="";
        if(arrRecCanceladosCuotas.length==0){
            sRecibos="NINGUNO";
        }
        else{
            if(arrRecCanceladosCuotas.length==1){
                sRecibos=arrRecCanceladosCuotas[0]+"";
            }
            else{
                for(int i=0;i<arrRecCanceladosCuotas.length;i++){
                    sRecibos=sRecibos+arrRecCanceladosCuotas;
                }
            }
        }
        return sRecibos;
    }


    public String getRecCanceladosExencion() {
        String sRecibos="";
        if(arrRecCanceladosExencion.length==0){
            sRecibos="NINGUNO";
        }
        else{
            if(arrRecCanceladosExencion.length==1){
                sRecibos=arrRecCanceladosExencion[0]+"";
            }
            else{
                for(int i=0;i<arrRecCanceladosExencion.length;i++){
                    sRecibos=sRecibos+arrRecCanceladosExencion;
                }
            }
        }
        return sRecibos;
    }

    public CorteCaja getCorteCaja() {
        return oCorteCaja;
    }

    public void setCorteCaja(CorteCaja oCorteCaja) {
        this.oCorteCaja = oCorteCaja;
    }

    public FoliosCaja[] getRecCuotaUtilizados() {
        return arrRecCuotaUtilizados;
    }

    public void setRecCuotaUtilizados(FoliosCaja[] arrRecCuotaUtilizados) {
        this.arrRecCuotaUtilizados = arrRecCuotaUtilizados;
    }

    public FoliosCaja[] getComprobanteExUtilizados() {
        return arrComprobanteExUtilizados;
    }

    public void setComprobanteExUtilizados(FoliosCaja[] arrComprobanteExUtilizados) {
        this.arrComprobanteExUtilizados = arrComprobanteExUtilizados;
    }

    public FoliosCaja[] getRecCuotaExistentes() {
        return arrRecCuotaExistentes;
    }

    public void setRecCuotaExistentes(FoliosCaja[] arrRecCuotaExistentes) {
        this.arrRecCuotaExistentes = arrRecCuotaExistentes;
    }

    public FoliosCaja[] getComprobanteExExistentes() {
        return arrComprobanteExExistentes;
    }

    public void setComprobanteExExistentes(FoliosCaja[] arrComprobanteExExistentes) {
        this.arrComprobanteExExistentes = arrComprobanteExExistentes;
    }

    public PersonalHospitalario getCajero() {
        return oCajero;
    }

    public void setCajero(PersonalHospitalario oCajero) {
        this.oCajero = oCajero;
    }

    public PersonalHospitalario getJefeCajaGral() {
        return oJefeCajaGral;
    }

    public void setJefeCajaGral(PersonalHospitalario oJefeCajaGral) {
        this.oJefeCajaGral = oJefeCajaGral;
    }

}
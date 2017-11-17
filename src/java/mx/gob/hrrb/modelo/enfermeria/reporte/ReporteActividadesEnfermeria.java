package mx.gob.hrrb.modelo.enfermeria.reporte;

import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Parametrizacion;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ReporteActividadesEnfermeria extends DetalleDiaMes implements Serializable{
	
        private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dFechaInicio;
        private Date dFechaFin;
	private int nTotal;	
	private String sActividad;
        private AreaServicioHRRB oServicio;
        private Parametrizacion oTipoActividad;        
        private ArrayList<ReporteActividadesEnfermeria> arrActividadMensual;
        private String sFecha1;
        private String sFecha2;
        
        private String sFecha3;
        private String sFecha4;
        private SimpleDateFormat df;
	public ReporteActividadesEnfermeria(){
            oServicio= new AreaServicioHRRB();
            oTipoActividad= new Parametrizacion();
            dFechaInicio= new Date();
            dFechaFin = new Date();
            df= new SimpleDateFormat("yyyy-MM-dd");
            arrActividadMensual= new ArrayList<ReporteActividadesEnfermeria>();
            
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
     
        public ArrayList<ReporteActividadesEnfermeria> buscaReporteMensualActividadesEnfermeria() throws Exception{
            ArrayList<ReporteActividadesEnfermeria> arrRet=null;
            ReporteActividadesEnfermeria oTAZ, oTBA, oTBB, oA;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            if(this.getServicio().getClave()==0
                    || this.getFechaInicio()==null || this.getFechaFin()==null){
                throw new Exception("ReporteActividadesEnfermeria.buscaReporteMensualActividadesEnfermeria:error,faltan datos");
            }else{
                sQuery="SELECT * FROM buscaActividadEnfermeriaGeneraReporteMensual('"
                        +this.getFechaInicioStr()+"'::date,'"
                        +this.getFechaFinStr()+"'::date,"
                        +this.getServicio().getClave() +"::smallint,8,29,5);";
                oAD= new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst!=null && rst.size()>0){
                    arrRet= new ArrayList<ReporteActividadesEnfermeria>();
                    oTAZ = new ReporteActividadesEnfermeria();
                    oTBA = new ReporteActividadesEnfermeria();
                    oTBB = new ReporteActividadesEnfermeria();
                    oTAZ.setActividad("ADMINISTRATIVAS");
                    oTBA.setActividad("TÉCNICAS Y PROCEDIMIENTOS");
                    oTBB.setActividad("ACTIVIDADES DOCENTES");
                    for(i=0; i<rst.size();i++){
                        ArrayList vRT= (ArrayList)rst.get(i);
                        oA= new ReporteActividadesEnfermeria();
                        oA.getTipActividad().setTipoParametro((String)vRT.get(0));
                        oA.getTipActividad().setClaveParametro((String)vRT.get(1));
                        oA.getTipActividad().setValor((String)vRT.get(2));
                        oA.setDia26(((Double)vRT.get(3)).intValue()==0?"":String.valueOf(((Double)vRT.get(3))));
                        oA.setDia27((((Double)vRT.get(4)).intValue())==0?"":String.valueOf(((Double)vRT.get(4)).intValue()));
                        oA.setDia28((((Double)vRT.get(5)).intValue())==0?"":String.valueOf(((Double)vRT.get(5)).intValue()));
                        oA.setDia29((((Double)vRT.get(6)).intValue())==0?"":String.valueOf(((Double)vRT.get(6)).intValue()));
                        oA.setDia30((((Double)vRT.get(7)).intValue())==0?"":String.valueOf(((Double)vRT.get(7)).intValue()));
                        oA.setDia31((((Double)vRT.get(8)).intValue())==0?"":String.valueOf(((Double)vRT.get(8)).intValue()));
                        oA.setDia1((((Double)vRT.get(9)).intValue())==0?"":String.valueOf(((Double)vRT.get(9)).intValue()));
                        oA.setDia2((((Double)vRT.get(10)).intValue())==0?"":String.valueOf(((Double)vRT.get(10)).intValue()));
                        oA.setDia3((((Double)vRT.get(11)).intValue())==0?"":String.valueOf(((Double)vRT.get(11)).intValue()));
                        oA.setDia4((((Double)vRT.get(12)).intValue())==0?"":String.valueOf(((Double)vRT.get(12)).intValue()));
                        oA.setDia5((((Double)vRT.get(13)).intValue())==0?"":String.valueOf(((Double)vRT.get(13)).intValue()));
                        oA.setDia6((((Double)vRT.get(14)).intValue())==0?"":String.valueOf(((Double)vRT.get(14)).intValue()));
                        oA.setDia7((((Double)vRT.get(15)).intValue())==0?"":String.valueOf(((Double)vRT.get(15)).intValue()));
                        oA.setDia8((((Double)vRT.get(16)).intValue())==0?"":String.valueOf(((Double)vRT.get(16)).intValue()));
                        oA.setDia9((((Double)vRT.get(17)).intValue())==0?"":String.valueOf(((Double)vRT.get(17)).intValue()));
                        oA.setDia10((((Double)vRT.get(18)).intValue())==0?"":String.valueOf(((Double)vRT.get(18)).intValue()));
                        oA.setDia11((((Double)vRT.get(19)).intValue())==0?"":String.valueOf(((Double)vRT.get(19)).intValue()));
                        oA.setDia12((((Double)vRT.get(20)).intValue())==0?"":String.valueOf(((Double)vRT.get(20)).intValue()));
                        oA.setDia13((((Double)vRT.get(21)).intValue())==0?"":String.valueOf(((Double)vRT.get(21)).intValue()));
                        oA.setDia14((((Double)vRT.get(22)).intValue())==0?"":String.valueOf(((Double)vRT.get(22)).intValue()));
                        oA.setDia15((((Double)vRT.get(23)).intValue())==0?"":String.valueOf(((Double)vRT.get(23)).intValue()));
                        oA.setDia16((((Double)vRT.get(24)).intValue())==0?"":String.valueOf(((Double)vRT.get(24)).intValue()));
                        oA.setDia17((((Double)vRT.get(25)).intValue())==0?"":String.valueOf(((Double)vRT.get(25)).intValue()));
                        oA.setDia18((((Double)vRT.get(26)).intValue())==0?"":String.valueOf(((Double)vRT.get(26)).intValue()));
                        oA.setDia19((((Double)vRT.get(27)).intValue())==0?"":String.valueOf(((Double)vRT.get(27)).intValue()));
                        oA.setDia20((((Double)vRT.get(28)).intValue())==0?"":String.valueOf(((Double)vRT.get(28)).intValue()));
                        oA.setDia21((((Double)vRT.get(29)).intValue())==0?"":String.valueOf(((Double)vRT.get(29)).intValue()));
                        oA.setDia22((((Double)vRT.get(30)).intValue())==0?"":String.valueOf(((Double)vRT.get(30)).intValue()));
                        oA.setDia23((((Double)vRT.get(31)).intValue())==0?"":String.valueOf(((Double)vRT.get(31)).intValue()));
                        oA.setDia24((((Double)vRT.get(32)).intValue())==0?"":String.valueOf(((Double)vRT.get(32)).intValue()));
                        oA.setDia25((((Double)vRT.get(33)).intValue())==0?"":String.valueOf(((Double)vRT.get(33)).intValue()));
                        oA.setTotal(((Double)vRT.get(34)).intValue());
                        if(oA.getTipActividad().getTipoParametro().equals("TAZ")){
                            oTAZ.getArrActividadMensual().add(oA);
                        }else if(oA.getTipActividad().getTipoParametro().equals("TBA")){
                            oTBA.getArrActividadMensual().add(oA);
                        }else if(oA.getTipActividad().getTipoParametro().equals("TBB")){
                            oTBB.getArrActividadMensual().add(oA);
                        }
                        
                    }                   
                    arrRet.add(oTAZ);
                    arrRet.add(oTBA);
                    arrRet.add(oTBB);
                }
            }
            return arrRet;
        }
        
	public Date getFechaInicio() {
            return dFechaInicio;
	}

	public void setFechaInicio(Date valor) {
            this.dFechaInicio=valor;
	}
       
        public Date getFechaFin(){
            return dFechaFin;
        }
        
        public void setFechaFin(Date valor){
            this.dFechaFin=valor;
        }

	public int getTotal() {
            return nTotal;
	}

	public void setTotal(int valor) {
            this.nTotal=valor;
	}

	public AreaServicioHRRB getServicio() {
            return oServicio;
	}

	public void setServicio(AreaServicioHRRB valor) {
            this.oServicio=valor;
	}

	public String getActividad() {
            return sActividad;
	}

	public void setActividad(String valor) {
            this.sActividad=valor;
	}
        
        public String getFechaInicioStr(){
            return sFecha1;
        }
        
        public void setFechaInicioStr(String v){
            this.sFecha1=v;
        }
        
        public String getFechaFinStr(){
            return sFecha2;
        }
        
        public void setFechaFinStr(String vv){
            this.sFecha2=vv;
        }
        
        public String getFechaInicioStr2(){
            return sFecha3;
        }
        
        public void setFechaInicioStr2(String v){
            this.sFecha3=v;
        }
        
        public String getFechaFinStr2(){
            return sFecha4;
        }
        
        public void setFechaFinStr2(String vv){
            this.sFecha4=vv;
        }

    public Parametrizacion getTipActividad() {
        return oTipoActividad;
    }
    
    public void setTipActividad(Parametrizacion oTipoActividad) {
        this.oTipoActividad = oTipoActividad;
    }
    
    public ArrayList<ReporteActividadesEnfermeria> getArrActividadMensual() {
        return arrActividadMensual;
    }
    
    public void setArrActividadMensual(ArrayList<ReporteActividadesEnfermeria> arrActividadMensual) {
        this.arrActividadMensual = arrActividadMensual;
    }

} 

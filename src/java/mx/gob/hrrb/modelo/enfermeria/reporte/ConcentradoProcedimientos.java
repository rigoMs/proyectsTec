package mx.gob.hrrb.modelo.enfermeria.reporte;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Parametrizacion;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class ConcentradoProcedimientos extends DetalleDiaMes implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
        private String sFechaInicio;
        private String sFechaFin;
        private String sFechaInicio2;
        private String sFechaFin2;
        private int nTotal;
        private Parametrizacion oProcEnf;
        private AreaServicioHRRB oServicio;        
        
        
	public ConcentradoProcedimientos(){
            oProcEnf= new Parametrizacion();
            oServicio= new AreaServicioHRRB();
            HttpServletRequest req;
            req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
            if (req.getSession().getAttribute("oFirm") != null) {
                    sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
            }
	}
	
	public ConcentradoProcedimientos[] buscarConcentradoProcediminetos() throws Exception{
            ConcentradoProcedimientos arrRet[]=null, oC;
            ArrayList rst = null;
            String sQuery = "";
            int i=0;
            if(this.getServicio().getClave()==0 || this.getFechaInicio()==null || this.getFechaInicio().equals("")){
                throw new Exception("ConcentradoProcedimientos.buscarConcentradoProcediminetos: error, faltan datos");
            }else{
                if(this.getServicio().getClave()==72 || this.getServicio().getClave()==79 || this.getServicio().getClave()==73){
                    sQuery="SELECT * FROM buscaConcentradoMensualProcedimientoEnf('"
                            +this.getFechaInicio()+"'::date,'"+this.getFechaFin()+"'::date,"+this.getServicio().getClave()+"::smallint,2);";
                }else{
                   sQuery="SELECT * FROM buscaConcentradoMensualProcedimientoEnf('"
                            +this.getFechaInicio()+"'::date,'"+this.getFechaFin()+"'::date,"+this.getServicio().getClave()+"::smallint,1);";
                }               
                oAD=new AccesoDatos(); 
                if (oAD.conectar()){ 
                        rst = oAD.ejecutarConsulta(sQuery); 
                        oAD.desconectar(); 
                }
                if (rst != null && rst.size()>0) {
                        arrRet = new ConcentradoProcedimientos[rst.size()];
                        for (i = 0; i < rst.size(); i++) {
                            ArrayList vRT= (ArrayList)rst.get(i);
                            oC= new ConcentradoProcedimientos(); 
                            oC.getTipoProcedimineto().setValor((String)vRT.get(3));
                            oC.setDia26(((Double)vRT.get(4)).intValue()==0?"":String.valueOf(((Double)vRT.get(4))));
                            oC.setDia27((((Double)vRT.get(5)).intValue())==0?"":String.valueOf(((Double)vRT.get(5)).intValue()));
                            oC.setDia28((((Double)vRT.get(6)).intValue())==0?"":String.valueOf(((Double)vRT.get(6)).intValue()));
                            oC.setDia29((((Double)vRT.get(7)).intValue())==0?"":String.valueOf(((Double)vRT.get(7)).intValue()));
                            oC.setDia30((((Double)vRT.get(8)).intValue())==0?"":String.valueOf(((Double)vRT.get(8)).intValue()));
                            oC.setDia31((((Double)vRT.get(9)).intValue())==0?"":String.valueOf(((Double)vRT.get(9)).intValue()));
                            oC.setDia1((((Double)vRT.get(10)).intValue())==0?"":String.valueOf(((Double)vRT.get(10)).intValue()));
                            oC.setDia2((((Double)vRT.get(11)).intValue())==0?"":String.valueOf(((Double)vRT.get(11)).intValue()));
                            oC.setDia3((((Double)vRT.get(12)).intValue())==0?"":String.valueOf(((Double)vRT.get(12)).intValue()));
                            oC.setDia4((((Double)vRT.get(13)).intValue())==0?"":String.valueOf(((Double)vRT.get(13)).intValue()));
                            oC.setDia5((((Double)vRT.get(14)).intValue())==0?"":String.valueOf(((Double)vRT.get(14)).intValue()));
                            oC.setDia6((((Double)vRT.get(15)).intValue())==0?"":String.valueOf(((Double)vRT.get(15)).intValue()));
                            oC.setDia7((((Double)vRT.get(16)).intValue())==0?"":String.valueOf(((Double)vRT.get(16)).intValue()));
                            oC.setDia8((((Double)vRT.get(17)).intValue())==0?"":String.valueOf(((Double)vRT.get(17)).intValue()));
                            oC.setDia9((((Double)vRT.get(18)).intValue())==0?"":String.valueOf(((Double)vRT.get(18)).intValue()));
                            oC.setDia10((((Double)vRT.get(19)).intValue())==0?"":String.valueOf(((Double)vRT.get(19)).intValue()));
                            oC.setDia11((((Double)vRT.get(20)).intValue())==0?"":String.valueOf(((Double)vRT.get(20)).intValue()));
                            oC.setDia12((((Double)vRT.get(21)).intValue())==0?"":String.valueOf(((Double)vRT.get(21)).intValue()));
                            oC.setDia13((((Double)vRT.get(22)).intValue())==0?"":String.valueOf(((Double)vRT.get(22)).intValue()));
                            oC.setDia14((((Double)vRT.get(23)).intValue())==0?"":String.valueOf(((Double)vRT.get(23)).intValue()));
                            oC.setDia15((((Double)vRT.get(24)).intValue())==0?"":String.valueOf(((Double)vRT.get(24)).intValue()));
                            oC.setDia16((((Double)vRT.get(25)).intValue())==0?"":String.valueOf(((Double)vRT.get(25)).intValue()));
                            oC.setDia17((((Double)vRT.get(26)).intValue())==0?"":String.valueOf(((Double)vRT.get(26)).intValue()));
                            oC.setDia18((((Double)vRT.get(27)).intValue())==0?"":String.valueOf(((Double)vRT.get(27)).intValue()));
                            oC.setDia19((((Double)vRT.get(28)).intValue())==0?"":String.valueOf(((Double)vRT.get(28)).intValue()));
                            oC.setDia20((((Double)vRT.get(20)).intValue())==0?"":String.valueOf(((Double)vRT.get(29)).intValue()));
                            oC.setDia21((((Double)vRT.get(30)).intValue())==0?"":String.valueOf(((Double)vRT.get(30)).intValue()));
                            oC.setDia22((((Double)vRT.get(31)).intValue())==0?"":String.valueOf(((Double)vRT.get(31)).intValue()));
                            oC.setDia23((((Double)vRT.get(32)).intValue())==0?"":String.valueOf(((Double)vRT.get(32)).intValue()));
                            oC.setDia24((((Double)vRT.get(33)).intValue())==0?"":String.valueOf(((Double)vRT.get(33)).intValue()));
                            oC.setDia25((((Double)vRT.get(34)).intValue())==0?"":String.valueOf(((Double)vRT.get(34)).intValue()));
                            arrRet[i]=oC;
                        } 
                } 
            }
            
            return arrRet; 
	} 

    
    public Parametrizacion getTipoProcedimineto() {
        return oProcEnf;
    }

    public void setTipoProcedimineto(Parametrizacion oProcEnf) {
        this.oProcEnf = oProcEnf;
    }

    public AreaServicioHRRB getServicio() {
        return oServicio;
    }

    public void setServicio(AreaServicioHRRB oServicio) {
        this.oServicio = oServicio;
    }

    public String getFechaInicio() {
        return sFechaInicio;
    }

    public void setFechaInicio(String sFechaInicio) {
        this.sFechaInicio = sFechaInicio;
    }

    public String getFechaFin() {
        return sFechaFin;
    }
    
    public void setFechaFin(String sFechaFin) {
        this.sFechaFin = sFechaFin;
    }
    public String getFechaInicio2() {
        return sFechaInicio2;
    }

    public void setFechaInicio2(String sFechaInicio) {
        this.sFechaInicio2 = sFechaInicio;
    }

    public String getFechaFin2() {
        return sFechaFin2;
    }
    
    public void setFechaFin2(String sFechaFin) {
        this.sFechaFin2 = sFechaFin;
    }

    public int getTotal() {
        nTotal= (Integer.parseInt(this.getDia26()==null || this.getDia26().equals("")?"0":this.getDia26())+
                Integer.parseInt(this.getDia27()==null || this.getDia27().equals("")?"0":this.getDia27())+
                Integer.parseInt(this.getDia28()==null || this.getDia28().equals("")?"0":this.getDia28())
                +Integer.parseInt(this.getDia29()==null || this.getDia29().equals("")?"0":this.getDia29())
                +Integer.parseInt(this.getDia30()==null || this.getDia30().equals("")?"0":this.getDia30())
                +Integer.parseInt(this.getDia31()==null || this.getDia31().equals("")?"0":this.getDia31())+
                Integer.parseInt(this.getDia1()==null || this.getDia1().equals("")?"0":this.getDia1())+
                Integer.parseInt(this.getDia2()==null || this.getDia2().equals("")?"0":this.getDia2())+
                Integer.parseInt(this.getDia3()==null || this.getDia3().equals("")?"0":this.getDia3())
                +Integer.parseInt(this.getDia4()==null || this.getDia4().equals("")?"0":this.getDia4())
                +Integer.parseInt(this.getDia5()==null || this.getDia5().equals("")?"0":this.getDia5())+
                Integer.parseInt(this.getDia6()==null || this.getDia6().equals("")?"0":this.getDia6())+
                Integer.parseInt(this.getDia7()==null || this.getDia8().equals("")?"0":this.getDia8())+
                Integer.parseInt(this.getDia9()==null || this.getDia9().equals("")?"0":this.getDia9())+
                Integer.parseInt(this.getDia10()==null || this.getDia10().equals("")?"0":this.getDia10())+
                Integer.parseInt(this.getDia11()==null || this.getDia11().equals("")?"0":this.getDia11())+
                Integer.parseInt(this.getDia12()==null || this.getDia12().equals("")?"0":this.getDia12())+
                Integer.parseInt(this.getDia13()==null || this.getDia13().equals("")?"0":this.getDia13())+
                Integer.parseInt(this.getDia14()==null || this.getDia14().equals("")?"0":this.getDia14())+
                Integer.parseInt(this.getDia15()==null || this.getDia15().equals("")?"0":this.getDia15())+
                Integer.parseInt(this.getDia16()==null || this.getDia16().equals("")?"0":this.getDia16())+
                Integer.parseInt(this.getDia17()==null || this.getDia17().equals("")?"0":this.getDia17())+
                Integer.parseInt(this.getDia18()==null || this.getDia18().equals("")?"0":this.getDia18())+
                Integer.parseInt(this.getDia19()==null || this.getDia19().equals("")?"0":this.getDia19())+
                Integer.parseInt(this.getDia20()==null || this.getDia20().equals("")?"0":this.getDia20())+
                Integer.parseInt(this.getDia21()==null || this.getDia21().equals("")?"0":this.getDia21())+
                Integer.parseInt(this.getDia22()==null || this.getDia22().equals("")?"0":this.getDia22())+
                Integer.parseInt(this.getDia23()==null || this.getDia23().equals("")?"0":this.getDia23())+
                Integer.parseInt(this.getDia24()==null || this.getDia24().equals("")?"0":this.getDia24())+
                Integer.parseInt(this.getDia25()==null || this.getDia25().equals("")?"0":this.getDia25()));
        return nTotal;
    }
    
	
} 

package mx.gob.hrrb.modelo.cxc; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.util.ArrayList; 
import java.util.Date;
import mx.gob.hrrb.modelo.caja.AutorizacionPago;
import mx.gob.hrrb.modelo.core.Causes; 
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Seguro;
import mx.gob.hrrb.modelo.datos.AccesoDatos; 
import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.modelo.trabajosocial.NivelSocioEconomico;

public class ServicioRealizadoT extends ServicioRealizado implements Serializable {
   private String sDescripcion;
   private int nfolioImprec; 
   private NivelSocioEconomico oNivelSocioEconomico;
   private Seguro oSeguro;
   private Causes oCauses;
   private Date dFechaDeRealizacion;
   private BigDecimal nMontoCubiertoProgramas;
   private BigDecimal nMontoCubiertoSeguroPopular;
   private BigDecimal nMontoCubiertoPaciente;
   private BigDecimal nMontoAbonado;
   private ServicioCobrable oServicioCobrable;
   private String sConcepto;
   
   
   public ServicioRealizadoT(){
    oServicioCobrable=new ServicioCobrable();
    nMontoCubiertoProgramas= new BigDecimal("0.0");
    nMontoCubiertoSeguroPopular= new BigDecimal("0.0");
    nMontoCubiertoPaciente= new BigDecimal("0.0");
    oNivelSocioEconomico= new NivelSocioEconomico(); 
    this.setEpisodio(new EpisodioMedico()); 
    oSeguro = new Seguro();
    oCauses = new Causes();
   }

    @Override
    public ServicioCobrable getServicioCobrable() {
       return oServicioCobrable;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable val) {
         this.oServicioCobrable = val;
    }

    @Override
    public boolean buscar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public ServicioRealizado[] buscarTodos() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int insertar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int modificar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }

    @Override
    public int eliminar() throws Exception {
        throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
    }
    
     public ServicioRealizadoT[] buscarTodoslosServiciosRealizadosReintegro() throws Exception {
       ServicioRealizadoT arrRet[]=null, ServicioRealizadoT=null;
	ArrayList rst = null;
	String sQuery = "select * from buscaServicioaReintegrar('"+this.getFolioImprec()+"',"+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+");";  
	int i=0; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {  
                            ServicioRealizadoT=new ServicioRealizadoT();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);   
                            ServicioRealizadoT.setIdentificador(((Double) vRowTemp.get(0)).intValue());
                            ServicioRealizadoT.setMontocobrado(BigDecimal.valueOf(((Double) vRowTemp.get(1)).intValue()));
                            ServicioRealizadoT.setDescripcion((String) vRowTemp.get(2));
                            arrRet[i]=ServicioRealizadoT;
			} 
		} 
		return arrRet; 
    }    
       public ServicioRealizadoT[] buscarTodoslosServiciosRealAnticipo(int numTipo) throws Exception {
       ServicioRealizadoT arrRet[]=null, ServicioRealizadoT=null;
	ArrayList rst = null;
	String sQuery = "select * from buscaTodosServiciosAnticiponoAsegurados("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"', "+numTipo+",'"+this.getSeguro().getNumero()+"','"+this.getSeguro().getDerechohabienteP()+"');";  
	int i=0; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {  
                            ServicioRealizadoT=new ServicioRealizadoT();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);  
                            ServicioRealizadoT.setDescripcion((String) vRowTemp.get(0)); 
                            ServicioRealizadoT.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(1)).intValue()));
                            ServicioRealizadoT.setIdentificador(((Double) vRowTemp.get(2)).intValue());
                            arrRet[i]=ServicioRealizadoT;
			} 
		} 
		return arrRet; 
    }
         
       
        public ServicioRealizadoT[] buscarTodoslosServicioRealSeg(int numTipo) throws Exception {
       ServicioRealizadoT arrRet[]=null, ServicioRealizadoT=null;
	ArrayList rst = null;
	String sQuery = "select * from buscaTodosServiciosAsegurados("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+", "+numTipo+");";  
	int i=0; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {  
                            ServicioRealizadoT=new ServicioRealizadoT();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);  
                            ServicioRealizadoT.setDescripcion((String) vRowTemp.get(0)); 
                            ServicioRealizadoT.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(1)).intValue()));
                            ServicioRealizadoT.setIdentificador(((Double) vRowTemp.get(2)).intValue());
                            arrRet[i]=ServicioRealizadoT;
			} 
		} 
		return arrRet; 
    }
       
       public ServicioRealizadoT[] buscarTodosMaterialYMedicamentoSeg(int numTipo) throws Exception {
       ServicioRealizadoT arrRet[]=null, ServicioRealizadoT=null;
	ArrayList rst = null;
	String sQuery = "select * from  buscaTodosServicioRealizadoMaterialyMedicamentoSP("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+", "+numTipo+");";  
	int i=0; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {  
                            ServicioRealizadoT=new ServicioRealizadoT();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);   
                            ServicioRealizadoT.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(0)).intValue()));
                            ServicioRealizadoT.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=ServicioRealizadoT;
			} 
		} 
		return arrRet; 
    }   
       
       
       public ServicioRealizadoT[] buscarTodosLosServicioAnticipados() throws Exception {
       ServicioRealizadoT arrRet[]=null, ServicioRealizadoT=null;
	ArrayList rst = null;
	String sQuery = "select * from BuscaServicioAnticipados("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"');";  
	int i=0; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {  
                            ServicioRealizadoT=new ServicioRealizadoT();
                            ServicioRealizadoT.setAutorizacionPago(new AutorizacionPago());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);  
                            ServicioRealizadoT.setDescripcion((String) vRowTemp.get(0)); 
                            ServicioRealizadoT.setIdentificador(((Double) vRowTemp.get(1)).intValue()); 
                            ServicioRealizadoT.getAutorizacionPago().setMonto(BigDecimal.valueOf(((Double) vRowTemp.get(2)).intValue()));
                            ServicioRealizadoT.getAutorizacionPago().setMontoSubsidio(BigDecimal.valueOf(((Double) vRowTemp.get(3)).intValue()));
                            arrRet[i]=ServicioRealizadoT;
			} 
		} 
		return arrRet; 
    }
       
       public ServicioRealizadoT[] buscaTodosLosCostosDelPaciente(int TipoPac, String TipoServ) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSP=null;
	ArrayList rst = null;
	String sQuery = ""; 
	int i=0;
		sQuery = "select * from buscaCostosDePaciente ("+
                    this.getEpisodio().getPaciente().getFolioPaciente()+", "+
                    this.getEpisodio().getClaveEpisodio()+", '"+
                    this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"', "+
                        TipoPac+", '"+this.getEpisodio().getPaciente().getSeg().getNumero()+"', '"+
                        this.getEpisodio().getPaciente().getSeg().getDerechohabienteP()+"', "+
                        this.getEpisodio().getPrograma().getDescripcion()+", '"+TipoServ+"'); "; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) { 
                             oSP=new ServicioRealizadoT();
                             ArrayList vRowTemp = (ArrayList)rst.get(i);  
                             oSP.setFechaDeRealizacion((Date) vRowTemp.get(0));
                             oSP.setCantAplicada(((Double) vRowTemp.get(1)).shortValue());
                             oSP.setDescripcion((String) vRowTemp.get(2)); 
                             oSP.setMontoCubiertoPaciente(BigDecimal.valueOf(((Double) vRowTemp.get(4)).intValue()));
                             oSP.setMontoCubiertoSeguroPopular(BigDecimal.valueOf(((Double) vRowTemp.get(5)).intValue()));
                             oSP.setMontoCubiertoProgramas(BigDecimal.valueOf(((Double) vRowTemp.get(6)).intValue()));
                            arrRet[i]=oSP;
			} 
                        return arrRet;
		}
                else{
		return null; 
                }
    }
       
       
       public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }
       
       
   /////////////////////////////////EstATNGinecoObst    
        public ServicioRealizadoT [] buscaTodosEstATNGinecoObstpacsinSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosEstATNGinecoObstpacsinSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"',"+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }
        
        
       public ServicioRealizadoT [] buscaTodosEstATNGinecoObstpacconSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosEstATNGinecoObstpacconSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'06',"+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }
       
       public ServicioRealizadoT [] buscaTodosEstATNGinecoObstpac() throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosEstATNGinecoObstpac("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"');"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }
       
       /////////////////////////////////Procedimientos Especiales
       
        public ServicioRealizadoT [] buscaTodosProcedEspeciconSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosProcedEspeciconSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'06',"+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }
        
        public ServicioRealizadoT [] buscaTodosProcedEspecisinSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosProcedEspecisinSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"',"+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }
        
        
       public ServicioRealizadoT [] buscaTodosProcedEspeci() throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosProcedEspeci("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"');"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }
        /////////////////////////////////Cirugias Generales 
       
       public ServicioRealizadoT [] buscaTodasCirugiaGeneralpagPac() throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodasCirugiaGeneralpagPac("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"');"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
                        return arrRet; 
		} 
                 return null; 
		 
    } 
       
       
    public ServicioRealizadoT [] buscaTodasCirugiaGeneralPacConSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodasCirugiaGeneralPacConSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'06',"+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		     return arrRet; 
		} 
                 return null;  
    }
     
    
       public ServicioRealizadoT [] buscaTodasCirugiaGeneralPacsinSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodasCirugiaGeneralPacsinSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"',"+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT();
                            oSerCob.setServicioCobrable(new ServicioCobrable());
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.getServicioCobrable().setClave((String) vRowTemp.get(0));
                            oSerCob.getServicioCobrable().setAbreviatura((String) vRowTemp.get(1)); 
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(2)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		     return arrRet; 
		} 
                 return null;  
    }
       
       
       
       ////////Todos los Servicios Cuenta Corriente Documento
       
        public ServicioRealizadoT [] buscaTodosServCuentaCorrientPacPag(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosServicioCuentaCorrientePagaPac("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"',"+ num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oSerCob.setConcepto((String) vRowTemp.get(0));
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(1)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }   
        
        
       public ServicioRealizadoT [] buscaTodosServicioCuentaCorrienteSinSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosServicioCuentaCorrienteSinSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"', "+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.setConcepto((String) vRowTemp.get(0));
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(1)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    }   
       
       public ServicioRealizadoT [] buscaTodosServicioCuentaCorrienteConSeguro(int num) throws Exception{
        ServicioRealizadoT arrRet[]=null, oSerCob=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosServicioCuentaCorrienteConSeguro("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'06', "+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			arrRet = new ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oSerCob=new ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oSerCob.setConcepto((String) vRowTemp.get(0));
                            oSerCob.setCantACobrar(((Double) vRowTemp.get(1)).shortValue());
                            arrRet[i]=oSerCob;
			} 
		} 
		return arrRet; 
    } 
       
       
       
       
       public void buscaAbonosACuenta() throws Exception{
   	ArrayList rst = null;
	String sQuery = "";  
        
        sQuery = "select sum(poutnmonto) from buscaTodasAutoRizPagadasPac("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+");";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) {
                        ArrayList vRowTemp = (ArrayList)rst.get(0); 
                        this.setMontoAbonado(BigDecimal.valueOf(((Double) vRowTemp.get(0)).intValue()));
                }
               
 }
       
       public  ServicioRealizadoT[] buscaTodoslosServiciosNoAsegurados(int num) throws Exception{
         ServicioRealizadoT arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosServicioRealizadoPA("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"',"+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new  ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new  ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oFD.setDescripcion((String) vRowTemp.get(0));  
                            oFD.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(1)).intValue()));
                            oFD.setIdentificador(((Double) vRowTemp.get(2)).intValue());
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    }     
     
         public  ServicioRealizadoT[] buscaTodoslosServiciosAsegurados(int num) throws Exception{
         ServicioRealizadoT arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodosServicioRealizadoSP("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+","+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new  ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new  ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oFD.setDescripcion((String) vRowTemp.get(0));  
                            oFD.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(1)).intValue()));
                            oFD.setIdentificador(((Double) vRowTemp.get(2)).intValue());
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    }
       public  ServicioRealizadoT[] buscaTodosLosServiciosPac() throws Exception{
         ServicioRealizadoT arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select poutnmontoacobrar, poutnidentificadorservreal from buscaTodosServiciosAnticipo("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+",'"+this.getNivelSocioEconomico().getNivelAlcanzado().getClaveParametro()+"');"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new  ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new  ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oFD.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(0)).intValue()));
                            oFD.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    }
       
         public  ServicioRealizadoT[] buscaTodosMaterialYMedicamentoPA(int num) throws Exception{
         ServicioRealizadoT arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select  poutnpreciores , poutnidentificadorservrealres from buscaTodosServicioRealizadoMaterialyMedicamentoPA("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+", "+num+");"; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new  ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new  ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oFD.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(0)).intValue()));
                            oFD.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    } 
    
          public  ServicioRealizadoT[] buscaTodosMaterialYMedicamentoSP(int num) throws Exception{
         ServicioRealizadoT arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select   poutnpreciores , poutnidentificadorservrealres  from buscaTodosServicioRealizadoMaterialyMedicamentoSP("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+", "+num+");";
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new  ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new  ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oFD.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(0)).intValue()));
                            oFD.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    } 
          
         public  ServicioRealizadoT[] buscaTodosMaterial() throws Exception{
         ServicioRealizadoT arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select  poutnprecio , poutnidentificadorservreal  from buscaTodoMaterialesPac("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+");";
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new  ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new  ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oFD.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(0)).intValue()));
                            oFD.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    }   
        
                public  ServicioRealizadoT[] buscaTodoMedicamento() throws Exception{
         ServicioRealizadoT arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select  poutnprecio , poutnidentificadorservreal  from buscaTodoMedicamenPac("+this.getEpisodio().getPaciente().getFolioPaciente()+","+this.getEpisodio().getClaveEpisodio()+");";
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new  ServicioRealizadoT[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new  ServicioRealizadoT(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oFD.setCostoUnitACobrar(BigDecimal.valueOf(((Double) vRowTemp.get(0)).intValue()));
                            oFD.setIdentificador(((Double) vRowTemp.get(1)).intValue());
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    }    
         
          

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }

    public int getFolioImprec() {
        return nfolioImprec;
    }

    public void setFolioImprec(int nfolioImprec) {
        this.nfolioImprec = nfolioImprec;
    } 

    public NivelSocioEconomico getNivelSocioEconomico() {
        return oNivelSocioEconomico;
    }

    public void setNivelSocioEconomico(NivelSocioEconomico oNivelSocioEconomico) {
        this.oNivelSocioEconomico = oNivelSocioEconomico;
    }

    public Seguro getSeguro() {
        return oSeguro;
    }

    public void setSeguro(Seguro oSeguro) {
        this.oSeguro = oSeguro;
    }

    public BigDecimal getMontoCubiertoProgramas() {
        return nMontoCubiertoProgramas;
    }

    public void setMontoCubiertoProgramas(BigDecimal nMontoCubiertoProgramas) {
        this.nMontoCubiertoProgramas = nMontoCubiertoProgramas;
    }

    public BigDecimal getMontoCubiertoSeguroPopular() {
        return nMontoCubiertoSeguroPopular;
    }

    public void setMontoCubiertoSeguroPopular(BigDecimal nMontoCubiertoSeguroPopular) {
        this.nMontoCubiertoSeguroPopular = nMontoCubiertoSeguroPopular;
    }

    public BigDecimal getMontoCubiertoPaciente() {
        return nMontoCubiertoPaciente;
    }

    public void setMontoCubiertoPaciente(BigDecimal nMontoCubiertoPaciente) {
        this.nMontoCubiertoPaciente = nMontoCubiertoPaciente;
    }

    public Date getFechaDeRealizacion() {
        return dFechaDeRealizacion;
    }

    public void setFechaDeRealizacion(Date dFechaDeRealizacion) {
        this.dFechaDeRealizacion = dFechaDeRealizacion;
    }

    public String getConcepto() {
        return sConcepto;
    }

    public void setConcepto(String sConcepto) {
        this.sConcepto = sConcepto;
    }

    public BigDecimal getMontoAbonado() {
        return nMontoAbonado;
    }

    public void setMontoAbonado(BigDecimal nMontoAbonado) {
        this.nMontoAbonado = nMontoAbonado;
    }
    
  

  
    
}

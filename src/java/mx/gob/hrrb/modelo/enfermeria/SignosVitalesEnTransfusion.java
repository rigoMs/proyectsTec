package mx.gob.hrrb.modelo.enfermeria;

import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class SignosVitalesEnTransfusion implements Serializable{
    
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private long nIdSignos;
	private String sFC;
	private String sFR;
	private String sTA;
	private String sTemp;
	private Parametrizacion oTipoRevisado;
	private HojaDeTransfusion oHojaDeTransfusion;
        private final String sTabTipoRevisado="TAI";   
        List<Parametrizacion> LTipoRevisado;  
        
	public SignosVitalesEnTransfusion(){
            oTipoRevisado = new Parametrizacion();
            try {
                LTipoRevisado=  new ArrayList<Parametrizacion>(Arrays.asList((new Parametrizacion()).buscarTabla(sTabTipoRevisado)));
            } catch (Exception ex) {
                Logger.getLogger(SignosVitalesEnTransfusion.class.getName()).log(Level.SEVERE, null, ex);
            }  
	    HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
        
        public int insertarTransfusionYSignosVistales(ArrayList<SignosVitalesEnTransfusion> pArraySig) throws Exception{	
            ArrayList<String> rstTemp=null;
            int nRet = -1;
            String sQuery = "";
		 if( this.getHojaDeTransfusion().getFolio()==-2){ 
			throw new Exception("SignosVitalesEnTransfusion.insertarSignosTransfusion: error, faltan datos");
		}else{ 
                     rstTemp= new ArrayList<String>();
                     rstTemp.add(this.getHojaDeTransfusion().getInsertarTransfusion());
                     for(SignosVitalesEnTransfusion oSg :pArraySig){
                         sQuery="SELECT * FROM insertaSignosVitalesEnTransfusion("+this.getHojaDeTransfusion().getFolio()+"::bigint,'"
                                 +sTabTipoRevisado+"'::character varying,'"+buscarClaveTipoRevisado(oSg.getTipoRevisado().getValor())+"'::character varying,'"
                                 +oSg.getTa()+"'::character varying,'"+oSg.getFc()+"'::character varying,'"+oSg.getTemp()+"'::character varying,'"+oSg.getFr()+"'::character varying,'"
                                 +sUsuarioFirmado+"'::character varying);";
                         rstTemp.add(sQuery);
                     }
                     if(rstTemp.size()>0){
                         oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				nRet = oAD.ejecutarConsultaComando(rstTemp);
				oAD.desconectar();	
			}
                     }
                 } 
		return nRet; 
	} 
        
        public int insertarSignosTransfusion(ArrayList<SignosVitalesEnTransfusion> pArraySig) throws Exception{	
            ArrayList<String> rstTemp=null;
            int nRet = -1;
            String sQuery = "";
		 if( this.getHojaDeTransfusion().getFolio()==0){ 
			throw new Exception("SignosVitalesEnTransfusion.insertarSignosTransfusion: error, faltan datos");
		}else{ 
                     rstTemp= new ArrayList<String>();                     
                     for(SignosVitalesEnTransfusion oSg :pArraySig){
                         sQuery="SELECT * FROM insertaSignosVitalesEnTransfusion("+this.getHojaDeTransfusion().getFolio()+"::bigint,'"
                                 +sTabTipoRevisado+"'::character varying,'"+buscarClaveTipoRevisado(oSg.getTipoRevisado().getValor())+"'::character varying,'"
                                 +oSg.getTa()+"'::character varying,'"+oSg.getFc()+"'::character varying,'"+oSg.getTemp()+"'::character varying,'"+oSg.getFr()+"'::character varying,'"
                                 +sUsuarioFirmado+"'::character varying);";
                         rstTemp.add(sQuery);
                     }
                     if(rstTemp.size()>0){
                         oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				nRet = oAD.ejecutarConsultaComando(rstTemp);
				oAD.desconectar();	
			}
                     }
                 } 
		return nRet; 
	} 
        
	public int modificarTransfucionYSignosVitales(ArrayList<SignosVitalesEnTransfusion> pArraySig) throws Exception{
            ArrayList rst = null;
            ArrayList<String> vTemp=null;
            int nRet = -1;
            String sQuery = "";
		 if(pArraySig.isEmpty()){  
			throw new Exception("SignosVitalesEnTransfusion.modificarSignosVitalesEnTransfusion: error, faltan datos");
		}else{
                     vTemp = new ArrayList<String>();
                     vTemp.add(this.getHojaDeTransfusion().getModificarTransfusion());
                     for(SignosVitalesEnTransfusion oSig : pArraySig){
                        sQuery = "SELECT * FROM modificaSignosVitalesTransfusion("+oSig.getIdSignos()+"::bigint,"
                                +oSig.getHojaDeTransfusion().getFolio()+"::bigint,'"
                                +oSig.getTa()+"'::character varying,'"+oSig.getFc()+"'::character varying,'"
                                +oSig.getTemp()+"'::character varying,'"+oSig.getFr()+"'::character varying,"
                                +"'"+sUsuarioFirmado+"'::character varying);";
                        vTemp.add(sQuery);
                     }
                     if(vTemp.size()>0){
                         oAD=new AccesoDatos(); 
                         if(oAD.conectar()){
                             nRet= oAD.ejecutarConsultaComando(vTemp);
                             oAD.desconectar();
                         }
                     }			
		} 
		return nRet; 
	}
        
        
	public long getIdSignos() {
            return nIdSignos;
	}

	public void setIdSignos(long valor) {
            this.nIdSignos=valor;
	}

	public String getFc() {
            return sFC;
	}

	public void setFc(String valor) {
            this.sFC=valor;
	}

	public String getFr() {
            return sFR;
	}

	public void setFr(String valor) {
            this.sFR=valor;
	}

	public String getTa() {
            return sTA;
	}
        
	public void setTa(String valor) {
            this.sTA=valor;
	}

	public String getTemp() {
            return sTemp;
	}

	public void setTemp(String valor) {
            this.sTemp=valor;
	}

	public Parametrizacion getTipoRevisado() {
            return oTipoRevisado;
	}

	public void setTipoRevisado(Parametrizacion valor) {
            oTipoRevisado=valor;
	}

	public HojaDeTransfusion getHojaDeTransfusion() {
            return oHojaDeTransfusion;
	}

	public void setHojaDeTransfusion(HojaDeTransfusion valor) {
            oHojaDeTransfusion=valor;
	}
        
        public List<Parametrizacion> getArregloTipoRevisado(){            
            return LTipoRevisado;
        }
               
        
         public String buscarClaveTipoRevisado(String valorBusca){
        String sClave="";
        for(Parametrizacion oP : LTipoRevisado){
            if(oP.getValor().equals(valorBusca)){
                sClave = oP.getTipoParametro();
                break;
            }
        }
        
        return sClave;
    }

} 

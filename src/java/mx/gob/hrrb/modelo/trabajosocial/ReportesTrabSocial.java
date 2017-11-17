 
package mx.gob.hrrb.modelo.trabajosocial; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.caja.AutorizacionPago;
import mx.gob.hrrb.modelo.caja.CuotaRecuperacion; 
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos; 


public class ReportesTrabSocial implements Serializable{
    private EpisodioMedico oEpi; 
    private String sDescripcion;
    private String sTipoAuto;
    private AccesoDatos oAD; 
    private AutorizacionPago oAutorizacionPago;
     private PersonalHospitalario oPersonalAuto; 
    
    
    public ReportesTrabSocial(){
        oAutorizacionPago=new AutorizacionPago();
        oEpi=new EpisodioMedico();
        oPersonalAuto = new PersonalHospitalario();
    }
    
    
    public ReportesTrabSocial[] buscaAutorizaciones(String tipo, Date fechInici,Date fechFinal) throws Exception{
        ReportesTrabSocial arrRet[]=null, oAut=null;
        SimpleDateFormat df =new SimpleDateFormat("dd/MM/yyyy");
        String fechainicail =df.format(fechInici);
        String fechaFinal=df.format(fechFinal); 
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaTodasAutorizaDep("+isNull(this.getEpi().getPaciente().getNombres().toUpperCase())+","+isNull(this.getEpi().getPaciente().getApPaterno().toUpperCase().toUpperCase())+", "+isNull(this.getEpi().getPaciente().getApMaterno().toUpperCase())+", "+numnull(this.getEpi().getPaciente().getExpediente().getNumero())+", "+this.numnull(this.getPersonalAuto().getNoTarjeta())+","+this.isNull(tipo)+" ,'"+fechainicail+"','"+fechaFinal+"') order by poutdfechautorizacionRes desc "; 
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new ReportesTrabSocial[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oAut=new ReportesTrabSocial(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oAut.getEpi().getPaciente().setNombres((String)vRowTemp.get(0));
                            oAut.getEpi().getPaciente().setApPaterno((String)vRowTemp.get(1));
                            oAut.getEpi().getPaciente().setApMaterno((String)vRowTemp.get(2));
                            oAut.getEpi().getPaciente().getExpediente().setNumero(((Double) vRowTemp.get(3)).intValue());
                            oAut.setTipoAuto((String)vRowTemp.get(4));
                            oAut.setDescripcion((String)vRowTemp.get(5));
                            oAut.getAutorizacionPago().setMonto(BigDecimal.valueOf(((Double) vRowTemp.get(6)).intValue()));
                            oAut.getAutorizacionPago().setFechaAutorizacion((Date)vRowTemp.get(7));
                            oAut.getAutorizacionPago().getQuienCapturaAutPago().setNoTarjeta(((Double) vRowTemp.get(8)).intValue());
                            oAut.getAutorizacionPago().getQuienCapturaAutPago().buscarCapa();
                            oAut.getAutorizacionPago().getQuienAutorizaPago().setNoTarjeta(((Double) vRowTemp.get(9)).intValue());
                            oAut.getAutorizacionPago().getQuienAutorizaPago().buscarCapa();
                            oAut.getAutorizacionPago().getQuienTramitaPago().setNoTarjeta(((Double) vRowTemp.get(10)).intValue());      
                            oAut.getAutorizacionPago().getQuienTramitaPago().buscarCapa();
                            arrRet[i]=oAut;
			} 
		} 
		return arrRet; 
    }
    
    
   
    
    
         
     public String isNull(String param){
            if( param==null||param.equals("") || param.isEmpty())
                param=null;
            else
                param="'"+param.trim()+"'";
            return param;
    }

public String numnull(int num){
    if(num==0){
        return null;
    }
    else{
        return ""+num;
    }
    
}


public String numnullshort(short num){
    if(num==0){
        return null;
    }
    else{
        return ""+num;
    }
    
}
    

    public EpisodioMedico getEpi() {
        return oEpi;
    }

    public void setEpi(EpisodioMedico oEpi) {
        this.oEpi = oEpi;
    } 

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion(String sdescripcion) {
        this.sDescripcion = sdescripcion;
    } 

    public String getTipoAuto() {
        return sTipoAuto;
    }

    public void setTipoAuto(String sTipoAuto) {
        this.sTipoAuto = sTipoAuto;
    }

    public PersonalHospitalario getPersonalAuto() {
        return oPersonalAuto;
    }

    public void setPersonalAuto(PersonalHospitalario oPersonalAuto) {
        this.oPersonalAuto = oPersonalAuto;
    }

    public AutorizacionPago getAutorizacionPago() {
        return oAutorizacionPago;
    }

    public void setAutorizacionPago(AutorizacionPago oAutorizacionPago) {
        this.oAutorizacionPago = oAutorizacionPago;
    }
    
}

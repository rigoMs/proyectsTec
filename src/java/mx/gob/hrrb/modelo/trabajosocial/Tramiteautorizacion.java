package mx.gob.hrrb.modelo.trabajosocial;
 
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

public class Tramiteautorizacion implements Serializable{
  private long nIdTramit;
  private Date dFechaTramite;
  private PersonalHospitalario oTstramito; 
  private AccesoDatos oAD; 
  
  public Tramiteautorizacion(){
 oTstramito= new PersonalHospitalario(); 
}
  
  
  public int inserta(EpisodioMedico epi) throws Exception{
         int puntaje=0;
         ArrayList rst = null;
        SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
        Date now = new Date();
        String strDate = SF.format(now);
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from insertatramiteautorizacion('"+this.getTstramito().getUsuar().getIdUsuario()+"', '"+strDate+"', "+this.getTstramito().getNoTarjeta()+", "+epi.getPaciente().getFolioPaciente()+","+epi.getClaveEpisodio()+");";
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				puntaje=(((Double)vRowTemp.get( 0 )).intValue());
                        }
                 
		} 
                  return puntaje; 
      } 
  
  
         public void buscaIdTramite(EpisodioMedico epi) throws Exception{
   	ArrayList rst = null;
	String sQuery = "";  
        
        sQuery = "select * from buscaLlavetramiteautorizacion("+epi.getPaciente().getFolioPaciente()+","+epi.getClaveEpisodio()+");";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) {
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        this.setIdTramit(((Double) vRowTemp.get( 0 )).intValue());  
                        
                }else{
                    this.setIdTramit(0);
                }
               
 }
  
  
  
  
  

    public long getIdTramit() {
        return nIdTramit;
    }

    public void setIdTramit(long nIdTramit) {
        this.nIdTramit = nIdTramit;
    }

    public Date getFechaTramite() {
        return dFechaTramite;
    }

    public void setdFechaTramite(Date dFechaTramite) {
        this.dFechaTramite = dFechaTramite;
    }

    public PersonalHospitalario getTstramito() {
        return oTstramito;
    }

    public void setTstramito(PersonalHospitalario oTstramito) {
        this.oTstramito = oTstramito;
    } 
  
  
  
}

 

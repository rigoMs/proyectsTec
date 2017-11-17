package mx.gob.hrrb.modelo.trabajosocial;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

public class NotaTrabajoSocial implements Serializable {
     private AccesoDatos oAD; 
     private Date dfechaRegistro;
     private String sNota;
     private EpisodioMedico oEpisodioMedico;
     
 public NotaTrabajoSocial[] buscalistdesplAutori() throws Exception{
     ArrayList rst = null;
     NotaTrabajoSocial arrRet[]=null, oNts=null;
     String sQuery = "";     
     int i=0,nTam=0;
     List<NotaTrabajoSocial> vObj=null;
        sQuery = "select * from buscaNotasTrabajosocial("+this.getEpisodioMedico().getPaciente().getFolioPaciente()+","+this.getEpisodioMedico().getClaveEpisodio()+");"; 
        System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
	            rst = oAD.ejecutarConsulta(sQuery); 
	            oAD.desconectar(); 
		}
                if (rst != null) {
                    vObj = new ArrayList<NotaTrabajoSocial>();
                    for (i=0; i<rst.size(); i++){
                        oNts=new  NotaTrabajoSocial();
                        ArrayList<Object> vRowTemp = (ArrayList)rst.get(i); 
                        oNts.setFechaRegistro((Date)vRowTemp.get(0));
                        oNts.setNota((String)vRowTemp.get(1)); 
                        vObj.add(oNts); 
                    }
                    nTam = vObj.size();
                    arrRet = new NotaTrabajoSocial[nTam];
                    
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }                         
                }
                return arrRet;
 }


   public int insertarNotaTrabajoSocial(String sUsuario,Paciente oPaciente, EpisodioMedico oEpisodioMedico, String Fech, String nota, String nombreTra  ) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Cita.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertNotaTrabajoSocial('"+sUsuario+"',"+ oPaciente.getFolioPaciente()+","+oEpisodioMedico.getClaveEpisodio()+",'"+Fech +"','"+ nombreTra +": "+nota+"');"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos();  
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
                                        ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	}
   
   public NotaTrabajoSocial[] buscaHistorialNotasTrabajoSocial(long foliopac) throws Exception{
        NotaTrabajoSocial arrNotas[] = null, oNota=null;
        ArrayList rst=null;
        String sQuery="";
        int i=0;
            if(foliopac==0){
                throw new Exception("NotaTrabajoSocial.buscaHistorialNotasTrabajoSocial: Error, Falta datos");
            }else{                
                sQuery="SELECT * FROM buscahistorialnotastrabajosocial("+foliopac+"::bigint);"; 
                System.out.println(sQuery);
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);                    
                    oAD.desconectar();
                }
                if(!rst.isEmpty()){
                    arrNotas = new NotaTrabajoSocial[rst.size()];
                    for(i = 0; i < rst.size(); i++){
                        oNota= new NotaTrabajoSocial();
                        oNota.setEpisodioMedico(new EpisodioMedico());
                        ArrayList vRowTemp = (ArrayList)rst.get(i);                        
                        oNota.getEpisodioMedico().setClaveEpisodio(((Double)vRowTemp.get(0)).longValue());
                        oNota.getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                        oNota.setFechaRegistro((Date)vRowTemp.get(2));
                        arrNotas[i] = oNota;
                    }
                }
            }
        return arrNotas;
    }
  
   public NotaTrabajoSocial buscaDatosBasicosHojaTrabajoSocial() throws Exception{
        NotaTrabajoSocial oNota=null;
        ArrayList rst=null;
        String sQuery="", edad="";
        int i=0;
            if(this==null){
                throw new Exception("NotaTrabajoSocial.buscaDatosBasicosHojaTrabajoSocial: Error, Falta datos");
            }else{                
                sQuery="SELECT * FROM buscadatosbasicoshojatrabajosocial("+this.getEpisodioMedico().getPaciente().getFolioPaciente()+","+this.getEpisodioMedico().getClaveEpisodio()+");";
                //System.out.println(sQuery);
                oAD=new AccesoDatos();
                if(oAD.conectar()){
                    rst= oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst !=null && rst.size()>0){
                        oNota= new NotaTrabajoSocial();
                        oNota.setEpisodioMedico(new EpisodioMedico());
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oNota.getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(0));
                        oNota.getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(1));
                        oNota.getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(2));
                        oNota.getEpisodioMedico().getPaciente().setFechaNac((Date)vRowTemp.get(3));
                        edad=(String)vRowTemp.get(4);
                        if (edad.compareTo("")!=0){
                            if(edad.substring(0, 3).compareTo("000")!=0){
                                if (edad.charAt(0)=='0')
                                   oNota.getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(1, 3)+" AÑOS");
                                else
                                   oNota.getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(0, 3)+" AÑOS");
                            }else{
                                if (edad.substring(4, 6).compareTo("00")!=0)
                                    oNota.getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(4, 6)+" MESES");
                                else
                                    oNota.getEpisodioMedico().getPaciente().setEdadNumero(edad.substring(7, 9)+" DÍAS");
                                }
                        }
                        oNota.getEpisodioMedico().getPaciente().setSexoP((String)vRowTemp.get(5));
                        oNota.getEpisodioMedico().getExpediente().setNumero2(((Double)vRowTemp.get(6)).intValue());
                        oNota.getEpisodioMedico().getPaciente().getSeg().setNumero((String)vRowTemp.get(7));
                        oNota.getEpisodioMedico().getArea().setDescripcion((String)vRowTemp.get(8));
                        oNota.getEpisodioMedico().getCama().setNumero((String)vRowTemp.get(9));
                    }
            }
        return oNota;
    }
    
    public Date getFechaRegistro() {
        return dfechaRegistro;
    }
 
    public void setFechaRegistro(Date dfechaRegistro) {
        this.dfechaRegistro = dfechaRegistro;
    }
 
    public String getNota() {
        return sNota;
    }
 
    public void setNota(String sNota) {
        this.sNota = sNota;
    }

    public EpisodioMedico getEpisodioMedico() {
        return oEpisodioMedico;
    }

    public void setEpisodioMedico(EpisodioMedico oEpisodioMedico) {
        this.oEpisodioMedico = oEpisodioMedico;
    }
}
    


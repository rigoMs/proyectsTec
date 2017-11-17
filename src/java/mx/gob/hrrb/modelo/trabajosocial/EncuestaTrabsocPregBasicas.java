package mx.gob.hrrb.modelo.trabajosocial; 

import java.util.ArrayList;
import java.util.List;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

public class EncuestaTrabsocPregBasicas {
    private short nIdPregunta;
    private String sDescripcion;
    private short nPuntaje;
    private Parametrizacion oTipoPreg;
    private AccesoDatos oAD; 
    
    
    public EncuestaTrabsocPregBasicas() throws Exception{
        oTipoPreg = new Parametrizacion();
    }
    
      public EncuestaTrabsocPregBasicas [] buscaPreguntaEscuetaSocioEco(String stipoPreg) throws Exception{
     ArrayList rst = null;
        EncuestaTrabsocPregBasicas[] arrRet = null;
        EncuestaTrabsocPregBasicas oPre = null;
     String sQuery = "";     
     int i=0,nTam=0;
     List<EncuestaTrabsocPregBasicas> vObj=null;
     sQuery = "select * from buscaEncuestaSocioEconoc('"+stipoPreg+"')"; 
     ///   System.out.println(sQuery);
                oAD=new AccesoDatos();  
		if (oAD.conectar()){ 
	            rst = oAD.ejecutarConsulta(sQuery); 
	            oAD.desconectar(); 
		}
                if (rst != null) {
                    vObj = new ArrayList<EncuestaTrabsocPregBasicas >();
                    for (i=0; i<rst.size(); i++){
                        oPre=new  EncuestaTrabsocPregBasicas();
                        ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                        oPre.setIdPregunta(((Double) vRowTemp.get(0)).shortValue());
                        oPre.setPuntaje(((Double) vRowTemp.get(1)).shortValue());
                        oPre.setDescripcion((String)vRowTemp.get(2));
                        oPre.getTipoPreg().setTipoParametro((String)vRowTemp.get(3));
                        oPre.getTipoPreg().setTipoParametro((String)vRowTemp.get(4)); 
                        vObj.add(oPre); 
                    }
                      nTam = vObj.size();
                    arrRet = new EncuestaTrabsocPregBasicas[nTam];
                    
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                }
                }
                 return arrRet;
 } 
      
       public short buscaPuntaje(short idpregunta) throws Exception{
         short puntaje=0;
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from buscaEncuestaSocioEconocpuntaje('"+idpregunta+"')";
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				puntaje=(((Double)vRowTemp.get( 0 )).shortValue());
                        }
                 
		} 
                  return puntaje; 
      }
       
       public int insertarespuestastrabsocbas(String Usuario, long foliopac, String fecha, short idpre) throws Exception{
         int Resul=0; 
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from insertarespuestastrabsocbas('"+Usuario+"'::character varying, "+foliopac+", '"+fecha+"'::date,"+idpre+"::smallint);";
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
                        if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				Resul=(((Double)vRowTemp.get( 0 )).intValue());
                        }
                 
		} 
                  return Resul; 
      }
       
    public EncuestaTrabsocPregBasicas[] buscaRespuestasBasicasTS(long foliopac,String fecha) throws Exception{
        EncuestaTrabsocPregBasicas arrRet[]=null, oRP=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
        if( foliopac==0 || fecha.isEmpty()){
            throw new Exception("EncuestaTrabSocPregBasicas.buscaRespuestasBasicasTS: error de programacion, faltan datos");
        }else{ 
            sQuery = "select * from buscarespuestasbasicasTS("+foliopac+",'"+fecha+"');"; 
            oAD=new AccesoDatos(); 
            System.out.println(sQuery);
            if (oAD.conectar()){ 
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar(); 
            }
            if (rst != null) {
                arrRet = new EncuestaTrabsocPregBasicas[rst.size()];
                for (i = 0; i < rst.size(); i++) {
                    oRP=new EncuestaTrabsocPregBasicas();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oRP.setIdPregunta(((Double) vRowTemp.get(0)).shortValue());
                    oRP.setPuntaje(((Double) vRowTemp.get(1)).shortValue());
                    oRP.getTipoPreg().setValor((String)vRowTemp.get(2));
                    oRP.setDescripcion((String)vRowTemp.get(3));
                    arrRet[i]=oRP;
                }
            }
        }
        return arrRet;
    }    

    public short getIdPregunta() {
        return nIdPregunta;
    }

    public void setIdPregunta(short nIdPregunta) {
        this.nIdPregunta = nIdPregunta;
    }

    public String getDescripcion() {
        return sDescripcion;
    }

    public void setDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }

    public short getPuntaje() {
        return nPuntaje;
    }

    public void setPuntaje(short nPuntaje) {
        this.nPuntaje = nPuntaje;
    }

    public Parametrizacion getTipoPreg() {
        return oTipoPreg;
    }

    public void setTipoPreg(Parametrizacion oTipoPreg) {
        this.oTipoPreg = oTipoPreg;
    }
    
}

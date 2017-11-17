package mx.gob.hrrb.modelo.trabajosocial;
 
import java.util.ArrayList;
import java.util.List;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

 

public class IngresoFamiliar {
    private short nAnio;
    private short nOrden;
    private String sTxtSalario; 
    private String sTxtIngreso;
    private short nPtjDepen1_2=0;
    private short nPtjDepen3_4=0;
    private short nPtjDepen5_6=0;
    private short nPtjDepen7_8=0;
    private short nPtjDepen9=0; 
    private AccesoDatos oAD; 
    
    public IngresoFamiliar (){
         oAD = new AccesoDatos();
    }
    
    
    
    
       public IngresoFamiliar[] BuscaPreguntaEscuetaSocioEcoingre() throws Exception{
         IngresoFamiliar arrRet[]=null, oPreIng=null;
         ArrayList rst = null;
          List<IngresoFamiliar> vObj=null;
     String sQuery = "";     
     int i=0,nTam=0;
     
      sQuery ="select * from buscaIngreMensualEncuesta();"; 
        System.out.println(sQuery);
                oAD=new AccesoDatos();  
		if (oAD.conectar()){ 
	            rst = oAD.ejecutarConsulta(sQuery); 
	            oAD.desconectar(); 
		}
                if (rst != null) {
                    vObj = new ArrayList<IngresoFamiliar>();
                    for (i=0; i<rst.size(); i++){
                        oPreIng=new  IngresoFamiliar();
                        ArrayList<Object> vRowTemp = (ArrayList)rst.get(i); 
                        oPreIng.setOrden(((Double) vRowTemp.get( 0 )).shortValue());
                        oPreIng.setTxtIngreso((String)vRowTemp.get(1));
                        oPreIng.setTxtSalario((String)vRowTemp.get(2));
                        vObj.add(oPreIng); 
                    }
                      nTam = vObj.size();
                    arrRet = new IngresoFamiliar[nTam];
                    
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                }
                }
                 return arrRet;
 } 
       
       public void buscapuntaIngresoFamiliar(short puntaje) throws Exception{
   	ArrayList rst = null;
	String sQuery = "";  
        
        sQuery = " select * from buscapuntajeenctrabsocingrfamiliar('"+puntaje+"');";
                System.out.println(sQuery);
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()==1) {
                        ArrayList vRowTemp = (ArrayList)rst.get(0); 
                        this.setPtjDepen1_2(((Double)vRowTemp.get( 0 )).shortValue());
                        this.setPtjDepen3_4(((Double)vRowTemp.get( 1 )).shortValue());
                        this.setPtjDepen5_6(((Double)vRowTemp.get( 2 )).shortValue());
                        this.setPtjDepen7_8(((Double)vRowTemp.get( 3 )).shortValue());
                        this.setPtjDepen9(((Double)vRowTemp.get( 4 )).shortValue());
                }
               
 }
       
       
          public int insertaresptrabsocingrfam(String Usuario, long foliopac, String fecha,int filaselcc ,int columnselecc) throws Exception{
         int Resul=0;
         int ano=2013;
         ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hospitalizacion.buscarHospitalizado: error de programacion, faltan datos");
		}else{ 
			sQuery = "select * from insertaresptrabsocingrfam('"+Usuario+"'::character varying, "+foliopac+"::bigint ,'"+fecha+"'::date, "+ano+"::smallint, "+filaselcc+"::smallint, "+columnselecc+"::smallint);";
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

    public short getAnio() {
        return nAnio;
    }

    public void setAnio(short nAnio) {
        this.nAnio = nAnio;
    }

    public short getOrden() {
        return nOrden;
    }

    public void setOrden(short nOrden) {
        this.nOrden = nOrden;
    }

    public String getTxtSalario() {
        return sTxtSalario;
    }

    public void setTxtSalario(String sTxtSalario) {
        this.sTxtSalario = sTxtSalario;
    }

    public String getTxtIngreso() {
        return sTxtIngreso;
    }

    public void setTxtIngreso(String sTxtIngreso) {
        this.sTxtIngreso = sTxtIngreso;
    }

    public short getPtjDepen1_2() {
        return nPtjDepen1_2;
    }

    public void setPtjDepen1_2(short nPtjDepen1_2) {
        this.nPtjDepen1_2 = nPtjDepen1_2;
    }

    public short getPtjDepen3_4() {
        return nPtjDepen3_4;
    }

    public void setPtjDepen3_4(short nPtjDepen3_4) {
        this.nPtjDepen3_4 = nPtjDepen3_4;
    }

    public short getPtjDepen5_6() {
        return nPtjDepen5_6;
    }

    public void setPtjDepen5_6(short nPtjDepen5_6) {
        this.nPtjDepen5_6 = nPtjDepen5_6;
    }

    public short getPtjDepen7_8() {
        return nPtjDepen7_8;
    }

    public void setPtjDepen7_8(short nPtjDepen7_8) {
        this.nPtjDepen7_8 = nPtjDepen7_8;
    }

    public short getPtjDepen9() {
        return nPtjDepen9;
    }

    public void setPtjDepen9(short nPtjDepen9) {
        this.nPtjDepen9 = nPtjDepen9;
    }
    
}

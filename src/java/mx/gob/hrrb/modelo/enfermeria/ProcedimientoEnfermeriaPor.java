
package mx.gob.hrrb.modelo.enfermeria;

import java.io.Serializable;
import java.util.ArrayList;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author Javier
 */
public class ProcedimientoEnfermeriaPor implements Serializable {
    private AccesoDatos oAD;
    private int nClavePor;
    private String sDescripcion;
    private String sAbreviacion;
    private int nServicioCoba;
    private Parametrizacion oProcPor;
    private static final  String sTablaProc="TPE";
    
    public ProcedimientoEnfermeriaPor(){
        oProcPor= new Parametrizacion();
    }  
    
    public ProcedimientoEnfermeriaPor[] buscarProcedimientoEnfermeria(String tipo) throws Exception{
        ProcedimientoEnfermeriaPor arrRet[]=null, oPro=null;
	ArrayList rst = null;        
	String sQuery = "";
	int i=0;
		sQuery = "select * from  buscarProcedimientoEnfermeria('"+tipo+"'::character varying);"; 
                //System.out.println(sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                    arrRet = new ProcedimientoEnfermeriaPor[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oPro = new ProcedimientoEnfermeriaPor();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPro.setClavePor(((Double)vRowTemp.get(0)).intValue());
                            oPro.setDescripcion((String)vRowTemp.get(1));
                            arrRet[i]=oPro;
			}
                    
		} 
		return arrRet; 
	}          
    
    
    public int getClavePor() {
        return nClavePor;
    }

   
    public void setClavePor(int sClavePor) {
        this.nClavePor = sClavePor;
    }

    
    public String getDescripcion() {
        return sDescripcion;
    }

    
    public void setDescripcion(String sDescripcion) {
        this.sDescripcion = sDescripcion;
    }

    
    public String getAbreviacion() {
        return sAbreviacion;
    }

    
    public void setAbreviacion(String sAbreviacion) {
        this.sAbreviacion = sAbreviacion;
    }

    
    public Parametrizacion getProcPor() {
        return oProcPor;
    }

    
    public void setProcPor(Parametrizacion oProcPor) {
        this.oProcPor = oProcPor;
    }
    
    
}

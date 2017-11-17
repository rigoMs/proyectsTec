 
package mx.gob.hrrb.modelo.trabajosocial; 

import java.io.Serializable;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

public class EgresoFamiliarCapturado implements Serializable {
   private BigDecimal nMonto;
   private AccesoDatos oAD;
   private Parametrizacion oParametrizacion;
   private NivelSocioEconomico oNivelSocioEconomico;
   
public EgresoFamiliarCapturado(){
    nMonto= new BigDecimal("0.00");
    oParametrizacion=new Parametrizacion();
}

public EgresoFamiliarCapturado[] buscarMontosDeEgresoFamil(long folipac, String fechaReg) throws Exception{
        EgresoFamiliarCapturado arrRet[]=null, oFD=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaresptrabsocegrefam("+folipac+"::BIGINT, '"+fechaReg+"'::DATE);";
                oAD=new AccesoDatos(); 
                System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new EgresoFamiliarCapturado[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oFD=new EgresoFamiliarCapturado(); 
                            ArrayList vRowTemp = (ArrayList)rst.get(i); 
                            oFD.setMonto(BigDecimal.valueOf(((Double) vRowTemp.get(1)).intValue()));
                            arrRet[i]=oFD;
			} 
		} 
		return arrRet; 
    }

 


   public String  insertaresptrabsocegrefam() throws Exception{
        String sQuery = "";
        SimpleDateFormat SF = new SimpleDateFormat("yyyy-MM-dd");
        Date now = new Date();
        String strDate = SF.format(now);
        if(this==null){
	throw new Exception("FoliosDisponibles.Modificar: error, faltan datos");}
        else{
            sQuery = "select * from insertaresptrabsocegrefam ('"+oNivelSocioEconomico.getOtrabSocCapt().getIdUsuario()+"'::character varying,"+oNivelSocioEconomico.getPac().getFolioPaciente()+",'"+strDate+"'::date,'TA6'::character(3),'"+oParametrizacion.getTipoParametro()+"'::character(3),"+this.getMonto()+"::numeric);"; 
        }
        return sQuery;
    } 

    public BigDecimal getMonto() {
        return nMonto;
    }

    public void setMonto(BigDecimal nMonto) {
        this.nMonto = nMonto;
    }

    public Parametrizacion getParametrizacion() {
        return oParametrizacion;
    }

    public void setParametrizacion(Parametrizacion oParametrizacion) {
        this.oParametrizacion = oParametrizacion;
    }

    public NivelSocioEconomico getNivelSocioEconomico() {
        return oNivelSocioEconomico;
    }

    public void setNivelSocioEconomico(NivelSocioEconomico oNivelSocioEconomico) {
        this.oNivelSocioEconomico = oNivelSocioEconomico;
    }


    
    
}

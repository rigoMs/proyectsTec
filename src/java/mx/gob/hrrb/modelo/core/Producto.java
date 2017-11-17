package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.notas.SegundaMitadEmbarazo;
import mx.gob.hrrb.modelo.core.notas.TerminacionEmbarazo;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Producto implements Serializable{
	private AccesoDatos oAD;
	private boolean bEgresado;
	private String sReanimacionNeonatal;
	private int nApgar5Min;
	private int nEstanciaEnCunero;
	private int nPesoAlNacer;
	private Parametrizacion oSexoProducto;
	private String sCondicion;
	private char sCondicionNacimiento;
	private String sFolioCertificadoNac;
	private AtencionObstetrica oAtencionObstetrica;
        private String sSexoProductoP;
/**********************INICIAN ATRIBUTOS AGREGADOS POR ALBERTO***************************/
        private String sEstadoNacimiento;
        private TerminacionEmbarazo oTerminacionEmbarazo;
        private Date dFechaNacimiento;        
        private int nApgar1Min;
        private String sMalformaciones;
        private boolean bKristeller;
        private boolean bTraumatismObstetrico;
        private String sObservaciones;
        private int nnumingresohosp;
        private int nconsecutivoproducto;
        private String sNombreRn;
/**********************TERMINAN ATRIBUTOS AGREGADOS POR ALBERTO***************************/
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Producto.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveProducto();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
				bRet = true;
			}
		} 
		return bRet; 
	} 
	public Producto[] buscarTodos() throws Exception{
	Producto arrRet[]=null, oProducto=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosProducto();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Producto[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Producto.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaProducto();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)rst.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	} 
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Producto.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaProducto();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)rst.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	} 
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Producto.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaProducto();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)rst.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	}
//******* METODO QUE TRAE EL DETALLE DEL PRODUCTO CREADO POR ALBERTO
        public ArrayList<Producto> detalleProducto(short opc)throws Exception{
            ArrayList<Producto> arrProducto = null;
            Producto oProducto = null;
            ArrayList rst = null;
            String sQuery = null;
            
            if(this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() == 0 || this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() == 0)
                throw new Exception("NO ES POSIBLE PROCESAR LA INFORMACION");
            else{
                sQuery = "SELECT  * FROM buscadetalleproductopartograma(" + this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getFolioPaciente() + "::BIGINT," +
                        this.getTerminacionEmbarazo().getPartoGrama().getEpiMed().getPaciente().getClaveEpisodio() + "::BIGINT,"+
                        this.getTerminacionEmbarazo().getPartoGrama().getConsecutivo() + "::SMALLINT," + 
                        this.getTerminacionEmbarazo().getPartoGrama().getNpartograma() + "::BIGINT);";                
                
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null){
                    if(opc == 1){                        
                        arrProducto = new ArrayList<Producto>();
                        ArrayList vRowTemp = null;
                        for(int i = 0; i < rst.size(); i++){
                            oProducto = new Producto();
                            vRowTemp = (ArrayList)rst.get(i);
                            oProducto.setFechaNacimiento((Date)vRowTemp.get(0));
                            oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
                            oProducto.getTerminacionEmbarazo().getPartoGrama().setSegundaMitadEmbarazo(new SegundaMitadEmbarazo());
                            oProducto.getTerminacionEmbarazo().getPartoGrama().getSegundaMitadEmbarazo().setSemanasAmerorrea(((Double)vRowTemp.get(1)).intValue());
                            oProducto.setEstadoNacimiento((String)vRowTemp.get(2).toString());
                            oProducto.setSexoProductoP((String)vRowTemp.get(3).toString());
                            oProducto.setApgar1Min(((Double)vRowTemp.get(4)).intValue());
                            oProducto.setApgar5Min(((Double)vRowTemp.get(5)).intValue());
                            oProducto.setPesoAlNacer(((Double)vRowTemp.get(6)).intValue());
                            oProducto.setMalformaciones((String)vRowTemp.get(7).toString());
                            oProducto.setKristeller(vRowTemp.get(8).toString().compareTo("1") == 0);
                            oProducto.setTraumatismObstetrico(vRowTemp.get(9).toString().compareTo("1") == 0);
                            String reanimacion = vRowTemp.get(10).toString().compareTo("1") == 0 ? "SÍ" : "NO";
                            oProducto.setReanimacionNeonatal(reanimacion);
                            oProducto.setObservaciones((String)vRowTemp.get(11).toString());
                            oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(12).toString());
                            String nombres = (String)vRowTemp.get(13).toString() + " " +  (String)vRowTemp.get(14).toString() + " " + (String)vRowTemp.get(15).toString();
                            oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres(nombres);
                            arrProducto.add(oProducto);
                        }                    
                    }else{
                        if(opc == 0){                            
                            arrProducto = new ArrayList<Producto>();
                            ArrayList vRowTemp = null;
                            for(int i = 0; i < rst.size(); i++){
                                oProducto = new Producto();
                                vRowTemp = (ArrayList)rst.get(i);
                                oProducto.setFechaNacimiento((Date)vRowTemp.get(0));
                                oProducto.setTerminacionEmbarazo(new TerminacionEmbarazo());
                                oProducto.setSexoProducto(new Parametrizacion());
                                oProducto.getTerminacionEmbarazo().getPartoGrama().setSegundaMitadEmbarazo(new SegundaMitadEmbarazo());
                                oProducto.getTerminacionEmbarazo().getPartoGrama().getSegundaMitadEmbarazo().setSemanasAmerorrea(((Double)vRowTemp.get(1)).intValue());
                                String estado = (String)vRowTemp.get(16).toString() + "" + (String)vRowTemp.get(17).toString();
                                oProducto.setEstadoNacimiento(estado);
                                String sexo = (String)vRowTemp.get(18).toString() + "" + (String)vRowTemp.get(19).toString();
                                oProducto.getSexoProducto().setClaveParametro(sexo);
                                oProducto.setSexoProductoP((String)vRowTemp.get(3).toString());
                                oProducto.setApgar1Min(((Double)vRowTemp.get(4)).intValue());
                                oProducto.setApgar5Min(((Double)vRowTemp.get(5)).intValue());
                                oProducto.setPesoAlNacer(((Double)vRowTemp.get(6)).intValue());
                                oProducto.setMalformaciones((String)vRowTemp.get(7).toString());
                                oProducto.setKristeller(vRowTemp.get(8).toString().compareTo("1") == 0);
                                oProducto.setTraumatismObstetrico(vRowTemp.get(9).toString().compareTo("1") == 0);
                                String reanimacion = vRowTemp.get(10).toString().compareTo("1") == 0 ? "SÍ" : "NO";
                                oProducto.setReanimacionNeonatal(reanimacion);
                                oProducto.setObservaciones((String)vRowTemp.get(11).toString());
                                oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setCedProf((String)vRowTemp.get(12).toString());
                                String nombres = (String)vRowTemp.get(13).toString() + " " +  (String)vRowTemp.get(14).toString() + " " + (String)vRowTemp.get(15).toString();
                                oProducto.getTerminacionEmbarazo().getPartoGrama().getMedicoSupervisor().setNombres(nombres);
                                oProducto.getTerminacionEmbarazo().getPartoGrama().setConsecutivo(((Double)vRowTemp.get(21)).intValue());
                                arrProducto.add(oProducto);
                            }
                        }
                    }
                }
            }
            return arrProducto;
        }
	public boolean getEgresado() {
	return bEgresado;
	}

	public void setEgresado(boolean valor) {
	bEgresado=valor;
	}

	public String getReanimacionNeonatal() {
	return sReanimacionNeonatal;
	}

	public void setReanimacionNeonatal(String valor) {
	sReanimacionNeonatal=valor;
	}

	public int getApgar5Min() {
	return nApgar5Min;
	}

	public void setApgar5Min(int valor) {
	nApgar5Min=valor;
	}

	public int getEstanciaEnCunero() {
	return nEstanciaEnCunero;
	}

	public void setEstanciaEnCunero(int valor) {
	nEstanciaEnCunero=valor;
	}

	public int getPesoAlNacer() {
	return nPesoAlNacer;
	}

	public void setPesoAlNacer(int valor) {
	nPesoAlNacer=valor;
	}

	public Parametrizacion getSexoProducto() {
	return oSexoProducto;
	}

	public void setSexoProducto(Parametrizacion valor) {
	oSexoProducto=valor;
	}
        
	public String getCondicion() {
	return sCondicion;
	}

	public void setCondicion(String valor) {
	sCondicion=valor;
	}

	public char getCondicionNacimiento() {
	return sCondicionNacimiento;
	}

	public void setCondicionNacimiento(char valor) {
	sCondicionNacimiento=valor;
	}

	public String getFolioCertificadoNac() {
	return sFolioCertificadoNac;
	}

	public void setFolioCertificadoNac(String valor) {
	sFolioCertificadoNac=valor;
	}

	public AtencionObstetrica getAtencionObstetrica() {
	return oAtencionObstetrica;
	}

	public void setAtencionObstetrica(AtencionObstetrica valor) {
	oAtencionObstetrica=valor;
	}
        
        public String getSexoProductoP() {
        return sSexoProductoP;
    }

    public void setSexoProductoP(String sSexoProductoP) {
        this.sSexoProductoP = sSexoProductoP;
    }
    public Date getFechaNacimiento(){
        return dFechaNacimiento;
    }
    public void setFechaNacimiento(Date dFechaNacimiento){
        this.dFechaNacimiento = dFechaNacimiento;
    }
    public int getApgar1Min(){
        return nApgar1Min;
    }
    public void setApgar1Min(int nApgar1Min){
        this.nApgar1Min = nApgar1Min;
    }
    public TerminacionEmbarazo getTerminacionEmbarazo(){
        return oTerminacionEmbarazo;
    }
    public void setTerminacionEmbarazo(TerminacionEmbarazo oTerminacionEmbarazo){
        this.oTerminacionEmbarazo = oTerminacionEmbarazo;
    }
    public String getMalformaciones(){
        return sMalformaciones;
    }
    public void setMalformaciones(String sMalformaciones){
        this.sMalformaciones = sMalformaciones;
    }
    public boolean getKristeller(){
        return bKristeller;
    }
    public void setKristeller(boolean bKristeller){
        this.bKristeller = bKristeller;
    }
    public boolean getTraumatismObstetrico(){
        return bTraumatismObstetrico;
    }
    public void setTraumatismObstetrico(boolean bTraumatismObstetrico){
        this.bTraumatismObstetrico = bTraumatismObstetrico;
    }
    public String getObservaciones(){
        return sObservaciones;
    }
    public void setObservaciones(String sObservaciones){
        this.sObservaciones = sObservaciones;
    }
    public boolean getReanimacion(){
        return !(this.sReanimacionNeonatal== null || this.sReanimacionNeonatal.equals(""));
    }
    public String getEstadoNacimiento(){
        return sEstadoNacimiento;
    }
    public void setEstadoNacimiento(String sEstadoNacimiento){
        this.sEstadoNacimiento = sEstadoNacimiento;
    }
    public int getNumeroIngresoHospitalario(){
        return nnumingresohosp;
    }
    public void setNumeroIngresoHospitalario(int nnumingresohosp){
        this.nnumingresohosp = nnumingresohosp;
    }
    public int getConsecutivoProducto(){
        return nconsecutivoproducto;
    }
    public void setConsecutivoProducto(int nconsecutivoproducto){
        this.nconsecutivoproducto = nconsecutivoproducto;
    }
    public String getNombreProducto(){
        return sNombreRn;
    }
    public void setNombreProducto(String sNombreRn){
        this.sNombreRn = sNombreRn;
    }
    public String getValorKristeller(){
        return getKristeller() ? "SI(X) NO( )" : "SI( ) NO(X)"; 
    }
    public String getValorTraumatismo(){
        return getTraumatismObstetrico() ? "SI(X) NO( )" : "SI( ) NO(X)";
    }
    public String getValorReanimacion(){
        return getReanimacion() ? "SI(X) NO( )" : "SI( ) NO(X)";
    }
    public String getValorEstadoNacimiento(){        
        if(this.getEstadoNacimiento() == null || this.getEstadoNacimiento().isEmpty())
            return null;
        else{
            if(this.getEstadoNacimiento().substring(0, 5).compareTo("TAU01") == 0)
                return "VIVO";
            if(this.getEstadoNacimiento().substring(0, 5).compareTo("TAU02") == 0)
                return "MUERTO";
            if(this.getEstadoNacimiento().substring(0, 5).compareTo("TAU03") == 0)
                return "ÓBITO";
            if(this.getEstadoNacimiento().substring(0, 5).compareTo("TAU04") == 0)
                return "INTRAPARTO";
            if(this.getEstadoNacimiento().substring(0, 5).compareTo("TAU05") == 0)
                return "MACERADO";
        }
        return null;
    }
    public String getValorSexo(){        
        if(getSexoProducto().getClaveParametro() == null || getSexoProducto().getClaveParametro().isEmpty())
            return null;
        if(getSexoProducto().getClaveParametro().substring(0, 5).compareTo("T0901") == 0)
            return "MASCULINO";
        if(getSexoProducto().getClaveParametro().substring(0, 5).compareTo("T0902") == 0)
            return "FEMENINO";
        if(getSexoProducto().getClaveParametro().substring(0, 5).compareTo("T0909") == 0)
            return "NO ESPECIFICADO";
        return null;
    }
    public String getCadenaEstadoNacimiento(){
        if(this.getEstadoNacimiento().compareTo("VIVO") == 0)
            return "VIVO(X) MUERTO( ) ÓBITO( ) INTRAPARTO( ) MACERADO( )";
        if(this.getEstadoNacimiento().compareTo("MUERTO") == 0)
            return "VIVO( ) MUERTO(X) ÓBITO( ) INTRAPARTO( ) MACERADO( )";
        if(this.getEstadoNacimiento().compareTo("ÓBITO") == 0)
            return "VIVO( ) MUERTO( ) ÓBITO(X) INTRAPARTO( ) MACERADO( )";
        if(this.getEstadoNacimiento().compareTo("INTRAPARTO") == 0)
            return "VIVO( ) MUERTO( ) ÓBITO( ) INTRAPARTO(X) MACERADO( )";
        if(this.getEstadoNacimiento().compareTo("MACERADO") == 0)
            return "VIVO( ) MUERTO( ) ÓBITO( ) INTRAPARTO( ) MACERADO(X)";
        return "VIVO( ) MUERTO( ) ÓBITO( ) INTRAPARTO( ) MACERADO(X)";
    }
    public String getCadenaSexo(){
        return this.getSexoProductoP().compareTo("FEMENINO") == 0 ? "MASCULINO( ) FEMENINO(X)" : "MASCULINO(X) FEMENINO( )";
    }
} 

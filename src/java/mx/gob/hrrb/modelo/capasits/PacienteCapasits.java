package mx.gob.hrrb.modelo.capasits;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Etnicidad;
import mx.gob.hrrb.modelo.core.Expediente;
import mx.gob.hrrb.modelo.core.Paciente;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.core.Seguro;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class PacienteCapasits extends Paciente implements Serializable{
	private AccesoDatos oAD;
	private String arrObservaciones;
	private int nCodigoBarras;
	private long nIdNacional;
	private ArrayList arrCitaTrabajoSocialCapasits;
	private ArrayList arrCitaPsicologiaCapasits;
	private ArrayList arrCitaOdontologiaCapasits;
	private ArrayList arrCitaMedicinaGeneralCapasits;
        private Date dPrimerFechaIngreso;
        private boolean bReferidoHrrb;
        private Seguro segu=null;
        private Etnicidad etni=null;
        private Expediente expe=null;
        private Parametrizacion oPara;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private Cita oCit;
        private String disc="";
        private String referido="";

    
    public PacienteCapasits() {
        super();
        segu=new Seguro();
        etni= new Etnicidad();
        expe=new Expediente();
        oPara=new Parametrizacion();
        
        
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
    }


        @Override
    public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePacienteCapasits("+this.getIdNacional()+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
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
    
     public boolean buscarIdNacional() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "", tiposeg="";
		 if( this.getIdNacional()==0){   //completar llave
			throw new Exception("PacienteCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaIdNacional("+this.getIdNacional()+");"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                            this.setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                            this.setIdNacional(((Double)vRowTemp.get(1)).intValue());
                            this.setPrimerFechaIngreso((Date)vRowTemp.get(2));
                            this.setNombres((String)vRowTemp.get(3));
                            this.setApPaterno((String)vRowTemp.get(4));
                            this.setApMaterno((String)vRowTemp.get(5));
                            this.setFechaNac((Date)vRowTemp.get(6));
                            this.setSexos((String)vRowTemp.get(7));
                            this.getExp().setNumero(((Double)vRowTemp.get(8)).intValue());
                            this.getSeg().setNumero((String)vRowTemp.get(9));
                            this.getParam().setValor((String)vRowTemp.get(10));
                            this.getSeg().setVigencia((Date)vRowTemp.get(11));
                            this.disc=(String)vRowTemp.get(12);
                            this.getEtnic().setPerteneceGpoInd((String)vRowTemp.get(13));
                            tiposeg=(String)vRowTemp.get(14);
				bRet = true;
			}
		} 
                 if(disc.equals("S")) setDiscapacitado(true);
                 if(!this.getSeg().getTipoSeguro().equals("08")) 
                     this.getSeg().setTipoSeguro(tiposeg.substring(3,5));
		return bRet; 
	}   
        
        public boolean buscarExpe() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = ""; System.out.println(getExp().getNumero());
		 if(getExp().getNumero()<0){   //completar llave
			throw new Exception("Expediente.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaExpedienteH("+this.getExp().getNumero()+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.getExp().setNumero(((Double)vRowTemp.get(0)).intValue());
                                this.setFolioPaciente(((Double)vRowTemp.get(1)).intValue());
                                this.setNombres((String)vRowTemp.get(2));
                                this.setApPaterno((String)vRowTemp.get(3));
                                this.setApMaterno((String)vRowTemp.get(4));
                                this.setFechaNac((Date)vRowTemp.get(5));
                                this.setSexos((String)vRowTemp.get(6));
                                this.getSeg().setNumero((String)vRowTemp.get(7));
                                this.getParam().setValor((String)vRowTemp.get(8));
                                this.getSeg().setVigencia((Date)vRowTemp.get(9));
                                this.getSeg().setDerechohabienteP((String)vRowTemp.get(10));
				bRet = true;
			}
		} 
		return bRet; 
	}
        
        public boolean buscarIdNacionalCapa() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "", tiposeg="";
		 if( this.getIdNacional()==0){   //completar llave
			throw new Exception("PacienteCapasits.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscapacientecapasitsclave("+this.getIdNacional()+");"; 
                        System.out.println(sQuery);
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                this.setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                            this.setIdNacional(((Double)vRowTemp.get(1)).intValue());
                            this.setPrimerFechaIngreso((Date)vRowTemp.get(2));
                            this.setNombres((String)vRowTemp.get(3));
                            this.setApPaterno((String)vRowTemp.get(4));
                            this.setApMaterno((String)vRowTemp.get(5));
                            this.setFechaNac((Date)vRowTemp.get(6));
                            this.setSexos((String)vRowTemp.get(7));
                            setHospitalizado(((Double)vRowTemp.get(8)).intValue());
                            
				bRet = true;
			}
		} 
                 if(disc.equals("S")) setDiscapacitado(true);
                 if(!this.getSeg().getTipoSeguro().equals("08")) 
                     this.getSeg().setTipoSeguro(tiposeg.substring(3,5));
		return bRet; 
	} 
     
        public PacienteCapasits[] buscarVigenciaSPaciente() throws Exception{
	PacienteCapasits arrRet[]=null, oPacienteCapasits=null;
	ArrayList rst = null;
         ArrayList<PacienteCapasits> vObj=null;
	String sQuery = "";
	int i=0,nTam=0;
		sQuery = "SELECT * FROM buscaVigenciaPacienteCapasits("+this.getIdNacional()+");"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<PacienteCapasits>();
			for (i = 0; i < rst.size(); i++) {
                            oPacienteCapasits= new PacienteCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPacienteCapasits.setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                            oPacienteCapasits.setIdNacional(((Double)vRowTemp.get(1)).intValue());
                            oPacienteCapasits.setNombres((String)vRowTemp.get(2));
                            oPacienteCapasits.setApPaterno((String)vRowTemp.get(3));
                            oPacienteCapasits.setApMaterno((String)vRowTemp.get(4));
                            oPacienteCapasits.getSeg().setNumero((String)vRowTemp.get(5));
                            oPacienteCapasits.getSeg().setVigencia((Date)vRowTemp.get(6));
                            vObj.add(oPacienteCapasits);   
			} 
                        nTam = vObj.size();
                    arrRet = new PacienteCapasits[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}
        
        public PacienteCapasits[] buscarTodosVigenciaSPaciente() throws Exception{
	PacienteCapasits arrRet[]=null, oPacienteCapasits=null;
	ArrayList rst = null;
         ArrayList<PacienteCapasits> vObj=null;
	String sQuery = "";
	int i=0,nTam=0;
		sQuery = "SELECT * FROM buscaTodosVigenciaPacienteCapasits();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<PacienteCapasits>();
			for (i = 0; i < rst.size(); i++) {
                            oPacienteCapasits= new PacienteCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPacienteCapasits.setFolioPaciente(((Double)vRowTemp.get(0)).intValue());
                            oPacienteCapasits.setIdNacional(((Double)vRowTemp.get(1)).intValue());
                            oPacienteCapasits.setNombres((String)vRowTemp.get(2));
                            oPacienteCapasits.setApPaterno((String)vRowTemp.get(3));
                            oPacienteCapasits.setApMaterno((String)vRowTemp.get(4));
                            oPacienteCapasits.getSeg().setNumero((String)vRowTemp.get(5));
                            oPacienteCapasits.getSeg().setVigencia((Date)vRowTemp.get(6));
                            vObj.add(oPacienteCapasits);   
			} 
                        nTam = vObj.size();
                    arrRet = new PacienteCapasits[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}
     
        @Override
	public PacienteCapasits[] buscarTodos() throws Exception{
	PacienteCapasits arrRet[]=null, oPacienteCapasits=null;
	ArrayList rst = null;
         ArrayList<PacienteCapasits> vObj=null;
	String sQuery = "";
	int i=0,nTam=0;
		sQuery = "SELECT * FROM buscaTodosPacienteCapasits();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<PacienteCapasits>();
			for (i = 0; i < rst.size(); i++) {
                            oPacienteCapasits= new PacienteCapasits();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPacienteCapasits.setCodigoBarras(((Double)vRowTemp.get(0)).intValue());
                            oPacienteCapasits.setIdNacional(((Long)vRowTemp.get(1)).intValue());
                            oPacienteCapasits.setPrimerFechaIngreso((Date)vRowTemp.get(2));
                            oPacienteCapasits.setNombres((String)vRowTemp.get(3));
                            oPacienteCapasits.setApPaterno((String)vRowTemp.get(4));
                            oPacienteCapasits.setApMaterno((String)vRowTemp.get(5));
                            oPacienteCapasits.setFechaNac((Date)vRowTemp.get(6));
                            oPacienteCapasits.setSexos((String)vRowTemp.get(7));
                            oPacienteCapasits.getExpediente().setNumero(((Double)vRowTemp.get(8)).intValue());
                            oPacienteCapasits.getSeg().setNumero((String)vRowTemp.get(9));
                            oPacienteCapasits.getParam().setValor((String)vRowTemp.get(10));
                            vObj.add(oPacienteCapasits);   
			} 
                        nTam = vObj.size();
                    arrRet = new PacienteCapasits[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                        System.out.println(arrRet[i].getCodigoBarras());
                    }
		} 
		return arrRet; 
	} 
        
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
         if(this.getDiscapacitado())
             disc="S";
         else
             disc="N";
         if(this.getReferidoHrrb())
             referido="S"; else referido="N";
		 if( this.nIdNacional==0){   //completar llave
			throw new Exception("PacienteCapasits.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT *  from insertapacientecapasitscompleto('"+sUsuario+"','"+this.getNombres()+"','"+this.getApPaterno()+"','"
                                +this.getApMaterno()+"','"+this.getFechaNac()+"','"+this.getSexos()+"','"+disc+"','"+etni.getPerteneceGpoInd()+"',"
                                +this.getIdNacional()+","+this.getCodigoBarras()+",'"+referido+"','"+this.getPrimerFechaIngreso()+"','"+segu.getNumero()+"',"
                                + (segu.getVigencia()==null?"null":"'"+segu.getVigencia()+"'")+",'"+segu.getTipoSeguro()+"',"+expe.getNumero()+");"; 
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
        
        @Override
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if(this.getDiscapacitado())
             disc="S";
         else
             disc="N";
         if(this.getReferidoHrrb())
             referido="S"; else referido="N";
		 if( this.nIdNacional==0){   //completar llave
			throw new Exception("PacienteCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPacienteCapasits("+getFolioPaciente()+",'"+sUsuario+"','"+this.getNombres()+"','"+this.getApPaterno()+"','"
                                +this.getApMaterno()+"','"+this.getFechaNac()+"','"+this.getSexos()+"','"+disc+"','"+etni.getPerteneceGpoInd()+"',"
                                +this.getIdNacional()+","+this.getCodigoBarras()+",'"+referido+"','"+this.getPrimerFechaIngreso()+"','"+segu.getNumero()+"',"
                                + (segu.getVigencia()==null?"null":"'"+segu.getVigencia()+"'")+",'"+segu.getTipoSeguro()+"',"+expe.getNumero()+");"; 
			oAD=new AccesoDatos();  System.out.println(sQuery);
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
        
        public int modificarPacH() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
             referido="S"; 
		 if( this.nIdNacional==0){   //completar llave
			throw new Exception("PacienteCapasits.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPacH('"+sUsuario+"',"+getFolioPaciente()+","+this.getIdNacional()+",'"+referido+"','"+this.getPrimerFechaIngreso()+"','"+segu.getNumero()+"',"
                                + (segu.getVigencia()==null?"null":"'"+segu.getVigencia()+"'")+",'"+segu.getTipoSeguro()+"');"; 
			oAD=new AccesoDatos();  System.out.println(sQuery);
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
        
        public int modificarVigenciaPaciente() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Seguro.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaVigenciaPaciente('"+sUsuario+"',"+getFolioPaciente()+","+(segu.getVigencia()==null?"null":"'"+segu.getVigencia()+"'")+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ System.out.println(sQuery);
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
        
        @Override
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("PacienteCapasits.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPacienteCapasits();"; 
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
	public String getrrObservaciones() {
	return arrObservaciones;
	}

	public void setrrObservaciones(String valor) {
	arrObservaciones=valor;
	}
        
        public Date getPrimerFechaIngreso() {
            return dPrimerFechaIngreso;
        }
        
        public void setPrimerFechaIngreso(Date valor){
            this.dPrimerFechaIngreso=valor;
        }

	public int getCodigoBarras() {
	return nCodigoBarras;
	}

	public void setCodigoBarras(int valor) {
	nCodigoBarras=valor;
	}

	public long getIdNacional() {
	return nIdNacional;
	}

	public void setIdNacional(long valor) {
	nIdNacional=valor;
	}

	public ArrayList getrrCitaTrabajoSocialCapasits() {
	return arrCitaTrabajoSocialCapasits;
	}

	public void setrrCitaTrabajoSocialCapasits(ArrayList valor) {
	arrCitaTrabajoSocialCapasits=valor;
	}

	public ArrayList getrrCitaPsicologiaCapasits() {
	return arrCitaPsicologiaCapasits;
	}

	public void setrrCitaPsicologiaCapasits(ArrayList valor) {
	arrCitaPsicologiaCapasits=valor;
	}

	public ArrayList getrrCitaOdontologiaCapasits() {
	return arrCitaOdontologiaCapasits;
	}

	public void setrrCitaOdontologiaCapasits(ArrayList valor) {
	arrCitaOdontologiaCapasits=valor;
	}

	public ArrayList getrrCitaMedicinaGeneralCapasits() {
	return arrCitaMedicinaGeneralCapasits;
	}

	public void setrrCitaMedicinaGeneralCapasits(ArrayList valor) {
	arrCitaMedicinaGeneralCapasits=valor;
	}
        
        public boolean getReferidoHrrb(){
            return bReferidoHrrb;
        }
        
        public void setReferidoHrrb(boolean valor){
            bReferidoHrrb=valor;
        }
        
        public Etnicidad getEtnic(){
            return etni;
        }
        public void setEtnic(Etnicidad valor){
            etni=valor;
        }
        
        public Expediente getExp(){
            return expe;
        }
        public void setExp(Expediente valor){
            expe=valor;
        }
        
         public Parametrizacion getParam(){
            return oPara;
        }
        public void setParam(Parametrizacion valor){
            oPara=valor;
        }
        
        public Cita getCit(){
            return oCit;
        }
        
        public void setCit(Cita valor){
            oCit=valor;
        }
    
} 

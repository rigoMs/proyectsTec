package mx.gob.hrrb.modelo.consultaexterna;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Permiso implements Serializable{
	private AccesoDatos oAD;
	private Date dFechaFin;
	private Date dFechaIni;
        private Date dnewFechaIni;
        private Date dnewFechaFin;
        private String sHoras2;
	private PersonalHospitalario oPersonalHospitalario;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private FacesMessage facesMessage;
        private String sHoras;
        private String sHini;
        private String sHfin;
        private Date dActual;
        
        public Permiso(){
            oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
        oPersonalHospitalario=new PersonalHospitalario();
        sHoras="";
        sHini="";
        sHfin="";
        dActual=new Date();
        }

	public boolean buscar(String[] tarj, Date cita) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        int tarjeta=Integer.parseInt(tarj[0]);
		 if( this==null){   //completar llave
			throw new Exception("Permiso.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePermisos("+tarjeta+");"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
                                for (int i=0; i<rst.size(); i++){
                                ArrayList vRowTemp = (ArrayList)rst.get(i);
                                bRet=buscaPermisoMedico(bRet, (Date)vRowTemp.get(1), (Date)vRowTemp.get(2), cita);
                                
                                }

			}
		} 
		return bRet; 
	}
        
        //**********************************************************************
        public boolean buscaPermisoMedico(boolean bRet, Date fechaIni, Date fechaFin, Date cita) throws Exception{
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
            
            String f1=fechaIni.toString();
            String f2=fechaFin.toString();
            String fecCita=df.format(cita);
            if (fecCita.compareTo(f1)>=0 && fecCita.compareTo(f2)<=0)
                bRet=true;
            return bRet;
        } 
        //**********************************************************************
        public int buscarPermisoHora(String tarjeta, Date cita) throws Exception{
	int bRet = 0;
	ArrayList rst = null;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Permiso.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscarPermisoHora("+tarjeta+", '"+df.format(cita)+"%');";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                bRet=((Double)vRowTemp.get(0)).intValue();
			}
		} 
		return bRet; 
	}
        //**********************************************************************
        public int buscarPermisoExistente(int tarjeta, Date fini, Date ffin) throws Exception{
	int bRet = 0;
	ArrayList rst = null;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Permiso.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaPermisoExistente("+tarjeta+", '"+fini+"'::date, '"+ffin+"'::date);";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                bRet=((Double)vRowTemp.get(0)).intValue();
			}
		} 
		return bRet; 
	}
        //**********************************************************************
        public int PermisoExistenteMod(int tarjeta, Date fini, Date ffin, String inicial) throws Exception{
	int bRet = 0;
	ArrayList rst = null;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Permiso.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM PermisoExistenteMod("+tarjeta+", '"+fini+"'::date, '"+ffin+"'::date, '"+inicial+"'::date);";
                        oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
                                ArrayList vRowTemp = (ArrayList)rst.get(0);
                                bRet=((Double)vRowTemp.get(0)).intValue();
			}
		} 
		return bRet; 
	}
        //***********************************************************************
	public Permiso[] buscarTodos() throws Exception{
	Permiso arrRet[]=null, oPermiso=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPermiso();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Permiso[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        //***************************************************************
        public Permiso[] buscarPermisosAux() throws Exception{
	Permiso arrRet[]=null, oPermiso=null;
	ArrayList rst = null;
	String sQuery = "";
        ArrayList<Permiso> vObj=null;
	int i=0, nTam=0, h1, m1, h2, m2;
        String hrs="";
		sQuery = "SELECT * FROM buscaPermisosAux();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                    vObj = new ArrayList<Permiso>();
			for (i = 0; i < rst.size(); i++) {
                            oPermiso=new Permiso();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPermiso.getPersonalHospitalario().setNoTarjeta(((Double)vRowTemp.get(0)).intValue());
                            oPermiso.getPersonalHospitalario().setApPaterno((String)vRowTemp.get(1));
                            oPermiso.getPersonalHospitalario().setApMaterno((String)vRowTemp.get(2));
                            oPermiso.getPersonalHospitalario().setNombres((String)vRowTemp.get(3));
                            oPermiso.setFechaIni((Date)vRowTemp.get(4));
                            oPermiso.setFechaFin((Date)vRowTemp.get(5));
                            
                            h1=((Double)vRowTemp.get(6)).intValue();
                            m1=((Double)vRowTemp.get(7)).intValue();
                            h2=((Double)vRowTemp.get(8)).intValue();
                            m2=((Double)vRowTemp.get(9)).intValue();
                            if (h1==0 && h2==0 && oPermiso.getFechaIni().toString().compareTo(oPermiso.getFechaFin().toString())==0)
                                oPermiso.setHoras("DÍA COMPLETO");
                            if (h1==0 && h2==0 && oPermiso.getFechaIni().toString().compareTo(oPermiso.getFechaFin().toString())!=0)
                                oPermiso.setHoras("PERIODO");
                            if (h1>0 && h2>0){
                                if (h1<10) hrs="0"+h1; else hrs=h1+"";
                                if (m1<10) hrs+=":0"+m1; else hrs+=":"+m1;
                                hrs+="  -  ";
                                if (h2<10) hrs+="0"+h2; else hrs+=h2+"";
                                if (m2<10) hrs+=":0"+m2; else hrs+=":"+m2;
                                oPermiso.setHoras(hrs);
                            }
                            String vh1[];
                                vh1=oPermiso.getFechaIni().toString().split("-");
                                oPermiso.setHini(vh1[2]+"/"+vh1[1]+"/"+vh1[0]);
                                vh1=oPermiso.getFechaFin().toString().split("-");
                                oPermiso.setHfin(vh1[2]+"/"+vh1[1]+"/"+vh1[0]);
                            vObj.add(oPermiso);
			}
                    nTam = vObj.size();
                    arrRet = new Permiso[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        //***************************************************************
	public int insertar(int tarjeta, String fechaIni, String fechaFin) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
        String sLlave=tarjeta+", "+fechaIni;
	String sQuery = "";
        String tipo="";

            if (fechaFin.endsWith("00:00:00")){
                if ((fechaIni+" 00:00:00").compareTo(fechaFin)==0)
                    tipo="2";
                else
                    tipo="1";
        }else
            tipo="3";
		 if( this==null){   //completar llave
			throw new Exception("Permiso.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPermisos('"+sUsuario+"', "+tarjeta+", '"+fechaIni+"', '"+fechaFin+"', '"+tipo+"', '"+sLlave+"');"; 
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
    public int modificar(String nuevaIni, String nuevaFin) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
        String tipo="";
	String sQuery = "";
        String fechaIni="";
        String sLlave="";
        if (getHoras().compareTo("PERIODO")==0 || getHoras().compareTo("DÍA COMPLETO")==0){
            fechaIni=getFechaIni()+" 00:00:00";
        }else{
            fechaIni=getFechaIni()+" "+getHoras().substring(0, 5)+":00";
        }
        if (nuevaIni.endsWith("00:00:00")){
            if (nuevaIni.substring(0, 10).compareTo(nuevaFin.substring(0, 10))==0)
                tipo="2";
            else
                tipo="1";
        }else
            tipo="3";

        sLlave=getPersonalHospitalario().getNoTarjeta()+", "+fechaIni;

		 if( this==null){   //completar llave
			throw new Exception("Permiso.modificar: error de programación, faltan datos");
		}else{ 
                sQuery = "SELECT * FROM modificaPermisos('"+sUsuario+"', "+getPersonalHospitalario().getNoTarjeta()+", '"+fechaIni+"', '"+nuevaIni+"', '"+nuevaFin+"', '"+sLlave+"', '"+tipo+"');"; 
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
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
        String tiempo="";
        String sLlave=getPersonalHospitalario().getNoTarjeta()+", "+getFechaIni();
        if (getHoras().compareTo("PERIODO")==0)tiempo=" 00:00:00";
        else{
            if (getHoras().compareTo("DÍA COMPLETO")==0)tiempo=" 00:00:00";
            else
                tiempo=" "+getHoras().substring(0, 5)+":00";
        }
           
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Permiso.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPermisos('"+sUsuario+"', "+getPersonalHospitalario().getNoTarjeta()+", '"+getFechaIni()+tiempo+"', '"+sLlave+"');"; 
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
	public Date getFechaFin() {
	return dFechaFin;
	}

	public void setFechaFin(Date valor) {
	dFechaFin=valor;
	}

	public Date getFechaIni() {
	return dFechaIni;
	}

	public void setFechaIni(Date valor) {
	dFechaIni=valor;
	}

	public PersonalHospitalario getPersonalHospitalario() {
	return oPersonalHospitalario;
	}

	public void setPersonalHospitalario(PersonalHospitalario valor) {
	oPersonalHospitalario=valor;
	}
        
        public void setHoras(String sHoras){
            this.sHoras=sHoras;
        }
        
        public String getHoras(){
            return sHoras;
        }

    public String getHini() {
        return sHini;
    }

    public void setHini(String sHini) {
        this.sHini = sHini;
    }

    public String getHfin() {
        return sHfin;
    }

    public void setHfin(String sHfin) {
        this.sHfin = sHfin;
    }
    
    public void setHoras2(String sHoras2){
            this.sHoras2=sHoras2;
    }
        
    public String getHoras2(){
            return sHoras2;
    }
    
    public Date getNewFechaIni() {
        return dnewFechaIni;
    }

    public void setNewFechaIni(Date dnewFechaIni) {
        this.dnewFechaIni = dnewFechaIni;
    }

    public Date getNewFechaFin() {
        return dnewFechaFin;
    }

    public void setNewFechaFin(Date dnewFechaFin) {
        this.dnewFechaFin = dnewFechaFin;
    }
    
    public Date getActual(){
        return dActual;
    }
    
    public void setActual(Date dActual){
        this.dActual=dActual;
    }
} 
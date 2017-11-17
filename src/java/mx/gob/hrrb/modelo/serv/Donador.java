package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.Paciente;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class Donador implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private Date dFechaDonacion;
private String nombreDonador;
private SolicitudSangre oSolicitudSangre;
private int nConsecutivo;

	public Donador(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Donador.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDonador();"; 
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
	public Donador[] buscarTodos() throws Exception{
	Donador arrRet[]=null, oDonador=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosDonador();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Donador[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Donador.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaDonador('"+sUsuarioFirmado+"');"; 
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
        
        public int insertaDonador(int nIdSolicitud) throws Exception{
            int i=0;
            ArrayList rst = null;
            String sQuery="";
            SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
            if(nIdSolicitud == 0){
                throw new Exception("Donador.insertaDonador: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM insertaDonadorPorSolicitud('"+ sUsuarioFirmado +"'::character varying,"+ nIdSolicitud +", " + getConsecutivo() +"::smallint, ";
                sQuery = sQuery + "'"+ getNombreDonador() +"'::character varying, '"+ oFec.format(getFechaDonacion()) +"'::Date);";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    
                    if(rst != null && rst.size()==1){
                        ArrayList vRowTemp = (ArrayList) rst.get(0);
                        i = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return i;
        }
        
        public int insertaDonadorAltruista() throws Exception{
            int i=0;
            ArrayList rst = null;
            String sQuery="";
            SimpleDateFormat oFec = new SimpleDateFormat("yyyy-MM-dd");
            if(getNombreDonador().equals("") && getFechaDonacion()==null){
                throw new Exception("Donador.instarDonadorAltruista: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM insertaDonadorAltruista('"+ sUsuarioFirmado +"'::character varying," + getConsecutivo() +"::smallint, '"+ getNombreDonador() +"'::character varying,  ";
                sQuery = sQuery + "'"+ oFec.format(getFechaDonacion()) +"'::Date);";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    
                    if(rst != null && rst.size()==1){
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        i=((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            
            return i;
        }
        
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Donador.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaDonador('"+sUsuarioFirmado+"');"; 
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
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Donador.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDonador('"+sUsuarioFirmado+"');"; 
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
    
    public Donador[] buscaNombreDonad(Paciente oPac, EpisodioMedico oEpi) 
            throws Exception{
    ArrayList rst = null;
    Donador arrRet[]=null, oNd=null;
    String sQuery = "";     
    int i=0,nTam=0;
    List<Donador> vObj=null;
        sQuery = "select * from buscaDonadoresSangre("+oPac.getFolioPaciente()+"::BIGINT,"+oEpi.getClaveEpisodio()+"::BIGINT);"; 
        oAD=new AccesoDatos();  
        if (oAD.conectar()){ 
            rst = oAD.ejecutarConsulta(sQuery); 
            oAD.desconectar(); 
        }
        if (rst != null) {
            vObj = new ArrayList<Donador>();
            for (i=0; i<rst.size(); i++){
                oNd=new  Donador();
                ArrayList<Object> vRowTemp = (ArrayList)rst.get(i); 
                oNd.setNombreDonador((String)vRowTemp.get(0));
                oNd.setFechaDonacion((Date)vRowTemp.get(1)); 
                vObj.add(oNd); 
            }
            nTam = vObj.size();
            arrRet = new Donador[nTam];
                    
            for (i=0; i<nTam; i++){
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
     
        
    public Date getFechaDonacion() {
        return dFechaDonacion;
    }

    public void setFechaDonacion(Date valor) {
        dFechaDonacion=valor;
    }

    public String getNombreDonador() {
        return nombreDonador;
    }

    public void setNombreDonador(String valor) {
        nombreDonador=valor;
    }

    public SolicitudSangre getSolicitudSangre() {
        return oSolicitudSangre;
    }

    public void setSolicitudSangre(SolicitudSangre valor) {
        oSolicitudSangre=valor;
    }

    public int getConsecutivo() {
        return nConsecutivo;
    }

    public void setConsecutivo(int nConsecutivo) {
        this.nConsecutivo = nConsecutivo;
    }

} 

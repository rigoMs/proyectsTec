package mx.gob.hrrb.modelo.consultaexterna;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Horario implements Serializable{
	private AccesoDatos oAD;
	private Date dHoraFin;
	private Date dHoraIni;
	private String sDia;
        private int nClave;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        
        public Horario(){
        dHoraIni=new Date();
        dHoraFin=new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("HH:mm");
	String dateInString = "00:00";
        try{
            dHoraIni=formatter.parse(dateInString);
            dHoraFin=formatter.parse(dateInString);
        }catch(Exception e){}
        sDia="";
        oFirm=new Firmado();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
        }
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Horario.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveHorario();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setDia((String)vRowTemp.get(1));
                                dHoraIni=((Date)vRowTemp.get(2));
                                dHoraIni=((Date)vRowTemp.get(3));
				bRet = true;
			}
		} 
		return bRet; 
	} 
	public Horario[] buscarTodos() throws Exception{
	Horario arrRet[]=null, oHorario=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosHorario();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Horario[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        //********************************************************************************************
        public boolean buscarPorDia(String dia, String hi, String hf) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( dia==null){   //completar llave
			throw new Exception("Horario.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaHoraDia('"+dia+"', '"+hi+"', '"+hf+"');";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setClave(((Double)vRowTemp.get(0)).intValue());
				bRet = true;
			}
		} 
		return bRet; 
	} 
        //*****************************************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Horario.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHorario();"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = Integer.parseInt(rst.get(0).toString().substring(1, 2));
				}
			}
		} 
		return nRet; 
	}
        //********************************************************************************************
        public int insertarHoras(String dia, String hi, String hf) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String sLlave=dia+", "+hi+", "+hf;
		 if( this==null){   //completar llave
			throw new Exception("Horario.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM InsertaHorarioAux('"+sUsuario+"', '"+dia+"', '"+hi+"', '"+hf+"', '"+sLlave+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = Integer.parseInt(rst.get(0).toString().substring(1, 2));
				}
			}
		} 
		return nRet; 
	} 
        //****************************************************************************************************
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Horario.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHorario();"; 
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
			throw new Exception("Horario.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaHorario();"; 
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
        
        public String getHoras(){
            return dHoraIni.toString().substring(11, 16)+"  -  "+dHoraFin.toString().substring(11, 16);
        }
        
        public void setHoras(String horas){
            SimpleDateFormat formatoDelTexto = new SimpleDateFormat("HH:mm");
            Date horaIni = null;
            Date horaFin = null;
            if(horas.length()>=15){
            try {
                horaIni = formatoDelTexto.parse(horas.substring(0, 2)+":"+horas.substring(3, 5));
                horaFin = formatoDelTexto.parse(horas.substring(10, 12)+":"+horas.substring(13, 15));
            } catch (ParseException ex) {ex.printStackTrace();}
                dHoraIni=horaIni;
                dHoraFin=horaFin;
            }
        }
        
	public String getDia() {
	return sDia;
	}

	public void setDia(String valor) {
	sDia=valor;
	}

    public int getClave() {
        return nClave;
    }

    public void setClave(int nClave) {
        this.nClave = nClave;
    }

} 

package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
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

public class DiaFestivo implements Serializable{
	private AccesoDatos oAD;
	private Date nDia, nDiaEli;
        private String sIdUsuario;
        private Firmado oFirm;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        
        private short nCveAni;
        private int nAnio;
        
        public DiaFestivo(){
            buscarUsuario();
        }
        
        public DiaFestivo(short nCve, int nAn){
            nCveAni= nCve;
            nAnio=nAn;
        }
        
        private boolean buscarUsuario(){
            boolean bRet = false;
            faceContext = FacesContext.getCurrentInstance();
            httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
            if (httpServletRequest.getSession().getAttribute("oFirm") != null){
                oFirm = (Firmado)httpServletRequest.getSession().getAttribute("oFirm");
                sIdUsuario = oFirm.getUsu().getIdUsuario();
                bRet = (sIdUsuario != null && !sIdUsuario.isEmpty());
            }
            return bRet;
        }

	public boolean buscar(Date fecha) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   
			throw new Exception("DiaFestivo.buscar: error de programación, faltan datos");
		}else{ 
                        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
			sQuery = "SELECT * FROM buscaLlaveDiaFestivo('"+df.format(fecha)+"');"; 
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
        
        public DiaFestivo[] buscarTodos() throws Exception{
	DiaFestivo arrRet[]=null, oDiaFestivo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosDiaFestivo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new DiaFestivo[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	}
        
        public int buscarFech(Date Fecha) throws Exception{
        nDia = Fecha;
        DiaFestivo arrRet[]=null, oDiaFestivo=null;
	ArrayList<DiaFestivo> vObj=null;
        ArrayList rst = null;       
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaFecha('"+Fecha+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                        vObj = new ArrayList<DiaFestivo>();
			for (i = 0; i < rst.size(); i++) {
                            oDiaFestivo = new DiaFestivo();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDiaFestivo.setDia((Date)vRowTemp.get(0));
                            vObj.add(oDiaFestivo);
			}
                        nTam = vObj.size();
                        arrRet = new DiaFestivo[nTam];

                        for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		} 
		return nTam;  
	}
        
        public DiaFestivo[] buscarTodosPorAnio(int Ani) throws Exception{
        DiaFestivo arrRet[]=null, oDiaFestivo=null;
	ArrayList<DiaFestivo> vObj=null;
        ArrayList rst = null;       
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaTodosDiasFestivosPorAnio("+Ani+");"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                        vObj = new ArrayList<DiaFestivo>();
			for (i = 0; i < rst.size(); i++) {
                            oDiaFestivo = new DiaFestivo();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDiaFestivo.setDia((Date)vRowTemp.get(0));
                            vObj.add(oDiaFestivo);
			}
                        nTam = vObj.size();
                        arrRet = new DiaFestivo[nTam];

                        for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		} 
		return arrRet; 
	}
        
        /*----- Para Insertar Una Fecha -----*/
        public int insertar(Date Fech) throws Exception{
            nDia = Fech;
            ArrayList rst = null;
            int nRet = 0;
            String sQuery= "";
            if(sIdUsuario == null || sIdUsuario.equals("") || getDia().after(nDia)){
                throw new Exception("Dia.insertar: error de programacion, faltan datos");
            }else{
                sQuery = getInsertar();
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst != null && rst.size() == 1){
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        nRet = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return nRet;
        }
        
        private String getInsertar() throws Exception{ 
            String sQuery = "";
            if (sIdUsuario == null || sIdUsuario.equals("") || getDia().after(nDia)){
                throw new Exception("Dia.getInsertar: error de programacion, Faltan Datos");
            } 
            else {
                sQuery = "SELECT * FROM insertaDiaFestivo("+"'"+sIdUsuario+"'::character varying,"
                                                           +"'"+nDia+"'::date);";
            }
            return sQuery;
        }
        
         /*----- Para Eliminar Una Fecha -----*/
        public int eliminar(Date Fech) throws Exception{
            nDiaEli = Fech;
            ArrayList rst = null;
            int nRet = 0;
            String sQuery = "";
            if(sIdUsuario == null || sIdUsuario.equals("") || getDiaEli().after(Fech)){
                throw new Exception("Dia.insertar: Error de Programacion, Faltan Datos");
            } else{
                sQuery = getEliminar();
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                    if(rst != null && rst.size() == 1){
                        ArrayList vRowTemp = (ArrayList)rst.get(0);
                        nRet = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return nRet;
        }
        
        private String getEliminar() throws Exception{
            String sQuery = "";
            if (sIdUsuario == null || sIdUsuario.equals("") || getDiaEli().after(nDiaEli)){
                throw new Exception("Dia.getEliminar: error de programacion, Faltan Datos");
            } else {
                sQuery = "SELECT * FROM eliminaDiaFestivo("+"'"+sIdUsuario+"'::character varying,"
                                                           +"'"+nDiaEli+"'::date);";
            }
            return sQuery;
        }
        
        
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("DiaFestivo.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaDiaFestivo();"; 
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
        
        public DiaFestivo[] buscarAnio(){
            DiaFestivo[] arrRet = new DiaFestivo[30];
            arrRet[0] = new DiaFestivo((short)1, 2015);
            arrRet[1] = new DiaFestivo((short)2, 2016);
            arrRet[2] = new DiaFestivo((short)3, 2017);
            arrRet[3] = new DiaFestivo((short)4, 2018);
            arrRet[4] = new DiaFestivo((short)5, 2019);
            arrRet[5] = new DiaFestivo((short)6, 2020);
            arrRet[6] = new DiaFestivo((short)7, 2021);
            arrRet[7] = new DiaFestivo((short)8, 2022);
            arrRet[8] = new DiaFestivo((short)9, 2023);
            arrRet[9] = new DiaFestivo((short)10, 2024);
            arrRet[10] = new DiaFestivo((short)11, 2025);
            arrRet[11] = new DiaFestivo((short)12, 2026);
            arrRet[12] = new DiaFestivo((short)13, 2027);
            arrRet[13] = new DiaFestivo((short)14, 2028);
            arrRet[14] = new DiaFestivo((short)15, 2029);
            arrRet[15] = new DiaFestivo((short)16, 2030);
            arrRet[16] = new DiaFestivo((short)17, 2031);
            arrRet[17] = new DiaFestivo((short)18, 2032);
            arrRet[18] = new DiaFestivo((short)19, 2033);
            arrRet[19] = new DiaFestivo((short)20, 2034);
            arrRet[20] = new DiaFestivo((short)21, 2035);
            arrRet[21] = new DiaFestivo((short)22, 2036);
            arrRet[22] = new DiaFestivo((short)23, 2037);
            arrRet[23] = new DiaFestivo((short)24, 2038);
            arrRet[24] = new DiaFestivo((short)25, 2039);
            arrRet[25] = new DiaFestivo((short)26, 2040);
            arrRet[26] = new DiaFestivo((short)27, 2041);
            arrRet[27] = new DiaFestivo((short)28, 2042);
            arrRet[28] = new DiaFestivo((short)29, 2043);
            arrRet[29] = new DiaFestivo((short)30, 2044);
            return arrRet;
        }
        
        public short getCveAnio(){
            return nCveAni;
        }
        
        public void setCveAnio(short value){
            nCveAni = value;
        }
        
        public int getAnio(){
            return nAnio;
        }
        
        public void setAnio(int value){
           nAnio = value;
        }
        
	public Date getDia() {
            return nDia;
	}

	public void setDia(Date valor) {
            nDia=valor;
	}
        
        public Date getDiaEli() {
            return nDiaEli;
	}

	public void setDiaEli(Date valor) {
            nDiaEli=valor;
	}
} 

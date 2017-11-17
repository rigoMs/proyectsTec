package mx.gob.hrrb.modelo.enfermeria;
//import mx.gob.hrrb.modelo.serv.DetalleSolicitudHemoderivado;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.Parametrizacion;
/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class Hemoderivados implements Serializable{
	private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private Date dHoraAplicacion;
	private int nCantidad;
	private long nClaveHemod;
        private String sHemoderivado;
        //private DetalleSolicitudHemoderivado oSolHemo;
	private HojaEnfermeriaQuirofano oHojaEnfermeriaQuirofano;
        private Parametrizacion oTipoSangre;
        private Parametrizacion oTipoRH;
        private String sClaveHemoderivado;
        private String sDescripcionHem;
        private int nIdDetalleSol;
        
	public Hemoderivados(){
            oHojaEnfermeriaQuirofano= new HojaEnfermeriaQuirofano();
            oTipoSangre= new Parametrizacion();
            oTipoRH= new Parametrizacion();
            HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
        
        public Hemoderivados[] buscaSolicitudHemoderivadoPaciente(long nFolioPa, long nCveEp) throws Exception{
            Hemoderivados arrHem[]=null, oTran=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            if(nFolioPa==0 || nCveEp==0){
                throw new Exception("HojaDeTransfucion. buscaSolicitudHemoderivadoPaciente: Error, Faltan Datos");
            }else{
                sQuery="SELECT * FROM buscaSolicitudHemoderivadoPacienteSinAplicar("+nFolioPa+"::bigint,"+nCveEp+"::bigint)";                 
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                arrHem = new Hemoderivados[rst.size()];
                for(i=0; i<rst.size();i++){
                    ArrayList vRowTemp=(ArrayList)rst.get(i);
                    oTran= new Hemoderivados();
                    oTran.getHojaEnfermeriaQuirofano().getEpisodioMedico().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oTran.getHojaEnfermeriaQuirofano().getEpisodioMedico().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oTran.getHojaEnfermeriaQuirofano().getEpisodioMedico().getPaciente().setNombres((String)vRowTemp.get(2));
                    oTran.getHojaEnfermeriaQuirofano().getEpisodioMedico().getPaciente().setApPaterno((String)vRowTemp.get(3));
                    oTran.getHojaEnfermeriaQuirofano().getEpisodioMedico().getPaciente().setApMaterno((String)vRowTemp.get(4));
                    oTran.getTipoSangre().setValor((String)vRowTemp.get(6));
                    oTran.getTipoRH().setValor((String)vRowTemp.get(7));
                    oTran.getTipoSangre().setClaveParametro((String)vRowTemp.get(8));
                    oTran.getTipoSangre().setTipoParametro((String)vRowTemp.get(9));
                    oTran.getTipoRH().setClaveParametro((String)vRowTemp.get(10));
                    oTran.getTipoRH().setTipoParametro((String)vRowTemp.get(11));                    
                    oTran.setClaveHemoderivado((String)vRowTemp.get(13));
                    oTran.setDescripcionHem((String)vRowTemp.get(14));
                    oTran.setIdDetalleSol(((Double)vRowTemp.get(15)).intValue());
                    arrHem[i]= oTran;
                }
            }
        }
        return arrHem;
    }
 
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hemoderivados.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaHemoderivados('"+sUsuarioFirmado+"');"; 
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
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Hemoderivados.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaHemoderivados('"+sUsuarioFirmado+"');"; 
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
	 
	public Date getHoraAplicacion() {
	return dHoraAplicacion;
	}

	public void setHoraAplicacion(Date valor) {
	dHoraAplicacion=valor;
	}

	public int getCantidad() {
	return nCantidad;
	}

	public void setCantidad(int valor) {
	nCantidad=valor;
	}

	public long getClaveHemod() {
	return nClaveHemod;
	}

	public void setClaveHemod(long valor) {
	nClaveHemod=valor;
	}


	public HojaEnfermeriaQuirofano getHojaEnfermeriaQuirofano() {
	return oHojaEnfermeriaQuirofano;
	}

	public void setHojaEnfermeriaQuirofano(HojaEnfermeriaQuirofano valor) {
	oHojaEnfermeriaQuirofano=valor;
	}

    public String getHemoderivado() {
        return sHemoderivado;
    }

    public void setHemoderivado(String sHemoderivado) {
        this.sHemoderivado = sHemoderivado;
    }
    
    public String getHoraAplicadaString(){
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");        
        return format.format(this.getHoraAplicacion());
    }

  
    public Parametrizacion getTipoSangre() {
        return oTipoSangre;
    }

    public void setTipoSangre(Parametrizacion oTipoSangre) {
        this.oTipoSangre = oTipoSangre;
    }

    public Parametrizacion getTipoRH() {
        return oTipoRH;
    }

    public void setTipoRH(Parametrizacion oTipoRH) {
        this.oTipoRH = oTipoRH;
    }


    public void setClaveHemoderivado(String sClaveHemoderivado) {
        this.sClaveHemoderivado = sClaveHemoderivado;
    }
    public String getClaveHemoderivado() {
        return sClaveHemoderivado;
    }

    public String getDescripcionHem() {
        return sDescripcionHem;
    }

    public void setDescripcionHem(String sDescripcionHem) {
        this.sDescripcionHem = sDescripcionHem;
    }

    public int getIdDetalleSol() {
        return nIdDetalleSol;
    }

    public void setIdDetalleSol(int nIdDetalleSol) {
        this.nIdDetalleSol = nIdDetalleSol;
    }

} 

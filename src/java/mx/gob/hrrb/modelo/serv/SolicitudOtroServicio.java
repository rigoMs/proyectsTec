package mx.gob.hrrb.modelo.serv;

import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.core.Estudios;
import mx.gob.hrrb.modelo.core.OtroServicio;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class SolicitudOtroServicio extends ServicioRealizado implements Serializable{
private AccesoDatos oAD;
protected String sUsuarioFirmado;
protected OtroServicio oOtroServ;
private Estudios oEstudios; //REVISAR CON RAFA

	public SolicitudOtroServicio(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
	}
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudOtroServicio.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveSolicitudOtroServicio();"; 
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
        @Override
	public SolicitudOtroServicio[] buscarTodos() throws Exception{
	SolicitudOtroServicio arrRet[]=null, oSolicitudOtroServicio=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosSolicitudOtroServicio();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new SolicitudOtroServicio[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudOtroServicio.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaSolicitudOtroServicio('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudOtroServicio.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaSolicitudOtroServicio('"+sUsuarioFirmado+"');"; 
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
        @Override
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = -1;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("SolicitudOtroServicio.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaSolicitudOtroServicio('"+sUsuarioFirmado+"');"; 
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
    
    ////////////////////////////////////////////////////////////////////////////////
    //Órdenes de servicio 
    public SolicitudOtroServicio[] buscarEstudiosOto(String EstOto) throws Exception {
    SolicitudOtroServicio arrRet[]=null, oEst=null;
    ArrayList rst = null;
    ArrayList <SolicitudOtroServicio> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
    sQuery = "SELECT * FROM buscarestudiosdesolicitudotoacusticos('"+EstOto+"');";
    oAD = new AccesoDatos();
        if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery); 
                oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<SolicitudOtroServicio>();
            for(i=0; i<rst.size(); i++){
                    oEst = new SolicitudOtroServicio();
                    oEst.setEstudio(new Estudios());
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oEst.getEstudio().setConcepto(((String) vRowTemp.get(0)).trim());
                    vObj.add(oEst);
            }
            nTam=vObj.size();
            arrRet = new SolicitudOtroServicio[nTam];

            for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public List<SolicitudOtroServicio> getListaEstudiosOto(String txt) {
    List<SolicitudOtroServicio> lListaEst = null;
    try{
        lListaEst = new ArrayList<SolicitudOtroServicio>(Arrays.asList(
                (new SolicitudOtroServicio()).buscarEstudiosOto(txt)));
    } catch (Exception ex) {
        Logger.getLogger(SolicitudOtroServicio.class.getName()).log(Level.SEVERE, null, ex);
    }
    return lListaEst;
}

    public List<String> completarEstOto(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<SolicitudOtroServicio> lis = getListaEstudiosOto(sTxt);
        if (lis != null){
            for (SolicitudOtroServicio li:lis) {
                if (sp_ascii(li.getEstudio().getConcepto()).contains(sTxt)) {
                    arrRet.add(li.getEstudio().getConcepto());
                }
            }
        }
        return arrRet;
    }    

    public String sp_ascii(String input) {
    // Cadena de caracteres original a sustituir.
    String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
    // Cadena de caracteres ASCII que reemplazarán los originales.
    String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
    String output = input;
    for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
    }//for i
    return output;
}               

    public boolean buscarClavesEstudiosOto() throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
    sQuery = "SELECT * FROM buscarestudiosotoacusticosclaves('" + this.getEstudio().getConcepto() + "');";
    oAD = new AccesoDatos();
    if (oAD.conectar()) {
        rst = oAD.ejecutarConsulta(sQuery);
        oAD.desconectar();
    }
    if (rst != null && rst.size() >= 1) {
        ArrayList vRowTemp = (ArrayList) rst.get(0);
        this.getEstudio().setClaveInterna(((Double) vRowTemp.get(0)).intValue());
        this.getEstudio().setClaveStudio(((String) vRowTemp.get(1)).trim());
        bRet = true;
    }
    return bRet;
}

        public String getInsertaSolOto() throws Exception{
            String sQuery="";
            if(getEstudio().getClaveInterna()==0){
                throw new Exception("Estudios.Modificar: error, faltan datos");
            }else{
                System.out.println("Dentro del Else");
                sQuery = "SELECT * FROM insertasolicitudotoacusticos('"+ sUsuarioFirmado +"'::character varying,"
                        + getEpisodio().getPaciente().getFolioPaciente() +"::bigint,"
                        + getEpisodio().getClaveEpisodio() +"::bigint,"
                        + getAutorizadoPor().getNoTarjeta() +"::integer,"
                        + getEstudio().getClaveInterna()+"::smallint,"
                        + getEpisodio().getArea().getClave() +"::smallint,'"
                        + getEpisodio().getDiagIngreso().getClave() +"');";
            }
            return sQuery;
        }
               
        public void insertaEstudiosOto(ArrayList<SolicitudOtroServicio> arrEstudioOto) throws Exception{
            System.out.println("Dentro del método insertaEstudiosOto");
                ArrayList<String> vSolicLab=new ArrayList<>();
                int nRegAfectados=0;
                for(SolicitudOtroServicio Es:arrEstudioOto){
                    vSolicLab.add(Es.getInsertaSolOto());
                }
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    nRegAfectados = oAD.ejecutarConsultaComando(vSolicLab);
                    oAD.desconectar();
                }
        }
                
        public int insertarElectro() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
            if( this==null ){
                throw new Exception("SolicitudOtroServicio.insertar: error de programación, faltan datos");
            }else{ 
                sQuery = "SELECT * FROM insertasolicitudelectro('"+ sUsuarioFirmado +"'::character varying,"
                        + getEpisodio().getPaciente().getFolioPaciente() + "::bigint,"
                        + getEpisodio().getClaveEpisodio() + "::bigint,"
                        + getAutorizadoPor().getNoTarjeta() + "::integer,"
                        + 355 + "::smallint,"
                        + getEpisodio().getArea().getClave() +"::smallint,'"
                        + getEpisodio().getDiagIngreso().getClave() +"');";
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
        
        
////////////////////////////////////////////////////////////////////////////////        

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oOtroServ;
    }

    @Override
    public void setServicioCobrable(ServicioCobrable oValor) {
        this.oOtroServ = (OtroServicio)oValor;
    }

    public Estudios getEstudio() {
        return oEstudios;
    }

    public void setEstudio(Estudios oEstudios) {
        this.oEstudios = oEstudios;
    }

} 

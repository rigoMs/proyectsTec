package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/
public class DetalleRecetaHRRB extends DetalleReceta implements Serializable{
        private AccesoDatos oAD;
	private String sUsuarioFirmado;
	private PersonalHospitalario oQuienSurte;
	private Parametrizacion oVia;
	private String sDosis;
	private String sLote;
        ////////////////////////////////
        private String sFrecuencia;
        private String sDuracion;
        ///////////////////////////////
        
        public DetalleRecetaHRRB(){
	HttpServletRequest req;
		req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
		if (req.getSession().getAttribute("oFirm") != null) {
			sUsuarioFirmado = ((Firmado)req.getSession().getAttribute("oFirm")).getUsu().getIdUsuario();
		}
                oVia = new Parametrizacion();
	}
        
        @Override
        public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("DetalleRecetaHRRB.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDetalleRecetaHRRB();"; 
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
	public DetalleRecetaHRRB[] buscarTodos() throws Exception{
	DetalleRecetaHRRB arrRet[]=null, oDetalleRecetaHRRB=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosDetalleRecetaHRRB();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new DetalleRecetaHRRB[rst.size()];
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
			throw new Exception("DetalleRecetaHRRB.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaDetalleRecetaHRRB('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("DetalleRecetaHRRB.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaDetalleRecetaHRRB('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("DetalleRecetaHRRB.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDetalleRecetaHRRB('"+sUsuarioFirmado+"');"; 
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
public int insertaRecetaHrrb(ArrayList<DetalleRecetaHRRB> lRecPac) throws Exception{
    int mod=0;
    ArrayList rst=null, vRowTemp=null;
    String sQuery="";
    if(this == null && lRecPac==null){
        throw new Exception("DetalleRecetaHRRB.insertaRecetaHrrb: error, faltan datos");
    }else{
        sQuery = "SELECT * FROM insertarecetahrrbarr('"+ sUsuarioFirmado +"'::character varying,"
            + oReceta.getEpisodioMedico().getPaciente().getFolioPaciente() +"::bigint,"
            + oReceta.getEpisodioMedico().getClaveEpisodio() +"::bigint,"
            + getAutorizadoPor().getNoTarjeta() +"::integer,'"
            + oReceta.getEpisodioMedico().getPaciente().getSeg().getNumero() +"'::character varying,"
            + oReceta.getFolioReceta() + "::integer,ARRAY[";
            for(DetalleRecetaHRRB rh:lRecPac){
                if(rh.getMedicamento()!=null){
                    sQuery = sQuery + "'" + rh.getMedicamento().getClaveMedicamento()+"', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";

            for(DetalleRecetaHRRB rh:lRecPac){
                if(rh.getMedicamento().getPresentacion()!=null){
                    sQuery = sQuery + "'" + rh.getMedicamento().getPresentacion()+"', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";

            for(DetalleRecetaHRRB rh:lRecPac){
                if(rh.getMedicamento().getCodBarras()>=0){
                    sQuery = sQuery + "" + rh.getMedicamento().getCodBarras() + ", ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::bigint[], ARRAY[";

            for(DetalleRecetaHRRB rh:lRecPac){
                if(rh.getDosis()!=null){
                    sQuery = sQuery + "'" + rh.getDosis() + "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";

            for(DetalleRecetaHRRB rh:lRecPac){
                if(rh.getVia().getClaveParametro()!=null){
                    sQuery = sQuery + "'" + rh.getVia().getClaveParametro()+ "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character(3)[], ARRAY[";

            for(DetalleRecetaHRRB rh:lRecPac){
                if(rh.getFrecuencia()!=null){
                    sQuery = sQuery + "'" + rh.getFrecuencia()+ "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[], ARRAY[";

            for(DetalleRecetaHRRB rh:lRecPac){
                if(rh.getDuracion()!=null){
                    sQuery = sQuery + "'" + rh.getDuracion()+ "', ";
                }
            }
            sQuery = sQuery.substring(0, sQuery.length()-2);
            sQuery = sQuery + "]::character varying[]);";

            System.out.println(sQuery);
        oAD = new AccesoDatos();
        if(oAD.conectar()){
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();

            if(rst != null && rst.size()==1){
                vRowTemp = (ArrayList) rst.get(0);
                mod = ((Double)vRowTemp.get(0)).intValue();
            }
        }
    }
    return mod;
}

    

        
        public int insertaRecetaInd(ArrayList<DetalleRecetaHRRB> lRecInd) throws Exception{
            int mod=0;
            ArrayList rst=null, vRowTemp=null;
            String sQuery="";
            if(this == null && lRecInd==null){
                throw new Exception("DetalleRecetaHRRB.insertaRecetaInd: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM insertarecetaindividualarr('"+ sUsuarioFirmado +"'::character varying,"
                            + oReceta.getEpisodioMedico().getPaciente().getFolioPaciente() +"::bigint,"
                            + oReceta.getEpisodioMedico().getClaveEpisodio() +"::bigint,"
                            + getAutorizadoPor().getNoTarjeta() + "::integer,"
                            + getReceta().getFolioReceta() + "::integer,ARRAY[";
                for(DetalleRecetaHRRB rh:lRecInd){
                    if(rh.getMedicamento().getClaveMedicamento()!=null){
                        sQuery = sQuery + "'" + rh.getMedicamento().getClaveMedicamento()+"', ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::character varying[], ARRAY[";
                for(DetalleRecetaHRRB rh:lRecInd){
                    if(rh.getMedicamento().getPresentacion()!=null){
                        sQuery = sQuery + "'" + rh.getMedicamento().getPresentacion()+"', ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::character varying[], ARRAY[";
                for(DetalleRecetaHRRB rh:lRecInd){
                    if(rh.getCantidad()!=0){
                        sQuery = sQuery + "" + rh.getCantidad()+", ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::smallint[], ARRAY[";
                for(DetalleRecetaHRRB rh:lRecInd){
                    if(rh.getMedicamento().getCodBarras()>=0){
                        sQuery = sQuery + "" + rh.getMedicamento().getCodBarras()+", ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::bigint[]);";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();

                    if(rst != null && rst.size()==1){
                        vRowTemp = (ArrayList) rst.get(0);
                        mod = ((Double)vRowTemp.get(0)).intValue();
                    }
                }
            }
            return mod;
        }
        
        
////////////////////////////////////////////////////////////////////////////////
        
        
        public DetalleRecetaHRRB[] buscarMedicamentosSolicitadosRecetaIndividualPaciente(Date dFecAux, int AreaServ, long FolioPac, long CveEpiMed) throws Exception{
	DetalleRecetaHRRB arrRet[]=null, oRec=null;
	ArrayList rst = null;
        ArrayList<DetalleRecetaHRRB> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){
			throw new Exception("DetalleRecetaHRRB.buscar: error de programación, faltan datos");
		}else{
                            sQuery = "SELECT * FROM buscamedicamentosolicitadosurtidorecetaindividualpaciente('"+df.format(dFecAux)+"%',"
                                                                                                    +AreaServ+"::smallint,"
                                                                                                    +FolioPac+"::bigint,"
                                                                                                    +CveEpiMed+"::bigint);";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<DetalleRecetaHRRB>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oRec=new DetalleRecetaHRRB();
                                oRec.getMedicamento().setNombre((String) vRowTemp.get(0));
                                oRec.getMedicamento().setPresentacion((String) vRowTemp.get(1));
                                oRec.setCantSolicitada(((Double) vRowTemp.get(2)).shortValue());
                                oRec.setCantSurtida(((Double) vRowTemp.get(3)).shortValue());
                                
				vObj.add(oRec);                              
                            }
                            nTam = vObj.size();
                            arrRet = new DetalleRecetaHRRB[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
        
        
        public DetalleRecetaHRRB[] buscarMedicamentosControladosSolicitadosRecetaIndividualPaciente(Date dFecAux, int AreaServ, long FolioPac, long CveEpiMed) throws Exception{
	DetalleRecetaHRRB arrRet[]=null, oRec=null;
	ArrayList rst = null;
        ArrayList<DetalleRecetaHRRB> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){
			throw new Exception("DetalleRecetaHRRB.buscar: error de programación, faltan datos");
		}else{
                            sQuery = "SELECT * FROM buscamedicamentocontroladosolicitadosurtidorecetaindividualpaciente('"+df.format(dFecAux)+"%',"
                                                                                                    +AreaServ+"::smallint,"
                                                                                                    +FolioPac+"::bigint,"
                                                                                                    +CveEpiMed+"::bigint);";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<DetalleRecetaHRRB>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oRec=new DetalleRecetaHRRB();
                                oRec.getMedicamento().setNombre((String) vRowTemp.get(0));
                                oRec.getMedicamento().setPresentacion((String) vRowTemp.get(1));
                                oRec.setCantSolicitada(((Double) vRowTemp.get(2)).shortValue());
                                oRec.setCantSurtida(((Double) vRowTemp.get(3)).shortValue());
                                
				vObj.add(oRec);                              
                            }
                            nTam = vObj.size();
                            arrRet = new DetalleRecetaHRRB[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
        
        
////////////////////////////////////////////////////////////////////////////////

        
    /**
     * @return the oVia
     */
    public Parametrizacion getVia() {
        return oVia;
    }

    /**
     * @param oVia the oVia to set
     */
    public void setVia(Parametrizacion oVia) {
        this.oVia = oVia;
    }

    /**
     * @return the sDosis
     */
    public String getDosis() {
        return sDosis;
    }

    /**
     * @param sDosis the sDosis to set
     */
    public void setDosis(String sDosis) {
        this.sDosis = sDosis;
    }
////////////////////////////////////////////////////
    /**
     * @return the sFrecuencia
     */
    public String getFrecuencia() {
        return sFrecuencia;
    }

    /**
     * @param sFrecuencia the sFrecuencia to set
     */
    public void setFrecuencia(String sFrecuencia) {
        this.sFrecuencia = sFrecuencia;
    }

    /**
     * @return the sDuracion
     */
    public String getDuracion() {
        return sDuracion;
    }

    /**
     * @param sDuracion the sDuracion to set
     */
    public void setDuracion(String sDuracion) {
        this.sDuracion = sDuracion;
    }
/////////////////////////////////////////////////

    public String getLote() {
        return sLote;
    }

    public void setLote(String sLote) {
        this.sLote = sLote;
    }
}
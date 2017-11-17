package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.serv.ServicioRealizado;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import javax.servlet.http.HttpServletRequest;
import javax.faces.context.FacesContext;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public  class DetalleRecetarioColectivo extends ServicioRealizado implements Serializable{
private AccesoDatos oAD;
private String sUsuarioFirmado;
private PersonalHospitalario oQuienSurte;
private Parametrizacion oUnidadMedida;
private Medicamento oMedicamento;
private RecetarioColectivo oRecetario;
private int nCantidad;
private short nCantSolicitadaArea;

//Para facilitar consulta de totales NO USAR
private long nCantidadSolTot;
private long nCantidadSurTot;

	public DetalleRecetarioColectivo(){
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
			throw new Exception("DetalleRecetarioColectivo.buscar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDetalleRecetarioColectivo();"; 
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
	public DetalleRecetarioColectivo[] buscarTodos() throws Exception{
	DetalleRecetarioColectivo arrRet[]=null, oDetalleRecetarioColectivo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosDetalleRecetarioColectivo();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new DetalleRecetarioColectivo[rst.size()];
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
			throw new Exception("DetalleRecetarioColectivo.insertar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaDetalleRecetarioColectivo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("DetalleRecetarioColectivo.modificar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaDetalleRecetarioColectivo('"+sUsuarioFirmado+"');"; 
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
			throw new Exception("DetalleRecetarioColectivo.eliminar: error, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDetalleRecetarioColectivo('"+sUsuarioFirmado+"');"; 
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
        
        public int insertaRecetarioColectivo(
                ArrayList<DetalleRecetarioColectivo> lRecCol) 
                throws Exception{
        int mod=0;
        ArrayList rst=null, vRowTemp=null;
        String sQuery="";
            if(this == null && lRecCol==null){
                throw new Exception("DetalleRecetarioColectivo.insertaRecetarioColectivo: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM insertarecetariocolectivoarr('"+ sUsuarioFirmado +"'::character varying,"
                        + getEpisodio().getPaciente().getFolioPaciente() +"::bigint,"
                        + getEpisodio().getClaveEpisodio() +"::bigint,"
                        + getAutorizadoPor().getNoTarjeta() +"::integer,"
                        + getEpisodio().getArea().getClave() + "::smallint,'"
                        + ((Medicamento)getServicioCobrable()).getGpoLeySalud() + "'::character(3),ARRAY[";
                for(DetalleRecetarioColectivo rc:lRecCol){
                    if(rc.getCantidadSol()!=0){
                        sQuery = sQuery + "" + rc.getCantidadSol()+", ";
                    }
                }
                    sQuery = sQuery.substring(0, sQuery.length()-2);
                    sQuery = sQuery + "]::smallint[], ARRAY[";
                    for(DetalleRecetarioColectivo rc:lRecCol){
                        if(((Medicamento)rc.getServicioCobrable()).getClaveMedicamento()!=null){
                            sQuery = sQuery + "'" + ((Medicamento)rc.getServicioCobrable()).getClaveMedicamento()+"', ";
                        }
                    }
                    sQuery = sQuery.substring(0, sQuery.length()-2);
                    sQuery = sQuery + "]::character varying[], ARRAY[";
                    for(DetalleRecetarioColectivo rc:lRecCol){
                        if(((Medicamento)rc.getServicioCobrable()).getPresentacion()!=null){
                            sQuery = sQuery + "'" + ((Medicamento)rc.getServicioCobrable()).getPresentacion()+"', ";
                        }
                    }
                    sQuery = sQuery.substring(0, sQuery.length()-2);
                    sQuery = sQuery + "]::character varying[], ARRAY[";
                    for(DetalleRecetarioColectivo rc:lRecCol){
                        if(((Medicamento)rc.getServicioCobrable()).getCodBarras()>=0){
                            sQuery = sQuery + "" + ((Medicamento)rc.getServicioCobrable()).getCodBarras()+", ";
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
        
        public int insertaResurtirMedicamentoColectivo(
                ArrayList<DetalleRecetarioColectivo> lResCol) throws Exception{
            int mod=0;
            ArrayList rst=null, vRowTemp=null;
            String sQuery="";
            if(this.oEpisodio == null && lResCol==null){
                throw new Exception("DetalleRecetarioColectivo.insertaResurtirMedicamentoColectivo: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM insertaresurtirrecetacolectivoarr('"+ sUsuarioFirmado +"'::character varying,"
                            + oRecetario.getAreaServ().getClave() + "::smallint,ARRAY[";
                for(DetalleRecetarioColectivo vc:lResCol){
                    if(((Medicamento)vc.getServicioCobrable()).getClaveMedicamento()!=null){
                        sQuery = sQuery + "'" + ((Medicamento)vc.getServicioCobrable()).getClaveMedicamento()+"', ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::character varying[], ARRAY[";
                for(DetalleRecetarioColectivo vc:lResCol){
                    if(((Medicamento)vc.getServicioCobrable()).getPresentacion()!=null){
                        sQuery = sQuery + "'" + ((Medicamento)vc.getServicioCobrable()).getPresentacion()+"', ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::character varying[], ARRAY[";
                for(DetalleRecetarioColectivo vc:lResCol){
                    if(((Medicamento)vc.getServicioCobrable()).getCodBarras()>=0){
                        sQuery = sQuery + "" + ((Medicamento)vc.getServicioCobrable()).getCodBarras()+", ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::bigint[], ARRAY[";
                for(DetalleRecetarioColectivo vc:lResCol){
                    if(vc.getCantSolicitadaArea() != 0){
                        sQuery = sQuery + "" + vc.getCantSolicitadaArea() + ", ";
                    }
                }
                sQuery = sQuery.substring(0, sQuery.length()-2);
                sQuery = sQuery + "]::smallint[]);";
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
        
        public DetalleRecetarioColectivo[] buscarMedicamentoSolicitadosPaciente(
                Date dFecAux, int AreaServ, long FolioPac, long CveEpiMed) 
                throws Exception{
	DetalleRecetarioColectivo arrRet[]=null, oRec=null;
	ArrayList rst = null;
        ArrayList<DetalleRecetarioColectivo> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){
			throw new Exception("DetalleRecetarioColectivo.buscar: error de programación, faltan datos");
		}else{
                    sQuery = "SELECT * FROM buscamedicamentosolicitadosurtidorecetariocolectivo('"+df.format(dFecAux)+"%',"
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
                        vObj = new ArrayList<DetalleRecetarioColectivo>();
                        for (i=0; i<rst.size(); i++){
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oRec = new DetalleRecetarioColectivo();
                            ((Medicamento)oRec.getServicioCobrable()).setNombre((String) vRowTemp.get(0));
                            ((Medicamento)oRec.getServicioCobrable()).setPresentacion((String) vRowTemp.get(1));
                            oRec.setCantSolicitada(((Double) vRowTemp.get(2)).shortValue());
                            oRec.setCantSurtida(((Double) vRowTemp.get(3)).shortValue());

                            vObj.add(oRec);                              
                        }
                        nTam = vObj.size();
                        arrRet = new DetalleRecetarioColectivo[nTam];
                        for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
	}
        
        public DetalleRecetarioColectivo[] buscarMedicamentoResurtidoArea(
                Date dFecAux, int AreaServ) throws Exception{
	DetalleRecetarioColectivo arrRet[]=null, oRec=null;
	ArrayList rst = null;
        ArrayList<DetalleRecetarioColectivo> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){
			throw new Exception("DetalleRecetarioColectivo.buscar: error de programación, faltan datos");
		}else{
                            sQuery = "SELECT * FROM buscamedicamentoresurtidorecetariocolectivo('"+df.format(dFecAux)+"%',"
                                                                                                        +AreaServ+"::smallint);";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<DetalleRecetarioColectivo>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oRec = new DetalleRecetarioColectivo();
                                ((Medicamento)oRec.getServicioCobrable()).setNombre((String) vRowTemp.get(0));
                                ((Medicamento)oRec.getServicioCobrable()).setPresentacion((String) vRowTemp.get(1));
                                oRec.setCantSolicitada(((Double) vRowTemp.get(2)).shortValue());
                                oRec.setCantSurtida(((Double) vRowTemp.get(3)).shortValue());
                                
				vObj.add(oRec);                              
                            }
                            nTam = vObj.size();
                            arrRet = new DetalleRecetarioColectivo[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
        
        public DetalleRecetarioColectivo[] buscarMedicamentoControladoSolicitadosPaciente(Date dFecAux, int AreaServ, long FolioPac, long CveEpiMed) throws Exception{
	DetalleRecetarioColectivo arrRet[]=null, oRec=null;
	ArrayList rst = null;
        ArrayList<DetalleRecetarioColectivo> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){
			throw new Exception("DetalleRecetarioColectivo.buscar: error de programación, faltan datos");
		}else{
                            sQuery = "SELECT * FROM buscamedicamentocontroladosolicitadosurtidorecetariocolectivo('"+df.format(dFecAux)+"%',"
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
                            vObj = new ArrayList<DetalleRecetarioColectivo>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oRec = new DetalleRecetarioColectivo();
                                ((Medicamento)oRec.getServicioCobrable()).setNombre((String) vRowTemp.get(0));
                                ((Medicamento)oRec.getServicioCobrable()).setPresentacion((String) vRowTemp.get(1));
                                oRec.setCantSolicitada(((Double) vRowTemp.get(2)).shortValue());
                                oRec.setCantSurtida(((Double) vRowTemp.get(3)).shortValue());
                                
				vObj.add(oRec);                              
                            }
                            nTam = vObj.size();
                            arrRet = new DetalleRecetarioColectivo[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
        
        public DetalleRecetarioColectivo[] buscarMedicamentoControladoResurtidoArea(Date dFecAux, int AreaServ) throws Exception{
	DetalleRecetarioColectivo arrRet[]=null, oRec=null;
	ArrayList rst = null;
        ArrayList<DetalleRecetarioColectivo> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){
			throw new Exception("DetalleRecetarioColectivo.buscar: error de programación, faltan datos");
		}else{
                            sQuery = "SELECT * FROM buscamedicamentocontroladoresurtidorecetariocolectivo('"+df.format(dFecAux)+"%',"
                                                                                                        +AreaServ+"::smallint);";
                            System.out.println(sQuery);
			oAD=new AccesoDatos();
                        
			if (oAD.conectar()){
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
			}
			if (rst != null) {
                            vObj = new ArrayList<DetalleRecetarioColectivo>();
                            for (i=0; i<rst.size(); i++){
				ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oRec = new DetalleRecetarioColectivo();
                                ((Medicamento)oRec.getServicioCobrable()).setNombre((String) vRowTemp.get(0));
                                ((Medicamento)oRec.getServicioCobrable()).setPresentacion((String) vRowTemp.get(1));
                                oRec.setCantSolicitada(((Double) vRowTemp.get(2)).shortValue());
                                oRec.setCantSurtida(((Double) vRowTemp.get(3)).shortValue());
                                
				vObj.add(oRec);                              
                            }
                            nTam = vObj.size();
                            arrRet = new DetalleRecetarioColectivo[nTam];
                            for (i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
		    }
		}
                return arrRet;
	}
        
        public DetalleRecetarioColectivo[] buscarTotalMedicamentoControladoSolSur(Date dFecAux, int AreaServ) throws Exception{
        DetalleRecetarioColectivo arrRet[]=null, oRec=null;
        ArrayList rst = null;
        ArrayList <DetalleRecetarioColectivo> vObj = null;
        String sQuery="";
        int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyy-MM-dd");
                if(this==null){
                    throw new Exception("DetalleRecetarioColectivo.buscar: error de programación, faltan datos");
                } else {
                    sQuery = "SELECT * FROM buscamedicamentocontroladosolicitadosurtidorecetariocolectivototal('"+df.format(dFecAux)+"%',"
                                                                                                                         +AreaServ+"::smallint);";
                        oAD=new AccesoDatos();
                        
                        if (oAD.conectar()){
                            rst = oAD.ejecutarConsulta(sQuery);
                            oAD.desconectar();
                        }
                        if (rst != null) {
                            vObj = new ArrayList<DetalleRecetarioColectivo>();
                            for(i=0; i<rst.size(); i++){
                                ArrayList vRowTemp = (ArrayList)rst.get(i);
                                oRec = new DetalleRecetarioColectivo();
                                oRec.getMedicamento().setNombre((String) vRowTemp.get(0));
                                oRec.getMedicamento().setPresentacion((String) vRowTemp.get(1));
                                oRec.setCantSolicitada(((Double) vRowTemp.get(2)).shortValue());
                                oRec.setCantSurtida(((Double) vRowTemp.get(3)).shortValue());
                                vObj.add(oRec);
                        }
                        nTam = vObj.size();
                        arrRet = new DetalleRecetarioColectivo[nTam];
                        for(i=0; i<nTam; i++){
                            arrRet[i] = vObj.get(i);
                        }
                    }
                }
                return arrRet;
        }
        
        public DetalleRecetarioColectivo[] buscarTotalMedicamentoSolSur(
                Date dFecAux, int AreaServ) throws Exception{
	DetalleRecetarioColectivo arrRet[]=null, oRec=null;
	ArrayList rst = null;
        ArrayList<DetalleRecetarioColectivo> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
            if( this==null){
                throw new Exception("DetalleRecetarioColectivo.buscar: error de programación, faltan datos");
            }else{
                sQuery = "SELECT * FROM buscamedicamentosolicitadosurtidorecetariocolectivototal('"+
                        df.format(dFecAux)+"%',"
                        +AreaServ+"::smallint);";
                oAD=new AccesoDatos();

                if (oAD.conectar()){
                        rst = oAD.ejecutarConsulta(sQuery);
                        oAD.desconectar(); 
                }
                if (rst != null) {
                    vObj = new ArrayList<DetalleRecetarioColectivo>();
                    for (i=0; i<rst.size(); i++){
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oRec = new DetalleRecetarioColectivo();
                        oRec.getMedicamento().setNombre((String) vRowTemp.get(0));
                        oRec.getMedicamento().setPresentacion((String) vRowTemp.get(1));
                        oRec.setCantidadSolTot(((Double) vRowTemp.get(2)).longValue());
                        oRec.setCantidadSurTot(((Double) vRowTemp.get(3)).longValue());

                        vObj.add(oRec);                              
                    }
                    nTam = vObj.size();
                    arrRet = new DetalleRecetarioColectivo[nTam];
                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
                }
            }
            return arrRet;
        }
        
        
        
	public PersonalHospitalario getQuienSurte() {
	return oQuienSurte;
	}

	public void setQuienSurte(PersonalHospitalario valor) {
	oQuienSurte=valor;
	}

	public Parametrizacion getUnidadMedida() {
	return oUnidadMedida;
	}

	public void setUnidadMedida(Parametrizacion valor) {
	oUnidadMedida=valor;
	}

    @Override
    public ServicioCobrable getServicioCobrable() {
        return this.oMedicamento;
    }
    
    @Override
    public void setServicioCobrable(ServicioCobrable oServicioCobrable) {
            this.oMedicamento = (Medicamento) oServicioCobrable;
        }


    public int getCantidadSol() {
        return nCantidad;
    }

    public void setCantidadSol(int valor) {
        nCantidad = valor;
    }
    
    public short getCantSolicitadaArea() {
        return nCantSolicitadaArea;
    }

    public void setCantSolicitadaArea(short nCantSolicitadaArea) {
        this.nCantSolicitadaArea = nCantSolicitadaArea;
    }
    
    public RecetarioColectivo getRecetario(){
        return this.oRecetario;
    }
    public void setRecetario(RecetarioColectivo val){
        this.oRecetario = val;
    }
    
    //Para facilitar consulta de totales, NO USAR
    public long getCantidadSolTot() {
        return nCantidadSolTot;
    }

    public void setCantidadSolTot(long nCantidadSolTot) {
        this.nCantidadSolTot = nCantidadSolTot;
    }

    public long getCantidadSurTot() {
        return nCantidadSurTot;
    }

    public void setCantidadSurTot(long nCantidadSurTot) {
        this.nCantidadSurTot = nCantidadSurTot;
    }
    
    
    //Por EL
    public Medicamento getMedicamento(){
        return this.oMedicamento;
    }
} 

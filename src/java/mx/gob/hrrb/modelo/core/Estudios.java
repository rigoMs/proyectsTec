package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.capasits.PacienteCapasits;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 * Objetivo: 
 * @author : Pedro, Pablo
 * @version: 1.0
*/

public class Estudios extends ServicioCobrable implements Serializable{
protected AccesoDatos oAD;
protected AreaServicioHRRB oAreaServHrrb;
protected Parametrizacion oClasificacion;
protected String sClasifDeEstudio;
protected String sClaveStudio;
protected Parametrizacion oImagenRegion;
protected String sConcepto;
protected String sObservaciones;
protected Date dFechaEstudio;
        private PacienteCapasits oPac;
        private Expediente oExp;
        private String sUsuario;
        protected int nClaveInterna;
        private boolean bSroja;
        private boolean bFosfatasa;
        private boolean bSblanca;
        private boolean bFecriles;
        private boolean bPlaquetas;
        private boolean bLcr;
        private boolean bReticulositos;
        private boolean bSodiop;
        private boolean bSedimentacion;
        private boolean bCalcio;
        private boolean bTp;
        private boolean bOrina;
        private boolean bTpt;
        private boolean bGch;
        private boolean bGlucosa;
        private boolean bBetagch;
        private boolean bUrea;
        private boolean bCoprop;
        private boolean bCreatinina;
        private boolean bBilirrubina;
        private boolean bTgo;
        private boolean bCv;
        private boolean bTgp;
        private boolean bCd4;
        private boolean bDhl;
        private boolean bCloro;
        private boolean bGpoyhr;
        private String sCitobact;
        private String sCultivo;
        private String sOtros;
        private String sSeRoja;
        private String sFosfatasaAl;
        private String sSeBlanca;
        private String sReFebriles;
        private String sPlaquetas2;
        private String sCitoLcr;
        private String sReticulositos8;
        private String sSodioyP;
        private String sVSedimentacion;
        private String sCalcio2;
        private String sTp1;
        private String sGOrina;
        private String sTpt2;
        private String sGchCu;
        private String sGlucosa1;
        private String sBetagchcua;
        private String sUrea4;
        private String sCopropara;
        private String sCreatinina6;
        private String sBilirrubina8;
        private String sTgo1;
        private String sCarv;
        private String sTgp2;
        private String sCd40;
        private String sDhl6;
        private String sCloro3;
        private String sGpoyhr6;
        private String sD4;
        private String sCa;
        List<String> lCadena;
        private List<Estudios> lClaveEst;
        
    public Estudios() {
    HttpServletRequest req;
        oPac=new PacienteCapasits();
        oExp=new Expediente();
        oImagenRegion = new Parametrizacion();
        oAreaServHrrb = new AreaServicioHRRB();
        req=(HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
        if (req.getSession().getAttribute("oFirm") != null) {
            sUsuarioFirmado = ((Firmado)req.getSession().getAttribute(
                    "oFirm")).getUsu().getIdUsuario();
        }
    }
        
        @Override
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Estudios.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveEstudios();"; 
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
	public Estudios[] buscarTodos() throws Exception{
	Estudios arrRet[]=null, oEstudios=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosEstudios();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Estudios[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
        public boolean buscarEstudiosDePaciente() throws Exception{
	Estudios arrRet[]=null, oEstudios=null;
	ArrayList rst = null;
	String sQuery = "";
        boolean ban=false;
	int i=0;
		sQuery = "SELECT * FROM buscaEstudiosDePaciente('"+getFechaEstudio()+"',"+getPac().getFolioPaciente()+");"; 
		oAD=new AccesoDatos(); System.out.println(sQuery);
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size() > 0) { 
			arrRet = new Estudios[rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oEstudios=new Estudios();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEstudios.setClaveInterna(((Double)vRowTemp.get(0)).intValue());
                            oEstudios.setFechaEstudio((Date)vRowTemp.get(1));
                            oEstudios.setObservaciones((String)vRowTemp.get(2));
                            if(oEstudios.getClaveInterna()==1) {setSroja(true); setSeRoja("X");} if(oEstudios.getClaveInterna()==2){setSblanca(true); setSeBlanca("X");} if(oEstudios.getClaveInterna()==3){ setPlaquetas(true); setPlaquetas2("X");}if(oEstudios.getClaveInterna()==4){ setReticulositos(true); setReticulositos8("X");} if(oEstudios.getClaveInterna()==5) {setSedimentacion(true); setVSedimentacion("X");}
                            if(oEstudios.getClaveInterna()==6){ setGpoyhr(true); setGpoyhr6("X");} if(oEstudios.getClaveInterna()==7) {setTp(true); setTp1("X");} if(oEstudios.getClaveInterna()==8) {setTpt(true); setTpt2("X");} if(oEstudios.getClaveInterna()==9) {setGlucosa(true); setGlucosa1("X");} if(oEstudios.getClaveInterna()==10) {setUrea(true); setUrea4("X");}
                            if(oEstudios.getClaveInterna()==11) {setCreatinina(true); setCreatinina6("X");} if(oEstudios.getClaveInterna()==12){ setBilirrubina(true); setBilirrubina8("X");}if(oEstudios.getClaveInterna()==13) {setTgo(true); setTgo1("X");} if(oEstudios.getClaveInterna()==14){ setTgp(true); setTgp2("X");} if(oEstudios.getClaveInterna()==15){ setDhl(true); setDhl6("X");}
                            if(oEstudios.getClaveInterna()==16){ setFosfatasa(true); setFosfatasaAl("X");} if(oEstudios.getClaveInterna()==17){setFebriles(true); setReFebriles("X");} if(oEstudios.getClaveInterna()==18) {setLcr(true); setCitoLcr("X");} if(oEstudios.getClaveInterna()==19){setSodiop(true); setSodioyP("X");} if(oEstudios.getClaveInterna()==20) {setCloro(true); setCloro3("X");}
                            if(oEstudios.getClaveInterna()==21){ setCalcio(true); setCalcio2("X");} if(oEstudios.getClaveInterna()==22) {setOrina(true); setGOrina("X");} if(oEstudios.getClaveInterna()==23) {setGch(true); setGchCu("X");} if(oEstudios.getClaveInterna()==24){ setBetagch(true); setBetagchcua("X");} if(oEstudios.getClaveInterna()==25){ setCoprop(true); setCopropara("X");}
                            if(oEstudios.getClaveInterna()==26) setCultivo(oEstudios.getObservaciones()); if(oEstudios.getClaveInterna()==27) setCitobact(oEstudios.getObservaciones()); if(oEstudios.getClaveInterna()==28) {setCd4(true); setCd40("CD4"); setD4("X");} if(oEstudios.getClaveInterna()==29) {setCv(true); setCarv("CARGA VIRAL"); setCa("X");} if(oEstudios.getClaveInterna()==30) setOtros(oEstudios.getObservaciones());
			} 
                        ban=true;
		} 
		return ban; 
	} 
        
        
        public Estudios[] buscarEstudiosPorServicio(int nClaveArea) throws Exception{
            Estudios arrRet[]=null, oEstudios=null;
            ArrayList rst=null;
            ArrayList<Estudios> vObj=null;
            String sQuery="";
            int i=0, nTam=0;
            sQuery = "SELECT * FROM buscarEstudiosServicios("+ nClaveArea +"::smallint);";
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst != null && rst.size() > 0){
                vObj= new ArrayList<Estudios>();
                for(i=0;i<rst.size();i++){
                    oEstudios = new Estudios();
                    ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                    oEstudios.setClaveStudio((String)vRowTemp.get(0));
                    oEstudios.setDescripcion((String)vRowTemp.get(1));
                    vObj.add(oEstudios);
                }
                nTam=vObj.size();
                arrRet = new Estudios[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
        
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Estudios.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaEstudios();"; 
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
        
        public int insertarEstudioPac() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Estudios.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPaciente_Estudios('"+sUsuario+"',"+getClaveInterna()+"::smallint,'"+getFechaEstudio()+"',"+getPac().getFolioPaciente()+","+(getObservaciones()==null?"null":"'"+getObservaciones()+"'")+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
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
		 if( this==null){   //completar llave
			throw new Exception("Estudios.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEstudios();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Estudios.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaEstudios();"; 
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
        
        public int eliminarEstudioDePaciente() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Estudios.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaEstudioDePaciente('"+sUsuario+"','"+getFechaEstudio()+"',"+getPac().getFolioPaciente()+");"; 
			oAD=new AccesoDatos(); System.out.println(sQuery);
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
        
        public Estudios[] buscarEstudioPatologia(String sDescripcion) throws Exception{
            Estudios arrRet[]=null, oEst=null;
            ArrayList<Estudios> vObj=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0, nTam=0;
            if(sDescripcion.equals("")){
                throw new Exception("EstudioPatologia.buscarEstudioPatologia: error, faltan datos");
            }else{
                sQuery = "SELECT * FROM buscaProcedimientosPatologia('"+ sDescripcion +"');";
                System.out.println(sQuery);
                oAD = new AccesoDatos();
                if(oAD.conectar()){
                    rst = oAD.ejecutarConsulta(sQuery);
                    oAD.desconectar();
                }
                if(rst != null){
                    vObj = new ArrayList<Estudios>();
                    for(i=0;i<rst.size();i++){
                        oEst = new Estudios();
                        ArrayList<Object> vRowTemp = (ArrayList)rst.get(i);
                        oEst.setConcepto((String)vRowTemp.get(0));
                        vObj.add(oEst);
                    }
                    nTam = vObj.size();
                    arrRet = new Estudios[nTam];
                    
                    for(i=0; i<nTam;i++){
                        arrRet[i] = vObj.get(i);
                    }
                } 
            }
            return arrRet;
        }
        
        public Estudios[] buscarClavesEstudios (String sClave) throws Exception{
            Estudios arrRet[]=null, oEst=null;
            ArrayList<Estudios> vObj=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0, nTam=0;
            sQuery = "SELECT * FROM buscarClaveEstudio('"+ sClave +"')";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null){
                vObj = new ArrayList<Estudios>();
                for(i=0; i<rst.size();i++){
                    oEst = new Estudios();
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    oEst.setClaveInterna(((Double)vRowTemp.get(0)).intValue());
                    oEst.setClaveStudio((String)vRowTemp.get(1));
                    vObj.add(oEst);
                }
                
                nTam = vObj.size();
                arrRet = new Estudios[nTam];
                
                for(i=0;i<nTam;i++){
                    arrRet[i] = vObj.get(i);
                }
            }
            return arrRet;
        }
        
        public List<Estudios> getListaEstudios(String sDesc) throws Exception{
            List<Estudios> lListaEst = null;
            try{
                lListaEst = new ArrayList<Estudios>(Arrays.asList(new Estudios().buscarEstudioPatologia(sDesc)));
            }catch(Exception ex){
                Logger.getLogger(Estudios.class.getName()).log(Level.SEVERE, null, ex);
            }
            return lListaEst;
        }
        public List<String> complet(String sText) throws Exception{
           lCadena = new ArrayList<String>();
           List<Estudios> list = getListaEstudios(sText);
            for (Estudios list1 : list) {
                if (sp_ascii(list1.getConcepto()).contains(sText)) {
                    lCadena.add(list1.getConcepto());
                }
            }
           return lCadena; 
        } 
        public String sp_ascii(String sCad) {
            String cad = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
            String cascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
            String sCadSalida = sCad;
            for(int i = 0; i<cad.length();i++){
                sCadSalida = sCadSalida.replace(cad.charAt(i), cascii.charAt(i));
            }
            return sCadSalida;
        }
        
        public List<Estudios> getClavesEstudios(String sCad){
            setClaveEst(null);
            try{
                setClaveEst(new ArrayList<Estudios>(Arrays.asList(
                        new Estudios().buscarClavesEstudios(sCad))));
            }catch(Exception ex){
                Logger.getLogger(Estudios.class.getName()).log(Level.SEVERE, null, ex);
            }
            return getClaveEst();
        }
        
        
        public String claveEst(){
            String c="";
            int d=0;
            if(getClaveEst()==null || getClaveEst().isEmpty()){
                c="";
            }else{
                c = getClaveEst().get(0).getClaveStudio();
                d = getClaveEst().get(0).getClaveInterna();
                setClaveStudio(c);
                setClaveInterna(d);
                System.out.println("Clave interna " + getClaveInterna());
            }
            return c;
        }
        
        
    
    public List<Estudios> getClaveEst() {
        return lClaveEst;
    }

    public void setClaveEst(List<Estudios> lClaveEst) {
        this.lClaveEst = lClaveEst;
    }
        
	public String getClaveStudio() {
	return sClaveStudio;
	}

	public void setClaveStudio(String valor) {
	sClaveStudio=valor;
	}

	public String getConcepto() {
	return sConcepto;
	}

	public void setConcepto(String valor) {
	sConcepto=valor;
	}

        public PacienteCapasits getPac() {
        return oPac;
        }

        public void setPac(PacienteCapasits oPac) {
        this.oPac = oPac;
        }
        
        public Expediente getExp(){
            return oExp;
        }
        
        public void setExp(Expediente valor){
            this.oExp=valor;
        }

        public Date getFechaEstudio() {
        return dFechaEstudio;
        }

        public void setFechaEstudio(Date dFechaEstudio) {
        this.dFechaEstudio = dFechaEstudio;
        }

        public String getObservaciones() {
        return sObservaciones;
        }

        public void setObservaciones(String sObservaciones) {
        this.sObservaciones = sObservaciones;
        }

        public int getClaveInterna() {
        return nClaveInterna;
        }

        public void setClaveInterna(int nClaveInterna) {
        this.nClaveInterna = nClaveInterna;
        }
        
        public boolean getSroja() {
        return bSroja;
    }

    public void setSroja(boolean bSroja) {
        this.bSroja = bSroja;
    }

    public boolean getFosfatasa() {
        return bFosfatasa;
    }

    public void setFosfatasa(boolean bFosfatasa) {
        this.bFosfatasa = bFosfatasa;
    }

    public boolean getSblanca() {
        return bSblanca;
    }

    public void setSblanca(boolean bSblanca) {
        this.bSblanca = bSblanca;
    }

    public boolean getFebriles() {
        return bFecriles;
    }

    public void setFebriles(boolean bFecriles) {
        this.bFecriles = bFecriles;
    }

    public boolean getPlaquetas() {
        return bPlaquetas;
    }

    public void setPlaquetas(boolean bPlaquetas) {
        this.bPlaquetas = bPlaquetas;
    }

    public boolean getLcr() {
        return bLcr;
    }

    public void setLcr(boolean bLcr) {
        this.bLcr = bLcr;
    }

    public boolean getReticulositos() {
        return bReticulositos;
    }

    public void setReticulositos(boolean bReticulositos) {
        this.bReticulositos = bReticulositos;
    }

    public boolean getSodiop() {
        return bSodiop;
    }

    public void setSodiop(boolean bSodiop) {
        this.bSodiop = bSodiop;
    }

    public boolean getSedimentacion() {
        return bSedimentacion;
    }

    public void setSedimentacion(boolean bSedimentacion) {
        this.bSedimentacion = bSedimentacion;
    }

    public boolean getCalcio() {
        return bCalcio;
    }

    public void setCalcio(boolean bCalcio) {
        this.bCalcio = bCalcio;
    }

    public boolean getTp() {
        return bTp;
    }

    public void setTp(boolean bTp) {
        this.bTp = bTp;
    }

    public boolean getOrina() {
        return bOrina;
    }

    public void setOrina(boolean bOrina) {
        this.bOrina = bOrina;
    }

    public boolean getTpt() {
        return bTpt;
    }

    public void setTpt(boolean bTpt) {
        this.bTpt = bTpt;
    }

    public boolean getGch() {
        return bGch;
    }

    public void setGch(boolean bGch) {
        this.bGch = bGch;
    }

    public boolean getGlucosa() {
        return bGlucosa;
    }

    public void setGlucosa(boolean bGlucosa) {
        this.bGlucosa = bGlucosa;
    }

    public boolean getBetagch() {
        return bBetagch;
    }

    public void setBetagch(boolean bBetagch) {
        this.bBetagch = bBetagch;
    }

    public boolean getUrea() {
        return bUrea;
    }

    public void setUrea(boolean bUrea) {
        this.bUrea = bUrea;
    }

    public boolean getCoprop() {
        return bCoprop;
    }

    public void setCoprop(boolean bCoprop) {
        this.bCoprop = bCoprop;
    }

    public boolean getCreatinina() {
        return bCreatinina;
    }

    public void setCreatinina(boolean bCreatinina) {
        this.bCreatinina = bCreatinina;
    }

    public boolean getBilirrubina() {
        return bBilirrubina;
    }

    public void setBilirrubina(boolean bBilirrubina) {
        this.bBilirrubina = bBilirrubina;
    }

    public boolean getTgo() {
        return bTgo;
    }

    public void setTgo(boolean bTgo) {
        this.bTgo = bTgo;
    }

    public boolean getCv() {
        return bCv;
    }

    public void setCv(boolean bCv) {
        this.bCv = bCv;
    }

    public boolean getTgp() {
        return bTgp;
    }

    public void setTgp(boolean bTgp) {
        this.bTgp = bTgp;
    }

    public boolean getCd4() {
        return bCd4;
    }

    public void setCd4(boolean bCd4) {
        this.bCd4 = bCd4;
    }

    public boolean getDhl() {
        return bDhl;
    }

    public void setDhl(boolean bDhl) {
        this.bDhl = bDhl;
    }

    public boolean getCloro() {
        return bCloro;
    }

    public void setCloro(boolean valor) {
        this.bCloro = valor;
    }
    
    public boolean getGpoyhr() {
        return bGpoyhr;
    }

    public void setGpoyhr(boolean valor) {
        this.bGpoyhr = valor;
    }

    public String getCitobact() {
        return sCitobact;
    }

    public void setCitobact(String sCitobact) {
        this.sCitobact = sCitobact;
    }

    public String getCultivo() {
        return sCultivo;
    }

    public void setCultivo(String sCultivo) {
        this.sCultivo = sCultivo;
    }

    public String getOtros() {
        return sOtros;
    }

    public void setOtros(String sOtros) {
        this.sOtros = sOtros;
    } 

    public String getSeRoja() {
        return sSeRoja;
    }

    public void setSeRoja(String sSeRoja) {
        this.sSeRoja = sSeRoja;
    }

    public String getFosfatasaAl() {
        return sFosfatasaAl;
    }

    public void setFosfatasaAl(String sFosfatasaAl) {
        this.sFosfatasaAl = sFosfatasaAl;
    }

    public String getSeBlanca() {
        return sSeBlanca;
    }

    public void setSeBlanca(String sSeBlanca) {
        this.sSeBlanca = sSeBlanca;
    }

    public String getReFebriles() {
        return sReFebriles;
    }

    public void setReFebriles(String sReFebriles) {
        this.sReFebriles = sReFebriles;
    }

    public String getPlaquetas2() {
        return sPlaquetas2;
    }

    public void setPlaquetas2(String sPlaquetas2) {
        this.sPlaquetas2 = sPlaquetas2;
    }

    public String getCitoLcr() {
        return sCitoLcr;
    }

    public void setCitoLcr(String sCitoLcr) {
        this.sCitoLcr = sCitoLcr;
    }

    public String getReticulositos8() {
        return sReticulositos8;
    }

    public void setReticulositos8(String sReticulositos8) {
        this.sReticulositos8 = sReticulositos8;
    }

    public String getSodioyP() {
        return sSodioyP;
    }

    public void setSodioyP(String sSodioyP) {
        this.sSodioyP = sSodioyP;
    }

    public String getVSedimentacion() {
        return sVSedimentacion;
    }

    public void setVSedimentacion(String sVSedimentacion) {
        this.sVSedimentacion = sVSedimentacion;
    }

    public String getCalcio2() {
        return sCalcio2;
    }

    public void setCalcio2(String sCalcio2) {
        this.sCalcio2 = sCalcio2;
    }

    public String getTp1() {
        return sTp1;
    }

    public void setTp1(String sTp1) {
        this.sTp1 = sTp1;
    }

    public String getGOrina() {
        return sGOrina;
    }

    public void setGOrina(String sGOrina) {
        this.sGOrina = sGOrina;
    }

    public String getTpt2() {
        return sTpt2;
    }

    public void setTpt2(String sTpt2) {
        this.sTpt2 = sTpt2;
    }

    public String getGchCu() {
        return sGchCu;
    }

    public void setGchCu(String sGchCu) {
        this.sGchCu = sGchCu;
    }

    public String getGlucosa1() {
        return sGlucosa1;
    }

    public void setGlucosa1(String sGlucosa1) {
        this.sGlucosa1 = sGlucosa1;
    }

    public String getBetagchcua() {
        return sBetagchcua;
    }

    public void setBetagchcua(String sBetagchcua) {
        this.sBetagchcua = sBetagchcua;
    }

    public String getUrea4() {
        return sUrea4;
    }

    public void setUrea4(String sUrea4) {
        this.sUrea4 = sUrea4;
    }

    public String getCopropara() {
        return sCopropara;
    }

    public void setCopropara(String sCopropara) {
        this.sCopropara = sCopropara;
    }

    public String getCreatinina6() {
        return sCreatinina6;
    }

    public void setCreatinina6(String sCreatinina6) {
        this.sCreatinina6 = sCreatinina6;
    }

    public String getBilirrubina8() {
        return sBilirrubina8;
    }

    public void setBilirrubina8(String sBilirrubina8) {
        this.sBilirrubina8 = sBilirrubina8;
    }

    public String getTgo1() {
        return sTgo1;
    }

    public void setTgo1(String sTgo1) {
        this.sTgo1 = sTgo1;
    }

    public String getCarv() {
        return sCarv;
    }

    public void setCarv(String sCarv) {
        this.sCarv = sCarv;
    }

    public String getTgp2() {
        return sTgp2;
    }

    public void setTgp2(String sTgp2) {
        this.sTgp2 = sTgp2;
    }

    public String getCd40() {
        return sCd40;
    }

    public void setCd40(String sCd40) {
        this.sCd40 = sCd40;
    }

    public String getDhl6() {
        return sDhl6;
    }

    public void setDhl6(String sDhl6) {
        this.sDhl6 = sDhl6;
    }

    public String getCloro3() {
        return sCloro3;
    }

    public void setCloro3(String sCloro3) {
        this.sCloro3 = sCloro3;
    }

    public String getGpoyhr6() {
        return sGpoyhr6;
    }

    public void setGpoyhr6(String sGpoyhr6) {
        this.sGpoyhr6 = sGpoyhr6;
    }  
    
    public String getD4() {
        return sD4;
    }

    public void setD4(String valor) {
        this.sD4 = valor;
    }
    
    public String getCa() {
        return sCa;
    }

    public void setCa(String valor) {
        this.sCa = valor;
    }
    
    public AreaServicioHRRB getAreaServHrrb() {
        return oAreaServHrrb;
    }

    public void setAreaServHrrb(AreaServicioHRRB oAreaServHrrb) {
        this.oAreaServHrrb = oAreaServHrrb;
    }
    
    public Parametrizacion getClasificacion() {
        return oClasificacion;
    }

    public void setClasificacion(Parametrizacion oClasificacion) {
        this.oClasificacion = oClasificacion;
    }

    public Parametrizacion getImagenRegion() {
        return oImagenRegion;
    }

    public void setImagenRegion(Parametrizacion oImagenRegion) {
        this.oImagenRegion = oImagenRegion;
    }

    public String getClasifDeEstudio() {
        return sClasifDeEstudio;
    }

    public void setClasifDeEstudio(String sClasifDeEstudio) {
        this.sClasifDeEstudio = sClasifDeEstudio;
    }
} 

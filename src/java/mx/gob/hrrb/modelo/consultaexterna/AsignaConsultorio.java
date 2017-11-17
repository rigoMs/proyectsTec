package mx.gob.hrrb.modelo.consultaexterna;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;
import javax.faces.application.FacesMessage;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Consultorio;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.core.Turno;

/**
 * Objetivo: 
 * @author : Javi
 * @version: 1.0
*/

public class AsignaConsultorio implements Serializable{
	private AccesoDatos oAD;
	private int nCitas1eraVez;
	private int nCitasSubs;
	private int nCitasTotales;
	private Turno oTurno;
        private List<String> lDias;
        private AreaServicioHRRB oAreaServicio;
        private PersonalHospitalario oPH;
        private Cita oCita;
        private Consultorio oCons;
        private String horaLun, horaMar, horaMie, horaJue, horaVie;
        private String totLun, totMar, totMie, totJue, totVie;
        private Firmado oFirm;
        private String sUsuario;
        private HttpServletRequest httpServletRequest;
        private FacesContext faceContext;
        private FacesMessage facesMessage;
        private int nMaximo;
        private Date dFechaConsulta;
        private int nClaveEpisodio;
        private String tipoPac;
        private int nCitados;
        private String sFechaMensual;
        private Date dNuevaFecha;
        private int atendidos;
        private int faltantes;
        private int atendidosPrim;
        private int faltantesPrim;
        private int atendidosSub;
        private int faltantesSub;
        private int nCon5;
        private int total;
        private AsignaConsultorio arrRetC5[];

        public AsignaConsultorio(){
            lDias=new ArrayList<String>();
	    nCitasSubs=0;
            nCitados=0;
            total=0;
            oAreaServicio=new AreaServicioHRRB();
            oPH=new PersonalHospitalario();
            oCons=new Consultorio();
            oCita=new Cita();
            horaLun=horaMar=horaMie=horaJue=horaVie="";
            totLun=totMar=totMie=totJue=totVie="";
            oFirm=new Firmado();
            dFechaConsulta=new Date();
            faceContext=FacesContext.getCurrentInstance();
            httpServletRequest=(HttpServletRequest)faceContext.getExternalContext().getRequest();
            if(httpServletRequest.getSession().getAttribute("oFirm")!=null)
            {
            oFirm=(Firmado)httpServletRequest.getSession().getAttribute("oFirm");
            sUsuario=oFirm.getUsu().getIdUsuario();
            }
            dNuevaFecha=null;
            atendidos=0;
            faltantes=0;
            atendidosPrim=0;
            atendidosSub=0;
            faltantesPrim=0;
            faltantesSub=0;
            nCon5=0;
            arrRetC5=null;
            oTurno=new Turno();
        }
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AsignaConsultorio.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveAsignaConsultorio();"; 
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
        //**********************************************************************************************
        public boolean buscaMaxPrimSubs(String datos[], String dia) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AsignaConsultorio.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaMaxTipoCita("+datos[0]+", "+datos[1]+"::smallint, "+datos[2]+"::smallint, '"+datos[4].substring(0, 3)+"', '"+dia.toUpperCase()+"');";
                        oAD=new AccesoDatos();
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setCitas1eraVez(((Double)vRowTemp.get(0)).intValue());
                                setCitasSubs(((Double)vRowTemp.get(1)).intValue());
				bRet = true;
			}else{
                            setCitas1eraVez(0);
                            setCitasSubs(0);
                        }
		} 
		return bRet; 
	}
        //**********************************************************************************************
        public boolean buscaRepetidos(int consul, int tarjeta, String turno, String dia) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AsignaConsultorio.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaAsignacionRepetida("+tarjeta+", "+consul+"::smallint, '"+turno.substring(0, 3)+"', '"+dia+"');";
                        oAD=new AccesoDatos();
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() > 0) {
				bRet = true;
			}
		} 
		return bRet; 
	}
        //**********************************************************************************************
        //Busca todos los registros no repetidos en AsignaConsultorio 
        public AsignaConsultorio[] buscarProgramacion(String turno) throws Exception{
        AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
        
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaProgCons('"+turno+"');";
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAsignaConsultorio.getCons().setNoConsultorio(((Double)vRowTemp.get(0)).intValue());
                            oAsignaConsultorio.getPH().setNoTarjeta(((Double)vRowTemp.get(1)).intValue());
                            oAsignaConsultorio.getPH().setApPaterno((String)vRowTemp.get(2));
                            oAsignaConsultorio.getPH().setApMaterno((String)vRowTemp.get(3));
                            oAsignaConsultorio.getPH().setNombres((String)vRowTemp.get(4));
                            oAsignaConsultorio.getAreaServicio().setDescripcion((String)vRowTemp.get(5));
                            oAsignaConsultorio.getAreaServicio().setClave(((Double)vRowTemp.get(6)).intValue());
                            oAsignaConsultorio.setCitas1eraVez(((Double)vRowTemp.get(8)).intValue());
                            oAsignaConsultorio.setCitasSubs(((Double)vRowTemp.get(9)).intValue());
                            oAsignaConsultorio.setCitasTotales(oAsignaConsultorio.getCitas1eraVez()+oAsignaConsultorio.getCitasSubs());
                            oAsignaConsultorio.setHoraLun(buscaHoraProgCons(oAsignaConsultorio, "LUNES",turno.substring(0, 3)));
                            oAsignaConsultorio.setHoraMar(buscaHoraProgCons(oAsignaConsultorio, "MARTES",turno.substring(0, 3)));
                            oAsignaConsultorio.setHoraMie(buscaHoraProgCons(oAsignaConsultorio, "MIÉRCOLES",turno.substring(0, 3)));
                            oAsignaConsultorio.setHoraJue(buscaHoraProgCons(oAsignaConsultorio, "JUEVES",turno.substring(0, 3)));
                            oAsignaConsultorio.setHoraVie(buscaHoraProgCons(oAsignaConsultorio, "VIERNES",turno.substring(0, 3)));
                            vObj.add(oAsignaConsultorio);
			}
                        nTam = vObj.size();
                    arrRet = new AsignaConsultorio[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
        }
        //**********************************************************************************************
        public AsignaConsultorio buscarTodos(int noConsul, int noTarjeta, int Area, String t) throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        String l, m, x, j, v;
        l=m=x=j=v="";
        
	int i=0;
		sQuery = "select * from buscaProgramacionCons("+noConsul+"::smallint, "+noTarjeta+", "+Area+"::smallint);"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            if (((String)vRowTemp.get(15)).substring(0, 3).compareTo(t)==0){
                            oAsignaConsultorio=new AsignaConsultorio();
                            oAsignaConsultorio.getCons().setNoConsultorio(((Double)vRowTemp.get(0)).intValue());
                            oAsignaConsultorio.getPH().setNoTarjeta(((Double)vRowTemp.get(1)).intValue());
                            oAsignaConsultorio.getPH().setApPaterno((String)vRowTemp.get(2));
                            oAsignaConsultorio.getPH().setApMaterno((String)vRowTemp.get(3));
                            oAsignaConsultorio.getPH().setNombres((String)vRowTemp.get(4));
                            oAsignaConsultorio.getAreaServicio().setDescripcion((String)vRowTemp.get(5));
                            oAsignaConsultorio.getAreaServicio().setClave(((Double)vRowTemp.get(6)).intValue());
                            oAsignaConsultorio.setHoraLun((String)vRowTemp.get(7));
                            oAsignaConsultorio.setHoraMar((String)vRowTemp.get(8));
                            oAsignaConsultorio.setHoraMie((String)vRowTemp.get(9));
                            oAsignaConsultorio.setHoraJue((String)vRowTemp.get(10));
                            oAsignaConsultorio.setHoraVie((String)vRowTemp.get(11));
                            oAsignaConsultorio.setCitas1eraVez(((Double)vRowTemp.get(12)).intValue());
                            oAsignaConsultorio.setCitasSubs(((Double)vRowTemp.get(13)).intValue());
                            oAsignaConsultorio.setCitasTotales(((Double)vRowTemp.get(14)).intValue());
                            t=((String)vRowTemp.get(15));
                            //System.out.println(t);
			}
                        }
		} 
		return oAsignaConsultorio; 
	}
        //**********************************************************************************************
        public String buscaHoraProgCons(AsignaConsultorio oAsig, String dia, String turno) throws Exception{
	ArrayList rst = null;
        String horario="";
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaHoraProgCons('"+dia+"', "+oAsig.getPH().getNoTarjeta()+", "+oAsig.getCons().getNoConsultorio()+"::smallint, "+oAsig.getAreaServicio().getClave()+"::smallint, "+oAsig.getCitas1eraVez()+"::smallint, "+oAsig.getCitasSubs()+"::smallint, '"+turno+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
			for (i = 0; i < rst.size(); i++) {
                            ArrayList vRowTemp = (ArrayList)rst.get(i);                           
                            horario=(String)vRowTemp.get(0)+" - "+(String)vRowTemp.get(1);
                            if(dia.compareTo("LUNES")==0)
                                oAsig.setTotLun(oAsig.getCitas1eraVez()+oAsig.getCitasSubs()+" CITAS");
                            if(dia.compareTo("MARTES")==0)
                                oAsig.setTotMar(oAsig.getCitas1eraVez()+oAsig.getCitasSubs()+" CITAS");
                            if(dia.compareTo("MIÉRCOLES")==0)
                                oAsig.setTotMie(oAsig.getCitas1eraVez()+oAsig.getCitasSubs()+" CITAS");
                            if(dia.compareTo("JUEVES")==0)
                                oAsig.setTotJue(oAsig.getCitas1eraVez()+oAsig.getCitasSubs()+" CITAS");
                            if(dia.compareTo("VIERNES")==0)
                                oAsig.setTotVie(oAsig.getCitas1eraVez()+oAsig.getCitasSubs()+" CITAS");
                        }
		} 
		return horario; 
	}
        //**********************************************************************************************
        public AsignaConsultorio[] buscaCitasPorMedico() throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        int i=0, nTam=0;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formato=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat formato2=new SimpleDateFormat("EEEE");
        Calendar Cal=Calendar.getInstance();
        Cal.setTime(getFechaConsulta());
        String diaTexto="";
        int numDia=Cal.get(Calendar.DAY_OF_WEEK);
        switch(numDia){
            case 1: diaTexto="DOMINGO"; break;
            case 2: diaTexto="LUNES"; break;
            case 3: diaTexto="MARTES"; break;
            case 4: diaTexto="MIÉRCOLES"; break;
            case 5: diaTexto="JUEVES"; break;
            case 6: diaTexto="VIERNES"; break;
            case 7: diaTexto="SÁBADO"; break;
        }
        
		sQuery = "select * from buscaCitasPorMedico('"+df.format(getFechaConsulta())+"', '"+diaTexto+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAsignaConsultorio.getCons().setNoConsultorio(((Double)vRowTemp.get(0)).intValue());
                            oAsignaConsultorio.getPH().setNoTarjeta(((Double)vRowTemp.get(1)).intValue());
                            oAsignaConsultorio.getPH().setApPaterno((String)vRowTemp.get(2));
                            oAsignaConsultorio.getPH().setApMaterno((String)vRowTemp.get(3));
                            oAsignaConsultorio.getPH().setNombres((String)vRowTemp.get(4));
                            oAsignaConsultorio.getAreaServicio().setClave(((Double)vRowTemp.get(5)).intValue());
                            oAsignaConsultorio.getAreaServicio().setDescripcion((String)vRowTemp.get(6));
                            oAsignaConsultorio.setMaximo(((Double)vRowTemp.get(7)).intValue());
                            oAsignaConsultorio.setCitas1eraVez(cuentaCitas(oAsignaConsultorio.getPH().getNoTarjeta(),oAsignaConsultorio.getAreaServicio().getClave(), "S", formato.format(getFechaConsulta()),((String)vRowTemp.get(8))));
                            oAsignaConsultorio.setCitasSubs(cuentaCitas(oAsignaConsultorio.getPH().getNoTarjeta(),oAsignaConsultorio.getAreaServicio().getClave(), "N", formato.format(getFechaConsulta()),((String)vRowTemp.get(8))));
                            oAsignaConsultorio.setCitasTotales(oAsignaConsultorio.getCitas1eraVez()+oAsignaConsultorio.getCitasSubs());
                            oAsignaConsultorio.getTurno().setClave((String)vRowTemp.get(8));
                            if (oAsignaConsultorio.getCitasTotales()!=0)
                            vObj.add(oAsignaConsultorio);
			}
                    nTam = vObj.size();
                    arrRet = new AsignaConsultorio[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		}
                Calendar f=new GregorianCalendar();
                sFechaMensual="1"+"/"+(f.get(Calendar.MONTH)+1)+"/"+f.get(Calendar.YEAR);
		return arrRet; 
	}
        //**********************************************************************************************
        public AsignaConsultorio[] buscaCitasMensuales(String dia) throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        int i=0, nTam=0;
	SimpleDateFormat df =  new SimpleDateFormat("yyyy-MM-dd");
        Calendar f=new GregorianCalendar();
        int mes=f.get(Calendar.MONTH);
        Date datemen=null;
        String month=(mes+1)+"";
        if (month.length()<2)
            month="0"+month;
        sFechaMensual=dia+"/"+month+"/"+f.get(Calendar.YEAR);
        String fechaEnvio=f.get(Calendar.YEAR)+"-"+month+"-"+dia;
        String valida=muestraDia(Integer.parseInt(dia));
        SimpleDateFormat for2=new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat formato2=new SimpleDateFormat("EEEE");
        try {   datemen = for2.parse(sFechaMensual);
	} catch (Exception e) {
		e.printStackTrace();
	}      
        Calendar Cal=Calendar.getInstance();
        String diaTexto="";
        if(datemen!=null){
        Cal.setTime(datemen);
        int numDia=Cal.get(Calendar.DAY_OF_WEEK);
        switch(numDia){
            case 1: diaTexto="DOMINGO"; break;
            case 2: diaTexto="LUNES"; break;
            case 3: diaTexto="MARTES"; break;
            case 4: diaTexto="MIÉRCOLES"; break;
            case 5: diaTexto="JUEVES"; break;
            case 6: diaTexto="VIERNES"; break;
            case 7: diaTexto="SÁBADO"; break;
        }
        }else
            diaTexto="";
		sQuery = "select * from buscaCitasPorMedico('"+sFechaMensual+"', '"+diaTexto+"');"; 
                oAD=new AccesoDatos();
                if(valida.compareTo("")==0){
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}}
		if (rst != null && rst.size()>0) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAsignaConsultorio.getCons().setNoConsultorio(((Double)vRowTemp.get(0)).intValue());
                            oAsignaConsultorio.getPH().setNoTarjeta(((Double)vRowTemp.get(1)).intValue());
                            oAsignaConsultorio.getPH().setApPaterno((String)vRowTemp.get(2));
                            oAsignaConsultorio.getPH().setApMaterno((String)vRowTemp.get(3));
                            oAsignaConsultorio.getPH().setNombres((String)vRowTemp.get(4));
                            oAsignaConsultorio.getAreaServicio().setClave(((Double)vRowTemp.get(5)).intValue());
                            oAsignaConsultorio.getAreaServicio().setDescripcion((String)vRowTemp.get(6));
                            oAsignaConsultorio.setMaximo(((Double)vRowTemp.get(7)).intValue());
                            oAsignaConsultorio.setCitas1eraVez(cuentaCitas(oAsignaConsultorio.getPH().getNoTarjeta(),oAsignaConsultorio.getAreaServicio().getClave(), "S", fechaEnvio, (String)vRowTemp.get(8)));
                            oAsignaConsultorio.setCitasSubs(cuentaCitas(oAsignaConsultorio.getPH().getNoTarjeta(),oAsignaConsultorio.getAreaServicio().getClave(), "N", fechaEnvio, (String)vRowTemp.get(8)));
                            oAsignaConsultorio.setCitasTotales(oAsignaConsultorio.getCitas1eraVez()+oAsignaConsultorio.getCitasSubs());
                            oAsignaConsultorio.getTurno().setClave((String)vRowTemp.get(8));
                            if (oAsignaConsultorio.getCitasTotales()!=0)
                            vObj.add(oAsignaConsultorio);
			}
                    nTam = vObj.size();
                    arrRet = new AsignaConsultorio[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		}
                sFechaMensual=(Integer.parseInt(dia)+1)+"/"+(mes+1)+"/"+f.get(Calendar.YEAR);

		return arrRet; 
	}
        //**********************************************************************************************
        public int cuentaCitas(int tarjeta, int area, String esPrimeraVez, String fecha, String turno) throws Exception{
	ArrayList rst = null;
        String sQuery= "";
        int i=0, nTam=0;
        int cont=0;
        String status, tipoPac, emisor;
             
            sQuery = "select * from cuentaCitasMedicoDia("+tarjeta+", "+
                    area+"::smallint, '"+fecha+"', '"+
                    turno.substring(0, 3)+"', '"+ esPrimeraVez +"');";
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                for (i = 0; i < rst.size(); i++) {
                    ArrayList vRowTemp = (ArrayList)rst.get(i);
                    cont=(((Double)vRowTemp.get(0)).intValue());
                }
            } 
            return cont; 
	}
        //**********************************************************************************************
        public AsignaConsultorio[] buscarPacPorMedico() throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        int i=0, cita=-1, nTam=0;
        nCitados=0;
	SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2=new SimpleDateFormat("yyyy-MM-dd");
		sQuery = "select * from buscaPacPorMedico2("+getPH().getNoTarjeta()+", "+getCons().getNoConsultorio()+"::smallint, "+getAreaServicio().getClave()+"::smallint, '"+df.format(getFechaConsulta())+"', '"+getTurno().getClave().substring(0, 3)+"', '"+df2.format(getFechaConsulta())+"%');";
                
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAsignaConsultorio.getCita().setNoConsulta(((Double)vRowTemp.get(0)).intValue());
                            oAsignaConsultorio.getCita().getPaciente().setFolioPaciente(((Double)vRowTemp.get(1)).longValue());
                            oAsignaConsultorio.getCita().setFolioCita(((Double)vRowTemp.get(2)).intValue());
                            oAsignaConsultorio.getCita().getPaciente().setApPaterno((String)vRowTemp.get(3));
                            oAsignaConsultorio.getCita().getPaciente().setApMaterno((String)vRowTemp.get(4));
                            oAsignaConsultorio.getCita().getPaciente().setNombres((String)vRowTemp.get(5));
                            oAsignaConsultorio.getCita().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(6)).intValue());
                            oAsignaConsultorio.getCita().getPaciente().getSeg().setNumero((String)vRowTemp.get(7));
                            oAsignaConsultorio.getCita().getPaciente().getTipoSol().setValor((String)vRowTemp.get(8));
                            oAsignaConsultorio.getCita().getPaciente().getReferencia().setDescripcion((String)vRowTemp.get(8));
                            if (((String)vRowTemp.get(9)).compareTo("S")==0)
                                oAsignaConsultorio.getCita().setPrimEsp("1ERA VEZ");
                                if (((String)vRowTemp.get(9)).compareTo("N")==0)
                                    oAsignaConsultorio.getCita().setPrimEsp("SUBSECUENTE");
                            oAsignaConsultorio.getCita().getAreaServicio().setClave(getAreaServicio().getClave());
                            oAsignaConsultorio.getCita().setFechaAux(getFechaConsulta());
                            vObj.add(oAsignaConsultorio);
                            nCitados++;
			}
                    nTam = vObj.size();
                    arrRet = new AsignaConsultorio[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		}
		return arrRet; 
	}       
        //**********************************************************************************************
        public String buscaTipoPacEsp(long folioPac, int episodio) throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");
        int i=0;
        String tipo="";
             
		sQuery = "select * from buscaTipoPacEsp("+folioPac+", "+episodio+", '"+df.format(getFechaConsulta())+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            tipo=((String)vRowTemp.get(0));

			}
		}
                if (tipo.compareTo("S")==0)
                    tipo="1ERA VEZ";
                if (tipo.compareTo("N")==0)
                    tipo="SUBSECUENTE";
		return tipo; 
	}
        //**********************************************************************************************
        public int buscaAtiendePorDia(String tarjeta, String turno, String dia) throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        int atiende=0;
             
		sQuery = "select * from buscaatiendepordia("+tarjeta+", '"+turno.substring(0, 3)+"', '"+dia.toUpperCase()+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            atiende=((Double)vRowTemp.get(0)).intValue();

		}
                return atiende;
	}
        //**********************************************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AsignaConsultorio.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAsignaConsultorio();"; 
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
        //************************************************************************************************
        public int insertaAsignaConsAux(int area, int consultorio, int tarjeta, int horario, int citasPrim, int citasSubs, String turno) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        String sLlave=area+", "+consultorio+", "+tarjeta+", "+horario;
        
		 if( this==null){   //completar llave
			throw new Exception("Asignacion.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaAsignaConsultorio('"+sUsuario+"', "+area+"::smallint, "+consultorio+"::smallint, "+tarjeta+", "+horario+"::smallint, "+citasPrim+"::smallint, "+citasSubs+"::smallint, '"+turno+"', '"+sLlave+"');"; 
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
        //************************************************************************************************
	public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("AsignaConsultorio.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaAsignaConsultorio();"; 
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

        String sLlave=getAreaServicio().getClave()+", "+getCons().getNoConsultorio()+", "+getPH().getNoTarjeta();
		 if( this==null){   //completar llave
			throw new Exception("AsignaConsultorio.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaAsignaConsultorioAux('"+sUsuario+"', "+getAreaServicio().getClave()+"::smallint, "+getCons().getNoConsultorio()+"::smallint, "+getPH().getNoTarjeta()+", '"+oTurno.getClave()+"', "+getCitas1eraVez()+"::smallint, "+getCitasSubs()+"::smallint, '"+sLlave+"');"; 
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
        
        public String muestraDia(int dia){
         Calendar f=new GregorianCalendar();
        if (dia==31 && ((f.get(Calendar.MONTH)+1)==4 || (f.get(Calendar.MONTH)+1)==6 || (f.get(Calendar.MONTH)+1)==9 || (f.get(Calendar.MONTH)+1)==11))
            return "mostrarbtn";
        else{
            if ((f.get(Calendar.MONTH)+1)==2){
                if ((f.get(Calendar.YEAR) % 4 == 0) && ((f.get(Calendar.YEAR) % 100 != 0) || (f.get(Calendar.YEAR) % 400 == 0))){
                    if (dia==30 || dia==31)
                    return "mostrarbtn";
                }else{
                    if (dia==30 || dia==31 || dia==29)
                    return "mostrarbtn";
                }
            }
            return "";
        }
        }
        
        public String DaFormatoNuevaFecha(int dia){
        Calendar f=new GregorianCalendar();
        String sFec=dia+"/"+(f.get(Calendar.MONTH)+1)+"/"+f.get(Calendar.YEAR);
        SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        try{
            dFechaConsulta=formatter.parse(sFec);
            setNuevaFecha(dFechaConsulta);
        }catch(Exception e){}
        return "DetalleBitacora";
        }
        
        public String volver(){
            setFechaConsulta(new Date());
            return "Bitacora";
        }
 //************************************************************************************************
	public int modificarCitasAsignacion(int tarjeta, int cons, int area, int nuevoCons, int nuevaArea, String turno) throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
		 if( this==null){   //completar llave
			throw new Exception("AsignaConsultorio.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaCitasAsignacion("+tarjeta+", "+cons+"::smallint, "+area+"::smallint, "+nuevoCons+"::smallint, "+nuevaArea+"::smallint, '"+turno.substring(0, 3)+"', '"+sUsuario+"', '"+df.format(new Date())+"');"; 
                        
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() >= 1) {
					ArrayList vRowTemp = (ArrayList)rst.get(0);
					nRet = ((Double)vRowTemp.get(0)).intValue();
				}
			}
		} 
		return nRet; 
	} 
        //***************************************************************        
        
	public int getCitas1eraVez() {
	return nCitas1eraVez;
	}

	public void setCitas1eraVez(int valor) {
	nCitas1eraVez=valor;
	}

	public int getCitasSubs() {
	return nCitasSubs;
	}

	public void setCitasSubs(int valor) {
	nCitasSubs=valor;
	}

	public int getCitasTotales() {
	return getnCitasTotales();
	}

	public void setCitasTotales(int valor) {
	setnCitasTotales(valor);
	}

	public Turno getTurno() {
	return oTurno;
	}

	public void setTurno(Turno valor) {
	oTurno=valor;
	}


        public List<String> getDias() {
        return lDias;
        }

        public void setDias(List<String> lDias) {
        this.lDias = lDias;
        }

    public AreaServicioHRRB getAreaServicio() {
        return oAreaServicio;
    }

    public void setAreaServicio(AreaServicioHRRB oAreaServicio) {
        this.oAreaServicio = oAreaServicio;
    }

    public PersonalHospitalario getPH() {
        return oPH;
    }

    public void setPH(PersonalHospitalario oPH) {
        this.oPH = oPH;
    }

    public Consultorio getCons() {
        return oCons;
    }

    public void setCons(Consultorio oCons) {
        this.oCons = oCons;
    }

    public String getHoraLun() {
        return horaLun;
    }

    public void setHoraLun(String horaLun) {
        this.horaLun = horaLun;
    }

    public String getHoraMar() {
        return horaMar;
    }

    public void setHoraMar(String horaMar) {
        this.horaMar = horaMar;
    }

    public String getHoraMie() {
        return horaMie;
    }

    public void setHoraMie(String horaMie) {
        this.horaMie = horaMie;
    }

    public String getHoraJue() {
        return horaJue;
    }

    public void setHoraJue(String horaJue) {
        this.horaJue = horaJue;
    }

    public String getHoraVie() {
        return horaVie;
    }

    public void setHoraVie(String horaVie) {
        this.horaVie = horaVie;
    }

    public int getnCitasTotales() {
        return nCitasTotales;
    }

    public void setnCitasTotales(int nCitasTotales) {
        this.nCitasTotales = nCitasTotales;
    }

    public int getMaximo() {
        return nMaximo;
    }

    public void setMaximo(int nMaximo) {
        this.nMaximo = nMaximo;
    }

    public Date getFechaConsulta() {
        return dFechaConsulta;
    }

    public void setFechaConsulta(Date dFechaConsulta) {
        this.dFechaConsulta = dFechaConsulta;
    }
    
    public String fechaConsultaFormat(){
      DateFormat df =  DateFormat.getDateInstance();
      return df.format(dFechaConsulta);
    }

    public Cita getCita() {
        return oCita;
    }

    public void setCita(Cita oCita) {
        this.oCita = oCita;
    }

    public int getClaveEpisodio() {
        return nClaveEpisodio;
    }

    public void setClaveEpisodio(int nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    public String getTipoPac() {
        return tipoPac;
    }

    public void setTipoPac(String tipoPac) {
        this.tipoPac = tipoPac;
    }
    
    public int getCitados(){
        return nCitados;
    }
    public String getFechaConsulTexto(){
        SimpleDateFormat df = new SimpleDateFormat("dd/MM/yyyy");
        return df.format(dFechaConsulta);
    }
    
    public String getFechaMensual(){
        return sFechaMensual;
    }
    
    

    public String getNuevaFecha() {
        DateFormat df =  DateFormat.getDateInstance();
        
        return df.format(dNuevaFecha);
    }

    public void setNuevaFecha(Date dNuevaFecha) {
        this.dNuevaFecha = dNuevaFecha;
    }
    
    //**********************************************************************************************
        public AsignaConsultorio[] buscarPacAtendidos() throws Exception{
	AsignaConsultorio arrRet[]=null, oAsignaConsultorio=null;
	ArrayList rst = null;
        ArrayList<AsignaConsultorio> vObj=null;
	String sQuery = "";
        int i=0, cita=-1, nTam=0;
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat df2=new SimpleDateFormat("EEEE");
        Calendar Cal=Calendar.getInstance();
        Cal.setTime(getCita().getFechaAux());
        String diaTexto="";
        int numDia=Cal.get(Calendar.DAY_OF_WEEK);
        switch(numDia){
            case 1: diaTexto="DOMINGO"; break;
            case 2: diaTexto="LUNES"; break;
            case 3: diaTexto="MARTES"; break;
            case 4: diaTexto="MIÉRCOLES"; break;
            case 5: diaTexto="JUEVES"; break;
            case 6: diaTexto="VIERNES"; break;
            case 7: diaTexto="SÁBADO"; break;
        }
        if(nCon5==0){
                setAtendidos(0);
                setFaltantes(0);
		sQuery = "select * from buscapacientesatendidos('"+df.format(getCita().getFechaAux())+"%', '"+diaTexto+"');";
                oAD=new AccesoDatos();
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery);
                        nCon5++;
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<AsignaConsultorio>();
			for (i = 0; i < rst.size(); i++) {
                            oAsignaConsultorio=new AsignaConsultorio();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oAsignaConsultorio.getCita().setNoConsultorio(((Double)vRowTemp.get(0)).intValue());
                            oAsignaConsultorio.getCita().getPH().setNoTarjeta(((Double)vRowTemp.get(1)).intValue());
                            oAsignaConsultorio.getCita().getPH().setApPaterno((String)vRowTemp.get(2));
                            oAsignaConsultorio.getCita().getPH().setApMaterno((String)vRowTemp.get(3));
                            oAsignaConsultorio.getCita().getPH().setNombres((String)vRowTemp.get(4));
                            oAsignaConsultorio.getCita().getAreaServicio().setClave(((Double)vRowTemp.get(5)).intValue());
                            oAsignaConsultorio.getCita().getAreaServicio().setDescripcion((String)vRowTemp.get(6));
                            oAsignaConsultorio.setMaximo(((Double)vRowTemp.get(7)).intValue());
                            oAsignaConsultorio.setCitas1eraVez(((Double)vRowTemp.get(8)).intValue());
                            oAsignaConsultorio.setCitasSubs(((Double)vRowTemp.get(9)).intValue());
                            oAsignaConsultorio.getTurno().setClave((String)vRowTemp.get(10));
                            buscarNumAtendidos(oAsignaConsultorio);
                            if(oAsignaConsultorio.getTotal()>0)
                            vObj.add(oAsignaConsultorio);
			}
                    nTam = vObj.size();
                    arrRet = new AsignaConsultorio[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		}
                arrRetC5=arrRet;
        }
        arrRet=arrRetC5;
		return arrRet; 
	}       
        //**********************************************************************************************
        public void buscarNumAtendidos(AsignaConsultorio oAsigCon) throws Exception{
	ArrayList rst = null;
        ArrayList rst2 = null;
	String sQuery = "", sQuery2="";
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

		sQuery = "select * from buscanumatendidos('"+df.format(getCita().getFechaAux())+"%', "+oAsigCon.getCita().getPH().getNoTarjeta()+", "+oAsigCon.getCita().getAreaServicio().getClave()+"::smallint, 'T4401', '"+oAsigCon.getTurno().getClave().substring(0, 3)+"', 'S');";
                sQuery2 = "select * from buscanumatendidos('"+df.format(getCita().getFechaAux())+"%', "+oAsigCon.getCita().getPH().getNoTarjeta()+", "+oAsigCon.getCita().getAreaServicio().getClave()+"::smallint, 'T4400', '"+oAsigCon.getTurno().getClave().substring(0, 3)+"', 'S');";
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery);
                        rst2 = oAD.ejecutarConsulta(sQuery2);
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            oAsigCon.setAtendidosPrim(((Double)vRowTemp.get(0)).intValue()); //1
                            oAsigCon.setAtendidos(((Double)vRowTemp.get(1)).intValue()); //1
                            vRowTemp = (ArrayList)rst2.get(0);
                            oAsigCon.setFaltantesPrim(((Double)vRowTemp.get(0)).intValue()); //4
                            oAsigCon.setFaltantes(((Double)vRowTemp.get(1)).intValue()); //3
                            
                            oAsigCon.setTotal(oAsigCon.getAtendidos()+oAsigCon.getFaltantes()); //1+3
                            oAsigCon.setAtendidosSub(oAsigCon.getAtendidos()-oAsigCon.getAtendidosPrim());//1-1
                            oAsigCon.setFaltantesSub(oAsigCon.getFaltantes()-oAsigCon.getFaltantesPrim());//3-4
                            setAtendidos(getAtendidos()+oAsigCon.getAtendidosPrim()+oAsigCon.getAtendidosSub());
                            setFaltantes(getFaltantes()+oAsigCon.getFaltantesPrim()+oAsigCon.getFaltantesSub());
		} 
	}       
        //**********************************************************************************************
        public void buscarNumAtendidosTipo(AsignaConsultorio oAsigCon) throws Exception{
	ArrayList rst = null;
        ArrayList rst2 = null;
	String sQuery = "", sQuery2="";
	SimpleDateFormat df=new SimpleDateFormat("yyyy-MM-dd");

		sQuery = "select * from buscanumatendidostipo('"+df.format(getCita().getFechaAux())+"%', "+oAsigCon.getCita().getPH().getNoTarjeta()+", "+oAsigCon.getCita().getAreaServicio().getClave()+"::smallint, 'T4401', '"+oAsigCon.getTurno().getClave().substring(0, 3)+"');";
                sQuery2 = "select * from buscanumatendidostipo('"+df.format(getCita().getFechaAux())+"%', "+oAsigCon.getCita().getPH().getNoTarjeta()+", "+oAsigCon.getCita().getAreaServicio().getClave()+"::smallint, 'T4400', '"+oAsigCon.getTurno().getClave().substring(0, 3)+"');";
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery);
                        rst2 = oAD.ejecutarConsulta(sQuery2);
			oAD.desconectar(); 
		}
		if (rst != null && rst.size()>0) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            oAsigCon.setAtendidosPrim(((Double)vRowTemp.get(0)).intValue());
                            vRowTemp = (ArrayList)rst2.get(0);
                            oAsigCon.setFaltantesPrim(((Double)vRowTemp.get(0)).intValue());
                            setAtendidosPrim(getAtendidosPrim()+oAsigCon.getAtendidosPrim());
                            setFaltantesPrim(getFaltantesPrim()+oAsigCon.getFaltantesPrim());
                            setAtendidosSub(getAtendidosPrim()-oAsigCon.getAtendidosPrim());
                            setFaltantesSub(getFaltantesPrim()-oAsigCon.getFaltantesPrim());
		} 
	}
    //*************************************************************************
    
    
    public int getAtendidos() {
        return atendidos;
    }

    public void setAtendidos(int atendidos) {
        this.atendidos = atendidos;
    }

    public int getFaltantes() {
        return faltantes;
    }

    public void setFaltantes(int faltantes) {
        this.faltantes = faltantes;
    }
    
    public void iniciaConsulta5(){
        nCon5=0;
    }

    public String getTotLun() {
        return totLun;
    }

    public void setTotLun(String totLun) {
        this.totLun = totLun;
    }

    public String getTotMar() {
        return totMar;
    }

    public void setTotMar(String totMar) {
        this.totMar = totMar;
    }

    public String getTotMie() {
        return totMie;
    }

    public void setTotMie(String totMie) {
        this.totMie = totMie;
    }

    public String getTotJue() {
        return totJue;
    }

    public void setTotJue(String totJue) {
        this.totJue = totJue;
    }

    public String getTotVie() {
        return totVie;
    }

    public void setTotVie(String totVie) {
        this.totVie = totVie;
    }
    
    public int getAtendidosPrim() {
        return atendidosPrim;
    }

    public void setAtendidosPrim(int atendidosPrim) {
        this.atendidosPrim = atendidosPrim;
    }

    public int getFaltantesPrim() {
        return faltantesPrim;
    }

    public void setFaltantesPrim(int faltantesPrim) {
        this.faltantesPrim = faltantesPrim;
    }
    
    public int getAtendidosSub() {
        return atendidosSub;
    }

    public void setAtendidosSub(int atendidosSub) {
        this.atendidosSub = atendidosSub;
    }

    public int getFaltantesSub() {
        return faltantesSub;
    }

    public void setFaltantesSub(int faltantesSub) {
        this.faltantesSub = faltantesSub;
    }
    
    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
} 
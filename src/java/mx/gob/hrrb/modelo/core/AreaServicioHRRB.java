package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;

/**
 * Objetivo:
 *
 * @author : Carlos, Betia, Francisco, Rafa, JMHG
 * @version: 1.0
 */
public class AreaServicioHRRB implements Serializable{

    private AccesoDatos oAD;
    private int nClave;
    private String sDescripcion;
    private String tipo;
    private Firmado oFirm;
    private String sUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    //JMHG
    private Turno oTurno;
    private int nCitasMax;
    private int nCitasExtra;

    public AreaServicioHRRB(){
	oTurno = new Turno();
	faceContext = FacesContext.getCurrentInstance();
	httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
	if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null ){
	    oFirm = (Firmado)httpServletRequest.getSession().getAttribute( "oFirm" );
	    sUsuario = oFirm.getUsu().getIdUsuario();
	}
    }

    public AreaServicioHRRB[] buscarAreaServicioHRRB() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT *FROM buscaareaservicio();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );

		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;

    }

    public String buscar() throws Exception{
	String bRet = "";
	ArrayList rst = null;
	String sQuery = "";
	if( this == null ){   //completar llave
	    throw new Exception( "AreaServicioHRRB.buscar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM buscaLlaveAreaServicioHRRB(" + getClave() + "::smallint);";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 ){
		ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		bRet = ((String)vRowTemp.get( 0 ));
	    }
	}
	return bRet;
    }

    public AreaServicioHRRB[] buscarTodos() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaTodosAreaServicioHRRB();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		if( ((String)vRowTemp.get( 2 )).compareTo( "CE" ) == 0 || ((String)vRowTemp.get( 2 )).compareTo( "URG" ) == 0 || ((String)vRowTemp.get( 2 )).compareTo( "HOSP" ) == 0 ){
		    oArea.setTipo( " - " + (String)vRowTemp.get( 2 ) );
		}
		else{
		    oArea.setTipo( "" );
		}
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public AreaServicioHRRB[] buscarAreasCE() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasServicioHRRBCE();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //Areas de Servicio de Urgencia
    public AreaServicioHRRB[] buscarAreasUrgs() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasServicioHRRBUrgs();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public AreaServicioHRRB[] buscarAreasHosp( String sexo ) throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasServicioHRRBhosp('" + sexo + "');";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public AreaServicioHRRB[] buscarAreasHosPrin() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasPrinHosp();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //Areas de SubServicios de Hospitalizacion
    public AreaServicioHRRB[] buscarSubServiciosHosp( int area ) throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaSubServicioHRRBhosp(" + area + "::smallint);";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //Areas de SubServicios de Hospitalizacion
    public AreaServicioHRRB[] buscarAreasExp() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaServicioHRRBExpediente();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //Buscar sub-áreas de Imagenología
    public AreaServicioHRRB[] buscarSubImg() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscarAreasImagenologia();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}

	return arrRet;
    }

    ///////////////////////////////////////////////////////////////////////
    public AreaServicioHRRB[] buscarTodosTipoServicio() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaareasserviciohrrbservicios();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new AreaServicioHRRB[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oArea;
	    }
	}
	return arrRet;
    }

    public void equalsArea( AreaServicioHRRB arrArea[] ){
	for( AreaServicioHRRB areas : arrArea ){
	    if( this.getClave() == areas.getClave() ){
		this.setClave( areas.getClave() );
		this.setDescripcion( areas.getDescripcion() );
	    }
	}
    }

    public AreaServicioHRRB[] buscarTodosServiciosHospitalizacion() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaareasserviciohrrbHospitalizacion();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new AreaServicioHRRB[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oArea;
	    }
	}
	return arrRet;
    }

    public void equalsAreaHospitalizacion( AreaServicioHRRB arrArea[] ){
	for( AreaServicioHRRB areas : arrArea ){
	    if( this.getClave() == areas.getClave() ){
		this.setClave( areas.getClave() );
		this.setDescripcion( areas.getDescripcion() );
	    }
	}
    }

    public AreaServicioHRRB[] buscarTodosServiciosUrgencias() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaareasserviciohrrburgs();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new AreaServicioHRRB[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oArea;
	    }
	}
	return arrRet;
    }

    public void equalsAreaUrgencias( AreaServicioHRRB arrArea[] ){
	for( AreaServicioHRRB areas : arrArea ){
	    if( this.getClave() == areas.getClave() ){
		this.setClave( areas.getClave() );
		this.setDescripcion( areas.getDescripcion() );
	    }
	}
    }

    public AreaServicioHRRB[] buscarTodosServiciosConsultaExterna() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaareasserviciohrrbce();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new AreaServicioHRRB[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oArea;
	    }
	}
	return arrRet;
    }

    public void equalsAreaConsultaExterna( AreaServicioHRRB arrArea[] ){
	for( AreaServicioHRRB areas : arrArea ){
	    if( this.getClave() == areas.getClave() ){
		this.setClave( areas.getClave() );
		this.setDescripcion( areas.getDescripcion() );
	    }
	}
    }

    public AreaServicioHRRB[] buscarConsultoriosCE() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasServicioHRRBCE();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new AreaServicioHRRB[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		oArea.setTipo( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oArea;
	    }
	}
	return arrRet;
    }

    public void equalsAreaConsulta( AreaServicioHRRB arrAreaCE[] ){
	for( AreaServicioHRRB areasCE : arrAreaCE ){
	    if( this.getDescripcion().equals( areasCE.getDescripcion() ) ){
		this.setClave( areasCE.getClave() );
		this.setDescripcion( areasCE.getDescripcion() );
		this.setTipo( areasCE.getTipo() );
	    }
	}
    }

    public AreaServicioHRRB[] buscarAreasServicioXGrupo( String sClaveGrupo ) throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	String sQuery = "";
	if( sClaveGrupo.isEmpty() ){
	    throw new Exception( "AreaServicioHRRB.buscarAreasServicioXGrupo: error de programación, faltan datos." );
	}
	else{
	    sQuery = "SELECT * FROM buscaAreaServicioXPersonalGrupo( '" + sClaveGrupo + "' );";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() > 0 ){
		ArrayList vRowTemp = null;
		arrRet = new AreaServicioHRRB[rst.size()];
		for( int i = 0;i < rst.size();i ++ ){
		    vRowTemp = (ArrayList)rst.get( i );
		    oArea = new AreaServicioHRRB();
		    oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		    oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		    oArea.setTipo( " - " + (String)vRowTemp.get( 2 ) );
		    arrRet[i] = oArea;
		}
	    }
	}
	return arrRet;
    }

    ///////////////////////////////////////////////////////////////////////
    public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
	if( this == null ){   //completar llave
	    throw new Exception( "AreaServicioHRRB.insertar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM insertaAreaServicioHRRB();";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
		if( rst != null && rst.size() == 1 ){
		    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		    nRet = ((Double)rst.get( 0 )).intValue();
		}
	    }
	}
	return nRet;
    }

    public int modificar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
	if( this == null ){   //completar llave
	    throw new Exception( "AreaServicioHRRB.modificar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM modificaAreaServicioHRRB();";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
		if( rst != null && rst.size() == 1 ){
		    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		    nRet = ((Double)rst.get( 0 )).intValue();
		}
	    }
	}
	return nRet;
    }

    public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
	if( this == null ){   //completar llave
	    throw new Exception( "AreaServicioHRRB.eliminar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM eliminaAreaServicioHRRB();";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
		if( rst != null && rst.size() == 1 ){
		    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		    nRet = ((Double)rst.get( 0 )).intValue();
		}
	    }
	}
	return nRet;
    }

    //********************************************************************************************************
    public AreaServicioHRRB[] buscarAreasServicioUsuario() throws Exception{
	AreaServicioHRRB arrRet[] = null, oAreaServicioHRRB = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasServicioUsuario('" + sUsuario + "');";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oAreaServicioHRRB = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oAreaServicioHRRB.setDescripcion( (String)vRowTemp.get( 0 ) );
		oAreaServicioHRRB.setClave( ((Double)vRowTemp.get( 1 )).intValue() );
		vObj.add( oAreaServicioHRRB );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //********************************************************************************************************
    public AreaServicioHRRB[] buscarAreasHospReporte() throws Exception{
	AreaServicioHRRB arrRet[] = null, oAreaServicioHRRB = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreaHRRBReporteHosp();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oAreaServicioHRRB = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oAreaServicioHRRB.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oAreaServicioHRRB.setDescripcion( (String)vRowTemp.get( 1 ) );
		vObj.add( oAreaServicioHRRB );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //ESPECIALIDAD QUE SE SOLICITA AL GENERAR LA ORDEN DE APERTURA DE UN EXPEDIENTE
    public AreaServicioHRRB[] buscaAreasEspecialidadSolicitada() throws Exception{
	AreaServicioHRRB arrRet[] = null, oAreaServicioHRRB = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasEspecialidadSolicitada();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oAreaServicioHRRB = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oAreaServicioHRRB.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oAreaServicioHRRB.setDescripcion( (String)vRowTemp.get( 1 ) );
		vObj.add( oAreaServicioHRRB );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public int getClave(){
	return nClave;
    }

    public void setClave( int valor ){
	nClave = valor;
    }

    public String getDescripcion(){
	return sDescripcion;
    }

    public void setDescripcion( String valor ){
	sDescripcion = valor;
    }

    /**
     * @return the tipo
     */
    public String getTipo(){
	return tipo;
    }

    /**
     * @param tipo the tipo to set
     */
    public void setTipo( String tipo ){
	this.tipo = tipo;
    }

    public Turno getTurno(){
	return oTurno;
    }

    public void setTurno( Turno oTurno ){
	this.oTurno = oTurno;
    }

    public int getCitasMax(){
	return nCitasMax;
    }

    public void setCitasMax( int nCitasMax ){
	this.nCitasMax = nCitasMax;
    }

    public int getCitasExtra(){
	return nCitasExtra;
    }

    public void setCitasExtra( int nCitasExtra ){
	this.nCitasExtra = nCitasExtra;
    }

    //Retorna lista de areas de servicio de Urgencias
    public List<AreaServicioHRRB> getListaAreasUrgs() throws Exception{
	List<AreaServicioHRRB> lLista = null;

	lLista = new ArrayList<AreaServicioHRRB>( Arrays.asList(
		(new AreaServicioHRRB()).buscarAreasUrgs() ) );

	return lLista;
    }

    //Retorna lista de areas de servicio para apertura de Expediente
    public List<AreaServicioHRRB> getListaAreasExp() throws Exception{
	List<AreaServicioHRRB> lLista = null;

	lLista = new ArrayList<AreaServicioHRRB>( Arrays.asList(
		(new AreaServicioHRRB()).buscarAreasExp() ) );

	return lLista;
    }

    //Areas de areas para reportes de emision de estados de salud
    public AreaServicioHRRB[] buscarAreasRepEdoSalud() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreaHRRBReporteEmision();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //Areas de areas para reportes de hospitalizacion por dia
    public AreaServicioHRRB[] buscarAreasRepHospDia() throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasReporteHosp();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //Areas nombre de determinada clave de area
    public String buscarNombreArea( int area ) throws Exception{
	String oArea = "";
	ArrayList rst = null;
	String sQuery = "", arrRet = "", vObj = "";
	int i = 0, nTam = 0;
	sQuery = "select * from buscaNombreAreaServicioHRRB(" + area + "::smallint);";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = "";
	    for( i = 0;i < rst.size();i ++ ){
		oArea = "";
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		setDescripcion( (String)vRowTemp.get( 0 ) );
		vObj = getDescripcion();
	    }
	}

	return vObj;
    }
    //METODO CREADO POR DANIEL HERNANDEZ SANCHEZ
    //

    public AreaServicioHRRB[] buscaAreasSerInicialCE() throws Exception{
	AreaServicioHRRB arrRet[] = null, oAreaServicioHRRB = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreasSerInicialCE();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oAreaServicioHRRB = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oAreaServicioHRRB.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oAreaServicioHRRB.setDescripcion( (String)vRowTemp.get( 1 ) );
		vObj.add( oAreaServicioHRRB );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //JMHG
    public AreaServicioHRRB[] buscarAreasServ( String sClaveperfil ) throws Exception{
	AreaServicioHRRB arrRet[] = null, oArea = null;
	ArrayList rst = null;
	ArrayList<AreaServicioHRRB> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscarAreasPorServicio('" + sClaveperfil + "');";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<AreaServicioHRRB>();
	    for( i = 0;i < rst.size();i ++ ){
		oArea = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oArea.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oArea.setDescripcion( (String)vRowTemp.get( 1 ) );
		vObj.add( oArea );
	    }
	    nTam = vObj.size();
	    arrRet = new AreaServicioHRRB[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}

	return arrRet;
    }
    //----

    //JMHG
    public void buscarDisponibilidadArea() throws Exception{
	String sQuery = "";
	ArrayList rst = null;

	if( oTurno == null || nClave < 1 ){
	    throw new Exception( "AreaServicioHRRB.buscarDisponibilidadArea: error, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM buscarDisponibilidadArea("
		    + nClave + "::smallint, '" + oTurno.getClave() + "');";
	    System.out.println( sQuery );
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();

		if( rst != null && rst.size() == 1 ){
		    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		    nCitasMax = ((Double)vRowTemp.get( 0 )).intValue();
		    nCitasExtra = ((Double)vRowTemp.get( 1 )).intValue();
		}
	    }
	}
    }

    //AREAS DONDE PUEDE SER PRESTADO EL EXPEDIENTE
    public AreaServicioHRRB[] buscaServiciohrrbPrestamoExp() throws Exception{
	AreaServicioHRRB arrRet[] = null, oAreaServicioHRRB = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaserviciohrrbprestamoexp();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new AreaServicioHRRB[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oAreaServicioHRRB = new AreaServicioHRRB();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oAreaServicioHRRB.setClave( ((Double)vRowTemp.get( 0 )).intValue() );
		oAreaServicioHRRB.setDescripcion( (String)vRowTemp.get( 1 ) );
		arrRet[i] = oAreaServicioHRRB;
	    }
	}
	return arrRet;
    }

}

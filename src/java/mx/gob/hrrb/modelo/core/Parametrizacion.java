package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.consultaexterna.AsignaConsultorio;

/**
 * Objetivo:
 *
 * @author :
 * @version: 1.0
 */
public class Parametrizacion implements Serializable{

    private AccesoDatos oAD;
    private String sClaveParametro;
    private String sTipoParametro;
    private String sValor;
    public static final String TABLA_CAJA = "T70";
    public static final String TABLA_RECIBO = "T71";
    public static final String CAJA_AUX_URG = "CU";
    public static final String CAJA_AUX_CE = "CCE";
    public static final String RECIBO_CUOTAS = "RCR";
    public static final String RECIBO_SEGPOP = "RSP";
    public static final String RECIBO_EXENTO = "RCE";
    public static final String SIT_PAGO_PAGADO = "3";
    public static final String SIT_PAGO_REINTEGRADO = "4";
    public static final String TABLA_DOCUMENTO = "TBH";
    public static final String TABLA_TIPO_EMPLEADO = "TBK"; //AGREGADO: 27/03/17 (JMHG)

    public Parametrizacion(){
	sClaveParametro = "";
    }

    public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
	if( this == null ){   //completar llave
	    throw new Exception( "Parametrizacion.buscar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM buscaLlaveParametrizacion('" + this.sClaveParametro + "','" + this.sTipoParametro + "');";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 ){
		ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		this.setClaveParametro( (String)vRowTemp.get( 0 ) );
		this.setTipoParametro( (String)vRowTemp.get( 1 ) );
		this.setValor( (String)vRowTemp.get( 2 ) );
		bRet = true;
	    }
	}
	return bRet;
    }

    //****************************************************
    public String buscaValorParametrizado( String cveCompleta ) throws Exception{
	AsignaConsultorio arrRet[] = null, oAsignaConsultorio = null;
	ArrayList rst = null;
	ArrayList<AsignaConsultorio> vObj = null;
	String sQuery = "";
	int i = 0;
	String valor = "";
	if( cveCompleta.compareTo( "" ) != 0 ){
	    sQuery = "select * from buscaValorParametrizado('" + cveCompleta.substring( 0, 3 ) + "', '" + cveCompleta.substring( 3, 5 ) + "');";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null ){
		vObj = new ArrayList<AsignaConsultorio>();
		for( i = 0;i < rst.size();i ++ ){
		    oAsignaConsultorio = new AsignaConsultorio();
		    ArrayList vRowTemp = (ArrayList)rst.get( i );
		    valor = ((String)vRowTemp.get( 0 ));

		}
	    }
	}
	else{
	    valor = "";
	}
	return valor;
    }

//****************************************************
    public Parametrizacion[] buscarTodos() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaTodosParametrizacion();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
	    }
	}
	return arrRet;
    }

    public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
	if( this == null ){   //completar llave
	    throw new Exception( "Parametrizacion.insertar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM insertaParametrizacion();";
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
	    throw new Exception( "Parametrizacion.modificar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM modificaParametrizacion();";
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
	    throw new Exception( "Parametrizacion.eliminar: error de programación, faltan datos" );
	}
	else{
	    sQuery = "SELECT * FROM eliminaParametrizacion();";
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

    //Obtener Estados Civiles
    public Parametrizacion[] buscarEdosCiviles() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaEdosCiviles();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
		//System.out.println(arrRet[i].getClaveParametro().toString()+arrRet[i].getTipoParametro().toString()+arrRet[i].getValor().toString());
	    }
	}
	return arrRet;
    }

    //Obtener pertenencias a gpos indigenas
    public Parametrizacion[] buscarPertGpoInd() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaPertenenciaGpoInd();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
		//System.out.println(arrRet[i].getTipoParametro().toString()+arrRet[i].getClaveParametro().toString()+arrRet[i].getValor().toString());
	    }
	}
	return arrRet;
    }
    
    
    //Obtener pertenencias gratuidad
public Parametrizacion[] buscaGratuidad()throws Exception{
Parametrizacion arrRet[] =null, oParametrizacion=null;
ArrayList rst =null;
ArrayList<Parametrizacion>vObj=null;
String sQuery="";
int i=0, nTam=0;
sQuery="SELECT *  FROM buscagratuidad();";
oAD = new AccesoDatos();
    if(oAD.conectar()){
    rst= oAD.ejecutarConsulta(sQuery);
    oAD.desconectar();
    }
    if(rst !=null){
    vObj= new ArrayList<Parametrizacion>();
    for(i=0;i<rst.size();i++){
    oParametrizacion= new Parametrizacion();
    ArrayList vRowTemp = (ArrayList)rst.get(i);
    oParametrizacion.setTipoParametro((String)vRowTemp.get(0));
    oParametrizacion.setClaveParametro((String)vRowTemp.get(1));
    oParametrizacion.setValor((String)vRowTemp.get(2));
    vObj.add(oParametrizacion);
    }
    nTam = vObj.size();
    arrRet= new Parametrizacion[nTam];
    for(i=0;i<nTam;i++){
        arrRet[i] = vObj.get(i);
    }}
    return arrRet;
}
    
    
    
    
    //Obtener opciones derechohabiencias
    public Parametrizacion[] buscarDerechohabiencia() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaDerechohabiencia();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
		//System.out.println(arrRet[i].getTipoParametro().toString()+arrRet[i].getClaveParametro().toString()+arrRet[i].getValor().toString());
	    }
	}
	return arrRet;
    }

    //Obtener opciones edad
    public Parametrizacion[] buscarClaveEdad() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaClaveEdad();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
		//System.out.println(arrRet[i].getClaveParametro().toString()+arrRet[i].getTipoParametro().toString()+arrRet[i].getValor().toString());
	    }
	}
	return arrRet;
    }

    //Obtener opciones sexo
    public Parametrizacion[] buscarSexo() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;

	sQuery = "SELECT * FROM buscaSexo();";

	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarSexo( String valor ) throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;

	sQuery = "SELECT * FROM buscaSexo('" + valor + "');";

	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarTabla( String tipo ) throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaTabla('" + tipo + "');";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
		//System.out.println(arrRet[i].getClaveParametro().toString()+arrRet[i].getTipoParametro().toString()+arrRet[i].getValor().toString());
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarTabuladorDeNiveles() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "select * from  buscaTabuladorDeNiveles();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

//**************************************************************************************
    public Parametrizacion[] buscarSolicitudes() throws Exception{
	Parametrizacion arrRet[] = null, oPacs = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaSol();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oPacs = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPacs.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oPacs.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oPacs.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oPacs );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //***************************************************************
    public Parametrizacion[] buscarCajasAuxiliares() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaParamCajasAuxiliares();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //***************************************************************
    public Parametrizacion[] buscarTipoMovimientoMedicamento() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscarTipoMovimientoMedicamento();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }
    //***************************************************************

    public Parametrizacion[] buscarTipoAdquisicion() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT *FROM buscaTipoAdquisicion();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;

    }

    // método de búsqueda de motivo de la tabla de parametrizacion.
    public Parametrizacion[] buscarTipoMotivoMaterial() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT *FROM buscaMotivoParametrizacion();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }

	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;

    }

    public Parametrizacion[] buscarTipoPaciente() throws Exception{
	Parametrizacion arrRet[] = null, oParam = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaTipoPacienteIntExt()";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParam = new Parametrizacion();
		ArrayList<Object> vRowTemp = (ArrayList)rst.get( i );
		oParam.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParam.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParam.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParam );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarGrupoSanguineo() throws Exception{
	Parametrizacion arrRet[] = null, oParam = null;
	ArrayList<Parametrizacion> vObj = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaGrupoSanguineo();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParam = new Parametrizacion();
		ArrayList<Object> vRowTemp = (ArrayList)rst.get( i );
		oParam.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParam.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParam.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParam );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarRH() throws Exception{
	Parametrizacion arrRet[] = null, oParam = null;
	ArrayList<Parametrizacion> vObj = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscarRHBancoSangre();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParam = new Parametrizacion();
		ArrayList<Object> vRowTemp = (ArrayList)rst.get( i );
		oParam.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParam.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParam.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParam );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}

	return arrRet;
    }

    public String getClaveParametro(){
	return sClaveParametro;
    }

    public void setClaveParametro( String valor ){
	sClaveParametro = valor;
    }

    public String getTipoParametro(){
	return sTipoParametro;
    }

    public void setTipoParametro( String valor ){
	sTipoParametro = valor;
    }

    public String getValor(){
	return sValor;
    }

    public void setValor( String valor ){
	sValor = valor;
    }
//******************************************************************************

    public Parametrizacion[] buscarTipoPersonal() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscarTipoPersonal();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //******************************************************************************
    public Parametrizacion[] buscarTipoContrato() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscarTipoContrato();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarAgenteLesion() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAgenteLesion();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( Integer.toString( ((Double)vRowTemp.get( 0 )).intValue() ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarAreaAnatomica() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaAreaAnatomicaGrave();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( Integer.toString( ((Double)vRowTemp.get( 0 )).intValue() ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarConsecuenciaResultante() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaConsecuenciaResultante();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( Integer.toString( ((Double)vRowTemp.get( 0 )).intValue() ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public void equalsParam( Parametrizacion arrParam[] ){
	for( Parametrizacion param : arrParam ){
	    if( this.sTipoParametro.equals( param.sTipoParametro ) ){
		this.setClaveParametro( param.getClaveParametro() );
		this.setValor( param.getValor() );
	    }
	}
    }

    public void equalsEstudios( ArrayList<Parametrizacion> arrClasificacion ){
	for( Parametrizacion clasificacion : arrClasificacion ){
	    if( this.getClaveParametro().equals( clasificacion.getClaveParametro() ) ){
		this.setValor( clasificacion.getValor() );
		this.setTipoParametro( clasificacion.getTipoParametro() );
		this.setClaveParametro( clasificacion.getClaveParametro() );
	    }
	}
    }

    ///////////////////////////////////////////////////////////////
    // Órdenes de servicio
    public Parametrizacion[] buscarTodosTipoEstudio( int cveArea ) throws Exception{
	Parametrizacion arrRet[] = null, oEst = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscartodostipoestudios('" + cveArea + "');";
	System.out.println( sQuery );
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oEst = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oEst.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oEst.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oEst.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oEst;
	    }
	}
	return arrRet;
    }

    public void equalsEstudios( Parametrizacion arrClasificacion[] ){
	//System.out.println("This cveparam-->"+this.getValor()+"<---");
	for( Parametrizacion clasificacion : arrClasificacion ){
	    // System.out.println("This cveparam arr-->"+clasificacion.getValor()+"<---");
	    if( this.getValor().equals( clasificacion.getValor() ) ){
		this.setValor( clasificacion.getValor() );
		this.setTipoParametro( clasificacion.getTipoParametro() );
		this.setClaveParametro( clasificacion.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarSitiosAnat() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaSitioAnatomicoPato();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oPar.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oPar.setValor( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsPatologiaSitiosAnat( Parametrizacion arrSitiosAnat[] ){
	for( Parametrizacion sitio : arrSitiosAnat ){
	    if( this.getValor().equals( sitio.getValor() ) ){
		this.setValor( sitio.getValor() );
		this.setTipoParametro( sitio.getTipoParametro() );
		this.setClaveParametro( sitio.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarTipoBiopPzaQx() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaTiposBiopsiaPzQx();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oPar.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oPar.setValor( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsPatologiaBiopsia( Parametrizacion arrTipoBiopPzaQx[] ){
	for( Parametrizacion biopPza : arrTipoBiopPzaQx ){
	    if( this.getValor().equals( biopPza.getValor() ) ){
		this.setValor( biopPza.getValor() );
		this.setTipoParametro( biopPza.getTipoParametro() );
		this.setClaveParametro( biopPza.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarAntecCitologia() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaCitologiaPato();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oPar.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oPar.setValor( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsPatologiaCitologia( Parametrizacion arrAntecCitol[] ){
	for( Parametrizacion citol : arrAntecCitol ){
	    if( this.getValor().equals( citol.getValor() ) ){
		this.setValor( citol.getValor() );
		this.setTipoParametro( citol.getTipoParametro() );
		this.setClaveParametro( citol.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarSitGineco() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaSituaciónGinecoPato();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oPar.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oPar.setValor( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsPatologiaSituacionGineco( Parametrizacion arrSitGineco[] ){
	for( Parametrizacion sitGineco : arrSitGineco ){
	    if( this.getValor().equals( sitGineco.getValor() ) ){
		this.setValor( sitGineco.getValor() );
		this.setTipoParametro( sitGineco.getTipoParametro() );
		this.setClaveParametro( sitGineco.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarTipoProcCitol() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaTipoProcedimientosCitologiaPato();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oPar.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oPar.setValor( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsPatologiaTipoProcCitol( Parametrizacion arrTipoProcCitol[] ){
	for( Parametrizacion tipoProcCitol : arrTipoProcCitol ){
	    if( this.getValor().equals( tipoProcCitol.getValor() ) ){
		this.setValor( tipoProcCitol.getValor() );
		this.setTipoParametro( tipoProcCitol.getTipoParametro() );
		this.setClaveParametro( tipoProcCitol.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarMotivoEnvio() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscamotivoenvio();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsMotivoEnv( Parametrizacion arrMotivoEnv[] ){
	for( Parametrizacion MotivoEnv : arrMotivoEnv ){
	    if( this.getValor().equals( MotivoEnv.getValor() ) ){
		this.setValor( MotivoEnv.getValor() );
		this.setTipoParametro( MotivoEnv.getTipoParametro() );
		this.setClaveParametro( MotivoEnv.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarRegionImagen( int cveEst ) throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaregionessecundarias(" + cveEst + "::smallint);";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setValor( (String)vRowTemp.get( 0 ) );
		oPar.setTipoParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsImagenRegion( Parametrizacion arrRegImg[] ){
	for( Parametrizacion RegImg : arrRegImg ){
	    if( this.getValor().equals( RegImg.getValor() ) ){
		this.setValor( RegImg.getValor() );
		this.setTipoParametro( RegImg.getTipoParametro() );
		this.setClaveParametro( RegImg.getClaveParametro() );
	    }
	}
    }

    public void equalsGrupoSangre( Parametrizacion arrGrpSang[] ){
	for( Parametrizacion GrpSang : arrGrpSang ){
	    if( this.getValor().equals( GrpSang.getValor() ) ){
		this.setValor( GrpSang.getValor() );
		this.setTipoParametro( GrpSang.getTipoParametro() );
		this.setClaveParametro( GrpSang.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarTipoPacExtInt() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscatipopacienteintext();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsTipoPacExtInt( Parametrizacion arrTipoPacEI[] ){
	for( Parametrizacion TipoPacEI : arrTipoPacEI ){
	    if( this.getValor().equals( TipoPacEI.getValor() ) ){
		this.setValor( TipoPacEI.getValor() );
		this.setTipoParametro( TipoPacEI.getTipoParametro() );
		this.setClaveParametro( TipoPacEI.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarNivUrgBanco() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaNivelUrgBanco();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsNivUrgBanco( Parametrizacion arrNivUrgBanco[] ){
	for( Parametrizacion NivUrgBanco : arrNivUrgBanco ){
	    if( this.getValor().equals( NivUrgBanco.getValor() ) ){
		this.setValor( NivUrgBanco.getValor() );
		this.setTipoParametro( NivUrgBanco.getTipoParametro() );
		this.setClaveParametro( NivUrgBanco.getClaveParametro() );
	    }
	}
    }

    public void equalsRHBanSang( Parametrizacion arrRHBancoSangre[] ){
	for( Parametrizacion RHBancoSangre : arrRHBancoSangre ){
	    if( this.getValor().equals( RHBancoSangre.getValor() ) ){
		this.setValor( RHBancoSangre.getValor() );
		this.setTipoParametro( RHBancoSangre.getTipoParametro() );
		this.setClaveParametro( RHBancoSangre.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarTipoSolBanco() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaTipoSolicitudBanco();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarElectivaUrgente() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscarElectivaUrgente();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscarViaAdministracion() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaViaAdmMedicamentos();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsViaAdm( Parametrizacion arrVia[] ){
	for( Parametrizacion via : arrVia ){
	    if( this.getValor().equals( via.getValor() ) ){
		this.setValor( via.getValor() );
		this.setTipoParametro( via.getTipoParametro() );
		this.setClaveParametro( via.getClaveParametro() );
	    }
	}
    }

    public Parametrizacion[] buscarMedioContraste() throws Exception{
	Parametrizacion arrRet[] = null, oPar = null;
	ArrayList rst = null;
	String sQuery = "";
	int i = 0;
	sQuery = "SELECT * FROM buscaMedioContraste();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    arrRet = new Parametrizacion[rst.size()];
	    for( i = 0;i < rst.size();i ++ ){
		oPar = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oPar.setTipoParametro( ((String)vRowTemp.get( 0 )).trim() );
		oPar.setClaveParametro( ((String)vRowTemp.get( 1 )).trim() );
		oPar.setValor( ((String)vRowTemp.get( 2 )).trim() );
		arrRet[i] = oPar;
	    }
	}
	return arrRet;
    }

    public void equalsMedioContraste( Parametrizacion arrMedCont[] ){
	for( Parametrizacion MedCont : arrMedCont ){
	    if( this.getValor().equals( MedCont.getValor() ) ){
		this.setValor( MedCont.getValor() );
		this.setTipoParametro( MedCont.getTipoParametro() );
		this.setClaveParametro( MedCont.getClaveParametro() );
	    }
	}
    }

    ///////////////////////////////////////////////////////////////////////
    /*José Daniel Hernández*/
    public Parametrizacion[] buscaLugarAperturaExp() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	sQuery = "SELECT * FROM buscaLugarAperturaExp();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setTipoParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 1 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 2 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    //QX
    public Parametrizacion[] buscaHorariosQx() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;

	sQuery = "SELECT * FROM buscaHorariosQx();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscaMotivosDiferirQx() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;

	sQuery = "SELECT * FROM buscaCausasDiferirQx();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscaTiposProceQx() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;

	sQuery = "SELECT * FROM buscaTiposProcedimientosQx();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscaMotivosCancelacionQx() throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;

	sQuery = "SELECT * FROM buscaCausasCancelacionQx();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscaHorariosDisponiblesQx( Date fecha, String turno, String clvQx ) throws Exception{
	Parametrizacion arrRet[] = null, oParametrizacion = null;
	ArrayList rst = null;
	ArrayList<Parametrizacion> vObj = null;
	String sQuery = "";
	int i = 0, nTam = 0;
	SimpleDateFormat fd = new SimpleDateFormat( "yyyy-MM-dd" );
	sQuery = "SELECT * FROM buscaHorariosDisponiblesQx('" + fd.format( fecha ) + "','" + turno + "','" + clvQx + "');";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null ){
	    vObj = new ArrayList<Parametrizacion>();
	    for( i = 0;i < rst.size();i ++ ){
		oParametrizacion = new Parametrizacion();
		ArrayList vRowTemp = (ArrayList)rst.get( i );
		oParametrizacion.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParametrizacion.setValor( (String)vRowTemp.get( 1 ) );
		vObj.add( oParametrizacion );
	    }
	    nTam = vObj.size();
	    arrRet = new Parametrizacion[nTam];

	    for( i = 0;i < nTam;i ++ ){
		arrRet[i] = vObj.get( i );
	    }
	}
	return arrRet;
    }

    public boolean asignaValorParametrizado( String sClaveCompleta ) throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
	if( sClaveCompleta.isEmpty() ){
	    //throw new Exception( "Parametrizacion.asignaValorParametrizado: error de programación, faltan datos" );
	}
	else{
	    sQuery = "select * from buscaLlaveParametrizacion( "
		    + "'" + sClaveCompleta.substring( 0, 3 ) + "', "
		    + "'" + sClaveCompleta.substring( 3 ) + "' );";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() ){
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 ){
		ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		setClaveParametro( (String)vRowTemp.get( 0 ) );
		setTipoParametro( (String)vRowTemp.get( 1 ) );
		setValor( (String)vRowTemp.get( 2 ) );
		bRet = true;
	    }
	}

	return bRet;
    }

    public Parametrizacion[] buscaEstadoActivoPersonal() throws Exception{
	Parametrizacion arrRet[] = null, oParam = null;
	ArrayList rst = null;
	String sQuery = "SELECT * FROM buscaEstadoActivoPersonal();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null && rst.size() > 0 ){
	    ArrayList vRowTemp = null;
	    arrRet = new Parametrizacion[rst.size()];
	    for( int i = 0;i < rst.size();i ++ ){
		oParam = new Parametrizacion();
		vRowTemp = (ArrayList)rst.get( i );
		oParam.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParam.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oParam.setValor( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oParam;
	    }
	}
	return arrRet;
    }

    public Parametrizacion[] buscaTipoEmpleado() throws Exception{
	Parametrizacion arrRet[] = null, oParam = null;
	ArrayList rst = null;

	String sQuery = "SELECT * FROM buscaTipoEmpleado();";
	oAD = new AccesoDatos();
	if( oAD.conectar() ){
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null && rst.size() > 0 ){
	    ArrayList vRowTemp = null;
	    arrRet = new Parametrizacion[rst.size()];
	    for( int i = 0;i < rst.size();i ++ ){
		oParam = new Parametrizacion();
		vRowTemp = (ArrayList)rst.get( i );
		oParam.setClaveParametro( (String)vRowTemp.get( 0 ) );
		oParam.setTipoParametro( (String)vRowTemp.get( 1 ) );
		oParam.setValor( (String)vRowTemp.get( 2 ) );
		arrRet[i] = oParam;
	    }
	}
	return arrRet;
    }
}

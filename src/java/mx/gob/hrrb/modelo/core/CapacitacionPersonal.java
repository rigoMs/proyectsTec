/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author JMHG
 */
public class CapacitacionPersonal implements Serializable
{
    //private PersonalHospitalario oPersHosp; //EDITADO: 17/12/16 (JMHG)
    private int nNoTarjeta;
    private Date dFechaReal;
    private String sDescripcion;
    private Date dFechaRealOld;
    
    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    
    private String sTipoComando;
    
    //EDITADO: 17/12/16 (JMHG)
    public CapacitacionPersonal()
    {
	//oPersHosp = new PersonalHospitalario();
	nNoTarjeta = 0;
	dFechaReal = null;
	sDescripcion = "";
	dFechaRealOld = null;
	sTipoComando = "";
    }
    
    private boolean buscarUsuario()
    {
	boolean bRet = false;
	faceContext = FacesContext.getCurrentInstance();
	httpServletRequest = (HttpServletRequest)faceContext.getExternalContext().getRequest();
	if( httpServletRequest.getSession().getAttribute( "oFirm" ) != null )
	{
	    oFirm = (Firmado)httpServletRequest.getSession().getAttribute( "oFirm" );
	    sIdUsuario = oFirm.getUsu().getIdUsuario();
	    bRet = ( sIdUsuario != null && !sIdUsuario.isEmpty() );
	}
	return bRet;
    }
    
    public boolean buscar() throws Exception
    {
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
	if( getNoTarjeta() < 1 || getFechaReal() == null )
	{
	    throw new Exception( "CapacitacionPersonal.buscar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM buscaLlaveCapacitacionPersonal( " +
		    getNoTarjeta() + "::INTEGER, " +
		    format.format( getFechaReal() ) + "::DATE );";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 )
	    {
		ArrayList vRowTemp = (ArrayList) rst.get( 0 );
		setNoTarjeta( ( (Double)vRowTemp.get( 0 ) ).intValue() );
		setFechaReal( (Date)vRowTemp.get( 1 ) );
		setDescripcion( (String)vRowTemp.get( 2 ) );
		setFechaRealOld( new Date() );
		getFechaRealOld().setTime( getFechaReal().getTime() );
		bRet = true;
	    }
	}
	return bRet;
    }
    
    public CapacitacionPersonal[] buscarTodos() throws Exception
    {
	CapacitacionPersonal arrRet[] = null, oCapacitacionPersonal = null;
	ArrayList rst = null;
	String sQuery = "";
	sQuery = "SELECT * FROM buscaTodosCapacitacionPersonal();";
	oAD = new AccesoDatos();
	if( oAD.conectar() )
	{
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null && rst.size() > 0 )
	{
	    arrRet = new CapacitacionPersonal[ rst.size() ];
	    ArrayList vRowTemp = null;
	    for( int i = 0; i < rst.size(); i++ )
	    {
		vRowTemp = (ArrayList)rst.get( i );
		oCapacitacionPersonal = new CapacitacionPersonal();
		oCapacitacionPersonal.setNoTarjeta( ( (Double)vRowTemp.get( 0 ) ).intValue() );
		oCapacitacionPersonal.setFechaReal( (Date)vRowTemp.get( 1 ) );
		oCapacitacionPersonal.setDescripcion( (String)vRowTemp.get( 2 ) );
		oCapacitacionPersonal.setFechaRealOld( new Date() );
		oCapacitacionPersonal.getFechaRealOld().setTime( oCapacitacionPersonal.getFechaReal().getTime() );
		arrRet[ i ] = oCapacitacionPersonal;
	    }
	}
	return arrRet;
    }
    
    public CapacitacionPersonal[] buscarTodosFiltro() throws Exception
    {
	CapacitacionPersonal arrRet[] = null, oCapacitacionPersonal = null;
	ArrayList rst = null;
	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
	String sQuery = "SELECT * FROM buscaCapacitacionPersonalFiltro( " +
		getNoTarjeta() + "::INTEGER, " +
		( getFechaReal() != null ? format.format( getFechaReal() ) + "::DATE" : "NULL" ) + ", " +
		"'" + getDescripcion() + "' );";
	oAD = new AccesoDatos();
	if( oAD.conectar() )
	{
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null && rst.size() > 0 )
	{
	    arrRet = new CapacitacionPersonal[ rst.size() ];
	    ArrayList vRowTemp = null;
	    for( int i = 0; i < rst.size(); i++ )
	    {
		vRowTemp = (ArrayList)rst.get( i );
		oCapacitacionPersonal = new CapacitacionPersonal();
		oCapacitacionPersonal.setNoTarjeta( ( (Double)vRowTemp.get( 0 ) ).intValue() );
		oCapacitacionPersonal.setFechaReal( (Date)vRowTemp.get( 1 ) );
		oCapacitacionPersonal.setDescripcion( (String)vRowTemp.get( 2 ) );
		oCapacitacionPersonal.setFechaRealOld( new Date() );
		oCapacitacionPersonal.getFechaRealOld().setTime( oCapacitacionPersonal.getFechaReal().getTime() );
		arrRet[ i ] = oCapacitacionPersonal;
	    }
	}
	return arrRet;
    }
    
    public int insertar() throws Exception
    {
	int nRet = 0;
	ArrayList rst = null;
	String sQuery = "";
	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
	if( !buscarUsuario() || getNoTarjeta() < 1 || getFechaReal() == null )
	{
	    throw new Exception( "CapacitacionPersonal.insertar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM insertaCapacitacionPersonal( " +
		    "'" + sIdUsuario + "', " +
		    getNoTarjeta() + "::INTEGER, " +
		    "'" + format.format( getFechaReal() ) + "'::DATE, " +
		    "'" + getDescripcion() + "' );";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 )
	    {
		ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		nRet = ( (Double)vRowTemp.get( 0 ) ).intValue();
	    }
	}
	return nRet;
    }
    
    public int modificar() throws Exception
    {
	int nRet = 0;
	ArrayList rst = null;
	String sQuery = "";
	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
	if( !buscarUsuario() || getNoTarjeta() < 1 && getFechaRealOld() == null && getFechaReal() == null )
	{
	    throw new Exception( "CapacitacionPersonal.modificar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM modificaCapacitacionPersonal( " +
		    "'" + sIdUsuario + "', " +
		    getNoTarjeta() + "::INTEGER, " + 
		    "'" + format.format( getFechaRealOld() ) + "'::DATE, " +
		    "'" + format.format( getFechaReal() ) + "'::DATE, " +
		    "'" + getDescripcion() + "' );";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 )
	    {
		ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		nRet = ( (Double)vRowTemp.get( 0 ) ).intValue();
	    }
	}
	return nRet;
    }
    
    public int modificarComp( List<CapacitacionPersonal> lCapacitacionOld, List<CapacitacionPersonal> lCapacitacion )
	    throws Exception
    {
	int nRet = 0;
	ArrayList rst = null;
	String sQuery = "";
	String sQueryArr = "";
	if( !buscarUsuario() || getNoTarjeta() < 1)
	{
	    throw new Exception( "CapacitacionPersona.modificarComp: error de programación, faltan datos" );
	}
	else
	{
	    sQueryArr = crearQueryArreglos( lCapacitacionOld, lCapacitacion );
	    sQuery = "SELECT * FROM modificaCapacitacionPersonalComp( " +
		    "'" + sIdUsuario + "', " +
		    getNoTarjeta() + "::INTEGER, " +
		    sQueryArr + " );";
	    System.out.println( sQuery );
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 )
	    {
		ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		nRet = ( (Double)vRowTemp.get( 0 ) ).intValue();
	    }
	}
	return nRet;
    }
    
    public int eliminar() throws Exception
    {
	int nRet = 0;
	ArrayList rst = null;
	String sQuery = "";
	SimpleDateFormat format = new SimpleDateFormat( "yyyy-MM-dd" );
	if( !buscarUsuario() || getNoTarjeta() < 1 || getFechaReal() == null )
	{
	    throw new Exception( "CapacitacionPersonal.eliminar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM eliminaCapacitacionPersonal( " +
		    "'" + sIdUsuario + "', " +
		    getNoTarjeta() + "::INTEGER, " +
		    "'" + format.format( getFechaReal() ) + "'::DATE );";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 )
	    {
		ArrayList vRowTemp = (ArrayList)rst.get( 0 );
		nRet = ( (Double)vRowTemp.get( 0 ) ).intValue();
	    }
	}
	return nRet;
    }
    
    public String crearQueryArreglos( List<CapacitacionPersonal> lCapacitacionOld, List<CapacitacionPersonal> lCapacitacion )
    {
	String sRet = "";
	if( ( lCapacitacion != null && !lCapacitacion.isEmpty() ) ||
		( lCapacitacionOld != null && !lCapacitacionOld.isEmpty() ) )
	{
	    List<CapacitacionPersonal> lTemp = new ArrayList<>();
	    List<CapacitacionPersonal> lTempUpdate = new ArrayList<>();
	    List<CapacitacionPersonal> lTempInsert = new ArrayList<>();
	    if( lCapacitacionOld != null && !lCapacitacionOld.isEmpty() )
	    {
		for( CapacitacionPersonal oCapasitacion : lCapacitacionOld )
		{
		    if( oCapasitacion.getTipoComando().compareTo( "DELETE" ) == 0 )
		    {
			lTemp.add( oCapasitacion );
		    }
		}
	    }
	    if( lCapacitacion != null && !lCapacitacion.isEmpty() )
	    {
		for( CapacitacionPersonal oCapasitacion : lCapacitacion )
		{
		    if( oCapasitacion.getTipoComando().compareTo( "INSERT" ) == 0 )
		    {
			lTempInsert.add( oCapasitacion );
		    }
		    else
		    {
			oCapasitacion.setTipoComando( "UPDATE" );
			lTempUpdate.add( oCapasitacion );
		    }
		}
	    }
	    lTemp.addAll( lTempUpdate );
	    lTemp.addAll( lTempInsert );
	    StringBuilder sbArrsComandos = new StringBuilder( "ARRAY[ " );
	    StringBuilder sbArrdFechaOld = new StringBuilder( "ARRAY[ " );
	    StringBuilder sbArrdFecha = new StringBuilder( "ARRAY[ " );
	    StringBuilder sbArrsDescripcion = new StringBuilder( "ARRAY[ " );
	    SimpleDateFormat oFormat = new SimpleDateFormat( "yyyy-MM-dd" );
	    int i = 1, nSize = lTemp.size();
	    for( CapacitacionPersonal oCapasitacion : lTemp )
	    {
		sbArrsComandos.append( "'" ).append( oCapasitacion.getTipoComando() ).append( "'" );
		if( oCapasitacion.getFechaRealOld() == null )
		{
		    oCapasitacion.setFechaRealOld( new Date() );
		}
		sbArrdFechaOld.append( "'" ).append( oFormat.format( oCapasitacion.getFechaRealOld() ) ).append( "'::DATE" );
		sbArrdFecha.append( "'" ).append( oFormat.format( oCapasitacion.getFechaReal() ) ).append( "'::DATE" );
		sbArrsDescripcion.append( "'" ).append( oCapasitacion.getDescripcion() ).append( "'" );
		if( i < nSize )
		{
		    sbArrsComandos.append( ", " );
		    sbArrdFechaOld.append( ", " );
		    sbArrdFecha.append( ", " );
		    sbArrsDescripcion.append( ", " );
		}
		else
		{
		    sbArrsComandos.append( " ]" );
		    sbArrdFechaOld.append( " ]" );
		    sbArrdFecha.append( " ]" );
		    sbArrsDescripcion.append( " ]" );
		}
		i++;
	    }
	    sRet = sbArrsComandos.toString() + ", " +sbArrdFechaOld.toString() + ", " + sbArrdFecha.toString() + ", " + sbArrsDescripcion.toString();
	}
	
	return sRet;
    }

    /*public PersonalHospitalario getPersHosp()
    {
	return oPersHosp;
    }

    public void setPersHosp( PersonalHospitalario oPersHosp )
    {
	this.oPersHosp = oPersHosp;
    }*/
    
    public int getNoTarjeta()
    {
	return nNoTarjeta;
    }
    
    public void setNoTarjeta( int nNoTarjeta )
    {
	this.nNoTarjeta = nNoTarjeta;
    }

    public Date getFechaReal()
    {
	return dFechaReal;
    }

    public void setFechaReal( Date dFechaReal )
    {
	this.dFechaReal = dFechaReal;
    }

    public String getDescripcion()
    {
	return sDescripcion;
    }

    public void setDescripcion( String sDescripcion )
    {
	this.sDescripcion = sDescripcion;
    }

    public Date getFechaRealOld()
    {
	return dFechaRealOld;
    }

    public void setFechaRealOld( Date dFechaRealOld )
    {
	this.dFechaRealOld = dFechaRealOld;
    }

    public String getTipoComando()
    {
	return sTipoComando;
    }

    public void setTipoComando( String sComando )
    {
	this.sTipoComando = sComando;
    }
}
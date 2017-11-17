package mx.gob.hrrb.modelo.core;

import java.io.Serializable;
import java.util.ArrayList;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author JMHG
 */
public class TipoMedico implements Serializable
{
    private int nClaveTipo;
    private String sDescripcion;
    private AccesoDatos oAD;
    private Firmado oFirm;
    private String sIdUsuario;
    private HttpServletRequest httpServletRequest;
    private FacesContext faceContext;
    
    public TipoMedico()
    {
	nClaveTipo = 0;
	sDescripcion = "";
    }
    
    public boolean buscarUsuario()
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
	if( getClaveTipo() < 1 )
	{
	    throw new Exception( "TipoMedico.buscar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM buscaLlaveTipoMedico( " + getClaveTipo() + "::SMALLINT );" ;
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() == 1 )
	    {
		ArrayList vRowTemp = (ArrayList) rst.get( 0 );
		setClaveTipo( ( (Double)vRowTemp.get( 0 ) ).intValue() );
		setDescripcion( (String)vRowTemp.get( 1 ) );
		bRet = true;
	    }
	}
	return bRet;
    }
    
    public TipoMedico[] buscarTodos() throws Exception
    {
	TipoMedico arrRet[] = null, oTipoMedico = null;
	ArrayList rst = null;
	String sQuery = "";
	sQuery = "SELECT * FROM buscaTodosTipoMedico();";
	oAD = new AccesoDatos();
	if( oAD.conectar() )
	{
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null && rst.size() > 0 )
	{
	    arrRet = new TipoMedico[ rst.size() ];
	    ArrayList vRowTemp = null;
	    for( int i = 0; i < rst.size(); i++ )
	    {
		vRowTemp = (ArrayList)rst.get( i );
		oTipoMedico = new TipoMedico();
		oTipoMedico.setClaveTipo( ( (Double)vRowTemp.get( 0 ) ).intValue() );
		oTipoMedico.setDescripcion( (String)vRowTemp.get( 1 ) );
		arrRet[ i ] = oTipoMedico;
	    }
	}
	return arrRet;
    }
    
    public TipoMedico[] buscarTipoMed( String sTxt ) throws Exception
    {
	TipoMedico arrRet[] = null, oTipoMedico = null;
	ArrayList rst = null;
	String sQuery = "";
	sQuery = "SELECT * FROM buscaTodosTipoMedico( '" + sTxt + "' );";
	oAD = new AccesoDatos();
	if( oAD.conectar() )
	{
	    rst = oAD.ejecutarConsulta( sQuery );
	    oAD.desconectar();
	}
	if( rst != null && rst.size() > 0 )
	{
	    arrRet = new TipoMedico[ rst.size() ];
	    ArrayList vRowTemp = null;
	    for( int i = 0; i < rst.size(); i++ )
	    {
		vRowTemp = (ArrayList)rst.get( i );
		oTipoMedico = new TipoMedico();
		oTipoMedico.setClaveTipo( ( (Double)vRowTemp.get( 0 ) ).intValue() );
		oTipoMedico.setDescripcion( (String)vRowTemp.get( 1 ) );
		arrRet[ i ] = oTipoMedico;
	    }
	}
	return arrRet;
    }
    
    public int insertar() throws Exception
    {
	int nRet = 0;
	ArrayList rst = null;
	String sQuery = "";
	if( this == null ) //completar llave
	{
	    throw new Exception( "TipoMedico.insertar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM insertatipomedico( " +
		    "'ROOT'::character varying, "+
		    "'" + getDescripcion() + "'::character varying);";
                   oAD = new AccesoDatos();
	    if( oAD.conectar() ){
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
	if( this == null ) //completar llave
	{
	    throw new Exception( "TipoMedico.modificar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM modificarTipoMedico2( " +
		    getClaveTipo() + "::SMALLINT, " +
		    "'" + getDescripcion() + "'::character varying);";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {   
                System.out.println(sQuery);
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
	if( this == null ) //completar llave
	{
	    throw new Exception( "TipoMedico.eliminar: error de programación, faltan datos" );
	}
	else
	{
	    sQuery = "SELECT * FROM eliminaTipoMedico( " +
		    "'" + sIdUsuario + "', ";
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

    public int getClaveTipo()
    {
	return nClaveTipo;
    }

    public void setClaveTipo( int nClaveTipo )
    {
	this.nClaveTipo = nClaveTipo;
    }

    public String getDescripcion()
    {
	return sDescripcion;
    }

    public void setDescripcion( String sDescripcion )
    {
	this.sDescripcion = sDescripcion;
    }
}
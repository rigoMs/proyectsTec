package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Puesto implements Serializable{
	private AccesoDatos oAD;
	private float nSalario;
	private String sClave;
	private String sDescripcion;
	private boolean bPuedeAutorizar;

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Puesto.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlavePuesto();"; 
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
	public Puesto[] buscarTodos() throws Exception{ //EDITADO: 27/03/17 (JMHG)
	Puesto arrRet[]=null, oPuesto=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosPuesto();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Puesto[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			    ArrayList vRowTemp = (ArrayList)rst.get( i );
			    oPuesto = new Puesto();
			    oPuesto.setClave( (String)vRowTemp.get( 0 ) );
			    oPuesto.setDescripcion( (String)vRowTemp.get( 1 ) );
			    oPuesto.setSalario( ( (Double)vRowTemp.get( 2 ) ).floatValue() );
			    oPuesto.setPuedeAutorizar( ( (String)vRowTemp.get( 3 ) ).trim().compareTo( "1" ) == 0 );
			    arrRet[ i ] = oPuesto;
			} 
		} 
		return arrRet; 
	}
        //**********************************************************************
        public Puesto[] buscarPuestoCE() throws Exception{
	Puesto arrRet[]=null, oPuesto=null;
	ArrayList rst = null;
        ArrayList<Puesto> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaPuestoCE();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Puesto>();
			for (i = 0; i < rst.size(); i++) {
                            oPuesto = new Puesto();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oPuesto.setClave((String)vRowTemp.get(0));
                            oPuesto.setDescripcion((String)vRowTemp.get(1));
                            vObj.add(oPuesto);
			}
                    nTam = vObj.size();
                    arrRet = new Puesto[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        
        public Puesto[] buscarPuestoXGrupo( String sClaveGrupo ) throws Exception
	{
	    Puesto arrRet[] = null, oPuesto = null;
	    ArrayList rst = null;
	    String sQuery = "";
	    if( sClaveGrupo.isEmpty() )
	    {
		throw new Exception( "Puesto.buscarPuestoXGrupo: error de programación, faltan datos." );
	    }
	    else
	    {
		sQuery = "SELECT * FROM buscaPuestoXPersonalGrupo( '" + sClaveGrupo + "' );";
		oAD = new AccesoDatos();
		if( oAD.conectar() )
		{
		    rst = oAD.ejecutarConsulta( sQuery );
		    oAD.desconectar();
		}
		if( rst != null && rst.size() > 0 )
		{
		    ArrayList vRowTemp = null;
		    arrRet = new Puesto[ rst.size() ];
		    for( int i = 0; i < rst.size(); i++ )
		    {
			vRowTemp = (ArrayList)rst.get( i );
			oPuesto = new Puesto();
			oPuesto.setClave( (String)vRowTemp.get( 0 ) );
			oPuesto.setDescripcion( (String)vRowTemp.get( 1 ) );
			oPuesto.setSalario( ( (Double)vRowTemp.get( 2 ) ).floatValue() );
			oPuesto.setPuedeAutorizar( ( (String)vRowTemp.get( 3 ) ).compareTo( "1" ) == 0 );
			arrRet[ i ] = oPuesto;
		    }
		}
	    }
	    return arrRet;
	}
	
        //**********************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Puesto.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaPuesto();"; 
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
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Puesto.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaPuesto();"; 
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
		 if( this==null){   //completar llave
			throw new Exception("Puesto.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaPuesto();"; 
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
	public float getSalario() {
	return nSalario;
	}

	public void setSalario(float valor) {
	nSalario=valor;
	}

	public String getClave() {
	return sClave;
	}

	public void setClave(String valor) {
	sClave=valor;
	}

	public String getDescripcion() {
	return sDescripcion;
	}

	public void setDescripcion(String valor) {
	sDescripcion=valor;
	}
        
        public boolean isPuedeAutorizar()
    {
	return bPuedeAutorizar;
    }

    public void setPuedeAutorizar( boolean bPuedeAutorizar )
    {
	this.bPuedeAutorizar = bPuedeAutorizar;
    }
} 

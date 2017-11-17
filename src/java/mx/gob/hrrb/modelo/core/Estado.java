package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : 
 * @version: 1.0
*/

public class Estado implements Serializable{
    private AccesoDatos oAD;
    private Pais oPais;
    private String sClaveEdo;
    private String sDescripcionEdo;

        public Estado(){
            sClaveEdo="30";
            oPais=new Pais();
        }

	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Estado.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveEstado('"+sClaveEdo+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setDescripcionEdo((String)vRowTemp.get(1));
				bRet = true;
			}
		} 
		return bRet; 
	} 
	
        public Estado[] buscarTodos() throws Exception{
	Estado arrRet[]=null, oEstado=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "SELECT * FROM buscaTodosEstado();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Estado[rst.size()];
			for (i = 0; i < rst.size(); i++) {
			} 
		} 
		return arrRet; 
	} 
        
	public Estado[] buscarEstadosDesc( String sClavePais, String sTxt ) throws Exception
	{
	    Estado arrRet[] = null, oEstado = null;
	    ArrayList rst = null;
	    String sQuery = "SELECT * FROM buscaTodosEstado( " +
		    "'" + sClavePais + "', " +
		    "'" + sTxt + "' );";
	    oAD = new AccesoDatos();
	    if( oAD.conectar() )
	    {
		rst = oAD.ejecutarConsulta( sQuery );
		oAD.desconectar();
	    }
	    if( rst != null && rst.size() > 0 )
	    {
		ArrayList vRowTemp = null;
		arrRet = new Estado[ rst.size() ];
		for( int i = 0; i < rst.size(); i++ )
		{
		    vRowTemp = (ArrayList)rst.get( i );
		    oEstado = new Estado();
		    oEstado.setClaveEdo( (String)vRowTemp.get( 0 ) );
		    oEstado.setDescripcionEdo( (String)vRowTemp.get( 1 ) );
		    oEstado.getPais().setClavePais( (String)vRowTemp.get( 2 ) );
		    arrRet[ i ] = oEstado;
		}
	    }
	    
	    return arrRet;
	}
        
        //***************************************************************
        public Estado[] buscarEstados() throws Exception{
	Estado arrRet[]=null, oEdos=null;
	ArrayList rst = null;
        ArrayList<Estado> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaEstados();"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Estado>();
			for (i = 0; i < rst.size(); i++) {
                            oEdos = new Estado();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEdos.setClaveEdo((String)vRowTemp.get(0));
                            oEdos.setDescripcionEdo((String)vRowTemp.get(1));
                            oEdos.oPais.setClavePais((String)vRowTemp.get(2));
                            vObj.add(oEdos);
			}
                    nTam = vObj.size();
                    arrRet = new Estado[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        //***************************************************************
        
        public Estado[] buscaEstadoCP(String cp) throws Exception{
	Estado arrRet[]=null, oEdos=null;
	ArrayList rst = null;
        ArrayList<Estado> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaEstadoCP('"+cp+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<Estado>();
			for (i = 0; i < rst.size(); i++) {
                            oEdos = new Estado();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oEdos.setClaveEdo((String)vRowTemp.get(0));
                            oEdos.setDescripcionEdo((String)vRowTemp.get(1));
                            vObj.add(oEdos);
			}
                    nTam = vObj.size();
                    arrRet = new Estado[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        //***************************************************************
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Estado.insertar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM insertaEstado();"; 
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
			throw new Exception("Estado.modificar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM modificaEstado();"; 
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
			throw new Exception("Estado.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaEstado();"; 
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
        public Estado[] buscarEstadosPorPais(String clavepais) throws Exception{
	Estado arrRet[]=null, oEdo=null;
	ArrayList rst = null;
	String sQuery = "";
	int i=0;
		sQuery = "select * from buscaEstadosPorPais('"+clavepais+"'::varchar);";
                oAD=new AccesoDatos(); 
                //System.out.println(sQuery); //EDITADO: 16/12/16 (JMHG)
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			arrRet = new Estado [rst.size()];
			for (i = 0; i < rst.size(); i++) {
                            oEdo=new Estado ();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                             oEdo.setClaveEdo(((String)vRowTemp.get(0)).trim());
                            oEdo.setDescripcionEdo((String)vRowTemp.get(1));
                            arrRet[i]=oEdo;
			} 
		} 
		return arrRet; 
    }
        
        
	public Pais getPais() {
	return oPais;
	}

	public void setPais(Pais valor) {
	oPais=valor;
	}

	public String getClaveEdo() {
	return sClaveEdo;
	}

	public void setClaveEdo(String valor) {
	sClaveEdo=valor;
	}

	public String getDescripcionEdo() {
	return sDescripcionEdo;
	}

	public void setDescripcionEdo(String valor) {
	sDescripcionEdo=valor;
	}
} 

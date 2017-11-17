package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;

/**
 * Objetivo: 
 * @author : Francisco, JMHG, Rafael, Javier
 * @version: 1.0
*/

public class Turno implements Serializable{
    private AccesoDatos oAD;
    private String sClave;
    private String sDescripcion;
    private String sHorario;
    public static final String CVE_MATUTINO="MAT";
    public static final String CVE_VESPERTINO="VES";
    public static final String CVE_NOCTURNO="NOC";
    public static final String CVE_JORNADA="JAC";
    public static final String CVE_OTRO="OTR";

    public boolean buscar() throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
    if( this==null){   //completar llave
        throw new Exception("Turno.buscar: error de programación, faltan datos");
    }
    else{ 
        sQuery = "SELECT * FROM buscaLlaveTurno();"; 
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

    public boolean buscarTurnoPersonal(long tarjeta) throws Exception{
    boolean bRet = false;
    ArrayList rst = null;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("Turno.buscar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM buscaTurnoPersonalCapa("+tarjeta+");"; 
                    oAD=new AccesoDatos(); 
                    if (oAD.conectar()){ 
                            rst = oAD.ejecutarConsulta(sQuery); 
                            oAD.desconectar(); 
                    }
                    if (rst != null && rst.size() == 1) {
                            ArrayList vRowTemp = (ArrayList)rst.get(0);
                            setClave((String)vRowTemp.get(0));
                            bRet = true;
                    }
            } 
            return bRet; 
    }

    public Turno[] buscarTodos() throws Exception{
    Turno arrRet[]=null, oTurno=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaTodosTurno();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new Turno[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                    } 
            } 
            return arrRet; 
    }

    public Turno[] buscarTurnosCE() throws Exception{
    Turno arrRet[]=null, oTurno=null;
    ArrayList rst = null;
    ArrayList<Turno> vObj=null;
    String sQuery = "";
    int i=0, nTam=0;
            sQuery = "SELECT * FROM buscaTurnosCE();"; 
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    vObj = new ArrayList<Turno>();
                    for (i = 0; i < rst.size(); i++) {
                        oTurno = new Turno();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oTurno.setClave((String)vRowTemp.get(0));
                        oTurno.setDescripcion((String)vRowTemp.get(1));
                        vObj.add(oTurno);
                    }
                nTam = vObj.size();
                arrRet = new Turno[nTam];

                for (i=0; i<nTam; i++){
                    arrRet[i] = vObj.get(i);
                }
            } 
            return arrRet; 
    } 

    public int insertar() throws Exception{
    ArrayList rst = null;
    int nRet = 0;
    String sQuery = "";
             if( this==null){   //completar llave
                    throw new Exception("Turno.insertar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM insertaTurno();"; 
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
                    throw new Exception("Turno.modificar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM modificaTurno();"; 
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
                    throw new Exception("Turno.eliminar: error de programación, faltan datos");
            }else{ 
                    sQuery = "SELECT * FROM eliminaTurno();"; 
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

    public String getHorario() {
    return sHorario;
    }

    public void setHorario(String valor) {
    sHorario=valor;
    }

    //JMHG
    public String buscaHorarioTurno( int nOp ) throws Exception
    {
        String sRet = "";
        String sQuery = "";
        ArrayList rst = null;

        if( this == null )
        {
            throw new Exception("Turno.buscaHorarioTurno: error de programación, faltan datos");
        }
        else
        {
            if( nOp == 0 )
            {
                sQuery = "SELECT * FROM buscaHorarioTurno('', '" + sHorario + "');";
            }
            else
            {
                sQuery = "SELECT * FROM buscaHorarioTurno('" + sClave + "', '');";
            }
            oAD = new AccesoDatos();
            if( oAD.conectar() )
            {
                rst = oAD.ejecutarConsulta( sQuery );
                oAD.desconectar();
                if( rst != null && rst.size() == 1 )
                {
                    ArrayList vRowTemp = (ArrayList)rst.get( 0 );
                    sRet = (String)vRowTemp.get( nOp );
                }
            }
        }

        return sRet;
    }
    //----
    
    ///////////////////////////////////////////////////////////////////////////
    // Órdenes de servicio
        
    public Turno[] buscarTurnoAplicacionMedicamento() throws Exception{
    Turno arrRet[]=null, oTurno=null;
    ArrayList rst = null;
    String sQuery = "";
    int i=0;
            sQuery = "SELECT * FROM buscaturnoadministracionmedicamento();";
            oAD=new AccesoDatos(); 
            if (oAD.conectar()){ 
                    rst = oAD.ejecutarConsulta(sQuery); 
                    oAD.desconectar(); 
            }
            if (rst != null) {
                    arrRet = new Turno[rst.size()];
                    for (i = 0; i < rst.size(); i++) {
                        oTurno = new Turno();
                        ArrayList vRowTemp = (ArrayList)rst.get(i);
                        oTurno.setClave(((String)vRowTemp.get(0)).trim());
                        oTurno.setDescripcion(((String)vRowTemp.get(1)).trim());
                        oTurno.setHorario(((String)vRowTemp.get(2)).trim());
                        arrRet[i]=oTurno;
                        }
            } 
            return arrRet; 
    }

    public void equalsTurnoApliMed(Turno arrTurno[]){
                    for(Turno turno:arrTurno){
                        if(this.getDescripcion().equals(turno.getDescripcion())){
                            this.setDescripcion(turno.getDescripcion());
                            this.setClave(turno.getClave());
                            this.setHorario(turno.getHorario());
                        }
                    }
        }
       
////////////////////////////////////////////////////////////////////////////////
    //Javier
    public Turno[] buscarTurnosRequeridos() throws Exception{
            Turno arrTurno[] = null, oTur=null;
            ArrayList rst=null;
            String sQuery="";
            int i=0;
            sQuery=" SELECT * FROM buscarTurnosRequeridos();";
            oAD=  new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrTurno = new Turno[rst.size()];
                for(i=0;i<rst.size();i++){
                    ArrayList vRowTemp= (ArrayList)rst.get(i);
                    oTur= new Turno();
                    oTur.setClave((String)vRowTemp.get(0));
                    oTur.setDescripcion((String)vRowTemp.get(1));
                    arrTurno[i]=oTur;
                }
            }
            return arrTurno;
        }


} 

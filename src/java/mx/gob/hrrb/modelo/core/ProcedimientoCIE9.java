package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 *
 * @author : 
 * @version: 1.0
*/
public class ProcedimientoCIE9 extends ServicioCobrable implements Serializable {

private ArrayList arrCauses;
private Causes oCauses;
private String sClaveCie;
List<ProcedimientoCIE9> lListaProcecve;
List<ProcedimientoCIE9> lListaProcecveUrg;
List<ProcedimientoCIE9> lListaProcecveCE;

/*Agregados en Fase II*/
//Indica si el procedimiento requiere Quirófano o no
private boolean bRequiereQx; 


    public ProcedimientoCIE9() {
        sClaveCie="";
        bRequiereQx = false;
        oCauses = new Causes();
            lListaProcecve = null;
            lListaProcecveUrg = null;
            lListaProcecveCE = null;
        }

        @Override
    public boolean buscar() throws Exception {
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
        if (this.sClaveCie == null || this.sClaveCie.equals("")) {   //completar llave
			throw new Exception("ProcedimientoCIE9.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaLlaveProcedimientoCIE9('" + this.sClaveCie + "');";
            //System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                this.sClaveCie = (String) vRowTemp.get(0);
                this.sDescripcion = (String) vRowTemp.get(1);
                this.bRequiereQx = Double.parseDouble((String) vRowTemp.get(2)) == 1;
                this.sClave = Integer.toString(((Double) vRowTemp.get(3)).intValue());

				bRet = true;
			}
		} 
		return bRet; 
	} 
	
        @Override
        public ProcedimientoCIE9[] buscarTodos() throws Exception{
	ProcedimientoCIE9 arrRet[]=null, oProcedimientoCIE9=null;
	ArrayList rst = null;
        int res=0;
        ArrayList<ProcedimientoCIE9> vObj=null;
	String sQuery = "";
	int i=0;
		//sQuery = "SELECT * FROM buscaTodosProcedimientoCIE9();";
		sQuery = "SELECT * FROM procedimientosCIE9 WHERE sclave = '01' or sclave <= '001';";
                oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj=new ArrayList<ProcedimientoCIE9>();
			for (i = 0; i < rst.size(); i++) {
                            oProcedimientoCIE9 = new ProcedimientoCIE9();
                            ArrayList<Object> vRowTemp =(ArrayList)rst.get(i);
                            oProcedimientoCIE9.setClave((String)vRowTemp.get(0));
                            oProcedimientoCIE9.setDescripcion((String)vRowTemp.get(1));
                            vObj.add(oProcedimientoCIE9);
			} 
                        res=vObj.size();
                        arrRet = new ProcedimientoCIE9[res];
                        
                        for(i=0; i<res; i++){
                            arrRet[i]=vObj.get(i);
                        }
                        //System.out.println("DATOS DEL ARREGLO " + arrRet);
		} 
		return arrRet; 
	} 
        
        @Override
	public int insertar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if (this.sClaveCie == null || this.sClaveCie.equals("")
                || this.sDescripcion == null || this.sDescripcion.equals("")) {
			throw new Exception("ProcedimientoCIE9.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaProcedimientoCIE9('" + this.sUsuarioFirmado + "','"
                    + this.sClaveCie + "','"
                    + this.sDescripcion + "','"
                    + (this.bRequiereQx ? "1" : "0") + "',"
                    + this.sClave + "::integer,null::character varying);";   //por si se requieren los campos de servicio cobrable y cve tabulador
            //System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
                                        
				}
			}
		} 
		return nRet; 
	} 

        @Override
    public int modificar() throws Exception {
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if (this.sClaveCie == null || this.sClaveCie.equals("")
                || this.sDescripcion == null || this.sDescripcion.equals("")
                || this.sClave == null || this.sClave.equals("")) {
            //System.out.println(sClaveCie+" "+sDescripcion+" "+sClave+"");
			throw new Exception("ProcedimientoCIE9.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaProcedimientoCIE9('" + this.sUsuarioFirmado + "','"
                    + this.sClaveCie + "','"
                    + this.sDescripcion + "','"
                    + (this.bRequiereQx ? "1" : "0") + "','"
                    + this.sClave + "');";
            //System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
				rst = oAD.ejecutarConsulta(sQuery);
				oAD.desconectar(); 
				if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    nRet = ((Double) vRowTemp.get(0)).intValue();
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
			throw new Exception("ProcedimientoCIE9.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaProcedimientoCIE9();"; 
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

    /**
     * @return todos los procedimientos CIE 9 MC
     */
    public List<ProcedimientoCIE9> buscarTodosCie9() throws Exception {
        List<ProcedimientoCIE9> arrRet = null;
        ProcedimientoCIE9 oProcCIE9 = null;
        ArrayList rst = null;
        int res = 0;
        String sQuery = "";
        int i = 0;

        sQuery = "SELECT * FROM buscaTodosProcedimientoCIE9();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                ArrayList<Object> vRowTemp = (ArrayList) rst.get(i);
                oProcCIE9 = new ProcedimientoCIE9();
                oProcCIE9.setClaveCie((String) vRowTemp.get(0));
                oProcCIE9.setDescripcion((String) vRowTemp.get(1));
                oProcCIE9.setRequiereQx(Double.parseDouble((String) vRowTemp.get(2)) == 1);
                oProcCIE9.setClave(Integer.toString(((Double) vRowTemp.get(3)).intValue()));
                if (oProcCIE9.sClaveCie.trim().equals("016")) {
                    //System.out.println(oProcCIE9.sClaveCie + oProcCIE9.sDescripcion + oProcCIE9.getRequiereQx());
                }
                arrRet.add(oProcCIE9);
            }
        }
        return arrRet;
    }

	 //Busca Coincidencias en descricion de procedimientos
        public ProcedimientoCIE9[] buscarPronostico(String proce) throws Exception{
	ProcedimientoCIE9 arrRet[]=null, oDiag=null;
	ArrayList rst = null;
        ArrayList<ProcedimientoCIE9> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaProcedimiento('"+proce+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<ProcedimientoCIE9>();
			for (i = 0; i < rst.size(); i++) {
                            oDiag = new ProcedimientoCIE9();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDiag.setDescripcion((String)vRowTemp.get(0));
                            vObj.add(oDiag);
                            //System.out.println("buscar-proc:-->"+oDiag.getDescripcion().toString());
			}
                    nTam = vObj.size();
                    arrRet = new ProcedimientoCIE9[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        
         //Busca Coincidencias en descricion de procedimientos
        public ProcedimientoCIE9[] buscarClavesCIE9(String proce) throws Exception{
	ProcedimientoCIE9 arrRet[]=null, oProce=null;
	ArrayList rst = null;
        ArrayList<ProcedimientoCIE9> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaClavesProc('"+proce+"', 1::smallint);"; 
                //System.out.println(sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<ProcedimientoCIE9>();
			for (i = 0; i < rst.size(); i++) {
                            oProce = new ProcedimientoCIE9();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oProce.setClave((String)vRowTemp.get(0));
                            oProce.oCauses.setTipo((String)vRowTemp.get(2));
                            if(oProce.oCauses.getTipo().equals("T4602") || oProce.oCauses.getTipo().equals("T4606") || oProce.oCauses.getTipo().equals("T4607") || oProce.oCauses.getTipo().equals("T4609"))
                                oProce.oCauses.setClave((String)vRowTemp.get(1));
                            else
                                oProce.oCauses.setClave("");                            
                            vObj.add(oProce);
                            //System.out.println("buscarCIE10: CIE10-->"+oProce.getClave().toString());
                        }
                    nTam = vObj.size();
                    arrRet = new ProcedimientoCIE9[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}        
        
         //Busca Coincidencias en descricion de procedimientos
        public ProcedimientoCIE9[] buscarClavesCIE9Urg(String proce) throws Exception{
	ProcedimientoCIE9 arrRet[]=null, oProce=null;
	ArrayList rst = null;
        ArrayList<ProcedimientoCIE9> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaClavesProc('"+proce+"', 2::smallint);"; 
                //System.out.println(sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<ProcedimientoCIE9>();
			for (i = 0; i < rst.size(); i++) {
                            oProce = new ProcedimientoCIE9();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oProce.setClave((String)vRowTemp.get(0));
                            oProce.oCauses.setTipo((String)vRowTemp.get(2));
                            if(oProce.oCauses.getTipo().equals("T4601") || oProce.oCauses.getTipo().equals("T4604") || oProce.oCauses.getTipo().equals("T4606") || oProce.oCauses.getTipo().equals("T4609"))
                                oProce.oCauses.setClave((String)vRowTemp.get(1));
                            else
                                oProce.oCauses.setClave("");                            
                            vObj.add(oProce);
                            //System.out.println("buscarCIE10: CIE10-->"+oProce.getClave().toString());
                        }
                    nTam = vObj.size();
                    arrRet = new ProcedimientoCIE9[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}          
        
         //Busca Coincidencias en descricion de procedimientos
        public ProcedimientoCIE9[] buscarClavesCIE9CE(String proce) throws Exception{
	ProcedimientoCIE9 arrRet[]=null, oProce=null;
	ArrayList rst = null;
        ArrayList<ProcedimientoCIE9> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaClavesProc('"+proce+"', 3::smallint);"; 
                //System.out.println(sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<ProcedimientoCIE9>();
			for (i = 0; i < rst.size(); i++) {
                            oProce = new ProcedimientoCIE9();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oProce.setClave((String)vRowTemp.get(0));
                            oProce.oCauses.setTipo((String)vRowTemp.get(2));
                            if(oProce.oCauses.getTipo().equals("T4600") || oProce.oCauses.getTipo().equals("T4604") || oProce.oCauses.getTipo().equals("T4607") || oProce.oCauses.getTipo().equals("T4609"))
                                oProce.oCauses.setClave((String)vRowTemp.get(1));
                            else
                                oProce.oCauses.setClave("");                            
                            vObj.add(oProce);
                            //System.out.println("buscarCIE10: CIE10-->"+oProce.getClave().toString());
                        }
                    nTam = vObj.size();
                    arrRet = new ProcedimientoCIE9[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}  
        
       //Retorna lista Procedimiento
     public List<ProcedimientoCIE9> getListaProcedimiento(String txt) throws Exception{
       List<ProcedimientoCIE9>lListaProce = null;
       
          lListaProce= new ArrayList<ProcedimientoCIE9>(Arrays.asList(
                   (new ProcedimientoCIE9()).buscarPronostico(txt)));
       
        return lListaProce;
    }    
    
     public List<String> completar(String sTxt) throws Exception{
        List<String> arrRet = new ArrayList<String>();
        List<ProcedimientoCIE9> lis= getListaProcedimiento(sTxt);
        for (ProcedimientoCIE9 li : lis) {
            if (sp_ascii(li.getDescripcion()).contains(sTxt)) {
                arrRet.add(li.getDescripcion());
            }
        }
    return arrRet;
    }         

    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        String output = input;
            for (int i=0; i<original.length(); i++) {
                // Reemplazamos los caracteres especiales.
                output = output.replace(original.charAt(i), ascii.charAt(i));
            }//for i
        return output;
    }  
    
    public List<ProcedimientoCIE9> getClaveCIE9(String txt) throws Exception{
         lListaProcecve = null;
        // System.out.println("getClaves: "+txt);
        lListaProcecve= new ArrayList<ProcedimientoCIE9>(Arrays.asList(
                   (new ProcedimientoCIE9()).buscarClavesCIE9(txt)));
       
        return lListaProcecve;
    }     
    
    public List<ProcedimientoCIE9> getClaveCIE9Urg(String txt) throws Exception{
         lListaProcecveUrg = null;
        // System.out.println("getClaves: "+txt);
        lListaProcecveUrg= new ArrayList<ProcedimientoCIE9>(Arrays.asList(
                   (new ProcedimientoCIE9()).buscarClavesCIE9Urg(txt)));
       
        return lListaProcecveUrg;
    } 

    public List<ProcedimientoCIE9> getClaveCIE9CE(String txt){
         lListaProcecveCE = null;
        // System.out.println("getClaves: "+txt);
       try {
           lListaProcecveCE= new ArrayList<ProcedimientoCIE9>(Arrays.asList(
                   (new ProcedimientoCIE9()).buscarClavesCIE9CE(txt)));
       } catch (Exception ex) {
           Logger.getLogger(ProcedimientoCIE9.class.getName()).log(Level.SEVERE, null, ex);
       }
        return lListaProcecveCE;
    }     
     
    public String getClaveCIE9() {
        String x="";
        //System.out.println("Entra a claveCIE9 lista: "+lListaProcecve);
        if(lListaProcecve==null || lListaProcecve.isEmpty()){
            x="";
            setClave(x);
        }
        else{
            x=lListaProcecve.get(0).getClave();
            setClave(x);        
        }
           // System.out.println("X: "+x);
        return x;
    }    
     
    public String getClaveCAUSES() {
    String y="";
    if(lListaProcecve!=null && !lListaProcecve.isEmpty()){
        y=lListaProcecve.get(0).getCauses().getClave();
    }
    oCauses.setClave(y);
    return y;
}

    public String getClaveCIE9Urg() {
        String x="";
        if (lListaProcecveUrg!=null &&
            !lListaProcecveUrg.isEmpty()){
            x=lListaProcecveUrg.get(0).getClave();
        }
        setClave(x); 
        return x;
    }
    ///////////////////////////////////////////////////////////////////////
    //Órdenes de servicio
    
     
     public boolean buscarClavexProcedimiento(String proce) throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
            System.out.println("El procedimiento que lleva es --->" + proce);
            sQuery = "SELECT * FROM buscaClaveProceCIE9('" + proce +"');";
            oAD = new AccesoDatos();
            if (oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                this.setClave(((String) vRowTemp.get(0)).trim());
                bRet = true;
            }
            return bRet;
     }
        public boolean buscarPorProcedimiento() throws Exception{
            boolean bRet = false;
            ArrayList rst = null;
            String sQuery = "";
            sQuery = "SELECT * FROM buscaclaveprocedimiento('" + this.getDescripcion() +"');";
            oAD = new AccesoDatos();
            if (oAD.conectar()){
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                this.setClave(((String) vRowTemp.get(0)).trim());
                this.setDescripcion(((String) vRowTemp.get(1)));
                bRet = true;
            }
            return bRet;
        }
    ///////////////////////////////////////////////////////////////////////
    
    public void setDescripcionCompleta(String valor) throws Exception{
	sDescripcion=valor;
        if (valor.compareTo("")!=0 || !valor.isEmpty() || valor!=null){
            getClaveCIE9(sDescripcion);
            getClaveCIE9Urg(sDescripcion);
            getClaveCIE9CE(sDescripcion);
        }        
    }

    /**
     * @return the oCauses
     */
    public Causes getCauses() {
        return oCauses;
    }

    /**
     * @param oCauses the oCauses to set
     */
    public void setCauses(Causes oCauses) {
        this.oCauses = oCauses;
    }
    
    public boolean getRequiereQx(){
        return this.bRequiereQx;
    }

    public void setRequiereQx(boolean valor) {
        this.bRequiereQx = valor;
    }

    /**
     * @return the lListaProcecve
     */
    public List<ProcedimientoCIE9> getListaProcecve() {
        return lListaProcecve;
} 

    /**
     * @param lListaProcecve the lListaProcecve to set
     */
    public void setListaProcecve(List<ProcedimientoCIE9> lListaProcecve) {
        this.lListaProcecve = lListaProcecve;
    }

    /**
     * @return the sClaveCie
     */
    public String getClaveCie() {
        return sClaveCie;
    }

    /**
     * @param sClaveCie the sClaveCie to set
     */
    public void setClaveCie(String sClaveCie) {
        this.sClaveCie = sClaveCie;
    }

}

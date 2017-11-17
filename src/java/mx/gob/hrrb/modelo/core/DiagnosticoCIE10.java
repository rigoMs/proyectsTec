package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 *
 * @author : 
 * @version: 1.0
*/
public class DiagnosticoCIE10 implements Serializable {

	private AccesoDatos oAD;
	private String sClave;
        private String sDescripcionDiag;
        private Causes oCauses;
    private boolean bEsCapasits;
    private List<DiagnosticoCIE10> lListaDiagcve;
    private List<DiagnosticoCIE10> lListaDiagcveUrg;
    private List<DiagnosticoCIE10> lListaDiagcveHosp;
        private int nCantidadH;
        private int nCantidadM;
        private int nTotal;
    private String sUsuarioFirm;
    private ServicioCobrable oServCob;

    public DiagnosticoCIE10() {
        oCauses = new Causes();
        lListaDiagcve = null;
        lListaDiagcveUrg = null;
        lListaDiagcveHosp = null;
        sClave = "";
        sDescripcionDiag = "";
        bEsCapasits = false;
        oServCob = new ServicioCobrable();

        Firmado oFirm = new Firmado();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuarioFirm = oFirm.getUsu().getIdUsuario();
        }
    }
        
	public boolean buscar() throws Exception{
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( getClave().equals("")){   //completar llave
			throw new Exception("DiagnosticoCIE10.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveDiagnosticoCIE10('"+getClave()+"');"; 
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                sClave = (String) vRowTemp.get(0).toString().trim();
                sDescripcionDiag = (String) vRowTemp.get(1);
                bEsCapasits = ((String) vRowTemp.get(2)).charAt(0) == 'S';
				bRet = true;
			}
		} 
		return bRet; 
	}
        
    public List<DiagnosticoCIE10> buscarTodos() throws Exception {
        ArrayList<DiagnosticoCIE10> arrRet = null;
        DiagnosticoCIE10 oDiagCIE10 = null;
	ArrayList rst = null;
	String sQuery = "";
        int i = 0;
		sQuery = "SELECT * FROM buscaTodosDiagnosticoCIE10();"; 
        //System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
            arrRet = new ArrayList<>();
			for (i = 0; i < rst.size(); i++) {
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oDiagCIE10 = new DiagnosticoCIE10();

                oDiagCIE10.sClave = (String) vRowTemp.get(0).toString().trim();
                oDiagCIE10.sDescripcionDiag = (String) vRowTemp.get(1);
                oDiagCIE10.bEsCapasits = ((String) vRowTemp.get(2)).charAt(0) == 'S';

                arrRet.add(oDiagCIE10);
			} 
		} 
		return arrRet; 
	} 

    public int insertar() throws Exception {
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")
                || this.sDescripcionDiag == null || this.sDescripcionDiag.equals("")) {
			throw new Exception("DiagnosticoCIE10.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaDiagnosticoCIE10('"
                    + this.sUsuarioFirm + "','"
                    + this.sClave + "','"
                    + this.sDescripcionDiag + "','"
                    + (this.bEsCapasits ? "S" : "N") + "');";
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

    public int modificar() throws Exception {
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
        if (this.sClave == null || this.sClave.equals("")
                || this.sDescripcionDiag == null || this.sDescripcionDiag.equals("")) {   //completar llave
			throw new Exception("DiagnosticoCIE10.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaDiagnosticoCIE10('"
                    + this.sUsuarioFirm + "','"
                    + this.sClave + "','"
                    + this.sDescripcionDiag + "','"
                    + (this.bEsCapasits ? "S" : "N") + "');";
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

    public int eliminar() throws Exception {
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("DiagnosticoCIE10.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaDiagnosticoCIE10();"; 
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

        //Busca Coincidencias en descricion de diagnostico
        public DiagnosticoCIE10[] buscarDiagnostico(String diag) throws Exception{
	DiagnosticoCIE10 arrRet[]=null, oDiag=null;
	ArrayList rst = null;
        ArrayList<DiagnosticoCIE10> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaDiagnostico('"+diag+"');"; 
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<DiagnosticoCIE10>();
			for (i = 0; i < rst.size(); i++) {
                            oDiag = new DiagnosticoCIE10();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDiag.setDescripcionDiag((String)vRowTemp.get(0));
                            vObj.add(oDiag);
               
			}
                    nTam = vObj.size();
                    arrRet = new DiagnosticoCIE10[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 

         //Busca Coincidencias en descricion de diagnostico
        public DiagnosticoCIE10[] buscarClavesCIE10CAUSES(String diag) throws Exception{
	DiagnosticoCIE10 arrRet[]=null, oDiag=null;
	ArrayList rst = null;
        ArrayList<DiagnosticoCIE10> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaClaves('"+diag+"', 1::smallint);"; 
                //System.out.println(sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<DiagnosticoCIE10>();
			for (i = 0; i < rst.size(); i++) {
                            oDiag = new DiagnosticoCIE10();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDiag.setClave((String)vRowTemp.get(0));
                            oDiag.oCauses.setTipo((String)vRowTemp.get(2));
                            if(oDiag.oCauses.getTipo().equals("T4600") || oDiag.oCauses.getTipo().equals("T4604") || oDiag.oCauses.getTipo().equals("T4607") || oDiag.oCauses.getTipo().equals("T4609"))
                                oDiag.oCauses.setClave((String)vRowTemp.get(1));
                            else
                                oDiag.oCauses.setClave("");
                            vObj.add(oDiag);
                       }
                    nTam = vObj.size();
                    arrRet = new DiagnosticoCIE10[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 

         //Busca Coincidencias en descricion de diagnostico
        public DiagnosticoCIE10[] buscarClavesCIE10CAUSESUrg(String diag) throws Exception{
	DiagnosticoCIE10 arrRet[]=null, oDiag=null;
	ArrayList rst = null;
        ArrayList<DiagnosticoCIE10> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaClaves('"+diag+"', 2::smallint);"; 
                //System.out.println(sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<DiagnosticoCIE10>();
			for (i = 0; i < rst.size(); i++) {
                            oDiag = new DiagnosticoCIE10();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDiag.setClave((String)vRowTemp.get(0));
                            oDiag.oCauses.setTipo((String)vRowTemp.get(2));
                            if(oDiag.oCauses.getTipo().equals("T4601") || oDiag.oCauses.getTipo().equals("T4604") || oDiag.oCauses.getTipo().equals("T4606") || oDiag.oCauses.getTipo().equals("T4609"))
                                oDiag.oCauses.setClave((String)vRowTemp.get(1));
                            else
                                oDiag.oCauses.setClave("");
                            vObj.add(oDiag);
                       }
                    nTam = vObj.size();
                    arrRet = new DiagnosticoCIE10[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	} 
        
         //Busca Coincidencias en descricion de diagnostico
        public DiagnosticoCIE10[] buscarClavesCIE10CAUSESHosp(String diag) throws Exception{
	DiagnosticoCIE10 arrRet[]=null, oDiag=null;
	ArrayList rst = null;
        ArrayList<DiagnosticoCIE10> vObj=null;
	String sQuery = "";
	int i=0, nTam=0;
		sQuery = "SELECT * FROM buscaClaves('"+diag+"', 3::smallint);"; 
                //System.out.println(sQuery);
		oAD=new AccesoDatos(); 
		if (oAD.conectar()){ 
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
			vObj = new ArrayList<DiagnosticoCIE10>();
			for (i = 0; i < rst.size(); i++) {
                            oDiag = new DiagnosticoCIE10();
                            ArrayList vRowTemp = (ArrayList)rst.get(i);
                            oDiag.setClave((String)vRowTemp.get(0));
                            oDiag.oCauses.setTipo((String)vRowTemp.get(2));
                            if(oDiag.oCauses.getTipo().equals("T4602") || oDiag.oCauses.getTipo().equals("T4606") || oDiag.oCauses.getTipo().equals("T4607") || oDiag.oCauses.getTipo().equals("T4609"))
                                oDiag.oCauses.setClave((String)vRowTemp.get(1));
                            else
                                oDiag.oCauses.setClave("");
                            vObj.add(oDiag);
                       }
                    nTam = vObj.size();
                    arrRet = new DiagnosticoCIE10[nTam];

                    for (i=0; i<nTam; i++){
                        arrRet[i] = vObj.get(i);
                    }
		} 
		return arrRet; 
	}         

     //Retorna lista Diagnosticos
     public List<DiagnosticoCIE10> getListaDiagnostico(String txt) throws Exception{
       List<DiagnosticoCIE10>lListaDiag = null;
       
           lListaDiag= new ArrayList<DiagnosticoCIE10>(Arrays.asList(
                   (new DiagnosticoCIE10()).buscarDiagnostico(txt)));
       
        return lListaDiag;
    }
   
    public List<String> completar(String sTxt) throws Exception{
    long start = System.currentTimeMillis();
    ArrayList<String> arrRet = new ArrayList<String>();
    List<DiagnosticoCIE10> lis= getListaDiagnostico(sTxt);
            for (DiagnosticoCIE10 li : lis) {
                //if (sp_ascii(lis.get(i).getDescripcionDiag()).startsWith(sTxt)){
                if (sp_ascii(li.getDescripcionDiag()).contains(sTxt)) {
                    arrRet.add(li.getDescripcionDiag());
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
    
    public String getClave2CE() {
        String y="";
        //System.out.println("Entra a getClave2");
        if(getListaDiagcve()==null || getListaDiagcve().isEmpty())
            y="";
        else{
            y=getListaDiagcve().get(0).getCauses().getClave();
           // System.out.println("Y: "+y);
        }
        return y;
    }
    
    public String getClave1CE() {
        String x="";
        //System.out.println("Entra a clave1");
        if(getListaDiagcve()==null || getListaDiagcve().isEmpty())
            x="";
        else{
            x=getListaDiagcve().get(0).getClave();
            setClave(x);
        }
            //System.out.println("Clave: "+ getClave());
        return x;
    }
    
    public String getClave2() {
        String y="";
        //System.out.println("Entra a getClave2");
        if(getListaDiagcveUrg()==null || getListaDiagcveUrg().isEmpty())
            y="";
        else{
            y=getListaDiagcveUrg().get(0).getCauses().getClave();
           // System.out.println("Y: "+y);
        }
        return y;
    }
    
    public String getClave1() {
        String x="";
        //System.out.println("Entra a clave1");
        if(getListaDiagcveUrg()==null || getListaDiagcveUrg().isEmpty())
            x="";
        else{
            x=getListaDiagcveUrg().get(0).getClave();
            setClave(x);
        }
            //System.out.println("X: "+x);
        return x;
    }    

      //Retorna Claves Causes y CIE10 de un determinado 
     public List<DiagnosticoCIE10> getClavesCIE10Causes(String txt) throws Exception{
         setListaDiagcve(null);
       
            setListaDiagcve(new ArrayList<DiagnosticoCIE10>(Arrays.asList(
                    (new DiagnosticoCIE10()).buscarClavesCIE10CAUSES(txt))));
       
        return getListaDiagcve();
    }
     
      //Retorna Claves Causes y CIE10 Urgencias
     public List<DiagnosticoCIE10> getClavesCIE10CausesUrg(String txt) throws Exception{
         setListaDiagcveUrg(null);
       
            setListaDiagcveUrg(new ArrayList<DiagnosticoCIE10>(Arrays.asList(
                    (new DiagnosticoCIE10()).buscarClavesCIE10CAUSESUrg(txt))));
       
        return getListaDiagcveUrg();
    }
     
      //Retorna Claves Causes y CIE10 Hospitalizacion
     public List<DiagnosticoCIE10> getClavesCIE10CausesHosp(String txt) throws Exception{
         setListaDiagcveHosp(null);
       
            setListaDiagcveHosp(new ArrayList<DiagnosticoCIE10>(Arrays.asList(
                    (new DiagnosticoCIE10()).buscarClavesCIE10CAUSESHosp(txt))));
       
        return getListaDiagcveHosp();
    }     
     
    public String getClaveCIE10() {
        String x="";
        if(getListaDiagcveHosp()==null || getListaDiagcveHosp().isEmpty()){
            x="";       
            setClave(x);
        }
        else{
            x=getListaDiagcveHosp().get(0).getClave();
            setClave(x);
        }
        return x;
    }

    public String getClaveCAUSES() {
        String y="";
        if(getListaDiagcveHosp()==null || getListaDiagcveHosp().isEmpty()){
            y="";       
            oCauses.setClave(y);
        }
        else{
            y=getListaDiagcveHosp().get(0).getCauses().getClave();
            oCauses.setClave(y);
        }
        return y;
    }

    public String getClave() {
	return sClave;
    }

    public void setClave(String valor) {
	sClave=valor;
    }

    public String getDescripcionDiag() {
	return sDescripcionDiag;
    }

    public void setDescripcionDiag(String valor) throws Exception{
	sDescripcionDiag=valor;
            if (valor.compareTo("")!=0 || !valor.isEmpty() || valor!=null){

                getClavesCIE10Causes(sDescripcionDiag);           
                getClavesCIE10CausesUrg(sDescripcionDiag);            
                getClavesCIE10CausesHosp(sDescripcionDiag);
            }
    }

    public Causes getCauses() {
        return oCauses;
    }

    public void setCauses(Causes oCauses) {
        this.oCauses = oCauses;
    }       

    public List<DiagnosticoCIE10> getListaDiagcve() {
        return lListaDiagcve;
    }

    public void setListaDiagcve(List<DiagnosticoCIE10> lListaDiagcve) {
        this.lListaDiagcve = lListaDiagcve;
    }

    /**
     * @return the nCantidadH
     */
    public int getCantidadH() {
        return nCantidadH;
    }

    /**
     * @param nCantidadH the nCantidadH to set
     */
    public void setCantidadH(int nCantidadH) {
        this.nCantidadH = nCantidadH;
    }

    /**
     * @return the nCantidadM
     */
    public int getCantidadM() {
        return nCantidadM;
    }

    /**
     * @param nCantidadM the nCantidadM to set
     */
    public void setCantidadM(int nCantidadM) {
        this.nCantidadM = nCantidadM;
    }

    /**
     * @return the nTotal
     */
    public int getTotal() {
        return nTotal;
    }

    /**
     * @param nTotal the nTotal to set
     */
    public void setTotal(int nTotal) {
        this.nTotal = nTotal;
    }

    /**
     * @return the lListaDiagcveUrg
     */
    public List<DiagnosticoCIE10> getListaDiagcveUrg() {
        return lListaDiagcveUrg;
    }

    /**
     * @param lListaDiagcveUrg the lListaDiagcveUrg to set
     */
    public void setListaDiagcveUrg(List<DiagnosticoCIE10> lListaDiagcveUrg) {
        this.lListaDiagcveUrg = lListaDiagcveUrg;
    }

    /**
     * @return the lListaDiagcveHosp
     */
    public List<DiagnosticoCIE10> getListaDiagcveHosp() {
        return lListaDiagcveHosp;
    }

    /**
     * @param lListaDiagcveHosp the lListaDiagcveHosp to set
     */
    public void setListaDiagcveHosp(List<DiagnosticoCIE10> lListaDiagcveHosp) {
        this.lListaDiagcveHosp = lListaDiagcveHosp;
    }
         
    /**
     * @return the bEsCapasits
     */
    public boolean getEsCapasits() {
        return bEsCapasits;
} 

    /**
     * @param bEsCapasits the bEsCapasits to set
     */
    public void setEsCapasits(boolean bEsCapasits) {
        this.bEsCapasits = bEsCapasits;
    }

    /**
     * @return the oServCob
     */
    public ServicioCobrable getServCob() {
        return oServCob;
    }

    /**
     * @param oServCob the oServCob to set
     */
    public void setServCob(ServicioCobrable oServCob) {
        this.oServCob = oServCob;
    }

}

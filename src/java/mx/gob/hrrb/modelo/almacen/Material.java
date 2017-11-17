package mx.gob.hrrb.modelo.almacen;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import mx.gob.hrrb.modelo.core.Parametrizacion;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 *
 * @author GIL
 */
public class Material extends ServicioCobrable implements Serializable {
private AccesoDatos oAD;    
private String sNombre;
private String sClaveMaterial=null;
private Parametrizacion oPresentacion;
private Parametrizacion oConcentrado; 
    
    public Material(){
        oPresentacion = new Parametrizacion();
        oConcentrado = new Parametrizacion();
    }

    @Override
    public boolean buscar() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        
        String sQuery = "";
               //sQuery = "SELECT * FROM buscaLlaveMaterial('" + getClaveMaterial()+"'::character varying);";
        sQuery = "SELECT * FROM buscaLlaveMaterial('"+getClaveMaterial()+"');"; 

        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();

        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            
            this.setNombre(((String) vRowTemp.get(1)));           
            this.getPresentacion().setValor(((String) vRowTemp.get(2)));
            this.getConcentrado().setValor(((String) vRowTemp.get(3)));
            bRet = true;
        }
        return bRet;
    }
    
    public Material[] buscarNombreComplete(String nombre) throws Exception {
    Material arrRet[] = null, oDiag = null;
    ArrayList rst = null;
    ArrayList<Material> vObj = null;
    String sQuery = "";
    int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaXNombreMaterial('"+nombre+"');";
        
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Material>();
            for (i = 0; i < rst.size(); i++) {
                oDiag = new Material();
                ArrayList vRowTemp = (ArrayList) rst.get(i);     
                oDiag.setNombre((String) vRowTemp.get(0));
                vObj.add(oDiag);
            }
            nTam = vObj.size();
            arrRet = new Material[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }
    
    public boolean buscarMaterialSalida() throws Exception {
            boolean bRet = false;
            ArrayList rst = null;

            String sQuery = "";
            System.out.println(getNombre());

            sQuery = "SELECT * FROM buscaMaterial('"+getNombre()+"');"; 

            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();

            }
            if (rst != null && rst.size() >= 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);     
                   this.setClaveMaterial(((String) vRowTemp.get(0)));  
                this.setNombre(((String) vRowTemp.get(1)));  
                this.getPresentacion().setValor((String)vRowTemp.get(2));

                String a=getClaveMaterial();
                bRet = true;
            }
            return bRet;
        }

    public InventarioMateriales[]buscaLotes(String a)throws Exception{

               InventarioMateriales arrRet[]=null,oProv=null;
               ArrayList rst=null;
               ArrayList<InventarioMateriales>vObj=null;
               String sQuery="";
               int i=0,nTam=0;

                   sQuery="SELECT *FROM buscaLotes('"+a+"');";
                   System.out.println(sQuery);
                   oAD=new AccesoDatos();
                   if(oAD.conectar()){
                       rst=oAD.ejecutarConsulta(sQuery);
                       oAD.desconectar();
                   }
                   if (rst != null) {
                           vObj = new ArrayList<InventarioMateriales>();
                           for (i = 0; i < rst.size(); i++) {
                               oProv = new InventarioMateriales();
                               ArrayList vRowTemp = (ArrayList)rst.get(i);
                               oProv.setLote((String)vRowTemp.get(0));


                               vObj.add(oProv);
                           }
                       nTam = vObj.size();
                       arrRet = new InventarioMateriales[nTam];

                       for (i=0; i<nTam; i++){
                           arrRet[i] = vObj.get(i);
                       }
                   } 
                   return arrRet; 


           }

    public boolean buscarNombresMateriales() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        
        String sQuery = "";
               //sQuery = "SELECT * FROM buscaLlaveMaterial('" + getClaveMaterial()+"'::character varying);";
        sQuery = "SELECT * FROM buscaNombreMateriales('"+getNombre()+"');"; 

        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();

        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setClaveMaterial((String)vRowTemp.get(0));
            this.setNombre(((String) vRowTemp.get(1)));           
            this.getPresentacion().setValor(((String) vRowTemp.get(2)));
            this.getConcentrado().setValor(((String) vRowTemp.get(3)));
            bRet = true;
        }
        return bRet;
    }
    
    public boolean buscaMaterial () throws Exception {
    boolean bRet = false;
    ArrayList rst = null;
        
        String sQuery = "";
               //sQuery = "SELECT * FROM buscaLlaveMaterial('" + getClaveMaterial()+"'::character varying);";
        sQuery = "SELECT * FROM buscaNombreMateriales('"+getNombre()+"');"; 

        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setClaveMaterial((String)vRowTemp.get(0));
            this.setNombre(((String) vRowTemp.get(1)));           
            this.getPresentacion().setValor(((String) vRowTemp.get(2)));
            this.getConcentrado().setValor(((String) vRowTemp.get(3)));
            bRet = true;
        }
        return bRet;
    }
    
    @Override
    public Material[] buscarTodos() throws Exception {
        Material arrRet[] = null, oMaterial = null;
        ArrayList rst = null;
        ArrayList<Material> vObj = null;
        String sQuery = "";
        int i = 0;
         sQuery = "SELECT * FROM buscaTodosMaterial();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new Material[rst.size()];
            vObj = new ArrayList<Material>();
            for (i = 0; i < rst.size(); i++) {
                oMaterial = new Material();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMaterial.setClaveMaterial(((String) vRowTemp.get(0)));
                oMaterial.setNombre(((String) vRowTemp.get(1)));                
                oMaterial.getPresentacion().setValor(((String) vRowTemp.get(2)));
                oMaterial.getConcentrado().setValor(((String) vRowTemp.get(3)));

                vObj.add(oMaterial);
            }
            int nTam = vObj.size();
            arrRet = new Material[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public Material[] buscarNombreMaterial(String nombre) throws Exception {
     Material arrRet[] = null, oDiag = null;
      ArrayList rst = null;
      ArrayList<Material> vObj = null;
      String sQuery = "";
      int i = 0, nTam = 0;
      sQuery = "SELECT * FROM buscaXNombreMaterial('"+nombre+"');";

      oAD = new AccesoDatos();
      if (oAD.conectar()) {
          rst = oAD.ejecutarConsulta(sQuery);
          oAD.desconectar();
      }
      if (rst != null) {
          vObj = new ArrayList<Material>();
          for (i = 0; i < rst.size(); i++) {
              oDiag = new Material();
              ArrayList vRowTemp = (ArrayList) rst.get(i);                

              oDiag.setNombre((String) vRowTemp.get(0));
              vObj.add(oDiag);
          }
          nTam = vObj.size();
          arrRet = new Material[nTam];

          for (i = 0; i < nTam; i++) {
              arrRet[i] = vObj.get(i);
          }
      }
      System.out.println(sQuery);
      return arrRet;
  }

    public Material[] buscaproximoscaducados() throws Exception {
        Material arrRet[] = null, oMaterial = null;
        ArrayList rst = null;
        ArrayList<Material> vObj = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT *FROM buscaproximoscaducados();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();

        }
        if (rst != null) {
            vObj = new ArrayList<Material>();
            for (i = 0; i < rst.size(); i++) {
                oMaterial = new Material();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMaterial.setClaveMaterial(((String) vRowTemp.get(0)));
                oMaterial.setNombre(((String) vRowTemp.get(1)));
                oMaterial.getConcentrado().setValor(((String) vRowTemp.get(2)));
                vObj.add(oMaterial);
            }
            int nTam = vObj.size();
            arrRet = new Material[nTam];
            for (i = 0; i < nTam; i++) {
                arrRet[i] = vObj.get(i);
            }
        }
        return arrRet;
    }

    public int insertar(String sUsuario) throws Exception {
        
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {           
            throw new Exception("Material.insertar:error de programacion ,faltan datos");
        } else {
            
      //sQuery = "SELECT * FROM insertaMaterial('"+ sUsuario +"'::character varying,'" + getClaveMaterial() + "'::character varying,'" + getNombre() +"'::character varying,'"+  getPresentacion() + "'::character varying,'" + getConcentrado() +"'::character varying);";
         sQuery = "SELECT * FROM insertaMaterial('"+sUsuario +"','" + getClaveMaterial() + "','" + getNombre() + "','" + getPresentacion() + "','" + getConcentrado()+ " ');";
 

            System.out.println(sQuery);
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

    public int modificar (String sUsuario)throws Exception{
        ArrayList rst=null;
        int nRet=0;
        String sQuery="";
        if(this==null){
            throw new Exception("Material.modificar:error de programacion,faltan datos");
        }else{
            //sQuery="SELECT * FROM modificaMaterial('"+sUsuario+"','"+getClaveMaterial()+"','"+getNombre()+"','"+getConcentrado()+"','"+getPresentacion()+"'," + getCodBarras() + );";
            System.out.println(sQuery);
            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null && rst.size()==1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    nRet=((Double)vRowTemp.get(0)).intValue();
                }
            }        
        }
        return nRet;
    }

    public int eliminar(String sUsuario)throws Exception{
        ArrayList rst=null;
        int nRet=0;
        String sQuery="";
        if(this==null){
            throw new Exception("Materiales.eliminar:error de programacion,faltan datos");
        }else{
           //  sQuery="SELECT * FROM eliminaMaterial();";
             sQuery = "SELECT * FROM eliminaMaterial('"+sUsuario+"','"+ getClaveMaterial()+"');";
               System.out.println(sQuery);


            oAD=new AccesoDatos();
            if(oAD.conectar()){
                rst=oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if(rst!=null&& rst.size()==1){
                    ArrayList vRowTemp=(ArrayList)rst.get(0);
                    nRet=((Double)rst.get(0)).intValue();


                }
            }
        }
        return nRet;
    }

    //////////////////////////////////////////////////////////////////////
    //Órdenes de servicio        
    public Material[] buscarMaterialAlm(String Mate) throws Exception {
    Material arrRet1[] = null, oMat = null;
    ArrayList rst = null;
    ArrayList<Material> vObj = null;
    String sQuery = "";
    int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarmaterialesalmacen('" + Mate + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Material>();
            for (i = 0; i < rst.size(); i++) {
                oMat = new Material();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMat.setNombre((String) vRowTemp.get(0));
                vObj.add(oMat);
            }
            nTam = vObj.size();
            arrRet1 = new Material[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }
    
    public List<Material> getListaMaterialAlm(String txt) {
        List<Material> lListaProce = null;

        try {
            lListaProce = new ArrayList<Material>(Arrays.asList((new Material()).buscarMaterialAlm(txt)));
        } catch (Exception ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaProce;
    }
    
    public List<String> completarMatAlm(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<Material> lis = getListaMaterialAlm(sTxt);
        for (Material li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre());
            }
        }
        return arrRet;
    }
    
    public Material[] buscarMaterialOst(String Mate) throws Exception {
    Material arrRet1[] = null, oMat = null;
    ArrayList rst = null;
    ArrayList<Material> vObj = null;
    String sQuery = "";
    int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarmaterialosteosintesis('" + Mate + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Material>();
            for (i = 0; i < rst.size(); i++) {
                oMat = new Material();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMat.setNombre((String) vRowTemp.get(0));
                vObj.add(oMat);
            }
            nTam = vObj.size();
            arrRet1 = new Material[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }
    
    public List<Material> getListaMaterialOst(String txt) {
        List<Material> lListaProce = null;

        try {
            lListaProce = new ArrayList<Material>(Arrays.asList((new Material()).buscarMaterialOst(txt)));
        } catch (Exception ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaProce;
    }
    
    public List<String> completarMatOst(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<Material> lis = getListaMaterialOst(sTxt);
        for (Material li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre());
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
        for (int i = 0; i < original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            output = output.replace(original.charAt(i), ascii.charAt(i));
        }//for i
        return output;
    }
    
    public boolean buscarPorNombre() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        sQuery = "SELECT * FROM buscamaterialalmacenclave('" + this.getNombre() + "');";
        System.out.println(sQuery);
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null && rst.size() >= 1) {
            ArrayList vRowTemp = (ArrayList) rst.get(0);
            this.setClaveMaterial(((String) vRowTemp.get(0)));
            this.getPresentacion().setValor(((String) vRowTemp.get(1)));
            this.getPresentacion().setClaveParametro(((String) vRowTemp.get(2)));
            bRet = true;
        }
        return bRet;
    }
    
    public Material[] buscarMaterialCeye(String Mate) throws Exception {
        Material arrRet1[] = null, oMat = null;
        ArrayList rst = null;
        ArrayList<Material> vObj = null;
        String sQuery = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscarmaterialesceye('" + Mate + "');";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            vObj = new ArrayList<Material>();
            for (i = 0; i < rst.size(); i++) {
                oMat = new Material();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oMat.setNombre((String) vRowTemp.get(0));
                vObj.add(oMat);
            }
            nTam = vObj.size();
            arrRet1 = new Material[nTam];

            for (i = 0; i < nTam; i++) {
                arrRet1[i] = vObj.get(i);
            }
        }
        return arrRet1;
    }
    
    public List<Material> getListaMaterialCeye(String txt) {
    List<Material> lListaProce = null;

        try {
            lListaProce = new ArrayList<Material>(Arrays.asList((
                    new Material()).buscarMaterialCeye(txt)));
        } catch (Exception ex) {
            Logger.getLogger(Material.class.getName()).log(Level.SEVERE, null, ex);
        }
        return lListaProce;
    }
    
    public List<String> completarMatCeye(String sTxt) throws Exception {
    ArrayList<String> arrRet = new ArrayList<String>();
        List<Material> lis = getListaMaterialCeye(sTxt);
        for (Material li : lis) {
            if (sp_ascii(li.getNombre()).contains(sTxt)) {
                arrRet.add(li.getNombre());
            }
        }
        return arrRet;
    }
    
    //////////////////////////////////////////////////////////////////////////
    
    public String getNombre() {
        return sNombre;
    }

    public void setNombre(String valor) {
        this.sNombre = valor;
    }

    public String getClaveMaterial() {
   
        return sClaveMaterial;
        
    }

    public void setClaveMaterial(String valor) {
        this.sClaveMaterial = valor;
    }

    public Parametrizacion getPresentacion() {
        return oPresentacion;
    }

    public void setPresentacion(Parametrizacion valor) {
        this.oPresentacion = valor;
    }

    public Parametrizacion getConcentrado() {
        return oConcentrado;
    }

    public void setConcentrado(Parametrizacion valor) {
        this.oConcentrado = valor;
    }
 
    
}
    
    
    
    
    
    
    
    
    

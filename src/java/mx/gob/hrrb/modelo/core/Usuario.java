package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class Usuario implements Serializable {

    private AccesoDatos oAD;
    private ArrayList<Perfil> arrPerfil;
    private PersonalHospitalario oPersonal;
    private String sIdUsuario;
    private String sIdUsuario2;
    private String sPassword;
    private String sPassword2;
    private Puesto oPuesto;
    private Turno oTurno;
    private String desPerfil;
    private String cvePerfil;
    private String cvePerfil2;
    private String area;
    
    //private Perfil oPerfil;

    public Usuario() {
        oPersonal = new PersonalHospitalario();
        oPuesto = new Puesto();
        oTurno = new Turno();
        sIdUsuario = "";
        sIdUsuario2 = "";
        sPassword = "";
        sPassword2 = "";
        desPerfil = "";
        cvePerfil = "";
        cvePerfil2 = "";
        area = "";
    }

    public boolean buscarPorPerfil() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if ( this.sIdUsuario==null || this.sIdUsuario.equals("") || this.cvePerfil==null || this.cvePerfil.equals("") ) {
            throw new Exception("Usuario.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaUsuarioConPerfil('" + getIdUsuario() + "', '" + getCvePerfil() + "');";
            System.out.println("buca x perfil="+sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                setIdUsuario((String) vRowTemp.get(0));
                sIdUsuario2 = getIdUsuario();
                this.oPersonal.setNombres((String) vRowTemp.get(1));
                this.oPersonal.setApPaterno((String) vRowTemp.get(2));
                this.oPersonal.setApMaterno((String) vRowTemp.get(3));
                this.oPersonal.setNoTarjeta(((Double) vRowTemp.get(4)).intValue());
                this.oPersonal.setFechaNac((Date) vRowTemp.get(5));
                //this.oPuesto.setDescripcion((String) vRowTemp.get(6));
                this.setArea((String) vRowTemp.get(7));
                this.setDesPerfil((String) vRowTemp.get(8));
                this.setCvePerfil((String) vRowTemp.get(9));
                this.setCvePerfil2((String) vRowTemp.get(9));
                this.oPersonal.setCurp((String) vRowTemp.get(10));
                this.oPersonal.setActividad((String) vRowTemp.get(11));
                this.oTurno.setDescripcion((String) vRowTemp.get(12));
                bRet = true;
            }
        }
        return bRet;
    }

    //*********************metodo de buscar por curp puede servir para la busqueda del paciente**********************************************

    public boolean buscarPorCURP() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";

        if (this == null) {   //completar llave
            throw new Exception("Usuario.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscarPorCURP('" + getPersonal().getCurp() + "', " + getPersonal().getNoTarjeta() + ");";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() > 0) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);

                this.oPersonal.setNombres((String) vRowTemp.get(0));
                this.oPersonal.setApPaterno((String) vRowTemp.get(1));
                this.oPersonal.setApMaterno((String) vRowTemp.get(2));
                this.oPersonal.setNoTarjeta(((Double)vRowTemp.get(3)).intValue());
                this.oPersonal.setFechaNac((Date) vRowTemp.get(4));
                this.oPersonal.setStatus(((String) vRowTemp.get(5)).trim());
                this.oPersonal.setCurp((String)vRowTemp.get(6));
                
                PersonalAreaServ persArea = new PersonalAreaServ();

                AreaServicioHRRB a= new AreaServicioHRRB();
                a.setDescripcion((String)vRowTemp.get(7));
                persArea.setAreaServ(a);

                Puesto p= new Puesto();
                p.setDescripcion((String)vRowTemp.get(8));
                persArea.setPuesto(p);

                Turno t= new Turno();
                t.setDescripcion((String)vRowTemp.get(9));
                persArea.setTurno(t);
                
                this.oPersonal.setPersonalAreaServ(persArea);
                
                bRet = true;
            } else {
                oPersonal.setNombres("");
                oPersonal.setApPaterno("");
                oPersonal.setApMaterno("");
                oPersonal.setNoTarjeta(0);
                oPersonal.setFechaNac(null);
                oPuesto.setDescripcion("");
                setArea("");
                setDesPerfil("");
                setCvePerfil("");
                oPersonal.setCurp("");
                oPersonal.setActividad("");
                oPersonal.setPersonalAreaServ(new PersonalAreaServ());
                oTurno.setDescripcion("");
                
            }
        }
        return bRet;
    }

    //****************************MÃ‰TODO DE CAPASITS**********************
    public boolean buscarNombreUsuario() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Usuario.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaNombreUsuario('" + getIdUsuario() + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                setIdUsuario(((String) vRowTemp.get(0)));
                setPassword(((String) vRowTemp.get(1)));
                bRet = true;
            }
        }
        return bRet;
    }

    //**Busca la clave del perfil de un Usuario
    public boolean buscarUsuarioPerfilCapa() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Usuario.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaUsuarioPerfilCapa('" + getIdUsuario() + "');";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                setIdUsuario(((String) vRowTemp.get(0)));
                setCvePerfil(((String) vRowTemp.get(1)));
                bRet = true;
                System.out.println(bRet);
            }
        }
        return bRet;
    }

    //*******************************************************************
    public Usuario[] buscarTodos() throws Exception {
        Usuario arrRet[] = null, oUsuario = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT * FROM buscaTodosUsuario();";
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new Usuario[rst.size()];
            for (i = 0; i < rst.size(); i++) {
            }
        }
        return arrRet;
    }

    public int insertar(String usu) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQueryInsUsu = "", sQueryModPers="";
        ArrayList inserciones = new ArrayList();
        if (this.sPassword==null || this.sPassword.equals("")
                ||usu==null || usu.equals("") ||getPersonal().getStatus()== null 
                || this.getPersonal().getNoTarjeta()<0) {
            throw new Exception("Usuario.insertar: error de programación, faltan datos");
        } else {
            sQueryInsUsu = "SELECT * FROM insertaUsuario('" + usu
                    + "', '" + getIdUsuario()
                    + "', '" + getPassword()
                    + "', " + getPersonal().getNoTarjeta()
                    + ", ARRAY[";
                    for(Perfil p:arrPerfil){
                        sQueryInsUsu+="'"+p.getClave()+"', ";
                    }
                    sQueryInsUsu = sQueryInsUsu.substring(0, sQueryInsUsu.length()-2);
                    sQueryInsUsu+="]::character varying[] );";
            System.out.println(sQueryInsUsu);
            
            sQueryModPers = "SELECT * FROM modificaStatusPersonal('"+usu+"'::character varying,"
                    + this.getPersonal().getNoTarjeta() + ",'"
                    +this.getPersonal().getStatus()+"'::character(5));";
            
            inserciones.add(sQueryInsUsu);
            inserciones.add(sQueryModPers);
            
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                
                nRet = oAD.ejecutarConsultaComando(inserciones);
                oAD.desconectar();
                
            }
        }
        return nRet;
    }

    ////////Metodo de CAPASITS 

    public int insertarCapa(String usu) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Usuario.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertaUsuarioCapa('" + usu + "', '" + getIdUsuario() + "', '" + getPassword() + "');";
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

    ////////Metodo de CAPASITS 

    public int insertarUsuPerf(String usu) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("UsuarioPerfil.insertar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM insertausuario_perfil('" + usu + "', '" + getIdUsuario() + "', '" + getDesPerfil() + "');";
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

    /**
     * Busca a un usuario por su cve y contraseña, regresa verdadero si lo
     * encuentra
     */
    public boolean buscarCvePwd() throws Exception {
        boolean bRet = false;
        Menu oMenu = null;
        Funcion oFun = null;
        Perfil oPer = null;
        ArrayList rst = null, lin = null, rst2 = null, rst3 = null, rst4 = null;
        String fath, description, call;
        fath = description = call = "*";
        int i = 0;
        int padre = 0;
        String sFuncion = "";
        int clavemenu = 0;
        String nomMenuPrin = "";
        int elimina = -1;
        int nClave = 0;
        if (buscarUsuario() == true) {
            //Traer de bd
            //PRIMER MENÚ DE LA BARRA DE MENÚS   
            rst2 = new ArrayList();
            if (this.oAD == null) {
                oAD = new AccesoDatos();
                if (this.oAD.conectar()) {
                    rst = this.oAD.ejecutarConsulta("select * from buscaUsuarioCvePwd('" + this.sIdUsuario + "', '" + this.sPassword + "');");
                    this.oAD.desconectar();
                }
                oAD = null;
            }
            if (rst != null && rst.size() > 0) {
                for (Object rst1 : rst) {
                    lin = new ArrayList();
                    ArrayList vRowTemp = (ArrayList) rst1;
                    lin.add(new Double((Double) vRowTemp.get(0))); //Orden
                    lin.add((String) vRowTemp.get(1)); //Clave de Perfil
                    if (nClave == 0) {
                        lin.add((String) vRowTemp.get(2)); //Descripcion del perfil
                    } else {
                        lin.add(call); //Descripcion del perfil
                    }
                    lin.add((String) vRowTemp.get(3)); //Descripcion de la tabla Funcion **
                    lin.add((String) vRowTemp.get(4)); //Llamada de la tabla funcion **
                    lin.add(new Double((Double) vRowTemp.get(5))); //Clave del Menú
                    lin.add((String) vRowTemp.get(6)); //Nombre del Menú
                    if (((Double) vRowTemp.get(7)).intValue() != 0) {
                        lin.add(new Double((Double) vRowTemp.get(7))); //padre
                    } else {
                        lin.add((Double) null);
                    }
                    lin.add((String) vRowTemp.get(9)); //icono del menu

                    if (description.compareTo((String) vRowTemp.get(3)) != 0) {
                        rst2.add(lin);
                    }
                    if (((String) vRowTemp.get(3)).compareTo("") == 0
                            && ((String) vRowTemp.get(6)).compareTo("Consulta Externa 1") == 0) {
                        rst2.add(lin);
                    }

                    description = (String) vRowTemp.get(3);
                    if (nClave == 0) {
                        call = (String) vRowTemp.get(2);
                    }

                    nClave++;
                }
            }

            rst = rst2;
            if (rst != null) {
                this.arrPerfil = new ArrayList<Perfil>();
                for (i = 0; i < rst.size(); i++) {
                    lin = (ArrayList) rst.get(i); //AQUI SE TRAE DE LA BD

                    if (oPer != null && !oPer.getDescripcion().equals((String) lin.get(2))) {
                        this.arrPerfil.add(oPer);
                        oPer = new Perfil();
                        oPer.setClave((String) lin.get(1)); //clave del perfil 
                        oPer.setDescripcion((String) lin.get(2)); //descripcion del perfil 
                        oPer.setPermisos(new ArrayList<Funcion>());
                    } else {
                        if (oPer == null) {
                            oPer = new Perfil();
                            oPer.setClave((String) lin.get(1)); //clave del perfil = SIST
                            oPer.setDescripcion((String) lin.get(2)); //descripcion del perfil = SISTEMAS
                            oPer.setPermisos(new ArrayList<Funcion>());
                        }
                        oFun = new Funcion();
                        oFun.setDescripcion((String) lin.get(3)); //Descripcion (funcion) = NULL
                        if (oFun.getDescripcion().compareTo("") == 0 && lin.get(7) != null) {
                            oFun.setDescripcion((String) lin.get(6));
                        }
                        oFun.setLlamada((String) lin.get(4)); //pagina a la que va (funcion) = NULL
                        oMenu = new Menu();
                        oMenu.setClaveMenu(((Double) lin.get(5)).intValue()); //clave identificadora del menu = 1
                        oMenu.setDescripcion((String) lin.get(6)); //nombre del menú u opcion del menú = PRIMER MENU
                        Icono oIconTemp= new Icono();
                        
                        oIconTemp.setNombre((String) lin.get(8));
                        oMenu.setIcono(oIconTemp);
                        oFun.setMenu(oMenu);
                        
                        oIconTemp= new Icono();
                        oIconTemp.setNombre((String) lin.get(8));
                        oFun.setIcono(oIconTemp);
                        
                        if (lin.get(7) != null) {
                            oMenu = new Menu();
                            oMenu.setClaveMenu(((Double) lin.get(7)).intValue());
                            oFun.getMenu().setPadre(oMenu);

                        }
                        oPer.getPermisos().add(oFun);
                    }
                }
                //Falta agregar el último perfil
                if (oPer != null) {
                    this.arrPerfil.add(oPer);
                }
                bRet = true;
            }
        }
        return bRet;
    }
//******************************************************************************

    public boolean buscarUsuario() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";

        if (this.sIdUsuario.equals("") || this.sPassword.equals("")) {
            throw new Exception("Usuario.buscarCvePwd: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaUsuario('" + this.sIdUsuario + "',md5('" + this.sPassword + "'));";
            if (this.oAD == null) {
                oAD = new AccesoDatos();
                if (this.oAD.conectar()) {
                    rst = this.oAD.ejecutarConsulta(sQuery);
                    this.oAD.desconectar();
                }
                oAD = null;
            } else {
                rst = this.oAD.ejecutarConsulta(sQuery);
            }
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                bRet = true;
            }
        }
        return bRet;
    }
//******************************************************************************

    public ArrayList<Usuario> buscarUsuarios() throws Exception {
        Usuario oUsuario;
        ArrayList rst = null;
        ArrayList<Usuario> arrRet = null;
        String sQuery = "";
        String edad = "";
        int i = 0, nTam = 0;
        sQuery = "SELECT * FROM buscaTodosUsuario();";

        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<Usuario>();
            for (i = 0; i < rst.size(); i++) {
                oUsuario = new Usuario();
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oUsuario.setIdUsuario((String) vRowTemp.get(0));
                oUsuario.getPersonal().setNombres((String) vRowTemp.get(1));
                oUsuario.getPersonal().setApPaterno((String) vRowTemp.get(2));
                oUsuario.getPersonal().setApMaterno((String) vRowTemp.get(3));
                oUsuario.getPersonal().setNoTarjeta(((Double) vRowTemp.get(4)).intValue());
                oUsuario.getPersonal().setFechaNac((Date) vRowTemp.get(5));
                oUsuario.setCvePerfil((String) vRowTemp.get(6));
                //oUsuario.getPuesto().setDescripcion((String) vRowTemp.get(6));
                //oUsuario.setArea((String) vRowTemp.get(7));
                //oUsuario.setDesPerfil((String) vRowTemp.get(8));
                arrRet.add(oUsuario);
            }
        }
        return arrRet;
    }
//******************************************************************************************    

    public List<Perfil> buscarPerfilesDisp() throws Exception {
        List<Perfil> arrRet = null;
        Perfil oPerfil = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        if (this.cvePerfil == null) {
            Perfil p= new Perfil();
            return p.buscarTodosLosPerfiles();
        } else {
            sQuery = "SELECT * FROM buscaTodosPerfilDisp('" + this.sIdUsuario + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                arrRet = new ArrayList<>();
                for (i = 0; i < rst.size(); i++) {
                    oPerfil = new Perfil();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oPerfil.setClave((String)vRowTemp.get(0));
                    oPerfil.setDescripcion((String) vRowTemp.get(1));
                    arrRet.add(oPerfil);
                }
            }
            return arrRet;
        }
    }

    public List<Perfil> buscarPerfilesUsados() throws Exception {
        List <Perfil> arrRet = null;
        Perfil oPerfil = null;
        ArrayList rst = null;
        ArrayList<Perfil> arrRes = new ArrayList<Perfil>();
        String sQuery = "";
        int i = 0;
        if (this.sIdUsuario == null) {
            throw new Exception("Perfil.buscarTodosUsadosPor: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaTodosPerfilUsados('" + this.sIdUsuario + "');";
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null) {
                arrRet = new ArrayList<Perfil>();
                for (i = 0; i < rst.size(); i++) {
                    oPerfil = new Perfil();
                    ArrayList vRowTemp = (ArrayList) rst.get(i);
                    oPerfil.setClave((String)vRowTemp.get(0));
                    oPerfil.setDescripcion((String) vRowTemp.get(1));
                    arrRes.add(oPerfil);
                    arrRet.add(oPerfil);
                }
                this.setPerfil(arrRes);
            }
            return arrRet;
        }
    }
    
    
    
    
//******************************************************************************
    
    public int modificar(String usu) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQueryMod = "", sQueryModPers="";;
        ArrayList inserciones = new ArrayList();
        if (this.sIdUsuario==null || this.sIdUsuario.equals("") || this.sPassword==null
                || this.getPersonal().getStatus()==null ) {
            throw new Exception("Usuario.modificar: error de programación, faltan datos");
        } else {
            sQueryMod = "SELECT * FROM modificaUsuario('"+usu +
                    "', '"+ this.sIdUsuario +
                    "', '" + getPassword() +
                    "', ARRAY[";
                    for(Perfil p:arrPerfil){
                        sQueryMod+="'"+p.getClave()+"', ";
                    }
                    sQueryMod = sQueryMod.substring(0, sQueryMod.length()-2);
                    sQueryMod+="]::character varying[] );";
                    
            sQueryModPers = "SELECT * FROM modificaStatusPersonal('"+usu+"'::character varying,"
                    + this.getPersonal().getNoTarjeta() + ",'"
                    +this.getPersonal().getStatus()+"'::character(5));";
              
            inserciones.add(sQueryMod);
            inserciones.add(sQueryModPers);
            
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                nRet = oAD.ejecutarConsultaComando(inserciones);
                oAD.desconectar();
            }
        }
        return nRet;
    }
//******************************************************************************

    public int modificarUsuarioCapa(String usu) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Usuario.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificaUsuarioCapa('" + usu + "', '" + getIdUsuario() + "','" + getIdUsuario2() + "', '" + getPassword() + "');";
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
    
    public boolean modificarUsuarioRoot() throws Exception {
        
        ArrayList rst = null;
        boolean nRet = false;
        String sQuery = "";
        if (this.sPassword==null || this.sPassword.equals("") ||
                this.getPassword2()==null || this.getPassword2().equals("") ) { 
            throw new Exception("Usuario.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM modificarUsuarioRoot('"
                    +this.sPassword+"'::character varying,'"
                    +this.sPassword2+"'::character varying);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
                if (rst != null && rst.size() == 1) {
                    ArrayList vRowTemp = (ArrayList) rst.get(0);
                    int res=0;
                    res=((Double)vRowTemp.get(0)).intValue();
                    if(res>0)
                        nRet = true;
                }
            }
        }
        return nRet;
    }
    
//******************************************************************************

    public int elimUsuPerHospCapa(String usu, int tarjeta) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (this == null) {   //completar llave
            throw new Exception("Usuario.modificar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaPersonalHospitalarioCapa('" + usu + "', " + tarjeta + ",'" + getIdUsuario() + "');";
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
//********************************************************************************

    public int eliminar(String usu) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";
        if (usu==null || usu.equals("") || getIdUsuario()==null || getIdUsuario().equals("")) {
            throw new Exception("Usuario.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM eliminaUsuario('" + usu + "'::character varying, '" 
                                    + getIdUsuario() + "'::character varying, " 
                                    + getPersonal().getNoTarjeta() + ");";
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

    //Elimina usuario de Capasits y en caso de que se encuentre en Persoanal Hospitalario tambien
    public int eliminarUsuCapa(String usu) throws Exception {
        ArrayList rst = null;
        int nRet = 0;
        String sQuery = "";

        if (this == null) {   //completar llave
            throw new Exception("Usuario.eliminar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM  eliminaUsuarioCapa('" + usu + "','" + getIdUsuario() + "');";
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
//******************************************************************************

    /**
     * Verifica si el usuario actual tiene permisos sobre la llamada indicada
     */
    public boolean tienePermiso(String sLlamada) throws Exception {
        boolean bRet = false;
        Funcion oFunOri = new Funcion(sLlamada);

        for (Perfil oPerf : this.arrPerfil) {
            try {
                if (oFunOri.buscarPermisoInterna(oPerf.getClave())) {
                    bRet = true;
                    break;
                }
            } catch (Exception e) {
                throw e;
            }
            for (Funcion oFun : oPerf.getPermisos()) {
                if (oFun.getLlamada().equalsIgnoreCase(sLlamada)) {
                    bRet = true;
                    break;
                }
            }
        }
        return bRet;
    }
//******************************************************************************

        public ArrayList<Perfil> getPerfil() {
        return arrPerfil;
    }

    public void setPerfil(ArrayList<Perfil> valor) {
        arrPerfil = valor;
    }

    public PersonalHospitalario getPersonal() {
        return oPersonal;
    }

    public void setPersonal(PersonalHospitalario valor) {
        oPersonal = valor;
    }

    public String getIdUsuario() {
        return sIdUsuario;
    }

    public void setIdUsuario(String valor) {
        sIdUsuario = valor;
    }

    public String getPassword() {
        return sPassword;
    }

    public void setPassword(String valor) {
        sPassword = valor;
    }

    public void setPuesto(Puesto oPuesto) {
        this.oPuesto = oPuesto;
    }

    public Puesto getPuesto() {
        return oPuesto;
    }

    public void setDesPerfil(String sDesPerfil) {
        desPerfil = sDesPerfil;
    }

    public String getDesPerfil() {
        return desPerfil;
    }

    public void setCvePerfil(String cvePerfil) {
        this.cvePerfil = cvePerfil;
    }

    public String getCvePerfil() {
        return cvePerfil;
    }

    public void setCvePerfil2(String cvePerfil2) {
        this.cvePerfil2 = cvePerfil2;
    }

    public String getCvePerfil2() {
        return cvePerfil2;
    }

    public Turno getTurno() {
        return oTurno;
    }

    public void setTurno(Turno oTurno) {
        this.oTurno = oTurno;
    }

    public String getArea() {
        return area;
    }

    public void setArea(String area) {
        this.area = area;
    }

    public String getIdUsuario2() {
        return sIdUsuario2;
    }

    public void setIdUsuario2(String sIdUsuario2) {
        this.sIdUsuario2 = sIdUsuario2;
    }

    /**
     * @return the sPassword2
     */
    public String getPassword2() {
        return sPassword2;
    }

    /**
     * @param sPassword2 the sPassword2 to set
     */
    public void setPassword2(String sPassword2) {
        this.sPassword2 = sPassword2;
    }
}

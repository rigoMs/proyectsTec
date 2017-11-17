package mx.gob.hrrb.modelo.core;

import mx.gob.hrrb.modelo.datos.AccesoDatos;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import mx.gob.hrrb.jbs.core.Firmado;
import mx.gob.hrrb.modelo.almacen.Material;
import mx.gob.hrrb.modelo.cxc.ServicioCobrable;

/**
 * Objetivo: 
 *
 * @author : 
 * @version: 1.0
*/
public class Causes implements Serializable {

	private AccesoDatos oAD;
    private int nNumero;
	private int nAnio;
	private String sClave;
	private String sDescripcion;
	private DiagnosticoCIE10 oDiagnosticoCIE10;
	private ProcedimientoCIE9 oProcedimientoCIE9;
        private String sTipo;
    private ArrayList<Medicamento> lMedicamentos;
    private ArrayList<Material> lMateriales;    
    private String sUsuarioFirm;
    private boolean bCompleto; //indica si el causes en edicion está listo para ser guardado rojo si está incompleto


    public void iniciar() {
        sClave = "";
        sDescripcion = "";
        oDiagnosticoCIE10 = new DiagnosticoCIE10();
        oProcedimientoCIE9 = new ProcedimientoCIE9();
        sTipo = "";
        lMedicamentos = new ArrayList<>();
        lMateriales = new ArrayList<>();
        bCompleto = false;
        
        this.getProcedimientoCIE9().setListaProcecve(new ArrayList<ProcedimientoCIE9>());
        this.getDiagnosticoCIE10().setListaDiagcve(new ArrayList<DiagnosticoCIE10>());
        
        Firmado oFirm = new Firmado();
        FacesContext faceContext = FacesContext.getCurrentInstance();
        HttpServletRequest httpServletRequest = (HttpServletRequest) faceContext.getExternalContext().getRequest();
        if (httpServletRequest.getSession().getAttribute("oFirm") != null) {
            oFirm = (Firmado) httpServletRequest.getSession().getAttribute("oFirm");
            sUsuarioFirm = oFirm.getUsu().getIdUsuario();
        }
    }

    public boolean buscar(String cie10) throws Exception {
	boolean bRet = false;
	ArrayList rst = null;
	String sQuery = "";
		 if( cie10.equals("")){   //completar llave
			throw new Exception("Causes.buscar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM buscaLlaveCauses('"+cie10+"');";
			oAD=new AccesoDatos(); 
			if (oAD.conectar()){ 
				rst = oAD.ejecutarConsulta(sQuery); 
				oAD.desconectar(); 
			}
			if (rst != null && rst.size() >= 1) {
				ArrayList vRowTemp = (ArrayList)rst.get(0);
                                setClave((String)vRowTemp.get(0));
				bRet = true;
			}
		} 
		return bRet; 
	} 
    
    /**
     * @return Devuelve true si existe la intervención y guarda su información o
     * false en caso contrario
     */
    public boolean buscarCause() throws Exception {
        boolean bRet = false;
        ArrayList rst = null;
        String sQuery = "";
        DiagnosticoCIE10 oDiag = new DiagnosticoCIE10();
        ArrayList<DiagnosticoCIE10> lDiag = new ArrayList<>();
        ProcedimientoCIE9 oProc = new ProcedimientoCIE9();
        ArrayList<ProcedimientoCIE9> lProc = new ArrayList<>();
        Material oMat = new Material();
        ArrayList<Material> lMaterialesRes = new ArrayList<>();
        Medicamento oMed = new Medicamento();
        ArrayList<Medicamento> lMedRes = new ArrayList<>();

        if (this.sClave == null || this.sClave.equals("") || this.nAnio < 1) {
            throw new Exception("Causes.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaCausesPorClave('" + this.sClave + "'::character(5)," + this.nAnio + "::smallint);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);

                setClave((String) vRowTemp.get(1));
                setAnio(((Double) vRowTemp.get(2)).intValue());
                setDescripcion((String) vRowTemp.get(3));
                setCompleto(((String)vRowTemp.get(8)).equals("1"));

                for (Object rst1 : rst) {
                    ArrayList vRowT = (ArrayList) rst1;
                    Causes oCausesTemp = new Causes();
                    oCausesTemp.setNumero(((Double) vRowT.get(0)).intValue());
                    oCausesTemp.setTipo((String) vRowT.get(6));
                    if (!((String) vRowT.get(4)).equals("")) {
                        DiagnosticoCIE10 oDiagL = new DiagnosticoCIE10();
                        ServicioCobrable serv = new ServicioCobrable();

                        oDiagL.setCauses(oCausesTemp);
                        oDiagL.setClave((String) vRowT.get(4));
                        serv.setClave("" + ((Double) vRowT.get(7)).intValue());
                        oDiagL.setServCob(serv);

                        oDiagL.buscar();
                        lDiag.add(oDiagL);
                    } else {
                        ProcedimientoCIE9 oProcL = new ProcedimientoCIE9();

                        oProcL.setClaveCie((String) vRowT.get(5));
                        oProcL.setCauses(oCausesTemp);
                        oProcL.setClave("" + ((Double) vRowT.get(7)).intValue());
                        oProcL.buscar();
                        lProc.add(oProcL);
                    }
                }

                oDiag.setListaDiagcve(lDiag);
                oProc.setListaProcecve(lProc);

                this.setDiagnosticoCIE10(oDiag);
                this.setProcedimientoCIE9(oProc);

                bRet = true;
            }

            sQuery = "SELECT * FROM buscaMaterialesPorCause('" + this.sClave + "'::character(5)," + this.nAnio + "::smallint);";
            //System.out.println(sQuery);
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                for (Object rst1 : rst) {
                    ArrayList vRowTemp = (ArrayList) rst1;
                    oMat = new Material();
                    oMat.setClaveMaterial((String) vRowTemp.get(0));
                    oMat.buscar();
                    lMaterialesRes.add(oMat);
                }
                setMateriales(lMaterialesRes);
            }

            sQuery = "SELECT * FROM buscaMedicamentosPorCause('" + this.sClave + "'::character(5)," + this.nAnio + "::smallint);";
            //System.out.println(sQuery);
            if (oAD.conectar()) {
                rst = oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if (rst != null && rst.size() >= 1) {
                for (Object rst1 : rst) {
                    ArrayList vRowTemp = (ArrayList) rst1;
                    oMed = new Medicamento();
                    oMed.setClaveMedicamento((String) vRowTemp.get(0));
                    oMed.setNombre((String) vRowTemp.get(3));
                    oMed.setPresentacion((String) vRowTemp.get(1));
                    lMedRes.add(oMed);
                }
                setMedicamentos(lMedRes);
            }
        }
        return bRet;
    }

    /**
     * Busca el catálogo completo de causes de un año específico
     *
     * @return
     * @throws Exception
     */
    public List<Causes> buscarTodos() throws Exception {
        List<Causes> arrRet = null;
        Causes oCauses = null;
	ArrayList rst = null;
        ArrayList vRowTemp;
        String sQuery = "", clave = "";
        int i = 0;
        if (this.nAnio == 0) {   //completar llave
            throw new Exception("Causes.buscar: error de programación, faltan datos");
        } else {
            sQuery = "SELECT * FROM buscaTodosCausesPorAnio(" + this.nAnio + "::smallint);";
            System.out.println(sQuery);
            oAD = new AccesoDatos();
            if (oAD.conectar()) {
			rst = oAD.ejecutarConsulta(sQuery); 
			oAD.desconectar(); 
		}
		if (rst != null) {
                arrRet = new ArrayList<>();

			for (i = 0; i < rst.size(); i++) {
                    vRowTemp = (ArrayList) rst.get(i);
                    oCauses = new Causes();
                    oCauses.setClave((String) vRowTemp.get(0));
                    oCauses.setDescripcion((String) vRowTemp.get(1));
                    oCauses.setCompleto( ((String)vRowTemp.get(2)).equals("1") ) ;
                    arrRet.add(oCauses);
			} 
		} 
        }
		return arrRet; 
	} 

    public int insertar() throws Exception {
        ArrayList rst;
	int nRet = 0;
        StringBuilder sQuery = new StringBuilder();

        sQuery.append("SELECT * FROM insertaCauses(")
                .append(armarConsulta())
                .append(");");

        //System.out.println(sQuery);

        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery.toString());
            oAD.desconectar();
            if (rst != null && rst.size() == 1) {
                ArrayList vRowTemp = (ArrayList) rst.get(0);
                nRet = ((Double) vRowTemp.get(0)).intValue();
            }
        }

        return nRet;
    }

    public int modificar() throws Exception {
        ArrayList rst;
        int nRet = 0;
	String sQuery = "";

        sQuery = "SELECT * FROM modificaCauses(" + armarConsulta() + ")";
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

		return nRet; 
	} 

    private String armarConsulta() throws Exception {

        StringBuilder sQuery = new StringBuilder(); //se usó en vez de String porque es mas eficiente con tantas concatenaciones
        StringBuilder sCie10 = new StringBuilder("Array[");
        StringBuilder sCie9 = new StringBuilder("Array[");
        StringBuilder sTipos = new StringBuilder("Array[");
        StringBuilder sMedic = new StringBuilder("Array[");
        StringBuilder sMateriales = new StringBuilder("Array[");
        StringBuilder sServCob = new StringBuilder("Array[");
        //              ^ aqui se guardan y posteriormente envian los servicios de cada array en orden que fueron declarados

        if (this.sClave == null || this.sClave.equals("") || this.nAnio == 0
                || this.sDescripcion == null || this.sDescripcion.equals("")
                || this.oDiagnosticoCIE10 == null || this.oDiagnosticoCIE10.getListaDiagcve() == null
                || this.oProcedimientoCIE9 == null || this.oProcedimientoCIE9.getListaProcecve() == null
                || this.lMedicamentos == null || this.lMateriales == null) {
            throw new Exception("Causes.insertar/modificar: error de programación, faltan datos");
        } else {

            if (!this.oDiagnosticoCIE10.getListaDiagcve().isEmpty()) {
                for (DiagnosticoCIE10 diag : this.oDiagnosticoCIE10.getListaDiagcve()) {
                    if (diag.getClave() == null || diag.getClave().equals("")) {
                        throw new Exception("Causes.insertar/modificar: error de programación, falta clave de diagnostico");
                    }
                    sCie10.append("'").append(diag.getClave()).append("', ");
                    sTipos.append("'").append(diag.getCauses().getTipo()).append("', "); //se llenan primero los cie 10 y despues con los cie9
                    if (!diag.getServCob().getClave().equals("")) {
                        sServCob.append("").append(diag.getServCob().getClave()).append(", ");
                    } else {
                        sServCob.append("").append("0").append(", ");
                    }
                }
                sCie10.delete(sCie10.length() - 2, sCie10.length()); //cierra array de cie10
            }

            if (!this.oProcedimientoCIE9.getListaProcecve().isEmpty()) {
                for (ProcedimientoCIE9 proc : this.oProcedimientoCIE9.getListaProcecve()) {
                    if (proc.getClaveCie() == null || proc.getClaveCie().equals("")) {
                        throw new Exception("Causes.insertar/modificar: error de programación, falta clave de procedimiento");
                    }
                    sCie9.append("'").append(proc.getClaveCie()).append("', ");
                    sTipos.append("'").append(proc.getCauses().getTipo()).append("', ");

                    if (!proc.getClave().equals("")) {
                        sServCob.append("").append(proc.getClave()).append(", ");
                    } else {
                        sServCob.append("").append("0").append(", ");
                    }

                }
                sCie9.delete(sCie9.length() - 2, sCie9.length());   //cierra array de cie9
            }

            if (!this.oProcedimientoCIE9.getListaProcecve().isEmpty() || !this.oDiagnosticoCIE10.getListaDiagcve().isEmpty()) {
                sTipos.delete(sTipos.length() - 2, sTipos.length());    //cierra array de tipos
            }
            if (!this.lMedicamentos.isEmpty()) {
                for (Medicamento m : lMedicamentos) {
                    sMedic.append("'").append(m.getClaveMedicamento()).append("', ");
                    //sServCob.append("'").append(m.getClave()).append("', ");
                }
                sMedic.delete(sMedic.length() - 2, sMedic.length());    //cierra array de medicamentos
            }

            if (!this.lMateriales.isEmpty()) {
                for (Material m : lMateriales) {
                    sMateriales.append("'").append(m.getClaveMaterial()).append("', ");
                    //sServCob.append("'").append(m.getClave()).append("', ");
                }
                sMateriales.delete(sMateriales.length() - 2, sMateriales.length()); //cierra array de materiales
            }

            if (!sServCob.toString().equals("Array[")) {
                sServCob.delete(sServCob.length() - 2, sServCob.length());  //cierra array de servicios cobrables
            }

            sQuery.append("'")
                    .append(this.sUsuarioFirm).append("'::character varying,'")
                    .append(this.sClave).append("'::character(5),")
                    .append(this.nAnio).append("::smallint,'")
                    .append(this.sDescripcion).append("'::character varying,")
                    .append(sCie10).append("]::character(6)[],")
                    .append(sCie9).append("]::character(6)[],")
                    .append(sTipos).append("]::character(5)[],")
                    .append(sMedic).append("]::character varying[],")
                    .append(sMateriales).append("]::character varying[],")
                    .append(sServCob).append("]::integer[],")
                    .append(this.bCompleto?"1":"0").append("::character(1)");
        }

        return sQuery.toString();
    }

    public int insertarTodosCausesAProduccion() throws Exception {
        ArrayList rst;
	int nRet = 0;
	String sQuery = "";

        if (this.sUsuarioFirm==null || this.sUsuarioFirm.equals("")) {
            throw new Exception("Causes.nsertarTodosCausesAProduccion: error de programación, faltan datos");
		}else{ 
            
            sQuery = "SELECT * FROM guardarCausesCompleto('" + this.sUsuarioFirm + "');";
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
    
	public int eliminar() throws Exception{
	ArrayList rst = null;
	int nRet = 0;
	String sQuery = "";
		 if( this==null){   //completar llave
			throw new Exception("Causes.eliminar: error de programación, faltan datos");
		}else{ 
			sQuery = "SELECT * FROM eliminaCauses();"; 
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

    public List<Causes> getAniosRegistrados() throws Exception {
        List<Causes> arrRet = null;
        Causes oCauses = null;
        ArrayList rst = null;
        String sQuery = "";
        int i = 0;
        sQuery = "SELECT nanio FROM edicioncauses GROUP BY nanio ORDER BY nanio DESC;";
        //ya no tenia ganas de hacer la funcion u_u
        oAD = new AccesoDatos();
        if (oAD.conectar()) {
            rst = oAD.ejecutarConsulta(sQuery);
            oAD.desconectar();
        }
        if (rst != null) {
            arrRet = new ArrayList<>();
            for (i = 0; i < rst.size(); i++) {
                ArrayList vRowTemp = (ArrayList) rst.get(i);
                oCauses = new Causes();
                oCauses.setAnio(((Double) vRowTemp.get(0)).intValue());
                arrRet.add(oCauses);
            }
        }
        return arrRet;
    }

	public int getAnio() {
	return nAnio;
	}

	public void setAnio(int valor) {
	nAnio=valor;
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

	public DiagnosticoCIE10 getDiagnosticoCIE10() {
	return oDiagnosticoCIE10;
	}

	public void setDiagnosticoCIE10(DiagnosticoCIE10 valor) {
	oDiagnosticoCIE10=valor;
	}

	public ProcedimientoCIE9 getProcedimientoCIE9() {
	return oProcedimientoCIE9;
	}

	public void setProcedimientoCIE9(ProcedimientoCIE9 valor) {
	oProcedimientoCIE9=valor;
	}

    /**
     * @return the sTipo
     */
    public String getTipo() {
        return sTipo;
    }

    /**
     * @param sTipo the sTipo to set
     */
    public void setTipo(String sTipo) {
        this.sTipo = sTipo;
    }

    /**
     * @return the nNumero
     */
    public int getNumero() {
        return nNumero;
} 

    /**
     * @param nNumero the nNumero to set
     */
    public void setNumero(int nNumero) {
        this.nNumero = nNumero;
    }

    /**
     * @return the lMedicamentos
     */
    public ArrayList<Medicamento> getMedicamentos() {
        return lMedicamentos;
    }

    /**
     * @param medicamentos the lMedicamentos to set
     */
    public void setMedicamentos(ArrayList<Medicamento> medicamentos) {
        this.lMedicamentos = medicamentos;
    }

    /**
     * @return the lMateriales
     */
    public ArrayList<Material> getMateriales() {
        return lMateriales;
    }

    /**
     * @param lMateriales the lMateriales to set
     */
    public void setMateriales(ArrayList<Material> lMateriales) {
        this.lMateriales = lMateriales;
    }

    /**
     * @return the bCompleto
     */
    public boolean isCompleto() {
        return bCompleto;
    }

    /**
     * @param bCompleto the bCompleto to set
     */
    public void setCompleto(boolean bCompleto) {
        this.bCompleto = bCompleto;
    }
    /**
     * 
     * @return rojo si bCompleto es false, black si es true
     */
    public String getColor(){
        return bCompleto?"black":"red";
    }

}

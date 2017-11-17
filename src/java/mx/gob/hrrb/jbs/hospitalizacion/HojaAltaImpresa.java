package mx.gob.hrrb.jbs.hospitalizacion;

import java.io.BufferedInputStream;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.ExternalContext;
import javax.faces.context.FacesContext;
import mx.gob.hrrb.modelo.core.Cita;
import mx.gob.hrrb.modelo.core.Hospitalizacion;

/**
 *
 * @author KarDan91
 */
@ManagedBean(name = "oAltaImp")
@SessionScoped
public class HojaAltaImpresa {
    private long nFolioPaciente;
    private long nClaveEpisodio;
    private long nNumIngresoHosp;
    private Hospitalizacion oHosp;
    private Cita oCita;
    private List<Cita> lCita;
    private boolean bActivar;
    private boolean bVerDesdeExpediente;
    /**
     * Creates a new instance of HojaAltaImpresa
     */
    public HojaAltaImpresa() {
        oHosp = new Hospitalizacion();
        oCita = new Cita();
        bVerDesdeExpediente=false;
    }
    public String recuperaDatos() throws Exception{
            oHosp=new Hospitalizacion();
            lCita = new ArrayList<Cita>();
            oHosp.setNumIngresoHos(getNumIngresoHosp());
            oHosp.getEpisodioMedico().setClaveEpisodio(getClaveEpisodio());
            oHosp.getPaciente().setFolioPaciente(getFolioPaciente());
            oCita.getPaciente().setFolioPaciente(getFolioPaciente());
            oCita.setClaveEpisodio(getClaveEpisodio());
            oCita.setNumIngresoHosp(getNumIngresoHosp());
            oHosp.buscarDatosEdoSalud();
            oHosp.buscarDatosPacienteCODE();    
            oHosp.buscarCodeEstancia();
            oHosp.buscarCamaServicio();
            oHosp.buscarHojaAlta();
            if(bVerDesdeExpediente!=false){
                //busca los datos del medico reponsable
                oHosp.buscarDatosMedicoHojaAlta();
            }else{
                //busca datos del medico firmado
                oHosp.buscarMedioHojaAlta();
            }
            oHosp.buscarSignosVitalesHojaAlta();
            oHosp.buscarCodeAfecciones();
            oHosp.buscarDatosEdoSalud();
            lCita=oCita.buscarCitasHojaAlta();
            System.out.println("Imprimiendo: "+oCita.buscarCitasHojaAlta().size());
            String nombreHTML=imprimeCODE();
            System.out.println(nombreHTML);
        return nombreHTML;
    }        

    public long diasEstancia() throws ParseException{
        SimpleDateFormat fecha= new SimpleDateFormat("yyyy-MM-dd");
        SimpleDateFormat fI=new SimpleDateFormat("yyyy-MM-dd");
        Date fechaIng=oHosp.getEpisodioMedico().getAltaHospitalaria();
        
        
        long milis1, milis2, diff=0;
        long milxdia = 24 * 60 * 60 * 1000;
        
        if(oHosp.getEpisodioMedico().getAltaHospitalaria()!=null){
            String fechaI=""+oHosp.getFechaIngresoHos();
            String fechaE=fI.format(fechaIng);
            Date fechaIngreso=null, fechaEgreso=null;
            System.out.println(fechaI);
            System.out.println(fechaE);
            fechaIngreso=fecha.parse(fechaI);
            fechaEgreso=fecha.parse(fechaE);       
            Calendar Finicio = Calendar.getInstance();
            Calendar Ffinal = Calendar.getInstance();
            Finicio.setTime(fechaIngreso);
            Ffinal.setTime(fechaEgreso);
            milis1 = Finicio.getTimeInMillis();
            milis2 = Ffinal.getTimeInMillis();
            diff = (milis2-milis1)/milxdia;
        }
         return diff;
        
    }    
    
public String imprimeCODE() throws FileNotFoundException, Exception {
        String nombreHTML = "";
        FileWriter fichero = null;
        PrintWriter pw = null;
        
        if (this==null) {
            throw new Exception("Funcion.HojaAltaImpresa: error de programación, faltan datos");
        } else {
            nombreHTML = "/hospitalizacion/HojaAltaImp.html";
            String HTML = "hospitalizacion/FormatoAlta.html";
            ExternalContext extCont = FacesContext.getCurrentInstance().getExternalContext();
            File folder = new File(extCont.getRealPath("//sesiones//"));
            System.out.println("Aqui se guarda "+folder);
            try {                    

                fichero = new FileWriter(folder+nombreHTML);
                 pw = new PrintWriter(fichero);
                    String str ="";
                    File myhtml = new File(folder, HTML);
                    FileInputStream fileinput = null;
                    BufferedInputStream mybuffer = null;
                    DataInputStream datainput = null;
                    fileinput = new FileInputStream(myhtml);
                    mybuffer = new BufferedInputStream(fileinput);
                    datainput = new DataInputStream(mybuffer);
                    SimpleDateFormat sdf = new SimpleDateFormat("dd-MM-yyyy HH:mm");
                    
                    int i = 0;

                    while (datainput.available() != 0) {
                        i++;
                        String linea = datainput.readLine();
                       //System.out.println(linea);
                    
                        if (linea.indexOf("varNombrePac")>0){
                            linea = linea.replace("varNombrePac", sp_ascii(oHosp.getPaciente().getNombres()));
                        }
                        if (linea.indexOf("varApPaterno")>0){
                            linea = linea.replace("varApPaterno", sp_ascii(oHosp.getPaciente().getApPaterno()));
                        }
                        if (linea.indexOf("varApMaterno")>0){
                            linea = linea.replace("varApMaterno", sp_ascii(oHosp.getPaciente().getApMaterno()));
                        }
                        if (linea.indexOf("varEdad")>0){
                            String edad=oHosp.getPaciente().getClaveEdadP();
                            if(edad.equals("AÑOS"))
                                edad="A&Ntilde;OS";
                            if(edad.equals("DÍAS"))
                                edad="D&Iacute;AS";
                            linea = linea.replace("varEdad", ""+oHosp.getPaciente().getEdad()+" "+edad);
                        }
                        if (linea.indexOf("varSexo")>0){
                            linea = linea.replace("varSexo", oHosp.getPaciente().getSexoP());
                        }                        
                        if (linea.indexOf("varNumExp")>0){
                            linea = linea.replace("varNumExp", ""+oHosp.getPaciente().getExpediente().getNumero());
                        }                          
                        if (linea.indexOf("varFolSegPop")>0){
                            linea = linea.replace("varFolSegPop", ""+oHosp.getSeguro().getNumero());
                        }      
                        if (linea.indexOf("varAreaSer")>0){
                            linea = linea.replace("varAreaSer", sp_ascii(oHosp.getEpisodioMedico().getArea().getDescripcion()));
                        }   
                        if (linea.indexOf("varCama")>0){
                            linea = linea.replace("varCama", oHosp.getEpisodioMedico().getCama().getNumero());
                        }    
                        if (linea.indexOf("varFechaHoraIngreso")>0){
                            String FI=sdf.format(oHosp.getFechaIngresoHos());
                            linea = linea.replace("varFechaHoraIngreso", ""+FI);
                        }    
                        if (linea.indexOf("varFechaHoraEgreso")>0){
                            String FE=sdf.format(oHosp.getEpisodioMedico().getAltaHospitalaria());
                            linea = linea.replace("varFechaHoraEgreso", ""+FE);
                        }         
                        if (linea.indexOf("varFechaHoraElaboracion")>0){
                            String FE=sdf.format(oHosp.getFechaElaboracion());
                            linea = linea.replace("varFechaHoraElaboracion", ""+FE);
                        }  
                        if (linea.indexOf("varDiasEstancia")>0){
                            String dias;
                            if(diasEstancia()<=1)
                                dias=diasEstancia()+" D&Iacute;A";
                            else
                                dias=diasEstancia()+" D&Iacute;AS";
                            linea = linea.replace("varDiasEstancia", dias);
                        } 
                        if (linea.indexOf("xRSINO")>0){
                            if(oHosp.getReingreso().equals("S"))
                                linea = linea.replace("xRSINO", "SI");
                            else
                                linea = linea.replace("xRSINO", "NO");
                        }                          
                        if (linea.indexOf("varDiagnosticosIngreso")>0){
                            linea = linea.replace("varDiagnosticosIngreso", oHosp.getEpisodioMedico().getDiagIngreso().getDescripcionDiag());
                        }                          
                        if (linea.indexOf("varDiagnosticosEgreso")>0){
                            String diagEgreso="";
                            if(oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag()!=null)
                                diagEgreso+="AFECCI&Oacute;N PRINCIPAL: "+oHosp.getEpisodioMedico().getAfePrincipal().getCIE10().getDescripcionDiag() +"<br/>";
                            if(oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag()!=null)
                                diagEgreso+="AFECCI&Oacute;N PRIMERA: "+oHosp.getEpisodioMedico().getAfePrimera().getCIE10().getDescripcionDiag()+ "<br/>";
                            if(oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag()!=null)
                                diagEgreso+="AFECCI&Oacute;N SEGUNDA: "+oHosp.getEpisodioMedico().getAfeSegunda().getCIE10().getDescripcionDiag() + "<br/>";
                            if(oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag()!=null)
                                diagEgreso+="AFECCI&Oacute;N TERCERA: "+oHosp.getEpisodioMedico().getAfeTercera().getCIE10().getDescripcionDiag() + "<br/>";
                            if(oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag()!=null)
                                diagEgreso+="AFECCI&Oacute;N CUARTA: "+oHosp.getEpisodioMedico().getAfeCuarta().getCIE10().getDescripcionDiag() + "<br/>";
                            if(oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag()!=null)
                                diagEgreso+="AFECCI&Oacute;N QUINTA: "+oHosp.getEpisodioMedico().getAfeQuinta().getCIE10().getDescripcionDiag() + "<br/>";
                            if(oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag()!=null)
                                diagEgreso+="AFECCI&Oacute;N SEXTA: "+oHosp.getEpisodioMedico().getAfeSexta().getCIE10().getDescripcionDiag() + "<br/>";
                                                                
                            linea = linea.replace("varDiagnosticosEgreso", diagEgreso);
                        }        
                        if (linea.indexOf("varMotivoEgreso")>0){
                            if(oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3801"))
                                linea = linea.replace("varMotivoEgreso", "CURACI&Oacute;N");
                            else if(oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3802"))
                                linea = linea.replace("varMotivoEgreso", "MEJOR&Iacute;A");
                            else if(oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3803"))
                                linea = linea.replace("varMotivoEgreso", "VOLUNTARIO");
                            else if(oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3804"))
                                linea = linea.replace("varMotivoEgreso", "PASE A OTRO HOSPITAL");
                            else if(oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3805"))
                                linea = linea.replace("varMotivoEgreso", "DEFUNCI&Oacute;N");
                            else if(oHosp.getEpisodioMedico().getMotivoEgresoP().equals("T3806"))
                                linea = linea.replace("varMotivoEgreso", "OTRO MOTIVO");
                            else
                                linea = linea.replace("varMotivoEgreso", "&nbsp;");
                        }        
                        if (linea.indexOf("varRazonTraslado")>0){
                            if(oHosp.getEpisodioMedico().getRazonAltaVolunTrasl()!=null && !oHosp.getEpisodioMedico().getRazonAltaVolunTrasl().equals(""))
                                linea = linea.replace("varRazonTraslado", sp_ascii(oHosp.getEpisodioMedico().getRazonAltaVolunTrasl()));
                            else
                                linea = linea.replace("varRazonTraslado", "&nbsp;");                            
                        }                                                              
                        if (linea.indexOf("varPesoE")>0){
                            linea = linea.replace("varPesoE", ""+oHosp.getPeso());
                        }         
                        if (linea.indexOf("varTallaE")>0){
                            linea = linea.replace("varTallaE", ""+oHosp.getTalla());
                        }        
                        if (linea.indexOf("varTemperaturaE")>0){
                            if(oHosp.getEpisodioMedico().getSignosVitales().getTemp()!=null && !oHosp.getEpisodioMedico().getSignosVitales().getTemp().equals(""))
                                linea = linea.replace("varTemperaturaE", sp_ascii(oHosp.getEpisodioMedico().getSignosVitales().getTemp())+" &deg;C");
                            else
                                linea = linea.replace("varTemperaturaE","&nbsp;");
                        }         
                        if (linea.indexOf("varFrecCardiacaE")>0){
                            if(oHosp.getEpisodioMedico().getSignosVitales().getFC()!=null)
                                linea = linea.replace("varFrecCardiacaE", sp_ascii(oHosp.getEpisodioMedico().getSignosVitales().getFC()));
                            else
                                linea = linea.replace("varFrecCardiacaE", "&nbsp;");
                        }           
                        if (linea.indexOf("varFrecRespiratoriaE")>0){
                            if(oHosp.getEpisodioMedico().getSignosVitales().getFR()!=null)
                                linea = linea.replace("varFrecRespiratoriaE", sp_ascii(oHosp.getEpisodioMedico().getSignosVitales().getFR()));
                            else
                                linea = linea.replace("varFrecRespiratoriaE", "&nbsp;");
                        }                         
                        if (linea.indexOf("varTensionArterialE")>0){
                            if(oHosp.getEpisodioMedico().getSignosVitales().getTA()!=null)
                                linea = linea.replace("varTensionArterialE", sp_ascii(oHosp.getEpisodioMedico().getSignosVitales().getTA()));
                            else
                                linea = linea.replace("varTensionArterialE", "&nbsp;");
                        }     
                        if (linea.indexOf("varResumenEvolucion")>0){
                            linea = linea.replace("varResumenEvolucion", sp_ascii(oHosp.getResumenEvolucion()));
                        }      
                        if (linea.indexOf("varManejoHospitalizacion")>0){
                            linea = linea.replace("varManejoHospitalizacion", sp_ascii(oHosp.getManejo()));
                        } 
                        if (linea.indexOf("varProcedimientosOperaciones")>0){
                            linea = linea.replace("varProcedimientosOperaciones", sp_ascii(oHosp.getProcedimientosOperaciones()));
                        }      
                        if (linea.indexOf("varProblemasClinicos")>0){
                            linea = linea.replace("varProblemasClinicos", sp_ascii(oHosp.getProblemasPend()));
                        }       
                        if (linea.indexOf("varPlanManejoEgreso")>0){
                            linea = linea.replace("varPlanManejoEgreso", sp_ascii(oHosp.getPlan()));
                        }      
                        if (linea.indexOf("varRecomendaciones")>0){
                            linea = linea.replace("varRecomendaciones", sp_ascii(oHosp.getRecomendaciones()));
                        }                                   
                        if (linea.indexOf("varPronostico")>0){
                            if(oHosp.getPronosticoP().equals("T4700"))
                                linea = linea.replace("varPronostico", "BUENO");
                            if(oHosp.getPronosticoP().equals("T4701"))
                                linea = linea.replace("varPronostico", "MALO");
                            if(oHosp.getPronosticoP().equals("T4702"))
                                linea = linea.replace("varPronostico", "RESERVADO");
                            if(oHosp.getPronosticoP().equals("") || oHosp.getPronosticoP()==null || oHosp.getPronosticoP().equals("     "))
                                linea = linea.replace("varPronostico", "&nbsp;");
                        }                        
                        if (linea.indexOf("varDestinoPaciente")>0){
                            if(oHosp.getEpisodioMedico().getDestinoSTR().equals("T3700"))
                                linea = linea.replace("varDestinoPaciente", "DOMICILIO");
                            else if(oHosp.getEpisodioMedico().getDestinoSTR().equals("T3701"))
                                linea = linea.replace("varDestinoPaciente", "CENTRO DE SALUD");
                            else if(oHosp.getEpisodioMedico().getDestinoSTR().equals("T3702"))
                                linea = linea.replace("varDestinoPaciente", "TERCER NIVEL");
                            else
                                linea = linea.replace("varDestinoPaciente", "&nbsp;");
                        }        
                        if (linea.indexOf("varConsultaExterna")>0){
                            linea = linea.replace("varConsultaExterna", oHosp.getConsultaExt());
                        }                         
                        
                        String FechasCitas="";
                        String TiempoAproxCita="";
                        String ServicioCita="";
                        if(lCita.size()>0){
                            for (Cita lCita1 : lCita) {
                                if (lCita1.getFechaCita() != null) {
                                    FechasCitas += lCita1.getFechaCita() + " " + lCita1.getHoraCita() + " <br/>";
                                }      
                            }

                            for (Cita lCita1 : lCita) {
                                if (lCita1.getTiempoAproxP() != null) {
                                    TiempoAproxCita += lCita1.getTiempoAproxP() + " <br/>";
                                }                          
                            }

                            for (Cita lCita1 : lCita) {
                                if (lCita1.getAreaServicio().getDescripcion() != null) {
                                    ServicioCita += lCita1.getAreaServicio().getDescripcion() + " <br/>";
                                }         
                            }
                        }
                        if (linea.indexOf("varFechaCita")>0){
                            linea = linea.replace("varFechaCita", FechasCitas);
                        }                
                        if (linea.indexOf("varServicioConsultaExterna")>0){
                            linea = linea.replace("varServicioConsultaExterna", sp_ascii(ServicioCita));
                        }                          
                        if (linea.indexOf("varTiempoAproximado")>0){
                            linea = linea.replace("varTiempoAproximado", sp_ascii(TiempoAproxCita));
                        }
                        if (linea.indexOf("varNombreMedico")>0){
                            linea = linea.replace("varNombreMedico", sp_ascii(oHosp.getEpisodioMedico().getMedicoTratante().getNombres()+" "+oHosp.getEpisodioMedico().getMedicoTratante().getApPaterno()+" "+oHosp.getEpisodioMedico().getMedicoTratante().getApMaterno()));
                        }                         
                        if (linea.indexOf("varCedulaProfesional")>0){
                            linea = linea.replace("varCedulaProfesional", oHosp.getEpisodioMedico().getMedicoTratante().getCedProf());
                        }
                        
                        str += linea + " ";
                        
                    }
                    
                    //System.out.println(str);
                    pw.println(str);
                    pw.println("");
                   fichero.close();

            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(bVerDesdeExpediente==true)
            return "hospitalizacion/HojaAltaImp.html";
        else
            return "HojaAltaImp.html";
    }        

    public String sp_ascii(String input) {
        // Cadena de caracteres original a sustituir.
        String original = "áàäéèëíìïóòöúùuñÁÀÄÉÈËÍÌÏÓÒÖÚÙÜÑçÇ";
        // Cadena de caracteres ASCII que reemplazarán los originales.
        String ascii = "aaaeeeiiiooouuunAAAEEEIIIOOOUUUNcC";
        //String html="";
        String output = input;
        for (int i=0; i<original.length(); i++) {
            // Reemplazamos los caracteres especiales.
            if(original.charAt(i)=='Á')
                output = output.replace(""+original.charAt(i), "&Aacute;");
            else if(original.charAt(i)=='É')
                output = output.replace(""+original.charAt(i), "&Eacute;");
            else if(original.charAt(i)=='Í')
                output = output.replace(""+original.charAt(i), "&Iacute;");
            else if(original.charAt(i)=='Ó')
                output = output.replace(""+original.charAt(i), "&Oacute;");
            else if(original.charAt(i)=='Ú')
                output = output.replace(""+original.charAt(i), "&Uacute;");
            else if(original.charAt(i)=='Ñ')
                output = output.replace(""+original.charAt(i), "&Ntilde;");
            else if(original.charAt(i)=='Ü')
                output = output.replace(""+original.charAt(i), "&Uuml;");
            else
                output = output.replace(original.charAt(i), ascii.charAt(i));
        }
        return output;
    }

    /**
     * @return the nFolioPaciente
     */
    public long getFolioPaciente() {
        return nFolioPaciente;
    }

    /**
     * @param nFolioPaciente the nFolioPaciente to set
     */
    public void setFolioPaciente(long nFolioPaciente) {
        this.nFolioPaciente = nFolioPaciente;
    }

    /**
     * @return the nClaveEpisodio
     */
    public long getClaveEpisodio() {
        return nClaveEpisodio;
    }

    /**
     * @param nClaveEpisodio the nClaveEpisodio to set
     */
    public void setClaveEpisodio(long nClaveEpisodio) {
        this.nClaveEpisodio = nClaveEpisodio;
    }

    /**
     * @return the nNumIngresoHosp
     */
    public long getNumIngresoHosp() {
        return nNumIngresoHosp;
    }

    /**
     * @param nNumIngresoHosp the nNumIngresoHosp to set
     */
    public void setNumIngresoHosp(long nNumIngresoHosp) {
        this.nNumIngresoHosp = nNumIngresoHosp;
    }

    /**
     * @return the oHosp
     */
    public Hospitalizacion getHosp() {
        return oHosp;
    }

    /**
     * @param oHosp the oHosp to set
     */
    public void setHosp(Hospitalizacion oHosp) {
        this.oHosp = oHosp;
    }

    /**
     * @return the bActivar
     */
    public boolean getActivar() {
        return bActivar;
    }

    /**
     * @param bActivar the bActivar to set
     */
    public void setActivar(boolean bActivar) throws Exception {
        if(bActivar ==true){
            recuperaDatos();
            bActivar=false;
        }        
        this.bActivar = bActivar;
    }

    public boolean getVerDesdeExpediente() {
        return bVerDesdeExpediente;
    }

    public void setVerDesdeExpediente(boolean bVerDesdeExpediente) {
        this.bVerDesdeExpediente = bVerDesdeExpediente;
    }
    
}

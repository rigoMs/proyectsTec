
package mx.gob.hrrb.modelo.enfermeria.reporte;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.core.PersonalHospitalario;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author J2GOV
 */
public class ActividadesTerapiaIntensiva implements Serializable {
    
    private AccesoDatos oAD;
    private Date dFechaInicio;
    private Date dFechaFin;
    private PersonalHospitalario oEnfermera;
    private EpisodioMedico oEpisodio;    
    
    public ActividadesTerapiaIntensiva(){
        dFechaInicio= new Date();
        dFechaFin = new Date();
        oEpisodio = new EpisodioMedico();
        oEnfermera = new PersonalHospitalario();
    }
    
    public ActividadesTerapiaIntensiva[] buscaActividadesTerapiaIntensiva() throws Exception{
        ActividadesTerapiaIntensiva arrRet[]=null, oA;
        ArrayList rst=null;
        String sQuery="",sEdad="";
        int i=0;
        if(this.getFechaInicio()==null || this.getFechaFin()==null){
            throw new Exception("ActividadesTerapiaIntensiva.buscaActividadesTerapiaIntensiva: error, faltan datos");
        }else{
            sQuery="SELECT * FROM buscaInformeMensualTerapiaIntesiva('"
                +this.getFechaInicioStr() +"'::date,'"
                + this.getFechaFinStr()+"'::date);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                arrRet= new ActividadesTerapiaIntensiva[rst.size()];
                for(i=0; i<rst.size();i++){
                    ArrayList vRowTemp= (ArrayList)rst.get(i);
                    oA= new ActividadesTerapiaIntensiva();
                    oA.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                    oA.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                    oA.getEpisodio().getPaciente().getExpediente().getServicioIngreso().setDescripcion((String)vRowTemp.get(2));
                    oA.getEpisodio().setFechaIngreso((Date)vRowTemp.get(3));
                    oA.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                    oA.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                    oA.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                    oA.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(7)).intValue());
                    sEdad= (String)vRowTemp.get(8);
                    if (sEdad.compareTo("")!=0){
                        if(sEdad.substring(0, 3).compareTo("000")!=0){
                            if (sEdad.charAt(0)=='0')
                              oA.getEpisodio().getPaciente().setEdadNumero(sEdad.substring(1, 3)+" AÑOS");
                            else
                              oA.getEpisodio().getPaciente().setEdadNumero(sEdad.substring(0, 3)+" AÑOS");
                        }else{
                            if (sEdad.substring(4, 6).compareTo("00")!=0)
                              oA.getEpisodio().getPaciente().setEdadNumero(sEdad.substring(4, 6)+" MESES");
                            else
                              oA.getEpisodio().getPaciente().setEdadNumero(sEdad.substring(7, 9)+" DÍAS");
                        }
                    }
                    if(((String)vRowTemp.get(9)).equals("M")){
                        oA.getEpisodio().getPaciente().setSexoP("MASCULINO");
                    } else if(((String)vRowTemp.get(9)).equals("F")) {
                        oA.getEpisodio().getPaciente().setSexoP("FEMENINO");
                    }else{
                        oA.getEpisodio().getPaciente().setSexoP("INDETERMINADO");
                    }
                    oA.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(10));
                    oA.getEpisodio().setDiasEstancia(((Double)vRowTemp.get(11)).intValue());
                    oA.getEpisodio().setMotivoEgresoP((String)vRowTemp.get(12));
                    oA.getEpisodio().setAltaHospitalaria((Date)vRowTemp.get(13));
                    arrRet[i]=oA;
                }
            }
        }
        return arrRet;
    }

    public Date getFechaInicio() {
        return dFechaInicio;
    }

    public void setFechaInicio(Date dFechaInicio) {
        this.dFechaInicio = dFechaInicio;
    }

    public Date getFechaFin() {
        return dFechaFin;
    }

    public void setFechaFin(Date dFechaFin) {
        this.dFechaFin = dFechaFin;
    }

    public PersonalHospitalario getEnfermera() {
        return oEnfermera;
    }

    public void setEnfermera(PersonalHospitalario oEnfermera) {
        this.oEnfermera = oEnfermera;
    }

    public EpisodioMedico getEpisodio() {
        return oEpisodio;
    }

    public void setEpisodio(EpisodioMedico oEpisodio) {
        this.oEpisodio = oEpisodio;
    }
    
    public String getFechaInicioStr(){
        SimpleDateFormat df= new SimpleDateFormat("yyyy-MM-dd");
        return df.format(this.getFechaInicio());
    }
    
    public String getFechaInicioStr2(){
        SimpleDateFormat df= new SimpleDateFormat("dd/MM/yyyy");
        return df.format(this.getFechaInicio());
    }
    
    public String getFechaFinStr(){
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        return f.format(this.getFechaFin());
    }
    
    public String getFechaFinStr2(){
        SimpleDateFormat f= new SimpleDateFormat("dd/MM/yyyy");
        return f.format(this.getFechaFin());
    }
    
}

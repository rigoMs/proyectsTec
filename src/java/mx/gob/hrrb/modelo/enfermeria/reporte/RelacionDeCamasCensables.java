
package mx.gob.hrrb.modelo.enfermeria.reporte;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import mx.gob.hrrb.modelo.core.AreaServicioHRRB;
import mx.gob.hrrb.modelo.core.EpisodioMedico;
import mx.gob.hrrb.modelo.datos.AccesoDatos;

/**
 *
 * @author J2GOV
 */
public class RelacionDeCamasCensables implements Serializable {    
    private String sProgLib;
    private String sDescripcionServ;
    private Date dFechaConsulta;
    private EpisodioMedico oEpisodio;
    private AreaServicioHRRB oServicio;
    private ArrayList<RelacionDeCamasCensables> arrCamas;    
    private AccesoDatos oAD;
    
    public RelacionDeCamasCensables(){
        dFechaConsulta= new Date();
        oEpisodio= new EpisodioMedico();
        oServicio= new AreaServicioHRRB();
        arrCamas= new ArrayList<RelacionDeCamasCensables>();
    }

    public ArrayList<RelacionDeCamasCensables> buscaRelacionDeCamasCensablesServicio() throws Exception{
        ArrayList <RelacionDeCamasCensables> arrRet=null;
        RelacionDeCamasCensables oI;
        ArrayList rst=null;        
        String sQuery="";
        int i=0;
        if(this.getServicio().getClave()==0){
            throw new Exception("RelacionCamasCensables.buscaRelacionDeCamasCensablesServicio:error, faltan datos");
        }else{
            arrRet=new ArrayList <RelacionDeCamasCensables>();
            sQuery="SELECT * FROM buscaRelacionCamasCensables('"
                    +this.getFechaConsultaStr()+"'::date,"
                    +this.getServicio().getClave()+"::smallint);";
            oAD= new AccesoDatos();
            if(oAD.conectar()){
                rst= oAD.ejecutarConsulta(sQuery);
                oAD.desconectar();
            }
            if(rst!=null && rst.size()>0){
                
                if(this.getServicio().getClave()==29){//gineco obstetricia                    
                    RelacionDeCamasCensables oD,oG;
                    oD= new RelacionDeCamasCensables();
                    oG= new RelacionDeCamasCensables();                    
                    oD.setDescripcionServ("DISTINCIÓN");
                    oG.setDescripcionServ("GINECO-OBSTETRICA");
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oI= new RelacionDeCamasCensables();
                        oI.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                        oI.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                        oI.getEpisodio().getCama().setNumero((String)vRowTemp.get(2));
                        oI.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                        oI.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                        oI.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                        oI.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                        oI.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                        oI.getEpisodio().setFechaIngreso((Date)vRowTemp.get(8));
                        oI.getServicio().setClave(((Double)vRowTemp.get(9)).intValue());
                        if(oI.getServicio().getClave()==21){
                            oD.getArrCamas().add(oI);
                        }else if(oI.getServicio().getClave()==29){
                            oG.getArrCamas().add(oI);
                        }
                    }
                    arrRet.add(oD);
                    arrRet.add(oG);
                }else if(this.getServicio().getClave()==56){//pediatria                    
                    RelacionDeCamasCensables oP,oOP,oUCIP,oURGP;
                    oP= new RelacionDeCamasCensables();
                    oOP= new RelacionDeCamasCensables();
                    oUCIP=  new RelacionDeCamasCensables();
                    oURGP= new RelacionDeCamasCensables();
                    oP.setDescripcionServ("PEDIATRÍA");
                    oOP.setDescripcionServ("ONCOPEDIATRÍA");
                    oUCIP.setDescripcionServ("UCIP");
                    oURGP.setDescripcionServ("URGENCIAS PEDIATRÍCAS");
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oI= new RelacionDeCamasCensables();
                        oI.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                        oI.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                        oI.getEpisodio().getCama().setNumero((String)vRowTemp.get(2));
                        oI.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                        oI.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                        oI.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                        oI.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                        oI.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                        oI.getEpisodio().setFechaIngreso((Date)vRowTemp.get(8));
                        oI.getServicio().setClave(((Double)vRowTemp.get(9)).intValue());
                        if(oI.getServicio().getClave()==56){
                            oP.getArrCamas().add(oI);
                        }else if(oI.getServicio().getClave()==48){
                            oOP.getArrCamas().add(oI);
                        }else if(oI.getServicio().getClave()==73){
                            oUCIP.getArrCamas().add(oI);
                        }else if(oI.getServicio().getClave()==76){
                            oURGP.getArrCamas().add(oI);
                        }
                    }
                    arrRet.add(oP);
                    arrRet.add(oOP);
                    arrRet.add(oUCIP);
                    arrRet.add(oURGP);                    
                }else if(this.getServicio().getClave()==39){// neonatos
                    RelacionDeCamasCensables oN,oU,oZT;
                    oN= new RelacionDeCamasCensables();
                    oU= new RelacionDeCamasCensables();
                    oZT= new RelacionDeCamasCensables();
                    oN.setDescripcionServ("NEONATOLOGÍA");
                    oZT.setDescripcionServ("ZONA ' T '");
                    oU.setDescripcionServ("URGENCIAS");
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oI= new RelacionDeCamasCensables();
                        oI.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                        oI.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                        oI.getEpisodio().getCama().setNumero((String)vRowTemp.get(2));
                        oI.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                        oI.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                        oI.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                        oI.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                        oI.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                        oI.getEpisodio().setFechaIngreso((Date)vRowTemp.get(8));
                        oI.getServicio().setClave(((Double)vRowTemp.get(9)).intValue());
                        if(oI.getEpisodio().getCama().getNumero().equals("A-1")){
                             oN.getArrCamas().add(oI);
                        }else if(oI.getEpisodio().getCama().getNumero().equals("A-2")){
                            oN.getArrCamas().add(oI);
                        }else if(!((String)vRowTemp.get(2)).equals("A-1") 
                                || !((String)vRowTemp.get(2)).equals("A-2")){
                            int p=Integer.parseInt(oI.getEpisodio().getCama().getNumero());
                            if(p<=14){
                                oN.getArrCamas().add(oI);
                            }else if(p>=15 && p<=24){
                                oZT.getArrCamas().add(oI);
                            }else if(p>=25 && p<=30){
                                oU.getArrCamas().add(oI);
                            }
                        }
                        
                        if(oI.getServicio().getClave()==39){
                           
                        }else if(oI.getServicio().getClave()==48){
                            oU.getArrCamas().add(oI);
                        }
                    }
                    arrRet.add(oN);
                    arrRet.add(oZT);
                    arrRet.add(oU);
                }else if(this.getServicio().getClave()==69){//toco cirugia                    
                    RelacionDeCamasCensables oT,oU,oM,oR;
                    oT= new RelacionDeCamasCensables();
                    oU= new RelacionDeCamasCensables();
                    oM= new RelacionDeCamasCensables();
                    oR= new RelacionDeCamasCensables();
                    oT.setDescripcionServ("LABOR");
                    oU.setDescripcionServ("UCI");
                    oM.setDescripcionServ("MAPE");
                    oR.setDescripcionServ("URGENCIAS");
                    for(i=0;i<rst.size();i++){
                        ArrayList vRowTemp= (ArrayList)rst.get(i);
                        oI= new RelacionDeCamasCensables();
                        oI.getEpisodio().getPaciente().setFolioPaciente(((Double)vRowTemp.get(0)).longValue());
                        oI.getEpisodio().getPaciente().setClaveEpisodio(((Double)vRowTemp.get(1)).longValue());
                        oI.getEpisodio().getCama().setNumero((String)vRowTemp.get(2));
                        oI.getEpisodio().getPaciente().getExpediente().setNumero(((Double)vRowTemp.get(3)).intValue());
                        oI.getEpisodio().getPaciente().setNombres((String)vRowTemp.get(4));
                        oI.getEpisodio().getPaciente().setApPaterno((String)vRowTemp.get(5));
                        oI.getEpisodio().getPaciente().setApMaterno((String)vRowTemp.get(6));
                        oI.getEpisodio().getDiagIngreso().setDescripcionDiag((String)vRowTemp.get(7));
                        oI.getEpisodio().setFechaIngreso((Date)vRowTemp.get(8));
                        oI.getServicio().setClave(((Double)vRowTemp.get(9)).intValue());
                        if(oI.getServicio().getClave()==69){
                            oT.getArrCamas().add(oI);
                        }else if(oI.getServicio().getClave()==72){
                            oU.getArrCamas().add(oI);
                        }else if(oI.getServicio().getClave()==38){
                            oM.getArrCamas().add(oI);
                        }else if(oI.getServicio().getClave()==75){
                            oR.getArrCamas().add(oI);
                        }
                    }
                    arrRet.add(oT);
                    arrRet.add(oU);
                    arrRet.add(oM);
                    arrRet.add(oR);
                    
                }
            }
        }
        return arrRet;
    }
    
    public String getProgLib() {
        return sProgLib;
    }

    public void setProgLib(String sProgLib) {
        this.sProgLib = sProgLib;
    }

    public Date getFechaConsulta() {
        return dFechaConsulta;
    }

    public void setFechaConsulta(Date dFechaConsulta) {
        this.dFechaConsulta = dFechaConsulta;
    }

    public EpisodioMedico getEpisodio() {
        return oEpisodio;
    }

    public void setEpisodio(EpisodioMedico oEpisodio) {
        this.oEpisodio = oEpisodio;
    }

    public AreaServicioHRRB getServicio() {
        return oServicio;
    }

    public void setServicio(AreaServicioHRRB oServicio) {
        this.oServicio = oServicio;
    }

    public ArrayList<RelacionDeCamasCensables> getArrCamas() {
        return arrCamas;
    }

    public void setArrCamas(ArrayList<RelacionDeCamasCensables> arrCamas) {
        this.arrCamas = arrCamas;
    }
    
    public String getFechaConsultaStr(){
        SimpleDateFormat f= new SimpleDateFormat("yyyy-MM-dd");
        return f.format(dFechaConsulta);
    }

    public String getDescripcionServ() {
        return sDescripcionServ;
    }

    public void setDescripcionServ(String sDescripcionServ) {
        this.sDescripcionServ = sDescripcionServ;
    }
    
}

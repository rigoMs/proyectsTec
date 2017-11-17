package mx.gob.hrrb.jbs.consultaexterna;

import java.io.Serializable;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import mx.gob.hrrb.modelo.core.Turno;
import mx.gob.hrrb.modelo.serv.EstudioRealizado;
import org.primefaces.context.RequestContext;

/**
 *
 * @author Juan
 */
@ManagedBean(name = "oRegCitaServ")
@SessionScoped
public class RegistroCitaServ implements Serializable
{
    private EstudioRealizado oEstReal;
    private long nCitasTotal;
    private int nCitasExtra;
    private int nCitasMax;
    private FacesMessage message;
    private String sEstado;
    private boolean bExtra;
    private boolean bLabs;
    private String sNext;
    
    public RegistroCitaServ()
    {
        oEstReal = new EstudioRealizado();
        sEstado="mostrarbtn";
        bExtra = false;
        bLabs = false;
    }
    
    public String registraCita() throws Exception
    {
        String sPag = "RegistrarCitaServ";
        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita","Registro Fallido");
        oEstReal.getArea().buscarDisponibilidadArea();
        nCitasTotal = oEstReal.buscarTotalCitados();
        nCitasMax = oEstReal.getArea().getCitasMax();
        nCitasExtra = oEstReal.getArea().getCitasExtra();
        int nTotalCitados = (int)nCitasTotal;
        int nAfectados = 0;
        System.out.println("Clave Episodio: " + oEstReal.getEpisodio().getClaveEpisodio());
        
        if( validaFechaHora() == true )
        {
            if( nTotalCitados < ( nCitasMax + nCitasExtra ) )
            {
                if( nTotalCitados < nCitasMax || bExtra == true )
                {
                    oEstReal.horarioTurno();
                    if( bLabs == true )
                    {
                        oEstReal.getEpisodio().setClaveEpisodio(oEstReal.getEpisodio().getClaveEpisodio());
                        nAfectados = oEstReal.insertaCitasLab();
                    }
                    else
                    {
                        nAfectados = oEstReal.insertaCitaServicio();
                    }
                    if( nAfectados > 0 )
                    {
                        SimpleDateFormat formato = new SimpleDateFormat( "dd/MM/yyyy HH:mm" );
                        message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita",
                            "Registro exitoso         " +
                            formato.format( oEstReal.getFechaProgramado() ) );
                        reseteaDatosCitaImagen();
                        sPag = "BuscarCitasXServicio";
                        bLabs = false;
                    }
                }
                else
                {
                    sEstado = "";
                    message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita",
                        "No hay espacio en la fecha seleccionada");
                }
            }
            else
            {
                sPag = "BuscarCitasXServicio";
                message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita",
                    "No se generó la cita por no existir cupo");
            }
        }
        else
        {
            message = new FacesMessage(FacesMessage.SEVERITY_INFO, "Registro de Cita",
                "No se puede registrar la cita en el turno seleccionado");
        }
        RequestContext.getCurrentInstance().showMessageInDialog(message);
        return sPag;
    }
    
    public boolean validaFechaHora() throws Exception
    {
        boolean bRet = false;
        Date dHoy = new Date();
        SimpleDateFormat dForm = new SimpleDateFormat( "dd/MM/yyyy" );
        String sProg = dForm.format( oEstReal.getFechaProgramado() );
        String sHoy = dForm.format( dHoy );
        
        if( sProg.compareTo( sHoy ) != 0 )
        {
            bRet = true;
        }
        else
        {
            String sTurno = oEstReal.getArea().getTurno().getClave().trim();
            if( sTurno.compareTo( Turno.CVE_VESPERTINO ) == 0 )
            {
                dForm = new SimpleDateFormat( "HH" );
                int nHoraActual = Integer.parseInt( dForm.format( dHoy ) );
                String sHoraTurnoVes = oEstReal.getArea().getTurno().buscaHorarioTurno( 1 );
                int nHoraVes = Integer.parseInt( sHoraTurnoVes.split( ":" )[ 0 ] );
                
                if( nHoraActual < nHoraVes )
                {
                    bRet = true;
                }
            }
        }
        
        return bRet;
    }
    
    public String fechaHoraMinimo() throws Exception
    {
        String sFecha = "";
        SimpleDateFormat fechaHoraFormat;
        Date dFechaMin = new Date();
        
        fechaHoraFormat = new SimpleDateFormat( "HH" );
        int nHoraActual = Integer.parseInt( fechaHoraFormat.format( dFechaMin ) );
        
        oEstReal.getArea().getTurno().setClave( "VES" );
        String sHoraTurnoVes = oEstReal.getArea().getTurno().buscaHorarioTurno( 1 );
        oEstReal.getArea().getTurno().setClave( null );
        int nHoraVes = Integer.parseInt( sHoraTurnoVes.split( ":" )[ 0 ] );
        
        fechaHoraFormat = new SimpleDateFormat( "dd/MM/yyyy" );
        if( nHoraActual < nHoraVes )
        {
            sFecha = fechaHoraFormat.format( dFechaMin );
        }
        else
        {
            Calendar cal = Calendar.getInstance();
            cal.setTime( dFechaMin );
            cal.add( Calendar.DATE, 1 );
            dFechaMin = cal.getTime();
            
            sFecha = fechaHoraFormat.format( dFechaMin );
        }
        
        return sFecha;
    }
    
    public String getDireccion(){
        return sNext = "RegistrarCitaServ";
    }

    public EstudioRealizado getEstReal()
    {
        return oEstReal;
    }

    public void setEstReal( EstudioRealizado oEstReal )
    {
        this.oEstReal = oEstReal;
    }
    
    public long getCitados()
    {
        return nCitasTotal;
    }
    
    public void setCitados( long nCitados )
    {
        this.nCitasTotal = nCitados;
    }
    
    public int getCitasExtra()
    {
        return nCitasExtra;
    }
    
    public void setCitasExtra( int nCitasExtra )
    {
        this.nCitasExtra = nCitasExtra;
    }
    
    public String muestraExtra()
    {
        return sEstado;
    }
    
    public String addExtra() throws Exception
    {
        bExtra = true;
        String sPag = registraCita();
        sEstado = "mostrarbtn";
        return sPag;
    }
    
    public void reseteaDatosCitaImagen()
    {
        bExtra = false;
        oEstReal = null;
    }
    
    public void setServLab( boolean bLabs )
    {
        this.bLabs = bLabs;
    }
    
    public boolean getServLab()
    {
        return bLabs;
    }

    public String getNext() {
        return sNext;
    }

    public void setNext(String sNext) {
        this.sNext = sNext;
    }
}
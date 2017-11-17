package mx.gob.hrrb.jbs.almacen;

import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.ViewScoped;
import mx.gob.hrrb.modelo.almacen.CabeceraControl;
/**
 *
 * @author GIL
 */
@ManagedBean(name = "oCaducida")
@ViewScoped
public class CaducidaMaterial  implements Serializable{
private CabeceraControl oDetMat=null;
private CabeceraControl[] arrMat=null;
private boolean verEdicion = true;
public static final int JUST_CENTER = 'c';
    
    public CaducidaMaterial() throws Exception{
        cargaLista();
    }
    
    private void cargaLista() throws Exception {
        oDetMat=new CabeceraControl();
        arrMat=(CabeceraControl[]) oDetMat.buscaproximoscaducados();
    }

    public CabeceraControl[]getLista()throws Exception{
        cargaLista();
        return arrMat;
    }
    
    public boolean isVerEdicion() {
        return verEdicion;
    }

    public void setVerEdicion(boolean verEdicion) {
        this.verEdicion = verEdicion;
    }
}
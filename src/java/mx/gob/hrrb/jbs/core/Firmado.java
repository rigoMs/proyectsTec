package mx.gob.hrrb.jbs.core;

import mx.gob.hrrb.modelo.core.Usuario;
import java.io.Serializable;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;

@ManagedBean(name="oFirm")
@SessionScoped
public class Firmado implements Serializable{
	private static final long serialVersionUID = 1L;
	private Usuario oUsu=new Usuario();
	
	public Usuario getUsu(){return oUsu;}
	public void setUsu(Usuario value){oUsu = value;}
}

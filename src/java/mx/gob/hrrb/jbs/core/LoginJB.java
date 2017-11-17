package mx.gob.hrrb.jbs.core;


import mx.gob.hrrb.modelo.core.Usuario;
import java.io.Serializable;
import javax.faces.application.FacesMessage;
import javax.faces.bean.ManagedBean;
import javax.faces.bean.SessionScoped;
import javax.faces.context.FacesContext;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import mx.gob.hrrb.modelo.core.Temperaturas;

@ManagedBean(name="oLogin")
@SessionScoped

public class LoginJB implements Serializable{
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private String cve;
	private String pwd;
	
	public String getCve(){return cve;}
	public void setCve(String value){cve = value;}
	public String getPwd(){return pwd;}
	public void setPwd(String value){pwd = value;}
	
	 public String login() {

        FacesContext facesContext = FacesContext.getCurrentInstance();
        Usuario oUsu = new Usuario();
        String sRet = "sesiones/Inicio";
        Firmado oFirm;

        oUsu.setIdUsuario(cve);
        oUsu.setPassword(pwd);
        FacesMessage msg = null;
        try {
            if (oUsu.buscarCvePwd()) {
                oFirm = new Firmado();
                oFirm.setUsu(oUsu);
                        if (oFirm.getUsu().getPerfil().get(0).getClave().equals("FARMCAPASI")) {        
                            if (new Temperaturas().buscaTemperaturaTurno() == -1) {
                               sRet = "sesiones/capasits/farmacia/caducacionProxima";
                            System.out.println();
                            }                
                        }
                HttpSession session
                        = (HttpSession) facesContext.getExternalContext().
                        getSession(false);
                session.setAttribute("oFirm", oFirm);
                System.out.println("usuario="+oFirm);
                msg = new FacesMessage(FacesMessage.SEVERITY_INFO, "Bienvenid@", cve);
            } else {
                sRet = "index";
                msg = new FacesMessage(FacesMessage.SEVERITY_WARN, "Login Error", "Credenciales no válidas");
                                //System.out.println(msg.toString());

            }
            FacesContext.getCurrentInstance().addMessage(null, msg);

        } catch (Exception e) {
            sRet = "index";
            e.printStackTrace();
        }
        return sRet;
        
    }
	public String logout(){
             HttpServletRequest request; request = (HttpServletRequest)FacesContext.getCurrentInstance().getExternalContext().getRequest();
             
            FacesContext.getCurrentInstance().getExternalContext().invalidateSession();
            return "/index.xhtml?faces-redirect=true";
	}
}

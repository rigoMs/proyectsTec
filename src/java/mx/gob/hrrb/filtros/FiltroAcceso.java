package mx.gob.hrrb.filtros;
import java.io.IOException;
import javax.servlet.*;
import javax.servlet.http.*;
import mx.gob.hrrb.jbs.core.Firmado;

public class FiltroAcceso implements Filter {
	FilterConfig config = null;
	ServletContext servletContext = null;

        @Override
	public void init(FilterConfig filterConfig) throws ServletException{
		config = filterConfig;
		servletContext = config.getServletContext();
	}
	
        @Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain)
	throws IOException, ServletException{
        HttpServletRequest httpRequest = (HttpServletRequest)request;
        HttpServletResponse httpResponse = (HttpServletResponse)response;
        HttpSession session = httpRequest.getSession();
        boolean bTienePermiso=false;
        String vec[]=null;
        Firmado oFirm;
        
            /*obtener objeto de sesión*/
            oFirm = (Firmado)session.getAttribute("oFirm"); 
            String urlStr = httpRequest.getRequestURL().toString().toLowerCase();
            
            if (!urlStr.contains("index.xhtml")){
                if (oFirm!=null){
                    if (urlStr.contains("inicio.xhtml"))
                        bTienePermiso = true;
                    else{
                        vec=urlStr.split("sesiones/");
                        try{
                            bTienePermiso = oFirm.getUsu().tienePermiso(vec[1]);
                        }catch(Exception e){
                            e.printStackTrace();
                            bTienePermiso = false;
                        }
                    }
                }
            }
                
            if (oFirm == null || bTienePermiso==false){
                httpResponse.sendRedirect("/HRRB/faces/index.xhtml");
            }else{
                chain.doFilter(request, response);
            }
	}

        @Override
	public void destroy() {
	}
}

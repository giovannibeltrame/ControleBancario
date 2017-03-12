package com.brq.filtro;

import java.io.IOException;
import javax.servlet.DispatcherType;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.brq.model.Gerente;
import com.brq.model.Usuario;
import com.brq.persistence.GerenteDao;

@WebFilter(
		dispatcherTypes = {
				DispatcherType.REQUEST, 
				DispatcherType.FORWARD, 
				DispatcherType.INCLUDE, 
				DispatcherType.ERROR
		}
					, 
		urlPatterns = { 
				"/LoginFilter", 
				"/banco/*"
		}, 
		servletNames = { "Sistema" })
public class LoginFilter implements Filter {

    public LoginFilter() {
        // TODO Auto-generated constructor stub
    }

	public void destroy() {
		// TODO Auto-generated method stub
	}

	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		HttpServletRequest req = (HttpServletRequest) request;
		HttpServletResponse res = (HttpServletResponse) response;
		HttpSession session = req.getSession();
		Usuario u = (Usuario) session.getAttribute("u");		
		String url[] = req.getServletPath().split("/");
		
		if (u == null) {
			res.sendRedirect(req.getContextPath() + "/");
		}
		else if (url.length >= 4) {
			try {
				if (url[3].equalsIgnoreCase("gerente")) {
					Gerente g = new GerenteDao().buscarPorId("idusuario", u.getIdusuario());
					if (g == null)
						res.sendRedirect(req.getContextPath() + "/");
				}
				chain.doFilter(request, response);
			}
			catch(Exception e) {
				e.printStackTrace();
			}
		}
		else {
			chain.doFilter(request, response);
		}
	}

	public void init(FilterConfig fConfig) throws ServletException {
		// TODO Auto-generated method stub
	}

}
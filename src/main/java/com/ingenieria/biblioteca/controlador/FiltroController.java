/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package com.ingenieria.biblioteca.controlador;

import java.io.IOException;
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

@WebFilter(filterName = "AuthFilter", urlPatterns = { "*.xhtml" })
public class FiltroController implements Filter {
      	
        public FiltroController() {
            
	}

	@Override
	public void init(FilterConfig filterConfig) throws ServletException {

	}

	@Override
	public void doFilter(ServletRequest request, ServletResponse response,
			FilterChain chain) throws IOException, ServletException {
		try {

			HttpServletRequest reqt = (HttpServletRequest) request;
			HttpServletResponse resp = (HttpServletResponse) response;
			HttpSession ses = reqt.getSession(false);
                     
                        
                        
			String reqURI = reqt.getRequestURI();
			if (reqURI.indexOf("/faces/index.xhtml") >= 0
					|| estaPermitida(ses, reqURI)
					|| reqURI.indexOf("/public/") >= 0
                                        || reqURI.indexOf("/registro.xhtml") >= 0    
					|| reqURI.contains("javax.faces.resource"))
				chain.doFilter(request, response);
			else
				resp.sendRedirect(reqt.getContextPath() + determinaDireccionamiento(ses));
		} catch (Exception e) {
			System.out.println(e.getMessage());
		}
	}

	@Override
	public void destroy() {

	}
        
        /*Recibe una sesion y una url y determina si le es permitido al usuario
        le es permitido visitar la url
        */
        public boolean estaPermitida(HttpSession ses, String reqURI){
            if (ses != null && ses.getAttribute("nombre") != null){
                return true;
            } else if (ses != null && ses.getAttribute("profesor") != null){
                return permitidasProfesor(reqURI);
            }
            else if (ses != null && ses.getAttribute("administrador") != null){
                return permitidasAlumno(reqURI);
            }
            
            return false;
        }

    //estas son las paginas que los profesores pueden ver
    private boolean permitidasProfesor(String reqURI) {
        if ( reqURI.indexOf("/principalProfesor.xhtml") >= 0 || reqURI.indexOf("/historialEventosProfesor.xhtml") >= 0 || reqURI.indexOf("/reservarEvento.xhtml") >= 0 ){
            
            return true;
        }
        
        return false;
    }

    //estas son las paginas que los alumnos pueden ver
    private boolean permitidasAlumno(String reqURI) {
        if ( reqURI.indexOf("/administrarProfesores.xhtml" ) >= 0 || reqURI.indexOf("/admistrarSalas.xhtml") >= 0 || reqURI.indexOf("/administrarEdificios.xhtml") >= 0 || reqURI.indexOf("/administrarEventos.xhtml") >= 0){
            return true;
        }
        
        return false;
    }
    
    /*cuando el usuario entra a una direccion no admitida este metodo determina
    a donde se le va a redirigir
    */
    
    private String determinaDireccionamiento(HttpSession ses){
        if (ses != null && ses.getAttribute("profesor") != null){
            return "/faces/principalProfesor.xhtml";
        }
        else if (ses != null && ses.getAttribute("administrador") != null){
            return "/faces/index.xhtml";
        }
        return "/faces/index.xhtml";
    }
}


package com.alura.gerenciador.servlet;

import java.io.IOException;

import jakarta.servlet.Filter;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;
import jakarta.servlet.annotation.WebFilter;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;

/**
 * Servlet Filter implementation class AutorizacionFilter
 */
//@WebFilter(urlPatterns = "/entrada")
public class AutorizacionFilter implements Filter {


	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain chain) 
			throws IOException, ServletException {
		
		System.out.println("AutorizacionFilter");
		
		HttpServletRequest request = (HttpServletRequest) servletRequest;
		HttpServletResponse response = (HttpServletResponse) servletResponse;
		String paramAccion = request.getParameter("accion");
		HttpSession sesion = request.getSession();
		
		Boolean esUnUsuarioNoLogado = (sesion.getAttribute("loginUsuario") == null);
		Boolean esUnaAccionProtegida = !(paramAccion.equals("Login") || paramAccion.equals("LoginForm"));
		
		if(esUnUsuarioNoLogado && esUnaAccionProtegida) {
			response.sendRedirect("entrada?accion=LoginForm");
		return;
		}
		
		// pass the request along the filter chain
		chain.doFilter(request, response);
	}

}

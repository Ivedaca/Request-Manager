package com.alura.gerenciador.servlet;

import java.io.IOException;
import java.util.List;

import com.alura.gerenciador.modelo.DB;
import com.alura.gerenciador.modelo.Empresa;
import com.google.gson.Gson;
import com.thoughtworks.xstream.XStream;

import jakarta.servlet.ServletException;
import jakarta.servlet.annotation.WebServlet;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class EmpresasService
 */
@WebServlet("/empresas")
public class EmpresasService extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		List<Empresa> empresas = new DB().getEmpresas();
		
		String valor = request.getHeader("Accept");
		
		if(valor.contains("xml")) {                         //endsWith permite hacer el pedido con el sufijo
			XStream xstream = new XStream(); 
			xstream.alias("empresa", Empresa.class);
			String xml = xstream.toXML(empresas); 
			
			response.setContentType("Application/xml"); 
			response.getWriter().print(xml);
		}else if (valor.contains("json")) {
			Gson gson = new Gson();                         //importa lib json
			String json = gson.toJson(empresas);            //convierte lista a string
			
			response.setContentType("Application/json");    //devuelve al http
			response.getWriter().print(json);               //imprime en pantalla
		}else {
			response.setContentType("Application/json");    
			response.getWriter().print("{'message':'No content'}");
		}
	}

}

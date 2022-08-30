package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select" })
public class Controller extends HttpServlet {
	private static final long serialVersionUID = 1L;
	DAO dao = new DAO();
	JavaBeans contato = new JavaBeans();

	public Controller() {
		super();

	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String action = request.getServletPath();
		System.out.println(action);
		if (action.equals("/main")) {
			contatos(request, response);
		} else if (action.equals("/insert")) {
			novoContato(request, response);
		} else if (action.equals("/select")) {
			listarContato(request, response);
		} else {
			response.sendRedirect("index.html");
		}

	}

	// Listar Contatos
	protected void contatos(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// CRIANDO UM OBJETO QUE IRA RECEBER OS DADOS JAVABEANS
		ArrayList<JavaBeans> lista = dao.listarContatos();
		// ENCAMINHAR A LISTA AO DOCUMENTO AGENDA.JSP
		request.setAttribute("contatos", lista);
		RequestDispatcher rd = request.getRequestDispatcher("agenda.jsp");
		rd.forward(request, response);
	}

	// Novo Contatos
	protected void novoContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// TESTE
		System.out.println(request.getParameter("nome"));
		System.out.println(request.getParameter("fone"));
		System.out.println(request.getParameter("email"));
		// SETAR VARIAVEIS JAVABEANS
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// INVOCAR O METODO INSERIRCONTATO PASSANDO O OBJETO CONTATO
		dao.inserirContato(contato);
		// REDIRECIONAR PARA O DOCUMENTO AGENDA.JSP
		response.sendRedirect("Main");
	}

	// EDITAR CONTATO
	protected void listarContato(HttpServletRequest request,
			HttpServletResponse response) {
		// RECEBIMENTO DO ID DO CONTATO QUE SERA EDITADO
		String idcon = request.getParameter("idcon");
		// SETAR VARIAVEL JAVABEANS
		contato.setIdcon(idcon);
		// Executar o metodo selecionarCOntato (DAO)
		dao.selecionarContato(contato);
		
	}
	
	
}

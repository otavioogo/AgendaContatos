package controller;

import java.io.IOException;
import java.util.ArrayList;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.itextpdf.text.Document;
import com.itextpdf.text.Paragraph;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;

import model.DAO;
import model.JavaBeans;

@WebServlet(urlPatterns = { "/Controller", "/main", "/insert", "/select", "/update", "/delete", "/report" })
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
		} else if (action.equals("/update")) {
			editarContato(request, response);
		} else if (action.equals("/delete")) {
			removerContato(request, response);
		} else if (action.equals("/report")) {
			gerarRelatorio(request, response);
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
	protected void listarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// RECEBIMENTO DO ID DO CONTATO QUE SERA EDITADO
		String idcon = request.getParameter("idcon");
		// SETAR VARIAVEL JAVABEANS
		contato.setIdcon(idcon);
		// Executar o metodo selecionarCOntato (DAO)
		dao.selecionarContato(contato);
		// SETAR ATRIBUTOS DO FORMULARIO COM O CONTEUDO JAVABEANS
		request.setAttribute("idcon", contato.getIdcon());
		request.setAttribute("nome", contato.getNome());
		request.setAttribute("fone", contato.getFone());
		request.setAttribute("email", contato.getEmail());
		// Encaminhar ao documento editar.jsp
		RequestDispatcher rd = request.getRequestDispatcher("editar.jsp");
		rd.forward(request, response);
	}

	protected void editarContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// SETAR AS VARIAVEIS JAVABEANS
		contato.setIdcon(request.getParameter("idcon"));
		contato.setNome(request.getParameter("nome"));
		contato.setFone(request.getParameter("fone"));
		contato.setEmail(request.getParameter("email"));
		// Executar o metodo alterarContato
		dao.alterarContato(contato);
		// REDICIONAR PARA O DOCUMENTO AGENDA.JSP (ATUALIZANDO AS ALTERACOES)
		response.sendRedirect("main");
	}

	// REMOVER CONTATO
	protected void removerContato(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// RECEBIMENTO DO ID DO CONTATO A SER EXCLUIDO ( VALIDADOR.JS)
		String idcon = request.getParameter("idcon");
		// SETAR A VARIAVEL IDCON NO JAVABEANS
		contato.setIdcon(idcon);
		// EXECUTAR O METODO DELETAR CONTATO PASSANDO POR PARAMETRO
		dao.deletarContato(contato);
		// REDICIONAR PARA O DOCUMENTO AGENDA.JSP (ATUALIZANDO AS ALTERACOES)
		response.sendRedirect("main");

	}

	// GERAR RELATORIOS EM PDF
	protected void gerarRelatorio(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Document documento = new Document();
		try {
			// TIPO DE CONTEUDO
			response.setContentType("apllication/pdf");
			// NOME DO DOCUMENTO
			response.addHeader("content-Disposition", "inline; filename=" + "contatos.pdf");
			// CRIAR O DOCUMENTO
			PdfWriter.getInstance(documento, response.getOutputStream());
			//ABRIR O DOCUMENTO PARA GERAR O CONTEUDO
			documento.open();
			documento.add(new Paragraph("Lista de Contatos:"));
			documento.add(new Paragraph(" "));
			//CRIAR UMA TABELA
			PdfPTable tabela = new PdfPTable(3);
			//CABECALHO
			PdfPCell col1 = new PdfPCell(new Paragraph("Nome"));
			PdfPCell col2 = new PdfPCell(new Paragraph("Fone"));
			PdfPCell col3 = new PdfPCell(new Paragraph("Email"));
			tabela.addCell(col1);
			tabela.addCell(col2);
			tabela.addCell(col3);
			documento.add(tabela);
			
			//FECHAR O DOCUMENTO
			documento.close();
		} catch (Exception e) {
			System.out.println(e);
			documento.close();
		}

	}
}

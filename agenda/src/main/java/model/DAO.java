package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ArrayList;

public class DAO {
	/** MODULO DE CONEXAO **/
	// PARAMETROS DE CONEXAO
	private String driver = "com.mysql.cj.jdbc.Driver";
	private String url = "jdbc:mysql://127.0.0.1:3306/dbagenda?useTimezone=true&serverTimezone=UTC";
	private String user = "root";
	private String password = "root";

	// METODO DE CONEXAO

	private Connection conectar() {
		Connection con = null;
		try {
			Class.forName(driver);
			con = DriverManager.getConnection(url, user, password);
			return con;
		} catch (Exception e) {
			System.out.println(e);
			return null;

		}
	}
	/* CRUD CREATE */

	public void inserirContato(JavaBeans contato) {
		String create = "insert into contatos(nome,fone,email) values(?,?,?)";
		try {
			// ABRIR A CONEXAO
			Connection con = conectar();
			// Preparar a query para execucao no banco de dados
			PreparedStatement pst = con.prepareStatement(create);
			// SUBSTITUIR OS PARAMETROS (?) pelo
			// CONTEUDO DAS VARIAVEIS JAVABEANS
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			// EXECUTAR A QUERY
			pst.executeUpdate();
			// ENCERRAR A CONEXAO COM O BANCO DE DADOS
			con.close();

		} catch (Exception e) {
			System.out.println(e);
		}
	}

	/* CRUD READ */

	public ArrayList<JavaBeans> listarContatos() {
		// CRIANDO UM OBJETO PARA ACESSAR A CLASSE JAVABEANS
		ArrayList<JavaBeans> contatos = new ArrayList<>();
		String read = "select * from contatos order by nome";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read);
			ResultSet rs = pst.executeQuery();
			// O lACO ABAIXO SERA EXECUTADO ENQUANTO HOUVER CONTATOS ABAIXO
			while (rs.next()) {
				// VARIAVEIS DE APOIO QUE RECEBEM OS DADOS DO BANCO
				String idcon = rs.getString(1);
				String nome = rs.getString(2);
				String fone = rs.getString(3);
				String email = rs.getString(4);
				// POPULANDO O ARRAYLIST
				contatos.add(new JavaBeans(idcon, nome, fone, email));

			}
			con.close();
			return contatos;
		} catch (Exception e) {
			System.out.println(e);
			return null;
		}
	}

	/** CRUD UPDATE **/
	// SELECIONAR O CONTATO
	public void selecionarContato(JavaBeans contato) {
		String read2 = "select * from contatos where idcon = ?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(read2);
			pst.setString(1, contato.getIdcon());
			ResultSet rs = pst.executeQuery();
			while (rs.next()) {
				contato.setIdcon(rs.getString(1));
				contato.setNome(rs.getString(2));
				contato.setFone(rs.getString(3));
				contato.setEmail(rs.getString(4));
			}
		} catch (Exception e) {
			System.out.println(e);
		}
	}

	// Editar o Contato
	public void alterarContato(JavaBeans contato) {
		String create = "update contatos set nome=?,fone=?,email=? where idcon=?";
		try {
			Connection con = conectar();
			PreparedStatement pst = con.prepareStatement(create);
			pst.setString(1, contato.getNome());
			pst.setString(2, contato.getFone());
			pst.setString(3, contato.getEmail());
			pst.setString(4, contato.getIdcon());
			
			pst.executeUpdate();
			con.close();
		} catch (Exception e) {
			System.out.println(e);
		}
	}

}

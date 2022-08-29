package model;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

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
}

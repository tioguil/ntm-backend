package br.com.projectBackAnd.dao;

import java.io.IOException;
import java.sql.SQLException;

public class UsuarioDAO extends GenericDAO{
	private static Long id;
	public  void persist() throws ClassNotFoundException, SQLException, IOException {
		String sql = "insert into usuario(nome,sobrenome,cpf,cidade, uf, cep, email, senha) values(?,?,?,?,?,?,?,?)";
		
		this.id = super.executeQuery(sql, "Guilherme", "Brito dos Santos", "43105581863","Osasco","SP", "06140-120", "guil@guil", "123456" );
				System.out.println(id);
	}
	public static void main (String args []) throws ClassNotFoundException, SQLException, IOException {
		new UsuarioDAO().persist();
	}
   
}

package br.com.projectBackAnd.dao;

import br.com.projectBackAnd.model.Usuario;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Component
public class UsuarioDAO extends GenericDAO{

	public Long cadastrar(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
	    String sql = "INSERT into usuario(nome, sobrenome, email, senha, data_nascimento, perfil_acesso, cep, endereco, numero, " +
                "complemento, cidade, uf)" +
                " values(?,?,?,MD5(?),?,?,?,?,?,?,?,?)";
        Long id = super.executeQuery(sql,
                usuario.getNome(),
                usuario.getSobreNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getNascimentoFormater("yyyy-MM-dd"),
                usuario.getPerfilAcesso(),
                usuario.getCep(),
                usuario.getEndereco(),
                usuario.getNumero(),
                usuario.getComplemento(),
                usuario.getCidade(),
                usuario.getUf()
                );
        return id;
	}

    public Usuario autenticar(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from usuario where email = ? and senha = MD5(?)";

        ResultSet rs = super.executeResutSet(sql, usuario.getEmail(), usuario.getSenha());

        if(rs.next()){
            usuario.setSenha("");
            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSobreNome(rs.getString("sobrenome"));
            usuario.setNascimento(rs.getDate("data_nascimento"));
            usuario.setPerfilAcesso(rs.getInt("perfil_acesso"));
            usuario.setCep(rs.getString("cep"));
            usuario.setEndereco(rs.getString("endereco"));
            usuario.setNumero(rs.getInt("numero"));
            usuario.setComplemento(rs.getString("complemento"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setUf(rs.getString("uf"));
        }else{
            usuario.setId(-1L);
        }

        return usuario;
    }
}

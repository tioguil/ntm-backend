package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Cargo;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class UsuarioDAO extends GenericDAO{

	public Long cadastrar(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
	    String sql = "INSERT into usuario(nome, sobrenome, email, senha, cep, endereco, numero_endereco, " +
                "complemento, cidade, uf, telefone, celular, cpf_cnpj, rg, cargo_id, observacao, perfil_acesso)" +
                " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
        Long id = super.executeQuery(sql,
                usuario.getNome(),
                usuario.getSobreNome(),
                usuario.getEmail(),
                usuario.getSenha(),
                usuario.getCep(),
                usuario.getEndereco(),
                usuario.getEnderecoNumero(),
                usuario.getComplemento(),
                usuario.getCidade(),
                usuario.getUf(),
                usuario.getTelefone(),
                usuario.getCelular(),
                usuario.getCpfCnpj(),
                usuario.getRg(),
                usuario.getCargo().getId(),
                usuario.getObservacao(),
                usuario.getPerfilAcesso()
                );
        return id;
	}

	public Usuario findByEmail(String email) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from usuario where email = ?";

        Usuario usuario = new Usuario();
        ResultSet rs = super.executeResutSet(sql, email);

        if(rs.next()){
            usuario.setEmail(email);
            usuario.setSenha(rs.getString("senha"));
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setCelular(rs.getString("celular"));
            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpfCnpj(rs.getString("cpf_cnpj"));
            usuario.setRg(rs.getString("rg"));
            usuario.setSobreNome(rs.getString("sobrenome"));
            usuario.setPerfilAcesso(rs.getString("perfil_acesso"));
            usuario.setCep(rs.getString("cep"));
            usuario.setEndereco(rs.getString("endereco"));
            usuario.setEnderecoNumero(rs.getString("numero_endereco"));
            usuario.setComplemento(rs.getString("complemento"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setUf(rs.getString("uf"));
        }else{
            usuario = null;
        }

        return usuario;
    }

    public Usuario autenticar(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from usuario where email = ? and senha = MD5(?)";

        ResultSet rs = super.executeResutSet(sql, usuario.getEmail(), usuario.getSenha());

        if(rs.next()){
            usuario.setSenha("");
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setCelular(rs.getString("celular"));
            usuario.setId(rs.getLong("id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setCpfCnpj(rs.getString("cpf_cnpj"));
            usuario.setRg(rs.getString("rg"));
            usuario.setSobreNome(rs.getString("sobrenome"));
            usuario.setPerfilAcesso(rs.getString("perfil_acesso"));
            usuario.setCep(rs.getString("cep"));
            usuario.setEndereco(rs.getString("endereco"));
            usuario.setEnderecoNumero(rs.getString("numero_endereco"));
            usuario.setComplemento(rs.getString("complemento"));
            usuario.setCidade(rs.getString("cidade"));
            usuario.setUf(rs.getString("uf"));
        }else{
            usuario.setId(-1L);
        }

        return usuario;
    }

    public String consultaNivel(Long idUser) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select perfil_acesso from usuario where id = ?";

        ResultSet rs = super.executeResutSet(sql, idUser);

        if(rs.next()){
            return rs.getString("perfil_acesso");
        }else {
            return "-1";
        }
    }

    public Boolean recuperarSenha(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {

	    String sqlGet = "select id from usuario where email = ?";

	    ResultSet rs = super.executeResutSet(sqlGet, usuario.getEmail());

        if(rs.next()){
            String sql = "update usuario set senha = ? where id = ?";

            super.executeQuery(sql, usuario.getSenha(), rs.getLong("id"));
            return true;
        }

        return false;
    }


    public List<Usuario> listarAnalistas() throws SQLException, IOException, ClassNotFoundException {


	    String sql = "select a.nome,a.sobrenome, b.cargo\n" +
                "from usuario a \n" +
                "inner join cargo b \n" +
                "on a.cargo_id = b.id\n" +
                "where a.perfil_acesso = \"analista\"; ";

	    ResultSet rs = super.executeResutSet(sql);

        ArrayList<Usuario> analistas = new ArrayList<>();


        while (rs.next()){
            Usuario analista = new Usuario();
            Cargo cargo = new Cargo();

            analista.setNome(rs.getString("nome"));
            analista.setSobreNome(rs.getString("sobrenome"));
            cargo.setCargo(rs.getString("cargo"));
            analista.setCargo(cargo);

            analistas.add(analista);

        }

        return  analistas;

    }


    public String findPasswordById(Long id) throws SQLException, IOException, ClassNotFoundException {
	    String sql = "select senha from usuario where id = ?";
	    ResultSet rs = super.executeResutSet(sql, id);

	    if(rs.next()){
	        return rs.getString("senha");
        }else {
            return null;
        }
    }

    public void atualizarSenha(Usuario usuarioFront) throws SQLException, IOException, ClassNotFoundException {
	    String sql = "update usuario set senha = ? where id = ?";
	    super.executeQuery(sql, usuarioFront.getSenha(), usuarioFront.getId());
    }
>>>>>>> 26b0673714b0f7b6ad2f28f442e92c080eacf94d
}

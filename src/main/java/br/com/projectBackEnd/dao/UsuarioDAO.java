package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.*;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Repository
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
            usuario.setImagePath(rs.getString("imagePath"));
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
            usuario.setImagePath(rs.getString("imagePath"));
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

    public String consultaNivel(Long idusuario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select perfil_acesso from usuario where id = ?";

        ResultSet rs = super.executeResutSet(sql, idusuario);

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


	    String sql = "select a.nome,a.sobrenome,a.cidaade, b.cargo\n" +
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
            analista.setCidade(rs.getString("cidade"));
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

    public List<Usuario> pesquisaAnalista(String search) throws SQLException, IOException, ClassNotFoundException {
	    search = "%"+search+"%";
	    String sql = "select us.id, us.email, us.nome,us.cidade, ca.cargo from usuario us left join cargo ca on us.cargo_id = ca.id where us.perfil_acesso = 'analista' and (us.nome like ? or us.email like ? or ca.cargo like ? or us.cpf_cnpj like ?) group by us.id limit 5";
	    ResultSet rs = super.executeResutSet(sql, search, search, search , search );

        List<Usuario> list = new ArrayList<>();
	    while (rs.next()){
            Usuario us = new Usuario();
            us.setId(rs.getLong("id"));
            us.setNome(rs.getString("nome"));
            us.setEmail(rs.getString("email"));
            us.setCidade(rs.getString("cidade"));
            Cargo cargo = new Cargo();
            cargo.setCargo(rs.getString("cargo"));
            us.setCargo(cargo);

//            List<Habilidade> listHabilidade = new ArrayList<>();
//            Habilidade habilidade = new Habilidade(rs.getString("habilidade"));
//            if(habilidade.getNome() != null) listHabilidade.add(habilidade);
//            us.setHabilidades(listHabilidade);
            list.add(us);
        }

	    return list;
    }

    public Usuario getUsuarioID(Long idUsuario) throws SQLException, IOException, ClassNotFoundException {

	    String sql = "select * from usuario join cargo on cargo.id = usuario.cargo_id where usuario.id = ?";
	    ResultSet rs = super.executeResutSet(sql, idUsuario);

	    if(rs.next()){
	        Usuario usuario = new Usuario();
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setCelular(rs.getString("celular"));
            usuario.setId(rs.getLong("id"));
            usuario.setImagePath(rs.getString("imagePath"));
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
            Cargo cargo = new Cargo();
            cargo.setId(rs.getLong("cargo_id"));
            cargo.setCargo(rs.getString("cargo"));
            cargo.setDescricao(rs.getString("descricao"));
            usuario.setCargo(cargo);
            return usuario;

        }else {
	        return null;
        }
    }
    
    public Usuario editarUsuario(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
		
    	String sql = "update usuario set nome = ?, sobrenome = ?, telefone = ?, celular = ?, endereco = ?, numero_endereco = ?, complemento = ?, cep = ?, cidade = ?, uf = ? where id = ?";	    
   		
    	super.executeQuery(sql, usuario.getNome(), usuario.getSobreNome(), usuario.getTelefone(), usuario.getCelular(), usuario.getEndereco(), usuario.getEnderecoNumero(), usuario.getComplemento(), usuario.getCep(), usuario.getCidade(), usuario.getUf(), usuario.getId());			
				
			return usuario;
    	    	
    }//fim editarUsuario
    
    public Usuario getUsuarioById(Long idUsuario) throws SQLException, IOException, ClassNotFoundException {

	    String sql = "select telefone, celular, id, imagePath, nome, cpf_cnpj, rg, "
	    		+ "sobrenome, perfil_acesso, cep, endereco, numero_endereco, complemento,"
	    		+ "cidade, email, uf from usuario where usuario.id = ?";
	    ResultSet rs = super.executeResutSet(sql, idUsuario);

	    if(rs.next()){
	        Usuario usuario = new Usuario();
            usuario.setTelefone(rs.getString("telefone"));
            usuario.setCelular(rs.getString("celular"));
            usuario.setId(rs.getLong("id"));
            usuario.setImagePath(rs.getString("imagePath"));
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
            usuario.setEmail(rs.getString("email"));
            usuario.setUf(rs.getString("uf"));
            return usuario;

        }else {
	        return null;
        }
    }

    public void saveImagePerfil(ImagePerfil imagePerfil) throws SQLException, IOException, ClassNotFoundException {
	    String sql = "update usuario set imagePath = ? where id = ?";
	    super.executeQuery(sql, imagePerfil.getDiretorio(), imagePerfil.getUsuario().getId());
    }

    public void deleteImage(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "update usuario set imagePath = ? where id = ?";
        super.executeQuery(sql, "", usuario.getId());
    }



}

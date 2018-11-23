package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Comentario;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ComentarioDAO extends GenericDAO{


    public List<Comentario> listComentariosByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select co.id, co.comentario, co.data_comentario, co.atividade_usuario_usuario_id, us.nome, us.sobrenome, us.perfil_acesso from comentario co join usuario us on us.id = co.atividade_usuario_usuario_id where co.atividade_usuario_atividade_id = ? order by data_comentario";
        ResultSet rs = super.executeResutSet(sql, idAtividade);

        List<Comentario> comentarios = new ArrayList<>();

        while (rs.next()){
            Comentario comentario = new Comentario();
            comentario.setId(rs.getLong("id"));
            comentario.setComentario(rs.getString("comentario"));
            comentario.setDataComentario(rs.getTimestamp("data_comentario"));
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("atividade_usuario_usuario_id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSobreNome(rs.getString("sobrenome"));
            usuario.setPerfilAcesso(rs.getString("perfil_acesso"));
            comentario.setUsuario(usuario);
            comentarios.add(comentario);
        }
        return comentarios;
    }

    public Comentario inserirComentario(Comentario comentario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT comentario(comentario, atividade_usuario_atividade_id, atividade_usuario_usuario_id)" +
                " values(?,?,?)";

        Long id = super.executeQuery(sql, comentario.getComentario(), comentario.getAtividade().getId(), comentario.getUsuario().getId());

        comentario.setId(id);

        return comentario;
    }

    public boolean verificaVinculo(Comentario comentario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from atividade_usuario where atividade_id = ? and usuario_id = ?";

        ResultSet rs = super.executeResutSet(sql, comentario.getAtividade().getId(), comentario.getUsuario().getId());

        if (rs.next()){
            return true;
        }else {
            return false;
        }

    }

    public void vincularGestor(Comentario comentario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "insert atividade_usuario(atividade_id, usuario_id) value(?,?)";
        super.executeQuery(sql, comentario.getAtividade().getId(), comentario.getUsuario().getId());
    }
}

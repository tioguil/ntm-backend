package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Anexo;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class AnexoDAO extends  GenericDAO{

    public List<Anexo> listAnexosByAtividades(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        String sql = "select ax.atividade_usuario_usuario_id ,ax.id, ax.local_armazenamento, ax.data_insercao, ax.tamanho, us.nome, us.sobrenome from anexo ax join usuario us on us.id = ax.atividade_usuario_usuario_id where ax.atividade_usuario_atividade_id = ?";
        ResultSet rs = super.executeResutSet(sql, idAtividade);

        List<Anexo> anexos = new ArrayList<>();
        while (rs.next()){
            Anexo anexo = new Anexo();
            anexo.setId(rs.getLong("id"));
            anexo.setLocalArmazenamento(rs.getString("local_armazenamento"));
            anexo.setTamanho(rs.getString("tamanho"));
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("atividade_usuario_usuario_id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSobreNome(rs.getString("sobrenome"));
            anexo.setUsuario(usuario);
            anexos.add(anexo);
        }

        return anexos;
    }
}

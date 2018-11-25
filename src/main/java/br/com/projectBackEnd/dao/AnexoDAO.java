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

        String sql = "select ax.atividade_usuario_usuario_id ,ax.id, ax.local_armazenamento, ax.nome_arquivo, ax.descricao, ax.data_insercao, ax.tamanho, ax.atividade_usuario_usuario_id, us.nome, us.sobrenome from anexo ax join usuario us on us.id = ax.atividade_usuario_usuario_id where ax.atividade_usuario_atividade_id = ?";
        ResultSet rs = super.executeResutSet(sql, idAtividade);

        List<Anexo> anexos = new ArrayList<>();
        while (rs.next()){
            Anexo anexo = new Anexo();
            anexo.setId(rs.getLong("id"));
            anexo.setLocalArmazenamento(rs.getString("local_armazenamento"));
            anexo.setNomeAquivo(rs.getString("nome_arquivo"));
            anexo.setDescricao(rs.getString("descricao"));
            anexo.setTamanho(rs.getLong("tamanho"));
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("atividade_usuario_usuario_id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setId(rs.getLong("atividade_usuario_usuario_id"));
            usuario.setSobreNome(rs.getString("sobrenome"));
            anexo.setUsuario(usuario);
            anexos.add(anexo);
        }

        return anexos;
    }

    public Anexo salvaNoBanco(Anexo anexo) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT anexo(local_armazenamento, nome_arquivo, descricao, tamanho, atividade_usuario_atividade_id, atividade_usuario_usuario_id) " +
                "values(?,?,?,?,?,?)";
        Long id = super.executeQuery(sql, anexo.getLocalArmazenamento(), anexo.getNomeAquivo(), anexo.getDescricao(), anexo.getTamanho(), anexo.getAtividade().getId(), anexo.getUsuario().getId());
        anexo.setId(id);
        return anexo;
    }

    public boolean verificaVinculo(Usuario usuario, Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from atividade_usuario where atividade_id = ? and usuario_id = ?";

        ResultSet rs = super.executeResutSet(sql, idAtividade,  usuario.getId());

        if (rs.next()){
            return true;
        }else {
            return false;
        }
    }

    public void vincularGestor(Usuario usuario, Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "insert atividade_usuario (atividade_id, usuario_id, status) values(?,?,?)";
        super.executeQuery(sql, idAtividade, usuario.getId(), 1);
    }

    public void deleteAnexo(Anexo anexo) throws SQLException, IOException, ClassNotFoundException {
        String sql = "delete from anexo where atividade_usuario_atividade_id = ? and id = ?";
        super.executeQuery(sql, anexo.getAtividade().getId(), anexo.getId());
    }
}



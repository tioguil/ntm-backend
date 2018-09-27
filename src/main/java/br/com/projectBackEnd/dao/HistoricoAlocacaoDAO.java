package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.HistoricoAlocacao;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HistoricoAlocacaoDAO extends GenericDAO{


    public List<HistoricoAlocacao> listAlocadosByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select DISTINCT atividade_usuario_usuario_id, id, status, data_alteracao, atividade_usuario_atividade_id from historico_alocacao where atividade_usuario_atividade_id = ? and status = 1";

        ResultSet rs = super.executeResutSet(sql, idAtividade);

        List<HistoricoAlocacao> alocacaoList = new ArrayList<>();

        while (rs.next()){
            HistoricoAlocacao alocacao = new HistoricoAlocacao();
            alocacao.setId(rs.getLong("id"));
            alocacao.setDataAlteracao(rs.getTimestamp("data_alteracao"));
            alocacao.setStatus(rs.getInt("status"));
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("atividade_usuario_usuario_id"));
            alocacao.setUsuario(usuario);
            alocacaoList.add(alocacao);
        }

        return alocacaoList;
    }

}

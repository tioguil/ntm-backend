package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Atividade;
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

    public HistoricoAlocacao vincularAnalista(HistoricoAlocacao alocacao) throws SQLException, IOException, ClassNotFoundException {

        String sqlVerifica = "select * from atividade_usuario where atividade_id = ? and usuario_id = ?";
        String insertAlocacao = "INSERT into historico_alocacao(status, atividade_usuario_atividade_id, atividade_usuario_usuario_id) " +
                "values(?,?,?)";
        String atividadeUsuario ="insert into atividade_usuario(atividade_id, usuario_id) values(?,?)";
        String updateAtividadeUsuario ="update atividade_usuario set status = 1 where atividade_id = ? and usuario_id = ?";

        ResultSet rs = super.executeResutSet(sqlVerifica, alocacao.getAtividade().getId(), alocacao.getUsuario().getId());

        if(!rs.next()){
            super.executeQuery(atividadeUsuario, alocacao.getAtividade().getId(), alocacao.getUsuario().getId());
        }else {
            super.executeQuery(updateAtividadeUsuario, alocacao.getAtividade().getId(), alocacao.getUsuario().getId());
        }

        Long id = super.executeQuery(insertAlocacao, 1, alocacao.getAtividade().getId(), alocacao.getUsuario().getId());
        alocacao.setId(id);

        return alocacao;
    }

    public List<HistoricoAlocacao> listarHistoricoVinculo(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        String sql = "select atividade_usuario_usuario_id, id, status, data_alteracao, atividade_usuario_atividade_id from historico_alocacao where atividade_usuario_atividade_id = ?";

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
    
    public Boolean consultaVinculado(HistoricoAlocacao historicoAlocacao) throws SQLException, IOException, ClassNotFoundException {
		String sql= "SELECT * from atividade_usuario where atividade_id = ? and usuario_id = ? and status = 1";
		ResultSet rs = super.executeResutSet(sql, historicoAlocacao.getAtividade().getId(),historicoAlocacao.getUsuario().getId());
		if (rs.next()) {
			return true;
		}else {
			return false;
		}
		
}

	public Boolean consultaConflitoAtividade(Atividade atividade, HistoricoAlocacao alocacao) throws ClassNotFoundException, SQLException, IOException {
		String sql = "select id from atividade atv join atividade_usuario au on atv.id = au.atividade_id where au.usuario_id = ? and atv.status <> 'finalizada' and atv.status <> 'cancelada' and atv.data_criacao <= ? and atv.data_entrega >= ?";
		ResultSet rs = super.executeResutSet(sql, alocacao.getUsuario().getId(), atividade.getDataCriacao(),  atividade.getDataCriacao());
		
		if(rs.next()) {
			return true;
		}else {
			return false;
		}
		
	}
    
    
}

package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.HorarioTrabalho;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class HorarioTrabalhoDao extends GenericDAO{

    public List<HorarioTrabalho> listHorarioTrabalhoByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        String sql = "select ht.id, ht.data_inicio, ht.data_fim, ht.latitude, ht.longitude,ht.atividade_usuario_usuario_id, us.nome, us.sobrenome from horario_trabalho ht join usuario us on us.id = ht.atividade_usuario_usuario_id where atividade_usuario_atividade_id = ?";
        ResultSet rs = super.executeResutSet(sql, idAtividade);

        List<HorarioTrabalho> trabalhoList = new ArrayList<>();

        while (rs.next()){
            HorarioTrabalho horarioTrabalho = new HorarioTrabalho();
            horarioTrabalho.setId(rs.getLong("id"));
            horarioTrabalho.setDataInicio(rs.getTimestamp("data_inicio"));
            horarioTrabalho.setDataFim(rs.getTimestamp("data_fim"));
            horarioTrabalho.setLatitude(rs.getString("latitude"));
            horarioTrabalho.setLongitude(rs.getString("longitude"));
            Usuario usuario = new Usuario();
            usuario.setId(rs.getLong("atividade_usuario_usuario_id"));
            usuario.setNome(rs.getString("nome"));
            usuario.setSobreNome(rs.getString("sobrenome"));
            horarioTrabalho.setUsuario(usuario);
            System.out.println(horarioTrabalho.getDataInicio());
            trabalhoList.add(horarioTrabalho);
        }
        return trabalhoList;
    }
}

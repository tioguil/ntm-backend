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

    public HorarioTrabalho registrarHorarioTrabalho(HorarioTrabalho trabalho) throws SQLException, IOException, ClassNotFoundException {

        String sql = "INSERT into horario_trabalho(latitude, longitude, atividade_usuario_atividade_id, atividade_usuario_usuario_id, data_inicio) " +
                "values(?,?,?,?, now())";

        Long id = super.executeQuery(sql, trabalho.getLatitude(), trabalho.getLongitude(), trabalho.getAtividade().getId(), trabalho.getUsuario().getId());

        trabalho.setId(id);
        return trabalho;
    }

    public void finalizarTrabalho(HorarioTrabalho trabalho) throws SQLException, IOException, ClassNotFoundException {
        String sql = "update horario_trabalho set data_fim = now() where id = ?";
        super.executeQuery(sql, trabalho.getId());
    }

    public List<HorarioTrabalho> listHorarioTrabalho(HorarioTrabalho horarioTrabalho) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from horario_trabalho where atividade_usuario_atividade_id = ? and atividade_usuario_usuario_id = ? order by data_inicio desc";

        ResultSet rs = super.executeResutSet(sql, horarioTrabalho.getAtividade().getId(), horarioTrabalho.getUsuario().getId());

        List<HorarioTrabalho> horarioTrabalhos = new ArrayList<>();
        while(rs.next()){
            HorarioTrabalho trabalho = new HorarioTrabalho();
            trabalho.setId(rs.getLong("id"));
            trabalho.setLatitude(rs.getString("latitude"));
            trabalho.setLongitude(rs.getString("longitude"));
            trabalho.setDataInicio(rs.getTimestamp("data_inicio"));
            trabalho.setDataFim(rs.getTimestamp("data_fim"));
            trabalho.setAtividade(horarioTrabalho.getAtividade());
            trabalho.setUsuario(horarioTrabalho.getUsuario());
            horarioTrabalhos.add(trabalho);
        }

        return horarioTrabalhos;
    }

    public Boolean horarioEmAndamento(HorarioTrabalho trabalho) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select id from horario_trabalho where atividade_usuario_atividade_id = ? and atividade_usuario_usuario_id = ? and data_fim is null";

        ResultSet rs = super.executeResutSet(sql, trabalho.getAtividade().getId(), trabalho.getUsuario().getId());

        if(rs.next()){
            return true;
        }else {
            return false;
        }
    }

    public List<HorarioTrabalho> listHorarioTrabalhoByAtividade2(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from horario_trabalho where atividade_usuario_atividade_id = ?";

        ResultSet rs = super.executeResutSet(sql, idAtividade);

        List<HorarioTrabalho> trabalhos = new ArrayList<>();

        while (rs.next()){
            HorarioTrabalho horarioTrabalho = new HorarioTrabalho();
            horarioTrabalho.setId(rs.getLong("id"));
        }


        return trabalhos;

    }
}

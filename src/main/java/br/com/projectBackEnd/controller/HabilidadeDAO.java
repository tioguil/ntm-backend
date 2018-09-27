package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.dao.GenericDAO;
import br.com.projectBackEnd.model.Habilidade;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class HabilidadeDAO extends GenericDAO {


    public List<Habilidade> listHabilidades(Long idUsuario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select ha.id, ha.nome, hu.descricao, hu.nivel from habilidade ha join habilidade_usuario hu on hu.habilidade_id = ha.id where hu.usuario_id = ?";

        ResultSet rs = super.executeResutSet(sql, idUsuario);

        List<Habilidade> habilidadeList = new ArrayList<>();
        while (rs.next()){
            Habilidade habilidade = new Habilidade();
            habilidade.setId(rs.getLong("id"));
            habilidade.setNome(rs.getString("nome"));
            habilidade.setDescricao(rs.getString("descricao"));
            habilidade.setNivel(rs.getString("nivel"));
        }

        return habilidadeList;
    }
}

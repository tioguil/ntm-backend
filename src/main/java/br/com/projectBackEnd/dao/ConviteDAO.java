package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Convite;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class ConviteDAO extends GenericDAO{


    public Convite getConviteById(Long id) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from convite where id = ?";

        ResultSet rs = super.executeResutSet(sql, id);

        if(rs.next()){
            Convite convite = new Convite();
            convite.setId(id);
            convite.setEmail(rs.getString("email"));
            convite.setNivelAcesso(rs.getString("nivel_acesso"));
            convite.setUsado(rs.getBoolean("usado"));
            convite.setUsuario(new Usuario(rs.getLong("id_usuario")));
            convite.setCargo(rs.getLong("cargo"));
            return convite;
        }else {
            return null;
        }
    }

    public Convite convidarAnalista(Convite convite) throws SQLException, IOException, ClassNotFoundException {
        String sql = "insert convite(email, nivel_acesso, id_usuario, cargo) values(?,?,?,?)";

        Long id = super.executeQuery(sql, convite.getEmail(), convite.getNivelAcesso(), convite.getUsuario().getId(), convite.getCargo());

        Convite convit = getConviteById(id);
        convit.setUsuario(convite.getUsuario());
        return convit;
    }

    public void atualizaConviteUsado(Convite convite) throws SQLException, IOException, ClassNotFoundException {
        String sql = "UPDATE convite SET usado = '1' WHERE id = ?";

        super.executeQuery(sql, convite.getId());

    }
}

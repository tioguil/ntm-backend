package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Cargo;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class CargoDAO extends GenericDAO {


    public List<Cargo> pesquisaCargo(String search) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from cargo where cargo like ? limit 7";
        search = "%"+ search+"%";
        ResultSet rs = super.executeResutSet(sql, search);
        List<Cargo> cargos = new ArrayList<>();
        while (rs.next()){
            Cargo cargo = new Cargo();

            cargo.setCargo(rs.getString("cargo"));
            cargo.setDescricao(rs.getString("descricao"));
            cargo.setId(rs.getLong("id"));
            cargos.add(cargo);
        }

        return cargos;
    }
}

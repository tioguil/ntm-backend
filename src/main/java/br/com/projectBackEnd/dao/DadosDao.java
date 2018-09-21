package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Dados;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class DadosDao extends GenericDAO{


    public Dados cadastrar(Dados dados) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into dados(tipo, valor, cliente_id)values(?,?,?)";
        Long id = super.executeQuery(sql, dados.getTipo(), dados.getValor(), dados.getClienteId());
        dados.setId(id);
        return dados;
    }
}

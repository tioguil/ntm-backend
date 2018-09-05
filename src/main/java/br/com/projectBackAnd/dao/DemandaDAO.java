package br.com.projectBackAnd.dao;

import br.com.projectBackAnd.model.Demanda;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class DemandaDAO extends GenericDAO{


    public Long cadastrar(Demanda demanda) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into demanda(nome, descricao,complexidade, cep, endereco, endereco_numero, complemento, cidade, uf ,projeto_id)values(?,?,?,?,?,?,?,?,?,?)";

        Long id = super.executeQuery(sql, demanda.getNome(), demanda.getDescricao(), demanda.getComplexidade(), demanda.getCep(),
                demanda.getEndereco(), demanda.getEnderecoNumero(), demanda.getComplemento(),demanda.getCidade(), demanda.getUf(),
                demanda.getProjeto().getId());

        return id;
    }
}

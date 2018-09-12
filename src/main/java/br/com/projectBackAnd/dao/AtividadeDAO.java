package br.com.projectBackAnd.dao;

import br.com.projectBackAnd.model.Atividade;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class AtividadeDAO extends GenericDAO{

    public Long cadastrar(Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into atividade(nome, descricao,complexidade, cep, endereco, endereco_numero, complemento, cidade, uf ,projeto_id)values(?,?,?,?,?,?,?,?,?,?)";

        Long id = super.executeQuery(sql, atividade.getNome(), atividade.getDescricao(), atividade.getComplexidade(), atividade.getCep(),
                atividade.getEndereco(), atividade.getEnderecoNumero(), atividade.getComplemento(),atividade.getCidade(), atividade.getUf(),
                atividade.getProjeto().getId());

        return id;
    }
}

package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.Projeto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class AtividadeDAO extends GenericDAO{

    public Long cadastrar(Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into atividade(nome, descricao,complexidade, cep, endereco, endereco_numero, complemento, cidade, uf ,projeto_id)values(?,?,?,?,?,?,?,?,?,?)";

        Long id = super.executeQuery(sql, atividade.getNome(), atividade.getDescricao(), atividade.getComplexidade(), atividade.getCep(),
                atividade.getEndereco(), atividade.getEnderecoNumero(), atividade.getComplemento(),atividade.getCidade(), atividade.getUf(),
                atividade.getProjeto().getId());

        return id;

    }

    public List<Atividade> listarAtividade (long idProject , Projeto projeto) throws SQLException, IOException, ClassNotFoundException{

        String sql = "select nome, descricao, complexidade, data_criacao, endereco from atividade where projeto_id = ?";

        List<Atividade> atividades = new ArrayList<>();
        ResultSet rs = super.executeResutSet(sql, idProject);
        Atividade atividade = new Atividade();
        projeto = new Projeto();

        while (rs.next()){
            atividade.setNome(rs.getString("nome"));
            atividade.setDescricao(rs.getString("descricao"));
            atividade.setComplexidade(rs.getInt("complexidade"));
            atividade.setDataCriacao(rs.getTimestamp("data_criacao"));
            atividade.setEndereco(rs.getString("endereco"));


            atividades.add(atividade);
        }


        return atividades;

    }

}

package br.com.projectBackAnd.dao;

import br.com.projectBackAnd.model.Projeto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjetoDAO extends GenericDAO{


    public Long cadastrar(Projeto projeto) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into projeto(nome, descricao, inicio, fim, status, cliente_id, usuario_id) values(?,?,?,?,?,?,?)";

        Long id = super.executeQuery(sql,projeto.getNome(), projeto.getDescricao(), projeto.getInicio("yyyy-MM-dd"),
                projeto.getFim("yyyy-MM-dd"), projeto.getStatus(), projeto.getCliente().getId(),
                projeto.getUsuario().getId());

        return id;
    }

    public List<Projeto> listar() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from projeto";

        ResultSet rs = super.executeResutSet(sql);
        List<Projeto> projetos = new ArrayList<>();
        while (rs.next()){
            Projeto projeto = new Projeto();
            projeto.setId(rs.getLong("id"));
            projetos.add(projeto);

        }
        return projetos;
    }


}

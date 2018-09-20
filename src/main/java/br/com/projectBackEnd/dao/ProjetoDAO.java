package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Cliente;
import br.com.projectBackEnd.model.Projeto;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Component
public class ProjetoDAO extends GenericDAO{


    public Long cadastrar(Projeto projeto) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into projeto(nome,numero_projeto, descricao, inicio, fim, status, cliente_id, usuario_id) values(?,?,?,?,?,?,?,?)";

        Long id = super.executeQuery(sql,projeto.getNome(), projeto.getNumeroProjeto() ,projeto.getDescricao(), projeto.getInicio("yyyy-MM-dd"),
                projeto.getFim("yyyy-MM-dd"), projeto.getStatus(), projeto.getCliente().getId(),
                projeto.getUsuario().getId());

        return id;
    }

    public List<Projeto> listar() throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from projeto order by id desc limit 500";

        ResultSet rs = super.executeResutSet(sql);
        List<Projeto> projetos = new ArrayList<>();
        while (rs.next()){
            Projeto projeto = new Projeto();
            projeto.setId(rs.getLong("id"));
            projeto.setNumeroProjeto(rs.getString("numero_projeto"));
            projeto.setNome(rs.getString("nome"));
            projeto.setDescricao(rs.getString("descricao"));
            projeto.setEstimativaEsforco(rs.getInt("estimativa_esforco"));
            projeto.setInicio(rs.getTimestamp("inicio"));
            projeto.setFim(rs.getTimestamp("fim"));

            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("cliente_id"));
            projeto.setCliente(cliente);

//            Usuario usuario = new Usuario();
//            usuario.setId(rs.getLong("usuario_id"));
//            projeto.setUsuario(usuario);
            projetos.add(projeto);
        }
        return projetos;
    }
}

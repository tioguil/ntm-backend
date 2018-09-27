package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Cliente;
import br.com.projectBackEnd.model.Projeto;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ProjetoDAO extends GenericDAO{


    public Long cadastrar(Projeto projeto) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into projeto(nome,numero_projeto, descricao, inicio, fim, status, cliente_id, usuario_id) values(?,?,?,?,?,?,?,?)";

        Long id = super.executeQuery(sql,projeto.getNome(), projeto.getNumeroProjeto() ,projeto.getDescricao(), projeto.getInicio("yyyy-MM-dd"),
                projeto.getFim("yyyy-MM-dd"), projeto.getStatus(), projeto.getCliente().getId(),
                projeto.getUsuario().getId());

        return id;
    }

    public List<Projeto> listar(String search) throws SQLException, IOException, ClassNotFoundException {
        search = "%" + search + "%";
        String sql = "select pr.id, pr.nome, pr.numero_projeto, pr.descricao, pr.estimativa_esforco, pr.fim," +
                " pr.inicio, pr.status, pr.cliente_id, cl.nome 'nomeCliente', cl.cpf_cnpj 'cpf_cnpjCliente', cl.telefone 'telefoneCliente', cl.email 'emailCliente', cl.observacao 'observacaoCliente' from projeto pr " +
                "left join cliente cl on cl.id = pr.cliente_id where numero_projeto like ? " +
                "or pr.nome like ? or cl.nome like ? or cl.cpf_cnpj like ? order by pr.id desc limit 20";

        ResultSet rs = super.executeResutSet(sql, search, search, search, search);
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
            projeto.setStatus(rs.getString("status"));


            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("cliente_id"));
            cliente.setNome(rs.getString("nomeCliente"));
            cliente.setCpfCnpj(rs.getString("cpf_cnpjCliente"));
            cliente.setTelefone(rs.getString("telefoneCliente"));
            cliente.setObservacao(rs.getString("observacaoCliente"));
            cliente.setEmail(rs.getString("emailCliente"));
            projeto.setCliente(cliente);

            projetos.add(projeto);
        }
        return projetos;
    }

    public Projeto buscaProjetoById(Long idPorjeto) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select pr.id, pr.nome, pr.numero_projeto, pr.descricao, pr.estimativa_esforco, pr.fim," +
                " pr.inicio, pr.status, pr.cliente_id, cl.nome 'nomeCliente', cl.cpf_cnpj 'cpf_cnpjCliente', cl.telefone 'telefoneCliente', cl.email 'emailCliente', cl.observacao 'observacaoCliente' from projeto pr " +
                "left join cliente cl on cl.id = pr.cliente_id where pr.id = ?";
        ResultSet rs = super.executeResutSet(sql, idPorjeto);

        if(rs.next()){
            Projeto projeto = new Projeto();
            projeto.setId(rs.getLong("id"));
            projeto.setNumeroProjeto(rs.getString("numero_projeto"));
            projeto.setNome(rs.getString("nome"));
            projeto.setDescricao(rs.getString("descricao"));
            projeto.setEstimativaEsforco(rs.getInt("estimativa_esforco"));
            projeto.setInicio(rs.getTimestamp("inicio"));
            projeto.setFim(rs.getTimestamp("fim"));
            projeto.setStatus(rs.getString("status"));

            Cliente cliente = new Cliente();
            cliente.setId(rs.getLong("cliente_id"));
            cliente.setNome(rs.getString("nomeCliente"));
            cliente.setCpfCnpj(rs.getString("cpf_cnpjCliente"));
            cliente.setTelefone(rs.getString("telefoneCliente"));
            cliente.setObservacao(rs.getString("observacaoCliente"));
            cliente.setEmail(rs.getString("emailCliente"));
            projeto.setCliente(cliente);

            return projeto;
        }else {
            return null;
        }
    }
}

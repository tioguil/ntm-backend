package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Cliente;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.sql.SQLException;

@Component
public class ClienteDao extends GenericDAO{

    public Cliente cadastrar(Cliente cliente) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into cliente(nome, cnpjcpf, segmento)values(?,?,?)";
        Long id = super.executeQuery(sql, cliente.getNome(), cliente.getCpfCnpj() ,cliente.getObservacao());
        cliente.setId(id);
        return cliente;
    }
}

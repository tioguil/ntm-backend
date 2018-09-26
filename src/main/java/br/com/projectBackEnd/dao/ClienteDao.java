package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Cliente;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Repository
public class ClienteDao extends GenericDAO{

    public Cliente cadastrar(Cliente cliente) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into cliente(nome, cnpjcpf, segmento)values(?,?,?)";
        Long id = super.executeQuery(sql, cliente.getNome(), cliente.getCpfCnpj() ,cliente.getObservacao());
        cliente.setId(id);
        return cliente;
    }

	public List<Cliente> pesquisarClientes(String search) throws ClassNotFoundException, SQLException, IOException {
		String sql = "select * from cliente where nome like ? or cpf_cnpj like ?";
		search = "%" + search + "%" ;
		ResultSet rs = super.executeResutSet(sql, search,search);
		List<Cliente> clientes = new ArrayList<>();
		while(rs.next()) {
			Cliente cliente = new Cliente();
			cliente.setNome(rs.getString("nome"));
			cliente.setId(rs.getLong("id"));
			cliente.setCpfCnpj(rs.getString("cpf_cnpj"));
			clientes.add(cliente);	
		}
		return clientes;
	}
}

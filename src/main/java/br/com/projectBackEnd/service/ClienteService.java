package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.ClienteDao;
import br.com.projectBackEnd.model.Cliente;
import br.com.projectBackEnd.model.Dados;
import br.com.projectBackEnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.ResponseBody;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Service
public class ClienteService {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private ClienteDao clienteDao;
    @Autowired
    private DadosService dadosService;

    /**
     *
     * @param cliente
     * @return Cliente Cadastro
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ResponseMessage cadastrar(Cliente cliente) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response;
        response = responseMessage;

        //salva Cliente no banco
        cliente = clienteDao.cadastrar(cliente);


        //Cliando Response de sucesso!
        response.setMessage("Cliente cadastrado com sucesso!");
        response.setStatusCode("201");
        response.setResponse(cliente);

        return response;
    }

	public ResponseMessage pesquisarClientes(String search) throws ClassNotFoundException, SQLException, IOException {
		List<Cliente> clientes = clienteDao.pesquisarClientes(search);
		ResponseMessage response;
        response = responseMessage;

		if (clientes.size() > 0 ) {
			//Response de sucesso!
	        response.setMessage("Cliente encontrado!");
	        response.setStatusCode("201");
	        response.setResponse(clientes);
		}
		else {
			response.setMessage("Cliente Não encontrado!");
	        response.setStatusCode("400");
	        response.setResponse(clientes);
		}
		return response;
	}
}

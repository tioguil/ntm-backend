package br.com.projectBackAnd.service;

import br.com.projectBackAnd.dao.ClienteDao;
import br.com.projectBackAnd.model.Cliente;
import br.com.projectBackAnd.model.Dados;
import br.com.projectBackAnd.model.ResponseMessage;
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
}

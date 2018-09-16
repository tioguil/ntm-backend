package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.ProjetoDAO;
import br.com.projectBackEnd.model.Projeto;
import br.com.projectBackEnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Service
public class ProjetoService {


    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private ProjetoDAO projetoDAO;

    public ResponseMessage cadastrar(Projeto projeto) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;


        projeto.setId(projetoDAO.cadastrar(projeto));
        response.setMessage("Projeto cadastrado com sucesso!");
        response.setStatusCode("201");
        response.setResponse(projeto);


        return response;
    }

    public ResponseMessage listar() throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Projeto> listaProjeto = projetoDAO.listar();
        response.setMessage("Total de Registros: " + listaProjeto.size());
        response.setStatusCode("200");
        response.setResponse(listaProjeto);

        return response;
    }
}
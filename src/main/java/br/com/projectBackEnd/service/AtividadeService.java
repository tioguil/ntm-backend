package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.AtividadeDAO;
import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class AtividadeService {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private AtividadeDAO atividadeDAO;


    public ResponseMessage cadastrar(Atividade demanda) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;


        demanda.setId(atividadeDAO.cadastrar(demanda));
        response.setMessage("Atividade cadastrada com sucesso!");
        response.setStatusCode("201");
        response.setResponse(demanda);

        return response;
    }
}
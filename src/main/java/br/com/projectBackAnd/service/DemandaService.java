package br.com.projectBackAnd.service;

import br.com.projectBackAnd.dao.DemandaDAO;
import br.com.projectBackAnd.model.Demanda;
import br.com.projectBackAnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;

@Service
public class DemandaService {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private DemandaDAO demandaDAO;


    public ResponseMessage cadastrar(Demanda demanda, Long idUser) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;



        demanda.setId(demandaDAO.cadastrar(demanda));
        response.setMessage("Demanda cadastrada com sucesso!");
        response.setStatusCode("201");
        response.setResponse(demanda);





        return response;
    }
}

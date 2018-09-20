package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.CargoDAO;
import br.com.projectBackEnd.model.Cargo;
import br.com.projectBackEnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class CargoService {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private CargoDAO cargoDAO;

    public ResponseMessage pesquisaCargo(String search) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Cargo> cargos = cargoDAO.pesquisaCargo(search);

        if(cargos.size() > 0){
            response.setStatusCode("200");
            response.setMessage("Cargos para o parametro " + search);
            response.setResponse(cargos);
        }else{
            response.setResponse(cargos);
            response.setMessage("Nenhum cargo encontrado com o parametro "+ search);
            response.setStatusCode("400");
        }

        return response;
    }
}

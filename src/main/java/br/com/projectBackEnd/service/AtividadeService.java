package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.AtividadeDAO;
import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

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

    public ResponseMessage listaAtividadeByAnalista(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Atividade> list = atividadeDAO.listaAtividadeByAnalista(usuario.getId());

        if(list.size() > 0){

            usuario.setAtividades(list);
            response.setResponse(usuario);
            response.setMessage("Total de atividade " + list.size());
            response.setStatusCode("200");
        }else {

            response.setResponse(usuario);
            response.setMessage("Nenhuma atividade cadastrada");
            response.setStatusCode("200");
        }

        return response;

    }
}

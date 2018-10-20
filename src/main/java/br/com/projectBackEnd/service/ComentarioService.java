package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.ComentarioDAO;
import br.com.projectBackEnd.model.Comentario;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioDAO comentarioDAO;
    @Autowired
    private ResponseMessage responseMessage;

    public List<Comentario> listComentariosByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<Comentario> comentarios = comentarioDAO.listComentariosByAtividade(idAtividade);

        return comentarios;
    }

    public ResponseMessage inserirComentario(Comentario comentario) throws SQLException, IOException, ClassNotFoundException {

        if(comentario.getUsuario().getPerfilAcesso().equals("gestor")){
            verificaVinculo(comentario);
        }

        ResponseMessage response = responseMessage;

        comentario = comentarioDAO.inserirComentario(comentario);

        response.setResponse(comentario);
        response.setMessage("Comentario inserido com sucesso!");
        response.setStatusCode("200");

       return response;
    }

    public ResponseMessage listaComentarioByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Comentario> comentarios = listComentariosByAtividade(idAtividade);

        response.setResponse(comentarios);
        response.setMessage("Total de comentarios encontrados " + comentarios.size());
        response.setStatusCode("200");

       return response;

    }

    private void verificaVinculo(Comentario comentario) throws SQLException, IOException, ClassNotFoundException {

        if(!comentarioDAO.verificaVinculo(comentario)){
            comentarioDAO.vincularGestor(comentario);
        }
    }
}

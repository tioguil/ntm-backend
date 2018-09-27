package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.ComentarioDAO;
import br.com.projectBackEnd.model.Comentario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class ComentarioService {

    @Autowired
    private ComentarioDAO comentarioDAO;

    public List<Comentario> listComentariosByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<Comentario> comentarios = comentarioDAO.listComentariosByAtividade(idAtividade);

        return comentarios;
    }
}

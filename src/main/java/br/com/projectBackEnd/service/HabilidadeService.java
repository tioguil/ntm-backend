package br.com.projectBackEnd.service;

import br.com.projectBackEnd.controller.HabilidadeDAO;
import br.com.projectBackEnd.model.Habilidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class HabilidadeService {

    @Autowired
    private HabilidadeDAO habilidadeDAO;

    public List<Habilidade> listHabilidades(Long idUsuario) throws SQLException, IOException, ClassNotFoundException {

        List<Habilidade> habilidadeList = habilidadeDAO.listHabilidades(idUsuario);

        return habilidadeList;
    }
}

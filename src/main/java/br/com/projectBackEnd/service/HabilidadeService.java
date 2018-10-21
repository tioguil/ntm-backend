package br.com.projectBackEnd.service;
import br.com.projectBackEnd.dao.HabilidadeDAO;
import br.com.projectBackEnd.model.Habilidade;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import br.com.projectBackEnd.dao.HabilidadeDAO;
import br.com.projectBackEnd.model.Habilidade;

@Service
public class HabilidadeService {

    @Autowired
    private HabilidadeDAO habilidadeDAO;

    public List<Habilidade> listHabilidadesByUsuario(Long idUsuario) throws SQLException, IOException, ClassNotFoundException {


        List<Habilidade> habilidadeList = habilidadeDAO.listHabilidades(idUsuario);

        return habilidadeList;
    }
}

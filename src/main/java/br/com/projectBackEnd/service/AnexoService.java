package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.AnexoDAO;
import br.com.projectBackEnd.model.Anexo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class AnexoService {

    @Autowired
    private AnexoDAO anexoDAO;

    public List<Anexo> listAnexosByAtividades(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<Anexo> anexos = anexoDAO.listAnexosByAtividades(idAtividade);

        return anexos;
    }
}

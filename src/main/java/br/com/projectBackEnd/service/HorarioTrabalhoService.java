package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.HorarioTrabalhoDao;
import br.com.projectBackEnd.model.HorarioTrabalho;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class HorarioTrabalhoService {

    @Autowired
    private HorarioTrabalhoDao horarioTrabalhoDao;

    public List<HorarioTrabalho> listHorarioTrabalhoByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<HorarioTrabalho> trabalhoList = horarioTrabalhoDao.listHorarioTrabalhoByAtividade(idAtividade);

        return trabalhoList;

    }
}

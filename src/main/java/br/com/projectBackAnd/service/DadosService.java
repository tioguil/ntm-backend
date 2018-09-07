package br.com.projectBackAnd.service;

import br.com.projectBackAnd.dao.DadosDao;
import br.com.projectBackAnd.model.Dados;
import br.com.projectBackAnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Service
public class DadosService {

    @Autowired
    private DadosDao dadosDao;

    public ResponseMessage cadastrar(Dados dados) {

        return null;
    }


    /**
     *
     * @param dados
     * @return Lista de Dados inseridos
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public List<Dados> inserir(List<Dados> dados, Long idCliente) throws SQLException, IOException, ClassNotFoundException {
        List<Dados> novaDados = new ArrayList<>();
        for(Dados dado: dados) {
            dado.setClienteId(idCliente);
            novaDados.add(dadosDao.cadastrar(dado));
        }

        return novaDados;
    }
}

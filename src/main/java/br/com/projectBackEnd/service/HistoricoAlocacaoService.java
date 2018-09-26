package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.HistoricoAlocacaoDAO;
import br.com.projectBackEnd.dao.UsuarioDAO;
import br.com.projectBackEnd.model.HistoricoAlocacao;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class HistoricoAlocacaoService {

    @Autowired
    private HistoricoAlocacaoDAO historicoAlocacaoDAO;
    @Autowired
    private UsuarioService usuarioService;

    public List<HistoricoAlocacao> listaHistorico (Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<HistoricoAlocacao> alocacaoList = historicoAlocacaoDAO.listaHistorico(idAtividade);

        for (int i =0; i < alocacaoList.size(); i++){

            Usuario usuario = usuarioService.getUsuarioID(alocacaoList.get(i).getUsuario().getId());

            alocacaoList.get(i).setUsuario(usuario);
        }

        return alocacaoList;
    }
}

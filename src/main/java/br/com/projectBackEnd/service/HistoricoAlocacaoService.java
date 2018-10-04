package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.HistoricoAlocacaoDAO;
import br.com.projectBackEnd.model.HistoricoAlocacao;
import br.com.projectBackEnd.model.ResponseMessage;
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
    @Autowired
    private ResponseMessage responseMessage;

    public List<HistoricoAlocacao> listAlocadosByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<HistoricoAlocacao> alocacaoList = historicoAlocacaoDAO.listAlocadosByAtividade(idAtividade);

        for (int i =0; i < alocacaoList.size(); i++){

            Usuario usuario = usuarioService.getUsuarioID(alocacaoList.get(i).getUsuario().getId());

            alocacaoList.get(i).setUsuario(usuario);
        }

        return alocacaoList;
    }

    public ResponseMessage vincularAnalista(HistoricoAlocacao alocacao) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

         alocacao = historicoAlocacaoDAO.vincularAnalista(alocacao);

         response.setStatusCode("200");
         response.setMessage("Usuario vinculado com sucesso!");
         response.setResponse(alocacao);

        return response;
    }

    public ResponseMessage listarHistoricoVinculo(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<HistoricoAlocacao> alocacaoList = historicoAlocacaoDAO.listarHistoricoVinculo(idAtividade);

        for (int i =0; i < alocacaoList.size(); i++){

            Usuario usuario = usuarioService.getUsuarioID(alocacaoList.get(i).getUsuario().getId());

            alocacaoList.get(i).setUsuario(usuario);
        }

        return response;
    }
}

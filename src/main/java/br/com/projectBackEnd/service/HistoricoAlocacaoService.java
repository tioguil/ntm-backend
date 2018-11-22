package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.AtividadeDAO;
import br.com.projectBackEnd.dao.HistoricoAlocacaoDAO;
import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.HistoricoAlocacao;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import com.google.common.cache.AbstractLoadingCache;
import org.apache.tomcat.util.buf.HexUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
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
    @Autowired
    private AtividadeDAO atividadeDAO;

    public List<HistoricoAlocacao> listAlocadosByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<HistoricoAlocacao> alocacaoList = historicoAlocacaoDAO.listAlocadosByAtividade(idAtividade);

        for (int i =0; i < alocacaoList.size(); i++){

            Usuario usuario = usuarioService.getUsuarioID(alocacaoList.get(i).getUsuario().getId());

            alocacaoList.get(i).setUsuario(usuario);
        }

        return alocacaoList;
    }

    public ResponseMessage vincularAnalista(HistoricoAlocacao alocacao) throws SQLException, IOException, ClassNotFoundException, MessagingException {
        ResponseMessage response = responseMessage;
        
        if(historicoAlocacaoDAO.consultaVinculado(alocacao)) {
            response.setStatusCode("401");
            response.setMessage("Usuário já vinculado!");
            response.setResponse(null);
            return response;
        }else {

            alocacao = historicoAlocacaoDAO.vincularAnalista(alocacao);
            response.setStatusCode("200");
            response.setMessage("Usuario vinculado com sucesso!");
            response.setResponse(historicoAlocacaoDAO.listarHistoricoVinculo(alocacao.getAtividade().getId()));
	        usuarioService.notificarUsuario(alocacao.getUsuario(), alocacao.getAtividade());
            return response;
        }
        			
        
    }
    
    public ResponseMessage consultaConflito(HistoricoAlocacao alocacao) throws ClassNotFoundException, SQLException, IOException {
    	ResponseMessage response = responseMessage;
    	
    	Atividade atividade = atividadeDAO.detalheAtividade(alocacao.getAtividade().getId());
		
		if(historicoAlocacaoDAO.consultaConflitoAtividade(atividade, alocacao)) {
	         response.setStatusCode("401");
	         response.setMessage("Conflito de atividade");
	         response.setResponse(null);
	         
		}else {
			response.setStatusCode("200");
	         response.setMessage("Nenhum conflito de horario");
	         response.setResponse(null);
		}
		
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


    public ResponseMessage desvincularAanalista (HistoricoAlocacao historicoAlocacao) throws SQLException, IOException, ClassNotFoundException, MessagingException {
        ResponseMessage response = responseMessage;

        List<HistoricoAlocacao> alocacaos = historicoAlocacaoDAO.desvincularAanalista(historicoAlocacao);
        usuarioService.notifiyWhenDesvinculado(historicoAlocacao.getUsuario(), historicoAlocacao.getAtividade(),historicoAlocacao);
        response.setStatusCode("200");
        response.setMessage("Usuario desvinculado com sucesso!");
        response.setResponse(alocacaos);

        return  response;
    }
    
}

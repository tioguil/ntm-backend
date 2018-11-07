package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.AtividadeDAO;
import br.com.projectBackEnd.model.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.Date;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;

@Service
public class AtividadeService {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private AtividadeDAO atividadeDAO;
    @Autowired
    private HistoricoAlocacaoService historicoAlocacaoService;
    @Autowired
    private HorarioTrabalhoService horarioTrabalhoService;
    @Autowired
    private AnexoService anexoService;
    @Autowired
    private ComentarioService comentarioService;


    public ResponseMessage cadastrar(Atividade demanda) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;


        demanda.setId(atividadeDAO.cadastrar(demanda));
        response.setMessage("Atividade cadastrada com sucesso!");
        response.setStatusCode("201");
        response.setResponse(demanda);

        return response;
    }

    public ResponseMessage listaAtividadeByAnalista(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Atividade> list = atividadeDAO.listaAtividadeByAnalista(usuario.getId());

        if(list.size() > 0){

            usuario.setAtividades(list);
            response.setResponse(usuario);
            response.setMessage("Total de atividade " + list.size());
            response.setStatusCode("200");
        }else {

            response.setResponse(usuario);
            response.setMessage("Nenhuma atividade cadastrada");
            response.setStatusCode("200");
        }

        return response;

    }

    public ResponseMessage listarAtividadeByProject(Long idProject) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;
        List<Atividade> atividades = atividadeDAO.listarAtividadebyProject(idProject);


        if(atividades.size() > 0){


            response.setResponse(atividades);
            response.setMessage("Total de atividade " + atividades.size());
            response.setStatusCode("200");
        }else {

            response.setResponse(atividades);
            response.setMessage("Nenhuma atividade cadastrada");
            response.setStatusCode("200");
        }

        return response;

    }

    public ResponseMessage detalheAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        //busca dados Atividade
        Atividade atividade = atividadeDAO.detalheAtividade(idAtividade);

        if(atividade == null){
            response.setResponse(null);
            response.setStatusCode("200");
            response.setMessage("Atividade não localizada");
            return response;
        }

        //Busca analistas alocados a atividade
        List<HistoricoAlocacao> alocacaoList = historicoAlocacaoService.listAlocadosByAtividade(idAtividade);
        atividade.setHistoricoAlocacao(alocacaoList);

        //Busca horarios de trabalho dos analistas
        List<HorarioTrabalho> trabalhoList = horarioTrabalhoService.listHorarioTrabalhoByAtividade(idAtividade);
        atividade.setHorarioTrabalho(trabalhoList);

        //Busca Anexos da Atividade
        List<Anexo> anexos = anexoService.listAnexosByAtividades(idAtividade);
        atividade.setAnexos(anexos);

        //Busca Comentarios
        List<Comentario> comentarios = comentarioService.listComentariosByAtividade(idAtividade);
        atividade.setComentarios(comentarios);

        //montando retorno
        response.setResponse(atividade);
        response.setStatusCode("200");
        response.setMessage("Atividade localizada com sucesso!");

        return response;

    }

    public ResponseMessage alteraStatus(Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;
        
    	atividade = atividadeDAO.alteraStatus(atividade);
        
        response.setResponse(atividade);
        response.setStatusCode("200");
        response.setMessage("Atividade atualizada com sucesso!");

        return response;
    }

    public ResponseMessage finalizarAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        atividade = atividadeDAO.finalizarAtividade(atividade);

        response.setResponse(atividade);
        response.setStatusCode("200");
        response.setMessage("Atividade Finalizada com sucesso!");

        return response;
    }

    public ResponseMessage listarAtividadeByData(Date dt_inicio, Date dt_fim, Long usuario_id) throws SQLException, IOException, ClassNotFoundException{

        ResponseMessage response = responseMessage;

        List<Atividade> atividades = atividadeDAO.listarAtividadeByData(dt_inicio,dt_fim,usuario_id);

        if (atividades.size() > 0){
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Total de atividades " + atividades.size());

        } else {
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Nenhuma atividade encontrada");
        }

        return response;
    }

    public ResponseMessage listarAtividadesByDash(Integer qtdias) throws SQLException, IOException, ClassNotFoundException{

        ResponseMessage response = responseMessage;

        List<Atividade> atividades = atividadeDAO.listarAtividadesByDash(qtdias);

        if (atividades.size() > 0){
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Total de atividades no periodo " + atividades.size());
        } else {
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Nenhuma atividade encontrada no periodo");
        }

        return response;

    }

    public ResponseMessage buscaByStatusData(Long idAnalista, String status, Date dataInicial, Date dataFim) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Atividade> atividades = atividadeDAO.buscaByStatusData(idAnalista, status,dataInicial, dataFim);

        if (atividades.size() > 0){
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Total de atividades no periodo " + atividades.size());
        } else {
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Nenhuma atividade encontrada no periodo");
        }

        return response;
    }


    public ResponseMessage buscaByStatus(Long idAnalista, String status) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Atividade> atividades = atividadeDAO.buscaByStatus(idAnalista, status);

        if (atividades.size() > 0){
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Total de atividades no periodo " + atividades.size());
        } else {
            response.setResponse(atividades);
            response.setStatusCode("200");
            response.setMessage("Nenhuma atividade encontrada no periodo");
        }

        return response;
    }

    public ResponseMessage editarAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException{
        ResponseMessage response = responseMessage;

        atividade = atividadeDAO.editarAtividade(atividade);

        response.setResponse(atividade);
        response.setStatusCode("200");
        response.setMessage("Atividade atualizada com sucesso");

        return response;
    }

}

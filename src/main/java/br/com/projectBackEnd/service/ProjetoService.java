package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.AtividadeDAO;
import br.com.projectBackEnd.dao.ProjetoDAO;
import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.Cliente;
import br.com.projectBackEnd.model.Projeto;
import br.com.projectBackEnd.model.ResponseMessage;
import io.swagger.models.auth.In;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;


@Service
public class ProjetoService {


    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private ProjetoDAO projetoDAO;

    public ResponseMessage cadastrar(Projeto projeto) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;


        projeto.setId(projetoDAO.cadastrar(projeto));
        response.setMessage("Projeto cadastrado com sucesso!");
        response.setStatusCode("201");
        response.setResponse(projeto);


        return response;
    }

    public ResponseMessage listar(String search) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Projeto> listaProjeto = projetoDAO.listar(search);
        response.setResponse(listaProjeto);
        Integer total = listaProjeto.size();

        if(total > 0){
            response.setMessage("Total de Registros: " + total);
            response.setStatusCode("200");
        }else{
            response.setMessage("Nenhum projeto encontrato com o parametro " + search);
            response.setStatusCode("200");
        }

        return response;
    }

    public ResponseMessage buscaProjetoById(Long idPorjeto) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        Projeto projeto = projetoDAO.buscaProjetoById(idPorjeto);

        if(projeto == null){
            response.setMessage("Nenhum projeto encontrado");
            response.setStatusCode("200");
            response.setResponse(null);
            return response;
        }

        List<Atividade> atividades = new AtividadeDAO().listarAtividadebyProject(idPorjeto);

        projeto.setAtividades(atividades);

        response.setMessage("Busca realizada com sucesso!");
        response.setStatusCode("200");
        response.setResponse(projeto);
        return response;

    }
    
    public ResponseMessage alteraStatus(Projeto projeto) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;
        
    	projeto = projetoDAO.alteraStatus(projeto);
        
        response.setResponse(projeto);
        response.setStatusCode("200");
        response.setMessage("Projeto atualizado com sucesso!");

        return response;
    }

    public ResponseMessage listarProjectByDash(Integer qtDias) throws  SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Projeto> projetos = projetoDAO.listarProjectsByDash(qtDias);

        if (projetos.size() > 0) {
            response.setResponse(projetos);
            response.setStatusCode("200");
            response.setMessage("Total de projetos encontrados " + projetos.size());

        } else {
            response.setResponse(projetos);
            response.setMessage("Nenhum projeto encontrado");
            response.setStatusCode("200");
        }

        return  response;
    }

    public ResponseMessage editarProjeto(Projeto projeto) throws  SQLException, IOException, ClassNotFoundException{
        ResponseMessage response = responseMessage;

        projeto = projetoDAO.editarProjeto(projeto);
        response.setMessage("Projeto atualizado com sucesso");
        response.setStatusCode("200");
        response.setResponse(projeto);

        return  response;
    }

}

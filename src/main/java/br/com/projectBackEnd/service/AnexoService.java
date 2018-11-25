package br.com.projectBackEnd.service;

import br.com.projectBackEnd.Utili.Disco;
import br.com.projectBackEnd.dao.AnexoDAO;
import br.com.projectBackEnd.model.Anexo;
import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.List;

@Service
public class AnexoService {

    @Autowired
    private AnexoDAO anexoDAO;
    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private Disco disco;


    public List<Anexo> listAnexosByAtividades(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<Anexo> anexos = anexoDAO.listAnexosByAtividades(idAtividade);

        return anexos;
    }


    public ResponseMessage listAnexos (Long idAtividade) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Anexo> anexos = listAnexosByAtividades(idAtividade);

        response.setResponse(anexos);
        response.setStatusCode("200");
        response.setMessage("Total de anexos encontrados " + anexos.size());

        return response;
    }

    public ResponseMessage uploadAnexo(MultipartFile file, Long idAtividade, Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        if(usuario.getPerfilAcesso().equals("gestor")){
            verificaVinculo(usuario, idAtividade );
        }

        Anexo anexo = new Anexo();
        anexo.setUsuario(usuario);
        Atividade atividade = new Atividade();
        atividade.setId(idAtividade);
        anexo.setAtividade(atividade);
        anexo.setFile(file);
        anexo.setTamanho(file.getSize());
        anexo.setNomeAquivo(file.getOriginalFilename());
        anexo.setDescricao("Padrao");

        //Salvar Arquivo no diretorio
        anexo = disco.salvarAnexo(anexo);
        //Salvar dados no bando
        anexo = anexoDAO.salvaNoBanco(anexo);
        System.out.println(anexo.getLocalArmazenamento());


        response.setMessage("Anexo salvo com sucesso");
        response.setStatusCode("200");
        anexo.setFile(null);
        response.setResponse(anexo);
        return response;

    }

    public Resource loadFileAsResource(String nameFile) {
        return disco.loadFileAsResource(nameFile);
    }

    public ResponseMessage deleteAnexo(Anexo anexo) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        anexoDAO.deleteAnexo(anexo);
        if(disco.deleteAnexo(anexo)){
            response.setMessage("Anexo deletado com sucesso");
            response.setStatusCode("200");
            response.setResponse(null);
        }else {
            response.setMessage("Falha ao deletar Anexo");
            response.setStatusCode("400");
            response.setResponse(null);
        }



        return response;
    }

    private void verificaVinculo(Usuario usuario, Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        if(!anexoDAO.verificaVinculo(usuario, idAtividade)){
            anexoDAO.vincularGestor(usuario, idAtividade);
        }
    }
}

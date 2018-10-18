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

        Anexo anexo = new Anexo();
        anexo.setUsuario(usuario);
        Atividade atividade = new Atividade();
        atividade.setId(idAtividade);
        anexo.setAtividade(atividade);
        anexo.setFile(file);
        anexo.setTamanho(file.getSize());
        anexo.setNomeAquivo(file.getOriginalFilename());
        anexo.setDescriocao("Padrao");

        //Salvar Arquivo
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

    public Resource loadFileAsResource(Long idAnexo) {

        Resource resource = disco.loadFileAsResource("c:/anexo/date1539898713545_User1_Ativ1requisitos_python.txt");

        return resource;
    }
}

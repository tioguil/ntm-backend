package br.com.projectBackEnd.Utili;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;
import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import br.com.projectBackEnd.model.Anexo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.xml.crypto.Data;

@Component
public class Disco {

    @Value("${diretorio.disco}")
    private String diretorioRaiz;


    public Anexo salvarAnexo(Anexo anexo) {

        Date date = new Date();
        String sufixo = "date" + date.getTime() + "_User" + anexo.getUsuario().getId()
                + "_Ativ" + anexo.getAtividade().getId();

        anexo.setLocalArmazenamento(diretorioRaiz+sufixo);
        this.salvar(anexo.getFile(), sufixo);

        return anexo;
    }

    private void salvar(MultipartFile arquivo, String sufixo) {
        Path diretorioPath = Paths.get(diretorioRaiz);
        String nomeArquivo = sufixo + arquivo.getOriginalFilename();
        Path arquivoPath = diretorioPath.resolve(nomeArquivo);

        try {
            Files.createDirectories(diretorioPath);
            arquivo.transferTo(arquivoPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar arquivo.", e);
        }
    }


    public Resource loadFileAsResource(String fileName) {
        try {
            Path diretorioPath = Paths.get(diretorioRaiz);
            Path filePath = diretorioPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }
}
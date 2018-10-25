package br.com.projectBackEnd.model;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;


@Data
public class ImagePerfil {

    private String imagem;
    private String name;
    private String diretorio;
    private Usuario usuario;
    private MultipartFile multipartFile;
}

package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

import java.util.Date;

@Getter
@Setter
public class Anexo {

    private Long id;
    private String localArmazenamento;
    private String nomeAquivo;
    private String descricao;
    private String extencao;
    private Date dataInsercao;
    private Long tamanho;
    private MultipartFile file;
    private Usuario usuario;
    private Atividade atividade;
}

package br.com.projectBackAnd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Anexo {

    private String localArmazenamento;
    private Date dataInsercao;
    private String tamanho;
    private Usuario usuario;
    private Atividade atividade;
}

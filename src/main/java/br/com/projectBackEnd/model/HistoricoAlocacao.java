package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HistoricoAlocacao {

    private Long id;
    private Integer status;
    private Date dataAlteracao;
    private Usuario usuario;
    private Atividade atividade;

}

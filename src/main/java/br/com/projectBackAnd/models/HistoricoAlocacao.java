package br.com.projectBackAnd.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HistoricoAlocacao {

    private Long id;
    private String status;
    private Date dataAlteracao;
    private Usuario usuario;
    private Demanda demanda;

}
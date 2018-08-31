package br.com.projectBackAnd.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Projeto {

    private Long id;
    private String nome;
    private String descricao;
    private Date inicio;
    private Date fim;
    private Integer status;
    private Cliente cliente;
    private Usuario usuario;
    private List<Demanda> demanda;

}

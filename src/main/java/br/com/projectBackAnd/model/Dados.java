package br.com.projectBackAnd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Dados {

    private Long id;
    private String tipo;
    private String valor;
    private Long clienteId;
}


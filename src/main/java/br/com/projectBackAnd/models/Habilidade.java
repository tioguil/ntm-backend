package br.com.projectBackAnd.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Habilidade {

    private Long id;
    private String nome;
    private String nivel;
    private String descricao;
    private Usuario usuario;
}

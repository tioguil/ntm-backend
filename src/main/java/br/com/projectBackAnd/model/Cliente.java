package br.com.projectBackAnd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class Cliente {

    private Long id;
    private String nome;
    private Long cpfCnpj;
    private String seguimento;
    private List<Dados> dados;
}

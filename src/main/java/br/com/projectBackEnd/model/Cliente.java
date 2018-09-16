package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cliente {

    private Long id;
    private String nome;
    private Long cpfCnpj;
    private String telefone;
    private String email;
    private String observacao;
}

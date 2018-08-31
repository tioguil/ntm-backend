package br.com.projectBackAnd.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Cliente {

    private Long id;
    private String nome;
    private String cpfCnpj;
    private String seguimento;
    private Dados dados;
}

package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Habilidade {

    private Long id;
    private String nome;
    private String descricao;
    private String nivel;

    public Habilidade(){

    }

    public Habilidade(String nome){
        this.nome = nome;
    }
}

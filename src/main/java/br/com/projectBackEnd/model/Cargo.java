package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class Cargo {

    private Long id;
    private String cargo;
    private String descricao;

    public Cargo(Long idCargo){
        this.id = idCargo;
    }

    public Cargo(){

    }
}

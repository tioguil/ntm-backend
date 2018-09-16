package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UsuarioHasHabilidade {

    private Usuario usuario;
    private Habilidade habilidade;
    private String descricao;
    private String nivel;
}

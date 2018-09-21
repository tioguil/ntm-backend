package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Comentario {

    private Long id;
    private String comentario;
    private Date dataComentario;
    private Usuario usuario;
    private Atividade Atividade;
}

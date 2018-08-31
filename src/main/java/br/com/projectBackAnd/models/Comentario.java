package br.com.projectBackAnd.models;

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
    private Demanda demanda;
}

package br.com.projectBackAnd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Horario {

    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private String latitude;
    private String longitude;
    private Usuario usuario;
    private Demanda demanda;

}

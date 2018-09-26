package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HorarioTrabalho {

    private Long id;
    private Date dataInicio;
    private Date dataFim;
    private String latitude;
    private String longitude;
    private Usuario usuario;
    private Atividade Atividade;

}

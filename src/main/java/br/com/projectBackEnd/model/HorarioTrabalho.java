package br.com.projectBackEnd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class HorarioTrabalho {

    private Long id;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date dataInicio;
    @JsonFormat(pattern="dd/MM/yyyy HH:mm:ss")
    private Date dataFim;
    private String totalHoras = "";
    private String latitude;
    private String longitude;
    private Usuario usuario;
    private Atividade Atividade;

}

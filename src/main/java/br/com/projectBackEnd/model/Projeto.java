package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Projeto {

    private Long id;
    private Integer numeroProjeto;

    private String nome;
    private String descricao;
    private Integer estimativaEsforco;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date inicio;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date fim;
    private Integer status;
    private Cliente cliente;
    private Usuario usuario;
    private List<Atividade> demanda;



    /**
     Recebe formato e retorna data no formato String já formadata
     */
    public String getInicio(String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(this.inicio);
    }

    /**
     Recebe formato e retorna data no formato String já formadata
     */
    public String getFim(String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(this.fim);
    }

}
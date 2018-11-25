package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class Convite {

    private Long id;
    private String email;
    private String nivelAcesso;
    private Long cargo;
    private Boolean usado;
    private Date dataConvite;
    private Usuario usuario;
}

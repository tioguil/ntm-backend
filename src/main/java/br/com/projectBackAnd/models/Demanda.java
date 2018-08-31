package br.com.projectBackAnd.models;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Demanda {

    private Long id;
    private String nome;
    private String descricao;
    private Integer complexidade;
    private Date dataCriacao;
    private String cep;
    private String endereco;
    private String enderecoNumero;
    private String complemento;
    private String cidade;
    private String uf;
    private Projeto projeto;
    private List<HistoricoAlocacao> historicoAlocacao;
    private List<Horario> horario;
    private List<Anexo> anexos;
    private List<Comentario> comentarios;
}
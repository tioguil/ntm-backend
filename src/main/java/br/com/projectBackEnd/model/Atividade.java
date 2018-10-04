package br.com.projectBackEnd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;
import java.util.List;

@Getter
@Setter
public class Atividade {

    private Long id;
    private String nome;
    private String descricao;
    private Projeto projeto;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataEntrega;
    @JsonFormat(pattern="dd/MM/yyyy")
    private Date dataCriacao;
    private Integer complexidade;
    private String cep;
    private String endereco;
    private String enderecoNumero;
    private String complemento;
    private String cidade;
    private String uf;
    private String status;
    private List<HistoricoAlocacao> historicoAlocacao;
    private List<HorarioTrabalho> horarioTrabalho;
    private List<Anexo> anexos;
    private List<Comentario> comentarios;
    //private List<AtividadeHasHabilidade> DescricaoHabilidade;
}

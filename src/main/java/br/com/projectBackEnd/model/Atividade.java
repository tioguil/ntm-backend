package br.com.projectBackEnd.model;

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
    private Integer complexidade;
    private Date dataCriacao;
    private Date dataEntrega;
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

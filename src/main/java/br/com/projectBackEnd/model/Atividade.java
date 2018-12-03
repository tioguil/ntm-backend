package br.com.projectBackEnd.model;

import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Getter;
import lombok.Setter;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

import org.springframework.format.annotation.DateTimeFormat;

@Getter
@Setter
public class Atividade {

    private Long id;
    private String nome;
    private String descricao;
    private Projeto projeto;
    @DateTimeFormat(pattern="yyyy-MM-dd")
    private Date dataEntrega;
    private Date dataCriacao;
    private Integer complexidade;
    private Integer qtd;
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

    /**
     Recebe formato e retorna data no formato String já formadata
     */
    public String getDataCriacao(String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(this.dataCriacao);
    }

    /**
     Recebe formato e retorna data no formato String já formadata
     */
    public String getDataEntrega(String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(this.dataEntrega);
    }
}

package br.com.projectBackAnd.model;

import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.format.annotation.DateTimeFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Getter
@Setter
@ToString
public class Usuario {

	private Long id;
	private String email;
	private String senha;
	private String nome;
	private String sobreNome;
	private String cpfCnpj;
	private String rg;
	private String perfilAcesso;
	private String telefone;
	private String celular;
	private String observacao;
	private String cep;
	private String endereco;
	private String enderecoNumero;
	private String complemento;
	private String cidade;
	private String uf;
	private Cargo cargo;
	private Token token;
	private List<UsuarioHasHabilidade> usuarioHasHabilidade;
	private List<HistoricoAlocacao> historicoAlocacao;
	private List<Horario> horario;
	private List<Anexo> anexos;
	private List<Comentario> comentarios;
	private List<Atividade> atividades;

	public Usuario(){

	}
	public Usuario(Long id){
		this.id = id;
	}

}

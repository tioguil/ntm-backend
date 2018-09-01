package br.com.projectBackAnd.model;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

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
	private Integer perfilAcesso;
	private Date nascimento;
	private String cep;
	private String endereco;
	private Integer numero;
	private String complemento;
	private String cidade;
	private String uf;
	private Token token;
	private List<Habilidade> habilidades;
	private List<HistoricoAlocacao> historicoAlocacao;
	private List<Horario> horario;
	private List<Anexo> anexos;
	private List<Comentario> comentarios;
	private List<Demanda> demanda;


	/**
		Recebe formato e retorna data no formato String já formadata
	 */
	public String getDateFormater(String formato) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		return dateFormat.format(this.nascimento);
	}

	/**
	 Recebe formato e data em String
	 */
	public void setDataGeracao(String formato, String data) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
		this.nascimento = dateFormat.parse(data);
	}

}

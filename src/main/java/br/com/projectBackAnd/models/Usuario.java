package br.com.projectBackAnd.models;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter
@Setter
@ToString
public class Usuario {

	private Long id;
	private String nome;
	private String sobreNome;
	private String cpf;
	private String cidade;
	private String uf;
	private String cep;
	private String email;
	private String senha;
}

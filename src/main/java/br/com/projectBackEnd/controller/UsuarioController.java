package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.sql.SQLException;



/**
 * Nova forma de autenticação
 * para logar deve enviar credenciais no Body através da Rota http://localhost:8080/login
 * sera devolvido response com usuário e token
 */


@Api(value="API REST Usuarios")
@RequestMapping("/usuario")
@RestController
public class UsuarioController {

	@Autowired
	private ResponseMessage responseMessage;
	@Autowired
	private UsuarioService usuarioService;

	@ApiOperation(value="Cadastra Analista no sistema")
	@PostMapping("/gestor/cadastrar")
	public ResponseEntity<ResponseMessage> inserir(@RequestBody Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
		ResponseMessage response = responseMessage;

		try {
			usuario.setPerfilAcesso("analista");
			response = usuarioService.cadastrar(usuario);
		} catch (Exception e) {
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation(value="Cadastra gestor no sistema")
	@PostMapping("/adm/cadastrar/gestor")
	public ResponseEntity<ResponseMessage> inserirGestor(@RequestBody Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
		ResponseMessage response = responseMessage;

		try {
			usuario.setPerfilAcesso("adm");
			response = usuarioService.cadastrar(usuario);
		} catch (Exception e) {
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

}
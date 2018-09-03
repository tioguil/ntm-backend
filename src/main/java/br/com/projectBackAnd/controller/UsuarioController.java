package br.com.projectBackAnd.controller;

import br.com.projectBackAnd.model.ResponseMessage;
import br.com.projectBackAnd.service.TokenService;
import br.com.projectBackAnd.service.UsuarioService;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import br.com.projectBackAnd.model.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

@Api(value="API REST Usuarios")
@RequestMapping("/usuario")
@RestController
public class UsuarioController {

	@Autowired
	private ResponseMessage responseMessage;
	@Autowired
	private UsuarioService usuarioService;
	@Autowired
	private TokenService tokenService;

	@ApiOperation(value="Inserir Usuario")
	@PostMapping("/cadastrar")
	public ResponseEntity<ResponseMessage> inserir(@RequestBody Usuario usuario, @RequestHeader(value="authentication") String token) throws SQLException, IOException, ClassNotFoundException {
		ResponseMessage response = responseMessage;

		Long idUser = tokenService.tokenInvalido(token).getUsuario().getId();
		if(idUser == -1) {
			response.setStatusCode("401");
			response.setMessage("Token invalido ou expirado");
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.UNAUTHORIZED);
		}

		try {
			response = usuarioService.cadastrar(usuario, idUser);
		} catch (Exception e) {
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}



	@ApiOperation(value="Autenticação")
	@PostMapping("/autenticar")
	public ResponseEntity<ResponseMessage> autenticar(@RequestBody Usuario usuarioRequest){
		ResponseMessage response = responseMessage;

		try {
			response = usuarioService.autenticar(usuarioRequest);
		} catch (Exception e) {
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}
}

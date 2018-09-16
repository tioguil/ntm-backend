package br.com.projectBackAnd.controller;

import br.com.projectBackAnd.model.Atividade;
import br.com.projectBackAnd.model.ResponseMessage;
import br.com.projectBackAnd.model.Usuario;
import br.com.projectBackAnd.service.AtividadeService;
import br.com.projectBackAnd.service.TokenService;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.sql.SQLException;

@Api(value="API REST Usuarios")
@RestController
@RequestMapping("/atividade")
public class AtividadeController {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private  ResponseMessage responseMessage;
	@Autowired
	private AtividadeService atividadeService;

	@ApiOperation(value="Inserir atividade")
	@PostMapping("/gestor/cadastrar")
	public ResponseEntity<ResponseMessage> cadastrar(@RequestBody Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
		ResponseMessage response = responseMessage;

		try {
			response = atividadeService.cadastrar(atividade);
		}catch (Exception e){
			response.setResponse(null);
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
		}


		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}
}

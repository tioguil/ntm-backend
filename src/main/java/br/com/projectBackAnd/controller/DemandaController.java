package br.com.projectBackAnd.controller;

import br.com.projectBackAnd.model.Demanda;
import br.com.projectBackAnd.model.ResponseMessage;
import br.com.projectBackAnd.service.DemandaService;
import br.com.projectBackAnd.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.Authorization;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import br.com.projectBackAnd.model.Usuario;
import io.swagger.annotations.ApiOperation;

import java.io.IOException;
import java.sql.SQLException;

@Api(value="API REST Usuarios")
@RestController
@RequestMapping("/demanda")
public class DemandaController {

	@Autowired
	private TokenService tokenService;
	@Autowired
	private  ResponseMessage responseMessage;
	@Autowired
	private DemandaService demandaService;

	@ApiOperation(value="Inserir uma Demanda")
	@PostMapping("/cadastrar")
	public ResponseEntity<ResponseMessage> cadastrar(@RequestBody Demanda  demanda, @RequestHeader(value="authentication") String token) throws SQLException, IOException, ClassNotFoundException {
		ResponseMessage response = responseMessage;

		Long idUser = tokenService.tokenInvalido(token).getUsuario().getId();
		if(idUser == -1) {
			response.setStatusCode("401");
			response.setMessage("Token invalido ou expirado");
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.UNAUTHORIZED);
		}

		try {

			response = demandaService.cadastrar(demanda, idUser);


		}catch (Exception e){
			response.setResponse(null);
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
		}


		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}
}

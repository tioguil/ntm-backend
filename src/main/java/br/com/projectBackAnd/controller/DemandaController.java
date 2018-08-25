package br.com.projectBackAnd.controller;

import io.swagger.annotations.Api;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projectBackAnd.models.Usuario;
import io.swagger.annotations.ApiOperation;

@Api(value="API REST Usuarios")
@RestController
@RequestMapping("/demanda")
public class DemandaController {

	@ApiOperation(value="Inserir uma Demanda")
	@PostMapping("/inserir")
	public ResponseEntity<Usuario> inserir(){
		
		Usuario usuario = new Usuario();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
}

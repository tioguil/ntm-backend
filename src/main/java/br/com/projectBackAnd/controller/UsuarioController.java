package br.com.projectBackAnd.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


import br.com.projectBackAnd.models.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="API REST Eventos")
@RequestMapping("/usuario")
@RestController
public class UsuarioController {

	@ApiOperation(value="Inserir Usuario")
	@PostMapping("/inserir")
	public ResponseEntity<Usuario> inserir(@RequestBody Usuario usuario){
		
		//Usuario usuario = new Usuario();
		return new ResponseEntity<Usuario>(usuario, HttpStatus.OK);
	}
}

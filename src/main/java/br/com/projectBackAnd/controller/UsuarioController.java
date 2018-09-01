package br.com.projectBackAnd.controller;

import br.com.projectBackAnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;


import br.com.projectBackAnd.model.Usuario;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

import java.util.ArrayList;
import java.util.List;

@Api(value="API REST Usuarios")
@RequestMapping("/usuario")
@RestController
public class UsuarioController {

	@Autowired
	private ResponseMessage responseMessage;


	/*
		{
   "cep":"06140-120",
   "cidade":"Osasco",
   "complemento":"",
   "cpfCnpj":"43105125685",
   "email":"guil_stay@hotmail.com",
   "endereco":"Rua Raimundo dos Santos",
   "nascimento":"1995-03-23",
   "nome":"Guilherme",
   "numero":0,
   "perfilAcesso":0,
   "senha":"123456",
   "sobreNome":"dos Santos",
   "uf":"SP"
}
	 */


	@ApiOperation(value="Inserir Usuario")
	@PostMapping("/inserir")
	public ResponseEntity<ResponseMessage> inserir(@RequestBody Usuario usuario){
		ResponseMessage response = responseMessage;
		response.setMessage("Tudo ok");
		response.setStatusCode("200");

		List<Usuario> list = new ArrayList<>();
		list.add(usuario);
		list.add(usuario);
		response.setResponse(list);

		//Usuario usuario = new Usuario();
		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation(value="Consultar")
	@GetMapping("/consultar/{id}")
	public ResponseEntity<ResponseMessage> consultar(@PathVariable("id") long id){
		ResponseMessage response = responseMessage;
		response.setMessage("Tudo ok");
		response.setStatusCode("200");
		response.setResponse(id);
		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}
}

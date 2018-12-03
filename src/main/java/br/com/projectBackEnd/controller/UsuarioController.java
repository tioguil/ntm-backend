package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.UsuarioService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;


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
	@PostMapping("/cadastrar/invite/{token}")
	public ResponseEntity<ResponseMessage> cadastrarAnalistaInvite(@RequestBody Usuario usuario,@PathVariable("token") String token) throws SQLException, IOException, ClassNotFoundException {
		ResponseMessage response = responseMessage;

		try {
			response = usuarioService.cadastrarAnalistaInvite(usuario, token);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation(value="Cadastra Analista no sistema")
	@PostMapping("/gestor/cadastrar")
	public ResponseEntity<ResponseMessage> inserir(@RequestBody Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
		ResponseMessage response = responseMessage;

		try {
			usuario.setPerfilAcesso("analista");
			response = usuarioService.cadastrar(usuario);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
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
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation(value="Recupera senha")
	@PostMapping("/recuperacao")
	public ResponseEntity<ResponseMessage> recuperarSenha(@RequestBody Usuario usuario){
		ResponseMessage response = responseMessage;

		try{
			response = usuarioService.recuperarSenha(usuario);
			if (response.getStatusCode() == "400")
				return new ResponseEntity<ResponseMessage>(response, HttpStatus.BAD_REQUEST);
		}catch (Exception e ){
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation(value = "Alteração de senha do usuario")
	@PostMapping("/analista/atualizarsenha")
	public ResponseEntity<ResponseMessage> atualizarSenha(@RequestBody Usuario usuario,Authentication authentication){
		ResponseMessage response = responseMessage;

		Usuario usuarioToken = (Usuario) authentication.getPrincipal();

		try{
			usuario.setId(usuarioToken.getId());
			response = usuarioService.atualizarSenha(usuario);
			if (response.getStatusCode() == "400")
				return new ResponseEntity<ResponseMessage>(response, HttpStatus.BAD_REQUEST);
		}catch (Exception e ){
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation(value="Pesquisa Analista no banco")
	@GetMapping({"/gestor/pesquisar/{search}", "/gestor/pesquisar/"})
	public ResponseEntity<ResponseMessage> pesquisaAnalista(@PathVariable Optional<String> search){
		ResponseMessage response = responseMessage;
		try {
			if(search.isPresent()){
				response = usuarioService.pesquisaAnalista(search.get());
			}else {
				response = usuarioService.pesquisaAnalista("");
			}

		}catch (Exception e){
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value="Edita Usuário")
	@PostMapping({"/analista/editar_perfil"})
	public ResponseEntity<ResponseMessage> editarUsuario(@RequestBody Usuario usuario, Authentication authentication){
		ResponseMessage response = responseMessage;
		
		System.out.println(authentication.getPrincipal());
		Usuario usuarioToken = (Usuario) authentication.getPrincipal();
		
		usuario.setId(usuarioToken.getId()); 
		
		
		try {
			response = usuarioService.editarUsuario(usuario);
			
		}catch (Exception e){
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}
		
		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}
	
	@ApiOperation(value = "Busca do usuário pelo ID")
	@GetMapping("/analista/buscar_usuario_by_id/{idUsuario}")
	public ResponseEntity<ResponseMessage> getUsuarioById(@PathVariable Long idUsuario){
		ResponseMessage response = responseMessage;

		try{
			response = usuarioService.getUsuarioById(idUsuario);
			
		}catch (Exception e ){
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@GetMapping("/analista/getimage/{name}")
	public ResponseEntity<ResponseMessage> getImagePerfil(@PathVariable String name){
		ResponseMessage response = new ResponseMessage();

		try{
			response = usuarioService.getImagePerfil(name);

		}catch (Exception e ){
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);

		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@PostMapping("/analista/uploadimage")
	public ResponseEntity<ResponseMessage> saveImagePerfil(@RequestParam MultipartFile image, Authentication authentication){
		ResponseMessage response = responseMessage;

		try{
			Usuario usuario = (Usuario) authentication.getPrincipal();
			response = usuarioService.saveImagePerfil(image,usuario);
		}catch (Exception e){
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@PostMapping("/analista/deleteimage")
	public ResponseEntity<ResponseMessage> deleteImage(Authentication authentication){
		ResponseMessage response = responseMessage;

		try{
			Usuario usuario = (Usuario) authentication.getPrincipal();
			response = usuarioService.deleteImage(usuario);
		}catch (Exception e){
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

}

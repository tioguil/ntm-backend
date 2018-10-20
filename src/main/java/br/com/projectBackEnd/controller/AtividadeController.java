package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.AtividadeService;
import br.com.projectBackEnd.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

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
			e.printStackTrace();
			response.setResponse(null);
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			e.printStackTrace();
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}


		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation("Lista de atividades por analista")
	@GetMapping("/analista/lista")
	public ResponseEntity<ResponseMessage> listaAtividadeByAnalista(Authentication authentication){
		ResponseMessage response = responseMessage;

		Usuario usuario	= (Usuario) authentication.getPrincipal();

		try{
			response = atividadeService.listaAtividadeByAnalista(usuario);
		}catch (Exception e){
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
	}

	@ApiOperation("Listar atividades por projeto")
	@GetMapping("/gestor/Listar/{idProject}")
	public ResponseEntity<ResponseMessage> listarAtividadeByProject(@PathVariable(value = "idProject") Long idProject) {

		ResponseMessage response = responseMessage;

		try {
			response = atividadeService.listarAtividadeByProject(idProject);


		} catch (Exception e) {
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);

	}


	@ApiOperation("Detalhe da atividade")
	@GetMapping("/analista/detalhe/{idAtividade}")
	public ResponseEntity<ResponseMessage> detalheAtividade(@PathVariable(value = "idAtividade") Long idAtividade){

		ResponseMessage response = responseMessage;

		try {
			response = atividadeService.detalheAtividade(idAtividade);

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);


	}


	@ApiOperation("Muda status da atividade para Finalizado")
	@PostMapping("/analista/finalizar")
	public ResponseEntity<ResponseMessage> finalizarAtividade(@RequestBody Atividade atividade, Authentication authentication){
		ResponseMessage response = responseMessage;

		try {
			response = atividadeService.finalizarAtividade(atividade);

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);

	}
	
	@ApiOperation("Muda status da atividade")
	@PostMapping("/gestor/atualizar_status_atividade")
	public ResponseEntity<ResponseMessage> alteraStatus(@RequestBody Atividade atividade, Authentication authentication){
		ResponseMessage response = responseMessage;

		try {
			response = atividadeService.alteraStatus(atividade);
		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);

	}
}

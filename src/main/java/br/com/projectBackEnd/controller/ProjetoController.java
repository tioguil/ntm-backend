package br.com.projectBackEnd.controller;


import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.Projeto;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.ProjetoService;
import br.com.projectBackEnd.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Optional;

@RequestMapping("/projeto")
@RestController
public class ProjetoController {

    @Autowired
    private ProjetoService projetoService;
    @Autowired
    private TokenService tokenService;
    @Autowired
    private ResponseMessage responseMessage;

    @ApiOperation("Cadastra um projeto")
    @PostMapping("/gestor/cadastrar")
    public ResponseEntity<ResponseMessage> cadastrar(@RequestBody Projeto projeto,
                                                     Authentication authentication) throws SQLException, IOException, ClassNotFoundException {

        ResponseMessage response = responseMessage;
        Usuario usuario = (Usuario) authentication.getPrincipal();

        try{
            projeto.setUsuario(usuario);
            response =  projetoService.cadastrar(projeto);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }


    }

    @ApiOperation("pesquisa projeto")
    @GetMapping({"/gestor/listar/", "/gestor/listar/{search}"})
    public ResponseEntity<ResponseMessage>listar(@PathVariable(value = "search") Optional<String> search) throws SQLException, IOException, ClassNotFoundException {

        ResponseMessage response = responseMessage;

        try{
            if(search.isPresent()){
                response =  projetoService.listar(search.get());
            }else {
                response =  projetoService.listar("");
            }

            return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);

        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

    }

    @ApiOperation("Busca Projeto pelo ID")
    @GetMapping("/gestor/buscaid/{idPorjeto}")
    public ResponseEntity<ResponseMessage> buscaProjetoById(@PathVariable(value = "idPorjeto") Long idPorjeto){
        ResponseMessage response = responseMessage;

        try{
            response = projetoService.buscaProjetoById(idPorjeto);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }
    
	@ApiOperation("Muda status do Projeto")
	@PostMapping("/gestor/atualizar_status_projeto")
	public ResponseEntity<ResponseMessage> alteraStatus(@RequestBody Projeto projeto, Authentication authentication){
		ResponseMessage response = responseMessage;

		try {
			response = projetoService.alteraStatus(projeto);
	

		} catch (Exception e) {
			e.printStackTrace();
			response.setStatusCode("500");
			response.setMessage(e.getMessage());
			response.setResponse(null);
			return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
		}

		return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);

	}

	@GetMapping("/gestor/listarProject/dash/{qtdDias}")
	public ResponseEntity<ResponseMessage> listarProjectsByDash(@PathVariable("qtdDias") Integer qtdias, Authentication authentication ){
        ResponseMessage response = responseMessage;

        try {
            response = projetoService.listarProjectByDash(qtdias);
        } catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
        }
        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }

    @PostMapping("/gestor/editarProjeto")
    public ResponseEntity<ResponseMessage> editarProjeto(@RequestBody Projeto projeto, Authentication authentication){
        ResponseMessage response = responseMessage;


        try {
            projeto.setId(projeto.getId());
            response = projetoService.editarProjeto(projeto);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }
}

package br.com.projectBackEnd.controller;


import br.com.projectBackEnd.model.Projeto;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.ProjetoService;
import br.com.projectBackEnd.service.TokenService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.io.IOException;
import java.sql.SQLException;

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

        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }

    @ApiOperation("Lista Projetos")
    @GetMapping("/gestor/listar")
    public ResponseEntity<ResponseMessage>listar(Authentication authentication) throws SQLException, IOException, ClassNotFoundException {

        ResponseMessage response = responseMessage;

        try{

            response =  projetoService.listar();

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

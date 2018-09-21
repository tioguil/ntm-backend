package br.com.projectBackEnd.controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import br.com.projectBackEnd.model.Cliente;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.service.ClienteService;
import br.com.projectBackEnd.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;

@Api(value="API REST Cliente")
@RequestMapping("/cliente")
@RestController
public class ClienteController {


    @Autowired
    private TokenService tokenService;
    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private ClienteService clienteService;

    @ApiOperation(value="Cadastra um cliente no sistema")
    @PostMapping("/gestor/cadastrar")
    public ResponseEntity<ResponseMessage> cadastrar(@RequestBody Cliente cliente){
        ResponseMessage response = responseMessage;
        try {
            response = clienteService.cadastrar(cliente);
        }catch (Exception e){
            e.printStackTrace();
            response.setResponse(null);
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }
    
    @ApiOperation(value="Pesquisar clientes no sistema")
    @GetMapping({"/gestor/listarclientes/","/gestor/listarclientes/{search}"})
    public ResponseEntity<ResponseMessage> pesquisaClientes(@PathVariable Optional<String> search ){
    	ResponseMessage response = responseMessage;
        try {
            
            if(search.isPresent()) {
            	response = clienteService.pesquisarClientes(search.get());
            }else {
            	response = clienteService.pesquisarClientes("");
            }
        }catch (Exception e){
            e.printStackTrace();
            response.setResponse(null);
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }
        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);   	
    }
}


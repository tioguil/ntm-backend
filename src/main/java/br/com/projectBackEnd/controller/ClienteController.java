package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.model.Cliente;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.service.ClienteService;
import br.com.projectBackEnd.service.TokenService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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
}


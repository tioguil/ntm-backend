package br.com.projectBackAnd.controller;


import br.com.projectBackAnd.model.Projeto;
import br.com.projectBackAnd.model.ResponseMessage;
import br.com.projectBackAnd.model.Usuario;
import br.com.projectBackAnd.service.ProjetoService;
import br.com.projectBackAnd.service.TokenService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
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

    @PostMapping("/cadastrar")
    public ResponseEntity<ResponseMessage> cadastrar(@RequestBody Projeto projeto,
                                                     @RequestHeader(value = "authentication") String token) throws SQLException, IOException, ClassNotFoundException {

        ResponseMessage response = responseMessage;

        Long idUser = tokenService.tokenInvalido(token).getUsuario().getId();

        if(idUser == -1) {
            response.setStatusCode("401");
            response.setMessage("Token invalido ou expirado");
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.UNAUTHORIZED);
        }

        try{

           response =  projetoService.cadastrar(projeto, idUser);

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

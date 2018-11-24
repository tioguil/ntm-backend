package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.model.Convite;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.ConviteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
@RequestMapping("/convite")
public class ConviteController {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private ConviteService conviteService;

    @PostMapping("/gestor/convidar")
    public ResponseEntity<ResponseMessage> convidarAnalista(@RequestBody Convite convite, Authentication authentication){
        ResponseMessage response = responseMessage;


        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            convite.setUsuario(usuario);
            response = conviteService.convidarAnalista(convite);
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

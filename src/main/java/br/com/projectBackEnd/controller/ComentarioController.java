package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.model.Comentario;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.ComentarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/comentario")
public class ComentarioController {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private ComentarioService comentarioService;

    @ApiOperation("Cadastra Comentario")
    @PostMapping("/analista/cadastrar")
    public ResponseEntity<ResponseMessage> inserirComentario(@RequestBody Comentario  comentario, Authentication authentication){
        ResponseMessage response = responseMessage;

        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            comentario.setUsuario(usuario);
            response = comentarioService.inserirComentario(comentario);
        }catch (Exception e){
            e.printStackTrace();
            response.setResponse(e);
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }

    @GetMapping("/analista/lista/{idAtividade}")
    public ResponseEntity<ResponseMessage> listaComentarioByAtividade(@PathVariable Long idAtividade){
        ResponseMessage response = responseMessage;

        try {
            response = comentarioService.listaComentarioByAtividade(idAtividade);

        }catch (Exception e){
            e.printStackTrace();
            response.setResponse(e);
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }
}

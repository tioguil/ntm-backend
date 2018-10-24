package br.com.projectBackEnd.controller;


import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.HorarioTrabalho;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.HorarioTrabalhoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.sql.ResultSet;
import java.util.List;

@RestController
@RequestMapping("/historico-trabalho")
public class HorarioTrabalhoController {


    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private HorarioTrabalhoService horarioTrabalhoService;



    @PostMapping("/analista/registrar")
    @ApiOperation("Registra o inicio do trabalho do analista")
    public ResponseEntity<ResponseMessage> registrarHorarioTrabalho(@RequestBody HorarioTrabalho trabalho, Authentication authentication){
        ResponseMessage response = responseMessage;

        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            trabalho.setUsuario(usuario);
            response = horarioTrabalhoService.registrarHorarioTrabalho(trabalho);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }


    @ApiOperation("Registra Horario de fim do trabalho")
    @PostMapping("/analista/finalizar-trabalho")
    public ResponseEntity<ResponseMessage> finalizarTrabalho(@RequestBody HorarioTrabalho trabalho, Authentication authentication){
        ResponseMessage response = responseMessage;

        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            trabalho.setUsuario(usuario);
            response = horarioTrabalhoService.finalizarTrabalho(trabalho);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }

    @ApiOperation("Retorna lista com horario de trabalho")
    @GetMapping("/analista/lista-horario/{idAtividade}")
    public ResponseEntity<ResponseMessage> listHorarioTrabalho(@PathVariable Long idAtividade, Authentication authentication){

        ResponseMessage response = responseMessage;

        try {
            Usuario usuario = (Usuario) authentication.getPrincipal();
            HorarioTrabalho horarioTrabalho = new HorarioTrabalho();
            Atividade atividade = new Atividade();
            atividade.setId(idAtividade);
            horarioTrabalho.setAtividade(atividade);
            horarioTrabalho.setUsuario(usuario);
            response = horarioTrabalhoService.listHorarioTrabalho(horarioTrabalho);
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

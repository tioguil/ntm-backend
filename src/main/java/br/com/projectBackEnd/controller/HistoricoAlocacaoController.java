package br.com.projectBackEnd.controller;


import br.com.projectBackEnd.model.HistoricoAlocacao;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.service.HistoricoAlocacaoService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/historicoAlocacao")
public class HistoricoAlocacaoController {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private HistoricoAlocacaoService alocacaoService;


    @ApiOperation("Vincula Analista a atividade")
    @PostMapping("/gestor/vincular")
    public ResponseEntity<ResponseMessage> vincularAnalista(@RequestBody HistoricoAlocacao alocacao){

        ResponseMessage response = responseMessage;

        try{
            response = alocacaoService.vincularAnalista(alocacao);
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

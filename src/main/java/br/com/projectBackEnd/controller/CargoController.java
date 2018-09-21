package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.service.CargoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/cargo")
public class CargoController {
    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private CargoService cargoService;

    @GetMapping({"/gestor/pesquisar/{search}", "/gestor/pesquisar/"})
    public ResponseEntity<ResponseMessage> pesquisaCargo(@PathVariable Optional<String> search){
        ResponseMessage response = responseMessage;

        try{
            if(search.isPresent()) {
                response = cargoService.pesquisaCargo(search.get());
            }else{
                response = cargoService.pesquisaCargo("");
            }
        }catch (Exception e ){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }
}

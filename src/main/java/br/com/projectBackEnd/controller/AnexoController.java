package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.Utili.Disco;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/anexo")
public class AnexoController {

    @Autowired
    private Disco disco;

    @PostMapping("/analista/upload")
    public void upload(@RequestParam MultipartFile anexo){
        disco.salvarFoto(anexo);
    }
}

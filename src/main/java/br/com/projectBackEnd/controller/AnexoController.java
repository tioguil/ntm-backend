package br.com.projectBackEnd.controller;

import br.com.projectBackEnd.Utili.Disco;
import br.com.projectBackEnd.model.Anexo;
import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.AnexoService;
import br.com.projectBackEnd.service.UsuarioService;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletRequest;
import javax.xml.ws.Response;

@RestController
@RequestMapping("/anexo")
public class AnexoController {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private AnexoService anexoService;

    @ApiOperation("Upload de anexos")
    @PostMapping("/analista/upload")
    public ResponseEntity<ResponseMessage> uploadAnexo(@RequestParam MultipartFile anexo, @RequestParam Long idAtividade , Authentication authentication){
        ResponseMessage response = responseMessage;

        try{
            Usuario usuario = (Usuario) authentication.getPrincipal();
            response = anexoService.uploadAnexo(anexo, idAtividade,usuario);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }

    @ApiOperation("Lista anxeos a partir do id da atividade")
    @GetMapping("/analista/list/{idAtividade}")
    public ResponseEntity<ResponseMessage> listAnexosByAtividades(@PathVariable Long idAtividade){

        ResponseMessage response = responseMessage;

        try{
            response = anexoService.listAnexos(idAtividade);
        }catch (Exception e){
            e.printStackTrace();
            response.setStatusCode("500");
            response.setMessage(e.getMessage());
            response.setResponse(null);
            return new ResponseEntity<ResponseMessage>(response, HttpStatus.INTERNAL_SERVER_ERROR);
        }

        return new ResponseEntity<ResponseMessage>(response, HttpStatus.OK);
    }


    @ApiOperation("Realiza Downlod do anexo")
    @GetMapping("/analista/download/{nameFile}")
    public ResponseEntity<Resource> downloadFile(@PathVariable String nameFile, HttpServletRequest request) {

        // Load file as Resource
        Resource resource = anexoService.loadFileAsResource(nameFile);

        // Try to determine file's content type
        String contentType = null;
        try {

            contentType = request.getServletContext().getMimeType(resource.getFile().getAbsolutePath());
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        // Fallback to the default content type if type could not be determined
        if(contentType == null) {
            contentType = "application/octet-stream";
        }

        return ResponseEntity.ok()
                .contentType(MediaType.parseMediaType(contentType))
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + resource.getFilename() + "\"")
                .body(resource);
    }

    @ApiOperation("Deleta Anexo")
    @PostMapping("/analista/delete")
    public ResponseEntity<ResponseMessage> deleteAnexo(@RequestBody Anexo anexo){
        ResponseMessage response = responseMessage;

        try{
            response = anexoService.deleteAnexo(anexo);
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

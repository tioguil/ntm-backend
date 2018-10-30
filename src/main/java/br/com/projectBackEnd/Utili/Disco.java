package br.com.projectBackEnd.Utili;

import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Date;

import br.com.projectBackEnd.model.ImagePerfil;
import org.apache.tomcat.util.codec.binary.Base64;

import org.springframework.core.io.Resource;
import org.springframework.core.io.UrlResource;

import br.com.projectBackEnd.model.Anexo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;

import javax.imageio.ImageIO;
import javax.xml.crypto.Data;

@Component
public class Disco {

    @Value("${diretorio.disco}")
    private String diretorioRaiz;
    @Value("${diretorio.image}")
    private String diretorioRaizImage;


    public Anexo salvarAnexo(Anexo anexo) {

        Date date = new Date();
        String sufixo = "date" + date.getTime() + "_User" + anexo.getUsuario().getId()
                + "_Ativ" + anexo.getAtividade().getId() + "_";

        anexo.setLocalArmazenamento(sufixo);
        this.salvar(anexo.getFile(), sufixo);

        return anexo;
    }

    private void salvar(MultipartFile arquivo, String sufixo) {
        Path diretorioPath = Paths.get(diretorioRaiz);
        String nomeArquivo = diretorioRaiz + sufixo + arquivo.getOriginalFilename();
    	Path anexosPath = diretorioPath.toAbsolutePath();
        Path arquivoPath = anexosPath.resolve(nomeArquivo);

        try {
            Files.createDirectories(diretorioPath);
        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de criar o diretorio.", e);
        }
        
        try {
        	arquivo.transferTo(arquivoPath.toFile());
        } catch (IOException e) {
        	throw new RuntimeException("Problemas na tentativa de salvar o arquivo no diretorio.", e);
        }
        
    }


    public Resource loadFileAsResource(String fileName) {
        try {
            fileName = diretorioRaiz + fileName;
            Path diretorioPath = Paths.get(diretorioRaiz);
            Path filePath = diretorioPath.resolve(fileName).normalize();
            Resource resource = new UrlResource(filePath.toUri());
            if (resource.exists()) {
                return resource;
            } else {
                return null;
            }
        } catch (Exception ex) {
            ex.printStackTrace();
            return null;
        }

    }


    public Boolean deleteAnexo(Anexo anexo){
        Path diretorioPath = Paths.get(diretorioRaiz);
        Path filePath = diretorioPath.resolve(diretorioRaiz + anexo.getLocalArmazenamento() + anexo.getNomeAquivo()).normalize();

        //String localArmazenamento = diretorioRaiz + anexo.getLocalArmazenamento() + anexo.getNomeAquivo();
        File file = filePath.toFile();
        if(file.delete()){
            System.out.println(file.getName() + " is deleted!");
            return true;
        }else{
            System.out.println("Delete operation is failed.");
            return false;
        }

    }

    public ImagePerfil saveImagePerfil(ImagePerfil image) {
        Date date = new Date();
        String sufixo = "date" + date.getTime() + "_User" + image.getUsuario().getId() + "_";
        String nameFile = sufixo+ image.getMultipartFile().getOriginalFilename();
        image.setDiretorio(nameFile);

        Path diretorioPath = Paths.get(diretorioRaizImage);
        Path anexosPath = diretorioPath.toAbsolutePath();
        Path arquivoPath = anexosPath.resolve(nameFile);

        try {
            Files.createDirectories(diretorioPath);
        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de criar o diretorio.", e);
        }

        try {
            image.getMultipartFile().transferTo(arquivoPath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Problemas na tentativa de salvar o arquivo no diretorio.", e);
        }
        image.setMultipartFile(null);
        return image;
    }

    public String getImage(String fileName) throws IOException {
        String base64Image = "";

        Path diretorioPath = Paths.get(diretorioRaizImage);
        Path filePath = diretorioPath.resolve(fileName).normalize();
        File folderInput = filePath.toFile();
        FileInputStream imageInFile = new FileInputStream(folderInput);
        byte imageData[] = new byte[(int) folderInput.length()];
        imageInFile.read(imageData);
        base64Image = Base64.encodeBase64String(imageData);

        imageInFile.close();
        return base64Image;
    }


    public Boolean deleteImage(String imagePerfil){
        Path diretorioPath = Paths.get(diretorioRaizImage);
        Path filePath = diretorioPath.resolve(imagePerfil).normalize();

        //String localArmazenamento = diretorioRaiz + anexo.getLocalArmazenamento() + anexo.getNomeAquivo();
        File file = filePath.toFile();
        if(file.delete()){
            System.out.println(file.getName() + " is deleted!");
            return true;
        }else{
            System.out.println("Delete operation is failed.");
            return false;
        }

    }
}
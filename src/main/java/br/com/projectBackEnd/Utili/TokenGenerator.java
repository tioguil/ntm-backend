package br.com.projectBackEnd.Utili;

import br.com.projectBackEnd.model.Usuario;
import org.apache.tomcat.util.codec.binary.Base64;
import org.springframework.stereotype.Component;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Date;


@Component
public class TokenGenerator {

    public String gerarToken(Usuario usuario) throws NoSuchAlgorithmException, UnsupportedEncodingException {
        /*
            gera md5 a partida da data e user
         */
        Date data = new Date();
        String str = data.toString();
        str = str+"user" + usuario.getId() + usuario.getEmail();
        System.out.println(str);
        MessageDigest m= MessageDigest.getInstance("MD5");
        m.update(str.getBytes("UTF-8"),0,str.length());
        String tokenMD5 = new BigInteger(1,m.digest()).toString(16);

        String tokenId = "#" +usuario.getId() +"#" + tokenMD5;

        //encode para base 64
        String TokenBase64 = encodeBase64(tokenId);

        return  TokenBase64;
    }

    public Long getIdByToken(String token){
       String cod = decodeBase64(token);
       String[] list =  cod.split("#");
       Long id = Long.parseLong(list[1]);
       return id;
    }


    private String encodeBase64(String str){
        // Faz Encode para base 64
        byte[] bytesEncoded = Base64.encodeBase64(str.getBytes());
        String str64 =  new String(bytesEncoded);

        return str64;
    }

    private String decodeBase64(String str64){

        // Faz Decode da base64
        byte[] valueDecoded = Base64.decodeBase64(str64.getBytes());
        String str = new String(valueDecoded);

        return str;
    }


}

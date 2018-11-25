package br.com.projectBackEnd.service;


import br.com.projectBackEnd.Utili.EnviarEmail;
import br.com.projectBackEnd.dao.ConviteDAO;
import br.com.projectBackEnd.model.Convite;
import br.com.projectBackEnd.model.ResponseMessage;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.EnableAspectJAutoProxy;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

import static br.com.projectBackEnd.Utili.SecurityConstants.*;


@Service
public class ConviteService {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private ConviteDAO conviteDAO;

    public ResponseMessage convidarAnalista(Convite convite) throws SQLException, IOException, ClassNotFoundException, MessagingException {
        ResponseMessage response = responseMessage;

        convite = conviteDAO.convidarAnalista(convite);

        String token = Jwts
                .builder()
                .setSubject(convite.getId()+"")
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_INVITE))
                .signWith(SignatureAlgorithm.HS512, SECRET_CONVITE)
                .compact();

        //Criando e enviando Email
        EnviarEmail email = new EnviarEmail();

        email.sendHtmlEmail(convite.getEmail(), "Convite para NTM-Project", "<h2>Bem Vindo ao NTM-Project </h2>  </p> <br/>Você recebeu um convite para se cadastrar em nossa plataforma <br><p>Para efetivar seu cadastro <a href='http://35.198.9.57/cadastro?token="+ token +"'> clique aqui </a> </p><p><i>Atensiosamente, <br> Equipe Nilone</i></p>");

        response.setStatusCode("200");
        response.setMessage("Convite enviado com sucesso!");
        response.setResponse(convite);
        return response;
    }
}

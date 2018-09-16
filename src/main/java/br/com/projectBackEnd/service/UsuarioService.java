package br.com.projectBackEnd.service;

import br.com.projectBackEnd.Utili.EnviarEmail;
import br.com.projectBackEnd.Utili.TokenGenerator;
import br.com.projectBackEnd.dao.TokenDao;
import br.com.projectBackEnd.dao.UsuarioDAO;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.UUID;

@Service
public class UsuarioService {

    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private UsuarioDAO usuarioDAO;
    @Autowired
    private TokenGenerator tokenGenerator;
    @Autowired
    private TokenDao tokenDao;

    /**
     *
     * @param usuario
     * @return Usuario cadastrado
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ResponseMessage cadastrar(Usuario usuario) throws SQLException, IOException, ClassNotFoundException, MessagingException {
        ResponseMessage response = responseMessage;

        //Gerando senha random
        UUID uuid = UUID.randomUUID();
        String senha = (uuid.toString().substring(0,8));
        System.out.println(senha);
        //Encoder Senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        usuario.setSenha(bCryptPasswordEncoder.encode(senha));

        usuario.setId(usuarioDAO.cadastrar(usuario));
        usuario.setSenha("");

        //Criando e enviando Email
        EnviarEmail email = new EnviarEmail();

        email.sendHtmlEmail(usuario.getEmail(), "Activict Controll", "<h2>Bem Vindo</h2>  <br/> sua senha de acesso: " + senha);

        response.setMessage("Usuario criado com sucesso!");
        response.setStatusCode("201");
        response.setResponse(usuario);
        return response;
    }

    /**
     *
     *
     * @param usuarioRequest
     * @return Usuario com Token
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public ResponseMessage autenticar(Usuario usuarioRequest) throws SQLException, IOException, ClassNotFoundException, NoSuchAlgorithmException {
        ResponseMessage response = responseMessage;

        //Buscando usuario na base
        Usuario usuario = new Usuario();
        usuario = usuarioDAO.autenticar(usuarioRequest);

        //Caso não econtre retorno -1
        if(usuario.getId() == -1){
            response.setStatusCode("403");
            response.setMessage("Usuario ou senha invalidos!");
            response.setResponse(null);
            return response;
        }else {
            //gera Token
            String token = tokenGenerator.gerarToken(usuario);
            //Resgistra Token
            usuario = tokenDao.registraToken(token, usuario);

            response.setStatusCode("200");
            response.setMessage("Autenticação realizada com sucesso");
            response.setResponse(usuario);

        }
        return response;
    }


}
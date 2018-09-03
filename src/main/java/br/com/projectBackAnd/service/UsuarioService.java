package br.com.projectBackAnd.service;

import br.com.projectBackAnd.Utili.TokenGenerator;
import br.com.projectBackAnd.dao.TokenDao;
import br.com.projectBackAnd.dao.UsuarioDAO;
import br.com.projectBackAnd.model.ResponseMessage;
import br.com.projectBackAnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;

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
    public ResponseMessage cadastrar(Usuario usuario, Long idUser) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;
        Integer nivel = usuarioDAO.consultaNivel(idUser);

        if(nivel == 0){
            response.setMessage("Você não tem autorização para realizar essa ação");
            response.setStatusCode("400");
            response.setResponse(null);
            return response;
        }


        usuario.setId(usuarioDAO.cadastrar(usuario));
        usuario.setSenha("");

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

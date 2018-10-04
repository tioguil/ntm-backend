package br.com.projectBackEnd.service;

import br.com.projectBackEnd.Utili.EnviarEmail;
import br.com.projectBackEnd.Utili.TokenGenerator;
import br.com.projectBackEnd.dao.TokenDao;
import br.com.projectBackEnd.dao.UsuarioDAO;
import br.com.projectBackEnd.model.Habilidade;
import br.com.projectBackEnd.model.ResponseMessage;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.util.List;
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

    @Autowired
    private HabilidadeService habilidadeService;

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


    public ResponseMessage recuperarSenha(Usuario usuario) throws IOException, MessagingException, SQLException, ClassNotFoundException {

        ResponseMessage response = responseMessage;

        //Gerando senha random
        UUID uuid = UUID.randomUUID();
        String senha = (uuid.toString().substring(0,8));
        System.out.println(senha);
        //Encoder Senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        usuario.setSenha(bCryptPasswordEncoder.encode(senha));

        //Atualiza senha do usuario na base, caso funcione retorna true
        if(usuarioDAO.recuperarSenha(usuario)){
            //Criando e enviando Email
            EnviarEmail email = new EnviarEmail();

            email.sendHtmlEmail(usuario.getEmail(), "Recupeção de senha", "<h2>Senha de acesso</h2>  <br/> Sua nova senha de acesso: " + senha);

            usuario.setSenha("");
            response.setStatusCode("200");
            response.setMessage("Enviado email com informações para recuperação de senha.");
            response.setResponse(null);

            return response;
        }else{
            usuario.setSenha("");
            response.setStatusCode("200");
            response.setMessage("conta não cadastrada.");
            response.setResponse(null);

            return response;
        }



    }

    public ResponseMessage atualizarSenha(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        //bucando senha no banco
        String senhaBanco = usuarioDAO.findPasswordById(usuario.getId());

        //object de encod
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();

        if(bCryptPasswordEncoder.matches(usuario.getSenha(),senhaBanco)){
            usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getNovaSenha()));
            usuarioDAO.atualizarSenha(usuario);
            response.setResponse(null);
            response.setMessage("Senha atualizada com sucesso!");
            response.setStatusCode("200");
        }else {
            response.setResponse(null);
            response.setMessage("Senhas não conferem");
            response.setStatusCode("400");
        }

        return response;
    }

    public ResponseMessage pesquisaAnalista(String search) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        List<Usuario> list = usuarioDAO.pesquisaAnalista(search);

        if(list.size()>0){
             response.setStatusCode("200");
             response.setMessage("Analista para o parametro " + search);
             response.setResponse(list);
        }else {
            response.setResponse(list);
            response.setMessage("Nenhum analista encontrado com o parametro "+ search);
            response.setStatusCode("400");
        }

        return response;
    }

    public Usuario getUsuarioID(Long idUsuario) throws SQLException, IOException, ClassNotFoundException {

        Usuario usuario = usuarioDAO.getUsuarioID(idUsuario);
        usuario.setHabilidades(habilidadeService.listHabilidadesByUsuario(usuario.getId()));

        return usuario;

    }
    
    public ResponseMessage editarUsuario(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
    	
    	ResponseMessage response = responseMessage;
    		
    	usuario = usuarioDAO.editarUsuario(usuario);    		
    	response.setMessage("Usuário atualizado com sucesso");
    	response.setStatusCode("200");
    	response.setResponse(usuario);
    		
    	return response;    	
    }
}

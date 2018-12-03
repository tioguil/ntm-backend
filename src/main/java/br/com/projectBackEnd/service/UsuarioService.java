package br.com.projectBackEnd.service;

import br.com.projectBackEnd.Utili.Disco;
import br.com.projectBackEnd.Utili.EnviarEmail;
import br.com.projectBackEnd.Utili.TokenGenerator;
import br.com.projectBackEnd.dao.*;
import br.com.projectBackEnd.model.*;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.datetime.joda.LocalDateParser;
import org.springframework.format.datetime.joda.LocalDateTimeParser;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import javax.mail.MessagingException;
import java.beans.Transient;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.NoSuchAlgorithmException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;

import static br.com.projectBackEnd.Utili.SecurityConstants.SECRET_CONVITE;
import static br.com.projectBackEnd.Utili.SecurityConstants.TOKEN_PREFIX;

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
    private AtividadeDAO atividadeDAO;

    @Autowired
    private HistoricoAlocacaoDAO historicoAlocacaoDAO;

//    @Autowired
//    private HabilidadeService habilidadeService;
    @Autowired
    private Disco disco;
    @Autowired
    private ConviteDAO conviteDAO;

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

        email.sendHtmlEmail(usuario.getEmail(), "Activict Controll", "<h2>Bem Vindo ao NTM </h2>  </p> <br/> sua senha de acesso: " + senha + "<br><p>Desejamos sucesso em seus projetos.</p><p><i>Atensiosamente, <br> Equipe Nilone</i></p>");

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

            email.sendHtmlEmail(usuario.getEmail(), "Recuperação de senha", "<h2>Senha de acesso</h2>  <br/> Foi gerada uma nova senha de acesso: <b>" + senha + "</b><br>Para alterar a sua senha, acesse o sistema com a nova senha gerada, clique no <b>nome do seu usuário</b>, depois em <b>Configurações</b> e clique em <b>Editar senha</b>.<p>Atenciosamente, <br> <i>Equipe Nilone</i></p>");

            usuario.setSenha("");
            response.setStatusCode("200");
            response.setMessage("Enviado email com informações para recuperação de senha.");
            response.setResponse(null);

            return response;
        }else{
            usuario.setSenha("");
            response.setStatusCode("400");
            response.setMessage("Conta não cadastrada.");
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
        //usuario.setHabilidades(habilidadeService.listHabilidadesByUsuario(usuario.getId()));

        return usuario;

    }
    
    public ResponseMessage getUsuarioById(Long usuarioId) throws SQLException, IOException, ClassNotFoundException {

    	ResponseMessage response = responseMessage;
    	
        Usuario usuario = usuarioDAO.getUsuarioById(usuarioId);
        
    	response.setMessage("Busca efetuada com sucesso");
    	response.setStatusCode("200");
    	response.setResponse(usuario);
    	
    	return response;

    }
    public ResponseMessage editarUsuario(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
    	
    	ResponseMessage response = responseMessage;
    		
    	usuario = usuarioDAO.editarUsuario(usuario);    		
    	response.setMessage("Usuário atualizado com sucesso");
    	response.setStatusCode("200");
    	response.setResponse(usuario);
    		
    	return response;    	
    }

    @Transient
    public ResponseMessage getImagePerfil(String name) throws IOException {
        ResponseMessage response = responseMessage;
        String image = disco.getImage(name);
        response.setMessage("Image localizada com sucesso");
        response.setStatusCode("200");
        response.setResponse(image);

        return response;
    }

    public ResponseMessage saveImagePerfil(MultipartFile image, Usuario usuario) throws SQLException, IOException, ClassNotFoundException {

        if(usuario.getImagePath() != null){
           if(!usuario.getImagePath().equals("")) {
        	   		deleteImage(usuario);
           }	
        }

        ImagePerfil imagePerfil = new ImagePerfil();
        imagePerfil.setUsuario(usuario);
        imagePerfil.setMultipartFile(image);
        imagePerfil = disco.saveImagePerfil(imagePerfil);
        ResponseMessage response = responseMessage;

        usuarioDAO.saveImagePerfil(imagePerfil);
        response.setMessage("Imagem salva com sucesso");
        response.setStatusCode("200");
        response.setResponse(imagePerfil);

        return response;
    }

    public ResponseMessage deleteImage(Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        disco.deleteImage(usuario.getImagePath());
        response.setMessage("Imagem deletada com sucesso");
        usuarioDAO.deleteImage(usuario);
        response.setStatusCode("200");
        response.setResponse(null);

        return response;

    }

    public void notificarUsuario(Usuario usuario, Atividade atividade) throws SQLException, IOException, ClassNotFoundException, MessagingException {

        usuario = usuarioDAO.getUsuarioById(usuario.getId());


        atividade = atividadeDAO.detalheAtividade(atividade.getId());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        EnviarEmail email = new EnviarEmail();

        String corpoEmail = "<h3> Caro  " + usuario.getNome() + ",</h3>" +
                "<p style='font-family: Arial, sans-serif; font-size: 14px;'> Uma nova atividade foi adicionada ao projeto: " + atividade.getProjeto().getNome() + " , segue dados:</p> " +
                "<p>Titulo da atividade: " + atividade.getNome() + "<br>Descrição: " +  atividade.getDescricao() +
                "<br>Data de criação: " + df.format(atividade.getDataCriacao()) +
                "<br>Data de Entrega: " + df.format(atividade.getDataEntrega()) + "</p>" + "" +
                "<i><p> Atensiosamente, <br> Equipe Nilone </p></i>" ;

        email.sendHtmlEmail(usuario.getEmail(), "Nova Atividade Vinculada", corpoEmail);


    }

    public void notifiyWhenDesvinculado(Usuario usuario, Atividade atividade , HistoricoAlocacao alocacao) throws SQLException, IOException, ClassNotFoundException, MessagingException {

        usuario = usuarioDAO.getUsuarioById(usuario.getId());

        atividade = atividadeDAO.detalheAtividade(atividade.getId());

        alocacao = historicoAlocacaoDAO.getAlocacaoByIdUserAndAtividade(usuario.getId(), atividade.getId());

        DateFormat df = new SimpleDateFormat("dd/MM/yyyy");

        EnviarEmail email = new EnviarEmail();

        String corpoEmail = "<h3> Caro  " + usuario.getNome() + ",</h3>" +
                "<p style='font-family: Arial, sans-serif; font-size: 14px;'> Você foi retirado da atividade: " + atividade.getNome() +
                ", no dia: " + df.format(new Date()) + "  </p> " +
                "<br>Projeto: " + atividade.getProjeto().getNome()+
                "<br>Descrição: " + atividade.getDescricao() +
                "<br>Data de criação: " + df.format(atividade.getDataCriacao()) +
                "<br> Para maiores informações, contate seu gestor." +
                "<i><p> Atensiosamente, <br> Equipe Nilone </p></i>";

        email.sendHtmlEmail(usuario.getEmail(), "Comunicado: Remoção de atividade", corpoEmail);


    }

    public ResponseMessage cadastrarAnalistaInvite(Usuario usuario, String token) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        String id = Jwts.parser().setSigningKey(SECRET_CONVITE).parseClaimsJws(token)
                .getBody().getSubject();

        Convite convite = conviteDAO.getConviteById(Long.parseLong(id));

        if(convite.getUsado()){
            response.setMessage("Convite invalido ou ja utilizado");
            response.setStatusCode("400");
            response.setResponse(null);
            return response;
        }
        usuario.setPerfilAcesso(convite.getNivelAcesso());
        usuario.setCargo(new Cargo(convite.getCargo()));
        //Encoder Senha
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder();
        usuario.setSenha(bCryptPasswordEncoder.encode(usuario.getSenha()));

        usuario.setId(usuarioDAO.cadastrar(usuario));
        usuario.setSenha("");


        conviteDAO.atualizaConviteUsado(convite);

        response.setMessage("Usuario criado com sucesso!");
        response.setStatusCode("201");
        response.setResponse(usuario);
        return response;


    }
}

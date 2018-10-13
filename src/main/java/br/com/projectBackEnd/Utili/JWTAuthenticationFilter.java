package br.com.projectBackEnd.Utili;

import br.com.projectBackEnd.dao.UsuarioDAO;
import br.com.projectBackEnd.model.Token;
import br.com.projectBackEnd.model.Usuario;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Date;

import static br.com.projectBackEnd.Utili.SecurityConstants.*;


/**
 * Class responsavel por realizar autenticação e gerar Token
 */
public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private AuthenticationManager authenticationManager;

    public  JWTAuthenticationFilter(AuthenticationManager authenticationManager){
        this.authenticationManager = authenticationManager;
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            Usuario usuario = new ObjectMapper().readValue(request.getInputStream(), Usuario.class);
            return this.authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(usuario.getEmail(), usuario.getSenha()));
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication authResult) throws IOException, ServletException {
        String username = ((User) authResult.getPrincipal()).getUsername();
        String token = Jwts
                .builder()
                .setSubject(username)
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TOKEN))
                .signWith(SignatureAlgorithm.HS512, SECRET)
                .compact();

        System.out.println("UserEmail " + username);
        Usuario usuario;
        String jsonUsuario = "";
        try {
            usuario = new UsuarioDAO().findByEmail(username);
            usuario.setSenha("");
            Token token1 = new Token();
            token1.setNumero(TOKEN_PREFIX + token);
            token1.setExpiracao(EXPIRATION_TOKEN);
            usuario.setToken(token1);
            jsonUsuario = new ObjectMapper().writeValueAsString(usuario);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
        response.getWriter().write(jsonUsuario);
        response.addHeader(HEADER_STRING, TOKEN_PREFIX + token);
    }

}

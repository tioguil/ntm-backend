package br.com.projectBackEnd.Utili;

import br.com.projectBackEnd.model.Usuario;
import br.com.projectBackEnd.service.CunstonUsuarioDetailService;
import io.jsonwebtoken.Jwts;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import static br.com.projectBackEnd.Utili.SecurityConstants.*;


/**
 * class que verificar token a cada requisição
 */
public class JWTAuthorizationFilter extends BasicAuthenticationFilter {
    @Autowired
    private CunstonUsuarioDetailService cunstonUsuarioDetailService;


    public JWTAuthorizationFilter(AuthenticationManager authenticationManager, CunstonUsuarioDetailService cunstonUsuarioDetailService) {
        super(authenticationManager);
        this.cunstonUsuarioDetailService = cunstonUsuarioDetailService;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {
        String header =  request.getHeader(HEADER_STRING);
        //Verificando se foi recebido Token no header
        if(header == null || !header.startsWith(TOKEN_PREFIX)){

            // verificação de URI liberação para documentação swagger
            if (request.getRequestURI().equals("/swagger-ui.html") || request.getRequestURI().matches(".*/swagger-resources.*") ||
                request.getRequestURI().equals("/v2/api-docs") || request.getRequestURI().matches("/webjars/.*") || request.getRequestURI().equals("/usuario/recuperacao") ||
                request.getRequestURI().matches("/usuario/cadastrar/invite/.*")){
                chain.doFilter(request,response);
            }else {
                throw new ServletException("Nenhum token informado");
            }

        }else {
            //Realizando autenticação do usuario a partir do TOken
            UsernamePasswordAuthenticationToken authemticationToken = getAuthemticationToken(request);
            SecurityContextHolder.getContext().setAuthentication(authemticationToken);
            chain.doFilter(request,response);
        }
    }


    private UsernamePasswordAuthenticationToken getAuthemticationToken(HttpServletRequest request){

        String token = request.getHeader(HEADER_STRING);
        if(token == null) return null;
        String userName = Jwts.parser().setSigningKey(SECRET).parseClaimsJws(token.replace(TOKEN_PREFIX,""))
                .getBody().getSubject();

        UserDetails userDetails = (UserDetails) cunstonUsuarioDetailService.loadUserByUsernameArray(userName).get(0);
        Usuario usuario = (Usuario) cunstonUsuarioDetailService.loadUserByUsernameArray(userName).get(1);
        usuario.setSenha("");
        return userName != null ? new UsernamePasswordAuthenticationToken(usuario, null, userDetails.getAuthorities()): null;

    }
}

package br.com.projectBackEnd.service;

import br.com.projectBackEnd.dao.UsuarioDAO;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Component
public class CunstonUsuarioDetailService implements UserDetailsService {

    @Autowired
    private UsuarioDAO usuarioDAO;

    private Usuario usuario;

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {

        try {
            usuario =  Optional.ofNullable(usuarioDAO.findByEmail(email)).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GrantedAuthority> authorityLista;
        if(usuario.getPerfilAcesso().equals("adm")){
            authorityLista = AuthorityUtils.createAuthorityList("ROLE_gestor","ROLE_avaliador","ROLE_adm");
            System.out.println("ADM");
        }else if(usuario.getPerfilAcesso().equals("gestor")){
            authorityLista = AuthorityUtils.createAuthorityList("ROLE_gestor");
        }else {
            authorityLista =AuthorityUtils.createAuthorityList("ROLE_avaliador");
        }

        return new User(usuario.getEmail(),usuario.getSenha(), authorityLista);
    }

    public ArrayList<Object> loadUserByUsernameArray(String email){
        try {
            usuario =  Optional.ofNullable(usuarioDAO.findByEmail(email)).orElseThrow(()-> new UsernameNotFoundException("User not found"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        List<GrantedAuthority> authorityLista;
        if(usuario.getPerfilAcesso().equals("adm")){
            authorityLista = AuthorityUtils.createAuthorityList("ROLE_gestor","ROLE_avaliador","ROLE_admin");
        }else if(usuario.getPerfilAcesso().equals("gestor")){
            authorityLista = AuthorityUtils.createAuthorityList("ROLE_gestor");
        }else {
            authorityLista =AuthorityUtils.createAuthorityList("ROLE_avaliador");
        }

        ArrayList<Object> retorno = new ArrayList<>();
        System.out.println(usuario.getEmail());
        retorno.add(new User(usuario.getEmail(),usuario.getSenha(), authorityLista));
        retorno.add(usuario);
        return retorno;
    }
}

package br.com.projectBackEnd.dao;

import br.com.projectBackEnd.model.Token;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.stereotype.Repository;

import java.io.IOException;
import java.sql.ResultSet;
import java.sql.SQLException;

@Repository
public class TokenDao extends GenericDAO{

    public Usuario registraToken(String token, Usuario usuario) throws SQLException, IOException, ClassNotFoundException {
        String sql = "INSERT into token(numero, usuario_id) values(?,?)";
        Long idToken = super.executeQuery(sql,token, usuario.getId() );
        Token token1 = new Token();
        token1.setNumero(token);
        usuario.setToken(token1);
        return usuario;
    }

    public Token getToken(Token token) throws SQLException, IOException, ClassNotFoundException {
        String sql = "SELECT * from token where usuario_id = ? and numero = ?";
        ResultSet rs = super.executeResutSet(sql,token.getUsuario().getId(),  token.getNumero());

        if(rs.next()){
            token.setDataGeracao(rs.getTimestamp("data_geracao"));
            return token;
        }else {
            token.setDataGeracao(null);
            return token;
        }
    }
}

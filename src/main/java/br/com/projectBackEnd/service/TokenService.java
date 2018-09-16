package br.com.projectBackEnd.service;

import br.com.projectBackEnd.Utili.TokenGenerator;
import br.com.projectBackEnd.dao.TokenDao;
import br.com.projectBackEnd.model.Token;
import br.com.projectBackEnd.model.Usuario;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;

@Service
public class TokenService {

    private TokenGenerator tokenGenerator;

    @Autowired
    private TokenDao tokenDao;

    public TokenService(){
        this.tokenGenerator = new TokenGenerator();
        this.tokenDao = new TokenDao();
    }

    /**
     *
     * @param tk
     * @return Caso o token seja invalido retorna true
     * @throws SQLException
     * @throws IOException
     * @throws ClassNotFoundException
     */
    public Token tokenInvalido(String tk) throws SQLException, IOException, ClassNotFoundException {
        final Long  tempo = 86400000L; //Intervalo de Tempo 1 dia 86400000L
        Token token = new Token();
        token.setNumero(tk);
        //Decode Token para pegar id do Usuario;
        TokenDao daoToken = tokenDao;
        Long idUsuario = tokenGenerator.getIdByToken(token.getNumero());
        //Consultando no banco de Dados
        token.setUsuario(new Usuario(idUsuario));
        token = daoToken.getToken(token);

        //caso nao encontre no bando a data esta null
        if(token.getDataGeracao() == null){
            token.getUsuario().setId(-1L);
            return token;
        }
        //verificando intervalo
        Date dataTual = new Date();
        Date dataToken = token.getDataGeracao();
        Long intervalo =  dataTual.getTime() - dataToken.getTime() ;

        if(intervalo < tempo ){
            return token;
        }else {
            token.getUsuario().setId(-1L);
            return token;
        }

    }

}

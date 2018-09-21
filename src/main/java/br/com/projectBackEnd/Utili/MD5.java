package br.com.projectBackEnd.Utili;

import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5 {

    public String md5(String senha) throws UnsupportedEncodingException, NoSuchAlgorithmException {
        MessageDigest m= MessageDigest.getInstance("MD5");
        m.update(senha.getBytes("UTF-8"),0,senha.length());
        String novaSenha = new BigInteger(1,m.digest()).toString(16);

        return novaSenha;
    }
}

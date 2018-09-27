package br.com.projectBackEnd.Utili;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        String senhaEncod = bCryptPasswordEncoder.encode("123456");
        System.out.println(senhaEncod);
        if(bCryptPasswordEncoder.matches("123456",senhaEncod )){
            System.out.println("Senha ok");
        }else {
            System.out.println("Senha nao conferem");
        }
    }
}

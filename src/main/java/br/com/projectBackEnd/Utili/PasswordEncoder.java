package br.com.projectBackEnd.Utili;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

public class PasswordEncoder {
    public static void main(String[] args) {
        BCryptPasswordEncoder bCryptPasswordEncoder = new BCryptPasswordEncoder(10);
        //String senhaEncod = bCryptPasswordEncoder.encode("123456");
        //System.out.println(senhaEncod)
        String senhaEncod = bCryptPasswordEncoder.encode("123456");
        System.out.println(senhaEncod);
        senhaEncod = "$2a$10$LxlmcNDtrKCnnLjkSezoA.8rz6.tYs5U/rnxXYMMknDBxr.D5/JIu";
        if(bCryptPasswordEncoder.matches("123456",senhaEncod )){

            System.out.println("Senha ok");
        }else {
            System.out.println("Senha nao conferem");
        }
    }
}

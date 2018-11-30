package br.com.projectBackEnd.Utili;

import javax.mail.*;
import javax.mail.internet.*;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

public class EnviarEmail {

    private final static String host = "smtp.gmail.com";
    private final static String port = "587";
    private final static String userName = "opinowltda@gmail.com";
    private final static String password = "nilone123456";


    public void sendHtmlEmail(String toAddress, String subject, String message) throws AddressException, MessagingException, IOException {

        new Thread(){

            @Override
            public void run() {
                try {
                    // sets SMTP server properties
                    Properties properties = new Properties();

                    properties.put("mail.smtp.host", host);
                    properties.put("mail.smtp.port", port);
                    properties.put("mail.smtp.auth", "true");
                    properties.put("mail.smtp.starttls.enable", "true");

                    properties.put("mail.transport.protocol", "smtp");

                    //adicionei, trata-se de uma lista de hosts confiaveis
                    properties.put("mail.smtp.ssl.trust", host);

                    // creates a new session with an authenticator
                    Authenticator auth = new Authenticator() {
                        public PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication(userName, password);
                        }
                    };

                    Session session = Session.getInstance(properties, auth);

                    // creates a new e-mail message
                    Message msg = new MimeMessage(session);

                    msg.setFrom(new InternetAddress(userName));
                    InternetAddress[] toAddresses = { new InternetAddress(toAddress) };
                    msg.setRecipients(Message.RecipientType.TO, toAddresses);
                    msg.setSubject(subject);
                    msg.setSentDate(new Date());

                    //esse e o campo de texto da mensagem
                    MimeBodyPart mimeBodyPart1 = new MimeBodyPart();
                    mimeBodyPart1.setText(message);
                    mimeBodyPart1.setHeader("Content-Type", "text/html");

                    //agora um pacote que contem todas as partes da mensagem, texto e anexo
                    Multipart mp = new MimeMultipart();

                    //adicionei o texto
                    mp.addBodyPart(mimeBodyPart1);

                    //configurei como conteudo da mensagem o pacote completo, com texto e anexo
                    msg.setContent(mp);

                    // sends the e-mail
                    Transport.send(msg);
                }catch (Exception e ){
                    e.printStackTrace();
                }

            }

        }.start();
    }

}

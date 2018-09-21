package br.com.projectBackEnd.model;

import lombok.Getter;
import lombok.Setter;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

@Getter
@Setter
public class Token {

    private Long id;
    private String numero;
    private Long expiracao;
    private Date dataGeracao;
    private Usuario usuario;

    /**
     Recebe formato e retorna data no formato String já formadata
     */
    public String getDateFormater(String formato) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        return dateFormat.format(this.dataGeracao);
    }

    /**
     Recebe formato e data em String
     */
    public void setDataGeracao(String formato, String data) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(formato);
        this.dataGeracao = dateFormat.parse(data);
    }



}

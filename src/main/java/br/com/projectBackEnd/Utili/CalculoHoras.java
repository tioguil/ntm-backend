package br.com.projectBackEnd.Utili;

import java.util.Date;

public class CalculoHoras {


    private Long totalHoras = 0L;

    public String getTotalHoras(){

        Long segundos = totalHoras / 1000;
        Long minutos = segundos / 60;
        Long horas = minutos / 60;

        minutos = minutos - (60*horas);

        String saidaHoras = String.format ("%02d", horas);
        String saidaMin = String.format ("%02d", minutos);


        return saidaHoras + ":" + saidaMin;
    }

    public String calculaDiferenca(Date dataInicio, Date dataFim){
        Long diferenca = dataFim.getTime()- dataInicio.getTime();

        totalHoras += diferenca;

        Long segundos = diferenca / 1000;
        Long minutos = segundos / 60;
        Long horas = minutos / 60;

        minutos = minutos - (60*horas);

        String saidaHoras = String.format ("%02d", horas);
        String saidaMin = String.format ("%02d", minutos);


        return saidaHoras + ":" + saidaMin;
    }

}

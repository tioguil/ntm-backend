package br.com.projectBackEnd.service;

import br.com.projectBackEnd.Utili.CalculoHoras;
import br.com.projectBackEnd.dao.HorarioTrabalhoDao;
import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.HorarioTrabalho;
import br.com.projectBackEnd.model.ResponseMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.sql.SQLException;
import java.util.Date;
import java.util.List;

@Service
public class HorarioTrabalhoService {

    @Autowired
    private HorarioTrabalhoDao horarioTrabalhoDao;
    @Autowired
    private ResponseMessage responseMessage;
    @Autowired
    private AtividadeService atividadeService;

    public List<HorarioTrabalho> listHorarioTrabalhoByAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        List<HorarioTrabalho> trabalhoList = horarioTrabalhoDao.listHorarioTrabalhoByAtividade(idAtividade);

        CalculoHoras calculoHoras = new CalculoHoras();

        for(int i = 0; i < trabalhoList.size(); i++){
            Date fim = trabalhoList.get(i).getDataFim();
            if(fim != null){

                Date inicio = trabalhoList.get(i).getDataInicio();
                String horasTabalhadas = calculoHoras.calculaDiferenca(inicio, fim);
                trabalhoList.get(i).setTotalHoras(horasTabalhadas);
            }

        }

        return trabalhoList;

    }

    public ResponseMessage registrarHorarioTrabalho(HorarioTrabalho trabalho) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response;
        if(!horarioTrabalhoDao.horarioEmAndamento(trabalho)){
            trabalho = horarioTrabalhoDao.registrarHorarioTrabalho(trabalho);
            response = listHorarioTrabalho(trabalho);
            response.setStatusCode("200");
            response.setMessage("Horario de trabalho registrado com sucesso");

        }else {
            response = listHorarioTrabalho(trabalho);
            response.setStatusCode("400");
            response.setMessage("Falha ao cadastrar, pois já consta trabalho em aberto");
        }

        Atividade atividade = trabalho.getAtividade();
        atividade.setStatus("iniciada");
        atividadeService.alteraStatus(atividade);

        return response;
    }


    public ResponseMessage finalizarTrabalho(HorarioTrabalho trabalho) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;

        horarioTrabalhoDao.finalizarTrabalho(trabalho);
        Atividade atividade = trabalho.getAtividade();
        atividade.setStatus("pendente");
        atividadeService.alteraStatus(atividade);

        response.setStatusCode("200");
        response.setMessage("Horario registrado com sucesso!");
        response.setResponse(trabalho);

        return response;

    }

    /**
     * Lista horario de trabalho do analista referente a atividade
     * @param horarioTrabalho
     * @return
     */
    public ResponseMessage listHorarioTrabalho(HorarioTrabalho horarioTrabalho) throws SQLException, IOException, ClassNotFoundException {
        ResponseMessage response = responseMessage;


        List<HorarioTrabalho> horarioTrabalhos = horarioTrabalhoDao.listHorarioTrabalho(horarioTrabalho);
        CalculoHoras calculoHoras = new CalculoHoras();

        for(int i = 0; i < horarioTrabalhos.size(); i++){
            Date fim = horarioTrabalhos.get(i).getDataFim();
            if(fim != null){

                Date inicio = horarioTrabalhos.get(i).getDataInicio();
                String horasTabalhadas = calculoHoras.calculaDiferenca(inicio, fim);
                horarioTrabalhos.get(i).setTotalHoras(horasTabalhadas);
            }

        }

        response.setStatusCode("200");
        response.setMessage(calculoHoras.getTotalHoras());
        response.setResponse(horarioTrabalhos);

        return response;
    }
}

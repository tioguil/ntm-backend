package br.com.projectBackEnd.dao;

import java.io.IOException;
import java.nio.charset.Charset;
import java.sql.Date;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import org.springframework.stereotype.Repository;

import br.com.projectBackEnd.model.Atividade;
import br.com.projectBackEnd.model.Projeto;

@Repository
public class AtividadeDAO extends GenericDAO{

    public Long cadastrar(Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
    	
    	String sql = "INSERT into atividade(nome, descricao,complexidade, cep, endereco, numero_endereco, complemento, cidade, uf ,projeto_id, data_entrega,data_criacao)values(?,?,?,?,?,?,?,?,?,?,?,?)";

        Long id = super.executeQuery(sql, atividade.getNome(), atividade.getDescricao(), atividade.getComplexidade(), atividade.getCep(),
                atividade.getEndereco(), atividade.getEnderecoNumero(), atividade.getComplemento(),atividade.getCidade(), atividade.getUf(),
                atividade.getProjeto().getId(), atividade.getDataEntrega(),atividade.getDataCriacao());

        return id;
        
    }


    public List<Atividade> listarAtividadebyProject (Long idProject) throws SQLException, IOException, ClassNotFoundException{


        String sql = "select id, nome, descricao, complexidade, data_criacao, data_entrega, cep, endereco, numero_endereco, " +
                "complemento, convert(cidade using UTF8) as cidade, uf, status from atividade where projeto_id = ?";

        List<Atividade> atividades = new ArrayList<>();

        ResultSet rs = super.executeResutSet(sql, idProject);

        while (rs.next()){

            Atividade atividade = new Atividade();
            atividade.setId(rs.getLong("id"));
            atividade.setNome(rs.getString("nome"));
            atividade.setDescricao(rs.getString("descricao"));
            atividade.setComplexidade(rs.getInt("complexidade"));
            atividade.setDataCriacao(rs.getDate("data_criacao"));
            atividade.setDataEntrega(rs.getDate("data_entrega"));
            atividade.setCep(rs.getString("cep"));
            atividade.setEndereco(rs.getString("endereco"));
            atividade.setEnderecoNumero(rs.getString("numero_endereco"));
            atividade.setComplemento(rs.getString("complemento"));
            atividade.setCidade(rs.getString("cidade"));
            atividade.setUf(rs.getString("uf"));
            atividade.setStatus(rs.getString("status"));
            System.out.print(atividade.getCidade());
            
         
            atividades.add(atividade);
        }

       return  atividades;
       

    }

    public List<Atividade> listaAtividadeByAnalista(Long id) throws SQLException, IOException, ClassNotFoundException {
        String sql = "select * from atividade join atividade_usuario on id = atividade_id where usuario_id = ? and atividade_usuario.status = 1";
        ResultSet rs = super.executeResutSet(sql, id);

        List<Atividade> list = new ArrayList<>();

        while (rs.next()){
            Atividade atividade = new Atividade();
            atividade.setId(rs.getLong("id"));		
            atividade.setNome(rs.getString("nome"));
            atividade.setDescricao(rs.getString("descricao"));
            atividade.setComplexidade(rs.getInt("complexidade"));
            atividade.setDataCriacao(rs.getTimestamp("data_criacao"));
            atividade.setDataEntrega(rs.getDate("data_entrega"));
            atividade.setCep(rs.getString("cep"));
            atividade.setEndereco(rs.getString("endereco"));
            atividade.setEnderecoNumero(rs.getString("numero_endereco"));
            atividade.setComplemento(rs.getString("complemento"));
            atividade.setCidade(rs.getString("cidade"));
            atividade.setUf(rs.getString("uf"));
            atividade.setStatus(rs.getString("status"));
            Projeto projeto = new Projeto();
            projeto.setId(rs.getLong("projeto_id"));
            atividade.setProjeto(projeto);
            list.add(atividade);

        }

        return list;
    }

    public Atividade detalheAtividade(Long idAtividade) throws SQLException, IOException, ClassNotFoundException {

        //String sql = "select * from atividade where id = ?";
        String sql = "select a.id, a.nome, a.descricao, a.complexidade, a.data_criacao, a.data_entrega,\n" +
                        "a.cep, a.endereco, a.numero_endereco, a.complemento, a.cidade, a.uf, \n" +
                        "a.status, a.projeto_id, p.nome, p.numero_projeto from atividade a INNER JOIN projeto p \n" +
                        "ON p.id = a.projeto_id \n" +
                        "where a.id = ?";
        ResultSet rs = super.executeResutSet(sql, idAtividade);

        if(rs.next()){
            Atividade atividade = new Atividade();
            atividade.setId(rs.getLong("id"));
            atividade.setNome(rs.getString("nome"));
            atividade.setDescricao(rs.getString("descricao"));
            atividade.setComplexidade(rs.getInt("complexidade"));
            atividade.setDataCriacao(rs.getTimestamp("data_criacao"));
            atividade.setDataEntrega(rs.getDate("data_entrega"));
            atividade.setCep(rs.getString("cep"));
            atividade.setEndereco(rs.getString("endereco"));
            atividade.setEnderecoNumero(rs.getString("numero_endereco"));
            atividade.setComplemento(rs.getString("complemento"));
            atividade.setCidade(rs.getString("cidade"));
            atividade.setUf(rs.getString("uf"));
            atividade.setStatus(rs.getString("status"));
            Projeto projeto = new Projeto();
            projeto.setId(rs.getLong("projeto_id"));
            projeto.setNome(rs.getString("p.nome"));
            projeto.setNumeroProjeto(rs.getString("p.numero_projeto"));

            atividade.setProjeto(projeto);
            return atividade;

        }else{
            return null;
        }
    }

    public Atividade alteraStatus(Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "update atividade set status = ? where id = ?";
        super.executeQuery(sql, atividade.getStatus(), atividade.getId());
		return atividade;
    }

    public Atividade finalizarAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException {
        String sql = "update atividade set status = 'finalizada' where id = ?";
        super.executeQuery(sql, atividade.getId());
        String sql1 = "update horario_trabalho set data_fim = now() where atividade_usuario_atividade_id = ? and data_fim is null";
        super.executeQuery(sql1, atividade.getId());
        atividade = detalheAtividade(atividade.getId());
        return atividade;
    }

    public List<Atividade> listarAtividadeByData(Date dt_inicio, Date dt_fim, Long usuario_id) throws SQLException, IOException, ClassNotFoundException{

        String sql = "SELECT a.id, a.nome, a.descricao, a.status, a.data_criacao, a.data_entrega\n" +
                "FROM atividade a\n" +
                "JOIN atividade_usuario ati\n" +
                "ON a.id = ati.atividade_id \n" +
                "JOIN usuario u\n" +
                "ON u.id = ati.usuario_id\n" +
                "WHERE a.data_criacao BETWEEN ? AND ? AND u.id = ? and ati.status = 1;";

        ResultSet rs = super.executeResutSet( sql, dt_inicio,dt_fim, usuario_id);
        List<Atividade> list = new ArrayList<>();

        while(rs.next()){
            Atividade atividade = new Atividade();
            atividade.setId(rs.getLong("id"));
            atividade.setNome(rs.getString("nome"));
            atividade.setDescricao(rs.getString("descricao"));
            atividade.setStatus(rs.getNString("status"));
            atividade.setDataCriacao(rs.getDate("data_criacao"));
            atividade.setDataEntrega(rs.getDate("data_entrega"));
            list.add(atividade);
        }

        return list;


    }

    public List<Atividade> listarAtividadesByDash(Integer qtdDias) throws SQLException, IOException, ClassNotFoundException{


        java.util.Date dataAtual = new java.util.Date();
        java.util.Date dataSubtraida = new java.util.Date();

        //Representação de um dia em milisegundos
        Long diasAsmili = 86400000L;

        //Get dia atual em milissegundos
        Long mili = dataSubtraida.getTime();

        dataAtual.setTime(mili + 86400000L);

        diasAsmili = diasAsmili * qtdDias;

        mili -= diasAsmili;

        dataSubtraida.setTime(mili);

        String sql = "SELECT status, count(*) 'quantidade' from atividade where data_criacao between ? and ? group by status";

        ResultSet rs = super.executeResutSet(sql,dataSubtraida,dataAtual);

        List<Atividade> atividades = new ArrayList<>();

        while (rs.next()){
            Atividade atividade = new Atividade();
            atividade.setStatus(rs.getString("status"));
            atividade.setQtd(rs.getInt("quantidade"));
            atividades.add(atividade);
        }

        return atividades;
    }



    public List<Atividade> buscaByStatusData(Long idAnalista, String status, Date dataInicial, Date dataFim) throws SQLException, IOException, ClassNotFoundException {
        String sqlTodos = "select * from atividade atv join atividade_usuario on id = atividade_id where usuario_id = ? and atividade_usuario.status = 1 and data_criacao between ? and ?";
        String sqlStatus = "select * from atividade atv join atividade_usuario on id = atividade_id where usuario_id = ? and atividade_usuario.status = 1 and data_criacao between ? and ? and atv.status = ?";

        ResultSet rs;
        if(status.equals("todos")){
            rs = executeResutSet(sqlTodos,idAnalista, dataInicial, dataFim);
        }else {
            rs = executeResutSet(sqlStatus,idAnalista, dataInicial, dataFim, status);
        }

        List<Atividade> list = new ArrayList<>();

        while (rs.next()){
            Atividade atividade = new Atividade();
            atividade.setId(rs.getLong("id"));
            atividade.setNome(rs.getString("nome"));
            atividade.setDescricao(rs.getString("descricao"));
            atividade.setComplexidade(rs.getInt("complexidade"));
            atividade.setDataCriacao(rs.getTimestamp("data_criacao"));
            atividade.setDataEntrega(rs.getDate("data_entrega"));
            atividade.setCep(rs.getString("cep"));
            atividade.setEndereco(rs.getString("endereco"));
            atividade.setEnderecoNumero(rs.getString("numero_endereco"));
            atividade.setComplemento(rs.getString("complemento"));
            atividade.setCidade(rs.getString("cidade"));
            atividade.setUf(rs.getString("uf"));
            atividade.setStatus(rs.getString("status"));
            Projeto projeto = new Projeto();
            projeto.setId(rs.getLong("projeto_id"));
            atividade.setProjeto(projeto);
            list.add(atividade);

        }

        return list;
    }

    public List<Atividade> buscaByStatus(Long idAnalista, String status) throws SQLException, IOException, ClassNotFoundException {

        String sqlTodos = "select * from atividade atv join atividade_usuario on id = atividade_id where usuario_id = ? and atividade_usuario.status = 1";
        String sqlStatus = "select * from atividade atv join atividade_usuario on id = atividade_id where usuario_id = ? and atividade_usuario.status = 1 and atv.status = ?";

        ResultSet rs;
        if (status.equals("todos")) {
            rs = executeResutSet(sqlTodos, idAnalista);
        } else {
            rs = executeResutSet(sqlStatus, idAnalista, status);
        }

        List<Atividade> list = new ArrayList<>();

        while (rs.next()) {
            Atividade atividade = new Atividade();
            atividade.setId(rs.getLong("id"));
            atividade.setNome(rs.getString("nome"));
            atividade.setDescricao(rs.getString("descricao"));
            atividade.setComplexidade(rs.getInt("complexidade"));
            atividade.setDataCriacao(rs.getTimestamp("data_criacao"));
            atividade.setDataEntrega(rs.getDate("data_entrega"));
            atividade.setCep(rs.getString("cep"));
            atividade.setEndereco(rs.getString("endereco"));
            atividade.setEnderecoNumero(rs.getString("numero_endereco"));
            atividade.setComplemento(rs.getString("complemento"));
            atividade.setCidade(rs.getString("cidade"));
            atividade.setUf(rs.getString("uf"));
            atividade.setStatus(rs.getString("status"));
            Projeto projeto = new Projeto();
            projeto.setId(rs.getLong("projeto_id"));
            atividade.setProjeto(projeto);
            list.add(atividade);

        }

        return list;
    }

    public Atividade editarAtividade(Atividade atividade) throws SQLException, IOException, ClassNotFoundException{

        String sql = "update atividade set nome = ?, descricao = ?, complexidade = ?, data_criacao = ?," +
                "data_entrega = ?, cep = ?, endereco = ?, numero_endereco = ?, complemento = ?, cidade = ?," +
                "uf = ?, status = ? where id = ?";

        super.executeQuery(sql,atividade.getNome(), atividade.getDescricao(), atividade.getComplexidade(),
                atividade.getDataCriacao(), atividade.getDataEntrega(), atividade.getCep(), atividade.getEndereco(),
                atividade.getEnderecoNumero(), atividade.getComplemento(), atividade.getCidade(), atividade.getUf(), atividade.getStatus(), atividade.getId());


        return atividade;
    }

}

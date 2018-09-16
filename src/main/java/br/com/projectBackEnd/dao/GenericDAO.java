package br.com.projectBackEnd.dao;

import java.io.IOException;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;


public class GenericDAO {
    
    ConexaoMysql con;
    
    public ResultSet executeResutSet(String sql, Object... params) throws SQLException, ClassNotFoundException, IOException {
        con = ConexaoMysql.getConnection();  
        PreparedStatement ps = con.getPreparedStatement(sql);
        
        for (int i = 0; i < params.length; i++) {
            ps.setObject(i+1, params[i]);          
        }
        return ps.executeQuery();
    }
    
    public long executeQuery(String sql, Object... params) throws SQLException, ClassNotFoundException, IOException {
        con = ConexaoMysql.getConnection();  
        PreparedStatement ps = con.getPreparedStatement(sql);
        
        for (int i = 0; i < params.length; i++) {
        	if (params[i] instanceof Calendar) {
        		Calendar data = (Calendar) params[i];
        		params[i] = data.getTime();
        	}
            ps.setObject(i+1, params[i]);          
        }    
        
        long retorno = ps.executeUpdate();
        
        try (ResultSet generatedKeys = ps.getGeneratedKeys()) {
            if (generatedKeys.next()) {
                retorno = generatedKeys.getLong(1);
            }            
        }
        
        return retorno;
    }
}
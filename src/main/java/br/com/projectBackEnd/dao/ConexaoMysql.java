package br.com.projectBackEnd.dao;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

public class ConexaoMysql {
    private static Connection connection;
    private final static ConexaoMysql conexaoMysql = new ConexaoMysql();
    private static Properties prop;
    
    public ConexaoMysql() {
    	super();
    	prop = new Properties();
    	Class<? extends ConexaoMysql> cls = this.getClass();
        InputStream is = cls.getResourceAsStream("/db.properties");
        try {
        	prop.load(is);        	
		} catch (IOException e) {
			e.getMessage();
		}
    }
        
    public static ConexaoMysql getConnection() throws SQLException, ClassNotFoundException, IOException {
        if (connection == null || connection.isClosed()){
            conexaoMysql.abreConexao();            
        }
        return conexaoMysql;
    }
    
    public static void beginTransaction() throws SQLException, ClassNotFoundException, IOException {
    	getConnection();
    	connection.setAutoCommit(false);
    }
    
    public static void commit() throws SQLException {
    	connection.commit();
    	connection.setAutoCommit(true);
    }
    
    public static String getDriver() {
    	return prop.getProperty("driver");
    }
    
    public static String getConnectionString() {
    	return prop.getProperty("connectionstring");
    }
    
    public static String getUser() {
    	return prop.getProperty("user");
    }
    
    public static String getPassword() {
    	return prop.getProperty("password");
    }
    
    public void abreConexao() throws ClassNotFoundException, SQLException, IOException{
        Class.forName(getDriver());
        
        connection = DriverManager.getConnection(
        		getConnectionString(), 
        		getUser(),
        		getPassword());
    }
    
    public void fechaConexao() throws SQLException {
        if (!connection.isClosed()) {
            connection.close();      
        }
    }
    
    public PreparedStatement getPreparedStatement(String sql) throws SQLException {
        return connection.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
    }
    
    public static int getId() {
    	return conexaoMysql.hashCode();
    }
}
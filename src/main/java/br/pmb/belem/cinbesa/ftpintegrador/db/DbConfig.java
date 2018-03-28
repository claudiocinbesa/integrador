/*
 * Sets de acesso ao banco
 */
package br.pmb.belem.cinbesa.ftpintegrador.db;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claudio Martins
 */
public class DbConfig {

    public static final String _urlIP = "10.1.200.241";   // DEV: 10.1.200.241  PROD= DEV: 10.1.200.240
    public static final String _dbName = "bdsemus";
    public static final String _user = "sa";
    public static final String _pwd = "semus";

    public static ResultSet getResultSet(String sql) {
        // Declare the JDBC objects.  
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        // Create a variable for the connection string. 
        String connectionUrl = "jdbc:sqlserver://" + DbConfig._urlIP
                + ";databaseName=" + DbConfig._dbName
                + ";user=" + DbConfig._user
                + ";password=" + DbConfig._pwd
                + "";

        try {
            // Establish the connection. 
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
            con = DriverManager.getConnection(connectionUrl);
            // Create and execute an SQL statement that returns some data.
            String SQL = sql;
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
        } catch (ClassNotFoundException ex) {
            Logger.getLogger(DbConfig.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(DbConfig.class.getName()).log(Level.SEVERE, null, ex);
        }
        
        return rs;
    }
}

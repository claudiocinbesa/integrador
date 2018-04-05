/*
 * Sets de acesso ao banco
 */
package br.pmb.belem.cinbesa.ftpintegrador.db;

import br.pmb.belem.cinbesa.ftpintegrador.ftp.Propriedades;
import br.pmb.belem.cinbesa.ftpintegrador.utils.PWSec;
import com.microsoft.sqlserver.jdbc.SQLServerDataSource;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 *
 * @author Claudio Martins
 */
public class DbConfig {

    public static final String _urlIP = Propriedades.getPropriedade("db.url");   // DEV: 10.1.200.241  PROD= DEV: 10.1.200.240
    public static final String _dbName = Propriedades.getPropriedade("db.name"); // "bdsemus";
    public static final String _user = Propriedades.getPropriedade("db.user"); // "sa";
    public static final String _pwd = PWSec.decrypt(Propriedades.getPropriedade("db.pwd"));

    public static String DB_H2_DRIVER = "org.h2.Driver";
    public static String DB_SQLSERVER_DRIVER = "com.microsoft.sqlserver.jdbc.SQLServerDriver";

    public static ResultSet getResultSetSQL(String sql) {
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
            Class.forName(DB_SQLSERVER_DRIVER);
            con = DriverManager.getConnection(connectionUrl);
            // Create and execute an SQL statement that returns some data.
            String SQL = sql;
            stmt = con.createStatement();
            rs = stmt.executeQuery(SQL);
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DbConfig.class.getName()).log(Level.SEVERE, null, ex);
        }

        return rs;
    }

    static DataSource getDataSource() {
        // acessa o dataSource nativo do SQLServer
        SQLServerDataSource ds = new SQLServerDataSource();
        ds.setUser(DbConfig._user);
        ds.setPassword(DbConfig._pwd);
        ds.setServerName(DbConfig._urlIP);
        // ds.setPortNumber(1433);
        ds.setDatabaseName(DbConfig._dbName);
        return ds;
    }
}

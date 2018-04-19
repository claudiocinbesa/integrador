/*
 * Sets de acesso ao banco de dados DW (data-warehouse)
 */
package br.pmb.belem.cinbesa.ftpintegrador.receive.db;

import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
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
import org.postgresql.ds.PGPoolingDataSource;

/**
 *
 * @author Claudio Martins
 */
public class DbConfig {

    public static final String _urlIP = Propriedades.getPropriedade("db.url");
    public static final String _dbName = Propriedades.getPropriedade("db.name");
    public static final String _user = Propriedades.getPropriedade("db.user");
    public static final String _pwd = Propriedades.getPropriedade("db.pwd");  // PWSec.decrypt(

    public static String DB_PG_DRIVER = "org.postgresql.Driver";

    public static ResultSet getResultSetSQL(String sql) {
        // Declare the JDBC objects.  
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        // Create a variable for the connection string. 
        String connectionUrl = "jdbc:postgresql://" + DbConfig._urlIP
                + "/" + DbConfig._dbName
                + "?user=" + DbConfig._user
                + "&password=" + DbConfig._pwd
                + "";

        try {
            // Establish the connection. 
            Class.forName(DB_PG_DRIVER);
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
        // acessa o dataSource do PW

        PGPoolingDataSource source = new PGPoolingDataSource();
       // source.setDataSourceName("A Data Source");
        source.setServerName(DbConfig._urlIP);
        source.setDatabaseName(DbConfig._dbName);
        source.setUser(DbConfig._user);
        source.setPassword(DbConfig._pwd);
        source.setMaxConnections(10);

        return source;
    }
}

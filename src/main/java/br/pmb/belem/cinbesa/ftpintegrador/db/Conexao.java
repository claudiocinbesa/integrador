/**
 * No exemplo a seguir, o código de exemplo define várias propriedades de
 * conexão usando métodos setter do SQLServerDataSource objeto e, em seguida,
 * chama o getConnection método o O objeto SQLServerDataSource para retornar um
 * SQLServerConnection objeto. Em seguida, o código de exemplo usa o prepareCall
 * método do objeto SQLServerConnection para criar um SQLServerCallableStatement
 * objeto e, em seguida, o executeQuery método é chamado para executar o
 * procedimento armazenado. Por fim, o exemplo usa o SQLServerResultSet objeto
 * retornado do método executeQuery para iterar pelos resultados retornados pelo
 * procedimento armazenado. FONTE:
 * https://docs.microsoft.com/pt-br/sql/connect/jdbc/data-source-sample
 */
package br.pmb.belem.cinbesa.ftpintegrador.db;

import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;

/**
 * @author Claudio Martins
 */
public class Conexao {

    public static String DB_H2_DRIVER = "org.h2.Driver";

    public static void main(String[] args) {
        // Declare the JDBC objects.  
        Connection con = null;
        PreparedStatement cstmt = null;   // se for usar uma chamada de Call = CallableStatement
        ResultSet rs = null;

        try {
            // Establish the connection.   
            SQLServerDataSource ds = new SQLServerDataSource();
            ds.setUser( DbConfig._user);
            ds.setPassword(DbConfig._pwd);
            ds.setServerName(DbConfig._urlIP);
            ds.setPortNumber(1433);
            ds.setDatabaseName(DbConfig._dbName);
            con = ds.getConnection();

            // Execute a stored procedure that returns some data.  
            // cstmt = con.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");
            // cstmt.setInt(1, 50);
            
            cstmt = con.prepareStatement("SELECT TOP 1000 * FROM  [dbo].[Geral_Paciente]\n" +
                        "WHERE [DataCriacao] > ?;");
            cstmt.setString(1, "20180301");
            rs = cstmt.executeQuery();

            // Iterate through the data in the result set and display it.  
            while (rs.next()) {
                System.out.println("PACIENTE: " + rs.getString(1)
                        + ", " + rs.getString(2));
                
            }
        } // Handle any errors that may have occurred.  
        catch (Exception e) {
            e.printStackTrace();
        } finally {
            if (rs != null) {
                try {
                    rs.close();
                } catch (Exception e) {
                }
            }
            if (cstmt != null) {
                try {
                    cstmt.close();
                } catch (Exception e) {
                }
            }
            if (con != null) {
                try {
                    con.close();
                } catch (Exception e) {
                }
            }
            System.exit(1);
        }
    }
}

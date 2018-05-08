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
package br.pmb.belem.cinbesa.ftpintegrador.send.db;

import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import java.sql.*;
import com.microsoft.sqlserver.jdbc.*;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.sql.DataSource;

/**
 * @author Claudio Martins
 */
public class Conexao {

    // Declare the JDBC objects.  
    Connection conn = null;
    PreparedStatement cstmt = null;   // se for usar uma chamada de Call = CallableStatement
    ResultSet rs = null;
  
    public Conexao() {
        try {
            // Establish the connection.
           // DataSource ds = DbConfig.getDataSource();
            // this.conn = ds.getConnection();
            this.conn = DbConfig.getConexao();
            System.out.println ("CONECTOU ...." + this.conn.getSchema());
        } catch (SQLServerException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Connection getConnection() {
        return this.conn;
    }

    public void fecha() {
        if (this.rs != null) {

            try {
                this.rs.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (this.cstmt != null) {

            try {
                this.cstmt.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
        if (this.conn != null) {

            try {
                this.conn.close();
            } catch (SQLException ex) {
                Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
            }

        }
    }


     ResultSet executaSqlPacientes(String dataRef) {

        try {
            // Execute a stored procedure that returns some data.
            // cstmt = con.prepareCall("{call dbo.uspGetEmployeeManagers(?)}");
            // cstmt.setInt(1, 50);
            cstmt = this.conn.prepareStatement("SELECT TOP 1000 * FROM  [dbo].[Geral_Paciente]\n"
                    + "WHERE [DataCriacao] > ?;");
            cstmt.setString(1, dataRef);
            rs = cstmt.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    
    public ResultSet executaSql (String sql) {

        try {
            cstmt = this.conn.prepareStatement(sql);
            rs = cstmt.executeQuery();
            return rs;
        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
    
    public static void main(String[] args) {
        try {
            Propriedades props = Propriedades.getInstanceSingleton(null, "configSEND.properties");
            Conexao oConn = new Conexao();
            String sql = "";
            ResultSet rs = oConn.executaSqlPacientes("20180301");

            // Iterate through the data in the result set and display it.
            while (rs.next()) {
                System.out.println("PACIENTE: " + rs.getString(1)
                        + ", " + rs.getString(2));
            }
            oConn.fecha();

        } catch (SQLException ex) {
            Logger.getLogger(Conexao.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

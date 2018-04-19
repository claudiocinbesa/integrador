/**
 * Classe de conexão ao banco DW
 */
package br.pmb.belem.cinbesa.ftpintegrador.receive.db;
 
import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import java.sql.*;
 
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
            DataSource ds = DbConfig.getDataSource();
            this.conn = ds.getConnection();
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
            cstmt = this.conn.prepareStatement("SELECT * FROM dbo.Geral_Paciente \n"
                    + " LIMIT 10");
                    // + "WHERE  DataCriacao  > ?;");
          //  cstmt.setString(1, dataRef);
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
        Propriedades props =  Propriedades.getInstanceSingleton(null, "configRECEIVE.properties"); 
        try {
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

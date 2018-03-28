/*
 * The following example retrieves data from a database table and writes it 
 * into a CSV file. We use MySQL database. 
 * FONTE: http://zetcode.com/articles/opencsv/
 */
package br.pmb.belem.cinbesa.ftpintegrador.csv;

import br.pmb.belem.cinbesa.ftpintegrador.db.DbConfig;
import com.opencsv.CSVWriter;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claudio Martins
 */
public class OpenCSVDatabaseEx {

    public static void main(String[] args) {
        System.out.println("gravando CSV....");
        try {
            String fileName = "c:/tmp/csv/pacientes.csv";                // "src/main/resources/cars.csv";
            Path myPath = Paths.get(fileName);

            ResultSet rs = DbConfig.getResultSet("SELECT TOP 100000  * FROM  dbo.Geral_Paciente;");
            // SELECT TOP 4000 * FROM  dbo.Geral_Paciente;

            CSVWriter writer = new CSVWriter(
                    Files.newBufferedWriter(myPath, StandardCharsets.UTF_8)
                    , CSVWriter.DEFAULT_SEPARATOR // '|'        
                    , '"' // CSVWriter.NO_QUOTE_CHARACTER
                    , CSVWriter.NO_ESCAPE_CHARACTER
                    , CSVWriter.RFC4180_LINE_END   // "\n"  // CSVWriter.DEFAULT_LINE_END
            );
            /*  CSVWriter.DEFAULT_SEPARATOR,
             CSVWriter.NO_QUOTE_CHARACTER,
             CSVWriter.NO_ESCAPE_CHARACTER,
             CSVWriter.DEFAULT_LINE_END);
             */
            int linhasGravadas = writer.writeAll(rs, true, true, false);
            System.out.println("LINHAS GRAVADAS=" + linhasGravadas);
            writer.flush();
            writer.close();
            rs.close();
        } catch (IOException ex) {
            Logger.getLogger(OpenCSVDatabaseEx.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SQLException ex) {
            Logger.getLogger(OpenCSVDatabaseEx.class.getName()).log(Level.SEVERE, null, ex);
        }

    }
}

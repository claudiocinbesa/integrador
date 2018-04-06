package br.pmb.belem.cinbesa.ftpintegrador;

import br.pmb.belem.cinbesa.ftpintegrador.csv.OpenCSVDatabaseEx;
import br.pmb.belem.cinbesa.ftpintegrador.db.Conexao;
import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Stream;

/**
 * Esta classe recupera os SQLs e gera os arquivos CSV na pasta csv (destino)
 *
 * @author Claudio Martins
 */
public class GeradorCSV {

    // pega os tipos de SQL que depende da tabela
    public static void geraCSVs(String dataRef) {
        Map<String, String> parametros = new HashMap<>();
        String sql = "";
        Conexao c = new Conexao();
        ResultSet rs = null;

        
        // gera paciente
        parametros.put("{DTCRIACAO}", dataRef);
        parametros.put("{DTALTERACAO}", dataRef);
        sql = new GeradorCSV().lerSQL("Template-Geral_Paciente.sql", parametros);
        // System.out.println(sql);
        rs = c.executaSql(sql);
        OpenCSVDatabaseEx.exportaCSVfromRecordSet(rs, "pacientes.csv");

        // gera AvaliacaoIncial
        parametros.put("{DTCRIACAO}", dataRef);
        parametros.put("{DTEXCLUSAO}", dataRef);
        parametros.put("{DTINIAVALIACAO}", dataRef);
        parametros.put("{DTFIMAVALIACAO}", dataRef);
        sql = new GeradorCSV().lerSQL("Template-PAS_AvaliacaoInicial.sql", parametros);
        rs = c.executaSql(sql);
        OpenCSVDatabaseEx.exportaCSVfromRecordSet(rs, "avaliacaoinicial.csv");

        // FilaAtendimento
        parametros.put("{DTATENDIMENTO}", dataRef);
        parametros.put("{DTCRIACAO}", dataRef);
        parametros.put("{DTINIATENDIMENTO}", dataRef);
        parametros.put("{DTFIMATENDIMENTO}", dataRef);
        parametros.put("{DTEXCLUSAO}", dataRef);
        sql = new GeradorCSV().lerSQL("Template-PAS_FilaAtendimento.sql", parametros);
        rs = c.executaSql(sql);
        OpenCSVDatabaseEx.exportaCSVfromRecordSet(rs, "filaatendimento.csv");

        // fecha a conexao ao banco.
        c.fecha();

    }

    public String lerSQL(String fileSQL, Map<String, String> param) {
        FileInputStream stream = null;
        try {
            String sqlTxt = "";
            stream = new FileInputStream(GeradorCSV.class.getClassLoader().getResource(fileSQL)
                    .getFile());
            InputStreamReader reader = new InputStreamReader(stream);
            BufferedReader br = new BufferedReader(reader);
            String linha = br.readLine();

            while (linha != null) {
                for (Map.Entry<String, String> p : param.entrySet()) {
                    String key = p.getKey();
                    String value = p.getValue();
                    if (linha.contains(key)) {
                        linha = linha.replace(key, value);
                    }
                }

                // if (linha.contains("{DTCRIACAO}")) 
                //    linha = linha.replace("{DTCRIACAO}", "<>");
                // System.out.println(linha);
                sqlTxt = sqlTxt + linha + " \n";
                linha = br.readLine();
            }
            return sqlTxt;
        } catch (FileNotFoundException ex) {
            Logger.getLogger(GeradorCSV.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            Logger.getLogger(GeradorCSV.class.getName()).log(Level.SEVERE, null, ex);
        } finally {
            try {
                stream.close();
            } catch (IOException ex) {
                Logger.getLogger(GeradorCSV.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
        return null;
    }

    public static void main(String[] args) throws IOException, SQLException {
        Map<String, String> parametros = new HashMap<>();
        parametros.put("{DTCRIACAO}", "'20180302'");
        parametros.put("{DTALTERACAO}", "'20180302'");
        String sql = new GeradorCSV().lerSQL("Template-Geral_Paciente.sql", parametros);
        System.out.println(sql);
        Conexao c = new Conexao();
        ResultSet rs = c.executaSql(sql);
        while (rs.next()) {
            System.out.println(rs.getString(1));
        }
    }

}

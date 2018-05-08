package br.pmb.belem.cinbesa.ftpintegrador.receive.csv;

import br.pmb.belem.cinbesa.ftpintegrador.receive.db.Conexao;
import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * The example below shows how to read and parse a CSV file using OpenCSV
 * library. It reads the CSV records one by one into a String array Em
 * https://www.callicoder.com/java-read-write-csv-file-opencsv/
 *
 * @author claudio martins
 */
public class CsvReaderWriterDB {

    private static Map<String, String> mapCampoTipo = new HashMap<String, String>();
    private static String nomeTabela;
    private static String pkTabela;
    private static ArrayList<Integer> pkIndice;

    private static Conexao conn;

    public static void main(String[] args) throws IOException {
        String SAMPLE_CSV_FILE_PATH = "c:/tmp/ftp/temp/pacientes.csv";
        String fileProp = "tabPACIENTE";
        load2WriteDB(SAMPLE_CSV_FILE_PATH, fileProp, null);
    }

    public static void load2WriteDB(String filePathCSV, String nomeFilePropriedade, Propriedades props) throws IOException {
        if (props == null) {
            props = Propriedades.getInstanceSingleton(null, "configRECEIVE.properties"); // Deve inicializar no inicio do processo          
        }
        conn = new Conexao();

        try (
                Reader reader = Files.newBufferedReader(Paths.get(filePathCSV));
                CSVReader csvReader = new CSVReader(reader);) {

            // Reading Records One by One in a String array
            String[] header = null;

            int contRec = 0;
            // csvReader.readNext();  // 1a linha = HEADER

            // System.out.println(sql);
            String[] nextRecord;

            while ((nextRecord = csvReader.readNext()) != null) {
                contRec++;
                if (contRec == 1) {
                    header = nextRecord;
                    mapeiaCamposTipos(nomeFilePropriedade, header);
                }
                if (contRec > 1) {
                    executaSQL(nomeTabela, header, nextRecord);
                }
            }
        }
        System.out.println(" ** OK GRAVOU ");
        conn.fecha();
    }

    private static void mapeiaCamposTipos(String tabelaNome, String[] header) {
        if (!tabelaNome.toLowerCase().contains(".properties")) {
            tabelaNome = tabelaNome + ".properties";
        }

        Propriedades p = Propriedades.getInstanceNew(tabelaNome);
        nomeTabela = p.getValor("tabela.nome");
        pkTabela = p.getValor("tabela.pk");
        // String[] tokensPK = pkTabela.split(",");
        pkIndice = new ArrayList<Integer>();                                  // new int[tokensPK.length];

        System.out.println("NOME DA TABELA = " + nomeTabela);
        for (int i = 0; i < header.length; i++) {
            String campo = header[i].toLowerCase();
            String tipo = p.getValor(campo);
            System.out.println(" CAMP = " + campo + "            TP="+ tipo);
            if (pkTabela.contains(campo)) {
                pkIndice.add(new Integer(i));
            }
            mapCampoTipo.put(campo, tipo);
            // System.out.println(campo + " TIPO = " + tipo);
        }
    }

    private static String mapeiaValor(String campo, String valor) {
        
        String tipo = mapCampoTipo.get(campo.toLowerCase()).trim();
        String resValor = valor;
        if (valor.contains("'")) {
            valor = valor.replace("'", "''");
        }
        if (tipo.equals("int")) {
            if (valor.trim().length() == 0) {
                resValor = "NULL";
            } else {
                resValor = valor;
            }
        } else if (tipo.equals("str")) {
            resValor = "'" + valor + "'";
        } else if (tipo.equals("date")) {
            if (valor.trim().length() == 0) {
                resValor = "NULL";
            } else {
                resValor = "'" + valor + "'";
            }
        } else {
            resValor = "<" + valor + ">";
        };
        return resValor;
    }

    private static void executaSQL(String nomeTabela, String[] header, String[] valores) {
        try {
            // monta o INSERT
            String sql = montaInsert(nomeTabela, header, valores);
           // if (nomeTabela.toLowerCase().contains("avaliacaoinicial"))
            //     System.out.println (sql);
            conn.executaSqlUpdate(sql);
        } catch (SQLException ex) {
            // Logger.getLogger(CsvReaderWriterDB.class.getName()).log(Level.SEVERE, null, ex);
             // System.err.println(" FALHA NA INCLUSAO CAUSA = " + ex.getMessage());
            // Logger.getLogger(CsvReaderWriterDB.class.getName()).log(Level.SEVERE, null, ex);
            if (ex.getMessage().contains("duplicate key")) {
                // faz update o UPDATE
                String sql = montaUpdade(nomeTabela, header, valores);
                try {
                    conn.executaSqlUpdate(sql);
                    // System.out.println(" Atualizou com UPDATE..");
                } catch (SQLException ex1) {
                    System.err.println(" FALHA No update CAUSA = " + ex1.getMessage());
                    Logger.getLogger(CsvReaderWriterDB.class.getName()).log(Level.SEVERE, null, ex1);
                }
            }
        }

    }

    private static String montaSqlCamposInsert(String[] header) {
        String stSQL = "";
        String virgula = "";
        for (int i = 0; i < header.length; i++) {
            String campo = header[i].toLowerCase();

            if (i < header.length - 1) {
                virgula = ", ";
            } else {
                virgula = " ";
            }
            stSQL = stSQL + campo + virgula;
        }

        return stSQL;
    }

    private static String montaSqlCamposValoresUpdate(String[] header, String[] valores) {
        String stSQL = "";
        String virgula = "";
        for (int i = 0; i < header.length; i++) {
            String campo = header[i].toLowerCase();

            if (i < header.length - 1) {
                virgula = ", ";
            } else {
                virgula = " ";
            }

            String valor = mapeiaValor(campo, valores[i]);

            stSQL = stSQL + campo + " = " + valor + virgula;
        }

        return stSQL;
    }

    private static String montaInsert(String nomeTabela, String[] header, String[] valores) {
        String sqlFirst = "INSERT INTO " + nomeTabela + "  (" + montaSqlCamposInsert(header) + ") \n  "
                + "   VALUES (";
        String pkValor = "";
        String virgula = "";
        String sqlValores = "";
        for (int i = 0; i < header.length; i++) {
            String campo = header[i];
// System.out.println( "TABELA= "+nomeTabela+  "   CAMPO = " + campo);
            String valor = mapeiaValor(campo, valores[i]);
           // if (campo.equalsIgnoreCase(pkCampo)) {
            //    pkValor = valor;
            // }
            if (i < header.length - 1) {
                virgula = ", ";
            } else {
                virgula = " ";
            }
            sqlValores = sqlValores + valor + virgula;
            // System.out.println(campo + " : " + nextRecord[i] + "   => " + valor);
        }
        sqlValores = sqlValores + " );";
        return sqlFirst + " " + sqlValores;
    }

    private static String montaUpdade(String nomeTabela, String[] header, String[] valores) {
        String clausulaWHERE = "";
        if (pkIndice.size() == 0) {
            clausulaWHERE = "; ";
        } else {
            clausulaWHERE = "  WHERE ";

            int cont = 0;
            for (Integer pkInd : pkIndice) {
                int i = pkInd.intValue();
                clausulaWHERE = clausulaWHERE
                        + header[i] + " = " + mapeiaValor(header[i], valores[i]);
                cont++;
                if (cont == pkIndice.size()) {
                    clausulaWHERE = clausulaWHERE + ";";
                            
                } else {
                    clausulaWHERE = clausulaWHERE + " AND ";
                }
            }
        }

        String sql = "UPDATE " + nomeTabela + "  SET " + montaSqlCamposValoresUpdate(header, valores) + " \n  "
                + clausulaWHERE;
       // if (nomeTabela.toLowerCase().contains("avaliacaoinicial"))
         //   System.out.println (sql);
        return sql;
    }

}

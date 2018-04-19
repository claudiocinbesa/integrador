package br.pmb.belem.cinbesa.ftpintegrador.receive.csv;

import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import com.opencsv.CSVReader;
import java.io.IOException;
import java.io.Reader;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Map;

/**
 * The example below shows how to read and parse a CSV file using OpenCSV
 * library. It reads the CSV records one by one into a String array Em
 * https://www.callicoder.com/java-read-write-csv-file-opencsv/
 *
 * @author claudio martins
 */
public class OpenCSVReader {
 
    static public Map<String, String> mapCampoTipo = new HashMap<String, String>();
    
    public static void main(String[] args) throws IOException {
        String SAMPLE_CSV_FILE_PATH = "c:/tmp/ftp/temp/pacientes.csv";
        
        mapeiaCamposTipos("tabPACIENTE");
        
        try (
                Reader reader = Files.newBufferedReader(Paths.get(SAMPLE_CSV_FILE_PATH));
                CSVReader csvReader = new CSVReader(reader);) {

            // Reading Records One by One in a String array
            String[] header = csvReader.readNext();  // 1a linha = HEADER
            if (header == null) {
                System.err.println("ARQUIVO VAZIO = " + SAMPLE_CSV_FILE_PATH);
            }
            for (int i = 0; i < header.length; i++) {
                String campo = header[i];
                System.out.println(campo);

            }
            System.out.println();
            String[] nextRecord;
            while ((nextRecord = csvReader.readNext()) != null) {

                for (int i = 0; i < header.length; i++) {
                    String campo = header[i];
                    String valor = mapeia (campo, nextRecord[i]);
                    System.out.println(campo + " : " + nextRecord[i] + "   => " + valor);
                }
                System.out.println("==========================");
                break;
            }
        }
    }

    private static void mapeiaCamposTipos(String tabelaNome){
        Propriedades p = Propriedades.getInstanceNew(tabelaNome);
        
    }
    
    private static String mapeia( String campo, String valor) {
        
        return null;
    }
    
    
}

package br.pmb.belem.cinbesa.ftpintegrador.packager;

import br.pmb.belem.cinbesa.ftpintegrador.utils.ArquivoDiretorio;
import br.pmb.belem.cinbesa.ftpintegrador.utils.Logging;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.zip.ZipEntry;
import java.util.zip.ZipOutputStream;
import javax.swing.JOptionPane;

public class CompactaArquivo {

    // intancia o log
    final static Logging logger = Logging.getLogger(CompactaArquivo.class);

    // These are the files to include in the ZIP file
    String[] filenames = new String[]{"f:/temp/copia2/CLIENTE.csv",
        "c:/temp/copia2/SERVICO.csv"};
    String outFilename = "c:/temp/backupCSV.zip";
    // Create a buffer for reading the files
    byte[] buf = new byte[1024];

    public CompactaArquivo(String[] filenames, String outFilename) {
        super();
        this.filenames = filenames;
        this.outFilename = outFilename;
        executaCompactacao();
    }

    public CompactaArquivo() {
        executaCompactacao();
    }

    private void executaCompactacao() {
        try {
            // Create the ZIP file
            ZipOutputStream out = new ZipOutputStream(new FileOutputStream(
                    outFilename));
            // Compress the files
            for (int i = 0; i < filenames.length; i++) {
                //       System.out.println("zipando o arquivo " + filenames[i]);

                FileInputStream in = new FileInputStream(filenames[i]);

                // Add ZIP entry to output stream.
                out.putNextEntry(new ZipEntry(ArquivoDiretorio.apenasNomeArquivo(filenames[i])));

                // Transfer bytes from the file to the ZIP file
                int len;
                while ((len = in.read(buf)) > 0) {
                    out.write(buf, 0, len);
                }

                // Complete the entry
                out.closeEntry();
                in.close();
            }

            // Complete the ZIP file
            out.flush();
            out.close();
        } catch (IOException e) {
            logger.printMsgFalha("Erro de I/O ao compactar.. \n \t\t"
                    + e.getMessage());
        }

    }

    

    public static void main(String[] args) {
        new CompactaArquivo();

    }
}

package br.pmb.belem.cinbesa.ftpintegrador.send;

import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import br.pmb.belem.cinbesa.ftpintegrador.packager.CompactaArquivo;
import br.pmb.belem.cinbesa.ftpintegrador.utils.ArquivoDiretorio;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Responsavel por recuperar os arquivos CSV e empacotar em um ZIP para envio
 * por FTP.
 *
 * @author Claudio Martinsl
 */
public class Empacotador {

    public static String enpacotar(String dataRef) {
        try {
            String pathCSVs = Propriedades.getPropriedade("csv.path");

            String[] fileNames = ArquivoDiretorio.getArquivos(pathCSVs, ".csv").getArquivos();
            String fileNameOut = pathCSVs + "/rbe-"
                    + Propriedades.getPropriedade("unid.codigo") + dataRef + ".zip";
            new CompactaArquivo(fileNames, fileNameOut);
            return fileNameOut;
        } catch (IOException ex) {
            Logger.getLogger(Empacotador.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }
}

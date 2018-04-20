package br.pmb.belem.cinbesa.ftpintegrador.receive;

import br.pmb.belem.cinbesa.ftpintegrador.utils.ArquivoDiretorio;
import br.pmb.belem.cinbesa.ftpintegrador.RESTOS.RestauraBackup;
import br.pmb.belem.cinbesa.ftpintegrador.receive.csv.CsvReaderWriterDB;
import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import java.io.IOException;
import java.util.List;

import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmartins
 */
class Importador {

    static public void executarCarga(Propriedades props) {
        ArquivoDiretorio filesDir = getFilesCSV(props.getPropriedade("temp.path"));
        System.out.println("IMPORTACAO....\n" + filesDir.toString());
        List<String> listArquivosCSV = filesDir.nomesArquivosSemExtensao();
        for (String arquivoCSV : listArquivosCSV) {

            String arquivoCSVpath = filesDir.getArquivoFullPath(arquivoCSV + filesDir.getFiltroExt());

            System.out.println("Carregando a tabela " + arquivoCSV.toUpperCase() + "  FROM = " + arquivoCSVpath);

            importa(arquivoCSV, arquivoCSVpath, props);
        }
    }

    private static ArquivoDiretorio getFilesCSV(String path) {
        try {
            String filtro = ".csv";
            ArquivoDiretorio dirArquivo = null;
            boolean ok = false;

            dirArquivo = ArquivoDiretorio.getArquivos(path, filtro);
            if (dirArquivo != null) {
                ok = true;
                return dirArquivo;
            }
            return dirArquivo;
        } catch (IOException ex) {
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    private static void importa(String arquivoCSV, String arquivoCSVpath, Propriedades props) {
        try {
            if (arquivoCSV.toLowerCase().contains("pacientes")) {
                CsvReaderWriterDB.load2WriteDB(arquivoCSVpath, "tabPACIENTE", props);
            } else System.out.println("NAO IMPORTOU O CSV =" + arquivoCSVpath);
        } catch (IOException ex) {
            Logger.getLogger(Importador.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

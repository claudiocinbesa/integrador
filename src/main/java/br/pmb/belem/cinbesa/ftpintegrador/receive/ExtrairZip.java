package br.pmb.belem.cinbesa.ftpintegrador.receive;

import br.pmb.belem.cinbesa.ftpintegrador.utils.ArquivoDiretorio;
import br.pmb.belem.cinbesa.ftpintegrador.RESTOS.RestauraBackup;
import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import java.io.IOException;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmartins
 */
class ExtrairZip {

    static public void executarExtracao(Propriedades props) {
        ArquivoDiretorio filesDir = getFilesZIP(props.getPropriedade("zip.path"));
        System.out.println(filesDir.toString());
        
        String arquivoPath = filesDir.getUltimoArquivoFullPath();
        System.out.println("ULTIMO ARQUIVO = " + arquivoPath);
        
        String diretorioDestinoCSV = props.getPropriedade("temp.path");
        filesDir.extraiArquivos(arquivoPath, diretorioDestinoCSV);
    }

    private static ArquivoDiretorio getFilesZIP(String path) {
       try {
           String filtro = ".zip";
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
    
     
}

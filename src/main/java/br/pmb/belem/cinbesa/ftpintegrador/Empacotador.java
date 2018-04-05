package br.pmb.belem.cinbesa.ftpintegrador;

import br.pmb.belem.cinbesa.ftpintegrador.ftp.Propriedades;
import br.pmb.belem.cinbesa.ftpintegrador.packager.CompactaArquivo;

/**
 * Responsavel por recuperar os arquivos CSV e empacotar em um ZIP para envio 
 * por FTP.
 * @author Claudio Martinsl
 */
public class Empacotador {
    public static void enpacotar(String dataRef) {
       String fileName = Propriedades.getPropriedade("csv.path");
       
       // new CompactaArquivo(filenames, fileName);
    }
}

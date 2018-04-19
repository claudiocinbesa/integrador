package br.pmb.belem.cinbesa.ftpintegrador.send;

import br.pmb.belem.cinbesa.ftpintegrador.send.ftp.Ftp;

/**
 * Responsavel por enviar o arquivo ZIP (empacotado para os arquivos ZIP) e 
 * transfere (por FTP) ao servidor de arquivos.
 * @author Claudio Martins
 */
public class EnviaFTP {

    static void enviar(String fileOutput) {
       // fileOutput = fileOutput.replace("'", "");
        Ftp.enviar(fileOutput);
    }
    
}

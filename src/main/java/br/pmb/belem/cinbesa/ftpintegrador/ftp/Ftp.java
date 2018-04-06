package br.pmb.belem.cinbesa.ftpintegrador.ftp;

import br.pmb.belem.cinbesa.ftpintegrador.Propriedades;
import br.pmb.belem.cinbesa.ftpintegrador.utils.Utils;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.apache.commons.net.ftp.FTPClient;

/*
 A API Commons Net permite o acesso a um servidor FTP. 
 Podemos conectar, logar, enviar e receber arquivos para uma área de FTP.
 a) Um aplicação Java pode verificar se algum arquivo foi modificado na máquina local 
 e enviá-lo para uma área de FTP caso a modificação tenha sido feita. 
 b) Podemos também agendar uma rotina que faça o backup de um servidor para outro,
 escolhendo somente arquivos PDF, por exemplo.
 EM https://www.devmedia.com.br/desenvolvendo-um-cliente-ftp/3547
 */
public class Ftp {

    public static void main(String[] args) throws IOException {
        System.out.println("Transf....");
        enviar("C:\\tmp\\csv\\rbe-UNIDADE'20180302'.zip");
    }

    public static void enviar(String fileName){
        try {
            FTPClient ftp = new FTPClient();
            ftp.connect(Propriedades.getPropriedade("ftp.connect")); //ftp.meudominio.com.br
            ftp.login(Propriedades.getPropriedade("ftp.user"), Propriedades.getPropriedade("ftp.pwd"));
            ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            
            String fileOut = Utils.apenasNomeArquivo(fileName);
            
            FileInputStream arqEnviar = new FileInputStream(fileName);
            
            if (ftp.storeFile(fileOut, arqEnviar)) {
                System.out.println("Arquivo armazenado com sucesso!");
            } else {
                System.out.println("Erro ao armazenar o arquivo.");
            }
            ftp.logout();
            ftp.disconnect();
        } catch (IOException ex) {
            Logger.getLogger(Ftp.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

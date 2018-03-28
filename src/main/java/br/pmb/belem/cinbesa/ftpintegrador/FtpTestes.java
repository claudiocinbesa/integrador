package br.pmb.belem.cinbesa.ftpintegrador;

import java.io.FileInputStream;
import java.io.IOException;
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
public class FtpTestes {

    public static void main(String[] args) throws IOException, FTPException {
        System.out.println("Transf....");
       
        enviar();
    }

    public static void enviar() throws IOException {
        FTPClient ftp = new FTPClient();
        ftp.connect("127.0.0.1"); //ftp.meudominio.com.br
        ftp.login("user", "12345");
        ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
        FileInputStream arqEnviar
                = new FileInputStream("/temp/aulaMicroservice.zip");

        if (ftp.storeFile("meuarquivo2.ZIP", arqEnviar)) {
            System.out.println("Arquivo armazenado com sucesso!");
        } else {
            System.out.println("Erro ao armazenar o arquivo.");
        }
        ftp.logout();
        ftp.disconnect();
    }
}

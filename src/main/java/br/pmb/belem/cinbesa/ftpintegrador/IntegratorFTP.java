package br.pmb.belem.cinbesa.ftpintegrador;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

public class IntegratorFTP {

    FTPClient ftp;
    private boolean estouNoRaiz;

    public IntegratorFTP() throws IOException, FTPException {
        ftp = new FTPClient();
        ftp.connect("127.0.0.1");
        if (FTPReply.isPositiveCompletion(ftp.getReplyCode())) {
            try {
                ftp.login("user", "12345");
                ftp.setFileType(FTPClient.BINARY_FILE_TYPE);
            } catch (IOException ex) {
                throw new FTPException("Não foi possível logar-se ao FTP.");
            }
        } else {
            throw new FTPException("Não foi possível conectar-se ao FTP.");
        }
    }

    public void enviarArquivoe(File arquivo) throws FTPException, FileNotFoundException {
        abrirPasta("/"); // caminho ate a pasta que quero jogar o arquivo...
        FileInputStream is = null;
        try {
            is = new FileInputStream(arquivo);
        } catch (FileNotFoundException e) {
            throw new FTPException("Erro localizar o arquivo " + arquivo.getName());
        }
        try {
            if (!ftp.storeFile(arquivo.getName(), is)) {
                throw new FTPException("Erro ao enviar arquivo: " + arquivo.getName() + " para o servidor.");
            }
        } catch (IOException ex) {
            throw new FTPException("Erro ao enviar arquivo: " + arquivo.getName() + " para o servidor.");
        }
    }

    private void abrirPasta(String caminho) throws FTPException {
        if (!estouNoRaiz) {
            voltarParaODiretorioRaiz();
        }
        try {
            ftp.changeWorkingDirectory(caminho);
            estouNoRaiz = false;
        } catch (IOException ex) {
            throw new FTPException("Erro ao abrir pasta de destino = " + caminho);
        }
    }

    private void voltarParaODiretorioRaiz() throws FTPException {
        while (!estouNoRaiz) {
            try {
                ftp.changeToParentDirectory();
                FTPFile[] arquivos = ftp.listFiles();
                for (FTPFile arquivo : arquivos) {
                    if (arquivo.getName().equals("appservers")) {
                        estouNoRaiz = true;
                    }
                }
            } catch (IOException ex) {
                throw new FTPException("Não foi possível voltar ao diretório raiz.");
            }
        }
    }
}

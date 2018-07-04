package br.pmb.belem.cinbesa.ftpintegrador.send;

import br.pmb.belem.cinbesa.ftpintegrador.RESTOS.RestauraBackup;
import br.pmb.belem.cinbesa.ftpintegrador.utils.ArquivoDiretorio;
import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import br.pmb.belem.cinbesa.ftpintegrador.utils.UtilData;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.Date;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Aplicacao principal (orquestrando a chamada das rotinas)
 *
 * @author Claudio Martins
 */
public class MainSendFTP {

    public static void main(String[] args) {
        String path = null;
        if (args.length == 0) {
            path = null;
        } else {
            path = args[0];
        }
        //
        System.out.println("Inicializando os parametros em " + path);    // "C:/tmp/csv";
        Propriedades.getInstanceSingleton(path, "configSEND.properties"); // Deve inicializar no inicio do processo          
        System.out.println("Caminho(PATH) do CSV = " + Propriedades.getPropriedade("csv.path"));
        System.out.println("URL do banco = " + Propriedades.getPropriedade("db.url"));

        // -- pega o ultimo arquivo gerado (ZIP)
        ArquivoDiretorio filesDir = getFilesZIP(Propriedades.getPropriedade("csv.path"));
        // System.out.println(filesDir.toString());

        String arquivoPath = filesDir.getUltimoArquivoFullPath();
        System.out.println("ULTIMO ARQUIVO = " + arquivoPath);
        String dtNomeUlt = getDataNomeFile(arquivoPath);
        String dtCriacao = getDataCriacaoFile(arquivoPath );
       // System.out.println(" DT ULT NOME = " + dtNomeUlt + "      DT CRIACAO = " + dtCriacao);
        
        // dataCriacaoUltZip = 
        // Workflow:  GeradorCSV -> Enpacotador -> EnviaFTP
        Date dtCorrente = new Date();
        String dataC = UtilData.converteData2yyyyMMdd(dtCorrente);
        String dataNomeArq = "'"+dataC + "'";  // "'20180509'";  -- observar as datas de finais de semanas que devem entrar

        String dataRef = "'"+ dtNomeUlt +"'";
        System.out.println("REF:" + dataRef);
        
        GeradorCSV.geraCSVs(dataRef);
        String fileOutput = Empacotador.enpacotar(  dataNomeArq ); // dataRef);
        EnviaFTP.enviar(fileOutput);
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

    private static String getDataNomeFile(String fileName) {
        int pos = fileName.indexOf("'");
        System.out.println(pos);
        String dt = fileName.substring(pos+1, pos+9);
        return dt;
    }
    private static String getDataCriacaoFile(String fileName) {
        Path path = Paths.get(fileName);
        BasicFileAttributes attr;
        try {
            attr = Files.readAttributes(path, BasicFileAttributes.class);
            System.out.println("Creation date: " + attr.creationTime());
            //System.out.println("Last access date: " + attr.lastAccessTime());
            //System.out.println("Last modified date: " + attr.lastModifiedTime());
            Date creationDate =
                    new Date(attr.creationTime().to(TimeUnit.MILLISECONDS));
           /* 
           System.out.println("DATA CRIACAO = "+ creationDate.getDate() + "/" +
                    (creationDate.getMonth() + 1) + "/" +
                    (creationDate.getYear() + 1900));
           */
           return UtilData.converteData2yyyyMMdd(creationDate);
           
        } catch (IOException e) {
            System.out.println("oops error! " + e.getMessage());

        }
        return null;
    }
}

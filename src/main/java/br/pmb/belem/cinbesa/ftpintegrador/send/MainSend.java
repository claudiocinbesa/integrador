package br.pmb.belem.cinbesa.ftpintegrador.send;

import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;
import br.pmb.belem.cinbesa.ftpintegrador.utils.UtilData;
import java.util.Date;

/**
 * Aplicacao principal (orquestrando a chamada das rotinas)
 *
 * @author Claudio Martins
 */
public class MainSend {

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
        // 
        // Workflow:  GeradorCSV -> Enpacotador -> EnviaFTP
        Date dtCorrente = new Date();
        String dataC = UtilData.converteData2yyyyMMdd(dtCorrente);
       // String dataRef = "'"+dataC + "'";  // "'20180509'";  -- observar as datas de finais de semanas que devem entrar
        String dataRef = "'20170101'";  
        System.out.println("REF:" + dataRef);
        GeradorCSV.geraCSVs(dataRef);
        String fileOutput = Empacotador.enpacotar(dataRef);
        EnviaFTP.enviar(fileOutput);
    }
}

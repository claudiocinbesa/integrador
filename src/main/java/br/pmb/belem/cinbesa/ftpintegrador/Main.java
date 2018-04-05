package br.pmb.belem.cinbesa.ftpintegrador;

import br.pmb.belem.cinbesa.ftpintegrador.ftp.Propriedades;

/**
 * Aplicacao principal (orquestrando a chamada das rotinas)
 *
 * @author Claudio Martins
 */
public class Main {

    public static void main(String[] args) {
        String path = null;
        if (args.length == 0) {
            path = null;
        } else {
            path = args[0];
        }

        System.out.println("Inicializando os parametros em " + path);    // "C:/tmp/csv";
        Propriedades.getInstance(path); // Deve inicializar no inicio do processo          
        System.out.println("Caminho(PATH) do CSV = " + Propriedades.getPropriedade("csv.path"));

        // Workflow:  GeradorCSV -> Enpacotador -> EnviaFTP
        
        GeradorCSV.geraCSVs("'20180302'");
        
        
        
        
    }
}
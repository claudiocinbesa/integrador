package br.pmb.belem.cinbesa.ftpintegrador;

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

        System.out.println("Inicializando os parametros em " + path);    // "C:/tmp/csv";
        Propriedades.getInstance(path); // Deve inicializar no inicio do processo          
        System.out.println("Caminho(PATH) do CSV = " + Propriedades.getPropriedade("csv.path"));

        // Workflow:  GeradorCSV -> Enpacotador -> EnviaFTP
        String dataRef = "'20170101'";
        GeradorCSV.geraCSVs(dataRef);
        String fileOutput = Empacotador.enpacotar(dataRef);
        EnviaFTP.enviar(fileOutput);
    }
}

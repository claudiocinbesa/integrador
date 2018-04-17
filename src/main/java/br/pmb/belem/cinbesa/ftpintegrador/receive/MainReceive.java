package br.pmb.belem.cinbesa.ftpintegrador.receive;
 
import br.pmb.belem.cinbesa.ftpintegrador.utils.Propriedades;

/**
 * Aplicacao principal (orquestrando a chamada das rotinas)
 *
 * @author Claudio Martins
 */
public class MainReceive {

    public static void main(String[] args) {
        String path = null;
        if (args.length == 0) {
            path = null;
        } else {
            path = args[0];
        }
        //
        System.out.println("Inicializando os parametros em " + path);    // "C:/tmp/csv";
        Propriedades props =  Propriedades.getInstance(path, "configRECEIVE.properties"); // Deve inicializar no inicio do processo          
         
        
        // Workflow:  RestauraBackup ->  ->  
        ExtrairZip.executarExtracao(props);
    }
}

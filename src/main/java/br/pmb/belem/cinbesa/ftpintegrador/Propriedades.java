package br.pmb.belem.cinbesa.ftpintegrador;

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.nio.file.FileSystems;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * Trata os dados de configuraÃ§Ã£o do aplicativo
 *
 * @author Claudio Martins
 */
public class Propriedades {

    private static Propriedades instance = null;

    protected Propriedades() {
    } // Exists only to defeat instantiation.

    Properties prop = new Properties();

    public static String getPropriedade(String key) {
        Propriedades obj = Propriedades.getInstance(null);
        return obj.prop.getProperty(key);
    }

    public static void main(String[] args) {
        // Propriedades oProp = Propriedades.getInstance("C:/tmp/csv"); // Deve inicializar no inicio do processo          
        System.out.println(Propriedades.getPropriedade("db.user"));

    }

    public static Propriedades getInstance(String pathPropriedade) {
        if (instance == null) {
            instance = new Propriedades();
            instance.loadProp(pathPropriedade);
        }
        return instance;
    }

    private void loadProp(String pathPropriedade) {
        boolean temFile = false;
        if (pathPropriedade != null) {
            File configFile = new File(pathPropriedade);
            if (!configFile.exists()) {
                System.out.println("ARQUIVO NAO EXISTE");
            } else {
                temFile = true;
            }
        }

        try {

            InputStream input = null;

            String filename = "config.properties";
            if (temFile) {
                Path path = FileSystems.getDefault().getPath(pathPropriedade, filename);
                input = Files.newInputStream(path);
            } else {
                input = Propriedades.class.getClassLoader().getResourceAsStream(filename);
            }

            if (input == null) {
                System.out.println("Sorry, unable to find " + filename);
                return;
            }

            //load a properties file from class path, inside static method
            prop.load(input);

            // this.csvPath = prop.getProperty("csv.path");
        } catch (IOException ex) {
            Logger.getLogger(Propriedades.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}

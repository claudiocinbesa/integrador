/*
 * Functions utilities
 */
package br.pmb.belem.cinbesa.ftpintegrador.utils;

import java.io.File;
import javax.swing.ImageIcon;

/* Utils.java is used by FileChooserDemo2.java. */
public class Utils {

    public final static String jpeg = "jpeg";
    public final static String jpg = "jpg";
    public final static String gif = "gif";
    public final static String tiff = "tiff";
    public final static String tif = "tif";
    public final static String png = "png";
    public final static String zip = "zip";
    /*
     * Get the extension of a file.
     */

    public static String getExtension(File f) {
        String ext = null;
        String s = f.getName();
        int i = s.lastIndexOf('.');

        if (i > 0 && i < s.length() - 1) {
            ext = s.substring(i + 1).toLowerCase();
        }
        return ext;
    }

    /** Returns an ImageIcon, or null if the path was invalid. */
    public static ImageIcon createImageIcon(String path) {
        java.net.URL imgURL = Utils.class.getResource(path);
        if (imgURL != null) {
            return new ImageIcon(imgURL);
        } else {
            System.err.println("Arquivo não encontrado: " + path);
            return null;
        }
    }
    
    public static String apenasNomeArquivo(String nomeArquivoCompleto) {
        String nomeArq;
        int pos = nomeArquivoCompleto.lastIndexOf("/");
        if (pos <= 0) {
            pos = nomeArquivoCompleto.lastIndexOf("\\");
        }

        nomeArq = nomeArquivoCompleto.substring(pos + 1);
        //     System.out.println("apenas o nome=" + nomeArq);
        return nomeArq;
    }
}

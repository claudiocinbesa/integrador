package br.pmb.belem.cinbesa.ftpintegrador.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import javax.swing.JFileChooser;

/**
 *
 * @author cmartins
 */
public class ArquivoDiretorio {

    public static final String PREFIXO_SERVICO = "bk_SERVICO_";
  
    private String diretorio;
    private Map<String, String> listArq;

    public ArquivoDiretorio() {
        listArq = new HashMap<String, String>();
    }

    public static ArquivoDiretorio getArquivosBackup() throws IOException {
        ArquivoDiretorio diretorio = new ArquivoDiretorio();

        JFileChooser chooser = new JFileChooser();
        // Note: source for ExampleFileFilter can be found in FileChooserDemo,
        // under the demo/jfc directory in the Java 2 SDK, Standard Edition.
        ExampleFileFilter filter = new ExampleFileFilter();
        // filter.addExtension("jpg");
        filter.addExtension("zip");
        filter.setDescription("Arquivo de backup (ZIP)");
        chooser.setFileSelectionMode(JFileChooser.DIRECTORIES_ONLY);
        int returnVal = chooser.showOpenDialog(null);
        if (returnVal == JFileChooser.APPROVE_OPTION) {
             
          // System.out.println("conteudo do diretorio=" + chooser.getCurrentDirectory().getCanonicalPath()
            //     + "/" + chooser.getSelectedFile().getName() + "  dir-select="+chooser.getSelectedFile().getCanonicalPath()                  );
           
           File[] conteudo = new java.io.File( chooser.getSelectedFile().getCanonicalPath()).listFiles();
                   // chooser.getCurrentDirectory().getCanonicalPath() + "/" + chooser.getSelectedFile().getName()).listFiles();
           
            diretorio.setDiretorio(chooser.getSelectedFile().getCanonicalPath());
                    // chooser.getCurrentDirectory().getCanonicalPath()   + "/" + chooser.getSelectedFile().getName());
            for (File file : conteudo) {
                if (file.getName().contains(PREFIXO_SERVICO)) {
                //    System.out.println(" * " + file.getName());
                    diretorio.addArquivo(file.getName());
                }
            }
        //    System.out.println("! escolha dir bk = "+diretorio.toString());
            if (diretorio.getListArq().size() == 0) {
                diretorio = null;
            } else
                return diretorio;
        } else {
            diretorio = null;
        }

        return diretorio;
    }

    private static class ExampleFileFilter extends javax.swing.filechooser.FileFilter {

        private String extensao;
        private File pathFile;
        private String descricao;

        public ExampleFileFilter() {
        }

        @Override
        public boolean accept(File f) {
            pathFile = f;
            if (f.isDirectory()) {
                return true;
            }

            String extension = Utils.getExtension(f);
            if (extension != null) {
                /*
                if (extension.equals(Utils.tiff)
                || extension.equals(Utils.tif)
                || extension.equals(Utils.gif)
                || extension.equals(Utils.jpeg)
                || extension.equals(Utils.jpg)
                || extension.equals(Utils.png)) {
                 * 
                 */
                if (extension.equals(Utils.zip)) {
                    return true;
                } else {
                    return false;
                }
            }

            return false;

        }

        private void addExtension(String ext) {
            extensao = ext;
        }

        private void setDescription(String d) {
            descricao = d;
        }

        @Override
        public String getDescription() {
            return descricao;
        }
    }

    public String getDiretorio() {
        return diretorio;
    }

    public void setDiretorio(String diretorio) {
        this.diretorio = diretorio;
    }

    public Map<String, String> getListArq() {
        return listArq;
    }

    public void setListArq(Map<String, String> listArq) {
        this.listArq = listArq;
    }

    private void addArquivo(String fileName) {
        String diaBackup = fileName.substring(PREFIXO_SERVICO.length(), fileName.indexOf('.'));
        //   System.out.println("k=" + diaBackup + " file=" + fileName);
        String ano;
        String mes = "";
        String dia, hora;


        ano = diaBackup.substring(0, 4);
        mes = diaBackup.substring(4, 6);
        dia = diaBackup.substring(6, 8);
        hora = diaBackup.substring(9, 14);
        hora = hora.replace('-', ':') + 'h';
        String dataFormatada = dia + "/" + mes + "/" + ano + " - " + hora;
        this.listArq.put(diaBackup, dataFormatada);
    }

    public static void main(String[] args) throws Exception {
        ArquivoDiretorio arqs = ArquivoDiretorio.getArquivosBackup();
        // 2011 11 10 _ 17- 01
        // 1234 56 78 9 012 34

        String dtFormatada = "";
        String dia, hora;

        Set<String> lista = arqs.getListArq().keySet();
        for (String dataBk : lista) {
            dtFormatada = arqs.getListArq().get(dataBk);
         //   System.out.println("DIA=" + dataBk + "     : " + dtFormatada);
        }

    }
}

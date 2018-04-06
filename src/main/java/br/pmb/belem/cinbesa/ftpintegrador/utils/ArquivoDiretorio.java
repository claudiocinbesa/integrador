package br.pmb.belem.cinbesa.ftpintegrador.utils;

import java.io.File;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

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

    public String[] getArquivos() {
        Set<String> keys = this.listArq.keySet();
        String[] files = new String[keys.size()];
        int i = 0;
        for (String fileK : keys) {
            files[i] = this.listArq.get(fileK);
            i++;
        }
        return files;
    }

    public static ArquivoDiretorio getArquivos(String pathFiles, String filtroExt) throws IOException {
        ArquivoDiretorio arqsDir = new ArquivoDiretorio();
        arqsDir.diretorio = pathFiles;
        File[] conteudo = new java.io.File(pathFiles).listFiles();
        for (File file : conteudo) {
            if (file.getName().toLowerCase().contains(filtroExt.toLowerCase())) {
                arqsDir.listArq.put(file.getName(), file.getAbsolutePath());
            }
        }
        return arqsDir;
    }

    public Map<String, String> getListArq() {
        return listArq;
    }

    public void setListArq(Map<String, String> listArq) {
        this.listArq = listArq;
    }

    private void addArquivoDATA(String fileName) {
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
        ArquivoDiretorio arqs = ArquivoDiretorio.getArquivos("c:/tmp/csv", ".csv");
        // 2011 11 10 _ 17- 01
        // 1234 56 78 9 012 34

        String dtFormatada = "";
        String dia, hora;

        Set<String> lista = arqs.getListArq().keySet();
        for (String dataBk : lista) {
            dtFormatada = arqs.getListArq().get(dataBk);
            System.out.println("FILES = " + dataBk + "     : " + dtFormatada);
        }
    }

    public String getDiretorio(String string) {
        return diretorio;
    }

}

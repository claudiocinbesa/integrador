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

    public static ArquivoDiretorio getArquivos(String pathFiles) throws IOException {
        ArquivoDiretorio arqsDir = new ArquivoDiretorio();
        arqsDir.diretorio = pathFiles;
        File[] conteudo = new java.io.File(pathFiles).listFiles();
        for (File file : conteudo) {
           // if (file.getName().contains(PREFIXO_SERVICO)) {
            //    System.out.println(" * " + file.getName());
            arqsDir.addArquivo(file.getName());
            // }
        }
        return arqsDir;
    }

    public Map<String, String> getListArq() {
        return listArq;
    }

    public void setListArq(Map<String, String> listArq) {
        this.listArq = listArq;
    }

    private void addArquivo(String fileName) {
        this.listArq.put(fileName, fileName);
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

    public static void main(String[] args) throws Exception {
        ArquivoDiretorio arqs = ArquivoDiretorio.getArquivos("c:/tmp/csv");
        // 2011 11 10 _ 17- 01
        // 1234 56 78 9 012 34

        String dtFormatada = "";
        String dia, hora;

        Set<String> lista = arqs.getListArq().keySet();
        for (String dataBk : lista) {
            dtFormatada = arqs.getListArq().get(dataBk);
            System.out.println("DIA=" + dataBk + "     : " + dtFormatada);
        }
    }

    public String getDiretorio(String string) {
        return diretorio;
    }

}

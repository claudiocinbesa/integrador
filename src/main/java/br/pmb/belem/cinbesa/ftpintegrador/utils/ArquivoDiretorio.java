package br.pmb.belem.cinbesa.ftpintegrador.utils;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author cmartins
 */
public class ArquivoDiretorio {

    public static final String PREFIXO_SERVICO = "rbe-";
    private String diretorio;
    private Map<String, String> listArq;
    private String filtroExt;

    @Override
    public String toString() {
        String saidaFormatada = "";
        saidaFormatada = "DIRETORIO=" + diretorio + "\n" + "TIPO-EXT=" + filtroExt + "\n";

        Set<String> chaves = listArq.keySet();
        for (String chave : chaves) {
            saidaFormatada = saidaFormatada + chave + " = " + listArq.get(chave) + "\n";
        }
        return saidaFormatada;
    }

    public List<String> nomesArquivosSemExtensao() {
        List<String> lista = new ArrayList<>();
        for (String arqNome : classificaListaKeyArquivo()) {
            int i = arqNome.lastIndexOf('.');
            // System.out.println(" ARQ = " + arqNome.substring(0,i));
            lista.add(arqNome.substring(0, i));
        }
        return lista;
    }

    public String ultimaArquivo() {
        List<String> lista = classificaListaKeyArquivo();
        return lista.get(lista.size() - 1);
    }

    private List<String> classificaListaKeyArquivo() {
        Set<String> keys = this.listArq.keySet();
        List<String> lista = new ArrayList<String>(keys); //  (List<String>) keys;
        Collections.sort(lista);
        return lista;
    }

    public ArquivoDiretorio() {
        listArq = new HashMap<String, String>();
    }

    public String[] getArquivosFullPath() {
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
        arqsDir.filtroExt = filtroExt;
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

    public String getArquivoFullPath(String nomeArquivo) {
        return this.listArq.get(nomeArquivo);
    }

    public String getFiltroExt() {
        return filtroExt;
    }

    public String getUltimoArquivoFullPath() {
        return this.listArq.get(ultimaArquivo());
    }

    public void deletaDiretorio(String diretorio) {
        File inFile = new File(diretorio);
        if (inFile.isFile()) {
            inFile.delete();
        } else {
            File files[] = inFile.listFiles();
            if (files != null) {
                for (int i = 0; i < files.length; i++) {
                    deletaDiretorio(files[i].getAbsolutePath());
                }
            }
        }
    }

    public void extraiArquivos(String arquivoOrigemZIP, String diretorioDestino) {
        try {

            deletaDiretorio(diretorioDestino);
            File diretorio = new File(diretorioDestino);  // cria o diretório, caso não exista.

            Zipper zip = new Zipper();
            File fileZip = new File(arquivoOrigemZIP);               // this.arquivoBackupPath);

            //  System.out.println("arquivo - " + fileZip + "  no diretorio - " + diretorio);
            zip.extrairZip(fileZip, diretorio);

            zip.fecharZip();

        } catch (IOException ex) {
            Logger.getLogger(ArquivoDiretorio.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

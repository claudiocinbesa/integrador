package br.pmb.belem.cinbesa.ftpintegrador.unpackager;

import br.pmb.belem.cinbesa.ftpintegrador.utils.ArquivoDiretorio;
import  br.pmb.belem.cinbesa.ftpintegrador.utils.Zipper;
import  br.pmb.belem.cinbesa.ftpintegrador.utils.UtilData;
import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
import javax.swing.JOptionPane;
import javax.swing.SwingWorker;
// import org.h2.tools.Csv;


public class RestauraBackup {
   BackupParametros parametrosBackup ;
    static String nomeArquivoZip = "";
    static String nomeArquivoZipCaixa = null;
    static ArquivoDiretorio arquivoDirBackup;
 public String DIRETORIO_TEMP = "C:/temp/copia2/";
    private String[] tabelasSistema2 = {"SERVICO ",
        "SERVICO_IMAGEM ", "SERVICO_STATUS ", "SISTEMA_PARAMETROS ",
        "SISTEMA_PERMISSAO ", "SISTEMA_TAREFAS ", "USUARIO ", "CLIENTE ",
        "PRESTADOR_SERVICO ", "PRESTADOR_SERVICO_SERVICOS ",
        "DESPACHANTE ", "ORDEM_SERVICO ", "OS_DESPACHO ", "OS_ITEM ",
        "OS_ITEM_TAREFA ", "OS_PAGAMENTO ", "DISTRIBUICAO ",
        "OS_LOG "};
     
    
    int i = 0;
    // 
    

   
    //  private String arquivoBackupPath;
    protected Connection conexao = null;
  

    public static void abreCsv(String arq) throws Exception {
        Csv csv = (Csv) Csv.getInstance();
        ResultSet rs = csv.read(arq, null, null);
        ResultSetMetaData meta = rs.getMetaData();
        while (rs.next()) {
            for (int i = 0; i < meta.getColumnCount(); i++) {
                //    System.out.println(meta.getColumnLabel(i + 1) + ": "
                //   + rs.getString(i + 1));
            }
            // System.out.println();
        }
        rs.close();
    }

    public void abreConexao() {
        // define a conexao com o banco de dados
        try {
            Class.forName(parametrosBackup.getClasseJDBC());
        } catch (ClassNotFoundException e1) {
            JOptionPane.showMessageDialog(null, "Driver do banco de dados não encontrado!");
            return;
        }
        // opcao para otimizar a importacao de grade volume de dados. Vem em http://www.h2database.com/html/performance.html
        String opcoesImport = parametrosBackup.getOpcoesRestore();

        try {
            this.conexao = DriverManager.getConnection(
                    parametrosBackup.getUrl() + parametrosBackup.getOpcoesRestore(),
                    parametrosBackup.getUsuario(),
                    parametrosBackup.getSenha());

        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "Banco de dados não localizado ou já está aberto para outro processo! "
                    + "\n MSG:" + e1.getMessage(), "Falha", JOptionPane.INFORMATION_MESSAGE);
        }
    }

    public static void executaImportacao() {

        String msg = "<html><body> "
                + "<br> <b>Atenção</b>"
                + "<br> A restauração dos dados implica na perda dos dados atuais do seu banco de dados "
                + "<br> e substituição pelos dados contidos no arquivo de backup."
                + "<br><br> Para executar essa operação você precisa ter perfil de ADMINISTRADOR."
                + "<br><br> - Primeiro passo é escolher o arquivo (zip) que contém o backup."
                + "<br>    - Em seguida, você pode confirmar a importação (restauração) dos dados contidos no backup."
                + " </body></html> ";
        JOptionPane.showMessageDialog(null, msg, "Restauração dos dados. Cuidados.", JOptionPane.INFORMATION_MESSAGE);

        arquivoDirBackup = encontrouBackup();

        if (arquivoDirBackup != null) {

            SwingWorker worker = new SwingWorker() {
                @Override
                protected Object doInBackground() throws Exception {
                    executaRestauracaoDados();
                    return null;
                }

                protected void done() {
                    System.exit(0);
                }
            };
            worker.execute();
        }

    }

    private static void executaRestauracaoDados() {
        // System.out.println(" dentro do if - tam da lista - " + arquivoDirBackup.getListArq().size());

        String diaHoraArquivoZip = escolheArquivoZip(arquivoDirBackup);

        // configura para recuperar o sistema de servicos
        nomeArquivoZip
                = arquivoDirBackup.getDiretorio()
                + "/"
                + ArquivoDiretorio.PREFIXO_SERVICO + montaSufixoZip(arquivoDirBackup, diaHoraArquivoZip)
                + ".zip";

        //      System.out.println("arquivo zip do Servico=" + nomeArquivoZip);
        BackupParametros paramSistemaServ = BackupParametros.getInstanciaSysServ();
        
        paramSistemaServ.setNomeArquivoZip(nomeArquivoZip);
        RestauraBackup importadorServ = new RestauraBackup();
       // importadorServ.montaJanelaDialogo(paramSistemaServ, "Servicos", 80, 80);
        importadorServ.extraiArquivos();
        importadorServ.importaDados();

     
       
         System.out.println( "Fim da Restauração! \nAbra novamente o sistema.");
       
    }

    

    public static void main(String[] args) throws Exception {

        RestauraBackup.executaImportacao();

    }

    private void testaCsv() throws Exception {
        String diretorioTemp = "c:/temp/copia2/";
        String arqCsv = diretorioTemp + "CLIENTE.csv";

        //   System.out.println("abrindo o arquivo=" + arqCsv);
        RestauraBackup.abreCsv(arqCsv);

    }

    private void fechaConexao() {
        try {
            this.conexao.commit();
            this.conexao.close();
        } catch (SQLException ex) {
            JOptionPane.showMessageDialog(null, "Falha no fechamento do banco de dados "
                    + "\n MSG:" + ex.getMessage(), "FALHA", JOptionPane.INFORMATION_MESSAGE);
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ArquivoDiretorio encontrouBackup() {
        ArquivoDiretorio dirArquivo = null;
        boolean ok = false;
        try {
            dirArquivo = ArquivoDiretorio.getArquivosBackup();
            //   System.out.println("retorno do arquivo bk=" + dirArquivo.toString() + "\n Tam lista="
            //     + dirArquivo.getListArq().size());
            if (dirArquivo != null) {
                ok = true;
                return dirArquivo;
            }

        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro na escolha do arquivo de backup.\n" + ex.getMessage());
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        return dirArquivo;
    }

    private void importaDados() {
        abreConexao();
        // System.out.println("importando....");
         System.out.println( "Importando o banco de dados" + parametrosBackup.getNomeBanco() + "..." + "\n");
        Zipper zip = new Zipper();
        File fileZip = new File(parametrosBackup.getNomeArquivoZip());  //(this.arquivoBackupPath);

        List<ZipEntry> listaArquivos = null;
        try {
            listaArquivos = zip.listarEntradasZip(fileZip);
        } catch (ZipException ex) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo ZIP: \n"
                    + "" + ex.getMessage());
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Erro no arquivo ZIP: \n"
                    + "" + ex.getMessage());
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        String tabela;
        i = 0;
        for (ZipEntry zipEntry : listaArquivos) {
            i++;
            tabela = zipEntry.getName();
            tabela = tabela.substring(0, tabela.lastIndexOf('.'));
            //   System.out.println("arquivo=" + tabela);
             System.out.println( tabela + "..." + "\n");

            deletaTabela(tabela);
            importaTabela(tabela);
        }
        fechaConexao();
       System.out.println( "\n *** RESTAURAÇÃO FINALIZADA ***" + "\n");
    }

    private void deletaTabela(String tabela) {
        //   System.out.println("deletando os dados da tabela = " + tabela);
        PreparedStatement pstmt = null;
        if (parametrosBackup.getTipoBanco() == BackupParametros.BANCO.H2) {
            try {
                pstmt = this.conexao.prepareStatement(
                        BackupParametros.configuraComandoDelete(
                                BackupParametros.BANCO.H2,
                                tabela));

                pstmt.executeUpdate();
                pstmt.close();
            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Falha ao apagar dados da tabela " + tabela
                        + "\n MSG" + ex, "FALHA", JOptionPane.INFORMATION_MESSAGE);
                Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void importaTabela(String tabela) {
        //   throw new UnsupportedOperationException("Not yet implemented");
        //   System.out.println("importando os dados da tabela = " + tabela);

        PreparedStatement pstmt = null;
        if (parametrosBackup.getTipoBanco() == BackupParametros.BANCO.H2) {
            try {
                String arquivoCSV = this.DIRETORIO_TEMP + tabela + ".csv";
                String comando = "INSERT INTO " + tabela + "  SELECT * FROM  CSVREAD('" + arquivoCSV + "',NULL, 'UTF-8'); ";

                pstmt = this.conexao.prepareStatement("SET REFERENTIAL_INTEGRITY FALSE;"
                        + comando);
                pstmt.executeUpdate();

                pstmt.close();

            } catch (SQLException ex) {
                JOptionPane.showMessageDialog(null, "Falha ao inserir dados na tabela " + tabela
                        + "\n MSG" + ex, "FALHA", JOptionPane.INFORMATION_MESSAGE);
                Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

    private void extraiArquivos() {
        //    System.out.println("extraindo os arquivos em pasta temporária...");
        File diretorio = new File(DIRETORIO_TEMP);  // cria o diretório, caso não exista.
        Zipper zip = new Zipper();
        File fileZip = new File(parametrosBackup.getNomeArquivoZip());               // this.arquivoBackupPath);
        try {
            //  System.out.println("arquivo - " + fileZip + "  no diretorio - " + diretorio);
            zip.extrairZip(fileZip, diretorio);
        } catch (ZipException ex) {
            JOptionPane.showMessageDialog(null, "Falha na operação de extração do arquivo.\n" + ex.getMessage());
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Falha na operação.\n" + ex.getMessage());
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
        zip.fecharZip();
//        System.out.println("ok descompactou!");

    }

     

    private static String escolheArquivoZip(ArquivoDiretorio arquivoDirBackup) {
       // JanelaDialogOpcaoDiaBackup janelaDialog = new JanelaDialogOpcaoDiaBackup();

        Collection<String> lista = new ArrayList();
        Set<String> chavesDia = arquivoDirBackup.getListArq().keySet();
        for (String k : chavesDia) {
            lista.add(arquivoDirBackup.getListArq().get(k));
        }
        // classifica a lista
        // classificaLista(lista);
        lista = classificaLista(lista);
      //  janelaDialog.abreJanelaDialogo(lista);

        return lista.toArray()[0].toString();  //janelaDialog.getEscolha();
    }

    private static String montaSufixoZip(ArquivoDiretorio arquivoDirBackup,
            String diaHoraArquivoZip) {
        // 14/11/2011 11:26h
        // 0123456789012345
        String dtHoraFormatado = diaHoraArquivoZip.substring(6, 10)
                + diaHoraArquivoZip.substring(3, 5)
                + diaHoraArquivoZip.substring(0, 2) + "_"
                + diaHoraArquivoZip.substring(11, 13) + "-"
                + diaHoraArquivoZip.substring(14, 16);
        //    System.out.println("Pesquisando a chave no arquivo com diaHora=" + diaHoraArquivoZip 
        //          + "   formato="+dtHoraFormatado);
        String sufixo = null;
        Set<String> lista = arquivoDirBackup.getListArq().keySet();
        for (String k : lista) {

            String valor = arquivoDirBackup.getListArq().get(k);

            // System.out.println("key="+k+ "    valor="+valor);
            if (k.equals(dtHoraFormatado)) {
                // System.out.println("nome encotrado do arquivo zip=" + k
                //        + "   diretorio=" + arquivoDirBackup.getDiretorio());
                return k;
            }
        }
        return sufixo;
    }

    private static Collection classificaLista(Collection<String> lista) {
        List<Date> listTemp = new ArrayList<Date>();
        String dtHr = "";
        java.util.Date dataHr = null;
        for (String valor : lista) {
            valor = valor.replace('-', ' ');
            dtHr = valor.substring(0, valor.indexOf('h'));
            dataHr = UtilData.converteDataSTR2Date(dtHr);
            // System.out.println("["+valor+"] na pos=" );
            listTemp.add(dataHr);

        }
        Collections.sort(listTemp);
        Collections.reverse(listTemp);
        lista = new ArrayList<String>();
        for (Date dt : listTemp) {
            dtHr = UtilData.converteData2ddMMyyyyHoraMin(dt) + "h";
            //  System.out.println("[" + dtHr + "] na pos=");
            lista.add(dtHr);
        }

        return lista;
    }
}

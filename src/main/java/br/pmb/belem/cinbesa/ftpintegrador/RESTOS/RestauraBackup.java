package br.pmb.belem.cinbesa.ftpintegrador.RESTOS;

import br.pmb.belem.cinbesa.ftpintegrador.utils.ArquivoDiretorio;
import  br.pmb.belem.cinbesa.ftpintegrador.utils.Zipper;
import java.io.File;
import java.io.IOException;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.zip.ZipEntry;
import java.util.zip.ZipException;
// import org.h2.tools.Csv;


public class RestauraBackup {
   BackupParametros parametrosBackup ;
    static String nomeArquivoZip = "";
   
    static ArquivoDiretorio arquivoDirBackup;
    
    public String DIRETORIO_TEMP = "";
    
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
      //  Csv csv = (Csv) Csv.getInstance();
        ResultSet rs = null ; // csv.read(arq, null, null);
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

    private void fechaConexao() {
        try {
            this.conexao.commit();
            this.conexao.close();
        } catch (SQLException ex) {
            
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    private static ArquivoDiretorio encontrouBackup(String path, String filtro) {
       try {
           ArquivoDiretorio dirArquivo = null;
           boolean ok = false;
           
           dirArquivo = ArquivoDiretorio.getArquivos(path, filtro);
           //   System.out.println("retorno do arquivo bk=" + dirArquivo.toString() + "\n Tam lista="
           //     + dirArquivo.getListArq().size());
           if (dirArquivo != null) {
               ok = true;
               return dirArquivo;
           }
           return dirArquivo;
       } catch (IOException ex) {
           Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
       }
       return null;
    }

    private void importaDados() {
      //  abreConexao();
        // System.out.println("importando....");
         System.out.println( "Importando o banco de dados" + parametrosBackup.getNomeBanco() + "..." + "\n");
        Zipper zip = new Zipper();
        File fileZip = new File(parametrosBackup.getNomeArquivoZip());  //(this.arquivoBackupPath);

        List<ZipEntry> listaArquivos = null;
        try {
            listaArquivos = zip.listarEntradasZip(fileZip);
        } catch (ZipException ex) {
             
            Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IOException ex) {
             
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
                Logger.getLogger(RestauraBackup.class.getName()).log(Level.SEVERE, null, ex);
            }
        }

    }

     

     

    private static String escolheArquivoZip(ArquivoDiretorio arquivoDirBackup) {
       
        Collection<String> lista = new ArrayList();
        Set<String> chavesDia = arquivoDirBackup.getListArq().keySet();
        for (String k : chavesDia) {
            lista.add(arquivoDirBackup.getListArq().get(k));
        }
        
    
        return lista.toArray()[0].toString();  //janelaDialog.getEscolha();
    }

      
    
    
    public static void main(String[] args) throws Exception {

      //  RestauraBackup.executaImportacao();

    }
}

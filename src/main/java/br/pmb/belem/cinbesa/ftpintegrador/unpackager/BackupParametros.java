package br.pmb.belem.cinbesa.ftpintegrador.unpackager;

import java.sql.Connection;
import java.sql.DatabaseMetaData;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.swing.JOptionPane;
 
 
/**
 *
 * @author claudio martins
 */
public class BackupParametros {
 
    public enum BANCO {
        H2,
        PostGreSQL
    }
    
    public final static String PREFIXO_ArquivoZip = "bk_";
    private String nomeBanco = "Servico";
    private BANCO  tipoBanco = BANCO.H2;
    private String url =   "jdbc:h2:c:/sistemas/sysserv/bd/sysservbd";
    private String usuario =    "sa";
    private String senha =    "";
    private String classeJDBC =   "org.h2.Driver";  //Conexao.DB_H2_DRIVER ;  // 
    private String opcoesRestore = ";LOG=0;CACHE_SIZE=65536;LOCK_MODE=0;UNDO_LOG=0";  // valido para H2
    private String[] tabelasSistema = {"SERVICO ORDER BY CATEGORIA_SERVICO ",
        "SERVICO_IMAGEM ",
        "SERVICO_STATUS ",
        "SISTEMA_PARAMETROS ",
        "SISTEMA_PERMISSAO ",
        "SISTEMA_TAREFAS ",
        "USUARIO ",
        "CLIENTE ",
        "PRESTADOR_SERVICO ",
        "PRESTADOR_SERVICO_SERVICOS ",
        "DESPACHANTE ",
        "ORDEM_SERVICO ",
        "OS_DESPACHO ",
        "OS_ITEM ",
        "OS_ITEM_TAREFA ",
        "OS_PAGAMENTO ",
        "DISTRIBUICAO ", "OS_LOG "};
    private String nomeArquivoZip = "";
    private Date dataHoraBackup;

    static String configuraComandoDelete(BANCO bd, String tabela) {
        String comando = "DELETE FROM " + tabela + ";";
        if (bd == BANCO.H2) {
            comando = "SET REFERENTIAL_INTEGRITY FALSE;"
                    + "DELETE FROM " + tabela + ";";
        }
        return comando;
    }
   
    static BackupParametros getInstanciaSysServ() {
        BackupParametros param = new BackupParametros();
        param.setNomeBanco("Servico");
        param.setTabelasSistema( param.recuperaNomeDasTabelas()); 
        
        return param;
    }

     

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getUsuario() {
        return usuario;
    }

    public void setUsuario(String usuario) {
        this.usuario = usuario;
    }

    public String getClasseJDBC() {
        return classeJDBC;
    }

    public void setClasseJDBC(String classeJDBC) {
        this.classeJDBC = classeJDBC;
    }

    public String[] getTabelasSistema() {
      //  tabelasSistema = recuperaNomeDasTabelas();
        return tabelasSistema;
    }

    public void setTabelasSistema(String[] tabelasSistema) {
        this.tabelasSistema = tabelasSistema;
    }

    public String getNomeArquivoZip() {
        return nomeArquivoZip;
    }

    public void setNomeArquivoZip(String nomeArquivoZip) {
        this.nomeArquivoZip = nomeArquivoZip;
    }

    public String getOpcoesRestore() {
        return opcoesRestore;
    }

    public void setOpcoesRestore(String opcoesRestore) {
        this.opcoesRestore = opcoesRestore;
    }

    public String getNomeBanco() {
        return nomeBanco;
    }

    public void setNomeBanco(String nomeBanco) {
        this.nomeBanco = nomeBanco;
    }

    public BANCO getTipoBanco() {
        return tipoBanco;
    }

    public void setTipoBanco(BANCO tipoBanco) {
        this.tipoBanco = tipoBanco;
    }

    public Date getDataHoraBackup() {
        return dataHoraBackup;
    }

    public void setDataHoraBackup(Date dataHoraBackup) {
        this.dataHoraBackup = dataHoraBackup;
    }

    public static void main(String[] args) {
        System.out.println("Recuperando o nome das tabelas...");
        // define a conexao com o banco de dados
        BackupParametros paramBackup = new BackupParametros();
        String[] listaTabelas = paramBackup.recuperaNomeDasTabelas();
        for (int i = 0; i < listaTabelas.length; i++) {
            String string = listaTabelas[i];
            System.out.println(" nome tab = " + string);
        }
    }

    public String[] recuperaNomeDasTabelas() {
        String[] retornoLista = null;
        try {
            Class.forName(getClasseJDBC());
        } catch (ClassNotFoundException e1) {
            // TODO Auto-generated catch block
            JOptionPane.showMessageDialog(null, "Driver do banco de dados não encontrado!");
            return null;
        }
        String nomeTab = "";
        try {
            Connection conexao = DriverManager.getConnection(
                    getUrl(), getUsuario(), getSenha());
            // recuperar a classe  DatabaseMetadaData a partir da conexao criada
            DatabaseMetaData dbmd = conexao.getMetaData();
            /*
            System.out.println ( "Versao do Driver JDBC = "+ dbmd.getDriverVersion());
            System.out.println ( "Versao do Banco de Dados = "+ dbmd.getDatabaseProductVersion());
            System.out.println ( "Suporta Select for Update? = "+ dbmd.supportsSelectForUpdate());  
            System.out.println ( "Suporta Transacoes? = "+ dbmd.supportsTransactions());
            // retornar todos os schemas(usuarios) do Banco de Dados 
            ResultSet r2 = dbmd.getSchemas();
            while (r2.next()) {
            System.out.println("SCHEMA DO BD = " + r2.getString(1));
            }
            
             * 
             */
            // retornar todos os schemas(usuarios) do Banco de Dados 
            ResultSet r3 = dbmd.getTables(null, "PUBLIC", null, null);
            // retornoLista = new String[r3]
            List<String> listaTabs = new ArrayList<String>();
            while (r3.next()) {
                // System.out.println("TAB = " + r3.getString(1) + "  " + r3.getString(2)
                nomeTab = r3.getString(3);
                if (!nomeTab.equalsIgnoreCase("sequence"))
                    listaTabs.add(nomeTab);
            }
            retornoLista = new String[listaTabs.size()];
            int i = 0;
            for (String nmTab : listaTabs) {
                retornoLista[i] = nmTab + " ";
                i++;
            }

        } catch (SQLException e1) {
            JOptionPane.showMessageDialog(null, "Banco de dados não localizado!", "Falha", JOptionPane.INFORMATION_MESSAGE);
            e1.printStackTrace();
        }
        return retornoLista;
    }
}

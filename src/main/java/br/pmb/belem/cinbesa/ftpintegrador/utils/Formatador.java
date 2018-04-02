package br.pmb.belem.cinbesa.ftpintegrador.utils;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

/**
 *
 * @author Microsoft
 */
public class Formatador {

    public static String decimal(BigDecimal valorBigDec) {
        if (valorBigDec == null) {
            return "";
        }

        NumberFormat numFormatado = new DecimalFormat("###,###,##0.00", new DecimalFormatSymbols(new Locale("pt", "BR")));

        String moedaFormat = numFormatado.format(valorBigDec);
        //   String.format("%12.2f", valorBigDec);
        return moedaFormat;
    }

    public static String moeda(Float aFloat) {
        return "R$ " + Formatador.decimal(new BigDecimal(aFloat));
    }
    
    public static String inteiroBrancos (Integer valor, int tamanho) {
          if (valor == null) {
            return "";
        }
        String brancos = Formatador.repete("#", tamanho);

        NumberFormat numFormatado = new DecimalFormat(brancos, new DecimalFormatSymbols(new Locale("pt", "BR")));

        String moedaFormat = numFormatado.format(valor);
        //   String.format("%12.2f", valorBigDec);
        return moedaFormat;
    }

    public static String inteiroZerado(Integer valor, int tamanho) {
        if (valor == null) {
            return "";
        }
        String zeros = Formatador.repete("0", tamanho);

        NumberFormat numFormatado = new DecimalFormat(zeros, new DecimalFormatSymbols(new Locale("pt", "BR")));

        String moedaFormat = numFormatado.format(valor);
        //   String.format("%12.2f", valorBigDec);
        return moedaFormat;
    }

    
    public static String repete(String s, int i) {
        String r = "";
        StringBuilder strB = new StringBuilder(r);
        for (int j = 0; j < i; j++) {
            // r += s;
            strB.append(s);
        }
        r = strB.toString();
        return r ;
    }
    
    public static void main(String args[]) {
        System.out.println(Formatador.repete("-", 80));
        System.out.println(Formatador.inteiroZerado(100, 5));
    }

}
 

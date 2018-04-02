package br.pmb.belem.cinbesa.ftpintegrador.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.joda.time.DateTime;
import org.joda.time.Minutes;

/**
 *
 * @author cmartins
 */
public class UtilData {

    public static String[] DIAS_SEMANA = {"DOMINGO", "SEGUNDA", "TERÇA", "QUARTA", "QUINTA", "SEXTA", "SABADO"};

    public static String converteData2ddMMyyyy(java.util.Date data) {
        if (data == null) {
            return "";
        }
        DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateformatddMMyyyy = new SimpleDateFormat("dd/MM/yyyy");
        return "" + new StringBuilder(dateformatddMMyyyy.format(data));
    }

    public static String converteData2ddMMyyyyHoraMin(java.util.Date data) {
        if (data == null) {
            return "";
        }
        // DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
        SimpleDateFormat dateformat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2yyyyMMddHoraMin(java.util.Date data) {
        if (data == null) {
            return "";
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMMdd_HH-mm");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2yyyy_MM_dd(java.util.Date data) {
        if (data == null) {
            return "";
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy-MM-dd");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static Date converteDataSTR2Date(String dataStr) {
        int posPontosHHMM = dataStr.indexOf(":");
        if (posPontosHHMM < 0) {
            dataStr = dataStr + " 00:00";
        }
        Date dt = null;
        try {
            dt = formataData(dataStr);
        } catch (Exception ex) {
            Logger.getLogger(UtilData.class.getName()).log(Level.SEVERE, null, ex);
            System.out.println("Erro nao esperado na conversão da data = " + dataStr
                    + "\n Verifique se falta horas e minutos na formatação");
        }
        return dt;
    }

    /**
     * Converte uma String para um objeto Date. Caso a String seja vazia ou
     * nula, retorna null - para facilitar em casos onde formulários podem ter
     * campos de datas vazios.
     *
     * @param data String no formato dd/MM/yyyy a ser formatada
     * @return Date Objeto Date ou null caso receba uma String vazia ou nula
     * @throws Exception Caso a String esteja no formato errado
     */
    public static Date formataData(String dataHHmm) throws Exception {
        String data = dataHHmm;
        if (data == null || data.equals("")) {
            return null;
        }
        Date date = null;
        try {
            DateFormat formatter = new SimpleDateFormat("dd/MM/yyyy HH:mm");
            date = (java.util.Date) formatter.parse(data);
        } catch (ParseException e) {
            throw e;
        }
        return date;
    }

    public static List datas(Date dataInicial, Date dataFinal) {
        List listaDatas = new LinkedList();
        int dias = diferencaDias(dataFinal, dataInicial);
        Calendar c = Calendar.getInstance();
        Calendar dt = Calendar.getInstance();
        c.setTime(dataInicial);
        dt.setTime(dataInicial);
        Date dtCalc = null;
        for (int i = 0; i <= dias; i++) {
            c.setTime(dataInicial);
            c.add(Calendar.DATE, i);
            dtCalc = c.getTime();
            // System.out.println(i + "   data=" + dtCalc + "  =" + UtilData.diaDaSemana(dtCalc));
            listaDatas.add(c.getTime());
        }
        return listaDatas;
    }

    public static String diaDaSemana(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int d = dt.getDay() + 1; //  c.DAY_OF_WEEK;
        String diaStr = "";
        switch (d) {
            case Calendar.SUNDAY:
                diaStr = "DOM";
                break;
            case Calendar.MONDAY:
                diaStr = "SEG";
                break;
            case Calendar.TUESDAY:
                diaStr = "TER";
                break;
            case Calendar.WEDNESDAY:
                diaStr = "QUA";
                break;
            case Calendar.THURSDAY:
                diaStr = "QUI";
                break;
            case Calendar.FRIDAY:
                diaStr = "SEX";
                break;
            case Calendar.SATURDAY:
                diaStr = "SAB";
                break;

            default:
                diaStr = "XXX";

        }

        return diaStr;
    }

    /**
     * Retorna o nome do dia da semana por extenso (DOMINGO, SEGUNDA...)
     *
     * @param dt - data informada
     * @return retorna o String com o nome do dia por extenso.
     */
    public static String diaDaSemanaExtenso(Date dt) {
        Calendar c = Calendar.getInstance();
        c.setTime(dt);
        int d = dt.getDay() + 1; //  c.DAY_OF_WEEK;
        String diaStr = "";
        switch (d) {
            case Calendar.SUNDAY:
                diaStr = "DOMINGO";
                break;
            case Calendar.MONDAY:
                diaStr = "SEGUNDA";
                break;
            case Calendar.TUESDAY:
                diaStr = "TERÇA";
                break;
            case Calendar.WEDNESDAY:
                diaStr = "QUARTA";
                break;
            case Calendar.THURSDAY:
                diaStr = "QUINTA";
                break;
            case Calendar.FRIDAY:
                diaStr = "SEXTA";
                break;
            case Calendar.SATURDAY:
                diaStr = "SÁBADO";
                break;

            default:
                diaStr = "XXX";

        }

        return diaStr;
    }

    /**
     * Retorna a quantidade (int) de dias entre duas datas.
     *
     * @param dataInicial
     * @param dataFinal
     * @return
     */
    public static int diferencaDias(Date dataInicial, Date dataFinal) {
        Calendar d1 = Calendar.getInstance();
        Calendar d2 = Calendar.getInstance();
        d1.setTime(dataFinal);
        d2.setTime(dataInicial);
        // d2.set(2011, 9-1, 1);
        int dias = diffInDays(d2, d1);
        return dias;
    }

    //
    /**
     * Returns the difference in days between the two calendars. The time is
     * ignored when comparing. Example: diffInDays(25/09/2004 14:09:14,
     * 30/09/2004 10:23:41) = -5.
     */
    public static int diffInDays(Calendar a, Calendar b) {
        //  System.out.println("datas a=" + a.getTime() + "   b=" + b.getTime());
        a.set(Calendar.HOUR_OF_DAY, 0);
        a.set(Calendar.MINUTE, 0);
        a.set(Calendar.SECOND, 0);
        a.set(Calendar.MILLISECOND, 0);
        b.set(Calendar.HOUR_OF_DAY, 0);
        b.set(Calendar.MINUTE, 0);
        b.set(Calendar.SECOND, 0);
        b.set(Calendar.MILLISECOND, 0);
        return (int) ((a.getTime().getTime() - b.getTime().getTime())
                / (1000 * 60 * 60 * 24));
    }

    /**
     * Returns true if the given calendars are in the same day.
     */
    public static boolean sameDay(Calendar a, Calendar b) {
        return (a.get(Calendar.DAY_OF_MONTH) == b.get(Calendar.DAY_OF_MONTH))
                && (a.get(Calendar.MONTH) == b.get(Calendar.MONTH))
                && (a.get(Calendar.YEAR) == b.get(Calendar.YEAR));
    }
    //

    public static String converteData2Ano(Date data) {
        if (data == null) {
            return "";
        }
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyy");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2Mes(Date data) {
        if (data == null) {
            return "";
        }
        // System.out.println("conv data  2 mes = " + data);
        SimpleDateFormat dateformat = new SimpleDateFormat("MM");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2Dia(Date data) {
        if (data == null) {
            return "";
        }
        // System.out.println("conv data  2 DIA = " + data);
        SimpleDateFormat dateformat = new SimpleDateFormat("dd");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2AnoMes(Date data) {
        if (data == null) {
            return "";
        }
        //System.out.println("conv data  2 Ano = "+data);
        SimpleDateFormat dateformat = new SimpleDateFormat("yyyyMM");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2HoraMin(Date data) {
        if (data == null) {
            return "";
        }
        // System.out.println("conv data  2 Ano = "+data);
        SimpleDateFormat dateformat = new SimpleDateFormat("HH:mm");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2Hora(Date data) {
        if (data == null) {
            return "";
        }
        // System.out.println("conv data  2 Ano = "+data);
        SimpleDateFormat dateformat = new SimpleDateFormat("HH");
        return "" + new StringBuilder(dateformat.format(data));
    }

    public static String converteData2Minutos(Date data) {
        if (data == null) {
            return "";
        }
        // System.out.println("conv data  2 Ano = "+data);
        SimpleDateFormat dateformat = new SimpleDateFormat("mm");
        return "" + new StringBuilder(dateformat.format(data));
    }

    /**
     *
     * @param dt1 - data inicial
     * @param dt2 - data final
     * @param ordem - deve ser "ASC" ou "DESC"
     * @return Retorna se é menor ou maior
     */
    public static int comparaDatas(Date dt1, Date dt2, String ordem) {
        if (ordem == null) {
            ordem = "ASC";
        }

        if (dt1 == null) {
            dt1 = UtilData.converteDataSTR2Date("01/01/1900 00:00");
        }
        if (dt2 == null) {
            dt2 = UtilData.converteDataSTR2Date("01/01/1900 00:00");
        }

        int retornoComparavel = 0;
        if (ordem.equalsIgnoreCase("ASC")) {
            retornoComparavel = dt1.compareTo(dt2);
        } else {
            retornoComparavel = dt2.compareTo(dt1);
        }
        return retornoComparavel;
    }

    public static int calcIdade(Date dataNasc, Date dataReferencia) {
        Date hoje = new Date();
        if (dataReferencia != null) {
            hoje = dataReferencia;
        }

        Calendar cal = Calendar.getInstance();
        cal.setTime(hoje);
        int day1 = cal.get(Calendar.DAY_OF_YEAR);
        int ano1 = cal.get(Calendar.YEAR);

        cal.setTime(dataNasc);
        int day2 = cal.get(Calendar.DAY_OF_YEAR);
        int ano2 = cal.get(Calendar.YEAR);

        int nAno = ano1 - ano2;

        if (day1 < day2) {
            nAno--; //Ainda não completou aniversario esse ano.    
        }
        return nAno;
    }

    public static int ultimoDiaMes(String anoRef, String mesRef) {
        Calendar cal = GregorianCalendar.getInstance();
        cal.setTime(UtilData.converteDataSTR2Date("01/" + mesRef + "/" + anoRef));  // new Date()

        int ultDia = cal.getActualMaximum(Calendar.DAY_OF_MONTH);
        int mes = (cal.get(Calendar.MONDAY) + 1);
        int ano = cal.get(Calendar.YEAR);

        // System.out.println("DATA DO ULTIMO DIA DO MES " + ultDia + "/" + mes + "/" + ano);
        try {
            Date data = (new SimpleDateFormat("dd/MM/yyyy")).parse(ultDia + "/" + mes + "/" + ano);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return ultDia;
    }  // fim

    public static int converteData2AnoInt(Date dt) {
        int result = 0;
        result = Integer.parseInt(UtilData.converteData2Ano(dt));
        return result;
    }

    public static int converteData2MesInt(Date dt) {
        int result = 0;
        result = Integer.parseInt(UtilData.converteData2Mes(dt));
        return result;
    }

    public static int converteData2DiaInt(Date dt) {
        int result = 0;
        result = Integer.parseInt(UtilData.converteData2Dia(dt));
        return result;
    }

    public static int converteData2HoraInt(Date dt) {
        int result = 0;
        result = Integer.parseInt(UtilData.converteData2Hora(dt));
        return result;
    }

    public static int converteData2MinutosInt(Date dt) {
        int result = 0;
        result = Integer.parseInt(UtilData.converteData2Minutos(dt));
        return result;
    }

    public static void diferencaHoras(Date dtInicial, Date dtFinal) {

        DateTime dataInicial = new DateTime(UtilData.converteData2AnoInt(dtInicial),
                UtilData.converteData2MesInt(dtInicial),
                UtilData.converteData2DiaInt(dtInicial),
                UtilData.converteData2HoraInt(dtInicial),
                UtilData.converteData2MinutosInt(dtInicial));
        DateTime dataFinal = new DateTime(UtilData.converteData2AnoInt(dtFinal),
                UtilData.converteData2MesInt(dtFinal),
                UtilData.converteData2DiaInt(dtFinal),
                UtilData.converteData2HoraInt(dtFinal),
                UtilData.converteData2MinutosInt(dtFinal));
        BigDecimal minutos
                = new BigDecimal(Minutes.minutesBetween(dataInicial, dataFinal).getMinutes());
        BigDecimal horas = minutos.divide(new BigDecimal("60"), 2, RoundingMode.HALF_UP);
        int minTotal = horas.intValue();
        int difMinutos = (minutos.intValue() - minTotal * 60);
        //  System.out.println(horas.intValue() + " - " +  difMinutos ); // 648,50h 
        // Hours.hoursBetween(dataInicial, dataFinal);
        // .hoursBetween(dtInicial, dtFinal).getHours();
    }

    public static String diferencaHorasFormatada(Date dtInicial, Date dtFinal) {

        DateTime dataInicial = new DateTime(UtilData.converteData2AnoInt(dtInicial),
                UtilData.converteData2MesInt(dtInicial),
                UtilData.converteData2DiaInt(dtInicial),
                UtilData.converteData2HoraInt(dtInicial),
                UtilData.converteData2MinutosInt(dtInicial));
        DateTime dataFinal = new DateTime(UtilData.converteData2AnoInt(dtFinal),
                UtilData.converteData2MesInt(dtFinal),
                UtilData.converteData2DiaInt(dtFinal),
                UtilData.converteData2HoraInt(dtFinal),
                UtilData.converteData2MinutosInt(dtFinal));
        BigDecimal minutos
                = new BigDecimal(Minutes.minutesBetween(dataInicial, dataFinal).getMinutes());
        BigDecimal horas = minutos.divide(new BigDecimal("60"), 2, RoundingMode.HALF_UP);
        int minTotal = horas.intValue();
        int difMinutos = (minutos.intValue() - minTotal * 60);
        // System.out.println(horas.intValue() + " - " +  difMinutos );
        String saidaResp = Formatador.inteiroZerado(horas.intValue(), 2) + ":"
                + Formatador.inteiroZerado(difMinutos, 2);
        return saidaResp;
    }

    public static String diaDaSemana(String dataKey) {
        Date dataRef = UtilData.converteDataSTR2Date(dataKey);
        String diaSemana = UtilData.diaDaSemana(dataRef);
        return diaSemana;
    }

    public static String somaHorariosHhMm(String hhmm1, String hhmm2) {
        // Date dataRef = UtilData.converteDataSTR2Date(dataKey);
        String hhmmSaida = "";
        int pos1Separador = hhmm1.indexOf(':');
        int hh1 = Integer.parseInt(hhmm1.substring(0, pos1Separador));
        int mm1 = Integer.parseInt(hhmm1.substring(pos1Separador + 1));
        //  System.out.println("parse 1 =>  hh="+hh1 +  "    mm=" +mm1 );
        int pos2Separador = hhmm2.indexOf(':');
        int hh2 = Integer.parseInt(hhmm2.substring(0, pos2Separador));
        int mm2 = Integer.parseInt(hhmm2.substring(pos2Separador + 1));
        //    System.out.println("parse 1 =>  hh="+hh1 +  "    mm=" +mm1  +  "      "
        //          + " parse 2 =>  hh="+hh2 +  "    mm=" +mm2  +  "      ");
        int hhTot = hh1 + hh2;
        int mmTot = mm1 + mm2;
        int resto = mmTot % 60;
        int inteiroHora = mmTot / 60;
        hhTot = hhTot + inteiroHora;
        hhmmSaida = "" + hhTot + ":" + Formatador.inteiroZerado(resto, 2);
        return hhmmSaida;
    }

    public static java.util.Date dataSubtraiMinutos(Date dt, int minutos) {
        org.joda.time.DateTime dtTime = new DateTime(dt);
        java.util.Date dtSaida = dtTime.minusMinutes(minutos).toDate();

        return dtSaida;
    }

    public static java.util.Date dataSomaMinutos(Date dt, int minutos) {
        org.joda.time.DateTime dtTime = new DateTime(dt);
        java.util.Date dtSaida = dtTime.plusMinutes(minutos).toDate();

        return dtSaida;
    }

    public static void main(String[] args) throws Exception {
        // testes();
        //System.out.println("hoje é " + UtilData.converteData2ddMMyyyy(new Date()));
        // System.out.println("hoje é " + UtilData.converteData2ddMMyyyyHoraMin(new Date()));
        /*
         Date dtF = formataData("20/08/2011 23:59");
         dtF = dtF;
         System.out.print ("Data formatada = "+ dtF);
         * 
         */
        /*
         System.out.println("a diferenca de datas =" + diferencaDias(
         UtilData.formataData("10/09/2011 00:00"),
         UtilData.formataData("01/09/2011 00:00")));
         List dts = UtilData.datas(
         UtilData.formataData("01/09/2011 00:00"),
         UtilData.formataData("11/09/2011 00:00"));
         //
         int dia = ultimoDiaMes("2013", "07");
         System.out.println("Ultimo Dia = " + dia);
         System.out.append("\n  - diferenca entre horas ");
         diferencaHoras(UtilData.converteDataSTR2Date("09/07/2013 08:10"), UtilData.converteDataSTR2Date("09/07/2013 13:50"));
         String hhmm = somaHorariosHhMm("04:10", "03:20");
         System.out.println("\n \n HORA SOMADA  =" + hhmm);
         System.out.println("\n \n ACUM HORA   =" + somaHorariosHhMm(hhmm, "01:50"));
         * 
         */
        Date dt = new Date();
        System.out.println(converteData2ddMMyyyyHoraMin(dt));
        System.out.println(converteData2ddMMyyyyHoraMin(dataSubtraiMinutos(dt, 10)) );
        System.out.println(converteData2ddMMyyyyHoraMin(dataSomaMinutos(dt, 10)) );

    }
}

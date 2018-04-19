package br.pmb.belem.cinbesa.ftpintegrador.utils;

import java.io.IOException;
import java.util.Date;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author Claudio Martins Formatador do Log
 */
public class Logging {
    public static String LOG_FILE = "c:/temp/log"
            + UtilData.converteData2yyyyMMddHoraMin(new Date())
            + ".xml";
    private static Logger LOG;
    FileHandler newHandler;

    /**
     * Cria um objeto de log
     *
     * @param aClass
     * @return Log Objec
     */
    public static Logging getLogger(Class aClass) {
        return new Logging(aClass);
    }

    public Logging() {
        try {
            newHandler = new FileHandler(LOG_FILE, true);
        } catch (IOException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG = Logger.getLogger(Logging.class.getName());
        LOG.addHandler(newHandler);
    }

    public Logging(Class classLog) {
        try {
            newHandler = new FileHandler(LOG_FILE, true);
        } catch (IOException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SecurityException ex) {
            Logger.getLogger(Logging.class.getName()).log(Level.SEVERE, null, ex);
        }
        LOG = Logger.getLogger(classLog.getName());
        LOG.addHandler(newHandler);
    }

    public static void printMsg(String msg) {
        if (msg == null) {
            LOG.severe("A mensagem não pode ser null!");
            return;
        } else if (msg.isEmpty()) {
            LOG.warning("A mensagem não deve ser vazia!");
        }

        LOG.info(msg);
    }

    public static void printMsgFalha(String msg) {
        if (msg == null) {
            LOG.severe("A mensagem não pode ser null!");
            return;
        } else if (msg.isEmpty()) {
            LOG.warning("A mensagem não deve ser vazia!");
        }

        LOG.severe(msg);
    }
}

package br.pmb.belem.cinbesa.ftpintegrador.utils;

import javax.crypto.*;
import javax.crypto.spec.*;
import java.security.*;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.KeySpec;
import java.util.logging.Level;
import java.util.logging.Logger;
import sun.misc.BASE64Encoder; // para simplificar o exemplo. Use alguma outra classe para converter
import sun.misc.BASE64Decoder; // para Base-64.

/**
 *
 * @author
 */
public class PWSec {

    private static SecretKey skey;
    private static KeySpec ks;
    private static PBEParameterSpec ps;
    private static final String algorithm = "PBEWithMD5AndDES";
    private static BASE64Encoder enc = new BASE64Encoder();
    private static BASE64Decoder dec = new BASE64Decoder();

    static {
        try {
            SecretKeyFactory skf = SecretKeyFactory.getInstance(algorithm);
            ps = new PBEParameterSpec(new byte[]{3, 1, 4, 1, 5, 9, 2, 6}, 20);
            ks = new PBEKeySpec("EAlGeEen3/m8/YkO".toCharArray()); // esta ? a chave que voc? quer manter secreta.
            // Obviamente quando voc? for implantar na sua empresa, use alguma outra coisa - por exemplo,
            // "05Bc5hswRWpwp1sew+MSoHcj28rQ0MK8". Nao use caracteres especiais (como ?) para nao dar problemas.
            skey = skf.generateSecret(ks);
        } catch (java.security.NoSuchAlgorithmException ex) {
            ex.printStackTrace();
        } catch (java.security.spec.InvalidKeySpecException ex) {
            ex.printStackTrace();
        }
    }

    public static void main(String[] args) throws Exception {
        String password = "semus"; // 3p6/Lsbp+MIK8zqK => esta ? a tal senha do banco de dados que voc? quer criptografar
        String encoded = PWSec.encrypt(password);
        System.out.println(encoded);  // imprime 
        System.out.println(PWSec.decrypt(encoded).equals(password)); // imprime "true"

        System.out.println("  DECOD = " + PWSec.decrypt(encoded));

    }

    public static final String encrypt(final String text) {
        try {
            final Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.ENCRYPT_MODE, skey, ps);
            return enc.encode(cipher.doFinal(text.getBytes()));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalBlockSizeException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (BadPaddingException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public static final String decrypt(final String text) {
        try {
            final Cipher cipher = Cipher.getInstance(algorithm);
            cipher.init(Cipher.DECRYPT_MODE, skey, ps);
            String ret = null;
            try {
                ret = new String(cipher.doFinal(dec.decodeBuffer(text)));
            } catch (Exception ex) {
            }
            return ret;
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchPaddingException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(PWSec.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

}

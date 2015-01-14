package _1_xifrar_desxifrar;

import crypto.io;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rafael.jose.lucena
 */
public class AES {

    static final String ALGORITHM = "AES";
    static final String PADDING = "SHA-1";
    static final String VERSION = "AES/CBC/PKCS5Padding";

    /*
        Xifrar fitxer clauXifrat
            entrada: fitxer fitxer a xifrar;
                     clauXifrat fitxer amb la clau secreta per xifrar;
            sortida: fitxer.enc fitxer xifrat amb el material necesari per desxifrar.
    */
    public byte[] xifrar(String fitxer, String clauXifrat) {
        byte[] bclave = io.leer_fichero(new File(clauXifrat));
        if (bclave.length != 16) {
            System.out.println("Error clave mayor a 16 bytes");
            return null;
        }

        SecretKeySpec key = new SecretKeySpec(bclave, ALGORITHM);

        byte[] datos = io.leer_fichero(new File(fitxer));

        // Comença l'encriptacio
        byte[] resultado = null;
        try {
            byte[] enkey = key.getEncoded();
            IvParameterSpec iv = new IvParameterSpec(enkey);

            Cipher aesCipher = Cipher.getInstance(VERSION);
            aesCipher.init(Cipher.ENCRYPT_MODE, key, iv);
            byte[] encryptdata = aesCipher.doFinal(datos);

            resultado = new byte[encryptdata.length + 16];
            System.arraycopy(iv.getIV(), 0, resultado, 0, 16);
            System.arraycopy(encryptdata, 0, resultado, 16, encryptdata.length);

        } catch (NoSuchAlgorithmException | NoSuchPaddingException | InvalidKeyException | InvalidAlgorithmParameterException | IllegalBlockSizeException | BadPaddingException e) {
            System.out.println("aes.xifrar.Exception:" + e.toString());
        }
        // fi

        io.escribir_fichero(resultado, new File(new File(fitxer).getName() + ".enc"));
        return resultado;
    }

    /*
        Desxifrar fitxer.enc clauXifrat
                  entrada: fitxer.enc fitxer xifrat amb el material necesari per desxifrar;
                  clauXifrat fitxer amb la clau secreta per desxifrar;
                  sortida: fitxer fitxer desxifrat.
    */
    public byte[] desxifrar(String fitxer_enc, String clauXifrat) {
        File ffitxer_enc = new File(fitxer_enc);

        byte[] bclave = io.leer_fichero(new File(clauXifrat));
        if (bclave.length != 16) {
            System.out.println("Error clave mayor a 16 bytes");
            return null;
        }

        SecretKeySpec key = new SecretKeySpec(bclave, ALGORITHM);

        byte[] datos = io.leer_fichero(ffitxer_enc);

        // Comença la desencriptacio
        byte[] resultado = null;
        try {
            byte[] iv = new byte[16];
            for (int i = 0; i < iv.length; ++i) {
                iv[i] = datos[i];
            }
            byte[] data = new byte[datos.length - 16];
            for (int i = 0; i < data.length; ++i) {
                data[i] = datos[i + 16];
            }

            Cipher aesCipher = Cipher.getInstance(VERSION);
            aesCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
            resultado = aesCipher.doFinal(data);
        } catch (Exception e) {
            System.out.println("Crypt.decrypt.Exception:" + e.toString());
        }
        // fi

        io.escribir_fichero(resultado, new File(ffitxer_enc.getName().replace(".enc", "")));
        return resultado;
    }
    
    public static final String AESKEYFILENAME = "mSecretKey";

    public byte[] AESKey() {
        try {
            KeyGenerator keyGen = KeyGenerator.getInstance(ALGORITHM);
            SecureRandom random = SecureRandom.getInstanceStrong();
            keyGen.init(random);
            SecretKey secretKey = keyGen.generateKey();

            io.escribir_fichero(secretKey.getEncoded(), new File(AESKEYFILENAME));
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

}

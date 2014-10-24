package _1_xifrar_desxifrar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.BadPaddingException;
import javax.crypto.Cipher;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.KeyGenerator;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rafael.jose.lucena
 */
public class AES {

    private Debug2 debug2;
    //  private String key = "thisisasecretkey";
    private SecretKey aesKey;

    public AES() {

        debug2 = new Debug2();

        KeyGenerator keygen;
        try {
            keygen = KeyGenerator.getInstance("AES");
            aesKey = keygen.generateKey();
        } catch (NoSuchAlgorithmException e) {
            System.out.println("AES.NoSuchAlgorithmException_1 " + e.toString());

        }

    }

    public String xifrar(File fitxer, String clauXifrat) throws FileNotFoundException, IOException {
        return xifrar(llegirFitxer(fitxer), clauXifrat);
    }

    public String xifrar(String fitxer, String clauXifrat) {




        return null;
    }

    public byte[] xifrar_b(File fitxer, String clauXifrat) throws FileNotFoundException, IOException {
        return xifrar_b(llegirFitxer(fitxer), clauXifrat);
    }

    public byte[] xifrar_b(String fitxer, String clauXifrat) {

        byte[] res = debug2.encrypt(aesKey, fitxer);


        return res;
    }

    public String desxifrar(byte[] fitxerenc) {

        //       String res = debug.decrypt(clauXifrat, fitxerenc);
        String res = debug2.decrypt(aesKey, fitxerenc);

        return res;
    }

    private String llegirFitxer(File fitxer) throws FileNotFoundException, IOException {
        StringBuilder content = new StringBuilder();
        BufferedInputStream bufferIn;
        bufferIn = new BufferedInputStream(new FileInputStream(fitxer));
        byte[] aux = new byte[1024];
        int bytesread;
        while ((bytesread = bufferIn.read(aux, 0, 1024)) != -1) {
            content.append(new String(aux, 0, bytesread));
        }
        return content.toString();
    }

    class Debug2 {

        byte[] encrypt(SecretKey aesKey, String text) {



            Cipher aesCipher;
            try {
                // Create the cipher
                aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Encrypt.NoSuchAlgorithmException_2 " + e.toString());
                return null;
            } catch (NoSuchPaddingException e) {
                System.out.println("Encrypt.NoSuchPaddingException " + e.toString());
                return null;
            }
            //We use the generated aesKey from above to initialize the Cipher object for encryption:

            try {
                // Initialize the cipher for encryption
                aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);
            } catch (InvalidKeyException e) {
                System.out.println("Encrypt.InvalidKeyException " + e.toString());
                return null;
            }

            // Our cleartext
            byte[] cleartext = text.getBytes();

            // Encrypt the cleartext
            byte[] ciphertext;
            try {
                ciphertext = aesCipher.doFinal(cleartext);
            } catch (IllegalBlockSizeException e) {
                System.out.println("Encrypt.IllegalBlockSizeException " + e.toString());
                return null;
            } catch (BadPaddingException e) {
                System.out.println("Encrypt.BadPaddingException " + e.toString());
                return null;
            }




            return ciphertext;
        }

        public String decrypt(SecretKey aesKey, String encryptedtext) {
            return decrypt(aesKey, encryptedtext.getBytes());
        }

        public String decrypt(SecretKey aesKey, byte[] encryptedbytes) {

            Cipher aesCipher;
            try {
                // Create the cipher
                aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            } catch (NoSuchAlgorithmException e) {
                System.out.println("Decrypt.NoSuchAlgorithmException_2 " + e.toString());
                return null;
            } catch (NoSuchPaddingException e) {
                System.out.println("Decrypt.NoSuchPaddingException " + e.toString());
                return null;
            }

            //We use the generated aesKey from above to initialize the Cipher object for encryption:
            try {
                // Initialize the same cipher for decryption
                aesCipher.init(Cipher.DECRYPT_MODE, aesKey);
            } catch (InvalidKeyException e) {
                System.out.println("Decrypt.InvalidKeyException " + e.toString());
                return null;
            }
            System.out.println("Descifrando encryptedbytes:" + new String(encryptedbytes));
            System.out.println("Descifrando aesCipher" + aesCipher.toString());
            byte[] cleartext;
            try {
                // Decrypt the ciphertext
                cleartext = aesCipher.doFinal(encryptedbytes);
            } catch (IllegalBlockSizeException e) {
                System.out.println("Decrypt.IllegalBlockSizeException " + e.toString());
                return null;
            } catch (BadPaddingException e) {
                System.out.println("Decrypt.BadPaddingException " + e.toString());
                return null;
            }
            System.out.println("->" + new String(cleartext) + "<-->" + new String(encryptedbytes) + "<-");

            return new String(cleartext);


        }

        class Debug3 {

            public byte[] encrypt(SecretKey key, byte[] content) {
                byte[] ciphertext = null;
                try {
                    Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                    aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);


                    ciphertext = aesCipher.doFinal(content);
                } catch (Exception ex) {
                    Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
                }
                return ciphertext;
            }

            public byte[] decrypt(SecretKey key, byte[] content) {
                byte[] cleartext1;
                try {
                    Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
    
      aesCipher.init(Cipher.DECRYPT_MODE, aesKey);

      // Decrypt the ciphertext
      cleartext1 = aesCipher.doFinal(content);
      
      
      return cleartext1;
                } catch (NoSuchAlgorithmException ex) {
                    Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
                } catch (NoSuchPaddingException ex) {
                    Logger.getLogger(AES.class.getName()).log(Level.SEVERE, null, ex);
                }

            }
        }
    }
}

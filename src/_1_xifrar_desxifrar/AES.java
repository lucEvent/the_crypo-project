package _1_xifrar_desxifrar;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rafael.jose.lucena
 */
public class AES {

    private Debug debug;
  //  private String key = "thisisasecretkey";
    
    public AES() {
        debug = new Debug();
    }
    
    public String xifrar(File fitxer, String clauXifrat) throws FileNotFoundException, IOException {
        return xifrar(llegirFitxer(fitxer), clauXifrat);
    }

    public String xifrar(String fitxer, String clauXifrat) {

        String res = debug.encrypt(clauXifrat, fitxer);

        return res;
    }

    public String desxifrar(File fitxerenc, String clauXifrat) throws FileNotFoundException, IOException {
        return desxifrar(llegirFitxer(fitxerenc), clauXifrat);
    }

    public String desxifrar(String fitxerenc, String clauXifrat) {

        String res = debug.decrypt(clauXifrat, fitxerenc);

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

    private class Debug {

        String algorithm = "AES";

        String encrypt(String key, String value) {
            try {

                
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), algorithm);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.ENCRYPT_MODE, skeySpec);
                byte[] encrypted = cipher.doFinal(value.getBytes());
                System.out.println("encrypted string:" + (new String(encrypted)));
                System.out.println("skeySpec.getEncoded:" + (new String(skeySpec.getEncoded())));
                return new String(skeySpec.getEncoded());
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }

        public String decrypt(String key, String encrypted) {
            try {
                SecretKeySpec skeySpec = new SecretKeySpec(key.getBytes(), algorithm);
                Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
                cipher.init(Cipher.DECRYPT_MODE, new SecretKeySpec(skeySpec.getEncoded(), algorithm));

                byte[] original = cipher.doFinal(encrypted.getBytes());
                original.toString();
            } catch (Exception ex) {
                ex.printStackTrace();
            }
            return null;
        }
    }
}

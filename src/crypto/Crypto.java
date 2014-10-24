package crypto;

import _1_xifrar_desxifrar.AES;
import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;

/**
 * @author rafael.jose.lucena
 */
public class Crypto {

    private static final int XIFRAR = 0;
    private static final int DESXIFRAR = 1;

    public static void main(String[] args) {
        AES p1 = new AES();

        if (args.length < 4 && false) {
            System.out.println("/*************** USAGE ***************/");
            System.out.println("/*                                   */");
            System.out.println("/**/");
            System.out.println("/**/");
            System.out.println("/**/");
            System.out.println("/**/");
            System.out.println("/**/");
            System.out.println("/**/");
            System.out.println("/*************************************/");
        }

        String message;// = args[1];
        String key;// = Long.parseLong(args[2]);
        int option;// = Integer.parseInt(args[3]);

        message = "Minamessageplease";
        key = "123456789";

        //Debug
        
        example();
        
        System.out.println("");
        String m = "Este deberia ser el mensaje correcto";
        //Cifrar
        byte[] m_cifrado = p1.xifrar_b("", m);
        System.out.println("Mensage inicial: " + m);
        System.out.println("Mensage cifrado: " + new String(m_cifrado));

        //Descifrar
        String m_descifrado = p1.desxifrar(m_cifrado);

        System.out.println("Mensage descifr: " + m_descifrado + " size:" + m_descifrado.length());

        if (true) {
            return;
        }
        //End debug



        option = DESXIFRAR;
        String res;
        switch (option) {
            case XIFRAR:
                //     res = p1.xifrar(message, key);
                break;
            case DESXIFRAR:
                //      res = p1.desxifrar(message, key);
                break;
            default:
                res = "";
        }
        //   System.out.println("Resultat:" + res);
    }

    private static void example() {
        try {
            KeyGenerator keygen = KeyGenerator.getInstance("AES");
            SecretKey aesKey = keygen.generateKey();
            Cipher aesCipher;

            // Create the cipher
            aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
            // Initialize the cipher for encryption
            aesCipher.init(Cipher.ENCRYPT_MODE, aesKey);

            // Our cleartext
            byte[] cleartext = "Este deberia ser el mensaje correcto".getBytes();

            // Encrypt the cleartext
            byte[] ciphertext = aesCipher.doFinal(cleartext);

            // Initialize the same cipher for decryption
            aesCipher.init(Cipher.DECRYPT_MODE, aesKey);

            // Decrypt the ciphertext
            byte[] cleartext1 = aesCipher.doFinal(ciphertext);

            System.out.println("cleartext-> lenght:" + cleartext.length + " - " + new String(cleartext));
            System.out.println("cleartext1-> lenght:" + cleartext1.length + " - " + new String(cleartext1));
        } catch (Exception ex) {
            System.out.println("Error");
        }

    }
}

package _1_xifrar_desxifrar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rafael.jose.lucena
 */
public class AES {

    public AES() {
    }

    public AES(String mensaje, String clave) {


        byte[] codes = mensaje.getBytes();
        if (codes == null || codes.length == 0) {
            System.out.println("Code es null o vacio");
            return;
        }
        SecretKeySpec secretkey = Crypt.generateKey(clave);
        if (secretkey == null) {
            System.out.println("Secretkey es null");
            return;
        }

        byte[] encode = Crypt.encrypt(codes, secretkey);
        if (encode == null || encode.length == 0) {
            System.out.println("Encode es null o vacio");
            return;
        }
        byte[] desencode = Crypt.decrypt(encode, secretkey);
        if (desencode == null || desencode.length == 0) {
            System.out.println("Desencode es null o vacio");
            return;
        }

        System.out.println("Mensaje incial   :" + mensaje);
        System.out.println("Mnsje codificado :" + new String(encode));
        System.out.println("Msj descodificado:" + new String(desencode));
    }

    public byte[] cifrar(byte[] datos, String clave) {
        SecretKeySpec key = Crypt.generateKey(clave);
        byte[] res = Crypt.encrypt(datos, key);
        return null;
    }

    public byte[] desxifrar(byte[] datos_cifrados, String clave) {
        SecretKeySpec key = Crypt.generateKey(clave);
        byte[] res = Crypt.decrypt(datos_cifrados, key);
        return res;
    }

    static class Crypt {

        public static SecretKeySpec generateKey(String clave) {
            SecretKeySpec key = null;
            try {
                byte[] pass = (clave).getBytes("UTF-8");
                MessageDigest sha = MessageDigest.getInstance("SHA-1");
                pass = sha.digest(pass);
                pass = Arrays.copyOf(pass, 16); // use only first 128 bit
                key = new SecretKeySpec(pass, "AES");
            } catch (Exception e) {
                System.out.println("Crypt.generateKey.Exception:" + e.toString());
            }
            return key;
        }

        public static byte[] encrypt(byte[] content, SecretKeySpec key) {
            byte[] ciphertext = null;
            try {
                Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                aesCipher.init(Cipher.ENCRYPT_MODE, key);
                ciphertext = aesCipher.doFinal(content);
            } catch (Exception e) {
                System.out.println("Crypt.encrypt.Exception:" + e.toString());
            }
            return ciphertext;
        }

        public static byte[] decrypt(byte[] content, SecretKeySpec key) {
            byte[] desciphertext = null;
            try {
                Cipher aesCipher = Cipher.getInstance("AES/ECB/PKCS5Padding");
                aesCipher.init(Cipher.DECRYPT_MODE, key);
                desciphertext = aesCipher.doFinal(content);
            } catch (Exception e) {
                System.out.println("Crypt.decrypt.Exception:" + e.toString());
            }
            return desciphertext;
        }
    }
    private static final String ACCION_CIFRAR = "-c";
    private static final String ACCION_DESCIFRAR = "-d";
    private static final int CIFRAR = 0;
    private static final int DESCIFRAR = 1;

    public static void main(String[] args) {

        if (args.length < 5) {
            System.out.println("Error: no hay suficientes parametros\n");
            muestra_usage();
            return;
        }

        String accion = args[1];
        int iaccion;
        if (ACCION_CIFRAR.equals(accion)) {
            iaccion = CIFRAR;
        } else if (ACCION_DESCIFRAR.equals(accion)) {
            iaccion = DESCIFRAR;
        } else {
            System.out.println("Error: " + accion + " accion no definida\n");
            muestra_usage();
            return;
        }

        String clave = args[2];

        File fichero_in = new File(args[3]);
        byte[] datos = leer_fichero(fichero_in);
        if (datos == null) {
            System.out.println("Error: no se encuentra el fichero de entrada\n");
            muestra_usage();
        }
        File fichero_out = new File(args[4]);

        AES aes = new AES();
        byte[] resultado = null;
        switch (iaccion) {
            case CIFRAR:
                resultado = aes.cifrar(datos, clave);
                break;
            case DESCIFRAR:
                aes.desxifrar(datos, clave);
                break;
        }
        escribir_fichero(resultado, fichero_out);
    }

    public static byte[] leer_fichero(File fichero) {
        try {
            StringBuilder content = new StringBuilder();
            BufferedInputStream bufferIn;
            bufferIn = new BufferedInputStream(new FileInputStream(fichero));
            byte[] aux = new byte[1024];
            int bytesread;
            while ((bytesread = bufferIn.read(aux, 0, 1024)) != -1) {
                content.append(new String(aux, 0, bytesread));
            }
            return content.toString().getBytes();
        } catch (Exception e) {
            System.out.println("AES.leer_fichero.Exception:" + e.toString());
        }
        return null;
    }

    public static void escribir_fichero(byte[] datos, File fichero) {
        try {
            BufferedOutputStream bufferOut;
            bufferOut = new BufferedOutputStream(new FileOutputStream(fichero));
            bufferOut.write(datos, 0, datos.length);
        } catch (Exception e) {
            System.out.println("AES.leer_fichero.Exception:" + e.toString());
        }
    }

    public static void muestra_usage() {
        System.out.println("/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* USAGE *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/");
        System.out.println("/*                                                                       */");
        System.out.println("/* nombre_programa accion clave fichero_entrada fichero_salida           */");
        System.out.println("/*                                                                       */");
        System.out.println("/* nombre_programa: aes.jar                                              */");
        System.out.println("/* accion:    cifrar: -c                                                 */");
        System.out.println("/*         descifrar: -d                                                 */");
        System.out.println("/*                                                                       */");
        System.out.println("/* exemplo:                                                              */");
        System.out.println("/*    /java -jar aes.jar -c miclave mensaje.txt mensaje_cifrado.txt      */");
        System.out.println("/*                                                                       */");
        System.out.println("/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/");
    }
}

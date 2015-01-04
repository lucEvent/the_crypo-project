package _1_xifrar_desxifrar;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.security.MessageDigest;
import java.util.Arrays;
import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

/**
 * @author rafael.jose.lucena
 */
public class AES {

    public AES() {
    }

    public byte[] cifrar(byte[] datos, String clave) {
        System.out.println("Cifrando con clave:" + clave);
        SecretKeySpec key = Crypt.generateKey(clave);
        System.out.println("Cifrado");
        byte[] res = Crypt.encrypt(datos, key);
        return res;
    }

    public byte[] descifrar(byte[] datos_cifrados, String clave) {
        System.out.println("Descifrando con clave:" + clave);
        SecretKeySpec key = Crypt.generateKey(clave);
        byte[] res = Crypt.decrypt(datos_cifrados, key);
        System.out.println("Descifrando");
        return res;
    }

    public byte[] cifrar(byte[] datos, byte[] clave) {
        System.out.println("Descifrando con clave:" + new String(clave));
        SecretKeySpec key = new SecretKeySpec(clave, "AES");
        System.out.println("Cifrado");
        byte[] res = Crypt.encrypt(datos, key);
        return res;
    }

    public byte[] descifrar(byte[] datos_cifrados, byte[] clave) {
        System.out.println("Descifrando(b) con clave:" + new String(clave));
        SecretKeySpec key = generateKey(clave);
        byte[] res = Crypt.decrypt(datos_cifrados, key);
        System.out.println("Descifrando");
        return res;
    }

    SecretKeySpec generateKey(byte[] clave) {
        SecretKeySpec key = null;
        try {
            byte[] pass = clave;
            MessageDigest sha = MessageDigest.getInstance("SHA-1");
            pass = sha.digest(pass);
            pass = Arrays.copyOf(pass, 16); // use only first 128 bit
            key = new SecretKeySpec(pass, "AES");
        } catch (Exception e) {
            System.out.println("Crypt.generateKey.Exception:" + e.toString());
        }
        return key;
    }

    static class Crypt {

        static final String ALGORITHM = "AES";
        static final String PADDING = "SHA-1";
        static final String VERSION = "AES/CBC/PKCS5Padding";

        public static SecretKeySpec generateKey(String clave) {
            SecretKeySpec key = null;
            try {
                byte[] pass = (clave).getBytes("UTF-8");
                MessageDigest sha = MessageDigest.getInstance(PADDING);
                pass = sha.digest(pass);
                pass = Arrays.copyOf(pass, 16); // use only first 128 bit
                key = new SecretKeySpec(pass, ALGORITHM);
            } catch (Exception e) {
                System.out.println("Crypt.generateKey.Exception:" + e.toString());
            }
            return key;
        }

        public static byte[] encrypt(byte[] content, SecretKeySpec key) {
            byte[] result = null;
            try {
                byte[] enkey = key.getEncoded();
                IvParameterSpec iv = new IvParameterSpec(enkey);

                Cipher aesCipher = Cipher.getInstance(VERSION);
                aesCipher.init(Cipher.ENCRYPT_MODE, key, iv);
                byte[] encryptdata = aesCipher.doFinal(content);

                result = new byte[encryptdata.length + 16];
                System.arraycopy(iv.getIV(), 0, result, 0, 16);
                System.arraycopy(encryptdata, 0, result, 16, encryptdata.length);

            } catch (Exception e) {
                System.out.println("Crypt.encrypt.Exception:" + e.toString());
            }
            return result;
        }

        public static byte[] decrypt(byte[] content, SecretKeySpec key) {
            byte[] desciphertext = null;
            try {
                byte[] iv = new byte[16];
                for (int i = 0; i < iv.length; ++i) {
                    iv[i] = content[i];
                }
                byte[] data = new byte[content.length - 16];
                for (int i = 0; i < data.length; ++i) {
                    data[i] = content[i + 16];
                }

                Cipher aesCipher = Cipher.getInstance(VERSION);
                aesCipher.init(Cipher.DECRYPT_MODE, key, new IvParameterSpec(iv));
                desciphertext = aesCipher.doFinal(data);
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
        {

//       String file = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\bits.jpg.enc";
//       String clave = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\mySecretKey";
//       String out = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\resultat.jpg";
//
//        byte[] bclave = leer_fichero(new File(clave));
//        if (bclave.length != 16)
//        { System.out.println("Error clave mayor a 16 bytes"); return; }
//        
//        SecretKeySpec k =  new SecretKeySpec(bclave, Crypt.ALGORITHM);
//        
//        byte[] datos = leer_fichero(new File(file));
//
//        byte[] resultado = Crypt.decrypt(datos, k);
//
//        escribir_fichero(resultado, new File(out));

            String file = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\sun.jpg";
            String clave = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\SecretKey";
            String out = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\resultat.jpg.enc";

            byte[] bclave = leer_fichero(new File(clave));
            if (bclave.length != 16) {
                System.out.println("Error clave mayor a 16 bytes");
                return;
            }

            SecretKeySpec k = new SecretKeySpec(bclave, Crypt.ALGORITHM);

            byte[] datos = leer_fichero(new File(file));

            byte[] resultado = Crypt.encrypt(datos, k);

            escribir_fichero(resultado, new File(out));



            file = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\resultat.jpg.enc";
            clave = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\SecretKey";
            out = "C:\\Users\\rafael.jose.lucena\\Desktop\\C\\Jocs de prova\\mine\\resultat.jpg";

            bclave = leer_fichero(new File(clave));
            if (bclave.length != 16) {
                System.out.println("Error clave mayor a 16 bytes");
                return;
            }

            k = new SecretKeySpec(bclave, Crypt.ALGORITHM);

            datos = leer_fichero(new File(file));

            resultado = Crypt.decrypt(datos, k);

            escribir_fichero(resultado, new File(out));

            if (true) {
                return;
            }
        }


        if (args.length < 4) {
            System.out.println("Error: no hay suficientes parametros\n");
            muestra_usage();
            return;
        }

        String accion = args[0];
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
        String clave = args[1];

        byte[] bclave = leer_fichero(new File(args[1]));

        byte[] datos = leer_fichero(new File(args[2]));
        if (datos == null) {
            System.out.println("Error: no se encuentra el fichero de entrada\n");
            muestra_usage();
        }

        AES aes = new AES();
        File fichero_out = new File(args[3]);
        byte[] resultado = null;
        switch (iaccion) {
            case CIFRAR:
                resultado = aes.cifrar(datos, clave);
                break;
            case DESCIFRAR:
                resultado = aes.descifrar(datos, bclave);
//                resultado = aes.descifrar(datos, clave);
                break;
        }
        escribir_fichero(resultado, fichero_out);
    }

    public static byte[] leer_fichero(File fichero) {
        System.out.println("Leyendo archivo");
        try {
            FileInputStream in = new FileInputStream(fichero);
            byte[] res = new byte[(int) fichero.length()];
            in.read(res);
            in.close();
            return res;
        } catch (Exception e) {
            System.out.println("AES.leer_fichero.Exception:" + e.toString());
        }
        return null;
    }

    public static void escribir_fichero(byte[] datos, File fichero) {
        System.out.println("Guardando resultado");
        try {
            BufferedOutputStream bufferOut;
            bufferOut = new BufferedOutputStream(new FileOutputStream(fichero));
            bufferOut.write(datos, 0, datos.length);
            bufferOut.flush();
            bufferOut.close();
        } catch (Exception e) {
            System.out.println("AES.escribir_fichero.Exception:" + e.toString());
        }
    }

    public static void muestra_usage() {
        System.out.println("/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-* USAGE *-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/");
        System.out.println("/*                                                                       */");
        System.out.println("/* nombre_programa accion clave fichero_entrada fichero_salida           */");
        System.out.println("/*                                                                       */");
        System.out.println("/* nombre_programa: aes.jar                                              */");
        System.out.println("/* accion: cifrar: -c                                                    */");
        System.out.println("/* descifrar: -d                                                         */");
        System.out.println("/*                                                                       */");
        System.out.println("/* exemplo:                                                              */");
        System.out.println("/* /java -jar aes.jar -c miclave mensaje.txt mensaje_cifrado.txt         */");
        System.out.println("/*                                                                       */");
        System.out.println("/*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*-*/");
    }
}
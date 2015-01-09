package _2_claus;

import crypto.io;
import java.io.File;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.NoSuchAlgorithmException;
import java.security.NoSuchProviderException;
import java.security.SecureRandom;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Keys {
    
    private final String RSAprivatefilename = "privadaRSA.pem";
    private final String RSApublicfilename = "publicaRSA.pem";
    private final String ECCprivatefilename = "privadaECC.pem";
    private final String ECCpublicfilename = "publicaECC.pem";
    
    private final String RSA_PRIVATE_PEM_begin = "-----BEGIN RSA PRIVATE KEY-----";
    private final String RSA_PRIVATE_PEM_end = "-----END RSA PRIVATE KEY-----";
    
    private final String ECC_PRIVATE_PEM_begin = "-----BEGIN EC PRIVATE KEY-----";
    private final String ECC_PRIVATE_PEM_end = "-----END EC PRIVATE KEY-----";

    private final String PUBLIC_PEM_begin = "-----BEGIN PUBLIC KEY-----";
    private final String PUBLIC_PEM_end = "-----END PUBLIC KEY-----";

    /*
        RSAkey n
        entrada: n nombre de bits de la clau RSA a generar;
        sortida: dos fitxers, en format PEM, un que contingui la clau publica RSA i un altre amb la
                clau privada RSA.
    */
    public void RSAkey(int n) {
        try {
            //Steps
            // 1 - Create a Key Pair Generator
            KeyPairGenerator generator = KeyPairGenerator.getInstance("RSA");
            // 2 - Initialize the Key Pair Generator
            generator.initialize(n, SecureRandom.getInstanceStrong());
            // 3 - Generate the Pair of Keys
            KeyPair pair = generator.generateKeyPair();
            
            // 4 - Guardant clau privada
            byte[] key = pair.getPrivate().getEncoded();

            byte[] kprivpem = pemFormat(RSA_PRIVATE_PEM_begin, key, RSA_PRIVATE_PEM_end);
            io.escribir_fichero(kprivpem, new File(RSAprivatefilename));

            // 5 - Guardant clau publica
            key = pair.getPublic().getEncoded();
           
            byte[] kpubpem = pemFormat(PUBLIC_PEM_begin, key, PUBLIC_PEM_end);
            io.escribir_fichero(kpubpem, new File(RSApublicfilename));

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Keys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    /*
        ECCkey corba
        entrada: corba nom de la corba amb la que es generara la clau ECC (podeu fer servir la
                  comanda openssl ecparam -list curves per obtenir el llistat de corbes);
        sortida: dos fitxers, en format PEM, un que contingui la clau p´ublica ECDH i un altre amb
                la clau privada ECDH.
    */
    public void ECCkey(String corba) {
        try {
            //Steps
            // 1 - Create a Key Pair Generator
            KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
            // 2 - Initialize the Key Pair Generator
            ECGenParameterSpec ecsp;
            ecsp = new ECGenParameterSpec(corba);
            generator.initialize(ecsp);
            // 3 - Generate the Pair of Keys
            KeyPair pair = generator.generateKeyPair();

            // 4 - Guardant clau privada
            byte[] key = pair.getPrivate().getEncoded();

            byte[] kprivpem = pemFormat(ECC_PRIVATE_PEM_begin, key, ECC_PRIVATE_PEM_end);
            io.escribir_fichero(kprivpem, new File(ECCprivatefilename));

            // 5 - Guardant clau publica
            key = pair.getPublic().getEncoded();

            byte[] kpubpem = pemFormat(PUBLIC_PEM_begin, key, PUBLIC_PEM_end);
            io.escribir_fichero(kpubpem, new File(ECCpublicfilename));
            System.out.println("File en:"+new File(ECCpublicfilename).getAbsolutePath());
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Keys.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidAlgorithmParameterException ex) {
            Logger.getLogger(Keys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public static void main(String[] args) {
        Keys k = new Keys();
//        k.RSAkey(2048);
        
    String curva_nombre = "secp256r1";
    k.ECCkey(curva_nombre);
    }

    private byte[] pemFormat(String begin, byte[] key, String end) {
        String fl = System.getProperty("line.separator");

        byte[] key64 = Base64.getEncoder().encode(key);
        
        StringBuilder res = new StringBuilder();
        res.append(begin);
        for (int i = 0; i < key64.length; i++) {
            if (i%64 == 0) {
                res.append(fl);
            }
            res.append((char)key64[i]);
        }
        res.append(fl).append(end);
        return res.toString().getBytes();
    }

}

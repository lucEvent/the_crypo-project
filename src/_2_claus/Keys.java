package _2_claus;

import crypto.io;
import java.io.File;
import java.io.IOException;
import java.math.BigInteger;
import java.security.AlgorithmParameterGenerator;
import java.security.AlgorithmParameters;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.crypto.KeyAgreement;
import javax.crypto.spec.DHParameterSpec;

public class Keys {
    
    public final String RSAprivatefilename = "privadaRSA.pem";
    public final String RSApublicfilename = "publicaRSA.pem";
    public final String ECCprivatefilename = "privadaECC.pem";
    public final String ECCpublicfilename = "publicaECC.pem";
    
    public static final String RSA_PRIVATE_PEM_begin = "-----BEGIN RSA PRIVATE KEY-----";
    public static final String RSA_PRIVATE_PEM_end = "-----END RSA PRIVATE KEY-----";
    
    public static final String ECC_PRIVATE_PEM_begin = "-----BEGIN EC PRIVATE KEY-----";
    public static final String ECC_PRIVATE_PEM_end = "-----END EC PRIVATE KEY-----";

    public static final String PUBLIC_PEM_begin = "-----BEGIN PUBLIC KEY-----";
    public static final String PUBLIC_PEM_end = "-----END PUBLIC KEY-----";

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
        sortida: dos fitxers, en format PEM, un que contingui la clau publica ECDH i un altre amb
                la clau privada ECDH.
    */
    /*
    corba pot ser: secp256, secp384 ó secp521
    */
    public void ECCkey(String corba) {
        try {
               //Steps
            // 1 - Create a Key Pair Generator
            KeyPairGenerator generator = KeyPairGenerator.getInstance("EC");
            // 2 - Initialize the Key Pair Generator
            ECGenParameterSpec ecsp = new ECGenParameterSpec(corba);
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
    
    public void OpenSSLECKey() {
            Runtime r = Runtime.getRuntime();
            
            String[] args1 = {"openssl", "ecparam", "-param_enc", "explicit", "-name", "secp256r1", "-genkey", "-outform", "PEM", "-out", "ec-openssl.pem"};
            String[] args2 = {"openssl", "ec", "-param_enc", "explicit", "-inform", "PEM", "-in", "ec-openssl.pem", "-pubout", "-outform", "DER", "-out", "ec-openssl.der"};
        try {

            Process p = r.exec(args1);
        } catch (IOException ex) {
            Logger.getLogger(Keys.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
 
    private byte[] pemFormat(String begin, byte[] key, String end) {
        String fl = System.getProperty("line.separator");
        System.out.println("Key has length: "+key.length);
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

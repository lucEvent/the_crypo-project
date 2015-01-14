package _3_signatura_vericacio_signatura;

import _2_claus.Keys;
import crypto.io;
import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidKeyException;
import java.security.KeyFactory;
import java.security.NoSuchAlgorithmException;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.Signature;
import java.security.SignatureException;
import java.security.spec.PKCS8EncodedKeySpec;
import java.security.spec.X509EncodedKeySpec;
import java.util.Base64;
import java.util.logging.Level;
import java.util.logging.Logger;

public class Signatura {
    
    static final String ALGORITHM = "EC";

    private byte[] readPEMFile(String fitxerclau, String heading, String ending) throws Exception {
        byte[] clau = io.leer_fichero(new File(fitxerclau));

        // Remove the first and last lines
        String algorithm = "" + ((char) clau[11]) + ((char) clau[12]) + ((char) clau[13]);
        System.out.println("Algoritme: " + algorithm);
        if (!algorithm.equals("EC ")) {
            throw new Exception("El fitxer de clau no es valid");
        }

        String skey = new String(clau);
        String s2key = skey.replaceFirst(heading, "");
        String s3key = s2key.replaceAll("\n", "");
        String s4key = s3key.replaceFirst(ending, "");

        {
            //  System.out.println("substring2 es:"+s2key);
            //System.out.println();
            System.out.println("substring3 es:" + s3key);
            System.out.println();
            System.out.println("substring4 es:" + s4key);
            System.out.println("sizes: " + s4key.length() + " vs " + s2key.length());
        }

        return Base64.getDecoder().decode(s4key);
    }

    public PrivateKey getPrivateKeyFromPEMFile(String fitxer_clau) throws Exception {
        byte[] encoded = readPEMFile(fitxer_clau, Keys.ECC_PRIVATE_PEM_begin, Keys.ECC_PRIVATE_PEM_end);

        PKCS8EncodedKeySpec keySpec = new PKCS8EncodedKeySpec(encoded);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        return keyFactory.generatePrivate(keySpec);
    }

    private PublicKey getPublicKeyFromPEMFile(String fitxer_clau) throws Exception {
        byte[] encoded = readPEMFile(fitxer_clau, Keys.PUBLIC_PEM_begin, Keys.PUBLIC_PEM_end);

        X509EncodedKeySpec keySpec = new X509EncodedKeySpec(encoded);

        KeyFactory keyFactory = KeyFactory.getInstance(ALGORITHM);

        return keyFactory.generatePublic(keySpec);
    }

     /*   Signar fitxer clauSignatura
                        entrada: fitxer fitxer a signar;
                        clauSignatura fitxer amb la clau per signar, en format PEM;
        sortida: fitxer.signature fittxer amb la signatura.
    */
    public byte[] signar(String fitxer, String clauSignatura) {
        byte[] data_sig = io.leer_fichero(new File(fitxer));
        try {

            PrivateKey privKey = getPrivateKeyFromPEMFile(clauSignatura);

            Signature ecsign = Signature.getInstance("SHA256withECDSA");
            ecsign.initSign(privKey);
            ecsign.update(data_sig);

            byte[] signature = ecsign.sign();

            io.escribir_fichero(signature, new File(new File(fitxer).getName() + "-firma"));
            return signature;
        } catch (SignatureException ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    /*  Verificar fitxer fitxer.signature clauVerificacio
        entrada: fitxer fitxer del que es vol verificar la signatura;
                 fitxer.signature fitxer amb la signatura a verificar;
                 clauVerificacio fitxer amb la clau per vericar la signatura, en format PEM;
        sortida: True o False.
    */
    public boolean verificar(String fitxer, String fitxer_signature, String clauVerificacio) {
        byte[] data_ver = io.leer_fichero(new File(fitxer));
        byte[] data_sig = io.leer_fichero(new File(fitxer_signature));

        boolean result = false;
        try {
            PublicKey key = getPublicKeyFromPEMFile(clauVerificacio);
            Signature signature = Signature.getInstance("SHA256withECDSA");

            signature.initVerify(key);
            signature.update(data_ver);
            result = signature.verify(data_sig);
            System.out.println("Valid: " + result);

        } catch (NoSuchAlgorithmException ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvalidKeyException ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (SignatureException ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        } catch (Exception ex) {
            Logger.getLogger(Signatura.class.getName()).log(Level.SEVERE, null, ex);
        }
        return result;
    }

    public static void main(String[] args) throws Exception {

        int mode = 1; //0 mine, 1 other
//        k.RSAkey(2048);
        String curva_nombre = "secp256r1";

        String file = "C:\\Users\\Rafa\\Downloads\\foto.jpg";

        if (mode == 0) {
            Keys k = new Keys();
            k.ECCkey(curva_nombre);
            new Signatura().signar(file, k.ECCprivatefilename);

        } else {
            String clau = "C:\\Users\\Rafa\\Downloads\\sistemaC\\privateEC.pem";

            new Signatura().signar(file, clau);
        }

    }

}

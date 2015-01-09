package _3_signatura_vericacio_signatura;

import crypto.io;
import java.io.File;

public class Signatura {

    /*
    EXEMPLES:
    to sign:
    
    KeyPair pair = GenerateKeys();
Signature ecdsaSign = Signature.getInstance("SHA256withECDSA", "BC");
ecdsaSign.initSign(pair.getPrivate());
ecdsaSign.update(plaintext.getBytes("UTF-8"));
byte[] signature = ecdsaSign.sign();

    And to verify:

Signature ecdsaVerify = Signature.getInstance("SHA256withECDSA", "BC");
ecdsaVerify.initVerify(pair.getPublic());
ecdsaVerify.update(plaintext.getBytes("UTF-8"));
boolean result = ecdsaVerify.verify(signature);
    */
    
    
    
 /*   Signar fitxer clauSignatura
        entrada: fitxer fitxer a signar;
                 clauSignatura txer amb la clau per signar, en format PEM;
        sortida: fitxer.signature fittxer amb la signatura.
*/
    public void signar(String fitxer, String clauSignatura) {
        byte[] data_sig = io.leer_fichero(new File(fitxer));
        byte[] clau = io.leer_fichero(new File(clauSignatura));
        
        
        
        //return fitxer.signature
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
        byte[] clau = io.leer_fichero(new File(clauVerificacio));
        
        
        //return true or false
        return false;
    }
    
}

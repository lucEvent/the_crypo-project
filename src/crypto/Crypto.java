package crypto;

import _1_xifrar_desxifrar.AES;
import _2_claus.Keys;
import _3_signatura_vericacio_signatura.Signatura;
import _4_enviar_rebre_un_missatge.Gestor_Missatges;

/**
 * @author rafael.jose.lucena
 */
public class Crypto {
    
    public static void main(String[] args) {
        String function = args[0].toLowerCase();
        
        if (function.equals("xifrar")) {
            String fitxer = args[1];
            String clauXifrat = args[2];
            
            new AES().xifrar(fitxer, clauXifrat);
            
        } else if (function.equals("desxifrar")) {
            String fitxer_enc = args[1];
            String clauXifrat = args[2];
            
            new AES().desxifrar(fitxer_enc, clauXifrat);
            
        } else if (function.equals("rsakey")) {
            String snbits = args[1];
            int nbits = Integer.parseInt(snbits);
            
            new Keys().RSAkey(nbits);
        } else if (function.equals("ecckey")) {
            // corba: secp256, secp384 o secp521
            String corba = args[1];

//            String corba = "secp256r1";
            new Keys().ECCkey(corba);
        } else if (function.equals("signar")) {
            String fitxer = args[1];
            String clauSignatura = args[2];
            
            new Signatura().signar(fitxer, clauSignatura);
            
        } else if (function.equals("verificar")) {
            String fitxer = args[1];
            String fitxer_signature = args[2];
            String clauVerificacio = args[3];
            
            boolean result_ver = new Signatura().verificar(fitxer, fitxer_signature, clauVerificacio);
            if (result_ver == true) {
                System.out.println("Verificació correcte");
            } else {
                System.out.println("Verificació fallida");
            }
        } else if (function.equals("enviarMissatge")) {
            String missatge = args[1];
            String clauFirma = args[2];
            String clauPublica = args[3];
            String clauPrivada = args[4];
            
            new Gestor_Missatges().enviarMissatge(missatge, clauFirma, clauPublica, clauPrivada);
            
        } else if (function.equals("rebreMissatge")) {
            String crytpograma = args[1];
            String clauVerificacioDeFirma = args[2];
            String clauPrivada = args[3];
            String clauPublica = args[4];
            
            new Gestor_Missatges().rebreMissatge(crytpograma, clauVerificacioDeFirma, clauPrivada, clauPublica);
        }
        
    }
    
}

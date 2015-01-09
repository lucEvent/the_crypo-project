package _4_enviar_rebre_un_missatge;

import crypto.io;
import java.io.File;

public class Gestor_Missatges {

/*  enviarMissatge M clauDeFirma clauPublica clauPrivada
        entrada: M nom del fitxer a xifrar, el contingut es tractara com una llista de bytes;
        clauFirma nom del txer que conte la clau privada de rma del rmant;
        clauPublica nom del txer que conte la clau publica del receptor del missatge;
        clauPrivada (si es necessari) nom del txer que conte la clau privada per generar
                    la clau de sessio (KS);
    sortida: un fitxer binari amb KSEkE(MkF).
*/
    public void enviarMissatge(String M, String clauFirma, String clauPublica, String clauPrivada) {
        byte[] fitxer = io.leer_fichero(new File(M));
        byte[] firma = io.leer_fichero(new File(clauFirma));
        byte[] clau_pub = io.leer_fichero(new File(clauPublica));
        byte[] clau_priv = io.leer_fichero(new File(clauPrivada));
        
        
        //    sortida: un fitxer binari amb KSEkE(MkF).
    }
    
/*  rebreMissatge C clauVerificacioDeFirma clauPrivada clauPublica
        entrada: C nom del txer a desxifrar,
                 clauVerificacioDeFirma nom del txer que conte clau publica de vericacio de rma
                        del signant;
                 clauPrivada nom del txer que conte la clau privada corresponent a la clau publica
                        feta servir per xifrar el missatge;
                 clauPublica (si es necessari) nom del txer que conte la clau publica per generar la
                        clau de sessio (KS);
        sortida: dos fitxers (un que contingui M i un altre amb F) i un missatge indicat la validesa
                        de la signatura.
  */  
    public void rebreMissatge(String C, String clauVerificacioDeFirma, String clauPrivada, String clauPublica) {
        byte[] fitxer = io.leer_fichero(new File(C));
        byte[] clau_ver = io.leer_fichero(new File(clauVerificacioDeFirma));
        byte[] clau_priv = io.leer_fichero(new File(clauPrivada));
        byte[] clau_pub = io.leer_fichero(new File(clauPublica));
        
        
    //     return         sortida: dos fitxers (un que contingui M i un altre amb F) i un missatge indicat la validesa
      //                  de la signatura.
    }


}

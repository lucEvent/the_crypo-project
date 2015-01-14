package _4_enviar_rebre_un_missatge;

import _1_xifrar_desxifrar.AES;
import _2_claus.Keys;
import _3_signatura_vericacio_signatura.Signatura;
import crypto.io;
import java.io.File;

public class Gestor_Missatges {

    private byte[] concat(byte[] data, byte[] data2) {
        byte[] res_data = new byte[data.length + data2.length];
        System.arraycopy(data, 0, res_data, 0, data.length);
        System.arraycopy(data2, 0, res_data, data.length, data2.length);
        return res_data;
    }

/*  enviarMissatge M clauDeFirma clauPublica clauPrivada
        entrada: M nom del fifitxer a xifrar, el contingut es tractara com una llista de bytes;
        clauFirma nom del fitxer que conte la clau privada de firma del firmant;
        clauPublica nom del fitxer que conte la clau publica del receptor del missatge;
        clauPrivada (si es necessari) nom del fitxer que conte la clau privada per generar
                    la clau de sessio (KS);
    sortida: un fifitxer binari amb KSE||E(M||F).
*/
    public void enviarMissatge(String M, String clauFirma, String clauPublica, String clauPrivada) {
        byte[] M_data = io.leer_fichero(new File(M));
        
        AES aes = new AES();
        Keys keys = new Keys();
        Signatura sign = new Signatura();
    
    /*    
        Quan un usuari (l'emissor) vol enviar un missatge M a un altre (el receptor) procedeix de la manera seguent:
    1. Firma el missatge en clar M amb la seva clau privada, afegint-li els bytes corresponents a la firma;
        diguem M||F al missatge amb la seva signatura.
    */
        //Firmar M amb clauFirma
        byte[] f_data = sign.signar(M, clauFirma);
        //Afegir bytes corresponents a la firma
        byte[] f2_data = new byte[f_data.length+0];
        
        //Concatenar M||firma
        byte[] mf_data = concat(M_data, f2_data);
        
        String mf_file = "mf_file";
        io.escribir_fichero(mf_data, new File(mf_file));
        
        /*
            2. Genera una clau de sessio KS. Denotem per KSE la informacio per que el receptor pugui obtenir KS.
        */
        
        //Genera una clau de sessio KS. Denotem per KSE la informacio per que el receptor pugui obtenir KS. (AES)
        byte[] kse_data = aes.AESKey();
        
        
        /*
    3. Amb KS, xifra el missatge M||F fent servir un algorisme de clau secreta. Notarem per E(M||F)
        el missatge xifrat.
        */
        //Xifrar M||F amb la clau secreta generada
        byte[] emf_data = aes.xifrar(mf_file, AES.AESKEYFILENAME);
        
        /*
        4. Concatena KSE amb E(M||F) i envia el resultat KSE||E(M||F) al receptor.
      */
        //Concatenar KSE amb E(M||F)        
        byte[] kseemf = concat(kse_data, emf_data);
        
        //Guardar el resultat
        io.escribir_fichero(kseemf, new File("resultat_binari"));
    }
    
    /*  rebreMissatge C clauVerificacioDeFirma clauPrivada clauPublica
            entrada: C nom del fitxer a desxifrar,
                 clauVerificacioDeFirma nom del fitxer que conte clau publica de verificacio de firma
                        del signant;
                 clauPrivada nom del fitxer que conte la clau privada corresponent a la clau publica
                        feta servir per xifrar el missatge;
                 clauPublica (si es necessari) nom del fitxer que conte la clau publica per generar la
                        clau de sessio (KS);
            sortida: dos fifitxers (un que contingui M i un altre amb F) i un missatge indicat la validesa
                        de la signatura.
  */  
    public void rebreMissatge(String C, String clauVerificacioDeFirma, String clauPrivada, String clauPublica) {
        byte[] fitxer = io.leer_fichero(new File(C));
        byte[] clau_ver = io.leer_fichero(new File(clauVerificacioDeFirma));
        byte[] clau_priv = io.leer_fichero(new File(clauPrivada));
        byte[] clau_pub = io.leer_fichero(new File(clauPublica));
        
        
    //     return         sortida: dos fifitxers (un que contingui M i un altre amb F) i un missatge indicat la validesa de la signatura.
    }


}
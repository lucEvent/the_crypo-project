package crypto;

import _1_xifrar_desxifrar.AES;

/**
 * @author rafael.jose.lucena
 */
public class Crypto {
    
    private static final int XIFRAR = 0;
    private static final int DESXIFRAR = 1;
    
    
    public static void main(String[] args) {
        AES p1 = new AES();
        
        if (args.length < 4) {
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
        
   
        option = XIFRAR;
        String res;
        switch(option) {
            case XIFRAR:
                res = p1.xifrar(message, key);
                break;
            case DESXIFRAR:
                res = p1.desxifrar(message, key);
                break;
            default:
                res = "";
        }
        System.out.println("Resultat:"+res);
    }
}

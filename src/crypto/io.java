package crypto;

import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;

public class io {

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
}

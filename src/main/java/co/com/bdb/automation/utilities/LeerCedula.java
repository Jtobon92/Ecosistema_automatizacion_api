package co.com.bdb.automation.utilities;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class LeerCedula {

    public static String obtenerCedula(String rutaArchivo) throws IOException {
        return new String(Files.readAllBytes(Paths.get(rutaArchivo))).trim();


    }
}

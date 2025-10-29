package com.laura.ficheros.io;

import java.io.File;
import java.util.Scanner;

public class configuracionRutas {
    public static final Scanner sr = new Scanner(System.in);
    public static final String RUTA_FICHEROBin = "src/main/resources/alumno.dat";
    public static final String RUTA_FICHEROXML = "src/main/resources/alumno.xml";
    public static final String RUTA_FICHEROTxt = "src/main/resources/alumno.txt";
    public static final String RUTA_FICHEROCsv = "src/main/resources/alumno.csv";
    public static final File archivoBin = new File(RUTA_FICHEROBin);
    public static final File archivoXml = new File(RUTA_FICHEROXML);
    public static final File archivoTxt = new File(RUTA_FICHEROTxt);
    public static final File archivoCsv = new File(RUTA_FICHEROCsv);
}

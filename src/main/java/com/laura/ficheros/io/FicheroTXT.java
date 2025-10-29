package com.laura.ficheros.io;

import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;

import java.io.*;

public class FicheroTXT {


    public void guardarAlumnos(ListaAlumnos lista) {
        File archivo = configuracionRutas.archivoTxt;
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            //para cada Alumno alumno(objeto temporal) que haya dentro de lista.getAlumnos()
            for (Alumno alumno : lista.getAlumnos())
                // .println() hace el trabajo de .write() + .newLine() en un solo paso
                pw.println(alumno.toString()); // <-- Un solo paso
            //se pone toString porque es un objeto y le estas diciendo que tiene que leer
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // "Receta" para leer de TXT (usa FileReader/BufferedReader)
    public ListaAlumnos leerAlumnos() {
        ListaAlumnos listaleida = new ListaAlumnos();
        File archivo = configuracionRutas.archivoTxt;
        if (!archivo.exists()) {
            System.out.println("El archivo TXT no existe, creando..");
            guardarAlumnos(listaleida);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {

            String linea = "";
            while ((linea = br.readLine()) != null) {

                System.out.println(linea);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }

        return listaleida; // (devuelve la lista leída)
    }
}

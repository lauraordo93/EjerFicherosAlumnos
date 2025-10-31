package com.laura.ficheros.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;

import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;

public class FicheroJson {

    /*
    ¿Qué es la dependencia?
    Tu clase FicheroJson depende de un objeto Gson para hacer su trabajo.
    * */
    //NO HE CREADO EL CONSTRUCTOR
    //poniendole la linea GsonBuilder().setPrettyPrinting().create() general el formato json para que se vea "Bonito"
    private final Gson gson = new GsonBuilder().setPrettyPrinting().create();

    /* SI CREARA EL CONSTRUCTOR:
     public class FicheroJson {

     private final Gson gson;

     public FicheroJson(Gson gson) { // Pide el objeto Gson
        this.gson = gson;
    }
    // ...
}
    */
    public void guardarAlumnos(ListaAlumnos listaAlumnos) {
        File archivo = configuracionRutas.archivoJson;

        try (FileWriter fw = new FileWriter(archivo)) {
            gson.toJson(listaAlumnos.getAlumnos(), fw);
            System.out.println("Alumno guardado en fichero Json");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public ListaAlumnos leerAlumnos() {
        ListaAlumnos listaleida = new ListaAlumnos();
        File archivo = configuracionRutas.archivoJson;
        if (!archivo.exists()) {
            System.out.println("El archivo Json no existe, creando...");
            guardarAlumnos(listaleida);
        }
        try (FileReader fr = new FileReader(archivo)) {
            Alumno[] alumnos = gson.fromJson(fr, Alumno[].class);
            if (alumnos != null) {
                for (Alumno m : alumnos) {
                    listaleida.agregarAlumno(m);
                }
            }

        } catch (Exception e) {
            e.printStackTrace();
        }


        return listaleida;
    }

}

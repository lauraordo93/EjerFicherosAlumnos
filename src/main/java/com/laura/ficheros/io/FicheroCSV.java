package com.laura.ficheros.io;

import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;

import java.io.*;
import java.util.List;

public class FicheroCSV {
    public void guardarAlumnos(ListaAlumnos lista) {
        File archivo = configuracionRutas.archivoCsv;
        try (PrintWriter pw = new PrintWriter(new FileWriter(archivo))) {
            for (Alumno alumno : lista.getAlumnos())

                pw.println(alumno.toString());
            System.out.println("Alumnos guardados en fichero CSV");


        } catch (IOException e) {
            e.printStackTrace();
        }


    }



    public ListaAlumnos leerAlumnos() throws IOException {
        File archivo = configuracionRutas.archivoCsv;
        ListaAlumnos listaleida = new ListaAlumnos();
        if (!archivo.exists()) {
            System.out.println("El archivo CSV no existe...creando");
            guardarAlumnos(listaleida);
        }
        try (BufferedReader br = new BufferedReader(new FileReader(archivo))) {
            String linea;
            while ((linea = br.readLine()) != null) {
                if (linea.trim().isEmpty()) continue;

                // Formato esperado: expediente;nombre;apellidos;nota
                String[] partes = linea.split(";");

                // Validar que hay al menos 3 partes
                if (partes.length >= 3) {

                    String expediente = partes[0].trim().replaceAll("\\s+", "");
                    String nombre = partes[1].trim();
                    String apellidos = partes[2].trim();

                    // Inicializa el alumno sin nota
                    Alumno nuevoAlumno = new Alumno(expediente, nombre, apellidos);

                    //Si existe la parte 4(nota) procesarla
                    if (partes.length >= 4) {
                        try {
                            String notaStr = partes[3].trim();
                            Double nota = Double.parseDouble(notaStr);
                            nuevoAlumno.setNota(nota);

                        } catch (Exception e) {
                            e.printStackTrace();
                        }



                    }
                    listaleida.agregarAlumno(nuevoAlumno);
                }
            }
        }
        return listaleida;
    }
}


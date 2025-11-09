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
            System.out.println("Alumnos guardados en fichero TXT");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // Dentro de la clase FicheroTXT
    public ListaAlumnos leerAlumnos() {
        ListaAlumnos listaleida = new ListaAlumnos();
        File archivo = configuracionRutas.archivoTxt;

        if (!archivo.exists()) {
            System.out.println("El archivo TXT no existe, creando..");
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

                            // 2. Asignar la nota directamente al campo simple
                            nuevoAlumno.setNota(nota);

                        } catch (NumberFormatException ignored) {
                            // Si la nota no es un número válido, se ignora y queda null.
                        }
                    }

                    listaleida.agregarAlumno(nuevoAlumno);
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
            return new ListaAlumnos();
        }

        return listaleida;
    }
}
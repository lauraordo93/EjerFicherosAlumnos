package com.laura.ficheros.services;

import com.laura.ficheros.io.*;
import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AlumnoServices {
    private ListaAlumnos lista;
    private final Scanner sc = new Scanner(System.in);
    private final FicheroBinario ficheroBinario;
    private final FicheroCSV ficheroCSV;
    private final FicheroJson ficheroJson;
    private final FicheroTXT ficheroTXT;
    private final FicheroXML ficheroXML;


    public AlumnoServices(ListaAlumnos lista, FicheroBinario ficheroBinario, FicheroCSV ficheroCSV, FicheroJson ficheroJson, FicheroTXT ficheroTXT, FicheroXML ficheroXML) {
        this.lista = lista;
        this.ficheroBinario = ficheroBinario;
        this.ficheroCSV = ficheroCSV;
        this.ficheroJson = ficheroJson;
        this.ficheroTXT = ficheroTXT;
        this.ficheroXML = ficheroXML;

    }


    public void eliminarAlumno() throws IOException {
        System.out.println("Expediente: ");
        String expediente = sc.nextLine().trim();
        boolean eliminado = lista.eliminaAlumno(expediente);

        if (eliminado) {
            System.out.println("Éxito: Alumno " + expediente + " eliminado. Guardando cambios...");
            ficheroBinario.guardarAlumnos(lista);
            ficheroCSV.guardarAlumnos(lista);
            ficheroJson.guardarAlumnos(lista);
            ficheroTXT.guardarAlumnos(lista);
            ficheroXML.guardarAlumnos(lista);
        } else {
            System.out.println("Error: No se encontró ningún alumno con el expediente " + expediente + ".");
        }
    }

    public void insertarNota(String expediente, double nota) throws Exception {
        String expedienteLimpio = expediente.trim();

        // 2. Iterar sobre la LISTA ACTIVA EN MEMORIA (this.lista)
        //    Usamos un bucle para buscar el alumno
        for (Alumno alumno : this.lista.getAlumnos()) { // <-- Usar la lista interna activa

            // 3. Buscar y comparar de forma segura (limpiando el expediente almacenado)
            if (alumno.getExpediente().trim().equals(expedienteLimpio)) {

                // 4. Si se encuentra, añadir la nota
                alumno.setNota(nota);

                // Si la operación es exitosa, se puede añadir un mensaje de confirmación
                System.out.println("Nota " + nota + " añadida al alumno " + expedienteLimpio + " en la memoria.");
                return; // Salir del método tras el éxito
            }
        }

        // Si el bucle termina sin encontrar el alumno
        throw new Exception("No se encontró ningún alumno con el expediente " + expedienteLimpio + " para añadir la nota.");
    }

    public void modificarNota(String expediente, double nuevaNota) throws Exception {
        // 1. Limpieza de entrada
        String expedienteLimpio = expediente.trim();
        //ListaAlumnos listaCompleta = ficheroJson.leerAlumnos();
        for (Alumno a : this.lista.getAlumnos()) {
            if (a.getExpediente().trim().equals(expedienteLimpio)) {
                // 2. Aquí no hay necesidad de validar el índice.
                //    Simplemente validamos que la nota anterior existe (no es null)
                //    y la actualizamos.
                //Hemos cambiado a Double objeto
                if (a.getNota() != null) {
                    a.setNota(nuevaNota);
                }} else {

                throw new Exception("El alumno no tiene una nota registrada para modificar.");

            }
            break;
        }

    }

    public Double consultarNota(String expediente) {
        String expedienteLimpio = expediente.trim();
        for (Alumno a : this.lista.getAlumnos()) {
            if (a.getExpediente().trim().equals(expedienteLimpio)) {
                return a.getNota();
            }
        }
        return null;
    }

    public void cargarListaTXT() {
        // Actualiza la lista interna del servicio
        ListaAlumnos cargada = ficheroTXT.leerAlumnos();
        this.lista.setAlumnos(cargada.getAlumnos());

    }

    public void cargarListaXML(ListaAlumnos lista) {

        // 1. Lee el fichero y crea una NUEVA lista cargada (Referencia B).
        ListaAlumnos cargada = ficheroXML.leerAlumnos();

        // 2. Transfiere el CONTENIDO de la Referencia B a la Referencia A (this.lista).

        this.lista.setAlumnos(cargada.getAlumnos());

    }

    public void setLista(ListaAlumnos nuevaLista) {
        // 💡 Asegúrate de que esta línea apunta a tu variable interna
        this.lista = nuevaLista;
    }
}


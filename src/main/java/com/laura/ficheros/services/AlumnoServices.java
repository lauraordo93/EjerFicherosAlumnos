package com.laura.ficheros.services;

import com.laura.ficheros.io.*;
import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;

import java.io.IOException;
import java.util.List;
import java.util.Scanner;

public class AlumnoServices {
    private final ListaAlumnos lista;
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
        String expediente = sc.nextLine();
        lista.eliminaAlumno(expediente);
        ficheroBinario.guardarAlumnos(lista);
        ficheroCSV.guardarAlumnos(lista);
        ficheroJson.guardarAlumnos(lista);
        ficheroTXT.guardarAlumnos(lista);
        ficheroXML.guardarAlumnos(lista);
    }

    public void insertarNota(String expediente, double nota) {


        // 1. Leer el objeto contenedor ListaAlumnos
        ListaAlumnos listaCompleta = ficheroJson.leerAlumnos();

        // CORRECCIÓN 1: Debes iterar sobre la lista interna,

        for (Alumno a : listaCompleta.getAlumnos()) {
            if (a.getExpediente().equals(expediente)) {
                a.getNotas().add(nota);
                break;
            }
        }

        this.ficheroJson.guardarAlumnos(listaCompleta);
        this.ficheroTXT.guardarAlumnos(listaCompleta);
        this.ficheroBinario.guardarAlumnos(listaCompleta);
        this.ficheroXML.guardarAlumnos(listaCompleta);
        this.ficheroCSV.guardarAlumnos(listaCompleta);
    }

    public void modificarNota(String expediente, int indice, double nuevaNota) {
        ListaAlumnos listaCompleta = ficheroJson.leerAlumnos();
        for (Alumno a : listaCompleta.getAlumnos()) {
            if (a.getExpediente().equals(expediente)) {
                if (indice >= 0 && indice < a.getNotas().size()) {
                    a.getNotas().set(indice, nuevaNota);
                }
                break;
            }
        }
        this.ficheroJson.guardarAlumnos(listaCompleta);
        this.ficheroTXT.guardarAlumnos(listaCompleta);
        this.ficheroBinario.guardarAlumnos(listaCompleta);
        this.ficheroXML.guardarAlumnos(listaCompleta);
        this.ficheroCSV.guardarAlumnos(listaCompleta);
    }

    public List<Double> consultarNota(String expediente) {
        ListaAlumnos listaCompleta = ficheroJson.leerAlumnos();
        for (Alumno a : listaCompleta.getAlumnos()) {
            if (a.getExpediente().equals(expediente)) {
                return a.getNotas();
            }
        }
        return null;
    }


}

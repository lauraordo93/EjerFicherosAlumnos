package com.laura.ficheros.services;

import com.laura.ficheros.io.*;
import com.laura.ficheros.models.ListaAlumnos;

import java.io.IOException;
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
}

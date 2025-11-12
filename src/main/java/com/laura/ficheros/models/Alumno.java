package com.laura.ficheros.models;

import jakarta.xml.bind.annotation.*;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

//alumn@ (expediente, nombre y apellidos).
//LA SERIALIZACION ES EN BINARIO
@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "Alumno")
public class Alumno implements Serializable {
    private String expediente;
    private String nombre;
    private String apellidos;
    private Double nota;


    @XmlElement(name = "nota", type = Double.class)
    public Double getNota() {
        return nota;
    }

    public void setNota(Double nota) {
        this.nota = nota;
    }

    public Alumno() {
        //Constructor vacio requerido por JAXB
    }

    public Alumno(String expediente, String apellidos, String nombre, Double nota) {
        this.expediente = expediente;
        this.apellidos = apellidos;
        this.nombre = nombre;
        this.nota = nota;
    }

    //Constructor con todos los campos
    public Alumno(String expediente, String nombre, String apellidos) {
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    @XmlElement
    public String getExpediente() {
        return expediente;
    }

    public void setExpediente(String expediente) {
        this.expediente = expediente;
    }

    @XmlElement
    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }


    @XmlElement
    public String getApellidos() {
        return apellidos;
    }

    public void setApellidos(String apellidos) {
        this.apellidos = apellidos;
    }



    // Alumno.java (Código corregido para compatibilidad con FicheroTXT/CSV)
    @Override
    public String toString() {
        // Definimos el valor de la nota: si es null, se escribe vacío o N/A.
        // Para TXT/CSV, necesitamos que sea el valor o una cadena vacía para que el split funcione.
        String notaDisplay = (this.nota != null) ? String.valueOf(this.nota) : ""; // <-- Usa cadena vacía

        // Formato requerido por leerAlumnos: expediente;nombre;apellidos;nota
        return String.format("%s;%s;%s;%s",
                this.expediente,
                this.nombre,
                this.apellidos,
                notaDisplay);
    }
}


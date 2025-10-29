package com.laura.ficheros.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
//@XmlAccessorType(XmlAccessType.FIELD) //Usa todos los campos directamente
@XmlRootElement(name = "Aula")
public class ListaAlumnos implements Serializable {
    private List<Alumno> alumnos = new ArrayList<>();

    @XmlElement(name = "Alumno")
    public List<Alumno> getAlumnos() {
        return alumnos;
    }

    public void setAlumnos(List<Alumno> alumnos) {
        this.alumnos = alumnos;
    }

    //Metodo para agregar alumnos
    public void agregarAlumno(Alumno alumno) {
        this.alumnos.add(alumno);
    }

    //Metodo para mostrar alumnos
    public void mostrarAlumnos() {
        System.out.println("--- Lista de Alumnos en Memoria ---");

        // Comprueba si la lista está vacía
        if (alumnos.isEmpty()) {
            System.out.println("  [No hay Alumnos en la lista]");
        } else {
            // Recorre cada Alumno en la lista
            /*Traducción al español: "Para ( for ) cada ( : ) Alumno individual—que llamaremos
             * temporalmente alumno—que encuentres dentro de la lista grande alumnos... haz lo siguiente:"*/
            for (Alumno alumno : alumnos) {
                // Imprime el alumno. Java usa automáticamente alumno.toString()
                // (Por eso es importante que tu clase Alumno tenga un buen toString())
                System.out.println("\t- " + alumno);
            }
        }
        System.out.println("---------------------------------");
    }

    @Override
    public String toString() {
        return "ListaAlumnos{" +
                "alumnos=" + alumnos +
                '}';
    }
}


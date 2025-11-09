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
    private List<Double> notas = new ArrayList<>();

    @XmlElementWrapper(name = "Notas")
    @XmlElement(name = "nota", type = Double.class)
    public List<Double> getNotas() {
        return notas;
    }

    public void setNotas(List<Double> notas) {
        this.notas = notas;
    }

    public Alumno() {
        //Constructor vacio requerido por JAXB
    }

    public Alumno(String expediente, List<Double> notas, String apellidos, String nombre) {
        this.expediente = expediente;
        this.notas = notas;
        this.apellidos = apellidos;
        this.nombre = nombre;
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

    @Override
    public String toString() {
        return "Alumno{" +
                "expediente=" + expediente +
                ", nombre='" + nombre + '\'' +
                ", apellidos='" + apellidos + '\'' +
                ", notas='" + notas + '\'' +
                '}';
    }
}


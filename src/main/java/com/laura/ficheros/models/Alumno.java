package com.laura.ficheros.models;

import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;

import java.io.Serializable;

//alumn@ (expediente, nombre y apellidos).
//LA SERIALIZACION ES EN BINARIO
@XmlRootElement(name = "Alumno")
public class Alumno implements Serializable {
    private int expediente;
    private String nombre;
    private String apellidos;


    public Alumno() {
        //Constructor vacio requerido por JAXB
    }


    //Constructor con todos los campos
    public Alumno(int expediente, String nombre, String apellidos) {
        this.expediente = expediente;
        this.nombre = nombre;
        this.apellidos = apellidos;
    }

    @XmlElement
    public int getExpediente() {
        return expediente;
    }

    public void setExpediente(int expediente) {
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
                '}';
    }
}


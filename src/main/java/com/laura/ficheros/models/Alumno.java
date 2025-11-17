
package com.laura.ficheros.models;

import jakarta.xml.bind.annotation.XmlAccessType;
import jakarta.xml.bind.annotation.XmlAccessorType;
import jakarta.xml.bind.annotation.XmlElement;
import jakarta.xml.bind.annotation.XmlRootElement;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@XmlAccessorType(XmlAccessType.FIELD)
//@XmlAccessorType(XmlAccessType.PROPERTY)
@XmlRootElement(name = "Alumno")
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Alumno implements Serializable {

    @XmlElement
    private String expediente;

    @XmlElement
    private String nombre;

    @XmlElement
    private String apellidos;

    @XmlElement(name = "nota", type = Double.class)
    private Double nota;

    public Alumno(String expediente, String nombre, String apellidos) {
        this.expediente = expediente;
        this.nombre = nombre;
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



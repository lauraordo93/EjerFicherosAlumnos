package com.laura.ficheros.io;

import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;
import jakarta.xml.bind.JAXBContext;
import jakarta.xml.bind.JAXBException;
import jakarta.xml.bind.Marshaller;
import jakarta.xml.bind.Unmarshaller;

import java.io.File;

public class FicheroXML {
    public void guardarAlumnos(ListaAlumnos listaalumnos) {

        try {

            File archivo = configuracionRutas.archivoXml;
            JAXBContext context = JAXBContext.newInstance(ListaAlumnos.class);
            Marshaller marshaller = context.createMarshaller();
            marshaller.setProperty(Marshaller.JAXB_FORMATTED_OUTPUT, true);
            //Despues de poenr la estructura tenemos que guardar la lista en el fichero
            marshaller.marshal(listaalumnos, archivo);
            System.out.println("Alumnos guardados XML");
        } catch (Exception e) {
            System.err.println("ERROR al guardar XML: " + e.getMessage());
        }
    }

    /*
    public ListaAlumnos leerAlumnos(): Defines el metodo,
    que no recibe nada y promete devolver un objeto ListaAlumnos.
    */

    public ListaAlumnos leerAlumnos() {
        //Creamos la lista vacia:
        ListaAlumnos listaleida = new ListaAlumnos();

        //Comprobar si esta creado el archivo
        File archivo = configuracionRutas.archivoXml;
        if (!archivo.exists()) {
            System.out.println("Fichero XML no existe creando....");
            //Guardamos en lista leida
            return listaleida;
            //guardarAlumnos(listaleida);
        }
        try {

            /*
            ListaAlumnos listaleida = new ListaAlumnos();:
            Creas una instancia vacía de ListaAlumnos. Esto es importante
            porque te da un objeto "seguro" para trabajar.
            Si el archivo no existe o falla la lectura,
            el metodo al menos tendrá algo que devolver (aunque esté vacío).
             */
            JAXBContext context = JAXBContext.newInstance(ListaAlumnos.class);
            Unmarshaller unmarshaller = context.createUnmarshaller();
            //Convierte el XML archivos en un objeto java
            listaleida = (ListaAlumnos) unmarshaller.unmarshal(archivo);
            limpiarExpedientesCargados(listaleida);

        } catch (JAXBException e) {
            System.out.println("Error" + e.getMessage());
        }
        return listaleida; // (devuelve la lista leída)

    }

    private void limpiarExpedientesCargados(ListaAlumnos lista) {
        // Esto asegura que el dato almacenado no tiene caracteres invisibles.
        for (Alumno alumno : lista.getAlumnos()) {
            String expedienteActual = alumno.getExpediente();
            if (expedienteActual != null) {
                // Elimina todos los caracteres de espacio en blanco (\s incluye espacios normales, tabs, etc.)
                String expedienteLimpio = expedienteActual.replaceAll("\\s+", "").trim();
                alumno.setExpediente(expedienteLimpio);
            }
        }
    }
}

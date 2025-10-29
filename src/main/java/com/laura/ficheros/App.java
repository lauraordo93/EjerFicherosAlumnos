package com.laura.ficheros;

import com.laura.ficheros.io.FicheroBinario;
import com.laura.ficheros.io.FicheroCSV;
import com.laura.ficheros.io.FicheroTXT;
import com.laura.ficheros.io.FicheroXML;
import com.laura.ficheros.models.ListaAlumnos;

import static com.laura.ficheros.io.configuracionRutas.sr;

/*
* Tenéis que desarrollar una aplicación en Java para gestionar alumn@s y sus notas.
La aplicación debe permitir realizar las siguientes operaciones:
Dar de alta un alumn@ (expediente, nombre y apellidos).
Dar de baja un alumn@ (por expediente).
Insertar una nota a un alumn@ (por expediente).
Modificar la nota de un alumn@.
Consultar la nota de un alumn@ (por expediente).
Consultar todas las notas.
La idea es crear diferentes proyectos para implementar estas funcionalidades, utilizando distintos tipos de almacenamiento:
* fichero de texto, CSV, binario, XML y JSON, manteniendo la misma interfaz de operaciones.
Se recomienda usar POJOs con Lombok para simplificar getters, setters, constructores y toString. (opcional)*/
public class App {
    public static void main(String[] args) {
// --- 1. PREPARACIÓN (El Jefe contrata a TODOS los especialistas) ---
        FicheroBinario gestorBinario = new FicheroBinario();
        FicheroXML gestorXML = new FicheroXML();
        FicheroCSV gestorCSV = new FicheroCSV();
        FicheroTXT gestorTXT = new FicheroTXT();
        // --- 2. EL VASO (La lista en MEMORIA) ---
        //    (Cargamos desde XML por defecto, por ejemplo)
        System.out.println("Cargando datos iniciales desde XML...");

        // --- 2. PREPARACIÓN (El Jefe coge su "Vaso") ---
        //    (Vamos a empezar con un vaso vacío esta vez, es más simple)
        //Creamos instancia de objeto misAlumnos
        ListaAlumnos misAlumnos = new ListaAlumnos();
        //Creamos menu
        boolean salir = false;
        String opcion = "";
        do {
            System.out.println("\n--- GESTOR DE ALUMNOS ---");
            System.out.println("¿Con qué formato de fichero quieres trabajar?");
            System.out.println("1. Ficheros TXT (.txt)");
            System.out.println("2. Ficheros CSV (.csv)");
            System.out.println("3. Ficheros XML (.xml)");
            System.out.println("4. Ficheros Binarios (.dat)");
            System.out.println("5. Salir");

            //Declaramos variable opcion para que el usuario pueda elegir
            opcion = sr.nextLine().trim().toLowerCase();
            switch (opcion) {
                case "1":
                  // gestorTXT.guardarAlumnos();
                    break;
                case "2":

                    break;
                case "3":


                    break;
                case "4":

                    break;
                case "5":
                    salir = true;
                    break;
            }


        } while (!salir);

        sr.close();


    }

    // (Esto va DENTRO de la clase Main, pero FUERA del metodo main)

    /**
     * Muestra el sub-menú para operar con ficheros TXT.
     * Recibe la lista de memoria actual y la devuelve (por si se recarga).
     */

    //Hay que crear 3 clases mas con cada una de los Ficheros
    public static ListaAlumnos gestionarTXT(ListaAlumnos lista, FicheroTXT gestor) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO TXT ---");
            System.out.println("1. Añadir Alumno (a la memoria)");
            System.out.println("2. Listar Alumnos (de la memoria)");
            System.out.println("3. Guardar cambios en TXT (Almacén)");
            System.out.println("4. Cargar datos desde TXT (Almacén)");
            System.out.println("5. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();

            switch (opcionSubMenu) {
                case "1":
                    // 1. Pides los datos
                    // ... (lógica para pedir datos) ...
                    // 2. Creas el alumno
                    // Alumno nuevo = new Alumno(nombre, ...);
                    // 3. Lo añades al "Vaso" (la lista en memoria)
                    // lista.add(nuevo);
                    System.out.println("Alumno añadido a la memoria. No olvides guardar (Opción 3).");
                    break;
                case "2":
                    // Miras el "Vaso"
                    lista.mostrarAlumnos(); // (Tu metodo de ListaAlumnos)
                    break;
                case "3":
                    // Le das el "Vaso" al "Especialista"
                    System.out.println("Guardando en TXT...");
                    gestor.guardarAlumnos(lista);
                    break;
                case "4":
                    // Reemplazas el "Vaso"
                    System.out.println("Cargando desde TXT...");
                    lista = gestor.leerAlumnos();
                    System.out.println("¡Datos cargados desde TXT!");
                    break;
                case "5":
                    System.out.println("Volviendo al menú principal...");
                    break;
                default:
                    System.out.println("Opción no válida.");
                    break;
            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }
}

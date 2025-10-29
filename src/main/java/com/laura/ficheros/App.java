package com.laura.ficheros;

import com.laura.ficheros.io.*;
import com.laura.ficheros.models.Alumno;
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
        FicheroJson gestorJson = new FicheroJson();

        /* PARA UTILIZAR LA LIBRERIA GSON HAY 2 OPCIONES Si creo el constructor dentro de la clase:
        * 1. Crea la configuración de Gson que quieres usar
          Gson configuracionGson = new GsonBuilder().setPrettyPrinting().create();

        * 2. Inyecta esa configuración al crear el gestor
          FicheroJson gestorJson = new FicheroJson(configuracionGson); // 👈 ¡Aquí están los parámetros!
         */



        // --- 2. EL VASO (La lista en MEMORIA) ---
        //    (Cargamos desde XML por defecto, por ejemplo)
        System.out.println("Cargando datos iniciales...");






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
            System.out.println("2. Ficheros CSV (.csv)");//Falta
            System.out.println("3. Ficheros XML (.xml)");
            System.out.println("4. Ficheros Binarios (.dat)");
            System.out.println("5. Ficheros Json (.json)");//Falta
            System.out.println("6. Salir");

            //Declaramos variable opcion para que el usuario pueda elegir
            opcion = sr.nextLine().trim().toLowerCase();
            switch (opcion) {
                case "1":
                    misAlumnos = gestionarTXT(misAlumnos, gestorTXT);
                case "2":

                    break;
                case "3":

                    misAlumnos = gestionarXML(misAlumnos, gestorXML);
                    break;
                case "4":
                    misAlumnos = gestionarBinario(misAlumnos, gestorBinario);
                    break;
                case "5":
                    misAlumnos = gestionarJson(misAlumnos, gestorJson);
                    break;
                case "6":
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
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine();
                    System.out.println("Inserta número de expediente alumno: ");
                    int expediente = sr.nextInt();
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
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

            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }

    public static ListaAlumnos gestionarXML(ListaAlumnos lista, FicheroXML gestor) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO XML ---");
            System.out.println("1. Añadir Alumno (a la memoria)");
            System.out.println("2. Listar Alumnos (de la memoria)");
            System.out.println("3. Guardar cambios en XML (Almacén)");
            System.out.println("4. Cargar datos desde XML (Almacén)");
            System.out.println("5. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine();
                    System.out.println("Inserta número de expediente alumno: ");
                    int expediente = sr.nextInt();
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
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
                    System.out.println("Guardando en XML...");
                    gestor.guardarAlumnos(lista);
                    break;
                case "4":
                    // Reemplazas el "Vaso"
                    System.out.println("Cargando desde XML...");
                    lista = gestor.leerAlumnos();
                    System.out.println("¡Datos cargados desde XML!");
                    break;
                case "5":
                    System.out.println("Volviendo al menú principal...");
                    break;

            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }

    public static ListaAlumnos gestionarBinario(ListaAlumnos lista, FicheroBinario gestor) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO BINARIO ---");
            System.out.println("1. Añadir Alumno (a la memoria)");
            System.out.println("2. Listar Alumnos (de la memoria)");
            System.out.println("3. Guardar cambios en BINARIO (Almacén)");
            System.out.println("4. Cargar datos desde BINARIO (Almacén)");
            System.out.println("5. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine();
                    System.out.println("Inserta número de expediente alumno: ");
                    int expediente = sr.nextInt();
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
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
                    System.out.println("Guardando en BINARIO...");
                    gestor.guardarAlumnos(lista);
                    break;
                case "4":
                    // Reemplazas el "Vaso"
                    System.out.println("Cargando desde BINARIO...");
                    lista = gestor.leerAlumnos();
                    System.out.println("¡Datos cargados desde BINARIO!");
                    break;
                case "5":
                    System.out.println("Volviendo al menú principal...");
                    break;

            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }

    public static ListaAlumnos gestionarJson(ListaAlumnos lista, FicheroJson gestor) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO JSON ---");
            System.out.println("1. Añadir Alumno (a la memoria)");
            System.out.println("2. Listar Alumnos (de la memoria)");
            System.out.println("3. Guardar cambios en Json (Almacén)");
            System.out.println("4. Cargar datos desde Json (Almacén)");
            System.out.println("5. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine();
                    System.out.println("Inserta número de expediente alumno: ");
                    int expediente = sr.nextInt();
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
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
                    System.out.println("Guardando en Json...");
                    gestor.guardarAlumnos(lista);
                    break;
                case "4":
                    // Reemplazas el "Vaso"
                    System.out.println("Cargando desde Json...");
                    lista = gestor.leerAlumnos();
                    System.out.println("¡Datos cargados desde Json!");
                    break;
                case "5":
                    System.out.println("Volviendo al menú principal...");
                    break;

            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }
}

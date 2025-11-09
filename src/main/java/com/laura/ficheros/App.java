package com.laura.ficheros;

import com.laura.ficheros.io.*;
import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;
import com.laura.ficheros.services.AlumnoServices;

import java.util.InputMismatchException;
import java.util.List;

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
        // --- 2. PREPARACIÓN (El Jefe coge su "Vaso") ---
        //    (Vamos a empezar con un vaso vacío esta vez, es más simple)
        //Creamos instancia de objeto misAlumnos
        ListaAlumnos misAlumnos = new ListaAlumnos();
// --- 1. PREPARACIÓN (El Jefe contrata a TODOS los especialistas) ---
        FicheroBinario gestorBinario = new FicheroBinario();
        FicheroXML gestorXML = new FicheroXML();
        FicheroCSV gestorCSV = new FicheroCSV();
        FicheroTXT gestorTXT = new FicheroTXT();
        FicheroJson gestorJson = new FicheroJson();
        AlumnoServices alumnoService = new AlumnoServices(
                misAlumnos,        // Parámetro 1: La lista vacía
                gestorBinario,      // Parámetro 2
                gestorCSV,          // Parámetro 3
                gestorJson,         // Parámetro 4
                gestorTXT,          // Parámetro 5
                gestorXML           // Parámetro 6
        );
        /* PARA UTILIZAR LA LIBRERIA GSON HAY 2 OPCIONES Si creo el constructor dentro de la clase:
        * 1. Crea la configuración de Gson que quieres usar
          Gson configuracionGson = new GsonBuilder().setPrettyPrinting().create();

        * 2. Inyecta esa configuración al crear el gestor
          FicheroJson gestorJson = new FicheroJson(configuracionGson); // 👈 ¡Aquí están los parámetros!
         */


        // --- 2. EL VASO (La lista en MEMORIA) ---
        //    (Cargamos desde XML por defecto, por ejemplo)
        System.out.println("Cargando datos iniciales...");


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
            System.out.println("5. Ficheros Json (.json)");
            System.out.println("6. Salir");

            //Declaramos variable opcion para que el usuario pueda elegir
            opcion = sr.nextLine().trim().toLowerCase();
            switch (opcion) {
                case "1":
                    misAlumnos = gestionarTXT(misAlumnos, gestorTXT, alumnoService);
                case "2":
                    misAlumnos = gestionarCSV(misAlumnos, gestorCSV, alumnoService);
                    break;
                case "3":
                    misAlumnos = gestionarXML(misAlumnos, gestorXML, alumnoService);
                    break;
                case "4":
                    misAlumnos = gestionarBinario(misAlumnos, gestorBinario, alumnoService);
                    break;
                case "5":
                    misAlumnos = gestionarJson(misAlumnos, gestorJson, alumnoService);
                    break;
                case "6":
                    salir = true;
                    break;
            }


        } while (!salir);

        sr.close();


    }


    /**
     * Muestra el sub-menú para operar con ficheros TXT.
     * Recibe la lista de memoria actual y la devuelve (por si se recarga).
     */

    //Hay que crear 3 clases mas con cada una de los Ficheros
    public static ListaAlumnos gestionarTXT(ListaAlumnos lista, FicheroTXT gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO TXT ---");
            System.out.println("1. Añadir Alumno ");
            System.out.println("2. Cargar Alumnos desde TXT");
            System.out.println("3. Eliminar Alumno");
            System.out.println("4. Añadir Nota");
            System.out.println("5. Modificar Nota");
            System.out.println("6. Eliminar Nota");
            System.out.println("7. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine().trim();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine().trim();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine().trim();
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
                    System.out.println("Alumno añadido a la memoria.");
                    System.out.println("Guardando cambios en TXT...");
                    gestor.guardarAlumnos(lista);
                    System.out.println("Guardado completado.");
                    break;
                case "2":
                    System.out.println("Cargando desde TXT...");
                    alumnoService.cargarListaTXT();

                    System.out.println("¡Datos cargados desde TXT!");
                    break;
                case "3":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine().trim();
                    // Llama al método y captura el resultado (true/false)
                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");
                        try {
                            gestor.guardarAlumnos(lista); // Guardado
                            System.out.println("Los cambios se han guardado permanentemente en el archivo TXT.");
                        } catch (Exception e) {
                            System.err.println("Error al guardar los cambios en el TXT.");
                        }
                    } else {
                        System.out.println("Error: No se encontró ningún alumno con el expediente " + expedienteAEliminar + " en la memoria.");
                    }
                    break;

                case "4":
                    //se añaden todas las notas que se quiera
                    System.out.println("Inserta nota por expediente:");
                    System.out.println("Expediente: ");
                    expediente = sr.nextLine().trim();
                    System.out.println("Nota: ");
                    double nota = sr.nextDouble();
                    sr.nextLine(); // NECESARIO: Consumir el salto de línea pendiente después de nextDouble()/nextInt()

                    try {
                        alumnoService.insertarNota(expediente, nota);
                        System.out.println("Nota insertada en la memoria. Guardando cambios en TXT...");
                        gestor.guardarAlumnos(lista);
                        System.out.println("Cambios guardados en el archivo TXT.");
                    } catch (Exception e) {
                        System.err.println("ERROR al insertar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case "5":
                    System.out.println("Ingresa expediente para modificar nota:");
                    try {
                        expediente = sr.nextLine().trim();
                        //Capturamos la lista devuelta por el metodo consultar nota
                        List<Double> notas = alumnoService.consultarNota(expediente);
                        //Comprobamos si se encuentran notas y las mostramos si es asi
                        if (notas == null || notas.isEmpty()) {
                            System.out.println("Alumno no encontrado o no tiene notas registradas.");
                            break;
                        }

                        System.out.println("\nNotas actuales del alumno (Posición | Nota):");

                        // 3. Imprimir la lista con su posición (índice + 1)
                        for (int i = 0; i < notas.size(); i++) {
                            System.out.println((i + 1) + " | " + notas.get(i));
                        }
                        System.out.println("\nIngresa la posicion de la nota que quieres cambiar");
                        int pos = sr.nextInt();
                        sr.nextLine();//Consumir salto de linea
                        System.out.println("Ingrese la nueva nota:");
                        double nuevaNota = sr.nextDouble();
                        sr.nextLine();//Consumir salto de linea
                        alumnoService.modificarNota(expediente, pos, nuevaNota);
                        System.out.println("El alumno con el expediente:" + expediente + " tiene una nueva nota: " + nuevaNota);

                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: La entrada no es un número válido. Inténtalo de nuevo.");
                        sr.nextLine(); // Limpiar el buffer
                    } catch (Exception e) {
                        System.err.println("ERROR al modificar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "6":
                    //Eliminar nota
                case "7":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }

    public static ListaAlumnos gestionarXML(ListaAlumnos lista, FicheroXML gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO XML ---");
            System.out.println("1. Añadir Alumno ");
            System.out.println("2. Cargar Alumnos desde XML");
            System.out.println("3. Eliminar Alumno");
            System.out.println("4. Añadir Nota");
            System.out.println("5. Modificar Nota");
            System.out.println("6. Eliminar Nota");
            System.out.println("7. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine().trim();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine().trim();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine().trim();
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
                    System.out.println("Alumno añadido a la memoria.");
                    System.out.println("Guardando cambios en XML...");
                    gestor.guardarAlumnos(lista);
                    System.out.println("Guardado completado.");
                    break;
                case "2":
                    System.out.println("Cargando desde XML...");
                    //alumnoService.cargarListaXML();
                    //gestor.leerAlumnos().mostrarAlumnos();
                    lista = gestor.leerAlumnos();

                    // Opcional: Ahora puedes ver la lista que acabas de cargar
                    lista.mostrarAlumnos();

                    // Y asegúrate de actualizar la lista del servicio para las operaciones (notas)
                    alumnoService.cargarListaXML(lista);
                    System.out.println("¡Datos cargados desde XML!");
                    break;
                case "3":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine().trim();
                    // Llama al método y captura el resultado (true/false)
                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");
                        try {
                            gestor.guardarAlumnos(lista); // Guardado
                            System.out.println("Los cambios se han guardado permanentemente en el archivo TXT.");
                        } catch (Exception e) {
                            System.err.println("Error al guardar los cambios en el TXT.");
                        }
                    } else {
                        System.out.println("Error: No se encontró ningún alumno con el expediente " + expedienteAEliminar + " en la memoria.");
                    }
                    break;

                case "4":
                    //se añaden todas las notas que se quiera
                    System.out.println("Inserta nota por expediente:");
                    System.out.println("Expediente: ");
                    expediente = sr.nextLine().trim();
                    System.out.println("Nota: ");
                    double nota = sr.nextDouble();
                    sr.nextLine(); // NECESARIO: Consumir el salto de línea pendiente después de nextDouble()/nextInt()

                    try {
                        alumnoService.insertarNota(expediente, nota);
                        System.out.println("Nota insertada en la memoria. Guardando cambios en XML...");
                        lista.mostrarAlumnos();
                        gestor.guardarAlumnos(lista);
                        System.out.println("Cambios guardados en el archivo XML.");
                    } catch (Exception e) {
                        System.err.println("ERROR al insertar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case "5":
                    System.out.println("Ingresa expediente para modificar nota:");
                    try {
                        expediente = sr.nextLine().trim();
                        //Capturamos la lista devuelta por el metodo consultar nota
                        List<Double> notas = alumnoService.consultarNota(expediente);
                        //Comprobamos si se encuentran notas y las mostramos si es asi
                        if (notas == null || notas.isEmpty()) {
                            System.out.println("Alumno no encontrado o no tiene notas registradas.");
                            break;
                        }

                        System.out.println("\nNotas actuales del alumno (Posición | Nota):");

                        // 3. Imprimir la lista con su posición (índice + 1)
                        for (int i = 0; i < notas.size(); i++) {
                            System.out.println((i + 1) + " | " + notas.get(i));
                        }
                        System.out.println("\nIngresa la posicion de la nota que quieres cambiar");
                        int pos = sr.nextInt();
                        sr.nextLine();//Consumir salto de linea
                        System.out.println("Ingrese la nueva nota:");
                        double nuevaNota = sr.nextDouble();
                        sr.nextLine();//Consumir salto de linea
                        alumnoService.modificarNota(expediente, pos, nuevaNota);
                        System.out.println("El alumno con el expediente:" + expediente + " tiene una nueva nota: " + nuevaNota);

                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: La entrada no es un número válido. Inténtalo de nuevo.");
                        sr.nextLine(); // Limpiar el buffer
                    } catch (Exception e) {
                        System.err.println("ERROR al modificar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "6":
                    //Eliminar nota
                case "7":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("7"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }

    public static ListaAlumnos gestionarBinario(ListaAlumnos lista, FicheroBinario gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO BINARIO ---");
            System.out.println("1. Añadir Alumno (a la memoria)");
            System.out.println("2. Listar Alumnos (de la memoria)");
            System.out.println("3. Guardar cambios en BINARIO (Almacén)");
            System.out.println("4. Cargar datos desde BINARIO (Almacén)");
            System.out.println("5. Eliminar Alumno (Almacén)");
            System.out.println("6. Añadir Nota");
            System.out.println("7. Modificar Nota");
            System.out.println("8. Eliminar Nota");
            System.out.println("9. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine();
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
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine();
                    // Llama al método y captura el resultado (true/false)
                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");

                    } else {
                        System.out.println("Error: No se encontró ningún alumno con el expediente " + expedienteAEliminar + " en la memoria.");
                    }
                    break;
                case "6":
                    System.out.println("Inserta nota por expediente:");
                    System.out.println("Expediente: ");
                    expediente = sr.nextLine();
                    System.out.println("Nota: ");
                    double nota = sr.nextDouble();
                    sr.nextLine(); // NECESARIO: Consumir el salto de línea pendiente después de nextDouble()/nextInt()

                    try {
                        alumnoService.insertarNota(expediente, nota);
                    } catch (Exception e) {
                        System.err.println("ERROR al insertar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "7":

                    break;
                case "8":

                    break;

                case "9":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }

    public static ListaAlumnos gestionarJson(ListaAlumnos lista, FicheroJson gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO JSON ---");
            System.out.println("1. Añadir Alumno (a la memoria)");
            System.out.println("2. Listar Alumnos (de la memoria)");
            System.out.println("3. Guardar cambios en Json (Almacén)");
            System.out.println("4. Cargar datos desde Json (Almacén)");
            System.out.println("5. Eliminar Alumno (Almacén)");
            System.out.println("6. Añadir Nota");
            System.out.println("7. Modificar Nota");
            System.out.println("8. Eliminar Nota");
            System.out.println("9. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine();
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
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine();
                    // Llama al método y captura el resultado (true/false)
                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");

                    } else {
                        System.out.println("Error: No se encontró ningún alumno con el expediente " + expedienteAEliminar + " en la memoria.");
                    }
                    break;
                case "6":
                    System.out.println("Inserta nota por expediente:");
                    System.out.println("Expediente: ");
                    expediente = sr.nextLine();
                    System.out.println("Nota: ");
                    double nota = sr.nextDouble();
                    sr.nextLine(); // NECESARIO: Consumir el salto de línea pendiente después de nextDouble()/nextInt()

                    try {
                        alumnoService.insertarNota(expediente, nota);
                    } catch (Exception e) {
                        System.err.println("ERROR al insertar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "7":

                    break;
                case "8":

                    break;

                case "9":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }

    public static ListaAlumnos gestionarCSV(ListaAlumnos lista, FicheroCSV gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO CSV ---");
            System.out.println("1. Añadir Alumno (a la memoria)");
            System.out.println("2. Listar Alumnos (de la memoria)");
            System.out.println("3. Guardar cambios en CSV (Almacén)");
            System.out.println("4. Cargar datos desde CSV (Almacén)");
            System.out.println("5. Eliminar Alumno (Almacén)");
            System.out.println("6. Añadir Nota");
            System.out.println("7. Modificar Nota");
            System.out.println("8. Eliminar Nota");
            System.out.println("9. Volver al menú principal");


            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine();
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
                    System.out.println("Guardando en CSV...");
                    gestor.guardarAlumnos(lista);
                    break;
                case "4":
                    // Reemplazas el "Vaso"
                    System.out.println("Cargando desde CSV...");
                    lista = gestor.leerAlumnos();
                    System.out.println("¡Datos cargados desde CSV!");
                    break;
                case "5":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine();
                    // Llama al método y captura el resultado (true/false)
                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");

                    } else {
                        System.out.println("Error: No se encontró ningún alumno con el expediente " + expedienteAEliminar + " en la memoria.");
                    }
                    break;
                case "6":
                    System.out.println("Inserta nota por expediente:");
                    System.out.println("Expediente: ");
                    expediente = sr.nextLine();
                    System.out.println("Nota: ");
                    double nota = sr.nextDouble();
                    sr.nextLine(); // NECESARIO: Consumir el salto de línea pendiente después de nextDouble()/nextInt()

                    try {
                        alumnoService.insertarNota(expediente, nota);
                    } catch (Exception e) {
                        System.err.println("ERROR al insertar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "7":

                    break;
                case "8":

                    break;

                case "9":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("5"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }
}
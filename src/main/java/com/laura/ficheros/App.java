package com.laura.ficheros;

import com.laura.ficheros.io.*;
import com.laura.ficheros.models.Alumno;
import com.laura.ficheros.models.ListaAlumnos;
import com.laura.ficheros.services.AlumnoServices;

import java.io.IOException;
import java.util.InputMismatchException;

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
    public static void main(String[] args) throws IOException {

        //Creamos instancia de objeto misAlumnos
        ListaAlumnos misAlumnos = new ListaAlumnos();
        //Preparamos todos los objetos que vamos a necesitar
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


        System.out.println("Cargando datos iniciales...");


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
            System.out.println("5. Ficheros Json (.json)");
            System.out.println("6. Salir");

            //Declaramos variable opcion para que el usuario pueda elegir
            opcion = sr.nextLine().trim().toLowerCase();
            switch (opcion) {
                case "1":
                    misAlumnos = gestionarTXT(misAlumnos, gestorTXT, alumnoService);
                    break;
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


    public static ListaAlumnos gestionarTXT(ListaAlumnos lista, FicheroTXT gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO TXT ---");
            System.out.println("1. Dar de alta Alumno ");
            System.out.println("2. Cargar lista Alumnos desde TXT");
            System.out.println("3. Eliminar Alumno");
            System.out.println("4. Añadir Nota final");
            System.out.println("5. Modificar Nota");
            System.out.println("0. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine().trim();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine().trim();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine().trim();
                    //System.out.print("Nota: ");
                    //double nota = Double.parseDouble(sr.nextLine());
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
                    System.out.println("Alumno añadido a la memoria.");
                    System.out.println("Guardando cambios en TXT...");
                    gestor.guardarAlumnos(lista);
                    System.out.println("Guardado completado.");
                    break;
                case "2":
                    System.out.println("Cargando desde TXT...");
                    lista = gestor.leerAlumnos();
                    lista.mostrarAlumnos();
                    alumnoService.setLista(lista);

                    System.out.println("¡Datos cargados desde TXT!");
                    break;
                case "3":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine().trim();

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
                    String notaStr = sr.nextLine().trim();

                    try {
                        // Convierte la entrada de la nota
                        double nota = Double.parseDouble(notaStr);

                        // 3. Llama al alumservice con las variables ya limpias y convertidas
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
                        Double notaActual = alumnoService.consultarNota(expediente);
                        //Comprobamos si se encuentran notas y las mostramos si es asi
                        if (notaActual == null) {
                            System.out.println("Alumno no encontrado o no tiene notas registradas.");
                            break;
                        }


                        System.out.println("Ingrese la nueva nota:");
                        double nuevaNota = sr.nextDouble();
                        sr.nextLine();//Consumir salto de linea
                        alumnoService.modificarNota(expediente, nuevaNota);
                        System.out.println("El alumno con el expediente: " + expediente + " tiene una nueva nota: " + nuevaNota);
                        gestor.guardarAlumnos(lista);
                        System.out.println("Cambios guardados en el archivo.");

                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: La entrada no es un número válido. Inténtalo de nuevo.");
                        sr.nextLine(); // Limpiar el buffer
                    } catch (Exception e) {
                        System.err.println("ERROR al modificar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("0"));


        return lista;
    }

    public static ListaAlumnos gestionarXML(ListaAlumnos lista, FicheroXML gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO XML ---");
            System.out.println("1. Dar de alta Alumno ");
            System.out.println("2. Cargar lista de Alumnos desde XML");
            System.out.println("3. Eliminar Alumno");
            System.out.println("4. Añadir Nota final");
            System.out.println("5. Modificar Nota");
            System.out.println("0. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine().trim();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine().trim();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine().trim();
                    //System.out.print("Nota: ");
                    //double nota = Double.parseDouble(sr.nextLine());
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
                    System.out.println("Alumno añadido a la memoria.");
                    System.out.println("Guardando cambios en XML...");
                    gestor.guardarAlumnos(lista);
                    System.out.println("Guardado completado.");
                    break;
                case "2":
                    System.out.println("Cargando desde XML...");

                    lista = gestor.leerAlumnos();
                    lista.mostrarAlumnos();
                    alumnoService.setLista(lista);

                    System.out.println("¡Datos cargados desde XML!");
                    break;
                case "3":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine().trim();

                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");
                        try {
                            gestor.guardarAlumnos(lista); // Guardado
                            System.out.println("Los cambios se han guardado permanentemente en el archivo XML.");
                        } catch (Exception e) {
                            System.err.println("Error al guardar los cambios en el XML.");
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
                    double nota = Double.parseDouble(sr.nextLine());


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
                        Double notasActual = alumnoService.consultarNota(expediente);
                        //Comprobamos si se encuentran notas y las mostramos si es asi
                        if (notasActual == null) {
                            System.out.println("Alumno no encontrado o no tiene notas registradas.");
                            break;
                        }

                        System.out.println("Ingrese la nueva nota:");
                        double nuevaNota = sr.nextDouble();
                        sr.nextLine();//Consumir salto de linea
                        alumnoService.modificarNotaNotex(expediente, nuevaNota);
                        System.out.println("El alumno con el expediente: " + expediente + " tiene una nueva nota: " + nuevaNota);

                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: La entrada no es un número válido. Inténtalo de nuevo.");
                        sr.nextLine(); // Limpiar el buffer
                    } catch (Exception e) {
                        System.err.println("ERROR al modificar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("0"));


        return lista;
    }


    public static ListaAlumnos gestionarBinario(ListaAlumnos lista, FicheroBinario gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO BINARIO ---");
            System.out.println("1. Dar de alta Alumno ");
            System.out.println("2. Cargar lista de Alumnos desde BINARIO");
            System.out.println("3. Eliminar Alumno");
            System.out.println("4. Añadir Nota final");
            System.out.println("5. Modificar Nota");
            System.out.println("0. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine().trim();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine().trim();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine().trim();
                    //System.out.print("Nota: ");
                    //double nota = Double.parseDouble(sr.nextLine());
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
                    System.out.println("Alumno añadido a la memoria.");
                    System.out.println("Guardando cambios en BINARIO...");
                    gestor.guardarAlumnos(lista);
                    System.out.println("Guardado completado.");
                    break;
                case "2":
                    System.out.println("Cargando desde BINARIO...");

                    lista = gestor.leerAlumnos();
                    lista.mostrarAlumnos();
                    alumnoService.setLista(lista);

                    System.out.println("¡Datos cargados desde Binario!");
                    break;
                case "3":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine().trim();

                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");
                        try {
                            gestor.guardarAlumnos(lista); // Guardado

                        } catch (Exception e) {
                            System.err.println("Error al guardar los cambios en el Bin.");
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
                    double nota = Double.parseDouble(sr.nextLine());
                    sr.nextLine();

                    try {
                        alumnoService.insertarNota(expediente, nota);
                        System.out.println("Nota insertada en la memoria. Guardando cambios en Binario...");
                        lista.mostrarAlumnos();
                        gestor.guardarAlumnos(lista);
                        System.out.println("Cambios guardados en el archivo Binario.");
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
                        Double notasActual = alumnoService.consultarNota(expediente);
                        //Comprobamos si se encuentran notas y las mostramos si es asi
                        if (notasActual == null) {
                            System.out.println("Alumno no encontrado o no tiene notas registradas.");
                            break;
                        }


                        System.out.println("Ingrese la nueva nota:");
                        double nuevaNota = sr.nextDouble();
                        sr.nextLine();//Consumir salto de linea
                        alumnoService.modificarNotaNotex(expediente, nuevaNota);
                        System.out.println("El alumno con el expediente: " + expediente + " tiene una nueva nota: " + nuevaNota);

                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: La entrada no es un número válido. Inténtalo de nuevo.");
                        sr.nextLine(); // Limpiar el buffer
                    } catch (Exception e) {
                        System.err.println("ERROR al modificar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;

                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("0"));

        return lista;
    }


    public static ListaAlumnos gestionarJson(ListaAlumnos lista, FicheroJson gestor, AlumnoServices alumnoService) {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO JSON ---");
            System.out.println("1. Dar de alta Alumno ");
            System.out.println("2. Cargar lista de Alumnos desde Json");
            System.out.println("3. Eliminar Alumno");
            System.out.println("4. Añadir Nota final");
            System.out.println("5. Modificar Nota");
            System.out.println("0. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine().trim();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine().trim();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine().trim();
                    //System.out.print("Nota: ");
                    //double nota = Double.parseDouble(sr.nextLine());
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
                    System.out.println("Alumno añadido a la memoria.");
                    System.out.println("Guardando cambios en Json...");
                    gestor.guardarAlumnos(lista);
                    System.out.println("Guardado completado.");
                    break;
                case "2":
                    System.out.println("Cargando desde Json...");
                    lista = gestor.leerAlumnos();
                    lista.mostrarAlumnos();
                    alumnoService.setLista(lista);

                    System.out.println("¡Datos cargados desde Json!");
                    break;
                case "3":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine().trim();

                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");
                        try {
                            gestor.guardarAlumnos(lista); // Guardado
                            System.out.println("Los cambios se han guardado permanentemente en el archivo Json.");
                        } catch (Exception e) {
                            System.err.println("Error al guardar los cambios en el Json.");
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
                    double nota = Double.parseDouble(sr.nextLine());


                    try {
                        alumnoService.insertarNota(expediente, nota);
                        System.out.println("Nota insertada en la memoria. Guardando cambios en Json...");
                        lista.mostrarAlumnos();
                        gestor.guardarAlumnos(lista);
                        System.out.println("Cambios guardados en el archivo Json.");
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
                        Double notasActual = alumnoService.consultarNota(expediente);
                        //Comprobamos si se encuentran notas y las mostramos si es asi
                        if (notasActual == null) {
                            System.out.println("Alumno no encontrado o no tiene notas registradas.");
                            break;
                        }


                        System.out.println("Ingrese la nueva nota:");
                        double nuevaNota = sr.nextDouble();
                        sr.nextLine();//Consumir salto de linea
                        alumnoService.modificarNotaNotex(expediente, nuevaNota);
                        System.out.println("El alumno con el expediente: " + expediente + " tiene una nueva nota: " + nuevaNota);

                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: La entrada no es un número válido. Inténtalo de nuevo.");
                        sr.nextLine(); // Limpiar el buffer
                    } catch (Exception e) {
                        System.err.println("ERROR al modificar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("0"));

        return lista;
    }


    public static ListaAlumnos gestionarCSV(ListaAlumnos lista, FicheroCSV gestor, AlumnoServices alumnoService) throws IOException {
        String opcionSubMenu = "";

        do {
            System.out.println("\n--- MODO CSV ---");
            System.out.println("1. Dar de alta Alumno ");
            System.out.println("2. Cargar lista de Alumnos desde CSV");
            System.out.println("3. Eliminar Alumno");
            System.out.println("4. Añadir Nota final");
            System.out.println("5. Modificar Nota");
            System.out.println("0. Volver al menú principal");

            opcionSubMenu = sr.nextLine().trim();


            switch (opcionSubMenu) {
                case "1":
                    System.out.println("Inserta Nombre alumno: ");
                    String nombre = sr.nextLine().trim();
                    System.out.println("Inserta Apellidos alumno: ");
                    String apellidos = sr.nextLine().trim();
                    System.out.println("Inserta número de expediente alumno: ");
                    String expediente = sr.nextLine().trim();
                    //System.out.print("Nota: ");
                    //double nota = Double.parseDouble(sr.nextLine());
                    Alumno alumnoNuevo = new Alumno(expediente, nombre, apellidos);
                    lista.agregarAlumno(alumnoNuevo);
                    System.out.println("Alumno añadido a la memoria.");
                    System.out.println("Guardando cambios en CSV...");
                    gestor.guardarAlumnos(lista);
                    System.out.println("Guardado completado.");
                    break;
                case "2":
                    System.out.println("Cargando desde CSV...");
                    lista = gestor.leerAlumnos();
                    lista.mostrarAlumnos();
                    alumnoService.setLista(lista);

                    System.out.println("¡Datos cargados desde CSV!");
                    break;
                case "3":
                    System.out.println("Elimina alumno por expediente");
                    String expedienteAEliminar = sr.nextLine().trim();
                    boolean eliminado = lista.eliminaAlumno(expedienteAEliminar);
                    if (eliminado) {
                        System.out.println("Éxito: Alumno con expediente " + expedienteAEliminar + " eliminado ");
                        try {
                            gestor.guardarAlumnos(lista); // Guardado
                            System.out.println("Los cambios se han guardado permanentemente en el archivo CSV.");
                        } catch (Exception e) {
                            System.err.println("Error al guardar los cambios en el CSV.");
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
                    double nota = Double.parseDouble(sr.nextLine());
                    //sr.nextLine();

                    try {
                        alumnoService.insertarNota(expediente, nota);
                        System.out.println("Nota insertada en la memoria. Guardando cambios en CSV...");
                        lista.mostrarAlumnos();
                        gestor.guardarAlumnos(lista);
                        System.out.println("Cambios guardados en el archivo CSV.");
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
                        Double notasActual = alumnoService.consultarNota(expediente);
                        //Comprobamos si se encuentran notas y las mostramos si es asi
                        if (notasActual == null) {
                            System.out.println("Alumno no encontrado o no tiene notas registradas.");
                            break;
                        }


                        System.out.println("Ingrese la nueva nota:");
                        double nuevaNota = sr.nextDouble();
                        sr.nextLine();//Consumir salto de linea
                        alumnoService.modificarNota(expediente, nuevaNota);
                        System.out.println("El alumno con el expediente: " + expediente + " tiene una nueva nota: " + nuevaNota);

                    } catch (InputMismatchException e) {
                        System.err.println("ERROR: La entrada no es un número válido. Inténtalo de nuevo.");
                        sr.nextLine(); // Limpiar el buffer
                    } catch (Exception e) {
                        System.err.println("ERROR al modificar nota: " + e.getMessage());
                        e.printStackTrace();
                    }
                    break;
                case "0":
                    System.out.println("Volviendo al menú principal...");
                    break;


            }

        } while (!opcionSubMenu.equals("0"));

        // Devuelve la lista (actualizada o recargada) al Main
        return lista;
    }


}
package com.laura.ficheros.io;

import com.laura.ficheros.models.ListaAlumnos;

import java.io.*;
//LA SERIALIZACION ES EN BINARIO
public class FicheroBinario {

    public void guardarAlumnos(ListaAlumnos listaalumnos) {
        File archivo = configuracionRutas.archivoBin;
        try (BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(archivo));
             ObjectOutputStream oos = new ObjectOutputStream(bos)) {



            oos.writeObject(listaalumnos);
            oos.flush();//Vacia Buffer.
            System.out.println("Alumnos guardados en fichero Binario");


        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }


    }


    public ListaAlumnos leerAlumnos() {
        ListaAlumnos listaleida = new ListaAlumnos();
        File archivo = configuracionRutas.archivoBin;
        if (!archivo.exists() || archivo.length() == 0) {
            System.out.println("El archivo Binario no esta creado, creando...");
            guardarAlumnos(listaleida);

        }


        try (BufferedInputStream bis = new BufferedInputStream(new FileInputStream(archivo));
             ObjectInputStream ois = new ObjectInputStream(bis)) {

            /*Lee los bytes y los "re-ensambla"
            en tu objeto*/

            listaleida = (ListaAlumnos) ois.readObject();//Casteamos con lista de alumnos
            System.out.println("--Binario Leido--");
        } catch (Exception e) {
            System.out.println("Error" + e.getMessage());
        }
        return listaleida; // (devuelve la lista leída)
    }
}

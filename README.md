# 🧮 EjerFicherosAlumnos en desarrollo..

**Ejercicio práctico de lectura y escritura de ficheros en Java**, en desarrollo como parte del Grado Superior de Desarrollo de Aplicaciones Multiplataforma (DAM).

Este proyecto gestiona una lista de alumnos utilizando diferentes tipos de ficheros para almacenar y recuperar la información.

---

## 📁 Estructura del proyecto

```text
EjerFicherosAlumnos/
│
├── src/
│   ├── main/java/com/laura/ficheros/
│   │   ├── App.java
│   │   ├── io/
|   |   |   ├── configuracionRutas.java
│   │   │   ├── FicheroBinario.java
│   │   │   ├── FicheroCSV.java
│   │   │   ├── FicheroJson.java
│   │   │   ├── FicheroTXT.java
│   │   │   └── FicheroXML.java
│   │   ├── models/
│   │   │   ├── Alumno.java
│   │   │   └── ListaAlumnos.java
│   │   └── service/
|   |   |   ├── AlumnoServices.java 
│   └── test/java/com/laura/ficheros/AppTest.java
│
├── pom.xml
└── .gitignore
```
## ⚙️ Funcionalidades principales

- 📄 **Lectura y escritura en diferentes formatos:**
  - Binario 
  - CSV  
  - Json
  - TXT
  - XML  


- 👨‍🎓 **Gestión de alumnos:**
  - Alta Alumno, listado todos los Alumnos,Eliminación Alumno, Insertar nota final, modificar nota final.  
  - Almacenamiento persistente en los ficheros

- 🔧 **Configuración flexible:**
  - Rutas de ficheros configurables desde `configuracionRutas.java`

---

## 🧰 Tecnologías utilizadas

- **Lenguaje:** Java 17+  
- **Entorno:** IntelliJ IDEA  
- **Gestor de dependencias:** Maven  
- **Paradigma:** Programación Orientada a Objetos (POO)

---

## 🚀 Ejecución del proyecto

1. Clonar el repositorio:
   ```bash```
   git clone https://github.com/lauraordo93/EjerFicherosAlumnos.git



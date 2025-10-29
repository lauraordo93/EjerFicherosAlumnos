# 🧮 EjerFicherosAlumnos en desarrollo..

**Ejercicio práctico de lectura y escritura de ficheros en Java**, desarrollando como parte del Grado Superior de Desarrollo de Aplicaciones Multiplataforma (DAM).

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
│   │   │   ├── FicheroTXT.java
│   │   │   ├── FicheroCSV.java
│   │   │   ├── FicheroBinario.java
│   │   │   └── FicheroXML.java
│   │   ├── models/
│   │   │   ├── Alumno.java
│   │   │   └── ListaAlumnos.java
│   │   └── configuracionRutas.java
│   └── test/java/com/laura/ficheros/AppTest.java
│
├── pom.xml
└── .gitignore
```
## ⚙️ Funcionalidades principales

- 📄 **Lectura y escritura en diferentes formatos:**
  - TXT  
  - CSV  
  - Binario  
  - XML  
  - *(En desarrollo: JSON)*

- 👨‍🎓 **Gestión de alumnos:**
  - Alta, modificación y listado  
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
   ```bash``
   git clone https://github.com/lauraordo93/EjerFicherosAlumnos.git



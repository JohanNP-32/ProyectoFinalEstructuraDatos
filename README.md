# 🏢 Sistema de Administración de Condominios.

## 📜 Descripción del Proyecto

Este es un programa desarrollado en **Java** con la librería **Swing** para la interfaz gráfica. La aplicación permite una gestión integral de las operaciones de un condominio, incluyendo:

* **Gestión de Residentes:** Agregar, eliminar y buscar residentes.
* **Gestión de Tareas:** Asignar tareas a departamentos (Mantenimiento, Seguridad, Administración) con niveles de prioridad y dependencias.
* **Sistema Financiero:** Registrar pagos, aplicar cuotas y generar reportes financieros dinámicos.
* **Auditoría:** Mantiene un historial de todas las actividades realizadas en el sistema.

El proyecto implementa estructuras de datos avanzadas como **Colas de Prioridad**, **Tablas Hash**, **Árboles Binarios de Búsqueda** y **Grafos** para garantizar un rendimiento eficiente.

## ⚙️ Requisitos Previos

Para poder compilar y ejecutar este proyecto, necesitarás el siguiente software y configuraciones.

### Software
1.  **Apache NetBeans IDE:** El entorno de desarrollo integrado utilizado para este proyecto.
    * Puedes descargarlo desde su sitio web oficial: [**netbeans.apache.org/download/**](https://netbeans.apache.org/download/)

2.  **Librería FlatLaf:** Una librería de "Look and Feel" moderna para Swing que le da a la aplicación su apariencia profesional.
    * Descarga el archivo `.jar` desde la página: [**https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4.1**](https://repo1.maven.org/maven2/com/formdev/flatlaf/3.4.1)

### Configuración
Una vez instalado NetBeans y descargado FlatLaf, debes agregar la librería al proyecto:

1.  Crea una carpeta llamada `lib` en la raíz de tu proyecto. (Esto solo si la carpeta `lib` no existe todavia)
2.  Copia el archivo `flatlaf-3.4.1.jar` que descargaste dentro de la carpeta `lib`.
3.  En NetBeans, en el panel de **"Projects"**, haz clic derecho sobre la carpeta **"Libraries"**.
4.  Selecciona la opción **"Add JAR/Folder..."**.
5.  Navega hasta la carpeta `lib` de tu proyecto, selecciona el archivo `.jar` de FlatLaf y haz clic en "Open".

![Añadir JAR en NetBeans](https://i.imgur.com/your-image-url.png) ## ▶️ Instrucciones de Ejecución

Sigue estos pasos para ejecutar la aplicación:

1.  **Abrir el Proyecto:** En NetBeans, ve a `File > Open Project...` y selecciona la carpeta del proyecto.
2.  **Verificar Librería:** Asegúrate de que el archivo `flatlaf-x.x.x.jar` aparezca bajo la carpeta "Libraries" en el panel de "Projects".
3.  **Establecer Clase Principal (Si es necesario):**
    * Haz clic derecho sobre el nombre del proyecto y selecciona `Properties`.
    * Ve a la categoría `Run`.
    * Haz clic en el botón `Browse...` al lado de "Main Class".
    * Selecciona `CondominioGUI` y confirma.
4.  **Ejecutar:** Haz clic en el botón verde de **"Run Project"** (Play) en la barra de herramientas, o presiona la tecla `F6`.

## 📝 Notas Adicionales

* **Dependencia Crítica:** Si la librería `FlatLaf` no está correctamente añadida al classpath del proyecto, la aplicación lanzará un error al iniciar (`ClassNotFoundException` o similar) y **no se ejecutará**.
* **Versión de Java:** El proyecto fue desarrollado y probado con **Java JDK 21** o superior.
* **Punto de Entrada:** La clase principal que contiene el método `main` para iniciar la interfaz gráfica es `CondominioGUI.java`.

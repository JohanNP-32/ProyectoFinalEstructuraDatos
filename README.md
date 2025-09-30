# Proyecto Final Estructura de Datos


# Manual de Usuario – Administracion de Condominios (VITA S.A.)

##  Funcionalidades principales

---

### **1.Inicio**

Al iniciar sesión o abrir el sistema, se muestra el **Panel**, donde el usuario puede ver métricas rápidas del condominio:

- **Total de Residentes (👤):** Número total de residentes registrados en el sistema.  
- **Tareas Pendientes (📋):** Cantidad de tareas aún no completadas.  
- **Tareas Completadas (✔️):** Número de tareas ya finalizadas.  
- **Ingresos Totales (💰):** Suma de los pagos recibidos por los residentes.  

---

### **2 Gestión de Pagos**

Esta sección controla los pagos de los residentes. La pantalla principal muestra una tabla con la siguiente información:

| **Campo**       | **Descripción**                                                  |
|------------------|------------------------------------------------------------------|
| **ID**           | Identificador único del residente.                               |
| **Nombre**       | Nombre del residente.                                            |
| **Departamento** | Unidad habitacional asignada.                                    |
| **Deuda**        | Estado financiero: **Al corriente** si no debe, o monto en rojo si hay deuda. |

**Acciones disponibles**  
1. **Registrar Pago**  
   - Se solicita el ID del residente y el monto a pagar.  
   - Tras confirmar, el pago se registra y se actualiza la tabla.  

2. **Aplicar Cuota General**  
   - Aplica una cuota fija de **$800.00** a todos los residentes.  
   - Se solicita confirmación antes de ejecutar la acción.  

3. **Ver Historial de Pagos**  
   - Abre una tabla con:  
     - **Fecha y hora del pago**  
     - **ID y nombre del residente**  
     - **Monto abonado**  
   - Funciona como registro oficial de transacciones.  

---

### **3.Gestión de Tareas**

Permite administrar y dar seguimiento a tareas relacionadas con el condominio. La tabla principal muestra:

| **Campo**        | **Descripción**                                                    |
|------------------|--------------------------------------------------------------------|
| **ID**           | Identificador único de la tarea.                                    |
| **Descripción**   | Detalle de la tarea a realizar.                                    |
| **Depto.**        | Departamento encargado (Mantenimiento, Seguridad, Administración). |
| **Urgencia**      | Nivel de prioridad: Alta, Media o Baja.                             |
| **Fecha**         | Fecha programada para la tarea.                                     |
| **Costo**         | Monto estimado de la tarea.                                         |
| **Estado**        | “Pendiente” o “Completada”.                                         |
| **Prerreq.**      | ID(s) de tareas previas necesarias antes de ejecutarla.            |

**Acciones disponibles**  
1. **Agregar Tarea**  
   - Se ingresa descripción, departamento, urgencia, costo, fecha y prerrequisitos opcionales.  
   - El sistema valida la información y guarda la nueva tarea.  

2. **Marcar Tarea como Completada**  
   - Se selecciona una tarea y se marca como completada.  
   - El sistema cambia su estado y color a verde.  

3. **Buscar por Costo**  
   - Permite localizar tareas con un costo exacto.  
   - Muestra datos si existe, o informa si no fue encontrada.  

---

### **4.Reportes Financieros**

Permite generar, consultar y eliminar reportes financieros. La tabla principal muestra:

| **Campo**      | **Descripción**                       |
|----------------|----------------------------------------|
| **ID**         | Identificador del reporte.             |
| **Título**     | Nombre asignado al reporte.            |
| **Fecha**      | Día en que fue generado.               |
| **Ingresos**   | Total de dinero ingresado.             |
| **Egresos**    | Total de gastos registrados.           |
| **Balance**    | Diferencia entre ingresos y egresos.   |

**Acciones disponibles**  
1. **Generar Nuevo Reporte**  
   - Se solicita un título y el sistema genera el reporte automáticamente.  

2. **Eliminar Reporte**  
   - Se selecciona el reporte y se confirma su eliminación.  

---

### **5.Historial de Actividades**

Muestra un registro cronológico de todas las acciones realizadas en el sistema.  

| **Campo**         | **Descripción**                                                 |
|-------------------|-----------------------------------------------------------------|
| **Fecha y Hora**   | Momento exacto en que se realizó la acción.                     |
| **Descripción**    | Ejemplo: “Se registró un pago”, “Tarea completada”, etc.         |

**Características**  
- Se actualiza automáticamente al acceder a la sección.  
- No se puede modificar manualmente, garantizando integridad.  
- Alterna colores por fila para mejor lectura.  

---

### **6.Gestión de Residentes**

Permite administrar la información de los residentes del condominio.  

| **Campo**        | **Descripción**                                             |
|------------------|-------------------------------------------------------------|
| **ID**           | Identificador único del residente.                           |
| **Nombre**       | Nombre completo del residente.                               |
| **Departamento** | Unidad habitacional asignada.                                |
| **Teléfono**     | Número de contacto del residente.                            |
| **Deuda**        | “Al corriente” si no debe, o monto en rojo si existe deuda.  |

**Acciones disponibles**  
1. **Agregar Residente**  
   - Se registran datos como nombre, departamento, teléfono y deuda inicial.  

2. **Eliminar Residente**  
   - Se selecciona un residente y se confirma la eliminación.  

3. **Buscar por ID**  
   - Localiza información ingresando el ID.  

4. **Buscar por Nombre**  
   - Localiza información ingresando el nombre.  

5. **Enviar Avisos**  
   - Genera avisos de pago en formato HTML para residentes con deudas.  

**Notas adicionales**  
- La tabla alterna colores por fila para facilitar la lectura.  
- La columna de deuda se resalta en **rojo** si hay deuda y en **verde** si está al corriente.  
- Los botones tienen estilo personalizado y se alinean al pie de la tabla.

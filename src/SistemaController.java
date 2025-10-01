/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.text.NumberFormat;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Clase central que actúa como "cerebro" de la aplicación.
 * Gestiona todos los datos (residentes, tareas, etc.) y la lógica del proyecto,
 * sirviendo como intermediario entre la interfaz de usuario y los modelos de datos.
 */
public class SistemaController {
    private final Map<Integer, Residente> residentesPorId = new HashMap<>();
    private final ArbolBinarioBusqueda residentesPorNombre = new ArbolBinarioBusqueda();
    private final Map<Integer, Tarea> tareasPorId = new HashMap<>();
    private final GestorTareas gestorTareas = new GestorTareas();
    private final List<Reporte> reportes = new ArrayList<>();
    private final List<Pago> historialPagos = new ArrayList<>();
    private final Pila<Actividad> historialActividades = new Pila<>();
    
    /**
     * Constructor del controlador. Llama al método para poblar el sistema con datos iniciales.
     */
    public SistemaController() {
        cargarDatosIniciales();
    }

    /**
     * Devuelve una lista de todos los residentes registrados, ordenados por su ID.
     * @return Una lista (`List`) de objetos Residente.
     */
    public List<Residente> getResidentes() {
        return residentesPorId.values().stream()
                .sorted(Comparator.comparing(Residente::getId))
                .collect(Collectors.toList());
    }

    /**
     * Devuelve una lista filtrada que contiene únicamente a los residentes con deuda.
     * @return Una lista (`List`) de objetos Residente con saldo negativo.
     */
    public List<Residente> getResidentesConDeuda() {
        return residentesPorId.values().stream()
                .filter(Residente::tieneDeuda)
                .sorted(Comparator.comparing(Residente::getId))
                .collect(Collectors.toList());
    }

    /**
     * Crea un nuevo residente y lo añade a las estructuras de datos correspondientes.
     * @param nombre El nombre completo del residente.
     * @param depto El identificador de su departamento.
     * @param tel Su número de teléfono.
     * @param saldo El saldo inicial (se usa un valor negativo para representar una deuda).
     */
    public void agregarResidente(String nombre, String depto, String tel, double saldo) {
        Residente nuevo = new Residente(nombre, depto, tel, saldo);
        residentesPorId.put(nuevo.getId(), nuevo);
        residentesPorNombre.insertar(nuevo);
        historialActividades.push(new Actividad("Agregado nuevo residente: " + nombre));
    }

    /**
     * Busca un residente de forma eficiente utilizando su ID.
     * @param id El ID único del residente a buscar.
     * @return El objeto Residente si se encuentra, de lo contrario, null.
     */
    public Residente buscarResidentePorId(int id) {
        return residentesPorId.get(id);
    }

    /**
     * Busca un residente de forma eficiente utilizando su nombre.
     * @param nombre El nombre del residente a buscar.
     * @return El objeto Residente si se encuentra, de lo contrario, null.
     */
    public Residente buscarResidentePorNombre(String nombre) {
        return residentesPorNombre.buscar(nombre);
    }
    
    /**
     * Elimina un residente del sistema basado en su ID.
     * @param id El ID del residente a eliminar.
     */
    public void eliminarResidente(int id) {
        Residente r = residentesPorId.remove(id);
        if (r != null) {
            historialActividades.push(new Actividad("Eliminado residente: " + r.getNombre()));
        }
    }

    /**
     * Devuelve una lista de todas las tareas, ordenadas por prioridad (urgencia y fecha).
     * @return Una lista (`List`) de objetos Tarea.
     */
    public List<Tarea> getTareasOrdenadas() {
        return gestorTareas.obtenerTodasLasTareasOrdenadas();
    }
    
    /**
     * Crea una nueva tarea y la añade a las estructuras de datos.
     * @param desc La descripción textual de la tarea.
     * @param depto El departamento asignado.
     * @param urg La urgencia (ALTA, MEDIA, BAJA).
     * @param costo El costo estimado de la tarea.
     * @param fecha La fecha límite para su realización.
     * @param prerrequisitos Una lista de IDs de tareas que deben completarse antes.
     */
    public void agregarTarea(String desc, String depto, Urgencia urg, double costo, LocalDate fecha, List<Integer> prerrequisitos) {
        Tarea nueva = new Tarea(desc, depto, urg, costo, fecha);
        prerrequisitos.forEach(nueva::agregarPrerrequisito);
        gestorTareas.agregarTarea(nueva);
        tareasPorId.put(nueva.getId(), nueva);
        historialActividades.push(new Actividad("Agregada nueva tarea: " + desc));
    }

    /**
     * Marca una tarea como completada, validando primero que sus prerrequisitos estén cumplidos.
     * @param id El ID de la tarea a marcar.
     * @return Un mensaje de texto con el resultado de la operación.
     */
    public String marcarTareaComoCompletada(int id) {
        Tarea tarea = tareasPorId.get(id);
        if (tarea == null) return "Tarea no encontrada.";
        if (tarea.estaCompletada()) return "La tarea ya estaba completada.";

        for (int prereqId : tarea.getPrerrequisitos()) {
            Tarea prerrequisito = tareasPorId.get(prereqId);
            if (prerrequisito != null && !prerrequisito.estaCompletada()) {
                return "Error: Prerrequisito ID " + prereqId + " (" + prerrequisito.getDescripcion() + ") no completado.";
            }
        }
        tarea.completar();
        historialActividades.push(new Actividad("Completada tarea ID " + id + ": " + tarea.getDescripcion()));
        return "Tarea ID " + id + " completada con éxito.";
    }

    /**
     * Prepara una lista de tareas y llama al algoritmo de búsqueda binaria recursiva.
     * @param tareas La lista de tareas a ordenar y en la que buscar.
     * @param costoBuscado El costo exacto a encontrar.
     * @return La Tarea encontrada que coincide con el costo, o null.
     */
    public Tarea busquedaBinariaTareas(List<Tarea> tareas, double costoBuscado) {
        tareas.sort(Comparator.comparingDouble(Tarea::getCosto));
        return busquedaBinariaRecursiva(tareas, costoBuscado, 0, tareas.size() - 1);
    }

    /**
     * Implementación recursiva del algoritmo de Búsqueda Binaria ("Divide y Vencerás").
     * @param tareas Lista de tareas previamente ordenada por costo.
     * @param costoBuscado Costo a buscar.
     * @param inicio Índice inicial del segmento de búsqueda.
     * @param fin Índice final del segmento de búsqueda.
     * @return La Tarea encontrada, o null.
     */
    private Tarea busquedaBinariaRecursiva(List<Tarea> tareas, double costoBuscado, int inicio, int fin) {
        if (inicio > fin) return null;
        int medio = inicio + (fin - inicio) / 2;
        Tarea tareaDelMedio = tareas.get(medio);
        if (tareaDelMedio.getCosto() == costoBuscado) return tareaDelMedio;
        if (tareaDelMedio.getCosto() < costoBuscado) return busquedaBinariaRecursiva(tareas, costoBuscado, medio + 1, fin);
        return busquedaBinariaRecursiva(tareas, costoBuscado, inicio, medio - 1);
    }

    /**
     * Aplica una cuota general a todos los residentes, aumentando su deuda.
     * @param cuota El monto de la cuota a aplicar.
     * @return Un mensaje de confirmación de la operación.
     */
    public String aplicarCuotaGeneral(double cuota) {
        residentesPorId.values().forEach(r -> r.restarSaldo(cuota));
        historialActividades.push(new Actividad("Se aplicó cuota general de $" + cuota));
        return "Cuota de $" + cuota + " aplicada a " + residentesPorId.size() + " residentes.";
    }

    /**
     * Registra un pago para un residente, reduciendo su deuda y guardándolo en el historial.
     * @param residenteId El ID del residente que efectúa el pago.
     * @param monto La cantidad pagada.
     * @return Un mensaje de confirmación de la operación.
     */
    public String registrarPago(int residenteId, double monto) {
        Residente res = residentesPorId.get(residenteId);
        if (res == null) return "Error: Residente con ID " + residenteId + " no encontrado.";
        res.sumarSaldo(monto);
        historialPagos.add(new Pago(monto, res));
        historialActividades.push(new Actividad("Se registró pago de $" + monto + " para residente ID " + residenteId));
        return "Pago registrado con éxito para " + res.getNombre();
    }
    
    /**
     * Genera un reporte financiero basado en el historial de pagos y tareas completadas.
     * @param titulo El título que el usuario le asigna al reporte.
     * @return El objeto Reporte recién creado.
     */
    public Reporte generarReporteFinanciero(String titulo) {
        double totalIngresos = historialPagos.stream().mapToDouble(Pago::getMonto).sum();
        double totalEgresos = tareasPorId.values().stream().filter(Tarea::estaCompletada).mapToDouble(Tarea::getCosto).sum();
        Reporte nuevoReporte = new Reporte(titulo, totalIngresos, totalEgresos);
        reportes.add(nuevoReporte);
        historialActividades.push(new Actividad("Se generó el reporte: " + titulo));
        return nuevoReporte;
    }

    /**
     * Crea un mensaje formateado en HTML con los avisos de pago para los deudores.
     * @return Un String con el contenido en formato HTML.
     */
    public String generarAvisosDePagoFormateados() {
        List<Residente> deudores = getResidentesConDeuda();
        if (deudores.isEmpty()) {
            return "<html><body><h2>Sin Deudas</h2><p>No hay residentes con deudas.</p></body></html>";
        }
        StringBuilder avisosHtml = new StringBuilder("<html><body><h2>Avisos de Pago</h2>");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (Residente res : deudores) {
            avisosHtml.append("<hr><p><b>Para:</b> ").append(res.getNombre()).append(" (Depto: ").append(res.getDepartamento()).append(")</p>")
                      .append("<p>Le recordamos que presenta una deuda de:</p>")
                      .append("<h3 style='color: #E57373;'>").append(currencyFormatter.format(Math.abs(res.getSaldo()))).append("</h3>");
        }
        avisosHtml.append("</body></html>");
        historialActividades.push(new Actividad("Se generaron avisos de pago."));
        return avisosHtml.toString();
    }
    
    /**
     * Devuelve la lista de todos los reportes generados.
     * @return Una lista (`List`) de objetos Reporte.
     */
    public List<Reporte> getReportes() { return reportes; }

    /**
     * Elimina un reporte del sistema basado en su ID.
     * @param id El ID del reporte a eliminar.
     */
    public void eliminarReporte(int id) {
        reportes.removeIf(r -> {
            if (r.id == id) {
                historialActividades.push(new Actividad("Eliminado reporte: " + r.titulo));
                return true;
            }
            return false;
        });
    }

    /**
     * Cambia el estado de un reporte a ACTIVO o CANCELADO.
     * @param id El ID del reporte a modificar.
     * @param nuevoStatus El nuevo estado para el reporte.
     */
    public void cambiarEstadoReporte(int id, ReporteStatus nuevoStatus) {
        for (Reporte r : reportes) {
            if (r.id == id) {
                r.setStatus(nuevoStatus);
                historialActividades.push(new Actividad("Cambiado estado de reporte ID " + id + " a " + nuevoStatus));
                break;
            }
        }
    }

    /**
     * Devuelve la lista de todas las transacciones de pago.
     * @return Una lista (`List`) de objetos Pago.
     */
    public List<Pago> getHistorialPagos() { return historialPagos; }
    
    /**
     * Devuelve la lista de todas las actividades del sistema registradas en la pila.
     * @return Una lista (`List`) de objetos Actividad.
     */
    public List<Actividad> getHistorialActividades() { return historialActividades.obtenerTodos(); }
    
    /**
     * Método privado que se ejecuta al iniciar el controlador para poblar el sistema
     * con un conjunto de datos de ejemplo para demostración.
     */
    private void cargarDatosIniciales() {
        Random rand = new Random();
        String[] nombres = {"Ana", "Luis", "Carla", "David", "Elena", "Fernando", "Gloria", "Hugo", "Irene", "Jorge"};
        String[] apellidos = {"Torres", "Morales", "Soto", "Reyes", "Garza", "Cruz", "Pena", "Jimenez", "Castillo", "Luna"};
        for (int i = 0; i < 50; i++) {
            String n = nombres[rand.nextInt(nombres.length)] + " " + apellidos[rand.nextInt(apellidos.length)];
            double saldoInicial = (i % 2 == 0) ? -800.0 : (rand.nextInt(5) == 0 ? -1250.0 : 0.0);
            agregarResidente(n, (char)('A' + rand.nextInt(3)) + "-" + (101 + i), "81" + (10000000 + rand.nextInt(90000000)), saldoInicial);
        }
        
        String[] dM = {"Reparar fuga", "Pintar lobby", "Limpiar alberca", "Revisar riego", "Cambiar foco"};
        String[] dS = {"Vigilar entrada", "Monitorear camaras", "Reportar vehiculo", "Controlar acceso", "Revisar bitacora"};
        String[] dA = {"Elaborar reporte", "Contactar proveedor", "Archivar facturas", "Programar junta", "Pagar servicios"};
        
        for (int i = 0; i < 15; i++) {
            agregarTarea(dM[rand.nextInt(dM.length)], "Mantenimiento", Urgencia.values()[rand.nextInt(3)], 1000 + rand.nextInt(4001), LocalDate.now().plusDays(rand.nextInt(30)), Collections.emptyList());
            agregarTarea(dS[rand.nextInt(dS.length)], "Seguridad", Urgencia.values()[rand.nextInt(3)], 500 + rand.nextInt(1001), LocalDate.now().plusDays(rand.nextInt(30)), Collections.emptyList());
            agregarTarea(dA[rand.nextInt(dA.length)], "Administracion", Urgencia.values()[rand.nextInt(3)], 100 + rand.nextInt(401), LocalDate.now().plusDays(rand.nextInt(30)), Collections.emptyList());
        }

        if (tareasPorId.containsKey(5) && tareasPorId.containsKey(10)) tareasPorId.get(10).agregarPrerrequisito(5);
        if (tareasPorId.containsKey(16) && tareasPorId.containsKey(20)) tareasPorId.get(20).agregarPrerrequisito(16);
        
        if(residentesPorId.containsKey(1)) registrarPago(1, 800);
        if(residentesPorId.containsKey(2)) registrarPago(2, 800);
        if(residentesPorId.containsKey(5)) registrarPago(5, 1200);

        reportes.add(new Reporte("Reporte Inicial", 45000, 32000));
        historialActividades.push(new Actividad("Sistema iniciado y datos de demostración cargados."));
    }
}
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

public class SistemaController {
    private final Map<Integer, Residente> residentesPorId = new HashMap<>();
    private final ArbolBinarioBusqueda residentesPorNombre = new ArbolBinarioBusqueda();
    private final Map<Integer, Tarea> tareasPorId = new HashMap<>();
    private final GestorTareas gestorTareas = new GestorTareas();
    private final List<Reporte> reportes = new ArrayList<>();
    private final List<Pago> historialPagos = new ArrayList<>();
    private final Pila<Actividad> historialActividades = new Pila<>();
    
    public SistemaController() {
        cargarDatosIniciales();
    }

    // --- Métodos de Residentes ---
    public List<Residente> getResidentes() {
        return residentesPorId.values().stream()
                .sorted(Comparator.comparing(Residente::getId))
                .collect(Collectors.toList());
    }

    public List<Residente> getResidentesConDeuda() {
        return residentesPorId.values().stream()
                .filter(Residente::tieneDeuda)
                .sorted(Comparator.comparing(Residente::getId))
                .collect(Collectors.toList());
    }

    public void agregarResidente(String nombre, String depto, String tel, double saldo) {
        Residente nuevo = new Residente(nombre, depto, tel, saldo);
        residentesPorId.put(nuevo.getId(), nuevo);
        residentesPorNombre.insertar(nuevo);
        historialActividades.push(new Actividad("Agregado nuevo residente: " + nombre));
    }

    public Residente buscarResidentePorId(int id) {
        return residentesPorId.get(id);
    }

    public Residente buscarResidentePorNombre(String nombre) {
        return residentesPorNombre.buscar(nombre);
    }
    
    public void eliminarResidente(int id) {
        Residente r = residentesPorId.remove(id);
        if (r != null) {
            historialActividades.push(new Actividad("Eliminado residente: " + r.getNombre()));
        }
    }

    // --- Métodos de Tareas ---
    public List<Tarea> getTareasOrdenadas() {
        return gestorTareas.obtenerTodasLasTareasOrdenadas();
    }
    
    public void agregarTarea(String desc, String depto, Urgencia urg, double costo, LocalDate fecha, List<Integer> prerrequisitos) {
        Tarea nueva = new Tarea(desc, depto, urg, costo, fecha);
        prerrequisitos.forEach(nueva::agregarPrerrequisito);
        gestorTareas.agregarTarea(nueva);
        tareasPorId.put(nueva.getId(), nueva);
        historialActividades.push(new Actividad("Agregada nueva tarea: " + desc));
    }

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

    public Tarea busquedaBinariaTareas(List<Tarea> tareas, double costoBuscado) {
        tareas.sort(Comparator.comparingDouble(Tarea::getCosto));
        return busquedaBinariaRecursiva(tareas, costoBuscado, 0, tareas.size() - 1);
    }

    private Tarea busquedaBinariaRecursiva(List<Tarea> tareas, double costoBuscado, int inicio, int fin) {
        if (inicio > fin) return null;
        int medio = inicio + (fin - inicio) / 2;
        Tarea tareaDelMedio = tareas.get(medio);
        if (tareaDelMedio.getCosto() == costoBuscado) return tareaDelMedio;
        if (tareaDelMedio.getCosto() < costoBuscado) return busquedaBinariaRecursiva(tareas, costoBuscado, medio + 1, fin);
        return busquedaBinariaRecursiva(tareas, costoBuscado, inicio, medio - 1);
    }

    // --- Métodos Financieros y de Reportes ---
    public String aplicarCuotaGeneral(double cuota) {
        residentesPorId.values().forEach(r -> r.restarSaldo(cuota));
        historialActividades.push(new Actividad("Se aplicó cuota general de $" + cuota));
        return "Cuota de $" + cuota + " aplicada a " + residentesPorId.size() + " residentes.";
    }

    public String registrarPago(int residenteId, double monto) {
        Residente res = residentesPorId.get(residenteId);
        if (res == null) return "Error: Residente con ID " + residenteId + " no encontrado.";
        res.sumarSaldo(monto);
        historialPagos.add(new Pago(monto, res));
        historialActividades.push(new Actividad("Se registró pago de $" + monto + " para residente ID " + residenteId));
        return "Pago registrado con éxito para " + res.getNombre();
    }
    
    public Reporte generarReporteFinanciero(String titulo) {
        double totalIngresos = historialPagos.stream().mapToDouble(Pago::getMonto).sum();
        double totalEgresos = tareasPorId.values().stream().filter(Tarea::estaCompletada).mapToDouble(Tarea::getCosto).sum();
        Reporte nuevoReporte = new Reporte(titulo, totalIngresos, totalEgresos);
        reportes.add(nuevoReporte);
        historialActividades.push(new Actividad("Se generó el reporte: " + titulo));
        return nuevoReporte;
    }

    public String generarAvisosDePagoFormateados() {
        List<Residente> deudores = getResidentesConDeuda();
        if (deudores.isEmpty()) {
            return "<html><body style='width: 300px; font-family: Segoe UI, sans-serif;'>"
                 + "<h2>Sin Deudas Pendientes</h2><p>¡Excelente trabajo! No hay residentes con deudas actualmente.</p>"
                 + "</body></html>";
        }
        StringBuilder avisosHtml = new StringBuilder("<html><body style='width: 350px; font-family: Segoe UI, sans-serif;'>");
        avisosHtml.append("<h2>Avisos de Pago Generados</h2>");
        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        for (Residente res : deudores) {
            avisosHtml.append("<hr>")
                      .append("<p><b>Para:</b> ").append(res.getNombre()).append(" (Depto: ").append(res.getDepartamento()).append(")</p>")
                      .append("<p>Estimado/a residente, le recordamos que presenta una deuda por un monto de:</p>")
                      .append("<h3 style='text-align: center; color: #E57373;'>").append(currencyFormatter.format(Math.abs(res.getSaldo()))).append("</h3>")
                      .append("<p style='font-size: 9px; color: gray;'>Favor de realizar su pago a la brevedad para evitar inconvenientes.</p>");
        }
        avisosHtml.append("</body></html>");
        historialActividades.push(new Actividad("Se generaron avisos de pago."));
        return avisosHtml.toString();
    }
    
    public List<Reporte> getReportes() { return reportes; }

    public void eliminarReporte(int id) {
        reportes.removeIf(r -> {
            if (r.id == id) {
                historialActividades.push(new Actividad("Eliminado reporte: " + r.titulo));
                return true;
            }
            return false;
        });
    }

    public void cambiarEstadoReporte(int id, ReporteStatus nuevoStatus) {
        for (Reporte r : reportes) {
            if (r.id == id) {
                r.setStatus(nuevoStatus);
                historialActividades.push(new Actividad("Cambiado estado de reporte ID " + id + " a " + nuevoStatus));
                break;
            }
        }
    }

    public List<Pago> getHistorialPagos() { return historialPagos; }
    
    public List<Actividad> getHistorialActividades() { return historialActividades.obtenerTodos(); }
    
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
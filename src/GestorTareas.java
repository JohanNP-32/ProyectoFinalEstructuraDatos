/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.PriorityQueue;

// Gestiona la cola de prioridad de las tareas.
public class GestorTareas {
    
    // Cola que ordena tareas automáticamente por urgencia y fecha.
    private PriorityQueue<Tarea> tareasPrioritarias = new PriorityQueue<>();

    // Agrega una nueva tarea a la cola.
    public void agregarTarea(Tarea tarea) {
        tareasPrioritarias.add(tarea);
    }

    // Devuelve una lista ordenada de todas las tareas.
    public List<Tarea> obtenerTodasLasTareasOrdenadas() {
        // Se crea una copia y se ordena para una visualización correcta.
        List<Tarea> listaOrdenada = new ArrayList<>(tareasPrioritarias);
        Collections.sort(listaOrdenada);
        return listaOrdenada;
    }
}
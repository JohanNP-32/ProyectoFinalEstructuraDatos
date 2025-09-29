/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.util.*;
public class GestorTareas {
    private PriorityQueue<Tarea> tareasPrioritarias = new PriorityQueue<>();
    public void agregarTarea(Tarea tarea) { tareasPrioritarias.add(tarea); }
    public List<Tarea> obtenerTodasLasTareasOrdenadas() {
        List<Tarea> listaOrdenada = new ArrayList<>(tareasPrioritarias);
        Collections.sort(listaOrdenada);
        return listaOrdenada;
    }
}
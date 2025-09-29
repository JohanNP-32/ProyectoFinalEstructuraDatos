/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
/**
 * Representa un nodo genérico para ser utilizado en estructuras de datos enlazadas.
 * Contiene un dato y referencias a los nodos siguiente y previo.
 * @param <T> El tipo de dato que almacenará el nodo.
 */
public class Nodo<T> {
    public T data;
    public Nodo<T> next;
    public Nodo<T> prev; 

    public Nodo(T data) {
        this.data = data;
        this.next = null;
        this.prev = null;
    }
}

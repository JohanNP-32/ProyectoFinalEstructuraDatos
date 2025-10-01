/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
/**
 * Implementación de una Cola (Queue) genérica basada en una ListaEnlazada.
 * Sigue el principio FIFO (First-In, First-Out).
 * @param <T> El tipo de dato que almacenará la cola.
 */
public class Cola<T> {

    private ListaEnlazada<T> list;

    /**
     * Constructor que inicializa una nueva cola vacía.
     */
    public Cola() {
        list = new ListaEnlazada<>();
    }

    /**
     * Agrega un nuevo elemento al final de la cola (en-cola).
     *
     * @param data El elemento que se va a añadir a la cola.
     */
    public void enqueue(T data) {
        list.add(data);
    }

    /**
     * Elimina y devuelve el elemento que se encuentra al frente de la cola (des-encola).
     * Sigue el principio FIFO (el primero que entró es el primero que sale).
     *
     * @return El elemento que estaba al frente de la cola, o null si la cola está vacía.
     */
    public T dequeue() {
        if (isEmpty()) {
            return null;
        }
        T data = list.head.data;
        list.removeAt(0);
        return data;
    }

    /**
     * Devuelve el elemento que se encuentra al frente de la cola sin eliminarlo.
     *
     * @return El elemento al frente de la cola, o null si está vacía.
     */
    public T peek() {
        if (isEmpty()) {
            return null;
        }
        return list.head.data;
    }

    /**
     * Verifica si la cola no contiene elementos.
     *
     * @return `true` si la cola está vacía, `false` en caso contrario.
     */
    public boolean isEmpty() {
        return list.head == null;
    }

    /**
     * Muestra el contenido de la cola en la consola.
     * Este método es principalmente para propósitos de depuración.
     */
    public void display() {
        list.display();
    }

    /**
     * Obtiene el nodo principal (head) de la lista enlazada subyacente.
     *
     * @return El primer nodo de la cola.
     */
    public Nodo<T> getHead() {
        return list.head;
    }
}
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

    public Cola() {
        list = new ListaEnlazada<>();
    }

    public void enqueue(T data) {
        list.add(data);
    }

    public T dequeue() {
        if (isEmpty()) return null;
        T data = list.head.data;
        list.removeAt(0);
        return data;
    }

    public T peek() {
        if (isEmpty()) return null;
        return list.head.data;
    }

    public boolean isEmpty() {
        return list.head == null;
    }

    public void display() {
        list.display();
    }

    public Nodo<T> getHead() {
        return list.head;
    }
}

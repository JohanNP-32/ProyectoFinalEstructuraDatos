/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
import java.util.ArrayList;
import java.util.List;

// Implementación de una Pila (Stack) genérica, que sigue el principio LIFO (Last-In, First-Out).
public class Pila<T> {
    private ListaEnlazada<T> list = new ListaEnlazada<>();

    /**
     * Agrega un nuevo elemento en la cima de la pila.
     * @param data El elemento a insertar.
     */
    public void push(T data) {
        list.insertAt(0, data);
    }

    /**
     * Devuelve una lista con todos los elementos de la pila, desde la cima hasta el fondo.
     * @return Una lista con todos los elementos.
     */
    public List<T> obtenerTodos() {
        List<T> todos = new ArrayList<>();
        Nodo<T> actual = list.head;
        while (actual != null) {
            todos.add(actual.data);
            actual = actual.next;
        }
        return todos;
    }
}
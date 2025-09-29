/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
/**
 * Implementación de una lista enlazada simple genérica.
 * Soporta operaciones básicas como agregar, insertar, eliminar y buscar elementos.
 * @param <T> El tipo de dato que almacenará la lista.
 */
public class ListaEnlazada<T> {
    public Nodo<T> head;
    private int size;

    public ListaEnlazada() {
        this.head = null;
        this.size = 0;
    }

    public int size() {
        return size;
    }

    // Agrega un elemento al final de la lista
    public void add(T data) {
        Nodo<T> nuevoNodo = new Nodo<>(data);
        if (head == null) {
            head = nuevoNodo;
        } else {
            Nodo<T> current = head;
            while (current.next != null) {
                current = current.next;
            }
            current.next = nuevoNodo;
        }
        size++;
    }

    // Inserta un elemento en un índice específico
    public void insertAt(int index, T data) {
        if (index < 0 || index > size) {
            System.out.println("Índice fuera de rango.");
            return;
        }
        Nodo<T> nuevoNodo = new Nodo<>(data);
        if (index == 0) {
            nuevoNodo.next = head;
            head = nuevoNodo;
        } else {
            Nodo<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            nuevoNodo.next = current.next;
            current.next = nuevoNodo;
        }
        size++;
    }

    // Elimina un elemento en un índice específico
    public void removeAt(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Índice fuera de rango.");
            return;
        }
        if (index == 0) {
            head = head.next;
        } else {
            Nodo<T> current = head;
            for (int i = 0; i < index - 1; i++) {
                current = current.next;
            }
            current.next = current.next.next;
        }
        size--;
    }

    // Obtiene el elemento en un índice específico
    public T get(int index) {
        if (index < 0 || index >= size) {
            System.out.println("Índice fuera de rango.");
            return null;
        }
        Nodo<T> current = head;
        for (int i = 0; i < index; i++) {
            current = current.next;
        }
        return current.data;
    }
    
    // Muestra todos los elementos de la lista
    public void display() {
        if (head == null) {
            System.out.println("La lista está vacía.");
            return;
        }
        Nodo<T> current = head;
        while (current != null) {
            System.out.println("-> " + current.data.toString());
            current = current.next;
        }
    }
}


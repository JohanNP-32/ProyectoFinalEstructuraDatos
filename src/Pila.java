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
public class Pila<T> {
    private ListaEnlazada<T> list = new ListaEnlazada<>();
    public void push(T data) { list.insertAt(0, data); }
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
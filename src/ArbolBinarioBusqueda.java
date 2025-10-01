/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */

/**
 * Representa un nodo individual dentro del ArbolBinarioBusqueda.
 * Contiene el dato (un Residente) y las referencias a sus nodos hijos.
 */
class NodoArbol {
    Residente residente;
    NodoArbol izquierdo, derecho;

    /**
     * Constructor para un nuevo nodo del árbol.
     * @param item El objeto Residente que se almacenará en este nodo.
     */
    public NodoArbol(Residente item) {
        residente = item;
        izquierdo = derecho = null;
    }
}

/**
 * Implementación de un Árbol Binario de Búsqueda para organizar y buscar objetos Residente
 * de manera eficiente por su nombre.
 */
public class ArbolBinarioBusqueda {
    NodoArbol raiz;

    /**
     * Constructor que inicializa un árbol vacío.
     */
    public ArbolBinarioBusqueda() {
        raiz = null;
    }

    /**
     * Método público para iniciar la inserción de un nuevo residente en el árbol.
     * @param r El objeto Residente a insertar.
     */
    public void insertar(Residente r) {
        raiz = insertarRec(raiz, r);
    }

    /**
     * Método recursivo privado que navega por el árbol para encontrar la posición correcta
     * e insertar el nuevo nodo.
     * @param n El nodo actual que se está evaluando.
     * @param r El nuevo Residente a insertar.
     * @return El nodo raíz del subárbol modificado.
     */
    private NodoArbol insertarRec(NodoArbol n, Residente r) {
        // Caso base: si el nodo actual es nulo, hemos encontrado el lugar para insertar.
        if (n == null) {
            return new NodoArbol(r);
        }

        // Paso recursivo: comparamos por nombre para decidir si ir a la izquierda o a la derecha.
        if (r.getNombre().compareTo(n.residente.getNombre()) < 0) {
            n.izquierdo = insertarRec(n.izquierdo, r);
        } else if (r.getNombre().compareTo(n.residente.getNombre()) > 0) {
            n.derecho = insertarRec(n.derecho, r);
        }

        // Devuelve el nodo sin cambios si el nombre ya existe.
        return n;
    }

    /**
     * Método público para buscar un residente por su nombre.
     * La búsqueda no distingue entre mayúsculas y minúsculas.
     * @param nombre El nombre del residente a buscar.
     * @return El objeto Residente si se encuentra, o null si no.
     */
    public Residente buscar(String nombre) {
        NodoArbol resultado = buscarRec(raiz, nombre);
        return (resultado != null) ? resultado.residente : null;
    }

    /**
     * Método recursivo privado que busca un residente por nombre.
     * @param n El nodo actual en la búsqueda.
     * @param nombre El nombre a buscar.
     * @return El nodo que contiene al residente, o null si no se encuentra.
     */
    private NodoArbol buscarRec(NodoArbol n, String nombre) {
        // Caso base: el nodo es nulo (no se encontró) o el nombre coincide.
        if (n == null || n.residente.getNombre().equalsIgnoreCase(nombre)) {
            return n;
        }

        // Paso recursivo: decidimos si buscar en el subárbol izquierdo o derecho.
        if (nombre.compareTo(n.residente.getNombre()) < 0) {
            return buscarRec(n.izquierdo, nombre);
        } else {
            return buscarRec(n.derecho, nombre);
        }
    }
}
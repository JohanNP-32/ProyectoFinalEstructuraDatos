/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

/**
 *
 * @author johannunezpulido
 */
class NodoArbol {
    Residente residente; NodoArbol izquierdo, derecho;
    public NodoArbol(Residente item) { residente = item; izquierdo = derecho = null; }
}
public class ArbolBinarioBusqueda {
    NodoArbol raiz;
    public ArbolBinarioBusqueda() { raiz = null; }
    public void insertar(Residente r) { raiz = insertarRec(raiz, r); }
    private NodoArbol insertarRec(NodoArbol n, Residente r) {
        if (n == null) return new NodoArbol(r);
        if (r.getNombre().compareTo(n.residente.getNombre()) < 0) n.izquierdo = insertarRec(n.izquierdo, r);
        else if (r.getNombre().compareTo(n.residente.getNombre()) > 0) n.derecho = insertarRec(n.derecho, r);
        return n;
    }
    public Residente buscar(String nombre) {
        NodoArbol res = buscarRec(raiz, nombre);
        return (res != null) ? res.residente : null;
    }
    private NodoArbol buscarRec(NodoArbol n, String nombre) {
        if (n == null || n.residente.getNombre().equalsIgnoreCase(nombre)) return n;
        if (nombre.compareTo(n.residente.getNombre()) < 0) return buscarRec(n.izquierdo, nombre);
        return buscarRec(n.derecho, nombre);
    }
}
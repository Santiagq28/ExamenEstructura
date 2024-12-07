
package estructura.examenestructura;
import java.util.ArrayList;
import java.util.List;
import java.util.LinkedList;
import javax.swing.JOptionPane;
public class MinHeap {
    private Nodo raiz;
    private int size;

    public MinHeap() {
        this.raiz = null;
        this.size = 0;
    }

    public void insertar(Personas persona,int edad) {
        Nodo nuevoNodo = new Nodo(persona,edad);
        if (raiz == null) {
            raiz = nuevoNodo;
        } else {
            insertarAlFinal(nuevoNodo);
            heapifyUp(nuevoNodo);
        }
        size++;
    }

    private void insertarAlFinal(Nodo nuevoNodo) {
        if (raiz == null) {
            raiz = nuevoNodo;
            return;
        }

        List<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        Nodo nodoActual = null;
        while (!cola.isEmpty()) {
            nodoActual = cola.remove(0);

            if (nodoActual.izquierdo == null) {
                nodoActual.izquierdo = nuevoNodo;
                nuevoNodo.padre = nodoActual;
                break;
            } else {
                cola.add(nodoActual.izquierdo);
            }

            if (nodoActual.derecho == null) {
                nodoActual.derecho = nuevoNodo;
                nuevoNodo.padre = nodoActual;
                break;
            } else {
                cola.add(nodoActual.derecho);
            }
        }
    }

    private void heapifyUp(Nodo nodo) {
        while (nodo.padre != null && nodo.padre.edad > nodo.edad) {
            intercambiarNodos(nodo, nodo.padre);
            nodo = nodo.padre;
        }
    }

    public String extraerMin() {
        if (raiz == null) {
            throw new IllegalStateException("El montículo está vacío");
        }
        String valorMinimo = raiz.persona.getNombre()+"("+ raiz.edad+")";

        Nodo ultimoNodo = obtenerUltimoNodo();
        intercambiarNodos(raiz,ultimoNodo);
        eliminarUltimoNodo();

        heapifyDown(raiz);
        size--;
        
        return valorMinimo;
    }

    private Nodo obtenerUltimoNodo() {
        if (raiz == null) {
            return null;
        }

        List<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        Nodo nodoActual = null;

        while (!cola.isEmpty()) {
            nodoActual = cola.remove(0);
            if (nodoActual.izquierdo != null) {
                cola.add(nodoActual.izquierdo);
            }
            if (nodoActual.derecho != null) {
                cola.add(nodoActual.derecho);
            }
        }
        return nodoActual;
    }

    private void eliminarUltimoNodo() {
        if (raiz == null) {
            return;
        }

        List<Nodo> cola = new LinkedList<>();
        cola.add(raiz);
        Nodo nodoActual = null;
        Nodo padreDelUltimo = null;

        while (!cola.isEmpty()) {
            nodoActual = cola.remove(0);

            if (nodoActual.izquierdo != null) {
                cola.add(nodoActual.izquierdo);
                padreDelUltimo = nodoActual;
            }
            if (nodoActual.derecho != null) {
                cola.add(nodoActual.derecho);
                padreDelUltimo = nodoActual;
            }
        }
        if (padreDelUltimo != null) {
            if (padreDelUltimo.derecho != null) {
                padreDelUltimo.derecho = null;
            } else if (padreDelUltimo.izquierdo != null) {
                padreDelUltimo.izquierdo = null;
            }
        } else {
            raiz = null;
        }
    }

    private void heapifyDown(Nodo nodo) {
        if (nodo == null) {
            return;
        }

        Nodo hijoIzquierdo = nodo.izquierdo;
        Nodo hijoDerecho = nodo.derecho;
        
        Nodo menorHijo = nodo;
        if (hijoIzquierdo != null && hijoIzquierdo.edad < menorHijo.edad) {
            menorHijo = hijoIzquierdo;
        }
        if (hijoDerecho != null && hijoDerecho.edad < menorHijo.edad) {
            menorHijo = hijoDerecho;
        }

        if (menorHijo != nodo) {
            intercambiarNodos(nodo, menorHijo);
            heapifyDown(menorHijo);
        }
    }
    
    private void intercambiarNodos(Nodo nodo1, Nodo nodo2) {
        int tempValor = nodo1.edad;
        nodo1.edad = nodo2.edad;
        nodo2.edad = tempValor;

        Personas tempNombre = nodo1.persona;
        nodo1.persona = nodo2.persona;
        nodo2.persona = tempNombre;
    }


    public int obtenerMin() {
        if (raiz != null) {
            return raiz.edad;
        }else{
            JOptionPane.showMessageDialog(null,"ERROR, montículo vacío");
            return -1;
        }
    }

    public boolean estaVacio() {
        return raiz == null;
    }

    public String obtenerHeapComoTexto() {
        ArrayList<String> resultado = new ArrayList<>();
        generarTextoNodo(raiz, "", true, resultado);

        String textoFinal = "";
        for (String linea : resultado) {
            textoFinal += linea + "\n";
        }
        return textoFinal;
    }

    private void generarTextoNodo(Nodo nodo, String prefix, boolean isLeft, ArrayList<String> resultado) {
        if (nodo != null) {
            // Agregar la línea actual al resultado
            resultado.add(prefix + (isLeft ? "├── " : "└── ") +nodo.persona.getNombre()+"("+ nodo.edad+")");

            // Generar texto para los nodos izquierdo y derecho
            generarTextoNodo(nodo.izquierdo, prefix + (isLeft ? "│   " : "    "), true, resultado);
            generarTextoNodo(nodo.derecho, prefix + (isLeft ? "│   " : "    "), false, resultado);
        }
    }
    
    public void encontrarPersona(String id){
        Nodo perso = buscarNodoPorId(id);
        if(perso == null){
            JOptionPane.showMessageDialog(null, "La persona no ha comprado boletas");
        }else{
            JOptionPane.showMessageDialog(null,"Nombre: "+perso.persona.getNombre()+ " Boletas compradas: "+perso.persona.getBoletas());
        }
    }
    
    
    
    public Nodo buscarNodoPorId(String id) {
        if (raiz == null) {
            return null;
        }

        List<Nodo> cola = new LinkedList<>();
        cola.add(raiz);

        while (!cola.isEmpty()) {
            Nodo nodoActual = cola.remove(0);

            if (nodoActual.persona.getDocumento().equals(id)) {
                
                return nodoActual; 
            }
            if (nodoActual.izquierdo != null) {
                cola.add(nodoActual.izquierdo);
            }
            if (nodoActual.derecho != null) {
                cola.add(nodoActual.derecho);
            }
        }

        return null; 
    }
}

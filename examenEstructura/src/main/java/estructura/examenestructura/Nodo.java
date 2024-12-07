
package estructura.examenestructura;

public class Nodo {
    Personas persona;
    int edad;
    Nodo izquierdo, derecho, padre;

    public Nodo(Personas persona,int edad) {
        this.persona = persona;
        this.edad = edad;
        this.izquierdo = null;
        this.derecho = null;
        this.padre = null;
    }
}

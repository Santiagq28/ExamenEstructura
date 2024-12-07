
package estructura.examenestructura;

public class Pelicula {

    public Pelicula(String nombre, boolean paraAdultos) {
        this.nombre = nombre;
        this.cantidad = 500;
        this.paraAdultos = paraAdultos;
    }

    public String getNombre() {
        return nombre;
    }
    public int getCantidad() {
        return cantidad;
    }
    public void setCantidad(int cantidad) {
        this.cantidad = cantidad;
    }
    public boolean isParaAdultos() {
        return paraAdultos;
    }
    private String nombre;
    private int cantidad;
    private boolean paraAdultos;
}

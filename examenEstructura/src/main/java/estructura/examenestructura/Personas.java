package estructura.examenestructura;

public class Personas {

    public Personas(String nombre, String documento, String nacimiento, int boletas, Pelicula pelicula) {
        this.nombre = nombre;
        this.documento = documento;
        this.nacimiento = nacimiento;
        this.boletas = boletas;
        this.pelicula = pelicula;
    }

    
    public String getNombre() {
        return nombre;
    }
    public String getDocumento() {
        return documento;
    }
    public String getNacimiento() {
        return nacimiento;
    }
    public int getBoletas() {
        return boletas;
    }
    public void setBoletas(int boletas) {
        this.boletas = boletas;
    }
    public Pelicula getPelicula(){
        return pelicula;
    }
    
    private String nombre;
    private String documento;
    private String nacimiento;
    private int boletas;
    private Pelicula pelicula;
}

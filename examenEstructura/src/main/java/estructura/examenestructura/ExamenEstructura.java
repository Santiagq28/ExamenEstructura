
package estructura.examenestructura;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.ArrayList;
import javax.swing.JOptionPane;

/**
 *
 * @author USER
 */
public class ExamenEstructura {
    
    ArrayList<Pelicula> peliculas = new ArrayList<>();
    MinHeap mp = new MinHeap();
    int[] mayor_menor = new int[2];
    boolean[] enabled = {false,false};
    
    public void solicitarPelicula(){
        String nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre de la película:");
        if(nombre.equals(" ")||nombre.equals("")){
            JOptionPane.showMessageDialog(null, "No ingresó ningún nombre");
            return;
        }
        String paraAdultos = JOptionPane.showInputDialog(null, "¿La película "+nombre+" es para adultos? (1)Sí (2)No");
        
        if(paraAdultos.equals("1")){
            Pelicula pelicula = new Pelicula(nombre,true);
            peliculas.add(pelicula);
            JOptionPane.showMessageDialog(null, "Película agregada con éxito a la cartelera");
            enabled[0]=true;
        }else if(paraAdultos.equals("2")){
            Pelicula pelicula = new Pelicula(nombre,false);
            peliculas.add(pelicula);
            JOptionPane.showMessageDialog(null, "Película agregada con éxito a la cartelera");
            enabled[0]=true;
        }else{
            JOptionPane.showMessageDialog(null,"No ingresó ninguna opción válida");
        }
    }
    
    public void venderBoleta(){
        String nombre = JOptionPane.showInputDialog(null,"Ingrese el nombre de la persona");
        String documento = JOptionPane.showInputDialog(null,"Ingrese el documento de la persona");
        String fecha = JOptionPane.showInputDialog(null,"Ingrese la fecha de nacimiento de la persona (dd-MM-yyyy)");
        
        if(nombre.equals(" ")||documento.equals(" ")||fecha.equals(" ")||nombre.equals("")||documento.equals("")||fecha.equals("")){
            JOptionPane.showMessageDialog(null,"No ingresó alguno de los campos.");
            return;
        }
        if(mp.buscarNodoPorId(documento)!=null){
            JOptionPane.showMessageDialog(null,"La persona solo puede comprar boletas para una película");
            return;
        }
        
        if(calcularEdad(fecha)==-1){
            return;
        }
        
        if(verificarString(documento)==false){
            JOptionPane.showMessageDialog(
                null,
                "Id con menos de 8 dígitos o con caracteres diferentes a números.",
                "Advertencia",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        if(verificarString2(nombre)==false){
            JOptionPane.showMessageDialog(
                null,
                "Nombre no válido por contener números.",
                "Advertencia",
                JOptionPane.ERROR_MESSAGE
            );
            return;
        }
        
        String cartelera=mostrarPeliculas();
        int opciones = Integer.parseInt(JOptionPane.showInputDialog(null,cartelera+"\n"+"Escoja la película ingresando su número"));
        opciones-=1;
        if(peliculas.get(opciones).isParaAdultos()){
            if(calcularEdad(fecha)>=14){
                int boletas = Integer.parseInt(JOptionPane.showInputDialog(null,peliculas.get(opciones).getNombre()+" ¿Cuántas boletas desea comprar? (Max 5)"));
                if(boletas<=5 && boletas >0){
                    if(peliculas.get(opciones).getCantidad()>=boletas){
                        Personas persona = new Personas(nombre,documento,fecha,boletas,peliculas.get(opciones));
                        peliculas.get(opciones).setCantidad(peliculas.get(opciones).getCantidad()-boletas);
                        mp.insertar(persona, calcularEdad(fecha));
                        menoresYmayores(calcularEdad(fecha));
                        enabled[1]=true;
                        JOptionPane.showMessageDialog(null, "Boletas vendidas con éxito");
                        
                    }else{
                        JOptionPane.showMessageDialog(null,"No hay suficientes boletas");
                    }
                }else{
                    JOptionPane.showMessageDialog(null,"Ingresó un valor incorrecto");
                }
                
            }else{
                JOptionPane.showMessageDialog(null,"No cuenta con la edad mínima para ver la película");
            }
        }else{
            int boletas = Integer.parseInt(JOptionPane.showInputDialog(null,peliculas.get(opciones).getNombre()+" ¿Cuántas boletas desea comprar? (Max 5)"));
            if(boletas<=5 && boletas >0){
                if(peliculas.get(opciones).getCantidad()>=boletas){
                    Personas persona = new Personas(nombre,documento,fecha,boletas,peliculas.get(opciones));
                    peliculas.get(opciones).setCantidad(peliculas.get(opciones).getCantidad()-boletas);
                    mp.insertar(persona, calcularEdad(fecha));
                    menoresYmayores(calcularEdad(fecha));
                    enabled[1]=true;
                    JOptionPane.showMessageDialog(null, "Boletas vendidas con éxito");
                }else{
                    JOptionPane.showMessageDialog(null,"No hay suficientes boletas");
                }
                    
            }else{
                    JOptionPane.showMessageDialog(null,"Ingresó un valor incorrecto");
            }
        }
        
    }
    
    public int calcularEdad(String fechaNacimiento) {
        
            try {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
            LocalDate fechaNac = LocalDate.parse(fechaNacimiento, formatter);

            LocalDate fechaActual = LocalDate.now();
            long años = ChronoUnit.YEARS.between(fechaNac, fechaActual);

            return (int) años;
        } catch (Exception e) {
            System.err.println("Error al calcular la edad: " + e.getMessage());
            JOptionPane.showMessageDialog(null, "Ingreso incorrectamente la edad");
            return -1;
        }
    }
    
    public String mostrarPeliculas(){
        String cartelera = "";
        int c = 1;
        for(Pelicula peli: peliculas){
            if(peli.isParaAdultos()){
                cartelera+=  "("+c+") "+peli.getNombre()+" [Apto+14] Boletas:" +peli.getCantidad()+"\n";
            }else{
                cartelera+=  "("+c+") "+peli.getNombre()+" [AptoTodos] Boletas:" +peli.getCantidad()+"\n";
            }
            c++;
        }
        return cartelera;
    }
    
    public void menoresYmayores(int edad){
        if(edad>=18){
            mayor_menor[0]++;
        }else{
            mayor_menor[1]++;
        }
    }
    
    public void mostrarReporte(){
        JOptionPane.showMessageDialog(null,"Reporte de Mayores/Menores de Edad \n Mayores: "+mayor_menor[0]+"\n Menores: "+mayor_menor[1]);
    }
    
    public void imprimirPersonas(){
        JOptionPane.showMessageDialog(null,mp.obtenerHeapComoTexto());
    }
    
    public void buscarPersona(){
        mp.encontrarPersona(JOptionPane.showInputDialog(null,"Ingrese el Documento: "));
    }
    
    public boolean[] cambiarEstado(){
        return(enabled);
    }
    
    public static boolean verificarString(String cadena) {
        int contadorDigitos = 0;

        for (int i = 0; i < cadena.length(); i++) {
            char c = cadena.charAt(i);
            
            if (Character.isDigit(c)) {
                contadorDigitos++;
            } else {
                return false;
            }
        }

        return contadorDigitos >= 6;
    }
    
    public static boolean verificarString2(String cadena) {
    for (int i = 0; i < cadena.length(); i++) {
        char c = cadena.charAt(i);
        if (Character.isDigit(c)) {
            return false;
        }
    }
    return true;
}
}

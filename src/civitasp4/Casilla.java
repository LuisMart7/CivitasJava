package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class Casilla{
    static int carcel;
    private String nombre;
    
    //EXAMEN
    private int menosVida = 2;
    
    Diario diario = Diario.getInstance();
    
    Casilla(String nombre){
        this.nombre=nombre;
    }

    public int getCarcel(){
        return carcel;
    }
    
    public String getNombre(){
        return nombre;
    }
    
    protected int getMenosVida(){
        return menosVida;
    }
    
    protected void informe(int actual, ArrayList<Jugador> todos){
        diario.ocurreEvento("El jugador " + todos.get(actual).getNombre() +
                           " ha caido en la casilla " + getNombre());
        
    }
    
    protected void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            
            //EXAMEN
            todos.get(actual).reduceVida(menosVida);
        }
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
         return (actual>=0 && actual < todos.size());
    }
    
    @Override
    public String toString(){
        String cadena = "La casilla se llama: ";
        cadena += (nombre + "\n");
        
        return cadena;
    }
}

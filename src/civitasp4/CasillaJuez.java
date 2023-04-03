package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class CasillaJuez extends Casilla{
    //EXAMEN
    final protected int FACTOR = 10;
    
    CasillaJuez(int carcel){
        super("Juez");
        
        Casilla.carcel = carcel;
    }
    
    @Override
    protected void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).encarcelar(carcel);
            
            //EXAMEN
            todos.get(actual).reduceVida(super.getMenosVida()*FACTOR);
        }
    }
    
    @Override
    public String toString(){
        String cadena = super.toString();
        cadena += "Ha caido en la casilla del juez. Ha sido encarcelado\n";
        
        return cadena;
    }
}
//EXAMEN
package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class CasillaPozo extends Casilla{
    protected int FACTOR = 20;
    
    CasillaPozo(){
        super("Pozo");
    }
    
    @Override
    protected void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            
            todos.get(actual).reduceVida(super.getMenosVida()*FACTOR);
        }
    }
    
    @Override
    public String toString(){
        String cadena = super.toString();
        cadena += "Ha caido en una casilla de tipo pozo\n";
        
        return cadena;
    }
}

package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class SorpresaIrCarcel extends Sorpresa{
    private Tablero tablero;
    
    SorpresaIrCarcel(Tablero tablero){
        super("Ir Carcel");
        
        this.tablero = tablero;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual,todos);
            
            todos.get(actual).encarcelar(tablero.getCarcel());
        }
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}
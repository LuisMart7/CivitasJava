package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class SorpresaJugadorEspeculador extends Sorpresa{
    private int valor;
    
    SorpresaJugadorEspeculador(int v){
        super("Jugador Especulador");
        
        valor = v;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            
            JugadorEspeculador jugadorEspeculador = new JugadorEspeculador(todos.get(actual), valor);
            todos.set(actual, jugadorEspeculador);
        }
    }
}

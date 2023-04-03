package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class SorpresaPorCasaHotel extends Sorpresa{
    private int valor;
    
    SorpresaPorCasaHotel(int v){
        super("Por Casa Hotel");
        
        valor = v;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo((float)(valor*todos.get(actual).cantidadCasasHoteles()));
        }
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}

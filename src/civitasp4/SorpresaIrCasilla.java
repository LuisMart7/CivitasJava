package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class SorpresaIrCasilla extends Sorpresa{
    private Tablero tablero;
    private int valor;
    
    SorpresaIrCasilla(int valor, Tablero tablero){
        super("Ir Casilla");
        
        this.valor = valor;
        this.tablero = tablero;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            
            int cas = todos.get(actual).getNumCasillaActual();
            int tirada = tablero.calcularTirada(cas, valor);
            int nuevaPos = tablero.nuevaPosicion(cas, tirada);
            
            todos.get(actual).moverACasilla(nuevaPos);
            tablero.getCasilla(nuevaPos).recibeJugador(actual,todos);
        }
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}

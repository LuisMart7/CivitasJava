package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class SorpresaPagarCobrar extends Sorpresa{
    private int valor;
    
    SorpresaPagarCobrar(int valor){
        super("Pagar Cobrar");
        
        this.valor = valor;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).modificarSaldo((float)valor);
        }
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
    
    protected int getValor(){
        return valor;
    }
}

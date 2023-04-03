package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class SorpresaPorJugador extends Sorpresa{
    private int valor;
    
    SorpresaPorJugador(int v){
        super("Por Jugador");
        
        valor = v;
    }
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            
            SorpresaPagarCobrar sorpresa_pagar = new SorpresaPagarCobrar((-1)*valor);
            SorpresaPagarCobrar sorpresa_cobrar = new SorpresaPagarCobrar(valor*todos.size()-1);
            
            for(int i=0; i<todos.size(); i++){
                if(i != actual)
                    todos.get(i).modificarSaldo((float)sorpresa_pagar.getValor());
                else
                    todos.get(actual).recibe((float)sorpresa_cobrar.getValor());
            }
        }
    }
    
    @Override
    public String toString(){
        return super.toString();
    }
}

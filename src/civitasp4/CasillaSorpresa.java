package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class CasillaSorpresa extends Casilla{
    MazoSorpresas mazo;
    
    //EXAMEN
    final protected int FACTOR = 3;
    
    CasillaSorpresa(MazoSorpresas mazo, String nombre){
        super(nombre);
        
        this.mazo = mazo;
    }
    
    @Override
    protected void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            
            Sorpresa sorpresa = mazo.siguiente();
            sorpresa.aplicarAJugador(actual, todos);
            
            //EXAMEN
            todos.get(actual).reduceVida(super.getMenosVida()*FACTOR);
        }
    }
    
    @Override
    public String toString(){
        String cadena = super.toString();
        cadena += "Ha caido en una casilla de tipo sorpresa\n";
        
        return cadena;
    }
}

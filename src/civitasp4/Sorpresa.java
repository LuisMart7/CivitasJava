package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public abstract class Sorpresa{
    private String texto;
    
    Diario diario = Diario.getInstance();

    Sorpresa( String texto){
        this.texto=texto;
    }
    
    public boolean jugadorCorrecto(int actual, ArrayList<Jugador> todos){
        return (actual >= 0 && actual < todos.size());
    }
    
    protected void informe(int actual, ArrayList<Jugador> todos){
        diario.ocurreEvento("Se le aplica la sorpresa "+texto.toUpperCase()+" al jugador "+todos.get(actual).toString());
    }
    
    public abstract void aplicarAJugador(int actual, ArrayList<Jugador> todos);
    
    @Override
    public String toString(){
        return texto;
    }
}

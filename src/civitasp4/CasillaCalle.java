package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class CasillaCalle extends Casilla{
    private TituloPropiedad titulo;
    
    //EXAMEN
    final protected int FACTOR = 2;
    
    CasillaCalle(TituloPropiedad titulo){
        super(titulo.getNombre());
        
        this.titulo = titulo;
    }
    
    protected TituloPropiedad getTitulo(){
        return titulo;
    }
    
    @Override
    protected void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual,todos);
            
            Jugador jugador = todos.get(actual);
            
            if(!titulo.tienePropietario()){
                boolean puedeComprar = todos.get(actual).puedeComprarCasilla();
                
                if(puedeComprar){
                    titulo.tramitarAlquiler(jugador);
                }
            }
            
            //EXAMEN
            todos.get(actual).reduceVida(super.getMenosVida()*FACTOR);
        }
    }
    
    @Override
    public String toString(){
        String cadena = super.toString();
        cadena += ("El titulo asociado a esta calle es: " + titulo.getNombre() + "\n");
        
        return cadena;
    }
}

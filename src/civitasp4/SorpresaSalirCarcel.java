package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class SorpresaSalirCarcel extends Sorpresa{
    MazoSorpresas mazo;
    
    SorpresaSalirCarcel(MazoSorpresas m){
        super("Salir Carcel");
        
        this.mazo = m;
    }
    
    @Override
    public void aplicarAJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual,todos)){
            informe(actual,todos);
            boolean hayCarta=false;
            for(int i=0; i<todos.size() && !hayCarta; i++){
                if(todos.get(i).tieneSalvoconducto())
                    hayCarta=true;
            }
            if(!hayCarta){
                todos.get(actual).obtenerSalvoconducto(this);
                salirDelMazo();
            }
        }
    }
    
    public void salirDelMazo(){
        mazo.inhabilitarCartaEspecial(this);
    }
    
    public void usada(){
        mazo.habilitarCartaEspecial(this);
    }
}

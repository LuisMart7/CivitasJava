package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class CasillaImpuesto extends Casilla{
    private float importe;
    
    //EXAMEN
    final protected int FACTOR = 4;
    
    CasillaImpuesto(float cantidad){
        super("Impuesto");
        
        importe = cantidad;
    }
    
    @Override
    protected void recibeJugador(int actual, ArrayList<Jugador> todos){
        if(jugadorCorrecto(actual, todos)){
            informe(actual, todos);
            todos.get(actual).pagaImpuesto(importe);
            
            //EXAMEN
            todos.get(actual).reduceVida(super.getMenosVida()*FACTOR);
        }
    }
    
    @Override
    public String toString(){
        String cadena = super.toString();
        cadena += ("El importe a abonar por el impuesto es de: " + importe + "\n");
        
        return cadena;
    }
}
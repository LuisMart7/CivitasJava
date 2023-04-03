package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class Diario{
    static final private Diario instance = new Diario();
    
    private ArrayList<String> eventos;
    
    private Diario(){
        eventos = new ArrayList<>();
    }
    
    public boolean eventosPendientes(){
        return (!(eventos.isEmpty()));
    }
    
    static public Diario getInstance(){
        return instance;
    }
    
    public String leerEvento(){
        String salida = "";
        
        if(eventosPendientes()){
            salida = eventos.remove(0);
        }
        
        return salida;
    }
    
    void ocurreEvento(String e){
        eventos.add(e);
    }
}

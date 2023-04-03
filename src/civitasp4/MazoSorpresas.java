/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package civitasp4;
import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class MazoSorpresas{
    private boolean barajada;
    private boolean debug;
    private int usadas;
    
    private Sorpresa ultimaSorpresa;
    
    private ArrayList<Sorpresa> sorpresas, cartasEspeciales;
    
    Diario diario = Diario.getInstance();
    
    private void init(){
        this.barajada = false;
        this.ultimaSorpresa = null;
        this.usadas = 0;
        
        this.sorpresas = new ArrayList<>();
        this.cartasEspeciales = new ArrayList<>();
    }
    
    MazoSorpresas(){
        init();
        
        this.debug = false;
    }
    
    MazoSorpresas(boolean debug){
        init();
        
        this.debug = debug;
        
        if(debug){
            diario.ocurreEvento("Actualizacion de debug a " + String.valueOf(debug));
        }
    }
    
    void alMazo(Sorpresa sorpresa){
        if(!barajada){
            sorpresas.add(sorpresa);
        }
        
        //siguiente();
    }
    
    Sorpresa siguiente(){
        if((!barajada || usadas == sorpresas.size()) && debug){
            Random random = new Random();
            
            sorpresas.addAll(sorpresas.size()-1, cartasEspeciales);
            
            for(int i=0; i<sorpresas.size()-1; i++){
                int resultado = random.nextInt()*(sorpresas.size()-1);
                
                if(resultado == i){
                    resultado++;
                    Collections.swap(sorpresas, i, resultado);
                }
            }
            
            barajada = true;
            usadas = 0;
        
            diario.ocurreEvento("Barajeo correcto");
        }
        
        usadas++;
        ultimaSorpresa = sorpresas.get(0);
            
        sorpresas.add(sorpresas.size()-1, ultimaSorpresa);
            
        return ultimaSorpresa;
    }
    
    void inhabilitarCartaEspecial(Sorpresa sorpresa){
        if(sorpresas.contains(sorpresa)){
            sorpresas.remove(sorpresas.indexOf(sorpresa));
            cartasEspeciales.add(sorpresa);
            
            diario.ocurreEvento(sorpresa.toString() + " removida del mazo");
        }
    }
    
    void habilitarCartaEspecial(Sorpresa sorpresa){
        if(sorpresas.contains(sorpresa)){
            cartasEspeciales.remove(cartasEspeciales.indexOf(sorpresa));
            sorpresas.add(sorpresas.size(), sorpresa);
            
            diario.ocurreEvento(sorpresa.toString() + " removida del mazo de cartas especiales");
        }
    }
}

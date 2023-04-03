package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class Tablero{
    private int numCasillaCarcel;
    private int porSalida;
    private boolean tieneJuez;
    
    private ArrayList<Casilla> casillas;
    
    private void init(){
        this.porSalida = 0;
        this.tieneJuez = false;
    
        this.casillas = new ArrayList<>();
    }
    
    public Tablero(int numCasillaCarcel){
        init();
        
        if(numCasillaCarcel >= 1){
            this.numCasillaCarcel = numCasillaCarcel;
        }
        
        else{
            this.numCasillaCarcel = 1;
        }        
    
        Casilla casilla = new Casilla("Salida");
        casillas.add(casilla);
    }
    
    int getCarcel(){return numCasillaCarcel;}
    boolean tieneJuez(){return tieneJuez;}
    
   public ArrayList<Casilla> getCasillas(){return casillas;}
    
    private boolean correcto(){
        return ((casillas.size() > numCasillaCarcel) && tieneJuez);
    }
    
    private boolean correcto(int numCasilla){
        return (correcto() && (numCasilla < casillas.size()));
    }
    
    int getPorSalida(){
        if(porSalida > 0){
            porSalida--;
            
            return (porSalida+1);
        }
        
        else{
            return porSalida;
        }
    }
    
    void añadeCasilla(Casilla casilla){
        Casilla carcel = new Casilla("Carcel");
        
        if(casillas.size() == numCasillaCarcel){
            casillas.add(carcel);
        }
        
        casillas.add(casilla);
        
        if(casillas.size() == numCasillaCarcel){
            casillas.add(carcel);
        }
    }
    
    void añadeJuez(){
        CasillaJuez juez = new CasillaJuez(numCasillaCarcel);
        casillas.add(juez);
        
        tieneJuez = true;
    }
    
    Casilla getCasilla(int numCasilla){
        if(correcto(numCasilla)){
            return casillas.get(numCasilla);
        }
        
        else{
            return null;
        }
    }
    
    int nuevaPosicion(int actual, int tirada){
        if(!correcto()){
            return -1;
        }
        
        int resultado = actual + tirada;
        
        if(resultado > casillas.size()){
            resultado %= casillas.size();
            porSalida++;
        }
        
        return resultado;
    }
    
    int calcularTirada(int origen, int destino){
        int resultado = destino - origen;
        
        if(resultado < 0){
            resultado += casillas.size();
        }
        
        return resultado;
    }
}

package civitasp4;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class JugadorEspeculador extends Jugador{
    final protected int FACTORESPECULADOR = 2;
    
    private float fianza;
    
    JugadorEspeculador(Jugador jugador, int fianza){
        super(jugador);
        
        this.fianza = fianza;
        
        for(int i=0; i<super.getPropiedades().size(); i++){
            super.getPropiedades().get(i).actualizaPropietarioPorConversion(this);
        }
    }
    
    @Override
    public boolean esEspeculador(){
        return true;
    }
    
    @Override
    protected int getCasasMax(){return super.getCasasMax()*FACTORESPECULADOR;}
    @Override
    protected int getHotelesMax(){return super.getHotelesMax()*FACTORESPECULADOR;}
    
    @Override
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado() == true){
            if(fianza <= super.getSaldo()){
                paga(fianza);
                
                diario.ocurreEvento("Jugador especulador " + getNombre() + "paga la fianza " +
                        "y no entra a la cárcel");
            }
        }
        
        super.encarcelar(numCasillaCarcel);
        
        return encarcelado;
    }
    
    @Override
    boolean pagaImpuesto(float cantidad){
        boolean precio = super.pagaImpuesto(cantidad/2);
        
        return precio;
    }
    
    @Override
    public String toString(){
        String cadena = super.toString();
        cadena += ("\nEl jugador es especulador");
        
        return cadena;
    }
    
    //EXAMEN
    @Override
    protected int reduceVida(int cantidad){
        /*if(cantidad >= (vida/FACTORESPECULADOR)){
            vida = cantidad/FACTORESPECULADOR;
        } else{
            vida = 0;
        }
        
        return vida;*/
        
        return super.reduceVida(cantidad/FACTORESPECULADOR);
    }
}

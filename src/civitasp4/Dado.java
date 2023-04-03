package civitasp4;

import java.util.Random;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class Dado{
    private Random random;
    
    private int ultimoResultado;
    private boolean debug;
    
    static final private Dado instance = new Dado();
    
    final private int salidaCarcel = 5;
    
    Diario diario = Diario.getInstance();
    
    private Dado(){
        random = new Random();
        ultimoResultado = 0;
        debug = false;
    }
    
    static public Dado getInstance(){
        return instance;
    }
    
    public int tirar(){
        if(!debug)
            ultimoResultado = (int)(random.nextDouble()*6+1);
        else
            ultimoResultado = 1;
        
        return ultimoResultado;
        
    }
    
    public boolean salgoDeLaCarcel(){
        int res = tirar();
        return res == salidaCarcel;
    }
    
    public int quienEmpieza(int n){
        return (int)(random.nextDouble()*(n)+0);
    }
    
    public void setDebug(boolean d){
        debug=d;

        diario.ocurreEvento("Actualizacion de debug a "+String.valueOf(debug));
    }
    
    public int getUltimoResultado(){
        return ultimoResultado;
    }
}

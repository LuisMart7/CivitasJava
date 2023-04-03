package civitasp4;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class TituloPropiedad{
    private final static float FACTORINTERESESHIPOTECA = (float)(1.1);
    
    private float alquilerBase;
    private float factorRevalorizacion;
    private float hipotecaBase;
    
    private boolean hipotecado;
    private String nombre;
    
    private int numCasas;
    private int numHoteles;
    
    private float precioCompra;
    private float precioEdificar;
    
    private Jugador propietario;
    
    private void init(){
        this.hipotecado = false;
        this.numCasas = 0;
        this.numHoteles = 0;
        
        this.propietario = null;
    }
    
    TituloPropiedad(String nom, float ab, float fr, float hb, float pc, float pe){
        init();
        
        this.nombre = nom;
        this.alquilerBase = ab;
        this.factorRevalorizacion = fr;
        this.hipotecaBase = hb;
        this.precioCompra = pc;
        this.precioEdificar = pe;
    }
    
    float getFactorInteresesHipoteca(){return FACTORINTERESESHIPOTECA;}
    
    String getNombre(){return nombre;}
    float getAlquilerBase(){return alquilerBase;}
    float getFactorRevaloriacion(){return factorRevalorizacion;}
    float getHipotecaBase(){return hipotecaBase;}
    public boolean getHipotecado(){return hipotecado;}
    float getPrecioCompra(){return precioCompra;}
    float getPrecioEdificar(){return precioEdificar;}
    
    int getCasas(){return numCasas;}
    int getHoteles(){return numHoteles;}
    int cantidadCasasHoteles(){return (numCasas + numHoteles);}
    
    Jugador getPropietario(){return propietario;}
    
    boolean tienePropietario(){
        return (propietario != null);
    }
    
    private boolean esEsteElPropietario(Jugador jugador){
        return (jugador == propietario);
    }
    
    private float getImporteHipoteca(){
        return ((float)(hipotecaBase * 1+(numCasas*0.5)+(numHoteles*2.5)));
    }
    
    @Override
    public String toString(){
        String cadena = "Nombre: " + nombre + 
                "\n\tPrecio alquiler base: " + String.valueOf(alquilerBase) + 
                "\n\tFactor de revalorizacion: " + String.valueOf(factorRevalorizacion) + 
                "\n\tPrecio base de hipoteca: " + String.valueOf(hipotecaBase) + 
                "\n\tPrecio de compra: " + String.valueOf(precioCompra) +
                "\n\tPrecio por edificar: " + String.valueOf(precioEdificar) + 
                "\n\tNumero de casas: " + String.valueOf(numCasas) + 
                "\n\tNumero de hoteles: " + String.valueOf(numHoteles) + "\n\t";
        
        if(tienePropietario()){
            cadena += ("Nombre propietario: " + propietario.getNombre() + "\n\t");
        }
        else{
            cadena += ("No tiene propietario\n\t");
        }
        
        if(hipotecado){
            cadena += "Propiedad hipotecada\n";
        }
        else{
            cadena += "Propiedad no hipotecada\n";
        }
        
        return cadena;
    }
    
    private float getPrecioAlquiler(){
        float alquiler = (float)(0.0);
        
        if(!(hipotecado) && !propietarioEncarcelado()){
            alquiler = (float)(alquilerBase * (1+(numCasas*0.5)+(numHoteles*2.5)));
        }
        
        return alquiler;
    }
    
    private float getPrecioVenta(){
        int casas = numCasas + 5*numHoteles;
        
        return ((float)(precioCompra + precioEdificar*casas*factorRevalorizacion));
    }
    
    float getImporteCancelarHipoteca(){
        return ((float)(getImporteHipoteca()*FACTORINTERESESHIPOTECA));
    }
    
    void tramitarAlquiler(Jugador jugador){
        if(tienePropietario()){
            if(!esEsteElPropietario(jugador)){
                float precio = getPrecioAlquiler();
                
                jugador.pagaAlquiler(precio);
                propietario.recibe(precio);
            }
        }
    }
    
    private boolean propietarioEncarcelado(){
        return propietario.isEncarcelado();
    }
    
    boolean derruirCasas(int n, Jugador jugador){
        boolean operacion = false;
        
        if(jugador.getNombre().equals(propietario.getNombre())){
            if(cantidadCasasHoteles() >= n){
                for(int i=0; i<n; i++){
                    numCasas--;
                }
                
                operacion = true;
            }
        }
        
        return operacion;
    }
    
    /*
    boolean derruirCasas(int n, Jugador jugador){
        boolean operacion = false;
        
        if(esEsteElPropietario(jugador)){
            if(cantidadCasasHoteles() >= n){
                //EDIFICAR HOTEL || DERRUIR CASAS INDIVIDUALMENTE
                if(n <= numCasas){
                    for(int i=0; i<n; i++){
                        numCasas--;
                    }
                }
                //VENDER
                else{
                    numCasas = 0;
                    numHoteles = 0;
                }
                
                operacion = true;
            }
        }
        
        return operacion;
    }*/
    
    boolean vender(Jugador jugador){
        boolean operacion = false;
        
        if(esEsteElPropietario(jugador) && !(hipotecado)){
            propietario.recibe(getPrecioVenta());
            derruirCasas(cantidadCasasHoteles(), jugador);
            
            numCasas = 0;
            numHoteles = 0;
            propietario = null;
            
            operacion = true;
        }
    
        return operacion;
    }
    
    void actualizaPropietarioPorConversion(Jugador jugador){
        propietario = jugador;
    }
    
    boolean cancelarHipoteca(Jugador jugador){
        boolean resultado = false;
        
        if(!hipotecado){
            if(esEsteElPropietario(jugador)){
                propietario.paga(getImporteCancelarHipoteca());
                
                hipotecado = false;
                resultado = true;
            }
        }
        
        return resultado;
    }
    
    boolean hipotecar(Jugador jugador){
        boolean salida = false;
        
        if(!hipotecado && esEsteElPropietario(jugador)){
            propietario.recibe(getImporteHipoteca());
            
            hipotecado = true;
            salida = false;
        }
        
        return salida;
    }
    
    boolean comprar(Jugador jugador){
        boolean resultado = false;
        
        if(!tienePropietario()){
            propietario = jugador;
            resultado = true;
            
            propietario.paga(precioCompra);
        }
        
        return resultado;
    }
    
    boolean construirCasa(Jugador jugador){
        boolean resultado = false;
        
        if(esEsteElPropietario(jugador)){
            if(jugador.puedoEdificarCasa(this)){
                propietario.paga(precioEdificar);
                
                numCasas++;
                resultado = true;
            }
        }
        
        return resultado;
    }
    
    boolean construirHotel(Jugador jugador){
        boolean resultado = false;
        
        if(esEsteElPropietario(jugador)){
            if(jugador.puedoEdificarHotel(this)){
                propietario.paga(precioEdificar);
                
                numHoteles++;
                resultado = true;
            }
        }
        
        return resultado;
    }
}

package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class Jugador implements Comparable<Jugador>{
    final protected int CASASMAX = 4;
    final protected int CASASPORHOTEL = 4;
    final protected int HOTELESMAX = 4;
    final protected float PASOPORSALIDA = (float)(1000.0);
    final protected float PRECIOLIBERTAD = (float)(200.0);
    final private float SALDOINICIAL = (float)(7500.0);
    
    //EXAMEN
    final private int VIDAMAXIMA = 1000;
    final private int VIDAPORSALIDA = 10;
    
    protected boolean encarcelado;
    private String nombre;
    private int numCasillaActual;
    private boolean puedeComprar;
    private float saldo;
    
    private ArrayList<TituloPropiedad> titulos;
    private SorpresaSalirCarcel salvoconducto;
    
    static final Diario diario = Diario.getInstance();
    static final Dado dado = Dado.getInstance();
    
    //EXAMEN
    protected int vida;
    
    private void init(){
        encarcelado = false;
        nombre = "";
        numCasillaActual = 0;
        puedeComprar = true;
        saldo = SALDOINICIAL;
        
        titulos = new ArrayList<>();
        salvoconducto = null;
        
        //EXAMEN
        vida = 0;
    }
    
    Jugador(String nombre){
        init();
        
        this.nombre = nombre;
    }
    
    protected Jugador(Jugador otro){
        this.nombre = otro.nombre;
        
        encarcelado = otro.encarcelado;
        numCasillaActual = otro.numCasillaActual;
        saldo = otro.saldo;
        puedeComprar = otro.puedeComprar;
        salvoconducto = otro.salvoconducto;
        
        titulos = otro.titulos;
        
        //EXAMEN
        this.vida = otro.vida;
    }
    
    int getCasasMax(){return CASASMAX;}
    int getHotelesMax(){return HOTELESMAX;}
    private float getPrecioLibertad(){return PRECIOLIBERTAD;}
    private float getPremioPasoSalida(){return PASOPORSALIDA;}
    int getCasasPorHotel(){return CASASPORHOTEL;}
    
    protected String getNombre(){return nombre;}
    public boolean isEncarcelado(){return encarcelado;}
    int getNumCasillaActual(){return numCasillaActual;}
    protected float getSaldo(){return saldo;}
    boolean getPuedeComprar(){return puedeComprar;}
    
    protected ArrayList<TituloPropiedad> getPropiedades(){return titulos;}
    
    public boolean esEspeculador(){
        return false;
    }
    
    int cantidadCasasHoteles(){
        int suma = 0;
        
        for(int i=0; i<titulos.size(); i++){
            suma += titulos.get(i).cantidadCasasHoteles();
        }
        
        return suma;
    }
    
    private boolean existeLaPropiedad(int ip){
        return(ip >= 0 && ip < titulos.size());
    }
    
    protected boolean debeSerEncarcelado(){
        if(isEncarcelado()){
            return false;
        }
        
        if(!tieneSalvoconducto()){
            return true;
        }
        
        else{
            perderSalvoconducto();
            diario.ocurreEvento("Jugador " + nombre + " se libra de la cÃ¡rcel");
            
            return false;
        }
    }
    
    boolean encarcelar(int numCasillaCarcel){
        if(debeSerEncarcelado()){
            moverACasilla(numCasillaCarcel);
            encarcelado = true;
            
            diario.ocurreEvento("Jugador " + nombre + " va a la cÃ¡rcel");
        }
        
        return encarcelado;
    }
    
    boolean obtenerSalvoconducto(SorpresaSalirCarcel sorpresa){
        if(isEncarcelado()){
            return false;
        }
        
        salvoconducto = sorpresa;
        return true;
    }
    
    void perderSalvoconducto(){
        salvoconducto.usada();
        salvoconducto = null;
    }
    
    boolean tieneSalvoconducto(){
        return(salvoconducto != null);
    }
    
    boolean puedeComprarCasilla(){
        puedeComprar = true;
        
        if(isEncarcelado()){
            puedeComprar = false;
        }
        
        return puedeComprar;
    }
    
    boolean paga(float cantidad){
        return(modificarSaldo((float)((-1.0)*cantidad)));
    }
    
    boolean pagaImpuesto(float cantidad){
        if(isEncarcelado()){
            return false;
        }
        
        return paga(cantidad);
    }
    
    boolean pagaAlquiler(float cantidad){
        if(isEncarcelado()){
            return false;
        }
        
        return paga(cantidad);
    }
    
    boolean recibe(float cantidad){
        if(isEncarcelado()){
            return false;
        }
        
        return modificarSaldo(cantidad);
    }
    
    boolean modificarSaldo(float cantidad){
        saldo += cantidad;
        diario.ocurreEvento("Saldo incrementado en " + String.valueOf(cantidad));
        
        return true;
    }
    
    boolean moverACasilla(int numCasilla){
        if(isEncarcelado()){
            return false;
        }
        
        numCasillaActual = numCasilla;
        puedeComprar = false;
        
        diario.ocurreEvento("Jugador " + nombre + " se mueve a casilla " + String.valueOf(numCasilla));
        
        return true;
    }
    
    private boolean puedoGastar(float cantidad){
        if(isEncarcelado()){
            return false;
        }
        
        return (saldo >= cantidad);
    }
    
    boolean vender(int ip){
        if(isEncarcelado()){
            return false;
        }
        
        if(existeLaPropiedad(ip)){
            if(titulos.get(ip).vender(this)){
                titulos.remove(ip);
                diario.ocurreEvento("Jugador " + nombre + " vende propiedad");
                
                return true;
            }
        }
        
        return false;
    }
    
    boolean tieneAlgoQueGestionar(){
        return (titulos.size() > 0);
    }
    
    private boolean puedeSalirCarcelPagando(){
        return (saldo >= PRECIOLIBERTAD);
    }
    
    boolean salirCarcelPagando(){
        if(isEncarcelado() && puedeSalirCarcelPagando()){
            paga(PRECIOLIBERTAD);
            encarcelado = false;
            
            diario.ocurreEvento("Jugador " + nombre + " sale de la cÃ¡rcel");
            
            return true;
        }
        
        return false;
    }
    
    boolean salirCarcelTirando(){
        if(dado.salgoDeLaCarcel()){
            encarcelado = false;
            diario.ocurreEvento("Jugador " + nombre + " sale de la cÃ¡rcel");
        }
        
        return (!encarcelado);
    }
    
    boolean pasaPorSalida(){
        modificarSaldo(PASOPORSALIDA);
        diario.ocurreEvento("Jugador " + nombre + " pasa por salida");
        
        //EXAMEN
        vida = aumentaVidaPorSalida();
        
        return true;
    }
    
    @Override
    public int compareTo(Jugador otro){
        return (Float.compare(this.saldo, otro.saldo));
    }
            
    boolean cancelarHipoteca(int ip){
        boolean resultado = false;
        
        if(encarcelado){
            return resultado;
        }
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = titulos.get(ip);
            float cantidad = propiedad.getImporteCancelarHipoteca();
            
            if(puedoGastar(cantidad)){
                resultado = propiedad.cancelarHipoteca(this);
                
                if(resultado){
                    diario.ocurreEvento("El jugador " + nombre + " cancela la hipoteca de la propiedad " + String.valueOf(ip));
                }
            }
        }
        
        return resultado;
    }
    
    boolean comprar(TituloPropiedad titulo){
        boolean resultado = false;
        
        if(encarcelado){
            return resultado;
        }
        
        if(puedeComprar){
            float precio = titulo.getPrecioCompra();
            
            if(puedoGastar(precio)){
                resultado = titulo.comprar(this);
                
                if(resultado){
                    titulos.add(titulo);
                    diario.ocurreEvento("El jugador " + nombre + " compra la proppiedad " + titulo.toString());
                }
                
                puedeComprar = false;
            }
        }
        
        return resultado;
    }
    
    boolean construirHotel(int ip){
        boolean resultado = false;
        boolean puedoEdificarHotel;
        
        if(encarcelado){
            return resultado;
        }
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = titulos.get(ip);
            puedoEdificarHotel = puedoEdificarHotel(propiedad);
            
            float precio = propiedad.getPrecioEdificar();
            
            if(puedoGastar(precio)){
                if(propiedad.getHoteles() < HOTELESMAX){
                    if(propiedad.getCasas() == CASASPORHOTEL){
                        puedoEdificarHotel = true;
                    }
                }
            }
            
            if(puedoEdificarHotel){
                resultado = propiedad.construirHotel(this);
                
                propiedad.derruirCasas(CASASPORHOTEL, this);
                diario.ocurreEvento("El jugador " + nombre + " construye hotel en la propiedad " + String.valueOf(ip));
            }
        }
        
        return resultado;
    }
    
    boolean construirCasa(int ip){
        boolean resultado = false;
        boolean puedoEdificarCasa;
        
        if(encarcelado){
            return resultado;
        }
        
        if(!encarcelado){
            if(existeLaPropiedad(ip)){
                TituloPropiedad propiedad = titulos.get(ip);
                puedoEdificarCasa = puedoEdificarCasa(propiedad);
                
                float precio = propiedad.getPrecioEdificar();
                
                if(puedoGastar(precio) && propiedad.getCasas() < CASASMAX){
                    puedoEdificarCasa = true;
                }
                
                if(puedoEdificarCasa){
                    resultado = propiedad.construirCasa(this);
                }
                
                if(resultado){
                    diario.ocurreEvento("El jugador " + nombre + " construye una casa en la propiedad " + String.valueOf(ip));
                }
            }
        }
        
        return resultado;
    }
    
    boolean hipotecar(int ip){
        boolean resultado = false;
        
        if(encarcelado){
            return resultado;
        }
        
        if(existeLaPropiedad(ip)){
            TituloPropiedad propiedad = titulos.get(ip);
            resultado = propiedad.hipotecar(this);
        }
        
        if(resultado){
            diario.ocurreEvento("El jugador " + nombre + " hipoteca la propiedad " + String.valueOf(ip));
        }
        
        return resultado;
    }
    
    boolean enBancarrota(){
        return (saldo < 0);
    }
    
    boolean puedoEdificarCasa(TituloPropiedad propiedad){
        boolean es_nuestra = (propiedad.getPropietario() == this);
        boolean puedoGastar = puedoGastar((propiedad.getPrecioEdificar()));
        boolean maximo = (propiedad.getCasas() < CASASMAX);
        
        return (!encarcelado && es_nuestra && puedoGastar && maximo);
    }
    
    boolean puedoEdificarHotel(TituloPropiedad propiedad){
        boolean es_nuestra = (propiedad.getPropietario() == this);
        boolean puedoGastar = puedoGastar((propiedad.getPrecioEdificar()));
        boolean maximo = (propiedad.getHoteles() < HOTELESMAX);
        
        return (!encarcelado && es_nuestra && puedoGastar && maximo);
    }
    
    @Override
    public String toString(){
        String cadena = "";
        
        for(int i=0; i<getPropiedades().size(); i++){
            if(i != getPropiedades().size()-1){
                cadena += getPropiedades().get(i).toString() + "\n";
            }else{
                cadena += getPropiedades().get(i).toString();
            }
        }
        
        return ("Nombre: " + nombre + "\n"
                + "Propiedades: \n" + cadena);
    }
    
    public ArrayList<String> getPropiedadesUI(){
        String cadena;
        ArrayList<String> lista = new ArrayList<>();
        
        for(int i=0; i<titulos.size(); i++){
            cadena = getPropiedades().get(i).toString();
            lista.add(cadena);
        }
        
        return lista;
    }
    
    //EXAMEN
    protected int reduceVida(int cantidad){
        if(cantidad >= vida){
            vida -= cantidad;
        } else{
            vida = 0;
        }
        
        return vida;
    }
    
    //EXAMEN
    protected int aumentaVidaPorSalida(){
        if(vida + VIDAPORSALIDA <= VIDAMAXIMA){
            vida += VIDAPORSALIDA;
        } else if(vida > VIDAMAXIMA - VIDAPORSALIDA){
            vida = VIDAMAXIMA;
        }
        
        return vida;
    }
    
    //EXAMEN
    protected String mostrarVida(){
        String cadena = ("Nombre: " + nombre + ", Vida: " + vida);
        
        return cadena;
    }
}

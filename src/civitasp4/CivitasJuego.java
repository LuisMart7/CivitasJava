package civitasp4;

import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class CivitasJuego{
    private int indiceJugadorActual;
    
    private ArrayList<Jugador> jugadores;
    
    private Tablero tablero;
    private GestorEstados gestor;
    private MazoSorpresas mazo;
    private EstadosJuego estados;
    private OperacionesJuego operaciones;
    
    Dado dado = Dado.getInstance();
    
    public CivitasJuego(String[] nombres){
        jugadores=new ArrayList<>();
        
        for (String nombre : nombres) {
            Jugador jugador = new Jugador(nombre);
            jugadores.add(jugador);
        }
        
        gestor = new GestorEstados();
        estados = gestor.estadoInicial();
        
        indiceJugadorActual = dado.quienEmpieza(jugadores.size());
        
        mazo= new MazoSorpresas();
        
        inicializarTablero(mazo);
        inicializarMazoSorpresas(tablero);
    }
    
    private void inicializarTablero(MazoSorpresas mazo){
        this.tablero = new Tablero(5);
        this.mazo = mazo;
        
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Arsenal", 50, (float)(0.025), 100, 150, 30)));
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Manchester City", 60, (float)(0.025), 100, 150, 30)));
        
        this.tablero.añadeCasilla(new CasillaSorpresa(this.mazo, "Champions"));
        
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Bayern de Munich", 70, (float)(0.05), 150, 200, 30)));
        
        this.tablero.añadeJuez();
        
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Chelsea", 80, (float)(0.05), 150, 200, 60)));
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Liverpool", 90, (float)(0.075), 150, 200, 60)));
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("PSG", 100, (float)(0.075), 200, 300, 90)));
        
        this.tablero.añadeCasilla(new CasillaImpuesto(600));
        
        this.tablero.añadeCasilla(new Casilla("Parking"));
        
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Manchester United", 110, (float)(0.1), 250, 400, 120)));
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Juventus", 120, (float)(0.1), 250, 400, 120)));

        this.tablero.añadeCasilla(new CasillaSorpresa(this.mazo, "UEFA"));
        
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Barcelona", 130, (float)(0.125), 350, 600, 150)));
        
        this.tablero.añadeCasilla(new Casilla("Carcel"));
        
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Real Madrid", 140, (float)(0.125), 350, 600, 150)));
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Granada", 150, (float)(0.15), 600, 900, 200)));
        
        this.tablero.añadeCasilla(new CasillaSorpresa(this.mazo, "Mundial"));
        
        this.tablero.añadeCasilla(new CasillaCalle(new TituloPropiedad("Inter de Milan", 160, (float)(0.15), 600, 900, 200)));
        
        //EXAMEN
        this.tablero.añadeCasilla(new CasillaPozo());
    }
    
    private void inicializarMazoSorpresas(Tablero tablero){
        this.tablero = tablero;
         
         this.mazo.alMazo(new SorpresaIrCarcel(tablero));
         
         this.mazo.alMazo(new SorpresaSalirCarcel(mazo));
         
         this.mazo.alMazo(new SorpresaPagarCobrar(-400));
         this.mazo.alMazo(new SorpresaPagarCobrar(400));
         
         this.mazo.alMazo(new SorpresaPorJugador(-100));
         this.mazo.alMazo(new SorpresaPorJugador(100));
         
         this.mazo.alMazo(new SorpresaPorCasaHotel(-50));
         this.mazo.alMazo(new SorpresaPorCasaHotel(50));
         
         this.mazo.alMazo(new SorpresaIrCasilla(10, tablero));
         this.mazo.alMazo(new SorpresaIrCasilla(tablero.getCarcel(), tablero));
    
         this.mazo.alMazo(new SorpresaJugadorEspeculador(150));
    }
    
    private void contabilizarPasosPorSalida(Jugador actual){
        if(tablero.getPorSalida() > 0){
            actual.pasaPorSalida();
            
            for(int i=0; i<tablero.getPorSalida()-1; i++){
                actual.pasaPorSalida();
            }
        }
    }
    
    private void pasarTurno(){
        indiceJugadorActual++;
        
        if(indiceJugadorActual >= jugadores.size()){
            indiceJugadorActual = 0;
        }
    }
    
    public void siguientePasoCompletado(OperacionesJuego operacion){
        estados = gestor.siguienteEstado(jugadores.get(indiceJugadorActual), estados, operacion);
    }
    
    private void avanzaJugador(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int posicionActual = jugadorActual.getNumCasillaActual();
        
        int tirada = dado.tirar();
        int posicionNueva = tablero.nuevaPosicion(posicionActual, tirada);
        
        Casilla casilla = tablero.getCasilla(posicionNueva);
        
        contabilizarPasosPorSalida(jugadorActual);
        
        jugadorActual.moverACasilla(posicionNueva);
        casilla.recibeJugador(indiceJugadorActual, jugadores);
        
        contabilizarPasosPorSalida(jugadorActual);
        
        if(posicionNueva == tablero.getCarcel() && jugadorActual.debeSerEncarcelado()){
            jugadorActual.encarcelado = true;
        }
    }
    
    public boolean cancelarHipoteca(int ip){
        return jugadores.get(indiceJugadorActual).cancelarHipoteca(ip);
    }
    
    public boolean comprar(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        int numCasillaActual = jugadorActual.getNumCasillaActual();
        
        CasillaCalle calle = (CasillaCalle)tablero.getCasilla(numCasillaActual);
        TituloPropiedad titulo = calle.getTitulo();
        
        return jugadorActual.comprar(titulo);
    }
    
    public boolean construirCasa(int ip){
        return jugadores.get(indiceJugadorActual).construirCasa(ip);
    }
    
    public boolean construirHotel(int ip){
        return jugadores.get(indiceJugadorActual).construirHotel(ip);
    }
    
    public boolean finDelJuego(){
        for(int i=0; i<jugadores.size(); i++){
            if(jugadores.get(i).enBancarrota()){
                return true;
            }
        }
        
        return false;
    }
    
    public Casilla getCasillaActual(){
        return tablero.getCasilla(jugadores.get(indiceJugadorActual).getNumCasillaActual());
    }
    
    public Jugador getJugadorActual(){
        return jugadores.get(indiceJugadorActual);
    }
    
    public boolean hipotecar(int ip){
        return jugadores.get(indiceJugadorActual).hipotecar(ip);
    }
    
    public String infoJugadorTexto(){
        return jugadores.get(indiceJugadorActual).toString();
    }
    
    private ArrayList<Jugador> ranking(){
        ArrayList<Jugador> ranking = jugadores;
        
        Collections.sort(ranking);
        
        return ranking;
    }
    
    public boolean salirCarcelPagando(){
        return jugadores.get(indiceJugadorActual).salirCarcelPagando();
    }
    
    public boolean salirCarcelTirando(){
        return jugadores.get(indiceJugadorActual).salirCarcelTirando();
    }
    
    public OperacionesJuego siguientePaso(){
        Jugador jugadorActual = jugadores.get(indiceJugadorActual);
        operaciones = gestor.operacionesPermitidas(jugadorActual, estados);
        
        if(operaciones == OperacionesJuego.PASAR_TURNO){
            pasarTurno();
            siguientePasoCompletado(operaciones);
        }
        
        if(operaciones == OperacionesJuego.AVANZAR){
            avanzaJugador();
            siguientePasoCompletado(operaciones);
        }
        
        return operaciones;
    }
    
    public boolean vender(int ip){
        return jugadores.get(indiceJugadorActual).vender(ip);
    }
    
    public String getRankingUI(){
        String cadena = "";
        ArrayList<Jugador> ranking = ranking();
        
        for(int i=0; i<ranking.size(); i++){
            cadena += ("Jugador: " + ranking.get(i).getNombre() + "\n");
        }
        
        return cadena;
    }
    
    //EXAMEN
    public static void main(String[] args){
        String[] nombres = {"Pepe", "Jose"};
        
        Dado.getInstance().setDebug(true);
        Diario diario = Diario.getInstance();
        
        if(nombres.length <= 2){
            CivitasJuego civitasJuego = new CivitasJuego(nombres);
            
            civitasJuego.avanzaJugador();
            
            System.out.println(civitasJuego.getJugadorActual().toString());
            System.out.println(civitasJuego.getJugadorActual().mostrarVida());
            
            for(int i=0; i<50; i++){
                civitasJuego.pasarTurno();
                civitasJuego.avanzaJugador();
                
                System.out.println(civitasJuego.getJugadorActual().toString());
                System.out.println(civitasJuego.getJugadorActual().mostrarVida());
                
                while(diario.eventosPendientes()){
                    diario.leerEvento();
                }
            }
        }
    }
    
    //Cambias en propiedades el run del proyecto
}

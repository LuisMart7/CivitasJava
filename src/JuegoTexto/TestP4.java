package JuegoTexto;

import civitasp4.CivitasJuego;
import civitasp4.Dado;

//import civitasp4.Casilla;
//import civitasp4.Diario;
//import civitasp4.MazoSorpresas;
//import civitasp4.EstadosJuego;
//import civitasp4.Jugador;
//import civitasp4.Sorpresa;
//import civitasp4.Tablero;
//import civitasp4.TituloPropiedad;
//import civitasp4.GestionesInmobiliarias;
//import civitasp4.OperacionInmobiliaria;
//import civitasp4.OperacionesJuego;
//import civitasp4.Respuestas;
//import civitasp4.SalidasCarcel;
//import java.util.*;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class TestP4{
    public static void main(String[] args){        
        String[] nombres = {"Luis", "Dani", "Juanmi", "Titos"};
        
        CivitasJuego juego = new CivitasJuego(nombres);
        VistaTextual vista = new VistaTextual();
        
        Dado.getInstance().setDebug(false);     
        
        Controlador controlador = new Controlador(juego, vista);
        controlador.juega();
    }
}

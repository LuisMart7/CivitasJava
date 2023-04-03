package JuegoTexto;

import civitasp4.CivitasJuego;

//import civitas.Diario;
//import civitas.SalidasCarcel;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Scanner;
//import civitas.Casilla;
//import civitas.Civitas;
//import civitas.GestionesInmobiliarias;
//import civitas.Jugador;
//import civitas.Respuestas;
//import civitas.TituloPropiedad;

/**
 *
 * @author Luis Martinez Sanchez de Lara
 */
public class Controlador {
    private CivitasJuego juego;
    private VistaTextual vista;
    
    Controlador(CivitasJuego juego, VistaTextual vista){
        this.juego = juego;
        this.vista = vista;
    }
    
    void juega(){
        vista.setCivitasJuego(juego);
        
        while(!juego.finDelJuego()){
            vista.actualizarVista();
            
            vista.pausa();
            
            civitasp4.OperacionesJuego operacion = juego.siguientePaso();
            vista.mostrarSiguienteOperacion(operacion);
            
            if(operacion != civitasp4.OperacionesJuego.PASAR_TURNO){
                vista.mostrarEventos();
            }
            
            assert(!juego.finDelJuego());
            
            civitasp4.Respuestas respuesta;
            
            switch(operacion){
                case COMPRAR:
                    respuesta = vista.comprar();
                    
                    if(respuesta == civitasp4.Respuestas.SI){
                        juego.comprar();
                    }
                    
                    juego.siguientePasoCompletado(operacion);
                break;   
                    
                case GESTIONAR:
                    vista.gestionar();
                    
                    int gestion = vista.getGestion();
                    int propiedad = vista.getPropiedad();
                    
                    civitasp4.GestionesInmobiliarias gestionInmobiliaria = civitasp4.GestionesInmobiliarias.values()[gestion];
                    
                    civitasp4.OperacionInmobiliaria operacionInmobiliaria = new civitasp4.OperacionInmobiliaria(gestionInmobiliaria, propiedad);
                    
                    switch(gestionInmobiliaria){
                        case VENDER:
                            juego.vender(operacionInmobiliaria.getNumPropiedad());
                        break;
                        
                        case HIPOTECAR:
                            juego.hipotecar(operacionInmobiliaria.getNumPropiedad());
                        break;
                        
                        case CANCELAR_HIPOTECA:
                            juego.cancelarHipoteca(operacionInmobiliaria.getNumPropiedad());
                        break;
                        
                        case CONSTRUIR_CASA:
                            juego.construirCasa(operacionInmobiliaria.getNumPropiedad());
                        break;
                        
                        case CONSTRUIR_HOTEL:
                            juego.construirHotel(operacionInmobiliaria.getNumPropiedad());
                        break;
                        
                        default:
                            juego.siguientePasoCompletado(operacion);
                    }
                break;
                
                case SALIR_CARCEL:
                    civitasp4.SalidasCarcel salida = vista.salirCarcel();
                    
                    if(salida == civitasp4.SalidasCarcel.PAGANDO){
                        juego.salirCarcelPagando();
                    }else{
                        juego.salirCarcelTirando();
                    }
                    
                    juego.siguientePasoCompletado(operacion);
                break;
            }
        }
        
        System.out.println(juego.getRankingUI());
        juego.finDelJuego();
    }
}

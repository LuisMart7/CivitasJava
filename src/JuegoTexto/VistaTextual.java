package JuegoTexto;

import civitasp4.Casilla;
import civitasp4.CivitasJuego;
import civitasp4.Diario;
import civitasp4.Jugador;
import civitasp4.OperacionesJuego;
import civitasp4.Respuestas;
import civitasp4.SalidasCarcel;

//import civitas.Civitas;
//import civitas.GestionesInmobiliarias;
//import civitas.TituloPropiedad;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

/**
 * 
 * @author Luis Martinez Sanchez de Lara
 */
class VistaTextual{
    CivitasJuego juegoModel;
    
    int iGestion=-1;
    int iPropiedad=-1;
    
    private final String separador = "=====================";

    private final Scanner in;
  
    VistaTextual () {
      in = new Scanner (System.in);
    }

    void mostrarEstado(String estado) {
      System.out.println (estado);
    }

    void pausa() {
      System.out.print ("Pulsa una tecla");
      in.nextLine();
    }

    int leeEntero (int max, String msg1, String msg2) {
        Boolean ok;
        String cadena;
        int numero = -1;
        do{
            System.out.print (msg1);
            cadena = in.nextLine();
            try {  
                numero = Integer.parseInt(cadena);
                ok = true;
            } catch (NumberFormatException e) { // No se ha introducido un entero
                System.out.println (msg2);
                ok = false;  
            }
            if (ok && (numero < 0 || numero >= max)) {
                System.out.println (msg2);
                ok = false;
            }
        } while (!ok);

        return numero;
    }

    int menu (String titulo, ArrayList<String> lista) {
        String tab = "  ";
        int opcion;
        System.out.println (titulo);
        for (int i = 0; i < lista.size(); i++) {
            System.out.println (tab+i+"-"+lista.get(i));
        }

        opcion = leeEntero(lista.size(),
                              "\n"+tab+"Elige una opciÃƒÂ³n: ",
                              tab+"Valor errÃƒÂ³neo");
        return opcion;
    }

    SalidasCarcel salirCarcel() {
        int opcion = menu ("Elige la forma para intentar salir de la carcel",
          new ArrayList<> (Arrays.asList("Pagando","Tirando el dado")));
        return (SalidasCarcel.values()[opcion]);
    }

    Respuestas comprar() {
        int opcion = menu ("Elige si desea comprar la casilla o no" ,
          new ArrayList<> (Arrays.asList("NO","SI")));
        return (Respuestas.values()[opcion]);
    }

    void gestionar () {
        ArrayList<String> propiedades = juegoModel.getJugadorActual().getPropiedadesUI();

        if(propiedades.size() > 0){
            iGestion = menu ("Elige una gestiÃ³n inmboliaria",
              new ArrayList<>(Arrays.asList("VENDER","HIPOTECAR","CANCELAR_HIPOTECA","CONSTRUIR_CASA",
              "CONSTRUIR_HOTEL","TERMINAR")));
        }else{
            iGestion = 5;
        }

        //Jugador actual = juegoModel.getJugadorActual();

        if(iGestion == 5){
            System.out.println("El jugador no tiene propiedades");
        }
        /*
        ArrayList<String> propiedades = new ArrayList<>();
        String cadena = actual.toString();

        String[] partes = cadena.split("\\\n");
        for(int i=2; i<partes.length; i++){
            propiedades.add(partes[i]);
        }
        */
        else{
            iPropiedad = menu("Elige una propiedad",propiedades);
        }
    }
  
    public int getGestion(){return iGestion;}
    public int getPropiedad(){return iPropiedad;}

    void mostrarSiguienteOperacion(OperacionesJuego operacion) {
        System.out.println(operacion.toString());
    }

    void mostrarEventos() {
        Diario diario = Diario.getInstance();
        while(diario.eventosPendientes()){
            System.out.println(diario.leerEvento());
        }
    }
  
    public void setCivitasJuego(CivitasJuego civitas){ 
        juegoModel=civitas;
        this.actualizarVista();
    }
  
    void actualizarVista(){
        Jugador actual = juegoModel.getJugadorActual();
        System.out.println(actual.toString());
        //System.out.println(juegoModel.infoJugadorTexto());

        Casilla casilla = juegoModel.getCasillaActual();
        System.out.println(casilla.toString());
    }
  
    public void mostrarMensaje(String mensaje){
        System.out.println(mensaje);
    }
}


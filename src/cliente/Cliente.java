package cliente;

import interfaz.*;
import clases.*;

import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.Arrays;
import java.util.Scanner;


public class Cliente {
	private static final String IP = "localhost"; // Puedes cambiar a localhost
	private static final int PUERTO = 5000; //Si cambias aquí el puerto, recuerda cambiarlo en el servidor
	
    public static void main(String[] args) throws RemoteException, NotBoundException {
    	
    	String cadenaPeliculas= "";
    	int filmSeleccionada;
    	
    	String film = "";
    	String mensajeServidor = "";
    	String peliculatransmitida = "";
    	boolean estoyViendoPeli = false;
    	int eleccion;
    	
    	Scanner sc = new Scanner(System.in);
    	
    	
    	Registry registry = LocateRegistry.getRegistry(IP, PUERTO);
        Interfaz interfaz = (Interfaz) registry.lookup("Sistema de Video"); //Buscar en el registro...
    	
       

        cadenaPeliculas  = interfaz.listarPeliculas();
       
        do {
        	System.out.println(cadenaPeliculas);
            
            filmSeleccionada = Integer.parseInt(sc.nextLine()); 
            film = interfaz.seleccionPelicula(filmSeleccionada);
            mensajeServidor= interfaz.avisarDisponibilidad(filmSeleccionada);
            System.out.println(mensajeServidor);
            if(interfaz.confirmacionPelicula(filmSeleccionada)) {
            	peliculatransmitida = interfaz.transmision(filmSeleccionada);
            	estoyViendoPeli = true;
            }
            System.out.println(peliculatransmitida);
            
            
            System.out.println("\n\n························\n"
            		+ "--Presione -1 para salir de la pelicula"
            		+ "\n o Presiona un Numero Cualquera para cargar la lista");
            eleccion = Integer.parseInt(sc.nextLine());
           
            
        }while(eleccion != -1);
        	if(estoyViendoPeli) {
        	interfaz.salirPelicula(filmSeleccionada);
        	}
        	System.out.println("Gracias por Visitarnos");
        
    	
   
    }
}
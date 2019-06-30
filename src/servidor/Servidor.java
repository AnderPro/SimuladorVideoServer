package servidor;

import interfaz.*;
import clases.*;

import java.io.IOException;
import java.rmi.AlreadyBoundException;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.rmi.server.UnicastRemoteObject;
import java.util.ArrayList;

import java.awt.Desktop;
import java.io.IOException;
import java.net.URI;
import java.net.URISyntaxException;


public class Servidor {
	private static final int PUERTO = 5000; //Si cambias aquí el puerto, recuerda cambiarlo en el cliente
	private static int slotsporPelicula = 0; 
	private static int max= 3;
	private static Runtime rt = null;
	
	private static final Pelicula[] listaPeliculas = {
			new Pelicula("La chica de Alado",slotsporPelicula)
			,new Pelicula("Rapidos Y Furiosos",slotsporPelicula)
			,new Pelicula("Matrix",slotsporPelicula)};
	
	//private static final String [] peliculas = {"La chica de Alado","Rapidos Y Furiosos","Matrix3"};
	
	
	
	public static void main(String[] args) throws RemoteException, AlreadyBoundException {
		
		
		
        Remote remote = UnicastRemoteObject.exportObject(new Interfaz(){
        	/*
				Sobrescribir opcionalmente los métodos que escribimos en la interfaz
        	*/
        	       	      	
                                  
            @Override
            public String listarPeliculas() throws RemoteException{
            	
            	int indice=0;
            	String  cadenaPeliculas= "";
            	for (Pelicula pelicula : listaPeliculas) {
            		
            		cadenaPeliculas+= indice +"->"+ pelicula.getTitulo() +"\n";
            		indice++;
				}
            	return cadenaPeliculas + "\nIngrese el número de pelicula que desea ver...\n";
            };
            
            @Override
            public String seleccionPelicula (int indice) throws RemoteException{
            	
            	String salida="";
            	          	
            	Pelicula film = listaPeliculas[indice];
            	
            	salida+="SELECCION: "+ film.getTitulo() ;
            	 	
            	return salida;
            
            	
            };
            
            
           
            @Override
            public void salirPelicula(int indice) throws RemoteException{
            	//verificar(indice);
            	listaPeliculas[indice].disminuirClientesPelicula();	
            	           	
            };
            
            @Override
            public String avisarDisponibilidad(int indice) throws RemoteException{
            	String salida = "";
            	if(confirmacionPelicula(indice)){
            		//salida+="Puede ver la pelicula" + listaPeliculas[indice].getTitulo();
            		salida+="Puede ver la pelicula";
            		
            	}else {
            		salida = "No esta disponible por el momento, hay muchos clientes en la pelicula";
            	}
            	return salida;
            };
            
            
            
            @Override
            public boolean confirmacionPelicula(int indice) throws RemoteException{
            	boolean bandera = false;
        		if(listaPeliculas[indice].getNumeroClientesPelicula() < max) {
        			bandera = true;
        		}else {
        			System.out.println("Esta copado con la pelicula: "+ listaPeliculas[indice].getTitulo());
        			bandera = false;
        		}
        		return bandera;
            			
            };
            @Override
            public String transmision(int indice) throws RemoteException{
            	String cadena = ""; 
            	listaPeliculas[indice].setNumeroClientesPelicula();
            	
            	cadena += "Se esta reproduciendo la pelicula: "+ listaPeliculas[indice].getTitulo() 
            			+"\n Nº de usuarios en linea:"+ listaPeliculas[indice].getNumeroClientesPelicula();
            	
            	Runtime rt = Runtime.getRuntime();
            	
            	//*** NOTA: se tiene que abrir tres vlc y configurar el directorio de la pelicula, cada una con diferente puerto y URL.
            	// OJO cambiar 192.168.1.4 por la Ip del Servidor
            	try {
            		switch (indice) {
    				case 0:	
    					// abrir el firefox y conectarse con el servidor de vlc
    					rt.exec("/usr/bin/firefox 192.168.1.4:8080/pelicula1");
    					break;
    					
    				case 1:	
    					rt.exec("/usr/bin/firefox 192.168.1.4:8081/pelicula2");
        				break;
        				
    				case 2:
    					rt.exec("/usr/bin/firefox 192.168.1.4:8080/pelicula3");
    					break;

    				default:
    					break;
    				}
					
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
            	return cadena;
            }
  
        }, 0);
        
        
        
        Registry registry = LocateRegistry.createRegistry(PUERTO);
       	System.out.println("Servidor escuchando en el puerto " + String.valueOf(PUERTO));
        registry.bind("Sistema de Video", remote); // Registrar Peliculas
    }
	/*
	public static boolean verificar(int indice) {
		boolean bandera = false;
		if(listaPeliculas[indice].getNumeroClientesPelicula()==max) {
			
			System.out.println("Esta copado con la pelicula: "+ listaPeliculas[indice].getTitulo());
			bandera=false;
			
			//return false;
		}
		else if(listaPeliculas[indice].getNumeroClientesPelicula()<max) {
			listaPeliculas[indice].setNumeroClientesPelicula();
			bandera = true;
			//return true;
		}
		return bandera;
		
	}*/
	
	
	
	
}


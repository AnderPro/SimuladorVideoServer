package interfaz;


import java.rmi.Remote;
import java.rmi.RemoteException;

/*
	Declarar firma de métodos que serán sobrescritos
*/
public interface Interfaz extends Remote {
    
	String listarPeliculas() throws RemoteException;
	String seleccionPelicula(int indice) throws RemoteException;
	String avisarDisponibilidad(int indice) throws RemoteException;
	String transmision(int indice) throws RemoteException;
	void salirPelicula(int indice)throws RemoteException;
	
	boolean confirmacionPelicula(int indice) throws RemoteException;
	//String puederVer()throws RemoteException;
	//String NopuedeVer()throws RemoteException;
	

}
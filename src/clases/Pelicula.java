package clases;

public class Pelicula {
	private String titulo;
	private int numeroClientesPelicula;
	
	
	
	public Pelicula(String titulo, int numeroClientesPelicula) {
		super();
		this.titulo = titulo;
		this.numeroClientesPelicula = numeroClientesPelicula;
	}
	public String getTitulo() {
		return titulo;
	}
	public void setTitulo(String titulo) {
		this.titulo = titulo;
	}
	public int getNumeroClientesPelicula() {
		return numeroClientesPelicula;
	}
	public void setNumeroClientesPelicula() {
		this.numeroClientesPelicula += 1;
	}
	public void disminuirClientesPelicula() {
		
		this.numeroClientesPelicula -=1;
	}
	

}

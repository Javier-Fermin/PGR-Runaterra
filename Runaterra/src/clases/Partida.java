package clases;

import java.time.LocalDate;

import utils.Utils;

public class Partida {
	private String idPartida, nickWinner, nickLosser;
	private LocalDate fechaPartida;
	
	//Setters & Getters
	public LocalDate getFechaPartida() {
		return fechaPartida;
	}
	public String getNickWinner() {
		return nickWinner;
	}
	public void setNickWinner(String nickWinner) {
		this.nickWinner = nickWinner;
	}
	public String getNickLosser() {
		return nickLosser;
	}
	public void setNickLosser(String nickLosser) {
		this.nickLosser = nickLosser;
	}
	public void setFechaPartida(LocalDate fechaPartida) {
		this.fechaPartida = fechaPartida;
	}
	public String getIdPartida() {
		return idPartida;
	}
	public void setIdPartida(String idPartida) {
		this.idPartida = idPartida;
	}
	
	//METHODS
	public void setDatos() {
		// TODO Auto-generated method stub
		System.out.println("Introduzca el id de la partida");
		this.idPartida=Utils.introducirCadena();
		System.out.println("Intoruzca la fecha de la partida (AAAA/MM/DD)");
		this.fechaPartida=Utils.leerFechaAMD();
		System.out.println("Introduzca el nickname del ganador");
		this.nickWinner=Utils.introducirCadena();
		System.out.println("Introduzca el nickname del perdedor");
		this.nickLosser=Utils.introducirCadena();
	}
	
	public void mostrar() {
		// TODO Auto-generated method stub
		System.out.println("ID de partida: "+this.idPartida);
		System.out.println("Ganador: "+this.nickWinner);
		System.out.println("Perdedor: "+this.nickLosser);
		System.out.println("Fecha de la partida: "+this.fechaPartida.toString());
	}
	
	
}

package clases;

import java.time.LocalDate;

public class Partida {
	private String idPartida;
	private LocalDate fechaPartida;
	private Jugador ganador;
	private Jugador perdedor;
	public Jugador getPerdedor() {
		return perdedor;
	}
	public void setPerdedor(Jugador perdedor) {
		this.perdedor = perdedor;
	}
	public Jugador getGanador() {
		return ganador;
	}
	public void setGanador(Jugador ganador) {
		this.ganador = ganador;
	}
	public LocalDate getFechaPartida() {
		return fechaPartida;
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
	
	
}

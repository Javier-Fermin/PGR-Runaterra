package clases;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import utils.Utils;

public class Jugador implements Serializable {
	private String nickname;
	private Map<Integer, Integer> mazo = new HashMap<Integer, Integer>();
	private int victorias;
	private Map<String, Partida> partidas = new HashMap<String, Partida>();
//Constructores
	public Jugador() {
		super();
	}
	public Jugador(String nickname, Map<Integer, Integer> mazo, int victorias, Map<String, Partida> partidas) {
		super();
		this.nickname = nickname;
		this.mazo = mazo;
		this.victorias = victorias;
		this.partidas = partidas;
	}
	// Getters y Setters
	public String getNickname() {
		return nickname;
	}
	public void setNickname(String nickname) {
		this.nickname = nickname;
	}
	public Map<Integer, Integer> getMazo() {
		return mazo;
	}
	public void setMazo(Map<Integer, Integer> mazo) {
		this.mazo = mazo;
	}
	public int getVictorias() {
		return victorias;
	}
	public void setVictorias(int victorias) {
		this.victorias = victorias;
	}
	public Map<String, Partida> getPartidas() {
		return partidas;
	}
	public void setPartidas(Map<String, Partida> partidas) {
		this.partidas = partidas;
	}
	// toString()
	@Override
	public String toString() {
		return "Nickname = " + this.nickname + ", Mazo de cartas = " + this.mazo + ", Nº de victorias=" + this.victorias
				+ ", Partidas jugadas = " + this.partidas;
	}
	// setDatos()
	public void setDatos() {
		System.out.println("Introduce el nickname del jugador:");
		this.nickname = Utils.introducirCadena();
		System.out.println("Jugador con nick " + this.nickname + " añadido correctamente\n");
	}
	// calcularVictorias()
	public void calcularVictorias() {
		int partidasGanadas = 0;
		for (Partida p : partidas.values()) {
			if (p.getNickWinner().equals(this.nickname))
				partidasGanadas++;
		}
		this.victorias = partidasGanadas;
	}
}
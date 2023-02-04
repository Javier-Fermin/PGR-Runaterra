package clases;

import java.util.ArrayList;

public class Hechizo extends Carta {
	private String tipo;

	public Hechizo(String tipo) {
		super();
		this.tipo = tipo;
	}
	
	public Hechizo() {
		super();
	}

	public String getTipo() {
		return tipo;
	}

	public void setTipo(String tipo) {
		this.tipo = tipo;
	}

	@Override
	public String toString() {
		return "Hechizo [tipo=" + tipo + "]";
	}

}

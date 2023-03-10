package clases;

import utils.Utils;

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
		super.toString();
		return "Hechizo [tipo=" + tipo + "]";
	}

	public void setDatos(int Id) {
	    super.setDatos(Id);
	    System.out.println("Introduce el tipo de hechizo: ");
	    this.tipo = Utils.introducirCadena();
	}
	
	public void mostrar() {
		super.mostrar();
		System.out.println("El tipo de hechizo es: "+this.tipo+"\n");
	}
}

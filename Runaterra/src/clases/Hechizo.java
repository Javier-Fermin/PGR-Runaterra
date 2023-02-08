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
		return "Hechizo [descripcion=" + descripcion + ", id=" + id + ", tipo=" + tipo + ", mana=" + mana + ", danno=" + danno + ", efectos=" + efectos + "]";
	}

	public void setDatos(int Id) {
		//Hacemos un set datos del tipo ya que es algo exclusivo de hechizos, junto al setDatos general de Carta
	    super.setDatos(Id);
	    System.out.println("Introduce el tipo de hechizo: ");
	    this.tipo = Utils.introducirCadena();
	}
}

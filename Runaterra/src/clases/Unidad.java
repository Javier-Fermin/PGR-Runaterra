package clases;

import utils.Utils;

public class Unidad extends Carta {
	private boolean esCampeon;

	public Unidad(boolean esCampeon) {
		super();
		this.esCampeon = esCampeon;
	}

	public Unidad() {
		super();
	}

	public boolean isEsCampeon() {
		return esCampeon;
	}

	public void setEsCampeon(boolean esCampeon) {
		this.esCampeon = esCampeon;
	}

	@Override
	public String toString() {
		return "Unidad [descripcion=" + descripcion + ", id=" + id + ", Campeon=" + esCampeon + ", mana=" + mana + ", danno=" + danno
				+ ", efectos=" + efectos + "]";
	}

	public void setDatos(int Id) {
		//Hacemos un set datos del campeon ya que es algo exclusivo de unidades, junto al setDatos general de Carta
		super.setDatos(Id);
		System.out.println("¿La unidad es campeón? (Sí / No)");
		this.esCampeon = Utils.esBoolean();
	}
}

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
		return "Unidad [esCampeon=" + esCampeon + "]";
	}

	public void setDatos(int Id) {
		super.setDatos(Id);
		System.out.println("¿La unidad es campeón? (Sí / No)");
		this.esCampeon = Utils.esBoolean();
	}
}

package clases;

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
	

}

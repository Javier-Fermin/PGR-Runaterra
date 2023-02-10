package clases;

import java.io.Serializable;
import java.util.ArrayList;
import utils.Utils;

public class Carta implements Serializable {
	protected int id;
	protected int mana;
	protected int danno;
	protected String descripcion;
	protected ArrayList<String> efectos = new ArrayList<>();
	
	public Carta() {
		
	}
	
	public Carta(int id, int mana, int danno, String descripcion, ArrayList<String> efectos) {
		super();
		this.id = id;
		this.mana = mana;
		this.danno = danno;
		this.descripcion = descripcion;
		this.efectos = efectos;
	}

	//Puede existir una carta sin efectos
	public Carta(int id, int mana, int danno, String descripcion) {
		super();
		this.id = id;
		this.mana = mana;
		this.danno = danno;
		this.descripcion = descripcion;
	}

	public int getId() {
		return id;
	}

	public void setId(int id) {
		this.id = id;
	}

	public int getMana() {
		return mana;
	}

	public void setMana(int mana) {
		this.mana = mana;
	}

	public int getDanno() {
		return danno;
	}

	public void setDanno(int danno) {
		this.danno = danno;
	}

	public String getDescripcion() {
		return descripcion;
	}

	public void setDescripcion(String descripcion) {
		this.descripcion = descripcion;
	}

	public ArrayList<String> getEfectos() {
		return efectos;
	}

	public void setEfectos(ArrayList<String> efectos) {
		this.efectos = efectos;
	}

	public void setDatos() {
		System.out.println("");
	}
	
	
	public void setDatos(int Id) {
		this.id = Id;
		System.out.println("Introduce el mana de la carta: ");
		this.mana = Utils.leerInt();
		System.out.println("Introduce el dano de la carta: ");
		this.danno = Utils.leerInt();
		System.out.println("Introduce la descripcion de la carta: ");
		this.descripcion = Utils.introducirCadena();
		System.out.println("¿Desea introducir algún efecto?");
		if(Utils.esBoolean()) {
			boolean seguir = true;
			do {
				System.out.println("Escriba el nombre del efecto a continuación:");
				//Se añade el efecto a la coleccion de posibles efectos
				efectos.add(Utils.leerString());
				System.out.println("¿Desea añadir otro efecto?");
				if (!Utils.esBoolean()) seguir = false;
			} while (seguir != false);
		}
	}

	public void mostrar() {
		System.out.println("ID de la carta: "+this.id);
		System.out.println("Coste de mana de la carta: "+this.mana);
		System.out.println("Daño de la carta: "+this.danno);
		System.out.println("Descripcion de la carta: "+this.descripcion);
	}
	@Override
	public String toString() {
		return "Carta [id=" + id + ", mana=" + mana + ", danno=" + danno + ", descripcion=" + descripcion + ", efectos="
				+ efectos + "]";
	}
}

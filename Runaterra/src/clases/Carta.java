package clases;

import java.util.ArrayList;

import utils.Utils;

public class Carta {
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
		//
		//Setear datos para Unidad y Hechizo
		//
		
		/*if (Carta instanceof Unidad) {
			
		}*/
	}

	@Override
	public String toString() {
		return "Carta [id=" + id + ", mana=" + mana + ", danno=" + danno + ", descripcion=" + descripcion + ", efectos="
				+ efectos + "]";
	}
}

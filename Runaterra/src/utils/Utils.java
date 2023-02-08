package utils;

import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.ArrayList;

public class Utils {
	public static boolean esBoolean() {
		String respu;
		do {
			respu = leerString().toLowerCase();
		} while (!respu.equals("0") && !respu.equals("1") && !respu.equals("si") && !respu.equals("no")
				&& !respu.equals("s") && !respu.equals("n") && !respu.equals("true") && !respu.equals("false"));
		if (respu.equals("1") || respu.equals("si") || respu.equals("s") || respu.equals("true")) {
			return true;
		} else {
			return false;
		}
	}
	public static int leerInt(int min, int max) {
		int num = min;
		boolean error;

		do {
			error = false;

			try {
				num = Integer.parseInt(leerString());
			} catch (NumberFormatException exc) {
				System.err.println("Error; non-numeric value. Please try again:");
				error = true;
			}

			if (num < min || num > max) {
				System.err.println("Error; nº out of range. Please enter a nº between " + min + " and " + max + ":");
				error = true;
			}
		} while (error);

		return num;
	}
	public static String leerString(int x) {
		String cadena = null;
		boolean error;

		do {
			error = false;
			cadena = leerString();

			if (cadena.length() > x) {
				System.err.println("Error; longitud máxima superada. Introduzca de nuevo:");
				error = true;
			}
		} while (error);

		return cadena;
	}
	public static int leerInt(String msj, int min, int max) {
		int num = 0;
		boolean error;

		System.out.println(msj);

		do {
			error = false;
			try {
				num = Integer.parseInt(leerString());

			} catch (NumberFormatException exc) {
				System.err.println("Error; valor no numérico. Inténtalo de nuevo:");
				error = true;
				num = min;
			}

			if (num < min || num > max) {
				System.err.println("Error; nº fuera de rango. Introduce un nº entre " + min + " y " + max + ":");
				error = true;
			}
		} while (error);

		return num;
	}

	public static String leerString() {
		String cadena = null;
		boolean error;
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(streamReader);

		do {
			error = false;

			try {
				cadena = reader.readLine();
			} catch (IOException exc) {
				System.err.println("Error de entrada/salida. Inténtalo de nuevo:");
				error = true;
			}
		} while (error);

		return cadena;
	}

	public static String leerString(String msj) {
		String cadena = null;
		boolean error;
		InputStreamReader streamReader = new InputStreamReader(System.in);
		BufferedReader reader = new BufferedReader(streamReader);

		System.out.println(msj);

		do {
			error = false;

			try {
				cadena = reader.readLine();
			} catch (IOException exc) {
				System.err.println("Error de entrada/salida. Inténtalo de nuevo:");
				error = true;
			}
		} while (error);

		return cadena;
	}

	public static boolean esBoolean(String message) {
		String respu;
		System.out.println(message);
		do {
			respu = leerString().toLowerCase();
		} while (!respu.equals("0") && !respu.equals("1") && !respu.equals("si") && !respu.equals("no")
				&& !respu.equals("s") && !respu.equals("n") && !respu.equals("true") && !respu.equals("false"));
		if (respu.equals("1") || respu.equals("si") || respu.equals("s") || respu.equals("true")) {
			return true;
		} else {
			return false;
		}
	}

	public static String fechaToString(LocalDate fecha) {
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String wfecha;

		wfecha = fecha.format(formateador);

		return wfecha;
	}

	public static LocalDate leerFechaDMA() {
		boolean error;
		LocalDate date = null;
		String dateString;
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		do {
			error = false;
			dateString = introducirCadena();
			try {
				date = LocalDate.parse(dateString, formateador);
			} catch (DateTimeParseException e) {
				System.out.println("Error, introduce una fecha en formato dd/mm/aaaa ");
				error = true;
			}
		} while (error);
		return date;
	}

	public static LocalDate leerFechaAMD() {
		boolean error;
		LocalDate date = null;
		String dateString;
		DateTimeFormatter formateador = DateTimeFormatter.ofPattern("yyyy/MM/dd");
		do {
			error = false;
			dateString = introducirCadena();
			try {
				date = LocalDate.parse(dateString, formateador);
			} catch (DateTimeParseException e) {
				System.out.println("Error, uuuu/hh/ee ");
				error = true;
			}
		} while (error);
		return date;
	}

	public static char leerCharSiNo() {
		char letra = ' ';
		String cadena;
		boolean error;
		do {
			error = false;
			cadena = introducirCadena();
			if (cadena.length() != 1) {
				System.out.println("Error, introduce un �nico caracter: ");
				error = true;
			} else {
				letra = cadena.charAt(0);
				letra = Character.toUpperCase(letra);
				if (letra != 'S' && letra != 'N') {
					System.out.println("Error, la opci�n introducida no es correcta, introduce S o N");
					error = true;
				}
			}
		} while (error);

		return letra;
	}

	public static char leerChar(ArrayList<Character> characters) {
		char letra = ' ';
		String cadena;
		boolean error;
		do {
			error = false;
			cadena = introducirCadena();
			if (cadena.length() != 1) {
				System.out.println("Error, introduce un �nico caracter: ");
				error = true;
			} else {
				for (int i = 0; i < characters.size(); i++) {
					if (letra != characters.get(i)) {
						error = true;
						i = characters.size();
					}
				}
			}
		} while (error);
		letra = cadena.charAt(0);
		return letra;
	}

	public static boolean confirmacion(String msg) {
		char letra = ' ';
		String cadena;
		boolean error, aux;
		do {
			error = false;
			System.out.println(msg);
			cadena = introducirCadena();
			if (cadena.length() != 1) {
				System.out.println("Error, introduce un �nico caracter: ");
				error = true;
			} else {
				letra = cadena.charAt(0);
				letra = Character.toUpperCase(letra);
				if (letra != 'S' && letra != 'N') {
					System.out.println("Error, la opci�n introducida no es correcta, introduce S o N");
					error = true;
				}
			}
		} while (error);
		if (letra == 'S') {
			aux = true;
		} else {
			aux = false;
		}
		return aux;
	}

	public static float leerFloatMinMax(float min, float max) {
		float num = 0;
		boolean error;
		do {
			error = false;
			try {
				num = Float.parseFloat(introducirCadena());

			} catch (NumberFormatException e) {
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error = true;
				num = min;
			}
			if (num < min || num > max) {
				System.out.println("N� fuera de rango, introduce n� entre " + min + " y " + max + ": ");
				error = true;
			}
		} while (error);
		return num;
	}

	public static float leerFloat() {
		float num = 0;
		boolean error;
		do {
			error = false;
			try {
				num = Float.parseFloat(introducirCadena());
			} catch (NumberFormatException e) {
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error = true;
			}
		} while (error);
		return num;
	}

	public static int leerIntMinMax(int min, int max) {
		int num = 0;
		boolean error;
		do {
			error = false;
			try {
				num = Integer.parseInt(introducirCadena());

			} catch (NumberFormatException e) {
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error = true;
				num = min;
			}
			if (num < min || num > max) {
				System.out.println("N� fuera de rango, introduce n� entre " + min + " y " + max + ": ");
				error = true;
			}
		} while (error);
		return num;
	}

	public static int leerInt() {
		int num = 0;
		boolean error;
		do {
			error = false;
			try {
				num = Integer.parseInt(introducirCadena());
			} catch (NumberFormatException e) {
				System.out.println("Valor no num�rico. Introduce de nuevo:");
				error = true;
			}
		} while (error);
		return num;
	}

	public static String introducirCadena() {
		String cadena = "";
		boolean error;
		InputStreamReader entrada = new InputStreamReader(System.in);
		BufferedReader teclado = new BufferedReader(entrada);
		do {
			error = false;
			try {
				cadena = teclado.readLine();
			} catch (IOException e) {
				System.out.println("Error en la entrada de datos");
				error = true;
			}
		} while (error);
		return cadena;
	}

	// Devuelve el n�mero de objetos de un fichero
	public static int calculoFichero(File fich) {
		int cont = 0;
		if (fich.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {
				fis = new FileInputStream(fich);
				ois = new ObjectInputStream(fis);

				Object aux = ois.readObject();

				while (aux != null) {
					cont++;
					aux = ois.readObject();
				}

			} catch (EOFException e1) {
				

			} catch (Exception e2) {
				e2.printStackTrace();
			}

			try {
				ois.close();
				fis.close();
			} catch (IOException e) {
				System.out.println("Error al cerrar los flujos");

			}
		}
		return cont;
	}
}
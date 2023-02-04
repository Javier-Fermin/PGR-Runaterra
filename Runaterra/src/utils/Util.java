package utils;

import java.io.InputStreamReader;
import java.io.ObjectInputStream;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.io.BufferedReader;
import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

public class Util {

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
				System.err.println("Error al cerrar el fichero");
			}
		}
		return cont;
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

	public static int leerInt() {
		int num = 0;
		boolean error;

		do {
			error = false;

			try {
				num = Integer.parseInt(leerString());
			} catch (NumberFormatException exc) {
				System.err.println("Error; valor no numérico. Inténtalo de nuevo:");
				error = true;
			}
		} while (error);

		return num;
	}

	public static int leerInt(int min, int max) {
		int num = min;
		boolean error;

		do {
			error = false;

			try {
				num = Integer.parseInt(leerString());
			} catch (NumberFormatException exc) {
				System.err.println("Error; valor no numérico. Inténtalo de nuevo:");
				error = true;
			}

			if (num < min || num > max) {
				System.err.println("Error; nº fuera de rango. Introduce un nº entre " + min + " y " + max + ":");
				error = true;
			}
		} while (error);

		return num;
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

	public static float leerFloat() {
		float num = 0;
		boolean error;

		do {
			error = false;

			try {
				num = Float.parseFloat(leerString());
			} catch (NumberFormatException exc) {
				System.err.println("Error; valor no numérico. Inténtalo de nuevo:");
				error = true;
			}
		} while (error);

		return num;
	}

	public static float leerFloat(String msj) {
		float num = 0;
		boolean error;

		System.out.println(msj);

		do {
			error = false;

			try {
				num = Float.parseFloat(leerString());
			} catch (NumberFormatException exc) {
				System.err.println("Error; valor no numérico. Inténtalo de nuevo:");
				error = true;
			}
		} while (error);

		return num;
	}

	public static float leerFloat(float min, float max) {
		float num = min;
		boolean error;

		do {
			error = false;

			try {
				num = Float.parseFloat(leerString());
			} catch (NumberFormatException exc) {
				System.err.println("Error; valor no numérico. Inténtalo de nuevo:");
				error = true;
			}

			if (num < min || num > max) {
				System.err.println("Error; nº fuera de rango. Introduce un nº entre " + min + " y " + max + ":");
				error = true;
			}
		} while (error);

		return num;
	}

	public static char leerChar() {
		char letra = ' ';
		String cadena;
		boolean error;

		do {
			error = false;
			cadena = leerString();

			if (cadena.length() != 1) {
				System.err.println("Error; introduce solo un carácter:");
				error = true;
			}
		} while (error);

		letra = cadena.charAt(0);

		return letra;
	}

	public static char leerChar(char opc1, char opc2) {
		char letra = ' ';
		String cadena;
		boolean error;

		do {
			error = false;
			cadena = leerString();

			if (cadena.length() != 1) {
				System.err.println("Error; introduce solo un carácter:");
				error = true;
			} else {
				letra = cadena.charAt(0);
				letra = Character.toUpperCase(letra);

				if (letra != opc1 && letra != opc2) {
					System.err.println(
							"Error; la opción introducida no es válida. Introduce " + opc1 + " o " + opc2 + ":");
					error = true;
				}
			}
		} while (error);

		return letra;
	}

	public static char leerArrayChar(char chars[]) {
		int i;
		boolean error = false;
		String letra;
		char aux = 0;

		do {
			error = false;
			letra = leerString();

			if (letra.length() != 1) {
				System.err.println("Error; introduce solo un carácter:");
				error = true;
			} else {
				aux = letra.charAt(0);
				for (i = 0; i < chars.length; i++) {
					if (Character.toUpperCase(chars[i]) == Character.toUpperCase(aux)) {
						break;
					}
				}
				if (i == chars.length) {
					error = true;
					System.err.println("Error; la opción introducida no es válida. Inténtalo de nuevo:");
				}
			}
		} while (error);

		return aux;
	}

	public static String fechaToString(LocalDate date) {
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");
		String wDate;

		wDate = date.format(formatter);

		return wDate;
	}

	public static LocalDate leerFechaDMA() {
		boolean error;
		LocalDate date = null;
		String dateToString;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy");

		do {
			error = false;
			dateToString = leerString();

			try {
				date = LocalDate.parse(dateToString, formatter);
			} catch (DateTimeParseException exc) {
				System.err.println("Error; introduce la fecha en formato dd/mm/aaaa:");
				error = true;
			}
		} while (error);

		return date;
	}

	public static LocalDate leerFechaAMD() {
		boolean error;
		LocalDate date = null;
		String dateToString;
		DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy/MM/dd");

		do {
			error = false;
			dateToString = leerString();

			try {
				date = LocalDate.parse(dateToString, formatter);
			} catch (DateTimeParseException exc) {
				System.err.println("Error; introduce la fecha en formato aaaa/MM/dd:");
				error = true;
			}
		} while (error);

		return date;
	}

	public static boolean esBoolean() {
		String respuesta;

		do {
			respuesta = leerString().toLowerCase();
		} while (!respuesta.equals("0") && !respuesta.equals("1") && !respuesta.equals("si") && !respuesta.equals("no")
				&& !respuesta.equals("s") && !respuesta.equals("n"));
		if (respuesta.equals("1") || respuesta.equals("si") || respuesta.equals("s"))
			return true;
		else
			return false;
	}
}
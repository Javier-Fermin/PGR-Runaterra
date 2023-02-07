package main;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.Collection;

import clases.Carta;
import clases.Hechizo;
import clases.Jugador;
import clases.Unidad;
import utils.MyObjectOutputStream;
import utils.Utils;

public class Main {

	public static void main(String[] args) {
		// TODO Auto-generated method stub
		int opc;
		File fichJugadores = new File("jugadores.dat");
		File fichCartas = new File("cartas.dat");
		boolean salir = false;
		do {
			System.out.println("Elige una opción:\n" + "\t1.  Añadir un jugador\n" + "\t2.  Modificar un jugador\n"
					+ "\t3.  Listar jugadores\n" + "\t4.  Eliminar un jugador\n" + "\t5.  Añadir una partida\n"
					+ "\t6.  Listar partidas\n" + "\t7.  Añadir una carta\n" + "\t8.  Modificar una carta\n"
					+ "\t9.  Listar carta/s\n" + "\t10. Eliminar una carta\n" + "\t11. Salir\n");
			opc = Utils.leerInt();
			switch (opc) {
			case 1:
				annadirJugador(fichJugadores, fichCartas);
				break;
			case 2:
				modificarJugador(fichJugadores, fichCartas);
				break;
			case 3:
				listarJugadores(fichJugadores);
				break;
			case 4:
				eliminarJugador(fichJugadores);
				break;
			case 5:

				break;
			case 6:
				break;
			case 7:
				break;
			case 8:
				break;
			case 9:
				break;
			case 10:
				break;
			case 11:
				salir = Utils.confirmacion("Desea salir?\nS para Si\nN para No");
				break;
			}
		} while (!salir);
	}

	private static void annadirJugador(File fichJugadores, File fichCartas) {
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		Jugador jug = new Jugador();
		boolean seguir = false;
		if (fichJugadores.exists()) {
			try {
				fos = new FileOutputStream(fichJugadores, true);
				oos = new MyObjectOutputStream(fos);
				jug.setDatos();
				oos.writeObject(jug);
				oos.writeObject(jug);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				fos = new FileOutputStream(fichJugadores);
				oos = new ObjectOutputStream(fos);
				jug.setDatos();
				oos.writeObject(jug);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		if (Utils.calculoFichero(fichCartas) != 0) {
			int annadirMazo = 0;
			boolean primeraVuelta = true;
			int tamMazo = 0;
			for (int e : jug.getMazo().values()) {
				tamMazo += e;
			}
			do {
				if (primeraVuelta)
					seguir = Utils.confirmacion("¿Quieres añadir una carta a tu mazo? (S SI / N NO):");
				if (annadirMazo == 1) {
					System.out.println("Introduce el ID de la carta a añadir:");
					int wId = Utils.leerInt();
					Carta aux = new Carta();
					FileInputStream fis = null;
					ObjectInputStream ois = null;
					try {
						fis = new FileInputStream(fichCartas);
						ois = new ObjectInputStream(fis);
						aux = (Carta) ois.readObject();
						while (aux != null) {
							if (aux.getId() == wId) {
								if (jug.getMazo().containsKey(wId))
									jug.getMazo().replace(wId, jug.getMazo().get(wId) + 1);
								else
									jug.getMazo().put(wId, 1);
							}
						}
						tamMazo++;
					} catch (FileNotFoundException e1) {
						e1.printStackTrace();
					} catch (EOFException e) {
						e.printStackTrace();
					} catch (ClassNotFoundException e) {
						e.printStackTrace();
					} catch (IOException e) {
						e.printStackTrace();
					}
				} else
					System.out.println("Has decidido no añadir ninguna carta a tu mazo\n");
				primeraVuelta = false;
				if (tamMazo == 40)
					System.out.println("Ya has metido el máximo de cartas en tuy mazo: 40");
				else
					seguir = Utils.confirmacion("¿Quieres añadir otra carta a tu mazo? (S SI / N NO):");
			} while (seguir && tamMazo > 40);
		}
		try {
			oos.close();
			fos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void modificarJugador(File fichJugadores, File fichCartas) {
		ArrayList<Jugador> jugadores = new ArrayList<>();
		ArrayList<Carta> cartas = new ArrayList<>();
		String wNickname;
		boolean encontrado = false;
		if (fichJugadores.exists()) {
			System.out.println("¿Quieres modificar el NICKNAME o el MAZO? (1 NICKNAME / 2 MAZO / 0 CANCELAR):");
			int opcion = Utils.leerInt();
			if (opcion == 1) {
				volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);
				System.out.println("Introduce el nickname actual del jugador que quieres modificar:");
				wNickname = Utils.introducirCadena();
				for (int i = 0; i < jugadores.size() && !encontrado; i++) {
					if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname)) {
						System.out.println("Introduce el nuevo nickname:");
						wNickname = Utils.introducirCadena();
						jugadores.get(i).setNickname(wNickname);
						System.out.println("Nickname modificado correctamente\n");
						encontrado = true;
					}
				}
				if (!encontrado)
					System.out.println("No hay ningún jugador con nick " + wNickname + "\n");
				volcadoArrayListAFicheroJugadores(fichJugadores, jugadores);
			} else if (opcion == 2) {
				System.out.println("¿Quieres AÑADIR o ELIMINAR una carta? (1 AÑADIR / 2 ELIMINAR / 0 CANCELAR):");
				int opcionMazo = Utils.leerInt();
				if (opcionMazo == 1) {
					System.out.println("Introduce el nickname del jugador al que quieres añadir una carta en el mazo:");
					String wNickname2 = Utils.introducirCadena();
					volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);
					for (int i = 0; i < jugadores.size(); i++) {

						if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname2) && !encontrado) {
							encontrado = true;

							if (Utils.calculoFichero(fichCartas) != 0) {

								int tamMazo = 0;
								for (int e : jugadores.get(i).getMazo().values()) {
									tamMazo += e;
								}
								if (tamMazo < 40) {
									System.out.println("Introduce el ID de la carta a añadir:");
									int wId = Utils.leerInt();
									volcadoFicheroAArrayListCartas(fichCartas, cartas);
									for (int j = 0; j < cartas.size(); j++) {
										if (cartas.get(i).getId() == wId) {
											for (int x = 0; x < jugadores.size(); x++) {
												if (jugadores.get(x).getMazo().containsKey(wId))
													jugadores.get(x).getMazo().replace(wId,
															jugadores.get(x).getMazo().get(wId) + 1);
												else
													jugadores.get(x).getMazo().put(wId, 1);
											}
										}
									}
									tamMazo++;
									System.out
											.println("Carta añadida correcatamente en el mazo de " + wNickname2 + "\n");
								} else
									System.out.println("El jugador " + wNickname2
											+ " ya tiene el máximo de cartas permitidas en su mazo\n");
							} else
								System.out.println("Aún no hay ninguna carta en fichero; imposible añadir carta\n");

						} else
							System.out.println("No se ha encontrado ningún jugador con nicnkname " + wNickname2 + "\n");

					}

					volcadoArrayListAFicheroJugadores(fichJugadores, jugadores);
					volcadoArrayListAFicheroCartas(fichCartas, cartas);

					if (!encontrado)
						System.out.println("No se ha encontrado ningún jugador con nick " + wNickname2 + "\n");

				} else if (opcionMazo == 2) {

					String wNickname2 = Utils.leerString(
							"Introduce el nickname del jugador al que quieres eliminar una carta en el mazo:");

					volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);

					boolean encontrado2 = false;

					for (int i = 0; i < jugadores.size(); i++) {

						if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname2)) {
							encontrado = true;

							if (Utils.calculoFichero(fichCartas) != 0) {
								encontrado2 = true;

								int tamMazo = 0;

								for (int e : jugadores.get(i).getMazo().values()) {
									tamMazo += e;
								}

								System.out.println("Introduce el ID de la carta a eliminar:");
								int wId = Utils.leerInt();

								volcadoFicheroAArrayListCartas(fichCartas, cartas);

								for (int j = 0; j < cartas.size(); j++) {
									if (cartas.get(i).getId() == wId) {
										for (int x = 0; x < jugadores.size(); x++) {
											if (jugadores.get(x).getMazo().containsKey(wId)) {
												jugadores.get(x).getMazo().remove(wId);
											}

										}
									}
								}

								tamMazo--;
								System.out.println("Carta eliminada correcatamente del mazo de " + wNickname2 + "\n");
							}
						}

						volcadoArrayListAFicheroJugadores(fichJugadores, jugadores);
						volcadoArrayListAFicheroCartas(fichCartas, cartas);
					}

					if (!encontrado2)
						System.out.println("Aún no hay ninguna carta en el mazo\n");

					if (!encontrado)
						System.out.println("No se ha encontrado ningún jugador con nick " + wNickname2 + "\n");

				} else
					System.out.println("Modificación cancelada\n");
			} else
				System.out.println("Modificación cancelada\n");
		} else
			System.out.println("Aún no hay jugadores\n");
	}

	private static void listarJugadores(File fichJugadores) {
		ArrayList<Jugador> jugadores = new ArrayList<>();
		boolean encontrado = false;

		int opcion = Utils.leerInt(
				"¿Quieres listar jugador/es por su NICKNAME o por el número de VICTORIAS? (1 NICKNAME / 2 VICTORIAS / 0 CANCELAR):",
				0, 2);
		
		if (opcion == 1) {
			String wNickname = Utils.leerString("Introduce el nickname del jugador del que quieres listar sus datos:");

			volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);

			for (int i = 0; i < jugadores.size(); i++) {
				if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname) && !encontrado) {
					encontrado = true;

					System.out.println(jugadores.get(i).toString() + "\n");
				}
			}

			volcadoArrayListAFicheroJugadores(fichJugadores, jugadores);

			if (!encontrado)
				System.out.println("No se ha encontrado ningún jugador con nickname " + wNickname + "\n");
		} else if (opcion == 2) {
			System.out.println("Introduce el número mínimo de victorias que debe/n tener el/los jugador/es a listar:");
			int wVictorias = Utils.leerInt();

			volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);

			for (int i = 0; i < jugadores.size(); i++) {
				jugadores.get(i).calcularVictorias();

				if (jugadores.get(i).getVictorias() >= wVictorias) {
					encontrado = true;

					System.out.println(jugadores.get(i).toString() + "\n");
				}
			}

			if (!encontrado)
				System.out.println("No hay ningún jugador que tenga al menos " + wVictorias + " victorias\n");
		} else
			System.out.println("Listado cancelado\n");
	}

	private static void eliminarJugador(File fichJugadores) {
		File fichAux = new File("auxiliar.dat");
		String wNickname = null;
		int cuantosJugadores = 0;
		boolean cambios = false;
		int borrar = 1;
		boolean encontrado = false;

		if (fichJugadores.exists()) {
			wNickname = Utils.leerString("Introduce el nickname del jugador que quieres eliminar:");

			cuantosJugadores = Utils.calculoFichero(fichJugadores);

			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichJugadores));
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichAux));

				for (int i = 0; i < cuantosJugadores && borrar == 1; i++) {
					cambios = false;
					Jugador jug = (Jugador) ois.readObject();

					if (jug.getNickname().equalsIgnoreCase(wNickname) && !encontrado) {
						encontrado = true;

						System.out.println(jug + "\n");

						borrar = Utils.leerInt(
								"¿Seguro que quieres eliminar este jugador definitivamente? (1 ELIMINAR / 0 CANCELAR):",
								0, 1);

						if (borrar == 1) {
							cambios = true;
							System.out.println("Jugador con nickname " + wNickname + " eliminada correctamente\n");
						} else
							System.out.println("Borrado cancelado\n");
					}

					if (!cambios)
						oos.writeObject(jug);
				}

				if (!encontrado)
					System.out.println("No se ha encontrado ningún jugador con nickname " + wNickname + "\n");

				oos.close();
				ois.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			} catch (ClassNotFoundException e) {
				e.printStackTrace();
			}

			if (borrar == 1) {
				if (fichJugadores.delete() == false) {
					
					System.out.println("No ha sido posible eliminar el fichero\n");
				fichJugadores.delete();
			}
				if (fichAux.renameTo(fichJugadores)== false) {
					System.out.println("No ha sido posible renombrar el fichero\n");
					fichAux.renameTo(fichJugadores);
				}
			}
		} else
			System.out.println("Aún no hay ningún jugador en el fichero\n");

	}

	private static void volcadoFicheroAArrayListJugadores(File fichJugadores, ArrayList<Jugador> jugadores) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;
		try {
			fis = new FileInputStream(fichJugadores);
			ois = new ObjectInputStream(fis);
			Jugador aux = (Jugador) ois.readObject();
			while (aux != null) {
				jugadores.add(aux);
				aux = (Jugador) ois.readObject();
			}
		} catch (EOFException e) {
		} catch (Exception e) {
			e.printStackTrace();
		}
		try {
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void volcadoFicheroAArrayListCartas(File fichCartas, ArrayList<Carta> cartas) {
		FileInputStream fis = null;
		ObjectInputStream ois = null;

		try {
			fis = new FileInputStream(fichCartas);
			ois = new ObjectInputStream(fis);

			Carta aux = (Carta) ois.readObject();

			while (aux != null) {
				cartas.add(aux);
				aux = (Carta) ois.readObject();
			}
		} catch (EOFException e) {

		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			ois.close();
			fis.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void volcadoArrayListAFicheroJugadores(File fichJugadores, ArrayList<Jugador> jugadores) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichJugadores));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Jugador j : jugadores) {
			try {
				oos.writeObject(j);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private static void volcadoArrayListAFicheroCartas(File fichCartas, ArrayList<Carta> cartas) {
		ObjectOutputStream oos = null;
		try {
			oos = new ObjectOutputStream(new FileOutputStream(fichCartas));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		for (Carta j : cartas) {
			try {
				oos.writeObject(j);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		try {
			oos.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	private static void aniadirCarta(File cartas) {
		FileOutputStream fos = null;
		ObjectOutputStream moos = null;
		
		//Si el archivo de cartas ya existe, se calcula el numero de cartas para que al crearla se anada ese numero como id de carta
		if (cartas.exists()) {
			try {
				fos = new FileOutputStream(cartas, true);
				moos = new MyObjectOutputStream(fos);
				int numeroCartas = Utils.calculoFichero(cartas);
				do {
					System.out.println("¿Qué tipo de carta quieres? (1- Unidad, 2- Hechizo)");
					int tipoCarta = Utils.leerIntMinMax(1,2);
					Carta c = new Carta();
					if (tipoCarta == 1) c = new Unidad();
					if (tipoCarta == 2) c = new Hechizo();
					//
					//Revisar codigo a partir de aqui dependiendo del tipo de carta
					//
					c.setDatos(++numeroCartas);
					//
					//Añadir ArrayList de efectos a la carta
					//
					try {
						moos.writeObject(c);
					} catch (IOException e) {
						System.out.println("Input/Output error");
					}	
				} while (Utils.esBoolean("Quieres introducir más socios?"));			
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
			} catch (IOException e) {
				System.out.println("Input/Output error");
			} finally {
				try {
					moos.flush();
					moos.close();
					fos.close();
				} catch (IOException e) {
					System.out.println("Error al cerrar los flujos.");
				}
			}
		} else {
			ObjectOutputStream oos = null;
			try {
				fos = new FileOutputStream(cartas);
				oos = new ObjectOutputStream(fos);
				System.out.println("Fichero creado");
			} catch (IOException e) {
				System.out.println("Input/Output error");
			} finally {
				try {
					oos.flush();
					oos.close();
					fos.close();
				} catch (IOException e) {
					System.out.println("Error al cerrar los flujos.");
				}
			}
		}
	}

	private static void modificarCarta(File cartas) {
		int opc;
		Carta carta = null;
		boolean encontrado = false;
		
		System.out.println("Introduce el id de la carta que deseas modificar.");
		int id = Utils.leerInt();
		if (cartas.exists()) {
			int numeroCartas = Utils.calculoFichero(cartas);
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;
					
			try {
				ois = new ObjectInputStream(new FileInputStream(cartas));
			} catch (FileNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			//Se busca en el fichero una id correspondiente a la solicitada por el usuario
			for (int i = 0; i < numeroCartas && !encontrado; i++) {
				try {
					carta = (Carta) ois.readObject();
				} catch (ClassNotFoundException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				} catch (IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				//Al encontrar la carta, se pregunta que dato desea modificar
				if (carta.getId()==id) {			
					do {
						System.out.println("¿Qué desea modificar?\\n\" + \"\\t1. Mana\\n\" + \"\\t2. Daño\\n\"\r\n"
								+ "					+ \"\\t3. Descripcion\\n\" + \"\\t4. Efectos\\n\t5. Salir\\n");
						opc = Utils.leerIntMinMax(1, 6);
						switch (opc) {
						case 1:
							//Se lee el dato introducido y se introduce en el objeto y posteriormente en el fichero
							int manaIntroducido = Utils.leerInt();
							((Carta) carta).setMana(manaIntroducido);
							try {
								oos.writeObject(carta);
							} catch (IOException e) {
								// TODO Auto-generated catch block
								e.printStackTrace();
							}
							break;
						case 2:
							break;
						case 3:
							break;
						case 4:
							break;
					
						case 5:
							//Modificar esCampeon (Unidad)
							break;
						case 6:
							//Modificar tipo (Hechizo)
							break;
							}
					} while (opc != 7);
					encontrado = true;
				}
			}

			/*String linea;
			try {
				linea = ois.readLine();
			} catch (IOException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
			while (linea != null) {
				linea = ois.readLine();
			}
			try {
				carta = (Carta) ois.readObject();

			} catch (ClassNotFoundException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			System.out.println(carta.toString());
*/
			

		} else {
			System.out.println("Aún no existen cartas almacenadas.");
		}
	}
}
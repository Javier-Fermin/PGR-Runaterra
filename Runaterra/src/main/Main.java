package main;

import java.io.EOFException;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;

import clases.Carta;
import clases.Hechizo;
import clases.Jugador;
import clases.Partida;
import clases.Unidad;
import utils.MyObjectOutputStream;
import utils.Utils;

public class Main {

	public static void main(String[] args) {
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
				anniadirPartidas(fichJugadores);
				break;
			case 6:
				listarPartidas(fichJugadores);
				break;
			case 7:
				aniadirCarta(fichCartas);
				break;
			case 8:
				modificarCarta(fichCartas);
				break;
			case 9:
				listarCarta(fichCartas);
				break;
			case 10:
				break;
			case 11:
				salir = Utils.confirmacion("Desea salir?\nS para Si\nN para No");
				break;
			default:
				System.err.println("La opcion introducida no es valida");
				break;
			}
		} while (!salir);
	}

	private static void listarPartidas(File fichJugadores) {
		// Variables
		int max = Utils.calculoFichero(fichJugadores);
		boolean found = false;
		ObjectInputStream ois = null;
		Jugador aux = new Jugador();
		try {
			ois = new ObjectInputStream(new FileInputStream(fichJugadores));
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		// We ask if the user wants to filter the search or not
		if (Utils.confirmacion("Desea filtrar las partidas?\nS para Si\nN para No")) {
			// In case they want to filter it:
			// Variables
			ArrayList<Character> letras = new ArrayList<Character>(Arrays.asList('I', 'F'));
			char opc;
			// We ask what kind of filter does the user wants to apply
			System.out.println(
					"Introduzca alguna de las siguientes opciones\nI para filtrar por ID\nF para filtrar por fecha");
			opc = Utils.leerChar(letras);
			switch (opc) {
			case 'I':
				// If he wants to filter by the ID of the game
				// Variables
				String idSearch;
				// We ask for the game ID
				System.out.println("Introduzca la ID de la partida");
				idSearch = Utils.introducirCadena();
				// Now we go through our file of players
				for (int i = 0; i < max && !found; i++) {
					try {
						aux = (Jugador) ois.readObject();
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// IF the player has a game with the desired ID we show it, and since IDs are
					// unique we use a flag to stop the search
					if (aux.getPartidas().containsKey(idSearch)) {
						aux.getPartidas().get(idSearch).mostrar();
						found = true;
					}
				}
				break;
			case 'F':
				// If he wants to filter it by the date of the game
				// Variables
				LocalDate fechSearch;
				// We ask for the date of the game
				System.out.println("Introduzca la fecha (AAAA/MM/DD) de la partida");
				fechSearch = Utils.leerFechaAMD();
				// Now we go through our file of players
				for (int i = 0; i < max; i++) {
					try {
						aux = (Jugador) ois.readObject();
					} catch (ClassNotFoundException | IOException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					// If the player has a game on the asked date
					for (Partida e : aux.getPartidas().values()) {
						if (e.getFechaPartida().equals(fechSearch)) {
							// We show the game
							e.mostrar();
							found = true;
						}
					}
				}
				break;
			default:
				System.err.println("La opcion introducida no es valida");
				break;
			}
		} else {
			// In case the user doesn't want to filter the search, we just go through out
			// players file
			for (int i = 0; i < max; i++) {
				try {
					aux = (Jugador) ois.readObject();
				} catch (ClassNotFoundException | IOException e) {
					// TODO Auto-generated catch block
					e.printStackTrace();
				}
				// And here we go through every player games showing all of them
				for (Partida e : aux.getPartidas().values()) {
					e.mostrar();
					found = true;
				}
			}
		}
		// In case there were not matches or not even a single game we show an error
		// message
		if (!found) {
			System.err.println("No se han encontrado coincidencias");
		}
		try {
			ois.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	private static void anniadirPartidas(File fichJugadores) {
		// TODO Auto-generated method stub
		// Variables
		File auxFile = new File("auxFile.dat");
		int max = Utils.calculoFichero(fichJugadores);
		String nickSearch;
		ObjectInputStream ois = null;
		ObjectOutputStream oos = null;
		Jugador aux = new Jugador();
		boolean found = false;
		// We ask for the nickname of the player that we want to add a game to
		System.out.println("Introduzca el nick del jugador al que desea añadir la partida");
		nickSearch = Utils.introducirCadena();
		try {
			ois = new ObjectInputStream(new FileInputStream(fichJugadores));
			oos = new ObjectOutputStream(new FileOutputStream(auxFile));
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		// Here we go through the players file looking for the player by the nickname
		for (int i = 0; i < max; i++) {
			try {
				aux = (Jugador) ois.readObject();
			} catch (ClassNotFoundException | IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			// We look if the player has the nickname that the user is looking for
			if (!found && aux.getNickname().equalsIgnoreCase(nickSearch)) {
				// In case it is the desired player we add a mew game into his games and we turn
				// a flag to stop searching
				found = true;
				Partida auxPart = new Partida();
				auxPart.setDatos();
				aux.getPartidas().put(auxPart.getIdPartida(), auxPart);
			}
			try {
				oos.writeObject(aux);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		// Finally if there were no matches we show a error message
		if (!found) {
			System.err.println("No se han encontrado coincidencias");
		} else {
			// Otherwise we show a message to let the user know that the change has been
			// done successfully
			System.out.println("Partida añadida con exito");
			fichJugadores.delete();
			auxFile.renameTo(fichJugadores);
		}
		try {
			ois.close();
			oos.close();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

	private static void annadirJugador(File fichJugadores, File fichCartas) {
		// Abrimos flujos de salida hacia en fichero de jugadores con cuidado de no
		// sobreescribir objetos
		FileOutputStream fos = null;
		ObjectOutputStream oos = null;
		Jugador jug = new Jugador();
		ArrayList<Jugador> jugadores = new ArrayList<>();

		boolean seguir = false;
		if (fichJugadores.exists()) {
			try {
				fos = new FileOutputStream(fichJugadores, true);
				oos = new MyObjectOutputStream(fos);

				// Rellenamos datos del jugador y lo escribimos en el fichero en la última
				// posición
				jug.setDatos();
				String wNickname = jug.getNickname();
				
				if (buscarNickname(fichJugadores, jugadores, wNickname) == false) {
					oos.writeObject(jug);
					System.out.println("Jugador con nick " + wNickname + " añadido correctamente\n");
				} else
					System.out.println("Ya existe un jugador con nickname " + wNickname + "\n");
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		} else {
			try {
				fos = new FileOutputStream(fichJugadores);
				oos = new ObjectOutputStream(fos);

				// Rellenamos datos del jugador y lo escribimos en el fichero
				jug.setDatos();
				oos.writeObject(jug);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}

		// En caso de que haya alguna carta en fichero, se le pregunta al usuario si
		// quiere añadir alguna carta a su mazo
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
							// Encuentra la carta en el fichero
							if (aux.getId() == wId) {
								// Si encuentra la carta en el mazo, añade +1 a la ya existente
								if (jug.getMazo().containsKey(wId))
									jug.getMazo().replace(wId, jug.getMazo().get(wId) + 1);
								else
									// Si no está aún en el mazo, simplemente la añade
									jug.getMazo().put(wId, 1);
							}
						}

						// Sumamos +1 al tamaño del mazo del jugador
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
					// Mensaje en caso de que decida no añadir ninguna carta
					System.out.println("Has decidido no añadir ninguna carta a tu mazo\n");
				primeraVuelta = false;
				if (tamMazo == 40)
					// Mensaje en caso de que ya tenga 40 cartas (el máximo) en su mazo
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
		// Declaramos ArrayList de jugadores y de cartas para poder hacer los volcados
		ArrayList<Jugador> jugadores = new ArrayList<>();
		ArrayList<Carta> cartas = new ArrayList<>();
		String wNickname;
		boolean encontrado = false;

		// Si el fichero existe, se le pregunta si quiere modificar el nickname o el
		// mazo de cartas
		if (fichJugadores.exists()) {
			int opcion = Utils.leerInt("¿Quieres modificar el NICKNAME o el MAZO? (1 NICKNAME / 2 MAZO / 0 CANCELAR):",
					0, 2);
			if (opcion == 1) {
				// En caso de que quiera modificar el nickname, se le pregunta por el nickname
				// actual (el que hay que actualizar)
				volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);
				System.out.println("Introduce el nickname actual del jugador que quieres modificar:");
				wNickname = Utils.introducirCadena();
				for (int i = 0; i < jugadores.size() && !encontrado; i++) {
					if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname)) {
						// Preguntamos cuál es el nuevo nickname y lo actualizamos
						System.out.println("Introduce el nuevo nickname:");
						wNickname = Utils.introducirCadena();
						jugadores.get(i).setNickname(wNickname);
						System.out.println("Nickname modificado correctamente\n");
						encontrado = true;
					}
				}
				if (!encontrado)
					// Mensaje en caso de que no se encuentre el nickname en el fichero de jugadores
					System.out.println("No hay ningún jugador con nick " + wNickname + "\n");
				volcadoArrayListAFicheroJugadores(fichJugadores, jugadores);
			} else if (opcion == 2) {
				// Si decide modificar el mazo, se le pregunta si quiere añadir o eliminar una
				// carta
				System.out.println("¿Quieres AÑADIR o ELIMINAR una carta? (1 AÑADIR / 2 ELIMINAR / 0 CANCELAR):");
				int opcionMazo = Utils.leerInt();
				if (opcionMazo == 1) {
					// En caso de que decida añadir una carta, le preguntamos el nickname del dueño
					// del mazo
					System.out.println("Introduce el nickname del jugador al que quieres añadir una carta en el mazo:");
					String wNickname2 = Utils.introducirCadena();
					volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);
					for (int i = 0; i < jugadores.size(); i++) {
						// Encontramos en al jugador en el fichero
						if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname2) && !encontrado) {
							encontrado = true;

							// Si existe alguna carta en fichero de cartas, procedemos a añadir
							if (Utils.calculoFichero(fichCartas) != 0) {
								int tamMazo = 0;
								for (int e : jugadores.get(i).getMazo().values()) {
									tamMazo += e;
								}

								// Primero hay que comprobar que tenga menos de 40 cartas en el mazo, que es el
								// máximo permitido
								if (tamMazo < 40) {
									// Preguntamos el ID de la carta
									System.out.println("Introduce el ID de la carta a añadir:");
									int wId = Utils.leerInt();
									volcadoFicheroAArrayListCartas(fichCartas, cartas);
									for (int j = 0; j < cartas.size(); j++) {
										// Encontramos la carta en el fichero
										if (cartas.get(i).getId() == wId) {
											for (int x = 0; x < jugadores.size(); x++) {
												// Si ya tiene esta carta en su mazo, le ponemos +1
												if (jugadores.get(x).getMazo().containsKey(wId))
													jugadores.get(x).getMazo().replace(wId,
															jugadores.get(x).getMazo().get(wId) + 1);
												else
													// Si aún no la tiene, la añadimos normalmente
													jugadores.get(x).getMazo().put(wId, 1);
											}
										}
									}

									// Sumamos 1 carta al tamaño del mazo del jugador
									tamMazo++;
									System.out
											.println("Carta añadida correcatamente en el mazo de " + wNickname2 + "\n");
								} else
									// Mensaje en caso de que ya tenga 40 cartas en el mazo
									System.out.println("El jugador " + wNickname2
											+ " ya tiene el máximo de cartas permitidas en su mazo\n");
							} else
								// Mensaje para cuando no exista ninguna carta en el fichero de cartas
								System.out.println("Aún no hay ninguna carta en fichero; imposible añadir carta\n");

						}

					}

					volcadoArrayListAFicheroJugadores(fichJugadores, jugadores);
					volcadoArrayListAFicheroCartas(fichCartas, cartas);

					if (!encontrado)
						// Mensaje en caso de que no se encuentre el nickname en el fichero de jugadores
						System.out.println("No se ha encontrado ningún jugador con nick " + wNickname2 + "\n");

				} else if (opcionMazo == 2) {
					// Entramos en la opción de "eliminar carta del mazo" y le preguntamos por el
					// dueño del mazo
					String wNickname2 = Utils.leerString(
							"Introduce el nickname del jugador al que quieres eliminar una carta en el mazo:");

					volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);

					boolean encontrado2 = false;

					for (int i = 0; i < jugadores.size(); i++) {
						// Encontramos al jugador en el fichero
						if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname2)) {
							encontrado = true;

							// Si hay alguna carta en el fichero, procedemos a eliminar la carta del mazo
							// del jugador
							if (Utils.calculoFichero(fichCartas) != 0) {
								encontrado2 = true;

								int tamMazo = 0;

								for (int e : jugadores.get(i).getMazo().values()) {
									tamMazo += e;
								}

								// Le preguntamos por el ID de la carta a eliminar del mazo
								System.out.println("Introduce el ID de la carta a eliminar:");
								int wId = Utils.leerInt();

								volcadoFicheroAArrayListCartas(fichCartas, cartas);

								for (int j = 0; j < cartas.size(); j++) {
									// Encontramos la carta en el fichero
									if (cartas.get(i).getId() == wId) {
										for (int x = 0; x < jugadores.size(); x++) {
											// Encontramos la carta en el mazo
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
						// Mensaje en caso de que no existan cartas en el fichero
						System.out.println("Aún no hay ninguna carta en el mazo\n");

					if (!encontrado)
						// Mensaje que se muestra si no se encuentra al jugador
						System.out.println("No se ha encontrado ningún jugador con nick " + wNickname2 + "\n");

				} else
					// Mensaje para cuando se retracte de modificar
					System.out.println("Modificación cancelada\n");
			} else
				// Mensaje para cuando se retracte de modificar
				System.out.println("Modificación cancelada\n");
		} else
			// Mensaje si todavía no hay ningún jugador en el fichero de jugadores
			System.out.println("Aún no hay jugadores\n");
	}

	private static void listarJugadores(File fichJugadores) {
		// Declaramos el ArrayList para poder hacer el volcado de jugadores
		ArrayList<Jugador> jugadores = new ArrayList<>();
		boolean encontrado = false;

		int opcion = Utils.leerInt(
				// Le preguntamos al usuario si quiere listar por nickname o por número mínimo
				// de victorias en partidas
				"¿Quieres listar jugador/es por su NICKNAME o por el número de VICTORIAS? (1 NICKNAME / 2 VICTORIAS / 0 CANCELAR):",
				0, 2);

		if (opcion == 1) {
			// Si decide listar por nickname, se le solicita el mismo
			String wNickname = Utils.leerString("Introduce el nickname del jugador del que quieres listar sus datos:");

			volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);

			for (int i = 0; i < jugadores.size(); i++) {
				// Encontramos al jugador
				if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname)) {
					encontrado = true;
					// Lo mostramos por pantalla
					System.out.println(jugadores.get(i).toString() + "\n");
				}
			}

			volcadoArrayListAFicheroJugadores(fichJugadores, jugadores);

			if (!encontrado)
				// Si no hemos encontrado al jugador con ese nickname, se lo notificamos
				System.out.println("No se ha encontrado ningún jugador con nickname " + wNickname + "\n");
		} else if (opcion == 2) {
			// Si decide listar a partir de un número determinado de victorias, le pedimos
			// el número de victorias
			System.out.println("Introduce el número mínimo de victorias que debe/n tener el/los jugador/es a listar:");
			int wVictorias = Utils.leerInt();

			volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);

			for (int i = 0; i < jugadores.size(); i++) {
				jugadores.get(i).calcularVictorias();

				// Todos los jugadores que tengan al menos esas victorias, serán listados
				if (jugadores.get(i).getVictorias() >= wVictorias) {
					encontrado = true;

					System.out.println(jugadores.get(i).toString() + "\n");
				}
			}

			if (!encontrado)
				// Mensaje de control cuando ningún jugador cumpla con los requisitos de
				// victorias mínimas
				System.out.println("No hay ningún jugador que tenga al menos " + wVictorias + " victorias\n");
		} else
			// Mensaje para cuando decida cancelar el listado
			System.out.println("Listado cancelado\n");
	}

	private static void eliminarJugador(File fichJugadores) {
		// Declaramos el fichero de jugadores auxiliar par apoder hacer el volcado
		// después
		File fichAux = new File("auxiliar.dat");
		String wNickname = null;
		int cuantosJugadores = 0;
		boolean cambios = false;
		int borrar = 1;
		boolean encontrado = false;

		// Si hay algún jugador en el fichero, procedemos a preguntarle por un nickname
		if (fichJugadores.exists()) {
			wNickname = Utils.leerString("Introduce el nickname del jugador que quieres eliminar:");

			cuantosJugadores = Utils.calculoFichero(fichJugadores);

			try {
				ObjectInputStream ois = new ObjectInputStream(new FileInputStream(fichJugadores));
				ObjectOutputStream oos = new ObjectOutputStream(new FileOutputStream(fichAux));

				for (int i = 0; i < cuantosJugadores && borrar == 1; i++) {
					cambios = false;
					Jugador jug = (Jugador) ois.readObject();

					// Encontramos el nickname en el fichero
					if (jug.getNickname().equalsIgnoreCase(wNickname) && !encontrado) {
						encontrado = true;
						// Mostramos los datos del jugador
						System.out.println(jug + "\n");

						// Le pedimos una confirmación de borrado
						borrar = Utils.leerInt(
								"¿Seguro que quieres eliminar este jugador definitivamente? (1 ELIMINAR / 0 CANCELAR):",
								0, 1);

						if (borrar == 1) {
							// Mensaje de confirmación de borrado
							cambios = true;
							System.out.println("Jugador con nickname " + wNickname + " eliminada correctamente\n");
						} else
							// Mensaje de cancelación de borrado
							System.out.println("Borrado cancelado\n");
					}

					if (!cambios)
						// Si no es el jugador que nos ha pedido que eliminemos, lo escribimos en el
						// fichero auxiliar
						oos.writeObject(jug);
				}

				if (!encontrado)
					// Mensaje en caso de que no encontremos ningún jugador con ese nickname
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
					// Control por si hay algún error al eliminar el fichero de jugadores original
					System.out.println("No ha sido posible eliminar el fichero\n");
					fichJugadores.delete();
				}
				if (fichAux.renameTo(fichJugadores) == false) {
					// Control por si al renombrar el fichero auxiliar con el nombre del fichero de
					// jugadores original, da error
					System.out.println("No ha sido posible renombrar el fichero\n");
					fichAux.renameTo(fichJugadores);
				}
			}
		} else
			// Mensaje que verá el usuario en caso de que no se encuentre ningún jugador en
			// fichero de jugadores
			System.out.println("Aún no hay ningún jugador en el fichero\n");

	}

	private static void aniadirCarta(File fichCartas) {
		FileOutputStream fos = null;
		ObjectOutputStream moos = null;
		Carta c = new Carta();

		// Si el archivo de cartas ya existe, se calcula el numero de cartas para que al
		// crearla se añada ese numero como id de carta
		if (fichCartas.exists()) {

			try {
				fos = new FileOutputStream(fichCartas, true);
				moos = new MyObjectOutputStream(fos);
				int numeroCartas = Utils.calculoFichero(fichCartas);
				do {
					System.out.println("¿Qué tipo de carta quieres? (1- Unidad, 2- Hechizo)");
					int tipoCarta = Utils.leerIntMinMax(1, 2);
					if (tipoCarta == 1)
						c = new Unidad();
					if (tipoCarta == 2)
						c = new Hechizo();
					c.setDatos(++numeroCartas);
					System.out.println("La carta ha sido creada y añadida al fichero.");
					try {
						moos.writeObject(c);
					} catch (IOException e) {
						System.out.println("Input/Output error");
					}

				} while (Utils.esBoolean("Quieres introducir más cartas?"));

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
		} else

		{
			ObjectOutputStream oos = null;
			try {
				fos = new FileOutputStream(fichCartas);
				oos = new ObjectOutputStream(fos);
				System.out.println("Fichero creado");
				do {
					System.out.println("¿Qué tipo de carta quieres? (1- Unidad, 2- Hechizo)");
					int tipoCarta = Utils.leerIntMinMax(1, 2);
					if (tipoCarta == 1)
						c = new Unidad();
					if (tipoCarta == 2)
						c = new Hechizo();
					c.setDatos(1);
					try {
						oos.writeObject(c);
					} catch (IOException e) {
						System.out.println("Input/Output error");
					}
					System.out.println("La carta ha sido creada y añadida al fichero.");
				} while (Utils.esBoolean("Quieres introducir más cartas?"));
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

	private static void modificarCarta(File fichCartas) {
		int opc;
		Carta carta = null;
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		boolean encontrado = false;
		boolean salir = false;

		System.out.println("Introduce el id de la carta que deseas modificar.");
		int id = Utils.leerInt();

		if (fichCartas.exists()) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			ObjectOutputStream oos = null;

			try {
				ois = new ObjectInputStream(new FileInputStream(fichCartas));
			} catch (FileNotFoundException e) {
				System.out.println("File not found.");
				e.printStackTrace();
			} catch (IOException e) {
				System.out.println("Input/Output error");
				e.printStackTrace();
			}

			// Se vuelcan los objetos del fichero a un objeto para un mejor manejo
			volcadoFicheroAArrayListCartas(fichCartas, cartas);
			// Se busca en el fichero una id correspondiente a la solicitada por el usuario
			for (int i = 0; i < cartas.size() && !encontrado; i++) {
				// Al encontrar la carta, se pregunta que dato desea modificar
				if (cartas.get(i).getId() == id) {

					do {
						System.out.println("¿Qué desea modificar?\\n\" + \"\\t1. Mana\\n\" + \"\\t2. Daño\\n\"\r\n"
								+ "					+ \"\\t3. Descripcion\\n\" + \"\\t4. Efectos\\n\t5. Salir\\n");
						opc = Utils.leerIntMinMax(1, 6);
						switch (opc) {
						case 1:
							System.out.println("Introduzca el nuevo mana que desea asignar a la carta.");
							int manaIntroducido = Utils.leerInt();
							cartas.get(i).setMana(manaIntroducido);
							System.out.println("Carta modificada.");
							break;
						case 2:
							System.out.println("Introduzca el nuevo daño que desea asignar a la carta.");
							int dannoIntroducido = Utils.leerInt();
							cartas.get(i).setDanno(dannoIntroducido);
							System.out.println("Carta modificada.");
							break;
						case 3:
							System.out.println("Introduzca la nueva descripcion que desea asignar a la carta.");
							String descripcionIntr = Utils.leerString();
							cartas.get(i).setDescripcion(descripcionIntr);
							System.out.println("Carta modificada.");
							break;
						case 4:
							System.out.println(
									"¿Desea añadir un efecto o modificar uno existente? (1- Añadir efecto o 2- Modificar efecto)");
							int respuesta = Utils.leerInt(1, 2);
							if (respuesta == 1) {
								// Añadimos un efecto nuevo al ArrayList existente y almacenado en esta carta
								String nuevoEfecto = Utils.leerString();
								cartas.get(i).getEfectos().add(nuevoEfecto);
							}
							if (respuesta == 2) {
								System.out.println("¿Qué efecto desea modificar?");
								String efectoABuscar = Utils.leerString();
								// Volcamos la lista de efectos en otro arraylist para manejarlo con mayor
								// facilidad para recorrer
								ArrayList<String> efectos = cartas.get(i).getEfectos();
								boolean encontrado2 = false;
								for (int j = 0; j < efectos.size() & !encontrado2; j++) {
									// Si el efecto que se desea modificar coincide, se elimina y se introduce uno
									// nuevo en su lugar
									if (efectos.get(j).equals(efectoABuscar)) {
										System.out.println("Efecto encontrado. Introduzca el nuevo efecto: ");
										String efectoN = Utils.leerString();
										efectos.remove(j);
										efectos.add(j, efectoN);
										System.out.println("Efecto modificado.");
										encontrado2 = true;
									}
								}
								// Volcamos la lista de nuevo en el propio ArrayList de la carta
								cartas.get(i).setEfectos(efectos);
							}
							break;

						case 5:
							// Comprobamos que esta carta sea unidad
							if (carta instanceof Unidad) {
								System.out.println("¿Desea que esta unidad sea Campeón? ('S' o 'N')");
								Unidad unidad = (Unidad) cartas.get(i);
								unidad.setEsCampeon(Utils.esBoolean());
								// Quitamos la carta previa y introducimos la nueva con los cambios pertinentes
								cartas.remove(i);
								cartas.add(i, unidad);
							} else {
								System.out.println("Esta carta no es una unidad, por lo tanto no puede ser Campeon.");
							}
							break;
						case 6:
							// Comprobamos que esta carta sea hechizo
							if (carta instanceof Hechizo) {
								System.out.println("Introduzca el nuevo tipo del hechizo.");
								Hechizo hechizo = (Hechizo) cartas.get(i);
								hechizo.setTipo(Utils.leerString());
								// Quitamos la carta previa y introducimos la nueva con los cambios pertinentes
								cartas.remove(i);
								cartas.add(i, hechizo);
							} else {
								System.out.println("Esta carta no es una unidad, por lo tanto no puede ser Campeon.");
							}
							break;
						case 7:
							salir = Utils.confirmacion("Desea salir?\nS para Si\nN para No");
							break;
						}
					} while (!salir);
					encontrado = true;
				}
				if (!encontrado) {
					System.out.println("No se ha encontrado la carta.");
				}
				volcadoArrayListAFicheroCartas(fichCartas, cartas);
			}

		} else {
			System.out.println("Aún no existen cartas almacenadas.");
		}
	}

	private static void listarCarta(File fichCartas) {
		ArrayList<Carta> cartas = new ArrayList<Carta>();
		boolean encontrado = false;
		System.out.println("Introduce el id de la carta que deseas mostrar.");
		int id = Utils.leerInt();

		if (fichCartas.exists()) {
			// Volcamos fichero a arraylist para buscar la carta deseada (No hace falta
			// volver a volcarlo al fichero ya que no vamos a modificar nada
			volcadoFicheroAArrayListCartas(fichCartas, cartas);
			for (int i = 0; i < cartas.size() && !encontrado; i++) {
				if (cartas.get(i).getId() == id) {
					// Dependiendo del tipo de Carta mostramos la unidad o el hechizo
					if (cartas.get(i) instanceof Unidad) {
						Unidad unidad = (Unidad) cartas.get(i);
						unidad.toString();
					}
					if (cartas.get(i) instanceof Hechizo) {
						Hechizo hechizo = (Hechizo) cartas.get(i);
						hechizo.toString();
					}
				}
			}

		} else {
			System.out.println("Aún no existen cartas almacenadas.");
		}
	}

	// Este método es un volcado de fichero de jugadores a ArrayList
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

	// Este método es un volcado de fichero de cartas a ArrayList
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

	// Este método es un volcado de ArrayList de jugadores a fichero
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

	// Este método es un volcado de ArrayList de cartas a fichero
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

	public static boolean buscarNickname(File fichJugadores, ArrayList<Jugador> jugadores, String wNickname) {
		boolean encontrado = false;
		
		volcadoFicheroAArrayListJugadores(fichJugadores, jugadores);
		
		for (int i = 0; i < jugadores.size() && !encontrado; i++) {
			if (jugadores.get(i).getNickname().equalsIgnoreCase(wNickname))
				encontrado = true;
		}

		return encontrado;
	}
}
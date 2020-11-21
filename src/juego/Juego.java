package juego;

import java.awt.Color;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	
	private int reloj; // tiempo, reloj

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private double velocidadDeBajadaDePantalla;
	private Conejo conejo;
	private Calle callePrimaria; // calleAngosta
	private Calle calleSecundaria; // calleSuperior,
	private Auto[] autosCalleSecundaria;
	private Auto[] autosCallePrimaria;
	private Rasengan[] rasengans; // lista mutable de los Rasengans
//	Menu menu = new Menu();
	private boolean partidaCorriendo;
	private boolean partidaPausada;

//	private Clip jump;
	private final String SONIDO_SALTO = "jump.wav";

	private int intentos;
	private int puntaje;
	private int saltos;

	int altoDelAuto;
	int altoDeLaCalle;
	int extremoInferiorCallePrimaria;
	int extremoInferiorCalleSecundaria;
	int espacioEntreAutos;
	int posicionPrimerAutoCallePrimaria;
	int posicionDelSiguienteAuto;
	int posicionDelPrimerAutoCalleSecundaria;
	double posicionInicialHorizontalDeAutosSentidoDerecho; // Okay
	double posicionEnXAutosHaciaIzquierda;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		// Inicializar lo necesario para el juego
		partidaCorriendo = false;
		partidaPausada = false;
		velocidadDeBajadaDePantalla = 0.5;
		// BUSCAR SIMETRIA ENTRE DISTANCIA DE AUTOS
		altoDelAuto = 42;
		altoDeLaCalle = 220;
		extremoInferiorCallePrimaria = entorno.alto() / 10 + altoDeLaCalle / 2;
		extremoInferiorCalleSecundaria = entorno.alto() / 10 * -4 + altoDeLaCalle / 2;
		espacioEntreAutos = (altoDeLaCalle - (altoDelAuto * 4)) / 5;
		posicionPrimerAutoCallePrimaria = extremoInferiorCallePrimaria - espacioEntreAutos - altoDelAuto / 2;
		posicionDelSiguienteAuto = espacioEntreAutos + altoDelAuto;
		posicionDelPrimerAutoCalleSecundaria = extremoInferiorCalleSecundaria - espacioEntreAutos - altoDelAuto / 2;
		// AUDIOS
//		jump = Herramientas.cargarSonido("jump.wav");
		// CREA AUTOS EN LA CALLE PRIMARIA
		autosCallePrimaria = new Auto[16];
		posicionInicialHorizontalDeAutosSentidoDerecho = 0;
		posicionEnXAutosHaciaIzquierda = entorno.ancho();

		for (int i = 0; i < autosCallePrimaria.length; i++) {
			if (i < 4) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionPrimerAutoCallePrimaria, 2, false, velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCallePrimaria[i].getAncho() * 3;

			} else if (i < 8) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

			else if (i < 12) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 2, false,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCallePrimaria[i].getAncho() * 3;
			} else {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

		}

		// CREA AUTOS EN LA CALLE SECUNDARIA
		autosCalleSecundaria = new Auto[16];
		posicionInicialHorizontalDeAutosSentidoDerecho = 0;
		posicionEnXAutosHaciaIzquierda = entorno.ancho();

		for (int i = 0; i < autosCalleSecundaria.length; i++) {
			if (i < 4) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria, 2, true, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 5;
			} else if (i < 8) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto, 1, false,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCalleSecundaria[i].getAncho() * 5;
			} else if (i < 12) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 2, 2, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 5;
			} else {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 3, 1, false,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCalleSecundaria[i].getAncho() * 5;
			}
		}

		conejo = new Conejo(30, 30, entorno.ancho() / 2, posicionPrimerAutoCallePrimaria + 420, 40,
				velocidadDeBajadaDePantalla);

		callePrimaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, entorno.alto() / 10,
				velocidadDeBajadaDePantalla);
		calleSecundaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, (entorno.alto() / 10) * -4,
				velocidadDeBajadaDePantalla);

		intentos = 1;
		rasengans = new Rasengan[200];
		// Inicia el juego!
		entorno.iniciar();
	}

	/**
	 * Durante el juego, el método tick() será ejecutado en cada instante y por lo
	 * tanto es el método más importante de esta clase. Aquí se debe actualizar el
	 * estado interno del juego para simular el paso del tiempo (ver el enunciado
	 * del TP para mayor detalle).
	 */
	public void tick() {
		// Procesamiento de un instante de tiempo
		reloj++;

		// if (estaIniciado && !estáPausado) {

		// if (running && !pausado) {
		// if(intentos!=0) {

		if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			conejo.saltar();
			saltos++;
//			jump.start();
			Herramientas.cargarSonido(SONIDO_SALTO).start();
		}

		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			conejo.saltarIzquierda();
			conejo.hacerSonidoDeSalto();
//			jump.start();
		}

		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			conejo.saltarDerecha(entorno);
			// jump.start();
		}

		// SONIDOS EN EL JUEGO
		if (entorno.sePresiono('a') || entorno.sePresiono('s') || entorno.sePresiono('d') || entorno.sePresiono('w')
				|| entorno.sePresiono(entorno.TECLA_ABAJO) || entorno.sePresiono(entorno.TECLA_ARRIBA)
				|| entorno.sePresiono(entorno.TECLA_DERECHA) || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			// jump.start();

		}

		if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
				|| conejo.seFueDePantalla()) {
			intentos--;
		}

		if (entorno.sePresiono('p')) {
			partidaPausada = true;
		}

		callePrimaria.deslizarHaciaAbajo(entorno);
		callePrimaria.dibujar(entorno);

		calleSecundaria.deslizarHaciaAbajo(entorno);
		calleSecundaria.dibujar(entorno);

		// Conejo
		conejo.esperar();
		conejo.dibujar(entorno);
		
		for (Auto a : autosCallePrimaria) {
			if (a != null) {
				a.mover(entorno);
				a.dibujar(entorno);
			}
		}
		
		for (Auto a : autosCalleSecundaria) {
			if (a != null) {
				a.mover(entorno);
				a.dibujar(entorno);
			}
		}
		
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {// FIXME
			int i = 0;
			rasengans[i++] = conejo.disparar();
		}

		for (int i = 0; i < rasengans.length; i++) {
			if (rasengans[i] != null && rasengans[i].destruirAuto(autosCallePrimaria)) {
				puntaje += 5;
				rasengans[i] = null;
			}
			if (rasengans[i] != null && rasengans[i].destruirAuto(autosCalleSecundaria)) {
				puntaje += 5;
				rasengans[i] = null;
			} else if (rasengans[i] != null && !rasengans[i].saleDePantalla()) {
				rasengans[i].dibujar(entorno);
				rasengans[i].mover();
			}
		}

		entorno.cambiarFont(Integer.toString(reloj), 30, Color.MAGENTA);
		entorno.escribirTexto("Tiempo: " + Integer.toString(reloj / 100), 30, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("saltos:", entorno.ancho() / 2 - 100, 30);
		entorno.cambiarFont(Integer.toString(saltos), 30, Color.PINK);
		entorno.escribirTexto(Integer.toString(saltos), entorno.ancho() / 2, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("Puntos:", 550, 30);
		entorno.cambiarFont(Integer.toString(puntaje), 30, Color.PINK);
		entorno.escribirTexto(Integer.toString(puntaje), 700, 30);

		reaparecerAutosEliminados(autosCallePrimaria);
		
//		reaparecerAutosEliminadosEnLaCalle(calleSecundaria, autosCalleSecundaria);

		
		// CONTROLA LA COLISION DE LOS RASENGANS
		// EL CONDICIONAL ES NECESARIO PARA EVITAR EL ERROR "NULL-POINTER"
//		if (rasengans != null) {
//			// ITERA POR CADA RASENGAN EN LA LISTA "RASENGANS"
//			for (int i = 0; i < rasengans.length; i++) {
//				// destruye un ransengan y el auto con el que ha colisionado (calle primaria)
//
//				// EL CONTROLADOR SIRVE PARA COMPROBAR SI HUBO UNA COLISION
//				// SI EL VALOR DEL CONTROLADOR ES "-1" ES PORQUE NO HUBO COLISION
//				// SI EL VALOR ES DISTINTO DE -1, SIGNIFICA QUE HUBO UNA COLISION
//				int controladorCallePrimaria = rasengans[i].destruisteAuto(autosCallePrimaria);
//				if (controladorCallePrimaria != -1) {
//					Rasengan[] r = new Rasengan[rasengans.length - 1]; // CREA UN NUEVO ARRAY PARA EL NUEVO TAMAÑO DEL
//																		// ARRAY DE LOS RASENGANS
//					int controladorRasengans = 0; // UN CONTROLADOR PARA COPIAR LOS OBJETOS EN LA NUEVA LISTA, SIRVE
//													// PARA EVIAR EL "INDEX OUT ERROR"
//					if (r != null) // COMPRUEBA SI LA LISTA "R" ES NULA O NO
//					{
//						// RECORRE LA LISTA DE RASENGANS
//						for (int j = 0; j < rasengans.length; j++) {
//							// SI EL VALOR DEL INDICE "J" EN LA LISTA DE RASENGANS ES DIFERENTE AL VALOR DE
//							// RASENGAS EN EL INDICE I, EJECUTA EL BLOQUE DE CODIGO
//							if (j != i) {
//								r[controladorRasengans] = rasengans[j]; // ASIGNA EL VALOR DE RASENGANS[I] EN R[I] ->
//																		// ESTO ES LA COPIA DE LOS DATOS QUE QUEREMOS
//																		// CONSERVAR
//								controladorRasengans++; // AUMENTA EL VALOR DEL CONTROLADOR PARA SEGUIR AREGREGANDO
//														// ELEMENTOS REQUERIDOS EN "R"
//							}
//						}
//						rasengans = r; // ASGINA LA NUEVA LISTA DE RASENGANS A LA ANTIGUA LISTA
//					}
//
//					// ELIMINA EL AUTO CON EL QUE COLISIONO
//					Auto[] a = new Auto[autosCallePrimaria.length - 1];
//					int controladorAutos = 0; // CONTROLADOR QUE AYUDA A EVITAR EL ERROR "INDEX OUT"
//					if (a != null) // CONTROLA SI EL ARRAY "A" ES NULO O NO
//					{
//						// ITERA EN LA LISTA DE AUTOS DE LA CALLE PRIMARIA
//						for (int n = 0; n < autosCallePrimaria.length; n++) {
//							if (n != controladorCallePrimaria) // SI EL OBJETO "N" EN LA LISTA DE AUTOS DE LA CALLE
//																// PRIMARIA ES DISTINTA AL CONTROLADOR DE AUTOS, EJECUTA
//																// EL BLOQUE DE CODIGO
//							{
//								a[controladorAutos] = autosCallePrimaria[n]; // ASIGNA EL ELEMENTO "N" DE LA LISTA DE
//																				// AUTOS DE LA CALLE PRIMARIA A LA NUEVA
//																				// LISTA
//								controladorAutos++; // AUMENTA EL CONTROLADOR
//							}
//						}
//						autosCallePrimaria = a; // ASGINA LA NUEVA LISTA A LA ANTIGUA LISTA DE AUTOS
//					}
//
//					puntaje += 5; // AUMENTA EL PUNTAJE CADA VEZ QUE EL RASENGAN COLISIONA CON UN AUTO
//					return; // SE USA LA PALABRA CLAVE "RETURN" PARA EVITAR QUE EL BUCLE CONTINUE, DE NO
//							// PONERLO, INTENTA TRABAJAR CON UN OBJETO QUE YA NO EXISTE, DANDO COMO
//							// RESULTADO UN "INDEX OUT ERROR"
//				}
//
//				// LO DE ARRIBA X2 XD
//				int controladorCalleSecundaria = rasengans[i].destruisteAuto(autosCalleSecundaria);
//				if (controladorCalleSecundaria != -1) {
//					Rasengan[] r = new Rasengan[rasengans.length - 1];
//					int controladorRasengans = 0;
//					if (r != null) {
//						for (int j = 0; j < rasengans.length; j++) {
//							if (j != i) {
//								r[controladorRasengans] = rasengans[j];
//								controladorRasengans++;
//							}
//						}
//						rasengans = r;
//					}
//
//					if (controladorCalleSecundaria != -1) {
//						Auto[] a = new Auto[autosCalleSecundaria.length - 1];
//						int controladorAutos = 0;
//						if (a != null) {
//							for (int n = 0; n < autosCalleSecundaria.length; n++) {
//								if (n != controladorCalleSecundaria) {
//									a[controladorAutos] = autosCalleSecundaria[n];
//									controladorAutos++;
//								}
//							}
//							autosCalleSecundaria = a;
//						}
//					}
//
//					// agregar un nuevo auto
//
//					puntaje += 5;
//					return;
//				}
//
//				// CONTROLA SI EL RASENGAN ESTÁ FUERA DE LA PANTALLA DE JUEGO
//				// DE SER ASI, ELIMINA EL RASENGAN
//				// ACA NO ES NECESARIO EL USO DE LA PALABRA CLAVE "RETURN"
//				// YA QUE ES LA ULTIMA COMPROBACION QUE SE HACE
//				if (rasengans[i].saleDePantalla()) {
//					Rasengan[] r = new Rasengan[rasengans.length - 1];
//					int controladorRasengans = 0;
//					if (r != null) {
//						for (int j = 0; j < rasengans.length; j++) {
//							if (j != i) {
//								r[controladorRasengans] = rasengans[j];
//								controladorRasengans++;
//							}
//						}
//						rasengans = r;
//					}
//				}
//			}

		// actualizar y dibujar rasengans
		// CONTROLA SI LA LISTA DE RASENGAN ES NULA O NO
		// SI NO SE PONE EL CONDICIONAL, JAVA VA A INTENTAR DIBUJAR UN OBJETO NULO
		// DANDO UN ERROR "NULL-POINTER"
//			if (rasengans != null) {
//				// ITERA EN CADA RASENGAN, LO ACTUALIZA Y LO DIBUJA
//				for (Rasengan r : rasengans) {
//					r.mover();
//					r.dibujar(entorno);
//				}
//			}

		entorno.cambiarFont(Integer.toString(reloj), 30, Color.MAGENTA);
		entorno.escribirTexto("Tiempo: " + Integer.toString(reloj / 100), 30, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("saltos:", entorno.ancho() / 2 - 100, 30);
		entorno.cambiarFont(Integer.toString(saltos), 30, Color.PINK);
		entorno.escribirTexto(Integer.toString(saltos), entorno.ancho() / 2, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("Puntos:", 550, 30);
		entorno.cambiarFont(Integer.toString(puntaje), 30, Color.PINK);
		entorno.escribirTexto(Integer.toString(puntaje), 700, 30);
	}

	private void reaparecerAutosEliminados(Auto[] autos) {
		for (int l = 0; l < autos.length; l++) { // FIXME
			if (autos[l] == null && l < 4) {
//				if (reloj < 500) {
//					System.out.println(reloj);
//					reloj = 0; // piensenlon
//				}
				autos[l] = new Auto(altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho, posicionPrimerAutoCallePrimaria + callePrimaria.posicionVertical(), 2, false, velocidadDeBajadaDePantalla, Color.CYAN);
				posicionInicialHorizontalDeAutosSentidoDerecho += autos[l].getAncho() * 3;
			}
			if (autos[l] == null && l >= 4 && l < 8) {
				autos[l] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto + callePrimaria.posicionVertical(), 1, true,
						velocidadDeBajadaDePantalla, Color.MAGENTA);
				posicionEnXAutosHaciaIzquierda += autos[l].getAncho() * 3;
			}
			if (autos[l] == null && l >= 8 && l < 12) {
				autos[l] = new Auto(altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2 + callePrimaria.posicionVertical(), 2, false,
						velocidadDeBajadaDePantalla, Color.GREEN);
				posicionInicialHorizontalDeAutosSentidoDerecho += autos[l].getAncho() * 3;
			} else if (autos[l] == null && l >= 12 && l < 16) {
				autos[l] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3 + callePrimaria.posicionVertical(), 1, true,
						velocidadDeBajadaDePantalla, Color.YELLOW);
				posicionEnXAutosHaciaIzquierda += autos[l].getAncho() * 3;
			}
		}
	}

	// System.out.println(rasengans.size());

//		if (!partidaCorriendo || partidaPausada) {
//			menu.dibujarMenu(entorno, this);
//		}

	// imprime la accion actual
	// System.out.println(menu.getAccion());

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

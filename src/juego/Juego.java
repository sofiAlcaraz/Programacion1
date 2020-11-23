package juego;

import java.awt.Color;
import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// Constantes
	private final String sonidoSalto = "jump.wav";
	private final String sonidoRasengan = "rasengan.wav";

	private final int altoDelAuto = 42;
	private final int altoDeLaCalle = 220;
	private final int anchoDeAuto = 50;
	private final double velocidadDeBajadaDePantalla = 1;

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;
	private Calle callePrimaria;
	private Calle calleSecundaria;
	private Auto[] autosCalleSecundaria;
	private Auto[] autosCallePrimaria;
	private Rasengan rasengan;
//	Menu menu = new Menu();
	private boolean partidaCorriendo;
	private boolean partidaPausada;
	// otros
	private int temporizadorAutos;
	private int reloj;
	private int intentos;
	private int puntaje;
	private int saltos;
	// para autos
	private int extremoInferiorCallePrimaria;
	private int extremoInferiorCalleSecundaria;
	private int espacioEntreAutos;
	private int posicionPrimerAutoCallePrimaria;
	private int posicionDelSiguienteAuto;
	private int posicionDelPrimerAutoCalleSecundaria;
	private double posicionInicialHorizontalDeAutosSentidoDerecho; // Okay
	private double posicionEnXAutosHaciaIzquierda;
	private Image imagenFondo;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		// Inicializar lo necesario para el juego
		partidaCorriendo = false;
		partidaPausada = false;
		imagenFondo = Herramientas.cargarImagen("fondoCesped.jpg");

		// buscar simetria entre autos
		extremoInferiorCallePrimaria = entorno.alto() / 10 + altoDeLaCalle / 2;
		extremoInferiorCalleSecundaria = entorno.alto() / 10 * -9 + altoDeLaCalle / 2;
		espacioEntreAutos = (altoDeLaCalle - (altoDelAuto * 4)) / 5;
		posicionPrimerAutoCallePrimaria = extremoInferiorCallePrimaria - espacioEntreAutos - altoDelAuto / 2;
		posicionDelSiguienteAuto = espacioEntreAutos + altoDelAuto;
		posicionDelPrimerAutoCalleSecundaria = extremoInferiorCalleSecundaria - espacioEntreAutos - altoDelAuto / 2;

		// crea autos en la calle primaria
		autosCallePrimaria = new Auto[16];
		posicionInicialHorizontalDeAutosSentidoDerecho = 0;
		posicionEnXAutosHaciaIzquierda = entorno.ancho();

		for (int i = 0; i < autosCallePrimaria.length; i++) {
			if (i < 4) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho, posicionPrimerAutoCallePrimaria, 3, false,
						velocidadDeBajadaDePantalla, "auto.png");
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCallePrimaria[i].getAncho() * 3;

			} else if (i < 8) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 2, true,
						velocidadDeBajadaDePantalla, "auto.png");
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

			else if (i < 12) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 3, false,
						velocidadDeBajadaDePantalla, "auto.png");
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCallePrimaria[i].getAncho() * 3;
			} else {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 2, true,
						velocidadDeBajadaDePantalla, "auto.png");
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

		}

		// crea autos en la calle secundaria
		autosCalleSecundaria = new Auto[16];
		posicionInicialHorizontalDeAutosSentidoDerecho = 0;
		posicionEnXAutosHaciaIzquierda = entorno.ancho();

		for (int i = 0; i < autosCalleSecundaria.length; i++) {
			if (i < 4) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria, 2, true, velocidadDeBajadaDePantalla, "auto.png");
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 3;
			} else if (i < 8) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto, 3, false,
						velocidadDeBajadaDePantalla, "auto.png");
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCalleSecundaria[i].getAncho() * 3;
			} else if (i < 12) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 2, 2, true,
						velocidadDeBajadaDePantalla, "auto.png");
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 3;
			} else {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 3, 3, false,
						velocidadDeBajadaDePantalla, "auto.png");
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCalleSecundaria[i].getAncho() * 3;
			}
		}

		conejo = new Conejo(30, 30, entorno.ancho() / 2, posicionPrimerAutoCallePrimaria + 420, 40,
				velocidadDeBajadaDePantalla);

		callePrimaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, entorno.alto() / 10,
				velocidadDeBajadaDePantalla);
		calleSecundaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, (entorno.alto() / 10) * -9,
				velocidadDeBajadaDePantalla);

		// intentos = 1;
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
		temporizadorAutos++;
		reloj++;

		// if (estaIniciado && !estáPausado) {

		// if (running && !pausado) {
		// if(intentos!=0) {
		entorno.dibujarImagen(imagenFondo, entorno.alto()/2, entorno.ancho()/2, 0, 1);
		

		if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
				|| conejo.seFueDePantalla()) {
			// intentos--;
		}

		callePrimaria.deslizarHaciaAbajo(entorno);
		callePrimaria.dibujar(entorno);
		calleSecundaria.deslizarHaciaAbajo(entorno);
		calleSecundaria.dibujar(entorno);
		// Conejo
		conejo.esperar();
//		conejo.dibujar(entorno);
		// Autos
		if (!entorno.sePresiono('w') || !entorno.sePresiono('a') || !entorno.sePresiono('d')) {
			conejo.dibujar(entorno);

			if (entorno.sePresiono('w')) {
				conejo.saltar(entorno);
				saltos++;
				Herramientas.cargarSonido(sonidoSalto).start();
			}
			if (entorno.sePresiono('a')) {
				conejo.saltarIzquierda(entorno);
				// conejo.hacerSonidoDeSalto();
				Herramientas.cargarSonido(sonidoSalto).start();
			}

			if (entorno.sePresiono('d')) {
				conejo.saltarDerecha(entorno);
				Herramientas.cargarSonido(sonidoSalto).start();
			}
		}
		
		for (Auto a : autosCallePrimaria) {
			if (a != null) {
				a.avanzar(entorno);
				a.dibujar(entorno);
			}
		}

		for (Auto a : autosCalleSecundaria) {
			if (a != null) {
				a.avanzar(entorno);
				a.dibujar(entorno);
			}
		}
		// Rasengans
		if (entorno.sePresiono(entorno.TECLA_ESPACIO) && rasengan == null) {// && rasengans[0] == null
			rasengan = conejo.disparar();
			Herramientas.cargarSonido(sonidoRasengan).start();
		}

		if (rasengan != null && rasengan.saleDePantalla()) {
			rasengan = null;
		}
		if (rasengan != null) {
			rasengan.dibujar(entorno);
			rasengan.mover();
		}

		for (int k = 0; k < autosCallePrimaria.length; k++) {
			if (rasengan != null && rasengan.colisionasteConAuto(autosCallePrimaria[k])
					&& autosCallePrimaria[k].getSentido() == true) {

				Auto autoRegenerado = new Auto(altoDelAuto, anchoDeAuto, autosCallePrimaria[k].getX() - entorno.ancho(),
						autosCallePrimaria[k].getY(), autosCallePrimaria[k].getVelocidad(),
						autosCallePrimaria[k].getSentido(), autosCallePrimaria[k].getBajadaDePantalla(), "auto.png");
				autosCallePrimaria[k] = autoRegenerado;
				rasengan = null;
				puntaje += 5;

			} else if (rasengan != null && rasengan.colisionasteConAuto(autosCallePrimaria[k])
					&& autosCallePrimaria[k].getSentido() == false) {

				Auto autoRegenerado = new Auto(altoDelAuto, anchoDeAuto, autosCallePrimaria[k].getX() + entorno.ancho(),
						autosCallePrimaria[k].getY(), autosCallePrimaria[k].getVelocidad(),
						autosCallePrimaria[k].getSentido(), autosCallePrimaria[k].getBajadaDePantalla(), "auto.png");
				autosCallePrimaria[k] = autoRegenerado;
				rasengan = null;
				puntaje += 5;

			}

		}

		for (int m = 0; m < autosCalleSecundaria.length; m++) {
			if (rasengan != null && rasengan.colisionasteConAuto(autosCalleSecundaria[m])
					&& autosCalleSecundaria[m].getSentido() == true) {

				Auto autosRegeneradosCalleSecundaria = new Auto(altoDelAuto, anchoDeAuto,
						autosCalleSecundaria[m].getX() - entorno.ancho(), autosCalleSecundaria[m].getY(),
						autosCalleSecundaria[m].getVelocidad(), autosCalleSecundaria[m].getSentido(),
						autosCalleSecundaria[m].getBajadaDePantalla(), "auto.png");
				autosCalleSecundaria[m] = autosRegeneradosCalleSecundaria;
				rasengan = null;
				puntaje += 5;

			} else if (rasengan != null && rasengan.colisionasteConAuto(autosCalleSecundaria[m])
					&& autosCalleSecundaria[m].getSentido() == false) {

				Auto autosRegeneradosCalleSecundaria = new Auto(altoDelAuto, anchoDeAuto,
						autosCalleSecundaria[m].getX() + entorno.ancho(), autosCalleSecundaria[m].getY(),
						autosCalleSecundaria[m].getVelocidad(), autosCalleSecundaria[m].getSentido(),
						autosCalleSecundaria[m].getBajadaDePantalla(), "auto.png");
				autosCalleSecundaria[m] = autosRegeneradosCalleSecundaria;
				rasengan = null;
				puntaje += 5;

			}

		}

		// texto en pantallam ,tamaño de letra=20
		entorno.cambiarFont(Integer.toString(reloj), 20, Color.PINK);
		entorno.escribirTexto("Tiempo: " + Integer.toString(reloj / 100), 30, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("saltos:", entorno.ancho() / 2 - 100, 30);
		entorno.cambiarFont(Integer.toString(saltos), 20, Color.PINK);
		entorno.escribirTexto(Integer.toString(saltos), entorno.ancho() / 2, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("Puntos:", 550, 30);
		entorno.cambiarFont(Integer.toString(puntaje), 20, Color.PINK);
		entorno.escribirTexto(Integer.toString(puntaje), 700, 30);

		reaparecerAutosEliminados(autosCallePrimaria,
				posicionPrimerAutoCallePrimaria + callePrimaria.posicionVertical() - espacioEntreAutos - anchoDeAuto,
				posicionDelSiguienteAuto);
		reaparecerAutosEliminados(autosCalleSecundaria, posicionDelPrimerAutoCalleSecundaria, posicionDelSiguienteAuto);

	}

//		if (entorno.sePresiono('p')) {
//			partidaPausada = true;
	// }
	// if (!partidaCorriendo || partidaPausada) {
//		menu.dibujarMenu(entorno, this);
//	}

// imprime la accion actual
// System.out.println(menu.getAccion());

//	}

	private void reaparecerAutosEliminados(Auto[] autos, double y, int sig) {
		if (temporizadorAutos >= 200) {
			for (int l = 0; l < autos.length; l++) {
				if (autos[l] == null && l < 4) {
					autos[l] = new Auto(altoDelAuto, anchoDeAuto,
							posicionInicialHorizontalDeAutosSentidoDerecho - 800 - 800 / 2, y, 3, false,
							velocidadDeBajadaDePantalla, "auto.png");
					posicionInicialHorizontalDeAutosSentidoDerecho += autos[l].getAncho() * 3;
					temporizadorAutos = 0;
				}
				if (autos[l] == null && l >= 4 && l < 8) {
					autos[l] = new Auto(altoDelAuto, anchoDeAuto, posicionEnXAutosHaciaIzquierda + 800 + 800 / 2,
							y - sig, 2, true, velocidadDeBajadaDePantalla, "auto.png");
					posicionEnXAutosHaciaIzquierda += autos[l].getAncho() * 3;
					temporizadorAutos = 0;
				}
				if (autos[l] == null && l >= 8 && l < 12) {
					autos[l] = new Auto(altoDelAuto, anchoDeAuto,
							posicionInicialHorizontalDeAutosSentidoDerecho - 800 - 800 / 2, y - sig * 2, 3, false,
							velocidadDeBajadaDePantalla, "auto.png");
					posicionInicialHorizontalDeAutosSentidoDerecho += autos[l].getAncho() * 3;
					temporizadorAutos = 0;
				} else if (autos[l] == null && l >= 12 && l < 16) {
					autos[l] = new Auto(altoDelAuto, anchoDeAuto, posicionEnXAutosHaciaIzquierda + 800 + 800 / 2,
							y - sig * 3, 2, true, velocidadDeBajadaDePantalla, "auto.png");
					posicionEnXAutosHaciaIzquierda += autos[l].getAncho() * 3;
					temporizadorAutos = 0;
				}
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

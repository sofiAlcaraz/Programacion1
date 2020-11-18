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
	int posRasengan;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		// Inicializar lo necesario para el juego
		partidaCorriendo = false;
		partidaPausada = false;
		velocidadDeBajadaDePantalla = 0.5;

		// buscar simetria entre autos
		altoDelAuto = 42;
		altoDeLaCalle = 220;
		extremoInferiorCallePrimaria = entorno.alto() / 10 + altoDeLaCalle / 2;
		extremoInferiorCalleSecundaria = entorno.alto() / 10 * -4 + altoDeLaCalle / 2;
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

		// crea autos en la calle secundaria
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
			Herramientas.cargarSonido(SONIDO_SALTO).start();
		}

		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			conejo.saltarIzquierda();
			// conejo.hacerSonidoDeSalto();
			Herramientas.cargarSonido(SONIDO_SALTO).start();
		}

		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			conejo.saltarDerecha(entorno);
			Herramientas.cargarSonido(SONIDO_SALTO).start();
		}

		if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
				|| conejo.seFueDePantalla()) {
			intentos--;
		}

		callePrimaria.deslizarHaciaAbajo(entorno);
		callePrimaria.dibujar(entorno);
		calleSecundaria.deslizarHaciaAbajo(entorno);
		calleSecundaria.dibujar(entorno);
		// Conejo
		conejo.esperar();
		conejo.dibujar(entorno);
		// Autos
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
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			rasengans[posRasengan++] = conejo.disparar();
		}
		// colicion RasengansConAutos,chequeo de puntaje
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
		// texto en pantallam ,tamaño de letra=20
		entorno.cambiarFont(Integer.toString(reloj), 20, Color.MAGENTA);
		entorno.escribirTexto("Tiempo: " + Integer.toString(reloj / 100), 30, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("saltos:", entorno.ancho() / 2 - 100, 30);
		entorno.cambiarFont(Integer.toString(saltos), 20, Color.PINK);
		entorno.escribirTexto(Integer.toString(saltos), entorno.ancho() / 2, 30);
		entorno.cambiarFont("", 30, Color.PINK);
		entorno.escribirTexto("Puntos:", 550, 30);
		entorno.cambiarFont(Integer.toString(puntaje), 20, Color.PINK);
		entorno.escribirTexto(Integer.toString(puntaje), 700, 30);

		reaparecerAutosEliminados(autosCallePrimaria);

		if (entorno.sePresiono('p')) {
			partidaPausada = true;
		}
		// if (!partidaCorriendo || partidaPausada) {
//		menu.dibujarMenu(entorno, this);
//	}

// imprime la accion actual
// System.out.println(menu.getAccion());

	}

	private void reaparecerAutosEliminados(Auto[] autos) {
		for (int l = 0; l < autos.length; l++) { // FIXME
			if (autos[l] == null && l < 4) {
//				if (reloj < 500) {
//					System.out.println(reloj);
//					reloj = 0; // piensenlon
//				}
				autos[l] = new Auto(altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionPrimerAutoCallePrimaria + callePrimaria.posicionVertical(), 2, false,
						velocidadDeBajadaDePantalla, Color.CYAN);
				posicionInicialHorizontalDeAutosSentidoDerecho += autos[l].getAncho() * 3;
			}
			if (autos[l] == null && l >= 4 && l < 8) {
				autos[l] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto + callePrimaria.posicionVertical(),
						1, true, velocidadDeBajadaDePantalla, Color.MAGENTA);
				posicionEnXAutosHaciaIzquierda += autos[l].getAncho() * 3;
			}
			if (autos[l] == null && l >= 8 && l < 12) {
				autos[l] = new Auto(
						altoDelAuto, 50, posicionInicialHorizontalDeAutosSentidoDerecho, posicionPrimerAutoCallePrimaria
								- posicionDelSiguienteAuto * 2 + callePrimaria.posicionVertical(),
						2, false, velocidadDeBajadaDePantalla, Color.GREEN);
				posicionInicialHorizontalDeAutosSentidoDerecho += autos[l].getAncho() * 3;
			} else if (autos[l] == null && l >= 12 && l < 16) {
				autos[l] = new Auto(
						altoDelAuto, 50, posicionEnXAutosHaciaIzquierda, posicionPrimerAutoCallePrimaria
								- posicionDelSiguienteAuto * 3 + callePrimaria.posicionVertical(),
						1, true, velocidadDeBajadaDePantalla, Color.YELLOW);
				posicionEnXAutosHaciaIzquierda += autos[l].getAncho() * 3;
			}
		}
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

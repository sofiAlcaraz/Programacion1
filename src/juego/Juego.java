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
	private double velocidadDeBajadaDePantalla = 1;
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;
	private Calle callePrimaria;
	private Calle calleSecundaria;
	private Auto[] autosCalleSecundaria;
	private Auto[] autosCallePrimaria;
	private Rasengan rasengan;
	private Menu menu;
	// booleanos
	private boolean partidaCorriendo;
	private boolean partidaPausada;
	private boolean perdioPartida;
	private boolean ganoPartida;
	// otros
	private int reloj;
	private int puntaje;
	private int saltos;
	// para autos
	private int extremoInferiorCallePrimaria;
	private int extremoInferiorCalleSecundaria;
	private int espacioEntreAutos;
	private int posicionPrimerAutoCallePrimaria;
	private int posicionDelSiguienteAuto;
	private int posicionDelPrimerAutoCalleSecundaria;
	private double posicionInicialHorizontalDeAutosSentidoDerecho;
	private double posicionInicialHorizontalDeAutosSentidoIzquierda;
	// imagenes
	private Image imagenFondo;
	private Image gameOver;
	private Image victoria;

	public Juego() {
		entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		menu = new Menu();
		partidaCorriendo = false;
		partidaPausada = false;
		perdioPartida = false;
		ganoPartida = false;
		imagenFondo = Herramientas.cargarImagen("fondoCesped.jpg");
		gameOver = Herramientas.cargarImagen("gameover.png");
		victoria = Herramientas.cargarImagen("victoria.png");
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
		posicionInicialHorizontalDeAutosSentidoIzquierda = entorno.ancho();

		for (int i = 0; i < autosCallePrimaria.length; i++) {
			if (i < 4) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho, posicionPrimerAutoCallePrimaria, 3, false,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCallePrimaria[i].getAncho() * 3;

			} else if (i < 8) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 2, true,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

			else if (i < 12) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 3, false,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCallePrimaria[i].getAncho() * 3;
			} else {
				autosCallePrimaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 2, true,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

		}

		// crea autos en la calle secundaria
		autosCalleSecundaria = new Auto[16];
		posicionInicialHorizontalDeAutosSentidoDerecho = 0;
		posicionInicialHorizontalDeAutosSentidoIzquierda = entorno.ancho();

		for (int i = 0; i < autosCalleSecundaria.length; i++) {
			if (i < 4) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoIzquierda, posicionDelPrimerAutoCalleSecundaria, 2, true,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoIzquierda += autosCalleSecundaria[i].getAncho() * 3;
			} else if (i < 8) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto, 3, false,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCalleSecundaria[i].getAncho() * 3;
			} else if (i < 12) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoIzquierda,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 2, 2, true,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoIzquierda += autosCalleSecundaria[i].getAncho() * 3;
			} else {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, anchoDeAuto,
						posicionInicialHorizontalDeAutosSentidoDerecho,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 3, 3, false,
						velocidadDeBajadaDePantalla);
				posicionInicialHorizontalDeAutosSentidoDerecho += autosCalleSecundaria[i].getAncho() * 3;
			}
		}

		conejo = new Conejo(30, 30, entorno.ancho() / 2,
				(posicionPrimerAutoCallePrimaria - espacioEntreAutos - altoDelAuto / 2) * 3,
				velocidadDeBajadaDePantalla);

		callePrimaria = new Calle(altoDeLaCalle, entorno.ancho(), entorno.ancho() / 2, entorno.alto() / 10,
				velocidadDeBajadaDePantalla);
		calleSecundaria = new Calle(altoDeLaCalle, entorno.ancho(), entorno.ancho() / 2, (entorno.alto() / 10) * -9,
				velocidadDeBajadaDePantalla);

		entorno.iniciar();

	}

	public void tick() {
		if (perdioPartida == true) {
			partidaCorriendo = false;
			partidaPausada = false;
			entorno.dibujarImagen(gameOver, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);

			if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				System.exit(0);
			}

		}

		if (ganoPartida == true) {
			partidaCorriendo = false;
			partidaPausada = false;
			entorno.dibujarImagen(victoria, entorno.ancho() / 2, entorno.alto() / 2, 0, 1);

			if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
				System.exit(0);
			}
		}

		if (partidaCorriendo == true && partidaPausada == false) {
			reloj++;
			entorno.dibujarImagen(imagenFondo, entorno.alto() / 2, entorno.ancho() / 2, 0, 1);

			if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
					|| conejo.seFueDePantalla()) {
				perdioPartida = true;
				partidaPausada = false;
			}

			callePrimaria.deslizarHaciaAbajo(entorno);
			callePrimaria.dibujar(entorno);
			calleSecundaria.deslizarHaciaAbajo(entorno);
			calleSecundaria.dibujar(entorno);
			// Conejo
			conejo.esperar();

			if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
				conejo.saltar(entorno, altoDelAuto, espacioEntreAutos);
				saltos++;
				Herramientas.cargarSonido(sonidoSalto).start();
			} else if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
				conejo.saltarIzquierda(entorno);
				Herramientas.cargarSonido(sonidoSalto).start();
			} else if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
				conejo.saltarDerecha(entorno);
				Herramientas.cargarSonido(sonidoSalto).start();
			}

			conejo.dibujar(entorno);

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
			if (entorno.sePresiono(entorno.TECLA_ESPACIO) && rasengan == null) {
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

			// autos
			for (int k = 0; k < autosCallePrimaria.length; k++) {
				if (rasengan != null && rasengan.colisionasteConAuto(autosCallePrimaria[k])
						&& autosCallePrimaria[k].getSentido() == true) {

					Auto autoRegenerado = new Auto(altoDelAuto, anchoDeAuto,
							autosCallePrimaria[k].getX() - entorno.ancho(), autosCallePrimaria[k].getY(),
							autosCallePrimaria[k].getVelocidad(), autosCallePrimaria[k].getSentido(),
							autosCalleSecundaria[k].getVelocidadBajada());

					autosCallePrimaria[k] = autoRegenerado;
					rasengan = null;
					puntaje += 5;

				} else if (rasengan != null && rasengan.colisionasteConAuto(autosCallePrimaria[k])
						&& autosCallePrimaria[k].getSentido() == false) {

					Auto autoRegenerado = new Auto(altoDelAuto, anchoDeAuto,
							autosCallePrimaria[k].getX() + entorno.ancho(), autosCallePrimaria[k].getY(),
							autosCallePrimaria[k].getVelocidad(), autosCallePrimaria[k].getSentido(),
							autosCalleSecundaria[k].getVelocidadBajada());
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
							autosCalleSecundaria[m].getVelocidadBajada());
					autosCalleSecundaria[m] = autosRegeneradosCalleSecundaria;
					rasengan = null;
					puntaje += 5;

				} else if (rasengan != null && rasengan.colisionasteConAuto(autosCalleSecundaria[m])
						&& autosCalleSecundaria[m].getSentido() == false) {

					Auto autosRegeneradosCalleSecundaria = new Auto(altoDelAuto, anchoDeAuto,
							autosCalleSecundaria[m].getX() + entorno.ancho(), autosCalleSecundaria[m].getY(),
							autosCalleSecundaria[m].getVelocidad(), autosCalleSecundaria[m].getSentido(),
							autosCalleSecundaria[m].getVelocidadBajada());
					autosCalleSecundaria[m] = autosRegeneradosCalleSecundaria;
					rasengan = null;
					puntaje += 5;

				}

			}

			if (puntaje == 100) {
				ganoPartida = true;
			}

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

			if (entorno.sePresiono('p')) {
				partidaPausada = true;
			}
		} else if (partidaPausada = true && perdioPartida == false && ganoPartida == false) {
			menu.dibujarMenu(entorno);
			if (menu.confirmarSeleccionado(entorno) == "jugar") {

				partidaCorriendo = true;
				partidaPausada = false;
			}
			if (menu.confirmarSeleccionado(entorno) == "salir") {

				System.exit(0);
			}

		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

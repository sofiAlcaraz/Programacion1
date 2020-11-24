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
	private boolean partidaCorriendo;
	private boolean partidaPausada;
	// otros
	private Menu menu;
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
	private double posicionInicialHorizontalDeAutosSentidoDerecho; // Okay
	private double posicionEnXAutosHaciaIzquierda;
	private Image imagenFondo;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		menu = new Menu();
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

		conejo = new Conejo(30, 30, entorno.ancho() / 2, posicionPrimerAutoCallePrimaria + 300, 40,
				velocidadDeBajadaDePantalla);

		callePrimaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, entorno.alto() / 10,
				velocidadDeBajadaDePantalla);
		calleSecundaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, (entorno.alto() / 10) * -9,
				velocidadDeBajadaDePantalla);
		
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
		
		if (partidaCorriendo==true && partidaPausada==false) {
			reloj++;
			// if(intentos!=0) {
			entorno.dibujarImagen(imagenFondo, entorno.alto() / 2, entorno.ancho() / 2, 0, 1);

			if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
					|| conejo.seFueDePantalla()) {
				partidaPausada=true;
			}

			callePrimaria.deslizarHaciaAbajo(entorno);
			callePrimaria.dibujar(entorno);
			calleSecundaria.deslizarHaciaAbajo(entorno);
			calleSecundaria.dibujar(entorno);
			// Conejo
			conejo.esperar();

			// Autos
//		if (!entorno.sePresiono('w') || !entorno.sePresiono('a') || !entorno.sePresiono('d')) {
//			conejo.dibujar(entorno);

			if (entorno.sePresiono('w')) {
				conejo.saltar(entorno);
				saltos++;
				Herramientas.cargarSonido(sonidoSalto).start();
			} else if (entorno.sePresiono('a')) {
				conejo.saltarIzquierda(entorno);
				Herramientas.cargarSonido(sonidoSalto).start();
			} else if (entorno.sePresiono('d')) {
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
							autosCalleSecundaria[k].getVelocidadBajada(), "auto.png");

					autosCallePrimaria[k] = autoRegenerado;
					rasengan = null;
					puntaje += 5;

				} else if (rasengan != null && rasengan.colisionasteConAuto(autosCallePrimaria[k])
						&& autosCallePrimaria[k].getSentido() == false) {

					Auto autoRegenerado = new Auto(altoDelAuto, anchoDeAuto,
							autosCallePrimaria[k].getX() + entorno.ancho(), autosCallePrimaria[k].getY(),
							autosCallePrimaria[k].getVelocidad(), autosCallePrimaria[k].getSentido(),
							autosCalleSecundaria[k].getVelocidadBajada(), "auto.png");
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
							autosCalleSecundaria[m].getVelocidadBajada(), "auto.png");
					autosCalleSecundaria[m] = autosRegeneradosCalleSecundaria;
					rasengan = null;
					puntaje += 5;

				} else if (rasengan != null && rasengan.colisionasteConAuto(autosCalleSecundaria[m])
						&& autosCalleSecundaria[m].getSentido() == false) {

					Auto autosRegeneradosCalleSecundaria = new Auto(altoDelAuto, anchoDeAuto,
							autosCalleSecundaria[m].getX() + entorno.ancho(), autosCalleSecundaria[m].getY(),
							autosCalleSecundaria[m].getVelocidad(), autosCalleSecundaria[m].getSentido(),
							autosCalleSecundaria[m].getVelocidadBajada(), "auto.png");
					autosCalleSecundaria[m] = autosRegeneradosCalleSecundaria;
					rasengan = null;
					puntaje += 5;

				}

			}
			
			
			if (puntaje == 50) {velocidadDeBajadaDePantalla = 1.5;}
			if (puntaje == 100) {velocidadDeBajadaDePantalla = 2;}

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

			if (entorno.sePresiono('p')) {
				partidaPausada = true;
			}}else if(partidaPausada=true){
				menu.dibujarMenu(entorno);
				System.out.println(menu.confirmarSeleccionado(entorno));
				if (menu.confirmarSeleccionado(entorno) == "jugar") {
					System.out.println("juegar");
					partidaCorriendo=true;
					partidaPausada=false;
				}
				if (menu.confirmarSeleccionado(entorno) == "salir") {
					System.out.println("salir");
					System.exit(0);
				}}
			
			
			
//			if (partidaCorriendo==false && partidaPausada==true) {
//				menu.dibujarMenu(entorno, partidaCorriendo, partidaPausada);
			
//		}
	
}

	

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

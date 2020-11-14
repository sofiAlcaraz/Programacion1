package juego;

import java.awt.Color;
import java.util.LinkedList;

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
	Menu menu = new Menu();
	private boolean partidaCorriendo;
	private boolean partidaPausada;
	private LinkedList<Rasengan> rasengans;
	private Clip jump;

	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		// Inicializar lo necesario para el juego
		partidaCorriendo = false;
		partidaPausada = false;
		velocidadDeBajadaDePantalla = 0.5;

		// BUSCAR SIMETRIA ENTRE DISTANCIA DE AUTOS

		int altoDelAuto = 35;
		int distanciaEntreAuto = 10;
		int posicionAuto = entorno.alto() / 10 + 220 / 2 - altoDelAuto / 2;
		
		// CREA AUTOS HACIA DERECHA
		autosHaciaDerecha = new Auto[16];
		double posicionEnX = entorno.ancho();
		for (int i = 0; i < autosHaciaDerecha.length; i++) {
		int altoDelAuto = 42;
		int altoDeLaCalle = 220;
		int extremoInferiorCallePrimaria = entorno.alto() / 10 + altoDeLaCalle / 2;
		int extremoInferiorCalleSecundaria = entorno.alto() / 10 * -4 + altoDeLaCalle / 2;
		int espacioEntreAutos = (altoDeLaCalle - (altoDelAuto * 4)) / 5;
		int posicionPrimerAutoCallePrimaria = extremoInferiorCallePrimaria - espacioEntreAutos - altoDelAuto / 2;
		int posicionDelSiguienteAuto = espacioEntreAutos + altoDelAuto;
		int posicionDelPrimerAutoCalleSecundaria = extremoInferiorCalleSecundaria - espacioEntreAutos - altoDelAuto / 2;
		// AUDIOS
		jump = Herramientas.cargarSonido("jump.wav");

		// CREA AUTOS EN LA CALLE PRIMARIA
		autosCallePrimaria = new Auto[16];
		double posicionEnXAutosHaciaDerecha = 0;
		double posicionEnXAutosHaciaIzquierda = entorno.ancho();
		for (int i = 0; i < autosCallePrimaria.length; i++) {


			if (i < 4) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionPrimerAutoCallePrimaria, 2, false, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCallePrimaria[i].getAncho() * 3;

			} else if (i < 8) {

				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX,			posicionAuto - (altoDelAuto * 2) - distanciaEntreAuto * 5, 3, true,
=======
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 1, true,
>>>>>>> 5a72322aeae2fa58715e0c85d77c4382d283d985
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

			else if (i < 12) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 2, false,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCallePrimaria[i].getAncho() * 3;
			} else {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[i].getAncho() * 3;
			}

		}

		// CREA AUTOS EN LA CALLE SECUNDARIA
		autosCalleSecundaria = new Auto[16];
		posicionEnXAutosHaciaDerecha = 0;
		posicionEnXAutosHaciaIzquierda = entorno.ancho();
		
		for (int i = 0; i < autosCalleSecundaria.length; i++) {
			if (i < 4) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria, 2, true, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 5;
			} else if (i < 8) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto, 1, false, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCalleSecundaria[i].getAncho() * 5;
			} else if (i < 12) {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 2, 2, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCalleSecundaria[i].getAncho() * 5;
			} else {
				autosCalleSecundaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto * 3, 1, false,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCalleSecundaria[i].getAncho() * 5;
			}
		}

		conejo = new Conejo(50, 30, entorno.ancho() / 2, entorno.alto() * 0.75, 40, velocidadDeBajadaDePantalla);

		callePrimaria = new Calle(altoDeLaCalle, 800, entorno.ancho() / 2, entorno.alto() / 10,
				velocidadDeBajadaDePantalla);
		calleSecundaria = new Calle(altoDeLaCalle, 800, entorno.ancho() / 2, (entorno.alto() / 10) * -4,
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
		// Procesamiento de un instante de tiempo
		reloj++; // fijense esto
		if (reloj / 100 != (reloj + 1) / 100) {
			System.out.println(reloj / 100);
		}

		// if (estaIniciado && !estáPausado) {

		// if (running && !pausado) {

		callePrimaria.dibujar(entorno);

		callePrimaria.deslizarHaciaAbajo();
		calleSecundaria.deslizarHaciaAbajo();

		calleSecundaria.dibujar(entorno);

		// Conejo
		conejo.esperar();
		conejo.dibujar(entorno);

		if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			conejo.saltar();
			jump.start();
		}

		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			conejo.saltarIzquierda();
			jump.start();
		}

		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			conejo.saltarDerecha(entorno);
			jump.start();

		}

		// SONIDOS EN EL JUEGO
		if (entorno.sePresiono('a') || entorno.sePresiono('s') || entorno.sePresiono('d') || entorno.sePresiono('w')
				|| entorno.sePresiono(entorno.TECLA_ABAJO) || entorno.sePresiono(entorno.TECLA_ARRIBA)
				|| entorno.sePresiono(entorno.TECLA_DERECHA) || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			jump.start();

		}

		for (Auto a : autosCallePrimaria) {
			if (a != null) {
				a.dibujar(entorno);
				a.mover(entorno,callePrimaria);
			}
		}

		for (Auto a : autosCalleSecundaria) {
			if (a != null) {
				a.dibujar(entorno);
				a.mover(entorno,calleSecundaria);
			}
		}

		// agregar intentos :)
<<<<<<< HEAD
//		if (conejo.chocasteAlgunAuto(autosHaciaIzquierda) || conejo.chocasteAlgunAuto(autosHaciaDerecha)) {
//			conejo = null;// FIXME
//			System.out.println("CONEJO CHOCO CON AUTO");
//		}
//		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			//rasengans.add(conejo.disparar());
			// pasado cierto tiempo o llegada cierta x ..matarlo
=======
		if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)) {
			conejo = null;// FIXME
			System.out.println("CONEJO CHOCO CON AUTO");
		}
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			rasengans.add(conejo.disparar());
			// pasado cierto tiempo o llegada cierta y ..matarlo
>>>>>>> 5a72322aeae2fa58715e0c85d77c4382d283d985
			// con un for each y matarlos o..un metodo,pero con este no se me ocurre como
		//}

		if (entorno.sePresiono('p')) {
			partidaPausada = true;
		}

		// }

//		if (!partidaCorriendo || partidaPausada) {
//			menu.dibujarMenu(entorno, this);
//		}

		// imprime la accion actual
		// System.out.println(menu.getAccion());

	}

	// si hay algún setter en el código, va a reentrega
//	public void setRunning(boolean value) {
//		this.running = value;
//	}
//
//	public void setPausado(boolean value) {
//		this.pausado = value;
//	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

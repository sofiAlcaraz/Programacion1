package juego;

import java.awt.Color;

import javax.sound.sampled.Clip;

import entorno.Entorno;
import entorno.Herramientas;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {
	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private int reloj;
	private double velocidadDeBajadaDePantalla;
	private Conejo conejo;
	private Calle callePrimaria;
	private Calle calleSecundaria;
	private Auto[] autosCalleSecundaria;
	private Auto[] autosCallePrimaria;
	private Rasengan[] rasengans; // lista mutable de los Rasengans
//	Menu menu = new Menu();
	private boolean partidaCorriendo;
	private boolean partidaPausada;
	private Clip jump;
	private int intentos;
	private int puntaje;
	private int saltos;
	private int altoDelAuto;
	private int altoDeLaCalle;
	private int extremoInferiorCallePrimaria;
	private int extremoInferiorCalleSecundaria;
	private int espacioEntreAutos;
	private int posicionPrimerAutoCallePrimaria;
	private int posicionDelSiguienteAuto;
	private int posicionDelPrimerAutoCalleSecundaria;
	private double posicionEnXAutosHaciaDerecha;
	private double posicionEnXAutosHaciaIzquierda; 
	
	public Juego() {
		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Conejo Ninja", 800, 600);
		// Inicializar lo necesario para el juego
		partidaCorriendo = false;
		partidaPausada = false;
		velocidadDeBajadaDePantalla = 1;

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
		jump = Herramientas.cargarSonido("jump.wav");

		// CREA AUTOS EN LA CALLE PRIMARIA
		autosCallePrimaria = new Auto[16];
		posicionEnXAutosHaciaDerecha = 0;
		posicionEnXAutosHaciaIzquierda = entorno.ancho();

		for (int i = 0; i < autosCallePrimaria.length; i++) {
			if (i < 4) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionPrimerAutoCallePrimaria, 2, false, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCallePrimaria[i].getAncho() * 3;

			} else if (i < 8) {
				autosCallePrimaria[i] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 1, true,
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
						posicionDelPrimerAutoCalleSecundaria - posicionDelSiguienteAuto, 1, false,
						velocidadDeBajadaDePantalla);
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

		conejo = new Conejo(30, 30, entorno.ancho() / 2, posicionPrimerAutoCallePrimaria + 420, 40,
				velocidadDeBajadaDePantalla);

		callePrimaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, entorno.alto() / 10,
				velocidadDeBajadaDePantalla);
		calleSecundaria = new Calle(altoDeLaCalle, 810, entorno.ancho() / 2, (entorno.alto() / 10) * -4,
				velocidadDeBajadaDePantalla);

		intentos = 1;

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
		// rasengans.clear();
		// if (estaIniciado && !estáPausado) {

		// if (running && !pausado) {
		// if(intentos!=0) {

		if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			conejo.saltar();
			saltos++;
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

		if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
				|| conejo.seFueDePantalla()) {
			intentos--;
		}
		if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {
			// mutar array
			//crea rasengasn
			if (rasengans != null){
				Rasengan[] r = new Rasengan[rasengans.length + 1];
				for (int i = 0; i < rasengans.length; i++) {
					r[i] = rasengans[i];
				}
				r[r.length - 1] = conejo.disparar();
				rasengans = r;
			} else {
				Rasengan[] r = new Rasengan[1];
				r[0] = conejo.disparar();
				rasengans = r;
			}
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

		// control de rasengans   
		if (rasengans != null) {
			for (int i = 0; i < rasengans.length; i++) {
				if (rasengans[i].destruisteAuto(autosCallePrimaria) || rasengans[i].destruisteAuto(autosCalleSecundaria)
						|| rasengans[i].saleDePantalla()) {
					puntaje+=5;
					// para rasengan
					Rasengan[] r = new Rasengan[rasengans.length - 1];
					int controlador = 0;
					if (r != null) {
						for (int j = 0; j < rasengans.length; j++) {
							if (j != i) {
								r[controlador] = rasengans[j];
								controlador++;
							}
						}
						rasengans = r;
					}
					
					// para destruir y generar un auto

				} else {
					rasengans[i].mover();
					rasengans[i].dibujar(entorno);
				}
			}
		}
		
//		for(int ñ=0;ñ<autosCallePrimaria.length;ñ++) {
//			if(autosCallePrimaria[ñ]==null && ñ<4) {
//				autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
//						posicionPrimerAutoCallePrimaria, 2, false, velocidadDeBajadaDePantalla);
//				posicionEnXAutosHaciaDerecha += autosCallePrimaria[ñ].getAncho() * 3;
//			}if(autosCallePrimaria[ñ]==null && ñ<8) {
//				autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
//						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 1, true,
//						velocidadDeBajadaDePantalla);
//				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[ñ].getAncho() * 3;
//			}if(autosCallePrimaria[ñ]==null && ñ<12) {
//				autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
//						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 2, false,
//						velocidadDeBajadaDePantalla);
//				posicionEnXAutosHaciaDerecha += autosCallePrimaria[ñ].getAncho() * 3;
//			}else {
//			autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
//					posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 1, true,
//					velocidadDeBajadaDePantalla);
//			posicionEnXAutosHaciaIzquierda += autosCallePrimaria[ñ].getAncho() * 3;
//			}
//		}
//		for(int ñ=0;ñ<autosCallePrimaria.length;ñ++) {
//			if(autosCallePrimaria[ñ]==null && ñ<4) {
//				autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
//						posicionPrimerAutoCallePrimaria, 2, false, velocidadDeBajadaDePantalla);
//				posicionEnXAutosHaciaDerecha += autosCallePrimaria[ñ].getAncho() * 3;
//			}if(autosCallePrimaria[ñ]==null && ñ<8) {
//				autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
//						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 1, true,
//						velocidadDeBajadaDePantalla);
//				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[ñ].getAncho() * 3;
//			}if(autosCallePrimaria[ñ]==null && ñ<12) {
//				autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
//						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 2, false,
//						velocidadDeBajadaDePantalla);
//				posicionEnXAutosHaciaDerecha += autosCallePrimaria[ñ].getAncho() * 3;
//			}else {
//			autosCallePrimaria[ñ] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
//					posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 1, true,
//					velocidadDeBajadaDePantalla);
//			posicionEnXAutosHaciaIzquierda += autosCallePrimaria[ñ].getAncho() * 3;
//		}
//			}
//		
		
		
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

//		if (!partidaCorriendo || partidaPausada) {
//			menu.dibujarMenu(entorno, this);
//		}

	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

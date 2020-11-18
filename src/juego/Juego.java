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
	private Clip jump;
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
	double posicionEnXAutosHaciaDerecha;
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
			//jump.start();
		}

		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			conejo.saltarIzquierda();
			//jump.start();
		}

		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			conejo.saltarDerecha(entorno);
			//jump.start();

		}

		// SONIDOS EN EL JUEGO
		if (entorno.sePresiono('a') || entorno.sePresiono('s') || entorno.sePresiono('d') || entorno.sePresiono('w')
				|| entorno.sePresiono(entorno.TECLA_ABAJO) || entorno.sePresiono(entorno.TECLA_ARRIBA)
				|| entorno.sePresiono(entorno.TECLA_DERECHA) || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			//jump.start();

		}

		if (conejo.chocasteAlgunAuto(autosCalleSecundaria) || conejo.chocasteAlgunAuto(autosCallePrimaria)
				|| conejo.seFueDePantalla()) {
			intentos--;
		}
		
		

	// dibujar rasengans y controlar sus colisiones

	if(entorno.sePresiono('p')){
		partidaPausada = true;
	}

	callePrimaria.deslizarHaciaAbajo(entorno);
	callePrimaria.dibujar(entorno);

	calleSecundaria.deslizarHaciaAbajo(entorno);
	calleSecundaria.dibujar(entorno);

	// Conejo
	conejo.esperar();
	conejo.dibujar(entorno);

	for(Auto a:autosCallePrimaria){
		if (a != null) {
			a.mover(entorno);
			a.dibujar(entorno);
		}
	}

	for(Auto a:autosCalleSecundaria){
		if (a != null) {
			a.mover(entorno);
			a.dibujar(entorno);
		}
	}
	if (entorno.sePresiono(entorno.TECLA_ESPACIO)) {//FIXME
		int i=0;
		rasengans[i++]=conejo.disparar();
		
	}
	
		for (int i = 0; i < rasengans.length; i++) {//FIXME
			if (rasengans[i] != null && rasengans[i].destruisteAuto(autosCallePrimaria)) {
				puntaje += 5;
				rasengans[i] = null;
			}
			if (rasengans[i] != null && rasengans[i].destruisteAuto(autosCalleSecundaria)) {
				puntaje += 5;
				rasengans[i] = null;
			} 
			else if (rasengans[i] != null && !rasengans[i].saleDePantalla()) {
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

		for (int l = 0; l < autosCallePrimaria.length; l++) { //FIXME
			if (autosCallePrimaria[l] == null && l < 4) {
				autosCallePrimaria[l] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionPrimerAutoCallePrimaria, 2, false, velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCallePrimaria[l].getAncho() * 3;
			}
			if (autosCallePrimaria[l] == null && l>=4 && l < 8) {
				autosCallePrimaria[l] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[l].getAncho() * 3;
			}
			if (autosCallePrimaria[l] == null && l>=8 && l < 12) {
				autosCallePrimaria[l] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaDerecha,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 2, 2, false,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaDerecha += autosCallePrimaria[l].getAncho() * 3;
			} else if(autosCallePrimaria[l] == null && l>=12 && l < 16){
				autosCallePrimaria[l] = new Auto(altoDelAuto, 50, posicionEnXAutosHaciaIzquierda,
						posicionPrimerAutoCallePrimaria - posicionDelSiguienteAuto * 3, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnXAutosHaciaIzquierda += autosCallePrimaria[l].getAncho() * 3;
			}
		}
		
		
	}

		// System.out.println(rasengans.size());

//		if (!partidaCorriendo || partidaPausada) {
//			menu.dibujarMenu(entorno, this);
//		}

		// imprime la accion actual
		// System.out.println(menu.getAccion());

	

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

package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;
	private Calle calle;
	private Calle calle2;
	// FIXME
	private Auto[] autosHaciaIzquierda;
	private Auto[] autosHaciaDerecha;

	private double velocidadDeBajadaDePantalla;

	public Juego() {

		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);

		// Inicializar lo que haga falta para el juego
		// ...
		
		// BUSCAR SIMETRIA ENTRE DISTANCIA DE AUTOS
		int altoDelAuto = 35;
		int anchoDeCalle = 220;
		int posicionCalle = entorno.alto() / 10;
		int distanciaEntreAuto = 10;
		int posicionAuto = entorno.alto() / 10 + 220/2 - altoDelAuto/2;
		velocidadDeBajadaDePantalla = 0.5;

		// QUIZAS UBICAR EN OTRO LADO
		// CREA AUTOS HACIA DERECHA
		autosHaciaDerecha = new Auto[16];
		

		double posicionEnX = entorno.ancho();
		for (int i = 0; i < autosHaciaDerecha.length; i++) {
			if (i < 4) {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX, posicionAuto-distanciaEntreAuto, 3, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 5;
			} else if (i < 8) {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX, posicionAuto-(altoDelAuto*2)-distanciaEntreAuto, 8, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 4;
			} else if (i < 12) {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 + 70, 5, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 3;
			} else {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 - 70, 1, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 8;
			}

		}
		// CREA AUTOS HACIA IZQUIERDA
		autosHaciaIzquierda = new Auto[16];
		posicionEnX = 0;
		for (int i = 0; i < autosHaciaIzquierda.length; i++) {
			if (i < 4) {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX, entorno.alto() / 10 + altoDelAuto, 3, false,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			} else if (i < 8) {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX, entorno.alto() / 10, 8, false,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			} else if (i < 12) {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 + 70, 5, false,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			} else {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 - 70, 1, false,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			}
		}

		conejo = new Conejo(50, 30, entorno.ancho() / 2, entorno.alto() * 0.75, 40, velocidadDeBajadaDePantalla);
//		auto = new Auto(60, 45, entorno.ancho(), entorno.alto() / 4, 3, true, velocidadDeBajadaDePantalla);// pruebo con
		calle = new Calle(220, 800, entorno.ancho() / 2, entorno.alto() / 10, velocidadDeBajadaDePantalla); // auto mas
		calle2 = new Calle(220, 800, entorno.ancho() / 2, (entorno.alto() / 10) * -4, velocidadDeBajadaDePantalla); // leno

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
		// ...

		calle.dibujar(entorno);
		calle.mover();
		calle2.dibujar(entorno);
		calle2.mover();

		// Conejo
		conejo.dibujar(entorno);
		conejo.esperar();

		if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			conejo.saltar();
		}

		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			conejo.saltarIzquierda();
		}

		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			conejo.saltarDerecha(entorno);
		}

		for (int i = 0; i < autosHaciaDerecha.length; i++) {
			autosHaciaDerecha[i].dibujar(entorno);
			autosHaciaDerecha[i].mover(entorno);
		}
		for (int i = 0; i < autosHaciaIzquierda.length; i++) {
			autosHaciaIzquierda[i].dibujar(entorno);
			autosHaciaIzquierda[i].mover(entorno);
		}

		// Auto
//		auto.dibujar(entorno);
////		auto2.dibujar(entorno);
//
//		auto.mover(entorno);

		// calle

//		auto2.mover("derecha", entorno);  // haciendo cagadas a full
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

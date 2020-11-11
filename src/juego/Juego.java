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
	private Auto auto;
	// private Auto auto2;

	private double velocidadDeBajadaDePantalla;

	public Juego() {

		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);

		// Inicializar lo que haga falta para el juego
		// ...
		velocidadDeBajadaDePantalla = 0.5;
		conejo = new Conejo(50, 30, entorno.ancho() / 2, entorno.alto() * 0.75, 40, velocidadDeBajadaDePantalla);
		auto = new Auto(60, 45, entorno.ancho(), entorno.alto() / 4, 3, true, velocidadDeBajadaDePantalla);// pruebo con
		calle = new Calle(200, 800, entorno.ancho() / 2, entorno.alto() / 10, velocidadDeBajadaDePantalla); // auto mas
		calle2 = new Calle(200, 800, entorno.ancho() / 2, (entorno.alto() / 10) * -4, velocidadDeBajadaDePantalla); // leno
//		this.auto2 = new Auto(60, 45, 0, entorno.alto() / 2, 8);

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
		conejo.esperar(); // el conejo no se cae, FIXME

		if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			conejo.saltar();
		}

		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			conejo.saltarIzquierda();
		}

		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			conejo.saltarDerecha(entorno);
		}

		// Auto
		auto.dibujar(entorno);
//		auto2.dibujar(entorno);

		auto.mover(entorno);

		// calle

//		auto2.mover("derecha", entorno);  // haciendo cagadas a full
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

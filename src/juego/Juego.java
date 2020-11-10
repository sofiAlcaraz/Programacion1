package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo conejo;

	// FIXME
	private Auto auto;
	private Auto auto2;
	
	private double velocidadDeBajadaDePantalla;

	public Juego() {

		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);

		// Inicializar lo que haga falta para el juego
		// ...
		this.conejo = new Conejo(50, 30, entorno.ancho() / 2, entorno.alto() * 0.75, 40);
		this.auto = new Auto(60, 45, entorno.ancho(), entorno.alto() / 4, 5, true);
//		this.auto2 = new Auto(60, 45, 0, entorno.alto() / 2, 8);
		
		// Inicia el juego!
		this.entorno.iniciar();
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
		
//		auto2.mover("derecha", entorno);  // haciendo cagadas a full
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

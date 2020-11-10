package juego;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private Conejo rabbit;
	private Auto auto;
	private Auto auto2;
	public Juego() {

		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno",1200, 950);
		
		// Inicializar lo que haga falta para el juego
		// ...
		this.rabbit = new Conejo(50, 30 , entorno.ancho()/2, entorno.alto()*0.75, 40);
		this.auto = new Auto(60, 45,entorno.ancho() , entorno.alto()/4, 5);
		this.auto2 = new Auto(60, 45,0 , entorno.alto()/2, 8);
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
		rabbit.dibujar(entorno);
		rabbit.caer();
		
		if (entorno.sePresiono('w') || entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			rabbit.moverseAdelante();
		}
		if (entorno.sePresiono('a') || entorno.sePresiono(entorno.TECLA_IZQUIERDA)) {
			rabbit.moverseIzquierda();
		}
		if (entorno.sePresiono('d') || entorno.sePresiono(entorno.TECLA_DERECHA)) {
			rabbit.moverseDerecha(entorno);
		}
	
	// Auto
		auto.dibujar(entorno);
		auto2.dibujar(entorno);
		auto.moverse("izquierda",entorno);
		auto2.moverse("derecha",entorno);
	
	}	
	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

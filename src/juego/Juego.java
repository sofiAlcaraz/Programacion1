package juego;

import java.util.LinkedList;

import entorno.Entorno;
import entorno.InterfaceJuego;

public class Juego extends InterfaceJuego {

	// El objeto Entorno que controla el tiempo y otros
	private Entorno entorno;
	private double velocidadDeBajadaDePantalla;
	private Conejo conejo;
	private Calle calle;
	private Calle calle2;
	// FIXME
	private Auto[] autosHaciaIzquierda;
	private Auto[] autosHaciaDerecha;
	private Rasengan r;
	private boolean running = false;
	private boolean pausado = false;
	Menu menu = new Menu();

	public Juego() {

		// Inicializa el objeto entorno
		this.entorno = new Entorno(this, "Prueba del Entorno", 800, 600);

		// Inicializar lo que haga falta para el juego
		// ...
		velocidadDeBajadaDePantalla = 0.5;
		
		calle = new Calle(300, 800, entorno.ancho() / 2, entorno.alto() / 10, velocidadDeBajadaDePantalla); 
		calle2 = new Calle(300, 800, entorno.ancho() / 2, (entorno.alto() / 10) * -6, velocidadDeBajadaDePantalla); 
		// BUSCAR SIMETRIA ENTRE DISTANCIA DE AUTOS
		int altoDelAuto = 35;
		int distanciaEntreAuto = 10;
		int posicionAuto = entorno.alto() / 10 + 220 / 2 - altoDelAuto / 2;
		// QUIZAS UBICAR EN OTRO LADO

		// CREA AUTOS HACIA DERECHA
		autosHaciaDerecha = new Auto[16];
		double posicionEnX = entorno.ancho();
		for (int i = 0; i < autosHaciaDerecha.length; i++) {
			if (i < 4) {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX, posicionAuto - distanciaEntreAuto, 2, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 5;
			} else if (i < 8) {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX,
						posicionAuto - (altoDelAuto * 2) - distanciaEntreAuto * 5, 3, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 4;
			} else if (i < 12) {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 + 70, 2, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 3;
			} else {
				autosHaciaDerecha[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 - 70, 3, true,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaDerecha[i].getAncho() * 8;
			}
		}
		// CREA AUTOS HACIA IZQUIERDA
		autosHaciaIzquierda = new Auto[16];
		posicionEnX = 0;
		for (int i = 0; i < autosHaciaIzquierda.length; i++) {
			if (i < 4) {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX, posicionAuto - distanciaEntreAuto - altoDelAuto,
						2, false, velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			} else if (i < 8) {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX,
						posicionAuto - distanciaEntreAuto - altoDelAuto * 2, 1, false, velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			} else if (i < 12) {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 + 70, 2, false,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			} else {
				autosHaciaIzquierda[i] = new Auto(35, 50, posicionEnX, (entorno.alto() / 10) * -4 - 70, 1, false,
						velocidadDeBajadaDePantalla);
				posicionEnX += autosHaciaIzquierda[i].getAncho() * 5;
			}
		}

		conejo = new Conejo(50, 30, entorno.ancho() / 2, entorno.alto() * 0.75, 40, velocidadDeBajadaDePantalla);

		r=new Rasengan(conejo);

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
		if (running && !pausado) {
			calle.mover();
			calle.dibujar(entorno);
			calle2.mover();
			calle2.dibujar(entorno);
			if(entorno.estaPresionada(entorno.TECLA_ESPACIO)) {
				r.dibujarRasengan(entorno);
				
			}
			// Conejo
			conejo.esperar();
			conejo.dibujar(entorno);

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
				// condcicion null
				if(r.colicionAuto(autosHaciaDerecha[i])){
					
				}
				autosHaciaDerecha[i].dibujar(entorno);
				autosHaciaDerecha[i].mover(entorno);
			}
			for (int i = 0; i < autosHaciaIzquierda.length; i++) {
				autosHaciaIzquierda[i].dibujar(entorno);
				autosHaciaIzquierda[i].mover(entorno);
			}

		
			
		
			
			
			
			
			if (conejo.controlarColision(this)) {
				System.out.println("Está colisionando");
			}

			if (entorno.sePresiono('p')) {
				pausado = true;
			}

		}	
		

		else if (!running || pausado) {
			menu.dibujarMenu(entorno, this);
		}

		// imprime la accion actual
		System.out.println(menu.getAccion());

	}

	public void setRunning(boolean value) {
		this.running = value;
	}

	public void setPausado(boolean value) {
		this.pausado = value;
	}

	public Auto[] getAutosDerecha() {
		return this.autosHaciaDerecha;
	}

	public Auto[] getAutosIzquierda() {
		return this.autosHaciaIzquierda;
	}

	@SuppressWarnings("unused")
	public static void main(String[] args) {
		Juego juego = new Juego();
	}

}

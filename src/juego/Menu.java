package juego;

import entorno.Entorno;
import java.awt.Color;

public class Menu {
	// para boton jugar
	// si son constantes
	private double posicionDelBotonJugarX;
	private double posicionDelBotonJugarY;
	// para boton salir
	private double posicionDelBotonSalirX;
	private double posicionDelBotonSalirY;
	// marcador de seleccion
	private double posicionDelCursorX;
	private double posicionDelCursorY;

	private String accion;

	public Menu() {
		posicionDelBotonJugarX = 800 / 2;
		posicionDelBotonJugarY = 600 / 2 - 50;
		// para boton salir
		posicionDelBotonSalirX = 800 / 2;
		posicionDelBotonSalirY = 600 / 2 + 50;
		// marcador de seleccion
		posicionDelCursorX = 800 / 2 - 150 / 2 - 30;
		posicionDelCursorY = posicionDelBotonJugarY;
		this.accion = "Jugar";

	}

	public void dibujarMenu(Entorno entorno) {

		this.accion = "jugar";

		botonJugar(entorno);
		botonSalir(entorno);
		mostrarSeleccionado(entorno);
		actualizarSeleccion(entorno);
		
	}
	
	
	

	public String actualizarSeleccion(Entorno entorno) {
		if (entorno.sePresiono(entorno.TECLA_ABAJO)) {
			posicionDelCursorX = posicionDelBotonSalirY;
			return "salir";
		}
		if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			posicionDelCursorY = posicionDelBotonJugarY;
			return "jugar";
		}
	 return "nada";

//	private void ejecutarAccion(Entorno entorno, boolean corriendo, boolean pausado) {
//		if (entorno.sePresiono('x')) {
//			if (accion == "jugar") {
//				corriendo=true;
//				pausado=false;
//			}
//			if (accion == "salir") {
//				System.exit(0);
//			}
//		}
	}

	private void mostrarSeleccionado(Entorno entorno) {
		entorno.dibujarTriangulo(posicionDelCursorX, posicionDelCursorY, 20, 50, 0, Color.BLUE);
	}

	private void botonJugar(Entorno entorno) {
		entorno.dibujarRectangulo(posicionDelBotonJugarX, posicionDelBotonJugarY, 150, 50, 0, Color.GREEN);
	}

	private void botonSalir(Entorno entorno) {
		entorno.dibujarRectangulo(posicionDelBotonSalirX, posicionDelBotonSalirY, 150, 50, 0, Color.RED);
	}


}

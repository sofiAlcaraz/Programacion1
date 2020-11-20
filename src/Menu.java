

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

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
	Image fondo;

	public Menu() {
		posicionDelBotonJugarX = 800 / 2;
		posicionDelBotonJugarY = 600 / 2 - 50;
		// para boton salir
		posicionDelBotonSalirX = 800 / 2;
		posicionDelBotonSalirY = 600 / 2 + 50;
		// marcador de seleccion
		posicionDelCursorX = 800 / 2 - 150 / 2 - 30;
		posicionDelCursorY = posicionDelBotonJugarY;
		fondo=Herramientas.cargarImagen("fondo.jpg");

	}

	public void dibujarMenu(Entorno entorno) {
		entorno.dibujarImagen(fondo, entorno.ancho()/2, entorno.alto()/2,0, 1);
		botonJugar(entorno);
		botonSalir(entorno);
		mostrarSeleccionado(entorno);
		actualizarCursor(entorno);

	}

	public void actualizarCursor(Entorno entorno) {
		if (entorno.sePresiono(entorno.TECLA_ABAJO)) {
			posicionDelCursorY = posicionDelBotonSalirY;

		}
		if (entorno.sePresiono(entorno.TECLA_ARRIBA)) {
			posicionDelCursorY = posicionDelBotonJugarY;

		}
	}

	public String confirmarSeleccionado(Entorno entorno) {
		if (entorno.sePresiono(entorno.TECLA_ENTER) && posicionDelCursorY == posicionDelBotonSalirY) {
			return "salir";
		}
		if (entorno.sePresiono(entorno.TECLA_ENTER) && posicionDelCursorY == posicionDelBotonJugarY) {
			return "jugar";
		}
		return "";
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

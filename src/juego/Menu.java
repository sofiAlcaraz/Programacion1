package juego;

import entorno.Entorno;
import entorno.Herramientas;

import java.awt.Color;
import java.awt.Image;

public class Menu {
	// para boton jugar
	private double posicionDelBotonJugarX;
	private double posicionDelBotonJugarY;
	// para boton salir
	private double posicionDelBotonSalirX;
	private double posicionDelBotonSalirY;
	// marcador de seleccion
	private double posicionDelCursorX;
	private double posicionDelCursorY;
	private Image botonJugar;
	private Image botonSalir;

	public Menu() {
		this.posicionDelBotonJugarX = 800 / 2;
		this.posicionDelBotonJugarY = 600 / 2 - 50;
		// para boton salir
		this.posicionDelBotonSalirX = 800 / 2;
		this.posicionDelBotonSalirY = 600 / 2 + 50;
		// marcador de seleccion
		this.posicionDelCursorX = 800 / 2 - 150 / 2 - 30;
		this.posicionDelCursorY = posicionDelBotonJugarY;

		this.botonJugar = Herramientas.cargarImagen("botonJugar.png");
		this.botonSalir = Herramientas.cargarImagen("botonSalir.png");

	}

	public void dibujarMenu(Entorno entorno) {
		botonJugar(entorno);
		botonSalir(entorno);
		mostrarSeleccionado(entorno);
		actualizarCursor(entorno);
		entorno.cambiarFont("Comic Sans MS", 30, Color.BLUE);
		entorno.escribirTexto("Para ganar, el puntaje debe llegar a 100 puntos", 85, 600/2 -100 );

	}

	// acá no debería haber interacción con le usuarie
	public void actualizarCursor(Entorno entorno) {
		if (entorno.sePresiono(entorno.TECLA_ABAJO)) { // esto no va acá
			posicionDelCursorY = posicionDelBotonSalirY;

		}
		if (entorno.sePresiono(entorno.TECLA_ARRIBA)) { // esto no va acá
			posicionDelCursorY = posicionDelBotonJugarY;

		}
	}

	public String confirmarSeleccionado(Entorno entorno) {
		if (entorno.sePresiono(entorno.TECLA_ENTER) && posicionDelCursorY == posicionDelBotonSalirY) { // esto no va acá
			return "salir";
		}
		if (entorno.sePresiono(entorno.TECLA_ENTER) && posicionDelCursorY == posicionDelBotonJugarY) { // esto no va acá
			return "jugar";
		}
		return "";
	}

	private void mostrarSeleccionado(Entorno entorno) {
		entorno.dibujarTriangulo(posicionDelCursorX, posicionDelCursorY, 20, 50, 0, Color.BLUE);
	}

	private void botonJugar(Entorno entorno) {
		entorno.dibujarImagen(botonJugar, posicionDelBotonJugarX, posicionDelBotonJugarY, 0, 1);
	}

	private void botonSalir(Entorno entorno) {
		entorno.dibujarImagen(botonSalir, posicionDelBotonSalirX, posicionDelBotonSalirY, 0, 1);
	}

}

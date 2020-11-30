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

	public void dibujar(Entorno entorno) {
		botonJugar(entorno);
		botonSalir(entorno);
		mostrarSeleccionado(entorno);
		entorno.cambiarFont("Comic Sans MS", 30, Color.BLUE);
		entorno.escribirTexto("Para ganar, el puntaje debe llegar a 100 puntos", 85, 600 / 2 - 100);
	}

	public void actualizarCursorYconFechas(boolean cursor) {
		if (cursor == true) {
			posicionDelCursorY = posicionDelBotonSalirY;
		} else {
			posicionDelCursorY = posicionDelBotonJugarY;
		}
	}

	public String confirmarSeleccionado() {
		if (posicionDelCursorY == posicionDelBotonSalirY) {
			return "salir";
		}
		if (posicionDelCursorY == posicionDelBotonJugarY) {
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

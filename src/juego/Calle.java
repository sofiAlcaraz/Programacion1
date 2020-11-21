package juego;

import java.awt.Color;

import entorno.Entorno;

public class Calle {
	private double largo;
	private double ancho;
	private double x;
	private double y;
	private double factorDeDesplazamiento;
	private Color color;

	public Calle(double largo, double ancho, double x, double y, double factorDeDesplazamiento) {
		this.largo = largo;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.factorDeDesplazamiento = factorDeDesplazamiento;
		this.color = color.GRAY;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, largo, 0, color);
	}

	public void deslizarHaciaAbajo(Entorno entorno) {
		y += factorDeDesplazamiento;
		if (y - largo / 2 > 600) {
			y -= entorno.alto() * 2;
		}
	}

	public double getLargo() {
		return largo;
	}

	public double posicionVertical() {
		return y;
	}

}

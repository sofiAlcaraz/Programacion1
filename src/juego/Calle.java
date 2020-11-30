package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Calle {
	private double largo;
	private double x;
	private double y;
	private double factorDeDesplazamiento;
	private Image imagenCalle;

	public Calle(double largo, double ancho, double x, double y, double factorDeDesplazamiento) {
		this.largo = largo;
		this.x = x;
		this.y = y;
		this.factorDeDesplazamiento = factorDeDesplazamiento;
		this.imagenCalle = Herramientas.cargarImagen("calle.png");
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagenCalle, x, y, 0, 1);
	}

	public void deslizarHaciaAbajo(Entorno entorno) {
		y += factorDeDesplazamiento;
		if (y - largo / 2 > entorno.alto()) {
			y -= entorno.alto() * 2;
		}
	}

//	public double getLargo() {
//		return largo;
//	}

//	public double posicionVertical() {
//		return y;
//	}

}

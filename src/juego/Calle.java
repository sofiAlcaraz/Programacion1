package juego;

import java.awt.Color;

import entorno.Entorno;

public class Calle {
	private double largo;
	private double ancho;
	private Color color;
	private double x;
	private double y;
	private double deslizarPantalla;
	private Auto[] autos;

	public Calle(double altura, double ancho, double x, double y, double bajadaDePantalla) {
		this.largo = altura;
		this.ancho = ancho;
		this.color = color.GRAY;
		this.x = x;
		this.y = y;
		this.deslizarPantalla = bajadaDePantalla;
		this.autos = new Auto[6];
	}

	public void mover() {
		y += deslizarPantalla;
		if (y - largo / 2 >= 600) {
			y -= y + largo / 2;
		}

	}
//PRUEBA DE COMMIT
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, largo, 0, color);
	}

	public void agregarAutos(Entorno entorno) {
		double alto = entorno.alto() / 4;
		int cant = 0;
		for (int i = 0; i < autos.length; i++) {
			if (autos[i] == null) {
				autos[i] = new Auto(60, 45, entorno.ancho(), alto, 3, true, deslizarPantalla);
			}
			if (cant == 3) {
				alto++;
			}
		}
	}

	public double getLargo() {
		return largo;
	}

	public double getAncho() {
		return ancho;
	}

	public Color getColor() {
		return color;
	}

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	public double getDeslizarPantalla() {
		return deslizarPantalla;
	}

	public Auto[] getAutos() {
		return autos;
	}

}

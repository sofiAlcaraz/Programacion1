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
	//private Auto[] autosDer;
	//private Auto[] autoIzq;

	public Calle(double altura, double ancho, double x, double y, double bajadaDePantalla) {
		this.largo = altura;
		this.ancho = ancho;
		this.color = color.GRAY;
		this.x = x;
		this.y = y;
		//this.deslizarPantalla = bajadaDePantalla;
		//this.autosDer = new Auto[6];
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
	
	/*public void agregarAutos(Entorno entorno,Auto auto,boolean sentido) {
	}*/

	public double getLargo() {
		return largo;
	}

	public double getAncho() {
		return ancho;
	}

	

	public double getX() {
		return x;
	}

	public double getY() {
		return y;
	}

	
	
	

}

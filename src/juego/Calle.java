package juego;

import java.awt.Color;

import entorno.Entorno;

public class Calle {

	private double x;
	private double y;
	private double factorDeDesplazamiento; 
	private double largo;
	private double ancho;
	private Color color;

	public Calle(double altura, double ancho, double x, double y, double bajadaDePantalla) {
		this.largo = altura;
		this.ancho = ancho;
		this.color = color.GRAY;
		this.x = x;
		this.y = y;
		this.factorDeDesplazamiento=bajadaDePantalla;
	}
	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, largo, 0, color);
	}
	
	public void mover() {
		y += factorDeDesplazamiento;
		if (y - largo / 2 >= 600) {
			y -= y + largo / 2;
		}
	}
	
		

}

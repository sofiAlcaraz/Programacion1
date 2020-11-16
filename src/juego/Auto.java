package juego;

import java.awt.Color;

import entorno.Entorno;

public class Auto {
	private double alto;
	private double ancho;
	private double x;
	private double y;
	private double velocidad;
	private boolean sentido;
	private double bajadaDePantalla;
	private Color color;
	

	public Auto(double altura, double ancho, double x, double y, double velocidad, boolean sentido,
			double bajadaDePantalla) {
		this.alto = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.bajadaDePantalla = bajadaDePantalla;
		this.color = color.RED;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, alto, 0, color);// cambie direccion por cero ya que no lo vamos a usar
	}

	public void mover(Entorno entorno) {
		y += this.bajadaDePantalla;
		if (y - ancho / 2 > 600) {
			y-=entorno.alto()*2;
		}
		if (sentido) {
			if (x - ancho/2 - velocidad < 0) {
				x = entorno.ancho();
			} else {
				x -= velocidad;
			}
		} else if (x + ancho/2 + velocidad > entorno.ancho()) {
			x = 0;
		} else {
			x += velocidad;
		}		
	}

	public double getAltura() {
		return alto;
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

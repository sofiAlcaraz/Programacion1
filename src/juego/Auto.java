package juego;

import java.awt.Color;

import entorno.Entorno;

public class Auto {

	private double altura;
	private double ancho;
	private double x;
	private double y;
	private double velocidad;
	private boolean sentido;
	private double bajadaDePantalla;
	private Color color;

	public Auto(double altura, double ancho, double x, double y, double velocidad, boolean sentido, double movbajada) {
		this.altura = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.color = Color.WHITE;
		this.sentido = sentido;
		this.bajadaDePantalla = movbajada;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, altura, 0, color);// cambie direccion por cero ya que no lo vamos a usar
	}

	// FIXME
	public void mover(Entorno entorno) {
		y += this.bajadaDePantalla;
		if (y - ancho / 2 >= 600) {
			y -= y + ancho / 2;
		}
		if (sentido) {
			if (x-ancho-velocidad<=0) {
				x = entorno.ancho();
			}else {
				x -= velocidad;
			}
		}else if (x+ancho+velocidad>=entorno.ancho()){
			x = 0;
		}else {
			x += velocidad;
		}
	}

	public double getAltura() {
		return altura;
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

	public double getVelocidad() {
		return velocidad;
	}

	public boolean isSentido() {
		return sentido;
	}

	public double getBajadaDePantalla() {
		return bajadaDePantalla;
	}

	public Color getColor() {
		return color;
	}
}
package juego;

import java.awt.Color;

import entorno.Entorno;

public class Rasengan {
	private double x;
	private double y;
	private double velocidad;
	private Color color;
	private int diametro;

	public Rasengan(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 5;
		this.color = Color.YELLOW;
		this.diametro = 20;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarCirculo(x, y, diametro, color);
	}

	public void mover() {
		y -= velocidad;
	}

	public boolean saleDePantalla() {
		if (y + diametro < 0)
			return true;

		return false;
	}

	/*
	 * El metodo "colicionaste" gestiona la colision "RECTANGULO-CIRCULO" para
	 * controlar si el rasengan colisiono con algun auto.
	 */
	public boolean colisionasteConAuto(Auto auto) {

		if (auto != null) {
			// el auto es un rectangulo
			double x1 = x;
			double y1 = y;

			if (this.x < auto.getX()) {
				x1 = auto.getX(); // lado izquierdo
			} else if (this.x > auto.getX() + auto.getAncho()) {
				x1 = auto.getX() + auto.getAncho(); // lado derecho
			}
			if (this.y < auto.getY()) {
				y1 = auto.getY(); // lado superior
			} else if (this.y > auto.getY() + auto.getAltura()) {
				y1 = auto.getY() + auto.getAltura(); // lado inferior
			}
			double distX = this.x - x1;
			double distY = this.y - y1;
			double distance = Math.sqrt((distX * distX) + (distY * distY));

			if (distance <= this.diametro) {
				return true;
			}
		}
		return false;
	}

	public double getY() {

		return y;
	}

}

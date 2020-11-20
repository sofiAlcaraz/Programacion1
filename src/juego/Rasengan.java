package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Rasengan {
	private double x;
	private double y;
	private double velocidad;
	private int diametro;
	private Image imagen;

	public Rasengan(double x, double y) {
		this.x = x;
		this.y = y;
		this.velocidad = 5;
		this.diametro = 20;
		this.imagen = Herramientas.cargarImagen("rasengan.png");
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarImagen(imagen, x, y, 0, 0.050);
	}

	public void mover() {
		y -= velocidad;
	}

	public boolean saleDePantalla() {
		
		if (y + diametro < 0)
			return true;

		return false;
	}

	public boolean colisionasteConAuto(Auto auto) {

		if (auto != null) {
			double x1 = x;
			double y1 = y;

			if (this.x < auto.getX()) {
				x1 = auto.getX(); // lado izquierdo
			} else if (x > auto.getX() + auto.getAncho()) {
				x1 = auto.getX() + auto.getAncho(); // lado derecho
			}
			if (this.y < auto.getY()) {
				y1 = auto.getY(); // lado superior
			} else if (y > auto.getY() + auto.getAltura()) {
				y1 = auto.getY() + auto.getAltura(); // lado inferior
			}
			double distX = x - x1;
			double distY = y - y1;
			double distancia = Math.sqrt((distX * distX) + (distY * distY));

			if (distancia <= diametro) {
				return true;
			}
		}
		return false;
	}

}

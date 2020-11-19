package juego;

import java.awt.Image;

import entorno.Entorno;
import entorno.Herramientas;

public class Auto {

	private double alto;
	private double ancho;
	private double x;
	private double y;
	private double velocidad;
	private boolean sentido;
	private double bajadaDePantalla;
	private Image imagenAutoHaciaDerecha;
	private Image imagenAutoHaciaIzquierda;

	public Auto(double altura, double ancho, double x, double y, double velocidad, boolean sentido,
			double bajadaDePantalla) {
		this.alto = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.sentido = sentido;
		this.bajadaDePantalla = bajadaDePantalla;
		this.imagenAutoHaciaDerecha = Herramientas.cargarImagen("autoSentidoDerecho.png");
		this.imagenAutoHaciaIzquierda=Herramientas.cargarImagen("autoSentidoIzquierdo.png");
		
	}

	public void dibujar(Entorno entorno) {
		if(sentido==true) {
			entorno.dibujarImagen(imagenAutoHaciaIzquierda, x, y, 0, 0.050);
		}
		entorno.dibujarImagen(imagenAutoHaciaDerecha, x, y, 0, 0.050);
	}

	public void avanzar(Entorno entorno) {
		y += bajadaDePantalla;
		if (y - ancho / 2 > 600) {
			y -= entorno.alto() * 2;
		}
		if (sentido) {
			if (x + ancho < 0) {
				x += entorno.ancho() * 2;
			} else {
				x -= velocidad;
			}
		} else if (x > entorno.ancho() + ancho) {
			x -= entorno.ancho() * 2;
		} else {
			x += velocidad;
		}
	}

	public boolean getSentido() {
		return sentido;
	}

	public double getVelocidad() {
		return velocidad;
	}

	public double getVelocidadBajada() {
		return bajadaDePantalla;
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

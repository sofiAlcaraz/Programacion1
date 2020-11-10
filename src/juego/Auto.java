package juego;

import java.awt.Color;

import entorno.Entorno;

public class Auto {
	private double altura;
	private double ancho;
	private double x;
	private double y;
	private double velocidad;
	private double direccion;
	private Color color;

	public Auto(double altura, double ancho, double x, double y, double velocidad) {
		this.altura = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.color = Color.WHITE;
		this.direccion = -Math.PI;

	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, ancho, altura, direccion, color);
	}

	public void moverse(String sentido, Entorno entorno) {
//      REVISAR 
//		if (!sentido.equals("izquierda") || !sentido.equals("derecha")) {
//			throw new RuntimeException("¡¡Sentido ingresado inválido!!");
//		}
		if (sentido.equals("izquierda")) {
			if (x-altura-velocidad <=-500) {
				x = entorno.ancho() + altura/2;
			}else {
				x -= velocidad;
			}
		}else {
			x += velocidad;
		}
	}
}
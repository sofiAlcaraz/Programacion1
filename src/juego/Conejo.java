package juego;

import java.awt.Color;

import entorno.Entorno;

public class Conejo {
	private double altura;
	private double ancho;
	private double x;
	private double y;
	private double velocidad;
	private double direccion;
	private Color color;

	public Conejo(double altura, double ancho, double x, double y, double velocidad) {
		this.altura = altura;
		this.ancho = ancho;
		this.x = x;
		this.y = y;
		this.velocidad = velocidad;
		this.color = Color.WHITE;
		this.direccion = -Math.PI/2;
	}

	public void dibujar(Entorno entorno) {
		entorno.dibujarRectangulo(x, y, altura, ancho, direccion, color);
	}

	public void caer () {
		y += 0.5;
	}
	
	public void moverseAdelante() {
		if (y-altura/2-velocidad <= 0) {
			y = altura/2;
		}else {
			y -= velocidad;
		}
	}

	public void moverseIzquierda() {
		//falta agregar cambio de direccion
		if (x-ancho/2-velocidad <= 0) {
			x = ancho/2;
		}else {
			x -= velocidad;
		}
		
	}

	public void moverseDerecha(Entorno entorno) {
		//falta agregar cambio de direccion	
		if (x+ancho/2+velocidad >= entorno.ancho()) {
			x = entorno.ancho() - ancho/2;
		}else {
			x += velocidad;
		}
	}
}
